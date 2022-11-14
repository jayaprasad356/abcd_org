package abcdjob.workonline.com.qrcode.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.dmoral.toasty.Toasty;
import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.Util.Method;
import abcdjob.workonline.com.qrcode.databinding.ActivityRefferBinding;
import abcdjob.workonline.com.qrcode.preferences.SharedPrefrence;
import abcdjob.workonline.com.qrcode.ui.adapter.ReferAdapter;
import abcdjob.workonline.com.qrcode.ui.adapter.Refer_Data;


public class RefferActivity extends AppCompatActivity {


    public ActivityRefferBinding binding;
    public SharedPrefrence preferencess;
    public Method method;
    RecyclerView refer_recycler;
    ReferAdapter referAdapter;
    private List<Refer_Data> refer_data;
    String url,test,url2;
    String ReferCode ;
    Activity activity;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = RefferActivity.this;
        method = new Method(activity);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reffer);

        method = new Method(this);

        binding.toolbar.setNavigationOnClickListener(view -> {
            super.onBackPressed();
        });
        binding.referTxt.setText(Method.userDTO.getUserReferalCode());
        binding.referbelowTxt.setText(Method.settings.getPerreferTxt());
        binding.others.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                //method.shareApp(ReferAndEarnActivity.this);


                String shareBody1 = "https://play.google.com/store/apps/details?id=" + getPackageName();
                String shareBody= Html.fromHtml(Method.settings.getAppReferTxt(), Html.FROM_HTML_MODE_COMPACT)+""+" you can also Download App from below link and enter referral code while login-"+ "\n" +Method.userDTO.getUserReferalCode()+ " \n" + shareBody1;

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
               /* String shareBody = "Hey, \nDownload the " +
                        preferences.getString(GlobalVariables.APP_NAME, "") +
                        ".The best earning earnonline.\nJoin using my referral code to get bonus\n" +
                        "My referral code is  " +
                        preferences.getString(GlobalVariables.USER_REFERAL_CODE, "") + "\n" +
                        "https://play.google.com/store/apps/details?id=" + getPackageName();*/
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        binding.whatsbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String shareBody1 = "https://play.google.com/store/apps/details?id=" + getPackageName();
                // String shareBody= "I have earned cash using "+GlobalVariables.settings.getAppName()+" earnonline.you can also earn by downloading earnonline from below link and enter referral code while login-"+ "\n" +GlobalVariables.profileuser.getSponsorid()+ " \n" + shareBody1;

                String shareBody= Html.fromHtml(Method.settings.getAppReferTxt(), Html.FROM_HTML_MODE_COMPACT)+""+" .you can also Download App from below link and enter referral code while login-"+ "\n" +Method.userDTO.getUserReferalCode()+ " \n" + shareBody1;


               /* String shareBody = "Hey, \nDownload the " +
                        preferences.getString(GlobalVariables.APP_NAME, "") +
                        ".The best earning earnonline.\nJoin using my referral code to get bonus\n" +
                        "My referral code is  " +
                        preferences.getString(GlobalVariables.USER_REFERAL_CODE, "") + "\n" +
                        "https://play.google.com/store/apps/details?id=" + getPackageName();*/
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                if (sendIntent != null) {
                    startActivity(Intent.createChooser(sendIntent, "Share via Whatsapp"));
                    startActivity(sendIntent);
                } else {

                    Toasty.error(activity, "Whatsapp is not installed", Toast.LENGTH_SHORT).show();
                }

            }

        });

        binding.telegramBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String shareBody1 = "https://play.google.com/store/apps/details?id=" + getPackageName();
                String shareBody= Html.fromHtml(Method.settings.getAppReferTxt(), Html.FROM_HTML_MODE_COMPACT)+""+" you can also Download App from below link and enter referral code while login-"+ "\n" +Method.userDTO.getUserReferalCode()+ " \n" + shareBody1;

               /* String shareBody = "Hey, \nDownload the " +
                        preferences.getString(GlobalVariables.APP_NAME, "") +
                        ".The best earning earnonline.\nJoin using my referral code to get bonus\n" +
                        "My referral code is  " +
                        preferences.getString(GlobalVariables.USER_REFERAL_CODE, "") + "\n" +
                        "https://play.google.com/store/apps/details?id=" + getPackageName();*/
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("org.telegram.messenger");
                if (sendIntent != null) {
                    startActivity(Intent.createChooser(sendIntent, "Share via Telegram"));
                    startActivity(sendIntent);
                } else {

                    Toasty.error(activity, "Telegram App is not installed", Toast.LENGTH_SHORT).show();
                }

            }

        });

        binding.copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                method.copyToclipboard(activity,binding.referTxt);
            }
        });


    }
}
