package dev.ohhoonim.menu.port;

import java.util.List;
import java.util.Optional;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.menu.model.LanguageCode;
import dev.ohhoonim.menu.model.MenuAttributeTag;
import dev.ohhoonim.menu.model.MenuItem;
import dev.ohhoonim.menu.model.MenuText;

public interface MenuItemPort {

    List<MenuText> menuTextsByMenuItem(Id menuItemId);

    Optional<MenuText> getMenuText(Id menuItem, LanguageCode languageCode);

    void addMenuText(MenuText newMenuText);

    void modifyMenuText(MenuText menuText);

    void removeMenuText(Id menuTextId);

    void addMenu(MenuItem newMenuItem);

    void modifyMenu(MenuItem menuItem);

    void removeMenu(Id menuItemId);

    Optional<MenuItem> menuById(Id menuItemId);

    void modifyOrder(String menuCode, Integer order);

    void modifyParentMenuItem(String currentMenuCode, String parentMenuCode);

    Optional<MenuItem> menuByCode(String menuCode);

    void addAttributeTag(MenuAttributeTag menuAttributeTag);

    void modifyAttributeTag(MenuAttributeTag menuAttributeTag);

    void removeAttributeTag(MenuAttributeTag menuAttributeTag);

    List<MenuAttributeTag> attributeTags(Id menuItemId);


}
