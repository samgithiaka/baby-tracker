package com.mwai.babyapp.ui.drop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.mwai.babyapp.R;
import com.mwai.babyapp.app.BaseActivity;
import com.mwai.babyapp.databinding.ActivityChildSelectionBinding;

public class ChildSelectionActivity extends BaseActivity {
    ActivityChildSelectionBinding binding;
    private static final String TAG = "ChildSelectionActivity";
    static public String SHARED_PREFERRENCE_TEMP_DATA_FOLDER = "TEMP_DATA";
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activityContext, R.layout.activity_child_selection);

        sharedPref = getSharedPreferences(SHARED_PREFERRENCE_TEMP_DATA_FOLDER, Context.MODE_PRIVATE);
        onClick();
        init();
    }

    private void init() {
        if (sharedPref.getString("mode", "").equals("pick")) {
            binding.btnConfirmD.setVisibility(View.GONE);
            binding.btnConfirmP.setVisibility(View.VISIBLE);
        } else {
            binding.btnConfirmD.setVisibility(View.VISIBLE);
            binding.btnConfirmP.setVisibility(View.GONE);
        }
    }

    private void onClick() {
        try {
            binding.btnConfirmD.setOnClickListener(v -> {
                sharedPref.edit().putString("mode", "pick").apply();
                finish();
            });

            binding.btnConfirmP.setOnClickListener(v -> {
                sharedPref.edit().putString("mode", "drop").apply();
                finish();
            });
        } catch (Exception e) {
            Log.e(TAG, "onClick: ", e);
        }
    }
}