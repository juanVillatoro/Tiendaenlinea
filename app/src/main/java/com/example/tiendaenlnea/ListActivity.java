package com.example.tiendaenlnea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
DatabaseHandler DB;
public Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);



        //Aqui mostramos los datos en el ListActivity
        showData();

    }





//MOSTRAMOS EL MENU
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) menuInfo;
        c.moveToPosition(info.position);
        menu.setHeaderTitle(c.getString(1)+" "+c.getString(2));

        }

   @Override
   public boolean onContextItemSelected(@NonNull MenuItem item){
       switch(item.getItemId()){
           case R.id.edit:
               try{
                String shop[]={c.getString(1), c.getString(2)};
                Bundle bundle=new Bundle();
                bundle.putString("action", "edit");
                bundle.putString("id",c.getString(0));
                bundle.putStringArray("shop", shop);

                Intent forMain=new Intent(ListActivity.this, MainActivity.class);
                forMain.putExtras(bundle);
                startActivity(forMain);

               }catch(Exception e){
                   Toast.makeText(ListActivity.this, "Error: "+e.getMessage().toString(), Toast.LENGTH_LONG).show();
               }
               return true;

           default:
               return super.onContextItemSelected(item);



           case R.id.delete:
               try{
                   String shopp[]={c.getString(1),c.getString(2)};
                   Bundle bundle=new Bundle();
                   bundle.putString("action","delete");
                   bundle.putString("id",c.getString(0));
                   bundle.putStringArray("shopp",shopp);

                   Intent forMain=new Intent(ListActivity.this, MainActivity.class);
                   forMain.putExtras(bundle);
                   startActivity(forMain);
               }catch (Exception e){
                   Toast.makeText(ListActivity.this,"Error: "+e.getMessage().toString(),Toast.LENGTH_LONG).show();
               }
               return true;


       }
   }

    private void showData(){
            DB=new DatabaseHandler(ListActivity.this);
            c=DB.getData();

            //evaluamos si existen registros en la tabla

        if(c.moveToFirst()){
            ListView listData=(ListView)findViewById(R.id.listData);

            //Creamos un array list

            final ArrayList<String> allData=new ArrayList<String>();

            final ArrayAdapter<String > aData=new ArrayAdapter<String>(ListActivity.this, android.R.layout.simple_expandable_list_item_1,allData);
            listData.setAdapter(aData);
            do{
                allData.add(c.getString(0)+" "+c.getString(1)+" "+c.getString(2));
            }while(c.moveToNext());
aData.notifyDataSetChanged();
            //aqui hacemos el llamado al listview para que os muestre el menu....
registerForContextMenu(listData);
        }else{
            Toast.makeText(ListActivity.this,"No hay registros que mostrar", Toast.LENGTH_LONG).show();
            return;
        }


    }

    public void addData(View v){
        Intent add=new Intent(ListActivity.this,MainActivity.class);

        startActivity(add);

    }
}