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
import java.io.IOException;

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
//            linha += br.readLine();
        }
        br.close();

        System.out.printf("texto:%s\n", linha);
//        boolean num = analisadorL.numero(linha);
//        if (!num) {
//            System.out.println("fail");
//        } else {
//            System.out.println("true");
//
//        }
        analisadorL.analisarCaracteres(linha);
    }
}
