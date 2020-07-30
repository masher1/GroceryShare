package com.example.groceryshare;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class pendingAdapter extends RecyclerView.Adapter<pendingAdapter.MyViewHolder>{

    ArrayList<String> storeName = new ArrayList<String>();
    ArrayList<String> orderID = new ArrayList<String>();
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> nickNames = new ArrayList<String>();
    ArrayList<String> shopperId = new ArrayList<String>();


    Context context;

    public pendingAdapter(Context ct, ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3, ArrayList<String> s4, ArrayList<String> s5){
        context = ct;
        storeName= s1;
        orderID = s2;
        name = s3;
        nickNames =s4;
        shopperId = s5;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pending_trips_row, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("profileImages/" + shopperId.get(position) + ".jpeg");
        // This gets the download url async
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                final String downloadUrl = uri.toString();
                if (!downloadUrl.equals("default")) {
                    Glide.with(holder.itemView.getContext()).load(downloadUrl).into(holder.profilePhoto);
                }
            }
        });
        if(!storeName.get(position).equals(""))
            holder.storeName_text.setText(storeName.get(position));
        else
            holder.storeName_text.setText("Anywhere");

        holder.order_nickname.setText(nickNames.get(position));
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

        TextView storeName_text, order_nickname, name_text;
        ImageView profilePhoto;

        Button button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            storeName_text = itemView.findViewById(R.id.storeName_text);
            order_nickname = itemView.findViewById(R.id.order_nickname);
            name_text = itemView.findViewById(R.id.name_text);
            profilePhoto = itemView.findViewById(R.id.profile_image);
            button =itemView.findViewById(R.id.pickOrder);
        }
    }
    public void addOrder(String s1, String s2, String s3, String s4, String s5) {
        storeName.add(s1);
        orderID.add(s2);
        name.add(s3);
        nickNames.add(s4);
        shopperId.add(s5);
        notifyItemInserted(storeName.size()-1);
    }
}
