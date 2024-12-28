package dev.ohhoonim.component.changedHistory;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ohhoonim.component.changedHistory.ChangedHistory.History;
import dev.ohhoonim.component.changedHistory.ChangedHistory.SearchCondition;
import dev.ohhoonim.component.changedHistory.infra.ChangedHistoryPort;


@Service
public class ChangedHistoryService implements ChangedHistory.History.Service {

    private final ChangedHistoryPort port;

    public ChangedHistoryService (ChangedHistoryPort port) {
        this.port = port;
    }

    @Override
    public List<History> histories(SearchCondition condition) {
        return port.histories(condition);
    }

    @Override
    @Transactional
    public void addHistory(History changedHistory) {
        port.recording(changedHistory);
    }


}
