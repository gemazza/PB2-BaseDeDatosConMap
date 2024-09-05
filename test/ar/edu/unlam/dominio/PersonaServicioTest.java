package ar.edu.unlam.dominio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unlam.dominio.entidades.Persona;
import ar.edu.unlam.dominio.excepciones.PersonaNoEncontradaException;
import ar.edu.unlam.dominio.excepciones.PersonaNoValidaException;
import ar.edu.unlam.repositorio.PersonaRepositorio;

public class PersonaServicioTest {

	private PersonaRepositorio personaRepositorio;
	private PersonaServicio personaServicio;
	
	@Before
	public void init() {
		this.personaRepositorio = new PersonaRepositorio();
		this.personaServicio = new PersonaServicio(personaRepositorio);
	}
	
	@Test
	public void dadoQueExisteUnServicioDePersonasCuandoGuardoUnaPersonaValidaObtengoVerdadero() throws PersonaNoValidaException {
		Persona persona = new Persona(34000111, "Nombre", "Apellido");
		Boolean personaGuardada = this.personaServicio.guardar(persona);
		assertTrue(personaGuardada);
	}
	
	@Test(expected = PersonaNoValidaException.class)
	public void dadoQueExisteUnServicioDePersonasCuandoGuardoUnaPersonaConNombreInvalidoObtengoUnaPersonaNoValidaException() throws PersonaNoValidaException {
		Persona persona = new Persona(34000111, "", "Apellido");
		this.personaServicio.guardar(persona);
	}
	
	@Test(expected = PersonaNoValidaException.class)
	public void dadoQueExisteUnServicioDePersonasCuandoGuardoUnaPersonaConApellidoInvalidoObtengoUnaPersonaNoValidaException() throws PersonaNoValidaException {
		Persona persona = new Persona(34000111, "Nombre", "");
		this.personaServicio.guardar(persona);
	}
	
	@Test(expected = PersonaNoValidaException.class)
	public void dadoQueExisteUnServicioDePersonasCuandoGuardoUnaPersonaConDNIMenorA10000000ObtengoUnaPersonaNoValidaException() throws PersonaNoValidaException {
		Persona persona = new Persona(0, "Nombre", "Apellido");
		this.personaServicio.guardar(persona);
	}
	
	@Test(expected = PersonaNoValidaException.class)
	public void dadoQueExisteUnServicioDePersonasCuandoGuardoUnaPersonaConDNINuloObtengoUnaPersonaNoValidaException() throws PersonaNoValidaException {
		Persona persona = new Persona(null, "Nombre", "Apellido");
		this.personaServicio.guardar(persona);
	}
	
	@Test
	public void dadoQueExisteUnServicioDePersonasCuandoObtengoUnaPersonaPorSuIdReciboEsaPersona() throws PersonaNoValidaException, PersonaNoEncontradaException {
		Persona persona = new Persona(34000111, "Nombre", "Apellido");
		Persona otraPersona = new Persona(34000222, "Otro nombre", "Otro apellido");
		this.personaServicio.guardar(persona);
		this.personaServicio.guardar(otraPersona);
		
		Long idPersona = 2L;
		Persona personaObtenida = this.personaServicio.obtener(idPersona);
		
		assertEquals(idPersona, personaObtenida.getId());
	}
	
	@Test(expected = PersonaNoEncontradaException.class)
	public void dadoQueExisteUnServicioDePersonasCuandoObtengoUnaPersonaPorSuIdYNoExisteObtengoUnaPersonaNoEncontradaException() throws PersonaNoValidaException, PersonaNoEncontradaException {
		Persona persona = new Persona(34000111, "Nombre", "Apellido");
		Persona otraPersona = new Persona(34000222, "Otro nombre", "Otro apellido");
		this.personaServicio.guardar(persona);
		this.personaServicio.guardar(otraPersona);
		
		Long idPersona = 99L;
		this.personaServicio.obtener(idPersona);
	}
	
	@Test
	public void dadoQueExisteUnServicioDePersonasCuandoObtengoTodasLasPersonasReciboUnaListaConEsasPersonas() throws PersonaNoValidaException, PersonaNoEncontradaException {
		Persona persona = new Persona(34000111, "Nombre", "Apellido");
		Persona otraPersona = new Persona(34000222, "Otro nombre", "Otro apellido");
		this.personaServicio.guardar(persona);
		this.personaServicio.guardar(otraPersona);
		
		List<Persona> personasObtenidas = this.personaServicio.obtener();
		
		int cantidadDePersonasEsperadas = 2;
		assertEquals(cantidadDePersonasEsperadas, personasObtenidas.size());
		assertEquals(persona.getDni(), personasObtenidas.get(0).getDni());
		assertEquals(otraPersona.getDni(), personasObtenidas.get(1).getDni());
	}
	
	@Test
	public void dadoQueExisteUnServicioDePersonasCuandoActualizoUnaPersonaValidaObtengoVerdadero() throws PersonaNoValidaException {
		Persona persona = new Persona(34000111, "Nombre", "Apellido");
		Persona otraPersona = new Persona(34000222, "Otro nombre", "Otro apellido");
		this.personaServicio.guardar(persona);
		this.personaServicio.guardar(otraPersona);
		Persona personaParaActualizar = this.personaServicio.obtenerPorDni(34000222);
		String nuevoNombre = "Nuevo nombre";
		String nuevoApellido = "Nuevo apellido";
		Persona personaActualizada = new Persona(personaParaActualizar.getId(), personaParaActualizar.getDni(), personaParaActualizar.getNombre(), personaParaActualizar.getApellido());
		personaActualizada.setNombre(nuevoNombre);
		personaActualizada.setApellido(nuevoApellido);
		
		Boolean actualizada = this.personaServicio.actualizar(personaActualizada);
		Persona personaObtenida = this.personaServicio.obtenerPorDni(34000222);
		assertTrue(actualizada);
		assertEquals(nuevoNombre, personaObtenida.getNombre());
		assertEquals(nuevoApellido, personaObtenida.getApellido());
	}
	
	@Test(expected = PersonaNoValidaException.class)
	public void dadoQueExisteUnServicioDePersonasCuandoActualizoUnaPersonaConNombreInvalidoObtengoUnaPersonaNoValidaException() throws PersonaNoValidaException {
		Persona persona = new Persona(34000111, "Nombre", "Apellido");
		Persona otraPersona = new Persona(34000222, "Otro nombre", "Otro apellido");
		this.personaServicio.guardar(persona);
		this.personaServicio.guardar(otraPersona);
		Persona personaParaActualizar = this.personaServicio.obtenerPorDni(34000222);
		String nuevoNombre = "";
		String nuevoApellido = "Nuevo apellido";
		Persona personaActualizada = new Persona(personaParaActualizar.getId(), personaParaActualizar.getDni(), personaParaActualizar.getNombre(), personaParaActualizar.getApellido());
		personaActualizada.setNombre(nuevoNombre);
		personaActualizada.setApellido(nuevoApellido);
		
		this.personaServicio.actualizar(personaActualizada);
	}
	
	@Test(expected = PersonaNoValidaException.class)
	public void dadoQueExisteUnServicioDePersonasCuandoActualizoUnaPersonaConApellidoInvalidoObtengoUnaPersonaNoValidaException() throws PersonaNoValidaException {
		Persona persona = new Persona(34000111, "Nombre", "Apellido");
		Persona otraPersona = new Persona(34000222, "Otro nombre", "Otro apellido");
		this.personaServicio.guardar(persona);
		this.personaServicio.guardar(otraPersona);
		Persona personaParaActualizar = this.personaServicio.obtenerPorDni(34000222);
		String nuevoNombre = "";
		String nuevoApellido = "Nuevo apellido";
		Persona personaActualizada = new Persona(personaParaActualizar.getId(), personaParaActualizar.getDni(), personaParaActualizar.getNombre(), personaParaActualizar.getApellido());
		personaActualizada.setNombre(nuevoNombre);
		personaActualizada.setApellido(nuevoApellido);
		
		this.personaServicio.actualizar(personaActualizada);
	}
	
	@Test(expected = PersonaNoValidaException.class)
	public void dadoQueExisteUnServicioDePersonasCuandoActualizoUnaPersonaConDNIMenorA10000000InvalidoObtengoUnaPersonaNoValidaException() throws PersonaNoValidaException {
		Persona persona = new Persona(34000111, "Nombre", "Apellido");
		Persona otraPersona = new Persona(34000222, "Otro nombre", "Otro apellido");
		this.personaServicio.guardar(persona);
		this.personaServicio.guardar(otraPersona);
		Persona personaParaActualizar = this.personaServicio.obtenerPorDni(34000222);
		String nuevoNombre = "Nuevo nombre";
		String nuevoApellido = "Nuevo apellido";
		Persona personaActualizada = new Persona(personaParaActualizar.getId(), personaParaActualizar.getDni(), personaParaActualizar.getNombre(), personaParaActualizar.getApellido());
		personaActualizada.setNombre(nuevoNombre);
		personaActualizada.setApellido(nuevoApellido);
		personaActualizada.setDni(0);
		
		this.personaServicio.actualizar(personaActualizada);
	}
	
	@Test(expected = PersonaNoValidaException.class)
	public void dadoQueExisteUnServicioDePersonasCuandoActualizoUnaPersonaConDNINuloInvalidoObtengoUnaPersonaNoValidaException() throws PersonaNoValidaException {
		Persona persona = new Persona(34000111, "Nombre", "Apellido");
		Persona otraPersona = new Persona(34000222, "Otro nombre", "Otro apellido");
		this.personaServicio.guardar(persona);
		this.personaServicio.guardar(otraPersona);
		Persona personaParaActualizar = this.personaServicio.obtenerPorDni(34000222);
		String nuevoNombre = "Nuevo nombre";
		String nuevoApellido = "Nuevo apellido";
		Persona personaActualizada = new Persona(personaParaActualizar.getId(), personaParaActualizar.getDni(), personaParaActualizar.getNombre(), personaParaActualizar.getApellido());
		personaActualizada.setNombre(nuevoNombre);
		personaActualizada.setApellido(nuevoApellido);
		personaActualizada.setDni(null);
		
		this.personaServicio.actualizar(personaActualizada);
	}
	
	@Test
	public void dadoQueExisteUnServicioDePersonasCuandoEliminoUnaPersonaNoExisteMas() throws PersonaNoValidaException {
		Persona persona = new Persona(34000111, "Nombre", "Apellido");
		Persona otraPersona = new Persona(34000222, "Otro nombre", "Otro apellido");
		this.personaServicio.guardar(persona);
		this.personaServicio.guardar(otraPersona);
		
		this.personaServicio.eliminar(1L);
		
		Persona personaObtenida = this.personaServicio.obtenerPorDni(34000111);
		
		assertNull(personaObtenida);
	}
	
	@Test
	public void dadoQueExisteUnServicioDePersonasCuandoVerificoSiEsValidaConDatosValidosObtengoVerdadero() {
		Persona persona = new Persona(34000111, "Nombre", "Apellido");
		Boolean esValida = this.personaServicio.esValida(persona);
		assertTrue(esValida);
	}
	
	@Test
	public void dadoQueExisteUnServicioDePersonasCuandoVerificoSiEsValidaConDNIMenorA10000000ObtengoFalso() {
		Persona persona = new Persona(0, "Nombre", "Apellido");
		Boolean esValida = this.personaServicio.esValida(persona);
		assertFalse(esValida);
	}
	
	@Test
	public void dadoQueExisteUnServicioDePersonasCuandoVerificoSiEsValidaConDNINuloObtengoFalso() {
		Persona persona = new Persona(null, "Nombre", "Apellido");
		Boolean esValida = this.personaServicio.esValida(persona);
		assertFalse(esValida);
	}
	
	@Test
	public void dadoQueExisteUnServicioDePersonasCuandoVerificoSiEsValidaConNombreInvalidoObtengoFalso() {
		Persona persona = new Persona(34000111, "", "Apellido");
		Boolean esValida = this.personaServicio.esValida(persona);
		assertFalse(esValida);
	}
	
	@Test
	public void dadoQueExisteUnServicioDePersonasCuandoVerificoSiEsValidaConApellidoInvalidoObtengoFalso() {
		Persona persona = new Persona(34000111, "Nombre", "");
		Boolean esValida = this.personaServicio.esValida(persona);
		assertFalse(esValida);
	}
	
	@Test
	public void dadoQueExisteUnServicioDePersonasCuandoVerificoSiEsValidoElDNIConUnDNIValidoObtengoVerdadero() {
		Integer dni = 34000111;
		Boolean esValido = this.personaServicio.esValidoEl(dni);
		assertTrue(esValido);
	}
	
	@Test
	public void dadoQueExisteUnServicioDePersonasCuandoVerificoSiEsValidoElDNIConUnDNIMenorA10000000ObtengoFalso() {
		Integer dni = 0;
		Boolean esValido = this.personaServicio.esValidoEl(dni);
		assertFalse(esValido);
	}
	
	@Test
	public void dadoQueExisteUnServicioDePersonasCuandoVerificoSiEsValidoElDNIConUnDNINuloObtengoFalso() {
		Integer dni = null;
		Boolean esValido = this.personaServicio.esValidoEl(dni);
		assertFalse(esValido);
	}
}
