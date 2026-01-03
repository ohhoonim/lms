package dev.ohhoonim.menu.model;

import org.jspecify.annotations.Nullable;
import dev.ohhoonim.component.auditing.model.Id;

public record MenuAttributeTag(@Nullable Id menuAttributeTagId, Id menuItemmId, String attribute,
                String tag) {

        public String getAttributeTag() {
                return this.attribute() + "_" + this.tag();
        }
}
