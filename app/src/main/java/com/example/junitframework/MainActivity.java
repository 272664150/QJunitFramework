package com.example.junitframework;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.junitframework.core.InstrumentHelper;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mStartBtn;
    private ProgressBar mProgressBar;

    private InstrumentHelper mHelper;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(permissions -> {
                    mHelper = new InstrumentHelper();
                    setupViews();
                })
                .onDenied(permissions -> {
                    android.os.Process.killProcess(android.os.Process.myPid());
                })
                .start();
    }

    private void setupViews() {
        mProgressBar = findViewById(R.id.progress);
        mStartBtn = findViewById(R.id.test_policy);
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instrumentTestCase(null, null);
            }
        });
    }


    private void instrumentTestCase(List<String> javaPackages, List<String> javaClass) {
        mHelper.runInstrumentedTest(getPackageName() + ".test", javaPackages, javaClass,
                new InstrumentHelper.OnInstrumentedTestListener() {
                    @Override
                    public void onInstrumentStart() {
                        Log.w("Instrument", "Instrumentation start");
                        mProgressBar.setVisibility(View.VISIBLE);
                        mStartBtn.setEnabled(false);
                    }

                    @Override
                    public void onInstrumentFinish(int code, String message) {
                        Log.w("Instrument", String.format("Instrumentation finished!! code = %s, message = %s", code, message));
                        mProgressBar.setVisibility(View.GONE);
                        mStartBtn.setEnabled(true);
                        Intent data = new Intent();
                        data.putExtra("code", code);
                        data.putExtra("message", message);
                    }
                });
    }
}
