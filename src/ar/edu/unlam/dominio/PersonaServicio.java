package ar.edu.unlam.dominio;

import java.util.List;

import ar.edu.unlam.dominio.contratos.ServicioGenerico;
import ar.edu.unlam.dominio.entidades.Persona;
import ar.edu.unlam.dominio.excepciones.PersonaNoEncontradaException;
import ar.edu.unlam.dominio.excepciones.PersonaNoValidaException;
import ar.edu.unlam.repositorio.PersonaRepositorio;

public class PersonaServicio implements ServicioGenerico<Persona> {

	private static final int MINIMO_DNI = 10000000;
	private final PersonaRepositorio personaRepository;

	public PersonaServicio(PersonaRepositorio personaRepository) {
		this.personaRepository = personaRepository;
	}

	@Override
	public Persona obtener(Long id) throws PersonaNoEncontradaException {
		Persona persona = this.personaRepository.obtener(id);

		if (persona == null) {
			throw new PersonaNoEncontradaException();
		}

		return persona;
	}

	@Override
	public List<Persona> obtener() {
		return this.personaRepository.obtener();
	}

	@Override
	public Boolean guardar(Persona persona) throws PersonaNoValidaException {
		if (!this.esValida(persona)) {
			throw new PersonaNoValidaException();
		}
		return this.personaRepository.guardar(persona);
	}

	@Override
	public Boolean actualizar(Persona persona) throws PersonaNoValidaException {
		if (!this.esValida(persona)) {
			throw new PersonaNoValidaException();
		}
		return this.personaRepository.actualizar(persona);
	}

	@Override
	public void eliminar(Long id) {
		this.personaRepository.eliminar(id);
	}

	@Override
	public Boolean esValida(Persona persona) {
		return this.esValidoEl(persona.getDni()) && !persona.getNombre().isEmpty() && !persona.getApellido().isEmpty();
	}

	public Boolean esValidoEl(Integer dni) {
		return dni != null && dni > MINIMO_DNI;
	}

	public Persona obtenerPorDni(Integer dni) {
		return this.personaRepository.obtenerPorDni(dni);
	}
}
