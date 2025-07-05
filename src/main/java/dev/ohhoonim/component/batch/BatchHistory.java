package dev.ohhoonim.component.batch;

public record BatchHistory(
        Long jobExecutionId,
        Long jobInstanceId,
        String jobName,
        String status,
        String exitMessage,
        String startTime,
        String jobKey) {
    public BatchHistory(
            Long jobExecutionId,
            Long jobInstanceId,
            String status,
            String exitMessage) {
        this(jobExecutionId,
                jobInstanceId,
                null, status, exitMessage, null, null);
    }

    public BatchHistory(
            Long jobExecutionId,
            Long jobInstanceId) {
        this(jobExecutionId,
                jobInstanceId,
                null, null, null, null, null);
    }

    public BatchHistory(
        String jobName,
        String jobKey
    ) {
        this(null, null, jobName, null, null, null, jobKey);
    }
}
