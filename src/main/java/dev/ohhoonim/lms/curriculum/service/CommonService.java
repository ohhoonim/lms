package dev.ohhoonim.lms.curriculum.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.ohhoonim.component.user.User.Assistant;
import dev.ohhoonim.component.user.User.ClassManager;
import dev.ohhoonim.component.user.User.Professor;
import dev.ohhoonim.lms.curriculum.Curriculum;


@Service
public final class CommonService implements Curriculum.CommonService {

    @Override
    public ClassManager manager(UUID userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'manager'");
    }

    @Override
    public Professor professor(UUID userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'professor'");
    }

    @Override
    public Assistant assistant(UUID uerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assistant'");
    }

}
