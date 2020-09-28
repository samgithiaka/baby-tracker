package com.mwai.babyapp.ui.main.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mwai.babyapp.R;
import com.mwai.babyapp.app.BaseFragment;
import com.mwai.babyapp.databinding.FragmentHomeBinding;
import com.mwai.babyapp.ui.drop.ChildSelectionActivity;
import com.mwai.babyapp.ui.payment.PaymentsActivity;

public class HomeFragment extends BaseFragment {
private FragmentHomeBinding binding;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false);

        binding.cvPickNDropServices.setOnClickListener(v -> {
            startActivity(new Intent(activityContext, ChildSelectionActivity.class));
        });
        binding.cvPayments.setOnClickListener(v -> {
            startActivity(new Intent(activityContext, PaymentsActivity.class));
        });
        return binding.getRoot();
    }
}