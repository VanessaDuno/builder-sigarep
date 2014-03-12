package sigarep.modelos.servicio.maestros;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import sigarep.modelos.data.maestros.Persona;

import sigarep.modelos.repositorio.maestros.IPersonaDAO;

/**
 * Clase ServicioPersona
 * 
 * @author BUILDER
 * @version 1.0
 * @since 18/12/2013
 */
@Service("serviciopersona")
public class ServicioPersona {
	private @Autowired
	IPersonaDAO iPersona;

	/**
	 * Guardar Persona
	 * 
	 * @param el objeto Persona
	 * @return No devuelve ning�n valor
	 * @throws No dispara ninguna excepci�n.
	 */
	public void guardar(Persona person) {
		iPersona.save(person);
	}

	/** Eliminar Persona
	 * @param String cedulaPersona
	 * @return No devuelve ning�n valor.
	 * @throws No dispara ninguna excepci�n.
	 */
	public void eliminar(String cedulaPersona) {
		Persona per = iPersona.findOne(cedulaPersona);
		per.setEstatus(false);
		iPersona.save(per);
	}
	
	/** listadoPersona
	 * @param No devuelve ning�n valor
	 * @return listadoPersona registradas y activas 
	 * @throws No dispara ninguna excepci�n.
	 */
	public List<Persona> listadoPersona() {
		return iPersona.findByEstatusTrue();
	}
	/** buscarPersonaFiltro
	 * @param String cedulaPersona
			String nombreCompleto, String nombreUsuario
	 * @return Persona
	 * @throws cedulaPersona == null,  nombreCompleto == null, nombreUsuario == null
	 */
	public List<Persona> buscarPersonaFiltro(String cedulaPersona,
			String nombreCompleto, String nombreUsuario) {
		List<Persona> resultado = new LinkedList<Persona>();
		if (cedulaPersona == null || nombreCompleto == null
				|| nombreUsuario == null) {
			resultado = listadoPersona();
		} else {
			for (Persona persona : listadoPersona()) {
				if (persona.getCedulaPersona().toLowerCase()
						.contains(cedulaPersona)
						&& persona.getNombreUsuario().getNombreCompleto()
								.toLowerCase().contains(nombreCompleto)
						&& persona.getNombreUsuario().getNombreUsuario()
								.toLowerCase().contains(nombreUsuario))
					resultado.add(persona);
			}
		}
		return resultado;
	}
	
	/** buscarPersonaNombreUsuario
	 * @param String nombreUsuario
	 * @return Persona
	 * @throws No dispara niguna excepci�n.
	 */
	public Persona buscarPersonaNombreUsuario(String nombreUsuario) {
		Persona persona = iPersona.buscarPersonaPorNombreUsuario(nombreUsuario);
		return persona;
	}
	/** buscaUnaPersona
	 * @param String cedula
	 * @return Persona
	 * @throws No dispara niguna excepci�n.
	 */
	public Persona buscaUnaPersona(String cedula) {
		Persona persona = iPersona.findOne(cedula);
		return persona;
	}
	/**eliminarFisicamente
	 * @param Persona persona
	 * @return Persona
	 * @throws No dispara niguna excepci�n.
	 */
	public void eliminarFisicamente(Persona persona) {
		iPersona.delete(persona);
	}
}
