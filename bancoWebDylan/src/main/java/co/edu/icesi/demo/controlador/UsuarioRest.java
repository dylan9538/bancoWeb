package co.edu.icesi.demo.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.demo.dto.UsuariosDTO;
import co.edu.icesi.demo.modelo.TiposUsuarios;
import co.edu.icesi.demo.modelo.Usuarios;
import co.edu.icesi.demo.vista.IDelegadoDeNegocio;

@RestController
@RequestMapping("/usuarioREST")
public class UsuarioRest {
	
	@Autowired
	private IDelegadoDeNegocio  delegadoDeNegocio; 
	
	@RequestMapping(value="/consultarUsuario/{cedula}",method=RequestMethod.GET)
	public UsuariosDTO consultarPorId(@PathVariable("cedula")long usuCedula)throws Exception{
		
		Usuarios usuarios = delegadoDeNegocio.consultarUsuariosPorID(usuCedula);
		
		if(usuarios==null){
			return null;
		}
		
		UsuariosDTO usuarioDTO=new UsuariosDTO();
		
		usuarioDTO.setUsuCedula(usuarios.getUsuCedula());
		usuarioDTO.setUsuClave(usuarios.getUsuClave());
		usuarioDTO.setUsuLogin(usuarios.getUsuLogin());
		usuarioDTO.setUsuNombre(usuarios.getUsuNombre());
		usuarioDTO.setTusuCodigo(usuarios.getTiposUsuarios().getTusuCodigo());
		
		return usuarioDTO;
	}
	
	@RequestMapping(value="/crearUsuario",method=RequestMethod.POST)
	public void crear(@RequestBody UsuariosDTO usuarioDTO)throws Exception{
		
		if(usuarioDTO==null){
			throw new Exception("La informacion del Usuario no esta completa!");
		}
		
		Usuarios usuario=new Usuarios();
		
		usuario.setUsuCedula(usuarioDTO.getUsuCedula());
		usuario.setUsuClave(usuarioDTO.getUsuClave());
		usuario.setUsuLogin(usuarioDTO.getUsuLogin());
		usuario.setUsuNombre(usuarioDTO.getUsuNombre());
		
		TiposUsuarios tipoUsuario=delegadoDeNegocio.consultarTiposUsuariosPorID(usuarioDTO.getTusuCodigo());
		usuario.setTiposUsuarios(tipoUsuario);
		
		delegadoDeNegocio.crearUsuarios(usuario);
		
	}
	
	@RequestMapping(value="/modificarUsuario",method=RequestMethod.PUT)
	public void modificar(@RequestBody UsuariosDTO usuarioDTO)throws Exception{
		
		if(usuarioDTO==null){
			throw new Exception("La informacion del Usuario no esta completa!");
		}
		
		Usuarios usuario = delegadoDeNegocio.consultarUsuariosPorID(usuarioDTO.getUsuCedula());
		
		if(usuario==null){
			throw new Exception("El Usuario que desea modificar no existe!");
		}
		
		usuario.setUsuCedula(usuarioDTO.getUsuCedula());
		usuario.setUsuClave(usuarioDTO.getUsuClave());
		usuario.setUsuLogin(usuarioDTO.getUsuLogin());
		usuario.setUsuNombre(usuarioDTO.getUsuNombre());
		
		TiposUsuarios tipoUsuario=delegadoDeNegocio.consultarTiposUsuariosPorID(usuarioDTO.getTusuCodigo());
		usuario.setTiposUsuarios(tipoUsuario);
		
		delegadoDeNegocio.modificarUsuarios(usuario);
		
	}
	
	@RequestMapping(value="/borrarUsuario/{cedula}",method=RequestMethod.DELETE)
	public void borrar(@PathVariable("cedula")long usuCedula)throws Exception{
		
		Usuarios usuario =delegadoDeNegocio.consultarUsuariosPorID(usuCedula);
		
		if(usuario==null){
			throw new Exception("El cliente que desea borrar no existe!");
		}
		
		delegadoDeNegocio.borrarUsuarios(usuario);

	}

}
