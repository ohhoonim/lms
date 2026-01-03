package dev.ohhoonim.component.container;

import dev.ohhoonim.component.auditing.model.Created;
import dev.ohhoonim.component.auditing.model.Modified;

// Valued Object
// value object 아님
public record Vo<T>(
        T record,
        Page page,
        Created creator,
        Modified modifier) implements Container {

    public Vo(T record) {
        this(record, null, null, null);
    }
    public Vo(T record, Page page) {
        this(record, page, null, null);
    }
}
