package dev.ohhoonim.component.attachFile;

import dev.ohhoonim.component.id.Id;

public record AttachFile(
    Id id,
    String name,
    String path,
    Long capacity,
    String extension
) {

}
