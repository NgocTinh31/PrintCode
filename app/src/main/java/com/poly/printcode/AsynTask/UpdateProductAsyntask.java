package com.poly.printcode.AsynTask;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.poly.printcode.connect.Constants;
import com.poly.printcode.function.AllProductActivity;
import com.poly.printcode.function.EditProductActivity;
import com.poly.printcode.model.Product;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateProductAsyntask {
    EditProductActivity context;

    public UpdateProductAsyntask(EditProductActivity context) {
        this.context = context;
    }
    public void UpdateProduct(final Product product) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.url_update_product, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (Integer.parseInt(jsonObject.getString(Constants.TAG_SUCCESS)) == 1) {
                        Toast.makeText(context.getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context.getApplicationContext(), AllProductActivity.class);
                        context.startActivity(intent);
                        context.finish();
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
                params.put("id", product.getId());
                params.put("product", product.getProduct());
                params.put("price", product.getPrice());
                params.put("description", product.getDescription());
                return params;
            }
        };
        Volley.newRequestQueue(context.getApplicationContext()).add(stringRequest);
    }
}
