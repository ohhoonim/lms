package dev.ohhoonim.menu.port;

import java.util.List;
import dev.ohhoonim.menu.model.MenuItem;
import dev.ohhoonim.menu.model.MenuTree;

public interface MenuTreeFactoryPort {
    
    MenuTree createTree(List<MenuItem> allMenuItems);
}
