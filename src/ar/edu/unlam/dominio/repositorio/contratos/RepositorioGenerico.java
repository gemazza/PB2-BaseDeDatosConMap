package ar.edu.unlam.dominio.repositorio.contratos;

import java.util.List;

public interface RepositorioGenerico<T> {

	T obtener(Long id);

	List<T> obtener();

	Boolean guardar(T item);

	Boolean actualizar(T item);

	void eliminar(Long id);
}
