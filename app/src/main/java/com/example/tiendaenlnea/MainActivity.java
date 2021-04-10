package com.example.tiendaenlnea;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ResourceCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText id, nombre, precio;
    Button insert, list, update, delete;
    DatabaseHandler DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.id);
        nombre = findViewById(R.id.nombre);
        precio = findViewById(R.id.precio);
        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        list = findViewById(R.id.btnViewData);
        delete = findViewById(R.id.btndelete);
        DB = new DatabaseHandler(this);


        //Agregamos evento del click en los botones
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codProducTXT = id.getText().toString();
                String nombreProducTXT = nombre.getText().toString();
                String precioProducTXT = precio.getText().toString();

                Boolean checkInsert = DB.insertData(codProducTXT,nombreProducTXT,precioProducTXT);
                if(checkInsert==true){

                    Toast.makeText(MainActivity.this, "Se ha insertado su nuevo registro",
                            Toast.LENGTH_LONG).show();

                }else{

                    Toast.makeText(MainActivity.this, "No se ha podido insertar el nuevo registro",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        list.setOnClickListener(new View.OnClickListener(){
            @Override
           public void onClick(View v){
               Cursor result = DB.getData();

               //Evaluamos si ya existe el registro en la tabla
               if(result.getCount()==0){

                   Toast.makeText(MainActivity.this, "Aún no existen registros ingresados",
                           Toast.LENGTH_SHORT).show();
                   return;

               }

               StringBuffer buffer = new StringBuffer();

               while (result.moveToNext()){

                   buffer.append("Código: " + result.getString(0) + "\n");
                   buffer.append("Nombre del producto: " + result.getString(1) + "\n");
                   buffer.append("Precio del producto: " + result.getString(2) + "\n\n");

               }

               //Mostramos los registros
               AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
               builder.setCancelable(true);
               builder.setTitle("Listado de automóviles registrados");
               builder.setMessage(buffer.toString());
               builder.show();

           }

        });

        //Estructura para botón de actualizar
        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String codProducTXT = id.getText().toString();
                String nombreProducTXT = nombre.getText().toString();
                String precioProducTXT = precio.getText().toString();

                Boolean checkInsert = DB.updateData(codProducTXT,nombreProducTXT,precioProducTXT);
               //Evaluamos si la tabla Data se ha actulizado
                if(checkInsert==true){

                    Toast.makeText(MainActivity.this, "Se ha actualizado su nuevo registro",
                            Toast.LENGTH_LONG).show();

                }else{

                    Toast.makeText(MainActivity.this, "No se ha podido actualizar el nuevo registro",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //Estructura para botón eliminar
        delete.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {

                String idTXT = id.getText().toString();

                Boolean checkdeleteData = DB.deleteData(idTXT);

                if(checkdeleteData==true){

                    Toast.makeText(MainActivity.this, "El registro se eliminó",
                            Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this, "El registro no se pudo eliminar",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}