package dev.ohhoonim.lms.curriculum;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import dev.ohhoonim.component.user.User;

public sealed interface Curriculum {

    public record Course(
            UUID courseId,
            String name,
            Integer round,
            String target,
            String content,
            UUID classManagerId,
            LocalDate startDate,
            LocalDate endDate,
            Boolean closed) implements Curriculum {

        public interface Service {

            public Boolean isClosed(Course course);

            public Course newRound(Course latestCourse);

            public Course save(Course course);

            public List<Course> listCourse(CourseCondition courseCondition);
        }
    }

    public record CourseSubject(
            UUID courseId,
            UUID subjectSyllabusId) implements Curriculum {

        public interface Service {

            public List<Subject> addSubject(CourseSubject courseSubject);

            public List<Subject> removeSubject(CourseSubject courseSubject);

            public List<Subject> subjects(UUID courseId);

            public List<Course> courses(UUID subjectId);
        }
    }

    public record Subject(
            UUID subjectId,
            String title,
            String category) implements Curriculum {

        public interface Service {

            public Subject save(Subject subject);

            public List<Subject> listSubject(SubjectCondition subjectCondition);
        }

    }

    public record SubjectSyllabus(
            UUID subjectSyllabusId,
            UUID subjectId,
            UUID syllabusId) implements Curriculum {

        public interface Service {

            public List<Syllabus> syllabues(UUID subjectId);

            public List<Syllabus> addSyllabus(UUID subjectId, UUID syllabusId);

            public List<Syllabus> removeSyllabus(UUID subjectSyllabusId);
        }
    }

    public record Syllabus(
            UUID syllabusId,
            String title,
            Integer timeOfHour,
            String timeUnit,
            UUID professorId) implements Curriculum {

        public interface Service {

            public Syllabus save(Syllabus syllabus);

            public Set<String> lecturesMethods();

            public List<Lecture> addLecture(Lecture lecture);

            public List<Lecture> removeLecture(Long lecture);

            public List<Lecture> lectures(UUID syllabusid);
        }
    }

    public record Lecture(
            Long lectureId,
            UUID syllabusId,
            Integer round,
            String title,
            String lectureMethod,
            Integer timeOfHour,
            String content,
            Boolean isCompleted,
            UUID professorId,
            UUID assistantId) implements Curriculum {

        public interface Service {

            public Lecture modify(Lecture lecture);

        }
    }

    public interface CommonService {

        public User.ClassManager manager(UUID userId);

        public User.Professor professor(UUID userId);

        public User.Assistant assistant(UUID uerId);
    }

    public record CourseCondition(
            UUID courseId,
            String name,
            Integer round,
            String target,
            UUID classManagerId,
            LocalDate startDate,
            LocalDate endDate,
            Boolean closed) {
    }

    public record SubjectCondition(
            UUID subjectId,
            String title,
            String category) {
    }
}
