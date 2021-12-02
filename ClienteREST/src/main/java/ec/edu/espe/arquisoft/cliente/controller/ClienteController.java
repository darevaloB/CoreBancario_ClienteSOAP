/*
 * Copyright (c) 2021 mafer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    mafer - initial API and implementation and/or initial documentation
 */
package ec.edu.espe.arquisoft.cliente.controller;

import ec.edu.espe.arquisoft.cliente.dto.GenericDetailSerializer;
import ec.edu.espe.arquisoft.cliente.service.ClienteService;
import ec.edu.espe.arquisoft.cliente.wsdl.Cliente;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity getClienteById(@PathVariable("id") String id) {
        String errorMessage = "Error de Busqueda.";
        try {
            Cliente cliente = this.service.getClienteId(id);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            GenericDetailSerializer errorResponse;
            errorResponse = new GenericDetailSerializer(
                    errorMessage, e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);

        }
    }

    @GetMapping
    public ResponseEntity getAllClientes() {
        String errorMessage = "Error de Busqueda.";
        try {
            List<Cliente> clientes = this.service.getAll();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            GenericDetailSerializer errorResponse;
            errorResponse = new GenericDetailSerializer(
                    errorMessage, e.getMessage());
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    
    @PostMapping(value = "return/")
    public ResponseEntity createClienteReturnId(@RequestBody Cliente cliente) {
        
        String errorMessage = "Error al crear CLiente.";
        try {
            return ResponseEntity.ok(this.service.createdClienteReturnId(cliente));
        } catch (Exception e) {
            GenericDetailSerializer errorResponse;
            errorResponse = new GenericDetailSerializer(
                    errorMessage, e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    
    @PutMapping(value = "estadoBancaWeb/{id}")
    public ResponseEntity modificarEstadoClienteBancaWeb(@PathVariable("id") String id) {
        String errorMessage = "Error al modificar estado de Cliente.";
        try {
           this.service.modificarEstadoClienteBancaWeb(id);
           
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            GenericDetailSerializer errorResponse;
            errorResponse = new GenericDetailSerializer(
                    errorMessage, e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
     @PostMapping
  
    public ResponseEntity createCliente(@RequestBody Cliente cliente) {
        String errorMessage = "Error al crear CLiente.";
        try {
            this.service.createCliente(cliente);
            return ResponseEntity.ok(cliente.getId());
        } catch (Exception e) {
            GenericDetailSerializer errorResponse;
            errorResponse = new GenericDetailSerializer(
                    errorMessage, e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
      @DeleteMapping(value = "{tipoIdentificacion}/{identificacion}")
 
    public ResponseEntity eliminarCliente(@PathVariable("tipoIdentificacion") String tipoIdentificacion,
            @PathVariable("identificacion") String identificacion) {
        String errorMessage = "Error al eliminar estado de Cliente.";
        try {
            this.service.eliminarCliente(tipoIdentificacion, identificacion);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            GenericDetailSerializer errorResponse;
            errorResponse = new GenericDetailSerializer(
                    errorMessage, e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    
    
}
