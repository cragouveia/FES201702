package br.estacio.fes201702.domain;

import java.io.Serializable;

/**
 * Created by carlos on 12/09/17.
 */

public class Aluno implements Serializable{

    private int id;
    private String nome;
    private String fone;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
