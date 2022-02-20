package com.starscodes.SpringReactor;

import com.starscodes.SpringReactor.models.UsuarioDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringReactorApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringReactorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringReactorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        List<String> usuariosList = new ArrayList<>();
        usuariosList.add("Alberto Sánchez");
        usuariosList.add("Pedro Guzman");
        usuariosList.add("Andres Sierra");
        usuariosList.add("Will Smith");
        usuariosList.add("James Sidis");

        Flux<String> nombres = Flux.fromIterable(usuariosList);

        Flux<UsuarioDTO> usuarios = nombres.map(nombre -> {
                    String usuario[] = nombre.toUpperCase().split(" ");
                    return new UsuarioDTO(usuario[0], usuario[1]);
                })
                .filter(usuarioDTO -> usuarioDTO.getNombre().equalsIgnoreCase("will"))
                .doOnNext(usuario -> {
                    if (usuario == null) {
                        throw new RuntimeException("Nombres no pueden ser vacíos");
                    }
                    //Cada vez que se reciba un elemento lo mostraremos por consola
                    System.out.println(usuario.getNombre().concat(" ").concat(usuario.getApellido()));
                })
                .map(usuario -> {
                    String nombre = usuario.getNombre().toLowerCase();
                    String apellido = usuario.getApellido().toLowerCase();
                    usuario.setNombre(nombre);
                    usuario.setApellido(apellido);
                    return usuario;
                });

        usuarios.subscribe(e -> log.info(e.toString()),
                error -> log.error(error.getMessage()),
                new Runnable() {
                    @Override
                    public void run() {
                        log.info("Ha finalizado la ejecución del observable con éxito");
                    }
                }
        );
    }
}
