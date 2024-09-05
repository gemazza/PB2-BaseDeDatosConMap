package ar.edu.unlam.dominio.contratos;

import java.util.List;

import ar.edu.unlam.dominio.excepciones.PersonaNoEncontradaException;
import ar.edu.unlam.dominio.excepciones.PersonaNoValidaException;

public interface ServicioGenerico<T> {
	T obtener(Long id) throws PersonaNoEncontradaException;

	List<T> obtener();

	Boolean guardar(T item) throws PersonaNoValidaException;

	Boolean actualizar(T item) throws PersonaNoValidaException;

	void eliminar(Long id);

	Boolean esValida(T item);
}
