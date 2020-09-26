package com.poly.printcode.function;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.poly.printcode.R;
import com.poly.printcode.adapter.AdapterUser;
import com.poly.printcode.connect.Constants;
import com.poly.printcode.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllfriendActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<User> arrayList = new ArrayList<>();
    String name;
    AdapterUser adapterUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allfriend);
        listView = findViewById(R.id.lv_user);
        getAllFriend();
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
    }

    private void getAllFriend() {
        final StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.url_all_friend, new Response.Listener<String>() {
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
                param.put("username", name);
                return param;
            }
        };
        Volley.newRequestQueue(AllfriendActivity.this).add(stringRequest);
    }

    private void AfterGetAll(String res) {
        String mess = "";
        String succcess = "";
        String strphone, strname;
        try {
            JSONObject jsonObject = new JSONObject(res);
            mess = jsonObject.getString(Constants.MESS);
            succcess = jsonObject.getString(Constants.TAG_SUCCESS);
            if (Integer.parseInt(succcess) == 1) {
                JSONArray array = jsonObject.getJSONArray(Constants.TAG_USERS);
                for (int i = 0; i < array.length(); i ++){
                    JSONObject object = array.getJSONObject(i);
                    strname = object.getString(Constants.TAG_NAME);
                    strphone = object.getString(Constants.TAG_PHONE);

                    User user = new User();
                    user.setName(strname);
                    user.setPhone(strphone);
                    arrayList.add(user);
                }
                adapterUser = new AdapterUser(this, arrayList);
                listView.setAdapter(adapterUser);
            } else {
                Toast.makeText(AllfriendActivity.this, "Null" , Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}