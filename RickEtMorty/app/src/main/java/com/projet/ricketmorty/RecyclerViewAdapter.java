package com.projet.ricketmorty;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.projet.ricketmorty.model.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Result> resultList;
    private final Context context;

    public RecyclerViewAdapter(ArrayList<Result> resultList, Context context) {
        this.resultList = resultList;
        this.context = context;
    }

    public void addItems(ArrayList<Result> newItems) {
        int startPosition = resultList.size();
        resultList.addAll(newItems);
        notifyItemRangeInserted(startPosition, newItems.size());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = resultList.get(position);

        String name = result.getName();
        if (name != null) {
            holder.name.setText(name);
        } else {
            holder.name.setText("N/A");
        }

        String avatar = result.getImage();
        if (avatar != null) {
            Picasso.get().load(avatar).into(holder.img);
        } else {
            holder.name.setText("N/A");
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ll.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.back_press));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.ll.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.back));
                    }
                }, 100);
                Intent intent = new Intent(context, CharInfoActivity.class);
                intent.putExtra("info", resultList.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public LinearLayout ll;
        public LinearLayout v1;
        public ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll = itemView.findViewById(R.id.linearLayout);
            v1 = itemView.findViewById(R.id.vert2);
            name = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.avatar);
        }
    }
}
