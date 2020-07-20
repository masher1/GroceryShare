package com.example.groceryshare;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    public static ArrayList<GroceryItem> shopList;
    private LayoutInflater layoutInflater;

    public ItemAdapter(Context context, ArrayList<GroceryItem> shopList) {
        layoutInflater = LayoutInflater.from(context);
        this.shopList = shopList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.add_item, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.itemName.setText(shopList.get(position).getItemName());
        holder.quantity.setText(shopList.get(position).getQuantity());
        holder.brand.setText(shopList.get(position).getBrand());
        Log.d("print","yes");
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        protected EditText itemName, quantity, brand;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemName = (EditText) itemView.findViewById(R.id.et_name);
            quantity = (EditText) itemView.findViewById(R.id.et_quantity);
            brand = (EditText) itemView.findViewById(R.id.et_brand);

            itemName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    shopList.get(getAdapterPosition()).setItemName(itemName.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            quantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    shopList.get(getAdapterPosition()).setQuantity(quantity.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            brand.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    shopList.get(getAdapterPosition()).setBrand(brand.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

    public void addItem(GroceryItem item) {
        if (shopList == null) shopList = new ArrayList();
        shopList.add(item);
        //notifyDataSetChanged();
        notifyItemInserted(shopList.size()-1);
    }
}

