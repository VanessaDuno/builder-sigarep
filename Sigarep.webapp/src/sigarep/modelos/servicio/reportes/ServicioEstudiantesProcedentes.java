package sigarep.modelos.servicio.reportes;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import sigarep.modelos.data.reportes.ListaEstudiantesProcedentes;

@Service("servicioEstudiantesProcedentes")
public class ServicioEstudiantesProcedentes {
	@PersistenceContext
	private EntityManager es;
	
	
	/** Buscar Estudiantes Procedentes Primera Apelaci�n
	 * @param Integer programaAcademico
	 * @return Listado del Resultado de la b�squeda 
	 * @throws No dispara ninguna excepcion.
	 */
	public List<ListaEstudiantesProcedentes> buscarEstudiantesProcedentesPrimeraApelacion(Integer programaAcademico) {
		String queryStatement = "SELECT sa.cedula_estudiante, e.primer_nombre,e.primer_apellido,p.nombre_programa, sa.veredicto, ia.instancia_apelada, es.semestre FROM sigarep.programa_academico as p, sigarep.estudiante as e, sigarep.lapso_academico as la, sigarep.solicitud_apelacion AS sa " +
				"LEFT JOIN sigarep.ESTUDIANTE_SANCIONADO AS es ON (es.cedula_estudiante=sa.cedula_estudiante AND es.codigo_lapso=sa.codigo_lapso) " +
				"LEFT JOIN sigarep.instancia_apelada as ia ON (ia.id_instancia_apelada=sa.id_instancia_apelada) WHERE sa.codigo_lapso= la.codigo_lapso and la.estatus= 'TRUE' " +
				"and p.id_programa=e.id_programa AND sa.id_instancia_apelada= 1 AND sa.cedula_estudiante=e.cedula_estudiante AND sa.veredicto= 'PROCEDENTE' AND e.id_programa= "+""+programaAcademico+" ORDER BY sa.cedula_estudiante,e.primer_nombre desc " ;
	     
	   	Query query = es.createNativeQuery(queryStatement);
	   	
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = query.getResultList();
		List<ListaEstudiantesProcedentes> results = new ArrayList<ListaEstudiantesProcedentes>();
		for (Object[] resultRow : resultSet) {
			results.add(new ListaEstudiantesProcedentes(
					(String)  resultRow[0],
					(String)  resultRow[1],
					(String)  resultRow[2],
					(String)  resultRow[3],
					(String)  resultRow[4],
					(String)  resultRow[5],
					(Integer) resultRow[6]));			
		}
		return results;
	}
	
	/** Buscar Estudiantes Procedentes Recurso de Reconsideraci�n
	 * @param Integer programaAcademico
	 * @return Listado del Resultado de la b�squeda 
	 * @throws No dispara ninguna excepcion.
	 */
	public List<ListaEstudiantesProcedentes> buscarEstudiantesProcedentesRecursoReconsideracion(Integer programaAcademico) {
		String queryStatement = "SELECT sa.cedula_estudiante, e.primer_nombre,e.primer_apellido,p.nombre_programa, sa.veredicto, ia.instancia_apelada, es.semestre FROM sigarep.programa_academico as p,sigarep.estudiante as e,sigarep.lapso_academico as la, sigarep.solicitud_apelacion AS sa " +
				"LEFT JOIN sigarep.ESTUDIANTE_SANCIONADO AS es ON (es.cedula_estudiante=sa.cedula_estudiante AND es.codigo_lapso=sa.codigo_lapso) " +
				"LEFT JOIN sigarep.instancia_apelada as ia ON (ia.id_instancia_apelada=sa.id_instancia_apelada) WHERE sa.codigo_lapso= la.codigo_lapso and la.estatus= 'TRUE' " +
				"and p.id_programa=e.id_programa AND sa.id_instancia_apelada= 2 AND sa.cedula_estudiante=e.cedula_estudiante AND sa.veredicto= 'PROCEDENTE' AND e.id_programa= "+""+programaAcademico+" ORDER BY sa.cedula_estudiante,e.primer_nombre desc " ;
	     
	   	Query query = es.createNativeQuery(queryStatement);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = query.getResultList();
		List<ListaEstudiantesProcedentes> resultado = new ArrayList<ListaEstudiantesProcedentes>();
		for (Object[] resultRow : resultSet) {
			resultado.add(new ListaEstudiantesProcedentes(
					(String)  resultRow[0],
					(String)  resultRow[1],
					(String)  resultRow[2],
					(String)  resultRow[3],
					(String)  resultRow[4],
					(String)  resultRow[5],
					(Integer) resultRow[6]));			
		}
		return resultado;
	}
	
	/** Buscar Estudiantes Procedentes Recurso Jer�rquico
	 * @param Integer programaAcademico
	 * @return Listado del Resultado de la b�squeda 
	 * @throws No dispara ninguna excepcion.
	 */
	public List<ListaEstudiantesProcedentes> buscarEstudiantesProcedentesRecursoJerarquico(Integer programaAcademico) {
		String queryStatement = "SELECT sa.cedula_estudiante, e.primer_nombre,e.primer_apellido,p.nombre_programa, sa.veredicto, ia.instancia_apelada, es.semestre FROM sigarep.programa_academico as p,sigarep.estudiante as e,sigarep.lapso_academico as la, sigarep.solicitud_apelacion AS sa " +
				"LEFT JOIN sigarep.ESTUDIANTE_SANCIONADO AS es ON (es.cedula_estudiante=sa.cedula_estudiante AND es.codigo_lapso=sa.codigo_lapso) " +
				"LEFT JOIN sigarep.instancia_apelada as ia ON (ia.id_instancia_apelada=sa.id_instancia_apelada) WHERE sa.codigo_lapso= la.codigo_lapso and la.estatus= 'TRUE' " +
				"and p.id_programa=e.id_programa AND sa.id_instancia_apelada= 3 AND sa.cedula_estudiante=e.cedula_estudiante AND sa.veredicto= 'PROCEDENTE' AND e.id_programa= "+""+programaAcademico+" ORDER BY sa.cedula_estudiante,e.primer_nombre desc " ;
	     
	   	Query query = es.createNativeQuery(queryStatement);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = query.getResultList();
		List<ListaEstudiantesProcedentes> resultados = new ArrayList<ListaEstudiantesProcedentes>();
		for (Object[] resultRow : resultSet) {
			resultados.add(new ListaEstudiantesProcedentes(
					(String)  resultRow[0],
					(String)  resultRow[1],
					(String)  resultRow[2],
					(String)  resultRow[3],
					(String)  resultRow[4],
					(String)  resultRow[5],
					(Integer) resultRow[6]));			
		}
		return resultados;
	}
}
