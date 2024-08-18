drop table if EXISTS Product;
drop table if EXISTS category;
drop table if EXISTS prd_option;
drop table if EXISTS Product_detail;
drop table if EXISTS picture;
drop table if EXISTS pdt_size;
drop table if EXISTS pdt_category;
drop table if EXISTS CART;
drop table if EXISTS ORD_STATUS;
drop table if EXISTS ORDERS;
drop table if EXISTS ORDER_DELT;
drop table if EXISTS ORDER_HIS;
drop table if EXISTS EXCHRE;
drop table if EXISTS Notice;
drop table if EXISTS FAQ;
drop table if EXISTS FAQ_CATE;
drop table if EXISTS INQUIRY;
drop table if EXISTS RESPOND;
drop table if EXISTS RES_STATUS;
drop table if EXISTS TOP;
drop table if EXISTS DELV;
drop table if EXISTS cust;
drop table if EXISTS cust_hist;
drop table if EXISTS cust_wish;

-- set foreign_key_checks=0;

CREATE TABLE `Product` (
	`pdt_no`	INT	NOT NULL,
	`pdt_price`	INT	NOT NULL,
	`pdt_name`	VARCHAR(255)	NOT NULL,
	`new_item`	boolean	NOT NULL,
	`posting_status`	boolean	NOT NULL,
	`delivery_tip`	INT	NOT NULL,
	`discountable`	boolean	NOT NULL,
	`pdt_amount`	INT	NOT NULL	COMMENT '각 옵션별로 고유의 제품 번호를 갖는다',
	`notice`	VARCHAR(150)	NOT NULL,
    PRIMARY KEY (`pdt_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `category` (
	`main_category`	INT	NOT NULL,
	`sub_category`	INT	NOT NULL,
	`category_name`	VARCHAR(20)	NOT NULL,
    PRIMARY KEY (`main_category`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- option 테이블명 사용 불가 option 테이블앞에 prd 붙임
CREATE TABLE `prd_option` (
	`opt_no`	INT	NOT NULL,
	`pdt_no`	INT	NOT NULL,
	`opt_name`	VARCHAR(20)	NOT NULL,
	`opt_detail`	VARCHAR(20)	NOT NULL,
	`status`	boolean	NOT NULL,
    PRIMARY KEY (`opt_no`, `pdt_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Product_detail` (
	`pdt_detail_no`	INT	NOT NULL,
	`pdt_no`	INT	NOT NULL,
	`pdt_detail_title`	VARCHAR(100)	NULL,
	`pdt_detail_content`	VARCHAR(255)	NULL,
    PRIMARY KEY (`pdt_detail_no`, `pdt_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `picture` (
	`picture_id`	INT	NOT NULL,
	`vertical`	INT	NULL,
	`horizontal`	INT	NULL,
	`resolution`	VARCHAR(20)	NULL,
	`picture_url`	VARCHAR(255)	NULL,
	`pdt_detail_no`	INT	NOT NULL,
	`pdt_no`	INT	NOT NULL,
    PRIMARY KEY (`picture_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `pdt_size` (
	`pdt_detail_no`	INT	NOT NULL,
	`pdt_no`	INT	NOT NULL,
	`size`	INT	NULL,
	`length`	INT	NULL,
	`chest_round`	INT	NULL,
	`bottom_round`	INT	NULL,
    PRIMARY KEY (`pdt_detail_no`,`pdt_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `pdt_category` (
	`pdt_no`	INT	NOT NULL,
	`main_category`	INT	NOT NULL,
    PRIMARY KEY (`pdt_no`,`main_category`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `CART` (
	`cart_no`	int	NOT NULL	COMMENT '주문으로 넘어가면 삭제',
	`pdt_no`	INT	NOT NULL,
	`cart_cnt`	int(2)	NOT NULL	DEFAULT 1,
	`cart_pc`	bigint	NOT NULL,
	`cart_op`	varchar(10)	NOT NULL,
	`cart_delv`	int(30)	NOT NULL,
	`c_email`	varchar(30)	NULL	COMMENT '회원ID가 없으면 비회원',
    PRIMARY KEY (`cart_no`,`pdt_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--  테이블명 에러 STATUS → ORD_STATUS 로 바꿈
CREATE TABLE `ORD_STATUS` (
	`status_no`	varchar(15)	NOT NULL	COMMENT '상태코드확인용도',
	`st_taname`	varchar(10)	NOT NULL,
	`st_name`	varchar(10)	NOT NULL,
	`st_code`	varchar(10)	NOT NULL,
	`st_tf`	varchar(10)	NOT NULL,
    PRIMARY KEY (`status_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--  테이블명 에러 ORDER → ORDERS 로 바꿈
CREATE TABLE `ORDERS` (
	`or_no`	int	NOT NULL,
	`c_email`	varchar(30)	NOT NULL,
	`or_prs`	bigint	NOT NULL	DEFAULT 0,
	`or_delv`	int(10)	NOT NULL	DEFAULT 0,
	`or_date`	timestamp	NOT NULL	DEFAULT now(),
	`frst_reg_dt`	timestamp			NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`frst_reg_id`	varchar(10)			NOT NULL	DEFAULT 'test',
	`last_mod_dt`	timestamp			NOT NULL 	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`last_mod_id`	varchar(10)			NOT NULL	DEFAULT 'test',
    PRIMARY KEY (`or_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ORDER_DELT` (
	`od_no`	int	NOT NULL	COMMENT '교환/반품을하면 새로운 데이터가 추가',
	`or_no`	int	NOT NULL,
	`pdt_no`	INT	NOT NULL,
	`od_cnt`	int(2)	NOT NULL	DEFAULT 1	COMMENT '99개까지밖에 주문못함',
	`od_pr`	bigint	NOT NULL,
	`od_delv`	int(10)	NOT NULL,
	`od_op`	varchar(10)	NOT NULL,
	`od_delvinfo`	varchar(10)	NOT NULL	DEFAULT "일반상품"	COMMENT '일반 3일이내 배송, 특별 7일이내 배송',
	`od_date`	timestamp	NOT NULL	DEFAULT now(),
	`frst_reg_dt`	timestamp			NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`frst_reg_id`	varchar(10)			NOT NULL	DEFAULT 'test',
	`last_mod_dt`	timestamp			NOT NULL 	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`last_mod_id`	varchar(10)			NOT NULL	DEFAULT 'test',
    PRIMARY KEY (`od_no`,`or_no`,`pdt_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ORDER_HIS` (
	`oh_no`	int	NOT NULL,
	`or_no`	int	NOT NULL,
	`oh_Price`	bigint	NOT NULL,
	`oh_dp`	int(30)	NOT NULL,
	`oh_oa`	char(1)	NOT NULL	DEFAULT 'F'	COMMENT 'T/F',
	`oh_privacy`	char(1)	NOT NULL	DEFAULT 'F'	COMMENT 'T/F',
	`oh_status`	varchar(10)	NOT NULL	DEFAULT "주문완료"	COMMENT '주문완료/주문취소/반품중/교환/처리중',
	`oh_date`	timestamp	NOT NULL	DEFAULT now(),
	`oh_tf`	varchar(10)	NOT NULL	DEFAULT "주문"	COMMENT '주문/취소/교환/반품',
	`oh_certf`	char(1)	NOT NULL	DEFAULT 'T'	COMMENT 'T/F 일주일지나면 F',
	`frst_reg_dt`	timestamp			NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`frst_reg_id`	varchar(10)			NOT NULL	DEFAULT 'test',
	`last_mod_dt`	timestamp			NOT NULL 	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`last_mod_id`	varchar(10)			NOT NULL	DEFAULT 'test',
    PRIMARY KEY (`oh_no`,`or_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `EXCHRE` (
	`exchre_no`	int	NOT NULL,
	`or_no`	int	NOT NULL,
	`exchre_cnt`	int(2)	NULL	COMMENT '99개까지주문',
	`exchre_delv`	varchar(30)	NOT NULL,
	`exchre_st`	char(1)	NOT NULL,
	`exchre_date`	timestamp	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`exchre_tf`	varchar(10)	NOT NULL,
	`exchre_op`	varchar(10)	NULL,
	`exchre_text`	varchar(100)	NULL,
	`frst_reg_dt`	timestamp			NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`frst_reg_id`	varchar(10)			NOT NULL	DEFAULT 'test',
	`last_mod_dt`	timestamp			NOT NULL 	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`last_mod_id`	varchar(10)			NOT NULL	DEFAULT 'test',
    PRIMARY KEY (`exchre_no`,`or_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Notice` (
	`notice_no`	int	NOT NULL	COMMENT 'autoincresement',
	`n_title`	varchar(100)	NOT NULL,
	`contents`	text	NOT NULL	COMMENT 'html',
	`writer`	VARCHAR(255)	NOT NULL,
	`createDate`	TimeStamp	NOT NULL,
	`VeiwCnt`	int	NOT NULL,
	`state`	char(1)	NOT NULL	DEFAULT 'Y',
	`frst_reg_dt`	timestamp			NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`frst_reg_id`	varchar(10)			NOT NULL	DEFAULT 'test',
	`last_mod_dt`	timestamp			NOT NULL 	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`last_mod_id`	varchar(10)			NOT NULL	DEFAULT 'test',
    PRIMARY KEY (`notice_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--  VARCHAR 에서 timestamp 로 바꿈 (디폴트가 Date형식이라 VARCHAR가 들어갈수없어요)
CREATE TABLE `FAQ` (
	`faq_no`	INT	NOT NULL,
	`category_no`	INT	NOT NULL,
	`faq_order`	INT	NOT NULL,
	`is_top`	CHAR(1)	NOT NULL	DEFAULT 'N',
	`faq_title`	VARCHAR(100)	NOT NULL,
	`faq_content`	TEXT	NOT NULL,
	`faq_closing`	CHAR(100)	NOT NULL,
	`faq_att_file`	VARCHAR(100)	NULL,
	`faq_show`	CHAR(1)	NOT NULL	DEFAULT 'N',
	`faq_reg_date`	timestamp	NULL	DEFAULT CURRENT_TIMESTAMP,
	`faq_view_cnt`	INT	NOT NULL	DEFAULT 0,
	`frst_reg_dt`	timestamp			NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`frst_reg_id`	varchar(10)			NOT NULL	DEFAULT 'test',
	`last_mod_dt`	timestamp			NOT NULL 	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`last_mod_id`	varchar(10)			NOT NULL	DEFAULT 'test',
    PRIMARY KEY (`faq_no`,`category_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `FAQ_CATE` (
	`cate_no`	INT	NOT NULL,
	`cate_mid`	CHAR(20)	NOT NULL,
	`cate_small`	CHAR(20)	NOT NULL,
	`frst_reg_dt`	timestamp			NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`frst_reg_id`	varchar(10)			NOT NULL	DEFAULT 'test',
	`last_mod_dt`	timestamp			NOT NULL 	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`last_mod_id`	varchar(10)			NOT NULL	DEFAULT 'test',
    PRIMARY KEY (`cate_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `INQUIRY` (
	`inq_no`	INT	NOT NULL,
	`cate_no`	INT	NOT NULL,
	`c_email`	varchar(30)	NOT NULL,
	`inq_title`	VARCHAR(30)	NOT NULL,
	`inq_content`	TEXT	NOT NULL,
	`inq_att_file`	VARCHAR(100)	NULL,
	`inq_date`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`inq_status`	CHAR(1)	NOT NULL	DEFAULT 'Y'	COMMENT 'Y D',
	`inq_view_cnt`	INT	NOT NULL	DEFAULT 0,
	`inq_sms`	CHAR(1)	NOT NULL	DEFAULT 'N',
	`inq_email`	CHAR(1)	NOT NULL	DEFAULT 'Y',
	`frst_reg_dt`	timestamp			NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`frst_reg_id`	varchar(10)			NOT NULL	DEFAULT 'test',
	`last_mod_dt`	timestamp			NOT NULL 	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`last_mod_id`	varchar(10)			NOT NULL	DEFAULT 'test',
    PRIMARY KEY (`inq_no`,`cate_no`,`c_email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `RESPOND` (
	`res_no`	INT	NOT NULL,
	`inq_no`	INT	NOT NULL,
	`cate_no`	INT	NOT NULL,
	`res_status`	INT	NOT NULL	DEFAULT 1,
	`c_email`	varchar(30)	NOT NULL,
	`res_id`	VARCHAR(10)	NOT NULL,
	`res_content`	TEXT	NOT NULL,
	`res_date`	DATETIME	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`res_view_cnt`	INT	NOT NULL	DEFAULT 0,
	`frst_reg_dt`	timestamp			NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`frst_reg_id`	varchar(10)			NOT NULL	DEFAULT 'test',
	`last_mod_dt`	timestamp			NOT NULL 	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`last_mod_id`	varchar(10)			NOT NULL	DEFAULT 'test',
    PRIMARY KEY (`res_no`,`inq_no`,`cate_no`,`res_status`,`c_email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `RES_STATUS` (
	`res_status`	INT	NOT NULL	DEFAULT 1,
	`res_code`	INT	NOT NULL,
	`res_code_name`	CHAR(20)	NOT NULL,
	`frst_reg_dt`	timestamp			NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`frst_reg_id`	varchar(10)			NOT NULL	DEFAULT 'test',
	`last_mod_dt`	timestamp			NOT NULL 	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`last_mod_id`	varchar(10)			NOT NULL	DEFAULT 'test',
    PRIMARY KEY (`res_status`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `TOP` (
	`top_no`	INT	NOT NULL,
	`faq_no`	INT	NOT NULL,
	`category_no`	INT	NOT NULL,
	`cate_name`	CHAR(20)	NOT NULL,
	`frst_reg_dt`	timestamp			NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`frst_reg_id`	varchar(10)			NOT NULL	DEFAULT 'test',
	`last_mod_dt`	timestamp			NOT NULL 	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`last_mod_id`	varchar(10)			NOT NULL	DEFAULT 'test',
    PRIMARY KEY (`top_no`,`faq_no`,`category_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `DELV` (
	`Key`	int	NOT NULL,
	`or_no`	int	NOT NULL,
	`delv_name`	varchar(10)	NOT NULL,
	`delv_nname`	varchar(30)	NOT NULL,
	`delv_phon`	varchar(26)	NOT NULL,
	`delv_addr`	varchar(100)	NOT NULL,
	`delv_dead`	varchar(100)	NOT NULL	COMMENT '뭘넣던 도로명주소로 변경',
	`delv_mesg`	varchar(100)	NOT NULL,
	`frst_reg_dt`	timestamp			NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`frst_reg_id`	varchar(10)			NOT NULL	DEFAULT 'test',
	`last_mod_dt`	timestamp			NOT NULL 	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`last_mod_id`	varchar(10)			NOT NULL	DEFAULT 'test'
);

CREATE TABLE `CUST` (
	`c_email`		varchar(30)			NOT NULL,
	`c_stat_cd`		varchar(10)			NOT NULL 	DEFAULT 'M',
	`c_pwd`			varchar(40)			NOT NULL,
	`c_name`		varchar(15)			NOT NULL,
	`c_nick`		varchar(10)			NOT NULL,
	`c_birth`		varchar(8)			NOT NULL,
	`c_gnd`			char(1)				NOT NULL,
	`c_phn`			varchar(12)			NOT NULL,
	`c_zip`			varchar(6)			NOT NULL,
	`c_road_a`		varchar(30)			NOT NULL,
	`c_street_a`	varchar(30)			NOT NULL,
	`c_det_a`		varchar(30)			NOT NULL,
	`sms_agr`		char(1)				NOT NULL	DEFAULT 'N',
	`email_agr`		char(1)				NOT NULL	DEFAULT 'N',
	`reg_date`		datetime			NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`login_dt`		datetime			NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`tot_amt`		int					NOT NULL	DEFAULT 0,
	`frst_reg_dt`	timestamp			NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`frst_reg_id`	varchar(10)			NOT NULL 	DEFAULT 'dani',
	`last_mod_dt`	timestamp			NOT NULL 	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`last_mod_id`	varchar(10)			NOT NULL 	DEFAULT 'dani',
    PRIMARY KEY (`c_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `CUST_HIST` (
	`c_hist_num`	int					NOT NULL	AUTO_INCREMENT,
	`c_email`		varchar(30)			NOT NULL,
	`c_ch_cd`		varchar(10)			NOT NULL,
	`c_bf`			varchar(30)			NULL,
	`c_af`			varchar(30)			NOT NULL,
	`frst_reg_dt`	timestamp			NOT NULL	DEFAULT now(),
	`frst_reg_id`	varchar(10)			NOT NULL	DEFAULT 'test',
	`last_mod_dt`	timestamp			NOT NULL 	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`last_mod_id`	varchar(10)			NOT NULL	DEFAULT 'test',
	PRIMARY KEY (`c_hist_num`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `CUST_WISH` (
	`c_email`		varchar(30)			NOT NULL,
    `pdt_no`		int					NOT NULL,
	`wish_stat`		char(1)				NOT NULL	DEFAULT 'N',
	`frst_reg_dt`	timestamp			NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`frst_reg_id`	varchar(10)			NOT NULL	DEFAULT 'test',
	`last_mod_dt`	timestamp			NOT NULL 	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`last_mod_id`	varchar(10)			NOT NULL	DEFAULT 'test',
    PRIMARY KEY (`c_email`,`pdt_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;