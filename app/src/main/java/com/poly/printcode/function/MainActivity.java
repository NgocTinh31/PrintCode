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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button signup, login;
    EditText eduser, edPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.btn_login);
        signup = findViewById(R.id.btn_signup);
        eduser = findViewById(R.id.edt_userLogin);
        edPass = findViewById(R.id.edt_passwordLogin);
        signup.setOnClickListener(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerLogin();
            }
        });
    }
    private void handlerLogin() {
        final StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.url_user_detail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                handlerAfterLogin(response);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Volley Erorr", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("username", eduser.getText().toString());
                param.put("password", edPass.getText().toString());
//                param.put("tag","login");
                return param;
            }
        };
        Volley.newRequestQueue(MainActivity.this).add(stringRequest);
    }

    private void handlerAfterLogin(String res) {
        String mess = "";
        String succcess ="";
        try {
            JSONObject jsonObject = new JSONObject(res);
            mess = jsonObject.getString(Constants.MESS);
            succcess = jsonObject.getString(Constants.TAG_SUCCESS);

            if (Integer.parseInt(succcess) == 1) {

                JSONArray user = jsonObject.getJSONArray(Constants.TAG_USERS);
                JSONObject object = user.getJSONObject(0);

                String name, email, phone , password;
                name = object.getString(Constants.TAG_NAME);
                email = object.getString(Constants.TAG_EMAIL);
                phone = object.getString(Constants.TAG_PHONE);
                password = object.getString(Constants.TAG_PASSWORD);

                Intent i = new Intent(MainActivity.this, UserActivity.class);
                i.putExtra("name", name);
                i.putExtra("email", email);
                i.putExtra("phone", phone);
                i.putExtra("password", password);
                finish();
                startActivity(i);
            } else {
                Toast.makeText(MainActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                Intent i = new Intent(MainActivity.this, MainSignUp.class);
                startActivity(i);
        }
    }
}