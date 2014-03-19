package sigarep.viewmodels.maestros;

import java.io.IOException;
import java.util.List;


import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;
import sigarep.herramientas.Archivo;
import sigarep.herramientas.MensajesAlUsuario;
import sigarep.modelos.data.maestros.EnlaceInteres;
import sigarep.modelos.servicio.maestros.ServicioEnlaceInteres;

/**
 * Clase VMenlaceInteres
 * 
 * @author BUILDER
 * @version 1.0
 * @since 19/12/2013
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMenlaceInteres {
	//-----------------Servicios----------------------------
	@WireVariable
	ServicioEnlaceInteres servicioenlacesinteres;
	//-----------------Variables Actividad -----------------
	private Integer idEnlace;
	private String nombreEnlace;
	private String direccionEnlace;
	private String descripcion;
	private Boolean estatus;
	//-----------------Variables Filtro---------------------
	private String nombreEnlaceFiltro ="";
	private String direccionEnlaceFiltro ="";
	//-----------------Variables Lista----------------------
	private List<EnlaceInteres> listaEnlaces ;
	//-----------------Variables Objeto---------------------
	private Archivo imagen = new Archivo();
	private AImage imagenes;
	private Media media;
	private EnlaceInteres enlaceSeleccionado;
	MensajesAlUsuario mensajeAlUsuario = new MensajesAlUsuario();
	
	
	// M�todos Set y Get
	public Integer getIdEnlace() {
		return idEnlace;
	}

	public void setIdEnlace(Integer idEnlace) {
		this.idEnlace = idEnlace;
	}


	public String getNombreEnlace() {
		return nombreEnlace;
	}

	public void setNombreEnlace(String nombreEnlace) {
		this.nombreEnlace = nombreEnlace;
	}


	public String getDireccionEnlace() {
		return direccionEnlace;
	}

	public void setDireccionEnlace(String direccionEnlace) {
		this.direccionEnlace = direccionEnlace;
	}


	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}

	public Archivo getImagen() {
		return imagen;
	}

	public void setImagen(Archivo imagen) {
		this.imagen = imagen;
	}

	public AImage getImagenes() {
		return imagenes;
	}

	public void setImagenes(AImage imagenes) {
		this.imagenes = imagenes;

	}

	public List<EnlaceInteres> getListaEnlaces() {
		return listaEnlaces;
	}

	public void setListaEnlaces(List<EnlaceInteres> listaEnlaces) {
		this.listaEnlaces = listaEnlaces;
	}

	public EnlaceInteres getEnlaceSeleccionado() {
		return enlaceSeleccionado;
	}

	public void setEnlaceSeleccionado(EnlaceInteres enlaceSeleccionado) {
		this.enlaceSeleccionado = enlaceSeleccionado;
	}
	
	public String getNombreEnlaceFiltro() {
		return nombreEnlaceFiltro;
	}

	public void setNombreEnlaceFiltro(String nombreEnlaceFiltro) {
		this.nombreEnlaceFiltro = nombreEnlaceFiltro;
	}

	public String getDireccionEnlaceFiltro() {
		return direccionEnlaceFiltro;
	}

	public void setDireccionEnlaceFiltro(String direccionEnlaceFiltro) {
		this.direccionEnlaceFiltro = direccionEnlaceFiltro;
	}
	//Fin  M�todos Set y Get

	/**
	 * inicializaci�n
	 * 
	 * @param init
	 * @return c�digo de inicializaci�n
	 * @throws No
	 * dispara ninguna excepci�n.
	 */
	@Init
	public void init() {
		imagen = new Archivo();
		buscarEnlaceInteres();
	}
	
	/**
	 * Buscar enlaces de inter�s
	 * 
	 * @param buscarEnlacesInteres
	 * @return Busca todos los registros. Inicializa el c�digo.
	 * @throws No
	 *             dispara ninguna excepcion.
	 */
	@Command
	@NotifyChange({ "listaEnlaces" })
	private void buscarEnlaceInteres() {
		listaEnlaces = servicioenlacesinteres.listadoEnlaceInteres();
	}

	/**
	 * Guardar Enlace
	 * 
	 * @param guardar
	 * @return Guarda el registro completo, el command indica a las variables el
	 *         cambio que se har� en el objeto.
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	@Command
	@NotifyChange({ "idEnlace", "nombreEnlace", "direccionEnlace",
			"descripcion", "estatus", "imagenes", "listaEnlaces" })
	public void guardar() {
		if (nombreEnlace == null || direccionEnlace == null
				|| descripcion == null)
			mensajeAlUsuario.advertenciaLlenarCampos();
		else if (imagen.getTamano() < 1)
			mensajeAlUsuario.advertenciaCargarImagen();
		else {
			EnlaceInteres enlace = new EnlaceInteres(idEnlace, nombreEnlace,
					direccionEnlace, descripcion, true, imagen);
			servicioenlacesinteres.guardarEnlace(enlace);
			limpiar();
			mensajeAlUsuario.informacionRegistroCorrecto();
		}

	}
	/**
	 * Carga de im�genes
	 * 
	 * @param cargarImagen
	 * @return Permite la carga de im�genes. utiliza Archivo del paquete
	 *         herramientas.
	 * @throws No
	 *             dispara ninguna excepcion.
	 */
	@Command
	@NotifyChange({"imagenes","imagen"})
	public void cargarImagen(
			@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event) {
		media = event.getMedia();
		if (media != null) {
			if (media instanceof org.zkoss.image.Image) {
				imagen.setNombreArchivo(media.getName());
				imagen.setTipo(media.getContentType());
				imagen.setContenidoArchivo(media.getByteData());
				
				if(imagen.getTamano()>50000){
					mensajeAlUsuario.advertenciaTamannoImagen(50);
					
					imagen = new Archivo();
					}else{imagenes = (AImage) media;}

				imagenes = (AImage) media;
			} else {
				Messagebox.show("El archivo: " + media
						+ " no es una imagen valida", "Error", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	/**
	 * Eliminar un enlace
	 * 
	 * @param eliminarEnlaceSeleccionado
	 * @return Elimina un enlace fisicamente.
	 * @throws No
	 *             dispara ninguna excepcion.
	 */
	
	@Command
	@NotifyChange({"listaEnlaces","nombreEnlace", "direccionEnlace", "descripcion", "imagenes"})
	public void eliminarEnlaceSeleccionado(@ContextParam(ContextType.BINDER) final Binder binder){
		if (nombreEnlace == null || direccionEnlace == null
				|| descripcion == null || imagen.getTamano() < 1) {
			mensajeAlUsuario.advertenciaSeleccionarParaEliminar();
		} else {
			Messagebox.show("�Desea eliminar el registro realmente?","Confirmar",new Messagebox.Button[] { Messagebox.Button.YES,Messagebox.Button.NO },
					Messagebox.QUESTION,new EventListener<ClickEvent>() {
				@SuppressWarnings("incomplete-switch")
				public void onEvent(ClickEvent e) throws Exception {
					switch (e.getButton()) {
						case YES:
							//binder.postCommand("limpiar", null);
							servicioenlacesinteres.eliminar(idEnlace);
							mensajeAlUsuario.informacionEliminarCorrecto();
							binder.postCommand("limpiar", null);
						case NO:
							binder.postCommand("limpiar", null);
					}
				}
			});		
		}
	}

	/**
	 * Mostrar Enlace
	 * 
	 * @param mostrarEnlace
	 * @return muestra en el �rea de datos el registro seleccionado.
	 * @throws No
	 *             dispara ninguna excepcion.
	 */
	@Command
	@NotifyChange({ "idEnlace", "nombreEnlace", "direccionEnlace",
			"descripcion", "estatus", "imagenes", "imagen" })
	public void mostrarEnlace() {
		idEnlace = enlaceSeleccionado.getIdEnlace();
		nombreEnlace = enlaceSeleccionado.getNombreEnlace();
		direccionEnlace = enlaceSeleccionado.getDireccionEnlace();
		descripcion = enlaceSeleccionado.getDescripcion();
		imagen = enlaceSeleccionado.getImagen();
		if (enlaceSeleccionado.getImagen().getTamano() > 0) {
			try {
				imagenes = new AImage(enlaceSeleccionado.getImagen()
						.getNombreArchivo(), enlaceSeleccionado.getImagen()
						.getContenidoArchivo());
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		} else {
			imagenes = null;
		}
	}
	
	/**
	 * Filtros
	 * 
	 * @param filtros
	 * @return M�todo que busca y filtra los enlaces por nombre y direcci�n web
	 * @throws No
	 * dispara ninguna excepcion.
	 */	
	
	@Command
	@NotifyChange({"listaEnlaces","nombreEnlacefiltro","direccionEnlacefiltro"})
	public void filtrosEnlace(){
		listaEnlaces = servicioenlacesinteres.buscarEnlacesFiltro(nombreEnlaceFiltro,direccionEnlaceFiltro);
	}
	
	/**
	 * Limpiar.
	 * 
	 * @param limpiar
	 * @return inicializa las cajas de texto
	 * @throws No
	 *             dispara ninguna excepcion.
	 */
	@Command
	@NotifyChange({"nombreEnlace", "direccionEnlace","descripcion", "estatus", "imagenes","listaEnlaces" })
	public void limpiar() {
		nombreEnlace =null;
		direccionEnlace = null;
		descripcion = null;
		//idEnlace =null;
		media = null;
		imagenes = null;
		imagen = new Archivo();
		buscarEnlaceInteres();
	}
	/**
	 * Cerrar Ventana
	 * 
	 * @param binder
	 * @return cierra el .zul asociado al VM
	 * @throws No
	 *             dispara ninguna excepcion.
	 */
	
	
	@Command
	@NotifyChange({"listaEnlaces","nombreEnlace", "direccionEnlace", "descripcion", "imagenes"})
	public void cerrarVentana(@BindingParam("ventana") final Window ventana){
		boolean condicion = false;
		if(nombreEnlace != null || direccionEnlace != null || descripcion != null || imagen.getTamano() > 1)
			condicion = true;
		mensajeAlUsuario.confirmacionCerrarVentanaMaestros(ventana,condicion);		
	}
	
	
}// fin VMenlaceInteres.

