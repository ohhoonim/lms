package dev.ohhoonim.para.activity.port;

import java.util.List;
import java.util.Optional;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.component.container.Page;
import dev.ohhoonim.para.Note;

public interface NotePort {

    void addNote(Note newNote, Id newNoteId);

    Optional<Note> getNote(Id noteId);

    void modifyNote(Note modifiedNote);

    void removeNote(Id noteId);

    List<Note> findNote(String searchString, Page page);
    
}
