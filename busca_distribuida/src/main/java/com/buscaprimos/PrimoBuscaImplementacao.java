/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buscaprimos;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

/**
 *Implementa a lógica de encontrar números primos em um intervalo específico. 
 *Herda de UnicastRemoteObject para ser um objeto RMI.
 * @author thaki
 */
public class PrimoBuscaImplementacao extends UnicastRemoteObject implements PrimoBuscaInterface {
    // Construtor necessário para RMI
    protected PrimoBuscaImplementacao() throws RemoteException {
        super();
    }

    @Override
    public boolean[] buscarPrimos(int inicio, int fim) throws RemoteException {
        // Implementação do Crivo de Eratóstenes para um intervalo específico
        boolean[] primos = new boolean[fim - inicio + 1];
        Arrays.fill(primos, true);

        for (int i = 2; i * i <= fim; i++) {
            for (int j = Math.max(i * i, (inicio + i - 1) / i * i); j <= fim; j += i) {
                if (j >= inicio) {
                    primos[j - inicio] = false;
                }
            }
        }

        return primos;
    }
}
