import com.sun.jna.Library;
import com.sun.jna.Native;
import java.util.*;

public class TCA {
    final static Scanner LER = new Scanner(System.in);

    final static Random FRUTINHAS = new Random();

    final static char VAZIO = ' ';
    final static char CABECA = 'Ø';
    final static char CORPO = 'o';
    static int direcaoLinha = 0;
    static int direcaoColuna = 1;
    

    public static int lerValorInteiro() {
        return LER.nextInt();
    }

    public static float lerFloat() {
        return LER.nextFloat();

    }

    public static char lerValorCaracter() {
        char letra = LER.next().charAt(0);
        return letra;
    }

    public static int[] gerarFruta(char[][] matriz) {
        int linha = FRUTINHAS.nextInt(matriz.length - 2) + 1;
        int coluna = FRUTINHAS.nextInt(matriz[0].length - 2) + 1;

        while (matriz[linha][coluna] != VAZIO) {
            linha = FRUTINHAS.nextInt(matriz.length - 2) + 1;
            coluna = FRUTINHAS.nextInt(matriz[0].length - 2) + 1;
        }

        matriz[linha][coluna] = '*';
        return new int[] { linha, coluna };
    }

    public static void gerarFrutas(char[][] matriz, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            gerarFruta(matriz);
        }
    }

    public static int[] gerarBufer(char[][] matriz) {
        int linha = FRUTINHAS.nextInt(matriz.length - 2) + 1;
        int coluna = FRUTINHAS.nextInt(matriz[0].length - 2) + 1;

        while (matriz[linha][coluna] != VAZIO) {
            linha = FRUTINHAS.nextInt(matriz.length - 2) + 1;
            coluna = FRUTINHAS.nextInt(matriz[0].length - 2) + 1;
        }

        matriz[linha][coluna] = '@';
        return new int[] { linha, coluna };
    }

    public static void gerarBufers(char[][] matriz, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            gerarBufer(matriz);
        }
    }

    public static void desenharCobra(char[][] matriz, int[] cobraLinha, int[] cobraColuna, int tamanho) {
        for (int posicao = 0; posicao < tamanho; posicao++) {

            if (posicao == 0) {
                matriz[cobraLinha[posicao]][cobraColuna[posicao]] = CABECA;
            } else {
                matriz[cobraLinha[posicao]][cobraColuna[posicao]] = CORPO;
            }
        }

    }

    public static int[] gerarBarreira(char[][] matriz) {
        int linha = FRUTINHAS.nextInt(matriz.length - 2) + 1;
        int coluna = FRUTINHAS.nextInt(matriz[0].length - 2) + 1;

        while (matriz[linha][coluna] != VAZIO) {
            linha = FRUTINHAS.nextInt(matriz.length - 2) + 1;
            coluna = FRUTINHAS.nextInt(matriz[0].length - 2) + 1;
        }

        matriz[linha][coluna] = '|';
        return new int[] { linha, coluna };
    }

    public static void gerarBarreiras(char[][] matriz, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            gerarBarreira(matriz);
        }
    }

    public static void atualizarTela(int linha, int coluna, char caractere) {
        System.out.print("\033[" + (linha + 12) + ";" + (coluna + 1) + "H");
        System.out.print(caractere);
        System.out.flush();
    }

    public static void mostrarPontuacao(int pontuacao) {
        System.out.print("\033[10;1H");
        System.out.print("  Pontuação: " + pontuacao);
        System.out.flush();
    }

    public static void iniciarJogo(int velocidadeJogo) {
        System.out.print("\033[?25l");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.print("\033[?25h")));

        direcaoLinha = 0;
        direcaoColuna = 1;
        System.out.print("\033[2J\033[H");
        char[][] matriz = new char[20][60];

        System.out.print("""

                   █   ███   ███   ███     ████   ███      ███   ███  ████  ████  ███ █   █ █   █  ███
                   █░░█ ░░█ █ ░░░ █ ░░█    █░░░█ █ ░░█    █ ░░░ █ ░░█ █░░░█ █░░░█  █░░██  █░█░  █░█ ░░█
                   █░░█░ ░█░█░ ██░█░ ░█░   █░░░█░█████░   █░ ░░░█░ ░█░████░░████░░ █░░█░█ █░█████░█████░
                █  █░░█░░ █░█░░ █░█░░ █░░  █░░ █░█░░░█░░  █░░   █░░ █░█░░░█ █░░█░ ░█░░█░░██░█░░░█░█░░░█░░
                 ██ ░░ ███ ░░███ ░░███ ░░  ████ ░█░░░█░░   ███   ███ ░████░░█░░░█░███░█░░ █░█░░░█░█░░░█░░
                  ░░ ░  ░░░ ░ ░░░ ░ ░░░ ░   ░░░░ ░░░  ░░    ░░░   ░░░ ░░░░░ ░░░  ░ ░░░ ░░  ░░░░  ░░░░  ░░
                   ░░    ░░░   ░░░   ░░░     ░░░░  ░   ░     ░░░   ░░░  ░░░░  ░   ░ ░░░ ░   ░ ░   ░ ░   ░
                   """);
        System.out.println("  Mova a cobrinha com W, A, S, D. ESC para sair.");

        for (int linha = 0; linha < matriz.length; linha++) {
            for (int coluna = 0; coluna < matriz[0].length; coluna++) {

                if (linha == matriz.length - 1 || linha == 0 || coluna == matriz[0].length - 1 || coluna == 0) {
                    matriz[linha][coluna] = '#';
                } else {
                    matriz[linha][coluna] = VAZIO;
                }

            }
        }
        System.out.printf("\n\n");

        int tamanho = 3;
        int pontuacao = 0;

        int[] cobraLinha = new int[(matriz.length - 2) * (matriz[0].length - 2)];
        int[] cobraColuna = new int[(matriz.length - 2) * (matriz[0].length - 2)];

        int posicao = 0;
        while (posicao < tamanho) {
            cobraLinha[posicao] = matriz.length / 2;
            cobraColuna[posicao] = matriz[0].length / 2 - posicao;
            posicao++;
        }

        desenharCobra(matriz, cobraLinha, cobraColuna, tamanho);

        gerarFrutas(matriz, 4);
        gerarBufers(matriz, 3);
        gerarBarreiras(matriz, 7);

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.print(matriz[i][j]);
            }
            System.out.println();
        }
        mostrarPontuacao(pontuacao);

        boolean buferAtivo = false;

        while (true) {

            if (pressionouTecla()) {
                int tecla = obtemTeclaPressionada();

                switch (tecla) {
                    case 27:
                        System.out.print("\033[38;1H");
                        System.out.println("Jogo encerrado - Pontuação: " + pontuacao);
                        return;
                    case 'w':
                    case 'W':
                        if (direcaoLinha != 1) {
                            direcaoLinha = -1;
                            direcaoColuna = 0;
                        }
                        break;
                    case 's':
                    case 'S':
                        if (direcaoLinha != -1) {
                            direcaoLinha = 1;
                            direcaoColuna = 0;
                        }
                        break;
                    case 'a':
                    case 'A':
                        if (direcaoColuna != 1) {
                            direcaoLinha = 0;
                            direcaoColuna = -1;
                        }
                        break;
                    case 'd':
                    case 'D':
                        if (direcaoColuna != -1) {
                            direcaoLinha = 0;
                            direcaoColuna = 1;
                        }
                        break;
                }
            }

            int linhaCauda = cobraLinha[tamanho - 1];
            int colunaCauda = cobraColuna[tamanho - 1];

            int linhaCabeca = cobraLinha[0];
            int colunaCabeca = cobraColuna[0];

            matriz[linhaCauda][colunaCauda] = VAZIO;
            atualizarTela(linhaCauda, colunaCauda, VAZIO);

            matriz[linhaCabeca][colunaCabeca] = CORPO;
            atualizarTela(linhaCabeca, colunaCabeca, CORPO);

            for (int i = tamanho - 1; i > 0; i--) {
                cobraLinha[i] = cobraLinha[i - 1];
                cobraColuna[i] = cobraColuna[i - 1];
            }

            cobraLinha[0] += direcaoLinha;
            cobraColuna[0] += direcaoColuna;

            if ((matriz[cobraLinha[0]][cobraColuna[0]] == '#' || matriz[cobraLinha[0]][cobraColuna[0]] == CORPO
                    || matriz[cobraLinha[0]][cobraColuna[0]] == '|')) {

                System.out.print("\033[2J\033[H");

                 System.out.printf(" ███   ███  █   █ █████     ███  █   █ █████ ████    \n" + //
                        "█ ░░░ █ ░░█ ██ ██░█░░░░░   █ ░░█ █░  █░█░░░░░█░░░█   \n" + //
                        "█░ ██░█████░█░█ █░████░░░  █░ ░█░█░░ █░████░░████░░  \n" + //
                        "█░░ █░█░░░█░█░░░█░█░░░░    █░░ █░░█░█ ░█░░░░ █░░█░ ░ \n" + //
                        " ███ ░█░░░█░█░░ █░█████░    ███ ░░ █ ░ █████░█░░░█░  \n" + //
                        "  ░░░ ░░░  ░░░░  ░░░░░░░     ░░░ ░  ░ ░ ░░░░░ ░░  ░  \n" + //
                        "   ░░░  ░   ░ ░   ░ ░░░░░     ░░░    ░   ░░░░░ ░   ░ - Sua pontuaçao = %d ",
                        pontuacao);
               
                        
                System.out.println("\n");

                esperarTecla();
                return;
            }
            if (matriz[cobraLinha[0]][cobraColuna[0]] == '*') {
                tamanho++;
                buferAtivo = false;
                cobraLinha[tamanho - 1] = linhaCauda;
                cobraColuna[tamanho - 1] = colunaCauda;
                matriz[linhaCauda][colunaCauda] = CORPO;
                atualizarTela(linhaCauda, colunaCauda, CORPO);

                pontuacao += 100;
                mostrarPontuacao(pontuacao);

                int[] fruta = gerarFruta(matriz);

                atualizarTela(fruta[0], fruta[1], '*');

            }

            if (matriz[cobraLinha[0]][cobraColuna[0]] == '@') {
                buferAtivo = true;

                pontuacao += 200;
                mostrarPontuacao(pontuacao);
                tamanho ++;

                int[] bufer = gerarBufer(matriz);
                atualizarTela(bufer[0], bufer[1], '@');
            }

            matriz[cobraLinha[0]][cobraColuna[0]] = CABECA;
            atualizarTela(cobraLinha[0], cobraColuna[0], CABECA);

            int velocidade = velocidadeJogo;

            if (buferAtivo) {
                velocidade = velocidadeJogo / 2;
            }
            for (int i = 0; i < velocidade; i++) {
            }
        }
    }

    // Liga o Java às funções _kbhit e _getch do Windows (só usada no Windows)
    public interface Crt extends Library {
        Crt INSTANCE = Native.load("msvcrt", Crt.class);

        int _kbhit();

        int _getch();
    }

    static boolean windows = System.getProperty("os.name").toLowerCase().contains("win");
    static boolean tecladoPreparado = false;

    // Retorna true se alguma tecla foi pressionada. NÃO espera: se nenhuma
    // tecla foi apertada, retorna false na hora e o jogo continua.
    public static boolean pressionouTecla() {
        if (windows) {
            return Crt.INSTANCE._kbhit() != 0;
        }
        prepararTecladoMacLinux();
        try {
            return System.in.available() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Retorna o código ASCII da tecla pressionada (ex.: 'a', 'w', 27 = ESC).
    // Se nenhuma tecla foi apertada, ESPERA até o usuário apertar uma.
    public static int obtemTeclaPressionada() {
        if (windows) {
            return Crt.INSTANCE._getch();
        }
        prepararTecladoMacLinux();
        try {
            return System.in.read();
        } catch (Exception e) {
            return -1;
        }
    }

    // Mostra "Pressione uma tecla para continuar..." e PARA o programa
    // até o jogador apertar qualquer tecla.
    //
    // Exemplo:
    // System.out.println("GAME OVER");
    // esperarTecla();
    public static void esperarTecla() {
        System.out.println("Pressione uma tecla para continuar...");
        obtemTeclaPressionada(); // fica esperando a tecla
    }

    // Mac/Linux: faz o terminal entregar cada tecla na hora (sem ENTER) e
    // sem mostrar a letra na tela. Quando o programa termina, volta ao normal.
    // Você não precisa chamar: as funções acima chamam sozinhas.
    static void prepararTecladoMacLinux() {
        if (tecladoPreparado) {
            return;
        }
        tecladoPreparado = true;
        executarStty("-icanon -echo -icrnl min 1"); // -icrnl: ENTER vira 13, igual ao Windows
        Runtime.getRuntime().addShutdownHook(new Thread(() -> executarStty("sane")));
    }

    static void executarStty(String opcoes) {
        try {
            new ProcessBuilder("sh", "-c", "stty " + opcoes + " < /dev/tty").start().waitFor();
        } catch (Exception e) {
            // se não conseguir, o teclado só vai funcionar com ENTER
        }
    }
}