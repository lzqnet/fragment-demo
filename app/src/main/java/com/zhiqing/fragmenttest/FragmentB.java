package com.zhiqing.fragmenttest;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentB extends Fragment {
    private final static String TAG = "lzqtest";
    private ICallback mCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentb, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle=getArguments();
        mCallback=(ICallback) bundle.getBinder(MainActivity.CALLBACK_KEY);
    }

    private void sendResult(IBinderCallback.Result result) {
        Log.w(TAG, "RecordCardActivity.sendResult: result " + result.toString());
        if (mCallback != null && mCallback.isBinderAlive()) {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            data.writeInterfaceToken(IBinderCallback.descriptor);
            data.writeInt(result.getCode());
            try {
                Log.w(TAG, "RecordCardActivity.sendResult: send success ");
                mCallback.transact(IBinderCallback.CALLBACK, data, reply, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.e(TAG, "sendResult: ", e);
            }
            reply.readException();
            data.recycle();
            reply.recycle();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCallback.sendResult(new ICallback.Result(100));
    }
}
