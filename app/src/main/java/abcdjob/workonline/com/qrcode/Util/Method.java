package abcdjob.workonline.com.qrcode.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.RewardedVideoAd;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.razorpay.Checkout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import abcdjob.workonline.com.qrcode.databinding.ActivityHomeBinding;
import abcdjob.workonline.com.qrcode.ui.generate.GenerateFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import abcdjob.workonline.com.qrcode.BuildConfig;
import abcdjob.workonline.com.qrcode.Interface.Helper;
import abcdjob.workonline.com.qrcode.Models.Settings;
import abcdjob.workonline.com.qrcode.Models.UserDTO;
import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.https.HttpsRequest;
import abcdjob.workonline.com.qrcode.preferences.SharedPrefrence;
import abcdjob.workonline.com.qrcode.ui.CommentOneByOne;
import abcdjob.workonline.com.qrcode.ui.ContactActivity;
import abcdjob.workonline.com.qrcode.ui.ForgotActivity;
import abcdjob.workonline.com.qrcode.ui.Interface.InterstitialAdView;
import abcdjob.workonline.com.qrcode.ui.Interface.VideoAds;
import abcdjob.workonline.com.qrcode.ui.RegisterActivity;
import abcdjob.workonline.com.qrcode.ui.TermsConditionActivity;
import abcdjob.workonline.com.qrcode.ui.generatedcode.GeneratedCodeActivity;
import abcdjob.workonline.com.qrcode.ui.home.HomeActivity;
import es.dmoral.toasty.Toasty;
import abcdjob.workonline.com.qrcode.ui.privacy_policy.PrivayPolicyActivity;
import abcdjob.workonline.com.qrcode.ui.tickets.TicketsActivity;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CLIPBOARD_SERVICE;
import static abcdjob.workonline.com.qrcode.ui.home.HomeActivity.method;


public class Method {
    public static ActivityHomeBinding mBinding;
    private Constant Constant;
    public SharedPrefrence preferencess;
    private InterstitialAdView interstitialAdView;
    private Class<? extends Method> activity;
    private VideoAds videoAd;
    public static boolean share = false, loginBack = false, allowPermitionExternalStorage = false, personalization_ad = false;
    private final Context _context;
    private DBHelper dbHelper;
    String TAG = "KINGSN";
    private com.facebook.ads.InterstitialAd interstitialfbAd;
    InterstitialAd mInterstitialAd;
    private RewardedVideoAd rewardedVideoAd;
    com.facebook.ads.AdView adView;
    com.google.android.gms.ads.AdView mAdView;
    private GeneratedCodeActivity GeneratedCodeActivity;
    public HashMap<String, String> params = new HashMap<>();
    public static Dialog loadingDialog;

    public static UserDTO userDTO;
    public static Settings settings;
    TimerTask timerTask;
    Timer timer;
    Double time = 0.0;
    private int qrtime = 0;
    private int qrtime1 = 0;
    boolean timerStarted = false;
    public String url;
    public String test;
    public String url2;
    public String checksum;
    public String orderId;
    public String mobile;
    public static String finalOrderId;
    public static final String WEBSITE = "DEFAULT";
    public static final String INDUSTRY_TYPE_ID = "Retail";
    public static final String CALLBACK_URL = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
    final int UPI_PAYMENT = 0;
    public static String type = "";
    public static String CURRENT_TAG = GlobalVariables.TAG_MAIN;
    private static ProgressDialog mProgressDialog;
    LayoutInflater layoutInflater;

    @SuppressLint("CommitPrefEdits")
    public Method(Context context) {
        this._context = context;
        //checkInternetConnection();
        preferencess = SharedPrefrence.getInstance(context);
        userDTO = preferencess.getParentUser2(GlobalVariables.USER_DTO);
        settings = preferencess.getSettings(GlobalVariables.SettingsDto);
        Log.d(TAG, "Method: " + userDTO.getEmail() + userDTO.getMobile());
        finalOrderId = "";
        Random r = new Random(System.currentTimeMillis());
        orderId = "QRCODE" + (1 + r.nextInt(2)) * 1000
                + r.nextInt(1000);
    }

    public void showFbBanner(Activity activity, FrameLayout FrameLayout) {

        adView = new com.facebook.ads.AdView(activity, settings.getFbBanner1(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container

        // Add the ad view to your activity layout
        (FrameLayout).addView(adView);

        // Request an ad
        adView.loadAd();
    }


    public boolean isOnline(Activity activity) {
        ConnectivityManager conMgr = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            Toast.makeText(activity, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static void privacyPolicyMsg(TextView tvPrivacy, Activity activity) {
        tvPrivacy.setClickable(true);
        tvPrivacy.setMovementMethod(LinkMovementMethod.getInstance());

        String message = activity.getString(R.string.term_privacy);
        String s2 = activity.getString(R.string.terms);
        String s1 = activity.getString(R.string.privacy_policy);
        final Spannable wordToSpan = new SpannableString(message);

        wordToSpan.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, TermsConditionActivity.class);
                intent.putExtra("type", "privacy");
                activity.startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                ds.isUnderlineText();
            }
        }, message.indexOf(s1), message.indexOf(s1) + s1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordToSpan.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, PrivayPolicyActivity.class);
                intent.putExtra("type", "terms");
                activity.startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                ds.isUnderlineText();
            }
        }, message.indexOf(s2), message.indexOf(s2) + s2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPrivacy.setText(wordToSpan);
    }

    //---------------Banner Ad---------------//
    private void showPersonalizedAds(FrameLayout linearLayout) {

        if (settings.getBannerAdd().equals("addmob")) {
            AdView adView = new AdView(_context);
            AdRequest adRequest = new AdRequest.Builder()
                    .build();
            adView.setAdUnitId(settings.getAddmobBanner1());
            adView.setAdSize(AdSize.SMART_BANNER);
            linearLayout.addView(adView);
            adView.loadAd(adRequest);
        }
    }

    private void showNonPersonalizedAds(FrameLayout linearLayout) {
        Bundle extras = new Bundle();
        extras.putString("npa", "1");
        if (settings.equals("addmob")) {
            AdView adView = new AdView(_context);
            AdRequest adRequest = new AdRequest.Builder()
                    .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                    .build();
            adView.setAdUnitId(settings.getAddmobBanner1());
            adView.setAdSize(AdSize.SMART_BANNER);
            linearLayout.addView(adView);
            adView.loadAd(adRequest);
        }
    }

    public void showBannerAd(FrameLayout linearLayout) {
        /*    Log.d("Response-ads", Boolean.toString(Constant.settings.isBanner_ad()));*/
        if (ConsentInformation.getInstance(_context).getConsentStatus() == ConsentStatus.NON_PERSONALIZED) {
            showNonPersonalizedAds(linearLayout);
        } else {
            showPersonalizedAds(linearLayout);
        }
    }
    //---------------Banner Ad---------------//

    //---------------Addmob Rewarded video ad---------------//

/*    public void showaddmobVideoAd(final Activity activity, final String uid, final String Device) {
        if (settings.getReward_add().equals("addmob")) {
           // Video_ads_limit_count(activity,uid,Device);
            if (abcdjob.V1infotech.com.Qrcode.ui.Util.Constant.VIDEO_AD_COUNT <= Integer.parseInt(settings.getReward_frequency())) {
                final ProgressDialog progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage(activity.getString(R.string.adsloading));
                progressDialog.setCancelable(false);
                progressDialog.show();
                mRewardedVideoAd = new RewardedVideoAd(activity, abcdjob.V1infotech.com.Qrcode.ui.Util.Constant.settings.getAddmob_rewarded1());
                if (settings != null) {
                    if (settings.getReward_add().equals("addmob")) {
                        if (mRewardedVideoAd != null) {
                            AdRequest adRequest;
                            if (ConsentInformation.getInstance(activity).getConsentStatus() == ConsentStatus.PERSONALIZED) {
                                adRequest = new AdRequest.Builder().build();
                            } else {
                                Bundle extras = new Bundle();
                                extras.putString("npa", "1");
                                adRequest = new AdRequest.Builder()
                                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                                        .build();
                            }

                            mRewardedVideoAd.loadAd(settings.getAddmob_rewarded1(), adRequest);
                            mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
                                @Override
                                public void onRewarded(RewardItem reward) {
                                    Log.d("reward_video_ad", "reward");

                                   *//* Toast.makeText(activity, "Reward Point Added", Toast.LENGTH_SHORT).show();
                                    BalanceUpdate(uid,"Video Ads",Constant.settings.getVideo_add_point(),Constant.PublicIP,1);
                                    Ads_Count_Update(uid,Device);
                                    Video_ads_limit_count(activity,uid,Device);*//*
                                }

                                @Override
                                public void onRewardedVideoAdLeftApplication() {
                                    Log.d("reward_video_ad", "AdLeftApplication");
                                }

                                @Override
                                public void onRewardedVideoAdFailedToLoad(int i) {
                                    progressDialog.dismiss();
                                    Toast.makeText(activity, "Video Ads load Failed", Toast.LENGTH_SHORT).show();
                                    Log.d("reward_video_ad", "Failed");
                                }

                                @Override
                                public void onRewardedVideoAdClosed() {
                                    Events.VideoAdsReload adsReload = new Events.VideoAdsReload("100");
                                    EventBus.getDefault().post(adsReload);
                                    Log.d("reward_video_ad", "close");


                                }

                                @Override
                                public void onRewardedVideoAdLoaded() {
                                    //  mRewardedVideoAd.show();
                                    progressDialog.dismiss();
                                    Toast.makeText(activity, "Video Ads load", Toast.LENGTH_SHORT).show();
                                    Log.d("reward_video_ad", "Video Ads load");
                                }

                                @Override
                                public void onRewardedVideoAdOpened() {
                                    Log.d("reward_video_ad", "open");
                                }

                                @Override
                                public void onRewardedVideoStarted() {
                                    Log.d("reward_video_ad", "start");
                                }

                                @Override
                                public void onRewardedVideoCompleted() {
                                    Log.d("reward_video_ad", "completed");

                                }
                            });
                        }
                    } else {
                        progressDialog.dismiss();

                    }
                } else {
                    progressDialog.dismiss();
                }
            }else{ Toast.makeText(activity, "Daily Ads Limit Over", Toast.LENGTH_SHORT).show();}
        }else{ Toast.makeText(activity, "Daily Ads Limit Over", Toast.LENGTH_SHORT).show();}
    }*/

    //---------------End Of Addmob Rewarded video ad---------------//

    //---------------Interstitial Ad---------------//

    public void loadInter(Activity activity) {
        //Log.d(TAG, "loadInter: "+settings.getIndustrial_add());
        Log.d(TAG, "loadInter: " + settings.getCallIndustrialOn());
        if (settings.getCallIndustrialOn().equals("addmob")) {
            abcdjob.workonline.com.qrcode.Util.Constant.AD_COUNT = abcdjob.workonline.com.qrcode.Util.Constant.AD_COUNT + 1;
            if (abcdjob.workonline.com.qrcode.Util.Constant.AD_COUNT == Integer.parseInt(settings.getRewardFrequency())) {
                abcdjob.workonline.com.qrcode.Util.Constant.AD_COUNT = 0;
                AdRequest adRequest;
                if (ConsentInformation.getInstance(activity).getConsentStatus() == ConsentStatus.PERSONALIZED) {
                    adRequest = new AdRequest.Builder().build();
                } else {
                    Bundle extras = new Bundle();
                    extras.putString("npa", "1");
                    adRequest = new AdRequest.Builder()
                            .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                            .build();
                }

            }
        }
    }

    public void createaddmobInterstitialAd(Activity context, String adUnit) {
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context, adUnit, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;


                        Log.e("KINGSN", "Addcalling activity" + GlobalVariables.adModelCount.getGenerate_clicked());

                        if ((GlobalVariables.adModelCount.getGenerate_clicked() == "GeneratedCodeActivity")) {
                            Log.e("KINGSN", "insertwallet:called ");
                            GeneratedCodeActivity generatedCodeActivity = new GeneratedCodeActivity();
                            // generatedCodeActivity.insertwallet();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent();
                                    intent.setClass(context, HomeActivity.class);
                                    context.startActivity(intent);
                                    GlobalVariables.adModelCount.setGenerate_clicked("0");
                                }
                            }, 500);
                            //Toast.makeText(GeneratedCodeActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                        }


                        ///

                    }

                });
    }


    //---------------AddMob Interstitial Ad---------------//

    public void showaddmobInterstitialAd(Activity activity) {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(activity);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
            createaddmobInterstitialAd(activity, settings.getAddmobIndustrial1());

            Log.d("TAG", "The interstitial wasn't loaded yet.");

        }

        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);
                ifGeneratedCodeActivity(activity);

            }

            @Override
            public void onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent();
                createaddmobInterstitialAd(activity, settings.getAddmobIndustrial1());

            }

            @Override
            public void onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });


    }


    //---------------End Of Interstitial Ad---------------//

  /*  public void Video_ads_limit_count(final Activity activity, final String uid, final String Device){
    String login = RestAPI.API_Video_Ads_Count + "&user_id=" + uid + "&device_id=" + Device;
    AsyncHttpClient client = new AsyncHttpClient();
    client.get(login, null, new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Log.d("Response", new String(responseBody));
            String res = new String(responseBody);
            try {
                JSONObject jsonObject = new JSONObject(res);
                JSONArray jsonArray = jsonObject.getJSONArray(AppSid);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String success = object.getString("success");
                    if (success.equals("1")) {
                        int  Counter = Integer.parseInt(object.getString("ads_count"));
                        Constant.VIDEO_AD_COUNT = Counter;

                     *//*   int CountLimit= Integer.parseInt(Constant.settings.getDaily_spin_limit());*//*

                    }
                    else {

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    });
}

    public void Ads_Count_Update(final String uid, final String Device) {
        String login = RestAPI.API_Video_Ads_Count_update + "&user_id=" + uid + "&device_id=" + Device;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(login, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("Response-count", new String(responseBody));
                String res = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray jsonArray = jsonObject.getJSONArray(AppSid);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String success = object.getString("success");

                        if (success.equals("1")) {
                        } else {
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }*/


    //---------------Facebook Interstitial Ad---------------//

    public void showFacdebookindustrialAd(Activity activity1) {
        AudienceNetworkAds.initialize(activity1);
        interstitialfbAd = new com.facebook.ads.InterstitialAd(activity1, settings.getFbIndustrial1());
        // Create listeners for the Interstitial Ad
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                //Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                //Log.e(TAG, "Interstitial ad dismissed.");

                //activity1.onTopResumedActivityChanged(true);
                /*if ((GlobalVariables.adModelCount.getGenerate_clicked() == "true")) {

                        GenerateFragment generateFragment=new GenerateFragment();
                        //editor.putBoolean(String.valueOf(GlobalVariables.generate_clicked),false);
                    GlobalVariables.adModelCount = new AdModelCount("true","true","true","false");

                    editor.putString(GlobalVariables.generate_clicked, "false");

                        editor.apply();
                        generateFragment.generateCode();

                } else {
                   // generateFragment.generateCode();
                    GlobalVariables.adModelCount = new AdModelCount("true","true","true","false");
                }*/
                Log.e("KINGSN", "Addcalling activity" + GlobalVariables.adModelCount.getGenerate_clicked());

                if ((GlobalVariables.adModelCount.getGenerate_clicked().equals("GeneratedCodeActivity"))) {
                    Log.e("KINGSN", "insertwallet:called ");
                    GeneratedCodeActivity generatedCodeActivity = new GeneratedCodeActivity();
                    // generatedCodeActivity.insertwallet();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent();
                            intent.setClass(activity1, HomeActivity.class);
                            activity1.startActivity(intent);
                            GlobalVariables.adModelCount.setGenerate_clicked("0");
                        }
                    }, 1000);
                    //Toast.makeText(GeneratedCodeActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                //Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                Log.e("KINGSN", "Addcalling activity" + GlobalVariables.adModelCount.getGenerate_clicked());

                ifGeneratedCodeActivity(activity1);

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                //Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialfbAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                //Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                //Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialfbAd.loadAd(
                interstitialfbAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

    //---------------End of Facebook Interstitial Ad---------------//

    //---------------Facebook Reward Interstitial Ad---------------//

    public void showFacebookVideoAd(Context context) {
        AudienceNetworkAds.initialize(context);
        rewardedVideoAd = new RewardedVideoAd(context, settings.getFbReward1());
        com.facebook.ads.RewardedVideoAdListener rewardedVideoAdListener = new com.facebook.ads.RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, com.facebook.ads.AdError error) {
                // Rewarded video ad failed to load
                Log.e(TAG, "Rewarded video ad failed to load: " + error.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Rewarded video ad is loaded and ready to be displayed
                Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!");
                //GlobalVariables.adClickModel=new AdClickModel("0","1");
                rewardedVideoAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Rewarded video ad clicked
                Log.d(TAG, "Rewarded video ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Rewarded Video ad impression - the event will fire when the
                // video starts playing
                Log.d(TAG, "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {
                // Rewarded Video View Complete - the video has been played to the end.
                // You can use this event to initialize your reward
                Log.d(TAG, "Rewarded video completed!");

                // Call method to give reward
                // giveReward();
            }

            @Override
            public void onRewardedVideoClosed() {
                // The Rewarded Video ad was closed - this can occur during the video
                // by closing the app, or closing the end card.
                Log.d(TAG, "Rewarded video ad closed!");
            }
        };
        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());
    }

    //--------------End Of Facebook Reward Interstitial Ad---------------//

    public void callindustrial(Activity activity) {
        if (!settings.getCallIndustrialOn().equalsIgnoreCase("false")) {
            if (settings.getCallIndustrialOn().equalsIgnoreCase("everytime")) {
                callfinalindus(activity);

            } else if (settings.getCallIndustrialOn().equalsIgnoreCase("on_wrong_code")) {
                callfinalindus(activity);
            } else if (settings.getCallIndustrialOn().equalsIgnoreCase("onscreen_opening")) {
                callfinalindus(activity);
            } else if (settings.getCallIndustrialOn().equalsIgnoreCase("both")) {
                callfinalindus(activity);
            }


        }
    }

    public void callfinalindus(Activity activity) {
        Log.d(TAG, "callfinalindusfff: " + settings.getCallIndustrialOn().toLowerCase());
        if (settings.getIndustrialAdd().equalsIgnoreCase("addmob")) {
            showaddmobInterstitialAd(activity);

        } else if (settings.getIndustrialAdd().equalsIgnoreCase("facebook")) {
            showFacdebookindustrialAd(activity);
        } else if (settings.getIndustrialAdd().equalsIgnoreCase("both")) {
            Log.e("KINGSN", "callfinalindus:BOTH " + GlobalVariables.adModelCount.getInterstitialadd());
            if (GlobalVariables.adModelCount.getInterstitialadd().equals("0")) {
                // GlobalVariables.adModelCount = new AdModelCount("1","0","0","0");
                showaddmobInterstitialAd(activity);
                GlobalVariables.adModelCount.setInterstitialadd("1");
            } else if (GlobalVariables.adModelCount.getInterstitialadd().equals("1")) {
                // GlobalVariables.adModelCount = new AdModelCount("0","0","0","0");
                GlobalVariables.adModelCount.setInterstitialadd("0");
                showFacdebookindustrialAd(activity);
            }
            Log.e("KINGSN", "callfinalindus: " + GlobalVariables.adModelCount.getInterstitialadd());
        } else if (settings.getIndustrialAdd().equalsIgnoreCase("false")) {
            if (activity == GeneratedCodeActivity) {

            }
        }
    }

    public void ifGeneratedCodeActivity(Activity activity) {

        if ((Objects.equals(GlobalVariables.adModelCount.getGenerate_clicked(), "GeneratedCodeActivity"))) {
            Log.e("KINGSN", "insertwallet:called ");
            GeneratedCodeActivity generatedCodeActivity = new GeneratedCodeActivity();
            // generatedCodeActivity.insertwallet();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.setClass(activity, HomeActivity.class);
                    activity.startActivity(intent);
                    GlobalVariables.adModelCount.setGenerate_clicked("0");
                }
            }, 500);
            //Toast.makeText(GeneratedCodeActivity.this, "Updated", Toast.LENGTH_SHORT).show();

        }

    }

    public void showToasty(Activity activity, String Type, String Message) {
        if (Type.equals("1")) {
            Toasty.success(activity, "" + Message, Toast.LENGTH_SHORT).show();
        } else {
            Toasty.error(activity, "" + Message, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("HardwareIds")
    public String getDeviceId(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            deviceId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            // Toast.makeText(context,  deviceId, Toast.LENGTH_SHORT).show();

        } else {

            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);


            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                //  Toast.makeText(LoginActivity.this,  deviceId, Toast.LENGTH_SHORT).show();
            }
        }

        return deviceId;

    }

    @SuppressLint("SimpleDateFormat")
    public static String convertTimestampDateToTime(String timestamp) {
        if (!timestamp.equals("")) {
            Timestamp tStamp = new Timestamp(Long.parseLong(correctTimestamp(timestamp)));
            SimpleDateFormat simpleDateFormat;
            simpleDateFormat = new SimpleDateFormat("dd MMM yyyy | hh:mm a");
            return simpleDateFormat.format(tStamp);
        } else {
            return "";
        }
    }

    public static String convertTimestampDate(long timestamp) {
        Timestamp tStamp = new Timestamp(timestamp);
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("MMM dd");
        return simpleDateFormat.format(tStamp);
    }


    public static String getFirstLetterCapital(String input) {
        String val = "";
        try {
            val = Character.toUpperCase(input.charAt(0)) + input.substring(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }

    public static String correctTimestamp(String timestampInMessage) {
        if (!timestampInMessage.equals("")) {
            long correctedTimestamp = Long.parseLong(timestampInMessage);

            if (timestampInMessage.length() < 13) {

                int difference = 13 - timestampInMessage.length(), i;
                String differenceValue = "1";
                for (i = 0; i < difference; i++) {
                    differenceValue += "0";
                }
                correctedTimestamp = (Long.parseLong(timestampInMessage) * Integer.parseInt(differenceValue))
                        + (System.currentTimeMillis() % (Integer.parseInt(differenceValue)));
            }
            return String.valueOf(correctedTimestamp);
        } else {
            return "";
        }


    }

    public void loadingDialogg(Activity activity) {
        loadingDialog = new Dialog(activity);
        loadingDialog.setContentView(R.layout.lotiee_loading);
        loadingDialog.setCancelable(false);
        Objects.requireNonNull(loadingDialog.getWindow()).setBackgroundDrawableResource(
                R.color.transparent);
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        } else {
            loadingDialog = new Dialog(activity);
            loadingDialog.setContentView(R.layout.lotiee_loading);
            loadingDialog.setCancelable(false);
            Objects.requireNonNull(loadingDialog.getWindow()).setBackgroundDrawableResource(
                    R.color.transparent);
            loadingDialog.show();
        }
    }

    public void startTimer2(Activity activity, TextView mtext) {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        mtext.setText(getTimerText());
                        timerStarted = true;

                    }
                });
            }

        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);


    }

    private String getTimerText() {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        qrtime = seconds;
        qrtime1 = minutes * 60 + qrtime;

        Log.e("KINGSN", "getTimerText:sec" + qrtime);
        Log.d(TAG, "getTimerText: " + qrtime1);
        return formatTime(seconds, minutes, hours);
    }

    @SuppressLint("DefaultLocale")
    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);

    }

    public void alert(Activity activity) {

        if (userDTO.getAllow().equals("2")) {
            Toast.makeText(activity, "Your account is blocked", Toast.LENGTH_SHORT).show();
            //finish();
            //return;

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            ViewGroup viewGroup = activity.findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(activity).inflate(R.layout.customdialogup, viewGroup, false);
            TextView text = dialogView.findViewById(R.id.text);
            TextView text2 = dialogView.findViewById(R.id.cssub);
            text.setText("Contact Admin");
            text2.setText("You Are Blocked !");
            ImageView image = dialogView.findViewById(R.id.image);
            image.setImageResource(R.drawable.mainl);
            Button appUpdate = dialogView.findViewById(R.id.dialogButtonOK);
            appUpdate.setText("Contact Now");
            builder.setView(dialogView);
            builder.setCancelable(false);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            appUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    activity.startActivity(new Intent(activity, ContactActivity
                            .class));
                    activity.finish();

                }
            });

        } else if (userDTO.getJoiningPaid().equals("1")) {
            //Toast.makeText(HomeActivity.this, "Your account is blocked", Toast.LENGTH_SHORT).show();
            //finish();
            //return;

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            ViewGroup viewGroup = activity.findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(activity).inflate(R.layout.customdialogup2, viewGroup, false);
            TextView text = dialogView.findViewById(R.id.cssub2);
            TextView text2 = dialogView.findViewById(R.id.cssub3);
            text.setText("One Step Away To Your Account");
            text2.setText(" Buy DataBase To  \n" +
                    " Unlock Your Dashboard");
            ImageView image = dialogView.findViewById(R.id.image);
            image.setImageResource(R.drawable.paym);
            Button appPay = dialogView.findViewById(R.id.paybtn);
            Button appPay3 = dialogView.findViewById(R.id.paybtn3);
            appPay.setText("Pay Now");
            appPay3.setText("Pay By Cash");
            appPay3.setVisibility(View.VISIBLE);
            builder.setView(dialogView);
            builder.setCancelable(false);
            final AlertDialog alertDialog = builder.create();

            appPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                                        /*startActivity(new Intent(HomeActivity.this, SettingsActivity
                                                .class));*/
                    loadingDialogg(activity);
                    // GenerateChecksum();
                    startPayment(activity);
                    // finish();


                }
            });

            appPay3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();


                    onSuccessPay(activity, orderId);
                    // finish();

                }
            });

            alertDialog.show();

        } else if (userDTO.getJoiningPaid().equals("3")) {
            //Toast.makeText(HomeActivity.this, "Your account is blocked", Toast.LENGTH_SHORT).show();
            //finish();
            //return;

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            ViewGroup viewGroup = activity.findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(activity).inflate(R.layout.customdialogup2, viewGroup, false);
            TextView text = dialogView.findViewById(R.id.cssub2);
            TextView text2 = dialogView.findViewById(R.id.cssub3);
            text.setText("Verification Pending ");
            text2.setText(" Plese Wait For The Approval !");
            ImageView image = dialogView.findViewById(R.id.image);
            image.setImageResource(R.drawable.paym);
            Button appPay = dialogView.findViewById(R.id.paybtn);
            Button appPay2 = dialogView.findViewById(R.id.paybtn2);
            appPay2.setVisibility(View.VISIBLE);
            appPay.setText("Contact us");
            appPay2.setText("Exit");
            builder.setView(dialogView);
            builder.setCancelable(false);
            final AlertDialog alertDialog = builder.create();

            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            alertDialog.show();

            appPay2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    alertDialog.cancel();
                                        /*startActivity(new Intent(HomeActivity.this, SettingsActivity
                                                .class));*/
                    //  loadingDialogg(activity);
                    //GenerateChecksum();
                    // finish();
                    activity.finish();
                    //loadingDialog.dismiss();
                    activity.finishAffinity();

                }
            });

            appPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingDialog.dismiss();
                    activity.startActivity(new Intent(activity, TicketsActivity
                            .class));
                    //loadingDialog.show();
                    //GenerateChecksum();
                    // finish();
                    alertDialog.dismiss();
                    activity.finish();


                }
            });


        }
    }


    public void startPayment(Activity activity) {
        if (settings.getPaymentGateway().equals("paytm_gateway")) {
            GenerateChecksum(activity);
        } else if (settings.getPaymentGateway().equals("razorpay_gateway")) {
            startRazorPayment(activity);
        } else if (settings.getPaymentGateway().equals("payumoney_gateway")) {
            startPayuPayment(activity);
        }
        if (settings.getPaymentGateway().equals("upiId")) {
            String upiId = settings.getUpiId();
            String name = (userDTO.getName());
            String desc = "Add Money";
            String note = (userDTO.getMobile());
            // String note = noteEt.getText().toString();
            payUsingUpi(activity, settings.getAppJoiningFee(), upiId, name, note);
        }
    }


    public static void startRazorPayment(Activity activity) {
        Checkout checkout = new Checkout();
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */

        final Checkout co = new Checkout();
        checkout.setKeyID(settings.getPayumoneyKey());
        /**
         * Instantiate Checkout
         */


        /**
         * Set your logo here
         */
        checkout.setImage(R.mipmap.ic_launcher_round);

        try {
            JSONObject options = new JSONObject();
            options.put("name", "NextGen Solution  Pvt.Ltd");
            options.put("description", "App Joining Fee");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://abcdjob.live/assets/images/profile");
            options.put("currency", "INR");

            String payment = (settings.getAppJoiningFee());

            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);
            JSONObject preFill = new JSONObject();
            preFill.put("email", userDTO.getEmail());
            preFill.put("contact", userDTO.getMobile());
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public void GenerateChecksum(Activity activity) {
        loadingDialogg(activity);
        Random r = new Random(System.currentTimeMillis());
        orderId = "QRCODE" + (1 + r.nextInt(2)) * 1000
                + r.nextInt(1000);
        //  editor.putString(GlobalVariables.txn_orderId, orderId);


        String url = "https://abcdjob.live/admin/paytm1/generateChecksum.php";


        Map<String, String> params = new HashMap<>();
        params.put("MID", settings.getPaytmMid());
        params.put("ORDER_ID", orderId);
        params.put("CUST_ID", userDTO.getUserReferalCode());
        params.put("MOBILE_NO", userDTO.getMobile());
        params.put("EMAIL", userDTO.getEmail());
        params.put("CHANNEL_ID", "WAP");
        params.put("TXN_AMOUNT", settings.getAppJoiningFee());
        params.put("WEBSITE", WEBSITE);
        params.put("INDUSTRY_TYPE_ID", INDUSTRY_TYPE_ID);
        params.put("CALLBACK_URL", CALLBACK_URL);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Toast.makeText(HomeActivity.this,response,Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has("CHECKSUMHASH")) {
                                checksum = jsonObject.getString("CHECKSUMHASH");
                                onStartTransaction(activity);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(HomeActivity.this,"Error...!",Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                activity.finish();
                activity.startActivity(activity.getIntent());
                loadingDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("MID", settings.getPaytmMid());
                params.put("ORDER_ID", orderId);
                params.put("CUST_ID", userDTO.getUserReferalCode());
                params.put("MOBILE_NO", userDTO.getMobile());
                params.put("EMAIL", userDTO.getEmail());
                params.put("CHANNEL_ID", "WAP");
                params.put("TXN_AMOUNT", settings.getAppJoiningFee());
                params.put("WEBSITE", WEBSITE);
                params.put("INDUSTRY_TYPE_ID", INDUSTRY_TYPE_ID);
                params.put("CALLBACK_URL", CALLBACK_URL);

                return params;
            }
        };


        queue.add(stringRequest);
// Access the RequestQueue through your singleton class.
        //MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public static void startPayuPayment(Activity activity) {

    }


    public void onStartTransaction(Activity activity) {
        PaytmPGService Service = PaytmPGService.getProductionService();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", settings.getPaytmMid());
        // Key in your staging and production MID available in your dashboard


        paramMap.put("ORDER_ID", orderId);
        paramMap.put("CUST_ID", userDTO.getUserReferalCode());
        paramMap.put("MOBILE_NO", userDTO.getMobile());
        paramMap.put("EMAIL", userDTO.getEmail());
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("TXN_AMOUNT", settings.getAppJoiningFee());
        paramMap.put("WEBSITE", WEBSITE);
        paramMap.put("INDUSTRY_TYPE_ID", INDUSTRY_TYPE_ID);
        paramMap.put("CALLBACK_URL", CALLBACK_URL);
        paramMap.put("CHECKSUMHASH", checksum);


        PaytmOrder Order = new PaytmOrder((HashMap<String, String>) paramMap);


        Service.initialize(Order, null);


        Service.startPaymentTransaction(activity, true,
                true, new PaytmPaymentTransactionCallback() {

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {
                        System.out.println("===== onTransactionResponse " + inResponse.toString());
                        if (Objects.equals(inResponse.getString("STATUS"), "TXN_SUCCESS")) {
                            //    Payment Success
                            Toast.makeText(activity, " Transaction success", Toast.LENGTH_LONG).show();

                            //uploadData();
                            onSuccessPay(activity, orderId);
                        } else if (!inResponse.getBoolean("STATUS")) {
                            //    Payment Failed
                            Toast.makeText(activity, " Transaction Failed", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(getContext(), HomeActivity.class));
                            activity.finish();
                            activity.startActivity(activity.getIntent());
                            loadingDialog.dismiss();

                        }
                    }

                    @Override
                    public void networkNotAvailable() {
                        // network error
                        //clickOnGenerate();
                        activity.finish();
                        activity.startActivity(activity.getIntent());
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        // AuthenticationFailed
                        activity.finish();
                        activity.startActivity(activity.getIntent());

                        // clickOnGenerate();
                        //startActivity(new Intent(getContext(), HomeActivity.class));
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                        // UI Error
                        // clickOnGenerate();
                        activity.finish();
                        activity.startActivity(activity.getIntent());
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                        //  Web page loading error
                        //clickOnGenerate();
                        //startActivity(new Intent(getContext(), HomeActivity.class));
                        activity.finish();
                        activity.startActivity(activity.getIntent());
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onBackPressedCancelTransaction() {
                        // on cancelling transaction

                        //clickOnGenerate();
                        // startActivity(new Intent(getContext(), HomeActivity.class));
                        activity.finish();
                        activity.startActivity(activity.getIntent());
                        loadingDialog.dismiss();

                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        // maybe same as onBackPressedCancelTransaction()
                        //clickOnGenerate();
                        //startActivity(new Intent(getContext(), HomeActivity.class));
                        activity.finish();
                        activity.startActivity(activity.getIntent());
                        loadingDialog.dismiss();
                    }
                });
    }


    private void onSuccessPay(Activity activity, String orderId) {

        if (orderId == null) {
            //Toast.makeText(HomeActivity.this, "NULL", Toast.LENGTH_LONG).show();
            //editor.putString(GlobalVariables.txn_orderId, orderId);


            Random r = new Random(System.currentTimeMillis());
            orderId = "BYCASH" + (1 + r.nextInt(2)) * 1000
                    + r.nextInt(1000);
            //  editor.putString(GlobalVariables.txn_orderId, this.orderId);
            preferencess.setValue(GlobalVariables.txn_orderId, orderId);
            Log.d(TAG, "onSuccessPay: "+method.params);
        }
        loadingDialogg(activity);
        String finalOrderId = orderId;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_insert_payment_verification,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(LoginActivity.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
                        try {
                            //    System.out.println(response);
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray(GlobalVariables.AppSid);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String success = object.getString("success");

                                if (success.equals("1")) {
                                    Toast.makeText(activity, "Updated", Toast.LENGTH_SHORT).show();
                                    activity.finish();
                                    // startActivity(getIntent());
                                    loadingDialog.dismiss();
//                                    loadingDialog.dismiss();
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
                                    alertDialogBuilder.setTitle(object.getString("title"));
                                    alertDialogBuilder.setMessage(object.getString("msg"));
                                    alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                                    alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.ok_message),
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    activity.finish();
                                                    loadingDialog.dismiss();
                                                    activity.finishAffinity();
                                                }
                                            });

                                    android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                    Toast.makeText(activity, object.getString("msg"), Toast.LENGTH_LONG).show();


                                } else {
                                    loadingDialog.dismiss();
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
                                    alertDialogBuilder.setTitle(object.getString("title"));
                                    alertDialogBuilder.setMessage(object.getString("msg"));
                                    alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                                    alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.ok_message),
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    activity.finish();
                                                    loadingDialog.dismiss();
                                                    activity.finishAffinity();
                                                }
                                            });

                                    android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                    Toast.makeText(activity, object.getString("msg"), Toast.LENGTH_LONG).show();
                                }

                            }

                            // progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(activity, "Error: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "RESPONSE: " + error, Toast.LENGTH_SHORT).show();
                Log.e("Error", "" + error.getMessage());
                Toast.makeText(activity, "ErrorV: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
                alertDialogBuilder.setTitle("Something Went Wrong");
                alertDialogBuilder.setMessage("Please Try With Active Internet ");
                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.ok_message),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                activity.finish();
                                loadingDialog.dismiss();
                                activity.finishAffinity();
                            }
                        });

                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("app_joining_fee_paid", "");
                params.put("user_id", userDTO.getMobile());
                params.put("amount", settings.getAppJoiningFee());
                params.put("name", userDTO.getName());
                params.put("email", userDTO.getEmail());
                params.put("paid", userDTO.getJoiningPaid());
                params.put("order_id", finalOrderId);
                params.put("city", userDTO.getCity());
                Log.d("KINGSN", "getParams: "+params);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);


    }

    public void payUsingUpi(Activity activity, String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if (null != chooser.resolveActivity(activity.getPackageManager())) {
            activity.startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(activity, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }

    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {


        if (requestCode == UPI_PAYMENT) {
            if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                if (data != null) {
                    String trxt = data.getStringExtra("response");
                    Log.d("UPI", "onActivityResult: " + trxt);
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add(trxt);
                    upiPaymentDataOperation(dataList, activity);
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList, activity);
                }
            } else {
                Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                ArrayList<String> dataList = new ArrayList<>();
                dataList.add("nothing");
                upiPaymentDataOperation(dataList, activity);
            }
        }

        // Result Code is -1 send from Payumoney activity
        Log.d("MainActivity", "request code " + requestCode + " resultcode " + resultCode);

    }

    public void upiPaymentDataOperation(ArrayList<String> data, Activity activity) {
        String str = data.get(0);
        Log.d("UPIPAY", "upiPaymentDataOperation: " + str);
        String paymentCancel = "";
        if (str == null) str = "discard";
        String status = "";
        String approvalRefNo = "";
        String[] response = str.split("&");
        for (String s : response) {
            String[] equalStr = s.split("=");
            if (equalStr.length >= 2) {
                if (equalStr[0].equalsIgnoreCase("Status")) {
                    status = equalStr[1].toLowerCase();
                } else if (equalStr[0].equalsIgnoreCase("ApprovalRefNo") || equalStr[0].equalsIgnoreCase("txnRef")) {
                    approvalRefNo = equalStr[1];
                }
            } else {
                paymentCancel = "Payment cancelled by user.";
            }
        }

        if (status.equals("success")) {
            //Code to handle successful transaction here.
            //  Toast.makeText(Payment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
            Log.d("UPI", "responseStr: " + approvalRefNo);
            // showPay_Status_AlertDialog(1);
            //  onSuccessPay(orderId);
            onSuccessPay(activity, orderId);
        } else if ("Payment cancelled by user.".equals(paymentCancel)) {
            //Toast.makeText(Payment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            Toast.makeText(activity, " Transaction Failed", Toast.LENGTH_LONG).show();
            //startActivity(new Intent(getContext(), HomeActivity.class));
            activity.finish();
            activity.startActivity(activity.getIntent());
            loadingDialog.dismiss();
        } else {
            Toast.makeText(activity, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            Toast.makeText(activity, " Transaction Failed", Toast.LENGTH_LONG).show();
            //startActivity(new Intent(getContext(), HomeActivity.class));
            activity.finish();
            activity.startActivity(activity.getIntent());
            loadingDialog.dismiss();
        }

    }

    public void checkUser(Activity activity) {
        loadingDialogg(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.CHECK_USER,
                response -> {
                    // Toast.makeText(LoginActivity.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
                    try {
                        System.out.println(response);
                        JSONObject object = new JSONObject(response).getJSONObject(GlobalVariables.AppSid);
                        String success = object.getString("success");

                        if (success.equals("11")) {
                            // progressDialog.dismiss();
                            //String user_id = object.getString("unique_id");
                            loadingDialog.dismiss();
                            //Toast.makeText(LoginActivity.this, Constant.userPhone, Toast.LENGTH_LONG).show();
                            // LoadSettings();
                            loadingDialog.dismiss();
                               /* Intent intent = new Intent(activity, LoginPageActivity.class);
                                intent.putExtra ("mobile",binding.phone.getText().toString() );
                                startActivity(intent);*/
                            Log.d(TAG, "checkUser: " + params.get("type"));

                            if (Objects.equals(params.get("type"), "register")) {
                                loadingDialog.dismiss();
                                Intent intent = new Intent(activity, RegisterActivity.class);
                                intent.putExtra("mobile", params.get("mobile"));
                                intent.putExtra("screentype", "1");
                                activity.finish();
                                activity.startActivity(intent);

                            } else {
                                loadingDialog.dismiss();
                                Intent intent = new Intent(activity, ForgotActivity.class);
                                intent.putExtra("mobile", params.get("mobile"));
                                intent.putExtra("screentype", "2");
                                activity.startActivity(intent);
                            }


                        } else {
                            // mobileNumber=binding.countryCodePicker.getSelectedCountryCode()+binding.phone.getText().toString();
                            loadingDialog.dismiss();
                            loadingDialog.dismiss();
                            //  sendVerificationCodeToUser(mobileNumber);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                            alertDialogBuilder.setTitle(object.getString("title"));
                            alertDialogBuilder.setMessage(object.getString("msg"));
                            alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                            alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.ok_message),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            loadingDialog.dismiss();
                                            //Log.d("Response",msg);
                                            activity.finishAffinity();

                                        }
                                    });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                            Toast.makeText(activity, object.getString("msg"), Toast.LENGTH_LONG).show();
                        }


                        // progressDialog.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(),
                        // Toast.LENGTH_SHORT).show();
                    }

                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Log.e("Error", "" + error.getMessage());
                //   Ex.AlertBox("Internet Connection Not Available");

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
        Log.d(TAG, "login:stringrequest " + stringRequest + params);


    }


    public void updateSetting(Activity activity, String restAPI) {
        loadingDialogg(activity);
        params.put("mobile", userDTO.getMobile());
        params.put("user_id", userDTO.getUserReferalCode());
        // method.showToasty(activity,"1",""+GlobalVariables.adminUserID);
        Log.d(GlobalVariables.TAG, "getHomeData2: called" + activity.toString());
        new HttpsRequest(restAPI, params, activity).stringPost2(GlobalVariables.TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, JSONObject abcdapp, String title, String message, JSONObject response) {

                if (flag) {
                    // binding.tvNo.setVisibility(View.GONE);
                    try {

                        // loadingDialog.dismiss();
                        if (restAPI.equals(RestAPI.API_update_profile)) {
                            Method.alertBox("1", title, message, activity, GlobalVariables.btntxt);
                        } // loadingDialog.dismiss();
                        else if (restAPI.equals(RestAPI.API_PUSH_TOKEN)) {
                           // Method.loadingDialog.dismiss();
                            loadingDialog.dismiss();
                        } else {
                            Method.alertBox("1", title, message, activity, GlobalVariables.btntxt);
                        }


                        loadingDialog.dismiss();
                        // setData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                    alertDialogBuilder.setTitle(title);
                    alertDialogBuilder.setMessage(message);
                    alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                    alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.ok_message),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    loadingDialog.dismiss();
                                    activity.finish();
                                    activity.startActivity(new Intent(activity, HomeActivity.class));
                                    //Log.d("Response",msg);
                                    // finishAffinity();

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }


        });
    }


    public static void alertBox(String type, String title, String message, Activity activity, String btntxt) {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.alert_dialog, viewGroup, false);
        builder.setView(dialogView);
        builder.setCancelable(false);
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        ImageView iconImage = dialogView.findViewById(R.id.iconImage);
        TextView alertTitle = dialogView.findViewById(R.id.alertTitle);
        TextView alertMsg = dialogView.findViewById(R.id.alertMsg);

        alertTitle.setText(title);
        alertMsg.setText(message);

        switch (type) {
            case "1":
                iconImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_check));
                iconImage.setBackground(activity.getResources().getDrawable(R.drawable.round_icon_bg));
                break;
            case "update":
                iconImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_update));
                iconImage.setBackground(activity.getResources().getDrawable(R.drawable.round_icon_bg));
                break;
            case "blocked":
                iconImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_baseline));
                iconImage.setBackground(activity.getResources().getDrawable(R.drawable.round_icon_bg));
                break;
            default:

                iconImage.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_baseline));
                iconImage.setBackground(activity.getResources().getDrawable(R.drawable.round_icon_bg));

                break;
        }




        Button alertBtn = dialogView.findViewById(R.id.alertBtn);
        alertBtn.setText(btntxt);
        alertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(GlobalVariables.TAG, "alertBox: ok clicked");
                //alertDialog.dismiss();
                Log.d("alertBoxtype", "onClick:homebtntxt "+ GlobalVariables.btntxt+"gyfygfy\n"+btntxt);
                if(Objects.equals(btntxt, GlobalVariables.btntxt)){
                    Log.d("alertBoxtype", "onClick:homebtntxt ");
                 /*   Intent intent = new Intent(activity, HomeActivity.class);
                    activity.startActivity(intent);
                    alertDialog.dismiss();*/
                    alertDialog.dismiss();
                    //activity.recreate();

                    showFragment(new GenerateFragment(),activity);

                }else if(Objects.equals(btntxt, GlobalVariables.UpdateKyc)){
                    Intent intent = new Intent(activity, TicketsActivity.class);
                    activity.startActivity(intent);
                    alertDialog.dismiss();

                }else if(Objects.equals(btntxt,  "Start Chat")){
                    alertDialog.dismiss();

                }else if(Objects.equals(btntxt,  GlobalVariables.update)){
                    Uri rate = Uri.parse(GlobalVariables.settings.getUpdateLink());
                    Intent intent = new Intent(Intent.ACTION_VIEW, rate);
                    activity.startActivity(intent);
                    if (Float.parseFloat(GlobalVariables.settings.getNewVersion()) == BuildConfig.VERSION_CODE) {
                        alertDialog.dismiss();
                    }
                }else if(Objects.equals(btntxt,  GlobalVariables.blocked)){
                    Uri rate = Uri.parse(GlobalVariables.settings.getUpdateLink());
                    Intent intent = new Intent(activity, TicketsActivity.class);
                    intent.putExtra("blocked", "blocked");
                    activity.startActivity(intent);
                }
               /* switch (btntxt) {
                    case GlobalVariables.btntxt: {
                        Intent intent = new Intent(activity, HomeActivity.class);
                        activity.startActivity(intent);
                        alertDialog.dismiss();
                        break;
                    }
                    case GlobalVariables.UpdateKyc: {
                        Intent intent = new Intent(activity, TicketsActivity.class);
                        activity.startActivity(intent);
                        alertDialog.dismiss();
                        break;
                    }
                    case "Start Chat":
                   *//* Intent intent = new Intent(activity, WithdrawDetailsActivity.class);
                    activity.startActivity(intent);*//*
                        alertDialog.dismiss();
                        break;
                    case GlobalVariables.update: {
                   *//* Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);*//*

                        Uri rate = Uri.parse(GlobalVariables.settings.getUpdateLink());
                        Intent intent = new Intent(Intent.ACTION_VIEW, rate);
                        activity.startActivity(intent);
                        if (Float.parseFloat(GlobalVariables.settings.getNewVersion()) == BuildConfig.VERSION_CODE) {
                            alertDialog.dismiss();
                        }


                        break;
                    }
                    case GlobalVariables.blocked: {
                   *//* Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);*//*

                        Uri rate = Uri.parse(GlobalVariables.settings.getUpdateLink());
                        Intent intent = new Intent(activity, TicketsActivity.class);
                        intent.putExtra("blocked", "blocked");
                        activity.startActivity(intent);


                        break;
                    }

                }*/
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

    }


    public String Otp() {
        Random rnd = new Random();
        return String.valueOf(100000 + rnd.nextInt(900000));
    }

    public void checkInternetConnection(Activity activity) {
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(ReactiveNetwork
                .observeNetworkConnectivity(_context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(connectivity -> {
                    if (connectivity.state() == NetworkInfo.State.CONNECTED) {
                        Log.d(GlobalVariables.TAG, "checkInternetConnection: Connected");
                        //showToasty(activity, "1", "Internet Is Connected");

                    } else {

                        Log.d(GlobalVariables.TAG, "checkInternetConnection: NOTConnected");
                        showToasty(activity, "2", "Internet Is Not Connected");

                    }

                }, throwable -> {
                    // Toast.makeText(this, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();

                }));
    }

    public void copyToclipboard(Activity activity, TextView txt) {
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", txt.getText());
        assert clipboard != null;
        clipboard.setPrimaryClip(clip);
        Toasty.success(activity, "Copied text", Toast.LENGTH_SHORT).show();
    }

    public void copyToclipboard1(Context activity, TextView txt) {
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", txt.getText());
        assert clipboard != null;
        clipboard.setPrimaryClip(clip);
        Toasty.success(activity, "Copied text", Toast.LENGTH_SHORT).show();
    }

    public static boolean hasPermissionInManifest(Activity activity, int requestCode, String permissionName) {
        if (ContextCompat.checkSelfPermission(activity,
                permissionName)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(activity,
                    new String[]{permissionName},
                    requestCode);
        } else {
            return true;
        }
        return false;
    }

    public static Dialog showProgressDialog(Context context,
                                            boolean isCancelable, String message) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
        mProgressDialog.setCancelable(isCancelable);
        //mProgressDialog.setIndeterminate(true);
        //mProgressDialog.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.my_animation));
        return mProgressDialog;
    }

    /**
     * Static method to pause the progress dialog.
     */
    public static void pauseProgressDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.cancel();
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    public static String getEditTextValue(EditText text) {
        return text.getText().toString().trim();
    }

    public static void callPushIntent(Activity activity, String type) {


        if (type != null) {
            if (type.equalsIgnoreCase(GlobalVariables.CHAT_NOTIFICATION)) {
                   /*header.setVisibility(View.VISIBLE);
                    navItemIndex = 2;*/
                CURRENT_TAG = GlobalVariables.TAG_CHAT;
                //loadHomeFragment(new ChatList(), CURRENT_TAG);
                activity.getIntent().getStringExtra("sendBy");
                //   getIntent().getStringExtra(GlobalVariables.adminMobile, sendBy);
                //intent.putExtra(GlobalVariables.adminMobile, sendBy);
                Log.d("KINGSN", "onCreateIntent: " + activity.getIntent().getStringExtra(GlobalVariables.userMobile));

                // startActivity(new Intent(MainActivity.this, OneTwoOneChat.class));


                Intent intent = new Intent(activity, CommentOneByOne.class);
                intent.putExtra("sendBy", activity.getIntent().getStringExtra("sendBy"));
                method.preferencess.setValue("ticketId", activity.getIntent().getStringExtra("title"));
                intent.putExtra("ticket", activity.getIntent().getStringExtra("ticketId"));
                intent.putExtra("sendTo", activity.getIntent().getStringExtra("sendTo"));

                activity.startActivity(intent);

            } else if (type.equalsIgnoreCase(GlobalVariables.ADMIN_NOTIFICATION)) {
                    /*header.setVisibility(View.VISIBLE);
                    navItemIndex = 11;*/
                CURRENT_TAG = GlobalVariables.TAG_ADMINNOTI;
                // startActivity(new Intent(HomeActivity.this, NotificationsActivity.class));
                Log.d("KINGSN", "onCreate: showing TTT");

                Intent intent = new Intent(activity, CommentOneByOne.class);
                intent.putExtra("sendBy", activity.getIntent().getStringExtra("sendBy"));
                method.preferencess.setValue("ticketId", activity.getIntent().getStringExtra("title"));
                intent.putExtra("ticket", activity.getIntent().getStringExtra("ticketId"));
                intent.putExtra("sendTo", activity.getIntent().getStringExtra("sendTo"));

                activity.startActivity(intent);

            } else if (type.equalsIgnoreCase(GlobalVariables.ADMIN_NOTIFICATION)) {
                    /*header.setVisibility(View.VISIBLE);
                    navItemIndex = 11;*/
                CURRENT_TAG = GlobalVariables.TAG_ADMINNOTI;
                // startActivity(new Intent(HomeActivity.this, NotificationsActivity.class));
                Log.d("KINGSN", "onCreate: showing TTT");

                Intent intent = new Intent(activity, CommentOneByOne.class);
                intent.putExtra("sendBy", activity.getIntent().getStringExtra("sendBy"));
                method.preferencess.setValue("ticketId", activity.getIntent().getStringExtra("title"));
                intent.putExtra("ticket", activity.getIntent().getStringExtra("ticketId"));
                intent.putExtra("sendTo", activity.getIntent().getStringExtra("sendTo"));

                activity.startActivity(intent);

            }
        } else {
            Intent intent = new Intent(activity, HomeActivity.class);
            intent.putExtra("sendBy", activity.getIntent().getStringExtra("sendBy"));
            method.preferencess.setValue("ticketId", activity.getIntent().getStringExtra("title"));
            intent.putExtra("ticket", activity.getIntent().getStringExtra("ticketId"));
            intent.putExtra("sendTo", activity.getIntent().getStringExtra("sendTo"));

            activity.startActivity(intent);

        }


    }


    public static void showFragment(Fragment fragment, Activity activity) {
        androidx.fragment.app.FragmentTransaction transaction = ((FragmentActivity)activity). getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.coordinator_layout_fragment_container, fragment,
                fragment.getClass().getSimpleName());
        transaction.commit();
      //  Log.d("FragmentName", "showFragment: "+fragment.getClass().getSimpleName());
       String fragmentName=fragment.getClass().getSimpleName();
       HomeActivity activityh=new HomeActivity();
        activityh.clickOnGenerate1();




    }


    }