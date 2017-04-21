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
import co.edu.icesi.demo.modelo.TiposDocumentos;

@ManagedBean
@ViewScoped
public class ClienteVista {

	private final static Logger log = LoggerFactory.getLogger(ClienteVista.class);

	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	private InputText txtIdentificacion;
	private InputText txtNombre;
	private InputText txtTelefono;
	private InputText txtDireccion;
	private InputText txtMail;

	private SelectOneMenu sonTiposDocumentos;

	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	private CommandButton btnLimpiar;

	private List<Clientes> losClientes;

	private List<SelectItem> losItemsTipoDocumento;

	public void txtIdentificacionListener() {
		log.info("txtIdentificacionListener");

		Clientes clientes = null;
		try {
			Long id = Long.parseLong(txtIdentificacion.getValue().toString());

			clientes = delegadoDeNegocio.consultarClientePorID(id);
		} catch (Exception e) {
		}

		if (clientes == null) {

			btnCrear.setDisabled(false);
			btnBorrar.setDisabled(true);
			btnModificar.setDisabled(true);

			txtDireccion.resetValue();
			txtNombre.resetValue();
			txtMail.resetValue();
			txtTelefono.resetValue();
			sonTiposDocumentos.setValue("-1");

		} else {

			btnCrear.setDisabled(true);
			btnBorrar.setDisabled(false);
			btnModificar.setDisabled(false);

			txtDireccion.setValue(clientes.getCliDireccion());
			txtNombre.setValue(clientes.getCliNombre());
			txtMail.setValue(clientes.getCliMail());
			txtTelefono.setValue(clientes.getCliTelefono());

			sonTiposDocumentos.setValue(clientes.getTiposDocumentos().getTdocCodigo());

		}
	}

	public String crearAction() {
		log.info("crearAction");

		try {

			Clientes clientes = new Clientes();
			Long identificacion = Long.parseLong(txtIdentificacion.getValue().toString());

			clientes.setCliId(identificacion);
			clientes.setCliDireccion(txtDireccion.getValue().toString());
			clientes.setCliNombre(txtNombre.getValue().toString());
			clientes.setCliTelefono(txtTelefono.getValue().toString());
			clientes.setCliMail(txtMail.getValue().toString());
			clientes.setTiposDocumentos(delegadoDeNegocio
					.consultarTiposDocumentosPorID(Long.parseLong(sonTiposDocumentos.getValue().toString())));

			delegadoDeNegocio.crearCliente(clientes);

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El cliente se creó con exito.", ""));
			losClientes = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}

		return "";
	}

	public String modificarAction() {
		log.info("modificarAction");

		try {

			Clientes clientes = new Clientes();
			Long identificacion = Long.parseLong(txtIdentificacion.getValue().toString());

			clientes.setCliId(identificacion);
			clientes.setCliDireccion(txtDireccion.getValue().toString());
			clientes.setCliNombre(txtNombre.getValue().toString());
			clientes.setCliTelefono(txtTelefono.getValue().toString());
			clientes.setCliMail(txtMail.getValue().toString());
			clientes.setTiposDocumentos(delegadoDeNegocio
					.consultarTiposDocumentosPorID(Long.parseLong(sonTiposDocumentos.getValue().toString())));

			delegadoDeNegocio.modificarCliente(clientes);

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El cliente se modificó con exito.", ""));
			losClientes = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}

		return "";
	}

	public String borrarAction() {
		log.info("borrarAction");

		try {

			Clientes clientes = new Clientes();
			Long identificacion = Long.parseLong(txtIdentificacion.getValue().toString());

			clientes.setCliId(identificacion);
			clientes.setCliDireccion(txtDireccion.getValue().toString());
			clientes.setCliNombre(txtNombre.getValue().toString());
			clientes.setCliTelefono(txtTelefono.getValue().toString());
			clientes.setCliMail(txtMail.getValue().toString());
			clientes.setTiposDocumentos(delegadoDeNegocio
					.consultarTiposDocumentosPorID(Long.parseLong(sonTiposDocumentos.getValue().toString())));

			delegadoDeNegocio.borrarCliente(clientes);

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El cliente se eliminó con exito.", ""));
			losClientes = null;
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

		txtIdentificacion.resetValue();
		txtDireccion.resetValue();
		txtNombre.resetValue();
		txtMail.resetValue();
		txtTelefono.resetValue();
		sonTiposDocumentos.setValue("-1");
		losClientes = null;
		return null;
	}

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	public List<Clientes> getLosClientes() {
		try {
			if (losClientes == null) {
				losClientes = delegadoDeNegocio.consultarTodosClientes();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return losClientes;
	}

	public void setLosClientes(List<Clientes> losClientes) {
		this.losClientes = losClientes;
	}

	public InputText getTxtIdentificacion() {
		return txtIdentificacion;
	}

	public void setTxtIdentificacion(InputText txtIdentificacion) {
		this.txtIdentificacion = txtIdentificacion;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public InputText getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(InputText txtTelefono) {
		this.txtTelefono = txtTelefono;
	}

	public InputText getTxtDireccion() {
		return txtDireccion;
	}

	public void setTxtDireccion(InputText txtDireccion) {
		this.txtDireccion = txtDireccion;
	}

	public InputText getTxtMail() {
		return txtMail;
	}

	public void setTxtMail(InputText txtMail) {
		this.txtMail = txtMail;
	}

	public SelectOneMenu getSonTiposDocumentos() {
		return sonTiposDocumentos;
	}

	public void setSonTiposDocumentos(SelectOneMenu sonTiposDocumentos) {
		this.sonTiposDocumentos = sonTiposDocumentos;
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

	public List<SelectItem> getLosItemsTipoDocumento() {

		try {

			if (losItemsTipoDocumento == null) {
				List<TiposDocumentos> losTiposDocumentos = delegadoDeNegocio.consultarTodosTiposDocumentos();

				losItemsTipoDocumento = new ArrayList<SelectItem>();

				for (TiposDocumentos tiposDocumentos : losTiposDocumentos) {

					SelectItem selectItem = new SelectItem(tiposDocumentos.getTdocCodigo(),
							tiposDocumentos.getTdocNombre());

					losItemsTipoDocumento.add(selectItem);
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return losItemsTipoDocumento;
	}

	public void setLosItemsTipoDocumento(List<SelectItem> losItemsTipoDocumento) {
		this.losItemsTipoDocumento = losItemsTipoDocumento;
	}

}
