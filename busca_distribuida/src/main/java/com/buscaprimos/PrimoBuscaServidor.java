/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.buscaprimos;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Inicia o serviço RMI, criando um registro e vinculando o serviço de busca de primos.
 * @author thaki
 */
public class PrimoBuscaServidor {
    public static void main(String[] args) {
        try {
            // Cria a implementação do serviço
            PrimoBuscaImplementacao servico = new PrimoBuscaImplementacao();
            
            // Cria o registro RMI na porta padrão (1099)
            Registry registro = LocateRegistry.createRegistry(1099);
            
            // Registra o serviço para que clientes possam encontrá-lo
            registro.rebind("ServicoBuscaPrimos", servico);
            
            System.out.println("Servidor RMI de busca de primos iniciado.");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}
