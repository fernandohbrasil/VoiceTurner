package br.com.fernandohbrasil.voicetuner.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import br.com.fernandohbrasil.voicetuner.R;
import br.com.fernandohbrasil.voicetuner.dao.PessoaDao;
import br.com.fernandohbrasil.voicetuner.model.Pessoa;

public class PessoaController extends Activity {

    private Button btnSalvar, btnSair;
    private RadioButton rbAluno;

    private EditText nome, email, senha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pessoa);

        iniciaObjetos();

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });
    }

    private int getPapel() {
        if (rbAluno.isChecked()) {
            return 0;
        } else {
            return 1;
        }
    }

    private Pessoa buscaValoresTela() {
        Pessoa oPessoa = new Pessoa();
        oPessoa.setId(0);
        oPessoa.setNome(nome.getText().toString());
        oPessoa.setEmail(email.getText().toString());
        oPessoa.setSenha(Integer.parseInt(senha.getText().toString()));
        oPessoa.setPapel(getPapel());
        return oPessoa;
    }

    private void salvar() {
        PessoaDao oPessoaDao = new PessoaDao();
        boolean emailJaCadastrado = new PessoaDao().emailExistente(email.getText().toString());

        if (!emailJaCadastrado) {
            if (PessoaDao.cadastraPessoa(buscaValoresTela())) {
                Toast.makeText(PessoaController.this, "Sucesso ao cadastrar!", Toast.LENGTH_LONG).show();
                System.exit(0);
            } else {
                Toast.makeText(PessoaController.this, "Erro ao cadastrar!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(PessoaController.this, "Email já cadastrado!", Toast.LENGTH_LONG).show();
        }
    }

    private void iniciaObjetos() {
        btnSalvar = (Button) findViewById(R.id.btnGravarPessoa);
        btnSair = (Button) findViewById(R.id.btnSair);

        nome = (EditText) findViewById(R.id.edtNome);
        email = (EditText) findViewById(R.id.edtEmail);
        senha = (EditText) findViewById(R.id.edtSenha);

        rbAluno = (RadioButton) findViewById(R.id.rbAluno);
    }
}
