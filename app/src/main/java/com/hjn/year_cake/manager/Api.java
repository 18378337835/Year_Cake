package com.hjn.year_cake.manager;

/**
 * Created by ${templefck} on 2018/8/28.
 */
public class Api {
    private static final boolean isTest = false;

    private static final String TEST_URL = "http://duokeapi.jrweid.com";
    private static final String RELEASE_URL = "https://dkapi.jrweid.com";
    private static final String URL = isTest ? TEST_URL : RELEASE_URL;

    /**
     * 登录
     */
    public static final String LOGIN = URL + "/broker/login";

/*
申述列表
*/
    public static final String Apeal=URL+"/appeal/list";
    /**
     * 获取验证码
     */
    public static final String GET_CHECK_CODE = URL+"/broker/send-verify-code";
    /**
     * 检查版本
     */
    public static final String CHECK_VERSION = URL + "/index/check_version";

    /**
     * 修改个人信息
     */
    public static final String upload_data=URL+"/member-center/update";
    /**
     * 个人中心
     */
    public static final String USE_INFO = URL + "/member-center/member-info";

    /**
     * 上传图像
     */
    public static final String file_upload  = URL + "/upload/file-upload";
    /**
     * 获取app banner信息
     */
    public static final String APP_BANNER  = URL + "/customer/banner";
    /**
     * 	客户抢单列表
     */
    public static final String HOME_DATA = URL + "/customer/lists";
    /**
     * 	获取选项数据
     */
    public static final String OPTION_DATA= URL + "/customer/option";
    /**
     * 	获取抢单详细信息
     */
    public static final String ORDER_DETAIL= URL + "/customer/detail";
    /**
     * 	我的客户列表
     */
    public static final String CUSTOMER_LIST= URL + "/grab-customer/lists";
    /**
     * 	删除客户
     */
    public static final String DELETE_CUSTOMER = URL + "/grab-customer/delete";
    /**
     * 	客户详情
     */
    public static final String CUSTOMER_DETAIL = URL + "/grab-customer/detail";

   /* 意见反馈*/
   public static final String Feedback= URL + "/feedback/feed-submit";

    /* 获取push数据*/
    public static final String GetPush= URL + "/divpush/GetPush";

    /* 发送push数据*/
    public static final String PushData= URL + "/divpush/SetPush";

    /* 邀请好友*/
    public static final String invite= URL + "/invite/list";

    /*申诉详情*/
    public static final String AppealContent= URL + "/appeal/detail";

    /*	客户更新*/
    public static final String UDPATE_CUSTOMER= URL + "/grab-customer/update";

    //导出客户查询
    public static final String EXPORT_CLIENT= URL + "/grab-customer/export";

    //	获取当前用户认证状态
    public static final String GET_AUTH_STATUS= URL + "/broker-auth/get-auth-status";
    /**
     * 三要素认证
     */
    public static final String THREE_ELE_AUTH = URL + "/broker-auth/three-ele-auth";
    /**
     * 活体识别
     */
    public static final String LIVING_AUTH = URL + "/broker-auth/living-auth";


    /**
     * 身份证ocr识别信息
     */
    public static final String OCR_ID_CARD = "https://api.megvii.com/faceid/v3/ocridcard";
    /*发送devicetoken和devicetype*/
    public static final String DeviceToken= URL + "/broker-auth/set-device-token";
    // 企业验证
    public static final String ENTER_VERIFY = URL + "/broker-auth/enterprise-auth";
    // 签到数据
    public static final String SIGN_INFO = URL + "/sign-in/sign-option";
    // 签到
    public static final String SIGN_IN = URL + "/sign-in/set";
    //埋点统计
    public static final String TJ_POINT = URL + "/tj/report";
    //埋点统计
    public static final String GRAB_CUSTOMER = URL + "/grab-customer/grab";
    //申诉申请
    public static final String APPEAL_SAVE = URL + "/appeal/save";
    //申诉申请
    public static final String LINK_INFO = URL + "/h5-links/list";
    //新的检查版本更新接口
    public static final String CHECK_UPDATE = isTest ? "http://testup.jrweid.com/duoke/index.php" :
                                              "http://up.jrweid.com/duoke/index.php";
}
