package com.example.toando.appbanhang.activity.adapter;
import com.example.toando.appbanhang.R;
import com.example.toando.appbanhang.activity.model.Danhmuc;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class DanhmucAdapter extends BaseAdapter {
    ArrayList<Danhmuc> arrayDanhmuc;
    Context context;

    public DanhmucAdapter(ArrayList<Danhmuc> arrayDanhmuc, Context context) {
        this.arrayDanhmuc = arrayDanhmuc;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayDanhmuc.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayDanhmuc.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        TextView txtdanhmuc;
        ImageView imgdanhmuc;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_danhmuc, null);
            viewHolder.txtdanhmuc = (TextView) view.findViewById(R.id.textviewdanhmuc);
            viewHolder.imgdanhmuc = (ImageView) view.findViewById(R.id.imageviewdanhmuc);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Danhmuc danhmuc = (Danhmuc) getItem(i);
        viewHolder.txtdanhmuc.setText(danhmuc.getDanhmuc());
        Picasso.with(context).load(danhmuc.getHinhanh_dm())
                .into(viewHolder.imgdanhmuc);
        return view;
    }
}
