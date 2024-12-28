package dev.ohhoonim.component.dataBy;

import java.time.LocalDateTime;
import java.util.UUID;

public record DataBy(
    UUID createdBy,
    LocalDateTime createdDate,
    UUID lastModifiedBy,
    LocalDateTime lastModifiedDate
) {
    
}
