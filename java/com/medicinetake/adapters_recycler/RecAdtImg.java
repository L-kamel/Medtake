package com.medicinetake.adapters_recycler;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medicinetake.R;
import com.medicinetake.db_tables.ImageMed;
import com.medicinetake.interfaces.OnClick_imgMed;

import java.util.ArrayList;

public class RecAdtImg extends RecyclerView.Adapter<RecAdtImg.ImgHolder> {

    private ArrayList<ImageMed> imageMeds = new ArrayList<>();
    private OnClick_imgMed onClick_imgMed;

    public RecAdtImg(ArrayList<ImageMed> imageMeds, OnClick_imgMed onClick_imgMed) {
        this.imageMeds = imageMeds;
        this.onClick_imgMed = onClick_imgMed;
    }

    @NonNull
    @Override
    public ImgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new ImgHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_img_med, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImgHolder holder, int position) {
        ImageMed imageMed = imageMeds.get(position);
        holder.img_med.setImageBitmap(BitmapFactory.decodeByteArray(imageMed.getImg_med(), 0, imageMed.getImg_med().length));
        holder.img_med.setTag(imageMed.getId_img_med());

    }

    @Override
    public int getItemCount() {
        return imageMeds.size();
    }

    public class ImgHolder extends RecyclerView.ViewHolder {
        public ImageView img_med;

        public ImgHolder(@NonNull View itemView) {
            super(itemView);

            img_med = itemView.findViewById(R.id.img_med);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id_img = (int) img_med.getTag();
                    onClick_imgMed.onClickImgMed(id_img);
                }
            });
        }
    }

}
