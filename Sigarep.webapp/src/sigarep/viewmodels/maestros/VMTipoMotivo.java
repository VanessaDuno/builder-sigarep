package sigarep.viewmodels.maestros;
import java.util.List;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;


import sigarep.herramientas.MensajesAlUsuario;

import sigarep.modelos.data.maestros.TipoMotivo;
import sigarep.modelos.servicio.maestros.ServicioTipoMotivo;

/**
 * TipoMotivo UCLA DCYT Sistemas de Informacion.
 * @author Equipo : Builder-Sigarep Lapso 2013-2
 * @version 1.0
 * @since 22/01/14
 */

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMTipoMotivo {
	@WireVariable ServicioTipoMotivo serviciotipomotivo;

	private String nombreTipoMotivo,nombreTipoMotivofiltro;
	private String descripcion,descripcionfiltro;
	private Integer idTipoMotivo;
	private Boolean estatus;
	private String nombreFiltro;
	private List<TipoMotivo> listaTipoMotivo;
	private TipoMotivo tiposeleccionado;
	@Wire Textbox txtnombreTipoMotivo;
    @Wire Window winTipoMotivo;
    MensajesAlUsuario mensajeAlUsuario = new MensajesAlUsuario();
    
    @Wire("#winTipoMotivo")//para conectarse a la ventana con el ID
	Window ventana;
	 @AfterCompose //para poder conectarse con los componentes en la vista, es necesario si no da null Pointer
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
        Selectors.wireComponents(view, this, false);
    }
	
    //Metodos set y get

    public String getNombreTipoMotivo() {
		return nombreTipoMotivo;
	}
    public String getDescripcion() {
		return descripcion;
	}
    public Boolean getEstatus() {
		return estatus;
	}
    public String getNombreTipoMotivofiltro() {
		return nombreTipoMotivofiltro;
	}
    public String getDescripcionfiltro() {
		return descripcionfiltro;
	}
    public List<TipoMotivo> getListaTipoMotivo() {
		return listaTipoMotivo;
	}
    public TipoMotivo getTiposeleccionado() {
		return tiposeleccionado;
	}
	public void setNombreTipoMotivofiltro(String nombreTipoMotivofiltro) {
		this.nombreTipoMotivofiltro = nombreTipoMotivofiltro;
	}
	
	public void setDescripcionfiltro(String descripcionfiltro) {
		this.descripcionfiltro = descripcionfiltro;
	}
	
	public void setNombreTipoMotivo(String nombreTipoMotivo) {
		this.nombreTipoMotivo = nombreTipoMotivo;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
	public void setListaTipoMotivo(List<TipoMotivo> ListaTipoMotivo) {
		this.listaTipoMotivo = ListaTipoMotivo;
	}
	public void setTiposeleccionado(TipoMotivo tiposeleccionado) {
		this.tiposeleccionado = tiposeleccionado;
	}

    public Integer getIdTipoMotivo() {
		return idTipoMotivo;
	}
	public void setIdTipoMotivo(Integer idTipoMotivo) {
		this.idTipoMotivo = idTipoMotivo;
	}
	public String getNombreFiltro() {
		return nombreFiltro;
	}
	public void setNombreFiltro(String nombreFiltro) {
		this.nombreFiltro = nombreFiltro;
	}
	//Fin de los metodod gets y sets
   
	//----------- OTROS METODOS
    @Init
    public void init(){

      	listaTipoMotivo();
      	
    } 
    
    /**
	 * guardarTipoMotivo
	 * 
	 * @param idTipoMotivo
	 *            , nombreTipoMotivo, descripcion, listaTipoMotivo
	 * @return No devuelve ningun valor
	 * @throws No
	 *             debe haber campos en blanco
	 */
    @Command
	@NotifyChange({"idTipoMotivo","nombreTipoMotivo", "descripcion","estatus","listaTipoMotivo"})//el notifychange le  avisa a que parametros en la pantalla se van a cambiar, en este caso es nombre,apellido,email,sexo se va a colocar en blanco al guardar!!
	public void guardarTipoMotivo(){
    	if (nombreTipoMotivo == null || descripcion == null) {
			mensajeAlUsuario.advertenciaLlenarCampos();
		} else {
			TipoMotivo tipo = new TipoMotivo(idTipoMotivo, descripcion, true, nombreTipoMotivo, false);
			serviciotipomotivo.guardarTipoMotivo(tipo);
			mensajeAlUsuario.informacionRegistroCorrecto();
			limpiar();
		}
	}
    	
	/**
	 * listaTipoMotivo
	 * 
	 * @param listaTipoMotivo
	 * @return No devuelve ningun valor
	 * @throws No
	 *             dispara ninguna excepci�n
	 */

 	@Command
 	@NotifyChange({ "listaTipoMotivo" })
 	public void listaTipoMotivo() {
 		listaTipoMotivo = serviciotipomotivo.listadoTipoMotivo();
 	}	

 	
 	
 	
 	/**
	 * limpiar
	 * 
	 * @param idTipoMotivo
	 *            , nombreTipoMotivo, descripcion, listaTipoMotivo, estatus
	 * @return No devuelve ningun valor
	 * @throws No
	 *             dispara ninguna excepci�n
	 */
    @Command
	@NotifyChange({"listaTipoMotivo","idTipoMotivo","nombreTipoMotivo", "estatus","descripcion","nombreFiltro"})
	public void limpiar(){
    	idTipoMotivo= null;
    	nombreTipoMotivo = null;
		descripcion=null;
		nombreFiltro= "";
		listaTipoMotivo();
		
	}
    
    
    /**
	 * eliminarTipoMotivo
	 * 
	 * @param idTipoMotivo
	 *            , nombreTipoMotivo, estatus, descripcion, listaTipoMotivo
	 * @return No devuelve ningun valor
	 * @throws Debe
	 *             seleccionar un registro para poder eliminarlo
	 */
  	@SuppressWarnings("unchecked")
	@Command
	@NotifyChange({"listaTipoMotivo", "idTipoMotivo","nombreTipoMotivo", "descripcion"})
	public void eliminarTipoMotivo(@ContextParam(ContextType.BINDER) final Binder binder){
  		if (nombreTipoMotivo==null  || descripcion==null ) {
			mensajeAlUsuario.advertenciaSeleccionarParaEliminar();
		} else {
			Messagebox.show("�Desea eliminar el registro realmente?","Confirmar",new Messagebox.Button[] { Messagebox.Button.YES,Messagebox.Button.NO },
					Messagebox.QUESTION,new EventListener<ClickEvent>() {
				@SuppressWarnings("incomplete-switch")
				public void onEvent(ClickEvent e) throws Exception {
					switch (e.getButton()) {
						case YES:
							//if you call super.delete here, since original zk event is not control by binder
							//the change of viewmodel will not update to the ui.
							//so, I post a delete to trigger to process it in binder controll.
							//binder.postCommand("limpiar", null);
							serviciotipomotivo.eliminarTipoMotivo(getTiposeleccionado().getIdTipoMotivo());
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
	 * mostrarSeleccionado
	 * 
	 * @param idTipoMotivo
	 *            , nombreTipoMotivo, estatus, descripcion
	 * @return No devuelve ningun valor
	 * @throws No
	 *             dispara ninguna excepci�n
	 */
    @Command
	@NotifyChange({"idTipoMotivo","nombreTipoMotivo", "descripcion","estatus"})
	public void mostrarSeleccionado(){
  
    	idTipoMotivo= getTiposeleccionado().getIdTipoMotivo();
		nombreTipoMotivo= getTiposeleccionado().getNombreTipoMotivo();
		descripcion=getTiposeleccionado().getDescripcion();	
	}
    
    
    
    /**
	 * filtros
	 * 
	 * @param listaTipoMotivo
	 * @return No devuelve ningun valor
	 * @throws No
	 *             dispara ninguna excepci�n
	 */
	@Command
	@NotifyChange({ "listaTipoMotivo", "nombreFiltro" })
	public void filtros() {
		listaTipoMotivo = serviciotipomotivo.filtrarTipoMotivo(nombreFiltro);
	}
	
	/**
	 * Cerrar Ventana
	 * 
	 * @param binder
	 * @return cierra el .zul asociado al VM
	 * @throws No
	 *             dispara ninguna excepcion.
	 */
	
	@SuppressWarnings("unchecked")
	@Command
	@NotifyChange({"listaTipoMotivo", "idTipoMotivo","nombreTipoMotivo", "descripcion"})
	public void cerrarVentana(@ContextParam(ContextType.BINDER) final Binder binder){
			
		if (nombreTipoMotivo !=null  || descripcion !=null ) 
		{
			Messagebox.show("�Realemente desea cerrar la ventana sin guardar los cambios?","Confirmar",new Messagebox.Button[] { Messagebox.Button.YES,Messagebox.Button.NO },
					Messagebox.QUESTION,new EventListener<ClickEvent>() {
				@SuppressWarnings("incomplete-switch")
				public void onEvent(ClickEvent e) throws Exception {
					switch (e.getButton()) {
						case YES:
								ventana.detach();
					
					}
				}
			});		
		}
		else{
		Messagebox.show("�Realmente desea cerrar la ventana?","Confirmar",new Messagebox.Button[] { Messagebox.Button.YES,Messagebox.Button.NO },
					Messagebox.QUESTION,new EventListener<ClickEvent>() {
				@SuppressWarnings("incomplete-switch")
				public void onEvent(ClickEvent e) throws Exception {
					switch (e.getButton()) {
						case YES:
								ventana.detach();
					
					
					}
				}
			});		
		}
	}


}
