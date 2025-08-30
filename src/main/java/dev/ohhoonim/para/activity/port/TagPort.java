package dev.ohhoonim.para.activity.port;

import java.util.Set;

import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.component.container.Page;
import dev.ohhoonim.para.Tag;

public interface TagPort {

    Set<Tag> findTags(String tag, Page page);

    void addTagInNote(Id noteId, Tag tag);

    Set<Tag> tagsInNote(Id noteId);

    void removeTagInNote(Id noteId, Tag tag);
}
