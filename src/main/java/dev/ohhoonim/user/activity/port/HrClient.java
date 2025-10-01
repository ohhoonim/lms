package dev.ohhoonim.user.activity.port;

import java.util.List;

import org.springframework.web.service.annotation.GetExchange;

public interface HrClient {

    @GetExchange("/fetch")
    List<HrUser> fetchHrUsers();
}
