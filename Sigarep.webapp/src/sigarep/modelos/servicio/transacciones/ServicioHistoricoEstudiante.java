package sigarep.modelos.servicio.transacciones;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import sigarep.modelos.data.transacciones.HistoricoEstudiante;

@Service("serviciohistoricoestudiante")
public class ServicioHistoricoEstudiante {

	@PersistenceContext
	private EntityManager em;

	public HistoricoEstudiante buscarHistoricoEstudiante(String cedula) {
		String queryStatement2 = "SELECT es.cedula_estudiante, prog.nombre_programa, es.primer_nombre, es.segundo_nombre, "
				+ "es.primer_apellido, es.segundo_apellido, lapac.codigo_lapso, sanm.nombre_sancion, estsan.lapsos_academicos_RP, "
				+ "asig.nombre_asignatura, solape.numero_sesion, solape.fecha_solicitud, mot.descripcion "
				+ "FROM sancion_maestro sanm, programa_academico prog, lapso_academico lapac, "
				+ "instancia_apelada isap, motivo mot, estudiante es, solicitud_apelacion solape, "
				+ "tipo_motivo tipmo, estudiante_sancionado AS estsan "
				+ "LEFT JOIN asignatura_estudiante_sancionado AS asiges ON (asiges.codigo_lapso = estsan.codigo_lapso "
				+ "AND asiges.cedula_estudiante = estsan.cedula_estudiante) "
				+ "LEFT JOIN asignatura AS asig ON asig.codigo_asignatura = asiges.codigo_asignatura "
				+ "WHERE estsan.id_sancion = sanm.id_sancion "
				+ "AND es.cedula_estudiante = ? "
				+ "AND es.id_programa = prog.id_programa "
				+ "AND estsan.codigo_lapso = lapac.codigo_lapso "
				+ "AND solape.cedula_estudiante = estsan.cedula_estudiante "
				+ "AND solape.id_instancia_apelada = isap.id_instancia_apelada "
				+ "AND solape.codigo_lapso = mot.codigo_lapso "
				+ "AND solape.cedula_estudiante = mot.cedula_estudiante "
				+ "AND solape.id_instancia_apelada = mot.id_instancia_apelada "
				+ "AND es.cedula_estudiante = estsan.cedula_estudiante "
				+ "AND solape.codigo_lapso = estsan.codigo_lapso "
				+ "AND tipmo.id_tipo_motivo = mot.id_tipo_motivo";

		Query query = em.createNativeQuery(queryStatement2);
		query.setParameter(1, cedula);

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = query.getResultList();
		HistoricoEstudiante results = new HistoricoEstudiante();
		List<String> motivos = new ArrayList<String>();
		for (Object[] resultRow : resultSet) {
			
			motivos.add((String) resultRow[12]);

			results = (new HistoricoEstudiante((String) resultRow[0],
					(String) resultRow[1],
					(String) resultRow[2] +" "+ resultRow[3], 
					(String) resultRow[4] +" "+ resultRow[5], 
					(String) resultRow[6],
					(String) resultRow[7], (String) resultRow[8],
					(String) resultRow[8], (String) resultRow[9],
					(String) resultRow[10], (Date) resultRow[11], motivos));
		}
		System.out.println(motivos);
		return results;
	}
}
