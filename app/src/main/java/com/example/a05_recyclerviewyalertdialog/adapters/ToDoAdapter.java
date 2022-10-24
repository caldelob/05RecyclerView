package com.example.a05_recyclerviewyalertdialog.adapters;


//esta clase se encarga de generar todos los cuadraditos pequeños y completar solo las filas que se necesiten mostrar:

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a05_recyclerviewyalertdialog.R;
import com.example.a05_recyclerviewyalertdialog.modelos.ToDo;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoVH> {


    //EL ADAPTER NO CREA ELEMENTOS, SOLO LOS GESTIONA

    private List<ToDo> objects;
    private int resourse; //esta será la fila
    private Context context;

    public ToDoAdapter(List<ToDo> objects, int resourse, Context context) {
        this.objects = objects;
        this.resourse = resourse;
        this.context = context;
    }


    //este método será llamado para crear una nueva FILA. Retorna un objeto ViewHolder
    @NonNull
    @Override
    public ToDoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View toDoView = LayoutInflater.from(context).inflate(resourse, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        toDoView.setLayoutParams(lp);

        return new ToDoVH(toDoView);
    }


    //A partir de un ViewHolder --> asigna valores a los elementos
    //Este método recibe un holder (que es el elemento a configurar)
    //Y recibe una posición (que es el elemento de la lista a mostrar)
    @Override
    public void onBindViewHolder(@NonNull ToDoVH holder, int position) {

        ToDo toDo = objects.get(position);
        holder.lbllTitulo.setText(toDo.getTitulo());
        holder.lblbContenido.setText(toDo.getContenido());
        holder.lblFecha.setText(toDo.getFecha().toString());
        if(toDo.isCompletado())
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_on_background);
        else
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_off_background);

        holder.btnCompletado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmaCambioEstado("Estas seguro de que quieres cambiar el estado?", toDo).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    private AlertDialog confirmaCambioEstado(String mensaje, ToDo toDo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(mensaje);
        builder.setCancelable(false); //con esto yo decido si al tocar fuera del mensaje, el mensaje se cierra o no

        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                toDo.setCompletado(!toDo.isCompletado());
                notifyDataSetChanged(); //esto notifica que el elemento cambió
            }
        });
        return builder.create();
    }

    //objeto que se intancia cada vez que tenga q mostrar un To-Do en el recycler
    // pero sólo se intancvian los q quepan en la pantalla + 1/2

    public class ToDoVH extends RecyclerView.ViewHolder{

        TextView lbllTitulo, lblbContenido, lblFecha;
        ImageButton btnCompletado;


        public ToDoVH(View itemView){
            super(itemView);
            lbllTitulo= itemView.findViewById(R.id.lblTituloToDoModelView);
            lblbContenido =  itemView.findViewById(R.id.lblContenidoToDoModelView);
            lblFecha =  itemView.findViewById(R.id.lblFechaToDoModelView);
            btnCompletado = itemView.findViewById(R.id.btnCompletadoToDoModelView);

        }
    }
}
