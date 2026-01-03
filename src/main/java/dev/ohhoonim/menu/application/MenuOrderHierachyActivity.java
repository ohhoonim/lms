package dev.ohhoonim.menu.application;

import java.util.Map;

// UC-MENU-04 메뉴 표시 순서/계층 관리
public interface MenuOrderHierachyActivity {

    void modifyMenuOrder(Map<String, Integer> menuCodesOrderMap);

    void modifyParentMenuItem(String currentMenuCode, String parentMenuCode);

}
