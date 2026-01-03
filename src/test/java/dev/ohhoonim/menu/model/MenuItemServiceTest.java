package dev.ohhoonim.menu.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.menu.port.MenuItemPort;
import io.jsonwebtoken.lang.Collections;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceTest {

    @InjectMocks
    MenuItemService menuItemService;

    @Mock
    MenuItemPort menuItemPort;

    @Test
    void menuTextsByMenuItem() {
        when(menuItemPort.menuTextsByMenuItem(any())).thenReturn(List.of());
        var menuTexts = menuItemService.menuTextsByMenuItem(new Id());
        assertThat(menuTexts).isEmpty();
    }

    @Test
    void getMenuText() {
        var menuItem = new Id();
        when(menuItemPort.getMenuText(eq(menuItem), any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> menuItemService.getMenuText(menuItem, null))
                .hasMessageContaining("등록된 menu text를 찾을 수 없습니다");

    }

    @Test
    void addMenuText() {
        var newMenuText = new MenuText();
        newMenuText.setMenuTextId(null);
        newMenuText.setMenuItemId(new Id());

        menuItemService.addMenuText(newMenuText);

        verify(menuItemPort, times(1)).addMenuText(eq(newMenuText));
    }

    @Test
    void modifyMenuText() {
        var menuText = new MenuText();
        menuText.setMenuItemId(new Id());
        menuItemService.modifyMenuText(menuText);
        verify(menuItemPort, times(1)).modifyMenuText(any());
    }

    @Test
    void removeMenuText() {
        menuItemService.removeMenuText(Id.valueOf("37SV6282WX8TP8T7HSDDT326SE"));
        verify(menuItemPort, times(1)).removeMenuText(any());
    }

    @Test
    void addMenu() {
        menuItemService.addMenu(new MenuItem());
        verify(menuItemPort, times(1)).addMenu(any());
    }

    @Test
    void modifyMenu() {
        menuItemService.modifyMenu(new MenuItem().builder().menuItemId(new Id()).build());
        verify(menuItemPort, times(1)).modifyMenu(any());
    }

    @Test
    void removeMenu() {
        var menuItemId = new Id();
        menuItemService.removeMenu(menuItemId);
        verify(menuItemPort, times(1)).removeMenu(eq(menuItemId));
    }

    @Test
    void menuById() {
        when(menuItemPort.menuById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> menuItemService.menuById(new Id()))
                .hasMessageContaining("menu를 찾을 수 없습니다");
    }

    @Test
    void testModifyMenuOrder() {

        var menuCodesOrderMap = new HashMap<String, Integer>();

        menuCodesOrderMap.put("MENU_PROFILE", 1);
        menuCodesOrderMap.put("MENU_SYSTEM", 1);
        menuCodesOrderMap.put("MENU_MENU", 1);

        menuItemService.modifyMenuOrder(menuCodesOrderMap);

        verify(menuItemPort, times(3)).modifyOrder(any(), any());
    }

    @Test
    void testmodifyParentMenuItem() {
        menuItemService.modifyParentMenuItem("MENU_PROFILE", "MENU_SYSTEM_GROUP");
        verify(menuItemPort, times(1)).modifyParentMenuItem(any(), any());
    }

    @Test
    void addAttributeTag() {
        var menuCode = "MENU_PROFILE";
        var permission = new MenuAttributeTag(null, null, "MENU", "PROFILE");

        when(menuItemPort.menuByCode(any()))
                .thenReturn(Optional.of(MenuItem.builder().menuItemId(new Id()).build()));

        menuItemService.addAttributeTag(menuCode, permission);
        verify(menuItemPort, times(1)).addAttributeTag(any());
    }

    @Test
    void modifyAttributeTag() {
        var menuCode = "MENU_PROFILE";
        var permission = new MenuAttributeTag(null, null, "MENU", "PROFILE");

        when(menuItemPort.menuByCode(any()))
                .thenReturn(Optional.of(MenuItem.builder().menuItemId(new Id()).build()));

        menuItemService.modifyAttrbuteTag(menuCode, permission);
        verify(menuItemPort, times(1)).modifyAttributeTag(any());
    }

    @Test
    void removeAttributeTag() {
        var menuCode = "MENU_PROFILE";
        var permission = new MenuAttributeTag(null, null, "MENU", "PROFILE");

        when(menuItemPort.menuByCode(any()))
                .thenReturn(Optional.of(MenuItem.builder().menuItemId(new Id()).build()));

        menuItemService.removeAttributeTag(menuCode, permission);
        verify(menuItemPort, times(1)).removeAttributeTag(any());
    }

    @Test
    void attributeTags() {
        var menuCode = "MENU_PROFILE";

        when(menuItemPort.menuByCode(any()))
                .thenReturn(Optional.of(MenuItem.builder().menuItemId(new Id()).build()));
        when(menuItemPort.attributeTags(any())).thenReturn(Collections.emptyList());

        var attributeTags = menuItemService.attributeTags(menuCode);
        assertThat(attributeTags).isEmpty();
    }
}

