package com.example.junitframework.core;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class InstrumentParams {

    /**
     * 测试程序包名
     */
    private String testPackage;

    /**
     * 待测试的包路径
     */
    private String javaPackage;

    /**
     * 待测试的类或方法路径
     */
    private String javaClass;

    private InstrumentParams(Builder builder) {
        testPackage = builder.testPackage;
        javaPackage = builder.javaPackageBuilder.toString();
        javaClass = builder.javaClassBuilder.toString();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String testPackage;
        private final StringBuilder javaPackageBuilder;
        private final StringBuilder javaClassBuilder;

        private Builder() {
            javaPackageBuilder = new StringBuilder();
            javaClassBuilder = new StringBuilder();
        }

        public Builder testPackage(String testPackageName) {
            this.testPackage = testPackageName;
            return this;
        }

        public Builder javaPackage(String val) {
            if (!TextUtils.isEmpty(val)) {
                if (javaPackageBuilder.length() != 0) {
                    javaPackageBuilder.append(",");
                }
                javaPackageBuilder.append(val);
            }
            return this;
        }

        public Builder javaClass(String val) {
            if (!TextUtils.isEmpty(val)) {
                if (javaClassBuilder.length() != 0) {
                    javaClassBuilder.append(",");
                }
                javaClassBuilder.append(val);
            }
            return this;
        }

        public InstrumentParams build() {
            if (TextUtils.isEmpty(testPackage)) {
                throw new IllegalStateException("testPackage must not be null");
            }
            return new InstrumentParams(this);
        }
    }

    @NonNull
    public String getTestPackage() {
        return testPackage;
    }

    @Nullable
    public String getJavaPackage() {
        return javaPackage;
    }

    @Nullable
    public String getJavaClass() {
        return javaClass;
    }
}
