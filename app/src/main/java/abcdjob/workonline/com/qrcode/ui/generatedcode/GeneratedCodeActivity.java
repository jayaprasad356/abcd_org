package abcdjob.workonline.com.qrcode.ui.generatedcode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import org.json.JSONException;
import org.json.JSONObject;

import abcdjob.workonline.com.qrcode.Interface.Helper;
import abcdjob.workonline.com.qrcode.Models.Settings;
import abcdjob.workonline.com.qrcode.databinding.ActivityGeneratedCodeBinding;
import abcdjob.workonline.com.qrcode.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.Util.Method;
import abcdjob.workonline.com.qrcode.Util.RestAPI;
import abcdjob.workonline.com.qrcode.https.HttpsRequest;
import abcdjob.workonline.com.qrcode.ui.home.HomeActivity;
import abcdjob.workonline.com.qrcode.ui.splash.SplashActivity;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.helpers.constant.AppConstants;
import abcdjob.workonline.com.qrcode.helpers.constant.IntentKey;
import abcdjob.workonline.com.qrcode.helpers.model.Code;
import abcdjob.workonline.com.qrcode.helpers.util.FileUtil;
import abcdjob.workonline.com.qrcode.helpers.util.PermissionUtil;
import abcdjob.workonline.com.qrcode.helpers.util.ProgressDialogUtil;
import abcdjob.workonline.com.qrcode.ui.settings.SettingsActivity;


public class GeneratedCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private final int REQUEST_CODE_TO_SHARE = 1;
    private final int REQUEST_CODE_TO_SAVE = 2;
    private final int REQUEST_CODE_TO_PRINT = 3;

    private ActivityGeneratedCodeBinding mBinding;
    private Menu mToolbarMenu;
    private Code mCurrentCode;
    private Bitmap mCurrentGeneratedCodeBitmap;
    private File mCurrentCodeFile, mCurrentPrintedFile;
    private CompositeDisposable mCompositeDisposable;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Dialog loadingDialog;
    private  Method method;

    public Boolean inrequest;

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        mCompositeDisposable = compositeDisposable;
    }

    public File getCurrentPrintedFile() {
        return mCurrentPrintedFile;
    }

    public void setCurrentPrintedFile(File currentPrintedFile) {
        mCurrentPrintedFile = currentPrintedFile;
    }

    public File getCurrentCodeFile() {
        return mCurrentCodeFile;
    }

    public void setCurrentCodeFile(File currentCodeFile) {
        mCurrentCodeFile = currentCodeFile;
    }

    public Code getCurrentCode() {
        return mCurrentCode;
    }

    public void setCurrentCode(Code currentCode) {
        mCurrentCode = currentCode;
    }

    public Menu getToolbarMenu() {
        return mToolbarMenu;
    }

    public void setToolbarMenu(Menu toolbarMenu) {
        mToolbarMenu = toolbarMenu;
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_generated_code);
        setCompositeDisposable(new CompositeDisposable());
        preferences = GeneratedCodeActivity.this.getSharedPreferences(GlobalVariables.ADMIN_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
     //   Log.d(GlobalVariables.TAG, "dto: "+GlobalVariables.usermDTO.getEmail());

        method =new Method(this);
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.lotiee_loading);
        loadingDialog.setCancelable(false);
        Objects.requireNonNull(loadingDialog.getWindow()).setBackgroundDrawableResource(
                R.color.transparent);

        getWindow().setBackgroundDrawable(null);
        Method ads=new Method(getApplicationContext());
        initializeToolbar();
       // GlobalVariables.adModelCount = new AdModelCount("true","true","true","GeneratedCodeActivity");

        loadQRCode();
        setListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getCompositeDisposable().dispose();
    }

    private void setListeners() {
        mBinding.imageViewSave.setOnClickListener(this);
        mBinding.imageViewShare.setOnClickListener(this);
        mBinding.imageViewPrint.setOnClickListener(this);
    }

    private void loadQRCode() {
        Intent intent = getIntent();

        if (intent != null) {
            Bundle bundle = intent.getExtras();

            if (bundle != null && bundle.containsKey(IntentKey.MODEL)) {
                setCurrentCode(bundle.getParcelable(IntentKey.MODEL));
                if (GlobalVariables.settings.getCallIndustrialOn().equalsIgnoreCase("on_wrong_code")
                        || GlobalVariables.settings.getCallIndustrialOn().equalsIgnoreCase("both"))
                    Log.e("KINGSN", "Txn_amount"+ (preferences.getString(GlobalVariables.Txn_amount, "")));

                {
                    if (!Objects.equals(method.preferencess.getValue(GlobalVariables.Txn_amount), method.preferencess.getValue(GlobalVariables.QR_COIN)))
                    {
                        Log.e("KINGSN", "Add Called");
                        //GlobalVariables.adModelCount = new AdModelCount("true","true","true","GeneratedCodeActivity");
                        GlobalVariables.adModelCount.setGenerate_clicked("GeneratedCodeActivity");
                        Method ads=new Method(GeneratedCodeActivity.this);
                     //   ads.callindustrial(GeneratedCodeActivity.this);
                        // ads.callindustrial(HomeActivity.this);
                    }// insertwallet();


                }
            }


           // insertwallet();
            getAllData(GeneratedCodeActivity.this);
        }

        if (getCurrentCode() != null) {
            ProgressDialogUtil.on().showProgressDialog(this);

            mBinding.textViewContent.setText(String.format(Locale.ENGLISH,
                    getString(R.string.content), getCurrentCode().getContent()));

            mBinding.textViewType.setText(String.format(Locale.ENGLISH, getString(R.string.code_type),
                    getResources().getStringArray(R.array.code_types)[getCurrentCode().getType()]));

            BarcodeFormat barcodeFormat;
            switch (getCurrentCode().getType()) {
                case Code.BAR_CODE:
                    barcodeFormat = BarcodeFormat.CODE_128;
                    break;

                case Code.QR_CODE:
                    barcodeFormat = BarcodeFormat.QR_CODE;
                    break;

                default:
                    barcodeFormat = null;
                    break;
            }

            if (barcodeFormat != null) {
                try {
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.encodeBitmap(getCurrentCode().getContent(),
                            barcodeFormat, 1000, 1000);
                    mBinding.imageViewGeneratedCode.setImageBitmap(bitmap);
                    mCurrentGeneratedCodeBitmap = bitmap;
                } catch (Exception e) {
                    if (!TextUtils.isEmpty(e.getMessage())) {
                        Log.e(getClass().getSimpleName(), Objects.requireNonNull(e.getMessage()));
                    }
                }
            }

            ProgressDialogUtil.on().hideProgressDialog();
        }


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

            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar_menu, menu);
        setToolbarMenu(menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_print:
                if (PermissionUtil.on().requestPermission(this,
                        REQUEST_CODE_TO_PRINT, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (getCurrentPrintedFile() == null) {
                        storeCodeDocument();
                    } else {
                        Toast.makeText(this,
                                getString(R.string.generated_qr_code_already_exists),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.image_view_save:
                if (PermissionUtil.on().requestPermission(this,
                        REQUEST_CODE_TO_SAVE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (getCurrentCodeFile() == null) {
                        storeCodeImage(true);
                    } else {
                        Toast.makeText(this,
                                getString(R.string.generated_qr_code_already_exists),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.image_view_share:
                if (PermissionUtil.on().requestPermission(this,
                        REQUEST_CODE_TO_SHARE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (getCurrentCodeFile() == null) {
                        storeCodeImage(false);
                    } else {
                        shareCode(getCurrentCodeFile());
                    }
                }
                break;

            default:
                break;
        }
    }

    private void storeCodeImage(boolean justSave) {
        ProgressDialogUtil.on().showProgressDialog(this);

        getCompositeDisposable().add(
                Completable.create(emitter -> {
                    String type = getResources().getStringArray(R.array.code_types)[getCurrentCode().getType()];
                    @SuppressLint("StringFormatMatches") File codeImageFile = FileUtil.getEmptyFile(this, AppConstants.PREFIX_IMAGE,
                            String.format(Locale.ENGLISH, getString(R.string.file_name_body),
                                    type.substring(0, type.indexOf(" Code")),
                                    System.currentTimeMillis()),
                            AppConstants.SUFFIX_IMAGE,
                            Environment.DIRECTORY_PICTURES);

                    if (codeImageFile != null && mCurrentGeneratedCodeBitmap != null) {
                        try (FileOutputStream out = new FileOutputStream(codeImageFile)) {
                            mCurrentGeneratedCodeBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                            setCurrentCodeFile(codeImageFile);

                            if (!emitter.isDisposed()) {
                                emitter.onComplete();
                            }
                        } catch (IOException e) {
                            if (!emitter.isDisposed()) {
                                emitter.onError(e);
                            }
                        }
                    } else {
                        if (!emitter.isDisposed()) {
                            emitter.onError(new NullPointerException());
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                ProgressDialogUtil.on().hideProgressDialog();
                                if (justSave) {
                                    Toast.makeText(GeneratedCodeActivity.this,
                                            getString(R.string.saved_the_code_successfully),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    shareCode(getCurrentCodeFile());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (e != null && !TextUtils.isEmpty(e.getMessage())) {
                                    Log.e(getClass().getSimpleName(), e.getMessage());
                                }

                                ProgressDialogUtil.on().hideProgressDialog();
                                if (justSave) {
                                    Toast.makeText(GeneratedCodeActivity.this,
                                            getString(R.string.failed_to_save_the_code),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(GeneratedCodeActivity.this,
                                            getString(R.string.failed_to_share_the_code), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }));
    }

    private void storeCodeDocument() {
        ProgressDialogUtil.on().showProgressDialog(this);

        getCompositeDisposable().add(
                Completable.create(emitter -> {
                    String type = getResources().getStringArray(R.array.code_types)[getCurrentCode().getType()];
                    @SuppressLint("StringFormatMatches") File codeDocumentFile = FileUtil.getEmptyFile(this, AppConstants.PREFIX_CODE,
                            String.format(Locale.ENGLISH, getString(R.string.file_name_body),
                                    type.substring(0, type.indexOf(" Code")),
                                    System.currentTimeMillis()),
                            AppConstants.SUFFIX_CODE,
                            Environment.DIRECTORY_DOCUMENTS);

                    if (codeDocumentFile != null && mCurrentGeneratedCodeBitmap != null && getCurrentCode() != null) {
                        try {
                            Document document = new Document();

                            PdfWriter.getInstance(document, new FileOutputStream(codeDocumentFile));

                            document.open();
                            document.setPageSize(PageSize.A4);
                            document.addCreationDate();
                            document.addAuthor(getString(R.string.app_name));
                            document.addCreator(getString(R.string.app_name));

                            BaseColor colorAccent = new BaseColor(0, 153, 204, 255);
                            float headingFontSize = 20.0f;
                            float valueFontSize = 26.0f;

                            BaseFont baseFont = BaseFont.createFont("res/font/opensans_regular.ttf", "UTF-8", BaseFont.EMBEDDED);

                            LineSeparator lineSeparator = new LineSeparator();
                            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

                            // Adding Title....
                            Font mOrderDetailsTitleFont = new Font(baseFont, 36.0f, Font.NORMAL, BaseColor.BLACK);
                            Chunk mOrderDetailsTitleChunk = new Chunk("Code Details", mOrderDetailsTitleFont);
                            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
                            mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
                            document.add(mOrderDetailsTitleParagraph);

                            document.add(new Paragraph(AppConstants.EMPTY_STRING));
                            document.add(Chunk.NEWLINE);
                            document.add(new Paragraph(AppConstants.EMPTY_STRING));
                            document.add(new Paragraph(AppConstants.EMPTY_STRING));
                            document.add(Chunk.NEWLINE);
                            document.add(new Paragraph(AppConstants.EMPTY_STRING));

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            mCurrentGeneratedCodeBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            Image codeImage = Image.getInstance(stream.toByteArray());
                            codeImage.setAlignment(Image.ALIGN_CENTER);
                            codeImage.scalePercent(40);
                            Paragraph imageParagraph = new Paragraph();
                            imageParagraph.add(codeImage);
                            document.add(imageParagraph);

                            document.add(new Paragraph(AppConstants.EMPTY_STRING));
                            document.add(Chunk.NEWLINE);
                            document.add(new Paragraph(AppConstants.EMPTY_STRING));

                            // Adding Chunks for Title and value
                            Font mOrderIdFont = new Font(baseFont, headingFontSize, Font.NORMAL, colorAccent);
                            Chunk mOrderIdChunk = new Chunk("Content:", mOrderIdFont);
                            Paragraph mOrderIdParagraph = new Paragraph(mOrderIdChunk);
                            document.add(mOrderIdParagraph);

                            Font mOrderIdValueFont = new Font(baseFont, valueFontSize, Font.NORMAL, BaseColor.BLACK);
                            Chunk mOrderIdValueChunk = new Chunk(getCurrentCode().getContent(), mOrderIdValueFont);
                            Paragraph mOrderIdValueParagraph = new Paragraph(mOrderIdValueChunk);
                            document.add(mOrderIdValueParagraph);

                            document.add(new Paragraph(AppConstants.EMPTY_STRING));
                            document.add(Chunk.NEWLINE);
                            document.add(new Paragraph(AppConstants.EMPTY_STRING));

                            // Fields of Order Details...
                            Font mOrderDateFont = new Font(baseFont, headingFontSize, Font.NORMAL, colorAccent);
                            Chunk mOrderDateChunk = new Chunk("Type:", mOrderDateFont);
                            Paragraph mOrderDateParagraph = new Paragraph(mOrderDateChunk);
                            document.add(mOrderDateParagraph);

                            Font mOrderDateValueFont = new Font(baseFont, valueFontSize, Font.NORMAL, BaseColor.BLACK);
                            Chunk mOrderDateValueChunk = new Chunk(type, mOrderDateValueFont);
                            Paragraph mOrderDateValueParagraph = new Paragraph(mOrderDateValueChunk);
                            document.add(mOrderDateValueParagraph);

                            document.close();

                            setCurrentPrintedFile(codeDocumentFile);
                            if (!emitter.isDisposed()) {
                                emitter.onComplete();
                            }
                        } catch (IOException | DocumentException ie) {
                            if (!emitter.isDisposed()) {
                                emitter.onError(ie);
                            }
                        } catch (ActivityNotFoundException ae) {
                            if (!emitter.isDisposed()) {
                                emitter.onError(ae);
                            }
                        }
                    } else {
                        if (!emitter.isDisposed()) {
                            emitter.onError(new NullPointerException());
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                ProgressDialogUtil.on().hideProgressDialog();
                                Toast.makeText(GeneratedCodeActivity.this,
                                        getString(R.string.saved_the_code_successfully),
                                        Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (e != null && !TextUtils.isEmpty(e.getMessage())) {
                                    Log.e(getClass().getSimpleName(), e.getMessage());
                                }

                                ProgressDialogUtil.on().hideProgressDialog();
                                Toast.makeText(GeneratedCodeActivity.this,
                                        getString(R.string.failed_to_save_the_code),
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
        );
    }

    private void shareCode(File codeImageFile) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            shareIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this,
                    getString(R.string.file_provider_authority), codeImageFile));
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(codeImageFile));
        }

        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_code_using)));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean isValid = true;

        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                isValid = false;
            }
        }

        switch (requestCode) {
            case REQUEST_CODE_TO_SAVE:
                if (isValid) {
                    if (getCurrentCodeFile() == null) {
                        storeCodeImage(true);
                    } else {
                        Toast.makeText(this,
                                getString(R.string.generated_qr_code_already_exists),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case REQUEST_CODE_TO_PRINT:
                if (isValid) {
                    if (getCurrentPrintedFile() == null) {
                        storeCodeDocument();
                    } else {
                        Toast.makeText(this,
                                getString(R.string.generated_qr_code_already_exists),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case REQUEST_CODE_TO_SHARE:
                if (isValid) {
                    if (getCurrentCodeFile() == null) {
                        storeCodeImage(false);

                        if (getCurrentCodeFile() != null) {
                            shareCode(getCurrentCodeFile());
                        } else {
                            Toast.makeText(this,
                                    getString(R.string.failed_to_share_the_code), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        shareCode(getCurrentCodeFile());
                    }
                }
                break;

            default:
                break;
        }
    }

    public void insertwallet() {

       StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_Insert_wallet,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                /*editor.putInt(GlobalVariables.QRCOUNT, preferences.getInt(GlobalVariables.QRCOUNT, 0) + 1);
                                editor.apply();*/

                                if (!Objects.equals(method.preferencess.getValue(GlobalVariables.Txn_amount), method.preferencess.getValue(GlobalVariables.QR_COIN)))
                                {


                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            //loadingDialog.dismiss();
                                           /* startActivity(new Intent(GeneratedCodeActivity.this, HomeActivity
                                                    .class));*/
                                            Intent intent = new Intent(GeneratedCodeActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                           /* intent.putExtra("activity", "GeneratedCodeActivity");
                                            method.preferencess.setValue("intent",GlobalVariables.settings.getMinReload());*/
                                            Log.d("KINGSN1", "onResponse: "+method.preferencess.getValue("intent"));
                                            //loadingDialog.show();
                                            //GenerateChecksum();
                                            // finish();
                                            loadingDialog.dismiss();
                                            finish();

                                        }
                                    }, 100);
                                    //Toast.makeText(GeneratedCodeActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                                }
                            }else if (success.equals("0")) {

                                //generateFragment.enablegen();

                                Intent intent = new Intent(GeneratedCodeActivity.this, HomeActivity.class);

                                startActivity(intent);

                                Log.d("KINGSN", "onResponse:error "+response);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(GeneratedCodeActivity.this, "Error: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            Log.d("KINGSN", "onResponse:error "+e.getMessage());
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Error", "" + error.getMessage());
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(GeneratedCodeActivity.this);
                alertDialogBuilder.setTitle("Something Went Wrong");
                alertDialogBuilder.setMessage("Please Try With Active Internet ");
                alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                alertDialogBuilder.setPositiveButton(GeneratedCodeActivity.this.getResources().getString(R.string.ok_message),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                                Intent intent = new Intent(GeneratedCodeActivity.this, HomeActivity.class);
                                startActivity(intent);

                            }
                        });

                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("insert_wallet","" );
                params.put("user_id", method.preferencess.getValue(GlobalVariables.USER_MOBILE));
                params.put("qrId", GlobalVariables.codeGen.getId());
                params.put("name", abcdjob.workonline.com.qrcode.Util.Method.userDTO.getName());
                params.put("email",  abcdjob.workonline.com.qrcode.Util.Method.userDTO.getEmail());
                params.put("amount", method.preferencess.getValue(GlobalVariables.Txn_amount));
                params.put("txn_type",  method.preferencess.getValue(GlobalVariables.Txn_type));
                params.put("txn_status", method.preferencess.getValue(GlobalVariables.Txn_status));

                return params;
            }
        };

        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(GeneratedCodeActivity.this);
        requestQueue.add(stringRequest);



       // Toast.makeText(GeneratedCodeActivity.this, "" + stringRequest, Toast.LENGTH_LONG).show();


    }

    public void getAllData(Activity activity) {
        method.params.clear();
        method.params.put("insert_wallet2","" );
        method.params.put("user_id", method.preferencess.getValue(GlobalVariables.USER_MOBILE));
        method.params.put("qrId", GlobalVariables.codeGen.getId());
        method.params.put("name", Method.userDTO.getName());
        method.params.put("email",  Method.userDTO.getEmail());
        method.params.put("amount", method.preferencess.getValue(GlobalVariables.Txn_amount));
        method.params.put("txn_type",  method.preferencess.getValue(GlobalVariables.Txn_type));
        method.params.put("txn_status", method.preferencess.getValue(GlobalVariables.Txn_status));
        method.params.put("device_id",method.getDeviceId(getApplicationContext()));
        Log.d(GlobalVariables.TAG, "getGenerated Code: called"+method.params);
        new HttpsRequest(RestAPI.API_Insert_wallet2, method.params, activity).stringPost2(GlobalVariables.TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, JSONObject abcdapp, String title, String message, JSONObject response) {

                if (flag) {
                    // binding.tvNo.setVisibility(View.GONE);
                    try {


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //loadingDialog.dismiss();
                                           /* startActivity(new Intent(GeneratedCodeActivity.this, HomeActivity
                                                    .class));*/
                                Intent intent = new Intent(GeneratedCodeActivity.this, HomeActivity.class);
                                startActivity(intent);
                                           /* intent.putExtra("activity", "GeneratedCodeActivity");
                                            method.preferencess.setValue("intent",GlobalVariables.settings.getMinReload());*/
                                Log.d("KINGSN1", "onResponse: "+method.preferencess.getValue("intent"));
                                //loadingDialog.show();
                                //GenerateChecksum();
                                // finish();
                                loadingDialog.dismiss();
                                finish();

                            }
                        }, 100);
                        //goToMainPage();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GeneratedCodeActivity.this);
                    try {
                        alertDialogBuilder.setTitle(response.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    alertDialogBuilder.setMessage(message);
                    alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
                    alertDialogBuilder.setPositiveButton(GeneratedCodeActivity.this.getResources().getString(R.string.ok_message),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                    finish();
                                    Intent intent = new Intent(GeneratedCodeActivity.this, HomeActivity.class);
                                    startActivity(intent);

                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GeneratedCodeActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }
}