/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import model.ErroSintatico;
import model.Token;

/**
 *
 * @author jvboa
 */
public class AnalisadorSintController {

    private ArrayList<Token> tokens;
    private int posicaoLista = 0;
    private ArrayList<ErroSintatico> errosSint = new ArrayList<>();

    public AnalisadorSintController(ArrayList<Token> lista) {
        this.tokens = lista;
    }

    public void analisarTokens() {
        tokens.forEach(((tokens) -> {
            System.out.printf("Token: " + tokens.getType() + " Valor: " + tokens.getValue() + "\n");
        }));
    }

    public void start() {
        System.out.println("\n========START========\n");
        while (posicaoLista < tokens.size()) {
            if (tokens.get(posicaoLista).getValue().equals("start")) {
                System.out.println("Encontrou START");
                posicaoLista++;
                //verificar Bloco, se possue chaves
                if (tokens.get(posicaoLista).getValue().equals("{")) {

                } else {
                    System.out.println("-------Esperava { ... erro sintático\n");
                }
            } else {
                System.out.println("-------Esperava START ... erro sintático\n");
            }
        }
    }

    public void bloco() {
        System.out.println("\n========BLOCO========\n");
        while (posicaoLista < tokens.size()) {
            if (tokens.get(posicaoLista).getValue().equals("{")) {
                System.out.println("Encontrou { abre chaves");

            } else {
                System.out.println("-------Esperava { ... erro sintático : %s %s\n");
            }
        }
    }

    public void print() {
        System.out.println("\n========Print========\n");
        boolean printCall = true;
        boolean pontoVirgula = false;

        if (tokens.get(posicaoLista).getValue().equals("print")) {
            System.out.println("Encontrou Print");
            posicaoLista++;
            while (posicaoLista < tokens.size()) {
                if (tokens.get(posicaoLista).getValue().equals("(")) {
                    System.out.println("Encontrou (");
                    posicaoLista++;
                    while (posicaoLista < tokens.size()) {
                        if (tokens.get(posicaoLista).getType().equals("IDE")) {
                            System.out.printf("Encontrou ID: %s %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType());
                            posicaoLista++;
                            idAcesso(printCall);
                            while (pontoVirgula == false && posicaoLista < tokens.size()) {
                                System.out.println("while pontoVirgula");
                                if (tokens.get(posicaoLista).getValue().equals(";")) {
                                    System.out.println("Encontrou ;");
                                    posicaoLista++;
                                    pontoVirgula = true;
                                } else {
                                    System.out.printf("-------Esperava ; ... PRINT: %s %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType());
                                    posicaoLista++;
                                    ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta delimitador: ; (PONTO E VIRGULA)");
                                    errosSint.add(es);

                                }
                            }
                            pontoVirgula = false;
                            break;
                        } else if (tokens.get(posicaoLista).getType().equals("CC")) {
                            System.out.println("Encontrou C Caracteres");
                            posicaoLista++;
                            idAcesso(printCall);
                            while (pontoVirgula == false && posicaoLista < tokens.size()) {
                                System.out.println("while CC pontoVirgula");
                                if (tokens.get(posicaoLista).getValue().equals(";")) {
                                    System.out.println("Encontrou ;");
                                    posicaoLista++;
                                    pontoVirgula = true;
                                } else {
                                    System.out.printf("-------Esperava ; ... PRINT: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                                    posicaoLista++;
                                    ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta delimitador: ; (PONTO E VIRGULA)");
                                    errosSint.add(es);
                                }
                            }
                            break;
                        } else {
                            System.out.printf("-------Esperava ID ou C Caracteres: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                            posicaoLista++;
                            ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta ID ou CC (ID OU CADEIA CARACTERES)");
                            errosSint.add(es);
                        }
                    }
                } else {
                    System.out.printf("-------Esperava ( ... PRINT: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                    posicaoLista++;
                    ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta delimitador: ( (ABRE PARENTESES)");
                    errosSint.add(es);
                }
            }
        } else {
            System.out.printf("-------Esperava Print ... erro sintáticoValor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
            posicaoLista++;
            ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta palavra reservada: print (PRINT)");
            errosSint.add(es);
        }
    }

    public void scan() {
        System.out.println("\n========SCAN========\n");
        boolean scanCall = false;
        boolean pontoVirgula = false;

        if (tokens.get(posicaoLista).getValue().equals("scan")) {
            System.out.println("Encontrou Scan\n");
            posicaoLista++;
            while (posicaoLista < tokens.size()) {
                if (tokens.get(posicaoLista).getValue().equals("(")) {
                    System.out.println("Encontrou (");
                    posicaoLista++;
                    while (posicaoLista < tokens.size()) {
                        if (tokens.get(posicaoLista).getType().equals("IDE")) {
                            System.out.printf("Encontrou ID: %s %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType());
                            posicaoLista++;
                            idAcesso(scanCall);
                            while (pontoVirgula == false && posicaoLista < tokens.size()) {
                                System.out.println("while pontoVirgula");
                                if (tokens.get(posicaoLista).getValue().equals(";")) {
                                    System.out.println("Encontrou ;");
                                    posicaoLista++;
                                    pontoVirgula = true;
                                } else {
                                    System.out.printf("-------Esperava ; ... PRINT: %s %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType());
                                    posicaoLista++;
                                    ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta delimitador: ; (PONTO E VIRGULA)");
                                    errosSint.add(es);
                                }
                            }
                            pontoVirgula = false;
                            break;
                        } else if (tokens.get(posicaoLista).getType().equals("CC")) {
                            System.out.println("Encontrou C Caracteres");
                            posicaoLista++;
                            idAcesso(scanCall);
                            while (pontoVirgula == false && posicaoLista < tokens.size()) {
                                System.out.println("while CC pontoVirgula");
                                if (tokens.get(posicaoLista).getValue().equals(";")) {
                                    System.out.println("Encontrou ;");
                                    posicaoLista++;
                                    pontoVirgula = true;
                                } else {
                                    System.out.printf("-------Esperava ; ... PRINT: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                                    posicaoLista++;
                                    ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta delimitador: ; (PONTO E VIRGULA)");
                                    errosSint.add(es);
                                }
                            }
                            break;
                        } else {
                            System.out.printf("-------Esperava ID ou C Caracteres: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                            posicaoLista++;
                            ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta ID ou CC (ID OU CADEIA CARACTERES)");
                            errosSint.add(es);
                        }
                    }
                } else {
                    System.out.printf("-------Esperava ( ... PRINT: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                    posicaoLista++;
                    ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta delimitador: ( (ABRE PARENTESES)");
                    errosSint.add(es);
                }
            }

        } else {
            System.out.println("-------Esperava Scan ... erro sintático\n");
            posicaoLista++;
            ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta palavra reservada: scan (SCAN)");
            errosSint.add(es);
        }
    }

    public void idAcesso(boolean printCall) {
        System.out.println("\n========ID Acesso========\n");

        while (posicaoLista < tokens.size()) {
            if (tokens.get(posicaoLista).getType().equals("IDE")) {
                System.out.printf("Encontrou ID: %s %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType());
                posicaoLista++;
                if (tokens.get(posicaoLista).getValue().equals(",")) {
                    System.out.printf("Encontrou virgula");
                    posicaoLista++;

                } else if (tokens.get(posicaoLista).getValue().equals("[")) {
                    System.out.println("Encontrou [");
                    posicaoLista++;

                    if (tokens.get(posicaoLista).getType().equals("NRO")) {
                        System.out.println("Encontrou numero");
                        posicaoLista++;
                        if (tokens.get(posicaoLista).getValue().equals("]")) {
                            System.out.println("Encontrou ]");
                            posicaoLista++;
                            if (tokens.get(posicaoLista).getValue().equals("[")) {
                                System.out.println("Encontrou [ MATRIZ");
                                posicaoLista++;
                                if (tokens.get(posicaoLista).getType().equals("NRO")) {
                                    System.out.println("Encontrou numero ... MATRIZ");
                                    posicaoLista++;
                                    if (tokens.get(posicaoLista).getValue().equals("]")) {
                                        System.out.println("Encontrou ] ... MATRIZ");
                                        posicaoLista++;
                                        if (tokens.get(posicaoLista).getValue().equals(")")) {
                                            System.out.println("\nEncontrou fecha Parenteses MATRIZ");
                                            posicaoLista++;
                                            return;
                                        } else if (tokens.get(posicaoLista).getValue().equals(",")) {
                                            System.out.println("Encontrou virgula MATRIZ");
                                            posicaoLista++;
                                        } else {
                                            System.out.printf("-------Esperava ) ou , ... MATRIZ: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                                            posicaoLista++;
                                            ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta delimitador: ) ou ,");
                                            errosSint.add(es);
                                        }
                                    } else {
                                        System.out.printf("-------Esperava ] ... MATRIZ: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                                        posicaoLista++;
                                        ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta delimitador: ] (FECHA COLCHETES)");
                                        errosSint.add(es);
                                    }
                                } else {
                                    System.out.printf("-------Esperava numero MATRIZ ... erro sintatico: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                                    posicaoLista++;
                                    ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta numero");
                                    errosSint.add(es);
                                }

                            } else if (tokens.get(posicaoLista).getValue().equals(",")) {
                                System.out.println("Encontrou virgula");
                                posicaoLista++;

                            } else if (tokens.get(posicaoLista).getValue().equals(")")) {
                                System.out.println("\nEncontrou fecha Parenteses VETOR");
                                posicaoLista++;
                                return;
                            } else {
                                System.out.printf("-------Esperava [ ... MATRIZ: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                                posicaoLista++;
                                ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta delimitador: [ (ABRE COLCHETES)");
                                errosSint.add(es);
                            }
                        } else {
                            System.out.printf("-------Esperava ] ... erro sintatico: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                            posicaoLista++;
                            ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta delimitador: ] (FECHA COLCHETES)");
                            errosSint.add(es);
                        }
                    } else {
                        System.out.printf("-------Esperava numero ... erro sintatico: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                        posicaoLista++;
                        ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta numero");
                        errosSint.add(es);
                    }
                } else if (tokens.get(posicaoLista).getValue().equals(")")) {
                    System.out.println("\nEncontrou fecha Parenteses ID");
                    posicaoLista++;
                    return;
                } else {
                    System.out.printf("Token inesperado: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                    posicaoLista++;
                    ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Esperava virgula, [ ou )");
                    errosSint.add(es);
                }

            } else if (tokens.get(posicaoLista).getValue().equals(",")) {
                System.out.println("Encontrou virgula");
                posicaoLista++;

            } else if (tokens.get(posicaoLista).getValue().equals(")")) {
                System.out.println("\nEncontrou fecha Parenteses ID");
                posicaoLista++;
                return;
            } else if (printCall == true && tokens.get(posicaoLista).getType().equals("CC")) {
                System.out.println("Encontrou C Caracteres ID ACESSO");
                posicaoLista++;
            } else if (tokens.get(posicaoLista).getValue().equals("[")) {
                vetorOuMatriz();
            } else {
                System.out.printf("-------Esperava acesso a ID: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                posicaoLista++;
                ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta ID ou CC (ID OU CADEIA CARACTERES)");
                errosSint.add(es);
            }
        }
    }

    public void vetorOuMatriz() {
        System.out.println("controller.AnalisadorSintController.vetorOuMatriz()");
        if (tokens.get(posicaoLista).getValue().equals("[")) {
            System.out.println("VM--Encontrou [");
            posicaoLista++;

            if (tokens.get(posicaoLista).getType().equals("NRO")) {
                System.out.println("VM--Encontrou numero");
                posicaoLista++;
                if (tokens.get(posicaoLista).getValue().equals("]")) {
                    System.out.println("VM--Encontrou ]");
                    posicaoLista++;
                    switch (tokens.get(posicaoLista).getValue()) {
                        case "[":
                            System.out.println("VM--Encontrou [ MATRIZ");
                            posicaoLista++;
                            if (tokens.get(posicaoLista).getType().equals("NRO")) {
                                System.out.println("VM--Encontrou numero ... MATRIZ");
                                posicaoLista++;
                                if (tokens.get(posicaoLista).getValue().equals("]")) {
                                    System.out.println("VM--Encontrou ] ... MATRIZ");
                                    posicaoLista++;
                                    switch (tokens.get(posicaoLista).getValue()) {
                                        case ")":
                                            System.out.println("\nVM--Encontrou fecha Parenteses MATRIZ");
                                            posicaoLista++;
                                            break;
                                        case ",":
                                            System.out.println("VM--Encontrou virgula MATRIZ");
                                            posicaoLista++;
                                            break;
                                        default:
                                            System.out.printf("VM-------Esperava ) ... MATRIZ: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                                            posicaoLista++;
                                            ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta delimitador: ) (FECHA PARENTESES)");
                                            errosSint.add(es);
                                            break;
                                    }
                                } else {
                                    System.out.printf("VM-------Esperava ] ... MATRIZ: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                                    posicaoLista++;
                                    ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta delimitador: ] (FECHA COLCHETES)");
                                    errosSint.add(es);
                                }
                            } else {
                                System.out.printf("VM-------Esperava numero MATRIZ ... erro sintatico: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                                posicaoLista++;
                                ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta numero");
                                errosSint.add(es);
                            }   break;
                        case ",":
                            System.out.println("VM--Encontrou virgula");
                            posicaoLista++;
                            break;
                        case ")":
                            System.out.println("\nVM--Encontrou fecha Parenteses VETOR");
                            posicaoLista++;
                            break;
                        default:
                            System.out.printf("VM-------Esperava [ ... MATRIZ: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                            posicaoLista++;
                            ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta delimitador: [ (ABRE COLCHETES)");
                            errosSint.add(es);
                            break;
                    }

                } else {
                    System.out.printf("VM-------Esperava ] ... MATRIZ: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                    posicaoLista++;
                    ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta delimitador: ] (FECHA COLCHETES)");
                    errosSint.add(es);
                }

            } else {
                System.out.printf("-------Esperava numero ... erro sintatico: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
                posicaoLista++;
                ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta numero");
                errosSint.add(es);
            }
        } else {
            System.out.printf("VM-------Esperava [ ... MATRIZ: Valor: %s Tipo: %s Line: %s Column: %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getLine(), tokens.get(posicaoLista).getPosition());
            posicaoLista++;
            ErroSintatico es = new ErroSintatico(tokens.get(posicaoLista).getLine(), "Falta delimitador: [ (ABRE COLCHETES)");
            errosSint.add(es);
        }

    }

    public void booleanToken() {
        System.out.println("\n-------Boolean-------\n");

        while (tokens.get(posicaoLista) != null) {
            if (tokens.get(posicaoLista).getType().equals("Boolean")) {
                System.out.println("Encontrou id");
                posicaoLista++;
                if (tokens.get(posicaoLista).getValue().equals(",")) {
                    System.out.println("Encontrou virgula");
                    posicaoLista++;

                } else if (tokens.get(posicaoLista).getValue().equals("[")) {

                } else if (tokens.get(posicaoLista).getValue().equals(")")) {
                    System.out.println("\nEncontrou fecha Parenteses");
                    return;
                } else {
                    System.out.printf("Token inesperado: %s %s\n", tokens.get(posicaoLista).getValue(), tokens.get(posicaoLista).getType());
                    posicaoLista++;
                }

            } else {
                System.out.printf("-------Esperava acesso a Identificador ---- %s %s\n", tokens.get(posicaoLista).getType(), tokens.get(posicaoLista).getValue());
                posicaoLista++;
            }
        }
    }

    public ArrayList<ErroSintatico> getErrosSint() {
        return errosSint;
    }
}
