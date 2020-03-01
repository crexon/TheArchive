package com.stucom.thearchive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

public class BDetailActivity extends AppCompatActivity {

    
    Intent detalle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bdetail);

        loadBookData();
    }
    
    private void loadBookData(){
        detalle = getIntent();

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
    
}
