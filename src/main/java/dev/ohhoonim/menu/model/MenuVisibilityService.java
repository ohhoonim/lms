package dev.ohhoonim.menu.model;

import java.util.List;
import org.springframework.stereotype.Service;
import dev.ohhoonim.menu.application.MenuPolicyResultCachingActivity;
import dev.ohhoonim.menu.application.MenuVisiblilityActivity;
import dev.ohhoonim.menu.port.MenuTreeFactoryPort;
import dev.ohhoonim.menu.port.MenuTreePort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public final class MenuVisibilityService extends MenuTree
        implements MenuVisiblilityActivity, MenuPolicyResultCachingActivity {

    private final MenuTreePort menuTreePort;
    private final MenuTreeFactoryPort menuTreeFactoryPort;

    @Override
    public MenuTree createTree() {
        List<MenuItem> allMenuItems = menuTreePort.allMenus();
        return menuTreeFactoryPort.createTree(allMenuItems);
    }

    @Override
    public void resultCaching(MenuTree menuTree) {
        menuTreePort.resultCaching(menuTree);
    }


}
