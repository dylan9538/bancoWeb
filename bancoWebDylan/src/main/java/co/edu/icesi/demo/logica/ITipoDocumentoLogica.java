package co.edu.icesi.demo.logica;

import java.util.List;

import co.edu.icesi.demo.modelo.TiposDocumentos;

public interface ITipoDocumentoLogica {

	public void crear(TiposDocumentos tipoDocumento) throws Exception;

	public void modificar(TiposDocumentos tipoDocumento) throws Exception;

	public void borrar(TiposDocumentos tipoDocumento) throws Exception;

	public TiposDocumentos consultarPorID(Long id) throws Exception;

	public List<TiposDocumentos> consultarTodos() throws Exception;
}
