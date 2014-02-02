package sigarep.modelos.repositorio.maestros;

import java.util.List;

/*
 * @ (#) EstadoApelacion.java 
 *
 * Copyright 2013 Builder. Todos los derechos reservados.
 * CONFIDENCIAL. El uso est� sujeto a los t�rminos de la licencia.
 */
/*
** Archivo del repositorio  del registro del maestro "Recaudo"
 * @ Author Beleanny Atacho 
 * @ Version 1.0, 16/12/13
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sigarep.modelos.data.maestros.Actividad;
import sigarep.modelos.data.maestros.Recaudo;

public interface IRecaudoDAO extends JpaRepository<Recaudo, Integer> {

	@Query("Select rec FROM Recaudo AS rec WHERE rec.tipoMotivo.idTipoMotivo = :tipoMotivo")
	public List<Recaudo> buscarRecaudosPorMotivo(@Param("tipoMotivo") Integer tipoMotivo);

	@Query("Select rec FROM Recaudo AS rec WHERE rec.nombreRecaudo = :nombreRecaudo")
	public Recaudo buscarRecaudoPorNombre(@Param("nombreRecaudo") String nombreRecaudo);
	
	@Query("Select rec FROM Recaudo AS rec WHERE rec.estatus = TRUE")
	public List<Recaudo> buscaRecaudosActivos();
	
	@Query("Select rec FROM Recaudo AS rec")
	public List<Recaudo> buscaRecaudos();
	
	@Query("Select rec FROM Recaudo AS rec WHERE rec.tipoMotivo.idTipoMotivo = '1'")
	public List<Recaudo> buscaRecaudosGenerales();
	
	@Query("SELECT r FROM Recaudo AS r, TipoMotivo AS tm, Motivo AS m, LapsoAcademico AS la " +
			"WHERE la.codigoLapso = m.id.codigoLapso AND la.estatus = 'TRUE' " +
			"AND r.tipoMotivo.idTipoMotivo = tm.idTipoMotivo " +
			"AND m.id.cedulaEstudiante = :cedula " +
			"AND m.id.idTipoMotivo = tm.idTipoMotivo ORDER BY r.tipoMotivo.idTipoMotivo")
	public List<Recaudo> listadoRecaudosPorApelacion(@Param("cedula") String cedula);
	
	@Query("SELECT r FROM Recaudo AS r, Motivo AS m, LapsoAcademico AS la " +
			"WHERE r.tipoMotivo.idTipoMotivo != '1' AND r.tipoMotivo.idTipoMotivo != '3' " +
			"AND r.idRecaudo NOT IN " +
			"(SELECT re.id.idRecaudo FROM RecaudoEntregado AS re, LapsoAcademico AS la " + 
			"WHERE re.id.cedulaEstudiante = :cedula " +
			"AND la.estatus = 'TRUE') " +
			"AND r.tipoMotivo.idTipoMotivo = m.id.idTipoMotivo " +
			"AND m.id.cedulaEstudiante = :cedula " +
			"AND m.id.codigoLapso = la.codigoLapso " +
			"AND la.estatus = 'TRUE'")
	public List<Recaudo> buscarRecaudosVerificarRecaudosII(@Param("cedula") String cedula);

	@Query("SELECT r FROM Recaudo AS r, Motivo AS m, LapsoAcademico AS la " +
			"WHERE r.tipoMotivo.idTipoMotivo != '1' AND r.tipoMotivo.idTipoMotivo != '2' " +
			"AND r.idRecaudo NOT IN " +
			"(SELECT re.id.idRecaudo FROM RecaudoEntregado AS re, LapsoAcademico AS la " + 
			"WHERE re.id.cedulaEstudiante = :cedula " +
			"AND la.estatus = 'TRUE') " +
			"AND r.tipoMotivo.idTipoMotivo = m.id.idTipoMotivo " +
			"AND m.id.cedulaEstudiante = :cedula " +
			"AND m.id.codigoLapso = la.codigoLapso " +
			"AND la.estatus = 'TRUE'")
	public List<Recaudo> buscarRecaudosVerificarRecaudosIII(@Param("cedula") String cedula);

}
