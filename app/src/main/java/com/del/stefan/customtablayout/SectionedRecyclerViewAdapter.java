package com.del.stefan.customtablayout;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefan on 3/30/17.
 */

public abstract class SectionedRecyclerViewAdapter<HVH extends RecyclerView.ViewHolder, IVH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = SectionedRecyclerViewAdapter.class.getSimpleName();

    private static final int TYPE_HEADER = -1;

    private List<Integer> mHeaderPositions = new ArrayList<>();

    public SectionedRecyclerViewAdapter() {
    }

    private void initHeaderPositions() {
        mHeaderPositions.clear();

        if (getItemSize() != 0) {
            mHeaderPositions.add(0);
        } else {
            return;
        }

        for (int i = 1; i < getItemSize(); i++) {
            if (onPlaceHeaderBetweenItems(i - 1)) {
                mHeaderPositions.add(i + mHeaderPositions.size());
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        initHeaderPositions();
    }

    public abstract boolean onPlaceHeaderBetweenItems(int position);

    public abstract IVH onCreateItemViewHolder(ViewGroup parent, int viewType);

    public abstract HVH onCreateHeaderViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindItemViewHolder(IVH holder, int itemPosition);

    public abstract void onBindHeaderViewHolder(HVH headerHolder, int nextItemPosition);

    public abstract int getItemSize();

    public int getViewType(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderOnPosition(position)) {
            return TYPE_HEADER;
        } else {
            return getViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return onCreateHeaderViewHolder(parent, viewType);
        } else {
            return onCreateItemViewHolder(parent, viewType);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderOnPosition(position)) {
            onBindHeaderViewHolder((HVH) holder, getItemPositionForViewHolder(position));
        } else {
            onBindItemViewHolder((IVH) holder, getItemPositionForViewHolder(position));
        }
    }

    @Override
    public int getItemCount() {
        return getItemSize() + mHeaderPositions.size();
    }

    public boolean isHeaderOnPosition(int position) {
        return mHeaderPositions.contains(position);
    }

    public int getItemPositionForViewHolder(int viewHolderPosition) {
        return viewHolderPosition - getCountOfHeadersBeforePosition(viewHolderPosition);
    }

    public int getCountOfHeadersBeforePosition(int position) {
        int count = 0;
        for (int headerPosition : mHeaderPositions) {
            if (headerPosition < position) {
                count++;
            }
        }
        return count;
    }

}
