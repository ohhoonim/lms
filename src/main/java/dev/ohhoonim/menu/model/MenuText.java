package dev.ohhoonim.menu.model;

import dev.ohhoonim.component.auditing.model.Entity;
import dev.ohhoonim.component.auditing.model.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuText implements Entity {
    private Id menuTextId;
    private Id menuItemId;
    private String text;
    private LanguageCode languageCode;

    @Builder
    public MenuText(Id menuTextId, Id menuItemId, String text, LanguageCode languageCode) {
        this.menuTextId = menuTextId;
        this.menuItemId = menuItemId;
        this.text = text;
        this.languageCode = languageCode;
    }

    @Override
    public Id getId() {
        return this.menuTextId;
    }
}
