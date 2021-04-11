package com.example.tiendaenlnea;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
String idS;
String  idSS;
    EditText id, nombre, precio;
    Button insert, list, update, delete, regresar;
    DatabaseHandler DB;
String action="new";





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
regresar=findViewById(R.id.btnRegresar);


        //datos recibidos del listactivity
showData();

regresar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(MainActivity.this,ListActivity.class);
        startActivity(intent);
    }
});


        //Agregamos evento del click en los botones
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codProducTXT = id.getText().toString();
                String nombreProducTXT = nombre.getText().toString();
                String precioProducTXT = precio.getText().toString();

                if (id.length() == 0) {
                    id.setError("Debe rellenar el espacio");
                    return;
                }
                if(nombre.length() == 0){
                    nombre.setError("Debe rellenar el espacio");
                    return;
                }
                if (precio.length() == 0){
                    precio.setError("Debe rellenar el espacio");
                    return;
                }



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
                if (id.length() == 0) {
                    id.setError("Debe rellenar el espacio");
                    return;
                }
                if(nombre.length() == 0){
                    nombre.setError("Debe rellenar el espacio");
                    return;
                }
                if (precio.length() == 0){
                    precio.setError("Debe rellenar el espacio");
                    return;
                }

               //Evaluamos si la tabla Data se ha actulizado
                if(checkInsert==true){

                    Toast.makeText(MainActivity.this, "Se ha actualizado su nuevo registro",
                            Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(getIntent());
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

                String codProducTXT = id.getText().toString();

                Boolean checkdeleteData = DB.deleteData(codProducTXT);

                if(checkdeleteData==true){

                    Toast.makeText(MainActivity.this, "El registro se acaba de  eliminar",
                            Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this, " El registro no se eliminó",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void showData() {
        try {
            Bundle bundle=getIntent().getExtras();
            action=bundle.getString("action");
                if(action.equals("edit")){


                    update.setVisibility(View.VISIBLE);
                    insert.setVisibility(View.GONE);
                idS=bundle.getString("id");
                String shop[]=bundle.getStringArray("shop");
                TextView tempVal=(TextView) findViewById(R.id.id);
                 tempVal.setText(idS);

                     tempVal=(TextView) findViewById(R.id.nombre);
                         tempVal.setText(shop[0].toString());

                              tempVal=(TextView) findViewById(R.id.precio);
                                  tempVal.setText(shop[1].toString());


}
        }catch(Exception e){
            Toast.makeText(MainActivity.this,"Error: "+e.getMessage().toString(), Toast.LENGTH_LONG).show();
         }

        try{
            Bundle bundle=getIntent().getExtras();
            action=bundle.getString("action");
            if(action.equals("delete")){
                delete.setVisibility(View.VISIBLE);
                update.setVisibility(View.GONE);
                insert.setVisibility(View.GONE);
                idSS=bundle.getString("id");
                String shopp[]=bundle.getStringArray("shopp");
                TextView tempVal=(TextView) findViewById(R.id.id);
                tempVal.setText(idSS);
            }
        }catch (Exception e){
            Toast.makeText(MainActivity.this, "Error: "+e.getMessage().toString(), Toast.LENGTH_LONG).show();

        }
    }


}