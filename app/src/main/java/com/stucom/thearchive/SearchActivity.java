package com.stucom.thearchive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.stucom.thearchive.modelo_book.Book;
import com.stucom.thearchive.modelo_book.BookList;

import java.util.List;


public class SearchActivity extends AppCompatActivity {

    RecyclerView rcBooks;
    ImageButton imSearch;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        rcBooks = findViewById(R.id.rcBooks);
        etSearch = findViewById(R.id.etSearch);
        imSearch = findViewById(R.id.imSearch);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcBooks.setLayoutManager(layoutManager);

        imSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadBooks();
            }
        });
    }

    
    protected void downloadBooks() {
        String search = etSearch.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.googleapis.com/books/v1/volumes?q=title:" + search + "&maxResult=10";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        BookList userList = gson.fromJson(response, BookList.class);

                        Log.d("Pol",userList.getItems().toString());
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
            TextView tvBookName ,tvCategory, tvAuthor, tvPublisher, tvPages;


            ViewHolder(View view) {
                super(view);
                ivPhotoBook = view.findViewById(R.id.ivPhotoBook);
                tvBookName = view.findViewById(R.id.tvBookName);
                tvCategory = view.findViewById(R.id.tvCategory);
                tvAuthor = view.findViewById(R.id.tvAuthor);
                tvPublisher = view.findViewById(R.id.tvPublisher);
                tvPages = view.findViewById(R.id.tvPages);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /**
                        int position = getAdapterPosition();
                        Intent intent = new Intent(RankingActivity.this, UserDetailsActivity.class);
                        intent.putExtra("id", user.getId());
                        startActivity(intent);
                         **/
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
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_book, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Book book = books.get(position);

             holder.tvBookName.setText(book.getBookInfo().getTitle());
             holder.tvCategory.setText(book.getBookInfo().getCategory());
             holder.tvAuthor.setText(book.getBookInfo().getAutor());
             holder.tvPublisher.setText(book.getBookInfo().getPublisher());
             holder.tvPages.setText(book.getBookInfo().getPages());
             Picasso.get().load(book.getBookInfo().getImg().getMiniatura()).into(holder.ivPhotoBook);

        }

        @Override
        public int getItemCount() {
            return books.size();
        }

    }
}
