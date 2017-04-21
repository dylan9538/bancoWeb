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
import org.primefaces.component.password.Password;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.demo.modelo.TiposUsuarios;
import co.edu.icesi.demo.modelo.Usuarios;

@ManagedBean
@ViewScoped
public class UsuarioVista {

	private final static Logger log = LoggerFactory.getLogger(UsuarioVista.class);

	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	private InputText txtCedula;
	private InputText txtNombre;
	private InputText txtLogin;
	private Password txtClave;

	private SelectOneMenu sonTiposUsuarios;

	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	private CommandButton btnLimpiar;

	private List<Usuarios> losUsuarios;

	private List<SelectItem> losItemsTipoUsuario;

	public void txtIdentificacionListener() {
		log.info("txtIdentificacionListener");

		Usuarios usuarios = null;
		try {
			Long id = Long.parseLong(txtCedula.getValue().toString());

			usuarios = delegadoDeNegocio.consultarUsuariosPorID(id);
		} catch (Exception e) {
		}

		if (usuarios == null) {

			btnCrear.setDisabled(false);
			btnBorrar.setDisabled(true);
			btnModificar.setDisabled(true);

			txtClave.resetValue();
			txtNombre.resetValue();
			txtLogin.resetValue();
			sonTiposUsuarios.setValue("-1");

		} else {

			btnCrear.setDisabled(true);
			btnBorrar.setDisabled(false);
			btnModificar.setDisabled(false);

			txtClave.setValue(usuarios.getUsuClave());
			txtNombre.setValue(usuarios.getUsuNombre());
			txtLogin.setValue(usuarios.getUsuLogin());

			sonTiposUsuarios.setValue(usuarios.getTiposUsuarios().getTusuCodigo());

		}
	}

	public String crearAction() {
		log.info("crearAction");

		try {

			Usuarios usuarios = new Usuarios();
			Long identificacion = Long.parseLong(txtCedula.getValue().toString());

			usuarios.setUsuCedula(identificacion);
			usuarios.setUsuClave(txtClave.getValue().toString());
			usuarios.setUsuNombre(txtNombre.getValue().toString());
			usuarios.setUsuLogin(txtLogin.getValue().toString());
			usuarios.setTiposUsuarios(delegadoDeNegocio
					.consultarTiposUsuariosPorID(Long.parseLong(sonTiposUsuarios.getValue().toString())));

			delegadoDeNegocio.crearUsuarios(usuarios);

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario se creó con exito.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}

		return "";
	}

	public String modificarAction() {
		log.info("modificarAction");

		try {

			Usuarios usuarios = new Usuarios();
			Long identificacion = Long.parseLong(txtCedula.getValue().toString());

			usuarios.setUsuCedula(identificacion);
			usuarios.setUsuClave(txtClave.getValue().toString());
			usuarios.setUsuNombre(txtNombre.getValue().toString());
			usuarios.setUsuLogin(txtLogin.getValue().toString());
			usuarios.setTiposUsuarios(delegadoDeNegocio
					.consultarTiposUsuariosPorID(Long.parseLong(sonTiposUsuarios.getValue().toString())));

			delegadoDeNegocio.modificarUsuarios(usuarios);

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario se modificó con exito.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}

		return "";
	}

	public String borrarAction() {
		log.info("borrarAction");

		try {

			Usuarios usuarios = new Usuarios();
			Long identificacion = Long.parseLong(txtCedula.getValue().toString());

			usuarios.setUsuCedula(identificacion);
			usuarios.setUsuClave(txtClave.getValue().toString());
			usuarios.setUsuNombre(txtNombre.getValue().toString());
			usuarios.setUsuLogin(txtLogin.getValue().toString());
			usuarios.setTiposUsuarios(delegadoDeNegocio
					.consultarTiposUsuariosPorID(Long.parseLong(sonTiposUsuarios.getValue().toString())));

			delegadoDeNegocio.borrarUsuarios(usuarios);

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario se eliminó con exito.", ""));
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

		txtCedula.resetValue();
		txtClave.resetValue();
		txtNombre.resetValue();
		txtLogin.resetValue();
		sonTiposUsuarios.setValue("-1");

		return null;
	}

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	public List<Usuarios> getLosUsuarios() {
		try {
			if (losUsuarios == null) {
				losUsuarios = delegadoDeNegocio.consultarTodosUsuarios();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return losUsuarios;
	}

	public void setLosUsuarios(List<Usuarios> losUsuarios) {
		this.losUsuarios = losUsuarios;
	}

	public InputText getTxtIdentificacion() {
		return txtCedula;
	}

	public void setTxtIdentificacion(InputText txtIdentificacion) {
		this.txtCedula = txtIdentificacion;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public InputText getTxtTelefono() {
		return txtLogin;
	}

	public void setTxtTelefono(InputText txtTelefono) {
		this.txtLogin = txtTelefono;
	}

	public Password getTxtDireccion() {
		return txtClave;
	}

	public void setTxtDireccion(Password txtDireccion) {
		this.txtClave = txtDireccion;
	}

	public SelectOneMenu getSonTiposDocumentos() {
		return sonTiposUsuarios;
	}

	public void setSonTiposDocumentos(SelectOneMenu sonTiposDocumentos) {
		this.sonTiposUsuarios = sonTiposDocumentos;
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

	public List<SelectItem> getLosItemsTipoUsuario() {

		try {

			if (losItemsTipoUsuario == null) {
				List<TiposUsuarios> losTiposUsuarios = delegadoDeNegocio.consultarTodosTiposUsuarios();

				losItemsTipoUsuario = new ArrayList<SelectItem>();

				for (TiposUsuarios tiposUsuarios : losTiposUsuarios) {

					SelectItem selectItem = new SelectItem(tiposUsuarios.getTusuCodigo(),
							tiposUsuarios.getTusuNombre());

					losItemsTipoUsuario.add(selectItem);
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return losItemsTipoUsuario;
	}

	public void setLosItemsTipoUsuario(List<SelectItem> losItemsTipoUsuario) {
		this.losItemsTipoUsuario = losItemsTipoUsuario;
	}

}
