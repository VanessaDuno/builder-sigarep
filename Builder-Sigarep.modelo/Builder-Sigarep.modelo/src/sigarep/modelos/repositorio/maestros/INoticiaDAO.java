package sigarep.modelos.repositorio.maestros;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sigarep.modelos.data.maestros.Noticia;

/**
 * Repositorio Noticia-INoticiaDAO
 * 
 * @author BUILDER
 * @version 1.0
 * @since 12/12/2013
 */
public interface INoticiaDAO extends JpaRepository<Noticia, Integer> {
	
	/**
	 * Busca las todas las noticias que poseen estatus true
	 * @return List<Noticia> Lista de Noticias con estatus true
	 */
	public List<Noticia> findByEstatusTrue();

	/**
	 * Busca el �ltimo id insertado en la tabla Noticia
	 * @return �ltimo id insertado en la tabla Noticia
	 */
	@Query("SELECT COALESCE(MAX(n.idNoticia),0) FROM Noticia AS n")
	public int buscarUltimoID();
}
