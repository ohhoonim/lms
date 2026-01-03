package dev.ohhoonim.menu.application;

import java.util.List;
import dev.ohhoonim.menu.model.MenuAttributeTag;

// UC-MENU-05 메뉴 ABAC 태그 설정
public interface MenuAttributeTagActivity {

    void addAttributeTag(String menuCode, MenuAttributeTag permission);

    void modifyAttrbuteTag(String menuCode, MenuAttributeTag permission);

    void removeAttributeTag(String menuCode, MenuAttributeTag persission);

    List<MenuAttributeTag> attributeTags(String menuCode);

}
