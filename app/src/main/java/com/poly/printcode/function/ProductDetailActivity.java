package com.poly.printcode.function;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.poly.printcode.AsynTask.GetProductDetailAsynTask;
import com.poly.printcode.R;

public class ProductDetailActivity extends AppCompatActivity {
    GetProductDetailAsynTask getProductDetailAsynTask;
    String id;
    TextView tvproduct, tvprice, tvdescription;
    ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        anhxa();
        getProductDetailAsynTask = new GetProductDetailAsynTask(ProductDetailActivity.this, tvproduct, tvprice, tvdescription, imgView, id);
        getProductDetailAsynTask.GetProductDetail();
    }

    private void anhxa() {
        tvproduct = findViewById(R.id.tv_product);
        tvprice = findViewById(R.id.tv_price);
        tvdescription = findViewById(R.id.tv_description);
        imgView = findViewById(R.id.imgView);
    }
}