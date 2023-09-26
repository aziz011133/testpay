package com.aziztech.testpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import chargily.epay.java.ChargilyCallback;
import chargily.epay.java.ChargilyClient;
import chargily.epay.java.ChargilyResponse;
import chargily.epay.java.Invoice;
import chargily.epay.java.PaymentMethod;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn=findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChargilyClient client = new ChargilyClient("api_c2ofgUmiRpjMVFeoiIYQKa4jIQVW9mzBanpIWbP5IcHxzpflrCznZtcPko4mxD0s");
                Invoice invoice = new Invoice(
                        "Chakhoum Ahmed",
                        "azizlabyod@gmail.com",
                        5.0,
                        "https://backend.com/webhook_endpoint",
                        "https://frontend.com",
                        PaymentMethod.EDAHABIA,
                        "5001",
                        10000.0);

                try {
                    ChargilyCallback<ChargilyResponse> responseCallback = new ChargilyCallback<ChargilyResponse>() {


                        @Override
                        public void onResponse(Call<ChargilyResponse> call, ChargilyResponse chargilyResponse) {
                            if (chargilyResponse.isSuccess()) {
                                chargilyResponse.getStatusCode();
                                chargilyResponse.getCheckoutUrl();
                            } else {
                                chargilyResponse.getStatusCode();
                                chargilyResponse.getErrorBody();
                            }
                        }

                        @Override
                        public void onFailure(Call<ChargilyResponse> call, Throwable throwable) {
                            Toast.makeText(MainActivity.this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                    };

                    client.submitInvoiceAsync(invoice, responseCallback);

                }catch (Exception e){
                    Toast.makeText(MainActivity.this, e.getMessage()+"", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}