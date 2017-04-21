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
import co.edu.icesi.demo.util.FacesUtils;

@ManagedBean
@ViewScoped
public class ModuloCambioClaveVista {
	
	private final static Logger log = LoggerFactory.getLogger(ModuloCambioClaveVista.class);

	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;
	
	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	private SelectOneMenu menuCuentas;
	
	private List<SelectItem> losItemsCuentas;

	private InputText txtClaveActual;
	private InputText txtClaveNueva;
	private InputText txtClaveConfirmada;

	private CommandButton btnModificarClave;

	public SelectOneMenu getMenuCuentas() {
		return menuCuentas;
	}

	public void setMenuCuentas(SelectOneMenu menuCuentas) {
		this.menuCuentas = menuCuentas;
	}

	public List<SelectItem> getLosItemsCuentas() {
		
		Clientes clienteLogueado =(Clientes)FacesUtils.getfromSession("kCliente"); 
		
		try {

			if (losItemsCuentas == null) {
				
				List<Cuentas> cuentasCliente = delegadoDeNegocio.consultarCuentasDeUnCliente(clienteLogueado.getCliId());

				losItemsCuentas = new ArrayList<SelectItem>();

				for (Cuentas ctaActual : cuentasCliente) {

					SelectItem selectItem = new SelectItem(ctaActual.getCueNumero(),
							ctaActual.getCueNumero());

					losItemsCuentas.add(selectItem);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return losItemsCuentas;
	}

	public void setLosItemsCuentas(List<SelectItem> losItemsCuentas) {
		this.losItemsCuentas = losItemsCuentas;
	}

	public InputText getTxtClaveActual() {
		
		
		return txtClaveActual;
	}

	public void setTxtClaveActual(InputText txtClaveActual) {
		this.txtClaveActual = txtClaveActual;
	}

	public InputText getTxtClaveNueva() {
		return txtClaveNueva;
	}

	public void setTxtClaveNueva(InputText txtClaveNueva) {
		this.txtClaveNueva = txtClaveNueva;
	}

	public InputText getTxtClaveConfirmada() {
		return txtClaveConfirmada;
	}

	public void setTxtClaveConfirmada(InputText txtClaveConfirmada) {
		this.txtClaveConfirmada = txtClaveConfirmada;
	}

	public CommandButton getBtnModificarClave() {
		return btnModificarClave;
	}

	public void setBtnModificarClave(CommandButton btnModificarClave) {
		this.btnModificarClave = btnModificarClave;
	}
	
	public String actualizarClaveAction(){
		
		log.info("Clave Modificada!");
		try {
			
			String claveCuenta= txtClaveActual.getValue().toString();
			String claveNueva =txtClaveNueva.getValue().toString();
			String claveConfirmada =txtClaveConfirmada.getValue().toString();

			String noCuentaSeleccionada = menuCuentas.getValue().toString();
			
			Cuentas ctaSeleccionada = delegadoDeNegocio.consultarCuentasPorID(noCuentaSeleccionada);
			
			if (ctaSeleccionada==null){
				throw new Exception("No ha seleccionado ni una Cuenta!");
			}
			
			if (!ctaSeleccionada.getCueClave().equals(claveCuenta)){
				throw new Exception("Clave Digitada incorrecta!");
			}
			
			if (!claveNueva.equals(claveConfirmada)){
				throw new Exception("Los campos de la contraseña nueva no coinciden!");
			}
			
			ctaSeleccionada.setCueClave(claveNueva);
			
			delegadoDeNegocio.modificarCuentas(ctaSeleccionada);
			

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Clave modificada exitosámente!", ""));
			
		} catch (Exception e) {
			
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			
			e.printStackTrace();
		}
		
		return "";
		
	}
	
}
