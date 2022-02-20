package com.starscodes.SpringReactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringReactorApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringReactorApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Flux<String> nombres = Flux.just("Alberto", "Pedro", "Andres", "Juan")
				.doOnNext(e -> {
					if(e.isEmpty()){
						throw new RuntimeException("Nombres no pueden ser vacíos");
					}
					//Cada vez que se reciba un elemento lo mostraremos por consola
					System.out.println(e);
				});

		nombres.subscribe(log::info,
				error -> log.error(error.getMessage()),
				new Runnable() {
					@Override
					public void run() {
						log.info("Ha finalizado la ejecución del observable con éxito");
					}
				}
		); // No ocurre nada hasta que no nos subcribamos al flujo

	}
}
