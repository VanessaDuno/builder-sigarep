package sigarep.viewmodels.maestros;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;



import sigarep.modelos.data.maestros.TipoMotivo;
import sigarep.modelos.servicio.maestros.ServicioTipoMotivo;


@SuppressWarnings("serial")
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMTipoMotivo {
	@WireVariable ServicioTipoMotivo serviciotipomotivo;
	private Integer idTipoMotivo;
	private String nombreTipoMotivo,nombreTipoMotivofiltro;
	private String descripcion,descripcionfiltro;
	private Boolean estatus;
	private List<TipoMotivo> listaTipoMotivo;
	private TipoMotivo tiposeleccionado;
    @Wire Textbox txtnombreTipoMotivo;
    @Wire Window winTipoMotivo;
	
    //Metodos set y get
    public Integer getIdTipoMotivo() {
		return idTipoMotivo;
	}
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
	public void setIdTipoMotivo(Integer idTipoMotivo) {
		this.idTipoMotivo = idTipoMotivo;
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
	//Fin de los metodod gets y sets
    //----------- OTROS METODOS
    @Init
    public void init(){
    	 //initialization code
    	buscarTipoMotivo();
    }
    //Metodo que busca un motivo partiendo por su titulo
  	@Command
  	@NotifyChange({"listaTipoMotivo"})
  	public void buscarTipoMotivo(){
  		listaTipoMotivo = serviciotipomotivo.buscarP(nombreTipoMotivo);
  	}
    //Metodos que Permite guardar los tipos de motivos
    @Command
	@NotifyChange({"nombreTipoMotivo", "descripcion","listaTipoMotivo"})//el notifychange le  avisa a que parametros en la pantalla se van a cambiar, en este caso es nombre,apellido,email,sexo se va a colocar en blanco al guardar!!
	public void guardar(){
		if (nombreTipoMotivo.equals("")||descripcion.equals("")){
			Messagebox.show("Debes Llenar todos los Campos", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);
		}
		else{
		TipoMotivo motivo = new TipoMotivo(idTipoMotivo,nombreTipoMotivo, descripcion,true);
		serviciotipomotivo.guardar(motivo);
		Messagebox.show("Se ha Registrado Correctamente", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
		limpiar();
		}
	}
  //Metodo que busca un tipo de motivo 
    @Command
	@NotifyChange({"nombreTipoMotivo", "descripcion"})
	public void limpiar(){
		nombreTipoMotivo = "";descripcion="";
		buscarTipoMotivo();
	}
 
  //Metodo que elimina un tipo de motivo tomando en cuenta el idTipoMotivo
  	@Command
  	@NotifyChange({"nombreTipoMotivo", "descripcion", "listaTipoMotivo"})
  	public void eliminarTipoMotivo(){
  		serviciotipomotivo.eliminar(getTiposeleccionado().getIdTipoMotivo());
  		limpiar();
  		Messagebox.show("Se ha Eliminado Correctamente", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
  	}
  //permite tomar los datos del objeto tipo motivo seleccionado
    @Command
	@NotifyChange({"nombreTipoMotivo", "descripcion"})
	public void mostrarSeleccionado(){
		nombreTipoMotivo= getTiposeleccionado().getNombreTipoMotivo();
		descripcion=getTiposeleccionado().getDescripcion();
		
	}
  //Este metodo busca al tipo motivo por el FILTRO de nombre
  	@Command
  	@NotifyChange({"listaTipoMotivo"})
  	public void buscarTipoMotivoFiltronombre(){
  		listaTipoMotivo =serviciotipomotivo.buscarP(nombreTipoMotivofiltro);
  	}
  	
  	//Este metodo busca al tipo motivo  por el FILTRO de nombre
  	@Command
  	@NotifyChange({"listaTipoMotivo"})
  	public void buscarTipoMotivoFiltrodescripcion(){
  		listaTipoMotivo =serviciotipomotivo.buscarP(descripcionfiltro);
  	}
}
