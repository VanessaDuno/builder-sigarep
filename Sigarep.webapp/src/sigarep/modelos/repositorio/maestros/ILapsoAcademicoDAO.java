package sigarep.modelos.repositorio.maestros;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



import sigarep.modelos.data.maestros.LapsoAcademico;
/**Clase Lapso Acad�mico 
* UCLA DCYT Sistemas de Informacion.
* Registra un lapso acad�mico mientras no haya otro lapso activo  ya registrado
* @author Equipo: Builder-SIGAREP 
* @version 1.0
* @since 20/12/13
*/
public interface ILapsoAcademicoDAO extends JpaRepository<LapsoAcademico, String> {
	
	/**
	 * Busca el lapso acad�mico ACTIVO, es decir, donde su estatus es true
	 * @return LapsoAcademico activo encontrado
	 */
	public LapsoAcademico findByEstatusTrue();
	
	/**
	 * Busca los lapsos acad�mico INACTIVOS, es decir, donde su estatus es false
	 * @return List<LapsoAcademico> Lapsos acad�mico inactivos
	 */
	public List<LapsoAcademico> findByEstatusFalse();
}
