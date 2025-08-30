package dev.ohhoonim.para.api;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.component.container.Response;
import dev.ohhoonim.component.container.Response.Success;
import dev.ohhoonim.component.container.ResponseCode;
import dev.ohhoonim.component.container.Search;
import dev.ohhoonim.component.container.Vo;
import dev.ohhoonim.para.Note;
import dev.ohhoonim.para.Para;
import dev.ohhoonim.para.Tag;
import dev.ohhoonim.para.activity.NoteActivity;
import dev.ohhoonim.para.activity.TagActivity;

@RestController
public class NoteController {

    private final NoteActivity noteService;
    private final TagActivity tagService;

    public NoteController(
            NoteActivity noteService, TagActivity tagService) {
        this.noteService = noteService;
        this.tagService = tagService;
    }

    @PostMapping("/note/list")
    public Vo<List<Note>> findNote(@RequestBody Search<NoteRequest> noteReq) {
        return noteService.findNote(noteReq.getReq().searchString(),
                noteReq.getPage());
    }

    public record NoteRequest(
            String searchString) {
    }

    @GetMapping("/note/{noteId}")
    public Note getNote(@PathVariable("noteId") String noteId) {
        return noteService.getNote(Id.valueOf(noteId))
                .orElseThrow(() -> new RuntimeException("note가 존재하지 않습니다"));
    }

    @PostMapping("/note/addNote")
    public Note addNote(@RequestBody Note newNote) {
        return noteService.addNote(newNote).orElseThrow(
                () -> new RuntimeException("저장되지 않았습니다."));
    }

    @PostMapping("/note/modifyNote")
    public Note modifyNote(@RequestBody Note modifiedNote) {
        return noteService.modifyNote(modifiedNote)
            .orElseThrow(() -> new RuntimeException("수정되지 않았습니다"));
    }

    @PostMapping("/note/{noteId}/removeNote")
    public Response removeNote(@PathVariable("noteId") String noteId) {
        noteService.removeNote(Id.valueOf(noteId));
        return new Success(ResponseCode.SUCCESS, noteId);
    }

    @GetMapping("/note/{noteId}/tags")
    public Set<Tag> tags(@PathVariable("noteId") String noteId) {
        return noteService.tags(Id.valueOf(noteId));
    }

    @PostMapping("/note/{noteId}/registTag")
    public Set<Tag> registTag(@PathVariable("noteId") String noteId,
            @RequestBody Tag tag) {
        return noteService.registTag(Id.valueOf(noteId), tag);
    }

    @PostMapping("/note/{noteId}/removeTag")
    public Set<Tag> removeTag(@PathVariable("noteId") String noteId,
            @RequestBody Tag tag) {
        return noteService.removeTag(Id.valueOf(noteId), tag);
    }

    @GetMapping("/note/{noteId}/paras")
    public Set<Para> paras(@PathVariable("noteId") String noteId) {
        return noteService.paras(Id.valueOf(noteId));
    }

    @PostMapping("/note/{noteId}/registPara")
    public Response registPara(@PathVariable("noteId") String noteId,
            @RequestBody ParaReq para) {
        noteService.registPara(Id.valueOf(noteId), 
                Para.getParaInstance(Id.valueOf(para.getNoteId()), para.getCategory()));
        return new Success(ResponseCode.SUCCESS, noteId);
    }

    @PostMapping("/note/{noteId}/removePara")
    public Response removePara(@PathVariable("noteId") String noteId,
            @RequestBody ParaReq para) {
        noteService.removePara(Id.valueOf(noteId), 
            Para.getParaInstance(Id.valueOf(para.getNoteId()), para.getCategory()));
        return new Success(ResponseCode.SUCCESS, noteId);
    }


    @PostMapping("/note/searchTags")
    public Set<Tag> findTagsLimit20PerPage(
            @RequestBody Search<NoteRequest> noteReq) {
        return tagService.findTagsLimit20PerPage(
                noteReq.getReq().searchString(),
                noteReq.getPage());
    }

}
