package dev.ohhoonim.component.dataBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public sealed interface DataBy {
    public UUID createdBy();

    public LocalDateTime createdDate();

    public UUID lastModifiedBy();

    public LocalDateTime lastModifiedDate();

    public record Create(
            UUID createdBy,
            LocalDateTime createdDate,
            UUID lastModifiedBy,
            LocalDateTime lastModifiedDate) implements DataBy {
        public Create(UUID createdBy, LocalDateTime now) {
            this(createdBy, now, createdBy, now);
        }
    }

    public record Modify(UUID createdBy,
            LocalDateTime createdDate,
            UUID lastModifiedBy,
            LocalDateTime lastModifiedDate) implements DataBy {
        public Modify(UUID lastModifiedBy) {
            this(null, null, lastModifiedBy, LocalDateTime.now());
        }
    }
}
