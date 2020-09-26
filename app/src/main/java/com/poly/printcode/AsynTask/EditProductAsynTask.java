package com.poly.printcode.AsynTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.poly.printcode.connect.Constants;
import com.poly.printcode.function.EditProductActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class EditProductAsynTask {
    EditProductActivity context;
    EditText product, price, description;
    ImageView imgview;
    String id;

    public EditProductAsynTask(EditProductActivity context, EditText product, EditText price, EditText description, ImageView imgview, String id) {
        this.context = context;
        this.product = product;
        this.price = price;
        this.description = description;
        this.imgview = imgview;
        this.id = id;
    }
    private Bitmap loadImage (String link){
        URL url;
        Bitmap bitmap = null;
        try {
            url = new URL(link);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public void GetProductDetail() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.url_product_detail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final String strProduct, strPrice, strDescription, strImg;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (Integer.parseInt(jsonObject.getString(Constants.TAG_SUCCESS)) == 1) {
                        JSONArray array = jsonObject.getJSONArray("pro");
                        JSONObject object = array.getJSONObject(0);
                        strProduct = object.getString("product");
                        strPrice = object.getString("price");
                        strDescription = object.getString("description");
                        strImg = object.getString("img");
                        product.setText(strProduct);
                        price.setText(strPrice);
                        description.setText(strDescription);
                        final Thread myThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final Bitmap bitmap = loadImage(strImg);
                                imgview.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        imgview.setImageBitmap(bitmap);
                                    }
                                });
                            }
                        });
                        myThread.start();
                    } else {
                        Toast.makeText(context.getApplicationContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley error", error.toString());
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                return params;
            }
        };
        Volley.newRequestQueue(context.getApplicationContext()).add(stringRequest);
    }
}
