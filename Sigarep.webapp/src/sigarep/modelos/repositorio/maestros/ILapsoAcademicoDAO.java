package sigarep.modelos.repositorio.maestros;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import sigarep.modelos.data.maestros.LapsoAcademico;


/**
 * Repositorio LapsoAcademico-ILapsoAcademicoDAO
 * 
 * @author BUILDER
 * @version 1.0
 * @since 12/12/2013
 */

public interface ILapsoAcademicoDAO extends JpaRepository<LapsoAcademico, String> {

	/**
	 * Busca el lapso acad�mico ACTIVO, es decir, donde su estatus es true
	 * 
	 * @return LapsoAcademico activo encontrado
	 */
	public LapsoAcademico findByEstatusTrue();

	/**
	 * Busca los lapsos acad�mico INACTIVOS, es decir, donde su estatus es false
	 * 
	 * @return List<LapsoAcademico> Lapsos acad�mico inactivos
	 */
	public List<LapsoAcademico> findByEstatusFalse();
}
