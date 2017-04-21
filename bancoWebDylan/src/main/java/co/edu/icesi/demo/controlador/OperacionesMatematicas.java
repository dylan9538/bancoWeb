package co.edu.icesi.demo.controlador;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.demo.dto.ResultadoDTO;

@RestController
@RequestMapping("/operaciones")
public class OperacionesMatematicas {
	
	@RequestMapping(value="/sumar/{numero_uno}/{numero_dos}",method=RequestMethod.GET)
	public ResultadoDTO sumar(@PathVariable("numero_uno")int n1, @PathVariable("numero_dos")int n2){
		ResultadoDTO resultadoDTO=new ResultadoDTO();
		resultadoDTO.setValor(n1+n2);
		return resultadoDTO;
	}

}
