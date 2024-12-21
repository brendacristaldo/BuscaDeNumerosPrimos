/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.crivodeeratostenesparalela;

import java.util.Arrays;

/*
Crivo de Eratóstenes
Autor:
    ChatGPT
Colaborador:
    RAIANE STEFANE CAMPOS CORREIA e DIOMENES DE ARAUJO MARANGONI
Descrição:
    Dado um número máximo, o Crivo de Eratóstenes encontra todos
    os números primos de 0 até este número máximo
*/
public class CrivoDeEratostenesParalela {

    private static final int NUM_THREADS = 6; // Número de threads
    private static final int LIMITE_SUPERIOR = 1000000; // Limite superior

    public static void main(String[] args) throws InterruptedException {
        boolean[] primos = new boolean[LIMITE_SUPERIOR + 1];
        Arrays.fill(primos, true);
        primos[0] = primos[1] = false;

        Thread[] threads = new Thread[NUM_THREADS];
        int intervalo = LIMITE_SUPERIOR / NUM_THREADS;

        for (int i = 0; i < NUM_THREADS; i++) {
            int inicio = i * intervalo + 2;
            int fim = (i == NUM_THREADS - 1) ? LIMITE_SUPERIOR : (inicio + intervalo - 1);
            threads[i] = new ThreadCrivo(primos, inicio, fim, LIMITE_SUPERIOR);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.print("Números primos até " + LIMITE_SUPERIOR + ":");
        for (int i = 2; i <= LIMITE_SUPERIOR; i++) {
            if (primos[i]) {
                System.out.println(i);
            }
        }
    }
}

class ThreadCrivo extends Thread {
    private boolean[] primos;
    private int inicio;
    private int fim;
    private int limiteSuperior;

    public ThreadCrivo(boolean[] primos, int inicio, int fim, int limiteSuperior) {
        this.primos = primos;
        this.inicio = inicio;
        this.fim = fim;
        this.limiteSuperior = limiteSuperior;
    }

    @Override
    public void run() {
        for (int i = inicio; i <= fim; i++) {
            if (primos[i]) {
                for (int j = i * i; j <= limiteSuperior; j += i) {
                    primos[j] = false;
                }
            }
        }
    }
}
