package com.contactapp.www.contactsapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.contactapp.www.contactsapp.R;
import com.contactapp.www.contactsapp.fragment.ContactListFragment;
import com.contactapp.www.contactsapp.fragment.MessageListFragment;

public class ContactAdapter extends FragmentPagerAdapter {

        private Context mContext;

        public ContactAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }
        // This determines the fragment for each tab
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new ContactListFragment();
            } else {
                return new MessageListFragment();
            }
        }
        // This determines the number of tabs
        @Override
        public int getCount() {
            return 2;
        }

        // This determines the title for each tab
        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            switch (position) {
                case 0:
                    return mContext.getString(R.string.contact_list);
                case 1:
                    return mContext.getString(R.string.message_list);
                default:
                    return null;
            }
        }

    }

