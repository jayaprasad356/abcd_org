package abcdjob.workonline.com.qrcode.ui;

import static android.view.View.*;
import static abcdjob.workonline.com.qrcode.Util.GlobalVariables.AppSid;
import static abcdjob.workonline.com.qrcode.Util.GlobalVariables.btntxt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cocosw.bottomsheet.BottomSheet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import abcdjob.workonline.com.qrcode.Interface.Helper;
import abcdjob.workonline.com.qrcode.Models.GetCommentDTO;
import abcdjob.workonline.com.qrcode.Models.UserDTO;
import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.Util.ImageCompression;
import abcdjob.workonline.com.qrcode.Util.MainFragment;
import abcdjob.workonline.com.qrcode.Util.Method;
import abcdjob.workonline.com.qrcode.Util.RestAPI;
import abcdjob.workonline.com.qrcode.https.HttpsRequest;
import abcdjob.workonline.com.qrcode.network.NetworkManager;
import abcdjob.workonline.com.qrcode.preferences.SharedPrefrence;
import abcdjob.workonline.com.qrcode.ui.adapter.AdapterViewComment;

public class CommentOneByOne extends AppCompatActivity implements OnClickListener, SwipeRefreshLayout.OnRefreshListener{
    private final String TAG = "KINGSN";
    private ListView lvComment;
    private ImageView buttonSendMessage, IVback, emojiButton;
    private AdapterViewComment adapterViewComment;
    private final String id = "";
    private ArrayList<GetCommentDTO> getCommentDTOList;
    private  InputMethodManager inputManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    public EditText edittextMessage;
    private  EmojIconActions emojIcon;
    private RelativeLayout relative;
    private Context mContext;
    //private ChatListDTO chatListDTO;
    public   HashMap<String, String> parmsGet = new HashMap<>();
    private TextView tvNameHedar;
    private SharedPrefrence prefrence;
    private UserDTO userDTO;
    IntentFilter intentFilter = new IntentFilter();
    private Method method;
    RequestQueue rQueue;
    Toolbar toolbar;

    private LinearLayout mContainerActions, mContainerImg;
    private boolean actions_container_visible = false, img_container_visible = false;
    private ImageView mActionImage, mPreviewImg, mDeleteImg, mActionContainerImg;

    BottomSheet.Builder builder;
    Uri picUri;
    int PICK_FROM_CAMERA = 1, PICK_FROM_GALLERY = 2;
    int CROP_CAMERA_IMAGE = 3, CROP_GALLERY_IMAGE = 4;
    String imageName;
    String pathOfImage;
    Bitmap bm;
    private SharedPreferences preferences, preferences1;
    ImageCompression imageCompression;
    byte[] resultByteArray;
    File file;
    Bitmap bitmap = null;
    private final HashMap<String, File> paramsFile = new HashMap<>();
    HashMap<String, String> values = new HashMap<>();
    JSONObject jsonObject;
 Activity activity;
    String encodeImageString;

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.requireNonNull(intent.getAction()).equalsIgnoreCase(GlobalVariables.CHAT_NOTIFICATION)) {
                parmsGet.put(GlobalVariables.adminMobile, getIntent().getStringExtra("sendBy"));
                parmsGet.put(GlobalVariables.userMobile, getIntent().getStringExtra("sendTo"));
                parmsGet.put("ticketId", method.preferencess.getValue("ticketId"));
                getComment();
                Log.e("CHAT_NOTIFICATION","CHAT_NOTIFICATION");
                Log.e(TAG, "Value " + prefrence.getIntValue("Value"));

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mContext = CommentOneByOne.this;
        activity = CommentOneByOne.this;
        jsonObject = new JSONObject();
        prefrence = SharedPrefrence.getInstance(mContext);
        // userDTO = prefrence.getParentUser(Consts.USER_DTO);
        method=new Method(this);
        edittextMessage = findViewById(R.id.edittextMessage);
        preferences = getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        jsonObject = new JSONObject();
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        intentFilter.addAction(GlobalVariables.CHAT_NOTIFICATION);
        toolbar=findViewById(R.id.toolbar);

       /* IVback.setOnClickListener(new OnClickListener() {
            @SuppressLint("MissingSuperCall")
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });*/

        toolbar.setNavigationOnClickListener(view -> {
            super.onBackPressed();
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, intentFilter);


        Log.d(TAG, "onCreateIntent2: "+getIntent().getStringExtra("sendBy"));

        parmsGet.put(GlobalVariables.adminMobile, getIntent().getStringExtra("sendBy"));
        parmsGet.put(GlobalVariables.userMobile, getIntent().getStringExtra("sendTo"));
        parmsGet.put(GlobalVariables.userMobile, Method.userDTO.getMobile());
        parmsGet.put("ticketId", method.preferencess.getValue("ticketId"));


        if (getIntent().hasExtra(GlobalVariables.adminMobile)){
            //chatListDTO = (ChatListDTO) getIntent().getSerializableExtra(GlobalVariables.CHAT_LIST_DTO);

            parmsGet.put(GlobalVariables.adminMobile, getIntent().getStringExtra("sendBy"));
            parmsGet.put(GlobalVariables.userMobile, getIntent().getStringExtra("sendTo"));
            parmsGet.put("ticketId", method.preferencess.getValue("ticketId"));
        }else{
            parmsGet.put(GlobalVariables.adminMobile,Method.settings.getAppContact());
            parmsGet.put(GlobalVariables.userMobile, Method.userDTO.getMobile());
            parmsGet.put("ticketId", method.preferencess.getValue("ticketId"));
        }
        setUiAction();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.e(TAG, "Value " + prefrence.getIntValue("Value"));
    }

    public void setUiAction() {
        mContainerActions = findViewById(R.id.container_actions);
        mContainerActions.setVisibility(GONE);
        mActionImage = findViewById(R.id.addFilesImg);
        mActionImage.setOnClickListener(this);

        mContainerImg = findViewById(R.id.container_img);
        mContainerImg.setVisibility(GONE);

        mPreviewImg = findViewById(R.id.previewImg);
        mDeleteImg = findViewById(R.id.deleteImg);
        mActionContainerImg = findViewById(R.id.actionContainerImg);

       // tvNameHedar = findViewById(R.id.tvNameHedar);
     //   tvNameHedar.setText("Admin Support");
        relative = findViewById(R.id.relative);
        edittextMessage = findViewById(R.id.edittextMessage);
        edittextMessage.setOnClickListener(this);
        emojiButton = findViewById(R.id.emojiButton);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        lvComment = findViewById(R.id.lvComment);
       // etMessage = (CustomEditText) findViewById(R.id.etMessage);
        buttonSendMessage = findViewById(R.id.buttonSendMessage);
       // IVback = findViewById(R.id.IVback);
        buttonSendMessage.setOnClickListener(this);
      //  IVback.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        Log.e("Runnable", "FIRST");
                                        if (NetworkManager.isConnectToInternet(mContext)) {
                                            // swipeRefreshLayout.setRefreshing(true);
                                            if (getIntent().hasExtra(GlobalVariables.adminMobile)){
                                               // chatListDTO = (ChatListDTO) getIntent().getSerializableExtra(GlobalVariables.CHAT_LIST_DTO);

                                                parmsGet.put(GlobalVariables.adminMobile, getIntent().getStringExtra("sendBy"));
                                                parmsGet.put(GlobalVariables.userMobile, getIntent().getStringExtra("sendTo"));
                                            }
                                            getComment();

                                        } else {
                                            method.showToasty(CommentOneByOne.this, "2",getResources().getString(R.string.noInternet));
                                        }
                                    }
                                }
        );

      /*  emojIcon = new EmojIconActions(activity, relative, edittextMessage, emojiButton, "#495C66", "#DCE1E2", "#E6EBEF");
        emojIcon.ShowEmojIcon();
        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e("Keyboard", "open");
                hideActionsContainer();

                if (img_container_visible) {
                    mActionContainerImg.setVisibility(View.GONE);
                }

            }

            @Override
            public void onKeyboardClose() {
                Log.e("Keyboard", "close");
            }
        });*/





   /*     if (pathOfImage != null && pathOfImage.length() > 0)
        {
            mPreviewImg.setImageURI(Uri.fromFile(new File(pathOfImage)));
            showImageContainer();
        }
*/
        mDeleteImg.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v)
            {
                picUri = null;
                pathOfImage = "";

                hideImageContainer();
            }
        });

        mActionContainerImg.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (actions_container_visible)
                {
                    hideActionsContainer();
                    return;
                }

                showActionsContainer();
            }
        });


        builder = new BottomSheet.Builder(CommentOneByOne.this).sheet(R.menu.menu_cards);
        builder.title(getResources().getString(R.string.select_img));
        builder.listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.camera_cards:
                        if (Method.hasPermissionInManifest(CommentOneByOne.this, PICK_FROM_CAMERA, Manifest.permission.CAMERA)) {
                            if (Method.hasPermissionInManifest(CommentOneByOne.this, PICK_FROM_GALLERY, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                try {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    File file = getOutputMediaFile(1);
                                    if (!file.exists()) {
                                        try {

                                            file.createNewFile();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        //Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.asd", newFile);
                                        picUri = FileProvider.getUriForFile(CommentOneByOne.this.getApplicationContext(), CommentOneByOne.this.getApplicationContext().getPackageName() + ".fileprovider", file);
                                    } else {
                                        picUri = Uri.fromFile(file); // create
                                    }

                                    prefrence.setValue(GlobalVariables.IMAGE_URI_CAMERA, picUri.toString());
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri); // set the image file
                                    startActivityForResult(intent, PICK_FROM_CAMERA);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        break;
                    case R.id.gallery_cards:
                        if (Method.hasPermissionInManifest(CommentOneByOne.this, PICK_FROM_CAMERA, Manifest.permission.CAMERA)) {
                            if (Method.hasPermissionInManifest(CommentOneByOne.this, PICK_FROM_GALLERY, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                File file = getOutputMediaFile(1);
                                if (!file.exists()) {
                                    try {
                                        file.createNewFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                picUri = Uri.fromFile(file);

                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_PICK);
                                startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)), PICK_FROM_GALLERY);

                            }
                        }
                        break;
                    case R.id.cancel_cards:
                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        });
                        break;
                }
            }
        });
    }


    private File getOutputMediaFile(int type) {
        String root =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File mediaStorageDir = new File(root, GlobalVariables.APP_NAME);

        /**Create the storage directory if it does not exist*/
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        /**Create a media file name*/
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    GlobalVariables.APP_NAME + timeStamp + ".png");

            // imageName =  Consts.APP_NAME + timeStamp + ".png";
        } else {
            return null;
        }
        return mediaFile;
    }

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CROP_CAMERA_IMAGE) {
            if (data != null) {
                Log.d(TAG, "onActivityResult1: "+(data.getExtras()));
                picUri = Uri.parse(Objects.requireNonNull(data.getExtras()).getString("resultUri"));
                try {
                    //bitmap = MediaStore.Images.Media.getBitmap(SaveDetailsActivityNew.this.getContentResolver(), resultUri);
                    pathOfImage = picUri.getPath();
                    imageCompression = new ImageCompression(mContext);
                    imageCompression.execute(pathOfImage);
                    imageCompression.setOnTaskFinishedEvent(new ImageCompression.AsyncResponse() {
                        @Override
                        public void processFinish(String imagePath) {
                            showImageContainer();
                            Glide.with(activity).load("file://" + imagePath)
                                    .thumbnail(0.5f)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(mPreviewImg);
                            try {
                                // bitmap = MediaStore.Images.Media.getBitmap(SaveDetailsActivityNew.this.getContentResolver(), resultUri);
                                file = new File(imagePath);
                                paramsFile.put(GlobalVariables.IMAGE, file);
                                Log.e("image", imagePath);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == CROP_GALLERY_IMAGE) {
            if (data != null) {
                Log.d(TAG, "onActivityResult2: "+(data.getExtras()));
                picUri = Uri.parse(Objects.requireNonNull(data.getExtras()).getString("resultUri"));

                try {
                    bm = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), picUri);
                    pathOfImage = picUri.getPath();
                    imageCompression = new ImageCompression(mContext);
                    imageCompression.execute(pathOfImage);
                    imageCompression.setOnTaskFinishedEvent(new ImageCompression.AsyncResponse() {
                        @Override
                        public void processFinish(String imagePath) {
                            showImageContainer();
                            Glide.with(mContext).load("file://" + imagePath)
                                    .thumbnail(0.5f)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(mPreviewImg);
                            Log.e("image", imagePath);
                            try {
                                file = new File(imagePath);
                                paramsFile.put(GlobalVariables.IMAGE, file);

                                Log.e("image", imagePath);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == PICK_FROM_CAMERA && resultCode == RESULT_OK) {
            if (picUri != null) {
                //  Log.d(TAG, "onActivityResult3: "+(data.getExtras()));
                picUri = Uri.parse((GlobalVariables.IMAGE_URI_CAMERA));
                startCropping(picUri, CROP_CAMERA_IMAGE);
            } else {
                picUri = Uri.parse((GlobalVariables.IMAGE_URI_CAMERA));
                startCropping(picUri, CROP_CAMERA_IMAGE);
            }
        }
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            try {
                // Log.d(TAG, "onActivityResult4: "+(data.getExtras()));
                Uri tempUri = Objects.requireNonNull(data).getData();
                Log.e("front tempUri", "" + tempUri);
                if (tempUri != null) {
                    startCropping(tempUri, CROP_GALLERY_IMAGE);
                } else {

                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
    public void startCropping(Uri uri, int requestCode) {
        Intent intent = new Intent(CommentOneByOne.this, MainFragment.class);
        intent.putExtra("imageUri", uri.toString());
        intent.putExtra("requestCode", requestCode);
        startActivityForResult(intent, requestCode);
    }

    public void showActionsContainer() {
        actions_container_visible = true;
        mContainerActions.setVisibility(VISIBLE);
        mActionContainerImg.setBackgroundResource(R.drawable.ic_close);
    }

    public void hideActionsContainer() {
        actions_container_visible = false;
        mContainerActions.setVisibility(GONE);
        mActionContainerImg.setBackgroundResource(R.drawable.ic_attach_chat);
    }

    public void showImageContainer() {
        img_container_visible = true;
        mContainerImg.setVisibility(VISIBLE);
        mActionContainerImg.setVisibility(GONE);
    }

    public void hideImageContainer() {
        img_container_visible = false;
        mContainerImg.setVisibility(GONE);
        mActionContainerImg.setVisibility(VISIBLE);
        mActionContainerImg.setBackgroundResource(R.drawable.ic_attach_chat);
        values.put(GlobalVariables.CHAT_TYPE, "1");
    }
    public boolean validateMessage() {
        if (edittextMessage.getText().toString().trim().length() <= 0) {
            edittextMessage.setError(getResources().getString(R.string.write_your_message));
            edittextMessage.requestFocus();
            return false;
        } else {
            edittextMessage.setError(null);
            edittextMessage.clearFocus();
            return true;
        }
    }

    public void submit() throws JSONException {
        if (!validateMessage()) {
            return;
        } else {
            try {
                inputManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {

            }
            if (NetworkManager.isConnectToInternet(mContext)) {
                //doComment1(bm);
                doComment();
            } else {
                method.showToasty(CommentOneByOne.this, "2",getResources().getString(R.string.noInternet));
            }


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSendMessage:
                try {
                    submit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            /*case R.id.IVback:
                finish();
                break;*/
            case R.id.addFilesImg:
                hideActionsContainer();
                builder.show();
                break;
        }
    }

    @Override
    public void onRefresh() {
        Log.e("ONREFREST_Firls", "FIRS");
        getComment();
    }

    public HashMap<String, String> getparm() {
        HashMap<String, String> parms = new HashMap<>();
        parms.put("userMobile", Method.userDTO.getMobile());
        return parms;
    }

  

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        finish();
    }



    public void getComment() {
        // ProjectUtils.showProgressDialog(getActivity(), true, getResources().getString(R.string.please_wait));

        new HttpsRequest(RestAPI.GET_TICKET_COMMENTS_API, parmsGet, mContext).stringPost2(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, JSONObject abcdapp, String title, String message, JSONObject response) {
                //  ProjectUtils.pauseProgressDialog();
                //  binding.swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setRefreshing(false);
                if (flag) {
                    try {
                        getCommentDTOList = new ArrayList<>();
                        Type getpetDTO = new TypeToken<List<GetCommentDTO>>() {
                        }.getType();
                        getCommentDTOList = new Gson().fromJson(response.getJSONObject(AppSid).getJSONArray("Results").toString(), getpetDTO);
                        showData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                }
            }
        });
    }




    public void showData() {
        adapterViewComment = new AdapterViewComment(mContext, getCommentDTOList, Method.userDTO);
        lvComment.setAdapter(adapterViewComment);
        lvComment.setSelection(getCommentDTOList.size() - 1);
    }


    public void doComment() {
        Method.showProgressDialog(mContext,false,"Please Wait While We Uploading Your File");
        values.put("userMobile", Method.userDTO.getMobile());
        values.put(GlobalVariables.USER_ID, Method.userDTO.getUserReferalCode());
        values.put("message", Method.getEditTextValue(edittextMessage));
        values.put("send_by", Method.userDTO.getMobile());
        values.put("sender_name", Method.userDTO.getName());
        values.put("ticketId",  method.preferencess.getValue("ticketId"));


        if (file != null) {
            values.put(GlobalVariables.CHAT_TYPE, "2");
        } else {
            values.put(GlobalVariables.CHAT_TYPE, "1");
        }

        new HttpsRequest(RestAPI.ADD_TICKET_COMMENTS_API, values, paramsFile, mContext).imagePost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, JSONObject abcdapp, String title, String message, JSONObject response) {
                Method.pauseProgressDialog();
                if (flag) {
                    edittextMessage.setText("");
                    hideImageContainer();
                    getComment();
                    file = null;
                    pathOfImage = "";
                } else {
                }

            }
        });

    }



    public void doComment1(Bitmap bm) {

        if (file != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            try {
                // jsonObject = new JSONObject();
                String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());
                jsonObject.put("imagename", imgname);
                //  Log.e("Image name", etxtUpload.getText().toString().trim());
                jsonObject.put("image", encodedImage);
                jsonObject.put("mobile",  getIntent().getStringExtra(GlobalVariables.userMobile));
                jsonObject.put("artist_id",Method.settings.getAppContact());
                jsonObject.put("sender_name",  Method.userDTO.getName());
                jsonObject.put("reciever_name",  getIntent().getStringExtra(GlobalVariables.userName));
                jsonObject.put("email",   Method.userDTO.getEmail());
                jsonObject.put("message", Method.getEditTextValue(edittextMessage));
                jsonObject.put("send_by", Method.userDTO.getName());
                jsonObject.put("city", Method.userDTO.getCity());
                jsonObject.put("chat_type","2");
                // jsonObject.put("aa", "aa");


            } catch (JSONException e) {
                // Log.e(TAG,"JSONObject Here"+ e.toString());
            }


        }else {
            try {
                jsonObject.put("imagename", "");
                jsonObject.put("chat_type","1");
                jsonObject.put("mobile",  getIntent().getStringExtra(GlobalVariables.userMobile));
                jsonObject.put("artist_id",Method.settings.getAppContact());
                jsonObject.put("sender_name",  Method.userDTO.getName());
                jsonObject.put("reciever_name",  getIntent().getStringExtra(GlobalVariables.userName));
                jsonObject.put("email",   Method.userDTO.getEmail());
                jsonObject.put("message", Method.getEditTextValue(edittextMessage));
                jsonObject.put("send_by", Method.userDTO.getName());
                jsonObject.put("city", Method.userDTO.getCity());
                jsonObject.put("chat_type","2");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }




        new HttpsRequest(RestAPI.ADD_TICKET_COMMENTS_API, jsonObject,mContext).stringPostJson(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, JSONObject abcdapp, String title, String message, JSONObject response) {
                Method.pauseProgressDialog();
                if (flag) {
                    edittextMessage.setText("");
                    hideImageContainer();
                    //getComment();
                    file = null;
                    pathOfImage = "";
                } else {

                    Method.alertBox("0",title,message,activity,btntxt);
                }

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            //unregisterReceiver(mBroadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}