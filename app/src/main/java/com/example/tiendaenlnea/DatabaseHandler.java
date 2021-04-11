package com.example.tiendaenlnea;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    //Creamos la base de datos
    public DatabaseHandler(Context context){
        super(context, "tienda.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creamos la tabla
        db.execSQL("CREATE TABLE tienda(codProduc TEXT PRIMARY KEY, nombreProduc TEXT, precioProduc TEXT)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tienda");
    }

    //Agregamos metodos para la tienda
    public Boolean insertData(String codProduc, String nombreProduc, String precioProduc){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("codProduc", codProduc);
        contentValues.put("nombreProduc", nombreProduc);
        contentValues.put("precioProduc", precioProduc);
        long results = db.insert("tienda", null, contentValues);
        if(results==-1){
            return false;
        }else{
            return true;
        }

    }

    public Cursor getData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tienda", null);
        return cursor;
    }

    //Estructura que actualiza los datos
    public Boolean updateData(String codProduc, String nombreProduc, String precioProduc){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("codProduc", codProduc);
        contentValues.put("nombreProduc", nombreProduc);
        contentValues.put("precioProduc", precioProduc);

        //Verificamos el registro usando el codigo de actualizar
        Cursor cursor = db.rawQuery("SELECT * FROM tienda WHERE codProduc=?", new String[]{codProduc});

        //Evaluamos el registro para verificar si existe
        if(cursor.getCount()>0){
            long result = db.update("tienda", contentValues, "codProduc=?", new String[]{codProduc});
            if(result==-1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }

    }

    //Creamos metodo para eliminar
    public Boolean deleteData(String id){

        SQLiteDatabase db = this.getWritableDatabase();

        //Busqueda del registro para eliminarlo
        Cursor cursor = db.rawQuery("  SELECT *  FROM tienda WHERE codProduc=?", new String[]{id});

        if(cursor.getCount()>0){

            long result = db.delete("tienda", "codProduc=?", new String[]{id});
            if(result==-1){
                return false;
            }else{
                return true;
            }

        }else{
            return false;
        }
    }


}
