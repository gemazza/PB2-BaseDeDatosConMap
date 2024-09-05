package ar.edu.unlam.repositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.unlam.dominio.entidades.Persona;
import ar.edu.unlam.dominio.repositorio.contratos.RepositorioGenerico;

public class PersonaRepositorio implements RepositorioGenerico<Persona>{
	private final Map<Long, Persona> database;
	private static Long proximoId;
	
	public PersonaRepositorio() {
		proximoId = 0L;
		this.database = new HashMap<>();
	}

	@Override
	public Persona obtener(Long id) {
		return database.get(id);
	}

	@Override
	public List<Persona> obtener() {
		return new ArrayList<>(database.values());
	}

	@Override
	public Boolean guardar(Persona persona) {
		persona.setId(++proximoId);
		database.put(persona.getId(), persona); 
		return true;
	}

	@Override
	public Boolean actualizar(Persona persona) {
		database.put(persona.getId(), persona); 
		return true;
	}

	@Override
	public void eliminar(Long id) {
		this.database.remove(id);
	}
	
	public Persona obtenerPorDni(Integer dni) {
		List<Persona> personas = new ArrayList<>(database.values());
		Persona personaBuscada = null;
		for(Persona persona : personas) {
			if(persona.getDni().equals(dni)) {
				personaBuscada = persona;
				break;
			}
		}
		return personaBuscada;
	}

}
