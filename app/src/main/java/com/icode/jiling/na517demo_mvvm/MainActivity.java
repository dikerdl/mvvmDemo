package com.icode.jiling.na517demo_mvvm;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.icode.jiling.na517demo_mvvm.fragment.HomeFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;


public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;

    private FragmentTransaction mTransaction;

    private HomeFragment mHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        mFragmentManager = getSupportFragmentManager();

        mTransaction = mFragmentManager.beginTransaction();;
        mHomeFragment = new HomeFragment();
        mTransaction.add(R.id.fl_content, mHomeFragment);
        mTransaction.commit();
    }

}
