package com.example.mobile_and_landing_charging;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mobile_and_landing_charging.databinding.ActivityChargeBinding;
import com.example.mobile_and_landing_charging.databinding.ActivityMainBinding;

public class ChargeActivity extends AppCompatActivity {

    ActivityChargeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityChargeBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_charge);
    }
}