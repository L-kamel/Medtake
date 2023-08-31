package com.medicinetake.adapters_recycler;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medicinetake.R;
import com.medicinetake.db_tables.Pic;
import com.medicinetake.interfaces.OnClickPic;

import java.util.ArrayList;

public class RecAdtPic extends RecyclerView.Adapter<RecAdtPic.PicHolder> {

    private ArrayList<Pic> picArrayList = new ArrayList<>();
    private OnClickPic clickPic;

    public RecAdtPic(ArrayList<Pic> picArrayList, OnClickPic clickPic) {
        this.picArrayList = picArrayList;
        this.clickPic = clickPic;
    }

    @NonNull
    @Override
    public PicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_pic_user, null, false);
        return new PicHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PicHolder holder, int position) {
        Pic pic = picArrayList.get(position);
        holder.img_pic.setImageBitmap(BitmapFactory.decodeByteArray(pic.getPic(), 0, pic.getPic().length));
        holder.img_pic.setTag(pic.getId_pic_user());
    }

    @Override
    public int getItemCount() {
        return picArrayList.size();
    }

    public class PicHolder extends RecyclerView.ViewHolder {
        public ImageView img_pic;

        public PicHolder(@NonNull View itemView) {
            super(itemView);
            img_pic = itemView.findViewById(R.id.img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id_pic = (int) img_pic.getTag();
                    clickPic.onClickPic(id_pic);
                }
            });

        }
    }

}
