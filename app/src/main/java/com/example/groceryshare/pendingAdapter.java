package com.example.groceryshare;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class pendingAdapter extends RecyclerView.Adapter<pendingAdapter.MyViewHolder>{
    ArrayList<String> storeName = new ArrayList<String>();
    ArrayList<String> orderID = new ArrayList<String>();
    ArrayList<String> name = new ArrayList<String>();
    Context context;
    public pendingAdapter(Context ct, ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3){
        context = ct;
        storeName= s1;
        orderID = s2;
        name = s3;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pending_trips_row, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.storeName_text.setText(storeName.get(position));
        holder.orderId_text.setText(orderID.get(position));
        holder.name_text.setText(name.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PendingActivity)context).goDetails(orderID.get(position));
            }
        });
    }
    @Override
    public int getItemCount() {
        return storeName.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        //find the content here
        TextView storeName_text, orderId_text, name_text;
        Button button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            storeName_text = itemView.findViewById(R.id.storeName_text);
            orderId_text = itemView.findViewById(R.id.orderId_text);
            name_text = itemView.findViewById(R.id.name_text);
            button =itemView.findViewById(R.id.pickOrder);
        }
    }
    public void addOrder(String s1, String s2, String s3) {
        storeName.add(s1);
        orderID.add(s2);
        name.add(s3);
        notifyItemInserted(storeName.size()-1);
    }
}