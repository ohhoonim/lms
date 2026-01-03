package dev.ohhoonim.menu.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public sealed class MenuTree permits MenuVisibilityService {

    private String menuCode;
    private String menuText;
    private URI targetUrl;

    private String iconClass;
    private Integer sortOrder;

    private List<MenuTree> children;

    @Builder
    public MenuTree(String menuCode, String menuText, URI targetUrl, String iconClass,
            Integer sortOrder) {
        this.menuCode = menuCode;
        this.menuText = menuText;
        this.targetUrl = targetUrl;
        this.iconClass = iconClass;
        this.sortOrder = Objects.requireNonNullElse(sortOrder, 1);
        this.children = new ArrayList<>();
    }

    public void addChild(MenuTree child) {
        if (child != null) {
            this.children.add(child);
        }
    }

    public void sortChildren() {
        Collections.sort(this.children, Comparator.comparing(MenuTree::getSortOrder));

        for (MenuTree child : this.children) {
            child.sortChildren();
        }
    }

    public boolean isLeaf() {
        return this.children.isEmpty();
    }

    public List<MenuTree> getChildren() {
        return Collections.unmodifiableList(children);
    }
}
