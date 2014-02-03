package sigarep.viewmodels.transacciones;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import sigarep.herramientas.mensajes;
import sigarep.modelos.data.maestros.LapsoAcademico;
import sigarep.modelos.data.maestros.Recaudo;
import sigarep.modelos.data.maestros.SancionMaestro;
import sigarep.modelos.data.maestros.TipoMotivo;
import sigarep.modelos.data.transacciones.ApelacionEstadoApelacion;
import sigarep.modelos.data.transacciones.ApelacionEstadoApelacionPK;
import sigarep.modelos.data.transacciones.AsignaturaEstudianteSancionado;
import sigarep.modelos.data.transacciones.EstudianteSancionado;
import sigarep.modelos.data.transacciones.Motivo;
import sigarep.modelos.data.transacciones.MotivoPK;
import sigarep.modelos.data.transacciones.RecaudoEntregado;
import sigarep.modelos.data.transacciones.RecaudoEntregadoPK;
import sigarep.modelos.data.transacciones.SolicitudApelacion;
import sigarep.modelos.data.transacciones.SolicitudApelacionPK;
import sigarep.modelos.servicio.maestros.ServicioRecaudo;
import sigarep.modelos.servicio.maestros.ServicioTipoMotivo;
import sigarep.modelos.servicio.transacciones.ServicioAsignaturaEstudianteSancionado;
import sigarep.modelos.servicio.transacciones.ServicioMotivo;
import sigarep.modelos.servicio.transacciones.ServicioRecaudoEntregado;
import sigarep.modelos.servicio.transacciones.ServicioSolicitudApelacion;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMVerificarRecaudosEntregadosII {

	private String labelAsignaturaLapsosConsecutivos;

	private String lapsosConsecutivos;
	private String asignaturaLapsosConsecutivos="";
	private List<AsignaturaEstudianteSancionado> asignaturas;
	private LapsoAcademico lapsoAcademico;

	private String programa;

	private String cedula;

	private String nombres;

	private String apellidos;

	private String asignatura;

	private String sancion;

	private String lapso;
	
	private Integer caso;

	private String fechaApelacion;

	private Integer semestreSancion;

	private String selected = "";

	private List<RecaudoEntregado> listaRecaudosEntregados = new LinkedList<RecaudoEntregado>();

	private List<Recaudo> listaRecaudosPorEntregar = new LinkedList<Recaudo>();
	@WireVariable
	private ServicioTipoMotivo serviciotipomotivo;
	@WireVariable
	private ServicioSolicitudApelacion serviciosolicitudapelacion;
	@WireVariable
	private ServicioAsignaturaEstudianteSancionado servicioasignaturaestudiantesancionado;
	@WireVariable
	private ServicioRecaudo serviciorecaudo;
	@WireVariable
	private ServicioMotivo serviciomotivo;
	@WireVariable
	private ServicioRecaudoEntregado serviciorecaudoentregado;
	@Wire
	private Combobox cmbSancion;
	@Wire
	private Datebox dtbFechaNacimiento;
	@Wire
	private Datebox dtbAnnoIngreso;

	private List<LapsoAcademico> listaLapso;
	private List<SancionMaestro> listaSancion;

	RecaudoEntregado recaudoEntregado = new RecaudoEntregado();
	RecaudoEntregadoPK recaudoEntregadoPK = new RecaudoEntregadoPK();
	EstudianteSancionado estudianteSancionado = new EstudianteSancionado();
	mensajes msjs = new mensajes(); // para llamar a los diferentes mensajes de
									// dialogo


	@WireVariable
	private TipoMotivo tipoMotivo;
	@WireVariable
	private List<Recaudo> listaRecaudosPorMotivo;

	@WireVariable
	private String telefono;

	private List<Recaudo> listaRecaudos;

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}



	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}



	public TipoMotivo getTipoMotivo() {
		return tipoMotivo;
	}

	public void setTipoMotivo(TipoMotivo tipoMotivo) {
		this.tipoMotivo = tipoMotivo;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getTelefono() {
		return telefono;
	}

	
	public Integer getCaso() {
		return caso;
	}

	public void setCaso(Integer caso) {
		this.caso = caso;
	}

	public String getFechaApelacion() {
		return fechaApelacion;
	}

	public void setFechaApelacion(String fechaApelacion) {
		this.fechaApelacion = fechaApelacion;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public LapsoAcademico getLapsoAcademico() {
		return lapsoAcademico;
	}

	public void setLapsoAcademico(LapsoAcademico lapsoAcademico) {
		this.lapsoAcademico = lapsoAcademico;
	}

	public String getSancion() {
		return sancion;
	}
	
	public String getLabelAsignaturaLapsosConsecutivos() {
		return labelAsignaturaLapsosConsecutivos;
	}

	public void setLabelAsignaturaLapsosConsecutivos(
			String labelAsignaturaLapsosConsecutivos) {
		this.labelAsignaturaLapsosConsecutivos = labelAsignaturaLapsosConsecutivos;
	}

public String getAsignaturaLapsosConsecutivos() {
		return asignaturaLapsosConsecutivos;
	}

	public void setAsignaturaLapsosConsecutivos(String asignaturaLapsosConsecutivos) {
		this.asignaturaLapsosConsecutivos = asignaturaLapsosConsecutivos;
	}


	public void setSancion(String sancion) {
		this.sancion = sancion;
	}

	public Integer getSemestreSancion() {
		return semestreSancion;
	}

	public void setSemestreSancion(Integer semestreSancion) {
		this.semestreSancion = semestreSancion;
	}

	public List<Recaudo> getListaRecaudosPorMotivo() {
		return listaRecaudosPorMotivo;
	}

	public void setListaRecaudosPorMotivo(List<Recaudo> listaRecaudosPorMotivo) {
		this.listaRecaudosPorMotivo = listaRecaudosPorMotivo;
	}

	public List<LapsoAcademico> getListaLapso() {
		return listaLapso;
	}

	public void setListaLapso(List<LapsoAcademico> ListaLapso) {
		this.listaLapso = ListaLapso;
	}

	public List<SancionMaestro> getListaSancion() {
		return listaSancion;
	}

	public void setListaSancion(List<SancionMaestro> listaSancion) {
		this.listaSancion = listaSancion;
	}

	public List<RecaudoEntregado> getListaRecaudosEntregados() {
		return listaRecaudosEntregados;
	}

	public void setListaRecaudosEntregados(
			List<RecaudoEntregado> listaRecaudosEntregados) {
		this.listaRecaudosEntregados = listaRecaudosEntregados;
	}

	public String getLapso() {
		return lapso;
	}

	public void setLapso(String lapso) {
		this.lapso = lapso;
	}


	public List<Recaudo> getListaRecaudosPorEntregar() {
		return listaRecaudosPorEntregar;
	}

	public void setListaRecaudosPorEntregar(List<Recaudo> listaRecaudosPorEntregar) {
		this.listaRecaudosPorEntregar = listaRecaudosPorEntregar;
	}
	
	public List<Recaudo> getListaRecaudos() {
		return listaRecaudos;
	}

	public void setListaRecaudos(List<Recaudo> listaRecaudos) {
		this.listaRecaudos = listaRecaudos;
	}

	@Command
	@NotifyChange({ "tipoMotivo", "nombreRecaudo", "listaRecaudosPorMotivo" })
	public void buscarRecaudosPorTipoMotivo(Integer tipoMotivo) {
		listaRecaudosPorMotivo = serviciorecaudo
				.listadoRecaudosPorMotivo(tipoMotivo);
	}

	@Init
	public void init(

	@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("sancionadoSeleccionado") SolicitudApelacion v1)

	// initialization code

	{
		Selectors.wireComponents(view, this, false);
		System.out.println("segundo nombre" + v1);
		this.cedula = v1.getEstudianteSancionado().getId()
				.getCedulaEstudiante();
		this.nombres = v1.getEstudianteSancionado().getEstudiante()
				.getPrimerNombre()
				+ " "
				+ v1.getEstudianteSancionado().getEstudiante()
						.getSegundoNombre();
		this.apellidos = v1.getEstudianteSancionado().getEstudiante()
				.getPrimerApellido()
				+ " "
				+ v1.getEstudianteSancionado().getEstudiante()
						.getSegundoApellido();
		this.programa = v1.getEstudianteSancionado().getEstudiante()
				.getProgramaAcademico().getNombrePrograma();
		this.sancion = v1.getEstudianteSancionado().getSancionMaestro()
				.getNombreSancion();
		this.lapso = v1.getEstudianteSancionado().getLapsoAcademico()
				.getCodigoLapso();
		this.semestreSancion = v1.getEstudianteSancionado().getSemestre();
		this.caso = v1.getNumeroCaso();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		this.fechaApelacion = sdf.format(v1.getFechaSolicitud());
		this.lapsosConsecutivos = v1.getEstudianteSancionado().getLapsosAcademicosRp();

		buscarRecaudos();
		
		if (sancion.equalsIgnoreCase("RR")) {
			asignaturas = servicioasignaturaestudiantesancionado
					.buscarAsignaturaDeSancion(cedula, lapso);
			if (asignaturas != null)
				for (int i = 0; i < asignaturas.size(); i++)
					asignaturaLapsosConsecutivos += asignaturas.get(i)
							.getAsignatura().getNombreAsignatura()
							+ ", ";
			labelAsignaturaLapsosConsecutivos = "Asignatura(s):";
		} else {
			labelAsignaturaLapsosConsecutivos = "Lapsos consecutivos:";
			asignaturaLapsosConsecutivos = lapsosConsecutivos;
		}
	}
	
	

	@Command
	@NotifyChange({"listaRecaudosEntregados", "listaRecaudosPorEntregar"})
	public void buscarRecaudos() {
		listaRecaudosEntregados = serviciorecaudoentregado.buscarRecaudosEntregadosVerificarRecaudosII(cedula);
	    listaRecaudosPorEntregar = serviciorecaudo.buscarRecaudosVerificarRecaudosII(cedula);
}

	
	@Command
	@NotifyChange({ "cedula", "nombres", "apellidos", "estudianteSancionado","lapso"})
	public void registrarRecaudosEntregados(@BindingParam("recaudosEntregados") Set<Listitem> recaudos, @BindingParam("window") Window winVerificarRecaudosII) {
		if (recaudos.size() == 0) {
			Messagebox.show("Debe seleccionar al menos un recaudo entregado",
					"Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);
		}
//		else if (selected.equals("")) {
//			Messagebox.show("Debe Seleccionar una sugerencia de procedencia del caso","Advertencia", Messagebox.OK,Messagebox.EXCLAMATION);
//		}
		else 
		{
			ApelacionEstadoApelacion apelacionEstadoApelacion = new ApelacionEstadoApelacion();
			if (getSelected().equals("sugiere"))
				apelacionEstadoApelacion.setSugerencia("Procedente");
			else
				apelacionEstadoApelacion.setSugerencia("No Procedente");
			
			SolicitudApelacionPK solicitudApelacionPK = new SolicitudApelacionPK();
			solicitudApelacionPK.setCedulaEstudiante(cedula);
			solicitudApelacionPK.setCodigoLapso(lapso);
			solicitudApelacionPK.setIdInstanciaApelada(2);
			SolicitudApelacion solicitudApelacion = new SolicitudApelacion();
			solicitudApelacion = serviciosolicitudapelacion.buscarSolicitudPorID(solicitudApelacionPK);
				
			Recaudo recaudo = new Recaudo();
			for(Listitem miRecaudo: recaudos){
				String nombreRecaudo = miRecaudo.getLabel();
				recaudo = serviciorecaudo.buscarRecaudoNombre(nombreRecaudo);
				recaudoEntregadoPK.setIdInstanciaApelada(2);
				recaudoEntregadoPK.setCedulaEstudiante(cedula);
				recaudoEntregadoPK.setIdTipoMotivo(recaudo.getTipoMotivo().getIdTipoMotivo());
				recaudoEntregadoPK.setCodigoLapso(lapso);
				recaudoEntregadoPK.setIdRecaudo(recaudo.getIdRecaudo());
				RecaudoEntregado recaudoEntregadoAux = new RecaudoEntregado();
				recaudoEntregadoAux.setId(recaudoEntregadoPK);
				recaudoEntregadoAux.setEstatus(true);
				MotivoPK motivoPK = new MotivoPK();
				motivoPK.setCedulaEstudiante(cedula);
				motivoPK.setIdTipoMotivo(recaudo.getTipoMotivo().getIdTipoMotivo());
				motivoPK.setCodigoLapso(lapso);
				motivoPK.setIdInstanciaApelada(2);
				Motivo motivo = new Motivo();
				motivo.setId(motivoPK);
				motivo.setEstatus(true);
				motivo.addRecaudoEntregado(recaudoEntregadoAux);
				serviciomotivo.guardarMotivo(motivo);
			}
				SolicitudApelacion solicitudApelacionAux = new SolicitudApelacion();
				solicitudApelacionAux.setId(solicitudApelacionPK);
				solicitudApelacionAux.setEstatus(true);
				solicitudApelacionAux.setFechaSesion(solicitudApelacion.getFechaSesion());
				solicitudApelacionAux.setFechaSolicitud(solicitudApelacion.getFechaSolicitud());
				solicitudApelacionAux.setNumeroCaso(solicitudApelacion.getNumeroCaso());
				solicitudApelacionAux.setNumeroSesion(solicitudApelacion.getNumeroSesion());
				solicitudApelacionAux.setVeredicto(solicitudApelacion.getVeredicto());
				solicitudApelacionAux.setObservacion(solicitudApelacion.getObservacion());
				solicitudApelacionAux.setVerificado(true);
				solicitudApelacionAux.setAnalizado(false);
				ApelacionEstadoApelacionPK apelacionEstadoApelacionPK = new ApelacionEstadoApelacionPK();
				apelacionEstadoApelacionPK.setCedulaEstudiante(cedula);
				apelacionEstadoApelacionPK.setCodigoLapso(lapso);
				apelacionEstadoApelacionPK.setIdEstadoApelacion(6);
				apelacionEstadoApelacionPK.setIdInstanciaApelada(2);
				
				apelacionEstadoApelacion.setId(apelacionEstadoApelacionPK);
				apelacionEstadoApelacion.setFechaEstado(new Date());
				solicitudApelacionAux.addApelacionEstadosApelacion(apelacionEstadoApelacion);
				serviciosolicitudapelacion.guardar(solicitudApelacionAux);
				
				try {
					msjs.informacionRegistroCorrecto();
					winVerificarRecaudosII.detach(); //oculta el window
					//falta actualizar la lista de apelaciones en este punto
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				limpiar();
		}
	}

	@Command
	@NotifyChange({"listaRecaudosPorEntregar", "listaRecaudosEntregados","selected" })
	public void limpiar() {
		selected = "";
		listaRecaudosEntregados = serviciorecaudoentregado.buscarRecaudosEntregadosVerificarRecaudosII(cedula);
	    listaRecaudosPorEntregar = serviciorecaudo.buscarRecaudosVerificarRecaudosII(cedula);
		buscarRecaudos();
	}

	@Command
	@NotifyChange({ "listaTipoMotivo", "tipoMotivo", "listaRecaudosPorMotivo" })
	public TipoMotivo objetoTipoMotivo() {
		System.out.println("tipo de motivo:" + tipoMotivo.getIdTipoMotivo());
		buscarRecaudosPorTipoMotivo(tipoMotivo.getIdTipoMotivo());
		return tipoMotivo;
	}
}
