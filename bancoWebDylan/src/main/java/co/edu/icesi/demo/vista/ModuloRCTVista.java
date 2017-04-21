package co.edu.icesi.demo.vista;

import java.util.ArrayList;
import java.util.Calendar;
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
import co.edu.icesi.demo.modelo.CuentasRegistradas;
import co.edu.icesi.demo.modelo.TiposDocumentos;
import co.edu.icesi.demo.util.FacesUtils;

@ManagedBean
@ViewScoped
public class ModuloRCTVista {

	private final static Logger log = LoggerFactory.getLogger(ModuloRCTVista.class);

	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	private Clientes clienteLogueado = (Clientes) FacesUtils.getfromSession("kCliente");

	private InputText txtNoIdentificacion, txtNoCuenta, txtDescripcion;

	private SelectOneMenu menuTiposDocumentos, menuRegistradas;

	private List<SelectItem> losItemsTipoDocumento, listaRegistradas;

	private CommandButton btnAsociar;

	public void txtCedulaListener() {

		try {

			long cedula = Long.parseLong(txtNoIdentificacion.getValue().toString());

			Clientes cliente = delegadoDeNegocio.consultarClientePorID(cedula);

			if (cliente == null || txtNoCuenta.getValue() == null) {
				btnAsociar.setDisabled(true);
			} else {
				btnAsociar.setDisabled(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void txtNoCuentaListener() {

		try {

			String noCuenta = txtNoCuenta.getValue().toString();

			Cuentas cuenta = delegadoDeNegocio.consultarCuentasPorID(noCuenta);

			if (cuenta == null || txtNoIdentificacion.getValue() == null) {
				btnAsociar.setDisabled(true);
			} else {
				btnAsociar.setDisabled(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String asociarCuenta() {
		System.out.println("CUENTA ASOCIADA!");

		try {

			if (txtNoIdentificacion.getValue() == null || txtNoCuenta.getValue() == null) {
				throw new Exception("Los campos no pueden ser nulos!");
			}

			long cedula = Long.parseLong(txtNoIdentificacion.getValue().toString());

			Clientes clienteDigitado = delegadoDeNegocio.consultarClientePorID(cedula);

			String noCuenta = txtNoCuenta.getValue().toString();

			Cuentas cuentaDigitada = delegadoDeNegocio.consultarCuentasPorID(noCuenta);

			if (clienteDigitado == null) {
				throw new Exception("El cliente no existe!");
			}

			if (cuentaDigitada == null) {
				throw new Exception("La cuenta no existe!");
			}
			
			
			TiposDocumentos tipo = delegadoDeNegocio.consultarTiposDocumentosPorID(
					Long.parseLong(menuTiposDocumentos.getValue().toString()));
			
			if (tipo== null){
				throw new Exception("Seleccione un Tipo de Documento, por favor!");
			}
			
			if (!(tipo.getTdocCodigo() + "").trim().equalsIgnoreCase(
					clienteDigitado.getTiposDocumentos().getTdocCodigo()+"")){
				throw new Exception("El tipo de Documento ingresado"
						+ "no corresponde con el del Cliente!");
			}

			if (!(cuentaDigitada.getClientes().getCliId() + "").equalsIgnoreCase(clienteDigitado.getCliId() + "")) {
				throw new Exception("La cuenta ingresada no pertenece al usuario ingresado!");
			}

			if (cuentaYaFueRegistrada(clienteLogueado, cuentaDigitada)){
				throw new Exception("La cuenta ya ha sido registrada, anteriormente!");
			}
			
			CuentasRegistradas nCuentaReg = new CuentasRegistradas();
			nCuentaReg.setClientes(clienteLogueado);
			nCuentaReg.setCuentas(cuentaDigitada);

			nCuentaReg.setCueregCodigo(Long.parseLong(generarNumeroCuenta()));

			nCuentaReg.setCueregDescripcion(txtDescripcion.getValue().toString());

			delegadoDeNegocio.asociarCuenta(nCuentaReg);

			listaRegistradas = null;

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cuenta Asociada! ", ""));

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));

			log.info(e.getMessage());

			e.printStackTrace();
		}

		return "";
	}
	
	private boolean cuentaYaFueRegistrada(Clientes cliente, Cuentas cuenta) throws Exception {

		List<CuentasRegistradas> cuentas = delegadoDeNegocio.consultarCuentasRegistradasDeUnCliente(cliente.getCliId());
		boolean esta = false;
		
		for (int i=0; i< cuentas.size() && !esta;i++){
			
			CuentasRegistradas cuentaAct =  cuentas.get(i);
			
			if (cuentaAct.getCuentas().getCueNumero().trim().equalsIgnoreCase(cuenta.getCueNumero().trim())){
				esta =true;
			}
			
		}
		
		return esta;
	}

	public String generarNumeroCuenta() {

		Calendar calendario = Calendar.getInstance();

		String año = calendario.get(Calendar.YEAR) + "";
		año = año.substring(2, 4);

		String mes = (calendario.get(Calendar.MONTH) + 1) + "";
		String dia = calendario.get(Calendar.DATE) + "";

		String hora = calendario.get(Calendar.HOUR_OF_DAY) + "";
		String minuto = calendario.get(Calendar.MINUTE) + "";
		String segundos = calendario.get(Calendar.SECOND) + "";

		String tiempoEje = System.currentTimeMillis() + "";
		tiempoEje = tiempoEje.substring(tiempoEje.length() - 4, tiempoEje.length());

		String cueNumero = año + mes + dia + hora + minuto + segundos + tiempoEje;

		return cueNumero;
	}
	

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	public InputText getTxtNoCuenta() {
		return txtNoCuenta;
	}

	public void setTxtNoCuenta(InputText txtNoCuenta) {
		this.txtNoCuenta = txtNoCuenta;
	}

	public SelectOneMenu getMenuTiposDocumentos() {
		return menuTiposDocumentos;
	}

	public void setMenuTiposDocumentos(SelectOneMenu menuTiposDocumentos) {
		this.menuTiposDocumentos = menuTiposDocumentos;
	}

	public CommandButton getBtnAsociar() {
		return btnAsociar;
	}

	public void setBtnAsociar(CommandButton btnAsociar) {
		this.btnAsociar = btnAsociar;
	}

	public void setLosItemsTipoDocumento(List<SelectItem> losItemsTipoDocumento) {
		this.losItemsTipoDocumento = losItemsTipoDocumento;
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

	public InputText getTxtNoIdentificacion() {
		return txtNoIdentificacion;
	}

	public void setTxtNoIdentificacion(InputText txtNoIdentificacion) {
		this.txtNoIdentificacion = txtNoIdentificacion;
	}

	public InputText getTxtDescripcion() {
		return txtDescripcion;
	}

	public void setTxtDescripcion(InputText txtDescripcion) {
		this.txtDescripcion = txtDescripcion;
	}

	public SelectOneMenu getMenuRegistradas() {
		return menuRegistradas;
	}

	public void setMenuRegistradas(SelectOneMenu menuRegistradas) {
		this.menuRegistradas = menuRegistradas;
	}

	public List<SelectItem> getListaRegistradas() {

		if (listaRegistradas == null) {
			List<CuentasRegistradas> cuentas = delegadoDeNegocio
					.consultarCuentasRegistradasDeUnCliente(clienteLogueado.getCliId());

			listaRegistradas = new ArrayList<SelectItem>();

			for (CuentasRegistradas cuentaReg : cuentas) {
				SelectItem selectItem = new SelectItem(cuentaReg.getCuentas().getCueNumero(),
						cuentaReg.getCuentas().getCueNumero());

				listaRegistradas.add(selectItem);
			}

		}

		return listaRegistradas;
	}

	public void setListaRegistradas(List<SelectItem> listaRegistradas) {
		this.listaRegistradas = listaRegistradas;
	}

}
