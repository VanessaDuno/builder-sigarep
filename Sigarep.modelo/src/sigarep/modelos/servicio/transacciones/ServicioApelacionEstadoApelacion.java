package sigarep.modelos.servicio.transacciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sigarep.modelos.data.transacciones.ApelacionEstadoApelacionPK;
import sigarep.modelos.data.transacciones.ApelacionEstadoApelacion;
import sigarep.modelos.data.transacciones.SolicitudApelacion;
import sigarep.modelos.data.transacciones.SolicitudApelacionPK;
import sigarep.modelos.repositorio.transacciones.IApelacionEstadoApelacionDAO;

/**
 * Clase ServicioApelacionEstadoApelacion : Clase de la capa servicio web para el manejo de consultas y persistencia de la tabla ApelacionEstadoApelacion 
 * 
 * @author Equipo Builder
 * @version 1.0
 * @since 04/01/2014
 * @last 10/05/2014
 */
@Service("servicioapelacionestadoapelacion")
public class ServicioApelacionEstadoApelacion {

	// Atributos de la clase
	private @Autowired IApelacionEstadoApelacionDAO iApelacionEstadoApelacionDAO;
	
	/**
	 * Guarda una apelaci�n con un estado de apelaci�n 
	 * @param apelacionestadoapelacion
	 * @throws No dispara ninguna excepci�n.
	 */
	public ApelacionEstadoApelacion guardar(ApelacionEstadoApelacion apelacionestadoapelacion) {
		return iApelacionEstadoApelacionDAO.save(apelacionestadoapelacion);
	}
	
	/**
	 * Elimina una apelaci�n en un estado de apelaci�n
	 * @param apelacionestadoapelacion
	 * @throws No dispara ninguna excepci�n.
	 */
	public void eliminar(ApelacionEstadoApelacion apelacionestadoapelacion){
		iApelacionEstadoApelacionDAO.delete(apelacionestadoapelacion);
	}
	
	/**
	 * Busca la lista de todas las apelaciones por estado de apelaci�n 
	 * @return List<ApelacionEstadoApelacion> Lista de apelaciones
	 * @throws No dispara ninguna excepci�n.
	 */
	public List<ApelacionEstadoApelacion> buscarTodos() {
		return iApelacionEstadoApelacionDAO.findAll();
	}
	
	/**
	 * Cuenta todas las apelaciones 
	 * @return N�mero de  ApelacionEstadoApelacion contadas
	 * @throws No dispara ninguna excepci�n.
	 */
	public int contarTodos() {
		return iApelacionEstadoApelacionDAO.findAll().size();
	}

	/**
	 * Nueva apelaci�n con un estado de apelaci�n 
	 * @return Objeto ApelacionEstadoApelacion
	 * @throws No dispara ninguna excepci�n.
	 */
	public ApelacionEstadoApelacion crear() {
		return new ApelacionEstadoApelacion();
	}

	/**
	 * Busca un historial de una apelaci�n en un estado de apelaci�n
	 * @param cedula, codigoLaso, idInstancia
	 * @return List<ApelacionEstadoApelacion> Lista de apelaciones
	 * @throws No dispara ninguna excepci�n.
	 */
	public List<ApelacionEstadoApelacion> buscarApelacionHistorial(String cedula, String codigoLaso, Integer idInstancia) {
		return iApelacionEstadoApelacionDAO.buscarApelacionHistorial(cedula, codigoLaso, idInstancia);
	}
	
	/**
	 * Busca una sugerencia para una apelaci�n en un estado de apelaci�n
	 * @param cedula, codigoLapso, instancia, estado
	 * @return List<ApelacionEstadoApelacion> Lista de apelaciones
	 * @throws No dispara ninguna excepci�n.
	 */
	public List<ApelacionEstadoApelacion> buscarSugerencia(String cedula, String codigoLapso, Integer instancia, Integer estado) {
		return iApelacionEstadoApelacionDAO.buscarSugerencia(cedula, codigoLapso, instancia, estado);
}
}