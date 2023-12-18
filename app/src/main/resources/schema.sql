CREATE TABLE IF NOT EXISTS posts (
	id bigserial NOT NULL,
	author varchar(255) NULL,
	title varchar(255) NULL,
	contents varchar(255) NULL,
	created_date_time timestamp(6) NULL,
	CONSTRAINT posts_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS posts_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
