package com.del.stefan.customtablayout;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by stefan on 3/30/17.
 */

public class BaseAdapter extends SectionedRecyclerViewAdapter<BaseAdapter.HeaderHolder, BaseAdapter.ContactHolder> {
    private static String TAG = "Base Adapter";
    List<Contact> mContactList;

    BaseAdapter(List<Contact> contactList) {
        super();
        this.mContactList = contactList;
    }

    @Override
    public ContactHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ContactHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item, parent, false));
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return new HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_header, parent, false));
    }

    @Override
    public void onBindItemViewHolder(final ContactHolder holder, int itemPosition) {
        final Contact contact = mContactList.get(itemPosition);
        Log.d(TAG, "onBindItemViewHolder() called with: holder = [" + contact.getName() + "] [" + contact.getStatus() + "]");

        holder.mName.setText(contact.getName());
        holder.mStatus.setText(contact.getStatus());
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder headerHolder, int nextItemPosition) {
        final Contact nextContact = mContactList.get(nextItemPosition);
        Log.d(TAG, "onBindHeaderViewHolder() called with: holder = [" + nextContact.getName().substring(0, 1) + "]");
        headerHolder.mHeaderText.setText(nextContact.getName().substring(0, 1));
    }

    @Override
    public int getItemSize() {
        return mContactList.size();
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {
        TextView mHeaderText;

        HeaderHolder(View itemView) {
            super(itemView);
            this.mHeaderText = (TextView) itemView.findViewById(R.id.headerText);
        }
    }

    static class ContactHolder extends RecyclerView.ViewHolder {
        TextView mName;
        TextView mStatus;

        ContactHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mStatus = (TextView) itemView.findViewById(R.id.status);
        }
    }

    @Override
    public boolean onPlaceHeaderBetweenItems(int position) {
        final Contact contact = mContactList.get(position);
        final Contact nextContact = mContactList.get(position + 1);

        return !contact.getName().substring(0, 1).equals(nextContact.getName().substring(0, 1));

    }
}
