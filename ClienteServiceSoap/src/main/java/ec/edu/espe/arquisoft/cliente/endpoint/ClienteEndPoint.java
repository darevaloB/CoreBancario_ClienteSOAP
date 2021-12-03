/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.arquisoft.cliente.endpoint;

import ec.edu.espe.arquisoft.cliente.dto.GenericDetailSerializer;
import ec.edu.espe.arquisoft.cliente.exception.CreateException;
import ec.edu.espe.arquisoft.cliente.exception.UpdateException;
import ec.edu.espe.arquisoft.cliente.service.ClienteService;
import ec.edu.espe.arquisoft.cliente.ws.*;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Slf4j
@Endpoint
public class ClienteEndPoint {

    private static final String NAMESPACE_URI = "http://espe.edu.ec/arquisoft/cliente/ws";
    public final ClienteService service;

    @Autowired
    public ClienteEndPoint(ClienteService service) {
        this.service = service;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getClienteByIdRequest")
    @ResponsePayload
    public GetClienteByIdResponse obtenerClienteId(@RequestPayload GetClienteByIdRequest request) throws RuntimeException {
        Optional<Cliente> clienteOpt = this.service.getById(request.getId());
        if (clienteOpt.isPresent()) {
            GetClienteByIdResponse response = new GetClienteByIdResponse();
            response.setCliente(clienteOpt.get());
            return response;

        } else {
            log.error("El cliente con el ID: {} no existe en la base de datos", request.getId());
            throw new CreateException("Mensaje: " + "El cliente con ID:" + request.getId() + " no existe en la base de datos");
        }

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getClienteByIdAndTypeRequest")
    @ResponsePayload
    public GetClienteByIdAndTypeResponse obtenerClienteId(@RequestPayload GetClienteByIdAndTypeRequest request) throws RuntimeException {
        try{
            Cliente cliente = this.service.getByTipoIdentificacionAndIdentifiacion(request.getTipoIdentificacion(),request.getId());
            GetClienteByIdAndTypeResponse response = new GetClienteByIdAndTypeResponse();
            response.setCliente(cliente);
            return response;
        } catch (Exception ex){
            log.error("El cliente con el ID: {} no existe en la base de datos", request.getId());
            throw new CreateException("Mensaje: " + "El cliente con ID:" + request.getId() + " no existe en la base de datos");
        }

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getClienteRequest")
    @ResponsePayload
    public GetClienteResponse obtenerClientes(@RequestPayload GetClienteRequest request) throws RuntimeException {

        try {
            List<Cliente> clientes = this.service.getAll();
            GetClienteResponse response = new GetClienteResponse();
            response.setCliente(clientes);
            return response;
        } catch (Exception e) {
            log.error("No existen clientes en la base de datos{} ", e.getMessage(), e.getStackTrace());
            throw new RuntimeException();
        }

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createdClienteReturnIdRequest")
    @ResponsePayload
    public CreatedClienteReturnIdResponse crearClienteReturnId(@RequestPayload CreatedClienteReturnIdRequest request) throws RuntimeException {
        String clienteId = this.service.createClienteReturnId(request.getCliente());
        try {
            CreatedClienteReturnIdResponse response = new CreatedClienteReturnIdResponse();
            response.setId(clienteId);
            return response;
        } catch (Exception e) {
            log.error("{} ", e.getMessage(), e.getStackTrace());
            throw new RuntimeException();
        }

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createdClienteRequest")
    @ResponsePayload
    public CreatedClienteResponse crearCliente(@RequestPayload CreatedClienteRequest request) throws CreateException {
        String errorMessage = "Error al crear CLiente.";
        try {
            log.info("The CLIENTE with this information: {} will be inserted in the database", request.getCliente());
            Cliente clienteTmp = this.service.createCliente(request.getCliente());
            CreatedClienteResponse response = new CreatedClienteResponse();
            response.setCliente(clienteTmp);
            return response;
        } catch (Exception e) {
            String exceptionMessage = e.getMessage();
            log.error("An error occurred while creating the CLIENTE {} - Returning a Bad Request - Caused by: {}",
                    request.getCliente(),
                    exceptionMessage);
            throw new CreateException("Error: " + exceptionMessage);
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "eliminarClienteRequest")
    @ResponsePayload
    public EliminarClienteResponse eliminarCliente(@RequestPayload EliminarClienteRequest request) throws UpdateException {
        try {
            Cliente cliente = this.service.getByTipoIdentificacionAndIdentifiacion(request.getTipoIdentificacion(), request.getIdentificacion());
            this.service.deleteCliente(cliente);
            log.info("se elimino");
            EliminarClienteResponse response = new EliminarClienteResponse();
            response.setCliente(cliente);
            return response;
        } catch (Exception e) {
            EliminarClienteResponse response = new EliminarClienteResponse();
            return response;
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "modificarEstadoClienteBancaWebRequest")
    @ResponsePayload
    public ModificarEstadoClienteBancaWebResponse modificarEstadoClienteBancaWebRequest(@RequestPayload ModificarEstadoClienteBancaWebRequest request) throws UpdateException {
        String errorMessage = "Error al modificar estado de Cliente.";
        try {
            Optional<Cliente> cliente = this.service.getById(request.getId());
            cliente.get().setEstadoBancaWeb("S");
            this.service.modifyCliente(cliente.get());
            ModificarEstadoClienteBancaWebResponse response = new ModificarEstadoClienteBancaWebResponse();
            response.setCliente(cliente.get());
            return response;
        } catch (Exception e) {
            GenericDetailSerializer errorResponse;
            errorResponse = new GenericDetailSerializer(
                    errorMessage, e.getMessage());
            ModificarEstadoClienteBancaWebResponse response = new ModificarEstadoClienteBancaWebResponse();
            return response;
        }
    }

}
