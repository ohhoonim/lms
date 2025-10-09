package dev.ohhoonim.user.port;

public record HrUser(
    String username,
    String eid,
    String name,
    String email,
    String phone
) {
    // 연동 시스템에 따라 구성하기 
}
