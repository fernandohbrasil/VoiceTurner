package br.com.fernandohbrasil.voicetuner.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.fernandohbrasil.voicetuner.R;
import br.com.fernandohbrasil.voicetuner.config.MyApp;
import br.com.fernandohbrasil.voicetuner.dao.Create;
import br.com.fernandohbrasil.voicetuner.dao.PessoaDao;
import br.com.fernandohbrasil.voicetuner.model.Pessoa;

public class LoginController extends Activity {

    private Button btnLogar, btnSair, btnNovo;
    private EditText email, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        new Create().createTable();
        iniciaVisual();

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novoUsuario();
            }
        });
    }

    private void novoUsuario() {
        Intent intent = new Intent(this, PessoaController.class);
        startActivity(intent);
    }

    private void login() {
        PessoaDao oPessoaDao = new PessoaDao();
        Pessoa oPessoa = oPessoaDao.fazLogin(email.getText().toString(), senha.getText().toString());

        if (oPessoa != null) {
            MyApp.setUsuarioLogado(oPessoa);
            Intent intent = new Intent(this, Principal.class);
            startActivity(intent);
        } else {
            Toast.makeText(LoginController.this, "Dados incorretos", Toast.LENGTH_LONG).show();
        }
    }

    private void iniciaVisual() {
        btnLogar = findViewById(R.id.btnLogar);
        btnSair = findViewById(R.id.btnSair);
        btnNovo = findViewById(R.id.btnNovo);

        email = (EditText) findViewById(R.id.edtEmail);
        senha = (EditText) findViewById(R.id.edtSenha);
    }
}
