package bibliotecaduoc.servicios;

import bibliotecaduoc.modelos.Usuario;
import bibliotecaduoc.modelos.Libro;
import bibliotecaduoc.excepciones.LibroNoEncontradoException;
import bibliotecaduoc.excepciones.LibroYaPrestadoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.TreeSet;

public class Biblioteca {

    private static final HashMap<String, Usuario> usuarios = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
    ArrayList<Libro> libros = new ArrayList<>();

    public Biblioteca() {
        libros.add(new Libro(1, "Las Aventuras de Duco", "Emilia Acevedo", true));
        libros.add(new Libro(2, "El Quijote", "Cervantes", true));
        libros.add(new Libro(3, "Así habló Zaratrustra", "Federico Nietzsche", true));
        libros.add(new Libro(4, "El Extranjero", "Albert Camus", true));
        libros.add(new Libro(5, "Noches Blancas", "Fiodor Dostoyevsky", true));
        libros.add(new Libro(6, "Ficciones", "Jorge Luis Borges", true));
    }

    public ArrayList<Libro> getLibros() {
        return libros;
    }

    public void mostrarLibros() {
        System.out.println("\n=== LISTA DE NUESTROS LIBROS ===");
        // Se crea en el momento para que la colección no este vacía
        TreeSet<Libro> biblioteca = new TreeSet<>(Comparator.comparing(Libro::getIndiceLibro));
        biblioteca.addAll(libros);
        
        for (Libro libro : biblioteca) {
            System.out.println("------------------------");
            System.out.println("NOMBRE: " + libro.getNombre());
            System.out.println("AUTOR(A): " + libro.getAutor());
            System.out.println("CÓDIGO PARA PRESTAMO: #" + libro.getIndiceLibro());
            System.out.println("DISPONIBILIDAD: " + (libro.isDisponible() ? "Disponible" : "No Disponible"));
        }
    }

    public void prestamoLibros() {
        System.out.println("\n=== PRESTAMO DE LIBROS ===");
        System.out.println("\nPor favor, ingrese su RUT (formato: XX.XXX.XXX-X):");
        String rut = scanner.nextLine().trim();

        if (!Usuario.existeUsuario(rut)) {
            System.out.println("\nEl RUT ingresado no está registrado.");
            System.out.println("Debe registrarse antes de pedir un préstamo.");
            return;
        }

        Usuario usuario = Usuario.obtenerUsuario(rut);
        System.out.println("\n¡Bienvenido(a), " + usuario.getNombreCompleto() + ". Puede continuar con el préstamo.");

        int opcionLibro;
        while (true) {
            System.out.println("\nPor favor, a continuación elija su libro según su código #.");

            try {
                opcionLibro = scanner.nextInt();
                scanner.nextLine();
                break;

            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Debe ingresar un número de código.");
                scanner.nextLine();
            }
        }

        try {
            boolean libroEncontrado = false;

            for (Libro libro : libros) {
                if (libro.getIndiceLibro() == opcionLibro) {
                    libroEncontrado = true;
                    if (libro.isDisponible()) {
                        libro.prestarA(rut);
                        usuario.agregarLibroPrestado(libro);
                        System.out.println("\nLibro elegido: " + libro.getNombre() + ". Ahora está en préstamo a: " + usuario.getNombreCompleto());
                    } else {
                        throw new LibroYaPrestadoException("\nEl libro " + libro.getNombre() + " ya fue prestado a otro usuario.");
                    }
                    break;
                }
            }

            if (!libroEncontrado) {
                throw new LibroNoEncontradoException("\nNo se encontró un libro con el código #" + opcionLibro);
            }

        } catch (LibroNoEncontradoException | LibroYaPrestadoException e) { //multi-catch. permite capturar varias excepciones diferentes si el manejo será el mismo
            System.out.println("ERROR: " + e.getMessage()); // ambas se manejan mostrando mensaje "error" y ambas heredan de Exception
        }
    }

    public void generarInforme() {
        String archivoSalida = "informe_usuarios.txt";

        try (FileWriter writer = new FileWriter(archivoSalida)) {
            writer.write("\n=== INFORME DE USUARIOS ===\n");
            
            HashSet<Usuario> usuariosRegistrados = new HashSet<>(usuarios.values());
            System.out.println("Cantidad de usuarios: " + usuariosRegistrados.size());

            for (Usuario usuario : usuariosRegistrados) {
                writer.write("Usuario: " + usuario.getNombreCompleto() + " [" + usuario.getRut() + "]\n");

                ArrayList<Libro> libros = usuario.getLibrosPrestados();
                if (libros.isEmpty()) {
                    writer.write("  No tiene libros prestados.\n");
                } else {
                    for (Libro libro : libros) {
                        writer.write("Libro: " + libro.getNombre() + " (Autor(a): " + libro.getAutor() + ") " + " Código de préstamo: [#" + libro.getIndiceLibro() + "]\n");
                    }
                }
                writer.write("\n");
            }

            System.out.println("Informe generado correctamente en " + archivoSalida);
        } catch (IOException e) {
            System.out.println("ERROR al generar informe: " + e.getMessage());
        }
    }

}
