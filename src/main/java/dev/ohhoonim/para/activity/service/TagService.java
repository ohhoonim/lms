package dev.ohhoonim.para.activity.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import dev.ohhoonim.component.container.Page;
import dev.ohhoonim.para.Tag;
import dev.ohhoonim.para.activity.TagActivity;
import dev.ohhoonim.para.activity.port.TagPort;

@Service
public class TagService implements TagActivity {

    private final TagPort tagPort;

    public TagService(TagPort tagPort) {
        this.tagPort = tagPort;
    }

    @Override
    public Set<Tag> findTagsLimit20PerPage(String tag, Page page) {
        return tagPort.findTags(tag, page);
    }
    
}
