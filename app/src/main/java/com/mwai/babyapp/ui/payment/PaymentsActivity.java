package com.mwai.babyapp.ui.payment;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.bdhobare.mpesa.Mpesa;
import com.bdhobare.mpesa.interfaces.AuthListener;
import com.bdhobare.mpesa.interfaces.MpesaListener;
import com.bdhobare.mpesa.models.STKPush;
import com.bdhobare.mpesa.utils.Pair;
import com.mwai.babyapp.R;
import com.mwai.babyapp.app.BaseActivity;
import com.mwai.babyapp.databinding.ActivityPaymentsBinding;
import com.mwai.babyapp.ui.payment.models.MpesaDataModel;

public class PaymentsActivity extends BaseActivity implements AuthListener, MpesaListener {
    private static final String PASSKEY = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
    private static final String BUSINESS_SHORT_CODE = "174379";
    ActivityPaymentsBinding binding;
    String CONSUMER_KEY = "xzvnzuj0zOO23QOU7Gyk9tyn3u0nzPWT";
    String CONSUMER_SECRET = "swhceFJAiHD0hSGJ";
    private static final String TAG = "PaymentsActivity";
    MpesaDataModel mpesaDataModel = new MpesaDataModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activityContext,
                R.layout.activity_payments);
        Mpesa.with(activityContext, CONSUMER_KEY, CONSUMER_SECRET);

        onClickListener();
        init();
    }

    private void init() {
        binding.setMpesaDetails(mpesaDataModel);
    }

    private void onClickListener() {
        try {
            binding.btnPay.setOnClickListener(v -> {
                if (mpesaDataModel.getPhone() != null) {
                    if (mpesaDataModel.getAmount() != null) {
                        instantiateMpesa();
                    } else {
                        Toast.makeText(activityContext, "Enter amount!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(activityContext, "Enter Phone Number!", Toast.LENGTH_LONG).show();
                }
            });
            binding.btnCancel.setOnClickListener(v -> {
                finish();
            });

        } catch (Exception e) {
            Log.e(TAG, "onClickListener: ", e);
        }
    }

    @Override
    public void onAuthError(Pair<Integer, String> result) {
        Log.e(TAG, "onAuthError: " + result);
        Toast.makeText(activityContext, "Error Authenticating", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthSuccess() {
        Toast.makeText(activityContext, "Succesfully Authenticated", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMpesaError(Pair<Integer, String> result) {
        Log.e(TAG, "onMpesaError: " + result);
        Toast.makeText(activityContext, "Error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMpesaSuccess(String MerchantRequestID, String CheckoutRequestID, String CustomerMessage) {
        Toast.makeText(activityContext, CustomerMessage, Toast.LENGTH_LONG).show();
    }

    private void instantiateMpesa() {
        try {
            int amount = Integer.parseInt(mpesaDataModel.getAmount());
            STKPush.Builder builder = new STKPush.Builder(BUSINESS_SHORT_CODE, PASSKEY, amount, BUSINESS_SHORT_CODE, mpesaDataModel.getPhone());

            STKPush push = builder.build();

            Mpesa.getInstance().pay(activityContext, push);
        } catch (Exception e) {
            Log.e(TAG, "instantiateMpesa: ", e);
        }
    }
}