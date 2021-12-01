/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquisoft.cliente.validation;

import lombok.extern.slf4j.Slf4j;
import static org.springframework.data.mongodb.core.validation.Validator.document;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Diana
 */
@Slf4j
public class ClienteCedulaValidation {

    public boolean identificacionValida(String identificacion) {
        byte sum = 0;
        try {
            int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
            Integer suma = 0;
            int digito = 0;
            for (int i = 0; i < (identificacion.length() - 1); i++) {
                digito = Integer.parseInt(identificacion.substring(i, i + 1)) * coefValCedula[i];
                suma += ((digito % 10) + (digito / 10));
            }
            suma = suma % 10 == 0 ? 0 : 10 - suma % 10;
            if (suma == Integer.parseInt(Character.toString(identificacion.charAt(9)))) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("La Cedula ingresada no valida", e.getMessage());

        }
        return false;
    }

}
