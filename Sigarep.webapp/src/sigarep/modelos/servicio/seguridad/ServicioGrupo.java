package sigarep.modelos.servicio.seguridad;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sigarep.modelos.data.seguridad.Grupo;
import sigarep.modelos.repositorio.seguridad.IGrupoDAO;

@Service("serviciogrupo")
public class ServicioGrupo {

	@Autowired
	private IGrupoDAO iGrupoDAO;

	public void guardarGrupo(Grupo grupo) {
		iGrupoDAO.save(grupo);
	}

	public Grupo buscarGrupo(Integer idgrupo) {
		return iGrupoDAO.findOne(idgrupo);

	}

	public List<Grupo> buscarTodos(String... roles) {
		return iGrupoDAO.findAll();

	}

	public Grupo buscarGrupoNombre(String rol) {

		return iGrupoDAO.findByNombre(rol);

	}

	public List<Grupo> listadoGrupo() {
		List<Grupo> GrupoLista = iGrupoDAO.buscarGruposActivos();
		return GrupoLista;
	}
	
	public List<Grupo> listadoGrupoPerteneceUsuario(String nombreUsuario) {
		List<Grupo> listaGrupoPertenece = iGrupoDAO.buscarGruposPerteneceUsuario(nombreUsuario);
		return listaGrupoPertenece;
	}
	
	public List<Grupo> listadoGrupoNoPerteneceUsuario(String nombreUsuario) {
		List<Grupo> listaGrupoNoPertenece = iGrupoDAO.buscarGruposNoPerteneceUsuario(nombreUsuario);
		return listaGrupoNoPertenece;
	}

	public void eliminar(Integer idGrupo) {
		Grupo miGrupo = iGrupoDAO.findOne(idGrupo);
		miGrupo.setEstatus(false);
		iGrupoDAO.save(miGrupo);
	}

	public List<Grupo> buscarP(String nombre) {
		List<Grupo> result = new LinkedList<Grupo>();
		if (nombre == null || "".equals(nombre)) {// si el nombre es null o
													// vacio,el resultado va a
													// ser la lista completa de
													// todos los s de motivo
			// si el codigo es null o vacio,el resultado va a ser la lista
			// completa de
			// todas los motivos
			result = listadoGrupo();
		} else {// caso contrario se recorre toda la lista y busca los s de
				// motivos con el nombre indicado en la caja de texto y tambien
				// busca todos los que tengan las letras iniciales de ese
				// nombre.
			for (Grupo grupo : listadoGrupo()) {
				if (grupo.getNombre().toLowerCase().contains(nombre.toLowerCase()) || grupo.getDescripcion().toLowerCase().contains(nombre.toLowerCase())) {
						result.add(grupo);
				}
			}
		}
		return result;
	}
}
