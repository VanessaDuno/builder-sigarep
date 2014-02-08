package sigarep.modelos.servicio.maestros;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import sigarep.modelos.data.maestros.Noticia;
import sigarep.modelos.data.maestros.NoticiaFiltro;
import sigarep.modelos.lista.ListaGenericaSancionados;
import sigarep.modelos.repositorio.maestros.INoticiaDAO;

@Service("servicionoticia")
public class ServicioNoticia {
	private @Autowired
	INoticiaDAO iNoticia;

	/** Guardar Noticia
	 * @parameters el objeto Noticia
	 * @return No devuelve ningun valor
	 * @throws No dispara ninguna excepcion.
	 */
	public void guardar(Noticia noticia) {
		iNoticia.save(noticia);
	}
	
	/** Eliminar Noticia
	 * @parameters idNoticia
	 * @return No devuelve ningun valor.
	 * @throws No dispara ninguna excepcion
	 */
	public void eliminar(Integer idNoticia) {
		Noticia n = iNoticia.findOne(idNoticia);
		n.setEstatus(false);
		iNoticia.save(n);
	}

	/** Lista de Noticia
	 * @parameters No devuelve ningun valor
	 * @return Lista de las Noticias registradas y activas 
	 * @throws No dispara ninguna excepcion
	 */
	public List<Noticia> listadoNoticia() {
		List<Noticia> noticiaLista = iNoticia.busar();
		return noticiaLista;
	}

	/** buscarn
	 * @parameters idNoticia
	 * @return r es una lista de Noticia.
	 */
	public  List<Noticia> buscarn(Integer idNoticia) {
		List<Noticia> r = new LinkedList<Noticia>();
		r = iNoticia.findAll();
		return r;
	}

	/** Buscar una Noticia por nombre
	 * @parameters nombre
	 * @return resultado que es un listadoNoticia.
	 * @throws la Excepcion es que el nombre este en blanco.
	 */
	public List<Noticia> buscarNoticia(String nombre) {
		List<Noticia> resultado = new LinkedList<Noticia>();
		if (nombre == null || "".equals(nombre)) {
			// si el codigo es null o vacio,el resultado va a ser la lista completa de
			//todas las noticias
			resultado = listadoNoticia();
		} else {// caso contrario se recorre toda la lista y busca las noticias.
			for (Noticia noticia : listadoNoticia()) {
				if (noticia.getTitulo().toLowerCase().contains(nombre.toLowerCase())) {
					resultado.add(noticia);
				}
			}
		}
		return resultado;
	}

	/** buscar una Noticia por filtro de titulo y contenido
	 * @parameters noticias
	 * @return result que es un listadoNoticia.
	 */
	public List<Noticia> buscarNoticias(NoticiaFiltro noticias){
		List<Noticia> result = new ArrayList<Noticia>();
		String titulo = noticias.getTitulo().toLowerCase();
		String contenido = noticias.getContenido().toLowerCase();
		if(titulo==null || contenido==null){
			result= listadoNoticia();
		}
		else{
			for (Noticia n: listadoNoticia())
			{
				if (n.getTitulo().toLowerCase().contains(titulo)&&
						n.getContenido().toLowerCase().contains(contenido)){
					result.add(n);
				}
			}
		}
		return result;
	} 

	/** filtrarApelacionesCargarRecaudo
	 * @parameters titulof
	 * @return result que es un listadoNoticia.
	 * @throws las Excepciones son que el titulo sea null
	 */
	public List<Noticia> filtrarApelacionesCargarRecaudo(String titulof){
		List<Noticia> result = new ArrayList<Noticia>();
		if(titulof==null){
			result= listadoNoticia();
		}
		else{
			for (Noticia n : listadoNoticia())
			{
				if (n.getTitulo().toLowerCase().contains(titulof.toLowerCase())){
					result.add(n);
				}
			}
		}
		return result;
	} 

}
