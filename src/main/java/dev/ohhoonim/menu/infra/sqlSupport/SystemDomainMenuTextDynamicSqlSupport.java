package dev.ohhoonim.menu.infra.sqlSupport;

import java.sql.JDBCType;
import java.util.Date;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SystemDomainMenuTextDynamicSqlSupport {
    public static final SystemDomainMenuText mnText = new SystemDomainMenuText();
    public static final SqlColumn<String> menuTextId = mnText.menuTextId;
    public static final SqlColumn<String> menuItemId = mnText.menuItemId;
    public static final SqlColumn<String> text = mnText.text;
    public static final SqlColumn<String> languageCode = mnText.languageCode;
    public static final SqlColumn<String> creator = mnText.creator;
    public static final SqlColumn<Date> created = mnText.created;
    public static final SqlColumn<String> modifier = mnText.modifier;
    public static final SqlColumn<Date> modified = mnText.modified;

    public static final class SystemDomainMenuText extends AliasableSqlTable<SystemDomainMenuText> {
        public final SqlColumn<String> menuTextId = column("menu_text_id", JDBCType.VARCHAR);
        public final SqlColumn<String> menuItemId = column("menu_item_id", JDBCType.VARCHAR);
        public final SqlColumn<String> text = column("text", JDBCType.VARCHAR);
        public final SqlColumn<String> languageCode = column("language_code", JDBCType.CHAR);
        public final SqlColumn<String> creator = column("creator", JDBCType.VARCHAR);
        public final SqlColumn<Date> created = column("created", JDBCType.TIMESTAMP);
        public final SqlColumn<String> modifier = column("modifier", JDBCType.VARCHAR);
        public final SqlColumn<Date> modified = column("modified", JDBCType.TIMESTAMP);

        public SystemDomainMenuText() {
            super("system_domain_menu_text", SystemDomainMenuText::new);
        }
    }
}