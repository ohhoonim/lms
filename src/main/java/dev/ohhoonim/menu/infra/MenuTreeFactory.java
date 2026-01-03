package dev.ohhoonim.menu.infra;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import dev.ohhoonim.menu.SensitivityLevelCode;
import dev.ohhoonim.menu.model.MenuItem;
import dev.ohhoonim.menu.model.MenuTree;
import dev.ohhoonim.menu.port.MenuTreeFactoryPort;
import dev.ohhoonim.user.application.UserAttributeActivity;
import lombok.RequiredArgsConstructor;

/**
 * MenuItem 리스트를 MenuTree 구조로 변환하는 팩토리 
 */
@Component
@RequiredArgsConstructor
public class MenuTreeFactory implements MenuTreeFactoryPort {

    private final UserAttributeActivity userService;
    private static final String ROOT_CODE = "ROOT";

    @Override
    public MenuTree createTree(List<MenuItem> allMenuItems) {

        List<String> userPermissions = userService.getPermissions();
        SensitivityLevelCode userSensitivity = userService.getSensitivity();

        if (allMenuItems == null || allMenuItems.isEmpty()) {
            return new MenuTree(ROOT_CODE, "Root", null, null, 0);
        }

        List<MenuItem> visibleMenuItems = allMenuItems.stream()
                .filter(this.getMenuVisibilityPredicate(userPermissions, userSensitivity)).toList();

        Map<String, List<MenuItem>> groupedByParentCode = visibleMenuItems.stream()
                .collect(Collectors.groupingBy(item -> item.getParentMenuItem() != null
                        ? item.getParentMenuItem().getMenuCode()
                        : ROOT_CODE));

        MenuTree rootTree = new MenuTree(ROOT_CODE, "Root", null, null, 0);

        List<MenuItem> rootItems =
                groupedByParentCode.getOrDefault(ROOT_CODE, Collections.emptyList());

        for (MenuItem rootItem : rootItems) {
            MenuTree childTree = buildTreeRecursive(rootItem, groupedByParentCode);
            rootTree.addChild(childTree);
        }

        rootTree.sortChildren();

        return rootTree;
    }

    private Predicate<MenuItem> getMenuVisibilityPredicate(final List<String> userPermissions,
            final SensitivityLevelCode userSensitivity) {
        return item -> {
            if (Boolean.FALSE.equals(item.getIsActive())) {
                return false;
            }

            var resultPermission = item.getRequiredPermissionTag().stream().anyMatch(attr -> {
                return userPermissions.contains(attr.getAttributeTag());
            });

            var sensitivityPermission = userSensitivity.compareTo(item.getSensitivityLevel()) > -1;

            return resultPermission && sensitivityPermission;
        };
    }

    private MenuTree buildTreeRecursive(MenuItem currentItem,
            Map<String, List<MenuItem>> groupedItems) {

        MenuTree currentNode = new MenuTree(currentItem.getMenuCode(),
                currentItem.getMenuText().getText(), currentItem.getTargetUrl(),
                currentItem.getIconClass(), currentItem.getSortOrder());

        List<MenuItem> childrenItems =
                groupedItems.getOrDefault(currentItem.getMenuCode(), Collections.emptyList());

        childrenItems.sort(Comparator.comparing(MenuItem::getSortOrder));

        for (MenuItem childItem : childrenItems) {
            MenuTree childNode = buildTreeRecursive(childItem, groupedItems);
            currentNode.addChild(childNode);
        }

        return currentNode;
    }

}
