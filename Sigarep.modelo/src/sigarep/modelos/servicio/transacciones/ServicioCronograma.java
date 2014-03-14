package sigarep.modelos.servicio.transacciones;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import sigarep.modelos.data.maestros.LapsoAcademico;
import sigarep.modelos.data.transacciones.Cronograma;
import sigarep.modelos.data.transacciones.CronogramaPK;
import sigarep.modelos.repositorio.transacciones.ICronogramaDAO;

/**Cronograma de Actividades - Planificar
 * UCLA DCYT Sistemas de Informacion.
 * @author Equipo : Builder-Sigarep Lapso 2013-2
 * @version 1.1
 * @since 10/02/14
 */

@Service("serviciocronograma")
public class ServicioCronograma {
	@PersistenceContext
	private EntityManager emcro;
	
	private @Autowired
	ICronogramaDAO iCronograma;
	
	/** eliminarCronograma. Busca el id del cronograma, actualiza estatus(false) y lo guarda en base de datos.
	 * @param id.
	 * @return No devuelve ningun valor.
	 * @throws No dispara ninguna excepcion
	 */
	public void eliminarCronograma(CronogramaPK id) {
		Cronograma c = iCronograma.findOne(id);
		c.setEstatus(false);
		iCronograma.save(c);
	}
	
	/** listadoCronograma. Busca una lista de todos los cronogramasc activos.
	 * @param listadoCronograma.
	 * @return cronogramaLista.
	 * @throws No dispara ninguna excepcion
	 */
	public List<Cronograma> listadoCronograma() {
		List<Cronograma> cronogramaLista = iCronograma.findByEstatusTrue();
		return cronogramaLista;
	}
	
	/** guardar.
	 * @param cronograma.
	 * @return cronogramaLista.
	 * @throws No dispara ninguna excepcion
	 */
	public Cronograma guardar(Cronograma cronograma) {
		return iCronograma.save(cronograma);
	}
	
	/** buscarTodos. Busca una lista de todos los cronogramas.
	 * @param No recibe parametros
	 * @return Lista de cronogramas.
	 * @throws No dispara ninguna excepcion
	 */
	public  List<Cronograma> buscarTodos() {
		return iCronograma.findAll();
	}
	
	/** buscarTodosCronogramas. Busca una lista de todos los cronogramas donde coincida su codigo de lapso
	 *                          con el codigo de lapso que entra por parametro.
	 * @param codigoLapso.
	 * @return listaCronogramas.
	 * @throws No dispara ninguna excepcion
	 */
    public List<Cronograma> buscarTodosCronogramas(String codigoLapso) {
		List<Cronograma> listaCronogramas = iCronograma.findById_CodigoLapso(codigoLapso);
		return listaCronogramas;
	}
    
    /** buscarUltimaFechaDelCronogramaActual.
	 * @param No recibe parametros
	 * @return Una fecha.
	 * @throws No dispara ninguna excepcion
	 */
    public Date buscarUltimaFechaDelCronogramaActual(){
    	return iCronograma.buscarUltimaFechaCronogramaActual();
    }
    
    /** filtrarCronograma. Hace filtrado por responsable, lugar y actividad
   	 * @param responsablef, lugarf, actividadf
   	 * @return result que es una listadoCronograma.
   	 * @throws la Excepcion es que las variables que entran por parametro sean null
   	 */
    public List<Cronograma> filtrarCronograma(String responsablef, String lugarf, String actividadf){
		List<Cronograma> result = new ArrayList<Cronograma>();
        if(responsablef==null || lugarf==null|| actividadf==null){
        	result= listadoCronograma();
        }
        else{
			for (Cronograma cro : listadoCronograma())
			{
				if (cro.getResponsable().getInstanciaApelada().toLowerCase().contains(responsablef.toLowerCase())&&
						cro.getLugar().toLowerCase().contains(lugarf.toLowerCase())&&
						cro.getActividad().getNombre().toLowerCase().contains(actividadf.toLowerCase())){
					result.add(cro);
				}
			}
        }
		return result;
	} 
    
	public List<String> historicoCronogramaActividades(LapsoAcademico lapso) {
		List<String> listaElementosAInsertar = new ArrayList<String>();
		String elementoAInsertar;
		List<Cronograma> cronogramas = iCronograma.findById_CodigoLapso(lapso.getCodigoLapso());

		for (int i = 0; i < cronogramas.size(); i++) {
				Cronograma cronograma = cronogramas.get(i);
				elementoAInsertar = "INSERT INTO sigarep.cronograma(codigo_lapso, id_actividad, estatus, fecha_fin, fecha_inicio, hora_inicio, lugar, observacion, id_instancia_apelada)"
						+ "VALUES ('"
						+ cronograma.getId().getCodigoLapso()
						+ "',"
						+ cronograma.getId().getIdActividad()
						+ ",'"
						+ cronograma.getEstatus()
						+ "', '"
						+ cronograma.getFechaFin()
						+ "','"
						+ cronograma.getFechaInicio()
						+ "','"
						+ cronograma.getHoraInicio()
						+ "','"
						+ cronograma.getLugar()
						+ "','"
						+ cronograma.getObservacion()
						+ "','"
						+ cronograma.getActividad().getInstanciaApelada().getInstanciaApelada()
						+ "');";
				listaElementosAInsertar.add(elementoAInsertar);
		}
		return listaElementosAInsertar;
	}
    
}