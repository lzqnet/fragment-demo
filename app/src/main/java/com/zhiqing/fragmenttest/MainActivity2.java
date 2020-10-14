package com.zhiqing.fragmenttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class MainActivity2 extends AppCompatActivity {
    private IBinder mCallback;
    private final static String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle=getIntent().getBundleExtra("bundle");
        mCallback=(IBinder) bundle.getBinder(MainActivity.CALLBACK_KEY);
        ICallback.sendResult(mCallback,new ICallback.Result(6));

    }

    public void sendResult(ICallback.Result result) {
        Log.w(TAG, "ICallbackProxy.sendResult: result " + result.toString());
        if (mCallback.isBinderAlive()) {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            data.writeInterfaceToken(ICallback.descriptor);
            data.writeInt(result.getCode());
            try {
                Log.w(TAG, "ICallbackProxy.sendResult: send success ");
                mCallback.transact(ICallback.CALLBACK, data, reply, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.e(TAG, "sendResult: ", e);
            }
            reply.readException();
            data.recycle();
            reply.recycle();
        }
    }
}