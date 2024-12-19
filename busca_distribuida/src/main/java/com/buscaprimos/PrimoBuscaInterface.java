/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.buscaprimos;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *Define o contrato para o serviço remoto. 
 *O método buscarPrimos será implementado no servidor e chamado pelo cliente.
 * @author thaki
 */
public interface PrimoBuscaInterface extends Remote {
    // Método para buscar números primos em um intervalo
    boolean[] buscarPrimos(int inicio, int fim) throws RemoteException;
}