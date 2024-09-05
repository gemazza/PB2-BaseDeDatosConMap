package ar.edu.unlam.repositorio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.dominio.entidades.Persona;

public class PersonaRepositorioTest {

	private PersonaRepositorio personaRepositorio;
	
	@Before
	public void init() {
		this.personaRepositorio = new PersonaRepositorio();
	}
	
	@Test
	public void dadoQueExisteUnRepositorioDePersonasCuandoGuardoUnaPersonaObtengoVerdadero() {
		Persona persona = new Persona(34000111, "Nombre", "Apellido");
		Boolean guardada = this.personaRepositorio.guardar(persona);
		assertTrue(guardada);
	}
	
	@Test
	public void dadoQueExisteUnRepositorioConPersonasCuandoObtengoUnaPersonaPorSuIdObtengoLaPersonaBuscada() {
		Persona persona = new Persona(34000111, "Nombre", "Apellido");
		Persona otraPersona = new Persona(34000222, "Otro nombre", "Otro apellido");
		this.personaRepositorio.guardar(persona);
		this.personaRepositorio.guardar(otraPersona);
		
		Long personaId = 2L;
		Persona personaObtenida = this.personaRepositorio.obtener(personaId);
		
		String nombreEsperado = "Otro nombre";
		String apellidoEsperado = "Otro apellido";
		assertEquals(personaId, personaObtenida.getId());
		assertEquals(nombreEsperado, personaObtenida.getNombre());
		assertEquals(apellidoEsperado, personaObtenida.getApellido());
	}
	
	@Test
	public void dadoQueExisteUnRepositorioConPersonasCuandoObtengoUnaPersonaPorSuDNIObtengoLaPersonaBuscada() {
		Persona persona = new Persona(34000111, "Nombre", "Apellido");
		Persona otraPersona = new Persona(34000222, "Otro nombre", "Otro apellido");
		this.personaRepositorio.guardar(persona);
		this.personaRepositorio.guardar(otraPersona);
		
		Integer dniPersona = 34000222;
		Persona personaObtenida = this.personaRepositorio.obtenerPorDni(dniPersona);
		
		String nombreEsperado = "Otro nombre";
		String apellidoEsperado = "Otro apellido";
		assertEquals(dniPersona, personaObtenida.getDni());
		assertEquals(nombreEsperado, personaObtenida.getNombre());
		assertEquals(apellidoEsperado, personaObtenida.getApellido());
	}
	
	@Test
	public void dadoQueExisteUnRepositorioConDosPersonasCuandoLasObtengoReciboUnaListaConEsasDosPersonas() {
		Persona persona = new Persona(34000111, "Nombre", "Apellido");
		Persona otraPersona = new Persona(34000222, "Otro nombre", "Otro apellido");
		this.personaRepositorio.guardar(persona);
		this.personaRepositorio.guardar(otraPersona);
		
		List<Persona> personasObtenidas = this.personaRepositorio.obtener();
		
		Integer dniEsperadoPersona = 34000111;
		Integer dniEsperadoOtraPersona = 34000222;
		int cantidadDePersonasEsperadas = 2;
		assertEquals(cantidadDePersonasEsperadas, personasObtenidas.size());
		assertEquals(dniEsperadoPersona, personasObtenidas.get(0).getDni());
		assertEquals(dniEsperadoOtraPersona, personasObtenidas.get(1).getDni());
		
	}
	
	@Test
	public void dadoQueExisteUnRepositorioConPersonasCuandoActualizoUnaPersonaObtengoVerdadero() {
		Persona persona = new Persona(34000111, "Nombre", "Apellido");
		Persona otraPersona = new Persona(34000222, "Otro nombre", "Otro apellido");
		this.personaRepositorio.guardar(persona);
		this.personaRepositorio.guardar(otraPersona);
		String nuevoNombre = "Nuevo nombre";
		String nuevoApellido = "Nuevo apellido";
		Persona personaParaActualizar = this.personaRepositorio.obtenerPorDni(34000222);
		Persona personaActualizada = new Persona(personaParaActualizar.getId(), personaParaActualizar.getDni(),personaParaActualizar.getNombre(), personaParaActualizar.getApellido());
		personaActualizada.setNombre(nuevoNombre);
		personaActualizada.setApellido(nuevoApellido);
		
		Boolean actualizada = this.personaRepositorio.actualizar(personaActualizada);

		Persona personaObtenida = this.personaRepositorio.obtenerPorDni(34000222);
		
		assertTrue(actualizada);
		assertEquals(nuevoNombre, personaObtenida.getNombre());
		assertEquals(nuevoApellido, personaObtenida.getApellido());
	}
	
	@Test
	public void dadoQueExisteUnRepositorioConPersonasCuandoEliminoUnaPersonaNoExisteMasEnElRepositorio() {
		Persona persona = new Persona(34000111, "Nombre", "Apellido");
		Persona otraPersona = new Persona(34000222, "Otro nombre", "Otro apellido");
		this.personaRepositorio.guardar(persona);
		this.personaRepositorio.guardar(otraPersona);
		
		this.personaRepositorio.eliminar(persona.getId());
		
		Persona personaObtenida = this.personaRepositorio.obtenerPorDni(34000111);
		
		assertNull(personaObtenida);
	}
}
