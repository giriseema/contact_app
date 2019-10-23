package com.contactapp.www.contactsapp.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.contactapp.www.contactsapp.dao.ContactDao;
import com.contactapp.www.contactsapp.entity.Contact;
import com.contactapp.www.contactsapp.roomdb.ContactDatabase;

import java.util.List;

public class ContactRepository {
    private ContactDao contactDao;
    private LiveData<List<Contact>> allSendContact;

    public ContactRepository(Application application) {
        ContactDatabase database = ContactDatabase.getInstance(application);
        contactDao = database.contactDao();
        allSendContact = contactDao.getAllContact();
    }

    public void insert(Contact contact) {
        new InsertContactAsyncTask(contactDao).execute(contact);
    }

    public LiveData<List<Contact>> getAllContact() {
        return allSendContact;
    }

    private static class InsertContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao contactDao;

        private InsertContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.insertSentContact(contacts[0]);
            return null;
        }
    }

}
