create table AuditLogDetail (id int8 not null, message varchar(256), oldValue varchar(4000), newValue varchar(4000), attribute varchar(100) not null, record_id int8 not null, primary key (id));
create table AuditLogRecord (id int8 not null, username varchar(100) not null, entityName varchar(254) not null, entityId int8 not null, createdDate timestamp not null, transactionId int8 not null, type varchar(255) not null, primary key (id));
alter table AuditLogDetail add constraint AUDIT_DETAIL_RECORD_FK foreign key (record_id) references AuditLogRecord;
create sequence AUDIT_ID_SEQ;