-- component change-history
create table if not exists component_changed_history (
   record_id varchar(36),
   classify varchar(10),
   reference_id varchar(36),
   old_contents text,
   new_contents text,
   created_by char(36),
   created_date timestamp,
   last_modified_by char(36),
   last_modified_date timestamp,
   constraint pk_component_change_history primary key (record_id)
);
-- component : member
create table if not exists component_member (
   user_id varchar(36),
   name varchar(255),
   password varchar(255),
   email varchar(255),
   phone varchar(255),
   created_by char(36),
   created_date timestamp,
   last_modified_by char(36),
   last_modified_date timestamp,
   constraint pk_component_member primary key (user_id)
);
----------------------------------------------------------------------------
-- domain : curriculum
create table if not exists lms_course(
   course_id char(36),
   name varchar(255),
   round int,
   target varchar(255),
   content varchar(255),
   class_manager_id char(36),
   start_date timestamp,
   end_date timestamp,
   closed Boolean,
   created_by char(36),
   created_date timestamp,
   last_modified_by char(36),
   last_modified_date timestamp,
   constraint pk_lms_course primary key (course_id)
);
create table if not exists lms_subject(
   subject_id char(36),
   title varchar(255),
   category varchar(255),
   created_by char(36),
   created_date timestamp,
   last_modified_by char(36),
   last_modified_date timestamp,
   constraint pk_lms_subject primary key (subject_id)
);
create table if not exists lms_syllabus(
   syllabus_id char(36),
   title varchar(255),
   time_of_hour int,
   time_unit varchar(255),
   professor_id char(36),
   created_by char(36),
   created_date timestamp,
   last_modified_by char(36),
   last_modified_date timestamp,
   constraint pk_lms_syllabus primary key (syllabus_id)
);

create sequence if not exists seq_lms_lecture start with 1;

create table if not exists lms_lecture(
   lecture_id bigint default nextval('seq_lms_lecture'),
   syllabus_id char(36),
   round int,
   title varchar(255),
   lecture_method varchar(255),
   time_of_hour int,
   content varchar(255),
   is_completed Boolean,
   professor_id char(36),
   assistant_id char(36),
   created_by char(36),
   created_date timestamp,
   last_modified_by char(36),
   last_modified_date timestamp,
   constraint pk_lms_lecture primary key (lecture_id),
   constraint fk_lms_lecture_syllabus_id foreign key (syllabus_id) references lms_syllabus(syllabus_id)
);
create table if not exists lms_subject_syllabus(
   subject_syllabus_id char(36),
   subject_id char(36),
   syllabus_id char(36),
   created_by char(36),
   created_date timestamp,
   last_modified_by char(36),
   last_modified_date timestamp,
   constraint pk_lms_subject_syllabus primary key (subject_syllabus_id),
   constraint fk_lms_subject_syllabus_subject foreign key (subject_id) references lms_subject(subject_id),
   constraint fk_lms_subject_syllabus_syllabus foreign key (syllabus_id) references lms_syllabus(syllabus_id)
);
create table if not exists lms_course_subject(
   course_id char(36),
   subject_syllabus_id char(36),
   created_by char(36),
   created_date timestamp,
   last_modified_by char(36),
   last_modified_date timestamp,
   constraint pk_lms_course_subject primary key (course_id, subject_syllabus_id),
   constraint fk_lms_course_subject_subject_syllabus foreign key (subject_syllabus_id) references lms_subject_syllabus(subject_syllabus_id)
);
----------------------------------------------------------------------------