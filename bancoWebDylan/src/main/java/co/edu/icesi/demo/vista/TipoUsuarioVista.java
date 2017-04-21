package co.edu.icesi.demo.vista;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.demo.modelo.TiposUsuarios;

@ManagedBean
@ViewScoped
public class TipoUsuarioVista {

	private final static Logger log = LoggerFactory.getLogger(TipoUsuarioVista.class);

	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	private InputText txtCodigo;
	private InputText txtNombre;

	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	private CommandButton btnLimpiar;

	private List<TiposUsuarios> losTipoUsuario;

	public void txtIdentificacionListener() {
		log.info("txtIdentificacionListener");

		TiposUsuarios tipoUsuario = null;
		try {
			Long id = Long.parseLong(txtCodigo.getValue().toString());

			tipoUsuario = delegadoDeNegocio.consultarTiposUsuariosPorID(id);
		} catch (Exception e) {
		}

		if (tipoUsuario == null) {

			btnCrear.setDisabled(false);
			btnBorrar.setDisabled(true);
			btnModificar.setDisabled(true);

			txtNombre.resetValue();

		} else {

			btnCrear.setDisabled(true);
			btnBorrar.setDisabled(false);
			btnModificar.setDisabled(false);

			txtNombre.setValue(tipoUsuario.getTusuNombre());

		}
	}

	public String crearAction() {
		log.info("crearAction");

		try {

			TiposUsuarios tiposUsuarios = new TiposUsuarios();
			Long identificacion = Long.parseLong(txtCodigo.getValue().toString());

			tiposUsuarios.setTusuCodigo(identificacion);
			tiposUsuarios.setTusuNombre(txtNombre.getValue().toString());

			delegadoDeNegocio.crearTiposUsuarios(tiposUsuarios);

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El tipo usuario se creó con exito.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}

		return "";
	}

	public String modificarAction() {
		log.info("modificarAction");

		try {

			TiposUsuarios tiposUsuarios = new TiposUsuarios();
			Long identificacion = Long.parseLong(txtCodigo.getValue().toString());

			tiposUsuarios.setTusuCodigo(identificacion);
			tiposUsuarios.setTusuNombre(txtNombre.getValue().toString());

			delegadoDeNegocio.modificarTiposUsuarios(tiposUsuarios);

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El tipo usuario se modificó con exito.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}

		return "";
	}

	public String borrarAction() {
		log.info("borrarAction");

		try {

			TiposUsuarios tiposUsuarios = new TiposUsuarios();
			Long identificacion = Long.parseLong(txtCodigo.getValue().toString());

			tiposUsuarios.setTusuCodigo(identificacion);
			tiposUsuarios.setTusuNombre(txtNombre.getValue().toString());

			delegadoDeNegocio.borrarTiposUsuarios(tiposUsuarios);

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El tipo usuario se eliminó con exito.", ""));
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

		txtCodigo.resetValue();
		txtNombre.resetValue();

		return null;
	}

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	public List<TiposUsuarios> getLosTipoUsuario() {
		try {
			if (losTipoUsuario == null) {
				losTipoUsuario = delegadoDeNegocio.consultarTodosTiposUsuarios();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return losTipoUsuario;
	}

	public void setLosTipoUsuario(List<TiposUsuarios> losTipoUsuario) {
		this.losTipoUsuario = losTipoUsuario;
	}

	public InputText getTxtIdentificacion() {
		return txtCodigo;
	}

	public void setTxtIdentificacion(InputText txtIdentificacion) {
		this.txtCodigo = txtIdentificacion;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
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
