package br.com.alura.consultatabelafipe.principal;

import br.com.alura.consultatabelafipe.model.Modelos;
import br.com.alura.consultatabelafipe.model.Dados;
import br.com.alura.consultatabelafipe.model.Veiculos;
import br.com.alura.consultatabelafipe.service.ConsumirAPI;
import br.com.alura.consultatabelafipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";
    private ConverteDados conversor = new ConverteDados();
    private ConsumirAPI consumirAPI = new ConsumirAPI();
    private Scanner leitura = new Scanner(System.in);


    public void menu() {
        System.out.println("""
                ***** OPÇÕES *****
                Carros
                Motos
                Caminhoes
                """);

        System.out.println("\nDigite uma das opções para consultar valores: ");
        String uriComplements = leitura.nextLine();

        if (uriComplements.toLowerCase().contains("car")){
            uriComplements = ENDERECO + "carros" + "/marcas";
        }else if (uriComplements.toLowerCase().contains("mot")){
            uriComplements = ENDERECO + "matos" + "/marcas";
        }else {
            uriComplements = ENDERECO + "caminhoes" + "/marcas";
        }

        String json = consumirAPI.obterDados(uriComplements);


        //Tratando JSON que vem em formato de lista
        List<Dados> marcas = conversor.obtemLista(json, Dados.class);

        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite o cód que deseja consultar: ");
        int uriModelo = leitura.nextInt();
        leitura.nextLine();
        uriComplements += "/" + uriModelo + "/modelos";

        //tratando JSON com Chave:[Lista de valores]
        json = consumirAPI.obterDados(uriComplements);
        var modelosList = conversor.obtemDados(json, Modelos.class);

        modelosList.modelos().stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);

        System.out.println("\nDigite o nome do modelo, para listar todos disponiveis: ");
        String modeloPesquisado = leitura.nextLine().toUpperCase();

        modelosList.modelos().stream()
                .filter(m -> m.nome().toUpperCase().contains(modeloPesquisado))
                .forEach(System.out::println);

        System.out.println("\nDigite o cód para verificar as variações de ano desse modelo disponivel");
        int codModeloPesquisado = leitura.nextInt();
        leitura.nextLine();
        uriComplements += "/" + codModeloPesquisado + "/anos";
        System.out.println(uriComplements);
        json = consumirAPI.obterDados(uriComplements);

        List<Dados> anos = conversor.obtemLista(json, Dados.class);
        List<Veiculos> anosVeiculo = new ArrayList<>();
        for (int i = 0; i < anos.size(); i++) {
            String endereco = uriComplements + "/" + anos.get(i).codigo();
            json = consumirAPI.obterDados(endereco);
            Veiculos veiculos = conversor.obtemDados(json, Veiculos.class);
            anosVeiculo.add(veiculos);
        }

        anosVeiculo.forEach(System.out::println);

    }


}
