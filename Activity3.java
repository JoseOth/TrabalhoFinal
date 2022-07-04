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

public class Activity3 extends AppCompatActivity {
    private TextView txtNome;
    private ListView listUsuarios;
    private Button btnArtigos;
    DBHelper helper;
    Usuario usuario;
    ArrayList<Usuario> arrayListUsuario;
    ArrayAdapter<Usuario> arrayAdapterUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        txtNome = findViewById(R.id.txtNome);
        Bundle args = getIntent().getExtras();
        String nome = args.getString("chave_usuario");
        txtNome.setText("Bem vindo "+nome);
        btnArtigos = findViewById(R.id.btnArtigos);

        listUsuarios=findViewById(R.id.listUsuarios);
        registerForContextMenu(listUsuarios);

        listUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Usuario contatoEnviada = (Usuario)
                        arrayAdapterUsuario.getItem(i);
                Intent it = new Intent(Activity3.this,Activity2.class);
                it.putExtra("chave_usuario",contatoEnviada);
                startActivity(it);
            }
        });
        listUsuarios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,View view, int
                    position, long id){
                usuario = arrayAdapterUsuario.getItem(position);
                return false;
            }
        });
    }
    public void preencheLista(){
        helper=new DBHelper(Activity3.this);
        arrayListUsuario = helper.buscarUsuarios();
        helper.close();
        if(listUsuarios!=null){
            arrayAdapterUsuario = new ArrayAdapter<Usuario>(
                    Activity3.this,
                    android.R.layout.simple_list_item_1,
                    arrayListUsuario);
            listUsuarios.setAdapter(arrayAdapterUsuario);
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
        MenuItem mArtigo = menu.add(Menu.NONE, 1, 1,"Visualizar Artigo");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                long retornoBD=1;
                helper=new DBHelper(Activity3.this);
                retornoBD = helper.excluirUsuario(usuario);
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
        mArtigo.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(Activity3.this, Activity4.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            return true;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    public void toartigo(View view){
        Intent i = new Intent(Activity3.this, Activity4.class);
        startActivity(i);
        finish();
    }
}