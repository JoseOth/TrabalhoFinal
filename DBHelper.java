package com.example.trabalhofinal;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "usuario.db";
    private static final String TABLE_NAME = "usuario";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "nome";
    private static final String COL_EMAIL = "email";
    private static final String COL_USER = "usuario";
    private static final String COL_PASS = "senha";
    private static final String COL_NIVEL = "nivel";

    private static final String TABLE_NAME2 = "Artigo";
    private static final String COL_ID2 = "id";
    private static final String COL_TITULO = "titulo";
    private static final String COL_ARTIGO = "artigo";
    private static final String COL_USERID = "idusuario";
    private static final String COL_IDPERMISSAO = "idpermissao";

    private static final String TABLE_NAME3 = "Permissao";
    private static final String COL_ID3 = "id";
    private static final String COL_IDARTIGO = "idartigo";
    private static final String COL_IDUSUARIO1 = "idusuario1";
    private static final String COL_IDUSUARIO2 = "idusuario2";
    private static final String COL_IDUSUARIO3 = "idusuario3";
    private static final String COL_IDUSUARIO4 = "idusuario4";
    private static final String COL_IDUSUARIO5 = "idusuario5";

    private static final String TABLE_CREATE="create table "+TABLE_NAME+
            "("+COL_ID+" integer primary key autoincrement, "+
            COL_NAME+" text not null, "+COL_EMAIL+" text not null, " +
            COL_USER+" text not null, "+COL_PASS+" text not null, " +
            COL_NIVEL+" integer);";

    private static final String TABLE_CREATE2 = "create table " + TABLE_NAME2 +
            "("+COL_ID2+" integer primary key autoincrement, "+
            COL_TITULO + " text not null , " +
            COL_ARTIGO + " text not null , " +
            COL_USERID + " integer references " + TABLE_NAME + "(" + COL_ID + ") , " +
            COL_IDPERMISSAO + " integer references " + TABLE_NAME3 + "(" + COL_ID3 + "));";

    private static final String TABLE_CREATE3="create table "+TABLE_NAME3+
            "("+COL_ID3+" integer primary key autoincrement, "+
            COL_IDARTIGO+" integer references " + TABLE_NAME2 + "(" + COL_ID2 + ") , " +
            COL_IDUSUARIO1+" integer references " + TABLE_NAME + "(" + COL_ID + ") , " +
            COL_IDUSUARIO2+" integer references " + TABLE_NAME + "(" + COL_ID + ") , " +
            COL_IDUSUARIO3+" integer references " + TABLE_NAME + "(" + COL_ID + ") , " +
            COL_IDUSUARIO4+" integer references " + TABLE_NAME + "(" + COL_ID + ") , " +
            COL_IDUSUARIO5+" integer references " + TABLE_NAME + "(" + COL_ID + "));";

    SQLiteDatabase db;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE2);
        db.execSQL(TABLE_CREATE3);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        String query2 = "DROP TABLE IF EXISTS "+TABLE_NAME2;
        String query3 = "DROP TABLE IF EXISTS "+TABLE_NAME3;
        db.execSQL(query);
        db.execSQL(query2);
        db.execSQL(query3);
        this.onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    public void insereArtigo(Artigo a) {
        db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_ID2, a.getId());
            values.put(COL_TITULO, a.getTitulo());
            values.put(COL_ARTIGO, a.getArtigo());
            values.put(COL_USERID, a.getIdUsuario());
            values.put(COL_IDPERMISSAO, a.getIdPermissao());
            db.insert(TABLE_NAME2, null, values);
            db.setTransactionSuccessful();
            Log.d(TAG, "Sucesso ao criar Artigo");
        } catch (Exception e) {
            Log.d(TAG, "Erro ao inserir Artigo");
        } finally {
            db.endTransaction();
        }
    }

    public void insereUsuario(Usuario u){
        db=this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_ID, u.getId());
            values.put(COL_NAME, u.getNome());
            values.put(COL_EMAIL, u.getEmail());
            values.put(COL_USER, u.getUsuario());
            values.put(COL_PASS, u.getSenha());
            values.put(COL_NIVEL, u.getNivel());
            db.insertOrThrow(TABLE_NAME,null,values);
            db.setTransactionSuccessful();
            Log.d(TAG, "Sucesso ao criar Usuario");
        }catch (Exception e){
            Log.d(TAG, "Erro ao inserir usuario");
        } finally{
            db.endTransaction();
        }
    }

    public void inserePermissao(Permissao p){
        db=this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_ID3, p.getId());
            values.put(COL_IDARTIGO, p.getIdArtigo());
            values.put(COL_IDUSUARIO1, p.getIdUsuario1());
            values.put(COL_IDUSUARIO2, p.getIdUsuario2());
            values.put(COL_IDUSUARIO3, p.getIdUsuario3());
            values.put(COL_IDUSUARIO4, p.getIdUsuario4());
            values.put(COL_IDUSUARIO5, p.getIdUsuario5());
            db.insertOrThrow(TABLE_NAME3,null,values);
            db.setTransactionSuccessful();
            Log.d(TAG, "Sucesso ao criar Permissao");
        }catch (Exception e){
            Log.d(TAG, "Erro ao inserir permissao" + e.getMessage());
        } finally{
            db.endTransaction();
        }
    }

    public String buscarSenha(String usuario){
        db = this.getReadableDatabase();
        String query = String.format("SELECT %s FROM %s WHERE %s = ?",
                COL_PASS, TABLE_NAME, COL_USER);
        String senha="não encontrado";
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(usuario)});
            try {
                if (cursor.moveToFirst()) {
                    senha = cursor.getString(0);
                    db.setTransactionSuccessful();
                }
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        }catch (Exception e) {
            Log.d(TAG, "Usuário não encontrado");
        } finally {
            db.endTransaction();
        }
        return senha;
    }

    public String buscarArtigoPorUsuario(Usuario u) {
        db = this.getReadableDatabase();
        String query = String.format("Select %s FROM %s Where %s = ?",
                COL_TITULO, TABLE_NAME2, COL_USERID);
        db.beginTransaction();
        String titulo = "Não encontrado";
        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(u.getId())});
            try {
                if (cursor.moveToFirst()) {
                    titulo = cursor.getString(0);
                    db.setTransactionSuccessful();
                }
            } finally {
                if (cursor != null && !cursor.isClosed())
                    cursor.close();
            }
        } finally {
            db.endTransaction();
        }
        return titulo;
    }

    public ArrayList<Artigo> buscarTodosArtigos() {
        String[] coluns = {COL_ID2, COL_TITULO, COL_ARTIGO, COL_USERID, COL_IDPERMISSAO};
        Cursor cursor = getReadableDatabase().query(TABLE_NAME2,
                coluns,null, null, null,
                null, "upper(titulo)", null);
        ArrayList<Artigo> listaArtigo = new ArrayList<Artigo>();
        while (cursor.moveToNext()) {
            Artigo a = new Artigo();
            a.setId(cursor.getInt(0));
            a.setTitulo(cursor.getString(1));
            a.setArtigo(cursor.getString(2));
            a.setIdUsuario(cursor.getInt(3));
            a.setIdPermissao(cursor.getInt(4));
            listaArtigo.add(a);
        }
        return listaArtigo;
    }

    public ArrayList<Usuario> buscarUsuarios(){
        String[] coluns={ COL_ID, COL_NAME, COL_EMAIL, COL_USER, COL_PASS, COL_NIVEL};
        Cursor cursor = getReadableDatabase().query(TABLE_NAME,
                coluns,null,null,null,
                null,"upper(nome)",null);
        ArrayList<Usuario> listaUsuario = new ArrayList<Usuario>();
        while(cursor.moveToNext()){
            Usuario u = new Usuario();
            u.setId(cursor.getInt(0));
            u.setNome(cursor.getString(1));
            u.setEmail(cursor.getString(2));
            u.setUsuario(cursor.getString(3));
            u.setSenha(cursor.getString(4));
            u.setNivel(cursor.getInt(5));
            listaUsuario.add(u);
        }
        return listaUsuario;
    }

    public ArrayList<Permissao> buscarPermissao(){
        String[] coluns={ COL_ID, COL_IDARTIGO, COL_IDUSUARIO1, COL_IDUSUARIO2, COL_IDUSUARIO3,
                COL_IDUSUARIO4, COL_IDUSUARIO5};
        Cursor cursor = getReadableDatabase().query(TABLE_NAME3,
                coluns,null,null,null,
                null,"upper(nome)",null);
        ArrayList<Permissao> listaPermissao = new ArrayList<Permissao>();
        while(cursor.moveToNext()){
            Permissao p = new Permissao();
            p.setId(cursor.getInt(0));
            p.setIdArtigo(cursor.getInt(1));
            p.setIdUsuario1(cursor.getInt(2));
            p.setIdUsuario2(cursor.getInt(3));
            p.setIdUsuario3(cursor.getInt(4));
            p.setIdUsuario4(cursor.getInt(5));
            p.setIdUsuario5(cursor.getInt(6));
            listaPermissao.add(p);
        }
        return listaPermissao;
    }

    public long excluirUsuario(Usuario usuario) {
        long retornoBD;
        db = this.getWritableDatabase();
        String[] args = {String.valueOf(usuario.getId())};
        retornoBD=db.delete(TABLE_NAME, COL_ID+"=?",args);
        Log.d(TAG, "sucesso excluir usuario");
        return retornoBD;
    }

    public long excluirArtigo(Artigo a) {
        long resultadoBD;
        db = this.getWritableDatabase();
        String[] args = {String.valueOf(a.getId())};
        resultadoBD = db.delete(TABLE_NAME2, COL_ID2 + "=?", args);
        Log.d(TAG, "sucesso excluir artigo");
        return resultadoBD;
    }

    public long excluirPermissao(Artigo a) {
        long resultadoBD;
        db = this.getWritableDatabase();
        String[] args = {String.valueOf(a.getId())};
        resultadoBD = db.delete(TABLE_NAME3, COL_ID3 + "=?", args);
        Log.d(TAG, "sucesso excluir Permissao");
        return resultadoBD;
    }

    public long atualizarUsuario(Usuario u){
        long retornoBD = -1;
        db = this.getWritableDatabase();
        try{
        ContentValues values = new ContentValues();
        values.put(COL_ID,u.getId());
        values.put(COL_NAME,u.getNome());
        values.put(COL_EMAIL, u.getEmail());
        values.put(COL_USER, u.getUsuario());
        values.put(COL_PASS,u.getSenha());
        values.put(COL_NIVEL,u.getNivel());
        String[] args = {String.valueOf(u.getId())};
        retornoBD=db.update(TABLE_NAME,values,"id=?",args);
            if(retornoBD != -1)
                Log.d(TAG, "sucesso atualizar Usuario");
        } catch (Exception e) {
            Log.d(TAG, "Erro atualizar Usuario");
        } finally {
            db.close();
            return retornoBD;
        }
    }

    public long atualizarArtigo (Artigo a){
        long resultadoBD = -1;
        db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_ID2, a.getId());
            values.put(COL_TITULO, a.getTitulo());
            values.put(COL_ARTIGO, a.getArtigo());
            values.put(COL_USERID, a.getIdUsuario());
            values.put(COL_IDPERMISSAO, a.getIdPermissao());
            String[] args = {String.valueOf(a.getTitulo())};
            resultadoBD = db.update(TABLE_NAME2, values, COL_ID2 + " = ?", args);
            if(resultadoBD != -1)
                Log.d(TAG, "sucesso atualizar Artigo");
        } catch (Exception e) {
            Log.d(TAG, "Erro atualizar Artigo");
        } finally {
            db.close();
            return resultadoBD;
        }
    }

    public long atualizarPermissao(Permissao p){
        long retornoBD = -1;
        db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(COL_ID3,p.getId());
            values.put(COL_IDARTIGO,p.getIdArtigo());
            values.put(COL_IDUSUARIO1,p.getIdUsuario1());;
            values.put(COL_IDUSUARIO2,p.getIdUsuario2());;
            values.put(COL_IDUSUARIO3,p.getIdUsuario3());;
            values.put(COL_IDUSUARIO4,p.getIdUsuario4());;
            values.put(COL_IDUSUARIO5,p.getIdUsuario5());
            String[] args = {String.valueOf(p.getId())};
            retornoBD=db.update(TABLE_NAME3,values,"id=?",args);
            if(retornoBD != -1)
                Log.d(TAG, "sucesso atualizar Permissao");
        } catch (Exception e) {
            Log.d(TAG, "Erro atualizar Permissao");
        } finally {
            db.close();
            return retornoBD;
        }
    }
}
