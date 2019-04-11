package br.com.fernandohbrasil.voicetuner.model;

public class Nota {

    private int id;
    private String descricao;
    private String sigla;
    private float frequencia;
    private int media;

    public Nota(int id, String descricao, String sigla, float frequencia, int media) {
        this.id = id;
        this.descricao = descricao;
        this.sigla = sigla;
        this.frequencia = frequencia;
        this.media = media;
    }

    public Nota() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public float getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(float frequencia) {
        this.frequencia = frequencia;
    }

    public int getMedia() {
        return media;
    }

    public void setMedia(int media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return descricao + " - " + sigla + " - Hz: " + frequencia;
    }
}
