package co.edu.icesi.demo.vista;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import co.edu.icesi.demo.modelo.Clientes;
import co.edu.icesi.demo.modelo.Cuentas;
import co.edu.icesi.demo.util.FacesUtils;

@ViewScoped
@ManagedBean(name = "loginVista")
public class LoginVista {

	@ManagedProperty(value = "#{authenticationManager}")
	private AuthenticationManager authenticationManager = null;

	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	private String userId;
	private String password;

	private InputText txtCedulaCliente;
	private InputText txtClaveCuentaCliente;

	private List<SelectItem> cuentasCliente;

	private SelectOneMenu menuCuentas;

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public String loginComoColaborador() {

		try {

			Authentication request = new UsernamePasswordAuthenticationToken(this.getUserId(), this.getPassword());

			Authentication result = authenticationManager.authenticate(request); // proovedor

			SecurityContext securityContext = SecurityContextHolder.getContext();

			securityContext.setAuthentication(result);

			System.out.println("");
			System.out.println("Autoridad: " + result.getAuthorities().toString());
			System.out.println("");

			((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true))
					.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

			return "/final_initialMenuColaborador.xhtml";

		} catch (AuthenticationException e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario o password no son validos", ""));
			return "/login.xhtml";
		} catch (NumberFormatException e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Campo 'Cedula' no es validos", ""));
			e.printStackTrace();
			return "/login.xhtml";

		} catch (Exception e) {
			e.printStackTrace();
			return "/login.xhtml";

		}

	}

	public String loginComoCliente() {
		try {

			long cedulaCliente = Long.parseLong(txtCedulaCliente.getValue().toString());
			String Nocuenta = menuCuentas.getValue().toString();
			String claveCta = txtClaveCuentaCliente.getValue().toString();

			Clientes clienteDigitado = delegadoDeNegocio.consultarClientePorID(cedulaCliente);

			if (clienteDigitado == null) {
				throw new Exception("No Existe un cliente con esa cedula!");
			}

			Cuentas cuenta = delegadoDeNegocio.consultarCuentasPorID(Nocuenta);

			if (cuenta == null) {
				throw new Exception("No Existe una cuenta con ese número!");
			}

			long cliDueño = cuenta.getClientes().getCliId();

			if (!(cedulaCliente + "").equalsIgnoreCase(cliDueño + "")) {
				throw new Exception("La cuenta no pertecene al Cliente ingresado!");
			}

			if (!cuenta.getCueClave().equals(claveCta)) {
				throw new Exception("La clave de la Cuenta es Incorrecta!");
			}

			FacesUtils.putinSession("kCliente", clienteDigitado);

			return "/final_initialMenuCliente.xhtml";

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));

			System.out.println(e.getMessage());
			return "/login.xhtml";
		}

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public InputText getTxtCedulaCliente() {
		return txtCedulaCliente;
	}

	public void setTxtCedulaCliente(InputText txtCedulaCliente) {
		this.txtCedulaCliente = txtCedulaCliente;
	}

	public SelectOneMenu getMenuCuentas() {
		return menuCuentas;
	}

	public void setMenuCuentas(SelectOneMenu menuCuentas) {
		this.menuCuentas = menuCuentas;
	}

	public InputText getTxtClaveCuentaCliente() {
		return txtClaveCuentaCliente;
	}

	public void setTxtClaveCuentaCliente(InputText txtClaveCuentaCliente) {
		this.txtClaveCuentaCliente = txtClaveCuentaCliente;
	}

	public void setCuentasCliente(List<SelectItem> cuentasCliente) {
		this.cuentasCliente = cuentasCliente;
	}

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	public List<SelectItem> getCuentasCliente() {

		return cuentasCliente;
	}

	public void txtCedulaListener() {

		try {

			String id = txtCedulaCliente.getValue().toString().trim();

			if (id.equals("")) {
				txtClaveCuentaCliente.resetValue();
				menuCuentas.resetValue();

			}

			else {
				long cedulaCliente = Long.parseLong(id);

				Clientes clientes = delegadoDeNegocio.consultarClientePorID(cedulaCliente);

				if (clientes != null) {

					cuentasCliente = new ArrayList<SelectItem>();

					List<Cuentas> lasCuentas = delegadoDeNegocio.consultarCuentasDeUnCliente(clientes.getCliId());

					for (Cuentas ctaActual : lasCuentas) {

						SelectItem selectItem = new SelectItem(ctaActual.getCueNumero(), ctaActual.getCueNumero());

						cuentasCliente.add(selectItem);
					}

				}

				else {
					cuentasCliente = null;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
