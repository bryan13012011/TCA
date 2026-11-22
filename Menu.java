import com.sun.jna.Library;
import com.sun.jna.Native;

public class Menu {

    public interface Crt extends Library {
        Crt INSTANCE = Native.load("msvcrt", Crt.class);

        int _kbhit();
        int _getch();
    }

    static boolean windows = System.getProperty("os.name")
            .toLowerCase().contains("win");

    static boolean tecladoPreparado = false;

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

    static void executarStty(String opcoes) {
        try {
            new ProcessBuilder(
                "sh", "-c", "stty " + opcoes + " < /dev/tty"
            ).start().waitFor();
        } catch (Exception e) {
        }
    }

    public static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void atualizarMenu(int opcaoSelecionada, String[] opcoes) {

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
        System.out.println("       W,A,S,D para navegar");
        System.out.println("       ENTER para selecionar");
        System.out.println("       ESC para sair");
    }

    public static void executarAcaoMenu(int opcaoSelecionada) {

        limparConsole();

        if (opcaoSelecionada == 0) {

            System.out.println("Dificuldade FACIL selecionada!");
            TCA.iniciarJogo(400000000);

        } else if (opcaoSelecionada == 1) {

            System.out.println("Dificuldade MEDIA selecionada!");
            TCA.iniciarJogo(300000000);

        } else if (opcaoSelecionada == 2) {

            System.out.println("Dificuldade DIFICIL selecionada!");
            TCA.iniciarJogo(200000000);

        } else if (opcaoSelecionada == 3) {

            System.exit(0);
        }
    }

    public static void main(String[] args) {

        String[] opcoes = {
            "FACIL",
            "MEDIO",
            "DIFICIL",
            "SAIR"
        };

        int opcaoSelecionada = 0;

        atualizarMenu(opcaoSelecionada, opcoes);

        while (true) {

            if (pressionouTecla()) {

                int tecla = obtemTeclaPressionada();

                if (tecla == 'w' || tecla == 'W') {

                    opcaoSelecionada--;

                    if (opcaoSelecionada < 0) {
                        opcaoSelecionada = opcoes.length - 1;
                    }

                    atualizarMenu(opcaoSelecionada, opcoes);

                } else if (tecla == 's' || tecla == 'S') {

                    opcaoSelecionada++;

                    if (opcaoSelecionada >= opcoes.length) {
                        opcaoSelecionada = 0;
                    }

                    atualizarMenu(opcaoSelecionada, opcoes);

                } else if (tecla == 13) {

                    executarAcaoMenu(opcaoSelecionada);
                    atualizarMenu(opcaoSelecionada, opcoes);

                } else if (tecla == 27) {

                    System.out.print("\033[?25h");
                    return;
                }
            }
        }
    }
}

   