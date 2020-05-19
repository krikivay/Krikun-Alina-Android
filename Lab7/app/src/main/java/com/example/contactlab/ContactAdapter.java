package com.example.contactlab;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//адаптер для списка контактов
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<StringBuffer> contacts;

    ContactAdapter(Context context, List<StringBuffer> contacts) {
        this.contacts = contacts;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ViewHolder holder, int position) {
        StringBuffer contact = contacts.get(position);
        holder.tv.setText(contact);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tv;
        ViewHolder(View view){
            super(view);
            tv = view.findViewById(R.id.tv);

        }
    }
}
