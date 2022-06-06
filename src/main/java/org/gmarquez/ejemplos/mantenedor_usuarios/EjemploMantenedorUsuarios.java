package org.gmarquez.ejemplos.mantenedor_usuarios;

import org.gmarquez.ejemplos.mantenedor_usuarios.modelos.Usuario;
import org.gmarquez.ejemplos.mantenedor_usuarios.repositorios.UsuarioRepositorio;
import org.gmarquez.java.dao_repository.EjemploDaoRepositorio;

import java.util.Scanner;

public class EjemploMantenedorUsuarios {
    public static void main(String[] args) {
        System.out.println("=".repeat(50));
        System.out.println("Ejemplo de uso de un mantenedor de usuarios");
        System.out.println("=".repeat(50));

        EjemploMantenedorUsuarios.mostarMenu();
        Scanner scanner = new Scanner(System.in);
        int opcion = Integer.parseInt(scanner.nextLine());

        UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();
        while (true) {
            switch (opcion) {
                case 1:
                    EjemploMantenedorUsuarios.agregarOActualizar(scanner, usuarioRepositorio, opcion);
                    break;
                case 2:
                    EjemploMantenedorUsuarios.eliminarUsuario(scanner, usuarioRepositorio);
                    break;
                case 3:
                    EjemploMantenedorUsuarios.agregarOActualizar(scanner, usuarioRepositorio, opcion);
                    break;
                case 4:
                    EjemploMantenedorUsuarios.listarUsuarios(usuarioRepositorio);
                    break;
                case 5:
                    System.out.println("Saliendo de la aplicacion...");
                    System.exit(0);

            }
            EjemploMantenedorUsuarios.mostarMenu();
            opcion = Integer.parseInt(scanner.nextLine());
        }


    }

    public static void agregarOActualizar(Scanner scanner, UsuarioRepositorio usuarioRepositorio, int opcion) {
        Usuario usuario = new Usuario();
        switch (opcion) {
            case 1 -> {
                System.out.println("Ingrese el id del usuario a actualizar");
                usuario.setId(Long.parseLong(scanner.nextLine()));
                System.out.println("Ingrese el nombre del usuario a actualizar");
                usuario.setUsername(scanner.nextLine());
                System.out.println("Ingrese el password del usuario a actualizar");
                usuario.setPassword(scanner.nextLine());
                System.out.println("Ingrese el email del usuario a actualizar");
                usuario.setEmail(scanner.nextLine());
                usuarioRepositorio.guardar(usuario);
                System.out.println("Usuario actualizado");
            }
            case 3 -> {
                System.out.println("Ingrese el nombre del usuario");
                usuario.setUsername(scanner.nextLine());
                System.out.println("Ingrese el password del usuario");
                usuario.setPassword(scanner.nextLine());
                System.out.println("Ingrese el email del usuario");
                usuario.setEmail(scanner.nextLine());
                usuarioRepositorio.guardar(usuario);
                System.out.println("Usuario guardado");
            }
        }
    }

    public static void eliminarUsuario(Scanner scanner, UsuarioRepositorio usuarioRepositorio) {
        System.out.println("Ingrese el id del usuaro a eliminar");
        usuarioRepositorio.eliminar(Long.parseLong(scanner.nextLine()));
        System.out.println("Usuario eliminado");
    }

    public static void listarUsuarios(UsuarioRepositorio usuarioRepositorio) {
        usuarioRepositorio.listar().forEach(System.out::println);
    }


    public static void mostarMenu() {
        System.out.println("Menu:");
        System.out.println("Seleccione una de las siguientes opciones:");
        System.out.println("1. Actualizar");
        System.out.println("2. Eliminar");
        System.out.println("3. Agregar");
        System.out.println("4. Listar");
        System.out.println("5. Salir");

    }
}
