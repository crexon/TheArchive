package com.stucom.thearchive.modelo_navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.stucom.thearchive.BDetailActivity;
import com.stucom.thearchive.R;
import com.stucom.thearchive.modelo_book.Book;
import com.stucom.thearchive.modelo_book.BookList;

import java.util.List;

public class SearchFragment extends Fragment {
    RecyclerView rcBooks;
    ImageButton imSearch;
    EditText etSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        rcBooks = v.findViewById(R.id.rcBooks);
        etSearch = v.findViewById(R.id.etSearch);
        imSearch = v.findViewById(R.id.imSearch);

        rcBooks.setLayoutManager(new GridLayoutManager(getContext(), 3));
        imSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadBooks();
            }
        });
        return v;
    }

    protected void downloadBooks() {
        String search = etSearch.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://www.googleapis.com/books/v1/volumes?q=title:" + search + "&maxResult=10";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        BookList userList = gson.fromJson(response, BookList.class);

                        Log.d("Pol", userList.getItems().toString());
                        BookAdapter adapter = new BookAdapter(userList.getItems());
                        rcBooks.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        queue.add(request);
    }

    class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

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
                        Book book = books.get(position);
                        Intent detalle = new Intent(getContext(), BDetailActivity.class);
                        detalle.putExtra("titulo", book.getBookInfo().getTitle());
                        detalle.putExtra("autor", book.getBookInfo().getAutor());
                        detalle.putExtra("editorial", book.getBookInfo().getPublisher());
                        detalle.putExtra("categoria", book.getBookInfo().getCategory());
                        detalle.putExtra("paginas", book.getBookInfo().getPages());
                        detalle.putExtra("fecha", book.getBookInfo().getPubDate());
                        detalle.putExtra("descripcion", book.getBookInfo().getDescription());
                        if (book.getBookInfo().getImg() != null) {
                            detalle.putExtra("miniatura", book.getBookInfo().getImg().getMiniatura());
                        }
                        startActivity(detalle);
                    }
                });
            }
        }

        private List<Book> books;


        BookAdapter(List<Book> books) {
            super();
            this.books = books;
        }

        @NonNull
        @Override
        public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_book, parent, false);
            return new BookAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
            Book book = books.get(position);
            holder.tvBookName.setText(book.getBookInfo().getTitle());
            if (book.getBookInfo().getImg() != null) {
                Picasso.get().load(book.getBookInfo().getImg().getMiniatura()).into(holder.ivPhotoBook);
            }
        }

        @Override
        public int getItemCount() {
            return books.size();
        }
    }
}

