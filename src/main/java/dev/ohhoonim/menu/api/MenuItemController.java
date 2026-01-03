package dev.ohhoonim.menu.api;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.menu.model.LanguageCode;
import dev.ohhoonim.menu.model.MenuAttributeTag;
import dev.ohhoonim.menu.model.MenuItem;
import dev.ohhoonim.menu.model.MenuItemService;
import dev.ohhoonim.menu.model.MenuText;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/menu-item")
@RequiredArgsConstructor
public class MenuItemController {

	private final MenuItemService menuItemService;

	@PostMapping("/addAttributeTag")
	public void addAttributeTag(@RequestBody MenuCodeAttribute attributeTag) {
		menuItemService.addAttributeTag(attributeTag.menuCode(), attributeTag.permission());
	}

	public record MenuCodeAttribute(String menuCode, MenuAttributeTag permission) {
	}

	@PostMapping("/modifyAttributeTag")
	public void modifyAttrbuteTag(@RequestBody MenuCodeAttribute attributeTag) {
		menuItemService.modifyAttrbuteTag(attributeTag.menuCode(), attributeTag.permission());
	}

	@PostMapping("/removeAttributeTag")
	public void removeAttributeTag(@RequestBody MenuCodeAttribute attributeTag) {
		menuItemService.removeAttributeTag(attributeTag.menuCode(), attributeTag.permission());
	}

	@PostMapping("/attributeTags/{menuCode}")
	public List<MenuAttributeTag> attributeTags(@PathVariable String menuCode) {
		return menuItemService.attributeTags(menuCode);
	}

	@PostMapping("/texts/{menuItemId}")
	public List<MenuText> menuTextsByMenuItem(@PathVariable String menuItemId) {
		return menuItemService.menuTextsByMenuItem(Id.valueOf(menuItemId));
	}

	@GetMapping("/texts/{menuItemId}/{languageCode}")
	public MenuText getMenuText(@PathVariable String menuItemId,
			@PathVariable LanguageCode languageCode) {
		return menuItemService.getMenuText(Id.valueOf(menuItemId), languageCode);
	}

	@PostMapping("/addMenuText")
	public void addMenuText(@RequestBody MenuText newMenuText) {
		menuItemService.addMenuText(newMenuText);
	}

	@PostMapping("/modifyMenuText")
	public void modifyMenuText(@RequestBody MenuText menuText) {
		menuItemService.modifyMenuText(menuText);
	}

	@PostMapping("/removeMenuText/{menuTextId}")
	public void removeMenuText(@PathVariable String menuTextId) {
		menuItemService.removeMenuText(Id.valueOf(menuTextId));
	}

	@PostMapping("/addMenu")
	public void addMenu(@RequestBody MenuItem newMenuItem) {
		menuItemService.addMenu(newMenuItem);
	}

	@PostMapping("/modifyMenu")
	public void modifyMenu(@RequestBody MenuItem menuItem) {
		menuItemService.modifyMenu(menuItem);
	}

	@PostMapping("/removeMenu/{menuItemId}")
	public void removeMenu(@PathVariable String menuItemId) {
		menuItemService.removeMenu(Id.valueOf(menuItemId));
	}

	@GetMapping("/menuItem/{menuItemId}")
	public MenuItem menuById(@PathVariable String menuItemId) {
		return menuItemService.menuById(Id.valueOf(menuItemId));
	}

	@PostMapping("/modifyMenuOrder")
	public void modifyMenuOrder(@RequestBody List<MenuCodeOrderMap> menuCodesOrderMap) {
		Map<String, Integer> menuOrderMap = menuCodesOrderMap.stream()
				.collect(Collectors.toMap(MenuCodeOrderMap::menuCode, MenuCodeOrderMap::order));
		menuItemService.modifyMenuOrder(menuOrderMap);
	}

	public record MenuCodeOrderMap(String menuCode, Integer order) {
	}

	@PostMapping("/modifyParent")
	public void modifyParentMenuItem(@RequestBody List<ParentMenuCode> parentMenuCodeMap) {
		for (var item : parentMenuCodeMap) {
			menuItemService.modifyParentMenuItem(item.currentMenuCode(), item.parentMenuCode());
		}
	}

	public record ParentMenuCode(String currentMenuCode, String parentMenuCode) {
	}

}
