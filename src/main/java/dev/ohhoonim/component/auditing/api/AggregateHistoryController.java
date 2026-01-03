package dev.ohhoonim.component.auditing.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ohhoonim.component.auditing.LookupEvent;
import dev.ohhoonim.component.auditing.infra.ChangedEventRepository;
import dev.ohhoonim.component.auditing.model.Id;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/aggregateHistory")
@RequiredArgsConstructor
public class AggregateHistoryController {

    private final ChangedEventRepository changedEventRepository;

    @GetMapping("/{entityType}/{entityId}")
    public List<LookupEvent<?>> lookup(@PathVariable("entityType") String entityType,
            @PathVariable("entityId") String entityId) throws ClassNotFoundException {

        return changedEventRepository.lookupEvent(
                new LookupEvent(Id.valueOf(entityId),
                        Class.forName(entityType)));
    }
}
