package sigarep.modelos.repositorio.maestros;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sigarep.modelos.data.maestros.PreguntaBasica;

/**
 * Repositorio Persona-IPreguntaBasicaDAO 
 * 
 * @author BUILDER
 * @version 1.0
 * @since 12/12/2013
 */

public interface IPreguntaBasicaDAO extends
		JpaRepository<PreguntaBasica, Integer> {

	/**
	 * Busca las todas las preguntas b�sicas que poseen estatus == true
	 * @return List<PreguntaBasica> Lista de preguntas b�sicas con estatus == true
	 */
	public List<PreguntaBasica> findByEstatusTrue();
	
	/**
	 * Busca el �ltimo id insertado en la tabla PreguntaBasica
	 * @return �ltimo id insertado en la tabla PreguntaBasica
	 */
	@Query("SELECT COALESCE(MAX(pb.idPreguntaBasica),0) FROM PreguntaBasica AS pb")
	public int buscarUltimoID();
}