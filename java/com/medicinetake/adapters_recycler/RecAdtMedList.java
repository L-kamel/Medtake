package com.medicinetake.adapters_recycler;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medicinetake.R;
import com.medicinetake.db_tables.Categories;
import com.medicinetake.db_tables.ListMedicines;
import com.medicinetake.interfaces.OnClickListMed;

import java.util.ArrayList;

public class RecAdtMedList extends RecyclerView.Adapter<RecAdtMedList.HolderMedList> {


    private ArrayList<ListMedicines> listMedicines = new ArrayList<>();
    private ArrayList<Categories> categories = new ArrayList<>();
    private OnClickListMed onClick_list_med;

    public RecAdtMedList(ArrayList<ListMedicines> listMedicines, ArrayList<Categories> categories, OnClickListMed onClick_list_med) {
        this.listMedicines = listMedicines;
        this.categories = categories;
        this.onClick_list_med = onClick_list_med;
    }

    public RecAdtMedList(ArrayList<ListMedicines> listMedicines) {
        this.listMedicines = listMedicines;
    }

    public RecAdtMedList(ArrayList<ListMedicines> listMedicines, ArrayList<Categories> categories) {
        this.listMedicines = listMedicines;
        this.categories = categories;
    }

    @NonNull
    @Override
    public HolderMedList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderMedList(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_medicines_list, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMedList holder, int position) {
        Categories categories1 = categories.get(position);
        ListMedicines listMed = listMedicines.get(position);
        holder.tv_reference_name.setText(listMed.getList_reference());
        holder.tv_med_category.setText(categories1.getCateg_name());
        if (listMed.getList_img_med() != null) {
            holder.img_med_list.setImageBitmap(BitmapFactory.decodeByteArray(listMed.getList_img_med(),
                    0, listMed.getList_img_med().length));
        }
        holder.img_med_list.setTag(listMed.getId_list_med());

    }

    @Override
    public int getItemCount() {
        return listMedicines.size();
    }


    public void searchList(ArrayList<ListMedicines> medicines) {

        listMedicines = medicines;
        notifyDataSetChanged();


    }

    public class HolderMedList extends RecyclerView.ViewHolder {
        public TextView tv_reference_name, tv_med_category;
        public ImageView img_med_list;

        public HolderMedList(@NonNull View itemView) {
            super(itemView);
            img_med_list = itemView.findViewById(R.id.img_medicines_list);
            tv_reference_name = itemView.findViewById(R.id.text_med_reference_list);
            tv_med_category = itemView.findViewById(R.id.text_category_med);

            img_med_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id_list_med = (int) img_med_list.getTag();
                    onClick_list_med.onClickListMed(id_list_med);



                }
            });


        }
    }

}
