package abcdjob.workonline.com.qrcode.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.razorpay.Checkout;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.Objects;
import abcdjob.workonline.com.qrcode.Interface.Helper;
import abcdjob.workonline.com.qrcode.Models.UserDTO;
import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.Util.Method;
import abcdjob.workonline.com.qrcode.Util.RestAPI;
import abcdjob.workonline.com.qrcode.databinding.ActivityLoginBinding;
import abcdjob.workonline.com.qrcode.https.HttpsRequest;

import abcdjob.workonline.com.qrcode.ui.home.HomeActivity;



public class LoginActivity extends AppCompatActivity {
    String phone, password;
    private Method method;
    public UserDTO userDTO;
    Activity activity;
    private ActivityLoginBinding binding;

    @SuppressLint("HardwareIds")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        activity = LoginActivity.this;
        method=new Method(LoginActivity.this);
        Checkout.preload(getApplicationContext());
        Method.privacyPolicyMsg(binding.tvPrivacy, activity);

        binding.contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ContactActivity.class);
                intent.putExtra("callingActivity","LoginActivity");  // pass your values and retrieve them in the other Activity using keyName
                startActivity(intent);

                finish();
            }
        });

/*        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Constant.DeviceID = getDeviceId(LoginActivity.this);

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                finish();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE)
                .check();*/

        binding.forgotbtn.setOnClickListener(v -> {
            //startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
            startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
            finish();
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phone = Objects.requireNonNull(binding.phoneNo.getEditText()).getText().toString().trim();
                password = Objects.requireNonNull(binding.password.getEditText()).getText().toString().trim();

                if (phone.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Your Phone or Password is Worng", Toast.LENGTH_SHORT).show();
                }else if (!binding.checkPrivacy.isChecked()) {

                    method.showToasty(LoginActivity.this,"2",getString(R.string.privacy_policy_alert_msg));
                } else {

                    if(method.isOnline(activity)){
                        login(activity);
                      //  Log.d(GlobalVariables.TAG, "onClick: "+method.isOnline(activity));
                    }else{
                        method.showToasty(LoginActivity.this,"2",getString(R.string.noInternet));

                    }


                }

            }
        });


    }

    public void OpenSignUp(View view) {
        Intent loginIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(loginIntent);
        finish();
    }

    public void OpenContactus(View view) {
        Intent loginIntent = new Intent(LoginActivity.this, ContactActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    public void onBackPressed() {
        finish();
        finishAffinity();
    }



    public void login(Activity activity) {
        Log.d(GlobalVariables.TAG, "getHomeData: "+ Method.userDTO.getTotalAllQrGeneration());
        method.loadingDialogg(activity);
        method.params.clear();
        method.params.put("users_login", "KINGSN");
        method.params.put("phone", phone);
        method.params.put("password", password);
        method.params.put("device_id", getDeviceId(this));
        // method.showToasty(activity,"1",""+GlobalVariables.adminUserID);
        Log.d(GlobalVariables.TAG, "getHomeData2: called"+activity.toString());
        new HttpsRequest(RestAPI.API_Login, method.params, activity).stringPost2(GlobalVariables.TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, JSONObject abcdapp, String title, String message, JSONObject response) {
                Log.d(GlobalVariables.TAG, "backResponsef: "+flag);
                if (flag) {
                        try {
                                userDTO = new Gson().fromJson(response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").toString(), (Type) UserDTO.class);
                                method.preferencess.setParentUser2(userDTO, String.valueOf(userDTO));
                                GlobalVariables.usermDTO=userDTO;
                                method.preferencess.setValue(GlobalVariables.USER_MOBILE,phone);
                            Log.d("helloji", "backResponse: "+method.userDTO.getMobile());

                                Method.loadingDialog.dismiss();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);

                            } catch (Exception e)
                            { e.printStackTrace();}

                            } else {
                                androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(activity);
                                alertDialogBuilder.setTitle("Login Failed");
                                alertDialogBuilder.setMessage(message);
                                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                                alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.ok_message),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                Method.loadingDialog.dismiss();
                                                activity.finish();
                                                startActivity(new Intent(activity, LoginActivity.class));


                                            }
                                        });

                                androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
            }
        });
    }



    @SuppressLint({"MissingPermission", "HardwareIds"})
    private String getDeviceId(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            deviceId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
           // Toast.makeText(LoginActivity.this,  deviceId, Toast.LENGTH_SHORT).show();

        }else {

            final TelephonyManager mTelephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);



            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
              //  Toast.makeText(LoginActivity.this,  deviceId, Toast.LENGTH_SHORT).show();
            }
        }

        return deviceId;

    }
}



