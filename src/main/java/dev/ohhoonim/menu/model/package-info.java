@NullMarked
package dev.ohhoonim.menu.model;

import org.jspecify.annotations.NullMarked;

/*
@startuml

interface MenuCrudActivity  <UC-MENU-01> {
    addMenu(): void
    modifyMenu(): void
    removeMenu(): void
    menuById(): MenuItem
}

interface MenuI18nActivity <UC-MENU-02>{
    menuTextsByMenuItem(): MenuText[0..*]
    getMenuText(): MenuText
    addMenuText(): void
    modifyMenuText(): void
    removeMenuText(): void
}

interface MenuAttributeTagActivity <UC-MENU-05>{
    addAttributeTag(): void
    modifyAttrbuteTag(): void
    removeAttributeTag(): void
    attributeTags(): MenuAttributeTag [0..*]

}

class MenuItemService {
    - menuItemPort: MenuItemPort
}

interface MenuItemPort

MenuItemService .left.> MenuItemPort

class MenuItem {
    menuItemId: Id [0..1]
    menuCode: String {unique}
    parentMenuItem: MenuItem [0..1]
    menuText: MenuText {menuItemId: Id}
    targetUrl: URI
    sortOrder: Integer
    isActive: Boolean
    iconClass: String
    sensitivityLevel: SensitivityLevelCode
    requiredPermissionTag: MenuAttributeTag [1..*]
}

class MenuText {
    menuTextId: Id
    menuItem: Id
    text: String
    laguageCode: LanguageCode
}

class MenuAttributeTag {
    menuAttributeTagId: Id
    menuItemId: Id
    attribute: String
    tag: String
}

MenuItem ..>  MenuItem
MenuItem   [menuItemId: Id] *.left.> MenuText
MenuItem [menuItemId: Id]  *.right.> MenuAttributeTag

MenuItemService .up.|> MenuCrudActivity
MenuItemService .up.|> MenuI18nActivity
MenuItemService .up.|> MenuAttributeTagActivity
MenuItemService .up.|> MenuOrderHierachyActivity 
MenuItemService --|> MenuItem

class MenuVisivilityService {
    - menuTreePort: MenuTreePort
    - menuTreeFactoryPort: MenuTreeFactoryPort
}

class MenuTree {
    menuCode: String
    menuText: String
    targetUrl: URI
    iconClass: String
    sortOrder: Integer
    children: MenuTree [0..*]

}

MenuVisivilityService --|> MenuTree 

interface MenuVisivilityActivity <UC-MENU-03> {
    createTree: MenuTree
}

interface MenuPolicyResultCachingActivity <UC-MENU-06> {
    resultCaching(): void
}

interface MenuOrderHierachyActivity <UC-MENU-04> {
    void modifyMenuOrder()
    void modifyParentMenuItem()
}

MenuVisivilityService .up.|> MenuVisivilityActivity
MenuVisivilityService .up.|> MenuPolicyResultCachingActivity 

interface MenuTreePort
interface MenuTreeFactoryPort {
    createTree(allMenuItems: List<MenuItem>): MenuTree
}

class MenuTreeFactory {
    - userService: UserAttributeActivity
    - {static} ROOT_CODE {"ROOT"}
}

MenuTreeFactory -up-|> MenuTreeFactoryPort

MenuVisivilityService ..> MenuTreePort
MenuVisivilityService ..> MenuTreeFactoryPort

class MenuItemAdaptor {
    - menuItemMapper: MenuItemMapper
    - menuCaching: MenuCaching
}

MenuItemAdaptor ..|> MenuItemPort
MenuItemAdaptor ..|> MenuTreePort


@enduml

@startuml
enum SesitivityLevelCode <<masterCode>>{
    P1(public)
    P2(restricted)
    P3(high confidential)
    P4(core secret)
}

enum LanguageCode <<masterCode>>{
    KO(ko-KR)
    EN(en-US)
}

enum Role <<masterCode>>{
    ADMIN
    USER
}

enum Department <<masterCode>>{
    SYSTEM
    SALES
}

@enduml
*/
