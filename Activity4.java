package com.example.trabalhofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity4 extends AppCompatActivity {
    private TextView txtNome;
    private ListView listArtigo;
    private Button btnCriarArtigo;
    DBHelper helper;
    Usuario usuario;
    Artigo artigo;
    ArrayList<Artigo> arrayListArtigo;
    ArrayAdapter<Artigo> arrayAdapterArtigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);

        txtNome = findViewById(R.id.txtNome);
        Bundle args = getIntent().getExtras();

        Intent it = getIntent();
        usuario = (Usuario) it.getSerializableExtra("usuario");
        String nome = usuario.getNome();
        txtNome.setText("Bem vindo "+nome);
        btnCriarArtigo = findViewById(R.id.btnCriarArtigo);

        listArtigo=findViewById(R.id.listArtigo);
        registerForContextMenu(listArtigo);

        listArtigo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artigo artigoEnviado = (Artigo)
                        arrayAdapterArtigo.getItem(i);
                Intent it = new Intent(Activity4.this,Activity5.class);
                it.putExtra("chave_artigo",artigoEnviado);
                startActivity(it);
            }
        });
        listArtigo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,View view, int
                    position, long id){
                artigo = arrayAdapterArtigo.getItem(position);
                return false;
            }
        });
    }
    public void preencheLista(){
        helper=new DBHelper(Activity4.this);
        arrayListArtigo = helper.buscarTodosArtigos();
        helper.close();
        if(listArtigo!=null){
            arrayAdapterArtigo = new ArrayAdapter<Artigo>(
                    Activity4.this,
                    android.R.layout.simple_list_item_1,
                    arrayListArtigo);
            listArtigo.setAdapter(arrayAdapterArtigo);
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        preencheLista();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View
            v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem mDelete = menu.add(Menu.NONE, 1, 1,"Deleta Registro");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                long retornoBD=1;
                helper=new DBHelper(Activity4.this);
                retornoBD = helper.excluirArtigo(artigo);
                helper.close();
                if(retornoBD==-1){
                    alert("Erro de excluir");
                }
                else{
                    alert("Sucesso ao excluir");
                }
                preencheLista();
                return false; }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    public void tocriarartigo(View view){
        Intent it = new Intent(Activity4.this, Activity5.class);
        startActivity(it);
    }
}