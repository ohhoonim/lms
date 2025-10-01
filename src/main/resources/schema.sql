-- --------------------------------------------------------------
-- --------------------------------------------------------------
-- <component> --------------------------------------------------
-- --------------------------------------------------------------
-- --------------------------------------------------------------

-- attach_file 
create table if not exists component_attach_file (
   id char(26),
   name varchar(1000),
   path varchar(2000),
   capacity bigint,
   extension varchar(16),
   created timestamp,
   creator varchar(26),
   modified timestamp,
   modifier varchar(26),
   constraint pk_component_attach_file primary key (id)
);
create table if not exists component_attach_file_group (
   id char(26),
   entity_id char(26),
   file_id char(26),
   created timestamp,
   creator varchar(26),
   modified timestamp,
   modifier varchar(26),
   constraint pk_component_attach_file_group primary key (id),
   constraint fk_component_attach_file_group_file_id foreign key (file_id) references component_attach_file(id)
);

-- auditing
create table if not exists component_changed_event (
    id varchar(26),
    entity_type varchar(255),
    entity_id varchar(26),
    creator varchar(26),
    created timestamp,
    json_data jsonb,
    constraint pk_component_changed_event primary key (id)
);
comment on table component_changed_event is '변경이력';
comment on column component_changed_event.entity_type is '엔티티 타입';
comment on column component_changed_event.entity_id is '엔티티 id';
comment on column component_changed_event.creator is 'creator';
comment on column component_changed_event.created is 'created';
comment on column component_changed_event.json_data is 'json_data';



-- --------------------------------------------------------------
-- --------------------------------------------------------------
-- <system> -----------------------------------------------------
-- --------------------------------------------------------------
-- --------------------------------------------------------------

-- user
create table if not exists system_domain_user (
    user_id varchar(26),
    username varchar(255) not null,
    password varchar(2048) not null,
    email varchar(255) not null,
    contact varchar(255),
    enabled varchar(10),
    locked varchar(10),
    failed_attempt_count smallint,
    creator varchar(26) not null,
    created timestamp,
    modifier varchar(26),
    modified timestamp,
    constraint pk_system_domain_user primary key (user_id),
    constraint unique_system_domain_user_username unique (username)
);
create table if not exists system_domain_user_attribute (
    attribute_id varchar(26),
    user_id varchar(26) not null,
    name varchar(255) not null,
    value varchar(2048) not null,
    creator varchar(26) not null,
    created timestamp,
    modifier varchar(26),
    modified timestamp,
    constraint pk_system_comain_user_attribute primary key (attribute_id),
    constraint fk_system_comain_user_attribute foreign key (user_id) references system_domain_user (user_id)
);
create table if not exists system_domain_user_pending_change (
    pending_change_id varchar(26),
    user_id varchar(26) ,
    username varchar(255) not null,
    change_type varchar(10),
    effective_date timestamp,
    status varchar(10),
    creator varchar(26) not null,
    created timestamp,
    modifier varchar(26),
    modified timestamp,
    constraint pk_system_domain_user_pending_change primary key (pending_change_id),
    constraint fk_system_domain_user_pending_change_user_id foreign key (user_id) references system_domain_user(user_id)
);
create table if not exists system_domain_user_change_detail (
    change_detail_id varchar(26),
    pending_change_id varchar(26) not null,
    attribute_name varchar(255) not null,
    old_value varchar(2048),
    new_value varchar(2048) not null,
    creator varchar(26) not null,
    created timestamp,
    modifier varchar(26),
    modified timestamp,
    constraint pk_system_domain_user_change_detail primary key (change_detail_id),
    constraint fk_system_domain_user_change_detail_pending_change_id foreign key (pending_change_id) references system_domain_user_pending_change (pending_change_id)
);





-- --------------------------------------------------------------
-- --------------------------------------------------------------
-- <business> ---------------------------------------------------
-- --------------------------------------------------------------
-- --------------------------------------------------------------

-- lms 
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

-- para
create table if not exists para_tag (
    tag_id varchar(26),
    tag varchar(255),
    creator varchar(26),
    creaated timestamp,
    modifier varchar(26),
    modified timestamp,
    constraint pk_para_tag primary key (tag_id)
);
create table if not exists para_note (
    note_id varchar(26) not null,
    title varchar(255),
    creator varchar(26),
    creaated timestamp,
    modifier varchar(26),
    modified timestamp,
    content text,
    constraint pk_para_note primary key (note_id)
);
comment on table para_note is '노트';
comment on column para_note.note_id is 'id';
comment on column para_note.title is '제목';
comment on column para_note.content is '노트 내용';

create table if not exists para_note_tag (
    note_id varchar(26) not null,
    tag_id varchar(26) not null,
    creator varchar(26),
    creaated timestamp,
    modifier varchar(26),
    modified timestamp,
    constraint pk_para_note_tag primary key (note_id, tag_id),
    constraint fk_para_note_tag_note_id foreign key (note_id) references para_note(note_id),
    constraint fk_para_note_tag_tag_id foreign key( tag_id) references para_tag(tag_id)
);
create table if not exists para_project (
    project_id varchar(26) not null,
    title varchar(255),
    content text,
    start_date timestamp,
    end_date timestamp,
    status varchar(10), /* backlog, ready, inprogress, done */
    creator varchar(26),
    creaated timestamp,
    modifier varchar(26),
    modified timestamp,
    constraint pk_para_project primary key (project_id)
);
comment on table para_project is 'para Project';
comment on column para_project.project_id is 'id';
comment on column para_project.title is '프로젝트명';
comment on column para_project.content is '프로젝트 내용';
comment on column para_project.start_date is '시작일';
comment on column para_project.end_date is '종료일';
comment on column para_project.status is '진행항태. backlog,ready, inprogress, done';

create table if not exists para_project_note (
    project_id varchar(26) not null,
    note_id varchar(26) not null,
    creator varchar(26),
    creaated timestamp,
    modifier varchar(26),
    modified timestamp,
    constraint pk_para_project_note primary key (project_id, note_id),
    constraint fk_para_project_note_project_id foreign key (project_id) references para_project(project_id),
    constraint fk_para_project_note_note_id foreign key (note_id) references para_note(note_id)
);
create table if not exists para_shelf (
    shelf_id varchar(26) not null,
    shape varchar(10) not null, /* area, resource, archive */
    title varchar(255),
    content text,
    creator varchar(26),
    creaated timestamp,
    modifier varchar(26),
    modified timestamp,
    constraint pk_para_shelf primary key (shelf_id)
);
comment on table para_shelf is 'area resource archive';
comment on column para_shelf.shelf_id is 'id';
comment on column para_shelf.shape is '구분. area, resource, archive';
comment on column para_shelf.title is '제목';
comment on column para_shelf.content is '내용';

create table if not exists para_shelf_note (
    shelf_id varchar(26) not null,
    note_id varchar(26) not null,
    creator varchar(26),
    creaated timestamp,
    modifier varchar(26),
    modified timestamp,
    constraint pk_para_shelf_note primary key (shelf_id, note_id),
    constraint fk_para_shelf_note_shelf_id foreign key (shelf_id) references para_shelf(shelf_id),
    constraint fk_para_shelf_note_note_id foreign key (note_id) references para_note(note_id)
);
