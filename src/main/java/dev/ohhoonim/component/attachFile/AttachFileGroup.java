package dev.ohhoonim.component.attachFile;

import dev.ohhoonim.component.id.Id;

public record AttachFileGroup(
    Id id,
    Id entityId,
    Id fileId
) {
    
}
