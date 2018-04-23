/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.AnalisadorLexController;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import model.ErroLex;

/**
 *
 * @author jvboa
 */
public class main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String linha = "";
        String path = "C:\\Users\\jvboa\\Documents\\GUSTAVO\\COMPILADORES\\PBLCOMPILADORES\\PBL01COMP\\src\\arquivo.txt";
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
        Collection<ErroLex> erros = analisadorL.getErrosLista();
        if (erros.isEmpty()) {
            System.out.println("SEM ERROS LEXICOS");
        }
        FileWriter arq = new FileWriter("C:\\Users\\jvboa\\Documents\\GUSTAVO\\COMPILADORES\\PBLCOMPILADORES\\PBL01COMP\\src\\saida.txt");
        PrintWriter escreverArq = new PrintWriter(arq);
        escreverArq.printf("+--Resultado--+%n");
        arq.close();
    }
}
