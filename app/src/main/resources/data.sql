truncate table posts;

insert into posts (id, author, title, contents, created_date_time)
values(nextval('posts_id_seq'), 'matthew', '2024년 새해 감사', '감사합니다.', now()) ;