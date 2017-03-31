package com.del.stefan.customtablayout.alphabet.scroll;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.del.stefan.customtablayout.R;

import java.util.List;

/**
 * Created by stefan on 3/31/17.
 */

public class FastScrollRecyclerView extends LinearLayout implements
        AlphabetScrollAdapter.OnLetterClickListener,
        View.OnTouchListener {

    private static final int TRACK_SNAP_RANGE = 5;


    private RecyclerView recyclerView, alphabetRecyclerView;
    private List<AlphabetLetter> mLetterList;
    private AlphabetScrollAdapter alphabetAdapter;
    private boolean isInitialized = false;
    private int height;
    private View handle;
    private ObjectAnimator currentAnimator = null;

    public FastScrollRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public FastScrollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FastScrollRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        if (isInitialized) {
            return;
        }
        isInitialized = true;
        setOrientation(HORIZONTAL);
        setClipChildren(false);
        View.inflate(context, R.layout.fast_scroller, this);
       // setViews(R.layout.alp)

        alphabetRecyclerView = (RecyclerView) findViewById(R.id.alphabet);
        alphabetRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//TODO context or getContext
        alphabetRecyclerView.setOnTouchListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                final float y = event.getY();
                setRecyclerViewPosition(y);
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void setRecyclerViewPosition(float y) {
        if (recyclerView != null) {
            final int itemCount = recyclerView.getAdapter().getItemCount();
            final float proportion = y / (float) height;
            final int targetPosition = getValueInRange(0, itemCount - 1, (int) (proportion * (float) itemCount));
            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(targetPosition, 0);

//            final String bubbleText = ((BubbleTextGetter) recyclerView.getAdapter()).getTextToShowInBubble(targetPos);
//            setAlphabetWordSelected(bubbleText);
        }
    }

    private int getValueInRange(int min, int max, int value) {
        int minimum = Math.max(min, value);
        return Math.min(minimum, max);
    }


    @Override
    public void OnLetterClicked(int alphabetPosition, int position) {
        performSelectedAlphabetWord(position);
        scrollRecyclerToAlphabetPosition(alphabetPosition);
    }

    private void scrollRecyclerToAlphabetPosition(int alphabetPosition) {
        if (recyclerView == null || recyclerView.getAdapter() == null) {
            return;
        }

        int count = recyclerView.getAdapter().getItemCount();
        if (alphabetPosition < 0 || alphabetPosition > count) {
            return;
        }

        ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(alphabetPosition, 0);
    }

    private void performSelectedAlphabetWord(int position) {
        if (position < 0 || position >= mLetterList.size()) {
            return;
        }

        for (AlphabetLetter alphabetLetter : mLetterList) {
            alphabetLetter.setActive(false);
        }

        mLetterList.get(position).setActive(true);
        alphabetAdapter.updateScrollLetters(mLetterList);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE: {

                Rect rect = new Rect();
                int childCount = alphabetRecyclerView.getChildCount();
                int[] listViewCoords = new int[2];
                alphabetRecyclerView.getLocationOnScreen(listViewCoords);
                int x = (int) event.getRawX() - listViewCoords[0];
                int y = (int) event.getRawY() - listViewCoords[1];

                View child;
                for (int i = 0; i < childCount; i++) {
                    child = alphabetRecyclerView.getChildAt(i);
                    child.getHitRect(rect);

                    // This is your pressed view
                    if (rect.contains(x, y)) {
                        LinearLayoutManager layoutManager = ((LinearLayoutManager) alphabetRecyclerView.getLayoutManager());
                        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                        int position = i + firstVisiblePosition;
                        performSelectedAlphabetWord(position);
                        alphabetTouchEventOnItem(position);
                        break;
                    }
                }
                v.onTouchEvent(event);
            }
        }
        return true;
    }

    private void alphabetTouchEventOnItem(int position) {
        if (mLetterList == null || position < 0 || position >= mLetterList.size()) {
            return;
        }

        scrollRecyclerToAlphabetPosition(mLetterList.get(position).getPosition());
    }


    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (/*bubble == null || handle.isSelected() ||*/recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE)
                    return;
                final int verticalScrollOffset = recyclerView.computeVerticalScrollOffset();
                final int verticalScrollRange = recyclerView.computeVerticalScrollRange();
                float proportion = (float) verticalScrollOffset / ((float) verticalScrollRange - height);
                // setBubbleAndHandlePosition(height * proportion);
                //setRecyclerViewPositionWithoutScrolling(height * proportion);
                //bubble.setVisibility(VISIBLE);
                // bubble.setAlpha(1f);
            }
        };
        recyclerView.addOnScrollListener(onScrollListener);
    }

    public void setUpAlphabet(List<AlphabetLetter> alphabetItems) {
        if (alphabetItems == null || alphabetItems.size() <= 0)
            return;
        mLetterList = alphabetItems;
        alphabetAdapter = new AlphabetScrollAdapter(getContext(), mLetterList);
        alphabetAdapter.setOnLetterClickListener(new OnAlphabetItemClickListener());
        alphabetRecyclerView.setAdapter(alphabetAdapter);
    }

    private class OnAlphabetItemClickListener implements AlphabetScrollAdapter.OnLetterClickListener {

        @Override
        public void OnLetterClicked(int alphabetPosition, int position) {
            performSelectedAlphabetWord(position);
            scrollRecyclerToAlphabetPosition(alphabetPosition);
        }
    }

//    private void setRecyclerViewPositionWithoutScrolling(float y) {
//        if (recyclerView != null) {
//            final int itemCount = recyclerView.getAdapter().getItemCount();
//            float proportion;
//            if (handle.getY() == 0)
//                proportion = 0f;
//            else if (handle.getY() + handle.getHeight() >= height - TRACK_SNAP_RANGE)
//                proportion = 1f;
//            else
//                proportion = y / (float) height;
//            final int targetPos = getValueInRange(0, itemCount - 1, (int) (proportion * (float) itemCount));
//            final String bubbleText = ((BubbleTextGetter) recyclerView.getAdapter()).getTextToShowInBubble(targetPos);
//            if (bubble != null)
//                bubble.setText(bubbleText);
//            setAlphabetWorkSelected(bubbleText);
//        }
//    }
}
