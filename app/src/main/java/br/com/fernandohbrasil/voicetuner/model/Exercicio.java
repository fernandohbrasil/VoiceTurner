package br.com.fernandohbrasil.voicetuner.model;

import java.util.ArrayList;

public class Exercicio {

    private int id;
    private int nivel;
    private Pessoa oProfessor;
    private int tamanho;
    private ArrayList<NotaExercicio> progressao;

    public Exercicio() {

    }

    public Exercicio(int id, int nivel, Pessoa oProfessor, int tamanho, ArrayList<NotaExercicio> progressao) {
        this.id = id;
        this.nivel = nivel;
        this.oProfessor = oProfessor;
        this.tamanho = tamanho;
        this.progressao = progressao;
    }

    public Exercicio(int id, int nivel, Pessoa oProfessor, int tamanho) {
        this.id = id;
        this.nivel = nivel;
        this.oProfessor = oProfessor;
        this.tamanho = tamanho;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Pessoa getoProfessor() {
        return oProfessor;
    }

    public void setoProfessor(Pessoa oProfessor) {
        this.oProfessor = oProfessor;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public ArrayList<NotaExercicio> getProgressao() {
        return progressao;
    }

    public void setProgressao(ArrayList<NotaExercicio> progressao) {
        this.progressao = progressao;
    }

    @Override
    public String toString() {
        return "Exercicio: " + id + "\n" +
                "Nivel: " + descreveNivel(nivel) + "\n" +
                "Prof: " + oProfessor.getNome() + "\n" +
                "Quantidade de Notas: " + tamanho;
    }

    private String descreveNivel(int i) {
        if (i == 0) {
            return "Fácil";
        } else if (i == 1) {
            return "Médio";
        } else {
            return "Difícil";
        }
    }
}