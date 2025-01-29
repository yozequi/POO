 
package hola.mundo;
import java.util.Scanner;
// 1 Declaracion de clase 
public class HolaMundo {
     
    // 2. Metodo principal punto de entrada
    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Cual es tu nombre?: ");        
        String nombre = sc.nextLine();
        
        System.out.print("Cual es tu edad?: ");
        int edad = sc.nextInt();
        
        //3. Instruccion para mostrar el texto en consola
        System.out.println("Tu nombre es " + nombre + " y tienes " + edad + " años");
            
            sc.close();
        
    }
}

