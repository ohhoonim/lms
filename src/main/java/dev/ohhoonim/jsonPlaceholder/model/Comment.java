package dev.ohhoonim.jsonPlaceholder.model;

public record Comment(
    int id,
    String name,
    String email,
    String body
) {

}
