import com.sun.jna.Library;
import com.sun.jna.Native;

public class Menu {

    // conecta o java com as funções do teclado do windows
    public interface Crt extends Library {
        Crt INSTANCE = Native.load("msvcrt", Crt.class);

        int _kbhit();
        int _getch();
    }

    static boolean windows = System.getProperty("os.name")
            .toLowerCase().contains("win");

    static boolean tecladoPreparado = false;

    // verifica se alguma tecla foi pressionada sem esperar
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

    // pega a tecla que foi pressionada
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

    // prepara o teclado para funcionar sem precisar apertar enter
    static void prepararTecladoMacLinux() {
        if (tecladoPreparado) {
            return;
        }

        tecladoPreparado = true;
        executarStty("-icanon -echo -icrnl min 1");

        Runtime.getRuntime().addShutdownHook(
            new Thread(() -> executarStty("sane"))
        );
    }

    // executa os comandos para configurar o terminal
    static void executarStty(String opcoes) {
        try {
            new ProcessBuilder(
                "sh", "-c", "stty " + opcoes + " < /dev/tty"
            ).start().waitFor();
        } catch (Exception e) {
        }
    }

    // limpa o console
    public static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // mostra o menu e destaca a opção selecionada
    public static void atualizarMenu(int opcaoSelecionada, String[] opcoes) {

        // chama a função para limpar a tela antes de mostrar o menu
        limparConsole();

        System.out.println();
        System.out.println();
        System.out.print("""

                   █   ███   ███   ███     ████   ███      ███   ███  ████  ████  ███ █   █ █   █  ███
                   █░░█ ░░█ █ ░░░ █ ░░█    █░░░█ █ ░░█    █ ░░░ █ ░░█ █░░░█ █░░░█  █░░██  █░█░  █░█ ░░█
                   █░░█░ ░█░█░ ██░█░ ░█░   █░░░█░█████░   █░ ░░░█░ ░█░████░░████░░ █░░█░█ █░█████░█████░
                █  █░░█░░ █░█░░ █░█░░ █░░  █░░ █░█░░░█░░  █░░   █░░ █░█░░░█ █░░█░ ░█░░█░░██░█░░░█░█░░░█░░
                 ██ ░░ ███ ░░███ ░░███ ░░  ████ ░█░░░█░░   ███   ███ ░████░░█░░░█░███░█░░ █░█░░░█░█░░░█░░
                  ░░ ░  ░░░ ░ ░░░ ░ ░░░ ░   ░░░░ ░░░  ░░    ░░░   ░░░ ░░░░░ ░░░  ░ ░░░ ░░  ░░░░  ░░░░  ░░
                   ░░    ░░░   ░░░   ░░░     ░░░░  ░   ░     ░░░   ░░░  ░░░░  ░   ░ ░░░ ░   ░ ░   ░ ░   ░
                   """);
        System.out.println();
        System.out.println("       ESCOLHA A DIFICULDADE");
        System.out.println();

        for (int i = 0; i < opcoes.length; i++) {

            if (i == opcaoSelecionada) {
                System.out.print("          > ");
                System.out.print("\033[32m");
                System.out.print(opcoes[i]);
                System.out.print("\033[0m");
                System.out.println();
            } else {
                System.out.println("            " + opcoes[i]);
            }
        }

        System.out.println();
        System.out.println("       W,S para navegar");
        System.out.println("       ENTER para selecionar");
        System.out.println("       ESC para sair");
    }

    // executa a ação de acordo com a dificuldade escolhida
    public static void executarAcaoMenu(int opcaoSelecionada

    ) {

        // limpa o console antes de iniciar a ação
        limparConsole();

        if (opcaoSelecionada == 0) {

            System.out.println("Dificuldade FACIL selecionada!");

            // chama o jogo com a velocidade da dificuldade fácil
            TCA.iniciarJogo(400000000);

        } else if (opcaoSelecionada == 1) {

            System.out.println("Dificuldade MEDIA selecionada!");

            // chama o jogo com a velocidade da dificuldade média
            TCA.iniciarJogo(300000000);

        } else if (opcaoSelecionada == 2) {

            System.out.println("Dificuldade DIFICIL selecionada!");

            // chama o jogo com a velocidade da dificuldade difícil
            TCA.iniciarJogo(200000000);

        } else if (opcaoSelecionada == 3) {

            System.exit(0);
        }
    }

    // inicia o programa e controla as opções do menu
    public static void main(String[] args) {

        String[] opcoes = {
            "FACIL",
            "MEDIO",
            "DIFICIL",
            "SAIR"
        };

        int opcaoSelecionada = 0;

        // chama a função para mostrar o menu pela primeira vez
        atualizarMenu(opcaoSelecionada, opcoes);

        while (true) {

            // chama a função para verificar se alguma tecla foi pressionada
            if (pressionouTecla()) {

                // chama a função para descobrir qual tecla foi apertada
                int tecla = obtemTeclaPressionada();

                if (tecla == 'w' || tecla == 'W') {

                    opcaoSelecionada--;

                    if (opcaoSelecionada < 0) {
                        opcaoSelecionada = opcoes.length - 1;
                    }

                    // atualiza o menu depois de mudar a opção
                    atualizarMenu(opcaoSelecionada, opcoes);

                } else if (tecla == 's' || tecla == 'S') {

                    opcaoSelecionada++;

                    if (opcaoSelecionada >= opcoes.length) {
                        opcaoSelecionada = 0;
                    }

                    // atualiza o menu depois de mudar a opção
                    atualizarMenu(opcaoSelecionada, opcoes);

                } else if (tecla == 13) {

                    // chama a função para executar a opção selecionada
                    executarAcaoMenu(opcaoSelecionada);

                    // mostra o menu novamente depois que o jogo termina
                    atualizarMenu(opcaoSelecionada, opcoes);

                } else if (tecla == 27) {

                    System.out.print("\033[?25h");
                    return;
                }
            }
        }
    }
}