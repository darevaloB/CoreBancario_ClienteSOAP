package ec.edu.espe.arquisoft.cliente.config;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import ec.edu.espe.arquisoft.cliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;


@Configuration
public class ClienteConfig {
      @Autowired
    private ClienteService client;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("ec.edu.espe.arquisoft.cliente.wsdl");
        return marshaller;
    }

    @Bean
    @Primary
    public ClienteService restCliente(Jaxb2Marshaller marshaller) {
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
