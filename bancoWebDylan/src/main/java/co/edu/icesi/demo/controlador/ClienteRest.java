package co.edu.icesi.demo.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.demo.dto.ClientesDTO;
import co.edu.icesi.demo.modelo.Clientes;
import co.edu.icesi.demo.modelo.TiposDocumentos;
import co.edu.icesi.demo.vista.IDelegadoDeNegocio;

@RestController
@RequestMapping("/clienteREST")
public class ClienteRest {
	
	@Autowired
	private IDelegadoDeNegocio  delegadoDeNegocio; 
	
	@RequestMapping(value="/consultar/{id}",method=RequestMethod.GET)
	public ClientesDTO consultarPorId(@PathVariable("id")long cliId)throws Exception{
		
		Clientes clientes=delegadoDeNegocio.consultarClientePorID(cliId);
		
		if(clientes==null){
			return null;
		}
		ClientesDTO clientesDTO=new ClientesDTO();
		clientesDTO.setCliDireccion(clientes.getCliDireccion());
		clientesDTO.setCliId(clientes.getCliId());
		clientesDTO.setCliMail(clientes.getCliMail());
		clientesDTO.setCliNombre(clientes.getCliNombre());
		clientesDTO.setCliTelefono(clientes.getCliTelefono());
		clientesDTO.setTdocCodigo(clientes.getTiposDocumentos().getTdocCodigo());
		
		return clientesDTO;
	}
	
	@RequestMapping(value="/crear",method=RequestMethod.POST)
	public void crear(@RequestBody ClientesDTO clientesDTO)throws Exception{
		if(clientesDTO==null){
			throw new Exception("La informacion del cliente no esta completa");
		}
		
		Clientes clientes=new Clientes();
		clientes.setCliDireccion(clientesDTO.getCliDireccion());
		clientes.setCliId(clientesDTO.getCliId());
		clientes.setCliMail(clientesDTO.getCliMail());
		clientes.setCliNombre(clientesDTO.getCliNombre());
		clientes.setCliTelefono(clientesDTO.getCliTelefono());
		TiposDocumentos tiposDocumentos=delegadoDeNegocio.consultarTiposDocumentosPorID(clientesDTO.getTdocCodigo());
		clientes.setTiposDocumentos(tiposDocumentos);
		
		delegadoDeNegocio.crearCliente(clientes);
		
	}
	
	@RequestMapping(value="/modificar",method=RequestMethod.PUT)
	public void modificar(@RequestBody ClientesDTO clientesDTO)throws Exception{
		if(clientesDTO==null){
			throw new Exception("La informacion del cliente no esta completa");
		}
		
		Clientes clientes=delegadoDeNegocio.consultarClientePorID(clientesDTO.getCliId());
		if(clientes==null){
			throw new Exception("El cliente que desea modificar no existe");
		}
		clientes.setCliDireccion(clientesDTO.getCliDireccion());
		clientes.setCliId(clientesDTO.getCliId());
		clientes.setCliMail(clientesDTO.getCliMail());
		clientes.setCliNombre(clientesDTO.getCliNombre());
		clientes.setCliTelefono(clientesDTO.getCliTelefono());
		TiposDocumentos tiposDocumentos=delegadoDeNegocio.consultarTiposDocumentosPorID(clientesDTO.getTdocCodigo());
		clientes.setTiposDocumentos(tiposDocumentos);
		
		delegadoDeNegocio.modificarCliente(clientes);
		
	}
	
	@RequestMapping(value="/borrar/{id}",method=RequestMethod.DELETE)
	public void borrar(@PathVariable("id")long cliId)throws Exception{
		
		Clientes clientes=delegadoDeNegocio.consultarClientePorID(cliId);
		
		if(clientes==null){
			throw new Exception("El cliente que desea borrar no existe");
		}
		
		delegadoDeNegocio.borrarCliente(clientes);

	}

}
