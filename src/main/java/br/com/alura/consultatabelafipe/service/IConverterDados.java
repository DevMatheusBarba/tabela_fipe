package br.com.alura.consultatabelafipe.service;

import java.util.List;

public interface IConverterDados {
    //Método para Dados genéricos
    <T> T obtemDados(String json, Class<T> classe);

    //Métdo para Lista Genérica
    <T> List <T> obtemLista(String json, Class<T> classe);
}
