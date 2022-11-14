package abcdjob.workonline.com.qrcode.Util;

import com.google.gson.JsonObject;

import abcdjob.workonline.com.qrcode.Models.AdModelCount;
import abcdjob.workonline.com.qrcode.Models.Settings;
import abcdjob.workonline.com.qrcode.Models.UserDTO;

public class GlobalVariables {

    public static final String PREF_NAME = "MAIN PREF";

    //todo: ADMIN PREFs
    public static final String ADMIN_PREF = "ADMIN PREFERENCES";
    public static final String JOINING_BONUS = "joiningBonus";
    public static final String REFER_RATE = "refer_rate";
    public static final String QR_COIN = "perQrcoin";
    public static final String NEWS_COINS = "newsCoins";
    public static final String QUIZ_COINS = "quizCoins";
    public static final String QUIZ_MATH_COINS = "quizMath";
    public static final String MAXM_MATHSQUIZ_QUESTN = "mathsQuizQuestn";
    public static final String HOURLY_SPIN_LIMIT = "hourlySpinLimit";
    public static final String HOURLY_MATHS_QUIZ_PLAY_LIMIT = "mathsQuizPlaylimit";
    public static final String MATHS_QUIZUNLOCK_MIN = "mathsQuizunlockMin";
    public static final String TASK_COINS = "taskCoin";
    public static final String REDEEM_MIN = "redeemMin";
    public static final String REDEEM_AMOUNT ="redeemAmount";
    public static final String APP_JOINING_FEE ="appJoiningfee";
    public static final String QR_MIN_TIME ="qrMinTime";
    public static final String WIDTHRAWAL_BTN ="widthrawBtn";
    public static final String GENERATE_BTN ="generateBtn";
    public static final String PAYMENT_GATEWAY ="paymentGateway";
    public static final String PAYTM_MID ="paytmMid";
    public static final String PAYTM_MERCHENT_KEY ="paytmMerchentKey";
    public static final String RAZORPAY_MID ="razorpaymid";
    public static final String RAZORPAY_MERCHENT_KEY ="razorpayMerchentKey";
    public static final String PAYUMONEY_MID ="payumoneyMid";
    public static final String PAYUMONEY_MERCHENT_KEY ="payumoneyMerchentKey";
    //public static final String USER_UID = "user uid";
    public static final String ADD_TYPE ="addType" ;
    public static final String BANNER_ADD ="bannerAdd" ;
    public static final String INDUSTRIAL_ADD ="industrialAdd" ;
    public static final String INDUSTRIAL_CALL_TYPE = "industrial_call_type";
    public static final String REWARD_ADD ="rewardAdd" ;
    public static final String NATIVE_ADD ="nativeAdd" ;
    public static final String FB_BANNER_AD1 = "fb_banner1";
    public static final String FB_BANNER_AD2 = "fb_banner2";
    public static final String FB_INTERSTITIAL_AD_ID_1 = "fb_indusrial1";
    public static final String FB_INTERSTITIAL_AD_ID_2 = "fb_indusrial2";
    public static final String FB_INTERSTITIAL_AD_ID_3 = "fb_indusrial3";
    public static final String FB_REWARD_AD_ID_1 = "fb_reward1";
    public static final String FB_REWARD_AD_ID_2 = "fb_reward2";
    public static final String FB_NATIVE = "fb_native";
    public static final String ADDMOBPUBLISHER_ID = "addModPublisherId";
    public static final String ADDMOB_BANER = "addMobBanner";
    public static final String ADDMOB_BANER2 = "addMobBanner2";
    public static final String ADDMOB_INDUSTRIAL = "addMobIndustrial";
    public static final String ADDMOB_REWARDED = "addMobRewarded";
    public static final String ADDMOB_NATIVE = "addMobNative";
    // public static final String FB_REWARD_COINS = "rewardAdCoins";
    public static final String TASKS_COMPLETION = "task comp";
    public static final String NEWS_API_KEY = "newsApi";
    public static final String TELEGRAM_LINK = "telegram" +
            "Link";
    public static final String YOUTUBE_LINK = "youtubeLink";
    public static final String FACEBOOK_PAGE = "facebookPage";
    public static final String NEW_VERSION = "newVersion";
    public static final String UPDATE_LINK = "updateLink";
    public static final String ADMIN_MSG = "adminMsg";
    public static final String JOIN_GROUP = "joinGroup";
    public static final String APP_NAME = "appName";
    public static final String APP_LOGO = "appLogo";
    public static final String APP_DESCRIPTION = "appDescription";
    public static final String APP_VERSION = "appVersion";
    public static final String APP_AUTHOR = "appAuthor";
    public static final String APP_EMAIL = "appEmail";
    public static final String APP_WEBSITE = "appWebsite";
    public static final String APP_DEVELOPED_BY = "appDevelopedby";
    public static final String PAYMENT_MODE1 = "paymentMode1";
    public static final String PAYMENT_MODE2 = "paymentMode2";
    public static final String WIDTHRAW_NOTE = "widthrawNote";
    public static final String TASK_INSTRUCTION1 = "taskInstruction1";
    public static final String TASK_INSTRUCTION2 = "taskInstruction2";
    public static final String TASK_INSTRUCTION3 = "taskInstruction3";
    public static final String TASK_INSTRUCTION4 = "taskInstruction4";
    public static final String TASK_INSTRUCTION5 = "taskInstruction5";
    public static final String TASK_INSTRUCTION6 = "taskInstruction6";
    public static final String TASK_INSTRUCTION7 = "taskInstruction7";
    public static final String TASK_INSTRUCTION8 = "taskInstruction8";
    public static final String TASK_INSTRUCTION9 = "taskInstruction9";
    public static final String MARQUE_TEXT=         "marque_text";
    public static final String CALLING_INDUSTRIAL_COUNT="CALLINGINDUSTRIALCOUNT";
    public static final String CALLED_INDUSTRIAL_ADDS="CALLINGINDUSTRIAL";
    public static final String TASK_COUNT="taskCount";

    public static final String CODE_ID = "S_id";
    public static final String CODE_STUDENT_NAME = "S_NAME";
    public static final String CODE_ID_NUMBER = "S_NUMBER";
    public static final String CODE_PINCODE = "S_PINCODE";
    public static final String CODE_CITY = "S_CITY";
    public static final String QRCOUNT= "qrcount";
    public static final String D1= "S_id";
    public static final String generate_clicked = "false";
    public static final String D2 = "S_NAME";
    public static final String D3 = "S_NUMBER";

    public static final String D4= "S_PINCODE";
    public static final String txn_orderId= "O_id";
    public static final String Txn_amount= "0";
    public static final String Txn_type= "txnType";
    public static final String Txn_status= "txnStatus";




    public static final String TASK_REWARD = "taskReward";
    //

    //USER_DETAILS STARTS HERE
    public static final String USER_ID = "user_id";
    public static final String USER_MOBILE = "userPhone";
    public static final String USER_PASSWORD = "password";
    public static final String USERNAME = "name";
    public static final String USER_CITY = "city";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_COINS = "coin";
    public static final String USER_BONUS_BALANCE = "bonusBalance";
    public static final String USER_TOTAL_QR_GEN = "usertotal_qr_gen";
    public static final String USER_CORRECT_QR_GEN = "user_correct_qr_gen";
    public static final String TOTAL_PAID = "totalPaid";
    public static final String REFFER_PAID = " reffered_paid";
    public static final String JOINING_PAID = "joining_paid";
    public static final String TOTAL_REFERALS = "totalreferals";
    public static final String USER_RANK = "rank";
    public static final String USER_REFERAL_CODE = "referCode";
    public static final String REFFERED_BY= "refferedBy";
    public static final String ALLOW = "allow";
    public static final String CODE_GEN_ALLOW = "CODE_GEN_ALLOW";
    public static final String WIDTHRAWAL_ALLOW = "widthrawal_allow";
    public static final String DEVICE_id = "deviceId";
    public static final String PROFILE_PIC = "profilePic";
    public static final String ACTIVE_DATE = "activeDate";
    public static final String JOINING_TIME = "joiningTime";
    public static final String DAILY_SPIN = "dailySpin";
    public static final String DAILY_WATCH = "dailyWatch";
    public static final String DAILY_SCRATCH = "dailyScratch";
    public static final String DAILY_QUIZ = "dailyQuiz";

    public static final String SPIN_HOUR = "spinHour";
    public static final String WATCH_HOUR = "watchHour";
    public static final String SCRATCH_HOUR = "scratchHour";
    public static final String QUIZ_HOUR = "quizHour";
    public static final String CURRENT_MINUTE = "currentminute";



    //todo: USER PREFS
    public static final String USER_PREF = "user pref";

    public static final String OLD_DATE = "old";
    public static final String OLD_DAY = "oldDay";
    public static final String REFERRAL_POS = "oldDay";
    public static final String REFERRAL_INPUT = "oldDay";



    public static final String Telegram_click="Telegram_click";
    public static final String Facebook_click="facebook_click";
    public static final String Youtube_click="Youtube_click";


    //todo:
    public static final String CHANNEL_ID = "exampleChannel";
    public static final String USER_DTO = "userDTO";
    public static final String SettingsDto = "settingsDTO";
    public static final Boolean USER_IS_LOGIN = false;
    public static final String DEVICE_TOKEN ="" ;
    public static final String CHAT_NOTIFICATION = "10007";//both
    public static final String IMAGE_URI_CAMERA = "image_uri_camera";
    public static final String IMAGE =  "big_picture";
    public static final String CHAT_TYPE = "chat_type";
    public static Settings settings;
    public static UserDTO usermDTO;
    public static abcdjob.workonline.com.qrcode.Models.codeGen codeGen;
    public static abcdjob.workonline.com.qrcode.Models.AdModelCount AdModelCount;
    public static AdModelCount adModelCount= new AdModelCount("0","0","0","0");;
    public static final String AppSid="abcdapp";
    public static JsonObject abcdapp;
    public static String TAG="abcdapp";

    public static String LANGUAGE = "language";
    public static String LATITUDE = "latitude";
    public static String LONGITUDE = "longitude";
    public static  String CATEGORY_ID = "category_id";
    public static long dismissAfter=100;
    public static  String LANGUAGE_SELECTION = "language_selection";

    public static String CAMERA_ACCEPTED = "camera_accepted";
    public static  String STORAGE_ACCEPTED = "storage_accepted";
    public static String MODIFY_AUDIO_ACCEPTED = "modify_audio_accepted";
    public static  String CALL_PRIVILAGE = "call_privilage";

    public static final String btntxt = "Go to Home";
    public static final String UpdateKyc = "Update Kyc";
    public static final String update="UPDATE";
    public static final String blocked="CONTACT ADMIN";


    public static String homeClicked="homeClicked";
    public static String AppContact="";
    /*Add Ticket*/
    public static final String REASON = "reason";
    public static final String PLAY_SOUND = "play_sound";
    public static final String VIBRATE = "vibrate";
    public static final  String SAVE_HISTORY = "save_history";
    public static final String COPY_TO_CLIPBOARD = "copy_to_clipboard";

    /*Get Ticket*/
    public static final  String TICKET_ID = "ticket_id";
    public static final String COMMENT = "comment";
    public static final String TAG_MAIN = "main";
    public static final String TAG_CHAT = "chat";
    public static final String TAG_HOME = "home";
    public static final String TAG_ADMINNOTI = "updateNotification";
    public static  String TYPE = "type";
    public static  String TOPIC_CUSTOMER = "Customer";
    public static  String userMobile = "userMobile";
    public static String CHAT_LIST_DTO = "chat_list_dto";
    public static String userName = "userName";
    public static String adminMobile = "adminMobile";
    public static  String SCREEN_TAG = "screen_tag";
    public static  String ADMIN_NOTIFICATION = "10016";
}
