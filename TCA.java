import java.util.Scanner;

public class TCA {
    final static Scanner LER = new Scanner(System.in);

    public static int lerValorInteiro(){
        return  LER.nextInt();
    }
    
    public static float lerFloat(){
        return LER.nextFloat();
    }

    public static void main(String[] args) {
        int[][] matriz = new int[25][80];
        for(int linha = 0;linha < matriz.length;linha++){
            for(int coluna = 0;coluna < matriz[0].length;coluna++){
                
                if(linha == matriz.length-1 || linha == 0||coluna == matriz[0].length-1 || coluna == 0){
                    System.out.print("#");
                }else{
                    System.out.print(" ");
                }

             
            }
            System.out.printf("\n");
            

        }
        
    }
}
