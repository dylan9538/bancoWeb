package co.edu.icesi.demo.dao;

import java.util.List;

import co.edu.icesi.demo.modelo.TiposDocumentos;

public interface ITipoDocumentoDAO {
	public void crear(TiposDocumentos tipoDocumento);

	public void modificar(TiposDocumentos tipoDocumento);

	public void borrar(TiposDocumentos tipoDocumento);

	public TiposDocumentos consultarPorID(Long id);

	public List<TiposDocumentos> consultarTodos();
}
