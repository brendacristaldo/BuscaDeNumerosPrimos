/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.primobuscasequencial;
import java.util.Arrays;
/*
Crivo de Eratóstenes
Autor:
    ChatGPT
Colaborador:
    Brenda Beatriz Cristaldo (brendacristaldo@alunos.utfpr.edu.br)
Descrição:
    Dado um número máximo, o Crivo de Eratóstenes encontra todos
    os números primos de 0 até este número máximo
*/
public class PrimoBuscaSequencial {

    public static void main(String[] args) {
        int limite = 100; // Limite superior para encontrar primos
        boolean[] primos = crivoDeEratostenes(limite);
        
        System.out.println("Numeros primos ate " + limite+ ":");
        for (int i = 2; i <= limite; i++) {
            if (primos[i]) {
                System.out.print(i + " ");
            }
        }
    }

    public static boolean[] crivoDeEratostenes(int n) {
        // Cria um array de booleanos onde true indica que o número é primo
        boolean[] primos = new boolean[n + 1];
        Arrays.fill(primos, true);
        primos[0] = primos[1] = false; // 0 e 1 não são primos

        for (int i = 2; i * i <= n; i++) {
            if (primos[i]) {
                // Marca todos os múltiplos de i como não primos
                for (int j = i * i; j <= n; j += i) {
                    primos[j] = false;
                }
            }
        }
        return primos;
    }
}
