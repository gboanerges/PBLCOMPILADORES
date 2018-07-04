/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.AnalisadorLexController;
import controller.AnalisadorSintController;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.ErroLex;
import model.ErroSintatico;
import model.Token;

/**
 *
 * @author jvboa
 */
public class main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String linha = "";
        String path = "src\\entrada.txt";
        AnalisadorLexController analisadorL = new AnalisadorLexController();

        BufferedReader br = new BufferedReader(new FileReader(path));
        while (br.ready()) {
            linha += br.readLine() + "\n";
            //linha += br.readLine();
        }
        br.close();

        System.out.printf("texto:%s\n", linha);
//        boolean num = analisadorL.cadeiaCaracteres(linha);
//        if (!num) {
//            System.out.println("fail");
//        } else {
//            System.out.println("true");
//        }
        analisadorL.analisarCaracteres(linha);
        ArrayList<Token> tokensLista = analisadorL.getTokensLista();
        ArrayList<ErroLex> errosLista = analisadorL.getErrosLista();
        FileWriter arq = new FileWriter("src\\saidaLex.txt");
        PrintWriter escreverArq = new PrintWriter(arq);
        escreverArq.printf("+--Resultado Analisador Léxico--+%n");
        if (tokensLista.isEmpty()) {
            escreverArq.printf("NAO EXISTE TOKENS OU ARQUIVO VAZIO");
        } else {
            tokensLista.forEach((tokens) -> {
                if (tokens.getDescription().equals("")) {
                    escreverArq.printf("Tipo: " + tokens.getType() + " Valor: " + tokens.getValue() + " Linha: " + tokens.getLine() + " Posicao: " + tokens.getPosition() + "%n");
                }

            });

        }
        if (errosLista.isEmpty()) {
            escreverArq.printf("SEM ERROS LEXICOS%n+----------------------+");
        } else {
            escreverArq.printf("%nERROS LEXICOS%n");
            errosLista.forEach((erros) -> {
                escreverArq.printf("Erro: " + erros.getDescription() + " Valor: " + erros.getValue() + " Linha: " + erros.getLine() + " Posicao: " + erros.getPosition() + "%n");
            });
        }
        arq.close();

        AnalisadorSintController as = new AnalisadorSintController(tokensLista);
        ArrayList<ErroSintatico> errosSint = as.getErrosSint();
        
        //Chamar o metodo respectivo ao que sera testado
        as.scan();
        as.print();

        FileWriter arq2 = new FileWriter("src\\saida.txt");
        PrintWriter escreverSint = new PrintWriter(arq2);
        escreverSint.printf("+--Resultado Analisador Sintático--+%n");
        if (errosSint.isEmpty()) {
            escreverSint.printf("SEM ERROS SINTATICOS%n+----------------------+");
        } else {
            escreverSint.printf("ERROS SINTATICOS%n");
            errosSint.forEach((erros) -> {
                escreverSint.printf("Erro: " + erros.getType() +" Linha: " + erros.getLine() +"%n");
            });
        }
        arq2.close();
    }
}
