package abcdjob.workonline.com.qrcode.Util;

import android.app.Activity;
import android.app.Application;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.facebook.ads.AudienceNetworkAds;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import abcdjob.workonline.com.qrcode.R;

public class MyApplication extends Application {

    Method method;
    @Override
    public void onCreate() {
        super.onCreate();
          method=new Method(this);
        AudienceNetworkAds.initialize(this);

     //   checkInternetConnection();


    }

    private void checkInternetConnection() {
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(ReactiveNetwork
                .observeNetworkConnectivity(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(connectivity -> {
                    if (connectivity.state() == NetworkInfo.State.CONNECTED) {
                        Log.d(GlobalVariables.TAG, "checkInternetConnection: Connected");
                        method.showToasty((Activity) getApplicationContext(),"1","Internet Is Connected");

                    } else {

                        Log.d(GlobalVariables.TAG, "checkInternetConnection: NOTConnected");
                        method.showToasty((Activity) getApplicationContext(),"2","Internet Is Not Connected");

                    }

                }, throwable -> {
                    Toast.makeText(this, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();

                }));
    }
}
