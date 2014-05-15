package sigarep.modelos.servicio.maestros;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sigarep.modelos.data.maestros.EstadoApelacion;
import sigarep.modelos.repositorio.maestros.IEstadoApelacionDAO;

/**
 * Clase  ServicioEstadoApelacion (Servicio para la Clase
 * EstadoApelacion)
 * 
 * @author Equipo Builder
 * @version 1.0
 * @since 18/12/2013
 * @last 10/05/2014
 */
@Service("servicioestadoapelacion")
public class ServicioEstadoApelacion {
	private @Autowired
	IEstadoApelacionDAO ea;
	
	/** Guardar Estado de Apelaci�n 
	 * @return guarda el objeto
	 * @param el objeto EstadoApelacion
	 * @throws No dispara ninguna excepci�n.
	 */
	public void guardarEstadoApelacion(EstadoApelacion estadoApelacion) {
		if (estadoApelacion.getIdEstadoApelacion() != null)
			ea.save(estadoApelacion);
		else{
			estadoApelacion.setIdEstadoApelacion(ea.buscarUltimoID()+1);
			ea.save(estadoApelacion);
		}
	}
	
	/** Lista de Estados de Apelaci�n 
	 * @return Lista de los Estados de Apelacion registrados y activos
	 * @param vacio
	 * @throws No dispara ninguna excepci�n.
	   */
	public List<EstadoApelacion> listadoEstadoApelacionActivas() {
		List<EstadoApelacion> ListaEstadoApelacion = ea.findByEstatusTrueOrderByPrioridadEjecucionAsc();
		return ListaEstadoApelacion;
	}
	
	/** Buscar Estados de Apelaci�n por nombre
	 * @return el estado de apelacion buscado si existe
	 * @param nombre del estado de apelaci�n
	 * @throws No dispara ninguna excepci�n.
	   */
	public EstadoApelacion buscarEstadoNombre(String nombreEstado) {
		EstadoApelacion estadoapelacion=ea.findByNombreEstado(nombreEstado);
      return estadoapelacion;
	}

	/**
	 * Busca los estados de apelaci�n de una instancia
	 * @param el id de la instancia
	 * @return lista de estados de una instancia
	 */
	public List<EstadoApelacion> buscarEstados(int instancia) {
		List<EstadoApelacion> listaEstados = ea.buscarEstados(instancia);
		return listaEstados;
	}
}