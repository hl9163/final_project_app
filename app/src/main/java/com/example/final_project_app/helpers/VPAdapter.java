package com.example.final_project_app.helpers;

import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class VPAdapter extends FragmentPagerAdapter {
    /**
     * @author		Harel Leibovich <hl9163@bs.amalnet.k12.il>
     * @version	1.0
     * @since		27/01/2023
     * fragments adapter
     */
    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final ArrayList<String> fragmentTitle = new ArrayList<>();


    public VPAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment, String title){
        fragmentArrayList.add(fragment);
        fragmentTitle.add(title);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }
}
