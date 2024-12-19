/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buscaprimos;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Busca Distribuída de número Primos - Cliente solicita o serviço remoto
 * Descrição:
 * Divide a busca de primos entre múltiplos hosts/servidores, usando RMI para comunicação remota.
 * @Nathália Miyuki
 * @Thaisse Veiga
 */


public class PrimoBuscaCliente {
    // Limite superior para busca de primos
    private static final int LIMITE_SUPERIOR = 5000;
    
    // Número de hosts/servidores a serem utilizados
    private static final int NUM_HOSTS = 4;

    public static void main(String[] args) {
        try {
            // Marca o tempo de início
            long tempoInicial = System.currentTimeMillis();

            // Busca primos de forma distribuída
            List<boolean[]> resultados = buscarPrimosDistribuido();

            // Imprime os números primos encontrados
            System.out.println("Números primos encontrados:");
            imprimirPrimos(resultados);

            // Calcula e imprime o tempo de execução
            long tempoFinal = System.currentTimeMillis();
            System.out.println("\nTempo de execução: " + (tempoFinal - tempoInicial) + " ms");

        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.toString());
        }
    }

    // Método para distribuir a busca de primos entre múltiplos hosts
    private static List<boolean[]> buscarPrimosDistribuido() throws Exception {
        List<boolean[]> resultados = new ArrayList<>();
        
        // Cria um pool de threads para gerenciar chamadas remotas
        ExecutorService executor = Executors.newFixedThreadPool(NUM_HOSTS);
        List<Future<boolean[]>> futures = new ArrayList<>();

        // Divide o intervalo total entre os hosts
        int intervalo = LIMITE_SUPERIOR / NUM_HOSTS;

        for (int i = 0; i < NUM_HOSTS; i++) {
            int inicio = i * intervalo + 2;
            int fim = (i == NUM_HOSTS - 1) ? LIMITE_SUPERIOR : (inicio + intervalo - 1);

            // Submete tarefa para buscar primos em um subintervalo
            futures.add(executor.submit(() -> {
                // Conecta ao registro RMI
                Registry registro = LocateRegistry.getRegistry("localhost", 1099);
                
                // Obtém referência para o serviço remoto
                PrimoBuscaInterface servico = (PrimoBuscaInterface) registro.lookup("ServicoBuscaPrimos");
                
                // Chama método remoto para buscar primos
                return servico.buscarPrimos(inicio, fim);
            }));
        }

        // Coleta resultados de todas as tarefas
        for (Future<boolean[]> future : futures) {
            resultados.add(future.get());
        }

        // Encerra o executor de threads
        executor.shutdown();
        return resultados;
    }

    // Método para imprimir os números primos encontrados
    private static void imprimirPrimos(List<boolean[]> resultados) {
        for (int i = 0; i < resultados.size(); i++) {
            boolean[] resultado = resultados.get(i);
            int inicio = i * (LIMITE_SUPERIOR / NUM_HOSTS) + 2;

            for (int j = 0; j < resultado.length; j++) {
                if (resultado[j]) {
                    System.out.print((inicio + j) + " ");
                }
            }
        }
    }
}
