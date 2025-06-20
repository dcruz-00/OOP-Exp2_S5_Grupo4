package bibliotecaduoc.servicios;

import java.util.Scanner;

public class Validaciones {

    public final Scanner scanner = new Scanner(System.in);
    
    public String validarYCapitalizar(String mensaje) {
        String texto;
        while (true) {
            System.out.println(mensaje);
            texto = scanner.nextLine().trim();
            if (texto.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+")) {
                texto = texto.toLowerCase();
                return texto.substring(0, 1).toUpperCase() + texto.substring(1);
            } else {
                System.out.println("\nEntrada invalida. Solo se permiten letras. Intente nuevamente.");
            }
        }
    }
}
