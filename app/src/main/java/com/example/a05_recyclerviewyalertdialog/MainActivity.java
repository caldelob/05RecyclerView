package com.example.a05_recyclerviewyalertdialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.a05_recyclerviewyalertdialog.adapters.ToDoAdapter;
import com.example.a05_recyclerviewyalertdialog.modelos.ToDo;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;


import com.example.a05_recyclerviewyalertdialog.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private ArrayList<ToDo> todosList;
    private ToDoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        todosList = new ArrayList<>();
       // crearToDos();

        adapter = new ToDoAdapter(todosList, R.layout.todo_view_model, MainActivity.this);
        binding.contentMain.contenedor.setAdapter(adapter);
       // layoutManager = new LinearLayoutManager(MainActivity.this);

        layoutManager = new GridLayoutManager(MainActivity.this, 2 ); //el número es la cantidad que columnas que queremos que tenga el grid
        binding.contentMain.contenedor.setLayoutManager(layoutManager); //asi va a poner a todos mis objetos en modo listado

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createToDo("NUEVA TAREA").show();
            }
        });
    }


    private AlertDialog createToDo(String titulo){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(titulo);

        View contenido = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_todo_alert_dialog, null);
       EditText txtTitulo = contenido.findViewById(R.id.txtTituloAddContenido);
       EditText txtContenido = contenido.findViewById(R.id.txtContenidoAddToDo);

       builder.setView(contenido);

       builder.setNegativeButton("CANCELAR", null);
       builder.setPositiveButton("AGREGAR", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
               ToDo toDo = new ToDo(txtTitulo.getText().toString(), txtContenido.getText().toString());
               todosList.add(toDo);
               adapter.notifyDataSetChanged();
           }
       });
       return builder.create();
    }

    private void crearToDos() {
        for (int i = 0; i < 1000000; i++) {
            todosList.add(new ToDo("Titulo "+i, "Contenido "+i));
        }
    }
}


