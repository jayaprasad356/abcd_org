package abcdjob.workonline.com.qrcode.ui;

import static abcdjob.workonline.com.qrcode.Util.GlobalVariables.AppSid;
import static abcdjob.workonline.com.qrcode.Util.Method.loadingDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import abcdjob.workonline.com.qrcode.Interface.Helper;
import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.Util.Method;
import abcdjob.workonline.com.qrcode.Util.RestAPI;
import abcdjob.workonline.com.qrcode.databinding.ActivityReferBinding;
import abcdjob.workonline.com.qrcode.https.HttpsRequest;
import abcdjob.workonline.com.qrcode.preferences.SharedPrefrence;
import abcdjob.workonline.com.qrcode.ui.adapter.ReferAdapter;
import abcdjob.workonline.com.qrcode.ui.adapter.Refer_Data;
import abcdjob.workonline.com.qrcode.ui.home.HomeActivity;

public class ReferActivity extends AppCompatActivity {
   public  ActivityReferBinding binding;
    public SharedPrefrence preferencess;
    public Method method;
    RecyclerView refer_recycler;
    ReferAdapter referAdapter;
    private List<Refer_Data> refer_data;
    String url,test,url2;
    String ReferCode ;
    Activity activity;
    ArrayList<Refer_Data> mlmDTOArrayList = new ArrayList<>();
    private List<Refer_Data> mlm_data;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=ReferActivity.this;
        method=new Method(activity);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_refer);

        refer_recycler=findViewById(R.id.refer_recycler);

        refer_data = new ArrayList<>();
        refer_recycler.setLayoutManager(new LinearLayoutManager(ReferActivity.this));
        referAdapter= new ReferAdapter(refer_data,ReferActivity.this);
      //  ReferCode=preferences.getString(GlobalVariables.USER_REFERAL_CODE,"");


        binding.toolbar.setNavigationOnClickListener(view -> {
            super.onBackPressed();
        });

        getReferal(activity);

    }


    public void getReferal(Activity activity) {

        method.loadingDialogg(activity);
        method.params.clear();
        method.params.put("mobile",Method.userDTO.getMobile() );
        method.params.put("user_Referal_history","");
        method.params.put("users_id",Method.userDTO.getMobile());
        method.params.put("refer_code",Method.userDTO.getUserReferalCode());

        // method.showToasty(activity,"1",""+GlobalVariables.adminUserID);
        Log.d(GlobalVariables.TAG, "getHomeData2refer: called"+activity.toString());
        new HttpsRequest(RestAPI.API_Refer_History, method.params, activity).stringPost2(GlobalVariables.TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, JSONObject abcdapp, String title, String message, JSONObject response) {
                //  ProjectUtils.pauseProgressDialog();
                //  binding.swipeRefreshLayout.setRefreshing(false);
                if (flag) {
                    // binding.tvNo.setVisibility(View.GONE);

                    Log.e("KINGSN", "onResponse:Refer "+response.length());
                    Log.e("KINGSN", "onResponse:Refer23 "+response);
                    try {
                        //  Log.d(GlobalVariables.TAG, "getIDhk:" + response.getJSONObject(GlobalVariables.AppSid).getJSONObject("Results").toString());

                        mlmDTOArrayList = new ArrayList<>();
                        Type getpetDTO = new TypeToken<List<Refer_Data>>() {
                        }.getType();
                        mlmDTOArrayList = (ArrayList<Refer_Data>) new Gson().fromJson(response.getJSONObject(AppSid).getJSONArray("Results").toString(), getpetDTO);
                        Log.e("KINGSN", "onResponse:Refer234 "+response);
                        //  setData();

                        if (mlmDTOArrayList.size() > 0) {
                            binding.referRecycler.setLayoutManager(new LinearLayoutManager(activity));
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                            binding.referRecycler.setLayoutManager(linearLayoutManager);
                            referAdapter= new ReferAdapter(mlmDTOArrayList,ReferActivity.this);
                            binding.referRecycler.getRecycledViewPool().clear();
                            binding.referRecycler.setItemAnimator(null);
                            refer_recycler.setAdapter(referAdapter);

         /*   adapterCustomerBooking = new AdapterCustomerBooking(MyBooking.this, baseActivity, userBookingList, userDTO, "booking");
            rvBooking.setAdapter(adapterCustomerBooking);*/
                        }

                        loadingDialog.dismiss();


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
                                    startActivity(new Intent(activity, HomeActivity.class));
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
}