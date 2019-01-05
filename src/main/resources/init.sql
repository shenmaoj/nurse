CREATE TABLE `t_sys_user` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `org_id` varchar(32) DEFAULT NULL COMMENT '单位id',
  `full_name` varchar(64) DEFAULT NULL COMMENT '全名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `user_type` varchar(2) DEFAULT NULL COMMENT '用户类型\r\n  ',
  `status` varchar(10) DEFAULT NULL COMMENT '用户状态',
  `create_id` varchar(10) DEFAULT NULL COMMENT '用户状态',
  `last_update_id` varchar(10) DEFAULT NULL COMMENT '用户状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基本信息表';


CREATE TABLE `t_sys_role` (
  `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `create_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人id',
  `last_update_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后修改人id',
  `status` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态',
  `name` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色名称',
  `type` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色类型（同用户类型）',
  `category` varchar(4) COLLATE utf8mb4_bin DEFAULT '' COMMENT '角色分类(单位05，科室06)',
  `relation` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '科室关联单位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色表';

CREATE TABLE `t_sys_role_authz` (
  `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `role_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色表id',
  `menu_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单表id',
  `authz` varchar(5000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '权限项',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色权限表';

CREATE TABLE `t_sys_menu` (
  `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键',
  `parent_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '上级菜单',
  `name` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单名称',
  `code` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单编号',
  `icon` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单url',
  `level` int(11) DEFAULT NULL COMMENT '菜单等级',
  `menu_order` int(11) DEFAULT NULL COMMENT '菜单排序',
  `authz` varchar(5000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '对应的权限项，使用逗号隔开',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='菜单表';

CREATE TABLE `t_sys_basedata_item` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `code` varchar(64) DEFAULT NULL COMMENT '项编码',
  `name` varchar(64) DEFAULT NULL COMMENT '项名称',
  `type_code` varchar(64) DEFAULT NULL COMMENT '类型编码',
  `remark` varchar(256) DEFAULT NULL COMMENT '说明',
  `seqno` int(11) DEFAULT NULL COMMENT '顺序',
  `last_update_id` varchar(36) DEFAULT NULL COMMENT '修改人ID',
  `last_update_time` datetime DEFAULT NULL COMMENT '修改人姓名',
  `create_id` varchar(36) DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典类型项目表';

CREATE TABLE `t_sys_basedata_type` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `code` varchar(64) DEFAULT NULL COMMENT '类型编码',
  `name` varchar(64) DEFAULT NULL COMMENT '类型名称',
  `last_update_id` varchar(36) DEFAULT NULL COMMENT '修改人ID',
  `last_update_time` datetime DEFAULT NULL COMMENT '修改人姓名',
  `create_id` varchar(36) DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典类型表';

