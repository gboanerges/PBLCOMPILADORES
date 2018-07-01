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
public class Token {

    private int line;
    private int position;

    private String value;
    private String type;
    private String description;
    
    
    public Token(String valor, String tipo, int linha, int posicao, String description) {
        this.line = linha;
        this.position = posicao;
        this.value = valor;
        this.type = tipo;
        this.description = description;
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
    
    public String getDescription() {
        return description;
    }
}
