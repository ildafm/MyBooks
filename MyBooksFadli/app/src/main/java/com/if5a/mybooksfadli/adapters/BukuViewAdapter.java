package com.if5a.mybooksfadli.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.if5a.mybooksfadli.R;
import com.if5a.mybooksfadli.models.Buku;
import com.if5a.mybooksfadli.utilities.ItemClickListener;

import java.util.ArrayList;

public class BukuViewAdapter extends RecyclerView.Adapter<BukuViewAdapter.viewHolder> {
    private ArrayList<Buku> data = new ArrayList<>();
    private Context context;
    private ItemClickListener<Buku> itemClickListener;

    public BukuViewAdapter(ItemClickListener<Buku> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setData(ArrayList<Buku> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BukuViewAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_books, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BukuViewAdapter.viewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        Buku buku = data.get(position);
        holder.tvTitle.setText(buku.getTitle());
        holder.tvAuthor.setText(buku.getAuthor());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(buku, pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvAuthor;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
        }
    }
}
