package com.example.recyclerdatalab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
//адаптер для RecyclerView
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<Post> posts;
    private LayoutInflater inflater;

    public DataAdapter(Context context, List<Post> posts)
    {
        this.posts = posts;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i)
    {
        View view = inflater.inflate(R.layout.list_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position)
    {
        Post post = posts.get(position);

        holder.tvUI2.setText(Integer.toString(post.getUserId()));
        holder.tvI2.setText(Integer.toString(post.getId()));
        holder.tvT2.setText(post.getTitle());
        holder.tvB2.setText(post.getBody());
    }

    @Override
    public int getItemCount()
    {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvUI2;
        final TextView tvI2;
        final TextView tvT2;
        final TextView tvB2;

        ViewHolder(View view){
            super(view);
            tvUI2 = view.findViewById(R.id.tvUI2);
            tvI2 = view.findViewById(R.id.tvI2);
            tvT2 = view.findViewById(R.id.tvT2);
            tvB2 = view.findViewById(R.id.tvB2);

        }
    }
}
