package com.example.groceryshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Shopping_trips_available_adapter extends RecyclerView.Adapter<Shopping_trips_available_adapter.MyViewHolder>{

    String data1[], data2[], data3[];
    int images[];
    Context context;

    public Shopping_trips_available_adapter(Context ct, String s1[], String s2[], String s3[], int img[]){
        context = ct;
        data1= s1;
        data2 = s2;
        data3 = s3;
        images = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.shopping_trips_available_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.fullName_text.setText(data1[position]);
        holder.storeName_text.setText(data2[position]);
        holder.howFar_text.setText(data3[position]);
        try{
            holder.profile_image.setImageResource(images[position]);
        }
        catch (ArrayIndexOutOfBoundsException e){
            holder.profile_image.setImageResource(R.drawable.ic_baseline_error_face_24);
        }
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
//find the content here
        TextView fullName_text, storeName_text, howFar_text;
        ImageView profile_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName_text = itemView.findViewById(R.id.fullName_text);
            storeName_text = itemView.findViewById(R.id.storeName_text);
            howFar_text = itemView.findViewById(R.id.howFar_text);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }
}
