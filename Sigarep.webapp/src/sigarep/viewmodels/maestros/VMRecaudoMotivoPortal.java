package sigarep.viewmodels.maestros;
import java.util.LinkedList;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Filedownload;
import sigarep.herramientas.MensajesAlUsuario;
import sigarep.modelos.data.maestros.Recaudo;
import sigarep.modelos.data.maestros.Reglamento;
import sigarep.modelos.data.maestros.TipoMotivo;
import sigarep.modelos.servicio.maestros.ServicioRecaudo;
import sigarep.modelos.servicio.maestros.ServicioReglamento;
import sigarep.modelos.servicio.maestros.ServicioTipoMotivo;

/** Clase VMrecaudoMotivoPortal.
 * @author Equipo Builder
 * @version 2.5.2
 * @since 02-/12/13
 * @last 09/05/2014
 */

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMRecaudoMotivoPortal {
	//-----------------Servicios----------------------------
	@WireVariable 
	private ServicioRecaudo serviciorecaudo;
	@WireVariable 
	ServicioTipoMotivo serviciotipomotivo;
	@WireVariable
	private ServicioReglamento servicioreglamento;
	//-----------------Variables RecaudoMotivoPortal -------
	private Integer idRecaudo;
	private String descripcion;
	private Boolean estatus;
	private String nombreRecaudo;
	private String observacion;
	private Integer idTipoMotivo;
	private String nombreTipoMotivo;
	//-----------------Variables Lista----------------------
	private List<Recaudo> listaRecaudos;
	private List<TipoMotivo> listaTipoMotivo;
	private List<Recaudo> listaRecaudosMotivos = new LinkedList<Recaudo>();
	private List<Reglamento> listaRecaudosMotivosPortal;
	//-----------------Variables Objeto---------------------
	private TipoMotivo tipoMotivo;
	private Recaudo recaudoSeleccionado;
	MensajesAlUsuario mensajeAlUsuario = new MensajesAlUsuario();

	// Metodos GETS Y SETS
	public Integer getIdRecaudo() {
		return idRecaudo;
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
	public TipoMotivo getTipoMotivo() {
		return tipoMotivo;
	}
	public void setTipoMotivo(TipoMotivo tipoMotivo) {
		this.tipoMotivo = tipoMotivo;
	}
	public Integer getIdTipoMotivo() {
		return idTipoMotivo;
	}
	public void setIdTipoMotivo(Integer idTipoMotivo) {
		this.idTipoMotivo = idTipoMotivo;
	}
	public String getNombreTipoMotivo() {
		return nombreTipoMotivo;
	}
	public void setNombreTipoMotivo(String nombreTipoMotivo) {
		this.nombreTipoMotivo = nombreTipoMotivo;
	}
	public Recaudo getRecaudoSeleccionado() {
		return recaudoSeleccionado;
	}
	public void setRecaudoSeleccionado(Recaudo recaudoSeleccionado) {
		this.recaudoSeleccionado = recaudoSeleccionado;
	}
	public List<Recaudo> getListaRecaudos() {
		return listaRecaudos;
	}

	public List<Recaudo> getListaRecaudosMotivos() {
		return listaRecaudosMotivos;
	}
	public void setListaRecaudosMotivos(List<Recaudo> listaRecaudosMotivos) {
		this.listaRecaudosMotivos = listaRecaudosMotivos;
	}
	public void setListaRecaudos(List<Recaudo> listaRecaudos) {
		this.listaRecaudos = listaRecaudos;
	}

	public List<TipoMotivo> getListaTipoMotivo() {
		return listaTipoMotivo;
	}
	public void setListaTipoMotivo(List<TipoMotivo> listaTipoMotivo) {
		this.listaTipoMotivo = listaTipoMotivo;
	}

	public List<Reglamento> getListaRecaudosMotivosPortal() {
		return listaRecaudosMotivosPortal;
	}
	public void setListaRecaudosMotivosPortal(
			List<Reglamento> listaRecaudosMotivosPortal) {
		this.listaRecaudosMotivosPortal = listaRecaudosMotivosPortal;
	}//Fin de los m�todos gets y sets

	/**
	/**
	 * inicializaci�n
	 *  Init. C�digo de inicializaci�n.
	 * @param ninguno
	 * @return Objetos inicializados.
	 * @throws No dispara ninguna excepci�n.
	 */
	@Init
	public void init() {

		buscarRecaudos();
		buscarTiposMotivo();
		buscarRecaudosMotivosPortal();
	}	

	/**
	 * Buscar Recaudos
	 * @param Ninguno
	 * @return Busca todos los recaudos activos.
	 * @throws No dispara ninguna excepcion.
	 */
	@Command
	@NotifyChange({"listaRecaudos"})
	public void buscarRecaudos(){
		listaRecaudos  = serviciorecaudo.listadoRecaudosActivos();
	}

	/**
	 * Buscar RecaudoMotivoCombo
	 * 
	 * @param Ninguno
	 * @return Objeto tipo motivo
	 * @throws No dispara ninguna excepcion.
	 */
	@Command
	@NotifyChange({"tipoMotivo","nombreTipoMotivo","listaTipoMotivo","listaRecaudosMotivos"})
	public void buscarRecaudoMotivoCombo() {
		listaRecaudosMotivos = serviciorecaudo.listadoRecaudosPorMotivo(tipoMotivo);
	}

	/**
	 * Buscar Recaudos de un Motivos 
	 * 
	 * @param Ninguno
	 * @return Metodo que buscar los recaudos que pertenecen a cada motivo de apelacion
	 * @throws No dispara ninguna excepcion.
	 */
	@Command
	@NotifyChange({ "listaRecaudosMotivosPortal" })
	public void buscarRecaudosMotivosPortal() {
		listaRecaudosMotivosPortal = servicioreglamento.buscarRecaudosPortal();

	}

	/**
	 * Buscar Tipo Motivo
	 * 
	 * @param Ninguno.
	 * @return Objeto tipo motivo
	 * @throws No dispara ninguna excepcion.
	 */	
	@Command
	@NotifyChange({ "listaTipoMotivo" })
	public void buscarTiposMotivo() {
		listaTipoMotivo = serviciotipomotivo.listadoTipoMotivo();
	}

	/**
	 * Objeto ComboMotivo
	 * @param Ninguno.
	 * @return M�todo que busca los tipos de motivos y los carga en el combobox de motivos
	 * @throws No dispara ninguna excepcion.
	 */	
	@Command
	@NotifyChange({"listaTipoMotivo"})
	public TipoMotivo objetoComboMotivo() {
		return tipoMotivo;
	}

	/**
	 * descargar archivo
	 * 
	 * @param ContextType.COMPONENT) Component componente
	 * @return Ninguno.
	 * @throws No dispara ninguna excepcion.
	 */		
	@Command
	public void descargarArchivo(@ContextParam(ContextType.COMPONENT) Component componente){
		int idDocumento = Integer.parseInt(componente.getAttribute("idReglamento").toString());
		for (int i = 0; i < listaRecaudosMotivosPortal.size(); i++) {
			if (idDocumento == listaRecaudosMotivosPortal.get(i).getIdDocumento())
				Filedownload.save(listaRecaudosMotivosPortal.get(i).getDocumento().getContenidoDocumento(),
						listaRecaudosMotivosPortal.get(i).getDocumento().getTipoDocumento(),
						listaRecaudosMotivosPortal.get(i).getDocumento().getNombreDocumento());
		}
	}

	/**
	 * limpiar
	 * 
	 * @param Ninguno
	 * @return Ninguno
	 * @throws No dispara ninguna excepci�n
	 */
	@Command
	@NotifyChange({"idRecaudo", "descripcion", "nombreRecaudo","observacion","nombreTipoMotivo","tipoMotivo","nombreRecaudofiltro","nombreTipoMotivofiltro","listaRecaudos"})
	public void limpiar(){
		idRecaudo = null;
		nombreRecaudo= null; 
		nombreTipoMotivo=null;
		descripcion=null; 
		observacion=null; 
		nombreRecaudo=null;
		tipoMotivo= null;
		buscarRecaudos();		
	}
}