package com.stucom.thearchive;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;
import com.stucom.thearchive.utils.AppUtils;

import java.util.HashMap;
import java.util.Map;

public class BDetailActivity extends AppCompatActivity implements View.OnClickListener {


    AppUtils appUtils;
    // Recommends 0 = no,  1 = si, 2 = no sabe
    int recommends = 2, progreso;
    Intent detalle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bdetail);

        detalle = getIntent();
        findViewById(R.id.btnBookRead).setOnClickListener(this);
        findViewById(R.id.btnBookReading).setOnClickListener(this);
        findViewById(R.id.btnBookUnread).setOnClickListener(this);
        if (detalle.getStringExtra("tipo") != null) { findViewById(R.id.btnAddBook).setVisibility(View.GONE); }
        appUtils = new AppUtils(this);
        loadBookData();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBookRead: dialogQuestion(); break;
            case R.id.btnBookReading: getSeekBarDialog(); break;
            case R.id.btnBookUnread: addBookToEstanteria(3, recommends, 0); break;
        }
    }

    private void loadBookData() {
        String titulo = detalle.getStringExtra("titulo");
        String autor = detalle.getStringExtra("autor");
        String categoria = detalle.getStringExtra("categoria");
        String editorial = detalle.getStringExtra("editorial");
        String paginas = detalle.getStringExtra("paginas");
        String descripcion = detalle.getStringExtra("descripcion");
        String fecha = detalle.getStringExtra("fecha");
        String miniatura = detalle.getStringExtra("miniatura");
        //String isbn = detalle.getStringExtra("isbn");

        CollapsingToolbarLayout layout = findViewById(R.id.collapse);
        layout.setTitleEnabled(true);
        layout.setTitle(titulo);

        TextView tvTitulo = findViewById(R.id.tvTitulo);
        TextView tvAutor = findViewById(R.id.tvAutor);
        TextView tvCategoria = findViewById(R.id.tvCategoria);
        TextView tvEditorial = findViewById(R.id.tvEditorial);
        TextView tvPaginas = findViewById(R.id.tvPaginas);
        TextView tvDescripcion = findViewById(R.id.description);
        TextView tvFecha = findViewById(R.id.pubDate_details);
        ImageView img = findViewById(R.id.imMiniatura);
        //TextView bookIsbn = findViewById(R.id.isbn_detalle);

        tvTitulo.setText(titulo);
        tvAutor.setText(autor);
        tvCategoria.setText(categoria);
        tvEditorial.setText(editorial);
        tvPaginas.setText("Páginas: " + paginas);
        tvDescripcion.setText(descripcion);
        tvFecha.setText(fecha);
        Picasso.get().load(miniatura).into(img);
        //bookIsbn.setText("ISBN 13: " + isbn);

    }

    private void dialogQuestion() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: addBookToEstanteria(1, 1, 100); break;
                    case DialogInterface.BUTTON_NEGATIVE: addBookToEstanteria(1, 0, 100); break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(BDetailActivity.this);
        builder.setMessage("¿Recomiendas el libro?").setPositiveButton("Sí", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }


    private void getSeekBarDialog() {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_seek, null);
        SeekBar seekBar = view.findViewById(R.id.size_seekbar);
        final TextView seekProgress = view.findViewById(R.id.set_size_help_text);

        seekBar.setMax(100);
        seekBar.setKeyProgressIncrement(1);

        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle(BDetailActivity.this.getString(R.string.book_reading_progress));
        popDialog.setPositiveButton(BDetailActivity.this.getString(R.string.accept), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                addBookToEstanteria(2, recommends, progreso);
            }
        });
        popDialog.setView(view);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekProgress.setText(progress + " %");
                progreso = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        popDialog.show();
    }


    protected void addBookToEstanteria(final int state, final int recommends, final int progress) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://169.254.25.54:8000/archive/estanteria/";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        appUtils.showAlert(BDetailActivity.this);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        StyleableToast.makeText(BDetailActivity.this, BDetailActivity.this.getString(R.string.book_already_added), Toast.LENGTH_LONG, R.style.toast).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("identifier", detalle.getStringExtra("id"));
                params.put("title", detalle.getStringExtra("titulo"));
                params.put("authors", detalle.getStringExtra("autor"));
                params.put("publisher", detalle.getStringExtra("editorial"));
                params.put("description", detalle.getStringExtra("descripcion"));
                params.put("publishedDate", detalle.getStringExtra("fecha"));
                params.put("pageCount", detalle.getStringExtra("paginas"));
                params.put("categories", detalle.getStringExtra("categoria"));
                params.put("thumbnail", detalle.getStringExtra("miniatura"));
                params.put("username", appUtils.getUsername());
                params.put("state", String.valueOf(state));
                params.put("progress", String.valueOf(progress));
                params.put("recommendation", String.valueOf(recommends));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + appUtils.getToken());
                return params;
            }
        };
        queue.add(request);
    }
}
