import com.sun.jna.Library;
import com.sun.jna.Native;

import java.util.*;


public class TCA {
    final static Scanner LER = new Scanner(System.in);


    final static Random FRUTINHAS = new Random();


    final static char VAZIO = '.';
    final static char CABECA = 'O'; 
    final static char CORPO = 'o';  


    public static int lerValorInteiro(){
        return  LER.nextInt();
    }
    
    public static float lerFloat(){
        return LER.nextFloat();

    }
    public static char lerValorCaracter(){
		char letra = LER.next().charAt(0);
		return letra;
	}


    public static void gerarFruta(char[][] matriz) {
        int linha = FRUTINHAS.nextInt(matriz.length - 2) + 1;
        int coluna = FRUTINHAS.nextInt(matriz[0].length - 2) + 1;


        while (matriz[linha][coluna] != VAZIO) {
            linha = FRUTINHAS.nextInt(matriz.length - 2) + 1;
            coluna = FRUTINHAS.nextInt(matriz[0].length - 2) + 1;
        }


        matriz[linha][coluna] = '*';
    }


    public static void gerarFrutas(char[][] matriz, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            gerarFruta(matriz);
        }
    }




    public static void desenharCobra(char[][] matriz, int[] cobraLinha, int[] cobraColuna, int tamanho) {
        for (int i = 0; i < tamanho; i++) {
            if (i == 0) {
                matriz[cobraLinha[i]][cobraColuna[i]] = CABECA;
            } else {
                matriz[cobraLinha[i]][cobraColuna[i]] = CORPO;
            }
        }
    }


    public static void main(String[] args) {
        char[][] matriz = new char[25][50];
        
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
       
        
        for(int linha = 0;linha < matriz.length;linha++){
            for(int coluna = 0;coluna < matriz[0].length;coluna++){
                
                if(linha == matriz.length-1 || linha == 0 || coluna == matriz[0].length-1 || coluna == 0){
                    matriz[linha][coluna] = '#';
                }else{
                    matriz[linha][coluna] = VAZIO;
                }


            }
        }
        System.out.printf("\n\n");
        
        
        
        int tamanho = 3; 
        
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
        
        
        for(int i = 0; i < matriz.length;i++){
            for(int j = 0; j < matriz[0].length;j++){
                System.out.print(matriz[i][j]);
            }
            System.out.println();
        }
       
       
        int posX = cobraLinha[CABECA];
        int posY = cobraColuna[CORPO];


        while (true) {
            // Lógica para detectar o movimento
            if (pressionouTecla()) {
                int tecla = obtemTeclaPressionada();
                switch (tecla) {
                    case 'a':
                    case 'A':
                        posX--;
                        break;
                    case 'd':
                    case 'D':
                        posX++;
                        break;
                    case 'w':
                    case 'W':
                        posY--;
                        break;
                    case 's':
                    case 'S':
                        posY++;
                        break;
                    case 27: // Código ASCII para a tecla ESC
                        return;
                    default:
                        break; // Sai do loop e termina o programa
                }
            }

        }
    }  
// Liga o Java às funções _kbhit e _getch do Windows (só usada no Windows)
    public interface Crt extends Library {
        Crt INSTANCE = Native.load("msvcrt", Crt.class);
        int _kbhit();
        int _getch();
    }
    
    
    //static boolean windows = System.getProperty("os.name").toLowerCase().contains("win");
    //static boolean tecladoPreparado = false;
    
    
    // Retorna true se alguma tecla foi pressionada. NÃO espera: se nenhuma
    // tecla foi apertada, retorna false na hora e o jogo continua.
    public static boolean pressionouTecla() {
        //if (windows) {
            return Crt.INSTANCE._kbhit() != 0;
        //}
        //prepararTecladoMacLinux();
        // try {
        //     return System.in.available() > 0;
        // } catch (Exception e) {
        //     return false;
        // }
    }
    
    
    // Retorna o código ASCII da tecla pressionada (ex.: 'a', 'w', 27 = ESC).
    // Se nenhuma tecla foi apertada, ESPERA até o usuário apertar uma.
    public static int obtemTeclaPressionada() {
        //if (windows) {
            return Crt.INSTANCE._getch();
        //}
        //prepararTecladoMacLinux();
        // try {
        //     return System.in.read();
        // } catch (Exception e) {
        //     return -1;
        // }
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
    // static void prepararTecladoMacLinux() {
    //     if (tecladoPreparado) {
    //         return;
    //     }
    //     tecladoPreparado = true;
    //     executarStty("-icanon -echo -icrnl min 1"); // -icrnl: ENTER vira 13, igual ao Windows
    //     Runtime.getRuntime().addShutdownHook(new Thread(() -> executarStty("sane")));
    // }
    
    
    // static void executarStty(String opcoes) {
    //     try {
    //         new ProcessBuilder("sh", "-c", "stty " + opcoes + " < /dev/tty").start().waitFor();
    //     } catch (Exception e) {
    //         // se não conseguir, o teclado só vai funcionar com ENTER
    //     }
    // }

}



