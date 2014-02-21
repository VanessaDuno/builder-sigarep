package sigarep.viewmodels.seguridad;
import java.io.IOException;
import java.util.ArrayList;


import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import sigarep.controlador.maestros.WindowController;
import sigarep.herramientas.Archivo;
import sigarep.herramientas.MensajesAlUsuario;
import sigarep.herramientas.UtilidadesSigarep;
import sigarep.modelos.data.seguridad.Usuario;
import sigarep.modelos.servicio.seguridad.ServicioUsuario;


/**
 * Utility class for ZK spring security.
 * @author henrichen
 */
@SuppressWarnings("rawtypes")
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMUtilidadesDeSeguridad {
	/**
	 * Return the current Authentication object.
	 */
	private Archivo fotoUsuario = new Archivo();
	private Media media;
	private String href = "";
	private AImage imagenUsuario;
	private static User usuario;
	private String ruta = UtilidadesSigarep.obtenerDirectorio();
	MensajesAlUsuario mensajes = new MensajesAlUsuario();
	WindowController winController = new WindowController();
	@WireVariable
	ServicioUsuario serviciousuario;

	public static Usuario getUser() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				try {
					Object p = auth.getPrincipal();
					if (p instanceof Usuario)
						return (Usuario) p;
				} catch (RuntimeException e) {
					e.printStackTrace();
					throw e;
				}
			}
			return null;
		} catch (RuntimeException e) {
			Window window = (Window) Executions.createComponents(
					"/accesoDenegado.zul", null, null);
			window.doModal();
			throw e;
		}
	}

	/**
	 * Return true if the authenticated principal is granted NONE of the roles
	 * specified in authorities.
	 * 
	 * @param authorities
	 *            A comma separated list of roles which the user must have been
	 *            granted NONE.
	 * @return true if the authenticated principal is granted authorities of
	 *         NONE the specified roles.
	 */
	public static boolean isNoneGranted(String authorities) {
		if (null == authorities || "".equals(authorities)) {
			return false;
		}
		final Collection<? extends GrantedAuthority> granted = getPrincipalAuthorities();

		final Set grantedCopy = retainAll(granted,
				parseAuthorities(authorities));
		return grantedCopy.isEmpty();
	}

	/**
	 * Return true if the authenticated principal is granted ALL of the roles
	 * specified in authorities.
	 * 
	 * @param authorities
	 *            A comma separated list of roles which the user must have been
	 *            granted ALL.
	 * @return true true if the authenticated principal is granted authorities
	 *         of ALL the specified roles.
	 */

	public User getUsuario() {
		usuario = (User) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return usuario;
	}

	public Archivo getFotoUsuario() {
		return fotoUsuario;
	}

	public void setFotoUsuario(Archivo fotoUsuario) {
		this.fotoUsuario = fotoUsuario;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public AImage getImagenUsuario() {
		return imagenUsuario;
	}

	public void setImagenUsuario(AImage imagenUsuario) {
		this.imagenUsuario = imagenUsuario;
	}
	
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * Inicializaci�n
	 * 
	 * @param init
	 * @return Carga de Variables y metodos inicializados
	 * @throws No dispara ninguna excepcion.
	 */
		
	@Init
	public void init() {
		Usuario usuarioAux = new Usuario();
		usuarioAux = serviciousuario.encontrarUsuario(getUsuario().getUsername());
		usuarioAux.setUltimoAcceso(new Date());
		cargarFotoImagen();
		serviciousuario.guardarUsuario(usuarioAux);
	}
		
	@Command
	@GlobalCommand
	@NotifyChange({ "fotoUsuario", "imagenUsuario" })
	public void cargarFotoImagen() {
		fotoUsuario = new Archivo();
		imagenUsuario = null;
		Usuario usuario = new Usuario();
		usuario = serviciousuario.encontrarUsuario(getUsuario().getUsername());
		if (usuario.getFoto() == null) {
			try {
				imagenUsuario = new AImage(ruta
						+ "/Sigarep.webapp/WebContent/imagenes/iconos/male.png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			try {
				imagenUsuario = usuario.getFoto().getAImage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		
	@Command
	public void editarPerfil() {
		try {
			// get an instance of the borderlayout defined in the zul-file
			Borderlayout bl = (Borderlayout) Path
					.getComponent("/mainBorderLayout");
			// get an instance of the searched CENTER layout area
			Center center = bl.getCenter();
			// clear the center child comps
			center.getChildren().clear();
			// call the zul-file and put it in the center layout area
			Executions.createComponents("/WEB-INF/sigarep/vistas/administracionSistema/EditarPerfilUsuario.zul",center, null);
		} catch (Exception e) {
			Messagebox.show(e.toString());
		}
	}
	
	@Command
	@NotifyChange({"href"})
	public void cerrarSesion() {
		mensajes.confirmacionCerrarSesion();
		
	}	
       
	public static boolean isAllGranted(String authorities) {
		if (null == authorities || "".equals(authorities)) {
			return false;
		}
		final Collection<? extends GrantedAuthority> granted = getPrincipalAuthorities();
		boolean isAllGranted = granted.containsAll(parseAuthorities(authorities));
		return isAllGranted;
	}
        
	/**
	 * Return true if the authenticated principal is granted ANY of the roles
	 * specified in authorities.
	 * 
	 * @param authorities
	 *            A comma separated list of roles which the user must have been
	 *            granted ANY.
	 * @return true true if the authenticated principal is granted authorities
	 *         of ALL the specified roles.
	 */
	public static boolean isAnyGranted(String authorities) {
		if (null == authorities || "".equals(authorities)) {
			return false;
		}
		final Collection<? extends GrantedAuthority> granted = getPrincipalAuthorities();
		final Set grantedCopy = retainAll(granted,parseAuthorities(authorities));
		return !grantedCopy.isEmpty();
	}
        
	public static List<String> roles() {
		List<String> roles = new ArrayList<String>();
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		Iterator<? extends GrantedAuthority> iterador = currentUser.getAuthorities().iterator();
		while (iterador.hasNext()) {
			roles.add(iterador.next().getAuthority());
		}
		roles.add(roles.remove(0));
		return roles;
	}
        
	private static Collection<? extends GrantedAuthority> getPrincipalAuthorities() {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		if (null == currentUser) {
			return Collections.emptyList();
		}
		if ((null == currentUser.getAuthorities())
				|| (currentUser.getAuthorities().isEmpty())) {
			return Collections.emptyList();
		}
		Collection<? extends GrantedAuthority> granted = currentUser.getAuthorities();
		return granted;
	}

    private static Collection<GrantedAuthority> parseAuthorities(String authorizationsString) {
        final ArrayList<GrantedAuthority> required = new ArrayList<GrantedAuthority>();
        final String[] roles = authorizationsString.split(",");

        for (int i = 0; i < roles.length; i++) {
            String role = roles[i].trim();
            required.add(new SimpleGrantedAuthority(role));
        }
        return required;
    }

    /**
     * Find the common authorities between the current authentication's {@link GrantedAuthority} and the ones
     * that have been specified in the tag's ifAny, ifNot or ifAllGranted attributes.<p>We need to manually
     * iterate over both collections, because the granted authorities might not implement {@link
     * Object#equals(Object)} and {@link Object#hashCode()} in the same way as {@link SimpleGrantedAuthority}, thereby
     * invalidating {@link Collection#retainAll(java.util.Collection)} results.</p>
     * <p>
     * <strong>CAVEAT</strong>:  This method <strong>will not</strong> work if the granted authorities
     * returns a <code>null</code> string as the return value of {@link
     * org.springframework.security.GrantedAuthority#getAuthority()}.
     * </p>
     * <p>Reported by rawdave, on Fri Feb 04, 2005 2:11 pm in the Spring Security forum.</p>
     *
     * @param granted The authorities granted by the authentication. May be any implementation of {@link
     *        GrantedAuthority} that does <strong>not</strong> return <code>null</code> from {@link
     *        org.springframework.security.GrantedAuthority#getAuthority()}.
     * @param required A {@link Set} of {@link SimpleGrantedAuthority}s that have been built using ifAny, ifAll or
     *        ifNotGranted.
     *
     * @return A set containing only the common authorities between <var>granted</var> and <var>required</var>.
     */
    private static Set retainAll(final Collection<? extends GrantedAuthority> granted, final Collection<? extends GrantedAuthority> required) {
        Set<String> grantedRoles = toRoles(granted);
        Set<String> requiredRoles = toRoles(required);
        grantedRoles.retainAll(requiredRoles);
        return toAuthorities(grantedRoles, granted);
    }
    
    /**
     * 
     * @param authorities
     * @return
     */
	private static Set<String> toRoles(Collection<? extends GrantedAuthority> authorities) {
		final Set<String> target = new HashSet<String>();
		for (GrantedAuthority au : authorities) {

			if (null == au.getAuthority()) {
				throw new IllegalArgumentException(
						"Cannot process GrantedAuthority objects which return null from getAuthority() - attempting to process "
								+ au.toString());
			}

			target.add(au.getAuthority());
		}
		return target;
	}
        
    /**
     * 
     * @param grantedRoles
     * @param granted
     * @return
     */
	private static Set<GrantedAuthority> toAuthorities(Set<String> grantedRoles,Collection<? extends GrantedAuthority> granted) {
		Set<GrantedAuthority> target = new HashSet<GrantedAuthority>();

		for (String role : grantedRoles) {
			for (GrantedAuthority authority : granted) {

				if (authority.getAuthority().equals(role)) {
					target.add(authority);
					break;
				}
			}
		}
		return target;
	}

	/**
	 * test if current user principal contains all given authorities
	 * 
	 * @param authorities
	 *            the roles will be checked
	 */
	public static void assertAll(String... authorities) {
		if (null == authorities || authorities.length == 0) {
			return;
		}

		final ArrayList<GrantedAuthority> required = new ArrayList<GrantedAuthority>();
		for (String auth : authorities) {
			required.add(new SimpleGrantedAuthority(auth));
		}

		final Collection<? extends GrantedAuthority> granted = getPrincipalAuthorities();
		if (!granted.containsAll(required)) {
			Authentication currentUser = SecurityContextHolder.getContext()
					.getAuthentication();
			throw new AccessDeniedException(
					"The current principal doesn't has enough authority. Authentication: "
						+ (currentUser == null ? "" : currentUser.getName()));
		}
	}
}
