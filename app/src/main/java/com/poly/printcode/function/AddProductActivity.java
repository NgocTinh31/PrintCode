package com.poly.printcode.function;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.poly.printcode.AsynTask.AddProductAsyntTask;
import com.poly.printcode.R;
import com.poly.printcode.Retrofit2.APIUtils;
import com.poly.printcode.Retrofit2.DataClient;
import com.poly.printcode.model.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {
    ImageView img;
    EditText ed_producr, ed_price, ed_description;
    Button add, cancel, btnAllproduct;
    int request_code_img = 123;
    String real_path = "";
    String strproduct, strprice, strdescrip, strimg;
    Product product;
    AddProductAsyntTask addProductAsyntTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        anhxa();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, request_code_img);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (real_path.length() == 0){
                    Toast.makeText(AddProductActivity.this,"Chưa có ảnh", Toast.LENGTH_SHORT).show();
                }else {
                    UploadImg();
                }
            }
        });
        btnAllproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProductActivity.this, AllProductActivity.class);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == request_code_img && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            real_path = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void anhxa() {
        img = findViewById(R.id.imgview);
        ed_producr = findViewById(R.id.product);
        ed_price = findViewById(R.id.price);
        ed_description = findViewById(R.id.description);
        add = findViewById(R.id.addproduct);
        cancel = findViewById(R.id.cancel);
        btnAllproduct = findViewById(R.id.btn_allproduct);
    }

    public void UploadImg() {
        File file = new File(real_path);
        String file_path = file.getAbsolutePath();
        String[] namefile = file_path.split("\\.");
        file_path = namefile[0] + System.currentTimeMillis() + "." + namefile[1];
        Log.d("/===", file_path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);
        DataClient dataClient = APIUtils.getData();
        Call<String> callback = dataClient.uploadphoto(body);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null) {
                    String mess = response.body();
                    strproduct = ed_producr.getText().toString();
                    strprice = ed_price.getText().toString();
                    strdescrip = ed_description.getText().toString();
                    strimg = APIUtils.Base_url+"image/"+mess;
                   if (strproduct.length() != 0&&strprice.length()!=0&&strdescrip.length()!=0){
                       product = new Product();
                       addProductAsyntTask = new AddProductAsyntTask(AddProductActivity.this);
                       product.setProduct(strproduct);
                       product.setPrice(strprice);
                       product.setDescription(strdescrip);
                       product.setImg(strimg);
                       addProductAsyntTask.insertProduct(product);
                       Log.d("/===", ""+product);
                   }else {
                       Toast.makeText(AddProductActivity.this,"Chưa điền thông tin", Toast.LENGTH_SHORT).show();
                   }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                        Log.d("/===", t.getMessage());
            }
        });
    }
}