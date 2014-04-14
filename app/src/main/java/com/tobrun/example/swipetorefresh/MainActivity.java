package com.tobrun.example.swipetorefresh;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.Vector;

/**
 * SwipeRefreshLayout with an EmptyView
 *
 * @author Tobrun
 */
public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mListViewContainer;
    private SwipeRefreshLayout mEmptyViewContainer;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adapter
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mAdapter.addAll(new Vector(Arrays.asList(getResources().getStringArray(R.array.kings))));

        // ListView
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setEmptyView(findViewById(R.id.swipeRefreshLayout_emptyView));
        listView.setAdapter(mAdapter);

        // SwipeRefreshLayout
        mListViewContainer = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout_listView);
        mEmptyViewContainer = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout_emptyView);

        // Configure SwipeRefreshLayout
        onCreateSwipeToRefresh(mListViewContainer);
        onCreateSwipeToRefresh(mEmptyViewContainer);
    }

    private void onCreateSwipeToRefresh(SwipeRefreshLayout refreshLayout) {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorScheme(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // Get the last king position
                int lastKingIndex = mAdapter.getCount() - 1;

                // If there is a king
                if(lastKingIndex > -1) {
                    // Remove him
                    mAdapter.remove(mAdapter.getItem(lastKingIndex));
                    mListViewContainer.setRefreshing(false);
                }else {
                    // No-one there, add new ones
                    mAdapter.addAll(new Vector(Arrays.asList(getResources().getStringArray(R.array.kings))));
                    mEmptyViewContainer.setRefreshing(false);
                }

                // Notify adapters about the kings
                mAdapter.notifyDataSetChanged();
            }
        }, 1000);
    }
}
