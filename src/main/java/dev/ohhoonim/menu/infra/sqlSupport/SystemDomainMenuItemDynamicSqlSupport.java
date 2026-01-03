package dev.ohhoonim.menu.infra.sqlSupport;

import java.sql.JDBCType;
import java.util.Date;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SystemDomainMenuItemDynamicSqlSupport {
    public static final SystemDomainMenuItem menuItem = new SystemDomainMenuItem();
    public static final SqlColumn<String> menuItemId = menuItem.menuItemId;
    public static final SqlColumn<String> menuCode = menuItem.menuCode;
    public static final SqlColumn<String> parentMenuItem = menuItem.parentMenuItem;
    public static final SqlColumn<String> menuText = menuItem.menuText;
    public static final SqlColumn<String> targetUrl = menuItem.targetUrl;
    public static final SqlColumn<Integer> sortOrder = menuItem.sortOrder;
    public static final SqlColumn<Boolean> isActive = menuItem.isActive;
    public static final SqlColumn<String> iconClass = menuItem.iconClass;
    public static final SqlColumn<String> sensitivityLevel = menuItem.sensitivityLevel;
    public static final SqlColumn<String> creator = menuItem.creator;
    public static final SqlColumn<Date> created = menuItem.created;
    public static final SqlColumn<String> modifier = menuItem.modifier;
    public static final SqlColumn<Date> modified = menuItem.modified;
    public static final class SystemDomainMenuItem extends AliasableSqlTable<SystemDomainMenuItem> {
        public final SqlColumn<String> menuItemId = column("menu_item_id", JDBCType.VARCHAR);
        public final SqlColumn<String> menuCode = column("menu_code", JDBCType.VARCHAR);
        public final SqlColumn<String> parentMenuItem = column("parent_menu_item", JDBCType.VARCHAR);
        public final SqlColumn<String> menuText = column("menu_text", JDBCType.VARCHAR);
        public final SqlColumn<String> targetUrl = column("target_url", JDBCType.VARCHAR);
        public final SqlColumn<Integer> sortOrder = column("sort_order", JDBCType.SMALLINT);
        public final SqlColumn<Boolean> isActive = column("is_active", JDBCType.BIT);
        public final SqlColumn<String> iconClass = column("icon_class", JDBCType.VARCHAR);
        public final SqlColumn<String> sensitivityLevel = column("sensitivity_level", JDBCType.VARCHAR);
        public final SqlColumn<String> creator = column("creator", JDBCType.VARCHAR);
        public final SqlColumn<Date> created = column("created", JDBCType.TIMESTAMP);
        public final SqlColumn<String> modifier = column("modifier", JDBCType.VARCHAR);
        public final SqlColumn<Date> modified = column("modified", JDBCType.TIMESTAMP);

        public SystemDomainMenuItem() {
            super("system_domain_menu_item", SystemDomainMenuItem::new);
        }
    }
}