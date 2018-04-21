/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author jvboa
 */
public class AnalisadorLexController {

    public void analisarCaracteres(String arquivoLido) {
        System.out.println("analisarCaracteres");
        String lexema = "";
        int iterador = 0;
        do {
            if (arquivoLido.charAt(iterador) == ' ') {
                System.out.println("ESPACO");
                iterador++;

            } else if (arquivoLido.charAt(iterador) == '\n') {
                System.out.println("BARRA N");
                iterador++;

            } else if (arquivoLido.charAt(iterador) == ';' || arquivoLido.charAt(iterador) == ',' || arquivoLido.charAt(iterador) == '(' || arquivoLido.charAt(iterador) == ')' || arquivoLido.charAt(iterador) == '[' || arquivoLido.charAt(iterador) == ']' || arquivoLido.charAt(iterador) == '{' || arquivoLido.charAt(iterador) == '}' || arquivoLido.charAt(iterador) == '.') {
                System.out.printf("DELIMITADOR:%c\n", arquivoLido.charAt(iterador));
                iterador++;

            } else if (arquivoLido.charAt(iterador) == '+' || arquivoLido.charAt(iterador) == '-' || arquivoLido.charAt(iterador) == '*' || arquivoLido.charAt(iterador) == '/') {

                System.out.printf("OP ARIT:%c\n", arquivoLido.charAt(iterador));
                lexema += arquivoLido.charAt(iterador);

                if (arquivoLido.charAt(iterador) == '+') {
                    iterador++;
                    if (arquivoLido.charAt(iterador) == '+') {
                        lexema += arquivoLido.charAt(iterador);
                        System.out.printf("DUPLO OP ARIT:%s\n", lexema);
                        lexema = "";
                        iterador++;
                    } else {
                        lexema = "";

                    }
                } else if (arquivoLido.charAt(iterador) == '-') {
                    iterador++;
                    if (arquivoLido.charAt(iterador) == '-') {
                        lexema += arquivoLido.charAt(iterador);
                        System.out.printf("DUPLO OP ARIT:%s\n", lexema);
                        lexema = "";
                        iterador++;

                    } else {
                        lexema = "";

                    }
                } else if (arquivoLido.charAt(iterador) == '/') {

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
                                lexema = "";
                                break;
                            }
                        }
                        iterador++;
                    } else if (arquivoLido.charAt(iterador) == '*') {
                        lexema += arquivoLido.charAt(iterador);
                        System.out.printf("COMENTARIO DE BLOCO:%s\n", lexema);

                        while (iterador < arquivoLido.length()) {
                            if (arquivoLido.charAt(iterador) == '*') {
                                iterador++;

                                if (arquivoLido.charAt(iterador) == '/') {

                                    lexema += arquivoLido.charAt(iterador - 1);
                                    lexema += arquivoLido.charAt(iterador);
                                    System.out.printf("FECHA COMENTARIO DE BLOCO:%s\n", lexema);
                                    lexema = "";
                                    break;
                                }
                            }
                            iterador++;

                        }
                        iterador++;
                    }
                } else {
                    lexema = "";
                    iterador++;
                }
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
                    lexema = "";
                } else if (identificadores(lexema)) {
                    System.out.printf("id:%s\n", lexema);
                    lexema = "";
                } else {
                    lexema = "";
                }
            } else if (Character.isDigit(arquivoLido.charAt(iterador))) {
                System.out.printf("\nPRIMEIRO NUMERO:%c\n", arquivoLido.charAt(iterador));
                lexema += arquivoLido.charAt(iterador);
                iterador++;
//                while (iterador < arquivoLido.length()) {
//                    System.out.println("WHILE NUM");
//                    if (Character.isDigit(arquivoLido.charAt(iterador))) {
//                        System.out.printf("NUM:%c\n", arquivoLido.charAt(iterador));
//                        lexema += arquivoLido.charAt(iterador);
//                        iterador++;
//
//                    } else {
//                        System.out.printf("ACHOU ALGO QUE NAO EH DIGITO:%c\n", arquivoLido.charAt(iterador));
//                        if (arquivoLido.charAt(iterador) == '.') {
//                            System.out.printf("PONTO:%c\n", arquivoLido.charAt(iterador));
//                            lexema += arquivoLido.charAt(iterador);
//                            iterador++;
//
//                        } else {
//                            iterador++;
//                            break;
//
//                        }
//                    }
//                }
                if (numero(lexema)) {
                    System.out.printf("numero:%s\n", lexema);
                    lexema = "";
                }
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

        return lexema.matches("[-]?[ ]*[0-9][0-9]*[[.]0-9[0-9]*]");
    }

    public boolean delimitadores(String lexema) {

        return lexema.matches(";|,");
    }

}
