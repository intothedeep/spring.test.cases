drop sequence file_test_seq;
create sequence file_test_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOCACHE;

drop table file_test;
create table file_test (
    fileSeq number not null primary key,
    originalfilename varchar2(255),
    storedfilename varchar2(255),
    filesize number,
    uploaddate date default sysdate,
    deleted number(1) default 0,
    email varchar2(50),
    bcode number,
    storedfolder varchar2(255),
    thumbStoredFileName varchar2(255),
	thumbStoredPath varchar2(255),
	originalFileSeq number
);

drop table member;
create table member (
    id varchar2(50),
    pass varchar2(50),
    name varchar2(50),
    email varchar2(50) not null primary key,
    joinType number default 1,
    memberType number default 1
);
insert into member
values('admin', '123', 'admin', 'admin@gmail.com', 1, 1);