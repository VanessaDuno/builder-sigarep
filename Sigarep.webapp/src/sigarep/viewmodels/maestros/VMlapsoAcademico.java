package sigarep.viewmodels.maestros;
import java.util.Date;
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

import sigarep.modelos.data.maestros.LapsoAcademico;
import sigarep.modelos.servicio.maestros.ServicioLapsoAcademico;


@SuppressWarnings("serial")
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMlapsoAcademico {
	@WireVariable ServicioLapsoAcademico serviciolapsoacademico;
	private String codigoLapso,codigoLapsoFiltro;
	private Date fechaInicio ;
	private Date fechaCierre;
	private Boolean estatus;
	private List<LapsoAcademico> listaLapsoAcademico;
	private LapsoAcademico lapsoAcademicoseleccionado;
    @Wire Textbox txtcodigoLapso;
    @Wire Window ventana;
  //Metodos set y get
    public String getCodigoLapso() {
		return codigoLapso;
	}
	public void setCodigoLapso(String codigoLapso) {
		this.codigoLapso = codigoLapso;
	}
	public Date getFechaCierre() {
		return fechaCierre;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	public Date getFechaInicio() {
		return this.fechaInicio;
	}
	public Boolean getEstatus() {
		return this.estatus;
	}
	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
	public List<LapsoAcademico> getListaLapsoAcademico() {
		return listaLapsoAcademico;
	}
	public void setListaLapsoAcademico(List<LapsoAcademico> listaLapsoAcademico) {
		this.listaLapsoAcademico = listaLapsoAcademico;
	}
	public String getCodigoLapsoFiltro() {
		return codigoLapsoFiltro;
	}
	public void setCodigoLapsoFiltro(String codigoLapsoFiltro) {
		this.codigoLapsoFiltro = codigoLapsoFiltro;
	}
	//@Command
	//@NotifyChange({"codigoLapso", "fechaCierre", "fechaInicio","estatus"})
	public LapsoAcademico getLapsoAcademicoseleccionado() {
		return lapsoAcademicoseleccionado;
	}
	public void setLapsoAcademicoseleccionado(LapsoAcademico lapsoAcademicoseleccionado) {
		this.lapsoAcademicoseleccionado = lapsoAcademicoseleccionado;
	}
	//Fin de los metodod gets y sets
	   
		//----------- OTROS METODOS
	
	//Metodo que busca una noticia partiendo por su titulo
	@Init
	public void init(){
        //initialization code
		buscarActivoLapso();
		buscarLapso();
    }
	
	@Command
	@NotifyChange({"listaLapsoAcademico"})
	public void buscarLapso(){
		listaLapsoAcademico =serviciolapsoacademico.buscarLapsoAcademico(codigoLapso);
	}
	 //Metodos que Permite guardar los lapsos Academicos
	@Command
	@NotifyChange({"codigoLapso", "fechaInicio", "fechaCierre","listaLapsoAcademico"})//el notifychange le  avisa a que parametros en la pantalla se van a cambiar, en este caso es se va a colocar en blanco al guardar!!
	public void guardarLapso(){
		if (codigoLapso==null||fechaInicio==null|| fechaCierre==null)
			Messagebox.show("Debes Llenar todos los Campos", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);
		else{   
		LapsoAcademico lapsoA = new LapsoAcademico(codigoLapso,fechaInicio,fechaCierre,true);
		serviciolapsoacademico.guardarLapso(lapsoA);
		Messagebox.show("Se ha Registrado Correctamente", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
		limpiarlapso();
		}
	}
	//Metodo que limpia  todos los campos y pone la fecha actual 
	@Command
	@NotifyChange({"codigoLapso", "fechaInicio", "fechaCierre","codigoLapsoFiltro","listaLapsoAcademico"})
	public void limpiarlapso(){
		Date fecha = new Date();
		codigoLapso = "";fechaInicio=fecha;fechaCierre=fecha;codigoLapsoFiltro = "";
		buscarActivoLapso();
	}
	 // M�todo que trae todos los registros en una lista de Lapso Academicos
	@Command
	@NotifyChange({"listaLapsoAcademico"})
	public void buscarActivoLapso(){
		listaLapsoAcademico =serviciolapsoacademico.buscarLapsoAcademico(codigoLapso);
	}
	//permite tomar los datos del objeto lapsoAcademico seleccionado
	@Command
	@NotifyChange({"codigoLapso", "fechaCierre", "fechaInicio","listaLapsoAcademico"})
	public void mostrarSeleccionadoLapso(){
		LapsoAcademico lapsoAA =getLapsoAcademicoseleccionado();
		codigoLapso=lapsoAA.getCodigoLapso();
		fechaInicio=lapsoAA.getFechaInicio();
	    fechaCierre=lapsoAA.getFechaCierre();
	}
	 //Metodo que elimina el lapso Academico tomando en cuenta el codigoLaso
  	@Command
  	@NotifyChange({"listaLapsoAcademico"})
  	public void eliminarLapsoAcademico(){
  		if (codigoLapso==null||fechaInicio==null|| fechaCierre==null){
			Messagebox.show("Debes Seleccionar un lapso Acad�mico", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);
  		}
		else{
  		serviciolapsoacademico.eliminarLapso(getLapsoAcademicoseleccionado().getCodigoLapso());
  		limpiarlapso();
  		Messagebox.show("Se ha Eliminado Correctamente", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
  	    }
  	}	
  	 //Este metodo busca el lapso academico por el filtro de codigo
  	@Command
  	@NotifyChange({"listaLapsoAcademico" })
  	public void buscarCodigoLapsoFiltro(){
  		listaLapsoAcademico =serviciolapsoacademico.buscarLapsoAcademico(codigoLapso);
  		}
  	
	
}