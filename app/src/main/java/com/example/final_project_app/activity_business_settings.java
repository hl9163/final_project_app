package com.example.final_project_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.final_project_app.helpers.VPAdapter;
import com.google.android.material.tabs.TabLayout;

public class activity_business_settings extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Intent gi;
    String businessId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_settings);

        tabLayout = (TabLayout) findViewById(R.id.tl_settings);
        viewPager =  (ViewPager) findViewById(R.id.vp_settings);

        gi = getIntent();
        businessId = gi.getStringExtra("business_id");

        tabLayout.setupWithViewPager(viewPager);
        VPAdapter vpAdapter= new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new fragment1(),"הגדרות כלליות");
        vpAdapter.addFragment(new fragment2(),"הגדרות שעות");
        vpAdapter.addFragment(new fragment4(),"הגדרות שירותים");
        vpAdapter.addFragment(new fragment3(),"הגדרות תורים");
        send_data_to_fragment(vpAdapter, 0);
        send_data_to_fragment(vpAdapter, 1);
        send_data_to_fragment(vpAdapter, 2);
        viewPager.setAdapter(vpAdapter);




    }
    public void send_data_to_fragment(VPAdapter vpAdapter, int pos){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("business_idB",businessId);
        vpAdapter.getItem(pos).setArguments(bundle);
    }


}