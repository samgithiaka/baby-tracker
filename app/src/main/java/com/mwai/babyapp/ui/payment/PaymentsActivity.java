package com.mwai.babyapp.ui.payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.bdhobare.mpesa.Mpesa;
import com.bdhobare.mpesa.interfaces.AuthListener;
import com.bdhobare.mpesa.interfaces.MpesaListener;
import com.bdhobare.mpesa.models.STKPush;
import com.bdhobare.mpesa.utils.Pair;
import com.mwai.babyapp.R;
import com.mwai.babyapp.app.BaseActivity;
import com.mwai.babyapp.app.BaseFragment;
import com.mwai.babyapp.databinding.ActivityPaymentsBinding;

public class PaymentsActivity extends BaseActivity implements AuthListener , MpesaListener {
    ActivityPaymentsBinding binding;
    String CONSUMER_KEY = "xzvnzuj0zOO23QOU7Gyk9tyn3u0nzPWT";
    String CONSUMER_SECRET = "swhceFJAiHD0hSGJ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(activityContext,
               R.layout.activity_payments);
        Mpesa.with(activityContext, CONSUMER_KEY, CONSUMER_SECRET);
    }

    @Override
    public void onAuthError(Pair<Integer, String> result) {

    }

    @Override
    public void onAuthSuccess() {

    }

    @Override
    public void onMpesaError(Pair<Integer, String> result) {

    }

    @Override
    public void onMpesaSuccess(String MerchantRequestID, String CheckoutRequestID, String CustomerMessage) {

    }

    public void instantiateMpesa() {
        STKPush.Builder builder = new STKPush.Builder(BUSINESS_SHORT_CODE, PASSKEY, amount,BUSINESS_SHORT_CODE, phone);

        STKPush push = builder.build();

        Mpesa.getInstance().pay(activityContext, push);
    }
}