package proyectofinal;

import java.util.*;
import java.util.Scanner;

class Usuario {
    protected String nombre;
    protected String correo;

    public Usuario(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Correo: " + correo;
    }
}

interface AdministrableEquipo {
    void agregarAlumnoAEquipo(Equipo equipo, Alumno alumno, String rol);
    void quitarAlumnoDeEquipo(Equipo equipo, int idAlumno);
}

class Profesor extends Usuario implements AdministrableEquipo{
    public Profesor(String nombre, String correo) {
        super(nombre, correo);
    }

    @Override
    public void agregarAlumnoAEquipo(Equipo equipo, Alumno alumno, String rol) {
        equipo.agregarMiembro(alumno, rol);
        System.out.println(alumno.getNombre() + " ha sido agregado al equipo " + equipo.getNombre() + " como " + rol);
    }

    @Override
    public void quitarAlumnoDeEquipo(Equipo equipo, int idAlumno) {
        if (equipo.quitarMiembro(idAlumno)) {
            System.out.println("Alumno con ID " + idAlumno + " ha sido"
                    + " eliminado del equipo " + equipo.getNombre());
        } else {
            System.out.println("No se encontró un alumno con el ID " + idAlumno);
        }
    }
}

class Alumno extends Usuario {
    private int id;

    public Alumno(String nombre, String correo, int id) {
        super(nombre, correo);
        this.id = id;
    }

    public int getId() { return id; }

    @Override
    public String toString() {
        return super.toString() + ", ID: " + id;
    }
}

class Equipo {
    private String nombre;
    private Map<Integer, Alumno> miembros;
    private Map<Integer, String> roles;
    private int siguienteId = 1;

    public Equipo(String nombre) {
        this.nombre = nombre;
        this.miembros = new HashMap<>();
        this.roles = new HashMap<>();
    }

    public String getNombre() { return nombre; }

    public void agregarMiembro(Alumno alumno, String rol) {
        miembros.put(alumno.getId(), alumno);
        roles.put(alumno.getId(), rol);
    }

    public int obtenerSiguienteId() {
        return siguienteId++;
    }

    public boolean quitarMiembro(int idAlumno) {
        if (miembros.containsKey(idAlumno)) {
            miembros.remove(idAlumno);
            roles.remove(idAlumno);
            return true;
        }
        return false;
    }

    public void mostrarMiembros() {
        System.out.println("Equipo " + nombre + " - Miembros:");
        if(miembros.isEmpty()){
            System.out.println("El equipo esta vacio");
            return;
        }
        for (Map.Entry<Integer, Alumno> entry : miembros.entrySet()) {
            System.out.println("ID: " + entry.getKey() + " - " + entry.getValue().getNombre() + " (" + roles.get(entry.getKey()) + ")");
        }
    }
}

class UsuarioManager {
    protected Map<String, String> usuarios = new HashMap<>();
    protected Scanner sc = new Scanner(System.in);
    protected List<Usuario> registrados = new ArrayList<>();


    public void registrarUsuario(String nombre, String correo, String username, String password, String tipoUsuario){
        if (usuarios.containsKey(username)) {
            System.out.println("Error: El nombre de usuario ya está registrado.");
            return;
        }
        usuarios.put(username, password);
        if("Profesor".equalsIgnoreCase(tipoUsuario)){
            registrados.add(new Profesor(nombre,correo));
            System.out.println("profesor creado exitosamente!");

        } else if ("Alumno".equalsIgnoreCase(tipoUsuario)) {

            System.out.println("Error : Aun no es posible crear Alumnos desde registrar");
        }
    }

    public boolean iniciarSesion(String nombreUsuario, String contrasena) {
        if (!usuarios.containsKey(nombreUsuario)) {
            System.out.println("Error: Nombre de usuario no encontrado.");
            return false;
        }

        if (!usuarios.get(nombreUsuario).equals(contrasena)) {
            System.out.println("Error: Contraseña incorrecta.");
            return false;
        }

        System.out.println("Inicio de sesión exitoso. Bienvenido, " + nombreUsuario + "!");
        return true;
    }

    public void listarUsuarios(){
        System.out.println("Usuarios Registrados:");
        for (Usuario usuario : registrados) {
            System.out.println(usuario);
        }
    }

}

class ConsolaUserManager extends UsuarioManager{

    public void registrar() {
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = sc.nextLine();
        if (usuarios.containsKey(nombreUsuario)) {
            System.out.println("Error: El nombre de usuario ya está registrado.");
            return;
        }

        System.out.print("Contraseña: ");
        String contrasena = sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Correo electrónico: ");
        String correo = sc.nextLine();

        super.registrarUsuario(nombre,correo,nombreUsuario,contrasena,"profesor");

        System.out.println("Registro exitoso.");
    }


}

class Proyecto {
    private String nombre;
    private List<Equipo> equipos;
    private List<Tarea> tareas;

    public Proyecto(String nombre) {
        this.nombre = nombre;
        this.equipos = new ArrayList<>();
        this.tareas = new ArrayList<>();
    }

    public String getNombre() { return nombre; }

    public List<Equipo> getEquipos() { return equipos; }

    public void agregarEquipo(Equipo equipo) {
        equipos.add(equipo);
    }

    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
    }

    public void mostrarEquipos() {
        System.out.println("Equipos en el proyecto " + nombre + ":");
        if(equipos.isEmpty()){
            System.out.println("No hay equipos aun");
        }
        for (Equipo e : equipos) {
            e.mostrarMiembros();
        }
    }
}

class Tarea {
    private String descripcion;
    private Alumno asignado;

    public Tarea(String descripcion, Alumno asignado) {
        this.descripcion = descripcion;
        this.asignado = asignado;
    }

    public void mostrarTarea() {
        System.out.println("Tarea: " + descripcion + " asignada a " + asignado.getNombre());
    }
}

public class Proyectofinal{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ConsolaUserManager userManager = new ConsolaUserManager();
        List<Proyecto> proyectos = new ArrayList<>();
        List<Alumno> alumnos = new ArrayList<>(); // Lista para almacenar alumnos

        while (true) {
            System.out.println("\n1. Crear Proyecto\n2. Administrar Equipos\n3. Asignar Tareas\n4. Mostrar Proyectos\n5. Registrar Alumno\n6. Salir");
            System.out.print("Elige una opción: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": {
                    System.out.print("Nombre del proyecto: ");
                    String nombreProyecto = sc.nextLine();
                    proyectos.add(new Proyecto(nombreProyecto));
                    System.out.println("Proyecto creado exitosamente.");
                    break;
                }
                case "2": {
                    if (proyectos.isEmpty()) {
                        System.out.println("No hay proyectos creados aún. Crea un proyecto primero.");
                        break;
                    }

                    System.out.println("Seleccione el proyecto para administrar equipos:");
                    for (int i = 0; i < proyectos.size(); i++) {
                        System.out.println((i + 1) + ". " + proyectos.get(i).getNombre());
                    }
                    System.out.print("Ingrese el número del proyecto: ");
                    try {
                        int proyectoIndex = Integer.parseInt(sc.nextLine()) - 1;
                        if (proyectoIndex >= 0 && proyectoIndex < proyectos.size()) {
                            Proyecto proyectoSeleccionado = proyectos.get(proyectoIndex);
                            administrarEquipos(proyectoSeleccionado, alumnos, sc); 
                        } else {
                            System.out.println("Número de proyecto inválido.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Ingrese un número.");
                    }
                    break;
                }
                case "3": {
                    if (proyectos.isEmpty() || alumnos.isEmpty()) {
                        System.out.println("No hay proyectos o alumnos registrados.");
                        break;
                    }

                    System.out.println("Seleccione el proyecto:");
                    for (int i = 0; i < proyectos.size(); i++) {
                        System.out.println((i + 1) + ". " + proyectos.get(i).getNombre());
                    }
                    System.out.print("Ingrese el número del proyecto: ");
                    try {
                        int proyectoIndex = Integer.parseInt(sc.nextLine()) - 1;
                        if (proyectoIndex >= 0 && proyectoIndex < proyectos.size()) {
                            Proyecto proyectoSeleccionado = proyectos.get(proyectoIndex);
                            asignarTareas(proyectoSeleccionado, alumnos, sc);
                        } else {
                            System.out.println("Número de proyecto inválido.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Ingrese un número.");
                    }
                    break;
                }
                case "4": {
                    if (proyectos.isEmpty()) {
                        System.out.println("No hay proyectos creados aún.");
                    } else {
                        for (Proyecto p : proyectos) {
                            System.out.println("Proyecto: " + p.getNombre());
                            p.mostrarEquipos();
                        }
                    }
                    break;
                }
                case "5":{
                    // Lógica para registrar un alumno
                    System.out.print("Nombre del alumno: ");
                    String nombreAlumno = sc.nextLine();
                    System.out.print("Correo electrónico del alumno: ");
                    String correoAlumno = sc.nextLine();
                    System.out.print("ID del alumno: ");
                    try {
                        int idAlumno = Integer.parseInt(sc.nextLine());
                        alumnos.add(new Alumno(nombreAlumno, correoAlumno, idAlumno));
                        System.out.println("Alumno registrado exitosamente.");
                    } catch (NumberFormatException e) {
                        System.out.println("ID inválido. Debe ser un número entero.");
                    }
                    break;
                }
                case "6": {
                    System.out.println("Saliendo del programa.");
                    sc.close();
                    return;
                }
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // Método para administrar equipos
    public static void administrarEquipos(Proyecto proyecto, List<Alumno> alumnos, Scanner sc) {
        while (true) {
            System.out.println("\nAdministración de Equipos para el proyecto " + proyecto.getNombre());
            System.out.println("1. Crear Equipo\n2. Agregar Alumno a Equipo\n3. Quitar Alumno de Equipo\n4. Mostrar Equipos\n5. Volver al menú principal");
            System.out.print("Elige una opción: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": {
                    System.out.print("Nombre del equipo: ");
                    String nombreEquipo = sc.nextLine();
                    Equipo nuevoEquipo = new Equipo(nombreEquipo);
                    proyecto.agregarEquipo(nuevoEquipo);
                    System.out.println("Equipo creado exitosamente.");
                    break;
                }
                case "2": {
                    // Agregar alumno a equipo
                    if (proyecto.getEquipos().isEmpty() || alumnos.isEmpty()) {
                        System.out.println("No hay equipos o alumnos registrados.");
                        break;
                    }

                    System.out.println("Seleccione el equipo:");
                    List<Equipo> equipos = proyecto.getEquipos();
                    for (int i = 0; i < equipos.size(); i++) {
                        System.out.println((i + 1) + ". " + equipos.get(i).getNombre());
                    }
                    System.out.print("Ingrese el número del equipo: ");
                    try {
                        int equipoIndex = Integer.parseInt(sc.nextLine()) - 1;
                        if (equipoIndex >= 0 && equipoIndex < equipos.size()) {
                            Equipo equipoSeleccionado = equipos.get(equipoIndex);

                            System.out.println("Seleccione el alumno:");
                            for (int i = 0; i < alumnos.size(); i++) {
                                System.out.println((i + 1) + ". " + alumnos.get(i).getNombre());
                            }
                            System.out.print("Ingrese el número del alumno: ");
                            try {
                                int alumnoIndex = Integer.parseInt(sc.nextLine()) - 1;
                                if (alumnoIndex >= 0 && alumnoIndex < alumnos.size()) {
                                    Alumno alumnoSeleccionado = alumnos.get(alumnoIndex);
                                    System.out.print("Ingrese el rol del alumno en el equipo: ");
                                    String rol = sc.nextLine();
                                    Profesor profesorTemporal = new Profesor("ProfesorTemporal", "correoTemporal"); // Crear un profesor temporal
                                    profesorTemporal.agregarAlumnoAEquipo(equipoSeleccionado, alumnoSeleccionado, rol);
                                } else {
                                    System.out.println("Número de alumno inválido.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Entrada inválida. Ingrese un número.");
                            }

                        } else {
                            System.out.println("Número de equipo inválido.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Ingrese un número.");
                    }
                    break;
                }
                case "3": {
                    // Quitar alumno de equipo
                    if (proyecto.getEquipos().isEmpty()) {
                        System.out.println("No hay equipos creados aún.");
                        break;
                    }

                    System.out.println("Seleccione el equipo:");
                    List<Equipo> equipos = proyecto.getEquipos();
                    for (int i = 0; i < equipos.size(); i++) {
                        System.out.println((i + 1) + ". " + equipos.get(i).getNombre());
                    }
                    System.out.print("Ingrese el número del equipo: ");
                    try {
                        int equipoIndex = Integer.parseInt(sc.nextLine()) - 1;
                        if (equipoIndex >= 0 && equipoIndex < equipos.size()) {
                            Equipo equipoSeleccionado = equipos.get(equipoIndex);

                            System.out.print("Ingrese el ID del alumno a eliminar: ");
                            try {
                                int idAlumno = Integer.parseInt(sc.nextLine());
                                Profesor profesorTemporal = new Profesor("ProfesorTemporal", "correoTemporal"); // Crear un profesor temporal
                                profesorTemporal.quitarAlumnoDeEquipo(equipoSeleccionado, idAlumno);
                            } catch (NumberFormatException e) {
                                System.out.println("ID inválido. Debe ser un número entero.");
                            }

                        } else {
                            System.out.println("Número de equipo inválido.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Ingrese un número.");
                    }
                    break;
                }
                case "4": {
                    proyecto.mostrarEquipos();
                    break;
                }
                case "5": {
                    return; // Volver al menú principal
                }
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // Método para asignar tareas
    public static void asignarTareas(Proyecto proyecto, List<Alumno> alumnos, Scanner sc) {
        System.out.print("Descripción de la tarea: ");
        String descripcion = sc.nextLine();

        System.out.println("Seleccione el alumno a quien asignar la tarea:");
        for (int i = 0; i < alumnos.size(); i++) {
            System.out.println((i + 1) + ". " + alumnos.get(i).getNombre());
        }
        System.out.print("Ingrese el número del alumno: ");
        try {
            int alumnoIndex = Integer.parseInt(sc.nextLine()) - 1;
            if (alumnoIndex >= 0 && alumnoIndex < alumnos.size()) {
                Alumno alumnoSeleccionado = alumnos.get(alumnoIndex);
                Tarea nuevaTarea = new Tarea(descripcion, alumnoSeleccionado);
                proyecto.agregarTarea(nuevaTarea);
                System.out.println("Tarea asignada exitosamente.");
            } else {
                System.out.println("Número de alumno inválido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Ingrese un número.");
        }
    }
}
