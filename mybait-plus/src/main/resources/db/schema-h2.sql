DROP TABLE IF EXISTS user;

CREATE TABLE user
(
  id      BIGINT (20) NOT NULL COMMENT '主键ID',
  name    VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
  age     INT (11) NULL DEFAULT NULL COMMENT '年龄',
  email   VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
  tags   json NULL DEFAULT NULL COMMENT '标签',
  attributes json NULL DEFAULT NULL COMMENT '属性',
  created_at DATE NULL DEFAULT NULL COMMENT '创建时间'
  version INT(10) NULL DEFAULT 1 COMMENT '乐观锁版本',
  deleted INT (11) NULL DEFAULT 1 COMMENT '逻辑删除字段',
  PRIMARY KEY (id)
);

alter table user add likes varchar(20) generated always as (attributes ->>'$.like');