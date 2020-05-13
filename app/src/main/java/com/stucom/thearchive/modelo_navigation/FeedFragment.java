package com.stucom.thearchive.modelo_navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.muddzdev.styleabletoast.StyleableToast;
import com.stucom.thearchive.R;
import com.stucom.thearchive.modelo_feed.Feed;
import com.stucom.thearchive.utils.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedFragment extends Fragment {

    RecyclerView rcFeeds;
    AppUtils appUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feed, container, false);

        // Type 1 = leido, 2 = leyendo, 3 = quiere leer (no), 4 = perfila actualizado
        rcFeeds = v.findViewById(R.id.rcFeed);
        appUtils = new AppUtils(getActivity());
        rcFeeds.setLayoutManager(new LinearLayoutManager(getContext()));
        downloadData();

        return v;

    }

    private void downloadData(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://169.254.25.54:8000/archive/feed/";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        List<Feed> feeds = gson.fromJson(response, new TypeToken<List<Feed>>(){}.getType());

                        FeedAdapter adapter = new FeedAdapter(feeds);
                        rcFeeds.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        StyleableToast.makeText(getActivity(), getActivity().getString(R.string.content_download), Toast.LENGTH_LONG, R.style.toast).show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + appUtils.getToken());
                return params;
            }
        };
        queue.add(request);
    }


    class FeedAdapter extends RecyclerView.Adapter<FeedFragment.FeedAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivPhoto;
            TextView tvFeedName, tvFeedDate, tvFeedDesc;


            ViewHolder(View view) {
                super(view);
                ivPhoto = view.findViewById(R.id.photo);
                tvFeedName = view.findViewById(R.id.tvFeedName);
                tvFeedDate = view.findViewById(R.id.tvFeedDate);
                tvFeedDesc = view.findViewById(R.id.tvFeedDesc);
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

            switch (feed.getType()){
                case 1: holder.tvFeedDesc.setText(feed.getUsername() + " ha agregado un libro a su estanteria como leído."); break;
                case 2: holder.tvFeedDesc.setText(feed.getUsername() + " ha agregado un libro a su estanteria  que esta leyendo actualmente."); break;
                case 3: holder.tvFeedDesc.setText(feed.getUsername() + " ha agregado un libro que le gustaría leer."); break;
                case 4: holder.tvFeedDesc.setText(feed.getUsername() + " ha actualizado su perfil."); break;
            }
            holder.tvFeedDate.setText(feed.getDate());
        }

        @Override
        public int getItemCount() {
            return feeds.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        downloadData();
    }
}
