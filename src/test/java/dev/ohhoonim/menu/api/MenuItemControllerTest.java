package dev.ohhoonim.menu.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.menu.api.MenuItemController.MenuCodeAttribute;
import dev.ohhoonim.menu.api.MenuItemController.MenuCodeOrderMap;
import dev.ohhoonim.menu.api.MenuItemController.ParentMenuCode;
import dev.ohhoonim.menu.model.LanguageCode;
import dev.ohhoonim.menu.model.MenuItem;
import dev.ohhoonim.menu.model.MenuItemService;
import dev.ohhoonim.menu.model.MenuText;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(MenuItemController.class)
class MenuItemControllerTest {

    @Autowired
    MockMvcTester mockMvc;

    @MockitoBean
    MenuItemService service;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void addAttributeTag() {
        MenuCodeAttribute body = new MenuCodeAttribute("MENU_MENU_MANAGEMENT", null);
        String jsonParam = objectMapper.writeValueAsString(body);
        mockMvc.post().with(csrf()).uri("/menu-item/addAttributeTag")
                .contentType(MediaType.APPLICATION_JSON).content(jsonParam).assertThat()
                .hasStatusOk();
    }

    @Test
    @WithMockUser
    void modifyAttributeTag() {
        MenuCodeAttribute body = new MenuCodeAttribute("MENU_MENU_MANAGEMENT", null);
        String jsonParam = objectMapper.writeValueAsString(body);
        mockMvc.post().with(csrf()).uri("/menu-item/modifyAttributeTag")
                .contentType(MediaType.APPLICATION_JSON).content(jsonParam).assertThat()
                .hasStatusOk();
    }

    @Test
    @WithMockUser
    void removeAttributeTag() {
        MenuCodeAttribute body = new MenuCodeAttribute("MENU_MENU_MANAGEMENT", null);
        String jsonParam = objectMapper.writeValueAsString(body);
        mockMvc.post().with(csrf()).uri("/menu-item/removeAttributeTag")
                .contentType(MediaType.APPLICATION_JSON).content(jsonParam).assertThat()
                .hasStatusOk();
    }

    @Test
    @WithMockUser
    void attributeTags() {
        var pathVariable = "MENU_MENU_MANAGEMENT";
        mockMvc.post().with(csrf()).uri("/menu-item/attributeTags/" + pathVariable)
                .contentType(MediaType.APPLICATION_JSON).content(pathVariable).assertThat()
                .hasStatusOk();
    }

    @Test
    @WithMockUser
    void menuTextsByMenuItem() {
        var pathVariable = new Id().toString();
        mockMvc.post().with(csrf()).uri("/menu-item/texts/" + pathVariable)
                .contentType(MediaType.APPLICATION_JSON).assertThat().hasStatusOk();
    }

    @Test
    @WithMockUser
    void getMenuText() {
        String menuItemId = new Id().toString();
        String languageCode = LanguageCode.KO.toString();
        mockMvc.get().uri("/menu-item/texts/" + menuItemId + "/" + languageCode)
                .contentType(MediaType.APPLICATION_JSON).assertThat().hasStatusOk();
    }

    @Test
    @WithMockUser
    void addMenuText() {
        var menuText = new MenuText(null, new Id(), "메뉴관리", LanguageCode.KO);
        // menuItemId로 세팅해서 보내줄것. menuCode로 세팅하면 안됨
        var jsonBody = objectMapper.writeValueAsString(menuText);
        mockMvc.post().uri("/menu-item/addMenuText").with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content(jsonBody).assertThat()
                .hasStatusOk();
    }

    @Test
    @WithMockUser
    void modifyMenuText() {
        var menuText = new MenuText(new Id(), null, "메뉴관리", LanguageCode.KO);
        // 수정할때는 menuItemId 없어도 됨
        var jsonBody = objectMapper.writeValueAsString(menuText);
        mockMvc.post().uri("/menu-item/modifyMenuText").with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content(jsonBody).assertThat()
                .hasStatusOk();
    }

    @Test
    @WithMockUser
    void removeMenuText() {
        String menuTextId = new Id().toString();
        mockMvc.post().uri("/menu-item/removeMenuText/" + menuTextId).with(csrf())
                .contentType(MediaType.APPLICATION_JSON).assertThat().hasStatusOk();
    }

    @Test
    @WithMockUser
    void addMenu() {
        MenuItem menuItem = new MenuItem();
        // menuItemId 세팅안해도 됨 
        // requiredPermissionTag 목록을 처리하는 로직 없음. 차후 추가할 예정
        String jsonBody = objectMapper.writeValueAsString(menuItem);
        mockMvc.post().uri("/menu-item/addMenu").with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content(jsonBody).assertThat()
                .hasStatusOk();
    }

    @Test
    @WithMockUser
    void modifyMenu() {
        MenuItem menuItem = new MenuItem();
        var jsonBody = objectMapper.writeValueAsString(menuItem);
        mockMvc.post().uri("/menu-item/modifyMenu").with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content(jsonBody).assertThat()
                .hasStatusOk();
    }

    @Test
    @WithMockUser
    void removeMenu() {
        String menuItemId = new Id().toString();
        mockMvc.post().uri("/menu-item/removeMenu/" + menuItemId).with(csrf())
                .contentType(MediaType.APPLICATION_JSON).assertThat().hasStatusOk();
    }

    @Test
    @WithMockUser
    void menuById() {
        String menuItemId = new Id().toString();
        mockMvc.get().uri("/menu-item/menuItem/" + menuItemId)
                .contentType(MediaType.APPLICATION_JSON).assertThat().hasStatusOk();
    }

    @Test
    @WithMockUser
    void modifyMenuOrder() {
        List<MenuCodeOrderMap> orderMap = List.of(new MenuCodeOrderMap("MENU_PROFILE", 2),
                new MenuCodeOrderMap("MENU_MENU_MANAGEMENT", 3),
                new MenuCodeOrderMap("MENU_USER_MANAGEMENT", 1),
                new MenuCodeOrderMap("MENU_SYSTEM", 4));
        var jsonBody = objectMapper.writeValueAsString(orderMap);
        mockMvc.post().uri("/menu-item/modifyMenuOrder").with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content(jsonBody).assertThat()
                .hasStatusOk();
    }

    @Test
    @WithMockUser
    void modifyParentMenuItem() {
        List<ParentMenuCode> parents = List.of(
            new ParentMenuCode("MENU_MENU_MANAGEMENT", "ROOT"),
            new ParentMenuCode("MENU_USER_MANAGEMENT", "MENU_SYSTEM")
        );
        var jsonBody = objectMapper.writeValueAsString(parents);
        mockMvc.post().uri("/menu-item/modifyParent").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).content(jsonBody)
            .assertThat().hasStatusOk();

        verify(service, times(2)).modifyParentMenuItem(any(), any());
    }

}
