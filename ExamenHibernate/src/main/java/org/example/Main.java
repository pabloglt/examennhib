package org.example;

import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        SessionFactory factory = DataProvider.getSessionFactory();

        PeliculaRepository peliculaRepository = new PeliculaRepository(factory);
        OpinionRepository opinionRepository = new OpinionRepository(factory);

        DataService dataService = new DataService(peliculaRepository, opinionRepository);

        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        System.out.println("Examen Hibernate");

        do {
            mostrarMenu();

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        registrarNuevaPelicula(scanner, dataService);
                        break;
                    case 2:
                        obtenerOpinionesPorUsuario(scanner, dataService);
                        break;
                    case 3:
                        anadirOpinionAPelicula(scanner, dataService);
                        break;
                    case 4:
                        listarOpinionesConPuntuacionBaja(dataService);
                        break;

                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Inténtelo de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Ingrese un número válido para la opción.");
                opcion = -1;
            } catch (Exception e) {
                System.err.println("Error inesperado: " + e.getMessage());
            }

        } while (opcion != 0);

        factory.close();
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("1. Registrar Película");
        System.out.println("2. Obtener Opiniones por usuario");
        System.out.println("3. Añadir opinión a una película");
        System.out.println("4. Listar opiniones con Puntuación de menos o igual a 3");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void registrarNuevaPelicula(Scanner scanner, DataService service) {
        System.out.print("Título de la nueva película: ");
        String titulo = scanner.nextLine();

        Pelicula nueva = service.registrarNuevaPelicula(titulo);
        System.out.println("Película '" + nueva.getTitulo() + "' registrada con Id " + nueva.getId());
    }

    private static void obtenerOpinionesPorUsuario(Scanner scanner, DataService service) {
        System.out.print("Ingrese el Email del usuario: ");
        String email = scanner.nextLine();

        List<Opinion> opiniones = service.obtenerOpinionesPorUsuario(email);

        if (opiniones.isEmpty()) {
            System.out.println("El usuario '" + email + "' no tiene opiniones registradas.");
        } else {
            System.out.println("Las opiniones del usuario " + email + " (" + opiniones.size() + " en total)");
            for (Opinion o : opiniones) {
                System.out.println(o);
            }
        }
    }

    private static void anadirOpinionAPelicula(Scanner scanner, DataService service) {
        try {
            System.out.print("ID de la Película para añadir opinión: ");
            Long peliculaId = Long.valueOf(scanner.nextLine());

            System.out.print("Email del usuario: ");
            String email = scanner.nextLine();
            System.out.print("Puntuación (1-10): ");
            int puntuacion = Integer.parseInt(scanner.nextLine());
            System.out.print("Descripción: ");
            String descripcion = scanner.nextLine();

            Optional<Opinion> opinionOpt = service.anadirOpinionAPelicula(peliculaId, email, puntuacion, descripcion);

            if (opinionOpt.isPresent()) {
                System.out.println("Opinión añadida con éxito a la película con id " + peliculaId + ". Id de Opinión: " + opinionOpt.get().getId());
            } else {
                System.out.println("No se pudo añadir la opinión para la Película con id " + peliculaId);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: Ingrese un número válido.");
        }
    }

    private static void listarOpinionesConPuntuacionBaja(DataService service) {

        List<Opinion> opiniones = service.listarOpinionesConPuntuacionBaja();

        if (opiniones.isEmpty()) {
            System.out.println("No hay opiniones con una puntuación de 3 o menos.");
        } else {
            System.out.println("Las opiniones con puntuación de 3 o menos (" + opiniones.size() + " en total)");
            for (Opinion opinion : opiniones) {
                System.out.println(opinion);
            }
        }
    }
}