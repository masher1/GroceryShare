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

import java.util.List;

public class Past_trips_shopper_adapter extends RecyclerView.Adapter<Past_trips_shopper_adapter.MyViewHolder> {

    Context context;
    List orders;

    public Past_trips_shopper_adapter(Context ct, List orders) {
        context = ct;
        this.orders = orders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.past_trips_shopper_row, parent, false);
        return new Past_trips_shopper_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final orderData data = (orderData) orders.get(position);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("profileImages/" + data.buyerID + ".jpeg");
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
        holder.fullName_text.setText(data.name);
        holder.storeName_text.setText(data.storeName);
        holder.howFar_text.setText(data.distance);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PastTripsShopper) context).goDetails(data.orderID);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //find the content here
        TextView fullName_text, storeName_text, howFar_text;
        ImageView profilePhoto;
        Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName_text = itemView.findViewById(R.id.fullName_text);
            storeName_text = itemView.findViewById(R.id.storeName_text);
            howFar_text = itemView.findViewById(R.id.howFar_text);
            profilePhoto = itemView.findViewById(R.id.profile_image);
            button = itemView.findViewById(R.id.pickOrder);
        }
    }
}
