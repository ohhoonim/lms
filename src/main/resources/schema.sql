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

-- component attach_file <start>
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
-- component attach_file <end>

-- spring batch schema <start>
/*
DROP TABLE  IF EXISTS BATCH_STEP_EXECUTION_CONTEXT;
DROP TABLE  IF EXISTS BATCH_JOB_EXECUTION_CONTEXT;
DROP TABLE  IF EXISTS BATCH_STEP_EXECUTION;
DROP TABLE  IF EXISTS BATCH_JOB_EXECUTION_PARAMS;
DROP TABLE  IF EXISTS BATCH_JOB_EXECUTION;
DROP TABLE  IF EXISTS BATCH_JOB_INSTANCE;

DROP SEQUENCE  IF EXISTS BATCH_STEP_EXECUTION_SEQ;
DROP SEQUENCE  IF EXISTS BATCH_JOB_EXECUTION_SEQ;
DROP SEQUENCE  IF EXISTS BATCH_JOB_SEQ;
*/

CREATE SEQUENCE BATCH_STEP_EXECUTION_SEQ MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE BATCH_JOB_EXECUTION_SEQ MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE BATCH_JOB_SEQ MAXVALUE 9223372036854775807 NO CYCLE;

CREATE TABLE BATCH_JOB_INSTANCE  (
	JOB_INSTANCE_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT ,
	JOB_NAME VARCHAR(100) NOT NULL,
	JOB_KEY VARCHAR(32) NOT NULL,
	constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ;

CREATE TABLE BATCH_JOB_EXECUTION  (
	JOB_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT  ,
	JOB_INSTANCE_ID BIGINT NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	START_TIME TIMESTAMP DEFAULT NULL ,
	END_TIME TIMESTAMP DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED TIMESTAMP,
	constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
	references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ;

CREATE TABLE BATCH_JOB_EXECUTION_PARAMS  (
	JOB_EXECUTION_ID BIGINT NOT NULL ,
	PARAMETER_NAME VARCHAR(100) NOT NULL ,
	PARAMETER_TYPE VARCHAR(100) NOT NULL ,
	PARAMETER_VALUE VARCHAR(2500) ,
	IDENTIFYING CHAR(1) NOT NULL ,
	constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE BATCH_STEP_EXECUTION  (
	STEP_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT NOT NULL,
	STEP_NAME VARCHAR(100) NOT NULL,
	JOB_EXECUTION_ID BIGINT NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	START_TIME TIMESTAMP DEFAULT NULL ,
	END_TIME TIMESTAMP DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	COMMIT_COUNT BIGINT ,
	READ_COUNT BIGINT ,
	FILTER_COUNT BIGINT ,
	WRITE_COUNT BIGINT ,
	READ_SKIP_COUNT BIGINT ,
	WRITE_SKIP_COUNT BIGINT ,
	PROCESS_SKIP_COUNT BIGINT ,
	ROLLBACK_COUNT BIGINT ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED TIMESTAMP,
	constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT  (
	STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT TEXT ,
	constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
	references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ;

CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT  (
	JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT TEXT ,
	constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

-- spring batch schema <end>
