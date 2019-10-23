package com.contactapp.www.contactsapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.contactapp.www.contactsapp.R;
import com.contactapp.www.contactsapp.constant.Constant;
import com.contactapp.www.contactsapp.entity.Contact;

import java.util.List;

/**
 * MessageListAdapter custom adapter for the sent Message History list
 */
public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Contact> contactList;

    public MessageListAdapter(Context context) {
        this.context = context;
    }

    private class ContainerViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView contactNumber;
        TextView otp;
        TextView time;
        LinearLayout linearRow;

        ContainerViewHolder(View view) {
            super(view);
            linearRow = view.findViewById(R.id.message_container);
            name = view.findViewById(R.id.name);
            contactNumber = view.findViewById(R.id.contact);
            otp = view.findViewById(R.id.otp);
            time = view.findViewById(R.id.time);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.message_row, viewGroup, false);
        return new ContainerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ContainerViewHolder mHolder = (ContainerViewHolder) viewHolder;
        final Contact contactBeanObject = contactList.get(position);
        mHolder.name.setText(contactBeanObject.getContactName());
        mHolder.contactNumber.setText(contactBeanObject.getPhoneNumber());
        mHolder.otp.setText(contactBeanObject.getOtpSMS());
        mHolder.time.setText(Constant.getDate(Long.parseLong(contactBeanObject.getTimeValue()),
                "dd/MM/yyyy HH:mm:ss a"));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void setNotes(List<Contact> contacts) {
        this.contactList = contacts;
        notifyDataSetChanged();
    }

}
