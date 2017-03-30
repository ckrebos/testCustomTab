package com.del.stefan.customtablayout;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    private List<Contact> mContactList;
    private RecyclerView recyclerView;
    private BaseAdapter mContactAdapter;
    private Comparator<Contact> contactComparator;

    private static final String TAG = "Contacts";

    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.contacts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Resources resources = getResources();
        String[] names = resources.getStringArray(R.array.names);
        String[] statuses = resources.getStringArray(R.array.statuses);

        mContactList = new ArrayList<>(20);

        for (int i = 0; i < 20; i++) {
            Contact contact = new Contact(names[i], statuses[i]);
            mContactList.add(contact);
            Log.d(TAG, "onViewCreated: " +
                    i + ". " + contact.getName() + " " + contact.getStatus());
        }

        this.contactComparator = (o1, o2) -> o1.getName().compareTo(o2.getName());
        Collections.sort(mContactList, contactComparator);
        mContactAdapter = new BaseAdapter(mContactList);

        recyclerView.setAdapter(mContactAdapter);

    }
}
