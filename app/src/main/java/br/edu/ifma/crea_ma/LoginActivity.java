package br.edu.ifma.crea_ma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsuario;
    private EditText txtSenha;
    private Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsuario = (EditText) findViewById(R.id.login_usuario);
        txtSenha = (EditText) findViewById(R.id.login_senha);
        btnEntrar = (Button) findViewById(R.id.login_btnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUsuario.getText().toString().trim().equals("") || txtSenha.getText().toString().trim().equals("")){
                    Toast.makeText(LoginActivity.this, "Os campos Login e senha são obrigatórios", Toast.LENGTH_LONG).show();
                }else {
                    if (txtUsuario.getText().toString().equals("admin") && txtSenha.getText().toString().equals("123")) {
                        Toast.makeText(LoginActivity.this, "Seja Bem vindo " + txtUsuario.getText().toString() + "!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, ListaNotificacoesActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Usuário ou Senha não confere", Toast.LENGTH_LONG).show();
                    }
                }
                limparCampos();
            }
        });

    }

    public void limparCampos(){
        txtUsuario.setText("");
        txtUsuario.requestFocus();
        txtSenha.setText("");
    }
}
