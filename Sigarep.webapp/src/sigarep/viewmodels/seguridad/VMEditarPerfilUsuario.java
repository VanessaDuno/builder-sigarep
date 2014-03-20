package sigarep.viewmodels.seguridad;

import java.io.IOException;
import java.util.Date;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import sigarep.herramientas.Archivo;
import sigarep.herramientas.MensajesAlUsuario;
import sigarep.herramientas.UtilidadesSigarep;
import sigarep.modelos.data.maestros.Persona;
import sigarep.modelos.data.seguridad.Usuario;
import sigarep.modelos.servicio.maestros.ServicioPersona;
import sigarep.modelos.servicio.seguridad.ServicioUsuario;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMEditarPerfilUsuario {
	
	@WireVariable
	private String cedula = "";
	@WireVariable
	private String nombre = "";
	@WireVariable
	private String apellido = "";
	@WireVariable
	private String correo = "";
	@WireVariable
	private String telefono = "";
	@WireVariable
	private Integer telefonoEntero = null;
	@WireVariable
	private Date fechaCreacion;
	@WireVariable
	private Persona persona = new Persona();
	@WireVariable
	private Archivo fotoUsuario = new Archivo();
	@WireVariable
	private AImage imagenUsuario;
	VMUtilidadesDeSeguridad seguridad = new VMUtilidadesDeSeguridad();
	@WireVariable
	ServicioUsuario serviciousuario;
	@WireVariable
	ServicioPersona serviciopersona;
	MensajesAlUsuario mensajesAlusuario = new MensajesAlUsuario(); //para llamar a los diferentes mensajes de dialogo
	
	String ruta = UtilidadesSigarep.obtenerDirectorio();
	public Archivo getFotoUsuario() {
		return fotoUsuario;
	}

	public void setFotoUsuario(Archivo fotoUsuario) {
		this.fotoUsuario = fotoUsuario;
	}
	
	public AImage getImagenUsuario() {
		return imagenUsuario;
	}

	public void setImagenUsuario(AImage imagenUsuario) {
		this.imagenUsuario = imagenUsuario;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
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

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Integer getTelefonoEntero() {
		return telefonoEntero;
	}

	public void setTelefonoEntero(Integer telefonoEntero) {
		this.telefonoEntero = telefonoEntero;
	}

	@Init
	public void init() {
		// initialization code
		buscarUsuario();
	}
	
	@GlobalCommand
    public void actualizarFotoPerfilUsuario(){
    	BindUtils.postGlobalCommand(null, null, "cargarFotoImagen", null);
    }
	
	@Command
	@NotifyChange({"correo","fechaCreacion","nombre","apellido","telefono","fotoUsuario","telefonoEntero","imagenUsuario"})
	public void buscarUsuario() {
		Persona persona = serviciopersona.buscarPersonaNombreUsuario(seguridad.getUsuario().getUsername());
		this.persona = persona;
		this.cedula = persona.getCedulaPersona();
		this.correo = persona.getUsuario().getCorreo();
		this.fechaCreacion = persona.getUsuario().getFechaCreacion();
		this.nombre = persona.getNombre();
		this.apellido = persona.getApellido();
		this.telefono = persona.getTelefono();
		this.telefonoEntero = Integer.parseInt(this.telefono);
		try {
			imagenUsuario = persona.getUsuario().getFoto().getAImage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	@Command
	@NotifyChange("imagenUsuario")
	public void removeImage() {
		imagenUsuario = null;
	}

	@Command
	@NotifyChange({"imagenUsuario","fotoUsuario"})
	public void upload(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) {
		UploadEvent upEvent = null;
		Object objUploadEvent = ctx.getTriggerEvent();
		if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
			upEvent = (UploadEvent) objUploadEvent;
		}
		if (upEvent != null) {
			Media media = upEvent.getMedia();
			fotoUsuario.setNombreArchivo(media.getName());
			fotoUsuario.setTipo(media.getContentType());
			fotoUsuario.setContenidoArchivo(media.getByteData());
			int tamanhoImagen = media.getByteData().length;
			int ancho = 500;
			if (media instanceof Image) {
				if (tamanhoImagen > ancho * 1024) {
					mensajesAlusuario.advertenciaTamannoImagen(500);
				} else {
					imagenUsuario = (AImage) media;// Initialize the bind object to show image in zul page and Notify it also
				}
			} else {
				mensajesAlusuario.advertenciaCargarImagen();
			}
		}
	}
	
	@Command
	@NotifyChange({"imagenUsuario","nombre","apellido", "correo","telefonoEntero"})
	public void guardarPerfilUsuario() {
		Usuario usuario = new Usuario();
		usuario = this.persona.getUsuario();
		if(nombre.equals("") || apellido.equals("") || correo.equals("") || telefonoEntero == null){
			mensajesAlusuario.advertenciaLlenarCampos();
		}
		else if(!mensajesAlusuario.errorValidarCorreo(correo)){}
		else
		{
			this.persona.setNombre(nombre);
			this.persona.setApellido(apellido);
			usuario.setNombreCompleto(this.persona.getNombre() + " " +this.persona.getApellido());
			this.persona.setTelefono(String.valueOf(telefonoEntero));
			usuario.setCorreo(correo);
			if(imagenUsuario == null){
				try {
					imagenUsuario = new AImage(ruta+"/Sigarep.webapp/WebContent/imagenes/iconos/usuario.png");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
			fotoUsuario.setNombreArchivo(imagenUsuario.getName());
			fotoUsuario.setTipo(imagenUsuario.getContentType());
			fotoUsuario.setContenidoArchivo(imagenUsuario.getByteData());
			usuario.setFoto(fotoUsuario);
			serviciopersona.guardar(this.persona);
			serviciousuario.guardarUsuario(usuario);
			mensajesAlusuario.informacionRegistroCorrecto();
		}	
	}	
	
	@Command
	@NotifyChange({ "imagenUsuario", "nombre", "apellido", "correo", "telefonoEntero" })
	public void limpiarPerfilUsuario() {
		nombre = "";
		apellido = "";
		telefonoEntero =  null;
		correo = "";
		if(persona.getUsuario().getFoto()!=null)
		try {
			imagenUsuario = this.persona.getUsuario().getFoto().getAImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Command
	@NotifyChange({"nombre","apellido", "correo","telefonoEntero"})
	public void cerrarVentana(@BindingParam("ventana") final Window ventana){
		boolean condicion = false;
		if(!nombre.equals("") || !apellido.equals("") || !correo.equals("") || telefonoEntero != null)
			condicion = true;
		mensajesAlusuario.confirmacionCerrarVentanaMaestros(ventana,condicion);		
	}
}
