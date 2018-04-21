/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.AnalisadorLexController;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author jvboa
 */
public class main {

    public static void main(String[] args) {
        String linha = "";
        String path = "C:\\Users\\jvboa\\Documents\\GUSTAVO\\COMPILADORES\\PBLCOMPILADORES\\PBL01COMP\\src\\arquivo.txt";
        AnalisadorLexController analisadorL = new AnalisadorLexController();

        try {

            BufferedReader br = new BufferedReader(new FileReader(path));
            while (br.ready()) {
                linha += br.readLine() + "\n";
                //linha += br.readLine();
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.printf("texto:%s", linha);
        analisadorL.analisarCaracteres(linha);
    }
}
