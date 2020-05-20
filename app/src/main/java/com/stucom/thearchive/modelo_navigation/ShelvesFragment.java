package com.stucom.thearchive.modelo_navigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.squareup.picasso.Picasso;
import com.stucom.thearchive.BDetailActivity;
import com.stucom.thearchive.R;
import com.stucom.thearchive.modelo_shelve.Shelve;
import com.stucom.thearchive.utils.AppUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShelvesFragment extends Fragment {

    AppUtils appUtils;
    ProgressDialog progressDialog;
    Spinner spType;
    RecyclerView rcOwnBooks;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_shelves, container, false);

        appUtils = new AppUtils(getContext());
        spType = v.findViewById(R.id.spType);
        rcOwnBooks = v.findViewById(R.id.rcOwnBooks);
        rcOwnBooks.setLayoutManager(new GridLayoutManager(getContext(), 3));


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(adapter);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position){
                    case 0: downloadBooks(1); break;
                    case 1: downloadBooks(2); break;
                    case 2: downloadBooks(3); break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }

    protected void downloadBooks(final int type) {
        prepareProgressBar(true);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://169.254.25.54:8000/archive/estanteria/" + type +"/" + appUtils.getUsername();
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        prepareProgressBar(false);
                        Gson gson = new Gson();
                        List<Shelve> libros = gson.fromJson(response, new TypeToken<List<Shelve>>(){}.getType());
                        ShelveAdapter adapter = new ShelveAdapter(libros);
                        rcOwnBooks.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        prepareProgressBar(false);
                        StyleableToast.makeText(getActivity(), getActivity().getString(R.string.content_download), Toast.LENGTH_LONG, R.style.toast).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + appUtils.getToken());
                return params;
            }
        };
        queue.add(request);
    }


    class ShelveAdapter extends RecyclerView.Adapter<ShelvesFragment.ShelveAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivPhotoBook;
            TextView tvBookName;


            ViewHolder(View view) {
                super(view);
                ivPhotoBook = view.findViewById(R.id.ivPhotoBook);
                tvBookName = view.findViewById(R.id.tvBookName);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        Shelve book = books.get(position);
                        Intent detalle = new Intent(getContext(), BDetailActivity.class);
                        detalle.putExtra("id", book.getIdBook());
                        detalle.putExtra("titulo", book.getTitle());
                        detalle.putExtra("autor", book.getAutor());
                        detalle.putExtra("editorial", book.getPublisher());
                        detalle.putExtra("categoria", book.getCategory());
                        detalle.putExtra("paginas", book.getPages());
                        detalle.putExtra("fecha", book.getPubDate());
                        detalle.putExtra("descripcion", book.getDescription());
                        detalle.putExtra("tipo", "yes");

                        if (book.getMiniatura() != null) {
                            detalle.putExtra("miniatura", book.getMiniatura());
                        }
                        startActivity(detalle);
                    }
                });
            }
        }

        private List<Shelve> books;


        ShelveAdapter(List<Shelve> books) {
            super();
            this.books = books;
        }

        @NonNull
        @Override
        public ShelvesFragment.ShelveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_book, parent, false);
            return new ShelveAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShelvesFragment.ShelveAdapter.ViewHolder holder, int position) {
            Shelve book = books.get(position);
            holder.tvBookName.setText(book.getTitle());
            if (book.getMiniatura() != null) {
                Picasso.get().load(book.getMiniatura()).into(holder.ivPhotoBook);
            }
        }

        @Override
        public int getItemCount() {
            return books.size();
        }
    }


    private void prepareProgressBar(boolean mode) {
        if (mode) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        } else {
            progressDialog.dismiss();
        }
    }
}