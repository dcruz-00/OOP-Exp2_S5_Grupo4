package bibliotecaduoc.main;

import bibliotecaduoc.servicios.Biblioteca;
import bibliotecaduoc.modelos.Usuario;
import java.util.Scanner;

public class Main {

    Biblioteca biblioteca = new Biblioteca();
    private final Scanner scanner = new Scanner(System.in);

    static String[] opcionesMenu = {
        "------------------------",
        "1) Registro de usuario",
        "2) Libros Disponibles",
        "3) Prestamo de libros",
        "4) Generar informe",
        "5) Cerrar sesión",
        "------------------------",};

    public static void main(String[] args) {
        Main app = new Main();
        app.ejecutarMenu();
    }

    public void ejecutarMenu() {
        int opcionMenu = -1;
        System.out.println("..:: ¡Bienvenido al sistema de préstamo de la Biblioteca de DUOC UC! ::..");
        do {           
            mostrarMenu();
            if (scanner.hasNextInt()) {
                opcionMenu = scanner.nextInt();
                scanner.nextLine();
                switch (opcionMenu) {
                    case 1 ->
                        Usuario.registrarUsuario();
                    case 2 ->
                        biblioteca.mostrarLibros();
                    case 3 ->
                        biblioteca.prestamoLibros();
                    case 4 ->
                        biblioteca.generarInforme();
                    case 5 ->
                        System.out.println("Saliendo del Sistema...........");
                    default ->
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } else {
                System.out.println("Entrada no válida. Ingrese un número.");
                scanner.nextLine(); // descartar entrada inválida
            }
        } while (opcionMenu != 5);
    }

    public void mostrarMenu() {
        System.out.println("\n==== MENU PRINCIPAL ====");
        for (int i = 0; i < opcionesMenu.length; i++) {
            System.out.println(opcionesMenu[i]);
        }
        System.out.println("Seleccione una opción: ");
    }

}
