
package actividad.pkg4;



// Clase Materia
class Materia {
    private String nombre;
    private String clave;
    private int creditos;
    private int horasSemanales;

    public Materia() {}

    public Materia(String nombre, String clave, int creditos, int horasSemanales) {
        this.nombre = nombre;
        this.clave = clave;
        this.creditos = creditos;
        this.horasSemanales = horasSemanales;
    }
    
    public Materia(Materia otra) {
        this(otra.nombre, otra.clave, otra.creditos, otra.horasSemanales);
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public int getCreditos() { return creditos; }
    public void setCreditos(int creditos) { this.creditos = creditos; }
    public int getHorasSemanales() { return horasSemanales; }
    public void setHorasSemanales(int horasSemanales) { this.horasSemanales = horasSemanales; }
}

// Clase Curso
class Curso {
    private String nombre;
    private Materia[] materias;

    public Curso() {
        this.materias = new Materia[3];
    }

    public Curso(String nombre, Materia m1, Materia m2, Materia m3) {
        this.nombre = nombre;
        this.materias = new Materia[]{m1, m2, m3};
    }
    
    public Curso(Curso otro) {
        this(otro.nombre, otro.materias[0], otro.materias[1], otro.materias[2]);
    }

    public int getTotalCreditos() {
        int total = 0;
        for (Materia m : materias) {
            total += m.getCreditos();
        }
        return total;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Materia[] getMaterias() { return materias; }
}

// Clase Profesor
class Profesor {
    private String nombre;
    private int numNomina;
    private double sueldoPorHora;
    private Materia materia;

    public Profesor() {}

    public Profesor(String nombre, int numNomina, double sueldoPorHora, Materia materia) {
        this.nombre = nombre;
        this.numNomina = numNomina;
        this.sueldoPorHora = sueldoPorHora;
        this.materia = materia;
    }
    
    public Profesor(Profesor otro) {
        this(otro.nombre, otro.numNomina, otro.sueldoPorHora, otro.materia);
    }

    public double calcularSueldoSemanal() {
        return materia.getHorasSemanales() * sueldoPorHora;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getNumNomina() { return numNomina; }
    public void setNumNomina(int numNomina) { this.numNomina = numNomina; }
    public double getSueldoPorHora() { return sueldoPorHora; }
    public void setSueldoPorHora(double sueldoPorHora) { this.sueldoPorHora = sueldoPorHora; }
    public Materia getMateria() { return materia; }
    public void setMateria(Materia materia) { this.materia = materia; }
}

// Clase Alumno
class Alumno {
    private String matricula;
    private String nombre;
    private int edad;
    private Curso curso;

    public Alumno() {}

    public Alumno(String matricula, String nombre, int edad, Curso curso) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.edad = edad;
        this.curso = curso;
    }
    
    public Alumno(Alumno otro) {
        this(otro.matricula, otro.nombre, otro.edad, otro.curso);
    }

    // Getters y setters
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
}

// Clase Principal para prueba
public class Actividad4 {
    public static void main(String[] args) {
        Materia m1 = new Materia("Matemáticas", "M101", 4, 5);
        Materia m2 = new Materia("Historia", "H102", 3, 4);
        Materia m3 = new Materia("Ciencias", "C103", 5, 6);
        
        Curso curso1 = new Curso("Primero A", m1, m2, m3);
        
        Profesor[] profesores = {
            new Profesor("Juan Pérez", 12345, 200, m1),
            new Profesor("Laura Gómez", 67890, 180, m2),
            new Profesor("Carlos Díaz", 11223, 220, m3)
        };
        
        Alumno[] alumnos = {
            new Alumno("A001", "María López", 16, curso1),
            new Alumno("A002", "Carlos García", 17, curso1),
            new Alumno("A003", "Ana Torres", 15, curso1)
        };
        
        for (Alumno alumno : alumnos) {
            System.out.println("Alumno: " + alumno.getNombre() + ", Curso: " + alumno.getCurso().getNombre() + ",  Créditos: " + alumno.getCurso().getTotalCreditos());
            System.out.println("-----------------------------");

        }
        
        System.out.println("\nProfesores:");
        for (Profesor profesor : profesores) {
            System.out.println("Nombre: " + profesor.getNombre() + ", Materia: " + profesor.getMateria().getNombre());
            System.out.println("Sueldo semanal: $" + profesor.calcularSueldoSemanal());
            System.out.println("-----------------------------");
        }
    }
}
