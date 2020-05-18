
-- 用户注册信息表
create table s_user (
  user_id varchar(32) not null comment '用户唯一标识',
  user_name varchar(32) not null comment '用户名，登录时需要用的',
  nick_name varchar(32) not null comment '昵称',
  real_name varchar(32) not null comment '真实姓名，别人不可见',
  birthday date default null,
  tel_phone varchar(20) default null,
  email varchar(30) default null,
  address varchar(50) default '' comment '默认收货地址',
  grade int default '1' comment '会员等级',
  password varchar(10) default null comment '密码',
  is_deleted int default 0 comment '是否被删除,1为删除,0为正常',
  create_date date not null,
  update_date date default null,
  primary key (user_id)
);

-- 用户购物地址
create table s_user_address (
  address_id int(32) not null comment '地址的id用来区分地址',
  user_id varchar(32) not null,
  is_default int default 0 comment '是否为默认地址,1为默认地址,0为非默认地址',
  tel_phone varchar(11) default null comment '联系电话',
  address_content varchar(100) not null comment '地址的id用来区分地址',
  receiver_name varchar(32) default null comment '接受者姓名',
  is_deleted int default 0 comment '是否被删除,1为删除,0为正常',
  create_date date not null,
  update_date date default null,
  primary key (address_id, user_id)
);

-- 用户优惠券表
create table s_user_coupon (
  coupon_id varchar(32) not null comment'优惠券的id',
  user_id varchar(32) default null comment '持有者的id',
  preferential_amount decimal(10,2) default null comment '优惠金额限度',
  minimum_consumption_amount double(10,2) default null comment '最低金额限度',
  expiry_date date default null comment '截止日期',
  shop_name varchar(255) default null comment '优惠券的商家名',
  is_deleted int default 0 comment '是否被删除,1为删除,0为正常',
  create_date date not null,
  update_date date default null,
  primary key (coupon_id, user_id)
);

-- 商家注册信息表
create table s_shop (
  shop_id varchar(32) not null comment '商家唯一标识',
  shop_name varchar(32) not null comment '商家名，登录时需要用的',
  nick_name varchar(32) not null comment '商家昵称',
  real_name varchar(32) not null comment '商家真是注册名，别人不可见',
  register_date date not null comment '商家注册时间',
  tel_phone varchar(20) default null,
  email varchar(30) default null,
  address varchar(50) default '' comment '注册地址',
  grade int default '1' comment '商家等级',
  password varchar(10) default null comment '密码',
  is_deleted int default 0 comment '是否被删除,1为删除,0为正常',
  create_date date not null,
  update_date date default null,
  primary key (shop_id)
);

-- 商家优惠券表
create table s_coupon_warehouse (
  coupon_id varchar(32) not null comment '优惠券id便于修改优惠券的相关信息',
  shop_id varchar(32) not null comment '商家唯一标识',
  preferential_amount decimal(10,2) default null comment '优惠金额限度',
  minimum_consumption_amount decimal(10,2) default null comment '最低金额限度',
  expiry_date date default null comment '截止日期',
  remaining_count int default 0 comment '剩余的优惠券数量',
  is_deleted int default 0 comment '是否被删除,1为删除,0为正常',
  create_date date not null,
  update_date date default null,
  primary key (coupon_id, shop_id)
);

-- 会员卡表
CREATE TABLE s_membership_card (
  card_id varchar(32) DEFAULT NULL COMMENT '会员卡ID',
  card_name varchar(10) DEFAULT NULL COMMENT '银行名称',
  card_points decimal(100,0) DEFAULT NULL COMMENT '余额',
  card_type int DEFAULT NULL COMMENT '会员卡类型',
  user_id varchar(32) DEFAULT '' COMMENT '会员卡持有者',
  is_deleted int default 0 comment '是否被删除,1为删除,0为正常',
  create_date date not null,
  update_date date default null,
  primary key (card_id, shop_id)
);

-- 订单表
create table s_order (
  order_id varchar(32) not null comment '订单id',
  product_numbers decimal(10,2) default null comment '商品数目',
  product_info varchar(50) default null,
  product_total_price decimal(10,2) default '0.00',
  order_delivery_status varchar(10) default null comment '订单的状态(例如是否发货，以及发货情况)',
  express_delivery_company varchar(10) default null comment '快递公司',
  address varchar(20) default null comment '收货地址',
  user_evaluation_grade int default null comment '用户评价',
  user_evaluation_content varchar(100) default null comment '用户评价内容',
  user_id varchar(10) default null comment '买家的id',
  user_name varchar(10) default null comment '买家的用户名',
  order_payment_status varchar(10) default null comment '订单的支付情况',
  product_profit decimal(10,2) default null comment '本订单的盈利',
  trading_time date default null comment '交易时间',
  is_deleted int default 0 comment '是否被删除,1为删除,0为正常',
  create_date date not null,
  update_date date default null,
  primary key (order_id)
);

-- 用户收藏表
CREATE TABLE s_user_collection (
  user_id varchar(32) DEFAULT NULL COMMENT '收藏的用户的id',
  img_id varchar(10) DEFAULT NULL COMMENT '图片的名称，便于寻找图片',
  product_name varchar(10) DEFAULT NULL COMMENT '商品名称',
  shopping_name varchar(10) DEFAULT NULL COMMENT '店名',
  product_id varchar(10) NOT NULL COMMENT '商品号',
  PRIMARY KEY (product_id)
);

-- 用户购物车表
CREATE TABLE s_shopping_car (
  user_name varchar(10) DEFAULT NULL COMMENT '用户名',
  product_name varchar(10) DEFAULT NULL,
  product_number double(10,2) DEFAULT NULL COMMENT '商品数量',
  product_total_price double(10,2) DEFAULT NULL COMMENT '商品的总价',
  img_id varchar(10) DEFAULT NULL COMMENT '商品图片名',
  shopping_id varchar(50) DEFAULT NULL COMMENT '购物车商品项的id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;