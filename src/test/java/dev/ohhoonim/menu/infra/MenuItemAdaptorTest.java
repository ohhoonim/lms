package dev.ohhoonim.menu.infra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.menu.SensitivityLevelCode;
import dev.ohhoonim.menu.model.LanguageCode;
import dev.ohhoonim.menu.model.MenuAttributeTag;
import dev.ohhoonim.menu.model.MenuItem;
import dev.ohhoonim.menu.model.MenuText;
import dev.ohhoonim.menu.model.MenuTree;
import io.jsonwebtoken.lang.Collections;

@ExtendWith(MockitoExtension.class)
class MenuItemAdaptorTest {

    @InjectMocks
    MenuItemAdaptor menuItemAdaptor;

    @Mock
    MenuItemMapper menuItemMapper;

    @Mock
    MenuCaching menuCaching;

    @Test
    void allMenus() {
        when(menuItemMapper.allMenus(any())).thenReturn(Collections.emptyList());
        var allMenus = menuItemAdaptor.allMenus();
        assertThat(allMenus).isEmpty();
    }

    @Test
    void resultCaching() {
        var menuTree = MenuTree.builder().build();
        menuItemAdaptor.resultCaching(menuTree);
        verify(menuCaching, times(1)).menuTreeCaching(any(), any());
    }

    @Test
    void menuTextsByMenuItem() {
        List<MenuText> menuTexts = Collections.emptyList();
        when(menuItemMapper.menuTextsByMenuItem(any())).thenReturn(menuTexts);
        var resultList = menuItemAdaptor.menuTextsByMenuItem(new Id());
        assertThat(resultList).isEmpty();
    }

    @Test
    void getMenuText() {

        var menuItemId = new Id();
        var languageCode = LanguageCode.KO;

        when(menuItemMapper.getMenuText(any(SelectStatementProvider.class)))
                .thenReturn(Optional.empty());

        var result = menuItemAdaptor.getMenuText(menuItemId, languageCode);
        assertThat(result).isEmpty();
    }

    @Test
    void addMenuText() {
        menuItemAdaptor.addMenuText(MenuText.builder().build());
        verify(menuItemMapper, times(1)).addMenuText(any());
    }

    @Test
    void modifyMenuText() {
        menuItemAdaptor.modifyMenuText(MenuText.builder().menuTextId(new Id()).menuItemId(new Id())
                .languageCode(LanguageCode.EN).build());
        verify(menuItemMapper, times(1)).modifyMenuText(any());
    }

    @Test
    void removeMenuText() {
        menuItemAdaptor.removeMenuText(new Id());
        verify(menuItemMapper, times(1)).removeMenuText(any());
    }

    @Test
    void addMenu() {
        var newMenuItem = MenuItem.builder().menuItemId(new Id()).build();
        menuItemAdaptor.addMenu(newMenuItem);
        verify(menuItemMapper, times(1)).addMenu(any());
    }

    @Test
    void modifyMenu() throws URISyntaxException {
        menuItemAdaptor.modifyMenu(MenuItem.builder().menuItemId(new Id())
                .parentMenuItem(MenuItem.builder().menuItemId(new Id()).build())
                .menuText(MenuText.builder().build()).targetUrl(new URI("http://localhost:8080"))
                .sensitivityLevel(SensitivityLevelCode.P1).build());
        verify(menuItemMapper, times(1)).modifyMenu(any(UpdateStatementProvider.class));
    }

    @Test
    void removeMenu() {
        menuItemAdaptor.removeMenu(new Id());
        verify(menuItemMapper, times(1)).removeMenu(any(DeleteStatementProvider.class));
    }

    @Test
    void menuById() {
        when(menuItemMapper.menuById(any(SelectStatementProvider.class)))
                .thenReturn(Optional.empty());
        var result = menuItemAdaptor.menuById(new Id());
        assertThat(result).isEmpty();
    }

    @Test
    void modifyOrder() {
        String menuCode = "MENU_PROFILE";
        Integer order = 1;
        menuItemAdaptor.modifyOrder(menuCode, order);
        verify(menuItemMapper, times(1)).modifyOrder(any(UpdateStatementProvider.class));
    }

    @Test
    void modifyParentMenuItem() {
        menuItemAdaptor.modifyParentMenuItem("MENU_PROFILE", "MENU_SYSTEJ");
        verify(menuItemMapper, times(1)).modifyParentMenuItem(any(UpdateStatementProvider.class));
    }

    @Test
    void menuByCode() {
        var menuCode = "MENU_PROFILE";
        when(menuItemMapper.menuByCode(any())).thenReturn(Optional.empty());
        var menuItem = menuItemAdaptor.menuByCode(menuCode);
        assertThat(menuItem).isEmpty();
    }

    @Test
    void addAttributeTag() {
        var menuAttributeTag = new MenuAttributeTag(null, null, null, null);
        menuItemAdaptor.addAttributeTag(menuAttributeTag);
        verify(menuItemMapper, times(1)).addAttributeTag(any(InsertStatementProvider.class));
    }

    @Test
    void modifyAttributeTag() {
        var menuAttributeTag = new MenuAttributeTag(new Id(), new Id(), "attribute name", "tag");
        menuItemAdaptor.modifyAttributeTag(menuAttributeTag);
        verify(menuItemMapper, times(1)).modifyAttributeTag(any(UpdateStatementProvider.class));
    }

    @Test
    void removeAttributeTag() {
        var menuAttributeTag = new MenuAttributeTag(new Id(), null, null, null);
        menuItemAdaptor.removeAttributeTag(menuAttributeTag);
        verify(menuItemMapper, times(1)).removeAttributeTag(any(DeleteStatementProvider.class));
    }

    @Test
    void attributeTags() {
        var menuItemId = new Id();
        when(menuItemMapper.attributeTags(any(SelectStatementProvider.class)))
                .thenReturn(Collections.emptyList());
        var result = menuItemAdaptor.attributeTags(menuItemId);
        assertThat(result).isEmpty();
    }
}

