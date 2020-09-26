package com.poly.printcode.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.poly.printcode.R;
import com.poly.printcode.function.AllProductActivity;
import com.poly.printcode.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class AdapterProduct extends BaseAdapter {
    AllProductActivity context;
    ArrayList<Product> arrayList;

    public AdapterProduct(AllProductActivity context, ArrayList<Product> products) {
        this.context = context;
        this.arrayList = products;
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
    public static class ViewHolder{
        ImageView imgView;
        TextView tv_product, tv_price, tv_id;
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
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
           view = inflater.inflate(R.layout.item_product, null);
           holder.imgView = view.findViewById(R.id.imgView_item);
           holder.tv_product = view.findViewById(R.id.tv_product);
           holder.tv_price = view.findViewById(R.id.tv_price);
           holder.tv_id = view.findViewById(R.id.tv_id);
           view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Product product = new Product();
        product = arrayList.get(i);
        holder.tv_id.setText(product.getId());
        holder.tv_product.setText(product.getProduct());
        holder.tv_price.setText(product.getPrice()+ " Đồng");
        final String img = product.getImg();
        final Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = loadImage(img);
                holder.imgView.post(new Runnable() {
                    @Override
                    public void run() {
                        holder.imgView.setImageBitmap(bitmap);
                    }
                });
            }
        });
        myThread.start();
        return view;
    }
}
