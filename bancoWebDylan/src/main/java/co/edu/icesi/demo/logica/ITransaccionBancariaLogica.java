package co.edu.icesi.demo.logica;

import java.math.BigDecimal;

public interface ITransaccionBancariaLogica {

	public void retirar(String cueNumero, BigDecimal valor, long identificacionCliente, long usuCedula,
			String descripcion) throws Exception;

	public void consignar(String cueNumero, BigDecimal valor, long identificacionCliente, long usuCedula,
			String descripcion) throws Exception;

	public void transladoEntreCuentas(String cueNumeroOrigen, String cueNumeroDestino, BigDecimal valor,
			long identificacionClienteOrigen, long identificacionClienteDestino, long usuCedula, String descripcion)
			throws Exception;

}
