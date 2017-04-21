package co.edu.icesi.demo.vista;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.demo.modelo.Clientes;
import co.edu.icesi.demo.modelo.Cuentas;

@ManagedBean
@ViewScoped

public class ModuloCuentaVista {

	private final static Logger log = LoggerFactory.getLogger(ModuloCuentaVista.class);

	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	private InputText txtClienteId, txtEstadoCuenta, txtCuentaGenerada;
	private CommandButton btnGenerar, btnActivar, btnDesacivar;

	private SelectOneMenu menuCuentas;
	private List<SelectItem> cuentasCliente;

	public void txtCedulaListener() {

		try {

			Clientes clientes = null;

			if (txtClienteId.getValue().toString().trim().equals("")) {
				cuentasCliente = null;
				btnActivar.setDisabled(true);
				btnDesacivar.setDisabled(true);
				txtEstadoCuenta.resetValue();
				txtCuentaGenerada.resetValue();
				btnGenerar.setDisabled(true);
			}

			else {
				long cedulaCliente = Long.parseLong(txtClienteId.getValue().toString());

				clientes = delegadoDeNegocio.consultarClientePorID(cedulaCliente);
			}

			if (clientes != null) {

				cuentasCliente = new ArrayList<SelectItem>();

				List<Cuentas> lasCuentas = delegadoDeNegocio.consultarCuentasDeUnCliente(clientes.getCliId());

				for (Cuentas ctaActual : lasCuentas) {

					SelectItem selectItem = new SelectItem(ctaActual.getCueNumero(), ctaActual.getCueNumero());

					cuentasCliente.add(selectItem);
				}

				btnActivar.setDisabled(false);
				btnDesacivar.setDisabled(false);
				btnGenerar.setDisabled(false);

			}

			else {
				btnGenerar.setDisabled(true);
				cuentasCliente = null;
				btnActivar.setDisabled(true);
				btnDesacivar.setDisabled(true);
				txtEstadoCuenta.resetValue();
				txtCuentaGenerada.resetValue();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void txtMenuCuentasListener() {

		try {

			String noCuenta = menuCuentas.getValue().toString();

			Cuentas cuenta = delegadoDeNegocio.consultarCuentasPorID(noCuenta);

			if (cuenta == null) {
				menuCuentas.setValue("-1");
			} else {
				String activa = cuenta.getCueActiva();

				if (activa.trim().equalsIgnoreCase("S")) {

					btnDesacivar.setDisabled(false);
					btnActivar.setDisabled(true);
				}

				else {
					btnDesacivar.setDisabled(true);
					btnActivar.setDisabled(false);
				}

				txtEstadoCuenta.setValue(activa);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<SelectItem> getCuentasCliente() {

		if (txtClienteId.getValue()!=null && cuentasCliente==null){
			
		
		
		long cedulaCliente = Long.parseLong(txtClienteId.getValue().toString());

		Clientes clientes;
		try {
			clientes = delegadoDeNegocio.consultarClientePorID(cedulaCliente);

			cuentasCliente = new ArrayList<SelectItem>();

			List<Cuentas> lasCuentas = delegadoDeNegocio.consultarCuentasDeUnCliente(clientes.getCliId());

			for (Cuentas ctaActual : lasCuentas) {

				SelectItem selectItem = new SelectItem(ctaActual.getCueNumero(), ctaActual.getCueNumero());

				cuentasCliente.add(selectItem);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		return cuentasCliente;
	}

	public CommandButton getBtnGenerar() {
		return btnGenerar;
	}

	public void setBtnGenerar(CommandButton btnGenerar) {
		this.btnGenerar = btnGenerar;
	}

	public CommandButton getBtnActivar() {
		return btnActivar;
	}

	public void setBtnActivar(CommandButton btnActivar) {
		this.btnActivar = btnActivar;
	}

	public CommandButton getBtnDesacivar() {
		return btnDesacivar;
	}

	public void setBtnDesacivar(CommandButton btnDesacivar) {
		this.btnDesacivar = btnDesacivar;
	}

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	public String generarCuentaAction() {

		try {

			if (txtClienteId.getValue() == null) {
				throw new Exception("El campo de la cedula no puede ser vacío!");
			}

			long id = Long.parseLong(txtClienteId.getValue().toString());

			Clientes clientes = delegadoDeNegocio.consultarClientePorID(id);

			if (clientes == null) {
				throw new Exception("El cliente no existe!");
			}

			Cuentas nCuenta = new Cuentas();
			nCuenta.setClientes(clientes);

			delegadoDeNegocio.crearCuentas(nCuenta);

			String mensaje = nCuenta.getCueNumero() + " - " + nCuenta.getCueClave();

			txtCuentaGenerada.setValue(mensaje);
			 cuentasCliente=null;

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cuenta Registrada! ", ""));

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));

			log.info(e.getMessage());

		}

		return "";
	}

	public String activarCuentaAction() {

		try {

			Cuentas cuentaSeleccionada = delegadoDeNegocio.consultarCuentasPorID(menuCuentas.getValue().toString());

			if (cuentaSeleccionada == null) {
				throw new Exception("Por favor, seleccione una cuenta!");
			}

			if (cuentaSeleccionada.getCueActiva().trim().equalsIgnoreCase("C")) {
				throw new Exception("Debe consignar un valor minímo de $100.000 para activar la cuenta!");
			}

			cuentaSeleccionada.setCueActiva("S");
			delegadoDeNegocio.modificarCuentas(cuentaSeleccionada);

			System.out.println("Cuenta Activada!");

			txtEstadoCuenta.setValue("S");

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cuenta Activada! ", ""));

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));

			log.info(e.getMessage());

		}
		return "";
	}

	public String desactivarCuentaAction() {

		try {

			Cuentas cuentaSeleccionada = delegadoDeNegocio.consultarCuentasPorID(menuCuentas.getValue().toString());

			if (cuentaSeleccionada == null) {
				throw new Exception("Por favor, seleccione una cuenta!");
			}

			cuentaSeleccionada.setCueActiva("N");
			delegadoDeNegocio.modificarCuentas(cuentaSeleccionada);

			System.out.println("Cuenta Desactivada!");

			txtEstadoCuenta.setValue("N");

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cuenta Desactivada! ", ""));

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));

			log.info(e.getMessage());
		}

		return "";
	}

	public InputText getTxtClienteId() {
		return txtClienteId;
	}

	public void setTxtClienteId(InputText txtClienteId) {
		this.txtClienteId = txtClienteId;
	}

	public SelectOneMenu getMenuCuentas() {
		return menuCuentas;
	}

	public void setMenuCuentas(SelectOneMenu menuCuentas) {
		this.menuCuentas = menuCuentas;
	}

	public InputText getTxtEstadoCuenta() {
		return txtEstadoCuenta;
	}

	public void setTxtEstadoCuenta(InputText txtEstadoCuenta) {
		this.txtEstadoCuenta = txtEstadoCuenta;
	}

	public InputText getTxtCuentaGenerada() {
		return txtCuentaGenerada;
	}

	public void setTxtCuentaGenerada(InputText txtCuentaGenerada) {
		this.txtCuentaGenerada = txtCuentaGenerada;
	}

}
