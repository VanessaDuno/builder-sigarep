package sigarep.viewmodels.maestros;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import sigarep.herramientas.mensajes;
import sigarep.modelos.data.maestros.RecaudoFiltro;
import sigarep.modelos.data.maestros.Recaudo;
import sigarep.modelos.data.transacciones.RecaudoEntregado;
import sigarep.modelos.servicio.maestros.ServicioRecaudo;

@SuppressWarnings("serial")
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMrecaudoGeneral {
	
	@WireVariable 
	private ServicioRecaudo serviciorecaudo;
	
	private Integer idRecaudo;

	private String descripcion;

	private Boolean estatus;

	private String nombreRecaudo;

	private String observacion;

	private List<RecaudoEntregado> recaudoEntregados;

	private List<Recaudo> listaRecaudo;
	
	private Recaudo recaudoSeleccionado;
	private RecaudoFiltro filtros = new RecaudoFiltro();
	mensajes mensajeAlUsuario = new mensajes();
	
	private String nombreRecaudoFiltro="";

	@WireVariable
	private List<Recaudo> listaRecaudos;
	
	@Init
	public void init() {
		// initialization code
		buscarRecaudos();
	}
	// Metodos GETS Y SETS
	
	
	public Integer getIdRecaudo() {
		return idRecaudo;
	}
	public RecaudoFiltro getFiltros() {
		return filtros;
	}

	public void setFiltros(RecaudoFiltro filtros) {
		this.filtros = filtros;
	}

	public void setIdRecaudo(Integer idRecaudo) {
		this.idRecaudo = idRecaudo;
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
	public String getNombreRecaudo() {
		return nombreRecaudo;
	}
	public void setNombreRecaudo(String nombreRecaudo) {
		this.nombreRecaudo = nombreRecaudo;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	public List<Recaudo> getListaRecaudo() {
		return listaRecaudo;
	}
	public void setListaRecaudo(List<Recaudo> listaRecaudo) {
		this.listaRecaudo = listaRecaudo;
	}
	public Recaudo getRecaudoSeleccionado() {
		return recaudoSeleccionado;
	}
	public void setRecaudoSeleccionado(Recaudo recaudoSeleccionado) {
		this.recaudoSeleccionado = recaudoSeleccionado;
	}	
	public List<RecaudoEntregado> getRecaudoEntregados() {
		return recaudoEntregados;
	}
	public void setRecaudoEntregados(List<RecaudoEntregado> recaudoEntregados) {
		this.recaudoEntregados = recaudoEntregados;
	}
	
	//Fin de los m�todos gets y sets
   
	public List<Recaudo> getListaRecaudos() {
		return listaRecaudos;
	}
	public void setListaRecaudos(List<Recaudo> listaRecaudos) {
		this.listaRecaudos = listaRecaudos;
	}
	
	//M�todo que perimite guardar un recaudo
	@Command
	@NotifyChange({"idRecaudo", "descripcion", "nombreRecaudo", "observacion", "listaRecaudos"})
	public void guardarRecaudoGeneral(){
		if (descripcion==null||nombreRecaudo==null||observacion==null)
			mensajeAlUsuario.advertenciaLlenarCampos();
			else{
	    		Recaudo recaudoNuevo= new Recaudo(idRecaudo, descripcion, true,
	    				nombreRecaudo, observacion); 
	    		serviciorecaudo.guardarRecaudo(recaudoNuevo);
	    		limpiar();
	    		mensajeAlUsuario.informacionRegistroCorrecto();
                }
	    		
	    }
	//Permite buscar los recaudos.
	@Command
	@NotifyChange({"listaRecaudos"})
	public void buscarRecaudos(){
			listaRecaudos  = serviciorecaudo.listadoRecaudosActivos();
	}
	
	//M�todo que limpia todos los campos de la pantalla
	@Command
	@NotifyChange({"descripcion", "nombreRecaudo", "observacion","listaRecaudos","nombreRecaudoFiltro"})
	public void limpiar(){
		nombreRecaudo=null; descripcion=null;  observacion=null; nombreRecaudoFiltro="";
		buscarRecaudos();
	}
	
	//Metodo que elimina un recaudo tomando en cuenta el idRecaudo
	@Command
	@NotifyChange({ "idRecaudo","descripcion", "nombreRecaudo", "observacion","listaRecaudos"})
	public void eliminarRecaudo(){
		if (descripcion==null||nombreRecaudo==null||observacion==null) {
			mensajeAlUsuario.advertenciaSeleccionarParaEliminar();
		} else {
		serviciorecaudo.eliminarRecaudo(idRecaudo);
		limpiar();
		mensajeAlUsuario.informacionEliminarCorrecto();
	}
	}
	
	//permite tomar los datos del objeto recaudoseleccionado
	@Command
	@NotifyChange({"descripcion", "nombreRecaudo", "observacion", "listaRecaudos"})
	public void mostrarSeleccionado(){
		idRecaudo=recaudoSeleccionado.getIdRecaudo();
		descripcion=recaudoSeleccionado.getDescripcion();
		nombreRecaudo=recaudoSeleccionado.getNombreRecaudo();
		observacion=recaudoSeleccionado.getObservacion();
	}
	
	// M�todo que busca y filtra los recaudos
		@Command
		@NotifyChange({ "listaRecaudos" })
		public void filtros() {
			listaRecaudos = serviciorecaudo.filtrarRecaudos(nombreRecaudoFiltro);
		}

		public String getNombreRecaudoFiltro() {
			return nombreRecaudoFiltro;
		}

		public void setNombreRecaudoFiltro(String nombreRecaudoFiltro) {
			this.nombreRecaudoFiltro = nombreRecaudoFiltro;
		}
	

}//Fin de VMrecaudoGeneral