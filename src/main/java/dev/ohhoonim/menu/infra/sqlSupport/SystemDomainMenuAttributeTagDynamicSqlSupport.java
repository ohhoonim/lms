package dev.ohhoonim.menu.infra.sqlSupport;

import java.sql.JDBCType;
import java.util.Date;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class SystemDomainMenuAttributeTagDynamicSqlSupport {
    public static final SystemDomainMenuAttributeTag attributeTag = new SystemDomainMenuAttributeTag();
    public static final SqlColumn<String> menuAttributeTagId = attributeTag.menuAttributeTagId;
    public static final SqlColumn<String> menuItemId = attributeTag.menuItemId;
    public static final SqlColumn<String> attribute = attributeTag.attribute;
    public static final SqlColumn<String> tag = attributeTag.tag;
    public static final SqlColumn<String> creator = attributeTag.creator;
    public static final SqlColumn<Date> created = attributeTag.created;
    public static final SqlColumn<String> modifier = attributeTag.modifier;
    public static final SqlColumn<Date> modified = attributeTag.modified;
    public static final class SystemDomainMenuAttributeTag extends AliasableSqlTable<SystemDomainMenuAttributeTag> {
        public final SqlColumn<String> menuAttributeTagId = column("menu_attribute_tag_id", JDBCType.VARCHAR);
        public final SqlColumn<String> menuItemId = column("menu_item_id", JDBCType.VARCHAR);
        public final SqlColumn<String> attribute = column("attribute", JDBCType.VARCHAR);
        public final SqlColumn<String> tag = column("tag", JDBCType.VARCHAR);
        public final SqlColumn<String> creator = column("creator", JDBCType.VARCHAR);
        public final SqlColumn<Date> created = column("created", JDBCType.TIMESTAMP);
        public final SqlColumn<String> modifier = column("modifier", JDBCType.VARCHAR);
        public final SqlColumn<Date> modified = column("modified", JDBCType.TIMESTAMP);

        public SystemDomainMenuAttributeTag() {
            super("system_domain_menu_attribute_tag", SystemDomainMenuAttributeTag::new);
        }
    }
}