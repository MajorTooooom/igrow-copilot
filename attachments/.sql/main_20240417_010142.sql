/*
 Navicat Premium Data Transfer

 Source Server         : sqlite3-igrow-copilot-new
 Source Server Type    : SQLite
 Source Server Version : 3030001
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3030001
 File Encoding         : 65001

 Date: 17/04/2024 01:01:14
*/

PRAGMA foreign_keys = false;

-- ----------------------------
-- Table structure for sqlite_sequence
-- ----------------------------
DROP TABLE IF EXISTS "sqlite_sequence";
CREATE TABLE "sqlite_sequence" (
  "name" ,
  "seq" 
);

-- ----------------------------
-- Records of sqlite_sequence
-- ----------------------------
INSERT INTO "sqlite_sequence" VALUES ('t_gen_sys_user', 10);
INSERT INTO "sqlite_sequence" VALUES ('t_conn_cfg', 10);
INSERT INTO "sqlite_sequence" VALUES ('t_table_cfg', 4);
INSERT INTO "sqlite_sequence" VALUES ('t_column_cfg', 172);
INSERT INTO "sqlite_sequence" VALUES ('t_gen_cfg', 0);

-- ----------------------------
-- Table structure for t_column_cfg
-- ----------------------------
DROP TABLE IF EXISTS "t_column_cfg";
CREATE TABLE "t_column_cfg" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "user_id" INTEGER,
  "table_cfg_id" INTEGER,
  "table_schema" TEXT,
  "table_name" TEXT,
  "ordinal_position" TEXT,
  "column_name" TEXT,
  "column_type" TEXT,
  "column_comment" TEXT,
  "data_type" TEXT,
  "table_catalog" TEXT,
  "column_default" TEXT,
  "is_nullable" TEXT,
  "character_maximum_length" TEXT,
  "character_octet_length" TEXT,
  "numeric_precision" TEXT,
  "numeric_scale" TEXT,
  "datetime_precision" TEXT,
  "character_set_name" TEXT,
  "collation_name" TEXT,
  "column_key" TEXT,
  "extra" TEXT,
  "privileges" TEXT,
  "is_generated" TEXT,
  "generation_expression" TEXT,
  "srs_id" INTEGER,
  "java_name" TEXT,
  "java_type" TEXT,
  "java_type_class_name" TEXT,
  "column_swagger_comment" TEXT,
  "column_validation_comment" TEXT
);

-- ----------------------------
-- Records of t_column_cfg
-- ----------------------------
INSERT INTO "t_column_cfg" VALUES (130, 9, 4, 'jp_igrow_test', 'project_certification_history', 1, 'id', 'int', '主键', 'int', 'def', NULL, 'NO', NULL, NULL, 10, 0, NULL, NULL, NULL, 'PRI', 'auto_increment', 'select,insert,update,references', NULL, '', NULL, 'id', 'java.lang.Integer', 'Integer', '主键', '主键AAA');
INSERT INTO "t_column_cfg" VALUES (131, 9, 4, 'jp_igrow_test', 'project_certification_history', 2, 'project_name', 'varchar(255)', '项目名称', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'projectName', 'java.lang.String', 'String', '项目名称', '项目名称');
INSERT INTO "t_column_cfg" VALUES (132, 9, 4, 'jp_igrow_test', 'project_certification_history', 3, 'project_category', 'varchar(255)', '项目类别', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'projectCategory', 'java.lang.String', 'String', '项目类别', '项目类别');
INSERT INTO "t_column_cfg" VALUES (133, 9, 4, 'jp_igrow_test', 'project_certification_history', 4, 'project_number', 'varchar(255)', '项目编号', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'projectNumber', 'java.lang.String', 'String', '项目编号', '项目编号');
INSERT INTO "t_column_cfg" VALUES (134, 9, 4, 'jp_igrow_test', 'project_certification_history', 5, 'hua_id', 'varchar(255)', 'HUA ID', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'huaId', 'java.lang.String', 'String', 'HUA ID', 'HUA ID');
INSERT INTO "t_column_cfg" VALUES (135, 9, 4, 'jp_igrow_test', 'project_certification_history', 6, 'login_name', 'varchar(255)', '旧工号', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'loginName', 'java.lang.String', 'String', '旧工号', '旧工号');
INSERT INTO "t_column_cfg" VALUES (136, 9, 4, 'jp_igrow_test', 'project_certification_history', 7, 'wd', 'varchar(255)', 'WD', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'wd', 'java.lang.String', 'String', 'WD', 'WD');
INSERT INTO "t_column_cfg" VALUES (137, 9, 4, 'jp_igrow_test', 'project_certification_history', 8, 'employee_name', 'varchar(255)', '员工姓名', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'employeeName', 'java.lang.String', 'String', '员工姓名', '员工姓名');
INSERT INTO "t_column_cfg" VALUES (138, 9, 4, 'jp_igrow_test', 'project_certification_history', 9, 'english_name', 'varchar(255)', '英文名', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'englishName', 'java.lang.String', 'String', '英文名', '英文名');
INSERT INTO "t_column_cfg" VALUES (139, 9, 4, 'jp_igrow_test', 'project_certification_history', 10, 'current_zz_status', 'varchar(16)', '当前是否在职0否1是', 'varchar', 'def', NULL, 'YES', 16, 64, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'currentZzStatus', 'java.lang.String', 'String', '当前是否在职0否1是', '当前是否在职0否1是');
INSERT INTO "t_column_cfg" VALUES (140, 9, 4, 'jp_igrow_test', 'project_certification_history', 11, 'current_sector', 'varchar(255)', '当前Sector', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'currentSector', 'java.lang.String', 'String', '当前Sector', '当前Sector');
INSERT INTO "t_column_cfg" VALUES (141, 9, 4, 'jp_igrow_test', 'project_certification_history', 12, 'current_workgroup_name', 'varchar(255)', '当前工作组名', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'currentWorkgroupName', 'java.lang.String', 'String', '当前工作组名', '当前工作组名');
INSERT INTO "t_column_cfg" VALUES (142, 9, 4, 'jp_igrow_test', 'project_certification_history', 13, 'current_workshop', 'varchar(255)', '当前工作坊_WC', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'currentWorkshop', 'java.lang.String', 'String', '当前工作坊_WC', '当前工作坊_WC');
INSERT INTO "t_column_cfg" VALUES (143, 9, 4, 'jp_igrow_test', 'project_certification_history', 14, 'current_employee_position', 'varchar(255)', '当前员工职位', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'currentEmployeePosition', 'java.lang.String', 'String', '当前员工职位', '当前员工职位');
INSERT INTO "t_column_cfg" VALUES (144, 9, 4, 'jp_igrow_test', 'project_certification_history', 15, 'current_direct_supervisor', 'varchar(255)', '当前直接上级', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'currentDirectSupervisor', 'java.lang.String', 'String', '当前直接上级', '当前直接上级');
INSERT INTO "t_column_cfg" VALUES (145, 9, 4, 'jp_igrow_test', 'project_certification_history', 16, 'current_department_manager', 'varchar(255)', '当前部门经理', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'currentDepartmentManager', 'java.lang.String', 'String', '当前部门经理', '当前部门经理');
INSERT INTO "t_column_cfg" VALUES (146, 9, 4, 'jp_igrow_test', 'project_certification_history', 17, 'certification_date', 'datetime', '认证日期', 'datetime', 'def', NULL, 'YES', NULL, NULL, NULL, NULL, 0, NULL, NULL, '', '', 'select,insert,update,references', NULL, '', NULL, 'certificationDate', 'java.util.Date', 'Date', '认证日期', '认证日期');
INSERT INTO "t_column_cfg" VALUES (147, 9, 4, 'jp_igrow_test', 'project_certification_history', 18, 'certification_week_of_year', 'int', '认证日期归属周数', 'int', 'def', NULL, 'YES', NULL, NULL, 10, 0, NULL, NULL, NULL, '', '', 'select,insert,update,references', NULL, '', NULL, 'certificationWeekOfYear', 'java.lang.Integer', 'Integer', '认证日期归属周数', '认证日期归属周数');
INSERT INTO "t_column_cfg" VALUES (148, 9, 4, 'jp_igrow_test', 'project_certification_history', 19, 'certification_result', 'int', '认证结果0未通过1通过', 'int', 'def', NULL, 'YES', NULL, NULL, 10, 0, NULL, NULL, NULL, '', '', 'select,insert,update,references', NULL, '', NULL, 'certificationResult', 'java.lang.Integer', 'Integer', '认证结果0未通过1通过', '认证结果0未通过1通过');
INSERT INTO "t_column_cfg" VALUES (149, 9, 4, 'jp_igrow_test', 'project_certification_history', 20, 'certification_fiscal_year', 'varchar(255)', '认证财年', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'certificationFiscalYear', 'java.lang.String', 'String', '认证财年', '认证财年');
INSERT INTO "t_column_cfg" VALUES (150, 9, 4, 'jp_igrow_test', 'project_certification_history', 21, 'certification_fiscal_quarter', 'varchar(255)', '认证财季度', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'certificationFiscalQuarter', 'java.lang.String', 'String', '认证财季度', '认证财季度');
INSERT INTO "t_column_cfg" VALUES (151, 9, 4, 'jp_igrow_test', 'project_certification_history', 22, 'source', 'int', '来源1自建2导入', 'int', 'def', NULL, 'YES', NULL, NULL, 10, 0, NULL, NULL, NULL, '', '', 'select,insert,update,references', NULL, '', NULL, 'source', 'java.lang.Integer', 'Integer', '来源1自建2导入', '来源1自建2导入');
INSERT INTO "t_column_cfg" VALUES (152, 9, 4, 'jp_igrow_test', 'project_certification_history', 23, 'certificate_number', 'varchar(255)', '证书编号', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'certificateNumber', 'java.lang.String', 'String', '证书编号', '证书编号');
INSERT INTO "t_column_cfg" VALUES (153, 9, 4, 'jp_igrow_test', 'project_certification_history', 24, 'issue_date', 'datetime', '发证日期', 'datetime', 'def', NULL, 'YES', NULL, NULL, NULL, NULL, 0, NULL, NULL, '', '', 'select,insert,update,references', NULL, '', NULL, 'issueDate', 'java.util.Date', 'Date', '发证日期', '发证日期');
INSERT INTO "t_column_cfg" VALUES (154, 9, 4, 'jp_igrow_test', 'project_certification_history', 25, 'issuing_organization', 'varchar(255)', '发证机构', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'issuingOrganization', 'java.lang.String', 'String', '发证机构', '发证机构');
INSERT INTO "t_column_cfg" VALUES (155, 9, 4, 'jp_igrow_test', 'project_certification_history', 26, 'expiration_date', 'datetime', '截止日期', 'datetime', 'def', NULL, 'YES', NULL, NULL, NULL, NULL, 0, NULL, NULL, '', '', 'select,insert,update,references', NULL, '', NULL, 'expirationDate', 'java.util.Date', 'Date', '截止日期', '截止日期');
INSERT INTO "t_column_cfg" VALUES (156, 9, 4, 'jp_igrow_test', 'project_certification_history', 27, 'certificate_status', 'varchar(255)', '证书状态0失效1有效', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'certificateStatus', 'java.lang.String', 'String', '证书状态0失效1有效', '证书状态0失效1有效');
INSERT INTO "t_column_cfg" VALUES (157, 9, 4, 'jp_igrow_test', 'project_certification_history', 28, 'remark_one', 'text', '备注1', 'text', 'def', NULL, 'YES', 65535, 65535, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'remarkOne', 'java.lang.String', 'String', '备注1', '备注1');
INSERT INTO "t_column_cfg" VALUES (158, 9, 4, 'jp_igrow_test', 'project_certification_history', 29, 'remark_second', 'text', '备注2', 'text', 'def', NULL, 'YES', 65535, 65535, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'remarkSecond', 'java.lang.String', 'String', '备注2', '备注2');
INSERT INTO "t_column_cfg" VALUES (159, 9, 4, 'jp_igrow_test', 'project_certification_history', 30, 'former_sector', 'varchar(255)', '时任Sector', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'formerSector', 'java.lang.String', 'String', '时任Sector', '时任Sector');
INSERT INTO "t_column_cfg" VALUES (160, 9, 4, 'jp_igrow_test', 'project_certification_history', 31, 'former_workgroup_name', 'varchar(255)', '时任工作组名', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'formerWorkgroupName', 'java.lang.String', 'String', '时任工作组名', '时任工作组名');
INSERT INTO "t_column_cfg" VALUES (161, 9, 4, 'jp_igrow_test', 'project_certification_history', 32, 'former_workshop', 'varchar(255)', '时任工作坊', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'formerWorkshop', 'java.lang.String', 'String', '时任工作坊', '时任工作坊');
INSERT INTO "t_column_cfg" VALUES (162, 9, 4, 'jp_igrow_test', 'project_certification_history', 33, 'former_employee_position', 'varchar(255)', '时任员工职位', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'formerEmployeePosition', 'java.lang.String', 'String', '时任员工职位', '时任员工职位');
INSERT INTO "t_column_cfg" VALUES (163, 9, 4, 'jp_igrow_test', 'project_certification_history', 34, 'former_direct_supervisor', 'varchar(255)', '时任直接上级', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'formerDirectSupervisor', 'java.lang.String', 'String', '时任直接上级', '时任直接上级');
INSERT INTO "t_column_cfg" VALUES (164, 9, 4, 'jp_igrow_test', 'project_certification_history', 35, 'former_department_manager', 'varchar(255)', '时任部门经理', 'varchar', 'def', NULL, 'YES', 255, 1020, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'formerDepartmentManager', 'java.lang.String', 'String', '时任部门经理', '时任部门经理');
INSERT INTO "t_column_cfg" VALUES (165, 9, 4, 'jp_igrow_test', 'project_certification_history', 36, 'deleted', 'varchar(4)', '是否已删除0否1是', 'varchar', 'def', NULL, 'YES', 4, 16, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'deleted', 'java.lang.String', 'String', '是否已删除0否1是', '是否已删除0否1是');
INSERT INTO "t_column_cfg" VALUES (166, 9, 4, 'jp_igrow_test', 'project_certification_history', 37, 'field_change_log', 'text', '时效性字段内容变更记录', 'text', 'def', NULL, 'YES', 65535, 65535, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'fieldChangeLog', 'java.lang.String', 'String', '时效性字段内容变更记录', '时效性字段内容变更记录');
INSERT INTO "t_column_cfg" VALUES (167, 9, 4, 'jp_igrow_test', 'project_certification_history', 38, 'creator_user_id', 'varchar(128)', '创建者UserId', 'varchar', 'def', NULL, 'YES', 128, 512, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'creatorUserId', 'java.lang.String', 'String', '创建者UserId', '创建者UserId');
INSERT INTO "t_column_cfg" VALUES (168, 9, 4, 'jp_igrow_test', 'project_certification_history', 39, 'creator_user_name', 'varchar(128)', '创建者名称', 'varchar', 'def', NULL, 'YES', 128, 512, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'creatorUserName', 'java.lang.String', 'String', '创建者名称', '创建者名称');
INSERT INTO "t_column_cfg" VALUES (169, 9, 4, 'jp_igrow_test', 'project_certification_history', 40, 'create_time', 'datetime', '创建时间', 'datetime', 'def', NULL, 'YES', NULL, NULL, NULL, NULL, 0, NULL, NULL, '', '', 'select,insert,update,references', NULL, '', NULL, 'createTime', 'java.util.Date', 'Date', '创建时间', '创建时间');
INSERT INTO "t_column_cfg" VALUES (170, 9, 4, 'jp_igrow_test', 'project_certification_history', 41, 'updater_user_id', 'varchar(128)', '更新者UserId', 'varchar', 'def', NULL, 'YES', 128, 512, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'updaterUserId', 'java.lang.String', 'String', '更新者UserId', '更新者UserId');
INSERT INTO "t_column_cfg" VALUES (171, 9, 4, 'jp_igrow_test', 'project_certification_history', 42, 'updater_user_name', 'varchar(128)', '更新者名称', 'varchar', 'def', NULL, 'YES', 128, 512, NULL, NULL, NULL, 'utf8mb4', 'utf8mb4_0900_ai_ci', '', '', 'select,insert,update,references', NULL, '', NULL, 'updaterUserName', 'java.lang.String', 'String', '更新者名称', '更新者名称');
INSERT INTO "t_column_cfg" VALUES (172, 9, 4, 'jp_igrow_test', 'project_certification_history', 43, 'update_time', 'datetime', '更新时间', 'datetime', 'def', NULL, 'YES', NULL, NULL, NULL, NULL, 0, NULL, NULL, '', '', 'select,insert,update,references', NULL, '', NULL, 'updateTime', 'java.util.Date', 'Date', '更新时间', '更新时间');

-- ----------------------------
-- Table structure for t_conn_cfg
-- ----------------------------
DROP TABLE IF EXISTS "t_conn_cfg";
CREATE TABLE "t_conn_cfg" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "user_id" INTEGER,
  "cfg_name" TEXT,
  "url" TEXT,
  "user_name" TEXT,
  "password" TEXT,
  "is_deleted" TEXT,
  "create_time" TEXT,
  "update_time" TEXT
);

-- ----------------------------
-- Records of t_conn_cfg
-- ----------------------------
INSERT INTO "t_conn_cfg" VALUES (6, 9, '云服务器101测试库', 'jdbc:mysql://101.33.232.242:3333/information_schema?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowMultiQueries=true', 'root', 'dororo', 0, '2024-04-14 16:33:21', '2024-04-14 16:33:21');
INSERT INTO "t_conn_cfg" VALUES (7, 9, 'asdasdasd', 1, 2, 3, 1, '2024-04-14 17:13:45', '2024-04-15 00:16:09');
INSERT INTO "t_conn_cfg" VALUES (8, 9, 111, 222, 33, 44, 1, '2024-04-14 17:52:18', '2024-04-15 00:16:09');
INSERT INTO "t_conn_cfg" VALUES (9, 9, 1112, 12, 13, 'mmmmmmmmmmmmm', 1, '2024-04-14 18:01:12', '2024-04-15 00:16:09');
INSERT INTO "t_conn_cfg" VALUES (10, 9, 'a', 'jdbc:mysql://101.33.232.242:3333/information_schema?', 'vv', 'ssz', 1, '2024-04-14 18:39:39', '2024-04-15 00:16:09');

-- ----------------------------
-- Table structure for t_gen_cfg
-- ----------------------------
DROP TABLE IF EXISTS "t_gen_cfg";
CREATE TABLE "t_gen_cfg" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "user_id" INTEGER,
  "gen_cfg_name" TEXT,
  "source_code_abs_path" TEXT,
  "domain_package" TEXT,
  "dto_package" TEXT,
  "mapper_package" TEXT,
  "service_package" TEXT,
  "controller_package" TEXT,
  "easy_excel_listener_package" TEXT,
  "resource_abs_path" TEXT,
  "mapper_xml_path" TEXT,
  "is_extend_tk_mapper" TEXT,
  "tk_mapper_package" TEXT,
  "is_gen_swagger" TEXT,
  "is_gen_comment" TEXT,
  "is_gen_easy_excel" TEXT,
  "is_gen_javax_validation" TEXT,
  "create_time" TEXT,
  "update_time" TEXT
);

-- ----------------------------
-- Records of t_gen_cfg
-- ----------------------------

-- ----------------------------
-- Table structure for t_gen_sys_user
-- ----------------------------
DROP TABLE IF EXISTS "t_gen_sys_user";
CREATE TABLE "t_gen_sys_user" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "name" TEXT NOT NULL,
  "password" TEXT,
  "is_deleted" TEXT,
  "create_time" TEXT,
  "update_time" TEXT
);

-- ----------------------------
-- Records of t_gen_sys_user
-- ----------------------------
INSERT INTO "t_gen_sys_user" VALUES (9, 'dororo', 'af622f54fe591c5fc2b2f13c3800556b', 0, '2024-04-14 14:22:43', '2024-04-14 14:22:43');

-- ----------------------------
-- Table structure for t_table_cfg
-- ----------------------------
DROP TABLE IF EXISTS "t_table_cfg";
CREATE TABLE "t_table_cfg" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "user_id" INTEGER,
  "table_cfg_name" TEXT,
  "conn_url" TEXT,
  "conn_user_name" TEXT,
  "conn_password" TEXT,
  "table_schema" TEXT,
  "table_name" TEXT,
  "domain_name" TEXT,
  "domain_zn_name" TEXT,
  "create_time" TEXT,
  "update_time" TEXT
);

-- ----------------------------
-- Records of t_table_cfg
-- ----------------------------
INSERT INTO "t_table_cfg" VALUES (4, 9, '[jp_igrow_test]-[project_certification_history]', 'jdbc:mysql://101.33.232.242:3333/information_schema?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowMultiQueries=true', 'root', 'dororo', 'jp_igrow_test', 'project_certification_history', 'ProjectCertificationHistory', '项目认证历史', NULL, '2024-04-17 00:36:18');

-- ----------------------------
-- Auto increment value for t_column_cfg
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 172 WHERE name = 't_column_cfg';

-- ----------------------------
-- Auto increment value for t_conn_cfg
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 10 WHERE name = 't_conn_cfg';

-- ----------------------------
-- Indexes structure for table t_conn_cfg
-- ----------------------------
CREATE UNIQUE INDEX "ix_c_c_name"
ON "t_conn_cfg" (
  "user_id" COLLATE BINARY ASC,
  "cfg_name" COLLATE BINARY ASC
);

-- ----------------------------
-- Auto increment value for t_gen_cfg
-- ----------------------------

-- ----------------------------
-- Indexes structure for table t_gen_cfg
-- ----------------------------
CREATE UNIQUE INDEX "ix_g_c_n_u"
ON "t_gen_cfg" (
  "user_id" COLLATE BINARY ASC,
  "gen_cfg_name" COLLATE BINARY ASC
);

-- ----------------------------
-- Auto increment value for t_gen_sys_user
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 10 WHERE name = 't_gen_sys_user';

-- ----------------------------
-- Indexes structure for table t_gen_sys_user
-- ----------------------------
CREATE UNIQUE INDEX "ix_g_s_u_name"
ON "t_gen_sys_user" (
  "name" COLLATE BINARY ASC
);

-- ----------------------------
-- Auto increment value for t_table_cfg
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 4 WHERE name = 't_table_cfg';

-- ----------------------------
-- Indexes structure for table t_table_cfg
-- ----------------------------
CREATE UNIQUE INDEX "ix_t_c_tcn"
ON "t_table_cfg" (
  "user_id" COLLATE BINARY ASC,
  "table_cfg_name" COLLATE BINARY ASC
);

PRAGMA foreign_keys = true;
