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

import co.edu.icesi.demo.modelo.TiposDocumentos;

@ManagedBean
@ViewScoped
public class TipoDocumentoVista {

	private final static Logger log = LoggerFactory.getLogger(TipoDocumentoVista.class);

	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	private InputText txtCodigo;
	private InputText txtNombre;

	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	private CommandButton btnLimpiar;

	private List<TiposDocumentos> losTipoDocumento;

	public void txtIdentificacionListener() {
		log.info("txtIdentificacionListener");

		TiposDocumentos tipoDocumento = null;
		try {
			Long id = Long.parseLong(txtCodigo.getValue().toString());

			tipoDocumento = delegadoDeNegocio.consultarTiposDocumentosPorID(id);
		} catch (Exception e) {
		}

		if (tipoDocumento == null) {

			btnCrear.setDisabled(false);
			btnBorrar.setDisabled(true);
			btnModificar.setDisabled(true);

			txtNombre.resetValue();

		} else {

			btnCrear.setDisabled(true);
			btnBorrar.setDisabled(false);
			btnModificar.setDisabled(false);

			txtNombre.setValue(tipoDocumento.getTdocNombre());

		}
	}

	public String crearAction() {
		log.info("crearAction");

		try {

			TiposDocumentos tiposDocumentos = new TiposDocumentos();
			Long identificacion = Long.parseLong(txtCodigo.getValue().toString());

			tiposDocumentos.setTdocCodigo(identificacion);
			tiposDocumentos.setTdocNombre(txtNombre.getValue().toString());

			delegadoDeNegocio.crearTiposDocumentos(tiposDocumentos);

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El tipo documento se creó con exito.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}

		return "";
	}

	public String modificarAction() {
		log.info("modificarAction");

		try {

			TiposDocumentos tiposDocumentos = new TiposDocumentos();
			Long identificacion = Long.parseLong(txtCodigo.getValue().toString());

			tiposDocumentos.setTdocCodigo(identificacion);
			tiposDocumentos.setTdocNombre(txtNombre.getValue().toString());

			delegadoDeNegocio.modificarTiposDocumentos(tiposDocumentos);

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El tipo documento se modificó con exito.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}

		return "";
	}

	public String borrarAction() {
		log.info("borrarAction");

		try {

			TiposDocumentos tiposDocumentos = new TiposDocumentos();
			Long identificacion = Long.parseLong(txtCodigo.getValue().toString());

			tiposDocumentos.setTdocCodigo(identificacion);
			tiposDocumentos.setTdocNombre(txtNombre.getValue().toString());

			delegadoDeNegocio.borrarTiposDocumentos(tiposDocumentos);

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El tipo documento se eliminó con exito.", ""));
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

	public List<TiposDocumentos> getLosTipoDocumento() {
		try {
			if (losTipoDocumento == null) {
				losTipoDocumento = delegadoDeNegocio.consultarTodosTiposDocumentos();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return losTipoDocumento;
	}

	public void setLosTipoDocumento(List<TiposDocumentos> losTipoDocumento) {
		this.losTipoDocumento = losTipoDocumento;
	}

	public InputText getTxtCodigo() {
		return txtCodigo;
	}

	public void setTxtCodigo(InputText txtCodigo) {
		this.txtCodigo = txtCodigo;
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
