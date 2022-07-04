package com.example.trabalhofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {
    DBHelper helper = new DBHelper(this);
    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtUsuario;
    private EditText edtSenha;
    private EditText edtConfSenha;
    private Button btnSalvar;
    private Usuario u;
    private Usuario altUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtUsuario = findViewById(R.id.edtUsuario);
        edtSenha = findViewById(R.id.edtSenha);
        edtConfSenha = findViewById(R.id.edtConfSenha);
        btnSalvar=findViewById(R.id.btnSalvar);

        Intent it=getIntent();
        altUsuario = (Usuario) it.getSerializableExtra("chave_usuario");
        u = new Usuario();

        if(altUsuario != null){
            btnSalvar.setText("ALTERAR");
            edtNome.setText(altUsuario.getNome());
            edtEmail.setText(altUsuario.getEmail());
            edtUsuario.setText(altUsuario.getUsuario());
            edtSenha.setText(altUsuario.getSenha());
            edtConfSenha.setText(altUsuario.getSenha());
            u.setId(altUsuario.getId());
        }else{
            btnSalvar.setText("SALVAR");
        }
    }
    public void cadastrar(View view) {
        String nome = edtNome.getText().toString();
        String email = edtEmail.getText().toString();
        String usuario = edtUsuario.getText().toString();
        String senha = edtSenha.getText().toString();
        String confSenha = edtConfSenha.getText().toString();

        if(!senha.equals(confSenha)){
            Toast toast = Toast.makeText(Activity2.this,
                    "Senha diferente da confirmação de senha!",
                    Toast.LENGTH_SHORT);
            toast.show();
            edtSenha.setText("");
            edtConfSenha.setText("");
        }
        else{
            u.setNome(nome);
            u.setEmail(email);
            u.setUsuario(usuario);
            u.setSenha(senha);
            if(btnSalvar.getText().toString().equals("SALVAR")) {
                helper.insereUsuario(u);
                Toast toast = Toast.makeText(Activity2.this,
                        "Sucesso ao cadastrar", Toast.LENGTH_SHORT);
                toast.show();
            }else{
                helper.atualizarUsuario(u);
                helper.close();
            }
            limpar();
            finish();
        }
    }
    public void limpar(){
        edtNome.setText("");
        edtEmail.setText("");
        edtUsuario.setText("");
        edtSenha.setText("");
        edtConfSenha.setText("");
    }
    public void cancelar(View view) {
        finish();
    }
}