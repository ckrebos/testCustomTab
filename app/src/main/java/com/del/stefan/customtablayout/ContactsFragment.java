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

import com.del.stefan.customtablayout.alphabet.scroll.AlphabetLetter;
import com.del.stefan.customtablayout.alphabet.scroll.FastScrollRecyclerView;
import com.viethoa.RecyclerViewFastScroller;
import com.viethoa.models.AlphabetItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    private List<Contact> mContactList;
    // private List<AlphabetLetter> mAlphabetLetters;
    private List<AlphabetItem> mAlphabetLetters;
    private RecyclerView recyclerView;
    private BaseAdapter mContactAdapter;
    private Comparator<Contact> contactComparator;
    //    private FastScrollRecyclerView fastScroller;
    private RecyclerViewFastScroller fastScroller;

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

//        fastScroller = (FastScrollRecyclerView) view.findViewById(R.id.fast_scroller);
        fastScroller = (RecyclerViewFastScroller) view.findViewById(R.id.fast_scroller);

        Resources resources = getResources();
        String[] names = resources.getStringArray(R.array.names);
        String[] statuses = resources.getStringArray(R.array.statuses);
        String[] alphabet = resources.getStringArray(R.array.alphabet);

        mContactList = new ArrayList<>(20);
        mAlphabetLetters = new ArrayList<>();
        List<String> stringAlphabets = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Contact contact = new Contact(names[i], statuses[i]);
            mContactList.add(contact);
            Log.d(TAG, "onViewCreated: " + i + ". " + contact.getName() + " " + contact.getStatus());
        }

        this.contactComparator = (o1, o2) -> o1.getName().compareTo(o2.getName());
        Collections.sort(mContactList, contactComparator);
        for (int j = 0; j < 26; j++) {
            String letter = alphabet[j];
            if (!stringAlphabets.contains(letter)) {
                stringAlphabets.add(letter);
                mAlphabetLetters.add(new AlphabetItem(j, letter, false));
            }
        }
        mContactAdapter = new BaseAdapter(mContactList);

        recyclerView.setAdapter(mContactAdapter);

        fastScroller.setRecyclerView(recyclerView);
        fastScroller.setUpAlphabet(mAlphabetLetters);

    }
}
