package sigarep.viewmodels.transacciones;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;
import sigarep.herramientas.MensajesAlUsuario;
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
import sigarep.modelos.servicio.transacciones.ServicioAsignaturaEstudianteSancionado;
import sigarep.modelos.servicio.transacciones.ServicioEstudianteSancionado;
import sigarep.modelos.servicio.transacciones.ServicioMotivo;
import sigarep.modelos.servicio.transacciones.ServicioRecaudoEntregado;
import sigarep.modelos.servicio.transacciones.ServicioSolicitudApelacion;
import sigarep.modelos.servicio.maestros.ServicioRecaudo;
import sigarep.modelos.servicio.maestros.ServicioTipoMotivo;

/**
 * VM Verificar recaudos I - comisi�n sustanciadora
 * Metodos necesarios para la verificaci�n de los recaudos
 * entregados a los estudiantes en la primera apelaci�n.
 * @author Equipo Builder
 * @version 1.3
 * @since 12/01/2014
 * @last 10/05/2014
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMVerificarRecaudosEntregadosI {
	// --------------------------Servicios------------------------------
	@WireVariable
	private ServicioEstudianteSancionado servicioestudiantesancionado;
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
	// --------------------------Variables de Control-------------------
	private LapsoAcademico lapsoAcademico;
	private String programa;
	private String cedula;
	private String nombres;
	private String apellidos;
	private String asignatura;
	private String sancion;
	private String lapso;
	private Integer semestreSancion;
	private String caso;
	private Integer periodoSancion;
	private String fechaApelacion;
	private String selected = "";
	private String lapsosConsecutivos;
	private String asignaturaLapsosConsecutivos = "";
	private String labelAsignaturaLapsosConsecutivos;
	private String telefono;
	// --------------------------Variables Lista------------------------
	private List<Recaudo> listaRecaudos = new LinkedList<Recaudo>();
	private List<AsignaturaEstudianteSancionado> asignaturas;
	private List<LapsoAcademico> listaLapso;
	private List<SancionMaestro> listaSancion;
	List<Recaudo> listaRecaudosGenerales = new LinkedList<Recaudo>();
	private List<Recaudo> listaRecaudosPorMotivo;
	// --------------------------Variables Objeto------------------------
	RecaudoEntregado recaudoEntregado = new RecaudoEntregado();
	RecaudoEntregadoPK recaudoEntregadoPK = new RecaudoEntregadoPK();
	EstudianteSancionado estudianteSancionado = new EstudianteSancionado();
	MensajesAlUsuario mensajeAlUsuario = new MensajesAlUsuario();
	private TipoMotivo tipoMotivo;

	// M�todos Set y Get
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

	public List<Recaudo> getListaRecaudos() {
		return listaRecaudos;
	}

	public void setListaRecaudos(List<Recaudo> listaRecaudos) {
		this.listaRecaudos = listaRecaudos;
	}

	public String getLapso() {
		return lapso;
	}

	public void setLapso(String lapso) {
		this.lapso = lapso;
	}

	public String getCaso() {
		return caso;
	}

	public void setCaso(String caso) {
		this.caso = caso;
	}

	public Integer getPeriodoSancion() {
		return periodoSancion;
	}

	public void setPeriodoSancion(Integer periodoSancion) {
		this.periodoSancion = periodoSancion;
	}

	public String getFechaApelacion() {
		return fechaApelacion;
	}

	public void setFechaApelacion(String fechaApelacion) {
		this.fechaApelacion = fechaApelacion;
	}

	public List<AsignaturaEstudianteSancionado> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(List<AsignaturaEstudianteSancionado> asignaturas) {
		this.asignaturas = asignaturas;
	}

	public String getLapsosConsecutivos() {
		return lapsosConsecutivos;
	}

	public void setLapsosConsecutivos(String lapsosConsecutivos) {
		this.lapsosConsecutivos = lapsosConsecutivos;
	}

	public String getAsignaturaLapsosConsecutivos() {
		return asignaturaLapsosConsecutivos;
	}

	public void setAsignaturaLapsosConsecutivos(
			String asignaturaLapsosConsecutivos) {
		this.asignaturaLapsosConsecutivos = asignaturaLapsosConsecutivos;
	}

	public String getLabelAsignaturaLapsosConsecutivos() {
		return labelAsignaturaLapsosConsecutivos;
	}

	public void setLabelAsignaturaLapsosConsecutivos(
			String labelAsignaturaLapsosConsecutivos) {
		this.labelAsignaturaLapsosConsecutivos = labelAsignaturaLapsosConsecutivos;
	}

	// Fin M�todos Set y Get

	/**
	 * Inicializaci�n
	 * 
	 * @param init
	 * @return Carga de Variables y m�todos inicializados.
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("sancionadoSeleccionado") SolicitudApelacion sa) {
		Selectors.wireComponents(view, this, false);
		this.cedula = sa.getEstudianteSancionado().getId()
				.getCedulaEstudiante();
		this.nombres = sa.getEstudianteSancionado().getEstudiante()
				.getPrimerNombre()
				+ " "
				+ sa.getEstudianteSancionado().getEstudiante()
						.getSegundoNombre();
		this.apellidos = sa.getEstudianteSancionado().getEstudiante()
				.getPrimerApellido()
				+ " "
				+ sa.getEstudianteSancionado().getEstudiante()
						.getSegundoApellido();
		this.programa = sa.getEstudianteSancionado().getEstudiante()
				.getProgramaAcademico().getNombrePrograma();
		this.sancion = sa.getEstudianteSancionado().getSancionMaestro()
				.getNombreSancion();
		this.lapso = sa.getId().getCodigoLapso();
		this.lapsosConsecutivos = sa.getEstudianteSancionado()
				.getLapsosAcademicosRp();
		this.caso = sa.getNumeroCaso();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		this.fechaApelacion = sdf.format(sa.getFechaSolicitud());
		this.periodoSancion = sa.getEstudianteSancionado().getPeriodoSancion();
		buscarRecaudos();
		if (sancion.equalsIgnoreCase("RR")) {
			asignaturas = servicioasignaturaestudiantesancionado
					.buscarAsignaturaDeSancion(this.cedula, this.lapso);
			if (asignaturas != null)
				for (int i = 0; i < asignaturas.size(); i++)
					this.asignaturaLapsosConsecutivos += asignaturas.get(i)
							.getAsignatura().getNombreAsignatura()
							+ ", ";
			this.labelAsignaturaLapsosConsecutivos = "Asignatura(s):";
		} else {
			this.labelAsignaturaLapsosConsecutivos = "Lapsos consecutivos:";
			this.asignaturaLapsosConsecutivos = lapsosConsecutivos;
		}
	}

	/**
	 * Actualizar lista sancionados
	 * 
	 * @param Ninguno
	 * @return Lista actualizada.
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	@GlobalCommand
	public void actualizarListaSancionados() {
		BindUtils.postGlobalCommand(null, null, "buscarSancionados", null);
	}

	/**
	 * Buscar recaudos.
	 * 
	 * @param Ninguno
	 * @return Busca todos los registros.
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	@Command
	@NotifyChange({ "cedula", "lapso", "nombreRecaudo", "nombreTipoMotivo",
			"listaRecaudos" })
	public void buscarRecaudos() {
		listaRecaudos = serviciorecaudo.buscarRecaudosPorApelacion(cedula,
				lapso, 1);
	}

	/**
	 * Registrar recaudos entregados. Guarda los recaudos seleccionados en el
	 * checkbox en la tabla de recaudos entregados y de ser el caso el veredicto
	 * 
	 * @param cedula
	 *            , nombre, apellidos, estudianteSancionado, lapso, observacion
	 * @return Registro guardado.
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	@Command
	@NotifyChange({ "cedula", "nombres", "apellidos", "estudianteSancionado",
			"lapso" })
	public void registrarRecaudosEntregados(
			@BindingParam("recaudosEntregados") Set<Listitem> recaudos,
			@BindingParam("window") Window winVerificarRecaudos,
			@BindingParam("listaRecaudos") Listbox listaRecaudos) {
		if (recaudos.size() == 0) {
			mensajeAlUsuario.advertenciaSeleccionarAlMenosUnRecaudoEntregado();
		} else {
			ApelacionEstadoApelacion apelacionEstadoApelacion = new ApelacionEstadoApelacion();
			SolicitudApelacionPK solicitudApelacionPK = new SolicitudApelacionPK();
			solicitudApelacionPK.setCedulaEstudiante(cedula);
			solicitudApelacionPK.setCodigoLapso(lapso);
			solicitudApelacionPK.setIdInstanciaApelada(1);
			SolicitudApelacion solicitudApelacion = new SolicitudApelacion();
			solicitudApelacion = serviciosolicitudapelacion
					.buscarSolicitudPorID(solicitudApelacionPK);
			Recaudo recaudo = new Recaudo();
			for (Listitem miRecaudo : recaudos) {
				String nombreRecaudo = miRecaudo.getLabel();
				recaudo = serviciorecaudo.buscarRecaudoPorNombre(nombreRecaudo);
				recaudoEntregadoPK.setIdInstanciaApelada(1);
				recaudoEntregadoPK.setCedulaEstudiante(cedula);
				recaudoEntregadoPK.setIdTipoMotivo(recaudo.getTipoMotivo()
						.getIdTipoMotivo());
				recaudoEntregadoPK.setCodigoLapso(lapso);
				recaudoEntregadoPK.setIdRecaudo(recaudo.getIdRecaudo());
				RecaudoEntregado recaudoEntregadoAux = new RecaudoEntregado();
				recaudoEntregadoAux.setId(recaudoEntregadoPK);
				recaudoEntregadoAux.setEstatus(true);
				MotivoPK motivoPK = new MotivoPK();
				motivoPK.setCedulaEstudiante(cedula);
				motivoPK.setIdTipoMotivo(recaudo.getTipoMotivo()
						.getIdTipoMotivo());
				motivoPK.setCodigoLapso(lapso);
				motivoPK.setIdInstanciaApelada(1);
				Motivo motivo = new Motivo();
				motivo = serviciomotivo.buscarMotivoPorID(motivoPK);
				motivo.addRecaudoEntregado(recaudoEntregadoAux);
				serviciomotivo.guardarMotivo(motivo);
			}
			solicitudApelacion.setVerificado(true);
			solicitudApelacion.setAnalizado(false);
			ApelacionEstadoApelacionPK apelacionEstadoApelacionPK = new ApelacionEstadoApelacionPK();
			apelacionEstadoApelacionPK.setCedulaEstudiante(cedula);
			apelacionEstadoApelacionPK.setCodigoLapso(lapso);
			apelacionEstadoApelacionPK.setIdEstadoApelacion(2);
			apelacionEstadoApelacionPK.setIdInstanciaApelada(1);
			apelacionEstadoApelacion.setId(apelacionEstadoApelacionPK);
			apelacionEstadoApelacion.setFechaEstado(new Date());
			solicitudApelacion
					.addApelacionEstadosApelacion(apelacionEstadoApelacion);
			serviciosolicitudapelacion.guardar(solicitudApelacion);
			try {
				mensajeAlUsuario.informacionRegistroCorrecto();
				winVerificarRecaudos.detach(); // oculta el window
				actualizarListaSancionados();
				// falta actualizar la lista de apelaciones en este punto
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			limpiar(listaRecaudos);
		}
	}

	/**
	 * Limpiar.
	 * 
	 * @param @BindingParam("listaRecaudos") Listbox listaRecaudos
	 * @return Ninguno.
	 * @throws No
	 *             dispara ninguna exepci�n.
	 */
	@Command
	@NotifyChange({ "cedula", "nombres", "selected", "apellidos",
			"listaRecaudosPorMotivo", "programa", "lapsoAcademico", "telefono",
			"sancion", "asignatura" })
	public void limpiar(@BindingParam("listaRecaudos") Listbox listaRecaudos) {
		selected = "";
		buscarRecaudos();
		listaRecaudos.clearSelection();
	}

	/**
	 * Notificar recaudo verificado.
	 * 
	 * @param @BindingParam("todosLosItems") List<Listitem> items,
	 * @ContextParam(ContextType.COMPONENT) Component componente
	 * @return Ninguno.
	 * @throws No
	 *             dispara ninguna exepci�n.
	 */
	@Command
	public void notificarRecaudoVerificado(
			@BindingParam("todosLosItems") List<Listitem> items,
			@ContextParam(ContextType.COMPONENT) Component componente) {
		String identificadorItemSeleccionado = String.valueOf(componente
				.getAttribute("identificadorListitem"));
		for (Listitem a : items) {
			String identificadorDelItem = ((Listcell) a.getChildren().get(2))
					.getLabel();
			if (identificadorDelItem.equals(identificadorItemSeleccionado)) {
				if (a.isSelected())
					Clients.showNotification("Recaudo Verificado",
							Clients.NOTIFICATION_TYPE_INFO, componente,
							"middle_center", 1000);
				break;
			}
		}
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
	@NotifyChange({ "tipoMotivo", "nombreRecaudo", "listaRecaudosPorMotivo" })
	public void cerrarVentana(@BindingParam("ventana") final Window ventana,
			@BindingParam("recaudosEntregados") Set<Listitem> recaudos) {
		boolean condicion = false;
		if (recaudos.size() != 0)
			condicion = true;
		mensajeAlUsuario.confirmacionCerrarVentanaMaestros(ventana, condicion);
	}

}//fin VMVerificarRecaudosEntregadosI