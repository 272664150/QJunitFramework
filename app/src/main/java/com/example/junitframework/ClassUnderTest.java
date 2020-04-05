package com.example.junitframework;

import android.content.Context;

public class ClassUnderTest {
    private Context mContext;

    public ClassUnderTest(Context context) {
        this.mContext = context;
    }

    protected String getHelloWorldString() {
        return mContext.getString(R.string.hello_world2);
    }
}
