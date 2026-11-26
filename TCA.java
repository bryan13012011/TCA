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

    // lê um número inteiro digitado
    public static int lerValorInteiro() {
        return LER.nextInt();
    }

    // lê um número decimal
    public static float lerFloat() {
        return LER.nextFloat();
    }

    // lê um caractere digitado
    public static char lerValorCaracter() {
        char letra = LER.next().charAt(0);
        return letra;
    }

    // cria uma fruta em uma posição aleatória do mapa
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

    // cria várias frutas no mapa
    public static void gerarFrutas(char[][] matriz, int quantidade) {
        for (int i = 0; i < quantidade; i++) {

            // chama a função que cria uma fruta
            gerarFruta(matriz);
        }
    }

    // cria um bônus em uma posição aleatória do mapa
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

    // cria vários bônus no mapa
    public static void gerarBufers(char[][] matriz, int quantidade) {
        for (int i = 0; i < quantidade; i++) {

            // chama a função que cria um bônus
            gerarBufer(matriz);
        }
    }

    // desenha a cobra na matriz usando a cabeça e o corpo
    public static void desenharCobra(char[][] matriz, int[] cobraLinha, int[] cobraColuna, int tamanho) {
        for (int posicao = 0; posicao < tamanho; posicao++) {

            if (posicao == 0) {
                matriz[cobraLinha[posicao]][cobraColuna[posicao]] = CABECA;
            } else {
                matriz[cobraLinha[posicao]][cobraColuna[posicao]] = CORPO;
            }
        }
    }

    // cria uma barreira em uma posição aleatória do mapa
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

    // cria várias barreiras no mapa
    public static void gerarBarreiras(char[][] matriz, int quantidade) {
        for (int i = 0; i < quantidade; i++) {

            // chama a função que cria uma barreira
            gerarBarreira(matriz);
        }
    }

    // atualiza um caractere em uma posição específica da tela
    public static void atualizarTela(int linha, int coluna, char caractere) {
        System.out.print("\033[" + (linha + 11) + ";" + (coluna + 1) + "H");
        System.out.print(caractere);
        System.out.flush();
    }

    // mostra a pontuação atual do jogador
    public static void mostrarPontuacao(int pontuacao) {
        System.out.print("\033[10;1H");
        System.out.print("  Pontuação: " + pontuacao);
        System.out.flush();
    }

    // inicia o jogo e controla tudo que acontece durante a partida
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

        // chama a função para desenhar a cobra no mapa
        desenharCobra(matriz, cobraLinha, cobraColuna, tamanho);

        // chama a função para criar quatro frutas
        gerarFrutas(matriz, 4);

        // chama a função para criar três bônus
        gerarBufers(matriz, 3);

        // chama a função para criar sete barreiras
        gerarBarreiras(matriz, 7);

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.print(matriz[i][j]);
            }
            System.out.println();
        }

        // chama a função para mostrar a pontuação inicial
        mostrarPontuacao(pontuacao);

        boolean buferAtivo = false;

        while (true) {

            // verifica se alguma tecla foi pressionada
            if (pressionouTecla()) {

                // pega a tecla que foi pressionada
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

            // atualiza a tela para apagar a posição antiga da cauda
            atualizarTela(linhaCauda, colunaCauda, VAZIO);

            matriz[linhaCabeca][colunaCabeca] = CORPO;

            // atualiza a tela para mostrar o corpo no lugar da cabeça antiga
            atualizarTela(linhaCabeca, colunaCabeca, CORPO);

            for (int i = tamanho - 1; i > 0; i--) {
                cobraLinha[i] = cobraLinha[i - 1];
                cobraColuna[i] = cobraColuna[i - 1];
            }

            cobraLinha[0] += direcaoLinha;
            cobraColuna[0] += direcaoColuna;

            if ((matriz[cobraLinha[0]][cobraColuna[0]] == '#' || matriz[cobraLinha[0]][cobraColuna[0]] == CORPO || matriz[cobraLinha[0]][cobraColuna[0]] == '|')) {

                System.out.print("\033[2J\033[H");

                System.out.printf(" ███   ███  █   █ █████     ███  █   █ █████ ████    \n" +
                        "█ ░░░ █ ░░█ ██ ██░█░░░░░   █ ░░█ █░  █░█░░░░░█░░░█   \n" +
                        "█░ ██░█████░█░█ █░████░░░  █░ ░█░█░░ █░████░░████░░  \n" +
                        "█░░ █░█░░░█░█░░░█░█░░░░    █░░ █░░█░█ ░█░░░░ █░░█░ ░ \n" +
                        " ███ ░█░░░█░█░░ █░█████░    ███ ░░ █ ░ █████░█░░░█░  \n" +
                        "  ░░░ ░░░  ░░░░  ░░░░░░░     ░░░ ░  ░ ░ ░░░░░ ░░  ░  \n" +
                        "   ░░░  ░   ░ ░   ░ ░░░░░     ░░░    ░   ░░░░░ ░   ░ - Sua pontuaçao = %d ",
                        pontuacao);

                System.out.println("\n");

                // chama a função para esperar o jogador apertar uma tecla
                esperarTecla();
                return;
            }

            if (matriz[cobraLinha[0]][cobraColuna[0]] == '*') {
                tamanho++;
                buferAtivo = false;

                cobraLinha[tamanho - 1] = linhaCauda;
                cobraColuna[tamanho - 1] = colunaCauda;

                matriz[linhaCauda][colunaCauda] = CORPO;

                // atualiza a tela para mostrar a cauda que foi mantida
                atualizarTela(linhaCauda, colunaCauda, CORPO);

                pontuacao += 100;

                // atualiza a pontuação na tela
                mostrarPontuacao(pontuacao);

                // chama a função para criar outra fruta
                int[] fruta = gerarFruta(matriz);

                // mostra a nova fruta na tela
                atualizarTela(fruta[0], fruta[1], '*');
            }

            if (matriz[cobraLinha[0]][cobraColuna[0]] == '@') {
                buferAtivo = true;

                pontuacao += 200;

                // atualiza a pontuação depois de pegar o bônus
                mostrarPontuacao(pontuacao);

                tamanho++;

                cobraLinha[tamanho - 1] = linhaCauda;
                cobraColuna[tamanho - 1] = colunaCauda;

                // chama a função para criar outro bônus
                int[] bufer = gerarBufer(matriz);

                // mostra o novo bônus na tela
                atualizarTela(bufer[0], bufer[1], '@');
            }

            matriz[cobraLinha[0]][cobraColuna[0]] = CABECA;

            // atualiza a tela para mostrar a nova posição da cabeça
            atualizarTela(cobraLinha[0], cobraColuna[0], CABECA);

            int velocidade = velocidadeJogo;

            if (buferAtivo) {
                velocidade = velocidadeJogo / 2;
            }

            for (int i = 0; i < velocidade; i++) {
            }
        }
    }

    // conecta o java com as funções do teclado do windows
    public interface Crt extends Library {
        Crt INSTANCE = Native.load("msvcrt", Crt.class);

        int _kbhit();
        int _getch();
    }

    static boolean windows = System.getProperty("os.name").toLowerCase().contains("win");
    static boolean tecladoPreparado = false;

    // verifica se alguma tecla foi pressionada sem esperar
    public static boolean pressionouTecla() {
        if (windows) {
            return Crt.INSTANCE._kbhit() != 0;
        }

        // prepara o teclado caso esteja usando mac ou linux
        prepararTecladoMacLinux();

        try {
            return System.in.available() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // pega o código da tecla que foi pressionada
    public static int obtemTeclaPressionada() {
        if (windows) {
            return Crt.INSTANCE._getch();
        }

        // prepara o teclado caso esteja usando mac ou linux
        prepararTecladoMacLinux();

        try {
            return System.in.read();
        } catch (Exception e) {
            return -1;
        }
    }

    // mostra uma mensagem e espera o jogador apertar uma tecla
    public static void esperarTecla() {
        System.out.println("Pressione uma tecla para continuar...");

        // chama a função para pegar a tecla apertada
        obtemTeclaPressionada();
    }

    // prepara o teclado do mac ou linux para funcionar sem precisar apertar enter
    static void prepararTecladoMacLinux() {
        if (tecladoPreparado) {
            return;
        }

        tecladoPreparado = true;

        // configura o terminal para receber as teclas diretamente
        executarStty("-icanon -echo -icrnl min 1");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> executarStty("sane")));
    }

    // executa os comandos necessários para configurar o terminal
    static void executarStty(String opcoes) {
        try {
            new ProcessBuilder("sh", "-c", "stty " + opcoes + " < /dev/tty").start().waitFor();
        } catch (Exception e) {
        }
    }
}