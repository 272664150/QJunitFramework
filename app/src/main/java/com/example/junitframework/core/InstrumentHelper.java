package com.example.junitframework.core;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class InstrumentHelper {
    private static final String TAG = InstrumentHelper.class.getSimpleName();

    private final AppExecutors mExecutors;

    public interface OnInstrumentedTestListener {
        /**
         * 开始执行
         */
        void onInstrumentStart();

        /**
         * 执行完毕
         *
         * @param code
         * @param message
         */
        @UiThread
        void onInstrumentFinish(int code, String message);
    }

    public InstrumentHelper() {
        mExecutors = new AppExecutors();
    }

    public void runInstrumentedTest(final InstrumentParams params, final OnInstrumentedTestListener listener) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                String command = buildInstrumentCommand(params);
                Log.d(TAG, "Instrument command = " + command);
                final CommandResult result = runShellCommand(command);
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.onInstrumentFinish(result.getCode(), result.getMessage());
                        }
                    }
                });
            }
        });
    }

    /**
     * 执行插桩单元测试指令
     *
     * @param testPackageName 测试程序包名
     * @param packages
     * @param classes
     * @param listener        测试执行结果监听
     */
    public void runInstrumentedTest(@NonNull final String testPackageName,
                                    final List<String> packages, List<String> classes,
                                    final OnInstrumentedTestListener listener) {
        if (listener != null) {
            listener.onInstrumentStart();
        }
        InstrumentParams.Builder builder1 = InstrumentParams.newBuilder();
        builder1.testPackage(testPackageName);
        if (packages != null) {
            for (String pkg : packages) {
                builder1.javaPackage(pkg);
            }
        }

        if (classes != null) {
            for (String clazz : classes) {
                builder1.javaClass(clazz);
            }
        }

        InstrumentParams params = builder1.build();
        runInstrumentedTest(params, listener);
    }

    /**
     * 执行shell命令
     *
     * @param command
     */
    public CommandResult runShellCommand(String command) {
        BufferedReader genericReader = null;
        BufferedReader errorReader = null;
        Throwable throwable;
        try {
            Process process = Runtime.getRuntime().exec(command);

            errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder error = new StringBuilder();
            String line = null;
            while ((line = errorReader.readLine()) != null) {
                error.append(line).append("\n");
            }
            Log.e(TAG, "Error message: " + error.toString());

            genericReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder generic = new StringBuilder();
            while ((line = genericReader.readLine()) != null) {
                generic.append(line).append("\n");
            }

            Log.d(TAG, "generic message: " + generic.toString());
            int exitVal = process.waitFor(); //0 is success,otherwise failure.
            process.destroy();
            Log.d(TAG, "Adb process exit value : " + exitVal);
            return new CommandResult(exitVal, generic.toString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throwable = e;
        } finally {
            if (genericReader != null) {
                try {
                    genericReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throwable = e;
                }
            }

            if (errorReader != null) {
                try {
                    errorReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throwable = e;
                }
            }
        }
        return new CommandResult(CommandResult.CODE_ERROR, throwable.getMessage());
    }

    /**
     * 构建instrument命令。命令格式：am instrument [flags] <test_package>/<runner_class> 。
     * <p>
     * 参考：https://developer.android.com/studio/test/command-line?hl=zh-cn
     *
     * @param params
     * @return
     */
    private String buildInstrumentCommand(InstrumentParams params) {
        return "am instrument -w --user 0"
                + buildTestCaseParams(params)
                + " -e listener de.schroepf.androidxmlrunlistener.XmlRunListener "//生成xml报告的监听类
                + params.getTestPackage()  //测试apk包名
                + "/androidx.test.runner.AndroidJUnitRunner";//插桩测试运行程序
    }

    /**
     * 构建-e package 和-e class的参数
     *
     * @param params
     * @return
     */
    private String buildTestCaseParams(InstrumentParams params) {
        if (params != null) {
            StringBuilder testcaseParams = new StringBuilder();
            String packages = params.getJavaPackage();
            if (!TextUtils.isEmpty(packages)) {
                testcaseParams.append(" -e package ")
                        .append(params.getJavaPackage());
            }
            String classes = params.getJavaClass();
            if (!TextUtils.isEmpty(classes)) {
                testcaseParams.append(" -e class ")
                        .append(classes);
            }
            return testcaseParams.toString();
        } else {
            return null;
        }
    }
}
