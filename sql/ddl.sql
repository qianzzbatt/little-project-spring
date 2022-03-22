drop table if exists spring.t_comment;
create table spring.t_comment
(
    id              int auto_increment comment '主键'
        primary key,
    order_id        int         null comment '订单id',
    score           int         null comment '评分',
    comment_content varchar(64) null comment '评价',
    status          char        null comment '状态',
    consumer_id     int         null comment '消费者id',
    teacher_id      int         null comment '教师id',
    create_time     datetime    null comment '评价时间',
    adopt_time      datetime    null comment '采纳时间',
    reject_time     datetime    null comment '驳回时间'
)CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci
    comment '评价表';

drop table if exists spring.t_consumer;
create table spring.t_consumer
(
    id            int auto_increment comment '主键'
        primary key,
    consumer_name varchar(20)    null comment '消费者名称',
    phone         varchar(20)    null comment '手机号',
    credits       int            null comment '积分',
    sex           char           null comment '性别',
    birthday      date           null comment '生日',
    order_times   int            null comment '购买次数',
    total_prices  decimal(16, 2) null comment '总消费金额',
    open_id       varchar(36)    null comment '微信openId',
    email         varchar(64)    null comment '邮箱',
    address       varchar(100)   null comment '地址',
    login_times   int default 0  null comment '登录次数'
)CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci
    comment '消费者表';

drop table if exists spring.t_consumer_coupon;
create table spring.t_consumer_coupon
(
    id            int auto_increment comment '主键'
        primary key,
    consumer_id   int            null comment '消费者id',
    consumer_name varchar(20)    null comment '消费者名称',
    coupon_id     int            null comment '优惠券id',
    coupon_name   varchar(20)    null comment '优惠券名称',
    status        char           null comment '状态',
    amount        decimal(10, 2) null comment '金额',
    create_time   datetime       null comment '领取时间',
    expire_time   datetime       null comment '失效时间'
)CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci
    comment '消费者优惠券表';

drop table if exists spring.t_coupon;
create table spring.t_coupon
(
    id            int auto_increment comment '主键'
        primary key,
    coupon_name   varchar(20)    null comment '消费券名称',
    status        char           null comment '状态',
    validity_days int            null comment '有效天数',
    amount        decimal(10, 2) null comment '金额',
    total         int            null comment '消费券总数',
    remain_num    int            null comment '剩余数量'
)CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci
    comment '消费券表';

drop table if exists spring.t_order;
create table spring.t_order
(
    id                 int auto_increment comment '主键'
        primary key,
    consumer_id        int            null comment '消费者id',
    consumer_name      varchar(20)    null comment '消费者名称',
    teacher_id         int            null comment '家教老师id',
    teacher_name       varchar(20)    null comment '家教老师名称',
    course             char(2)        null comment '学科',
    start_date         datetime       null comment '家教开始时间',
    end_date           datetime       null comment '家教结束时间',
    create_time        datetime       null comment '订单创建时间',
    pay_time           datetime       null comment '订单支付时间',
    cancel_time        datetime       null comment '订单取消时间',
    finish_time        datetime       null comment '订单完成时间',
    price_of_day       decimal(10, 2) null comment '每天教学价格',
    between_day        int            null comment '间隔天数',
    total_prices       decimal(10, 2) null comment '总价',
    deduct_credits     int            null comment '扣除积分',
    consumer_coupon_id int            null comment '优惠券id',
    coupon_amount      decimal(10, 2) null comment '优惠券金额',
    credits_amount     decimal(10, 2) null comment '积分抵扣金额',
    pay_amount         decimal(10, 2) null comment '支付金额',
    pay_channel        char           null comment '支付渠道',
    receive_credits    int            null comment '订单收获积分',
    receive_coupon_id  int            null comment '订单获得优惠券id',
    status             char           null comment '订单状态',
    comment_id         int            null comment '评价id',
    counted            char default '0' null comment '是否已统计'
)CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci
    comment '订单表';

drop table if exists spring.t_teacher;
create table spring.t_teacher
(
    id             int auto_increment comment '主键'
        primary key,
    teacher_name   varchar(20)    null comment '教师名称',
    course         char(2)        null comment '学科',
    score          decimal(4, 2)  null comment '评分',
    teaching_days   int           null comment '教学天数',
    status         char           null comment '状态',
    teaching_count int            null comment '授课总数',
    price_of_day   decimal(10, 2) null comment '每天教学价格',
    start_time     datetime       null comment '开始教学时间',
    description    varchar(200)   null comment '简介',
    photo          varchar(500)   null comment '照片',
    detail         varchar(1000)  null comment '详细信息'
)CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci
    comment '家教老师表';

