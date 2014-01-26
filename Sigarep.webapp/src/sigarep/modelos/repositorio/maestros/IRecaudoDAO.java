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
	
	@Query("Select rec FROM Recaudo AS rec, TipoMotivo As tm, Motivo AS m, SolicitudApelacion AS sap WHERE tm.idTipoMotivo = m.id.idTipoMotivo AND m.id.idInstanciaApelada = sap.id.idInstanciaApelada AND m.id.codigoLapso = sap.id.codigoLapso AND m.id.cedulaEstudiante = sap.id.cedulaEstudiante AND rec.tipoMotivo.idTipoMotivo = m.id.idTipoMotivo AND sap.id.cedulaEstudiante = :cedula AND sap.id.codigoLapso = :codigoLapso AND sap.id.idInstanciaApelada = :idInstancia ORDER BY rec.tipoMotivo.idTipoMotivo")
	public List<Recaudo> listadoRecaudosPorApelacion(@Param("cedula") String cedula, @Param("codigoLapso") String codigoLapso, @Param("idInstancia") Integer idInstancia);
	
}
