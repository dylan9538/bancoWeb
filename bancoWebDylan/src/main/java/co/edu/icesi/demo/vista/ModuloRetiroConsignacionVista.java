package co.edu.icesi.demo.vista;

import java.math.BigDecimal;
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
import co.edu.icesi.demo.modelo.Cuentas;
import co.edu.icesi.demo.modelo.Usuarios;
import co.edu.icesi.demo.util.FacesUtils;

@ManagedBean
@ViewScoped
public class ModuloRetiroConsignacionVista {

	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	Usuarios userLogueado = (Usuarios) FacesUtils.getfromSession("kUsuario");

	private InputText txtCliId, txtCtaEstado, txtValor, txtDescripcion, txtSaldo;

	private CommandButton btnRetirar, btnConsignar;
	private SelectOneMenu menuCuentas;
	private List<SelectItem> cuentas;

	public void txtCedulaListener() {
		try {
			long idCliente = Long.parseLong(txtCliId.getValue().toString());

			Clientes cliente;

			cliente = delegadoDeNegocio.consultarClientePorID(idCliente);

			if (cliente == null) {
				btnConsignar.setDisabled(true);
				btnRetirar.setDisabled(true);
				cuentas = null;
				txtValor.resetValue();
				txtDescripcion.resetValue();
				txtSaldo.resetValue();
			} else {
				btnConsignar.setDisabled(false);
				btnRetirar.setDisabled(false);

				List<Cuentas> cuentasImp = delegadoDeNegocio.consultarCuentasDeUnCliente(idCliente);

				cuentas = new ArrayList<SelectItem>();

				for (Cuentas cuentaActual : cuentasImp) {

					SelectItem selectItem = new SelectItem(cuentaActual.getCueNumero(), cuentaActual.getCueNumero());

					cuentas.add(selectItem);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void txtMenuListener() {
		try {
			String noCuenta = menuCuentas.getValue().toString();

			Cuentas cuenta = delegadoDeNegocio.consultarCuentasPorID(noCuenta);

			if (cuenta != null) {

				txtCtaEstado.setValue(cuenta.getCueActiva().trim());
				txtSaldo.setValue(cuenta.getCueSaldo());

				if (cuenta.getCueActiva().trim().equals("S")) {
					btnConsignar.setDisabled(false);
					btnRetirar.setDisabled(false);
				} 
				
				else if (cuenta.getCueActiva().trim().equals("C")) {
					btnConsignar.setDisabled(false);
					btnRetirar.setDisabled(true);
				}
				
				else {
					btnConsignar.setDisabled(true);
					btnRetirar.setDisabled(true);
				}

			} else {
				txtSaldo.resetValue();
				btnConsignar.setDisabled(true);
				btnRetirar.setDisabled(true);
				txtValor.resetValue();
				txtDescripcion.resetValue();
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public String retirar() {
		try {
			String cueNumero = menuCuentas.getValue().toString();
			BigDecimal valor = new BigDecimal(txtValor.getValue().toString());
			long identificacionCliente = Long.parseLong(txtCliId.getValue().toString());
			long usuCedula = userLogueado.getUsuCedula();
			String descripcion = txtDescripcion.getValue().toString();

			delegadoDeNegocio.retirar(cueNumero, valor, identificacionCliente, usuCedula, descripcion);
			
			Cuentas cuenta = delegadoDeNegocio.consultarCuentasPorID(cueNumero);
			
			txtSaldo.setValue(cuenta.getCueSaldo());
			
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Retiro Exitoso!", ""));
			
		} catch (Exception e) {
			
			
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			e.printStackTrace();
		}
		return "";
	}

	public String consignar() {

		try {
			String cueNumero = menuCuentas.getValue().toString();
			BigDecimal valor = new BigDecimal(txtValor.getValue().toString());
			long identificacionCliente = Long.parseLong(txtCliId.getValue().toString());
			long usuCedula = userLogueado.getUsuCedula();
			String descripcion = txtDescripcion.getValue().toString();

			delegadoDeNegocio.consignar(cueNumero, valor, identificacionCliente, usuCedula, descripcion);
			
			Cuentas cuenta = delegadoDeNegocio.consultarCuentasPorID(cueNumero);

			txtSaldo.setValue(cuenta.getCueSaldo());
			txtCtaEstado.setValue(cuenta.getCueActiva());
			
			
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Consignación Exitosa!", ""));
			

		} catch (Exception e) {
			
			
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			
			e.printStackTrace();
		}		
		return "";
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

	public InputText getTxtCtaEstado() {
		return txtCtaEstado;
	}

	public void setTxtCtaEstado(InputText txtCtaEstado) {
		this.txtCtaEstado = txtCtaEstado;
	}

	public InputText getTxtValor() {
		return txtValor;
	}

	public void setTxtValor(InputText txtValor) {
		this.txtValor = txtValor;
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

	public SelectOneMenu getMenuCuentas() {
		return menuCuentas;
	}

	public void setMenuCuentas(SelectOneMenu menuCuentas) {
		this.menuCuentas = menuCuentas;
	}

	public List<Usuarios> getLosUsuarios() {
		return losUsuarios;
	}

	public void setLosUsuarios(List<Usuarios> losUsuarios) {
		this.losUsuarios = losUsuarios;
	}

	public List<SelectItem> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<SelectItem> cuentas) {
		this.cuentas = cuentas;
	}

	public InputText getTxtSaldo() {
		return txtSaldo;
	}

	public void setTxtSaldo(InputText txtSaldo) {
		this.txtSaldo = txtSaldo;
	}

	private List<Usuarios> losUsuarios;

}
