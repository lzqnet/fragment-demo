package com.zhiqing.fragmenttest;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
public static final String CALLBACK_KEY="call_back_key";
    private ICallback mRecordCardCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonclick(View view) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragmenta_root, new FragmentA(),"fragmenta");
        transaction.commit();
    }

    public void goToActivity2(View view) {
        Intent intent=new Intent();
        intent.setClass(this.getApplicationContext(), MainActivity2.class);
        Bundle bundle = new Bundle();
        mRecordCardCallback = new ICallback() {
            @Override
            protected void onCallback(Result result) {
                Log.d(TAG, "onCallback: Thread="+Thread.currentThread().getName());

                Log.d(TAG, "RecordCardTestActivity.onCallback:  " + result.toString());
            }
        };
        bundle.putBinder(MainActivity.CALLBACK_KEY, mRecordCardCallback);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
}
