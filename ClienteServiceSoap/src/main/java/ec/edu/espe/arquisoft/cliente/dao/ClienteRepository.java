/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquisoft.cliente.dao;

import ec.edu.espe.arquisoft.cliente.ws.Cliente;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Diana
 */
public interface ClienteRepository extends MongoRepository<Cliente, String> {

    Cliente findByTipoIdentificacionAndIdentificacion(String tipoIdentificacion, String identificacion);
    List<Cliente> findByEstadoBancaWeb(String estadoBancaWeb);
    Integer countByEstadoBancaWeb(String estadoBancaWeb);


}
