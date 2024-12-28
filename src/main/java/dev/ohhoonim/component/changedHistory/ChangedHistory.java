package dev.ohhoonim.component.changedHistory;

import java.util.List;
import java.util.UUID;

import dev.ohhoonim.component.dataBy.DataBy;

public sealed interface ChangedHistory {

    public enum Classify {
        COURSE
    }

    public record History(
            UUID recordId,
            Classify classify,
            UUID referenceId,
            String oldContents,
            String newContents,
            DataBy dataBy) implements ChangedHistory {

        public interface Service {

            public List<History> histories(SearchCondition condition);

            public void addHistory(History changedHistory);
        }
    }

    public record SearchCondition(
            Classify classify,
            UUID referenceId) implements ChangedHistory {

        public SearchCondition {
            if (classify == null || referenceId == null) {
                throw new RequiredPropertyException("classify와 reference는 필수 값입니다.");
            }
        }
    }

}
