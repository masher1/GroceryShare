package com.example.groceryshare;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class listAdapter extends RecyclerView.Adapter<listAdapter.ItemViewHolder> {

    public static ArrayList<GroceryItem> shopList;
    private LayoutInflater layoutInflater;

    public listAdapter(Context context, ArrayList<GroceryItem> shopList) {
        layoutInflater = LayoutInflater.from(context);
        this.shopList = shopList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
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
        protected TextView itemName, quantity, brand;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.et_name);
            quantity = (TextView) itemView.findViewById(R.id.et_quantity);
            brand = (TextView) itemView.findViewById(R.id.et_brand);

        }
    }

    public void addItem(GroceryItem item) {
        if (shopList == null) shopList = new ArrayList();
        shopList.add(item);
        //notifyDataSetChanged();
        notifyItemInserted(shopList.size()-1);
    }

}
