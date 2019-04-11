package br.com.fernandohbrasil.voicetuner.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.fernandohbrasil.voicetuner.R;

public class Principal extends Activity {

    private Button btnCadastraExercicio, btnExecutaTreino;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        iniciaObjetos();
    }

    private void iniciaObjetos() {
        btnCadastraExercicio = findViewById(R.id.btnCadExercicio);
        btnExecutaTreino = findViewById(R.id.btnTreino);
    }

    public void treino(View v) {
        Intent intent = new Intent(this, TreinoController.class);
        startActivity(intent);
    }

    public void cadastraExercicio(View v) {
        Intent intent = new Intent(this, ExercioController.class);
        startActivity(intent);
    }

    public void Resultados(View v) {
        Intent intent = new Intent(this, ResultadoController.class);
        startActivity(intent);
    }

    public void ResultadosPorExercicio(View v) {
        Intent intent = new Intent(this, ResultadoPorExercicioController.class);
        startActivity(intent);
    }

    public void Remover(View v) {
        Intent intent = new Intent(this, RemoverController.class);
        startActivity(intent);
    }

}
