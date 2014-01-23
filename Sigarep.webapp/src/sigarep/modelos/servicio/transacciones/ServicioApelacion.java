package sigarep.modelos.servicio.transacciones;


import java.util.ArrayList;
import java.util.LinkedList;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import sigarep.modelos.data.maestros.EstadoApelacion;
import sigarep.modelos.data.maestros.Recaudo;
import sigarep.modelos.data.transacciones.ApelacionEstadoApelacion;
import sigarep.modelos.data.transacciones.EstudianteSancionado;
import sigarep.modelos.data.transacciones.SolicitudApelacion;
import sigarep.modelos.repositorio.maestros.IRecaudoDAO;
import sigarep.modelos.repositorio.transacciones.IApelacionEstadoApelacionDAO;
import sigarep.modelos.servicio.maestros.ServicioRecaudo;
import sigarep.modelos.data.maestros.Estudiante;


@Service("serviciolista")
public class ServicioApelacion  {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private IApelacionEstadoApelacionDAO apelacionestadoapelacion;
	@Autowired
	private IRecaudoDAO iRecaudoDAO;
	ServicioRecaudo serviciorecaudo;
	public List<ListaApelacionEstadoApelacion> buscarApelaciones() {
		
		
//FALTA PERIODO DE SANCION
		String queryStatement2 =
				"SELECT es.cedula_estudiante, es.primer_nombre, es.primer_apellido, " +
				"sa.nombre_sancion, es.email, es.telefono, p.nombre_programa, la.codigo_lapso, i.id_instancia_apelada, " +
				"es.segundo_nombre, es.segundo_apellido, a.nombre_asignatura, sap.numero_caso " +
				"FROM sancion_maestro sa, programa_academico p, lapso_academico la, instancia_apelada i, " +
				"solicitud_apelacion sap, estudiante es " +
				"INNER JOIN apelacion_estado_apelacion AS ap ON es.cedula_estudiante = ap.cedula_estudiante " +
				"INNER JOIN estado_apelacion AS m ON m.id_estado_apelacion = ap.id_estado_apelacion, " +
				"estudiante_sancionado AS esa LEFT JOIN asignatura_estudiante_sancionado AS aesa ON  " +
				" (aesa.codigo_lapso = esa.codigo_lapso AND aesa.cedula_estudiante = esa.cedula_estudiante) " +
				"LEFT JOIN asignatura AS a ON a.codigo_asignatura = aesa.codigo_asignatura " +
				"WHERE sa.id_sancion = esa.id_sancion AND  " +
				"m.id_estado_apelacion = ap.id_estado_apelacion  AND  sap.veredicto != ''   " +
				"AND esa.codigo_lapso = la.codigo_lapso  AND i.id_instancia_apelada = sap.id_instancia_apelada  " +
				"AND sap.id_instancia_apelada = ap.id_instancia_apelada AND es.id_programa= p.id_programa " +
				"AND la.estatus = 'true' AND es.cedula_estudiante = " +
				"esa.cedula_estudiante AND es.cedula_estudiante = sap.cedula_estudiante AND ap.id_instancia_apelada = '1' " +
				"AND sap.estatus = 'true' and  sap.cedula_estudiante not in (select ap.cedula_estudiante from " +
				"apelacion_estado_apelacion as ap where  ap.id_instancia_apelada = '2')";
				
		

		Query query = em.createNativeQuery(queryStatement2);

		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = query.getResultList();
		
		List<ListaApelacionEstadoApelacion> results = new ArrayList<ListaApelacionEstadoApelacion>();
		for (Object[] resultRow : resultSet) {
			System.out.println(resultRow[0]);
			System.out.println(resultRow[1]);
			System.out.println(resultRow[2]);
			System.out.println(resultRow[3]);
			
			results.add(new ListaApelacionEstadoApelacion((String) resultRow[0], (String) resultRow[1],
					(String) resultRow[2], (String) resultRow[3], (String) resultRow[4], (String) resultRow[5],
					(String) resultRow[6], (String) resultRow[7], (Integer)(resultRow[8]), (String) resultRow[9],
					 (String)resultRow[10],(String) resultRow[11], (Integer) resultRow[12]));
		}
		
		return results;
	}


	public List<ListaApelacionEstadoApelacion> buscarPorFiltros(ListaApelacionEstadoApelacionFiltros filtros){
		List<ListaApelacionEstadoApelacion> result = new ArrayList<ListaApelacionEstadoApelacion>();
		String programa = filtros.getPrograma().toLowerCase();
		String motivo = filtros.getMotivo().toLowerCase();
		String cedula = filtros.getCedula().toLowerCase();
		String nombre = filtros.getNombre().toLowerCase();
		String apellido = filtros.getApellido().toLowerCase();
		String sancion = filtros.getSancion().toLowerCase();
        if(programa==null || motivo==null || cedula==null 
        		|| nombre==null || apellido==null || sancion==null){
        	result= buscarApelaciones();
        }
        else{
			for (ListaApelacionEstadoApelacion ap : buscarApelaciones())
			{
				if (ap.getPrograma().toLowerCase().contains(programa)&&
						ap.getCedulaEstudiante().toLowerCase().contains(cedula)&&
						ap.getPrimerNombre().toLowerCase().contains(nombre)&&
						ap.getPrimerApellido().toLowerCase().contains(apellido)&&
						ap.getNombreSancion().toLowerCase().contains(sancion)){
					result.add(ap);
				}
			}
        }
		return result;
        } 
	

	public List<ListaRecaudosMotivoEstudiante> buscarRecaudosMotivos(String cedulaEstudiante, String codigoLapso, Integer idInstancia) {
		String queryStatement = 
				"SELECT r.nombre_recaudo, tm.nombre_tipo_motivo " +
				"FROM tipo_motivo tm, motivo m, solicitud_apelacion sap, recaudo r WHERE " +
				"tm.id_tipo_motivo = m.id_tipo_motivo AND " +
				"m.id_instancia_apelada = sap.id_instancia_apelada AND " +
				"m.codigo_lapso = sap.codigo_lapso AND  " +
				"m.cedula_estudiante = sap.cedula_estudiante AND " +
				"r.id_tipo_motivo = m.id_tipo_motivo AND tm.estatus = TRUE AND " +
				"sap.cedula_estudiante = '" +cedulaEstudiante+"' AND" +
				" sap.codigo_lapso = '" +codigoLapso+"' AND " +
				"sap.id_instancia_apelada = 1;";

		Query query = em.createNativeQuery(queryStatement);
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = query.getResultList();
		
		List<ListaRecaudosMotivoEstudiante> results = new ArrayList<ListaRecaudosMotivoEstudiante>();
		List<Recaudo> listaRecaudosGenerales=iRecaudoDAO.buscaRecaudosGenerales();
		for(int i=0; i<listaRecaudosGenerales.size();i++ ){
			Recaudo recaudo = new Recaudo();
			recaudo = listaRecaudosGenerales.get(i);
			results.add(new ListaRecaudosMotivoEstudiante (recaudo.getNombreRecaudo(), recaudo.getTipoMotivo().getNombreTipoMotivo()));
		}
		
		for (Object[] resultRow : resultSet) {
			
			results.add(new ListaRecaudosMotivoEstudiante ((String) resultRow[0], (String) resultRow[1]));
		}
		
		return results;
	}
	
	public List<ListaApelacionEstadoApelacion> buscarApelacionesPorInstancia(Integer idInstanciaApelada) {
		
		
		//FALTA PERIODO DE SANCION
	   String queryStatement3 =
				"SELECT es.cedula_estudiante, es.primer_nombre, es.primer_apellido, sa.nombre_sancion, " +
				"es.email, es.telefono, p.nombre_programa, la.codigo_lapso, i.id_instancia_apelada, es.segundo_nombre, " +
				"es.segundo_apellido, a.nombre_asignatura, sap.numero_caso FROM sancion_maestro sa, programa_academico p, " +
				"lapso_academico la, instancia_apelada i, solicitud_apelacion sap, estudiante es, estudiante_sancionado AS esa " +
				"LEFT JOIN asignatura_estudiante_sancionado AS aesa ON (aesa.codigo_lapso = esa.codigo_lapso AND " +
				"aesa.cedula_estudiante = esa.cedula_estudiante) LEFT JOIN asignatura AS a ON a.codigo_asignatura = aesa.codigo_asignatura " +
				"WHERE sa.id_sancion = esa.id_sancion AND esa.codigo_lapso = la.codigo_lapso  AND i.id_instancia_apelada = sap.id_instancia_apelada " +
				"AND es.id_programa= p.id_programa AND la.estatus = 'TRUE' AND es.cedula_estudiante = esa.cedula_estudiante AND es.cedula_estudiante = sap.cedula_estudiante " +
				"AND sap.id_instancia_apelada = '"+idInstanciaApelada+"' AND sap.estatus = 'TRUE' AND sap.verificado = 'FALSE' AND sap.analizado = 'FALSE';"; 
						
				

				Query query = em.createNativeQuery(queryStatement3);

				
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet = query.getResultList();
				
				List<ListaApelacionEstadoApelacion> results = new ArrayList<ListaApelacionEstadoApelacion>();
				for (Object[] resultRow : resultSet) {
					System.out.println(resultRow[0]);
					System.out.println(resultRow[1]);
					System.out.println(resultRow[2]);
					System.out.println(resultRow[3]);
					
					results.add(new ListaApelacionEstadoApelacion((String) resultRow[0], (String) resultRow[1],
							(String) resultRow[2], (String) resultRow[3], (String) resultRow[4], (String) resultRow[5],
							(String) resultRow[6], (String) resultRow[7], (Integer)(resultRow[8]), (String) resultRow[9],
							 (String)resultRow[10],(String) resultRow[11], (Integer) resultRow[12]));
				}
				
				return results;
			}
	
	public List<ListaRecaudosMotivoEstudiante> buscarTiposMotivoSolicitud(String cedulaEstudiante, String codigoLapso, Integer idInstancia) {
		
		   String queryStatement4 =
					"SELECT tm.nombre_tipo_motivo, tm.descripcion FROM tipo_motivo AS tm, " +
					"motivo AS m, solicitud_apelacion AS sap WHERE tm.id_tipo_motivo = m.id_tipo_motivo " +
					"AND sap.cedula_estudiante = m.cedula_estudiante AND " +
					"sap.codigo_lapso = m.codigo_lapso AND " +
					"sap.id_instancia_apelada = m.id_instancia_apelada AND tm.estatus = TRUE AND sap.cedula_estudiante = '"+cedulaEstudiante+"' " +
					"AND sap.codigo_lapso = '" + codigoLapso +"' AND sap.id_instancia_apelada = '"+idInstancia+"';"; 
							
					Query query = em.createNativeQuery(queryStatement4);

					@SuppressWarnings("unchecked")
					List<Object[]> resultSet = query.getResultList();
					
					List<ListaRecaudosMotivoEstudiante> results = new ArrayList<ListaRecaudosMotivoEstudiante>();
					for (Object[] resultRow : resultSet) {
						
						results.add(new ListaRecaudosMotivoEstudiante ((String) resultRow[0], (String) resultRow[1], null));
					}
					
					return results;
	}
}

