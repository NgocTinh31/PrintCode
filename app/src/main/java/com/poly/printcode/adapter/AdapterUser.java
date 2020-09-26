package com.poly.printcode.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.poly.printcode.R;
import com.poly.printcode.function.AllfriendActivity;
import com.poly.printcode.model.User;

import java.util.ArrayList;

public class AdapterUser extends BaseAdapter {
    AllfriendActivity context;
    ArrayList<User> arrayList;

    public AdapterUser(AllfriendActivity context, ArrayList<User> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static class ViewHolder {
        TextView name, phone;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_user, null);
            holder.name = (TextView) view.findViewById(R.id.tv_name_adapter);
            holder.phone = (TextView) view.findViewById(R.id.tv_phone_apdapter);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        User user = new User();
        user = arrayList.get(i);
        holder.name.setText(user.getName());
        holder.phone.setText("0"+user.getPhone());

        return view;
    }
}
