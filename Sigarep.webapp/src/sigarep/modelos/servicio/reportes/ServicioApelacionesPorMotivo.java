package sigarep.modelos.servicio.reportes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;



import sigarep.modelos.data.reportes.ApelacionesPorMotivo;
import sigarep.modelos.data.reportes.Sancionados;

@Service("servicioapelacionespormotivo")
public class ServicioApelacionesPorMotivo {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<ApelacionesPorMotivo> buscarPorMotivoResultado_ProgramaSancion(String codigo_lapso, String tipo_sancion, String programa) {
		String queryStatement =
				"select v.motivo as motivo, sum(v.apelaciones) apelaciones, sum(v.procedente) procedentes , " +
						"(SELECT COUNT(*) total FROM tipo_motivo timo, solicitud_apelacion sa " +
						"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
						"and essa.codigo_lapso = sa.codigo_lapso " +
						"INNER JOIN motivo as mo on mo.cedula_estudiante = sa.cedula_estudiante " +
						"and mo.codigo_lapso = sa.codigo_lapso " +
						"and mo.id_instancia_apelada = sa.id_instancia_apelada " +
						"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
						"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
						"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
						"WHERE mo.id_tipo_motivo = timo.id_tipo_motivo " +
						"and mo.id_tipo_motivo <> 1 " +
						"and mo.id_tipo_motivo <> 2 " +
						"and mo.id_tipo_motivo <> 3 " +
						"and sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
						"and prog.nombre_programa = "+ "'"+programa+"' " +
						"and sanma.nombre_sancion = "+ "'"+tipo_sancion+"' )" +
								"from " +
								"(select b.motivo as motivo, sum(b.apelaciones) apelaciones, 0 as procedente " +
								"from " +
								"(SELECT distinct timo.nombre_tipo_motivo as motivo,count(sa.cedula_estudiante) " +
								"as apelaciones, 0 as procedente " +
								"from tipo_motivo timo, solicitud_apelacion sa " +
								"INNER JOIN estudiante_sancionado as essa " +
								"on essa.cedula_estudiante = sa.cedula_estudiante " +
								"and essa.codigo_lapso = sa.codigo_lapso " +
								"INNER JOIN motivo as mo on mo.cedula_estudiante = sa.cedula_estudiante " +
								"and mo.codigo_lapso = sa.codigo_lapso " +
								"and mo.id_instancia_apelada = sa.id_instancia_apelada " +
								"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
								"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
								"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
								"WHERE mo.id_tipo_motivo = timo.id_tipo_motivo " +
								"and mo.id_tipo_motivo <> 1 " +
								"and mo.id_tipo_motivo <> 2 " +
								"and mo.id_tipo_motivo <> 3 " +
								"and sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
								"and prog.nombre_programa = "+ "'"+programa+"' " +
								"and sanma.nombre_sancion = "+ "'"+tipo_sancion+"' " +
								"group by motivo) as b " +
								"group by b.motivo " +
								"union all " +
								"select timo.nombre_tipo_motivo as motivo, 0 as apelaciones, count(sa.veredicto) " +
								"as procedente " +
								"from tipo_motivo timo, solicitud_apelacion sa " +
								"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
								"and essa.codigo_lapso = sa.codigo_lapso " +
								"INNER JOIN motivo as mo on mo.cedula_estudiante = sa.cedula_estudiante " +
								"and mo.codigo_lapso = sa.codigo_lapso " +
								"and mo.id_instancia_apelada = sa.id_instancia_apelada " +
								"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
								"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
								"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
								"WHERE mo.id_tipo_motivo = timo.id_tipo_motivo " +
								"and mo.id_tipo_motivo <> 1 " +
								"and mo.id_tipo_motivo <> 2 " +
								"and mo.id_tipo_motivo <> 3 " +
								"and sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
								"and prog.nombre_programa = "+ "'"+programa+"' " +
								"and sanma.nombre_sancion = "+ "'"+tipo_sancion+"' " +
								"and sa.veredicto = 'NO PROCEDENTE' " +
								"group by timo.nombre_tipo_motivo) as v " +
								"group by v.motivo " +
								"order by apelaciones desc";
						
						
								
				Query query = em.createNativeQuery(queryStatement);
				
				
				
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet = query.getResultList();
				
				List<ApelacionesPorMotivo> results = new ArrayList<ApelacionesPorMotivo>();
				for (Object[] resultRow : resultSet) {
					results.add(new ApelacionesPorMotivo(
							(String) resultRow[0],
							((BigDecimal) resultRow[1]).intValue(),
							((BigDecimal) resultRow[2]).intValue(),
							((BigInteger) resultRow[3]).intValue()));
				}
				
				return results;
	}
	
	public List<ApelacionesPorMotivo> buscarPorMotivoResultado_Programa(String codigo_lapso, String programa) {
		String queryStatement =
				"select v.motivo as motivo, sum(v.apelaciones) apelaciones, sum(v.procedente) procedentes, " +
				"(SELECT COUNT(*) total FROM tipo_motivo timo, solicitud_apelacion sa " +
				"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
				"and essa.codigo_lapso = sa.codigo_lapso " +
				"INNER JOIN motivo as mo on mo.cedula_estudiante = sa.cedula_estudiante " +
				"and mo.codigo_lapso = sa.codigo_lapso " +
				"and mo.id_instancia_apelada = sa.id_instancia_apelada " +
				"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
				"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
				"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
				"WHERE mo.id_tipo_motivo = timo.id_tipo_motivo " +
				"and mo.id_tipo_motivo <> 1 " +
				"and mo.id_tipo_motivo <> 2 " +
				"and mo.id_tipo_motivo <> 3 " +
				"and sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
				"and prog.nombre_programa = "+ "'"+programa+"' )" +
				"from " +
				"(select b.motivo as motivo, sum(b.apelaciones) apelaciones, 0 as procedente " +
				"from " +
				"(SELECT distinct timo.nombre_tipo_motivo as motivo,count(sa.cedula_estudiante) " +
				"as apelaciones, 0 as procedente " +
				"from tipo_motivo timo, solicitud_apelacion sa " +
				"INNER JOIN estudiante_sancionado as essa " +
				"on essa.cedula_estudiante = sa.cedula_estudiante " +
				"and essa.codigo_lapso = sa.codigo_lapso " +
				"INNER JOIN motivo as mo on mo.cedula_estudiante = sa.cedula_estudiante " +
				"and mo.codigo_lapso = sa.codigo_lapso " +
				"and mo.id_instancia_apelada = sa.id_instancia_apelada " +
				"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
				"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
				"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
				"WHERE mo.id_tipo_motivo = timo.id_tipo_motivo " +
				"and mo.id_tipo_motivo <> 1 " +
				"and mo.id_tipo_motivo <> 2 " +
				"and mo.id_tipo_motivo <> 3 " +
				"and sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
				"and prog.nombre_programa = "+ "'"+programa+"' " +
				"group by motivo) as b " +
				"group by b.motivo " +
				"union all " +
				"select timo.nombre_tipo_motivo as motivo, 0 as apelaciones, count(sa.veredicto) " +
				"as procedente " +
				"from tipo_motivo timo, solicitud_apelacion sa " +
				"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
				"and essa.codigo_lapso = sa.codigo_lapso " +
				"INNER JOIN motivo as mo on mo.cedula_estudiante = sa.cedula_estudiante " +
				"and mo.codigo_lapso = sa.codigo_lapso " +
				"and mo.id_instancia_apelada = sa.id_instancia_apelada " +
				"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
				"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
				"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
				"WHERE mo.id_tipo_motivo = timo.id_tipo_motivo " +
				"and mo.id_tipo_motivo <> 1 " +
				"and mo.id_tipo_motivo <> 2 " +
				"and mo.id_tipo_motivo <> 3 " +
				"and sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
				"and prog.nombre_programa = "+ "'"+programa+"' " +
				"and sa.veredicto = 'NO PROCEDENTE' " +
				"group by timo.nombre_tipo_motivo) as v " +
				"group by v.motivo " +
				"order by apelaciones desc";
//		"" +
//		"" +
//		"" +
//		"" +
//		"" +
//		"+
//				"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
//				"and essa.codigo_lapso = sa.codigo_lapso " +
//				"INNER JOIN motivo as mo on mo.cedula_estudiante = sa.cedula_estudiante " +
//				"and mo.codigo_lapso = sa.codigo_lapso " +
//				"and mo.id_instancia_apelada = sa.id_instancia_apelada " +
//				"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
//				"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
//				"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
//				"WHERE mo.id_tipo_motivo = timo.id_tipo_motivo " +
//				"and sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
//				"and prog.nombre_programa = "+ "'"+programa+"' " +
//				//"and sa.veredicto = 'Aprobado' " +
//				"GROUP BY timo.nombre_tipo_motivo, sa.veredicto " +
//				"ORDER BY numeroapelaciones desc, timo.nombre_tipo_motivo asc, sa.veredicto asc";
//		
						
		Query query = em.createNativeQuery(queryStatement);
		
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = query.getResultList();
		
		List<ApelacionesPorMotivo> results = new ArrayList<ApelacionesPorMotivo>();
		for (Object[] resultRow : resultSet) {
			results.add(new ApelacionesPorMotivo(
					(String) resultRow[0],
					((BigDecimal) resultRow[1]).intValue(),
					((BigDecimal) resultRow[2]).intValue(),
					((BigInteger) resultRow[3]).intValue()));
		}
		
		return results;
	}
	
	
	
	public List<ApelacionesPorMotivo> buscarPorInstanciaResultado_Programa(String codigo_lapso, String programa) {
		String queryStatement =
				"select v.instancia as instancia, sum(v.apelaciones) apelaciones, sum(v.procedente) procedentes, " +
				"(SELECT COUNT(*) total FROM solicitud_apelacion sa " +
				"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
				"and essa.codigo_lapso = sa.codigo_lapso " +
				"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
				"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
				"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
				"WHERE sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
				//"and sanma.nombre_sancion = 'RR' " +
				"and prog.nombre_programa = "+ "'"+programa+"' )" +
				"from " +
				"(select b.instancia as instancia, sum(b.apelaciones) apelaciones, 0 as procedente " +
				"from " +
				"(SELECT ins.instancia_apelada as instancia,count(sa.cedula_estudiante) as apelaciones, 0 as procedente " +
				"from instancia_apelada ins, solicitud_apelacion sa " +
				"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
				"and essa.codigo_lapso = sa.codigo_lapso " +
				"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
				"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
				"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
				"WHERE ins.id_instancia_apelada = sa.id_instancia_apelada " +
				"and sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
				//"and sanma.nombre_sancion = 'RR' " +
				"and prog.nombre_programa = "+ "'"+programa+"' " +
				"group by instancia) as b " +
				"group by b.instancia " +
				"union all " +
				"select ins.instancia_apelada as instancia, 0 as apelaciones, count(sa.veredicto) as procedente " +
				"from instancia_apelada ins, solicitud_apelacion sa " +
				"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
				"and essa.codigo_lapso = sa.codigo_lapso " +
				"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
				"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
				"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
				"WHERE ins.id_instancia_apelada = sa.id_instancia_apelada " +
				"and sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
				//"and sanma.nombre_sancion = 'RR' " +
				"and prog.nombre_programa = "+ "'"+programa+"' " +
				"and sa.veredicto = 'NO PROCEDENTE' " +
				"group by instancia ) as v " +
				"group by v.instancia " +
				"order by apelaciones desc";
		
						
		Query query = em.createNativeQuery(queryStatement);
		
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = query.getResultList();
		
		List<ApelacionesPorMotivo> results = new ArrayList<ApelacionesPorMotivo>();
		for (Object[] resultRow : resultSet) {
			results.add(new ApelacionesPorMotivo(
					(String) resultRow[0],
					((BigDecimal) resultRow[1]).intValue(),
					((BigDecimal) resultRow[2]).intValue(),
					((BigInteger) resultRow[3]).intValue()));
		}
		
		return results;
	}
	
	public List<ApelacionesPorMotivo> buscarPorInstanciaResultado_ProgramaSancion(String codigo_lapso, String tipo_sancion, String programa) {
		String queryStatement =
				"select v.motivo as motivo, sum(v.apelaciones) apelaciones, sum(v.procedente) procedentes , " +
				"(SELECT COUNT(*) total FROM tipo_motivo timo, solicitud_apelacion sa " +
				"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
				"and essa.codigo_lapso = sa.codigo_lapso " +
				"INNER JOIN motivo as mo on mo.cedula_estudiante = sa.cedula_estudiante " +
				"and mo.codigo_lapso = sa.codigo_lapso " +
				"and mo.id_instancia_apelada = sa.id_instancia_apelada " +
				"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
				"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
				"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
				"WHERE mo.id_tipo_motivo = timo.id_tipo_motivo " +
				"and mo.id_tipo_motivo <> 1 " +
				"and mo.id_tipo_motivo <> 2 " +
				"and mo.id_tipo_motivo <> 3 " +
				"and sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
				"and prog.nombre_programa = "+ "'"+programa+"' " +
				"and sanma.nombre_sancion = "+ "'"+tipo_sancion+"' " +
						"from " +
						"(select b.motivo as motivo, sum(b.apelaciones) apelaciones, 0 as procedente " +
						"from " +
						"(SELECT distinct timo.nombre_tipo_motivo as motivo,count(sa.cedula_estudiante) " +
						"as apelaciones, 0 as procedente " +
						"from tipo_motivo timo, solicitud_apelacion sa " +
						"INNER JOIN estudiante_sancionado as essa " +
						"on essa.cedula_estudiante = sa.cedula_estudiante " +
						"and essa.codigo_lapso = sa.codigo_lapso " +
						"INNER JOIN motivo as mo on mo.cedula_estudiante = sa.cedula_estudiante " +
						"and mo.codigo_lapso = sa.codigo_lapso " +
						"and mo.id_instancia_apelada = sa.id_instancia_apelada " +
						"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
						"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
						"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
						"WHERE mo.id_tipo_motivo = timo.id_tipo_motivo " +
						"and mo.id_tipo_motivo <> 1 " +
						"and mo.id_tipo_motivo <> 2 " +
						"and mo.id_tipo_motivo <> 3 " +
						"and sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
						"and prog.nombre_programa = "+ "'"+programa+"' " +
						"and sanma.nombre_sancion = "+ "'"+tipo_sancion+"' " +
						"group by motivo) as b " +
						"group by b.motivo " +
						"union all " +
						"select timo.nombre_tipo_motivo as motivo, 0 as apelaciones, count(sa.veredicto) " +
						"as procedente " +
						"from tipo_motivo timo, solicitud_apelacion sa " +
						"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
						"and essa.codigo_lapso = sa.codigo_lapso " +
						"INNER JOIN motivo as mo on mo.cedula_estudiante = sa.cedula_estudiante " +
						"and mo.codigo_lapso = sa.codigo_lapso " +
						"and mo.id_instancia_apelada = sa.id_instancia_apelada " +
						"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
						"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
						"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
						"WHERE mo.id_tipo_motivo = timo.id_tipo_motivo " +
						"and mo.id_tipo_motivo <> 1 " +
						"and mo.id_tipo_motivo <> 2 " +
						"and mo.id_tipo_motivo <> 3 " +
						"and sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
						"and prog.nombre_programa = "+ "'"+programa+"' " +
						"and sanma.nombre_sancion = "+ "'"+tipo_sancion+"' " +
						"and sa.veredicto = 'NO PROCEDENTE' " +
						"group by timo.nombre_tipo_motivo) as v " +
						"group by v.motivo " +
						"order by apelaciones desc";
				
				
						
		Query query = em.createNativeQuery(queryStatement);
		
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = query.getResultList();
		
		List<ApelacionesPorMotivo> results = new ArrayList<ApelacionesPorMotivo>();
		for (Object[] resultRow : resultSet) {
			results.add(new ApelacionesPorMotivo(
					(String) resultRow[0],
					((BigDecimal) resultRow[1]).intValue(),
					((BigDecimal) resultRow[2]).intValue(),
					((BigDecimal) resultRow[3]).intValue()));
		}
		
		return results;
	}
	

	public List<ApelacionesPorMotivo> buscarPorSexoResultado_Programa(String codigo_lapso, String programa) {
		String queryStatement =
				"select v.sexo as sexo, sum(v.apelaciones) apelaciones, sum(v.procedente) procedentes, " +
				"(SELECT COUNT(*) total FROM solicitud_apelacion sa " +
				"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
				"and essa.codigo_lapso = sa.codigo_lapso " +
				"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
				"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
				"WHERE sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
				"and prog.nombre_programa = "+ "'"+programa+"' )" +
				"from " +
				"(select b.sexo as sexo, sum(b.apelaciones) apelaciones, 0 as procedente " +
				"from " +
				"(SELECT es.sexo as sexo,count(sa.cedula_estudiante) as apelaciones, 0 as procedente " +
				"from solicitud_apelacion sa " +
				"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
				"and essa.codigo_lapso = sa.codigo_lapso " +
				"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
				"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
				"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
				"WHERE sa.codigo_lapso = "+"'"+codigo_lapso+"' " +
				"and prog.nombre_programa = "+ "'"+programa+"' " +
				"group by sexo) as b " +
				"group by b.sexo " +
				"union all " +
				"select es.sexo as sexo, 0 as apelaciones, count(sa.veredicto) as procedente " +
				"from solicitud_apelacion sa " +
				"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
				"and essa.codigo_lapso = sa.codigo_lapso " +
				"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
				"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
				"WHERE sa.codigo_lapso = "+"'"+codigo_lapso+"' " +
				"and prog.nombre_programa = "+ "'"+programa+"' " +
				"and sa.veredicto = 'NO PROCEDENTE' " +
				"group by sexo) as v " +
				"group by v.sexo " +
				"order by apelaciones desc";
		
						
		Query query = em.createNativeQuery(queryStatement);
		
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = query.getResultList();
		
		List<ApelacionesPorMotivo> results = new ArrayList<ApelacionesPorMotivo>();
		for (Object[] resultRow : resultSet) {
			results.add(new ApelacionesPorMotivo(
					(String) resultRow[0],
					((BigDecimal) resultRow[1]).intValue(),
					((BigDecimal) resultRow[2]).intValue(),
					((BigInteger) resultRow[3]).intValue()));
		}
		
		return results;
	}
	
	public List<ApelacionesPorMotivo> buscarPorSexoResultado_ProgramaSancion(String codigo_lapso, String tipo_sancion, String programa) {
		String queryStatement =
				"select v.sexo as sexo, sum(v.apelaciones) apelaciones, sum(v.procedente) procedentes, " +
						"(SELECT COUNT(*) total FROM solicitud_apelacion sa " +
						"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
						"and essa.codigo_lapso = sa.codigo_lapso " +
						"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
						"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
						"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
						"WHERE sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
						"and prog.nombre_programa = "+ "'"+programa+"' " +
						"and sanma.nombre_sancion = "+ "'"+tipo_sancion+"'" + ")" +
						"from " +
						"(select b.sexo as sexo, sum(b.apelaciones) apelaciones, 0 as procedente " +
						"from " +
						"(SELECT es.sexo as sexo,count(sa.cedula_estudiante) as apelaciones, 0 as procedente " +
						"from solicitud_apelacion sa " +
						"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
						"and essa.codigo_lapso = sa.codigo_lapso " +
						"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
						"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
						"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
						"WHERE --ins.id_instancia_apelada = sa.id_instancia_apelada and " +
						"sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
						"and prog.nombre_programa = "+ "'"+programa+"' " +
						"and sanma.nombre_sancion = "+ "'"+tipo_sancion+"'" +
						"group by sexo) as b " +
						"group by b.sexo " +
						"union all " +
						"select es.sexo as sexo, 0 as apelaciones, count(sa.veredicto) as procedente " +
						"from solicitud_apelacion sa " +
						"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
						"and essa.codigo_lapso = sa.codigo_lapso " +
						"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
						"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
						"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
						"WHERE sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
						"and prog.nombre_programa = "+ "'"+programa+"' " +
						"and sanma.nombre_sancion = "+ "'"+tipo_sancion+"'" +
						"and sa.veredicto = 'NO PROCEDENTE' " +
						"group by sexo ) as v " +
						"group by v.sexo " +
						"order by apelaciones desc";
				
				
						
		Query query = em.createNativeQuery(queryStatement);
		
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = query.getResultList();
		
		List<ApelacionesPorMotivo> results = new ArrayList<ApelacionesPorMotivo>();
		for (Object[] resultRow : resultSet) {
			results.add(new ApelacionesPorMotivo(
					(String) resultRow[0],
					((BigDecimal) resultRow[1]).intValue(),
					((BigDecimal) resultRow[2]).intValue(),
					((BigDecimal) resultRow[3]).intValue()));
		}
		
		return results;
	}
	
	public List<Sancionados> buscarSancionados(String codigo_lapso, String nombre_programa){
		String queryStatement =
				"SELECT es.cedula_estudiante, es.primer_nombre, es.primer_apellido, " +
				"sanma.nombre_sancion, sa.veredicto, sa.observacion " +
				"FROM tipo_motivo timo, solicitud_apelacion sa " +
				"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
				"and essa.codigo_lapso = sa.codigo_lapso " +
				"INNER JOIN motivo as mo on mo.cedula_estudiante = sa.cedula_estudiante " +
				"and mo.codigo_lapso = sa.codigo_lapso " +
				"and mo.id_instancia_apelada = sa.id_instancia_apelada " +
				"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
				"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
				"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
				"WHERE mo.id_tipo_motivo = timo.id_tipo_motivo " +
				"and sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
				"and prog.nombre_programa = "+ "'"+nombre_programa+"' ";

		
		Query query = em.createNativeQuery(queryStatement);
		
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = query.getResultList();
		
		List<Sancionados> results = new ArrayList<Sancionados>();
		for (Object[] resultRow : resultSet) {
			results.add(new Sancionados((String) resultRow[0], (String) resultRow[1], (String) resultRow[2], 
										(String) resultRow[3]));
		}
		System.out.println(results.get(0).getCedula());
		return results;
	}
	
	public List<Sancionados> buscarSancionadosPrueba(/*String instancia, String programa, String sancion*/){
		
		/*String adicioninstancia;
		String adicionprograma;
		String adicionsancion;
		
		if(instancia.equals("TODOS")){
			adicioninstancia="";
			
		}else{
			adicioninstancia = " AND instancia_apelada.instancia_apelada = '"+instancia+ "' ";	;
		}
		
		if(programa.equals("TODOS")){
			adicionprograma	="";
				
		}else{adicionprograma	=" AND programa_academico.nombre_programa = '"+programa+ "' ";}
			
		if(sancion.equals("TODOS")){
			adicionsancion="";	
					
		}else{adicionsancion=" AND sancion_maestro.nombre_sancion = '"+sancion+ "' ";	;	}
		*/
		System.out.println();
		
		String queryStatement =
//				"SELECT es.cedula_estudiante, es.primer_nombre, es.primer_apellido, " +
//				"sanma.nombre_sancion, sa.veredicto, sa.observacion " +
//				"FROM tipo_motivo timo, solicitud_apelacion sa " +
//				"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
//				"and essa.codigo_lapso = sa.codigo_lapso " +
//				"INNER JOIN motivo as mo on mo.cedula_estudiante = sa.cedula_estudiante " +
//				"and mo.codigo_lapso = sa.codigo_lapso " +
//				"and mo.id_instancia_apelada = sa.id_instancia_apelada " +
//				"INNER JOIN sancion_maestro as sanma on essa.id_sancion=sanma.id_sancion " +
//				"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
//				"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
//				"WHERE mo.id_tipo_motivo = timo.id_tipo_motivo " +
//				"and sa.codigo_lapso = "+ "'"+codigo_lapso+"' " +
//				"and prog.nombre_programa = "+ "'"+nombre_programa+"' ";
//				
//	" SELECT  estudiante.cedula_estudiante, estudiante.primer_nombre, estudiante.primer_apellido, " +
//	" sancion_maestro.nombre_sancion, solicitud_apelacion.veredicto, solicitud_apelacion.observacion" +
//	" FROM programa_academico, estudiante, lapso_academico, estudiante_sancionado, sancion_maestro, instancia_apelada, solicitud_apelacion" +
//	" WHERE programa_academico.id_programa = estudiante.id_programa " +
//	" AND estudiante.cedula_estudiante = estudiante_sancionado.cedula_estudiante AND" +
//	" lapso_academico.codigo_lapso = estudiante_sancionado.codigo_lapso " +
//	" AND estudiante_sancionado.cedula_estudiante = solicitud_apelacion.cedula_estudiante AND" +
//	" estudiante_sancionado.codigo_lapso = solicitud_apelacion.codigo_lapso " +
//	" AND sancion_maestro.id_sancion = estudiante_sancionado.id_sancion AND" +
//	" instancia_apelada.id_instancia_apelada = solicitud_apelacion.id_instancia_apelada"; //+adicioninstancia+adicionprograma+adicionsancion+			  
//	//" ;"
//		//		 ;
//

		
		"select es.cedula_estudiante, es.primer_nombre, es.primer_apellido, sa.veredicto " +
		"from solicitud_apelacion sa " +
		"INNER JOIN estudiante_sancionado as essa on essa.cedula_estudiante = sa.cedula_estudiante " +
		"and essa.codigo_lapso = sa.codigo_lapso " +
		"INNER JOIN estudiante as es on essa.cedula_estudiante = es.cedula_estudiante " +
		"INNER JOIN programa_academico as prog on es.id_programa = prog.id_programa " +
		"WHERE sa.codigo_lapso = '2013-2' " +
		"and prog.nombre_programa = 'Ingenier�a en Inform�tica'";
		
		Query query = em.createNativeQuery(queryStatement);
		
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = query.getResultList();
		
		List<Sancionados> results = new ArrayList<Sancionados>();
		for (Object[] resultRow : resultSet) {
			results.add(new Sancionados((String) resultRow[0], (String) resultRow[1], (String) resultRow[2], 
										(String) resultRow[3]));
		}
		//System.out.println(results.get(0).getCedula());
		return results;
	}



}
