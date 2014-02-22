package sigarep.modelos.servicio.maestros;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;


import sigarep.modelos.data.maestros.LapsoAcademico;
import sigarep.modelos.repositorio.maestros.ILapsoAcademicoDAO;


@Service("serviciolapsoacademico") 
public class ServicioLapsoAcademico{
	private @Autowired ILapsoAcademicoDAO iLapsoAcademico ;
	
	/** Guardar lapso académico 
	 * @return nada
	 * @parameters el objeto lapsoA
	 * @throws No dispara ninguna excepcion.
	 */
	public void guardarLapso(LapsoAcademico lapsoA) {
		iLapsoAcademico.save(lapsoA);
	}
	
	/**lapso académico activo
	 * @return  el lapso académico activo
	 * @parameters vacio
	 * @throws No dispara ninguna excepcion.
	   */
	public LapsoAcademico buscarLapsoActivo(){
		return iLapsoAcademico.findByEstatusTrue();
	}
	/** Lista de lapsos académico inactivos
	 * @return Lista de lapsos académicos inactivos
	 * @parameters vacio
	 * @throws No dispara ninguna excepcion.
	   */
	public List<LapsoAcademico> listadoLapsoAcademicoInactivos() {
	    List<LapsoAcademico> LapsoAcademicoLista=iLapsoAcademico.findByEstatusFalse();
	    return LapsoAcademicoLista ;
	}
	/** Lista de lapso académico
	 * @return Lista de todos los lapsos académicos activas e inactivas
	 * @parameters vacio
	 * @throws No dispara ninguna excepcion.
	   */
	public List<LapsoAcademico> buscarTodosLosLapsos(){
		return iLapsoAcademico.findAll();
	}
	/** Buscar un lapso académico 
	 * @return lapso académico  buscada
	 * @parameters  String codigolapso
	 * @throws No dispara ninguna excepcion.
	   */
	public LapsoAcademico buscarUnLapsoAcademico(String codigoLapso){
		return iLapsoAcademico.findOne(codigoLapso);
	}
	/**Buscar un lapso académico por codigo lapso
	 * @param String codigo lapso
	 * @return Busca un lapso académico por codigo lapso
	 * 	  @throws No dispara ninguna excepcion.
	 */
	
	public List<LapsoAcademico> filtrarLapsoAcademico(String codigoLapso){
		List<LapsoAcademico> result = new LinkedList<LapsoAcademico>();
		if (codigoLapso==null || "".equals(codigoLapso)){
			result = iLapsoAcademico.findAll();
		}else{
			for (LapsoAcademico lapso: iLapsoAcademico.findAll()){
				if (lapso.getCodigoLapso().toLowerCase().contains(codigoLapso.toLowerCase()))
				{
					result.add(lapso);
				}
			}
		}
		return result;

	}
}
