package actividad2;
import java.util.Scanner;

public class Actividad2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Ingresa tu nombre: ");
        String nombre = sc.nextLine();
        
        System.out.print("Vuelve a ingresar tu nombre: ");
        String nombre1 = sc.nextLine();
        
        if (nombre1.equals(nombre)) {
            System.out.println ("Hola " + nombre + " bienvenido!");
        }
        else {
            System.out.println(" Los nombres de usuario no coiniciden");
        }
            sc.close();                                   
                
    }
    
}
