package sigarep.modelos.repositorio.maestros;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sigarep.modelos.data.maestros.InstanciaApelada;

/**Clase IInstanciaApeladaDAO
* JPARepository para la Tabla InstanciaApelada
* @author Builder
* @version 1.0
* @since 20/12/13
*/

public interface IInstanciaApeladaDAO extends JpaRepository<InstanciaApelada, Integer> {
	@Query("SELECT insta from InstanciaApelada AS insta WHERE estatus = TRUE")
	public List<InstanciaApelada> buscarInstanciaActivo();
	
	@Query("SELECT insta from InstanciaApelada AS insta")
	public List<InstanciaApelada> buscarTodas();
}
