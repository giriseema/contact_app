package com.contactapp.www.contactsapp.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.contactapp.www.contactsapp.entity.Contact;
import com.contactapp.www.contactsapp.repository.ContactRepository;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private ContactRepository repository;
    private LiveData<List<Contact>> allSendContact;
    public ContactViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository(application);
        allSendContact = repository.getAllContact();
    }
    public void insert(Contact note) {
        repository.insert(note);
    }

    public LiveData<List<Contact>> getAllContact() {
        return allSendContact;
    }
}
