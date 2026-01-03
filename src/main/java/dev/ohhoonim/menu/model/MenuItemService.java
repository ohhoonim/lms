package dev.ohhoonim.menu.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.menu.application.MenuAttributeTagActivity;
import dev.ohhoonim.menu.application.MenuCrudActivity;
import dev.ohhoonim.menu.application.MenuI18NActivity;
import dev.ohhoonim.menu.application.MenuOrderHierachyActivity;
import dev.ohhoonim.menu.port.MenuItemPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public final class MenuItemService extends MenuItem implements MenuOrderHierachyActivity,
        MenuCrudActivity, MenuI18NActivity, MenuAttributeTagActivity {

    private final MenuItemPort menuItemPort;

    @Override
    public List<MenuText> menuTextsByMenuItem(Id menuItem) {
        return menuItemPort.menuTextsByMenuItem(menuItem);
    }

    @Override
    public MenuText getMenuText(Id menuItem, LanguageCode languageCode) {
        Optional<MenuText> menuText = menuItemPort.getMenuText(menuItem,
                Objects.requireNonNullElse(languageCode, LanguageCode.KO));
        return menuText.orElseThrow(() -> new RuntimeException("등록된 menu text를 찾을 수 없습니다."));
    }

    @Override
    public void addMenuText(MenuText newMenuText) {
        newMenuText.setMenuTextId(new Id());
        menuItemPort.addMenuText(newMenuText);
    }

    @Override
    public void modifyMenuText(MenuText menuText) {
        menuItemPort.modifyMenuText(menuText);
    }

    @Override
    public void removeMenuText(Id menuTextId) {
        menuItemPort.removeMenuText(menuTextId);
    }

    @Override
    public void addMenu(MenuItem newMenuItem) {
        newMenuItem.setMenuItemId(new Id());
        menuItemPort.addMenu(newMenuItem);
    }

    @Override
    public void modifyMenu(MenuItem menuItem) {
        menuItemPort.modifyMenu(menuItem);
    }

    @Override
    public void removeMenu(Id menuItemId) {
        menuItemPort.removeMenu(menuItemId);
    }

    @Override
    public MenuItem menuById(Id menuItemId) {
        Optional<MenuItem> menuItem = menuItemPort.menuById(menuItemId);
        return menuItem.orElseThrow(() -> new RuntimeException("menu를 찾을 수 없습니다"));
    }

    @Override
    public void modifyMenuOrder(Map<String, Integer> menuCodesOrderMap) {
        menuCodesOrderMap.forEach((menuCode, order) -> {
            menuItemPort.modifyOrder(menuCode, order);
        });
    }

    @Override
    public void modifyParentMenuItem(String currentMenuCode, String parentMenuCode) {
        menuItemPort.modifyParentMenuItem(currentMenuCode, parentMenuCode);
    }

    @Override
    public void addAttributeTag(String menuCode, MenuAttributeTag permission) {
        MenuItem menuItem = menuItemPort.menuByCode(menuCode)
                .orElseThrow(() -> new RuntimeException("메뉴가 존재하지 않습니다."));
        var menuAttributeTag = new MenuAttributeTag(new Id(), menuItem.getMenuItemId(),
                permission.attribute(), permission.tag());
        menuItemPort.addAttributeTag(menuAttributeTag);
    }

    @Override
    public void modifyAttrbuteTag(String menuCode, MenuAttributeTag permission) {
        MenuItem menuItem = menuItemPort.menuByCode(menuCode)
                .orElseThrow(() -> new RuntimeException("메뉴가 존재하지 않습니다."));
        var menuAttributeTag = new MenuAttributeTag(permission.menuAttributeTagId(),
                menuItem.getMenuItemId(), permission.attribute(), permission.tag());
        menuItemPort.modifyAttributeTag(menuAttributeTag);

    }

    @Override
    public void removeAttributeTag(String menuCode, MenuAttributeTag permission) {
        MenuItem menuItem = menuItemPort.menuByCode(menuCode)
                .orElseThrow(() -> new RuntimeException("메뉴가 존재하지 않습니다."));
        var menuAttributeTag = new MenuAttributeTag(permission.menuAttributeTagId(),
                menuItem.getMenuItemId(), permission.attribute(), permission.tag());
        menuItemPort.removeAttributeTag(menuAttributeTag);
    }

    @Override
    public List<MenuAttributeTag> attributeTags(String menuCode) {
        var menuItem = menuItemPort.menuByCode(menuCode)
                .orElseThrow(() -> new RuntimeException("메뉴가 존재하지 않습니다."));
        return menuItemPort.attributeTags(menuItem.getMenuItemId());
    }


}
