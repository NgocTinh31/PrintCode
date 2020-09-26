package com.poly.printcode.AsynTask;

import android.content.Context;
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
import com.poly.printcode.function.MainActivity;
import com.poly.printcode.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserAdd {
    Context context;

    public UserAdd(Context context) {
        this.context = context;
    }

    public void insertUser(final User user) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.url_create_user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("user: ", response);
                handlerAfterRegister(response);
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
                params.put(Constants.TAG_USERNAME, user.getUsername());
                params.put(Constants.TAG_PASSWORD, user.getPassword());
                params.put(Constants.TAG_EMAIL, user.getEmail());
                params.put(Constants.TAG_PHONE, user.getPhone());
                params.put(Constants.TAG_NAME,user.getName());
//                params.put("tag", "storeproduct");
                return params;
            }
        };
        Volley.newRequestQueue(context.getApplicationContext()).add(stringRequest);
    }

    private void handlerAfterRegister(String res) {
        String alert = "";
        try {
            JSONObject jsonObject = new JSONObject(res);
            if (Integer.parseInt(jsonObject.getString("success")) == 1) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                alert = "Đăng ký thành công";
                Toast.makeText(context.getApplicationContext(), alert, Toast.LENGTH_SHORT).show();
            } else {
                alert = jsonObject.getString("Đăng ký thất bại");
                Toast.makeText(context.getApplicationContext(), alert, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
