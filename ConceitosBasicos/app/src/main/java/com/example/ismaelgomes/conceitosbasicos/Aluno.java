package com.example.ismaelgomes.conceitosbasicos;

import android.content.res.Resources;
import android.graphics.Bitmap;

import java.io.Serializable;

public class Aluno implements Serializable{


    private int id;
    private String nome;
    private String telefone;
    private String endereco;
    private Bitmap foto;
    private String site;
    private Double nota;

    public Aluno(){

    }

    public Aluno(String nome, String tel, String end, Bitmap foto, String site, Double nota){
        this.setNome(nome);
        this.setTelefone(tel);
        this.setEndereco(end);
        this.setFoto(foto);
        this.setSite(site);
        this.setNota(nota);
    }

    public int getId(){ return  id;}

    public void setId(int id){ this.id = id;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}
