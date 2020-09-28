package com.mwai.babyapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.mwai.babyapp.R;
import com.mwai.babyapp.app.BaseActivity;
import com.mwai.babyapp.databinding.ActivityMainBinding;
import com.mwai.babyapp.ui.main.fragments.CctvFragment;
import com.mwai.babyapp.ui.main.fragments.HomeFragment;
import com.mwai.babyapp.ui.main.fragments.ProfileFragment;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activityContext, R.layout.activity_main);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()){
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    loadSelectedFragment(fragment);
                    return true;
                case R.id.navigation_emergency:
                    fragment = new CctvFragment();
                    loadSelectedFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    loadSelectedFragment(fragment);
                    return true;
            }
            return false;
        });
    }

    private void loadSelectedFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSelectedFragment(fragment != null ? fragment : new HomeFragment());
    }
}