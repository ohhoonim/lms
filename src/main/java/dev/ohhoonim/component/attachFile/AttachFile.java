package dev.ohhoonim.component.attachFile;

import dev.ohhoonim.component.auditing.dataBy.DataBy;
import dev.ohhoonim.component.auditing.dataBy.Id;
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
