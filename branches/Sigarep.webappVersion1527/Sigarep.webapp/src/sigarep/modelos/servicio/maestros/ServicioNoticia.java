package sigarep.modelos.servicio.maestros;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sigarep.modelos.data.maestros.Noticia;
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
		if (noticia.getIdNoticia() != null)
			iNoticia.save(noticia);
		else{
			noticia.setIdNoticia(iNoticia.buscarUltimoID()+1);
			iNoticia.save(noticia);
		}
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
		List<Noticia> noticiaLista = iNoticia.findByEstatusTrue();
		return noticiaLista;
	}

	/** filtrar Noticias
	 * @parameters titulof
	 * @return result que es un listadoNoticia.
	 * @throws las Excepciones son que el titulo sea null
	 */
	public List<Noticia> filtrarNoticias(String titulof){
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