package com.poly.printcode.function;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.poly.printcode.AsynTask.DeleteProductAsynTask;
import com.poly.printcode.AsynTask.EditProductAsynTask;
import com.poly.printcode.AsynTask.UpdateProductAsyntask;
import com.poly.printcode.R;
import com.poly.printcode.model.Product;

public class EditProductActivity extends AppCompatActivity {
    EditText edtProduct, edtPrict, edtDesription;
    ImageView imageView;
    Button update, cancel, delete;
    EditProductAsynTask editProductAsynTask;
    DeleteProductAsynTask deleteProductAsynTask;
    String strProduct, strPrice, strDescription, strImg;
    Product product;
    UpdateProductAsyntask updateProductAsyntask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        anhxa();
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        editProductAsynTask = new EditProductAsynTask(EditProductActivity.this, edtProduct, edtPrict, edtDesription, imageView, id);
        editProductAsynTask.GetProductDetail();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProductAsynTask = new DeleteProductAsynTask(EditProductActivity.this, id);
                deleteProductAsynTask.DelateProduct();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtProduct.length() > 0&&edtPrict.length() > 0&&edtDesription.length() > 0){
                    strProduct = edtProduct.getText().toString();
                    strPrice = edtPrict.getText().toString();
                    strDescription = edtDesription.getText().toString();
                    product = new Product();
                    updateProductAsyntask = new UpdateProductAsyntask(EditProductActivity.this);
                    product.setId(id);
                    product.setProduct(strProduct);
                    product.setPrice(strPrice);
                    product.setDescription(strDescription);
                    updateProductAsyntask.UpdateProduct(product);
                    Log.d("/===", ""+product);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    private void anhxa() {
        edtProduct = findViewById(R.id.productupdate);
        edtPrict = findViewById(R.id.priceupdate);
        edtDesription = findViewById(R.id.descriptionupdate);
        imageView = findViewById(R.id.imgviewupdate);
        update = findViewById(R.id.btnupdateproduct);
        cancel = findViewById(R.id.btncancelupdate);
        delete = findViewById(R.id.btn_deteleproduct);
    }
}