package com.stucom.thearchive.modelo_navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.stucom.thearchive.R;
import com.stucom.thearchive.modelo_feed.Feed;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {

    RecyclerView rcFeeds;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feed, container, false);

        rcFeeds = v.findViewById(R.id.rcFeed);
        rcFeeds.setLayoutManager(new LinearLayoutManager(getContext()));
        initData();

        return v;

    }

    private void initData(){
        Feed feed = new Feed("crexon", 1, "2020-05-12");
        Feed feed2 = new Feed("oripik", 3, "2020-03-03");
        Feed feed3 = new Feed("test", 2, "2020-04-06");

        List<Feed> feeds = new ArrayList<>();
        feeds.add(feed);
        feeds.add(feed2);
        feeds.add(feed3);

        FeedAdapter adapter = new FeedAdapter(feeds);
        rcFeeds.setAdapter(adapter);
    }


    class FeedAdapter extends RecyclerView.Adapter<FeedFragment.FeedAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivPhoto;
            TextView tvFeedName, tvFeedDate;


            ViewHolder(View view) {
                super(view);
                ivPhoto = view.findViewById(R.id.photo);
                tvFeedName = view.findViewById(R.id.tvFeedName);
                tvFeedDate = view.findViewById(R.id.tvFeedDate);
            }
        }

        private List<Feed> feeds;


        FeedAdapter(List<Feed> feeds) {
            super();
            this.feeds = feeds;
        }

        @NonNull
        @Override
        public FeedFragment.FeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_feed, parent, false);
            return new FeedAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FeedFragment.FeedAdapter.ViewHolder holder, int position) {
            Feed feed = feeds.get(position);
            holder.tvFeedName.setText(feed.getUsername());
            holder.tvFeedDate.setText(feed.getDate());
        }

        @Override
        public int getItemCount() {
            return feeds.size();
        }
    }

}
