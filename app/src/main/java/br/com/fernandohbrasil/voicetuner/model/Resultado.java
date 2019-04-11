package br.com.fernandohbrasil.voicetuner.model;

import java.util.ArrayList;

public class Resultado {

    private int id;
    private Pessoa oAluno;
    private Exercicio oExercicio;
    private ArrayList<ItemResultado> progressao;

    public Resultado(int id, Pessoa oAluno, Exercicio oExercicio, ArrayList<ItemResultado> progressao) {
        this.id = id;
        this.oAluno = oAluno;
        this.oExercicio = oExercicio;
        this.progressao = progressao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pessoa getoAluno() {
        return oAluno;
    }

    public void setoAluno(Pessoa oAluno) {
        this.oAluno = oAluno;
    }

    public Exercicio getoExercicio() {
        return oExercicio;
    }

    public void setoExercicio(Exercicio oExercicio) {
        this.oExercicio = oExercicio;
    }

    public ArrayList<ItemResultado> getProgressao() {
        return progressao;
    }

    public void setProgressao(ArrayList<ItemResultado> progressao) {
        this.progressao = progressao;
    }

    @Override
    public String toString() {
        return "Resultado: " + id + "\n" +
                "Aluno: " + oAluno.getNome() + "\n" +
                oExercicio;
    }
}
