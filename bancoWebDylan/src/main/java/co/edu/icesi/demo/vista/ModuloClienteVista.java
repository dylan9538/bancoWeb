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
public class ModuloClienteVista {

	private final static Logger log = LoggerFactory.getLogger(ModuloClienteVista.class);
	
	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	private InputText txtClienteId, txtClienteNombre, txtClienteDireccion, txtClienteTelefono
					,txtClienteEmail;
	
	public void txtIdentificacionListener() {
		log.info("txtIdentificacionListener");

		Clientes clientes = null;
		try {
			Long id = Long.parseLong(txtClienteId.getValue().toString());

			clientes = delegadoDeNegocio.consultarClientePorID(id);
		} catch (Exception e) {
		}

		if (clientes == null) {

			btnCrear.setDisabled(false);
			btnBorrar.setDisabled(true);
			btnModificar.setDisabled(true);

			txtClienteDireccion.resetValue();
			txtClienteNombre.resetValue();
			txtClienteEmail.resetValue();
			txtClienteTelefono.resetValue();
			menuTiposDocumentos.setValue("-1");

		} else {

			btnCrear.setDisabled(true);
			btnBorrar.setDisabled(false);
			btnModificar.setDisabled(false);

			txtClienteDireccion.setValue(clientes.getCliDireccion());
			txtClienteNombre.setValue(clientes.getCliNombre());
			txtClienteEmail.setValue(clientes.getCliMail());
			txtClienteTelefono.setValue(clientes.getCliTelefono());

			menuTiposDocumentos.setValue(clientes.getTiposDocumentos().getTdocCodigo());

		}
	}

	public String crearAction() {
		log.info("crearAction");

		try {

			Clientes clientes = new Clientes();
			Long identificacion = Long.parseLong(txtClienteId.getValue().toString());

			clientes.setCliId(identificacion);
			clientes.setCliDireccion(txtClienteDireccion.getValue().toString());
			clientes.setCliNombre(txtClienteNombre.getValue().toString());
			clientes.setCliTelefono(txtClienteTelefono.getValue().toString());
			clientes.setCliMail(txtClienteEmail.getValue().toString());
			clientes.setTiposDocumentos(delegadoDeNegocio
					.consultarTiposDocumentosPorID(Long.parseLong(menuTiposDocumentos.getValue().toString())));

			delegadoDeNegocio.crearCliente(clientes);
			losClientes = null;

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro Exitoso!", ""));
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			log.info(e.getMessage());
		}

		return "";
	}

	public String modificarAction() {
		log.info("modificarAction");

		try {

			Clientes clientes = new Clientes();
			Long identificacion = Long.parseLong(txtClienteId.getValue().toString());

			clientes.setCliId(identificacion);
			clientes.setCliDireccion(txtClienteDireccion.getValue().toString());
			clientes.setCliNombre(txtClienteNombre.getValue().toString());
			clientes.setCliTelefono(txtClienteTelefono.getValue().toString());
			clientes.setCliMail(txtClienteEmail.getValue().toString());
			clientes.setTiposDocumentos(delegadoDeNegocio
					.consultarTiposDocumentosPorID(Long.parseLong(menuTiposDocumentos.getValue().toString())));

			delegadoDeNegocio.modificarCliente(clientes);

		
			losClientes = null;
			
			
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización Exitosa!", ""));
			
			
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
			Long identificacion = Long.parseLong(txtClienteId.getValue().toString());

			clientes.setCliId(identificacion);
			clientes.setCliDireccion(txtClienteDireccion.getValue().toString());
			clientes.setCliNombre(txtClienteNombre.getValue().toString());
			clientes.setCliTelefono(txtClienteTelefono.getValue().toString());
			clientes.setCliMail(txtClienteEmail.getValue().toString());
			clientes.setTiposDocumentos(delegadoDeNegocio
					.consultarTiposDocumentosPorID(Long.parseLong(menuTiposDocumentos.getValue().toString())));

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

		txtClienteId.resetValue();
		txtClienteDireccion.resetValue();
		txtClienteNombre.resetValue();
		txtClienteEmail.resetValue();
		txtClienteTelefono.resetValue();
		menuTiposDocumentos.setValue("-1");
		losClientes = null;
		return null;
	}
	
	
	
	
	public InputText getTxtClienteId() {
		return txtClienteId;
	}
	public void setTxtClienteId(InputText txtClienteId) {
		this.txtClienteId = txtClienteId;
	}
	public InputText getTxtClienteNombre() {
		return txtClienteNombre;
	}
	public void setTxtClienteNombre(InputText txtClienteNombre) {
		this.txtClienteNombre = txtClienteNombre;
	}
	public InputText getTxtClienteDireccion() {
		return txtClienteDireccion;
	}
	public void setTxtClienteDireccion(InputText txtClienteDireccion) {
		this.txtClienteDireccion = txtClienteDireccion;
	}
	public InputText getTxtClienteTelefono() {
		return txtClienteTelefono;
	}
	public void setTxtClienteTelefono(InputText txtClienteTelefono) {
		this.txtClienteTelefono = txtClienteTelefono;
	}
	public InputText getTxtClienteEmail() {
		return txtClienteEmail;
	}
	public void setTxtClienteEmail(InputText txtClienteEmail) {
		this.txtClienteEmail = txtClienteEmail;
	}
	public SelectOneMenu getMenuTiposDocumentos() {
		return menuTiposDocumentos;
	}
	public void setMenuTiposDocumentos(SelectOneMenu menuTiposDocumentos) {
		this.menuTiposDocumentos = menuTiposDocumentos;
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
	private SelectOneMenu menuTiposDocumentos;
	private List<SelectItem> losItemsTipoDocumento;

	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	
	private List<Clientes> losClientes;
	
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
	
	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}
	
	
}
