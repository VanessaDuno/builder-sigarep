package sigarep.modelos.servicio.maestros;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sigarep.modelos.data.maestros.LapsoAcademico;
import sigarep.modelos.repositorio.maestros.ILapsoAcademicoDAO;

/**
 * Clase  ServicioLapsoAcademico
 * 
 * @author BUILDER
 * @version 1.0
 * @since 18/12/2013
 */
@Service("serviciolapsoacademico")
public class ServicioLapsoAcademico {
	private @Autowired
	ILapsoAcademicoDAO iLapsoAcademico;

	/**
	 * Guardar lapso acad�mico
	 * 
	 * @return objeto guardado
	 * @param el objeto lapso Academico
	 * @throws No dispara ninguna excepci�n.
	 */
	public void guardarLapso(LapsoAcademico lapsoA) {
		iLapsoAcademico.save(lapsoA);
	}

	/**
	 * lapso acad�mico activo
	 * 
	 * @return el lapso acad�mico activo
	 * @param vacio
	 * @throws No dispara ninguna excepci�n.
	 */
	public LapsoAcademico buscarLapsoActivo() {
		return iLapsoAcademico.findByEstatusTrue();
	}

	/**
	 * Lista de lapsos acad�mico inactivos
	 * 
	 * @return Lista de lapsos acad�micos inactivos
	 * @param vacio
	 * @throws No dispara ninguna excepci�n.
	 */
	public List<LapsoAcademico> listadoLapsoAcademicoInactivos() {
		List<LapsoAcademico> LapsoAcademicoLista = iLapsoAcademico
				.findByEstatusFalse();
		return LapsoAcademicoLista;
	}

	/**
	 * Lista de lapso acad�mico
	 * 
	 * @return Lista de todos los lapsos acad�micos activas e inactivas
	 * @param vacio
	 * @throws No dispara ninguna excepci�n.
	 */
	public List<LapsoAcademico> buscarTodosLosLapsos() {
		return iLapsoAcademico.findAll();
	}

	/**
	 * Buscar un lapso acad�mico
	 * 
	 * @return lapso acad�mico buscado
	 * @param String codigolapso
	 * @throws No
	 *             dispara ninguna excepcion.
	 */
	public LapsoAcademico buscarUnLapsoAcademico(String codigoLapso) {
		return iLapsoAcademico.findOne(codigoLapso);
	}

	/**
	 * Buscar un lapso acad�mico por codigo lapso
	 * 
	 * @return Busca un lapso acad�mico por c�digo lapso
	 * @param String codigolapso
	 * @throws No dispara ninguna excepcion.
	 */

	public List<LapsoAcademico> filtrarLapsoAcademico(String codigoLapso) {
		List<LapsoAcademico> result = new LinkedList<LapsoAcademico>();
		if (codigoLapso == null || "".equals(codigoLapso)) {
			result = iLapsoAcademico.findAll();
		} else {
			for (LapsoAcademico lapso : iLapsoAcademico.findAll()) {
				if (lapso.getCodigoLapso().toLowerCase()
						.contains(codigoLapso.toLowerCase())) {
					result.add(lapso);
				}
			}
		}
		return result;

	}
}
