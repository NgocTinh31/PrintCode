package com.poly.printcode.function;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.poly.printcode.R;
import com.poly.printcode.connect.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckUpdate extends AppCompatActivity {
    EditText username, password;
    Button gotoUpdate, cane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_update);
        username = findViewById(R.id.edt_username);
        password = findViewById(R.id.edt_password);
        gotoUpdate = findViewById(R.id.btn_gotoupdate);
        cane = findViewById(R.id.btn_cane);

        final String name, email, phone, pass;
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");
        pass = intent.getStringExtra("password");

        gotoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               CheckUpdate();
            }
        });
        cane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trname, tremail, trphone, trpass;
                trname = name;
                tremail = email;
                trphone =phone;
                trpass = pass;
                Intent intent = new Intent(CheckUpdate.this, UserActivity.class);
                intent.putExtra("name", trname);
                intent.putExtra("email", tremail);
                intent.putExtra("phone", trphone);
                intent.putExtra("password", trpass);
                startActivity(intent);
            }
        });
    }

    private void CheckUpdate() {
        final StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.url_user_detail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AfterCheckUpdate(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("username", username.getText().toString());
                param.put("password", password.getText().toString());
//                param.put("tag","login");
                return param;
            }
        };
        Volley.newRequestQueue(CheckUpdate.this).add(stringRequest);
    }

    private void AfterCheckUpdate(String res) {
        String mess = "";
        String succcess = "";
        String strusername, strpassword, stremail, strphone, strname;
        try {
            JSONObject jsonObject = new JSONObject(res);
            mess = jsonObject.getString(Constants.MESS);
            succcess = jsonObject.getString(Constants.TAG_SUCCESS);
            if (Integer.parseInt(succcess) == 1) {
                JSONArray array = jsonObject.getJSONArray("users");
                JSONObject object = array.getJSONObject(0);
                strusername = username.getText().toString();
                strpassword = password.getText().toString();
                stremail = object.getString(Constants.TAG_EMAIL);
                strphone = object.getString(Constants.TAG_PHONE);
                strname = object.getString(Constants.TAG_NAME);
                Intent intent = new Intent(CheckUpdate.this, UpdateActivity.class);
                intent.putExtra("username", strusername);
                intent.putExtra("password", strpassword);
                intent.putExtra("email", stremail);
                intent.putExtra("phone", strphone);
                intent.putExtra("name", strname);
                startActivity(intent);
                Toast.makeText(CheckUpdate.this, "Xát minh thành công" + succcess, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CheckUpdate.this, "" + mess + "" + succcess, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}