package abcdjob.workonline.com.qrcode.Util;


public class RestAPI {



    //public static final String BASE_URL="https://abcdjob.live";
    public static final String BASE_URL="http://192.168.43.38/abcdj/";
    public static final String URL= BASE_URL +"/api_v1/api2.php?";
    public static final String API_Registation=URL +"user_register";
    public static final String API_Registation2=URL +"user_register2";
    public static final String CHECK_USER=URL + "check_user";
    public static final String API_Login=URL + "users_login";
    public static final String API_Device_Login=URL + "device_login";
    public static final String API_password_update=URL + "forgot_password";
    public static final String API_Gen_code=URL + "gen_code&id=";
    public static final String API_Login_Logs=URL + "user_logs";
    public static final String API_User_Balance=URL + "user_balance";
    public static final String API_Insert_wallet=URL + "insert_wallet";
    public static final String API_Balance_Update=URL + "balance_update";
    public static final String API_appJoining_Update=URL + "app_joining_fee_paid";
    public static final String API_Transaction_History=URL + "user_wallet_history";
    public static final String API_withdrawal_History = URL + "user_withdrawal_history&user_id=";
    public static final String API_update_profile = URL + "users_profile_update";
    public static final String API_Refer_History= URL+"user_Referal_history";
    public static final String API_Settings=URL + "settings";
    public static final String API_Settings2=URL + "settings";
    public static final String API_insert_payment_verification=URL + "insert_payment_verification";
    public static final String API_contactus=URL + "insert_contact_us";
    public static final String API_Spin_Count=URL + "user_coin_count";
    public static final String API_Video_Ads_Count=URL + "video_ads_count";
    public static final String API_Video_Ads_Count_update=URL + "video_ads_count_update";
    public static final String API_Spin_Count_Update=URL + "coin_count_update";
    public static final String API_Forgot_Pass=URL + "forgot_pass";
    public static final String API_PUSH_TOKEN=URL + "pushTokenUpdate";
    public static final String UPDATE_PSWD = BASE_URL + "forgotpswd.php";
    public static final String UPDATE_dt2 = BASE_URL + "/edit_detail.php";
    public static final String API_INSERT_WIDTHRAWAL=URL + "insert_withdrwal";
    public static final String GET_MY_TICKET_API =URL + "getMyTicket";
    public static final String GENERATE_TICKET_API =URL +  "generateTicket";
    public static final String GET_TICKET_COMMENTS_API =URL + "getTicketComments";
    public static final String ADD_TICKET_COMMENTS_API = URL + "addTicketComments";
    public static final String API_Insert_wallet2=URL + "insert_wallet2";


}
