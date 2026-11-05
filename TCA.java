import java.util.*;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class TCA {
    final static Scanner LER = new Scanner(System.in);

    static Frutas[] fruta = new Frutas[4];

    final static Random FRUTINHAS = new Random();

    public static int lerValorInteiro(){
        return  LER.nextInt();
    }
    
    public static float lerFloat(){
        return LER.nextFloat();
    }

    public static void main(String[] args) {
        char[][] matriz = new char[25][80];
       
            System.out.print("""
                   
       ███  ███   ███   ███    ████   ███      ███   ███  ████  ████  ███ █   █ █   █  ███    
       █░░█ ░░█ █ ░░░ █ ░░█    █░░░█ █ ░░█    █ ░░░ █ ░░█ █░░░█ █░░░█  █░░██  █░█░  █░█ ░░█   
       █░░█░ ░█░█░ ██░█░ ░█░   █░░░█░█████░   █░ ░░░█░ ░█░████░░████░░ █░░█░█ █░█████░█████░  
    █  █░░█░░ █░█░░ █░█░░ █░░  █░░ █░█░░░█░░  █░░   █░░ █░█░░░█ █░░█░ ░█░░█░░██░█░░░█░█░░░█░░ 
     ██ ░░ ███ ░░███ ░░███ ░░  ████ ░█░░░█░░   ███   ███ ░████░░█░░░█░███░█░░ █░█░░░█░█░░░█░░ 
      ░░ ░  ░░░ ░ ░░░ ░ ░░░ ░   ░░░░ ░░░  ░░    ░░░   ░░░ ░░░░░ ░░░  ░ ░░░ ░░  ░░░░  ░░░░  ░░ 
       ░░    ░░░   ░░░   ░░░     ░░░░  ░   ░     ░░░   ░░░  ░░░░  ░   ░ ░░░ ░   ░ ░   ░ ░   ░ 
       """);
        System.out.printf(
        "Use W, A, S, D para jogar.");
        
        for(int linha = 0;linha < matriz.length;linha++){
            for(int coluna = 0;coluna < matriz[0].length;coluna++){
                
                if(linha == matriz.length-1 || linha == 0||coluna == matriz[0].length-1 || coluna == 0){
                    matriz[linha][coluna] = '#';
                    
                }else{
                    matriz[linha][coluna] = ' ';
                }

             
            }
            
        }
        System.out.printf("\n\n");
        
        boolean jogando = true;

        for(int i = 0; i < fruta.length;i++){
            fruta[i] = new Frutas();
            fruta[i].y = FRUTINHAS.nextInt(matriz.length-1);
            fruta[i].x = FRUTINHAS.nextInt(matriz[0].length-1);
            fruta[i].tipoFruta = '๏';

            matriz[fruta[i].y] [fruta[i].x] = fruta[i].tipoFruta;
            
        }
        for(int i = 0; i < matriz.length;i++){
            for(int j = 0; j < matriz[0].length;j++){
                
                System.out.print(matriz[i][j]);
                
            }
            System.out.println();
            
        }


        while(jogando){
            
            
            char jogada;


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
    //   System.out.println("GAME OVER");
    //   esperarTecla();
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



