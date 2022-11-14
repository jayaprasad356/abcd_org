package abcdjob.workonline.com.qrcode.ui.settings;

import static abcdjob.workonline.com.qrcode.R.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import abcdjob.workonline.com.qrcode.databinding.ActivitySettingsBinding;
import abcdjob.workonline.com.qrcode.ui.ContactActivity;
import abcdjob.workonline.com.qrcode.ui.LoginActivity;
import abcdjob.workonline.com.qrcode.ui.ReferActivity;
import abcdjob.workonline.com.qrcode.ui.RefferActivity;
import abcdjob.workonline.com.qrcode.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.Util.Method;
import abcdjob.workonline.com.qrcode.ui.about_us.AboutUsActivity;
import abcdjob.workonline.com.qrcode.ui.home.HomeActivity;
import abcdjob.workonline.com.qrcode.ui.privacy_policy.PrivayPolicyActivity;
import abcdjob.workonline.com.qrcode.ui.tickets.TicketsActivity;


public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, PaymentResultWithDataListener {

    private ActivitySettingsBinding mBinding;
    private TextView username, logoutp, emailidt, totalreferals, referals;
    public  Method method;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, layout.activity_settings);
        method=new Method(this);
        logoutp = (TextView) findViewById(id.logoutp);
        username = (TextView) findViewById(id.usernameTextView);
        emailidt = (TextView) findViewById(id.emailidt);
       // totalreferals = (TextView) findViewById(R.id.totalreferals);
        referals = (TextView) findViewById(id.referals);
        username.setText(Method.userDTO.getName());
        emailidt.setText(Method.userDTO.getEmail());


        Method ads=new Method(SettingsActivity.this);


       /* if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")
                ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both"))
        {
            ads.callindustrial( SettingsActivity.this);
            // ads.callindustrial(HomeActivity.this);
        }*/
        initializeToolbar();
        loadSettings();
        Checkout.preload(getApplicationContext());
        /*SplashActivity mActivity = new SplashActivity();//make sure that you pass the appropriate arguments if you have an args constructor
        mActivity.LoadSettings();*/
        setListeners();

    }

    private void loadSettings() {
        mBinding.switchCompatPlaySound.setChecked(method.preferencess.getBooleanValue(GlobalVariables.PLAY_SOUND));
        mBinding.switchCompatVibrate.setChecked(method.preferencess.getBooleanValue(GlobalVariables.VIBRATE));
        mBinding.switchCompatSaveHistory.setChecked(method.preferencess.getBooleanValue(GlobalVariables.SAVE_HISTORY));
/*
        mBinding.switchCompatCopyToClipboard.setChecked(SharedPrefUtil.readBooleanDefaultTrue(PreferenceKey.COPY_TO_CLIPBOARD));
*/


    }

    private void setListeners() {
        mBinding.switchCompatPlaySound.setOnCheckedChangeListener(this);
        mBinding.switchCompatVibrate.setOnCheckedChangeListener(this);
        mBinding.switchCompatSaveHistory.setOnCheckedChangeListener(this);
       // mBinding.switchCompatCopyToClipboard.setOnCheckedChangeListener(this);
        mBinding.logoutp.setOnClickListener(this);

        mBinding.totalreferals.setOnClickListener(this);
        mBinding.referals.setOnClickListener(this);

   /*     mBinding.textViewPlaySound.setOnClickListener(this);
        mBinding.textViewVibrate.setOnClickListener(this);
        mBinding.textViewSaveHistory.setOnClickListener(this);
        mBinding.textViewCopyToClipboard.setOnClickListener(this);*/
    }

       private void initializeToolbar() {
        setSupportActionBar(mBinding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case id.switch_compat_play_sound:
                method.preferencess.setBooleanValue(GlobalVariables.PLAY_SOUND,isChecked);
                break;

            case id.switch_compat_vibrate:
                method.preferencess.setBooleanValue(GlobalVariables.VIBRATE,isChecked);
                break;

            case id.switch_compat_save_history:
                method.preferencess.setBooleanValue(GlobalVariables.SAVE_HISTORY,isChecked);

                break;

           /* case R.id.switch_compat_copy_to_clipboard:
                SharedPrefUtil.write(PreferenceKey.COPY_TO_CLIPBOARD, isChecked);
                break;*/


            default:
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case id.text_view_play_sound:
                mBinding.switchCompatPlaySound.setChecked(!mBinding.switchCompatPlaySound.isChecked());
                break;

            case id.logoutp:
                method.preferencess.clearAllPreferences();
                method.preferencess.clearAllPreferences();
                finish();
                startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                break;


            case id.referals:

                startActivity(new Intent(SettingsActivity.this, RefferActivity.class));
                break;


            case id.totalreferals:
              //  preferences.edit().clear().apply();
               // startPayment();
                startActivity(new Intent(SettingsActivity.this, ReferActivity.class));
                finish();
                break;

            case id.help:
                //  preferences.edit().clear().apply();
                // startPayment();
                startActivity(new Intent(SettingsActivity.this, TicketsActivity.class));
                finish();
                break;
/*
            case R.id.text_view_save_history:
                mBinding.switchCompatSaveHistory.setChecked(!mBinding.switchCompatSaveHistory.isChecked());
                break;

            case R.id.text_view_copy_to_clipboard:
                mBinding.switchCompatCopyToClipboard.setChecked(!mBinding.switchCompatCopyToClipboard.isChecked());
                break;
*/
            default:
                break;
        }
    }

    public void startAboutUsActivity(View view) {

        startActivity(new Intent(this, AboutUsActivity.class));
    }


    public void startcontactUsActivity(View view) {

        startActivity(new Intent(this, ContactActivity.class));
    }

    public void startTicketsActivity(View view) {

        startActivity(new Intent(this, TicketsActivity.class));
    }

    public void startPrivacyPolicyActivity(View view) {
        startActivity(new Intent(this, PrivayPolicyActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent( SettingsActivity.this, HomeActivity.class));
    }

    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "OkZio Pvt. Ltd");
            options.put("description", "Delivery Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSX-HXB4YTEstkMafwkvBn0RWD80rQAxrrp4Qy_82LhrYKGaHZ7cg");
            options.put("currency", "INR");

            String payment = "20";

            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);
            JSONObject preFill = new JSONObject();
            preFill.put("email", "rajxxxxxxxxxxxx@gmail.com");
            preFill.put("contact", "978xxxxxxx8");
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

        Toast.makeText(this, "oid"+paymentData.getOrderId()+"pid"+paymentData.getPaymentId()+"user contact" +
                paymentData.getUserContact()+"user email"+paymentData.getUserEmail()  , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

    }




}