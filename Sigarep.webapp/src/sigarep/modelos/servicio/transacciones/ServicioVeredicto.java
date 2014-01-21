package sigarep.modelos.servicio.transacciones;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sigarep.modelos.lista.ListaGenericaSancionados;
import sigarep.modelos.repositorio.transacciones.ISolicitudApelacionDAO;
import sigarep.modelos.repositorio.transacciones.IApelacionEstadoApelacionDAO;
import sigarep.modelos.data.maestros.Estudiante;
import sigarep.modelos.data.transacciones.EstudianteSancionado;
import sigarep.modelos.data.maestros.SancionMaestro;
import sigarep.modelos.data.maestros.ProgramaAcademico;
import sigarep.modelos.data.transacciones.SolicitudApelacion;
import sigarep.modelos.data.transacciones.ApelacionEstadoApelacion;
import sigarep.modelos.data.maestros.EstadoApelacion;


@Service("servicioveredicto")
public class ServicioVeredicto {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private IApelacionEstadoApelacionDAO apelacionestadoapelacion;
	
	public List<ListaGenericaSancionados> buscarApelacionesVeredicto1() {
		
		String queryStatementVer =
				"SELECT es.cedula_estudiante, es.primer_nombre, es.segundo_nombre, es.primer_apellido, es.segundo_apellido, es.email, p.nombre_programa," +
				"esa.indice_grado, sa.nombre_sancion, esa.lapsos_academicos_rp, esa.codigo_lapso, sap.numero_caso, i.id_instancia_apelada FROM sancion_maestro sa," +
				"programa_academico p, estudiante_sancionado esa, lapso_academico la, instancia_apelada i,  estudiante es " + 
				"INNER JOIN solicitud_apelacion  AS sap ON es.cedula_estudiante = sap.cedula_estudiante " +
				"WHERE sa.id_sancion = esa.id_sancion " +
				"AND es.cedula_estudiante = esa.cedula_estudiante " +
				"AND esa.codigo_lapso = la.codigo_lapso AND i.id_instancia_apelada = sap.id_instancia_apelada " +
				"AND es.id_programa= p.id_programa " +
				"AND sap.analizado=TRUE " +
				"AND i.id_instancia_apelada=1 " +
				"AND (sap.veredicto IS NULL OR sap.codigo_sesion IS NULL)";
		Query query = em.createNativeQuery(queryStatementVer);	
	
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = query.getResultList();
		
		List<ListaGenericaSancionados> results = new ArrayList<ListaGenericaSancionados>();
		for (Object[] resultRow : resultSet) {
			results.add(
					new ListaGenericaSancionados(
							(String) resultRow[0],
							(String) resultRow[1],
							(String) resultRow[2],
							(String)resultRow[3],
							(String) resultRow[4], 
							(String) resultRow[5], 
							(String) resultRow[6],
							(Float) resultRow[7], 
							(String) resultRow[8],
							(String) resultRow[9],
							(String) resultRow[10],
							(Integer) resultRow[11],
							(Integer) resultRow[12]));
		}
		return results;
	}

	public List<ListaGenericaSancionados> filtrarApelacionesVeredicto1(String cedula, String nombre, String apellido, String programa, String sancion){
		List<ListaGenericaSancionados> result = new ArrayList<ListaGenericaSancionados>();
        if(cedula==null || nombre==null || apellido==null || programa==null ||  sancion==null){
        	result= buscarApelacionesVeredicto1();
        }
        else{
			for (ListaGenericaSancionados b: buscarApelacionesVeredicto1())
			{
				if (b.getCedulaEstudiante().toLowerCase().contains(cedula.toLowerCase())&&
					b.getPrimerNombre().toLowerCase().contains(nombre.toLowerCase())&&
					b.getPrimerApellido().toLowerCase().contains(apellido.toLowerCase())&&
					b.getProgramaAcademico().toLowerCase().contains(programa.toLowerCase()) &&
					b.getNombreSancion().toLowerCase().contains(sancion.toLowerCase())){
					result.add(b);
				}
			}
        }
		return result;
	}

}
