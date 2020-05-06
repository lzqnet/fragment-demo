package com.zhiqing.fragmenttest;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;


/**
 * Created by zhiqing on 2018/1/26.
 */

public abstract class ICallback extends Binder {
    public static final String descriptor = "android.os.IBinder";
    public static final int CALLBACK = Binder.FIRST_CALL_TRANSACTION + 1;
    private static final String TAG = "lzqtest";
    public void sendResult(Result result) {
        Log.w(TAG, "ICallbackProxy.sendResult: result " + result.toString());
        if (isBinderAlive()) {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            data.writeInterfaceToken(descriptor);
            data.writeInt(result.getCode());
            try {
                Log.w(TAG, "ICallbackProxy.sendResult: send success ");
                transact(CALLBACK, data, reply, 0);
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
    protected boolean onTransact(int code, Parcel data, Parcel reply,
                                 int flags) throws RemoteException {
        if (code == CALLBACK) {
            try {
                data.enforceInterface(descriptor);
                Result result = new Result();
                result.code = data.readInt();
                onCallback(result);
                reply.writeNoException();
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.w(TAG, "Callback ERROR!");
            }
        }
        return super.onTransact(code, data, reply, flags);
    }

    protected abstract void onCallback(Result result);


    public static final class Result {
        public static final int CMD_CLOSE = 100;

        private int code;

        public Result(int code) {
            this.code = code;
        }

        public Result() {
        }

        public int getCode() {
            return code;
        }


        @Override
        public String toString() {
            return "Result {"
                    + "code=" + code
                    + "}";
        }

    }

    public static abstract class ICallbackProxy implements IBinder {
        public void sendResult(Result result) {
            Log.w(TAG, "ICallbackProxy.sendResult: result " + result.toString());
            if (isBinderAlive()) {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                data.writeInterfaceToken(descriptor);
                data.writeInt(result.getCode());
                try {
                    Log.w(TAG, "ICallbackProxy.sendResult: send success ");
                    transact(CALLBACK, data, reply, 0);
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

}
