package com.poly.printcode.function;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.poly.printcode.R;

public class UserActivity extends AppCompatActivity {

    String getname, getemail, getphone, getpassword;
//    String traname, traemail, traphone, trapass;

    private TextView tvName, tvEmail, tvPhone, password;
    Button update, getfriend, add_product, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        tvName = findViewById(R.id.tvname);
        tvEmail = findViewById(R.id.tvemail);
        tvPhone = findViewById(R.id.tvphone);
        password = findViewById(R.id.tvpassword);
        update = findViewById(R.id.btn_update1);
        getfriend = findViewById(R.id.getall);
        add_product = findViewById(R.id.btn_addproduct);
        logout  = findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });

//        traname = tvName.getText().toString();
//        traemail = tvEmail.getText().toString();
//        traphone = tvPhone.getText().toString();
//        trapass = password.getText().toString();

        getfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserActivity.this, AllfriendActivity.class);
                String getName;
                getName = tvName.getText().toString();
                i.putExtra("name", getName);
                startActivity(i);
            }
        });
        update.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String traname, traemail, traphone, trapass;
                traname = tvName.getText().toString();
                traemail = tvEmail.getText().toString();
                traphone = tvPhone.getText().toString();
                trapass = password.getText().toString();
                Intent i = new Intent(UserActivity.this, CheckUpdate.class);
                i.putExtra("name", traname);
                i.putExtra("email", traemail);
                i.putExtra("phone", traphone);
                i.putExtra("password", trapass);
                startActivity(i);
                return false;
            }
        });

        Intent intent = getIntent();
        getname = intent.getStringExtra("name");
        getemail = intent.getStringExtra("email");
        getphone = intent.getStringExtra("phone");
        getpassword = intent.getStringExtra("password");

        tvName.setText(getname);
        tvEmail.setText(getemail);
        tvPhone.setText("0"+getphone);
        password.setText(getpassword);

    }

}