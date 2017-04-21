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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.demo.modelo.Clientes;
import co.edu.icesi.demo.modelo.Cuentas;
import co.edu.icesi.demo.modelo.CuentasRegistradas;
import co.edu.icesi.demo.modelo.TiposUsuarios;
import co.edu.icesi.demo.modelo.Usuarios;
import co.edu.icesi.demo.util.FacesUtils;

@ManagedBean
@ViewScoped
public class ModuloTrasladoVista {

	private final static Logger log = LoggerFactory.getLogger(ModuloTrasladoVista.class);
	private final static long CEDULA_ROBOT_TRANS = 888888888L;
	private final static long CODIGO_CAJERO = 10L;
	
	private Clientes clienteLogueado = (Clientes) FacesUtils.getfromSession("kCliente");

	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	private InputText txtCtaOrigenEstado, txtCtaDestinoEstado, txtValor, txtDescripcion,
	txtCtaOrigenSaldo,txtCtaDestinoSaldo;

	private SelectOneMenu menuCuentasOrigen, menuCuentasRegistradas;

	private List<SelectItem> cuentasCliente, cuentasClienteRegistradas;

	private CommandButton btnTrasladar;

	public Clientes getClienteLogueado() {
		return clienteLogueado;
	}

	public void setClienteLogueado(Clientes clienteLogueado) {
		this.clienteLogueado = clienteLogueado;
	}

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	public InputText getTxtCtaDestinoEstado() {
		return txtCtaDestinoEstado;
	}

	public void setTxtCtaDestinoEstado(InputText txtCtaDestinoEstado) {
		this.txtCtaDestinoEstado = txtCtaDestinoEstado;
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

	public CommandButton getBtnTrasladar() {
		return btnTrasladar;
	}

	public void setBtnTrasladar(CommandButton btnTrasladar) {
		this.btnTrasladar = btnTrasladar;
	}

	public void setMenuCuentasOrigen(SelectOneMenu menuCuentasOrigen) {
		this.menuCuentasOrigen = menuCuentasOrigen;
	}

	public void setCuentasCliente(List<SelectItem> cuentasCliente) {
		this.cuentasCliente = cuentasCliente;
	}

	public List<SelectItem> getCuentasCliente() {
		try {
			long cedulaCliente = clienteLogueado.getCliId();

			cuentasCliente = new ArrayList<SelectItem>();

			List<Cuentas> lasCuentas = delegadoDeNegocio.consultarCuentasDeUnCliente(cedulaCliente);

			for (Cuentas ctaActual : lasCuentas) {

				SelectItem selectItem = new SelectItem(ctaActual.getCueNumero(), ctaActual.getCueNumero());

				cuentasCliente.add(selectItem);
			}

		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return cuentasCliente;
	}

	public SelectOneMenu getMenuCuentasOrigen() {
		return menuCuentasOrigen;
	}

	public void setMenuCuentas(SelectOneMenu menuCuentas) {
		this.menuCuentasOrigen = menuCuentas;
	}

	public InputText getTxtCtaOrigenEstado() {
		return txtCtaOrigenEstado;
	}

	public void setTxtCtaOrigenEstado(InputText txtCtaOrigenEstado) {
		this.txtCtaOrigenEstado = txtCtaOrigenEstado;
	}

	public SelectOneMenu getMenuCuentasRegistradas() {
		return menuCuentasRegistradas;
	}

	public void setMenuCuentasRegistradas(SelectOneMenu menuCuentasRegistradas) {
		this.menuCuentasRegistradas = menuCuentasRegistradas;
	}

	public List<SelectItem> getCuentasClienteRegistradas() {
		try {

			long cedulaCliente = clienteLogueado.getCliId();

			cuentasClienteRegistradas = new ArrayList<SelectItem>();

			List<CuentasRegistradas> lasCuentas = delegadoDeNegocio
					.consultarCuentasRegistradasDeUnCliente(cedulaCliente);

			for (CuentasRegistradas ctaActual : lasCuentas) {

				SelectItem selectItem = new SelectItem(ctaActual.getCuentas().getCueNumero(),
						ctaActual.getCuentas().getCueNumero());

				cuentasClienteRegistradas.add(selectItem);
			}

		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return cuentasClienteRegistradas;
	}

	public void setCuentasClienteRegistradas(List<SelectItem> cuentasClienteRegistradas) {
		this.cuentasClienteRegistradas = cuentasClienteRegistradas;
	}

	public String generarTraslado() {

		log.info("TRASLADO GENERADO!");
		try {

			String cueNumeroOrigen = menuCuentasOrigen.getValue().toString();
			String cueNumeroDestino = menuCuentasRegistradas.getValue().toString();

			if (txtValor.getValue() == null) {
				throw new Exception("El Campo 'Valor' Lo Puede ser Nulo!");
			}

			int valor = Integer.parseInt(txtValor.getValue().toString());

			long identificacionClienteOrigen = clienteLogueado.getCliId();

			Cuentas cuentaDestino = delegadoDeNegocio.consultarCuentasPorID(cueNumeroDestino);

			long identificacionClienteDestino = cuentaDestino.getClientes().getCliId();

			if (txtDescripcion.getValue() == null) {
				throw new Exception("El campo 'descripción' no puede ser vacío!");
			}

			Usuarios usuario = getRobotTransacciones();
			
			String descripcion = txtDescripcion.getValue().toString();

			delegadoDeNegocio.transladoEntreCuentas(cueNumeroOrigen, cueNumeroDestino, new BigDecimal(valor),
					identificacionClienteOrigen, identificacionClienteDestino, usuario.getUsuCedula(), descripcion);
			
			Cuentas cuentaOrigen = delegadoDeNegocio.consultarCuentasPorID(cueNumeroOrigen);
			cuentaDestino = delegadoDeNegocio.consultarCuentasPorID(cueNumeroDestino);
			
			txtCtaDestinoSaldo.setValue(cuentaDestino.getCueSaldo());
			txtCtaOrigenSaldo.setValue(cuentaOrigen.getCueSaldo());

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Traslado Generado!", ""));
			
		} catch (Exception e) {
			
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			
			
			log.info(e.getMessage());
			
			
			e.printStackTrace();
		}

		return "";
	}
	
	private Usuarios getRobotTransacciones() throws Exception{
		Usuarios robot = delegadoDeNegocio.consultarUsuariosPorID(CEDULA_ROBOT_TRANS);
		if (robot == null){
			
			TiposUsuarios tipo = delegadoDeNegocio.consultarTiposUsuariosPorID(CODIGO_CAJERO);
			
			robot = new Usuarios();
			robot.setTiposUsuarios(tipo);
			robot.setUsuCedula(CEDULA_ROBOT_TRANS);
			robot.setUsuClave("ROBOT");
			robot.setUsuNombre("Robot Cajero Transacciones!");
			robot.setUsuLogin("Robot123");
			
			delegadoDeNegocio.crearUsuarios(robot);
		}
		
		return robot;
	}
	
	

	public void txtCuentaOrigenListener() {

		Cuentas cuentaOrigen;
		try {
			cuentaOrigen = delegadoDeNegocio.consultarCuentasPorID(menuCuentasOrigen.getValue().toString());

			if (cuentaOrigen == null) {
				txtCtaOrigenEstado.setValue("");
				btnTrasladar.setDisabled(true);
				txtCtaOrigenSaldo.resetValue();

			} else {
				String estado = cuentaOrigen.getCueActiva().trim();
				
				if (estado.equalsIgnoreCase("S")) {
					btnTrasladar.setDisabled(false);
				}

				else if (estado.equalsIgnoreCase("C")) {
					btnTrasladar.setDisabled(true);

				}

				else if (estado.equalsIgnoreCase("N")) {
					btnTrasladar.setDisabled(true);

				}

				txtCtaOrigenEstado.setValue(estado);
				txtCtaOrigenSaldo.setValue(cuentaOrigen.getCueSaldo());
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}

	public void txtCuentaDestinoListener() {

		Cuentas cuentaDestino;
		try {
			cuentaDestino = delegadoDeNegocio.consultarCuentasPorID(menuCuentasRegistradas.getValue().toString());

			if (cuentaDestino == null) {
				txtCtaDestinoEstado.setValue("");
				btnTrasladar.setDisabled(true);
				txtCtaDestinoSaldo.resetValue();

			} else {

				String estado = cuentaDestino.getCueActiva().trim();
				
				if (estado.equalsIgnoreCase("S")) {
					
				
					btnTrasladar.setDisabled(false);
				}

				else if (estado.equalsIgnoreCase("C") || estado.equalsIgnoreCase("N")) {
					btnTrasladar.setDisabled(true);

				}

		

				txtCtaDestinoEstado.setValue(estado);
				txtCtaDestinoSaldo.setValue(cuentaDestino.getCueSaldo());

			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}

	}

	public InputText getTxtCtaDestinoSaldo() {
		return txtCtaDestinoSaldo;
	}

	public void setTxtCtaDestinoSaldo(InputText txtCtaDestinoSaldo) {
		this.txtCtaDestinoSaldo = txtCtaDestinoSaldo;
	}

	public InputText getTxtCtaOrigenSaldo() {
		return txtCtaOrigenSaldo;
	}

	public void setTxtCtaOrigenSaldo(InputText txtCtaOrigenSaldo) {
		this.txtCtaOrigenSaldo = txtCtaOrigenSaldo;
	}

}
