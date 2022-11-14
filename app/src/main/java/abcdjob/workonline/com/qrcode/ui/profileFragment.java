package abcdjob.workonline.com.qrcode.ui;

import static abcdjob.workonline.com.qrcode.Util.Method.userDTO;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.HashMap;

import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.Util.Method;
import abcdjob.workonline.com.qrcode.Util.RestAPI;
import abcdjob.workonline.com.qrcode.databinding.FragmentProfileBinding;
import abcdjob.workonline.com.qrcode.preferences.SharedPrefrence;


public class profileFragment extends Fragment {

    public  FragmentProfileBinding binding;
    public SharedPrefrence preferencess;
    public Method method;
    public HashMap<String, String> params = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


              method=new Method(requireActivity());

        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        method=new Method(requireActivity());
        binding = FragmentProfileBinding.inflate(inflater, container, false);

      Setdata();

        return binding.getRoot();
    }

    private void Setdata() {
        binding.profileName.setText(userDTO.getName());
        binding.emailId.setText(userDTO.getEmail());
        binding.mobileNo.setText(userDTO.getMobile());
       binding.memberTv.setText(Method.convertTimestampDateToTime((userDTO.getJoiningTime())));

        binding.totalEarned.setText(userDTO.getTotalPaid());
        binding.totalReferals.setText(userDTO.getTotalReferals());
        binding.totalQrgen.setText(userDTO.getTotalAllQrGeneration());
        binding.subsUpto.setText(userDTO.getTotalPaid());


        binding.btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupAddBankDialog();
            }
        });
    }




    void setupAddBankDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View view = LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_edit_profile, binding.getRoot().findViewById(R.id.addprofile));
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        EditText BankName = view.findViewById(R.id.autoCompleteTvBankName);
        EditText nameEt = view.findViewById(R.id.nameEt);
        EditText emailET = view.findViewById(R.id.emailET);
        EditText passwordET = view.findViewById(R.id.passwordET);
        EditText mobileNo = view.findViewById(R.id.mobileNo);
        EditText cityET = view.findViewById(R.id.cityET);

        nameEt.setText(userDTO.getName());
        BankName.setText(userDTO.getName());
        emailET.setText(userDTO.getEmail());
        passwordET.setText(userDTO.getPassword());
        mobileNo.setText(userDTO.getMobile());
        cityET.setText(userDTO.getCity());

        view.findViewById(R.id.submitProfile).setOnClickListener(view1 -> {
            if(nameEt.getText().toString().equals("")){
                nameEt.setError("Field Is Required");
            }if(emailET.getText().toString().equals("")){
                emailET.setError("Field Is Required");
            }if(passwordET.getText().toString().equals("")){
                passwordET.setError("Field Is Required");
            }if(mobileNo.getText().toString().equals("")){
                mobileNo.setError("Field Is Required");
            }if(cityET.getText().toString().equals("")){
                cityET.setError("Field Is Required");
            }else {

                method.params.clear();
                method.params.put("users_profile_update","bank");
                method.params.put("name",nameEt.getText().toString());
                method.params.put("email",emailET.getText().toString());
                method.params.put("city",cityET.getText().toString());
                method.params.put("password",passwordET.getText().toString());
                method.params.put("userMobile",mobileNo.getText().toString());
                method.params.put("device_token",method.preferencess.getValue(GlobalVariables.DEVICE_TOKEN));
                //  Toast.makeText(WithdrawDetailsActivity.this, "Added Bank Account", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                method.updateSetting(requireActivity(),RestAPI.API_update_profile);
            }
        });

        view.findViewById(R.id.btnDismissAddBank).setOnClickListener(view1 -> {
            alertDialog.dismiss();

        });

        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }



}