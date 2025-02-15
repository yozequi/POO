package producto;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Bienvenido al sistema de gestión de productos\n");
            
            // Crear y llenar los datos de dos productos
            Producto producto1 = ingresarDatosProductoConConfirmacion(scanner, 1);
            Producto producto2 = ingresarDatosProductoConConfirmacion(scanner, 2);
            
            // Mostrar los detalles de los productos
            System.out.println("\nDetalles del primer producto:");
            producto1.mostrarProducto();
            
            System.out.println("\nDetalles del segundo producto:");
            producto2.mostrarProducto();
            
            // Comparar productos y mostrar el resultado
            Producto productoMayorPrecio = compararProductos(producto1, producto2);
            System.out.println("\nEl producto con mayor precio de venta es: " + productoMayorPrecio.getDescripcion());
        }
    }

    // Método para ingresar datos de un producto con confirmación
    private static Producto ingresarDatosProductoConConfirmacion(Scanner scanner, int numeroProducto) {
        Producto producto;
        while (true) {
            producto = ingresarDatosProducto(scanner, numeroProducto);
            System.out.println("\nDatos del producto ingresados:");
            producto.mostrarProducto();
            System.out.print("\n¿Son correctos los datos? (s/n): ");
            String confirmacion = scanner.nextLine().trim().toLowerCase();
            if (confirmacion.equals("s")) {
                break;
            } else {
                System.out.println("\nReingresando los datos del producto...");
            }
        }
        return producto;
    }

    // Método para ingresar datos de un producto
    private static Producto ingresarDatosProducto(Scanner scanner, int numeroProducto) {
        Producto producto = new Producto();
        System.out.println("\nIngrese los datos del producto " + numeroProducto + " ");

        System.out.println("Descripcion: ");
        producto.setDescripcion(scanner.nextLine().trim());

        System.out.println("Código: ");
        producto.setCodigo(scanner.nextLine().trim());

        System.out.println("Tipo: ");
        producto.setTipo(scanner.nextLine().trim());

        producto.setCosto(leerDouble(scanner, "Costo: "));
        producto.setImpuesto(leerDouble(scanner, "Impuesto (%): "));

        return producto;
    }

    // Método para leer un valor double válido
    private static double leerDouble(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Entrada inválida. Por favor, ingrese un número válido.");
            }
        }
    }

    // Método para comparar dos productos
    private static Producto compararProductos(Producto producto1, Producto producto2) {
        double precio1 = producto1.calcularPrecio(20); // Utilidad del 20%
        double precio2 = producto2.calcularPrecio(20);

        return (precio1 >= precio2) ? producto1 : producto2;
    }
}

class Producto {
    private String descripcion;
    private String codigo;
    private String tipo;
    private double costo;
    private double impuesto;

    // Getters
    public String getDescripcion() {
        return descripcion;
    }

    // Setters
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    // Mostrar detalles del producto
    public void mostrarProducto() {
        System.out.println("Descripción: " + descripcion);
        System.out.println("Código: " + codigo);
        System.out.println("Tipo: " + tipo);
        System.out.println("Costo: $" + String.format("%.2f", costo));
        System.out.println("Impuesto: " + String.format("%.2f", impuesto) + "%");
    }

    // Calcular precio de venta
    public double calcularPrecio(double utilidad) {
        double precioSinImpuesto = costo + (costo * (utilidad / 100));
        return precioSinImpuesto + (precioSinImpuesto * (impuesto / 100));
    }
}
