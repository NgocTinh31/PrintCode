package com.poly.printcode.function;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.poly.printcode.R;
import com.poly.printcode.adapter.AdapterProduct;
import com.poly.printcode.connect.Constants;
import com.poly.printcode.model.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllProductActivity extends AppCompatActivity {
    ListView listView_Product;
    AdapterProduct adapterProduct;
    ArrayList<Product> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        listView_Product = findViewById(R.id.lisview_product);
        getAllProduct();
        listView_Product.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = arrayList.get(i).getId();
                Intent intent = new Intent(AllProductActivity.this, EditProductActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                return true;
            }
        });
        listView_Product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = arrayList.get(i).getId();
                Intent intent = new Intent(AllProductActivity.this, ProductDetailActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }
    private void getAllProduct() {
        final StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.url_all_product, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AfterGetAll(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
//                param.put("username", name);
                return param;
            }
        };
        Volley.newRequestQueue(AllProductActivity.this).add(stringRequest);
    }
    private void AfterGetAll(String res) {
        String mess = "";
        String succcess = "";
        String strid, strproduct, strprice, strimg;
        try {
            JSONObject jsonObject = new JSONObject(res);
            mess = jsonObject.getString(Constants.MESS);
            succcess = jsonObject.getString(Constants.TAG_SUCCESS);
            if (Integer.parseInt(succcess) == 1) {
                JSONArray array = jsonObject.getJSONArray("pro");
                for (int i = 0; i < array.length(); i ++){
                    JSONObject object = array.getJSONObject(i);
                    strid = object.getString("id");
                    strproduct = object.getString("product");
                    strprice = object.getString("price");
                    strimg = object.getString("img");

                    Product product = new Product();
                    product.setId(strid);
                    product.setProduct(strproduct);
                    product.setPrice(strprice);
                    product.setImg(strimg);
                    arrayList.add(product);
                }
                adapterProduct = new AdapterProduct(AllProductActivity.this, arrayList);
                listView_Product.setAdapter(adapterProduct);
            } else {
                Toast.makeText(AllProductActivity.this, "Null" , Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}