BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_codegen_column
-- ----------------------------
DROP TABLE IF EXISTS `infra_codegen_column`;
CREATE TABLE `infra_codegen_column`
(
    `id`                       bigint(20)           AUTO_INCREMENT                    NOT NULL COMMENT '编号',
    `table_id`                 bigint(20)                              NOT NULL COMMENT '表编号',
    `column_name`              varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段名',
    `data_type`                varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段类型',
    `column_comment`           varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段描述',
    `nullable`                 bit(1)                                  NOT NULL COMMENT '是否允许为空 1是 0否',
    `primary_key`              bit(1)                                  NOT NULL COMMENT '是否主键 1是 0否',
    `field_repeat`             bit(1)                                  NOT NULL DEFAULT b'0' COMMENT '是否允许重复 1是 0否',
    `auto_increment`           char(1) COLLATE utf8mb4_unicode_ci      NOT NULL COMMENT '是否自增',
    `ordinal_position`         int(11)                                 NOT NULL COMMENT '排序',
    `java_type`                varchar(32) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT 'Java 属性类型',
    `java_field`               varchar(64) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT 'Java 属性名',
    `dict_type`                varchar(200) COLLATE utf8mb4_unicode_ci          DEFAULT '' COMMENT '字典类型',
    `example`                  varchar(64) COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '数据示例',
    `create_operation`         bit(1)                                  NOT NULL COMMENT '是否为 Create 创建操作的字段',
    `update_operation`         bit(1)                                  NOT NULL COMMENT '是否为 Update 更新操作的字段',
    `list_operation`           bit(1)                                  NOT NULL COMMENT '是否为 List 查询操作的字段',
    `list_operation_condition` varchar(32) COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '=' COMMENT 'List 查询操作的条件类型',
    `list_operation_result`    bit(1)                                  NOT NULL COMMENT '是否为 List 查询操作的返回字段',
    `html_type`                varchar(32) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '显示类型',
    `create_by`                varchar(64) COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time`              datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`                varchar(64) COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time`              datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `del_flag`                 bit(1)                                  NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='代码生成表字段定义';

-- ----------------------------
-- Records of infra_codegen_column
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for infra_codegen_table
-- ----------------------------
DROP TABLE IF EXISTS `infra_codegen_table`;
CREATE TABLE `infra_codegen_table`
(
    `id`                    bigint(20)            AUTO_INCREMENT                   NOT NULL COMMENT '编号',
    `data_source_config_id` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据源配置的编号',
    `scene`                 tinyint(4)                              NOT NULL DEFAULT '1' COMMENT '生成场景',
    `table_name`            varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表名称',
    `table_comment`         varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表描述',
    `remark`                varchar(500) COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '备注',
    `module_name`           varchar(30) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '模块名',
    `business_name`         varchar(30) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '业务名',
    `class_name`            varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '类名称',
    `class_comment`         varchar(50) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '类描述',
    `author`                varchar(50) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '作者',
    `template_type`         tinyint(4)                              NOT NULL DEFAULT '1' COMMENT '模板类型',
    `need_excel`            tinyint(4)                              NOT NULL DEFAULT '1' COMMENT '是否需要Excel',
    `parent_menu_id`        bigint(20)                                       DEFAULT NULL COMMENT '父菜单编号',
    `create_by`             varchar(64) COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '创建者',
    `create_time`           datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`             varchar(64) COLLATE utf8mb4_unicode_ci           DEFAULT '' COMMENT '更新者',
    `update_time`           datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `del_flag`              bit(1)                                  NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='代码生成表定义';

-- ----------------------------
-- Records of infra_codegen_table
-- ----------------------------
