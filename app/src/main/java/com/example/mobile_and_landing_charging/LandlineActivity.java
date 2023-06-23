package com.example.mobile_and_landing_charging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mobile_and_landing_charging.databinding.ActivityLandlineBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LandlineActivity extends AppCompatActivity {

    public static final MediaType JSON
            =MediaType.get("application/json;charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    ActivityLandlineBinding binding;

    Integer EndTermAmount;

    Integer MidTermAmount;

    String EndTermPaymentId;

    String MidTermPaymentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLandlineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Landline = binding.txtLandline.getText().toString();

                callAPI(Landline);
            }
        });
    }

    private void callAPI(String LandLine){
        JSONObject object = new JSONObject();

        try {
            object.put("FixedLineNumber",LandLine);
        } catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        RequestBody requestBody = RequestBody.create(object.toString(),JSON);
        Request request = new Request.Builder().url("https://charge.sep.ir/Inquiry/FixedLineBillInquiry")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) { call.cancel();}

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    EndTermAmount=jsonObject.getJSONObject("data").getJSONObject("FinalTerm").getInt("Amount");
                    MidTermAmount=jsonObject.getJSONObject("data").getJSONObject("MidTerm").getInt("Amount");
                    EndTermPaymentId=jsonObject.getJSONObject("data").getJSONObject("FinalTerm").getString("PaymentID");
                    MidTermPaymentId=jsonObject.getJSONObject("data").getJSONObject("MidTerm").getString("PaymentID");

                    load(EndTermAmount,MidTermAmount,EndTermPaymentId,MidTermPaymentId);

                } catch (Exception e) {
                    String error = e.getMessage();
                }
            }
        });
    }

    private void load(Integer endTermAmount, Integer midTermAmount, String endTermPaymentId, String midTermPaymentId){
        binding.lblEndTermAmount.setText(endTermAmount+"ریال");
        binding.lblMidTermAmount.setText(midTermAmount+"ریال");
        binding.lblEndPaymentId.setText(endTermPaymentId);
        binding.lblMidPaymentId.setText(midTermPaymentId);
    }
}









