package dev.ohhoonim.menu.model;

import java.net.URI;
import java.util.List;
import org.jspecify.annotations.Nullable;
import dev.ohhoonim.component.auditing.model.Entity;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.menu.SensitivityLevelCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public sealed class MenuItem implements Entity permits MenuItemService {

    private @Nullable Id menuItemId;
    private String menuCode;
    private @Nullable MenuItem parentMenuItem;
    private MenuText menuText;
    private URI targetUrl;
    private Integer sortOrder;
    private Boolean isActive;
    private String iconClass;
    private SensitivityLevelCode sensitivityLevel;

    private List<MenuAttributeTag> requiredPermissionTag;

    @Builder
    public MenuItem(Id menuItemId, String menuCode, MenuItem parentMenuItem, MenuText menuText,
            URI targetUrl, Integer sortOrder, Boolean isActive, String iconClass,
            List<MenuAttributeTag> requiredPermissionTag, SensitivityLevelCode sensitivityLevel) {
        this.menuItemId = menuItemId;
        this.menuCode = menuCode;
        this.parentMenuItem = parentMenuItem;
        this.menuText = menuText;
        this.targetUrl = targetUrl;
        this.sortOrder = sortOrder;
        this.isActive = isActive;
        this.iconClass = iconClass;
        this.requiredPermissionTag = requiredPermissionTag;
        this.sensitivityLevel = sensitivityLevel;
    }

    @Override
    public Id getId() {
        return this.menuItemId;
    }

}
