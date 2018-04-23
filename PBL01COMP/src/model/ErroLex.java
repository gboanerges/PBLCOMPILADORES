/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jvboa
 */
public class ErroLex {

    private int line;
    private int position;

    private String value;
    private String type;

    public ErroLex(String valor, String tipo, int linha, int posicao) {
        this.line = linha;
        this.position = posicao;
        this.value = valor;
        this.type = tipo;
    }

    public int getLine() {
        return line;
    }

    public int getPosition() {
        return position;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}
