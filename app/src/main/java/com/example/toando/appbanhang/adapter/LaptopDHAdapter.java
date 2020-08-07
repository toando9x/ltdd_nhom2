package com.example.toando.appbanhang.activity.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toando.appbanhang.R;
import com.example.toando.appbanhang.activity.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by ToanDo on 7/9/2020.
 */

public class LaptopDHAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayLaptopDH;

    public LaptopDHAdapter(Context context, ArrayList<Sanpham> arrayDienthoai) {
        this.context = context;
        this.arrayLaptopDH = arrayDienthoai;
    }

    @Override
    public int getCount() {
        return arrayLaptopDH.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayLaptopDH.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txttenlaptopDH, txtgialaptopDH, txtmota;
        public ImageView imglaptopDH;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_laptopdohoa, null);
            viewHolder.txttenlaptopDH = (TextView) view.findViewById(R.id.textviewLaptopDoHoa);
            viewHolder.txtgialaptopDH = (TextView) view.findViewById(R.id.textviewGiaLaptopDoHoa);
            viewHolder.txtmota = (TextView) view.findViewById(R.id.motaLaptopDoHoa);
            viewHolder.imglaptopDH = (ImageView) view.findViewById(R.id.imageviewLaptopDoHoa);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txttenlaptopDH.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgialaptopDH.setText("Giá : " + decimalFormat.format(sanpham.getGiasanpham()) + " VND");
        viewHolder.txtmota.setMaxLines(2);
        viewHolder.txtmota.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmota.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .error(R.drawable.error)
                .into(viewHolder.imglaptopDH);
        return view;
    }
}
