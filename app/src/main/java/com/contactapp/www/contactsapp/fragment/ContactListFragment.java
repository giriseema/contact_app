package com.contactapp.www.contactsapp.fragment;

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
import com.contactapp.www.contactsapp.adapter.ContactListAdapter;
import com.contactapp.www.contactsapp.constant.Constant;
import com.contactapp.www.contactsapp.contactbean.ContactBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContactListFragment extends Fragment {
    private View view;
    private RecyclerView contactRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ContactListAdapter contactListAdapter;
    private List<ContactBean> contactBeans;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contact_list_fragment, container, false);
        contactRecyclerView=view.findViewById(R.id.recyclerContactList);
        linearLayoutManager=new LinearLayoutManager(getContext());
        contactRecyclerView.setLayoutManager(linearLayoutManager);
        getListContact();
        contactListAdapter=new ContactListAdapter(getContext(),contactBeans);
        contactRecyclerView.setAdapter(contactListAdapter);
        return view;
    }
    // get List of contact from the fake JSON string
    private void getListContact() {
        String json=Constant.JSON_STRING;
        Type listType = new TypeToken<ArrayList<ContactBean>>() {
        }.getType();
        contactBeans = new Gson().fromJson(json, listType);
    }
}
