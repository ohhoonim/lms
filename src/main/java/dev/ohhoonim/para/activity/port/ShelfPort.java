package dev.ohhoonim.para.activity.port;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.component.container.Page;
import dev.ohhoonim.para.Note;
import dev.ohhoonim.para.Para;
import dev.ohhoonim.para.Para.Shelf;

public interface ShelfPort {

    List<Shelf> findShelves(String searchString, Page page);

    void addShelf(Shelf s, Id newParaId);

    Optional<Para> getShelf(Id newParaId);

    void registNote(Id noteId, Shelf s);

    List<Note> notes(Shelf para);

    void removeNote(Id noteId, Shelf s);

    void removeShelf(Shelf s);

    void modifyShelf(Shelf s);

    void moveToPara(Para origin, Class<? extends Shelf> targetPara);

    Set<Para> findShelfInNote(Id noteId);

}
