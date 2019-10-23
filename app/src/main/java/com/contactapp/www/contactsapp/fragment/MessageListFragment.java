package com.contactapp.www.contactsapp.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.contactapp.www.contactsapp.R;
import com.contactapp.www.contactsapp.adapter.MessageListAdapter;
import com.contactapp.www.contactsapp.entity.Contact;
import com.contactapp.www.contactsapp.view_model.ContactViewModel;

import java.util.List;

public class MessageListFragment extends Fragment {
    private View view;
    private RecyclerView contactRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MessageListAdapter messageListAdapter;
    private ContactViewModel contactViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.message_list_fragment, container, false);
        contactRecyclerView = view.findViewById(R.id.recyclerMessageList);
        linearLayoutManager = new LinearLayoutManager(getContext());
        contactRecyclerView.setLayoutManager(linearLayoutManager);
        contactRecyclerView.setHasFixedSize(true);


        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel.getAllContact().observe(MessageListFragment.this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> contacts) {
                //update RecyclerView
                messageListAdapter = new MessageListAdapter(getContext());
                contactRecyclerView.setAdapter(messageListAdapter);
                //Toast.makeText(getActivity(), "SMS History List", Toast.LENGTH_SHORT).show();
                messageListAdapter.setNotes(contacts);

            }
        });

        return view;
    }
}
