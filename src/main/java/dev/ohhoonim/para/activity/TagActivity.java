package dev.ohhoonim.para.activity;

import java.util.Set;

import dev.ohhoonim.component.container.Page;
import dev.ohhoonim.para.Tag;

public interface TagActivity {
    public Set<Tag> findTagsLimit20PerPage(String tag, Page page);
}
