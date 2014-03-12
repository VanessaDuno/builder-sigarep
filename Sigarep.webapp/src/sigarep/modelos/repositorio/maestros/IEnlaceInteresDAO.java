package sigarep.modelos.repositorio.maestros;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sigarep.modelos.data.maestros.EnlaceInteres;

/**
 * Repositorio EnlaceInteres-IEnlaceInteresDAO
 * 
 * @author BUILDER
 * @version 1.0
 * @since 12/12/2013
 */

public interface IEnlaceInteresDAO extends JpaRepository<EnlaceInteres, Integer> {

	/**
	 * Busca las todas los Enlaces de Inter�s que poseen estatus == true
	 * @return List<EnlacesInteres> Lista de Enlaces con estatus == true
	 */
	public List<EnlaceInteres> findByEstatusTrue();
		
	/**
	 * Busca el �ltimo id insertado en la tabla Enlace
	 * @return �ltimo id insertado en la tabla Enlace
	 */
	@Query("SELECT COALESCE(MAX(ei.idEnlace),0) FROM EnlaceInteres AS ei")
	public int buscarUltimoID();
}
