package co.edu.icesi.demo.controlador;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.demo.dto.EnvioCajeroDTO;
import co.edu.icesi.demo.dto.RespuestaAlCajeroDTO;
import co.edu.icesi.demo.modelo.Cuentas;
import co.edu.icesi.demo.vista.IDelegadoDeNegocio;

@RestController
@RequestMapping("/servicioCajero")
public class ServicioCajero {

	private final static long CEDULA_ROBOT_TRANS = 888888888L;

	@Autowired
	private IDelegadoDeNegocio delegadoDeNegocio;

	@RequestMapping(value = "/consultarCajero", method = RequestMethod.POST)
	public RespuestaAlCajeroDTO darInformacionDeRetiro(@RequestBody EnvioCajeroDTO envioDelCajero) {

		String mensaje = "";
		RespuestaAlCajeroDTO rta = new RespuestaAlCajeroDTO();
		rta.setSaldoNuevo(new BigDecimal("-1"));
		rta.setSePuedeRetirar(false);

		try {
			
			Cuentas cuenta = delegadoDeNegocio.consultarCuentasPorID(envioDelCajero.getNoCuentaDigitado());

			if (cuenta == null) {
				mensaje = "La cuenta no existe!";
			}

			else if (!envioDelCajero.getClaveDigitado().trim().equals(cuenta.getCueClave())) {
				mensaje = "La clave es incorrecta!";
			}

			else if (envioDelCajero.getValorDigitado().intValue() > cuenta.getCueSaldo().intValue()) {
				mensaje = "No hay suficiente saldo!";
				rta.setSaldoNuevo(cuenta.getCueSaldo());

			} else {

				String cueNumero = envioDelCajero.getNoCuentaDigitado();
				BigDecimal valor = envioDelCajero.getValorDigitado();

				BigDecimal saldoNuevo = cuenta.getCueSaldo().subtract(valor);

				delegadoDeNegocio.retirar(cueNumero, valor, cuenta.getClientes().getCliId(), CEDULA_ROBOT_TRANS,
						"Retiro Desde Cajero");

				mensaje = "Retiro Exitoso!";
				rta.setSaldoNuevo(saldoNuevo);
				rta.setSePuedeRetirar(true);

			}
			
			rta.setMensajeError(mensaje);
			return rta;

		} catch (Exception e) {
			mensaje = e.getMessage();

			rta.setMensajeError(mensaje);
			return rta;
		}
		
	}

}
