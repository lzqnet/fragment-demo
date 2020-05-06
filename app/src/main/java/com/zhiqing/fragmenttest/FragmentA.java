package com.zhiqing.fragmenttest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentA extends Fragment {
    private final static String TAG = "lzqtest";
    Button button;
    private ICallback mRecordCardCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmenta, container, false);
    }

    @Override

    public void onResume() {
        super.onResume();
        mRecordCardCallback = new ICallback() {
            @Override
            protected void onCallback(Result result) {
                Log.d(TAG, "onCallback: Thread="+Thread.currentThread().getName());

                Log.d(TAG, "RecordCardTestActivity.onCallback:  " + result.toString());
            }
        };
        button=getActivity().findViewById(R.id.fragmenta_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bundle bundle = new Bundle();
                //bundle.putBinder(MainActivity.CALLBACK_KEY, mRecordCardCallback);
                //FragmentB fragmentB=new FragmentB();
                //fragmentB.setArguments(bundle);
                //FragmentManager manager = getActivity().getSupportFragmentManager();
                //FragmentTransaction transaction = manager.beginTransaction();
                //transaction.add(R.id.fragmentb_root, fragmentB,"fragmentb");
                //transaction.addToBackStack(null).commit();

                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(FragmentA.this);
                transaction.commitAllowingStateLoss();
            }
        });
    }
}
