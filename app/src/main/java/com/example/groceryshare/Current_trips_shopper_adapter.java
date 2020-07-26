package com.example.groceryshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Current_trips_shopper_adapter extends RecyclerView.Adapter<Current_trips_shopper_adapter.MyViewHolder>{

    ArrayList<String> data1 = new ArrayList<String>();
    ArrayList<String> data2 = new ArrayList<String>();
    ArrayList<String> data3 = new ArrayList<String>();
    ArrayList<String> orderID = new ArrayList<String>();
    String userID;

    Context context;

    public Current_trips_shopper_adapter(Context ct, ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3, ArrayList<String> s4, String id){
        context = ct;
        data1= s1;
        data2 = s2;
        data3 = s3;
        orderID = s4;
        userID = id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.current_trips_shopper_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.fullName_text.setText(data1.get(position));
        holder.storeName_text.setText(data2.get(position));
        holder.howFar_text.setText(data3.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CurrentTripsShopper)context).goDetails(orderID.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        //find the content here
        TextView fullName_text, storeName_text, howFar_text;
        Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName_text = itemView.findViewById(R.id.fullName_text);
            storeName_text = itemView.findViewById(R.id.storeName_text);
            howFar_text = itemView.findViewById(R.id.howFar_text);
            button = itemView.findViewById(R.id.pickOrder);
        }
    }
    public void addOrder(String s1, String s2, String s3, String s4) {
        data1.add(s1);
        data2.add(s2);
        data3.add(s3);
        orderID.add(s4);
        //notifyDataSetChanged();
        notifyItemInserted(data1.size()-1);
    }

}
