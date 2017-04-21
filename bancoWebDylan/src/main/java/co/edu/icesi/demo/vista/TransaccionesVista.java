package co.edu.icesi.demo.vista;

import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.demo.modelo.Cuentas;

@ManagedBean
@ViewScoped
public class TransaccionesVista {

	private final static Logger log = LoggerFactory.getLogger(TransaccionesVista.class);

	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	private InputText txtCueNumeroRetiro;
	private InputText txtvalorRetiro;
	private InputText txtIdentificacionClienteRetiro;
	private InputText txtUsuCedulaRetiro;
	private InputText txtDescripcionRetiro;

	private InputText txtCueNumeroConsignacion;
	private InputText txtvalorConsignacion;
	private InputText txtIdentificacionClienteConsignacion;
	private InputText txtUsuCedulaConsignacion;
	private InputText txtDescripcionConsignacion;

	private InputText txtCueNumeroOrigen;
	private InputText txtCueNumeroDestino;
	private InputText txtIdentificacionClienteOrigen;
	private InputText txtIdentificacionClienteDestino;
	private InputText txtvalor;
	private InputText txtUsuCedula;
	private InputText txtDescripcion;

	private CommandButton btnRetirar;
	private CommandButton btnConsignar;
	private CommandButton btnTrasladar;
	private CommandButton btnLimpiarRetiro;
	private CommandButton btnLimpiarConsignacion;
	private CommandButton btnLimpiarTraslado;
	
	private int aux;

	public String limpiarRetiroAction() {
		log.info("limpiarRetiroAction");

		txtCueNumeroRetiro.resetValue();
		txtvalorRetiro.resetValue();
		txtIdentificacionClienteRetiro.resetValue();
		txtUsuCedulaRetiro.resetValue();
		txtDescripcionRetiro.resetValue();
		
		btnRetirar.setDisabled(true);

		return null;
	}

	public String limpiarConsignacionAction() {
		log.info("limpiarConsignacionAction");

		txtCueNumeroConsignacion.resetValue();
		txtvalorConsignacion.resetValue();
		txtIdentificacionClienteConsignacion.resetValue();
		txtUsuCedulaConsignacion.resetValue();
		txtDescripcionConsignacion.resetValue();

		btnConsignar.setDisabled(true);
		
		return null;
	}

	public String limpiarTrasladoAction() {
		log.info("limpiarTrasladoAction");

		txtCueNumeroOrigen.resetValue();
		txtCueNumeroDestino.resetValue();
		txtIdentificacionClienteOrigen.resetValue();
		txtIdentificacionClienteDestino.resetValue();
		txtUsuCedula.resetValue();
		txtDescripcion.resetValue();
		aux = 0;

		btnTrasladar.setDisabled(true);
		
		return null;
	}
	
	public void retirarAction(){
		
		log.info("retiroAction");
		
		String cueNumero = txtCueNumeroRetiro.getValue().toString();
		BigDecimal valor = BigDecimal.valueOf(Long.valueOf(txtvalorRetiro.getValue().toString()));
		Long identificacionCliente = Long.valueOf(txtIdentificacionClienteRetiro.getValue().toString());
		Long usuCedula = Long.valueOf(txtUsuCedulaRetiro.getValue().toString());
		String descripcion = txtDescripcionRetiro.getValue().toString();
		
		try {
			delegadoDeNegocio.retirar(cueNumero, valor, identificacionCliente, usuCedula, descripcion);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Retiro éxitoso.", ""));
		} catch (Exception e) {

			log.info(e.getMessage());
		}
	}
	
	public void consignarAction(){
		
		log.info("consignarAction");
		
		String cueNumero = txtCueNumeroConsignacion.getValue().toString();
		BigDecimal valor = BigDecimal.valueOf(Long.valueOf(txtvalorConsignacion.getValue().toString()));
		Long identificacionCliente = Long.valueOf(txtIdentificacionClienteConsignacion.getValue().toString());
		Long usuCedula = Long.valueOf(txtUsuCedulaConsignacion.getValue().toString());
		String descripcion = txtDescripcionConsignacion.getValue().toString();
		
		try {
			delegadoDeNegocio.consignar(cueNumero, valor, identificacionCliente, usuCedula, descripcion);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Consignación éxitosa.", ""));
		} catch (Exception e) {
			
			log.info(e.getMessage());
		}
	}
	
	public void trasladarAction(){
		
		log.info("trasladarAction");
		
		String cueNumeroOrigen = txtCueNumeroOrigen.getValue().toString();
		String cueNumeroDestino = txtCueNumeroDestino.getValue().toString();
		BigDecimal valor = BigDecimal.valueOf(Long.valueOf(txtvalor.getValue().toString()));
		Long identificacionClienteOrigen = Long.valueOf(txtIdentificacionClienteOrigen.getValue().toString());
		Long identificacionClienteDestino = Long.valueOf(txtIdentificacionClienteDestino.getValue().toString());
		Long usuCedula = Long.valueOf(txtUsuCedula.getValue().toString());
		String descripcion = txtDescripcion.getValue().toString();
		
		try {
			delegadoDeNegocio.transladoEntreCuentas(cueNumeroOrigen, cueNumeroDestino, valor, identificacionClienteOrigen, identificacionClienteDestino, usuCedula, descripcion);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Traslado éxitoso.", ""));
		} catch (Exception e) {
			
			log.info(e.getMessage());
		}
		
	}
	
	public void traerClienteRetiro(){
		log.info("traerClienteRetiro");
		
		Cuentas cuenta = null;
		
		try{
			
			String cueNumero = txtCueNumeroRetiro.getValue().toString();
			
			cuenta = delegadoDeNegocio.consultarCuentasPorID(cueNumero);
			
		}catch(Exception e){
		}
		
		if(cuenta != null){
			
			txtIdentificacionClienteRetiro.setValue(cuenta.getClientes().getCliId());
			btnRetirar.setDisabled(false);
			
		}else{
			
			btnRetirar.setDisabled(true);
			
			txtvalorRetiro.resetValue();
			txtIdentificacionClienteRetiro.resetValue();
			txtUsuCedulaRetiro.resetValue();
			txtDescripcionRetiro.resetValue();
		}
	}
	
	public void traerClienteConsignacion(){
		log.info("traerClienteConsignacion");
		
		Cuentas cuenta = null;
		
		try{
			
			String cueNumero = txtCueNumeroConsignacion.getValue().toString();
			
			cuenta = delegadoDeNegocio.consultarCuentasPorID(cueNumero);
			
		}catch(Exception e){
		}
		
		if(cuenta != null){
			
			txtIdentificacionClienteConsignacion.setValue(cuenta.getClientes().getCliId());
			btnConsignar.setDisabled(false);
			
		}else{
			
			btnConsignar.setDisabled(true);
			
			txtvalorConsignacion.resetValue();
			txtIdentificacionClienteConsignacion.resetValue();
			txtUsuCedulaConsignacion.resetValue();
			txtDescripcionConsignacion.resetValue();
		}
	}
	
	public void traerClienteOrigenTraslado(){
		log.info("traerClienteOrigenTraslado");
		
		Cuentas cuenta = null;
		
		try{
			
			String cueNumero = txtCueNumeroOrigen.getValue().toString();
			
			cuenta = delegadoDeNegocio.consultarCuentasPorID(cueNumero);
			
		}catch(Exception e){
		}
		
		if(cuenta != null){
			
			txtIdentificacionClienteOrigen.setValue(cuenta.getClientes().getCliId());
			
			aux++;
			
			if(aux == 2){
				btnTrasladar.setDisabled(false);
			}
			
		}else{
			

			txtIdentificacionClienteOrigen.resetValue();
			txtIdentificacionClienteDestino.resetValue();
			txtUsuCedula.resetValue();
			txtDescripcion.resetValue();
			
			btnTrasladar.setDisabled(false);			
		}
	}
	
	public void traerClienteDestinoTraslado(){
		log.info("traerClienteDestinoTraslado");
		
		Cuentas cuenta = null;
		
		try{
			
			String cueNumero = txtCueNumeroDestino.getValue().toString();
			
			cuenta = delegadoDeNegocio.consultarCuentasPorID(cueNumero);
			
		}catch(Exception e){
		}
		
		if(cuenta != null){
			
			txtIdentificacionClienteDestino.setValue(cuenta.getClientes().getCliId());
			
			aux++;
			
			if(aux == 2){
				btnTrasladar.setDisabled(false);
			}
			
		}else{
			

			txtIdentificacionClienteOrigen.resetValue();
			txtIdentificacionClienteDestino.resetValue();
			txtUsuCedula.resetValue();
			txtDescripcion.resetValue();
			
			btnTrasladar.setDisabled(false);			
		}
	}

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	public InputText getTxtCueNumeroRetiro() {
		return txtCueNumeroRetiro;
	}

	public void setTxtCueNumeroRetiro(InputText txtCueNumeroRetiro) {
		this.txtCueNumeroRetiro = txtCueNumeroRetiro;
	}

	public InputText getTxtvalorRetiro() {
		return txtvalorRetiro;
	}

	public void setTxtvalorRetiro(InputText txtvalorRetiro) {
		this.txtvalorRetiro = txtvalorRetiro;
	}

	public InputText getTxtIdentificacionClienteRetiro() {
		return txtIdentificacionClienteRetiro;
	}

	public void setTxtIdentificacionClienteRetiro(InputText txtIdentificacionClienteRetiro) {
		this.txtIdentificacionClienteRetiro = txtIdentificacionClienteRetiro;
	}

	public InputText getTxtUsuCedulaRetiro() {
		return txtUsuCedulaRetiro;
	}

	public void setTxtUsuCedulaRetiro(InputText txtUsuCedulaRetiro) {
		this.txtUsuCedulaRetiro = txtUsuCedulaRetiro;
	}

	public InputText getTxtDescripcionRetiro() {
		return txtDescripcionRetiro;
	}

	public void setTxtDescripcionRetiro(InputText txtDescripcionRetiro) {
		this.txtDescripcionRetiro = txtDescripcionRetiro;
	}

	public InputText getTxtCueNumeroConsignacion() {
		return txtCueNumeroConsignacion;
	}

	public void setTxtCueNumeroConsignacion(InputText txtCueNumeroConsignacion) {
		this.txtCueNumeroConsignacion = txtCueNumeroConsignacion;
	}

	public InputText getTxtvalorConsignacion() {
		return txtvalorConsignacion;
	}

	public void setTxtvalorConsignacion(InputText txtvalorConsignacion) {
		this.txtvalorConsignacion = txtvalorConsignacion;
	}

	public InputText getTxtIdentificacionClienteConsignacion() {
		return txtIdentificacionClienteConsignacion;
	}

	public void setTxtIdentificacionClienteConsignacion(InputText txtIdentificacionClienteConsignacion) {
		this.txtIdentificacionClienteConsignacion = txtIdentificacionClienteConsignacion;
	}

	public InputText getTxtUsuCedulaConsignacion() {
		return txtUsuCedulaConsignacion;
	}

	public void setTxtUsuCedulaConsignacion(InputText txtUsuCedulaConsignacion) {
		this.txtUsuCedulaConsignacion = txtUsuCedulaConsignacion;
	}

	public InputText getTxtDescripcionConsignacion() {
		return txtDescripcionConsignacion;
	}

	public void setTxtDescripcionConsignacion(InputText txtDescripcionConsignacion) {
		this.txtDescripcionConsignacion = txtDescripcionConsignacion;
	}

	public InputText getTxtCueNumeroOrigen() {
		return txtCueNumeroOrigen;
	}

	public void setTxtCueNumeroOrigen(InputText txtCueNumeroOrigen) {
		this.txtCueNumeroOrigen = txtCueNumeroOrigen;
	}

	public InputText getTxtCueNumeroDestino() {
		return txtCueNumeroDestino;
	}

	public void setTxtCueNumeroDestino(InputText txtCueNumeroDestino) {
		this.txtCueNumeroDestino = txtCueNumeroDestino;
	}

	public InputText getTxtIdentificacionClienteOrigen() {
		return txtIdentificacionClienteOrigen;
	}

	public void setTxtIdentificacionClienteOrigen(InputText txtIdentificacionClienteOrigen) {
		this.txtIdentificacionClienteOrigen = txtIdentificacionClienteOrigen;
	}

	public InputText getTxtIdentificacionClienteDestino() {
		return txtIdentificacionClienteDestino;
	}

	public void setTxtIdentificacionClienteDestino(InputText txtIdentificacionClienteDestino) {
		this.txtIdentificacionClienteDestino = txtIdentificacionClienteDestino;
	}

	public InputText getTxtUsuCedula() {
		return txtUsuCedula;
	}

	public void setTxtUsuCedula(InputText txtUsuCedula) {
		this.txtUsuCedula = txtUsuCedula;
	}

	public InputText getTxtDescripcion() {
		return txtDescripcion;
	}

	public void setTxtDescripcion(InputText txtDescripcion) {
		this.txtDescripcion = txtDescripcion;
	}

	public CommandButton getBtnRetirar() {
		return btnRetirar;
	}

	public void setBtnRetirar(CommandButton btnRetirar) {
		this.btnRetirar = btnRetirar;
	}

	public CommandButton getBtnConsignar() {
		return btnConsignar;
	}

	public void setBtnConsignar(CommandButton btnConsignar) {
		this.btnConsignar = btnConsignar;
	}

	public CommandButton getBtnTrasladar() {
		return btnTrasladar;
	}

	public void setBtnTrasladar(CommandButton btnTrasladar) {
		this.btnTrasladar = btnTrasladar;
	}

	public CommandButton getBtnLimpiarRetiro() {
		return btnLimpiarRetiro;
	}

	public void setBtnLimpiarRetiro(CommandButton btnLimpiarRetiro) {
		this.btnLimpiarRetiro = btnLimpiarRetiro;
	}

	public CommandButton getBtnLimpiarConsignacion() {
		return btnLimpiarConsignacion;
	}

	public void setBtnLimpiarConsignacion(CommandButton btnLimpiarConsignacion) {
		this.btnLimpiarConsignacion = btnLimpiarConsignacion;
	}

	public CommandButton getBtnLimpiarTraslado() {
		return btnLimpiarTraslado;
	}

	public void setBtnLimpiarTraslado(CommandButton btnLimpiarTraslado) {
		this.btnLimpiarTraslado = btnLimpiarTraslado;
	}

	public InputText getTxtvalor() {
		return txtvalor;
	}

	public void setTxtvalor(InputText txtvalor) {
		this.txtvalor = txtvalor;
	}
	
	
}