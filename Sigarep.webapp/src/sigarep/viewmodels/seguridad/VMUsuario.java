package sigarep.viewmodels.seguridad;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;


import sigarep.herramientas.Archivo;
import sigarep.herramientas.EnviarCorreo;
import sigarep.herramientas.MensajesAlUsuario;
import sigarep.modelos.data.maestros.InstanciaApelada;
import sigarep.modelos.data.maestros.Persona;

import sigarep.modelos.data.seguridad.Grupo;
import sigarep.modelos.data.seguridad.Usuario;
import sigarep.modelos.data.transacciones.InstanciaMiembro;
import sigarep.modelos.data.transacciones.InstanciaMiembroPK;
import sigarep.modelos.data.transacciones.SolicitudApelacion;
import sigarep.modelos.data.transacciones.UsuarioGrupo;
import sigarep.modelos.data.transacciones.UsuarioGrupoPK;

import sigarep.modelos.servicio.maestros.ServicioInstanciaApelada;
import sigarep.modelos.servicio.maestros.ServicioPersona;
import sigarep.modelos.servicio.seguridad.ServicioGrupo;
import sigarep.modelos.servicio.seguridad.ServicioUsuario;
import sigarep.modelos.servicio.transacciones.ServicioInstanciaMiembro;
import sigarep.modelos.servicio.transacciones.ServicioUsuarioGrupo;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMUsuario {

	@WireVariable 
	private ServicioPersona serviciopersona;
	@WireVariable 
	private ServicioInstanciaApelada servicioInstanciaApelada;
	@WireVariable 
	private ServicioInstanciaMiembro servicioInstanciaMiembro;
	@WireVariable
	private ServicioUsuarioGrupo serviciousuariogrupo;
	
	private List<InstanciaApelada> listaInstancia = new LinkedList<InstanciaApelada>();	
	private InstanciaApelada instanciaseleccionada;
	private String tituloinstancia = "";
	private String cargo ="";
	
	
	private InstanciaMiembro instanciaMiembro = new InstanciaMiembro();
	private InstanciaMiembroPK instanciaMiembroPK = new InstanciaMiembroPK();
	private List<InstanciaMiembro> listaInstanciaMiembro = new LinkedList<InstanciaMiembro>();
	
	private String cedulaPersona="";
	private String nombre="";
	private String apellido="";
	private String telefono="";
	
	private List<Persona> listaPersona;
	private Persona personaSeleccionado = new Persona();
	private String nombreUsuario="";
	private String correo="";
	private String confirmarcorreo="";
	private String clave="";
	private String confirmarcontrasenia="";
	private String nuevaContrasenia;
	private String nombreCompleto="";
	private String cedulaPersonafiltro = "";
	private String nombreCompletofiltro = "";
	private String nombreUsuariofiltro = "";
	
	private Archivo fotoUsuario = new Archivo();
	private Media mediaUsuario;
	private AImage imagenUsuario;
	
	
	private ListModelList<Grupo> modeloGrupo;
	List<Grupo> listGrupo;
	
	@WireVariable
	private String correoLogin;

	MensajesAlUsuario mensajes = new MensajesAlUsuario(); //para llamar a los diferentes mensajes de dialogo
	
	private Usuario usuarioSeleccionado;
	@WireVariable
	private Grupo grupoSeleccionado;	
	@WireVariable
	private List<Usuario> listaUsuario = new LinkedList<Usuario>();
	@WireVariable
	private List<Grupo> listaGrupoPertenece = new LinkedList<Grupo>();
	@WireVariable
	private List<Grupo> listaGrupoNoPertenece = new LinkedList<Grupo>();
	@WireVariable
	private ServicioGrupo serviciogrupo;
	@WireVariable
	private ServicioUsuario serviciousuario;
	
	SecurityUtil seguridad = new SecurityUtil();
	
	public List<InstanciaMiembro> getListaInstanciaMiembro() {
		return listaInstanciaMiembro;
	}

	public void setListaInstanciaMiembro(
			List<InstanciaMiembro> listaInstanciaMiembro) {
		this.listaInstanciaMiembro = listaInstanciaMiembro;
	}

	public String getConfirmarcorreo() {
		return confirmarcorreo;
	}

	public void setConfirmarcorreo(String confirmarcorreo) {
		this.confirmarcorreo = confirmarcorreo;
	}

	public Archivo getFotoUsuario() {
		return fotoUsuario;
	}

	public void setFotoUsuario(Archivo fotoUsuario) {
		this.fotoUsuario = fotoUsuario;
	}

	public Media getMediaUsuario() {
		return mediaUsuario;
	}

	public void setMediaUsuario(Media mediaUsuario) {
		this.mediaUsuario = mediaUsuario;
	}

	public AImage getImagenUsuario() {
		return imagenUsuario;
	}

	public void setImagenUsuario(AImage imagenUsuario) {
		this.imagenUsuario = imagenUsuario;
	}

	public Grupo getGrupoSeleccionado() {
		return grupoSeleccionado;
	}

	public void setGrupoSeleccionado(Grupo grupoSeleccionado) {
		this.grupoSeleccionado = grupoSeleccionado;
	}
	
	
	public List<Grupo> getListaGrupoPertenece() {
		return listaGrupoPertenece;
	}

	public void setListaGrupoPertenece(List<Grupo> listaGrupoPertenece) {
		this.listaGrupoPertenece = listaGrupoPertenece;
	}
	
	public List<Grupo> getListaGrupoNoPertenece() {
		return listaGrupoNoPertenece;
	}

	public void setListaGrupoNoPertenece(List<Grupo> listaGrupoNoPertenece) {
		this.listaGrupoNoPertenece = listaGrupoNoPertenece;
	}

		
	// Metodo que buscar los lapsos y cargarlos en el combobox
	@Command
	@NotifyChange({ "listaUsuario" })
	public void buscarListadoUsuario() {
		listaUsuario = serviciousuario.listadoUsuario();
	}
	
	public InstanciaMiembro getInstanciaMiembro() {
		return instanciaMiembro;
	}

	public void setInstanciaMiembro(InstanciaMiembro instanciaMiembro) {
		this.instanciaMiembro = instanciaMiembro;
	}

	public InstanciaMiembroPK getInstanciaMiembroPK() {
		return instanciaMiembroPK;
	}

	public void setInstanciaMiembroPK(InstanciaMiembroPK instanciaMiembroPK) {
		this.instanciaMiembroPK = instanciaMiembroPK;
	}

	public List<InstanciaApelada> getListaInstancia() {
		return listaInstancia;
	}

	public void setListaInstancia(List<InstanciaApelada> listaInstancia) {
		this.listaInstancia = listaInstancia;
	}

	public InstanciaApelada getInstanciaseleccionada() {
		return instanciaseleccionada;
	}

	public void setInstanciaseleccionada(InstanciaApelada instanciaseleccionada) {
		this.instanciaseleccionada = instanciaseleccionada;
	}

	public String getTituloinstancia() {
		return tituloinstancia;
	}

	public void setTituloinstancia(String tituloinstancia) {
		this.tituloinstancia = tituloinstancia;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Persona getPersonaSeleccionado() {
		return personaSeleccionado;
	}

	public void setPersonaSeleccionado(Persona personaSeleccionado) {
		this.personaSeleccionado = personaSeleccionado;
	}
	
	public List<Persona> getListaPersona() {
		return listaPersona;
	}

	public void setListaPersona(List<Persona> listaPersona) {
		this.listaPersona = listaPersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public ListModelList<Grupo> getModeloGrupo() {
		return modeloGrupo;
	}

	public void setModeloGrupo(ListModelList<Grupo> modeloGrupo) {
		this.modeloGrupo = modeloGrupo;
	}
	
	public Usuario getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}
	
	public String getCorreoLogin() {
		return correoLogin;
	}

	public void setCorreoLogin(String correoLogin) {
		this.correoLogin = correoLogin;
	}

	public String getConfirmarcontrasenia() {
		return confirmarcontrasenia;
	}

	public void setConfirmarcontrasenia(String confirmarcontrasenia) {
		this.confirmarcontrasenia = confirmarcontrasenia;
	}
	
	public List<Grupo> getListGrupo() {
		return listGrupo;
	}

	public void setListGrupo(List<Grupo> listGrupo) {
		this.listGrupo = listGrupo;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public String getNuevaContrasenia() {
		return nuevaContrasenia;
	}

	public void setNuevaContrasenia(String nuevaContrasenia) {
		this.nuevaContrasenia = nuevaContrasenia;
	}
	
	public String getCedulaPersona() {
		return cedulaPersona;
	}

	public void setCedulaPersona(String cedulaPersona) {
		this.cedulaPersona = cedulaPersona;
	}
	
	public String getCedulaPersonafiltro() {
		return cedulaPersonafiltro;
	}

	public void setCedulaPersonafiltro(String cedulaPersonafiltro) {
		this.cedulaPersonafiltro = cedulaPersonafiltro;
	}

	public String getNombreCompletofiltro() {
		return nombreCompletofiltro;
	}

	public void setNombreCompletofiltro(String nombreCompletofiltro) {
		this.nombreCompletofiltro = nombreCompletofiltro;
	}

	public String getNombreUsuariofiltro() {
		return nombreUsuariofiltro;
	}

	public void setNombreUsuariofiltro(String nombreUsuariofiltro) {
		this.nombreUsuariofiltro = nombreUsuariofiltro;
	}

	@Command
	@NotifyChange({ "nombreUsuario","nombreCompleto", "clave","confirmarcontrasenia", "correo","confirmarcorreo",
			"listaUsuario","cedulaPersona","nombre","apellido","telefono","listaPersona", "imagenUsuario","listaInstanciaMiembro."})
	public void guardarUsuario(@BindingParam("gruposDelUsuario") List<Listitem> gruposDelUsuario) {
		boolean existeUsuario = false;
		Usuario usuario = new Usuario();
		if (nombreUsuario.equals("") || correo.equals("") || cedulaPersona.equals("") || nombre.equals("")  || apellido.equals("") 
				|| clave.equals("")  || confirmarcontrasenia.equals("") ) {
			mensajes.advertenciaLlenarCampos();
		}
		else if(!correo.equals(confirmarcorreo)){
			mensajes.advertenciaContrasennasNoCoinciden();
		}
		else if(!clave.equals(confirmarcontrasenia)){
			mensajes.advertenciaContrasennasNoCoinciden();
		}
		else if(gruposDelUsuario.size()==0){
			mensajes.advertenciaSeleccionarGrupoUsuario();
		}
		else
		{
			Usuario usuarioAux = serviciousuario.encontrarUsuario(nombreUsuario);
			if(usuarioAux!=null){
				existeUsuario = true;
				for (UsuarioGrupo usuarioGrupoABorrar : usuarioAux.getUsuariosGrupos()) {
					 serviciousuariogrupo.eliminarUsuarioGrupo(usuarioGrupoABorrar.getId().getIdGrupo(), usuarioGrupoABorrar.getId().getNombreUsuario());
				}
			}
			usuario.setNombreUsuario(nombreUsuario);
			usuario.setClave(clave);
			usuario.setCorreo(correo);
			nombreCompleto = nombre + " " + apellido;
			usuario.setNombreCompleto(nombreCompleto);
			usuario.setEstatus(true);
			usuario.setFoto(fotoUsuario);
			for(Listitem miGrupo :gruposDelUsuario){
				Grupo grupo = new Grupo();
				String nombreGrupo = miGrupo.getLabel();
				grupo = serviciogrupo.buscarGrupoNombre(nombreGrupo);
				
				UsuarioGrupoPK usuarioGrupoPK = new UsuarioGrupoPK();
				UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
				usuarioGrupoPK.setIdGrupo(grupo.getIdGrupo());
				usuarioGrupoPK.setNombreUsuario(nombreUsuario);
				
				usuarioGrupo.setId(usuarioGrupoPK);
				usuarioGrupo.setUsuario(usuario);
				usuarioGrupo.setGrupo(grupo);
				usuarioGrupo.setEstatus(true);
				
				UsuarioGrupo usuarioGrupoAux = new UsuarioGrupo();
				usuarioGrupoAux.setId(usuarioGrupo.getId());
				usuarioGrupoAux.setGrupo(usuarioGrupo.getGrupo());
				usuarioGrupoAux.setUsuario(usuarioGrupo.getUsuario());
				usuarioGrupoAux.setEstatus(true);
				usuario.addUsuarioGrupo(usuarioGrupoAux);
				serviciousuario.guardarUsuario(usuario);
			}
			
			Persona persona = new Persona();
			persona.setCedulaPersona(cedulaPersona);
			persona.setNombre(nombre);
			persona.setApellido(apellido);
			
			persona.setNombreUsuario(usuario);
			persona.setCorreo(correo);
			persona.setEstatus(true);
			persona.setTelefono(telefono);
			serviciopersona.guardar(persona);
			
			if (tituloinstancia.equals("")) {
				System.out.println("instancia vacia");	
			}
			else
			{
//			    if (cargo.equals(""))
//			    	mensajes.advertenciaLlenarCampos();
//			    else
//			    {
			    	for(int i=0;listaInstanciaMiembro.size()>i;i++){
			    		instanciaMiembro = listaInstanciaMiembro.remove(i);
				    	instanciaMiembro.getId().setCedulaPersona(cedulaPersona);
				    	
						instanciaMiembro.setPersona(persona);
						try {
							servicioInstanciaMiembro.guardar(instanciaMiembro);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}	
			    	}
//			    }
			}
			
			mensajes.informacionRegistroCorrecto();
			if(existeUsuario==false){
				try {
					usuario.setFechaCreacion(new Date());
					UsuarioGrupoPK usuarioGrupoPK = new UsuarioGrupoPK();
					UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
					usuarioGrupoPK.setIdGrupo(1);
					usuarioGrupoPK.setNombreUsuario(nombreUsuario);
					Grupo grupo = new Grupo();
					grupo = serviciogrupo.buscarGrupo(1); //Grupo de id=1 que tiene la funci�n cambiar contrase�a
					usuarioGrupo.setId(usuarioGrupoPK);
					usuarioGrupo.setUsuario(usuario);
					usuarioGrupo.setGrupo(grupo);
					usuarioGrupo.setEstatus(true);
					usuario.addUsuarioGrupo(usuarioGrupo);
					serviciousuario.guardarUsuario(usuario); //Agregandole el grupo que tiene la funci�n cambiar contrase�a al usuario nuevo
					EnviarCorreo enviar = new EnviarCorreo();
					enviar.sendEmailWelcomeToSigarep(correo,nombreUsuario,clave);
					mensajes.informacionHemosEnviadoCorreo();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
	
			}
			limpiar();
		}
	}
	
	@Command
	@NotifyChange({ "listaUsuario","tituloinstancia","listaInstanciaMiembro" })
	public void agregarInstancia() {
		System.out.println("algoxxx");
		
		Integer cont =listaInstanciaMiembro.size();
		System.out.println(cont);
		InstanciaMiembro instanciaM = new InstanciaMiembro();
		InstanciaMiembroPK instanciaMPK = new InstanciaMiembroPK();
		if (tituloinstancia.equals("")) {
			System.out.println("Debe seleccionar una instancia");	
		}
		else
		{	System.out.println("preantes");
			if(cargo.equals(""))cargo="No asignado";
		
			instanciaMPK.setCedulaPersona("0000");
			instanciaMPK.setIdInstanciaApelada(getInstanciaseleccionada().getIdInstanciaApelada());
			instanciaM.setId(instanciaMPK);
			instanciaM.setCargo(cargo);
			instanciaM.setEstatus(true);
			instanciaM.setFechaEntrada(new Date());
			instanciaM.setInstanciaApelada(getInstanciaseleccionada());System.out.println("preantes7"+instanciaseleccionada.getInstanciaApelada());
			
			System.out.println("antes"+cont.toString());
			boolean llego = false;
			
			for(int j = 0;listaInstanciaMiembro.size()>j && !llego ;j++){
				System.out.println("for");
				if( (!listaInstanciaMiembro.get(j).getCargo().equals(instanciaM.getCargo())) && (listaInstanciaMiembro.get(j).getId().getIdInstanciaApelada() == instanciaM.getId().getIdInstanciaApelada()) ){
					listaInstanciaMiembro.remove(j);
					listaInstanciaMiembro.add(instanciaM); 
					System.out.println("if");
					llego = true;
					break;
				}else if( listaInstanciaMiembro.get(j).getCargo().equals(instanciaM.getCargo()) && (listaInstanciaMiembro.get(j).getId().getIdInstanciaApelada() == instanciaM.getId().getIdInstanciaApelada()) ){ break;}
				if(listaInstanciaMiembro.size()-1==j  ){
					listaInstanciaMiembro.add(instanciaM);
					llego = true;
					System.out.println("else");
				}
			}
			if(listaInstanciaMiembro.size()==0)listaInstanciaMiembro.add(instanciaM);
				
			System.out.println("despues "+listaInstanciaMiembro.size());
		}
	}
	@Command
	@NotifyChange({ "listaUsuario","tituloinstancia","listaInstanciaMiembro","cargo"})
	public void mostrarInstancia() {
		
		cargo = instanciaMiembro.getCargo();
		tituloinstancia = instanciaMiembro.getInstanciaApelada().getInstanciaApelada();
	}
	
	@Init
	public void init() {
		// initialization code
		buscarUsuario();
		buscarListadoUsuario();
		buscarListadoGrupos();
	}

	// Metodo que busca una noticia partiendo por su titulo
	@Command
	@NotifyChange({ "listaUsuario","listaPersona","listaInstancia" })
	public void buscarUsuario() {
		listaUsuario = serviciousuario.buscarUsuario(nombreUsuario);
		listaPersona = serviciopersona.buscarper(cedulaPersona);
		listaInstancia = servicioInstanciaApelada.buscarTodas();
	}
	
	@NotifyChange({ "listaGrupoNoPertenece" })
	public void buscarListadoGrupos() {
		List<Grupo> listadoGruposActivos = serviciogrupo.listadoGrupo();
		listaGrupoNoPertenece = listadoGruposActivos;
	}

	// Metodo que limpia todos los campos de la pantalla
	@Command
	@NotifyChange({ "nombreUsuario", "clave", "confirmarcontrasenia","correo","confirmarcorreo","listaPersona","listaInstancia","listaUsuario","cedulaPersona","nombre","apellido","telefono", "listaGrupoPertenece","listaGrupoNoPertenece","imagenUsuario","listaInstanciaMiembro","tituloinstancia","cargo"})
	public void limpiar() {
		System.out.println("limpiar");
		nombreUsuario = "";
		clave = "";
		confirmarcontrasenia = "";
		correo = "";
		
		
		cedulaPersona = "";
		nombre = "";
		apellido = "";
		telefono = "";
		mediaUsuario = null;
		imagenUsuario = null;
		fotoUsuario = new Archivo();
		confirmarcorreo = "";
		listaInstanciaMiembro = new LinkedList<InstanciaMiembro>();
		instanciaMiembroPK = null;
		instanciaMiembro= null;
		tituloinstancia = "";
		cargo = "";
		instanciaseleccionada= null;
		buscarUsuario();
//		listaGrupoPertenece.clear();
		buscarListadoGrupos();
	}
	
	@Command
	@NotifyChange({ "nombreUsuario", "contrasenia", "confirmarcontrasenia","nuevaContrasenia","listaUsuario"})
	public void cancelarCambiarContrasenia() {
		confirmarcontrasenia = "";
		nuevaContrasenia = "";
	}	

	// Metodo que elimina una actividad tomando en cuenta el idActividad
	@Command
	@NotifyChange({ "cedulaPersona","listaUsuario","listaPersona","listaGrupoPertenece","listaGrupoNoPertenece"})
	public void eliminarUsuario() {
		serviciousuario.eliminar(getPersonaSeleccionado().getNombreUsuario().getNombreUsuario());
		serviciopersona.eliminar(cedulaPersona);
		mensajes.informacionEliminarCorrecto();
		limpiar();
	}
	
	
	// permite tomar los datos del objeto usaurioseleccionado
	@Command
	@NotifyChange({ "nombreUsuario","clave","confirmarcontrasenia","confirmarcorreo","correo","cedulaPersona","apellido","nombre","telefono","listaGrupoPertenece", "listaGrupoNoPertenece", "personaSeleccionado","fotoUsuario","imagenUsuario","listaInstanciaMiembro","tituloinstancia","cargo"})
	public void mostrarSeleccionado() {	
		limpiar();
		listaInstanciaMiembro = new LinkedList<InstanciaMiembro>();
		instanciaMiembroPK = null;
		instanciaMiembro= null;
		System.out.println("seleccionado");
		nombreUsuario = getPersonaSeleccionado().getNombreUsuario().getNombreUsuario();
		clave = getPersonaSeleccionado().getNombreUsuario().getClave();
		confirmarcontrasenia = getPersonaSeleccionado().getNombreUsuario().getClave();
		correo = getPersonaSeleccionado().getCorreo();
		confirmarcorreo = getPersonaSeleccionado().getCorreo();
		cedulaPersona = getPersonaSeleccionado().getCedulaPersona();
		nombre = getPersonaSeleccionado().getNombre();
		apellido = getPersonaSeleccionado().getApellido();
		telefono = getPersonaSeleccionado().getTelefono();
		fotoUsuario = getPersonaSeleccionado().getNombreUsuario().getFoto();
		listaInstanciaMiembro = getPersonaSeleccionado().getInstanciaMiembros();
		
		if(fotoUsuario!=null){
			System.out.println("esta no nula");
			if (getPersonaSeleccionado().getNombreUsuario().getFoto().getTamano() > 0){
				try {
					imagenUsuario = new AImage(getPersonaSeleccionado().getNombreUsuario().getFoto().getNombreArchivo(),getPersonaSeleccionado().getNombreUsuario().getFoto().getContenidoArchivo());
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
			else{imagenUsuario = null;}
		}else{System.out.println("esta nula");}

		listaGrupoPertenece = serviciogrupo.listadoGrupoPerteneceUsuario(getPersonaSeleccionado().getNombreUsuario().getNombreUsuario());
		listaGrupoNoPertenece = serviciogrupo.listadoGrupoNoPerteneceUsuario(getPersonaSeleccionado().getNombreUsuario().getNombreUsuario());
	}
	
	@Command
	@NotifyChange({"listaGrupoNoPertenece","listaGrupoPertenece"})
	public void quitarGrupo(@BindingParam("itemGrupoPertenece") Listitem itemGrupoPertenece) {
		Grupo grupoAux1 = serviciogrupo.buscarGrupoNombre(itemGrupoPertenece.getLabel());
		listaGrupoNoPertenece.add(grupoAux1);
		listaGrupoPertenece.remove(itemGrupoPertenece.getIndex());
	}
	
	@Command
	@NotifyChange({"listaGrupoPertenece","listaGrupoNoPertenece"})
	public void agregarGrupo(@BindingParam("itemGrupoNoPertenece") Listitem itemGrupoNoPertenece) {
		Grupo grupoAux2 = serviciogrupo.buscarGrupoNombre(itemGrupoNoPertenece.getLabel());
		listaGrupoPertenece.add(grupoAux2);
		listaGrupoNoPertenece.remove(itemGrupoNoPertenece.getIndex());		
	}
	
	
	@Command
	@NotifyChange({"confirmarcontrasenia", "nuevaContrasenia" })
	public void cambiarContrasenia() {
	    if(confirmarcontrasenia==null || nuevaContrasenia == null)
	    	mensajes.advertenciaLlenarCampos();
	    else{
	    	if(serviciousuario.cambiarContrasena(seguridad.getUsuario().getUsername(),nuevaContrasenia, confirmarcontrasenia)==true){
	    		mensajes.informacionContrasennaAtualizada();
	    		cancelarCambiarContrasenia();
	    	}
	    }
	}
	
	@Command
	@NotifyChange({ "correoLogin" })
	public void recuperarContrasenna() {
		Usuario usuario = new Usuario();
		usuario.setNombreUsuario("-1");
		if (correoLogin=="")
			mensajes.advertenciaLlenarCampos();
		else {
			List<Usuario> listaUsuarios = serviciousuario.listadoUsuario();
				Usuario usuarioAux = new Usuario();
				for (int i = 0; i < listaUsuarios.size(); i++) {
					usuarioAux = listaUsuarios.get(i);
					if (usuarioAux.getCorreo() != null) 
						if (usuarioAux.getCorreo().equals(correoLogin) || usuarioAux.getNombreUsuario().equals(correoLogin)){
							usuario = usuarioAux;
						}
				}
				if (usuario.getNombreUsuario()!="-1") {
					EnviarCorreo enviar = new EnviarCorreo();
					enviar.sendEmail(usuario.getCorreo(), usuario.getClave());
					mensajes.informacionContrasennaRecuperada();
				}
				else
					mensajes.ErrorUsuarioEmailNoRegistrado();
		}
	}
	
	// M�todo que busca y filtra los usuarios
	@Command
	@NotifyChange({"listaPersona"})
	public void filtros(){
		listaPersona = serviciopersona.buscarPersonaFiltro(cedulaPersonafiltro, nombreCompletofiltro, nombreUsuariofiltro);
	}
	
	
	
	/** cargarImagenNoticia
	 * @parameters imagenUsuario, UploadEvent event Zkoss UI.
	 * @return No devuelve ningun valor.
	 * @throws la Excepcion es que la media usuario sea null
	 */
	@Command
	@NotifyChange("imagenUsuario")
	public void cargarImagenUsuario(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event){
		mediaUsuario = event.getMedia();
		if (mediaUsuario != null) {
			if (mediaUsuario instanceof org.zkoss.image.Image) {
				fotoUsuario.setNombreArchivo(mediaUsuario.getName());
				fotoUsuario.setTipo(mediaUsuario.getContentType());
				fotoUsuario.setContenidoArchivo(mediaUsuario.getByteData());
				imagenUsuario = (AImage) mediaUsuario;
			} else {
				Messagebox.show("El archivo: "+mediaUsuario+" no es una imagenNoticia valida", "Error", Messagebox.OK, Messagebox.ERROR);
			}
		} 
	}
}