package sigarep.modelos.servicio.seguridad;

import java.util.LinkedList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import sigarep.herramientas.MensajesAlUsuario;
import sigarep.modelos.data.seguridad.Grupo;
import sigarep.modelos.data.seguridad.Usuario;
import sigarep.modelos.repositorio.seguridad.IUsuarioDAO;
import sigarep.modelos.repositorio.transacciones.IUsuarioGrupoDAO;

@Service("serviciousuario")
public class ServicioUsuario {

	@Autowired
	private IUsuarioDAO iUsuarioDAO;
	public IUsuarioGrupoDAO iUsuarioGrupoDAO;
	MensajesAlUsuario mensajeAlUsuario = new MensajesAlUsuario();
	
	/**
	 * Guardar Usuario
	 * @param Usuario usuario
	 * @return Guarda el objeto
	 * @throws No dispara ninguna excepci�n.
	 */
	
	public void guardarUsuario(Usuario usuario) {
		iUsuarioDAO.save(usuario);
	}
	
	/**
	 * Encontrar el usuario por nombre del usuario
	 * @param nombre del usuario
	 * @return Busca el usuario
	 * @throws No dispara ninguna excepci�n.
	 */
	
	public Usuario encontrarUsuario(String nombreusaurio){
		return iUsuarioDAO.findOne(nombreusaurio);
	}
	
	/**
	 * Eliminar l�gicamente el Usuario
	 * @param String nombre de usuario
	 * @return Elimina l�gicamente el objeto
	 * @throws No dispara ninguna excepci�n.
	 */
	
	public void eliminar(String nombreusuario){
		Usuario miUsuario = iUsuarioDAO.findOne(nombreusuario);
		miUsuario.setEstatus(false);
		iUsuarioDAO.save(miUsuario);
	}
	
	/**
	 * Eliminar fisicamente el Usuario
	 * @param String nombre de usuario
	 * @return Elimina fisicamente el objeto
	 * @throws No dispara ninguna excepci�n.
	 */
	
	public void eliminarFisicamente(String nombreusuario){
		Usuario miUsuario = iUsuarioDAO.findOne(nombreusuario);		
		iUsuarioDAO.delete(miUsuario);
	}
	
	/**
	 * Listado de los roles del usuario
	 * @param String nombre del usuario
	 * @return Lista de todos los roles del usuario sin ninguna excepci�n
	 * @throws No dispara ninguna excepci�n.
	 */
	
	public List<Grupo> rolesDelUsuario(String nombreUsuario) {
		List<Grupo> listaRolesUsuario = iUsuarioDAO.totalidadRolesUsuario(nombreUsuario);
		return listaRolesUsuario;
	}
	
	/**
	 * Listado de los usuarios registrados
	 * @param 
	 * @return Lista de todos los usuarios
	 * @throws No dispara ninguna excepci�n.
	 */
	
	public List<Usuario> listadoUsuario() {
		List<Usuario> usuarioLista = iUsuarioDAO.findByEstatusTrue();
		return usuarioLista;
	}
	
	/**
	 * Buscar Usuario filtrando por nombre de usuario
	 * 
	 * @param String nombre de usuario
	 * @return Busca un usuario por nombre de usuario
	 * @throws No dispara ninguna excepci�n.
	 */
	
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
	
	/**
	 * Cambiar la contrase�a del Usuario
	 * 
	 * @param String nombre de usuario, String nuevaContrasena, String confirmarContrasena
	 * @return Cambia la contrasena del usuario a la nueva contrasena
	 * @throws Dispara excepci�n si el nombre del usuario no est� registrado o si la nueva 
	 * contrasena y confirmar contrasena no coinciden.
	 */
	
	public boolean cambiarContrasena(String nombreUsuario,String nuevaContrasena, String confirmarContrasena) {
		Usuario usuario = encontrarUsuario(nombreUsuario);
		if (nuevaContrasena != null && nuevaContrasena.trim().length() > 0) {
				if (nuevaContrasena.equals(confirmarContrasena)) {
						usuario.setClave(nuevaContrasena);
						guardarUsuario(usuario);
						return true;
				} else mensajeAlUsuario.advertenciaContrase�asNoCoinciden();
		} else {
			mensajeAlUsuario.advertenciaContrase�aVacia();
		}			
		return false;
	}
}
