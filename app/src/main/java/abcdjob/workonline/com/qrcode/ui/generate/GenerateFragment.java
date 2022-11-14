package abcdjob.workonline.com.qrcode.ui.generate;

import static java.lang.Integer.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.razorpay.Checkout;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import abcdjob.workonline.com.qrcode.Interface.Helper;
import abcdjob.workonline.com.qrcode.Models.Settings;
import abcdjob.workonline.com.qrcode.Models.UserDTO;
import abcdjob.workonline.com.qrcode.Models.codeGen;
import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.databinding.FragmentGenerateBinding;
import abcdjob.workonline.com.qrcode.helpers.constant.IntentKey;
import abcdjob.workonline.com.qrcode.helpers.model.Code;
import abcdjob.workonline.com.qrcode.https.HttpsRequest;
import abcdjob.workonline.com.qrcode.preferences.SharedPrefrence;
import abcdjob.workonline.com.qrcode.ui.ContactActivity;
import abcdjob.workonline.com.qrcode.ui.LoginActivity;
import abcdjob.workonline.com.qrcode.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.Util.Method;
import abcdjob.workonline.com.qrcode.Util.RestAPI;
import abcdjob.workonline.com.qrcode.ui.adapter.Student_Data;
import abcdjob.workonline.com.qrcode.ui.generatedcode.GeneratedCodeActivity;
import es.dmoral.toasty.Toasty;


public class GenerateFragment extends Fragment {
    private static final String TAG = "KINGSN";

    private FragmentGenerateBinding mBinding;
    public Settings settings;
    private List<Student_Data> student_data;
    @SuppressLint("StaticFieldLeak")
    private String test;
    private String qrtext;
    private int D1;
    private int D2;
    private int qrtime=0;
    private int qrtime1=0;
 
    private int count,item;
    private CountDownTimer mCountDownTimer;
    private static final long START_TIME_IN_MILLIS = 600000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    @Nullable
    Timer timer;
    TimerTask timerTask;
    public SharedPrefrence preferencess;
    Double time = 0.0;
    boolean timerStarted = false;
    public static String toGenText="";
    public   Method method;
    UserDTO userDTO;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    public  String orderId="";
    final int UPI_PAYMENT = 0;

    public static final String WEBSITE = "DEFAULT";
    public static final String CHANNEL_ID = "WAP";
    public static final String INDUSTRY_TYPE_ID = "Retail";
    public static final String CALLBACK_URL = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
    public static String checksum;
    public String intentd="";
    private final int codeGenDelay = 1500;
    private boolean mTimerRunning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle extras = requireActivity().getIntent().getExtras();
        if(extras == null) {
            intentd="";
        } else {
            intentd=extras.getString("activity");
        }

        preferencess = SharedPrefrence.getInstance(requireActivity());
        preferencess.setIntValue(String.valueOf(GlobalVariables.homeClicked),1);

    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        method=new Method(requireActivity());
        mBinding = FragmentGenerateBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();


        mBinding.textViewGenerate.setVisibility(View.VISIBLE);

        item= method.preferencess.getIntValue(GlobalVariables.QRCOUNT)+1;
        // Toast.makeText(getActivity(), "" + item, Toast.LENGTH_SHORT).show();

        if (requireActivity().getIntent().hasExtra(GlobalVariables.SCREEN_TAG)) {
           String  type = requireActivity().getIntent().getStringExtra(GlobalVariables.SCREEN_TAG);
            Log.d("KINGSN", "pushIntent: "+requireActivity().getIntent().getStringExtra(GlobalVariables.userMobile));

            Method.callPushIntent(requireActivity(),type);
        }



        method.loadingDialogg(requireActivity());
        //getHomeData(requireActivity());
        mBinding.spinnerTypes2.setSelection(1);
        mBinding.spinnerTypes2.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        
        timer = new Timer();


        mBinding.nametxtEt.setLongClickable(false);
        mBinding.nametxtEt.setOnTouchListener((View.OnTouchListener) requireActivity());
        mBinding.citytxtEt.setLongClickable(false);
        mBinding.pintxtEt.setLongClickable(false);
        mBinding.nametxt. setTextIsSelectable(false);
        mBinding.idtxt. setTextIsSelectable(false);
        mBinding.citytxt. setTextIsSelectable(false);
        mBinding.pincodetxt. setTextIsSelectable(false);

      /*  mBinding.nametxtEt.setText("55");
        mBinding.idtxt.setText("5555555555");
        mBinding.citytxt.setText("55");
        mBinding.pintxtEt.setText("55");
*/


        mBinding.nametxtEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                startTapped();
               /* Log.d(TAG, "nameedittxt: nameedittxt has text changed");
                Log.d(TAG, "onTextChanged: "+s);*/
               // method.showToasty(requireActivity(),"1", String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBinding.nametxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(mBinding.nametxt.hasFocus()){

                    Log.d(TAG, "nameedittxt: nameedittxt has focus");
                    //et1.setCursorVisible(true);
                   startTapped();

                }
            }
        });
        mBinding.pincodetxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(mBinding.nametxt.hasFocus()){
                    Log.d(TAG, "pincodetxt: pincodetxt has focus");
                    //et1.setCursorVisible(true);
                    startTapped();

                }
            }
        });
         mBinding.nametxt.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });

        mBinding.nametxtEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("","");
                clipboard.setPrimaryClip(clip);
                startTapped();

            }

        });


        mBinding.nametxtEt.setOnTouchListener((v, event) -> {
            @SuppressLint("ClickableViewAccessibility") ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("","");
            clipboard.setPrimaryClip(clip);
            Log.e("ontouch", "onTouch:started " );
            startTapped();
            //startStopTapped();
            int inType = mBinding.nametxtEt.getInputType(); // backup the input type
            // nametxtEt.setInputType(InputType.TYPE_NULL); // disable soft input
            mBinding.nametxtEt.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
            mBinding. nametxtEt.onTouchEvent(event); // call native handler
            mBinding.nametxtEt.setInputType(inType); // restore input type
           // startTapped();
            mBinding.textViewGenerate.isClickable();{
                mBinding.textViewGenerate.setClickable(true);
            }
            return true; // consume touch even

        });


        mBinding.pintxtEt.setCustomInsertionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        if( mBinding.nametxtEt.hasFocus()){
            startTapped();
            Log.e("nametext", "onCreateView:has focus");
        }

        mBinding.nametxtEt.setCustomInsertionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // startTimer();
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });





        mBinding.textViewGenerate.setOnClickListener(new View.OnClickListener() {
            // String qrtext = inputtext.getText().toString();

            @Override
            public void onClick(View v) {
                String otpdata = Objects.requireNonNull(mBinding.otp1.getText()) + Objects.requireNonNull(mBinding.otp2.getText()).toString() + Objects.requireNonNull(mBinding.otp3.getText()) + Objects.requireNonNull(mBinding.otp4.getText()) + Objects.requireNonNull(mBinding.otp5.getText()) +
                        Objects.requireNonNull(Objects.requireNonNull(mBinding.otp6.getText()))+ Objects.requireNonNull(mBinding.otp7.getText()) + Objects.requireNonNull(Objects.requireNonNull(mBinding.otp8.getText())) + Objects.requireNonNull(mBinding.otp9.getText()) + Objects.requireNonNull(mBinding.otp10.getText());
                Log.d(TAG, "onClick: generated clicked");
                Log.d(TAG, "onClick: generated clicked");
                //Toast.makeText(getActivity(),preferences.getString(GlobalVariables.D1,""), Toast.LENGTH_SHORT).show();
                // Toast.makeText(getActivity(),""+qrtext, Toast.LENGTH_SHORT).show();
                if(Objects.requireNonNull(mBinding.nametxtEt.getText()).toString().isEmpty()) {

                    mBinding.nametxtEt.setError("Fields Cant be Empty");
                    //Toast.makeText(RegisterActivity.this, "Input valid email", Toast.LENGTH_SHORT).show();
                    if (GlobalVariables.settings.getCallIndustrialOn().equalsIgnoreCase("everytime")
                            || GlobalVariables.settings.getCallIndustrialOn().equalsIgnoreCase("both"))

                    {
                        method.callindustrial((FragmentActivity) getContext());
                    }
                    mBinding.textViewGenerate.setClickable(true);
                    return;
                }if (!mBinding.nametxtEt.getText().toString().equals(GlobalVariables.codeGen.getStudentName())){
                    mBinding.nametxtEt.setError("Input Text Doesn't matched");
                    if (GlobalVariables.settings.getCallIndustrialOn().equalsIgnoreCase("everytime")
                            || GlobalVariables.settings.getCallIndustrialOn().equalsIgnoreCase("both"))

                    {
                        method.callindustrial((FragmentActivity) getContext());
                    }
                    mBinding.textViewGenerate.setClickable(true);
                    return;
                }/*else if(nametxtEt.getText().toString().equals(GlobalVariables.codeGen.getStudentName())){
            idtxtEt.callOnClick();
            idtxtEt.requestFocus();

        }*/
                if (!otpdata.equals(GlobalVariables.codeGen.getIdNumber())){
                    mBinding.otp10.setError("Please fill correct id");

                }
        /*if(idtxtEt.getText().toString().isEmpty()){
            idtxtEt.setError("Fields Cant be Empty");
            idtxtEt.animate();
            idtxtEt.setLineColor(
                    ResourcesCompat.getColor(requireActivity().getResources(), R.color.colorPrimary, getActivity().getTheme()));
            //Toast.makeText(RegisterActivity.this, "Input valid email", Toast.LENGTH_SHORT).show();
            if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")
                    ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both"))

            {
                method.callindustrial((FragmentActivity) getContext());}
             mBinding.textViewGenerate.setClickable(true);
            return;
        }*/ if (!otpdata.equals(GlobalVariables.codeGen.getIdNumber())){

                    mBinding.otp10.setError("Please fill correct id");
          /*  //Toast.makeText(RegisterActivity.this, "Input valid email", Toast.LENGTH_SHORT).show();
            if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")
                    ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both"))

            {
                method.callindustrial((FragmentActivity) getContext());}
             mBinding.textViewGenerate.setClickable(true);
            return;
        }   idtxtEt.setError("Input Text Doesn't matched");
            if (GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("everytime")
                    ||GlobalVariables.settings.getCallIndustrialOn().toLowerCase().equals("both"))

            {
                method.callindustrial((FragmentActivity) getContext());
            }
*/
                    mBinding.textViewGenerate.setClickable(true);
                    return;
                }
                if(mBinding.citytxtEt.getText().toString().isEmpty()){
                    mBinding.citytxtEt.setError("Fields Cant be Empty");
                    //Toast.makeText(RegisterActivity.this, "Input valid email", Toast.LENGTH_SHORT).show();
                    if (GlobalVariables.settings.getCallIndustrialOn().equalsIgnoreCase("everytime")
                            || GlobalVariables.settings.getCallIndustrialOn().equalsIgnoreCase("both"))

                    {
                        method.callindustrial((FragmentActivity) getContext());}
                    mBinding.textViewGenerate.setClickable(true);
                    return;
                } if (!mBinding.citytxtEt.getText().toString().equals(GlobalVariables.codeGen.getEcity())){
                    mBinding.citytxtEt.setError("Input Text Doesn't matched");
                    if (GlobalVariables.settings.getCallIndustrialOn().equalsIgnoreCase("everytime")
                            || GlobalVariables.settings.getCallIndustrialOn().equalsIgnoreCase("both"))

                    {
                        method.callindustrial((FragmentActivity) getContext());
                    }
                    mBinding.textViewGenerate.setClickable(true);
                    return;
                }if(mBinding.pintxtEt.getText().toString().isEmpty()){
                    mBinding.pintxtEt.setError("Fields Cant be Empty");

                    if (GlobalVariables.settings.getCallIndustrialOn().equalsIgnoreCase("everytime")
                            || GlobalVariables.settings.getCallIndustrialOn().equalsIgnoreCase("both"))

                    {
                        method.callindustrial((FragmentActivity) getContext());}
                     mBinding.textViewGenerate.setClickable(true);

                }if (! mBinding.pintxtEt.getText().toString().equals(GlobalVariables.codeGen.getPinCode())){
                     mBinding.pintxtEt.setError("Input Text Doesn't matched");
                    if (GlobalVariables.settings.getCallIndustrialOn().equalsIgnoreCase("everytime")
                            || GlobalVariables.settings.getCallIndustrialOn().equalsIgnoreCase("both"))

                    {
                        method.callindustrial((FragmentActivity) getContext());
                    }
                     mBinding.textViewGenerate.setClickable(true);

                }else {


                    StopTapped();

                    qrtext = mBinding.nametxtEt.getText() + "\n" + otpdata + "\n" + mBinding.citytxtEt.getText() + "\n" +
                             mBinding.pintxtEt.getText();
                    //  String qrtext = preferences.getString(GlobalVariables.D1,"");

                    Log.d(TAG, "onClick: generatedTextCode"+qrtext);
                    Log.d(TAG, "onClick: RequiredTextCode"+toGenText);

                    if (qrtext.equals(toGenText)) {
                        Log.d(TAG, "onClick: equal");
                    }else{
                        Log.d(TAG, "onClick: Notequal");
                    }

                    if (Method.userDTO.getCodeGenAllow().equals("0")) {


                        if (!Objects.equals(qrtext, toGenText)) {
                            //Toast.makeText(getActivity(), "NOT EQUAL", Toast.LENGTH_SHORT).show();
                            Toasty.error(requireActivity(), "NOT EQUAL \n InCorrect Qr Code Generation", Toast.LENGTH_LONG, true).show();

                            method.preferencess.setValue(GlobalVariables.Txn_amount,String.valueOf(0));
                            method.preferencess.setValue(GlobalVariables.Txn_type,getString(R.string.Qrcodegentxn));
                            method.preferencess.setValue(GlobalVariables.Txn_status,"CREDIT");



                        } else {
                            Toasty.success(requireActivity(), "EQUAL TEXT \n Correct Qr Code Generation", Toast.LENGTH_LONG, true).show();


                            if (qrtime1 > (Character.getNumericValue(settings.getQrMinTime().charAt(0)))) {

                                method.preferencess.setValue(GlobalVariables.Txn_amount, settings.getPerQrCoin());
                                method.preferencess.setValue(GlobalVariables.Txn_type, getString(R.string.Qrcodegentxn));
                                method.preferencess.setValue(GlobalVariables.Txn_status, "CREDIT");


                                Log.d(TAG, "Txn_amount: " +method.preferencess.getValue(GlobalVariables.Txn_amount));


                            } else {

                                method.preferencess.setValue(GlobalVariables.Txn_amount, String.valueOf(0));
                                method.preferencess.setValue(GlobalVariables.Txn_type, getString(R.string.Qrcodegentxn));
                                method.preferencess.setValue(GlobalVariables.Txn_status, "CREDIT");

                                Log.d(TAG, "Txn_amount: " +method.preferencess.getValue(GlobalVariables.Txn_amount));
                            }
                        }


                         mBinding.textViewGenerate.isClickable();{
                            mBinding.textViewGenerate.setClickable(false);
                        }
                        // insertwallet();
                        generateCode();



                    }else{

                        Toasty.error(requireActivity(), "You Have Been Restricted For Generating Qr Code. Contact Admin..!", Toast.LENGTH_LONG, true).show();
                    }

                }
            }
        });


        return view;

    }


  /*  private void setListeners() {
        mBinding.spinnerTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getSelectedView()).setTextColor(ContextCompat.getColor(mContext,
                        position == 0 ? R.color.text_hint : R.color.text_regular));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initializeCodeTypesSpinner() {
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(mContext,
                R.array.code_types, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        mBinding.spinnerTypes.setAdapter(arrayAdapter);
    }


*/

    public void generateCode() {
        Intent intent = new Intent(getContext(), GeneratedCodeActivity.class);
        if (qrtext != null) {
            String content = qrtext.trim();
            int type = mBinding.spinnerTypes2.getSelectedItemPosition();



            if (!TextUtils.isEmpty(content) && type != 0) {

                boolean isValid = true;

                switch (type) {
                    case Code.BAR_CODE:
                        if (content.length() > 80) {
                            Toast.makeText(getContext(),
                                    getString(R.string.error_barcode_content_limit),
                                    Toast.LENGTH_SHORT).show();
                            isValid = false;
                        }
                        break;

                    case Code.QR_CODE:
                        if (content.length() > 1000) {
                            Toast.makeText(getActivity(),
                                    getString(R.string.error_qrcode_content_limit),
                                    Toast.LENGTH_SHORT).show();
                            isValid = false;
                        }
                        break;

                    default:
                        isValid = false;
                        break;
                }

                if (isValid) {
                    Code code = new Code(content, type);
                    intent.putExtra(IntentKey.MODEL, code);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(getActivity(),
                        getString(R.string.error_provide_proper_content_and_type),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void getHomeData(Activity activity) {
        Log.d(TAG, "getHomeDatagen: "+ Method.userDTO.getTotalAllQrGeneration());

        method.params.clear();
        method.params.put("user_mobile",method.preferencess.getValue(GlobalVariables.USER_MOBILE));
        method.params.put("device_id",method.getDeviceId(activity.getApplicationContext()));
        //        // method.showToasty(activity,"1",""+GlobalVariables.adminUserID);
        Log.d(GlobalVariables.TAG, "getHomeData2: called"+ activity);
        new HttpsRequest(RestAPI.API_Settings, method.params, activity).stringPost2(GlobalVariables.TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, JSONObject abcdapp, String title, String message, JSONObject response) {

                if (flag) {
                    // binding.tvNo.setVisibility(View.GONE);
                    try {
                        Log.d(GlobalVariables.TAG, "hKINGSN123:" + response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results"));


                        userDTO = new Gson().fromJson(response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").getJSONObject("profile").toString(), (Type) UserDTO.class);
                        method.preferencess.setParentUser2(userDTO, "userDTO");
                        Method.userDTO =userDTO;
                        //  preferences.setParentUser3(userDTO, userDTO);

                        GlobalVariables.usermDTO=userDTO;
                        // GlobalVariables.settings= new Gson().fromJson(response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").getJSONObject("Settings").toString(), (Type) Settings.class);

                        settings= new Gson().fromJson(response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").getJSONObject("Settings").toString(), (Type) Settings.class);
//                editor.apply();
                        preferencess.setSettings(settings, GlobalVariables.SettingsDto);
                        Method.settings=settings;
                        // Log.d(TAG, "backResponse: "+settings.getDailytaskCoin());
                        GlobalVariables.settings= settings;
                      //  editor.apply();


                        GlobalVariables.codeGen= new Gson().fromJson(response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").getJSONObject("codeGen").toString(), (Type) codeGen.class);

                        Log.d(TAG, "backResponse: "+GlobalVariables.settings.getPublisherId());



                        Log.d(GlobalVariables.TAG, "hKINGSN123:" + GlobalVariables.codeGen.getStudentName() );

                        setData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                    try {
                        alertDialogBuilder.setTitle(response.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    alertDialogBuilder.setMessage(message);
                    alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                    alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.ok_message),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Method.loadingDialog.dismiss();
                                    activity.finish();
                                    startActivity(new Intent(activity, LoginActivity.class));
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

    @SuppressLint("SetTextI18n")
    private void setData()
    {
       // qrtime1=0;
        mBinding.textViewCountdown.setText(R.string._00_00);
        //HomeActivity.alert(requireActivity());


        mBinding.nametxt.setText(GlobalVariables.codeGen.getStudentName());
        mBinding.idtxt.setText(GlobalVariables.codeGen.getIdNumber());
        mBinding.pincodetxt.setText(GlobalVariables.codeGen.getPinCode());
        mBinding.citytxt.setText(GlobalVariables.codeGen.getEcity());

        toGenText= (GlobalVariables.codeGen.getStudentName())+"\n"
                +(GlobalVariables.codeGen.getIdNumber())+"\n"
                +(GlobalVariables.codeGen.getEcity())+"\n"
                +(GlobalVariables.codeGen.getPinCode());

        preferencess.setValue("toGenText", toGenText);
        // editor.apply();

        Log.d(TAG, "setData: "+toGenText);

        // Set the percentage of language used
        mBinding.totalcodes.setText("Total Codes : "+ userDTO.getTotalAllQrGeneration());
        mBinding.correctcodes.setText("Today's Codes : "+userDTO.getTodaysCodes());
        mBinding.wrongcodes.setText("History Day's : " + ((parseInt(userDTO.getTotalQrGeneration()))));

        String innerVal=mBinding.totalcodes.getText().toString()+"\n" +mBinding.correctcodes.getText().toString();
        int wrongcodes=((parseInt(userDTO.getTotalAllQrGeneration())) - ((parseInt(userDTO.getCorrectQrGeneration()))));
        int historydays= parseInt(userDTO.getTotalQrGeneration());
        // Set the percentage of language used

        int a= parseInt(userDTO.getTodaysCodes());
        int b= parseInt(userDTO.getTotalAllQrGeneration());
        D1 = (int) (((double) a / (double) b) * 50);

        D2 = (int) (((double) historydays / (double) 30) * 50);

        //    Toast.makeText(getContext(),String.valueOf(x), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "setData: "+(parseInt(userDTO.getCorrectQrGeneration())+ parseInt(userDTO.getTotalAllQrGeneration())));
        //  D2=(((wrongcodes)/Integer.parseInt(userDTO.getTotalAllQrGeneration()))*100);
        //  Toast.makeText(getContext(),String.valueOf(D1)+""+D2, Toast.LENGTH_SHORT).show();

        mBinding.piechart.clearChart();

         mBinding.piechart.setUseInnerValue(true);
        //  mBinding.piechart.getInnerPaddingColor();

        mBinding.piechart.setInnerPadding(5);
         mBinding.piechart.setHighlightStrength(D2);
         mBinding.piechart.setInnerValueSize(20);
         mBinding.piechart.setInnerValueString(innerVal);
        // mBinding.piechart.setInnerPaddingColor(Color.parseColor("#E0E0E0"));
       //  mBinding.piechart.setInnerPaddingColor(R.color.white);
         mBinding.piechart.setInnerValueColor(R.color.transparent);
          mBinding.piechart.setInnerPadding(0);


        /* "\n"+"Wallet Balance : "+preferences.getFloat(GlobalVariables.USER_COINS,0)+ " ₹"*/
        // Set the data and color to the pie chart



         mBinding.piechart.addPieSlice(
                new PieModel(
                        "D3",
                        parseInt(String.valueOf(D2)),
                        // Integer.parseInt(correctcodes.getText().toString()),
                        Color.parseColor("#66BB6A")));

         mBinding.piechart.addPieSlice(
                new PieModel(
                        "D4",
                        // Integer.parseInt(wrongcodes.getText().toString()),
                        parseInt(String.valueOf(30)),
                        Color.parseColor("#EF5350")));

         mBinding.piechart.addPieSlice(
                new PieModel(
                        "D1",

                        parseInt(String.valueOf(D1)),
                        Color.parseColor("#FFA726")));

         mBinding.piechart.addPieSlice(
                new PieModel(
                        "D2",

                        parseInt(String.valueOf(b)),
                        Color.parseColor("#FFA726")));

        // To animate the pie chart
         mBinding.piechart.startAnimation();


        new Handler().postDelayed(() -> {
            Method.loadingDialog.dismiss();
            //  method.preferencess.setValue("intent","");
            if(!Objects.equals(Method.userDTO.getOnesignalPushtoken(), method.preferencess.getValue(GlobalVariables.DEVICE_TOKEN)))
            {

                method.params.clear();
                method.params.put("userMobile",Method.userDTO.getMobile());
                method.params.put("push_token",method.preferencess.getValue(GlobalVariables.DEVICE_TOKEN));
                method.updateSetting(requireActivity(),RestAPI.API_PUSH_TOKEN);


            }
        }, parseInt(GlobalVariables.usermDTO.getCodegenTimer())* 1000L);


        method.alert(requireActivity());


    }

    @Override
    public void onResume() {
        super.onResume();
        //  mInterstitialAd.loadAd(new AdRequest.Builder().build());
        //  LoadSettings();
      //  timerStarted = false;
        if(!timerStarted){
            getHomeData(requireActivity());
        }

    }


    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
               /* mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);*/
            }
        }.start();

        mTimerRunning = true;
       /* mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);*/
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;

    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();

    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mBinding.textViewCountdown.setText(timeLeftFormatted);
        Log.e("timer", "updateCountDownText: "+ timeLeftFormatted);
    }

    private void startTimer2()
    {
        if (getActivity() != null) {
            timerTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    requireActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            time++;
                            mBinding.textViewCountdown.setText(getTimerText());
                            timerStarted = true;
                            mTimerRunning = true;

                        }
                    });
                }

            };
            assert timer != null;
            timer.scheduleAtFixedRate(timerTask, 0 ,1000);

        /*    Thread timerTask = new Thread() {
                @Override
                public void run() {
                    try {
                        while (!isInterrupted()) {
                            Thread.sleep(1000);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Calendario para obtener fecha & hora
                                    time++;
                                    mBinding.textViewCountdown.setText(getTimerText());
                                    timerStarted = true;
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        Log.v("InterruptedException", e.getMessage());
                    }

                }
            };*/

        }

    }

    private String getTimerText()
    {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        qrtime=seconds;
        qrtime1=minutes*60+qrtime;
//        editor.putString(GlobalVariables.timer_text, String.valueOf(seconds));
//        editor.apply();
        Log.e("KINGSN", "getTimerText:sec"+qrtime) ;
        Log.d(TAG, "getTimerText: "+qrtime1);
        return formatTime(seconds, minutes, hours);
    }

    @SuppressLint("DefaultLocale")
    private String formatTime(int seconds, int minutes, int hours)
    {
        return String.format("%02d",hours) + " : " + String.format("%02d",minutes) + " : " + String.format("%02d",seconds);

    }

    public void startTapped() {
        Log.d(TAG, "startTapped: "+timerStarted);
        if (!timerStarted) {
            timerStarted = true;
            //setButtonUI("STOP", R.color.red);

            startTimer2();
            //  method.startTimer2(requireActivity(),mTextViewCountDown);
        }

    }
    public void StopTapped()
    {


        timerStarted = false;
        mTimerRunning = false;
        String finaltimerText= String.valueOf(mBinding.textViewCountdown.getText());
        //setButtonUI("START", R.color.green);
        mBinding.textViewCountdown.setText(getTimerText());
        // editor.putString(GlobalVariables.timer_text, (String) mTextViewCountDown.getText());
        Log.e("KINGSN", String.format("startStopTapped:%s", mBinding.textViewCountdown.getText()));
        Log.e("KINGSN", "finalTimer: "+finaltimerText );
        timerTask.cancel();
         mBinding.textViewGenerate.isClickable();{
        // generate.setClickable(false);
    }

    }



    public void enablegen()
    {
        if(mBinding.textViewGenerate.isClickable())  {
            mBinding.textViewGenerate.setClickable(false);
        }else if(!mBinding.textViewGenerate.isClickable()){
            mBinding.textViewGenerate.setClickable(true);
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        if(timerTask != null){
            timerTask.cancel();
            //cancel timer task and assign null
        }
    }

    public void alert(Activity activity){

        if(Method.userDTO.getAllow().equals("2")) {
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
                    activity. finish();

                }
            });

        }else
        if(Method.userDTO.getJoiningPaid().equals("1")) {
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

            if (alertDialog.getWindow() != null){
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }

            alertDialog.show();
            appPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                                        /*startActivity(new Intent(HomeActivity.this, SettingsActivity
                                                .class));*/
                    method.loadingDialogg(activity);
                    // GenerateChecksum();
                    startPayment(requireActivity());
                    // finish();


                }
            });

            appPay3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                                        /*startActivity(new Intent(HomeActivity.this, SettingsActivity
                                                .class));*/
                    method.loadingDialogg(activity);

                    onSuccessPay(activity,orderId);
                    // finish();

                }
            });

        }else
        if(Method.userDTO.getJoiningPaid().equals("3")) {
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
            alertDialog.show();
            appPay2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                                        /*startActivity(new Intent(HomeActivity.this, SettingsActivity
                                                .class));*/
                    method.loadingDialogg(activity);
                    //GenerateChecksum();
                    // finish();
                    activity. finish();
                    //loadingDialog.dismiss();
                    activity.finishAffinity();

                }
            });

            appPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    activity. startActivity(new Intent(activity, ContactActivity
                            .class));
                    //loadingDialog.show();
                    //GenerateChecksum();
                    // finish();
                    activity.finish();
                    Method.loadingDialog.dismiss();


                }
            });

        }
    }

    public  void startPayment(Activity activity){
        switch (GlobalVariables.settings.getPaymentGateway()) {
            case "paytm_gateway":
                GenerateChecksum(activity);
                break;
            case "razorpay_gateway":
                startRazorPayment(activity);
                break;
            case "payumoney_gateway":
                startPayuPayment(activity);
                break;
        }
        if(GlobalVariables.settings.getPaymentGateway().equals("upiId")) {
            String upiId = GlobalVariables.settings.getUpiId();
            String name = (Method.userDTO.getName());
            String desc = "Add Money";
            String note = (Method.userDTO.getMobile());
            // String note = noteEt.getText().toString();
            payUsingUpi(settings.getAppJoiningFee(), upiId, name, note);
        }
    }


    public  void startRazorPayment(Activity activity) {
        Checkout checkout = new Checkout();
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */

        final Checkout co = new Checkout();
        checkout.setKeyID(GlobalVariables.settings.getPayumoneyKey());
        /**
         * Instantiate Checkout
         */


        /**
         * Set your logo here
         */
        checkout.setImage(R.mipmap.ic_launcher_round);

        try {
            JSONObject options = new JSONObject();
            options.put("name", "V1infotech Pvt.Ltd");
            options.put("description", "App Joining Fee");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://v1infotech.com/admin/assets/images/profile");
            options.put("currency", "INR");

            String payment = (GlobalVariables.settings.getAppJoiningFee());

            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);
            JSONObject preFill = new JSONObject();
            preFill.put("email", Method.userDTO.getEmail());
            preFill.put("contact", Method.userDTO.getMobile());
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public  void  GenerateChecksum(Activity activity) {
        method.loadingDialogg(activity);
        Random r = new Random(System.currentTimeMillis());
        orderId = "QRCODE" + (1 + r.nextInt(2)) * 1000
                + r.nextInt(1000);
        //  editor.putString(GlobalVariables.txn_orderId, orderId);


        String url="https://abcdjob.live/admin/paytm1/generateChecksum.php";


        Map<String, String> params = new HashMap<>();
        params.put( "MID" , GlobalVariables.settings.getPaytmMid());
        params.put( "ORDER_ID" , orderId);
        params.put( "CUST_ID" , Method.userDTO.getUserReferalCode());
        params.put( "MOBILE_NO" , Method.userDTO.getMobile());
        params.put( "EMAIL" , Method.userDTO.getEmail());
        params.put( "CHANNEL_ID" , "WAP");
        params.put( "TXN_AMOUNT" , GlobalVariables.settings.getAppJoiningFee());
        params.put( "WEBSITE" , WEBSITE);
        params.put( "INDUSTRY_TYPE_ID" , INDUSTRY_TYPE_ID);
        params.put( "CALLBACK_URL", CALLBACK_URL);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity);
// Request a string response from the provided URL.
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Toast.makeText(HomeActivity.this,response,Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if (jsonObject.has("CHECKSUMHASH"))
                            {
                                checksum=jsonObject.getString("CHECKSUMHASH");
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
                activity.startActivity(requireActivity().getIntent());
                Method.loadingDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<String, String>();
                params.put( "MID" , GlobalVariables.settings.getPaytmMid());
                params.put( "ORDER_ID" , orderId);
                params.put( "CUST_ID" , abcdjob.workonline.com.qrcode.Util.Method.userDTO.getUserReferalCode());
                params.put( "MOBILE_NO" , abcdjob.workonline.com.qrcode.Util.Method.userDTO.getMobile());
                params.put( "EMAIL" , abcdjob.workonline.com.qrcode.Util.Method.userDTO.getEmail());
                params.put( "CHANNEL_ID" , "WAP");
                params.put( "TXN_AMOUNT" , GlobalVariables.settings.getAppJoiningFee());
                params.put( "WEBSITE" , WEBSITE);
                params.put( "INDUSTRY_TYPE_ID" , INDUSTRY_TYPE_ID);
                params.put( "CALLBACK_URL", CALLBACK_URL);

                return params;
            }
        };


        queue.add(stringRequest);
// Access the RequestQueue through your singleton class.
        //MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public  void startPayuPayment(Activity activity){

    }


    public void onStartTransaction(Activity activity) {
        PaytmPGService Service = PaytmPGService.getProductionService();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put( "MID" , GlobalVariables.settings.getPaytmMid());
        // Key in your staging and production MID available in your dashboard


        paramMap.put( "ORDER_ID" , orderId);
        paramMap.put( "CUST_ID" , Method.userDTO.getUserReferalCode());
        paramMap.put( "MOBILE_NO" , Method.userDTO.getMobile());
        paramMap.put( "EMAIL" , Method.userDTO.getEmail());
        paramMap.put( "CHANNEL_ID" , "WAP");
        paramMap.put( "TXN_AMOUNT" , GlobalVariables.settings.getAppJoiningFee());
        paramMap.put( "WEBSITE" , WEBSITE);
        paramMap.put( "INDUSTRY_TYPE_ID" , INDUSTRY_TYPE_ID);
        paramMap.put( "CALLBACK_URL", CALLBACK_URL);
        paramMap.put( "CHECKSUMHASH" , checksum);



        PaytmOrder Order = new PaytmOrder((HashMap<String, String>) paramMap);



        Service.initialize(Order, null);



        Service.startPaymentTransaction(activity, true,
                true, new PaytmPaymentTransactionCallback() {

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {
                        System.out.println("===== onTransactionResponse " + inResponse.toString());
                        if (Objects.equals(inResponse.getString("STATUS"), "TXN_SUCCESS")) {
                            //    Payment Success
                            Toast.makeText(activity," Transaction success",Toast.LENGTH_LONG).show();

                            //uploadData();
                            onSuccessPay(activity,orderId);
                        } else if (!inResponse.getBoolean("STATUS")) {
                            //    Payment Failed
                            Toast.makeText(activity," Transaction Failed",Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(getContext(), HomeActivity.class));
                            activity.finish();
                            activity. startActivity(requireActivity().getIntent());
                            Method.loadingDialog.dismiss();

                        }
                    }

                    @Override
                    public void networkNotAvailable() {
                        // network error
                        //clickOnGenerate();
                        activity. finish();
                        startActivity(requireActivity().getIntent());
                        Method.loadingDialog.dismiss();
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        // AuthenticationFailed
                        activity.finish();
                        startActivity(requireActivity().getIntent());

                        // clickOnGenerate();
                        //startActivity(new Intent(getContext(), HomeActivity.class));
                        Method.loadingDialog.dismiss();
                    }

                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                        // UI Error
                        // clickOnGenerate();
                        activity.finish();
                        startActivity(requireActivity().getIntent());
                        Method.loadingDialog.dismiss();
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                        //  Web page loading error
                        //clickOnGenerate();
                        //startActivity(new Intent(getContext(), HomeActivity.class));
                        activity.finish();
                        startActivity(requireActivity().getIntent());
                        Method.loadingDialog.dismiss();
                    }

                    @Override
                    public void onBackPressedCancelTransaction() {
                        // on cancelling transaction

                        //clickOnGenerate();
                        // startActivity(new Intent(getContext(), HomeActivity.class));
                        activity.finish();
                        startActivity(requireActivity().getIntent());
                        Method.loadingDialog.dismiss();

                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        // maybe same as onBackPressedCancelTransaction()
                        //clickOnGenerate();
                        //startActivity(new Intent(getContext(), HomeActivity.class));
                        activity.finish();
                        startActivity(requireActivity().getIntent());
                        Method.loadingDialog.dismiss();
                    }
                });
    }


    private void onSuccessPay(Activity activity, String orderId) {

        Log.d(TAG, "getParamsOn Payinit: ");

        if(orderId ==null){
            //Toast.makeText(HomeActivity.this, "NULL", Toast.LENGTH_LONG).show();
            //editor.putString(GlobalVariables.txn_orderId, orderId);


            Random r = new Random(System.currentTimeMillis());
            orderId = "BYCASH" + (1 + r.nextInt(2)) * 1000
                    + r.nextInt(1000);
            //  editor.putString(GlobalVariables.txn_orderId, this.orderId);
            method.preferencess.setValue(GlobalVariables.txn_orderId,orderId);

        }
        method.loadingDialogg(activity);
     //   String finalOrderId = orderId;
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
                                  /*  Toast.makeText(activity, "Updated", Toast.LENGTH_SHORT).show();
                                    activity.finish();
                                    // startActivity(getIntent());
                                    Method.loadingDialog.dismiss();
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
                                                    Method.loadingDialog.dismiss();
                                                    activity.finishAffinity();
                                                }
                                            });

                                    android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                   // Toast.makeText(activity, object.getString("msg"), Toast.LENGTH_LONG).show();
*/
                                    Method.alertBox("1", object.getString("title"),object.getString("msg"), activity, "");


                                } else {
                                    Method.loadingDialog.dismiss();
                                   /* android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
                                    alertDialogBuilder.setTitle(object.getString("title"));
                                    alertDialogBuilder.setMessage(object.getString("msg"));
                                    alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                                    alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.ok_message),
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    activity.finish();
                                                    Method.loadingDialog.dismiss();
                                                    activity.finishAffinity();
                                                }
                                            });

                                    android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                    Toast.makeText(activity, object.getString("msg"), Toast.LENGTH_LONG).show();*/

                                    Method.alertBox("1", object.getString("title"),object.getString("msg"), activity, "");
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
                Method.loadingDialog.dismiss();
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(activity);
                alertDialogBuilder.setTitle("Something Went Wrong");
                alertDialogBuilder.setMessage("Please Try With Active Internet ");
                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                alertDialogBuilder.setPositiveButton(activity.getResources().getString(R.string.ok_message),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                activity.finish();
                                Method.loadingDialog.dismiss();
                                activity. finishAffinity();
                            }
                        });

                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        }) { @Override
        protected Map<String, String> getParams() {
            Log.d(TAG, "getParamsOn Pays: ");
            Map<String, String> params = new HashMap<>();
            params.put("app_joining_fee_paid","");
            params.put("user_id", abcdjob.workonline.com.qrcode.Util.Method.userDTO.getMobile());
            params.put("name", abcdjob.workonline.com.qrcode.Util.Method.userDTO.getName());
            params.put("email", abcdjob.workonline.com.qrcode.Util.Method.userDTO.getEmail());
            params.put("paid", abcdjob.workonline.com.qrcode.Util.Method.userDTO.getJoiningPaid());
            params.put("order_id", method.preferencess.getValue(GlobalVariables.txn_orderId));
            params.put("city", abcdjob.workonline.com.qrcode.Util.Method.userDTO.getCity());
            params.put("amount", abcdjob.workonline.com.qrcode.Util.Method.settings.getAppJoiningFee());

            Log.d(TAG, "getParamsOn Pay: "+params);
            return params;
        }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);



    }

    public void payUsingUpi( String amount, String upiId, String name, String note) {

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
        if(null != chooser.resolveActivity(requireActivity().getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(requireActivity(),"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }


   /* public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == UPI_PAYMENT) {
            if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                if (data != null) {
                    String trxt = data.getStringExtra("response");
                    Log.d("UPI", "onActivityResult: " + trxt);
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add(trxt);
                    upiPaymentDataOperation(dataList);
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
            } else {
                Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                ArrayList<String> dataList = new ArrayList<>();
                dataList.add("nothing");
                upiPaymentDataOperation(dataList);
            }
        }

        // Result Code is -1 send from Payumoney activity
        Log.d("MainActivity", "request code " + requestCode + " resultcode " + resultCode);

    }*/

    private void upiPaymentDataOperation(ArrayList<String> data) {
        String str = data.get(0);
        Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
        String paymentCancel = "";
        if(str == null) str = "discard";
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
            Log.d("UPI", "responseStr: "+approvalRefNo);
            // showPay_Status_AlertDialog(1);
            //  onSuccessPay(orderId);
            onSuccessPay(requireActivity(),orderId);
        }
        else if("Payment cancelled by user.".equals(paymentCancel)) {
            //Toast.makeText(Payment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            Toast.makeText(requireActivity()," Transaction Failed",Toast.LENGTH_LONG).show();
            //startActivity(new Intent(getContext(), HomeActivity.class));
            requireActivity().finish();
            requireActivity(). startActivity(requireActivity().getIntent());
            Method.loadingDialog.dismiss();
        }
        else {
            Toast.makeText(requireActivity(), "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            Toast.makeText(requireActivity()," Transaction Failed",Toast.LENGTH_LONG).show();
            //startActivity(new Intent(getContext(), HomeActivity.class));
            requireActivity().finish();
            requireActivity(). startActivity(requireActivity().getIntent());
            Method.loadingDialog.dismiss();
        }

    }





}
