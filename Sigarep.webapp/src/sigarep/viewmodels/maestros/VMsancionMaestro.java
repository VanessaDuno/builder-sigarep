package sigarep.viewmodels.maestros;

import java.util.List;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import sigarep.herramientas.MensajesAlUsuario;
import sigarep.modelos.data.maestros.SancionMaestro;
import sigarep.modelos.servicio.maestros.ServicioSancionMaestro;

/**
 * SancionMaestro UCLA DCYT Sistemas de Informacion.
 * 
 * @author Equipo : Builder-Sigarep Lapso 2013-2
 * @version 1.0
 * @since 22/01/14
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMsancionMaestro {
	// -----------------Servicios----------------------------
	@WireVariable
	ServicioSancionMaestro serviciosancionmaestro;
	// -----------------Variables SancionMaestro ------------
	private Integer id_sancion;
	private String nombre;
	private String nombreFiltro;
	private String descripcion;
	private Boolean estatus;
	// -----------------Variables Lista----------------------
	private List<SancionMaestro> listaTipoSancion;
	// -----------------Variables Objeto---------------------
	private SancionMaestro tipoSancionSeleccionada;
	private MensajesAlUsuario mensajeAlUsuario = new MensajesAlUsuario();

	// Inicion M�todos Sets y Gets
	public Integer getIdSancion() {
		return id_sancion;
	}

	public void setIdSancion(Integer id_sancion) {
		this.id_sancion = id_sancion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public List<SancionMaestro> getListaTipoSancion() {
		return listaTipoSancion;
	}

	public void setListaTipoSancion(List<SancionMaestro> listaTipoSancion) {
		this.listaTipoSancion = listaTipoSancion;
	}

	public SancionMaestro getTipoSancionSeleccionada() {
		return tipoSancionSeleccionada;
	}

	public void setTipoSancionSeleccionada(
			SancionMaestro tipoSancionSeleccionada) {
		this.tipoSancionSeleccionada = tipoSancionSeleccionada;
	}

	public String getNombreFiltro() {
		return nombreFiltro;
	}

	public void setNombreFiltro(String nombreFiltro) {
		this.nombreFiltro = nombreFiltro;
	}// Fin M�todos Sets y Gets

	/**
	 * inicializaci�n
	 * 
	 * @param init
	 * @return c�digo de inicializaci�n
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	@Init
	public void init() {
		listaTipoSancion();
	}

	/**
	 * listaTipoSancion
	 * 
	 * @param listaTipoSancion
	 * @return No devuelve ningun valor
	 * @throws No
	 *             dispara ninguna excepci�n
	 */
	@Command
	@NotifyChange({ "listaTipoSancion" })
	public void listaTipoSancion() {
		listaTipoSancion = serviciosancionmaestro.listaTipoSanciones();
	}

	/**
	 * guardarTipoSancion
	 * 
	 * @param id_sancion
	 *            , nombre, descripcion, listaSancion, estatus
	 * @return No devuelve ningun valor
	 * @throws No
	 *             debe haber campos en blanco
	 */
	@Command
	@NotifyChange({ "id_sancion", "nombre", "descripcion", "estatus",
			"listaTipoSancion" })
	public void guardarTipoSancion() {
		if (nombre == null || descripcion == null) {
			mensajeAlUsuario.advertenciaLlenarCampos();
		} else {
			SancionMaestro sanm = new SancionMaestro(id_sancion, descripcion,
					true, nombre);
			serviciosancionmaestro.guardarSancion(sanm);
			mensajeAlUsuario.informacionRegistroCorrecto();
			limpiar();
		}
	}

	/**
	 * eliminarTipoSancion
	 * 
	 * @param Binder
	 *            binder
	 * @return No devuelve ning�n valor
	 * @throws Debe
	 *             seleccionar un registro para poder eliminarlo
	 */
	@Command
	@NotifyChange({ "id_sancion", "nombre", "descripcion", "estatus",
			"listaTipoSancion" })
	public void eliminarTipoSancion(
			@ContextParam(ContextType.BINDER) final Binder binder) {
		if (nombre == null || descripcion == null) {
			mensajeAlUsuario.advertenciaSeleccionarParaEliminar();
		} else {
			System.out.println(id_sancion);
			if (id_sancion == 1 || id_sancion == 2) {
				Messagebox.show("�No puede eliminar este registro!",
						"Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);
			} else {
				Messagebox.show("�Desea eliminar el registro realmente?",
						"Confirmar", new Messagebox.Button[] {
								Messagebox.Button.YES, Messagebox.Button.NO },
						Messagebox.QUESTION, new EventListener<ClickEvent>() {
							@SuppressWarnings("incomplete-switch")
							public void onEvent(ClickEvent e) throws Exception {
								switch (e.getButton()) {
								case YES:
									serviciosancionmaestro
											.eliminarSancion(id_sancion);
									mensajeAlUsuario
											.informacionEliminarCorrecto();
									binder.postCommand("limpiar", null);
								case NO:
									binder.postCommand("limpiar", null);
								}
							}
						});
			}
		}
	}

	/**
	 * mostrarSeleccionada
	 * 
	 * @param id_sancion
	 *            , nombre, estatus, descripcion
	 * @return No devuelve ningun valor
	 * @throws No
	 *             dispara ninguna excepci�n
	 */
	@Command
	@NotifyChange({ "id_sancion", "nombre", "descripcion", "estatus" })
	public void mostrarSeleccionada() {
		id_sancion = getTipoSancionSeleccionada().getIdSancion();
		nombre = getTipoSancionSeleccionada().getNombreSancion();
		descripcion = getTipoSancionSeleccionada().getDescripcion();
	}

	/**
	 * filtros
	 * 
	 * @param listaTipoSancion
	 * @return No devuelve ningun valor
	 * @throws No
	 *             dispara ninguna excepci�n
	 */
	@Command
	@NotifyChange({ "listaTipoSancion", "nombreFiltro" })
	public void filtros() {
		listaTipoSancion = serviciosancionmaestro.filtrarSancion(nombreFiltro);
	}

	/**
	 * limpiar
	 * 
	 * @param id_sancion
	 *            , nombre, descripcion, listaSancion, estatus
	 * @return No devuelve ningun valor
	 * @throws No
	 *             dispara ninguna excepci�n
	 */
	@Command
	@NotifyChange({ "id_sancion", "nombre", "descripcion", "estatus",
			"nombreFiltro", "listaTipoSancion" })
	public void limpiar() {
		id_sancion = null;
		nombre = null;
		nombreFiltro = "";
		descripcion = null;
		listaTipoSancion();
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
	@NotifyChange({ "listaTipoSancion", "nombre", "descripcion", "estatus" })
	public void cerrarVentana(@BindingParam("ventana") final Window ventana) {
		boolean condicion = false;
		if (nombre != null || descripcion != null)
			condicion = true;
		mensajeAlUsuario.confirmacionCerrarVentanaMaestros(ventana, condicion);
	}
}
