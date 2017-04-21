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

import co.edu.icesi.demo.modelo.Clientes;
import co.edu.icesi.demo.modelo.Consignaciones;
import co.edu.icesi.demo.modelo.Cuentas;
import co.edu.icesi.demo.modelo.Retiros;
import co.edu.icesi.demo.modelo.TiposDocumentos;
import co.edu.icesi.demo.modelo.Transferencias;
import co.edu.icesi.demo.util.FacesUtils;

@ManagedBean
@ViewScoped
public class ModuloInfoPersonalVista {

	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	private Clientes clienteLogueado = (Clientes) FacesUtils.getfromSession("kCliente");

	private InputText txtCliId, txtTipoDoc, txtCliNombre,
	txtSaldoCue, txtCliDireccion, txtCliTelefono, txtCliEmail;
	
	private SelectOneMenu menuCuentas;

	private List<SelectItem> listaCuentas;

	private CommandButton btnActualizarDatos;

	private List<Consignaciones> consignaciones;
	private List<Retiros> retiros;
	private List<Transferencias> transferencias;

	public void txtMenuCuentasListener() {

		try {
			Cuentas cuenta = delegadoDeNegocio.consultarCuentasPorID(menuCuentas.getValue().toString());

			if (cuenta != null) {

				consignaciones = delegadoDeNegocio.consultarConsignaciones(cuenta.getCueNumero());
				retiros = delegadoDeNegocio.consultarRetiros(cuenta.getCueNumero());
				transferencias = delegadoDeNegocio.consultarTraslados(cuenta.getCueNumero());
				
				txtSaldoCue.setValue(cuenta.getCueSaldo());
			}

			else {
				consignaciones = null;
				retiros = null;
				transferencias = null;
				txtSaldoCue.resetValue();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	public InputText getTxtCliId() {
		return txtCliId;
	}

	public void setTxtCliId(InputText txtCliId) {
		this.txtCliId = txtCliId;
	}

	public InputText getTxtTipoDoc() {
		return txtTipoDoc;
	}

	public void setTxtTipoDoc(InputText txtTipoDoc) {
		this.txtTipoDoc = txtTipoDoc;
	}

	public InputText getTxtCliNombre() {
		return txtCliNombre;
	}

	public void setTxtCliNombre(InputText txtCliNombre) {
		this.txtCliNombre = txtCliNombre;
	}

	public InputText getTxtCliDireccion() {
		return txtCliDireccion;
	}

	public void setTxtCliDireccion(InputText txtCliDireccion) {
		this.txtCliDireccion = txtCliDireccion;
	}

	public InputText getTxtCliTelefono() {
		return txtCliTelefono;
	}

	public void setTxtCliTelefono(InputText txtCliTelefono) {
		this.txtCliTelefono = txtCliTelefono;
	}

	public InputText getTxtCliEmail() {
		return txtCliEmail;
	}

	public void setTxtCliEmail(InputText txtCliEmail) {
		this.txtCliEmail = txtCliEmail;
	}

	public CommandButton getBtnActualizarDatos() {
		return btnActualizarDatos;
	}

	public void setBtnActualizarDatos(CommandButton btnActualizarDatos) {
		this.btnActualizarDatos = btnActualizarDatos;
	}

	public Clientes getClienteLogueado() {
		return clienteLogueado;
	}

	public void setClienteLogueado(Clientes clienteLogueado) {
		this.clienteLogueado = clienteLogueado;
	}

	public String getTipoDocumento() {

		try {
			TiposDocumentos tDoc = delegadoDeNegocio
					.consultarTiposDocumentosPorID(clienteLogueado.getTiposDocumentos().getTdocCodigo());

			return tDoc.getTdocNombre();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public void actualizarAction() {

		System.out.println("Actualizar Información");

		try {

			Object direccion = txtCliDireccion.getValue();
			Object telefono = txtCliTelefono.getValue();
			Object email = txtCliEmail.getValue();

			if (direccion == null || email == null || telefono == null) {
				throw new Exception("Los Campos No Pueden Ser Vacíos!");
			}

			String cDireccion = direccion.toString();
			String sTelefono = telefono.toString();
			String sEmail = email.toString();

			long cedula = Long.parseLong(txtCliId.getValue().toString());

			Clientes cliente = delegadoDeNegocio.consultarClientePorID(cedula);

			cliente.setCliDireccion(cDireccion);
			cliente.setCliMail(sEmail);
			cliente.setCliTelefono(sTelefono);

			delegadoDeNegocio.modificarCliente(cliente);

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos Actualizados!", ""));

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
			e.printStackTrace();
		}

	}

	public SelectOneMenu getMenuCuentas() {
		return menuCuentas;
	}

	public void setMenuCuentas(SelectOneMenu menuCuentas) {
		this.menuCuentas = menuCuentas;
	}

	public List<SelectItem> getListaCuentas() {
		try {
			List<Cuentas> cuentasCli = delegadoDeNegocio.consultarCuentasDeUnCliente(clienteLogueado.getCliId());

			listaCuentas = new ArrayList<SelectItem>();

			for (Cuentas cuenta : cuentasCli) {
				SelectItem select = new SelectItem(cuenta.getCueNumero(), cuenta.getCueNumero());

				listaCuentas.add(select);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaCuentas;

	}

	public void setListaCuentas(List<SelectItem> listaCuentas) {
		this.listaCuentas = listaCuentas;
	}

	public List<Consignaciones> getConsignaciones() {
		return consignaciones;
	}

	public void setConsignaciones(List<Consignaciones> consignaciones) {
		this.consignaciones = consignaciones;
	}

	public List<Retiros> getRetiros() {
		return retiros;
	}

	public void setRetiros(List<Retiros> retiros) {
		this.retiros = retiros;
	}

	public List<Transferencias> getTransferencias() {
		return transferencias;
	}

	public void setTraslados(List<Transferencias> traslados) {
		this.transferencias = traslados;
	}

	public InputText getTxtSaldoCue() {
		return txtSaldoCue;
	}

	public void setTxtSaldoCue(InputText txtSaldoCue) {
		this.txtSaldoCue = txtSaldoCue;
	}

}
