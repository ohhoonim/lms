package dev.ohhoonim.menu.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import dev.ohhoonim.menu.port.MenuTreeFactoryPort;
import dev.ohhoonim.menu.port.MenuTreePort;

@ExtendWith(MockitoExtension.class)
class MenuVisibilityServiceTest {

    @InjectMocks
    MenuVisibilityService menuVisiblilityService;

    @Mock
    MenuTreePort menuTreePort;

    @Mock
    MenuTreeFactoryPort menuTreeFactoryPort;

    @Test
    void createTree() {
        var rootMenuItem = MenuItem.builder().menuCode("ROOT").build();
        var menuItems = List.of(rootMenuItem);
        when(menuTreePort.allMenus()).thenReturn(menuItems);
        when(menuTreeFactoryPort.createTree(eq(menuItems)))
                .thenReturn(MenuTree.builder().menuCode("ROOT").build());

        MenuTree resultTree = menuVisiblilityService.createTree();
        assertThat(resultTree.getMenuCode()).isEqualTo("ROOT");
    }

    @Test
    void resultCaching() {
        var rootMenuTree = MenuTree.builder().menuCode("ROOT").build();
        menuVisiblilityService.resultCaching(rootMenuTree);
        verify(menuTreePort, times(1)).resultCaching(eq(rootMenuTree));
    }

}
