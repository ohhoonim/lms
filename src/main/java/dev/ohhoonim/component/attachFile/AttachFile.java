package dev.ohhoonim.component.attachFile;

import java.time.LocalDateTime;

import dev.ohhoonim.component.dataBy.DataBy;
import dev.ohhoonim.component.id.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachFile {
    private Id id;
    private String name;
    private String path;
    private Long capacity;
    private String extension;
    private DataBy creator;
    private DataBy modifier;
}
