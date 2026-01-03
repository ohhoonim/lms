package dev.ohhoonim.menu.infra;

import static dev.ohhoonim.menu.infra.sqlSupport.SystemDomainMenuAttributeTagDynamicSqlSupport.attributeTag;
import static dev.ohhoonim.menu.infra.sqlSupport.SystemDomainMenuItemDynamicSqlSupport.menuItem;
import static dev.ohhoonim.menu.infra.sqlSupport.SystemDomainMenuTextDynamicSqlSupport.mnText;
import static org.mybatis.dynamic.sql.SqlBuilder.deleteFrom;
import static org.mybatis.dynamic.sql.SqlBuilder.insert;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.select;
import static org.mybatis.dynamic.sql.SqlBuilder.update;
import java.util.List;
import java.util.Optional;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.springframework.stereotype.Component;
import dev.ohhoonim.component.auditing.application.CurrentUser;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.menu.model.LanguageCode;
import dev.ohhoonim.menu.model.MenuAttributeTag;
import dev.ohhoonim.menu.model.MenuItem;
import dev.ohhoonim.menu.model.MenuText;
import dev.ohhoonim.menu.model.MenuTree;
import dev.ohhoonim.menu.port.MenuItemPort;
import dev.ohhoonim.menu.port.MenuTreePort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MenuItemAdaptor implements MenuItemPort, MenuTreePort {

    private final MenuItemMapper menuItemMapper;
    private final MenuCaching menuCaching;

    @Override
    public List<MenuItem> allMenus() {
        SelectStatementProvider selectStatement = select(menuItem.menuCode, menuItem.menuText,
                menuItem.targetUrl, menuItem.iconClass, menuItem.sortOrder).from(menuItem).build()
                        .render(RenderingStrategies.MYBATIS3);
        return menuItemMapper.allMenus(selectStatement);
    }

    @Override
    public void resultCaching(MenuTree menuTree) {
        var username = CurrentUser.getUsername();
        menuCaching.menuTreeCaching(username, menuTree);
    }

    @Override
    public List<MenuText> menuTextsByMenuItem(Id menuItemId) {
        SelectStatementProvider selectStatement =
                select(mnText.menuTextId, mnText.menuItemId, mnText.text, mnText.languageCode)
                        .from(mnText).where(mnText.menuItemId, isEqualTo(menuItemId.getId()))
                        .build().render(RenderingStrategies.MYBATIS3);
        return menuItemMapper.menuTextsByMenuItem(selectStatement);
    }

    @Override
    public Optional<MenuText> getMenuText(Id menuItemId, LanguageCode languageCode) {
        SelectStatementProvider selectStatement =
                select(mnText.menuTextId, mnText.menuItemId, mnText.text, mnText.languageCode)
                        .from(mnText).where(mnText.menuItemId, isEqualTo(menuItemId.getId()))
                        .and(mnText.languageCode, isEqualTo(languageCode.toString())).build()
                        .render(RenderingStrategies.MYBATIS3);
        return menuItemMapper.getMenuText(selectStatement);
    }

    @Override
    public void addMenuText(MenuText newMenuText) {
        InsertStatementProvider<MenuText> insertStatement =
                insert(newMenuText).into(mnText).map(mnText.menuTextId).toProperty("menuTextId")
                        .map(mnText.menuItemId).toProperty("menuItemId").map(mnText.text)
                        .toProperty("text").map(mnText.languageCode).toProperty("languageCode")
                        .build().render(RenderingStrategies.MYBATIS3);
        menuItemMapper.addMenuText(insertStatement);
    }

    @Override
    public void modifyMenuText(MenuText menuText) {
        UpdateStatementProvider updateStatement = update(mnText).set(mnText.menuItemId)
                .equalToWhenPresent(menuText.getMenuItemId().toString()).set(mnText.text)
                .equalToWhenPresent(menuText::getText).set(mnText.languageCode)
                .equalToWhenPresent(menuText.getLanguageCode().toString())
                .where(mnText.menuTextId, isEqualTo(menuText.getMenuTextId().toString())).build()
                .render(RenderingStrategies.MYBATIS3);
        menuItemMapper.modifyMenuText(updateStatement);
    }

    @Override
    public void removeMenuText(Id menuTextId) {
        DeleteStatementProvider deleteStatement =
                deleteFrom(mnText).where(mnText.menuTextId, isEqualTo(menuTextId.toString()))
                        .build().render(RenderingStrategies.MYBATIS3);
        menuItemMapper.removeMenuText(deleteStatement);
    }

    @Override
    public void addMenu(MenuItem newMenuItem) {
        InsertStatementProvider<MenuItem> insertStatement = insert(newMenuItem).into(menuItem)
                .map(menuItem.menuItemId).toProperty("menuItemId").map(menuItem.menuCode)
                .toProperty("menuCode").map(menuItem.parentMenuItem).toProperty("parentMenuItem")
                .map(menuItem.menuText).toProperty("menuText").map(menuItem.targetUrl)
                .toProperty("targetUrl").map(menuItem.sortOrder).toProperty("sortOrder")
                .map(menuItem.isActive).toProperty("isActive").map(menuItem.iconClass)
                .toProperty("iconClass").map(menuItem.sensitivityLevel)
                .toProperty("sensitivityLevel").map(menuItem.creator).toProperty("creator")
                .map(menuItem.created).toProperty("created").map(menuItem.modifier)
                .toProperty("modifier").map(menuItem.modified).toProperty("modified").build()
                .render(RenderingStrategies.MYBATIS3);
        menuItemMapper.addMenu(insertStatement);
    }

    @Override
    public void modifyMenu(MenuItem modifiedMenuItem) {
        UpdateStatementProvider updateStatement = update(menuItem).set(menuItem.menuCode)
                .equalTo(modifiedMenuItem.getMenuCode()).set(menuItem.parentMenuItem)
                .equalTo(modifiedMenuItem.getParentMenuItem().toString()).set(menuItem.menuText)
                .equalTo(modifiedMenuItem.getMenuText().getText()).set(menuItem.targetUrl)
                .equalTo(modifiedMenuItem.getTargetUrl().toString()).set(menuItem.sortOrder)
                .equalTo(modifiedMenuItem.getSortOrder()).set(menuItem.isActive)
                .equalTo(modifiedMenuItem.getIsActive()).set(menuItem.iconClass)
                .equalTo(modifiedMenuItem.getIconClass()).set(menuItem.sensitivityLevel)
                .equalTo(modifiedMenuItem.getSensitivityLevel().toString())
                .where(menuItem.menuItemId, isEqualTo(modifiedMenuItem.getMenuItemId().toString()))
                .build().render(RenderingStrategies.MYBATIS3);
        menuItemMapper.modifyMenu(updateStatement);
    }

    @Override
    public void removeMenu(Id menuItemId) {
        DeleteStatementProvider deleteStatement =
                deleteFrom(menuItem).where(menuItem.menuItemId, isEqualTo(menuItemId.toString()))
                        .build().render(RenderingStrategies.MYBATIS3);
        menuItemMapper.removeMenu(deleteStatement);
    }

    @Override
    public Optional<MenuItem> menuById(Id menuItemId) {
        SelectStatementProvider selectStatement = select(menuItem.menuItemId, menuItem.menuCode,
                menuItem.parentMenuItem, menuItem.menuText, menuItem.targetUrl, menuItem.sortOrder,
                menuItem.isActive, menuItem.iconClass, menuItem.sensitivityLevel).from(menuItem)
                        .where(menuItem.menuItemId, isEqualTo(menuItemId.toString())).build()
                        .render(RenderingStrategies.MYBATIS3);
        return menuItemMapper.menuById(selectStatement);
    }

    @Override
    public void modifyOrder(String menuItemCode, Integer order) {
        UpdateStatementProvider updateStatement = update(menuItem).set(menuItem.sortOrder)
                .equalTo(order).where(menuItem.menuCode, isEqualTo(menuItemCode)).build()
                .render(RenderingStrategies.MYBATIS3);
        menuItemMapper.modifyOrder(updateStatement);
    }

    @Override
    public void modifyParentMenuItem(String currentMenuCode, String parentMenuCode) {
        UpdateStatementProvider updateStatement = update(menuItem).set(menuItem.menuCode)
                .equalTo(parentMenuCode).where(menuItem.menuCode, isEqualTo(currentMenuCode))
                .build().render(RenderingStrategies.MYBATIS3);
        menuItemMapper.modifyParentMenuItem(updateStatement);
    }

    @Override
    public Optional<MenuItem> menuByCode(String menuCode) {
        SelectStatementProvider selectStatement = select(menuItem.menuItemId, menuItem.menuCode,
                menuItem.parentMenuItem, menuItem.menuText, menuItem.targetUrl, menuItem.sortOrder,
                menuItem.isActive, menuItem.iconClass, menuItem.sensitivityLevel).from(menuItem)
                        .where(menuItem.menuItemId, isEqualTo(menuCode.toString())).build()
                        .render(RenderingStrategies.MYBATIS3);
        return menuItemMapper.menuByCode(selectStatement);
    }

    @Override
    public void addAttributeTag(MenuAttributeTag menuAttributeTag) {
        InsertStatementProvider<MenuAttributeTag> insertStatement =
                insert(menuAttributeTag).into(attributeTag).map(attributeTag.menuAttributeTagId)
                        .toProperty("menuAttributeTagId").map(attributeTag.menuItemId)
                        .toProperty("menuItemId").map(attributeTag.attribute)
                        .toProperty("attribute").map(attributeTag.tag).toProperty("tag").build()
                        .render(RenderingStrategies.MYBATIS3);
        menuItemMapper.addAttributeTag(insertStatement);
    }

    @Override
    public void modifyAttributeTag(MenuAttributeTag menuAttributeTag) {
        UpdateStatementProvider updateStatement = update(attributeTag).set(attributeTag.menuItemId)
                .equalToWhenPresent(menuAttributeTag.menuAttributeTagId().toString())
                .set(attributeTag.attribute).equalToWhenPresent(menuAttributeTag.attribute())
                .set(attributeTag.tag).equalToWhenPresent(menuAttributeTag.tag())
                .where(attributeTag.menuAttributeTagId,
                        isEqualTo(menuAttributeTag.menuAttributeTagId().toString()))
                .build().render(RenderingStrategies.MYBATIS3);
        menuItemMapper.modifyAttributeTag(updateStatement);
    }

    @Override
    public void removeAttributeTag(MenuAttributeTag menuAttributeTag) {
        DeleteStatementProvider deleteStatement = deleteFrom(attributeTag)
                .where(attributeTag.menuAttributeTagId,
                        isEqualTo(menuAttributeTag.menuAttributeTagId().toString()))
                .build().render(RenderingStrategies.MYBATIS3);
        menuItemMapper.removeAttributeTag(deleteStatement);
    }

    @Override
    public List<MenuAttributeTag> attributeTags(Id menuItemId) {
        SelectStatementProvider selectStatement =
                select(attributeTag.menuAttributeTagId, attributeTag.menuItemId,
                        attributeTag.attribute, attributeTag.tag).from(attributeTag)
                                .where(attributeTag.menuItemId, isEqualTo(menuItemId.toString()))
                                .build().render(RenderingStrategies.MYBATIS3);

        return menuItemMapper.attributeTags(selectStatement);
    }
}
