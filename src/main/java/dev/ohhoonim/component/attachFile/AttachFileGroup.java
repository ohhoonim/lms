package dev.ohhoonim.component.attachFile;

import dev.ohhoonim.component.auditing.dataBy.DataBy;
import dev.ohhoonim.component.auditing.dataBy.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachFileGroup {

    private Id id;
    private Id entityId;
    private AttachFile fileId;
    private DataBy creator;
    private DataBy modifier;
}
