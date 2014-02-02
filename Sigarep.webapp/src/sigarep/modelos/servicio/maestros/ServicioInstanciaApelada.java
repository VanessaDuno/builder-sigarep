package sigarep.modelos.servicio.maestros;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sigarep.modelos.data.maestros.InstanciaApelada;
import sigarep.modelos.repositorio.maestros.IInstanciaApeladaDAO;
/**Servicio Instancia Apelada
* UCLA DCYT Sistemas de Informacion.
* @author Equipo: Builder-SIGAREP 
* @version 1.0
* @since 20/12/13
*/
@Service("servicioInstanciaApelada") //Definiendo la variable servicio
public class ServicioInstanciaApelada{
	private @Autowired IInstanciaApeladaDAO iInstancia ;

	public void guardar(InstanciaApelada pro) {
		iInstancia.save(pro);
	}
	public void eliminar(Integer codigoInstancia){
		InstanciaApelada instanciaapelada = iInstancia.findOne(codigoInstancia);
		instanciaapelada.setEstatus(false);
		iInstancia.save(instanciaapelada);
	}
	public List<InstanciaApelada> listadoInstanciaApelada() {
		List<InstanciaApelada> instanciaApeladaLista = iInstancia.buscarInstanciaActivo();
	    return instanciaApeladaLista ;
	}
	public List<InstanciaApelada> buscarInstancia(String instancia, String recurso) {
		List<InstanciaApelada> resultado = new LinkedList<InstanciaApelada>();
		if (instancia == null || recurso == null) {
			resultado = listadoInstanciaApelada();
		} else {
			for (InstanciaApelada inst : listadoInstanciaApelada()) {
				if (inst.getInstanciaApelada().toLowerCase().contains(instancia)
						&& inst.getNombreRecursoApelacion().toLowerCase().contains(recurso)) {
					resultado.add(inst);
				}
			}
		}
		return resultado;
	}
	public InstanciaApelada buscar(Integer codigoInstancia){
		return iInstancia.findOne(codigoInstancia);
	}
	public List<InstanciaApelada> buscarTodas(){
		return iInstancia.buscarTodas();
	}
	public List<InstanciaApelada> buscarTodasLasInstancias(){
		return iInstancia.buscarInstanciaActivo();
	}
}