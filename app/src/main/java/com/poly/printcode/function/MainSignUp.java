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
import com.poly.printcode.AsynTask.UserAdd;
import com.poly.printcode.R;
import com.poly.printcode.connect.Constants;
import com.poly.printcode.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainSignUp extends AppCompatActivity {
    private Button btn_signup, btn_cane;
    private EditText usrename, password, email, phone, name;
    UserAdd userAdd;
    User user;
    String strUserName, strPassword, strEmail, strPhone, strName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sign_up);
        btn_signup = findViewById(R.id.signup);
        btn_cane = findViewById(R.id.cane);

        usrename = findViewById(R.id.edt_username);
        password = findViewById(R.id.edt_password1);
//        checkpass = findViewById(R.id.checkpass);
        email = findViewById(R.id.edt_email);
        phone = findViewById(R.id.edt_phone);
        name = findViewById(R.id.edt_name);

        btn_cane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
                finish();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checklogin();
            }
        });
    }


    private void checklogin(){
        final StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.url_check_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                handlerAfterLogin(response);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainSignUp.this, "Volley Erorr", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("username", usrename.getText().toString());
//                param.put("tag","login");
                return param;
            }
        };
        Volley.newRequestQueue(MainSignUp.this).add(stringRequest);
    }

    private void handlerAfterLogin(String res) {
        String mess = "";
        String succcess ="";
        try {
            JSONObject jsonObject = new JSONObject(res);
            mess = jsonObject.getString(Constants.MESS);
            succcess = jsonObject.getString(Constants.TAG_SUCCESS);
            if (Integer.parseInt(succcess) == 1) {
                strUserName = usrename.getText().toString();
                strPassword = password.getText().toString();
//                strCheckPass = checkpass.getText().toString();
                strEmail = email.getText().toString();
                strPhone = phone.getText().toString();
                strName = name.getText().toString();
                if (strUserName.matches("")||
                        strPassword.matches("") ||
                        strEmail.matches("") ||
                        strPhone.matches("") ||
                        strName.matches("") ){
                    Toast.makeText(MainSignUp.this, "Must not be left blank", Toast.LENGTH_SHORT).show();
                } else {
//                    if (strPassword == strCheckPass){
                        userAdd = new UserAdd(MainSignUp.this);
                        user = new User();
                        user.setUsername(strUserName);
                        user.setPassword(strPassword);
                        user.setEmail(strEmail);
                        user.setPhone(strPhone);
                        user.setName(strName);
                        userAdd.insertUser(user);
                        finish();
//                    } else {
//                        Toast.makeText(MainSignUp.this, "Faill check password", Toast.LENGTH_SHORT).show();
//                    }
                }
            } else {
                mess = jsonObject.getString(Constants.MESS);
                succcess = jsonObject.getString(Constants.TAG_SUCCESS);
                Toast.makeText(this, mess + " " + succcess, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void goToLogin() {
        Intent i = new Intent(MainSignUp.this, MainActivity.class);
        startActivity(i);
    }
}