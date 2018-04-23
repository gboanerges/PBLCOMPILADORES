/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Collection;
import model.ErroLex;

import model.Token;

/**
 *
 * @author jvboa
 */
public class AnalisadorLexController {

    private Collection<Token> tokenList = new ArrayList<Token>();
    private Collection<ErroLex> errosList = new ArrayList<ErroLex>();

    public void analisarCaracteres(String arquivoLido) {
        System.out.println("analisarCaracteres");
        String lexema = "";
        boolean fechaComentBloco = false;
        int iterador = 0;
        int linha = 0;
        do {
            if (arquivoLido.charAt(iterador) == ' ') {
                System.out.println("ESPACO");
                iterador++;

            } else if (arquivoLido.charAt(iterador) == '\n') {
                System.out.println("BARRA N");
                linha++;
                iterador++;

            } else if (Character.isAlphabetic(arquivoLido.charAt(iterador))) {
                System.out.printf("\nPRIMEIRA LETRA:%c\n", arquivoLido.charAt(iterador));
                lexema += arquivoLido.charAt(iterador);
                iterador++;
                while (iterador < arquivoLido.length()) {
                    System.out.println("WHILE LETRA");
                    if (Character.isAlphabetic(arquivoLido.charAt(iterador))) {
                        System.out.printf("LETRA:%c\n", arquivoLido.charAt(iterador));
                        lexema += arquivoLido.charAt(iterador);
                        iterador++;

                    } else if (Character.isDigit(arquivoLido.charAt(iterador))) {
                        System.out.printf("DIGITO:%c\n", arquivoLido.charAt(iterador));
                        lexema += arquivoLido.charAt(iterador);
                        iterador++;

                    } else if (arquivoLido.charAt(iterador) == '_') {
                        System.out.printf("_:%c\n", arquivoLido.charAt(iterador));
                        lexema += arquivoLido.charAt(iterador);
                        iterador++;

                    } else {
                        System.out.printf("ACHOU ALGO QUE NAO EH LETRA/DIGITO/_:%c\n", arquivoLido.charAt(iterador));

                        break;
                    }

                }
                System.out.printf("lexema after while:%s\n", lexema);
                if (palavrasReservadas(lexema)) {

                    System.out.printf("palavraRes:%s\n", lexema);
                    Token palavRes = new Token(lexema, "PRE", linha, iterador);
                    tokenList.add(palavRes);
                    lexema = "";
                } else if (identificadores(lexema)) {
                    System.out.printf("id:%s\n", lexema);
                    Token id = new Token(lexema, "IDE", linha, iterador);
                    tokenList.add(id);
                    lexema = "";
                } else {
                    lexema = "";
                }
            } else if (Character.isDigit(arquivoLido.charAt(iterador))) {
                System.out.printf("\nPRIMEIRO NUMERO:%c\n", arquivoLido.charAt(iterador));
                lexema += arquivoLido.charAt(iterador);
                iterador++;
                while (iterador < arquivoLido.length()) {
                    System.out.println("WHILE NUM");
                    if (Character.isDigit(arquivoLido.charAt(iterador))) {
                        System.out.printf("NUM:%c\n", arquivoLido.charAt(iterador));
                        lexema += arquivoLido.charAt(iterador);
                        iterador++;

                    } else if (arquivoLido.charAt(iterador) == '.') {
                        System.out.printf("PONTO:%c\n", arquivoLido.charAt(iterador));

                        if (Character.isDigit(arquivoLido.charAt(iterador + 1))) {
                            System.out.printf("NUM PONTO:%c\n", arquivoLido.charAt(iterador));
                            lexema += arquivoLido.charAt(iterador);
                            iterador++;

                        } else {
                            System.out.printf("NAO EH DIGITO DPS DO PONTO:%c\n", arquivoLido.charAt(iterador + 1));
                            break;
                        }
                    } else {
                        System.out.printf("ACHOU ALGO QUE NAO EH DIGITO:%c\n", arquivoLido.charAt(iterador));
                        break;
                    }
                }
                System.out.printf("lexema after while NUM:%s\n", lexema);
                if (numero(lexema)) {
                    System.out.printf("numero:%s\n", lexema);
                    Token num = new Token(lexema, "NRO", linha, iterador);
                    tokenList.add(num);
                    lexema = "";
                } else {
                    lexema = "";
                }
            } else if (arquivoLido.charAt(iterador) == '+' || arquivoLido.charAt(iterador) == '-' || arquivoLido.charAt(iterador) == '*' || arquivoLido.charAt(iterador) == '/') {

                System.out.printf("OP ARIT:%c\n", arquivoLido.charAt(iterador));
                lexema += arquivoLido.charAt(iterador);

                switch (arquivoLido.charAt(iterador)) {
                    case '+':
                        iterador++;
                        if (arquivoLido.charAt(iterador) == '+') {
                            lexema += arquivoLido.charAt(iterador);
                            System.out.printf("DUPLO OP ARIT:%s\n", lexema);
                            Token opArit = new Token(lexema, "ART", linha, iterador);
                            tokenList.add(opArit);
                            lexema = "";
                            iterador++;
                        } else {
                            Token opArit = new Token(lexema, "ART", linha, iterador);
                            tokenList.add(opArit);
                            lexema = "";

                        }
                        break;
                    case '-':
                        iterador++;
                        if (arquivoLido.charAt(iterador) == '-') {
                            lexema += arquivoLido.charAt(iterador);
                            System.out.printf("DUPLO OP ARIT:%s\n", lexema);
                            Token opArit = new Token(lexema, "ART", linha, iterador);
                            tokenList.add(opArit);
                            lexema = "";
                            iterador++;

                        } else if (arquivoLido.charAt(iterador) == ' ' || arquivoLido.charAt(iterador) == '\t' || arquivoLido.charAt(iterador) == '\n' || arquivoLido.charAt(iterador) == '\r') {
                            System.out.printf("111ESPACO CASE -:%c\n", arquivoLido.charAt(iterador));
                            if (arquivoLido.charAt(iterador) == '\n') {
                                linha++;
                            }
                            lexema += arquivoLido.charAt(iterador);
                            iterador++;
                            while (iterador < arquivoLido.length()) {
                                System.out.println("WHILE ESPACO CASE -");
                                if (arquivoLido.charAt(iterador) == ' ' || arquivoLido.charAt(iterador) == '\t' || arquivoLido.charAt(iterador) == '\n' || arquivoLido.charAt(iterador) == '\r') {
                                    System.out.printf("222ESPACO CASE -:%c\n", arquivoLido.charAt(iterador));
                                    if (arquivoLido.charAt(iterador) == '\n') {
                                        linha++;
                                    }
                                    lexema += arquivoLido.charAt(iterador);
                                    iterador++;
                                } else if (Character.isDigit(arquivoLido.charAt(iterador))) {
                                    System.out.printf("\nPRIMEIRO NUMERO CASE -:%c\n", arquivoLido.charAt(iterador));
                                    lexema += arquivoLido.charAt(iterador);
                                    iterador++;
                                    while (iterador < arquivoLido.length()) {
                                        System.out.println("WHILE NUM");
                                        if (Character.isDigit(arquivoLido.charAt(iterador))) {
                                            System.out.printf("NUM:%c\n", arquivoLido.charAt(iterador));
                                            lexema += arquivoLido.charAt(iterador);
                                            iterador++;

                                        } else if (arquivoLido.charAt(iterador) == '.') {
                                            System.out.printf("PONTO:%c\n", arquivoLido.charAt(iterador));

                                            if (Character.isDigit(arquivoLido.charAt(iterador + 1))) {
                                                System.out.printf("NUM PONTO:%c\n", arquivoLido.charAt(iterador));
                                                lexema += arquivoLido.charAt(iterador);
                                                iterador++;

                                            } else {
                                                System.out.printf("NAO EH DIGITO DPS DO PONTO:%c\n", arquivoLido.charAt(iterador + 1));
                                                break;
                                            }
                                        } else {
                                            System.out.printf("ACHOU ALGO QUE NAO EH DIGITO:%c\n", arquivoLido.charAt(iterador));
                                            break;
                                        }
                                    }
                                    System.out.printf("lexema after while NUM CASE -:%s\n", lexema);
                                    if (numero(lexema)) {
                                        System.out.printf("numero CASE -:%s\n", lexema);
                                        Token num = new Token(lexema, "NRO", linha, iterador);
                                        tokenList.add(num);
                                        lexema = "";
                                    } else {
                                        lexema = "";
                                    }
                                } else {
                                    lexema = "";
                                    break;
                                }
                            }

                        } else if (Character.isDigit(arquivoLido.charAt(iterador))) {
                            System.out.printf("\nPRIMEIRO NUMERO -:%c\n", arquivoLido.charAt(iterador));
                            lexema += arquivoLido.charAt(iterador);
                            iterador++;
                            while (iterador < arquivoLido.length()) {
                                System.out.println("WHILE NUM");
                                if (Character.isDigit(arquivoLido.charAt(iterador))) {
                                    System.out.printf("NUM:%c\n", arquivoLido.charAt(iterador));
                                    lexema += arquivoLido.charAt(iterador);
                                    iterador++;

                                } else if (arquivoLido.charAt(iterador) == '.') {
                                    System.out.printf("PONTO:%c\n", arquivoLido.charAt(iterador));

                                    if (Character.isDigit(arquivoLido.charAt(iterador + 1))) {
                                        System.out.printf("NUM PONTO:%c\n", arquivoLido.charAt(iterador));
                                        lexema += arquivoLido.charAt(iterador);
                                        iterador++;

                                    } else {
                                        System.out.printf("NAO EH DIGITO DPS DO PONTO:%c\n", arquivoLido.charAt(iterador + 1));
                                        break;
                                    }
                                } else {
                                    System.out.printf("ACHOU ALGO QUE NAO EH DIGITO:%c\n", arquivoLido.charAt(iterador));
                                    break;
                                }
                            }
                            System.out.printf("lexema after while NUM:%s\n", lexema);
                            if (numero(lexema)) {
                                System.out.printf("numero:%s\n", lexema);
                                Token num = new Token(lexema, "NRO", linha, iterador);
                                tokenList.add(num);
                                lexema = "";
                            } else {
                                lexema = "";
                            }
                        } else {
                            Token opArit = new Token(lexema, "ART", linha, iterador);
                            tokenList.add(opArit);
                            lexema = "";
                        }
                        break;
                    case '/':
                        iterador++;
                        if (arquivoLido.charAt(iterador) == '/') {
                            lexema += arquivoLido.charAt(iterador);
                            System.out.printf("COMENTARIO DE LINHA:%s\n", lexema);
                            lexema = "";
                            while (iterador < arquivoLido.length()) {
                                if (arquivoLido.charAt(iterador) != '\n') {
                                    iterador++;
                                } else {
                                    System.out.println("QUEBRA DE LINHA");
                                    linha++;
                                    lexema = "";
                                    break;
                                }
                            }
                            iterador++;
                        } else if (arquivoLido.charAt(iterador) == '*') {
                            lexema += arquivoLido.charAt(iterador);
                            System.out.printf("COMENTARIO DE BLOCO:%s\n", lexema);
                            fechaComentBloco = false;

                            iterador++;
                            while (iterador < arquivoLido.length()) {
                                if (arquivoLido.charAt(iterador) == '*') {
                                    System.out.println("ACHOU *");
                                    iterador++;

                                    if (arquivoLido.charAt(iterador) == '/') {

                                        lexema += arquivoLido.charAt(iterador - 1);
                                        lexema += arquivoLido.charAt(iterador);
                                        System.out.printf("FECHA COMENTARIO DE BLOCO:%s\n", lexema);
                                        fechaComentBloco = true;
                                        lexema = "";
                                        iterador++;
                                        break;
                                    } else {
                                        //System.out.println("COMENTARIO DE BLOCO NAO FECHADO");
                                        iterador++;

                                    }
                                } else {
                                    iterador++;
                                }
                            }
                            if (!fechaComentBloco) {
                                System.out.println("COMENTARIO DE BLOCO NAO FECHADO");

                            }

                        } else {
                            Token opArit = new Token(lexema, "ART", linha, iterador);
                            tokenList.add(opArit);
                            lexema = "";
                        }
                        break;
                    case '*':
                        Token opArit = new Token(lexema, "ART", linha, iterador);
                        tokenList.add(opArit);
                        lexema = "";
                        iterador++;
                        break;
                    default:
                        lexema = "";
                        iterador++;
                        break;
                }
            } else if (arquivoLido.charAt(iterador) == '!' || arquivoLido.charAt(iterador) == '=' || arquivoLido.charAt(iterador) == '<' || arquivoLido.charAt(iterador) == '>') {
                System.out.printf("OP RELACIONAL:%c\n", arquivoLido.charAt(iterador));
                lexema += arquivoLido.charAt(iterador);

                if (arquivoLido.charAt(iterador) == '!') {
                    iterador++;
                    if (arquivoLido.charAt(iterador) == '=') {
                        lexema += arquivoLido.charAt(iterador);
                        System.out.printf("OP RELACIONAL COMPOSTO:%s\n", lexema);
                        Token opRelacional = new Token(lexema, "REL", linha, iterador);
                        tokenList.add(opRelacional);
                        lexema = "";
                        iterador++;
                    } else {
                        Token opRelacional = new Token(lexema, "LOG", linha, iterador);
                        tokenList.add(opRelacional);
                        lexema = "";
                    }

                } else if (arquivoLido.charAt(iterador) == '=') {
                    iterador++;
                    if (arquivoLido.charAt(iterador) == '=') {
                        lexema += arquivoLido.charAt(iterador);
                        System.out.printf("OP RELACIONAL COMPOSTO:%s\n", lexema);
                        Token opRelacional = new Token(lexema, "REL", linha, iterador);
                        tokenList.add(opRelacional);
                        lexema = "";
                        iterador++;
                    } else {
                        Token opRelacional = new Token(lexema, "REL", linha, iterador);
                        tokenList.add(opRelacional);
                        lexema = "";
                    }
                } else if (arquivoLido.charAt(iterador) == '<') {
                    iterador++;
                    if (arquivoLido.charAt(iterador) == '=') {
                        lexema += arquivoLido.charAt(iterador);
                        System.out.printf("OP RELACIONAL COMPOSTO:%s\n", lexema);
                        Token opRelacional = new Token(lexema, "REL", linha, iterador);
                        tokenList.add(opRelacional);
                        lexema = "";
                        iterador++;
                    } else {
                        Token opRelacional = new Token(lexema, "REL", linha, iterador);
                        tokenList.add(opRelacional);
                        lexema = "";
                    }
                } else if (arquivoLido.charAt(iterador) == '>') {
                    iterador++;
                    if (arquivoLido.charAt(iterador) == '=') {
                        lexema += arquivoLido.charAt(iterador);
                        System.out.printf("OP RELACIONAL COMPOSTO:%s\n", lexema);
                        Token opRelacional = new Token(lexema, "REL", linha, iterador);
                        tokenList.add(opRelacional);
                        lexema = "";
                        iterador++;
                    } else {
                        Token opRelacional = new Token(lexema, "REL", linha, iterador);
                        tokenList.add(opRelacional);
                        lexema = "";
                    }
                } else {
                    lexema = "";
                }
            } // Verifica e-comercial
            else if (arquivoLido.charAt(iterador) == '&') {
                System.out.printf("E COMERCIAL:%c\n", arquivoLido.charAt(iterador));
                lexema += arquivoLido.charAt(iterador);
                iterador++;
                if (arquivoLido.charAt(iterador) == '&') {
                    lexema += arquivoLido.charAt(iterador);
                    System.out.printf("OP LOGICO:%s\n", lexema);
                    Token opRelacional = new Token(lexema, "LOG", linha, iterador);
                    tokenList.add(opRelacional);
                    lexema = "";
                    iterador++;
                } else {
                    System.out.println("OP LOG MAL FORMADO &&");
                    ErroLex opRelacional = new ErroLex(lexema, "LOG MF", linha, iterador);
                    errosList.add(opRelacional);
                    lexema = "";

                }
            } else if (arquivoLido.charAt(iterador) == '|') {
                System.out.printf("MODULO:%c\n", arquivoLido.charAt(iterador));
                lexema += arquivoLido.charAt(iterador);
                iterador++;
                if (arquivoLido.charAt(iterador) == '|') {
                    lexema += arquivoLido.charAt(iterador);
                    System.out.printf("OP LOGICO:%s\n", lexema);
                    Token opRelacional = new Token(lexema, "LOG", linha, iterador);
                    tokenList.add(opRelacional);
                    lexema = "";
                    iterador++;
                } else {
                    System.out.println("OP LOG MAL FORMADO |");
                    ErroLex opRelacional = new ErroLex(lexema, "LOG MF", linha, iterador);
                    errosList.add(opRelacional);
                    lexema = "";
                }
            } else if (arquivoLido.charAt(iterador) == ';' || arquivoLido.charAt(iterador) == ',' || arquivoLido.charAt(iterador) == '(' || arquivoLido.charAt(iterador) == ')' || arquivoLido.charAt(iterador) == '[' || arquivoLido.charAt(iterador) == ']' || arquivoLido.charAt(iterador) == '{' || arquivoLido.charAt(iterador) == '}' || arquivoLido.charAt(iterador) == '.') {
                System.out.printf("DELIMITADOR:%c\n", arquivoLido.charAt(iterador));
                lexema += arquivoLido.charAt(iterador);
                Token opRelacional = new Token(lexema, "DEL", linha, iterador);
                tokenList.add(opRelacional);
                lexema = "";
                iterador++;

            } else if (arquivoLido.charAt(iterador) == '"') {
                System.out.printf("INICIO CADEIA CARACTERES:%c\n", arquivoLido.charAt(iterador));
                lexema += arquivoLido.charAt(iterador);
                iterador++;
                while (iterador < arquivoLido.length()) {
                    System.out.println("WHILE CC");
                    if (arquivoLido.charAt(iterador) == '"') {
                        System.out.println("FECHA CADEIA CARACTERES");
                        lexema += arquivoLido.charAt(iterador);
                        iterador++;
                        break;
                    } else if (Character.isAlphabetic(arquivoLido.charAt(iterador))) {
                        System.out.printf("CC LETRA:%c\n", arquivoLido.charAt(iterador));
                        lexema += arquivoLido.charAt(iterador);
                        iterador++;
                    } else if (Character.isDigit(arquivoLido.charAt(iterador))) {
                        System.out.printf("CC DIGITO:%c\n", arquivoLido.charAt(iterador));
                        lexema += arquivoLido.charAt(iterador);
                        iterador++;
                    } else if (arquivoLido.charAt(iterador) == '\\') {
                        System.out.printf("CC \\:%c\n", arquivoLido.charAt(iterador));
                        lexema += arquivoLido.charAt(iterador);
                        iterador++;
                        if (arquivoLido.charAt(iterador) == '"') {
                            System.out.printf("CC \":%c\n", arquivoLido.charAt(iterador));
                            lexema += arquivoLido.charAt(iterador);
                            iterador++;
                        }
                    } else if (arquivoLido.charAt(iterador) == ' ' || arquivoLido.charAt(iterador) == '!' || arquivoLido.charAt(iterador) == '#' || arquivoLido.charAt(iterador) == '$' || arquivoLido.charAt(iterador) == '%' || arquivoLido.charAt(iterador) == '&' || arquivoLido.charAt(iterador) == '\'' || arquivoLido.charAt(iterador) == '(' || arquivoLido.charAt(iterador) == ')' || arquivoLido.charAt(iterador) == '*' || arquivoLido.charAt(iterador) == '+' || arquivoLido.charAt(iterador) == ',' || arquivoLido.charAt(iterador) == '-' || arquivoLido.charAt(iterador) == '.' || arquivoLido.charAt(iterador) == '/' || arquivoLido.charAt(iterador) == ':' || arquivoLido.charAt(iterador) == ';' || arquivoLido.charAt(iterador) == '<' || arquivoLido.charAt(iterador) == '=' || arquivoLido.charAt(iterador) == '>' || arquivoLido.charAt(iterador) == '?' || arquivoLido.charAt(iterador) == '@' || arquivoLido.charAt(iterador) == '[' || arquivoLido.charAt(iterador) == ']' || arquivoLido.charAt(iterador) == '^' || arquivoLido.charAt(iterador) == '_' || arquivoLido.charAt(iterador) == '`' || arquivoLido.charAt(iterador) == '{' || arquivoLido.charAt(iterador) == '|' || arquivoLido.charAt(iterador) == '}' || arquivoLido.charAt(iterador) == '~') {
                        System.out.printf("SIMBOLO CC:%c\n", arquivoLido.charAt(iterador));
                        lexema += arquivoLido.charAt(iterador);
                        iterador++;
                    } else {
                        System.out.printf("CC MAL FORMADO:%c\n", arquivoLido.charAt(iterador));
                        ErroLex opRelacional = new ErroLex(lexema, "CC MF", linha, iterador);
                        errosList.add(opRelacional);
                        lexema = "";
                        break;
                    }
                }
                System.out.printf("lexema after while CC:%s\n", lexema);
                if (cadeiaCaracteres(lexema)) {
                    System.out.printf("CADEIA DE CARACTERES:%s\n", lexema);
                    Token opRelacional = new Token(lexema, "CC", linha, iterador);
                    tokenList.add(opRelacional);
                    lexema = "";
                } else {
                    lexema = "";
                }
            } else if (arquivoLido.charAt(iterador) == '#' || arquivoLido.charAt(iterador) == '$' || arquivoLido.charAt(iterador) == '%' || arquivoLido.charAt(iterador) == '\'' || arquivoLido.charAt(iterador) == ',' || arquivoLido.charAt(iterador) == '.' || arquivoLido.charAt(iterador) == ':' || arquivoLido.charAt(iterador) == '?' || arquivoLido.charAt(iterador) == '@' || arquivoLido.charAt(iterador) == '^' || arquivoLido.charAt(iterador) == '_' || arquivoLido.charAt(iterador) == '`' || arquivoLido.charAt(iterador) == '~' || arquivoLido.charAt(iterador) == '\\') {
                System.out.printf("SIMBOLO INESPERADO:%c\n", arquivoLido.charAt(iterador));
                //lexema += arquivoLido.charAt(iterador);
                iterador++;
                ErroLex simbInesperado = new ErroLex(lexema, "SMB IN", linha, iterador);
                errosList.add(simbInesperado);
                lexema = "";
                
            } else {
                iterador++;
            }
        } while (iterador < arquivoLido.length());
    }

    public boolean palavrasReservadas(String lexema) {
        return lexema.matches("[bB][oO]{2}[lL]|[cC][oO][nN][sS][tT]|[eE][lL][sS][eE]|[eE][xX][tT][eE][nN][dD][sS]|[fF][aA][lL][sS][eE]|[fF][lL][oO][aA][tT]|[fF][uU][nN][cC][tT][iI][oO][nN]|[iI][fF]|[iI][nN][tT]|[pP][rR][iI][nN][tT]|[pP][rR][oO][cC][eE][dD][uU][rR][eE]|[rR][eE][tT][uU][rR][nN]|[sS][cC][aA][nN]|[sS][tT][aA][rR][tT]|[sS][tT][rR][iI][nN][gG]|[sS][tT][rR][uU][cC][tT]|[tT][hH][eE][nN]|[tT][rR][uU][eE]|[tT][yY][pP][eE][dD][eE][fF]|[vV][aA][rR]|[wW][hH][iI][lL][eE]");
    }

    public boolean identificadores(String lexema) {

        return lexema.matches("[a-zA-Z][[a-zA-Z][0-9][_]]*");
    }

    public boolean numero(String lexema) {
        String space = "[[ ][\t][\n][\r]]*";
        return lexema.matches("[-]?" + space + "[0-9][0-9]*(.[0-9][0-9]*)?");
    }

    public boolean cadeiaCaracteres(String lexema) {
        String simbolo = "[ -~]*[^\"]";
        //return lexema.matches("\"[[a-zA-Z][0-9]" + simbolo + "[\"]]*\"");
        return lexema.matches("\"[" + simbolo + "[\"]]*\"");

    }

    public boolean simbolos(String lexema) {
        String simbolo = "[ -~]*[^\"]";
        return lexema.matches(simbolo);
    }

    public Collection<ErroLex> getErrosLista() {
        return errosList;
    }

    public Collection<Token> getTokensLista() {
        return tokenList;
    }

}
