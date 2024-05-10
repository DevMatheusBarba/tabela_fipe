package br.com.alura.consultatabelafipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class ConverteDados implements IConverterDados {

    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T obtemDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    //Implementando o método na qual Acessa a Lista recebida do JSON e converte em uma Class
    @Override
    public <T> List<T> obtemLista(String json, Class<T> classe) {
        CollectionType lista = mapper.getTypeFactory()
                .constructCollectionType(List.class, classe);
        try {
            return mapper.readValue(json, lista);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }

    }


}
