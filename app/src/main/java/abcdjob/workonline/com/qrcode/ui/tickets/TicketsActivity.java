package abcdjob.workonline.com.qrcode.ui.tickets;

import static abcdjob.workonline.com.qrcode.Util.GlobalVariables.AppSid;
import static abcdjob.workonline.com.qrcode.Util.GlobalVariables.btntxt;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abcdjob.workonline.com.qrcode.Interface.Helper;
import abcdjob.workonline.com.qrcode.Models.TicketDTO;
import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.Util.Method;
import abcdjob.workonline.com.qrcode.Util.RestAPI;
import abcdjob.workonline.com.qrcode.databinding.ActivityTicketBinding;
import abcdjob.workonline.com.qrcode.databinding.DailogAddTicketBinding;
import abcdjob.workonline.com.qrcode.https.HttpsRequest;
import abcdjob.workonline.com.qrcode.preferences.SharedPrefrence;
import abcdjob.workonline.com.qrcode.ui.adapter.TicketAdapter;

public class TicketsActivity extends AppCompatActivity {
    private final String TAG = TicketsActivity.class.getSimpleName();
    ActivityTicketBinding binding;
    private RecyclerView RVhistorylist;
    private TicketAdapter ticketAdapter;
    private ArrayList<TicketDTO> ticketDTOSList;
    private LinearLayoutManager mLayoutManager;
    private SharedPrefrence prefrence;
    private View view;
    private ImageView ivPost;
    private Dialog dialog;
    public Method method;
    DailogAddTicketBinding dailogBinding;


    private final HashMap<String, String> parmsadd = new HashMap<>();
    TicketsActivity activity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=TicketsActivity.this;
       /* binding = DataBindingUtil.setContentView(this, R.layout.activity_ticket);*/
        binding=ActivityTicketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        method=new Method(this);
        setUiAction(view);

        binding.toolbar.setNavigationOnClickListener(view -> {
            super.onBackPressed();
        });
    }

    public void setUiAction(View v) {

        mLayoutManager = new LinearLayoutManager(activity);
        binding.RVhistorylist.setLayoutManager(mLayoutManager);

        binding.ivPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogshow();
              /*  if (NetworkManager.isConnectToInternet(activity)) {
                    dialogshow();
                } else {
                    method.showToasty(activity, "2",activity.getString(R.string.noInternet));
                }*/
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
     /*   if (method.isOnline(activity)) {


        } else {
            method.showToasty(activity, "2", activity.getResources().getString(R.string.noInternet));
        }*/
       // method.loadingDialogg(activity);
        getTicket();
    }

    public void getTicket() {
       // ProjectUtils.showProgressDialog(getActivity(), true, getResources().getString(R.string.please_wait));

        new HttpsRequest(RestAPI.GET_MY_TICKET_API, getparm(), activity).stringPost2(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, JSONObject abcdapp, String title, String message, JSONObject response) {
                //  ProjectUtils.pauseProgressDialog();
                //  binding.swipeRefreshLayout.setRefreshing(false);
                if (flag) {
                 //   Method.loadingDialog.dismiss();
                    binding.tvNo.setVisibility(View.GONE);
                    binding.RVhistorylist.setVisibility(View.VISIBLE);
                    try {
                        ticketDTOSList = new ArrayList<>();
                        Type getpetDTO = new TypeToken<List<TicketDTO>>() {
                        }.getType();
                        ticketDTOSList = (ArrayList<TicketDTO>) new Gson().fromJson(response.getJSONObject(AppSid).getJSONArray("Results").toString(), getpetDTO);
                        showData();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    binding.tvNo.setVisibility(View.VISIBLE);
                   binding. RVhistorylist.setVisibility(View.GONE);
                }
            }


        });
    }

    public HashMap<String, String> getparm() {
        HashMap<String, String> parms = new HashMap<>();
        parms.put("user_mobile", Method.userDTO.getMobile());
        return parms;
    }

    public void showData() {
        ticketAdapter = new TicketAdapter(ticketDTOSList,activity);
        binding.RVhistorylist.setAdapter(ticketAdapter);
    }



    public void dialogshow() {


        // Inflate dialog main
        dailogBinding=DailogAddTicketBinding.inflate(getLayoutInflater());

        // Initialize dialog
        dialog=new Dialog(TicketsActivity.this);

        // set background transparent
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(
                Color.TRANSPARENT
        ));
        // set view
        dialog.setContentView(dailogBinding.getRoot());

        dailogBinding.btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dailogBinding.submitTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
        // display dialog
        dialog.show();

    }


    public void submitForm() {
        if (!validateReason()) {
            return;
        } else if (!validateDescription()) {
            return;
        } else {
            addTicket();

        }
    }

    public boolean validateReason() {
        if (dailogBinding.etReason.getText().toString().trim().equalsIgnoreCase("")) {
            dailogBinding.etReason.setError(getResources().getString(R.string.val_title));
            dailogBinding.etReason.requestFocus();
            return false;
        } else {
            dailogBinding.etReason.setError(null);
            dailogBinding.etReason.clearFocus();
            return true;
        }
    }

    public boolean validateDescription() {
        if (dailogBinding.etDescription.getText().toString().trim().equalsIgnoreCase("")) {
            dailogBinding.etDescription.setError(getResources().getString(R.string.val_description));
            dailogBinding.etDescription.requestFocus();
            return false;
        } else {
            dailogBinding.etDescription.setError(null);
            dailogBinding.etDescription.clearFocus();
            return true;
        }
    }
    public void addTicket() {
        parmsadd.put("description", Method.getEditTextValue(dailogBinding.etDescription));
        parmsadd.put("reason", Method.getEditTextValue(dailogBinding.etReason));
        parmsadd.put(GlobalVariables.userMobile, Method.userDTO.getMobile());
        Method.showProgressDialog(this, false, getResources().getString(R.string.please_wait));

        new HttpsRequest(RestAPI.GENERATE_TICKET_API, parmsadd, activity).stringPost2(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, JSONObject abcdapp, String title, String message, JSONObject response) throws JSONException {

                Method.pauseProgressDialog();
                if (flag) {
                    dialog.dismiss();
                    //ProjectUtils.showToast(activity, msg);
                    Method.alertBox("0",title,message,activity,btntxt);
                    getTicket();
                } else {
                    Method.alertBox("0",title,message,activity,btntxt);
                }
            }
        });
    }
}
