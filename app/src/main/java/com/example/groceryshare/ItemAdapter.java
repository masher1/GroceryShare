package com.example.groceryshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.groceryshare.databinding.AdditemBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    String data1[], data2[], data3[];
    Context context;
    public ItemAdapter(Context ct, String s1[], String s2[], String s3[]){
        context = ct;
        data1= s1;
        data2 = s2;
        data3 = s3;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.name.setText(data1[position]);
        holder.quantity.setText(data2[position]);
        holder.brand.setText(data3[position]);
    }
    @Override
    public int getItemCount() {
        return data1.length;
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        //find the content here
        EditText name, quantity, brand;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.et_name);
            quantity = itemView.findViewById(R.id.et_quantity);
            brand = itemView.findViewById(R.id.et_brand);
        }
    }

}

