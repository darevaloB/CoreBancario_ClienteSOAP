/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espe.arquisoft.cliente.service;

import ec.edu.espe.arquisoft.cliente.wsdl.Cliente;
import ec.edu.espe.arquisoft.cliente.wsdl.CreatedClienteRequest;
import ec.edu.espe.arquisoft.cliente.wsdl.CreatedClienteResponse;
import ec.edu.espe.arquisoft.cliente.wsdl.CreatedClienteReturnIdRequest;
import ec.edu.espe.arquisoft.cliente.wsdl.CreatedClienteReturnIdResponse;
import ec.edu.espe.arquisoft.cliente.wsdl.EliminarClienteRequest;
import ec.edu.espe.arquisoft.cliente.wsdl.EliminarClienteResponse;
import ec.edu.espe.arquisoft.cliente.wsdl.GetClienteByIdRequest;
import ec.edu.espe.arquisoft.cliente.wsdl.GetClienteByIdResponse;
import ec.edu.espe.arquisoft.cliente.wsdl.GetClienteRequest;
import ec.edu.espe.arquisoft.cliente.wsdl.GetClienteResponse;
import ec.edu.espe.arquisoft.cliente.wsdl.ModificarEstadoClienteBancaWebRequest;
import ec.edu.espe.arquisoft.cliente.wsdl.ModificarEstadoClienteBancaWebResponse;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Service
@Slf4j
public class ClienteService extends WebServiceGatewaySupport {

    private String endpoint = "http://localhost:8080/ws/cliente.wsdl";

    public List<Cliente> getAll() {
        GetClienteRequest request = new GetClienteRequest();
        GetClienteResponse response = (GetClienteResponse) getWebServiceTemplate()
                .marshalSendAndReceive(endpoint, request);

        return response.getCliente();
    }
    
      public Cliente getClienteId(String id) {
        GetClienteByIdRequest request= new GetClienteByIdRequest();
        request.setId(id);
        GetClienteByIdResponse response= 
                (  GetClienteByIdResponse) getWebServiceTemplate().marshalSendAndReceive(endpoint, request);
        
        log.info("Respuesta obtenida de SOAP {} ", response.getCliente().toString());
    
        return response.getCliente();  
    }
      
       public String createdClienteReturnId(Cliente clienteRQ) {
        CreatedClienteReturnIdRequest request = new CreatedClienteReturnIdRequest();
         request.setCliente(clienteRQ);
         CreatedClienteReturnIdResponse response= 
                 (CreatedClienteReturnIdResponse) getWebServiceTemplate().marshalSendAndReceive(endpoint,request);
         return response.getId();
        
    }
       
       
       
        public  void modificarEstadoClienteBancaWeb(String id) {
            ModificarEstadoClienteBancaWebRequest request = new ModificarEstadoClienteBancaWebRequest();
         request.setId(id);
         ModificarEstadoClienteBancaWebResponse response= 
                ( ModificarEstadoClienteBancaWebResponse) getWebServiceTemplate().marshalSendAndReceive(endpoint, request);
        
        log.info("Respuesta obtenida de SOAP {} ", response.getCliente().toString());
        
    }
        public Cliente createCliente(Cliente cliente) {
          CreatedClienteRequest request = new CreatedClienteRequest();
         request.setCliente(cliente);
         CreatedClienteResponse response= 
                ( CreatedClienteResponse) getWebServiceTemplate().marshalSendAndReceive(endpoint, request);
        
        log.info("Respuesta obtenida de SOAP {} ", response.getCliente().toString());
        return response.getCliente();
    }
       
    public Cliente eliminarCliente(String tipoIdentificacion, String identificacion) {
         EliminarClienteRequest request = new EliminarClienteRequest();
         request.setTipoIdentificacion(tipoIdentificacion);
         request.setIdentificacion(identificacion);
         EliminarClienteResponse response= 
                ( EliminarClienteResponse) getWebServiceTemplate().marshalSendAndReceive(endpoint, request);
        
        log.info("Respuesta obtenida de SOAP {} ", response.getCliente().toString());
        return response.getCliente();

      
    }
       

}
