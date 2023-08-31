package com.medicinetake.adapters_recycler;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medicinetake.R;
import com.medicinetake.db_tables.User;
import com.medicinetake.interfaces.OnClickUser;

import java.util.ArrayList;

public class RecAdtUser extends RecyclerView.Adapter<RecAdtUser.UserHolder> {

    private ArrayList<User> users = new ArrayList<>();
    private OnClickUser onClick;
    public Context context;

    public RecAdtUser(ArrayList<User> users, OnClickUser onClick) {
        this.users = users;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_user, null, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User user = users.get(position);
        holder.tv_username.setText(user.getUsername());
        if (user.getUser_img() != null) {
            holder.img_user_pic.setImageBitmap(BitmapFactory.decodeByteArray(user.getUser_img(), 0, user.getUser_img().length));
        }
        holder.img_user_pic.setTag(user.getId_user());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class UserHolder extends RecyclerView.ViewHolder {

        public TextView tv_username;
        public ImageView img_user_pic;


        public UserHolder(@NonNull final View itemView) {
            super(itemView);

            tv_username = itemView.findViewById(R.id.tv_username);
            img_user_pic = itemView.findViewById(R.id.img_user_pic_chs);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int idUser = (int) img_user_pic.getTag();
                    onClick.onClickPicUser(idUser);
                    Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_rec_user);
                    itemView.startAnimation(animation);
                }
            });
        }

    }

}
