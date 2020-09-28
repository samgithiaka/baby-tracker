package com.mwai.babyapp.ui.payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.mwai.babyapp.R;
import com.mwai.babyapp.app.BaseActivity;
import com.mwai.babyapp.app.BaseFragment;
import com.mwai.babyapp.databinding.ActivityPaymentsBinding;

public class PaymentsActivity extends BaseActivity {
    ActivityPaymentsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(activityContext,
               R.layout.activity_payments);
    }
}