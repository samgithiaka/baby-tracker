package com.mwai.babyapp.ui.drop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.mwai.babyapp.R;
import com.mwai.babyapp.app.BaseActivity;
import com.mwai.babyapp.databinding.ActivityChildSelectionBinding;

public class ChildSelectionActivity extends BaseActivity {
ActivityChildSelectionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(activityContext, R.layout.activity_child_selection);
       binding.btnConfirm.setOnClickListener(v -> {
           finish();
       });
    }
}