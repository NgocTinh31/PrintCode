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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {
    EditText name, email, phone, password;
    Button update, cane;
    String strName, strEmail, strPhone, strPassword, strusername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        name = findViewById(R.id.edt_name);
        email = findViewById(R.id.edt_email);
        phone = findViewById(R.id.edt_phone);
        password = findViewById(R.id.edt_password);
        update = findViewById(R.id.btn_update);
        cane = findViewById(R.id.btn_cane);
        // lấy dữ liệu
        Intent intent = getIntent();
        strName = intent.getStringExtra("name");
        strEmail = intent.getStringExtra("email");
        strPhone = intent.getStringExtra("phone");
        strPassword = intent.getStringExtra("password");
        strusername = intent.getStringExtra("username");
//        show dữ liệu lên edt
        name.setText(strName);
        email.setText(strEmail);
        phone.setText(strPhone);
        password.setText(strPassword);

        // gán dữ liệu vào str
//        strEmail = email.getText().toString();
//        strPhone = phone.getText().toString();
//        strName = name.getText().toString();
//        strPassword = password.getText().toString();

        cane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this, UserActivity.class);
                intent.putExtra("name", strName);
                intent.putExtra("email", strEmail);
                intent.putExtra("phone", strPhone);
                intent.putExtra("password", strPassword);
                startActivity(intent);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = email.getText().toString();
                strPhone = phone.getText().toString();
                strName = name.getText().toString();
                strPassword = password.getText().toString();
                Update();
                Toast.makeText(UpdateActivity.this, ""+ strusername +"" + strName, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void Update(){
        final StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.url_update_user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AfterUpdate(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateActivity.this, "Không thể truy cập", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("username", strusername);
                param.put("password", strPassword);
                param.put("email", strEmail);
                param.put("phone", strPhone);
                param.put("name", strName);
//                param.put("tag","login");
                return param;
            }
        };
        Volley.newRequestQueue(UpdateActivity.this).add(stringRequest);
    }
    private void AfterUpdate(String res) {
        String mess ="";
        String sussess = "";
        try {
            JSONObject jsonObject = new JSONObject(res);
            sussess = jsonObject.getString(Constants.TAG_SUCCESS);
            mess = jsonObject.getString(Constants.MESS);
            if (Integer.parseInt(sussess) == 1){
                Intent intent = new Intent(UpdateActivity.this, UserActivity.class);
                intent.putExtra("name", strName);
                intent.putExtra("email", strEmail);
                intent.putExtra("phone", strPhone);
                intent.putExtra("password", strPassword);
                startActivity(intent);
                Toast.makeText(UpdateActivity.this, ""+ mess, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(UpdateActivity.this, ""+ mess, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}