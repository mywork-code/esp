package com.apass.gfb.framework.utils;

import java.util.HashMap;

public class BaseConstants {
    //通用的状态码
    public abstract class CommonCode {
    	private CommonCode() {
		}

        public static final String FAILED_CODE         = "0";        //获取数据失败状态码
        public static final String SUCCESS_CODE        = "1";        //获取数据成功状态码
        public static final String ERROR_CODE          = "0";        //获取数据出错状态码
        public static final String PARAM_ERROR_CODE    = "0";        //参数传递错误状态码
        public static final String NO_LOGIN            = "-99";      //参数传递错误状态码

        public static final String VBS_SUCCESS_CODE    = "0";        // VBS的成功状态码
        public static final String VBS_FAIL_CODE       = "1";        // VBS的失败状态码
        public static final String WEIXIN_SUCCESS_CODE = "0";        // 微信的成功状态码
        public static final String REJECT_CODE         = "reject";   //  审批驳回
        public static final String PASS_CODE           = "pass";     //  审批通过
        public static final String PROGRESS            = "progress"; // 进行中
        //客户申请贷款状态码
        public static final int WAIT_SIGN_CODE            = -4; 
        public static final int FINANCIAL_RETURN_CODE     = -3;
        public static final int TO_REVIEW_CODE            = -2; 
        public static final int TO_REJECT_CODE            = -1; 
        public static final int TO_LOANING_CODE           = 0; 
        public static final int HAVE_LOAN_CODE            = 1; 
        public static final int REPAYMENT_CODE            = 2; 
        public static final int BE_OVERDUE_CODE           = 3; 
        public static final int PAY_OFF_CODE              = 4; 
        public static final int TO_SURRENDER_CODE         = 5; 
    }

    //通用的消息
    public abstract class CommonMessage {
    	private CommonMessage() {
		}
    	
        public static final String FAILED_MESSAGE      = "获取数据失败!";    //获取数据失败
        public static final String SUCCESS_MESSAGE     = "请求数据成功!";    //获取数据失败
        public static final String ERROR_MESSAGE       = "请求数据出错!!";   //获取数据出错!
        public static final String PARAM_ERROR_MESSAGE = "请求参数传递错误!!"; //参数传递错误
        public static final String COMMON_FAIL_MSG     = "网络异常, 请稍后再试";
        //客户申请贷款状态
        public static final String WAIT_SIGN            = "待签名"; 
        public static final String FINANCIAL_RETURN     = "财务退回";
        public static final String TO_REVIEW            = "复核中"; 
        public static final String TO_REJECT            = "已拒绝"; 
        public static final String TO_LOANING           = "放款中"; 
        public static final String HAVE_LOAN            = "已放款"; 
        public static final String REPAYMENT            = "还款已提交"; 
        public static final String BE_OVERDUE           = "已逾期"; 
        public static final String PAY_OFF              = "已还清"; 
        public static final String TO_SURRENDER         = "已解约"; 

    }

    /**
     * 常用的参数名
     */
    public static class ParamsCode {
    	private ParamsCode() {
		}
    	
    	/*
        public static final String OPEN_ID            = "openId";           // 微信openId
        public static final String ID                 = "id";               // id
        public static final String TOKEN              = "token";            // 令牌
        public static final String VERCODE            = "vercode";          // 验证码
        public static final String VALIDATE_TYPE      = "validateType";     // 验证方式
        public static final String CITY_CODE          = "cityCode";         // 城市
        public static final String CREDIT_CARD        = "creditCardNo";     // 代还信用卡
        public static final String APPLY_AMOUNT       = "applyAmount";      // 申请金额
        public static final String CREDIT_CARD_BANK   = "creditCardBank";   // 信用卡银行
        public static final String APPLY_PERIODS      = "applyPeriods";     // 申请金额
        public static final String ORDER_ID           = "orderId";          // 订单号
        public static final String STATUS             = "status";           // 状态
        public static final String MOBILE             = "mobile";           // 手机号
        public static final String DEBIT_ACCOUNT_BANK = "debitCardBank";    // 还款借记卡银行
        public static final String DEBIT_ACCOUNT_NO   = "debitCardNo";      // 还款借记卡账号
        public static final String CUSTOMER_COMMENTS  = "customerComments"; // 吐槽
        public static final String GUID               = "guid";             // 清贷标识
        public static final String RANDOM             = "random";           // 验证码
        public static final String ACTIVE_CODE        = "activeCode";       // 激活码
        public static final String USERNAME           = "username";         // 账号
        public static final String PASSWORD           = "password";         // 密码
        public static final String OPERATE            = "operate";          // 操作方式
        public static final String REPEAT             = "repeat";           // 重新操作
        public static final String APPOINT_DATE       = "appointDate";      // 预约放放款日期
        public static final String CUSTOMER_ID        = "customerId";       // 客户ID
        public static final String REAL_NAME          = "realName";         // 真实姓名
        public static final String JOB                = "job";              // 职业
        public static final String WORK_CITY          = "workCity";         // 工作城市
        public static final String WORK_PROVINCE      = "workProvince";     // 工作城市
        public static final String SUPPORT_ADVANCE    = "supportAdvance";   // 是否交金
        public static final String LOCAL_REGISTRY     = "localRegistry";    // 本地户籍
        public static final String IDENTITY_NO        = "identityNo";       // 身份证
        public static final String EDUCATION_DEGREE   = "educationDegree";  // 学历
        public static final String MARRY_STATUS       = "marryStatus";      // 婚姻
        public static final String REQ_TYPE           = "reqType";          // 请求方式
        public static final String REPEAT_FLAG        = "repeatFlag";       // 手机实名重新申请标识
        public static final String CONFIRM_PASSWORD   = "confirmPassword";  // 确认密码
        public static final String CUST_HEAD          = "custHead";         // 客户头像
        public static final String NAME               = "name";             // 姓名
        public static final String QUERY_CODE         = "queryCode";        // 查询码
        */
    	
        // add by lc 20161219
        public static final String GOODS_ID                 = "goodsId";                // 商品ID
        public static final String GOODS_STOCK_ID           = "goodsStockId";           // 商品库存ID
        public static final String COUNT                    = "count";                  // 一次添加进购物车的商品数量
        public static final String USER_ID                  = "userId";                 // 用户ID
        public static final String GOODS_SELECTED_PRICE     = "goodsSelectedPrice";     // 商品选择时价格
    }

    /**
     * 系统用到的外部数据字典
     */
    public static class DICT {
    	private DICT() {
		}
        public static final String EDUCATION          = "NEWEDUCATION";   // 学历
        public static final String MARRIAGE           = "NEWMARRIAGE";    // 婚姻状况
        public static final String BANK               = "BANKLIST";       // 借记卡
        public static final String CREDIT_CARD        = "CREDITCARDLIST";
        public static final String CITY               = "BRANCH";         // 城市
        public static final String HOUSE_HOLD         = "HOUSEHOLD";      // 是否本地户口
        public static final String PERIODS            = "LOANPERIOD";     // 贷款期限
        public static final String HOUSE_HOLD_BENDIJI = "bendiji";        // 本地户籍
        public static final String INCOME             = "INCOMELIST";     // 收入
        public static final String ADVANCE_BRANCH     = "ADVANCE_BRANCH"; // 提额地区
    }

    // 展期申请状态与描述的映射
    public static class RollOverApplyStatusMap {
        public static final HashMap<String, String> RollOverStatusMap = new HashMap<>();
        static {
            RollOverStatusMap.put("0", "待复核");
            RollOverStatusMap.put("1", "系统同意");
            RollOverStatusMap.put("2", "人工同意");
            RollOverStatusMap.put("-1", "已拒绝");
        }
    }

    /**
     * 菜单事件
     */
    public static class EventMenuKey {
    	private EventMenuKey() {
		}
        public static final String EVENT_ABOUT_KKCREDIT     = "key_about_kkcredit";     // 关于卡卡贷
        public static final String EVENT_KKCREDIT_QUESTIONS = "key_kkcredit_questions"; // 常见问题
    }

}
