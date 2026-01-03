package dev.ohhoonim.menu.infra;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import dev.ohhoonim.menu.model.MenuAttributeTag;
import dev.ohhoonim.menu.model.MenuItem;
import dev.ohhoonim.menu.model.MenuText;

@Mapper
public interface MenuItemMapper {

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("menuItem")
    List<MenuItem> allMenus(SelectStatementProvider selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("menuText")
    List<MenuText> menuTextsByMenuItem(SelectStatementProvider selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("menuText")
    Optional<MenuText> getMenuText(SelectStatementProvider selectStatement);

    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    void addMenuText(InsertStatementProvider<MenuText> insertStatement);

    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    void modifyMenuText(UpdateStatementProvider updateStatement);

    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    void addMenu(InsertStatementProvider<MenuItem> insertStatement);

    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    void modifyMenu(UpdateStatementProvider updateStatement);

    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    void removeMenu(DeleteStatementProvider deleteStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("menuItem")
    Optional<MenuItem> menuById(SelectStatementProvider selectStatement);

    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    void modifyOrder(UpdateStatementProvider updateStatement);

    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    void modifyParentMenuItem(UpdateStatementProvider updateStatement);

    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    void removeMenuText(DeleteStatementProvider deleteStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("menuItem")
    Optional<MenuItem> menuByCode(SelectStatementProvider selectStatement);

    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    void addAttributeTag(InsertStatementProvider<MenuAttributeTag> insertStatement);

    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    void modifyAttributeTag(UpdateStatementProvider updateStatement);

    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    void removeAttributeTag(DeleteStatementProvider deleteStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("menuAttributeTag")
    List<MenuAttributeTag> attributeTags(SelectStatementProvider selectStatement);

}
