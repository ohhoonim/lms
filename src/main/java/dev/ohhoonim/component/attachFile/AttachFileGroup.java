package dev.ohhoonim.component.attachFile;

import java.time.LocalDateTime;

import dev.ohhoonim.component.id.Id;

public record AttachFileGroup(
    Id id,
    Id entityId,
    Id fileId,
    LocalDateTime created,
    String creator
) {
    
}
