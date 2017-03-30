package com.del.stefan.customtablayout.alphabet.scroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.del.stefan.customtablayout.R;

import java.util.List;

/**
 * Created by stefan on 3/30/17.
 */

public class AlphabetScrollAdapter extends RecyclerView.Adapter<AlphabetScrollAdapter.ViewHolder> {
    private Context mContext;
    private List<AlphabetLetter> mLettersArray;
    private OnLetterClickListener mListener;

    public AlphabetScrollAdapter(Context context, List<AlphabetLetter> letters) {
        this.mContext = context;
        this.mLettersArray = letters;
    }

    public void setOnLetterClickListener(OnLetterClickListener listener) {
        this.mListener = listener;
    }

    public void refreshDataSetChange(List<AlphabetLetter> newLetters) {
        this.mLettersArray = newLetters;
        notifyDataSetChanged();
    }

    @Override
    public AlphabetScrollAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.alphabet_scroll_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AlphabetScrollAdapter.ViewHolder holder, int position) {
        AlphabetLetter letter = mLettersArray.get(position);
        if (letter == null || letter.getLetter() == null)
            return;

        holder.mLetter.setText(letter.getLetter());
        holder.mLetter.setTextColor(letter.isActive()
                ? mContext.getResources().getColor(R.color.colorAccent)
                : mContext.getResources().getColor(R.color.colorPrimaryDark));

        holder.mLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) {
                    return;
                }
                mListener.OnLetterClicked(letter.getPosition(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mLettersArray == null)
            return 0;
        return mLettersArray.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mLetter;

        public ViewHolder(View itemView) {
            super(itemView);
            mLetter = (TextView) itemView.findViewById(R.id.letter);
        }
    }

    public interface OnLetterClickListener {
        void OnLetterClicked(int alphabetPosition, int position);
    }

}
