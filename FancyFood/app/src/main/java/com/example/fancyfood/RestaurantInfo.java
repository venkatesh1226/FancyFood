package com.example.fancyfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RestaurantInfo extends AppCompatActivity {
    String name, address, year, area, phoneno, cuisines;
    EditText rName, rAddress, rPhoneNo, rYear, rCuisines, rArea;
    Bundle b = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        rName = findViewById(R.id.restaurant_name);
        rAddress = findViewById(R.id.restaurant_address);
        rPhoneNo = findViewById(R.id.phone_number);
        rYear = findViewById(R.id.restaurant_year_started);
        rCuisines = findViewById(R.id.cuisines_offered);
        rArea = findViewById(R.id.area);


    }

    public void nextButton(View view) {
        if (rName.length() == 0)
            rName.setError("Name cannot be empty");
        if (rAddress.length() == 0)
            rAddress.setError("Address cannot be empty");
        if (rPhoneNo.length() == 0)
            rPhoneNo.setError("Phone number cannot be empty");
        if (rYear.length() == 0)
            rYear.setError("Year cannot be empty");
        if (rCuisines.length() == 0)
            rCuisines.setError("Cuisines cannot be empty");
        if (rArea.length() == 0)
            rArea.setError("Area cannot be empty");
        if (rName.length() != 0 && rAddress.length() != 0 && rPhoneNo.length() != 0 && rYear.length() != 0 && rCuisines.length() != 0 && rArea.length() != 0 ) {
            resInfo();
            Intent i = new Intent(RestaurantInfo.this, RestaurantImages.class);
            i.putExtras(b);
            startActivity(i);
        }
    }

    public void resInfo() {
        name = rName.getText().toString();
        address = rAddress.getText().toString().trim();
        year = rYear.getText().toString().trim();
        area = rArea.getText().toString().trim();
        phoneno = rPhoneNo.getText().toString().trim();
        cuisines = rCuisines.getText().toString().trim();
        uploadDetails();
    }
    public void uploadDetails(){
        b.putString("name",name);
        b.putString("address",address);
        b.putString("year",year);
        b.putString("area",area);
        b.putString("phoneno",phoneno);
        b.putString("cuisines",cuisines);
    }
}