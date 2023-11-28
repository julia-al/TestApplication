package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RVAdapterManagement extends RecyclerView.Adapter<RVAdapterManagement.ViewHolder> {

    Context context;
    List<User> UserList;

    public RVAdapterManagement(Context context, List<User> TempList, FragmentActivity activity) {

        this.UserList = TempList;
        this.context = context;
    }

    @Override
    public RVAdapterManagement.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        RVAdapterManagement.ViewHolder viewHolder = new RVAdapterManagement.ViewHolder(view);
        return viewHolder;
    }


    public void onBindViewHolder(@NonNull final RVAdapterManagement.ViewHolder holder, int position) {


        User users = UserList.get(position);
        holder.firstNameView.setText(users.getFirstName());
        holder.departamentView.setText(users.getDepartment());
        holder.last_name.setText(users.getLastName());
        holder.birthday.setText(users.getBirthday());
        holder.phone.setText(users.getPhone());
        Picasso.get()
                .load(users.getAvatarUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra("firstname", UserList.get(holder.getAdapterPosition()).getFirstName());
                intent.putExtra("lastname", UserList.get(holder.getAdapterPosition()).getLastName());
                intent.putExtra("departament", UserList.get(holder.getAdapterPosition()).getDepartment());
                intent.putExtra("birthday", UserList.get(holder.getAdapterPosition()).getBirthday());
                intent.putExtra("phone", UserList.get(holder.getAdapterPosition()).getPhone());
                intent.putExtra("avatar", UserList.get(holder.getAdapterPosition()).getAvatarUrl());
                Picasso.get()
                        .load(users.getAvatarUrl())
                        .fit()
                        .into(holder.imageView);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return UserList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView firstNameView;
        public TextView departamentView;
        public ImageView imageView;
        public TextView last_name;
        public TextView birthday;
        public TextView phone;
        public TextView position;
        public TextView userTag;

        CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);

            firstNameView = itemView.findViewById(R.id.first_name);
            departamentView = itemView.findViewById(R.id.departament);
            imageView = itemView.findViewById(R.id.imageUser);
            last_name = itemView.findViewById(R.id.last_name);
            birthday = itemView.findViewById(R.id.birthday);
            phone = itemView.findViewById(R.id.phone);

            mCardView = itemView.findViewById(R.id.cardview1);
        }
    }
}


