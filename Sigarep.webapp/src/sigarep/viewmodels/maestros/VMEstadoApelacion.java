package sigarep.viewmodels.maestros;

import java.util.List;


import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;
import sigarep.herramientas.MensajesAlUsuario;
import sigarep.modelos.data.maestros.EstadoApelacion;
import sigarep.modelos.servicio.maestros.ServicioEstadoApelacion;
import sigarep.modelos.data.maestros.InstanciaApelada;
import sigarep.modelos.servicio.maestros.ServicioInstanciaApelada;

/**
 * Clase VMEstadoApelacion 
 * 
 * @author Equipo Builder
 * @version 1.0
 * @since 19/12/2013
 */

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMEstadoApelacion {
	//-----------------Servicios----------------------------
	@WireVariable
	ServicioEstadoApelacion servicioestadoapelacion;
	@WireVariable
	ServicioInstanciaApelada servicioInstanciaApelada;
	//-----------------Variables EstadoApelacion-----------------
	@WireVariable
	private Integer idEstadoApelacion; // clave principal de la tabla EstadoApelacion
	@WireVariable
	private String nombreEstado; // nombre del EstadoApelacion
	@WireVariable
	private String descripcion; // descripcion del EstadoApelacion
	@WireVariable
	private Boolean estatus; // estatus del EstadoApelacion
	//-----------------Variables Lista----------------------
	@WireVariable
	private List<EstadoApelacion> listaEstadoApelacion; // lista de Estados de Apelacion registrados
	@WireVariable
	private List<InstanciaApelada> listaInstanciaApelada; 
	//-----------------Variables Objeto---------------------
	@WireVariable
	private EstadoApelacion estadoseleccionado;
	@WireVariable
	private InstanciaApelada instanciaApelada;
	MensajesAlUsuario mensajeAlUsuario = new MensajesAlUsuario();
	
	private  @Wire Combobox cmbInstanciaApelada;

	// M�todos Set y Get
	public Integer getIdEstadoApelacion() {
		return idEstadoApelacion;
	}

	public void setIdEstadoApelacion(Integer idEstadoApelacion) {
		this.idEstadoApelacion = idEstadoApelacion;
	}
	
	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
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

	public List<EstadoApelacion> getListaEstadoApelacion() {
		return listaEstadoApelacion;
	}

	public void setListaEstadoApelacion(List<EstadoApelacion> ListaEstadoApelacion) {
		this.listaEstadoApelacion = ListaEstadoApelacion;
	}

	public EstadoApelacion getEstadoseleccionado() {
		return estadoseleccionado;
	}

	public void setEstadoseleccionado(EstadoApelacion estadoseleccionado) {
		this.estadoseleccionado = estadoseleccionado;
	}

	public List<InstanciaApelada> getListaInstanciaApelada() {
		return listaInstanciaApelada;
	}
	
	public void setListaInstanciaApelada(List<InstanciaApelada> listaInstanciaApelada) {
		this.listaInstanciaApelada = listaInstanciaApelada;
	}
	
	public Combobox getCmbInstanciaApelada() {
		return cmbInstanciaApelada;
	}
	public void setCmbInstanciaApelada(Combobox cmbInstanciaApelada) {
		this.cmbInstanciaApelada = cmbInstanciaApelada;
	}
	public InstanciaApelada getInstanciaApelada() {
		return instanciaApelada;
	}
	public void setInstanciaApelada(InstanciaApelada instanciaapelada) {
		this.instanciaApelada = instanciaapelada;
	}
	
	// Fin M�todos Set y Get
	

	/**
	 * inicializaci�n
	 * Init. C�digo de inicializaci�n.
	 * @param Ninguno
	 * @return c�digo de inicializaci�n
	 * @throws No dispara ninguna excepci�n.
	 */
		@Init
		public void init() {
			// initialization code
			buscarEstadoApelacion();
			buscarInstanciaApelada();
			
		}

	/** Guardar Estado de Apelaci�n 
	 * @param Ninguno
	 * @return Guarda el registro completo, el command indica a las variables el
	 *         cambio que se har� en el objeto.
	 * @throws No dispara ninguna excepci�n.
	 */
	@Command
	@NotifyChange({"listaEstadoApelacion", "nombreEstado", "descripcion", "instanciaapelada" })
	// el notifychange le avisa a que parametros en la pantalla se van a
	// cambiar, en este caso es nombre y descripci�n se va a colocar en blanco
	// al guardar
	public void guardarEstadoApelacion() {
		if (nombreEstado==null || descripcion==null || instanciaApelada==null) {
			mensajeAlUsuario.advertenciaSeleccionarEstadoApelacion();
		} else {
			//EstadoApelacion estadoapelacion = new EstadoApelacion(idEstadoApelacion,nombreEstado,descripcion,true,instanciaApelada);
			EstadoApelacion estadoApelacion = new EstadoApelacion(idEstadoApelacion, nombreEstado, descripcion, true);
			estadoApelacion.setInstanciaApelada(instanciaApelada);
			servicioestadoapelacion.guardarEstadoApelacion(estadoApelacion);
			mensajeAlUsuario.informacionRegistroCorrecto();
			limpiar();
		}
	}
	/** Buscar Estado Apelacion
	 *  @param Ninguno
	 *  @return Ninguno 
	 * @throws No dispara ninguna excepcion.
	   */
	@Command
	@NotifyChange({"nombreEstado","descripcion","instanciaapelada","listaEstadoApelacion"})
	public void buscarEstadoApelacion(){
			listaEstadoApelacion  = servicioestadoapelacion.listadoEstadoApelacionActivas();
	}
	
	/** buscar Instancia Apelada
	 *  @param Ninguno
	 *  @return Ninguno 
	 * @throws No dispara ninguna excepcion.
	   */
	@Command
	@NotifyChange({ "listaInstanciaApelada" })
	public void buscarInstanciaApelada() {
		listaInstanciaApelada = servicioInstanciaApelada.listadoInstanciaApelada();
	}
	
	/** Metodo que limpia todos los campos
	 * @param Ninguno
	 * @return Ninguno
	 * @throws No dispara ninguna excepcion.
	   */
	@Command
	@NotifyChange({ "nombreEstado", "descripcion","listaEstadoApelacion"})
	public void limpiar() {
		idEstadoApelacion = null;
		nombreEstado =null;
		descripcion = null;
		instanciaApelada=null;
		buscarEstadoApelacion();
	}

	/**
	 * mostrarSeleccionada : Muestra en el �rea 
de datos el registro seleccionado 
	 * 
	 * @param Ninguno. 
	 * @return Objeto Estado Apelacion seleccionada
	 * @throws No dispara ninguna excepci�n
	 */
	@Command
	@NotifyChange({ "idEstadoApelacion","nombreEstado", "descripcion", "instanciaApelada" })
	public void mostrarSeleccionado() {
		idEstadoApelacion = getEstadoseleccionado().getIdEstadoApelacion();
		nombreEstado = getEstadoseleccionado().getNombreEstado();
		descripcion = getEstadoseleccionado().getDescripcion();
		instanciaApelada = getEstadoseleccionado().getInstanciaApelada();
	}

	/**
	 * combo Estado Apelacion
	 * 
	 * @param Ninguno
	 * @return instanciaApelada
	 * @throws No dispara ninguna excepci�n
	 */
	@Command
	 @NotifyChange({"listaInstanciaApelada"})
	public InstanciaApelada objetoComboEstadoApelacion() {
		return instanciaApelada;
	}

	/** listado Estado Apelacion :  M�todo que trae todos los registros en una lista de Estados de Apelacion
	 * @param Ninguno
	 * @return Ninguno
	 * @throws No dispara ninguna excepcion.
	   */
	@Command
	@NotifyChange({ "listaEstadoApelacion"  })
	public void listadoEstadoApelacion() {
		listaEstadoApelacion = servicioestadoapelacion.listadoEstadoApelacionActivas();
	}
	
	/**
	 * Cerrar Ventana : cierra el .zul asociado al VM
	 * 
	 * @param  Window ventana
	 * @return Ninguno
	 * @throws No dispara ninguna excepcion.
	 */
	@Command
	@NotifyChange({"listaEstadoApelacion", "nombreEstado", "descripcion", "instanciaapelada" })
	public void cerrarVentana(@BindingParam("ventana") final Window ventana){
		boolean condicion = false;
		if(nombreEstado !=null || descripcion!=null || instanciaApelada!=null)
			condicion = true;
		mensajeAlUsuario.confirmacionCerrarVentanaMaestros(ventana,condicion);	
	}

}
