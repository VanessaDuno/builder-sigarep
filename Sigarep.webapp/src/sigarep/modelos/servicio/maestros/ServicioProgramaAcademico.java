package sigarep.modelos.servicio.maestros;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sigarep.modelos.data.maestros.ProgramaAcademico;
import sigarep.modelos.repositorio.maestros.IProgramaAcademicoDAO;

/**
 * Clase  ServicioProgramaAcademico
 * 
 * @author BUILDER
 * @version 1.0
 * @since 18/12/2013
 */

@Service("servicioprogramaacademico")
public class ServicioProgramaAcademico {
	private @Autowired
	IProgramaAcademicoDAO pro;

	/**guardarPrograma acad�mico
	 * @param ProgramaAcademico proa
	 * @return objeto guardado
	 */
	public void guardarPrograma(ProgramaAcademico proa) {
		if (proa.getIdPrograma() != null)
			pro.save(proa);
		else{
			proa.setIdPrograma(pro.buscarUltimoID()+1);
			pro.save(proa);
		}
	}

	/**
	 * Eliminar Programa
	 * 
	 * @param Integer idPrograma
	 * @return permite la eliminaci�n l�gica
	 * @throws No dispara ninguna excepci�n.
	 */
	public void eliminarPrograma(Integer idPrograma) {
		ProgramaAcademico miPrograma = pro.findOne(idPrograma);
		miPrograma.setEstatusPrograma(false);
		pro.save(miPrograma);
	}
	/**
	 * Listado Programas
	 * 
	 * @param vac�o
	 * @return listadoEnlaceInteres con estatus = true
	 * @throws No dispara ninguna excepci�n.
	 */
	public List<ProgramaAcademico> listadoProgramas() {
		List<ProgramaAcademico> programasLista = pro.findByEstatusProgramaTrue();
		return programasLista;
	}
	/**
	 * Buscar Programa
	 * 
	 * @param Integer idProgramaAcademico
	 * @return programa.
	 * @throws No dispara ninguna excepci�n.
	 */
	public ProgramaAcademico buscarPrograma(Integer idProgramaAcademico) {
		return pro.findOne(idProgramaAcademico);
	}

	/**
	 * Buscar Programa
	 * 
	 * @param String programa
	 * @return programa.
	 * @throws No dispara ninguna excepci�n.
	 */
	public List<ProgramaAcademico> buscarPrograma(String programa) {
		List<ProgramaAcademico> result = new LinkedList<ProgramaAcademico>();
		if (programa == null) {
			result = listadoProgramas();
		} else {
			for (ProgramaAcademico pr : listadoProgramas()) {
				if (pr.getNombrePrograma().toLowerCase().contains(programa)) {
					result.add(pr);
				}
			}
		}
		return result;
	}
}
