package co.edu.icesi.demo.vista;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.password.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.demo.modelo.Cuentas;

@ManagedBean
@ViewScoped
public class CuentaVista {

	private final static Logger log = LoggerFactory.getLogger(CuentaVista.class);

	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	private InputText txtCueNumero;
	private InputText txtCueSaldo;
	private InputText txtCueActiva;
	private Password txtClave;

	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	private CommandButton btnLimpiar;

	private List<Cuentas> lasCuentas;

	public void txtIdentificacionListener() {
		log.info("txtIdentificacionListener");

		Cuentas cuentas = null;
		try {
			Long id = Long.parseLong(txtCueNumero.getValue().toString());

			cuentas = delegadoDeNegocio.consultarCuentasPorID(String.valueOf(id));
		} catch (Exception e) {
		}

		if (cuentas == null) {

			btnCrear.setDisabled(false);
			btnBorrar.setDisabled(true);
			btnModificar.setDisabled(true);

			txtCueSaldo.resetValue();
			txtCueActiva.resetValue();
			txtClave.resetValue();

		} else {

			btnCrear.setDisabled(true);
			btnBorrar.setDisabled(false);
			btnModificar.setDisabled(false);

			txtCueSaldo.setValue(cuentas.getCueSaldo());
			txtCueActiva.setValue(cuentas.getCueActiva());
			txtClave.setValue(cuentas.getCueClave());

		}
	}

	public String crearAction() {
		log.info("crearAction");

		try {

			Cuentas cuentas = new Cuentas();
			
			cuentas.setCueSaldo(BigDecimal.valueOf(Long.parseLong(txtCueSaldo.getValue().toString())));
			cuentas.setCueActiva(txtCueActiva.getValue().toString());
			cuentas.setCueClave(txtClave.getValue().toString());

			delegadoDeNegocio.crearCuentas(cuentas);

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "La cuenta se creó con exito.", ""));
			lasCuentas = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}

		return "";
	}

	public String modificarAction() {
		log.info("modificarAction");

		try {

			Cuentas cuentas = new Cuentas();
			Long identificacion = Long.parseLong(txtCueNumero.getValue().toString());

			cuentas.setCueNumero(String.valueOf(identificacion));
			cuentas.setCueSaldo(BigDecimal.valueOf(Long.parseLong(txtCueSaldo.getValue().toString())));
			cuentas.setCueActiva(txtCueActiva.getValue().toString());
			cuentas.setCueClave(txtClave.getValue().toString());
			
			delegadoDeNegocio.modificarCuentas(cuentas);

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "La cuenta se modificó con exito.", ""));
			lasCuentas = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}

		return "";
	}

	public String borrarAction() {
		log.info("borrarAction");

		try {

			Cuentas cuentas = new Cuentas();
			Long identificacion = Long.parseLong(txtCueNumero.getValue().toString());

			cuentas.setCueNumero(String.valueOf(identificacion));
			cuentas.setCueSaldo(BigDecimal.valueOf(Long.parseLong(txtCueSaldo.getValue().toString())));
			cuentas.setCueActiva(txtCueActiva.getValue().toString());
			cuentas.setCueClave(txtClave.getValue().toString());
			
			delegadoDeNegocio.borrarCuentas(cuentas);

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "La cuenta se borró con exito.", ""));
			lasCuentas = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}

		return "";
	}

	public String limpiarAction() {
		log.info("limpiarAction");

		btnCrear.setDisabled(true);
		btnBorrar.setDisabled(true);
		btnModificar.setDisabled(true);

		txtCueNumero.resetValue();
		txtCueSaldo.resetValue();
		txtCueActiva.resetValue();
		txtClave.resetValue();
		lasCuentas = null;
		return null;
	}

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	public List<Cuentas> getLasCuentas() {
		try {
			if (lasCuentas == null) {
				lasCuentas = delegadoDeNegocio.consultarTodasCuentas();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lasCuentas;
	}

	public void setLasCuentas(List<Cuentas> lasCuentas) {
		this.lasCuentas = lasCuentas;
	}

	public InputText getTxtCueNumero() {
		return txtCueNumero;
	}

	public void setTxtCueNumero(InputText txtCueNumero) {
		this.txtCueNumero = txtCueNumero;
	}

	public InputText getTxtCueSaldo() {
		return txtCueSaldo;
	}

	public void setTxtCueSaldo(InputText txtCueSaldo) {
		this.txtCueSaldo = txtCueSaldo;
	}

	public InputText getTxtCueActiva() {
		return txtCueActiva;
	}

	public void setTxtCueActiva(InputText txtCueActiva) {
		this.txtCueActiva = txtCueActiva;
	}

	public Password getTxtClave() {
		return txtClave;
	}

	public void setTxtClave(Password txtClave) {
		this.txtClave = txtClave;
	}

	public CommandButton getBtnCrear() {
		return btnCrear;
	}

	public void setBtnCrear(CommandButton btnCrear) {
		this.btnCrear = btnCrear;
	}

	public CommandButton getBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(CommandButton btnModificar) {
		this.btnModificar = btnModificar;
	}

	public CommandButton getBtnBorrar() {
		return btnBorrar;
	}

	public void setBtnBorrar(CommandButton btnBorrar) {
		this.btnBorrar = btnBorrar;
	}

	public CommandButton getBtnLimpiar() {
		return btnLimpiar;
	}

	public void setBtnLimpiar(CommandButton btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}
	
	

}
