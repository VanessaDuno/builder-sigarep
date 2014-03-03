package sigarep.modelos.servicio.seguridad;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.zkoss.zhtml.Messagebox;

import sigarep.modelos.data.seguridad.Usuario;
import sigarep.modelos.repositorio.seguridad.IUsuarioDAO;
import sigarep.modelos.repositorio.transacciones.IUsuarioGrupoDAO;

import sigarep.modelos.data.transacciones.UsuarioGrupo;
import sigarep.modelos.data.transacciones.UsuarioGrupoPK;

@Service("serviciousuario")
public class ServicioUsuario {

	@Autowired
	private IUsuarioDAO iUsuarioDAO;
	public IUsuarioGrupoDAO iUsuarioGrupoDAO;
	private EntityManager em;
	
	public void guardarUsuario(Usuario usuario) {
		iUsuarioDAO.save(usuario);
	}
	
	public Usuario encontrarUsuario(String nombreusaurio){
		return iUsuarioDAO.findOne(nombreusaurio);
	}
	
	public void eliminar(String nombreusuario){
		Usuario miUsuario = iUsuarioDAO.findOne(nombreusuario);
		miUsuario.setEstatus(false);
		iUsuarioDAO.save(miUsuario);
	}
	public void eliminarFisicamente(String nombreusuario){
		Usuario miUsuario = iUsuarioDAO.findOne(nombreusuario);
		
//		String queryStatement = "delete from usuario_grupo ug where " +
//				"' and ug.nombre_usuario = '"+nombreusuario +"'";
//				Query query = em.createNativeQuery(queryStatement);
//				try {
//					query.getSingleResult();
//				} catch (Exception exp) {
//					System.out.println("");
//				}
		
		iUsuarioDAO.delete(miUsuario);
	}
	
	public List<Usuario> listadoUsuario() {
		List<Usuario> usuarioLista = iUsuarioDAO.findByEstatusTrue();
		return usuarioLista;
	}
	
	public List<Usuario> buscarUsuario(String nombreusuario) {
		List<Usuario> resultado = new LinkedList<Usuario>();
		if (nombreusuario == null || "".equals(nombreusuario)) {
			resultado = listadoUsuario();
		} else {
			for (Usuario usuario : listadoUsuario()) {
				if (usuario.getNombreUsuario().toLowerCase()
						.contains(nombreusuario.toLowerCase())) {
					resultado.add(usuario);
				}
			}
		}
		return resultado;
	}
	
	public boolean cambiarContrasena(String nombreUsuario,String nuevaContrasena, String repetirContrasena) {
		Usuario usuario = encontrarUsuario(nombreUsuario);
		if (nuevaContrasena != null && nuevaContrasena.trim().length() > 0) {
				if (nuevaContrasena.equals(repetirContrasena)) {
						usuario.setClave(nuevaContrasena);
						guardarUsuario(usuario);
						return true;
				} else Messagebox.show("La nueva contrase�a y la contrase�a de confirmaci�n no coinciden","Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);
		} else {
				Messagebox.show("La nueva contrase�a no puede estar vacia", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);
		}			
		return false;
	}
}
