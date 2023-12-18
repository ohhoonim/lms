package dev.ohhoonim.lms.domain.post;

import java.time.LocalDateTime;

public record Post (
    Long id,
    String author,
    String title,
    String contents,
    LocalDateTime createdDateTime
){}
