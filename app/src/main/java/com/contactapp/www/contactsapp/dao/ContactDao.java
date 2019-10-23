package com.contactapp.www.contactsapp.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.contactapp.www.contactsapp.entity.Contact;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact_table")
    List<Contact> getAllSentContactList();

    @Insert
    void insertSentContact(Contact users);

    @Query("SELECT * FROM contact_table ORDER BY timeValue DESC")
    LiveData<List<Contact>> getAllContact();
}
