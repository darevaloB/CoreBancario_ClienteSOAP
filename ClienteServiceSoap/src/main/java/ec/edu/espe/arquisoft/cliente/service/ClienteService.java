/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquisoft.cliente.service;


import ec.edu.espe.arquisoft.cliente.dao.ClienteRepository;
import ec.edu.espe.arquisoft.cliente.exception.CreateException;
import ec.edu.espe.arquisoft.cliente.validation.ClienteCedulaValidation;
import ec.edu.espe.arquisoft.cliente.ws.Cliente;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author Diana
 */
@Service
@Slf4j
public class ClienteService {

    private final ClienteRepository clienteRepo;

    public ClienteService(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    public List<Cliente> getAll() {
        return this.clienteRepo.findAll();
    }

    public Cliente getByTipoIdentificacionAndIdentifiacion(String tipoIdentificacion, String identificacion) {
        Cliente cliente = this.clienteRepo.findByTipoIdentificacionAndIdentificacion(tipoIdentificacion, identificacion);
        if (cliente == null) {
            throw new CreateException("Cliente no encontrado con ese número de identificación");
        }
        return cliente;
    }

    public String createClienteReturnId(Cliente cliente) {
        log.info("Cliente identificacion : {}", cliente.getIdentificacion());
        Cliente clienteAux = this.clienteRepo.findByTipoIdentificacionAndIdentificacion(cliente.getTipoIdentificacion(), cliente.getIdentificacion());
        log.info("Datos findByIdentificacion: {}", clienteAux);
        ClienteCedulaValidation valid = new ClienteCedulaValidation();
        if (valid.identificacionValida((cliente.getIdentificacion())) == true) {
            if (clienteAux == null) {
                cliente.setEstadoBancaWeb("N");
                cliente.setEstado("ACT");
                return this.clienteRepo.save(cliente).getId();
            } else {
                throw new CreateException("Ya existe un cliente con ese número de identificación");
            }
        } else {
            throw new CreateException("Cédula ingresada no válida");
        }

    }

    public Optional<Cliente> getById(String id) {
        return this.clienteRepo.findById(id);
    }

    public List<Cliente> getByEstadoBancaWeb(String estadoBancaWeb) {
        return this.clienteRepo.findByEstadoBancaWeb(estadoBancaWeb);
    }

    public Integer getCountEstadoBancaWeb(String estadoBancaWeb) {
        return this.clienteRepo.countByEstadoBancaWeb(estadoBancaWeb);
    }

    public Cliente createCliente(Cliente cliente) {
        log.info("Cliente identificacion : {}", cliente.getIdentificacion());
        Cliente clienteAux = this.clienteRepo.findByTipoIdentificacionAndIdentificacion(cliente.getTipoIdentificacion(), cliente.getIdentificacion());
        log.info("Datos findByIdentificacion: {}", clienteAux);
        ClienteCedulaValidation valid = new ClienteCedulaValidation();
        if (valid.identificacionValida((cliente.getIdentificacion())) == true) {
            if (clienteAux == null) {
                cliente.setEstadoBancaWeb("N");
                cliente.setEstado("ACT");
                return this.clienteRepo.save(cliente);
            } else {
                throw new CreateException("Ya existe un cliente con ese número de identificación");
            }
        } else {
            throw new CreateException("Cédula ingresada no válida");
        }

    }

    public void modifyCliente(Cliente cliente) {
        Optional<Cliente> clienteAux = this.getById(cliente.getId());
        if (clienteAux != null) {
            this.clienteRepo.save(cliente);
        } else {
            throw new CreateException("El cliente no existe, por lo tanto no se puede modificar");
        }
    }

    public Cliente deleteCliente(Cliente cliente) {
        Optional<Cliente> clienteAux = this.getById(cliente.getId());
        if (clienteAux.get() != null) {
            this.clienteRepo.delete(cliente);
        } else {
            throw new CreateException("El cliente no existe, por lo tanto no se puede eliminar");
           
        }
         return cliente;
    }

}
