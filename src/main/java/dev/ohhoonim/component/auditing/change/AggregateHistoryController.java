package dev.ohhoonim.component.auditing.change;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ohhoonim.component.auditing.dataBy.Id;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/aggregateHistory")
@RequiredArgsConstructor
public class AggregateHistoryController {
    
    private final ChangedEventRepository changedEventRepository;

    @GetMapping("/{entityType}/{entityId}")
    public List<LookupEvent> lookup(@PathParam("entityType") String entityType, 
            @PathParam("entityId") String entityId) throws ClassNotFoundException {
                
        return changedEventRepository.lookupEvent(
                new LookupEvent(Id.valueOf(entityId), 
                Class.forName(entityType)));
    }
}
