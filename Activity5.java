package com.example.trabalhofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity5 extends AppCompatActivity {
    DBHelper helper = new DBHelper(this);
    private EditText edtTitulo;
    private EditText edtIdDoCriador;
    private EditText edtArtigo;
    private Button btnSalvar;
    private Artigo a;
    private Artigo altArtigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);

        edtTitulo = findViewById(R.id.edtTitulo);
        edtIdDoCriador = findViewById(R.id.edtIdDoCriador);
        edtArtigo = findViewById(R.id.edtArtigo);
        btnSalvar=findViewById(R.id.btnSalvar);

        Intent it=getIntent();
        altArtigo = (Artigo) it.getSerializableExtra("chave_artigo");
        a = new Artigo();

        if(altArtigo != null){
            btnSalvar.setText("ALTERAR");
            edtTitulo.setText(altArtigo.getTitulo());
            edtIdDoCriador.setText(altArtigo.getIdUsuario());
            edtArtigo.setText(altArtigo.getArtigo());
            a.setId(altArtigo.getId());
        }else{
            btnSalvar.setText("SALVAR");
        }
    }
    public void cadastrar(View view) {
        String titulo = edtTitulo.getText().toString();
        int idDoCriador = Integer.parseInt(edtIdDoCriador.getText().toString());
        String artigo = edtArtigo.getText().toString();

        a.setTitulo(titulo);
        a.setIdUsuario(idDoCriador);
        a.setArtigo(artigo);

        if(btnSalvar.getText().toString().equals("SALVAR")) {
            Permissao p = new Permissao();
            p.setIdArtigo(a.getId());
            p.setIdUsuario1(a.getIdUsuario());
            p.setIdUsuario2(a.getIdUsuario());
            p.setIdUsuario3(a.getIdUsuario());
            p.setIdUsuario4(a.getIdUsuario());
            p.setIdUsuario5(a.getIdUsuario());
            helper.inserePermissao(p);
            helper.insereArtigo(a);
            Toast toast = Toast.makeText(Activity5.this,
                        "Sucesso ao cadastrar", Toast.LENGTH_SHORT);
            toast.show();

        }else{
            helper.atualizarArtigo(a);
            helper.close();
        }
        limpar();
        finish();

    }
    public void limpar(){
        edtTitulo.setText("");
        edtIdDoCriador.setText("");
        edtArtigo.setText("");
    }
    public void cancelar(View view) {
        finish();
    }
}