package dev.ohhoonim.component.attachFile;

import dev.ohhoonim.component.id.Id;
import java.time.LocalDateTime;

public record AttachFile(
    Id id,
    String name,
    String path,
    Long capacity,
    String extension,
    LocalDateTime created,
    String creator
) {

    public AttachFile(Id id, String baseName, String uploadFilePath, long size, String extension) {
        this(id, baseName, uploadFilePath, size, extension, null, "system");
    }

}
