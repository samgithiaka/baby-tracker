package com.mwai.babyapp.ui.main.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.mwai.babyapp.R;
import com.mwai.babyapp.app.BaseFragment;
import com.mwai.babyapp.databinding.FragmentHomeBinding;
import com.mwai.babyapp.ui.drop.ChildSelectionActivity;
import com.mwai.babyapp.ui.payment.PaymentsActivity;

public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    static public String SHARED_PREFERRENCE_TEMP_DATA_FOLDER = "TEMP_DATA";
    private final boolean selected = false;
    private SharedPreferences sharedPref;
    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getActivity().getSharedPreferences(SHARED_PREFERRENCE_TEMP_DATA_FOLDER, Context.MODE_PRIVATE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false);
        onClick();
        sharedPref = getActivity().getSharedPreferences(SHARED_PREFERRENCE_TEMP_DATA_FOLDER, Context.MODE_PRIVATE);

        return binding.getRoot();
    }

    private void onClick() {
        try {
            if (sharedPref.getString("mode", "").equals("pick")) {
                binding.tvTitle1.setText("PICK");
            } else {
                binding.tvTitle1.setText("DROP");
            }
            binding.cvPickNDropServices.setOnClickListener(v -> {

                startActivity(new Intent(activityContext, ChildSelectionActivity.class));
            });

            binding.cvPayments.setOnClickListener(v -> {
                startActivity(new Intent(activityContext, PaymentsActivity.class));
            });
        } catch (Exception e) {
            Log.e(TAG, "onClick: ", e);
        }
    }


}