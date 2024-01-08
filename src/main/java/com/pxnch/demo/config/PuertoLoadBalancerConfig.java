package com.pxnch.demo.config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PuertoLoadBalancerConfig {
	
    private final Environment env;
    public PuertoLoadBalancerConfig(Environment env) {
        this.env = env;
    }
    public void imprimirInformacion() {
        // Acceder a propiedades específicas
        String puerto = env.getProperty("server.port");
        String perfilActivo = env.getProperty("spring.profiles.active");

        System.out.println("Puerto: " + puerto);
        System.out.println("Perfil Activo: " + perfilActivo);
    }
}
