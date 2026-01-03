package dev.ohhoonim.menu.infra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.when;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.menu.SensitivityLevelCode;
import dev.ohhoonim.menu.model.MenuAttributeTag;
import dev.ohhoonim.menu.model.MenuItem;
import dev.ohhoonim.menu.model.MenuText;
import dev.ohhoonim.menu.model.MenuTree;
import dev.ohhoonim.user.application.UserAttributeActivity;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@Slf4j
class MenuTreeFactoryTest {

    @InjectMocks
    MenuTreeFactory menuTreeFactory;

    @Mock
    UserAttributeActivity userService;

    @ParameterizedTest(name = "menuItems")
    @MethodSource("sampleMenuItems")
    void createTree(List<MenuItem> menuItems) {

        var userPermissions =
                List.of("ROLE_ADMIN", "ROLE_USER", "DEPARTMENT_SALES", "MENU_MENU_MANAGEMENT");
        var userSensitivity = SensitivityLevelCode.P2;

        when(userService.getPermissions()).thenReturn(userPermissions);
        when(userService.getSensitivity()).thenReturn(userSensitivity);

        MenuTree visivilityMenuTree = menuTreeFactory.createTree(menuItems);

        ObjectMapper objectMapper = new ObjectMapper();
        var result = objectMapper.writeValueAsString(visivilityMenuTree);

        log.info(result);

        assertThat(visivilityMenuTree.getMenuCode()).isEqualTo("ROOT");
    }

    private static Stream<Arguments> sampleMenuItems() throws URISyntaxException {
        var menuItemId = new Id();
        List<MenuAttributeTag> requiredPermisson1 =
                List.of(new MenuAttributeTag(new Id(), menuItemId, "MENU", "MENU_MANAGEMENT"),
                        new MenuAttributeTag(new Id(), menuItemId, "DEPARTMENT", "SALES"));
        List<MenuAttributeTag> requiredPermisson2 =
                List.of(new MenuAttributeTag(new Id(), menuItemId, "MENU", "MENU_PROFILE"),
                        new MenuAttributeTag(new Id(), menuItemId, "DEPARTMENT", "SALES"));
        List<MenuAttributeTag> requiredPermisson3 = List.of(
                new MenuAttributeTag(new Id(), menuItemId, "MENU", "MENU_SYSTEM_MANAGEMENT"),
                new MenuAttributeTag(new Id(), menuItemId, "DEPARTMENT", "SALES"));
        List<MenuAttributeTag> requiredPermisson4 =
                List.of(new MenuAttributeTag(new Id(), menuItemId, "MENU", "MENU_USER_MANAGEMENT"),
                        new MenuAttributeTag(new Id(), menuItemId, "DEPARTMENT", "SUPPORT"));
        List<MenuAttributeTag> requiredPermisson5 =
                List.of(new MenuAttributeTag(new Id(), menuItemId, "MENU", "MENU_PARA"),
                        new MenuAttributeTag(new Id(), menuItemId, "DEPARTMENT", "SUPPORT"));
        List<MenuAttributeTag> requiredPermisson6 =
                List.of(new MenuAttributeTag(new Id(), menuItemId, "MENU", "MENU_POST"));

        var p1 = SensitivityLevelCode.P1;

        var menuItem6 = MenuItem.builder().menuCode("MENU_POST")
                .menuText(MenuText.builder().text("블로그").build())
                .targetUrl(new URI("http://localhost/abc")).iconClass("home").isActive(true)
                .parentMenuItem(null).requiredPermissionTag(requiredPermisson6).sortOrder(1)
                .sensitivityLevel(p1).build();
        var menuItem1 = MenuItem.builder().menuCode("MENU_MENU_MANAGEMENT")
                .menuText(MenuText.builder().text("메뉴관리").build())
                .targetUrl(new URI("http://localhost/abc")).iconClass("home").isActive(true)
                .parentMenuItem(menuItem6).requiredPermissionTag(requiredPermisson1).sortOrder(1)
                .sensitivityLevel(p1).build();
        var menuItem2 = MenuItem.builder().menuCode("프로파일")
                .menuText(MenuText.builder().text("MENU_PROFILE").build())
                .targetUrl(new URI("http://localhost/abc")).iconClass("home").isActive(true)
                .parentMenuItem(menuItem1).requiredPermissionTag(requiredPermisson2).sortOrder(1)
                .sensitivityLevel(p1).build();
        var menuItem3 = MenuItem.builder().menuCode("시스템관리")
                .menuText(MenuText.builder().text("MENU_SYSTEM_MANAGEMENT").build())
                // .parentMenuItem(menuItem1)
                .targetUrl(new URI("http://localhost/abc")).iconClass("home").isActive(true)
                .requiredPermissionTag(requiredPermisson3).sortOrder(1).sensitivityLevel(p1)
                .build();
        var menuItem4 = MenuItem.builder().menuCode("사용자관리")
                .menuText(MenuText.builder().text("MENU_USER_MANAGEMENT").build())
                .targetUrl(new URI("http://localhost/abc")).iconClass("home").isActive(true)
                .parentMenuItem(menuItem1).requiredPermissionTag(requiredPermisson4).sortOrder(1)
                .sensitivityLevel(p1).build();
        var menuItem5 = MenuItem.builder().menuCode("PARA분류")
                .menuText(MenuText.builder().text("MENU_PARA").build())
                .targetUrl(new URI("http://localhost/abc")).iconClass("home").isActive(true)
                .parentMenuItem(menuItem1).requiredPermissionTag(requiredPermisson5).sortOrder(1)
                .sensitivityLevel(p1).build();
        var menuItems = List.of(menuItem1, menuItem2, menuItem3, menuItem4, menuItem5, menuItem6);
        return Stream.of(arguments(menuItems));
    }

    @Test
    void enumCompare() {
        var menuItemSensitivity = SensitivityLevelCode.P1;
        var userSensitivity = SensitivityLevelCode.P2;

        assertThat(userSensitivity.compareTo(menuItemSensitivity)).isGreaterThan(-1);
    }

    @Test
    void getMenuVisibilityPredicate() {

        var userPermissions =
                List.of("ROLE_ADMIN", "ROLE_USER", "DEPARTMENT_SALES", "MENU_MENU_MANAGEMENT");
        var userSensitivity = SensitivityLevelCode.P2;

        var menuItemId = new Id();
        List<MenuAttributeTag> requiredPermisson =
                List.of(new MenuAttributeTag(new Id(), menuItemId, "ROLE", "ADMIN"),
                        new MenuAttributeTag(new Id(), menuItemId, "ROLE", "USER"),
                        new MenuAttributeTag(new Id(), menuItemId, "MENU", "MENU_MANAGEMENT"),
                        new MenuAttributeTag(new Id(), menuItemId, "DEPARTMENT", "SALES"));

        var menuItem = new MenuItem();
        menuItem.setMenuCode("MENU_MENUMANAGEMENT");
        menuItem.setIsActive(true);
        menuItem.setRequiredPermissionTag(requiredPermisson);
        menuItem.setSensitivityLevel(SensitivityLevelCode.P1);

        boolean result =
                getMenuVisibilityPredicate(userPermissions, userSensitivity).test(menuItem);

        assertThat(result).isTrue();

    }

    private Predicate<MenuItem> getMenuVisibilityPredicate(final List<String> userPermissions,
            final SensitivityLevelCode userSensitivity) {
        return item -> {
            if (Boolean.FALSE.equals(item.getIsActive())) {
                return false;
            }

            var resultPermission = item.getRequiredPermissionTag().stream().anyMatch(attr -> {
                return userPermissions.contains(attr.getAttributeTag());
            });

            var sensitivityPermission = userSensitivity.compareTo(item.getSensitivityLevel()) > -1;

            return resultPermission && sensitivityPermission;
        };
    }


}
