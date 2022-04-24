create table if not exists qrtz_calendars
(
    SCHED_NAME varchar(120) not null,
    CALENDAR_NAME varchar(200) not null,
    CALENDAR blob not null,
    primary key (SCHED_NAME, CALENDAR_NAME)
);

create table if not exists qrtz_fired_triggers
(
    SCHED_NAME varchar(120) not null,
    ENTRY_ID varchar(95) not null,
    TRIGGER_NAME varchar(200) not null,
    TRIGGER_GROUP varchar(200) not null,
    INSTANCE_NAME varchar(200) not null,
    FIRED_TIME bigint not null,
    SCHED_TIME bigint not null,
    PRIORITY int not null,
    STATE varchar(16) not null,
    JOB_NAME varchar(200) null,
    JOB_GROUP varchar(200) null,
    IS_NONCONCURRENT varchar(1) null,
    REQUESTS_RECOVERY varchar(1) null,
    primary key (SCHED_NAME, ENTRY_ID)
);

create table if not exists qrtz_job_details
(
    SCHED_NAME varchar(120) not null,
    JOB_NAME varchar(200) not null,
    JOB_GROUP varchar(200) not null,
    DESCRIPTION varchar(250) null,
    JOB_CLASS_NAME varchar(250) not null,
    IS_DURABLE varchar(1) not null,
    IS_NONCONCURRENT varchar(1) not null,
    IS_UPDATE_DATA varchar(1) not null,
    REQUESTS_RECOVERY varchar(1) not null,
    JOB_DATA blob null,
    primary key (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

create table if not exists qrtz_locks
(
    SCHED_NAME varchar(120) not null,
    LOCK_NAME varchar(40) not null,
    primary key (SCHED_NAME, LOCK_NAME)
);

create table if not exists qrtz_paused_trigger_grps
(
    SCHED_NAME varchar(120) not null,
    TRIGGER_GROUP varchar(200) not null,
    primary key (SCHED_NAME, TRIGGER_GROUP)
);

create table if not exists qrtz_scheduler_state
(
    SCHED_NAME varchar(120) not null,
    INSTANCE_NAME varchar(200) not null,
    LAST_CHECKIN_TIME bigint not null,
    CHECKIN_INTERVAL bigint not null,
    primary key (SCHED_NAME, INSTANCE_NAME)
);

create table if not exists qrtz_triggers
(
    SCHED_NAME varchar(120) not null,
    TRIGGER_NAME varchar(200) not null,
    TRIGGER_GROUP varchar(200) not null,
    JOB_NAME varchar(200) not null,
    JOB_GROUP varchar(200) not null,
    DESCRIPTION varchar(250) null,
    NEXT_FIRE_TIME bigint null,
    PREV_FIRE_TIME bigint null,
    PRIORITY int null,
    TRIGGER_STATE varchar(16) not null,
    TRIGGER_TYPE varchar(8) not null,
    START_TIME bigint not null,
    END_TIME bigint null,
    CALENDAR_NAME varchar(200) null,
    MISFIRE_INSTR smallint null,
    JOB_DATA blob null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint qrtz_triggers_ibfk_1
        foreign key (SCHED_NAME, JOB_NAME, JOB_GROUP) references qrtz_job_details (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

create table if not exists qrtz_blob_triggers
(
    SCHED_NAME varchar(120) not null,
    TRIGGER_NAME varchar(200) not null,
    TRIGGER_GROUP varchar(200) not null,
    BLOB_DATA blob null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint qrtz_blob_triggers_ibfk_1
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

create table if not exists qrtz_cron_triggers
(
    SCHED_NAME varchar(120) not null,
    TRIGGER_NAME varchar(200) not null,
    TRIGGER_GROUP varchar(200) not null,
    CRON_EXPRESSION varchar(200) not null,
    TIME_ZONE_ID varchar(80) null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint qrtz_cron_triggers_ibfk_1
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

create table if not exists qrtz_simple_triggers
(
    SCHED_NAME varchar(120) not null,
    TRIGGER_NAME varchar(200) not null,
    TRIGGER_GROUP varchar(200) not null,
    REPEAT_COUNT bigint not null,
    REPEAT_INTERVAL bigint not null,
    TIMES_TRIGGERED bigint not null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint qrtz_simple_triggers_ibfk_1
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

create table if not exists qrtz_simprop_triggers
(
    SCHED_NAME varchar(120) not null,
    TRIGGER_NAME varchar(200) not null,
    TRIGGER_GROUP varchar(200) not null,
    STR_PROP_1 varchar(512) null,
    STR_PROP_2 varchar(512) null,
    STR_PROP_3 varchar(512) null,
    INT_PROP_1 int null,
    INT_PROP_2 int null,
    LONG_PROP_1 bigint null,
    LONG_PROP_2 bigint null,
    DEC_PROP_1 decimal(13,4) null,
    DEC_PROP_2 decimal(13,4) null,
    BOOL_PROP_1 varchar(1) null,
    BOOL_PROP_2 varchar(1) null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint qrtz_simprop_triggers_ibfk_1
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

create index SCHED_NAME
    on qrtz_triggers (SCHED_NAME, JOB_NAME, JOB_GROUP);

create table if not exists user_email_info
(
    uuid varchar(30) not null,
    email varchar(50) default '1476378219@qq.com' not null,
    constraint user_email_info_email_uindex
        unique (email),
    constraint user_email_info_uuid_uindex
        unique (uuid)
);

alter table user_email_info
    add primary key (uuid);

create table if not exists user_youth_data
(
    userid varchar(25) not null,
    nid varchar(25) not null,
    uuid varchar(30) not null,
    status tinyint(1) default 0 not null,
    trigger_name varchar(100) not null,
    trigger_group varchar(100) not null,
    job_name varchar(100) not null,
    job_group varchar(100) not null,
    cron varchar(40) not null,
    create_time datetime not null comment '创建时间',
    update_time datetime null comment '更新时间',
    last_finish_time datetime null comment '上次登录时间',
    finish_history blob null comment '完成历史',
    send_history blob null comment '邮件发送历史',
    email_id varchar(30) null,
    primary key (userid, trigger_name, trigger_group, job_name, job_group),
    constraint user_youth_data_email_id_uindex
        unique (email_id),
    constraint user_youth_data_job_name_uindex
        unique (job_name),
    constraint user_youth_data_trigger_name_uindex
        unique (trigger_name),
    constraint user_youth_data_userid_uindex
        unique (userid)
);

create table if not exists youth_course
(
    course varchar(6) default 'course' not null
        primary key,
    id varchar(10) null
);




