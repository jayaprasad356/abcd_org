package abcdjob.workonline.com.qrcode.ui.splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.onesignal.OSSubscriptionObserver;
import com.onesignal.OSSubscriptionStateChanges;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import abcdjob.workonline.com.qrcode.Interface.Helper;
import abcdjob.workonline.com.qrcode.Models.Settings;
import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.Util.Method;
import abcdjob.workonline.com.qrcode.Util.RestAPI;

import abcdjob.workonline.com.qrcode.databinding.ActivitySplashBinding;
import abcdjob.workonline.com.qrcode.https.HttpsRequest;
import abcdjob.workonline.com.qrcode.ui.LoginActivity;
import abcdjob.workonline.com.qrcode.ui.home.HomeActivity;

@RequiresApi(api = 33)
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity implements OSSubscriptionObserver {
    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1003;
    ActivitySplashBinding binding;

    private final int SPLASH_DELAY = 1500;

    private SharedPreferences.Editor editor;
    private Method method;
    private Settings settingsDTO;


    private final String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE};
    public boolean accessNetState;
    private final Handler handler = new Handler();

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        method=new Method(SplashActivity.this);

        getWindow().setBackgroundDrawable(null);


       // getAllData(SplashActivity.this);
        animateLogo();
        OneSignal.initWithContext(this);
        FirebaseMessaging.getInstance().subscribeToTopic("customer")
                .addOnCompleteListener(task -> {
                    // Log.d(GlobalVariables.TAG, "onCreate: "+n);

                });


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(GlobalVariables.TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d(GlobalVariables.TAG, token);

                        //  Toast.makeText(SplashActivity.this,"HELLO"+ msg, Toast.LENGTH_SHORT).show();
                        method.preferencess.setValue(GlobalVariables.DEVICE_TOKEN,token);


                   /*     Toast.makeText(SplashActivity.this,"global Device Token"+
                                preferences.getString(GlobalVariables.DEVICE_TOKEN,""), Toast.LENGTH_SHORT).show();*/
                    }
                });





        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId("a55b3068-3d26-4b9c-9e82-ec1e09e0859f");
        OneSignal.addSubscriptionObserver(this);

        OneSignal.getDeviceState();
        OneSignal.disablePush(false);
        if (NotificationManagerCompat.from(SplashActivity.this).areNotificationsEnabled()) {
            //Do your Work
        } else {
            //Ask for permission
            PermissionListener permissionlistener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {

                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {
                    finish();
                }

                /**
                 * Method called whenever a requested permission has been granted
                 *
                 * @param response A response object that contains the permission that has been requested and
                 *                 any additional flags relevant to this response
                 */


            };
        }
    }

    @Override
    protected void onResume () {
        super.onResume();
           /* if (!hasPermissions(SplashActivity.this, permissions)) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            } else {

                goToMainPage();

            }*/
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            try {

                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;


                method.preferencess.setBooleanValue(GlobalVariables.CAMERA_ACCEPTED,cameraAccepted);


                boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                method.preferencess.setBooleanValue(GlobalVariables.STORAGE_ACCEPTED,storageAccepted);



                accessNetState = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                //  preferences.setBooleanValue(GlobalVariables.MODIFY_AUDIO_ACCEPTED, accessNetState);

                method.preferencess.setBooleanValue(GlobalVariables.MODIFY_AUDIO_ACCEPTED,accessNetState);

                goToMainPage();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Intentscreen(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(!method.preferencess.getBooleanValue(String.valueOf(GlobalVariables.USER_IS_LOGIN))){
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                    SplashActivity.this.finish();
                }else{
                    Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(mainIntent);
                    SplashActivity.this.finish();
                }


            }
        }, SPLASH_DELAY);
    }

    @Override
    public void onOSSubscriptionChanged(OSSubscriptionStateChanges stateChanges) {
        if (!stateChanges.getFrom().isSubscribed() &&
                stateChanges.getTo().isSubscribed()) {
          /*  new AlertDialog.Builder(this)
                    .setMessage("You've successfully subscribed to push notifications!")
                    .show();*/

            stateChanges.getTo().getUserId();
        }

        Log.i("Debug", "onOSSubscriptionChanged: " + stateChanges);
    }

    public void getAllData(Activity activity) {
        method.params.clear();
        method.params.put("device_id",method.getDeviceId(getApplicationContext()));
        Log.d(GlobalVariables.TAG, "getHomeData2: called"+activity.toString());
        new HttpsRequest(RestAPI.API_Settings, method.params, activity).stringPost2(GlobalVariables.TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, JSONObject abcdapp, String title, String message, JSONObject response) {

                if (flag) {
                    // binding.tvNo.setVisibility(View.GONE);
                    try {
                        //   Log.d(GlobalVariables.TAG, "hKINGSN123:" + response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").toString());
                        Settings settings= new Gson().fromJson(response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").getJSONObject("Settings").toString(), (Type) Settings.class);
//                       settings = new Gson().fromJson(response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").toString(), (Type) UserDTO.class);
                        method.preferencess.setSettings(settings, String.valueOf(settings));
                        GlobalVariables.settings=settings;

                        if (!hasPermissions(SplashActivity.this, permissions)) {
                            ActivityCompat.requestPermissions(SplashActivity.this, permissions, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                        } else {
                            // callinappupdate();
                            //  handler.postDelayed(mTask, 3000);

                            goToMainPage();
                        }
                        //goToMainPage();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SplashActivity.this);
                    try {
                        alertDialogBuilder.setTitle(response.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    alertDialogBuilder.setMessage(message);
                    alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                    alertDialogBuilder.setPositiveButton(SplashActivity.this.getResources().getString(R.string.ok_message),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                    finish();

                                    //Log.d("Response",msg);
                                    finishAffinity();

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });
    }

    private void goToMainPage() {



        if (method.preferencess.getValue(GlobalVariables.USER_MOBILE).equals("")){
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }, SPLASH_DELAY);

        }else{

            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            }, SPLASH_DELAY);
        }


    }

    /**
     * This method animates the logo
     */
    private void animateLogo() {
        binding.imageViewLogo.setVisibility(View.VISIBLE);
        // image_view.setVisibility(View.INVISIBLE);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_without_duration);
        fadeInAnimation.setDuration(SPLASH_DELAY);

        binding.imageViewLogo.startAnimation(fadeInAnimation);
        getAllData(SplashActivity.this);
    }

    private void animateback() {
        binding.imageViewLogo.setVisibility(View.INVISIBLE);
        binding.imageViewLogo.setVisibility(View.VISIBLE);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_without_duration);
        fadeInAnimation.setDuration(SPLASH_DELAY);

        binding.imageViewLogo.startAnimation(fadeInAnimation);
        //animateLogo();
        new Handler().postDelayed(this::animateLogo, 2000);
    }





}