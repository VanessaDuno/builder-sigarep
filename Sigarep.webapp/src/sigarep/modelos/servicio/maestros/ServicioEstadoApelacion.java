package sigarep.modelos.servicio.maestros;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import sigarep.modelos.data.maestros.EstadoApelacion;

import sigarep.modelos.repositorio.maestros.IEstadoApelacionDAO;



@Service("servicioestadoapelacion")
public class ServicioEstadoApelacion {
	private @Autowired
	IEstadoApelacionDAO ea;
	
	/** Guardar Estado de Apelaci�n 
	 * @return guarda el objeto
	 * @parameters el objeto EstadoApelacion
	 * @throws No dispara ninguna excepcion.
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
	 * @parameters vacio
	 * @throws No dispara ninguna excepcion.
	   */
	public List<EstadoApelacion> listadoEstadoApelacionActivas() {
		List<EstadoApelacion> ListaEstadoApelacion = ea.findByEstatusTrue();
		return ListaEstadoApelacion;
	}
	
	/** Buscar Estados de Apelaci�n por nombre
	 * @return el estado de apelacion buscado si existe
	 * @parameters nombre del estado de apelacion
	 * @throws No dispara ninguna excepcion.
	   */
	public EstadoApelacion buscarEstadoNombre(String nombreEstado) {
		EstadoApelacion estadoapelacion=ea.findByNombreEstado(nombreEstado);
      return estadoapelacion;
	}

	/**
	 * Busca los estados de apelacion de una instancia
	 * @param el id de la instancia
	 * @return lista de estados de una instancia
	 */
	public List<EstadoApelacion> buscarEstados(int instancia) {
		List<EstadoApelacion> listaEstados = ea.buscarEstados(instancia);
		return listaEstados;
	}
}

