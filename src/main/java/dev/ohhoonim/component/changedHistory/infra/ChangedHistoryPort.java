package dev.ohhoonim.component.changedHistory.infra;

import java.util.List;

import dev.ohhoonim.component.changedHistory.ChangedHistory.History;
import dev.ohhoonim.component.changedHistory.ChangedHistory.SearchCondition;


public interface ChangedHistoryPort {

    List<History> histories(SearchCondition condition);

    void recording(History changedHistory);

}
