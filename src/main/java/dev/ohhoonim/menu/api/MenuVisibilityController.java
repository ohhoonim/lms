package dev.ohhoonim.menu.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.ohhoonim.menu.model.MenuTree;
import dev.ohhoonim.menu.model.MenuVisibilityService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuVisibilityController {

    private final MenuVisibilityService menuVisibilityService;

    @GetMapping("")
    public MenuTree createTree() {
        return menuVisibilityService.createTree();
    }
    
}
