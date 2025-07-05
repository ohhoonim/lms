package dev.ohhoonim.component.batch;

public record BatchHistorySearch(
    String jobDivisionCode,
    String batchStatusCode,
    String exitMessage,
    String periodRange
) {

}
