package br.com.alura.consultatabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Dados(String codigo, String nome) {

    @Override
    public String toString() {
        return "cód: " + codigo +
                " Nome: " + nome;
    }
}


