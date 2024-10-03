package dev.ohhoonim.lms.courses.model;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ohhoonim.lms.courses.model.port.SyllabusCommandPort;
import dev.ohhoonim.lms.courses.model.port.SyllabusQueryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SyllabusService {
   private final SyllabusCommandPort commandPort;
   private final SyllabusQueryPort queryPort;

   
}
