package com.contactapp.www.contactsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.contactapp.www.contactsapp.R;
import com.contactapp.www.contactsapp.activity.DetailContactActivity;
import com.contactapp.www.contactsapp.constant.Constant;
import com.contactapp.www.contactsapp.contactbean.ContactBean;

import java.util.List;

/**
 * ContactListAdapter custom adapter for contact list
 */
public class ContactListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ContactBean> contactBeans;

    public ContactListAdapter(Context context, List<ContactBean> objectVehicleList) {
        this.context = context;
        contactBeans = objectVehicleList;
    }

    private class ContainerViewHolder extends RecyclerView.ViewHolder {
        TextView firstName;
        TextView lastName;
        LinearLayout linearRow;

        ContainerViewHolder(View view) {
            super(view);
            linearRow = view.findViewById(R.id.contact_container);
            firstName = view.findViewById(R.id.first_name);
            lastName = view.findViewById(R.id.last_name);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.alert_row, viewGroup, false);
        return new ContainerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ContainerViewHolder mHolder = (ContainerViewHolder) viewHolder;
        final ContactBean contactBeanObject=contactBeans.get(position);
        mHolder.firstName.setText(contactBeanObject.getFirstName());
        mHolder.lastName.setText(contactBeanObject.getLastName());
        mHolder.linearRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,DetailContactActivity.class);
                intent.putExtra(Constant.CONTACT_DETAIL_KEY, contactBeanObject);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        int countSize = contactBeans.size();
        return countSize;
    }

}
