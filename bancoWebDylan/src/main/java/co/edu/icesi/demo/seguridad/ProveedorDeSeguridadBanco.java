package co.edu.icesi.demo.seguridad;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import co.edu.icesi.demo.modelo.Usuarios;
import co.edu.icesi.demo.util.FacesUtils;
import co.edu.icesi.demo.vista.IDelegadoDeNegocio;

@Scope("singleton")
@Component("proveedorDeSeguridadBanco")
public class ProveedorDeSeguridadBanco implements AuthenticationProvider {

	@Autowired
	private IDelegadoDeNegocio delegadoDeNegocio;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();

		try {
			Usuarios usuarioLogueado = delegadoDeNegocio.validarUsuario(name, password);

			if (usuarioLogueado != null) {

				final List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
				//grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
				
				String rol = usuarioLogueado.getTiposUsuarios().getTusuNombre().trim();
				grantedAuths.add(new SimpleGrantedAuthority(rol));
				
				System.out.println("");
				System.out.println("ROL: " + rol);
				System.out.println("");

				final UserDetails principal = new User(name, password, grantedAuths);
				final Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);

				// Poner el usuario en la session
				FacesUtils.putinSession("kUsuario", usuarioLogueado);

				return auth;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
