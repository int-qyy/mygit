package com.swufe.myapplication;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FrameActivity extends FragmentActivity {
    private androidx.fragment.app.Fragment mFtagments[];
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbtHome,rbtFunc,rbtSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        mFtagments= new androidx.fragment.app.Fragment[3];
        fragmentManager=getSupportFragmentManager();
        mFtagments[0]=fragmentManager.findFragmentById(R.id.fragment_main);
        mFtagments[1]=fragmentManager.findFragmentById(R.id.fragment_func);
        mFtagments[2]=fragmentManager.findFragmentById(R.id.fragment_setting);
        fragmentTransaction=fragmentManager.beginTransaction().hide(mFtagments[0]).hide(mFtagments[1]).hide(mFtagments[2]);
        fragmentTransaction.show(mFtagments[0]).commit();
        rbtHome=(RadioButton)findViewById(R.id.fragment_main);
        rbtFunc=(RadioButton)findViewById(R.id.fragment_func);
        rbtSetting=(RadioButton)findViewById(R.id.fragment_setting);

        radioGroup=(RadioGroup)findViewById(R.id.bottonGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i("radioGroup","checjId="+checkedId);
                fragmentTransaction=fragmentManager.beginTransaction().hide(mFtagments[0]).hide(mFtagments[1]).hide(mFtagments[2]);
                switch(checkedId){
                    case R.id.fragment_main:
                        fragmentTransaction.show(mFtagments[0]).commit();
                        break;
                    case R.id.fragment_func:
                        fragmentTransaction.show(mFtagments[1]).commit();
                        break;
                    case R.id.fragment_setting:
                        fragmentTransaction.show(mFtagments[2]).commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
