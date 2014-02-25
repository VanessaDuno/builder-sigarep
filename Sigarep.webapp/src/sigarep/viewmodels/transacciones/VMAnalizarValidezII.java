package sigarep.viewmodels.transacciones;

/**VM Analizar validezII
 * Registra y modifica EL analizar Validez de la reconsideraciones
 * @author  Builder
 * @version 1.0
 * @since 15/01/14
 */

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
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
import sigarep.modelos.servicio.transacciones.ServicioMotivo;
import sigarep.modelos.servicio.transacciones.ServicioRecaudoEntregado;
import sigarep.modelos.servicio.transacciones.ServicioSolicitudApelacion;
import sigarep.modelos.servicio.maestros.ServicioRecaudo;
import sigarep.modelos.servicio.maestros.ServicioTipoMotivo;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMAnalizarValidezII {
	@WireVariable
	private LapsoAcademico lapsoAcademico;
	private String programa;
	private String cedula;
	private String nombres;
	private String apellidos;
	private String asignatura;
	private String lapsosConsecutivos;
	private String asignaturaLapsosConsecutivos = "";
	private String labelAsignaturaLapsosConsecutivos;
	private String sancion;
	private String lapso;
	private String caso;
	private String telefono;
	private String fechaApelacion;
	private String observacion;
	private String selected = "";
	private String observacionexperto;
	private Integer semestreSancion;
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
	private SolicitudApelacion sancionadoSeleccionado;
	private boolean mostrarButtonObservacionAnterior = false;
	private TipoMotivo tipoMotivo;
	private List<LapsoAcademico> listaLapso;
	private List<SancionMaestro> listaSancion;
	private List<AsignaturaEstudianteSancionado> asignaturas;
	private List<Recaudo> listaRecaudosPorMotivo;
	private List<RecaudoEntregado> listaRecaudo;
	List<Recaudo> listaRecaudosGenerales = new LinkedList<Recaudo>();
	RecaudoEntregado recaudoEntregado = new RecaudoEntregado();
	RecaudoEntregadoPK recaudoEntregadoPK = new RecaudoEntregadoPK();
	EstudianteSancionado estudianteSancionado = new EstudianteSancionado();
	MensajesAlUsuario mensajeAlUsuario = new MensajesAlUsuario();



	// Metodos setteres y getteres
	public String getLabelAsignaturaLapsosConsecutivos() {
		return labelAsignaturaLapsosConsecutivos;
	}

	public void setLabelAsignaturaLapsosConsecutivos(
			String labelAsignaturaLapsosConsecutivos) {
		this.labelAsignaturaLapsosConsecutivos = labelAsignaturaLapsosConsecutivos;
	}

	public List<RecaudoEntregado> getListaRecaudo() {
		return listaRecaudo;
	}

	public void setListaRecaudo(List<RecaudoEntregado> listaRecaudo) {
		this.listaRecaudo = listaRecaudo;
	}

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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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

	public String getFechaApelacion() {
		return fechaApelacion;
	}

	public void setFechaApelacion(String fechaApelacion) {
		this.fechaApelacion = fechaApelacion;
	}

	public SolicitudApelacion getSancionadoSeleccionado() {
		return sancionadoSeleccionado;
	}

	public void setSancionadoSeleccionado(
			SolicitudApelacion sancionadoSeleccionado) {
		this.sancionadoSeleccionado = sancionadoSeleccionado;
	}

	public String getAsignaturaLapsosConsecutivos() {
		return asignaturaLapsosConsecutivos;
	}

	public void setAsignaturaLapsosConsecutivos(
			String asignaturaLapsosConsecutivos) {
		this.asignaturaLapsosConsecutivos = asignaturaLapsosConsecutivos;
	}

	public String getObservacionexperto() {
		return observacionexperto;
	}

	public void setObservacionexperto(String observacionexperto) {
		this.observacionexperto = observacionexperto;
	}

	public boolean isMostrarButtonObservacionAnterior() {
		return mostrarButtonObservacionAnterior;
	}

	public void setMostrarButtonObservacionAnterior(
			boolean mostrarButtonObservacionAnterior) {
		this.mostrarButtonObservacionAnterior = mostrarButtonObservacionAnterior;
	}

	// Fin de los metodos setters y getters

	// Metodo que inicializa el codigo del VM
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("sancionadoSeleccionado") SolicitudApelacion v1) {
		Selectors.wireComponents(view, this, false);
		this.sancionadoSeleccionado = v1;
		cedula = sancionadoSeleccionado.getId().getCedulaEstudiante();
		lapso = sancionadoSeleccionado.getEstudianteSancionado()
				.getLapsoAcademico().getCodigoLapso();
		sancion = sancionadoSeleccionado.getEstudianteSancionado()
				.getSancionMaestro().getNombreSancion();
		lapsosConsecutivos = sancionadoSeleccionado.getEstudianteSancionado()
				.getLapsosAcademicosRp();
		caso = sancionadoSeleccionado.getNumeroCaso();
		
		this.observacion = v1.getObservacion();

		buscarRecaudosEntregados(cedula);

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

		SolicitudApelacionPK solicitudApelacionPK2 = new SolicitudApelacionPK();
		solicitudApelacionPK2.setCedulaEstudiante(cedula);
		solicitudApelacionPK2.setCodigoLapso(lapso);
		solicitudApelacionPK2.setIdInstanciaApelada(2);
		Date fechaSA = serviciosolicitudapelacion.buscarSolicitudPorID(
				solicitudApelacionPK2).getFechaSolicitud();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		String fecha = sdf.format(fechaSA);
		this.fechaApelacion = fecha;

		// para lo del Button Analisis anterior

		for (RecaudoEntregado recaudoEntregado : buscarRecaudosEntregados(cedula)) {
			if (recaudoEntregado.getObservacionExperto() == null) {
				this.setMostrarButtonObservacionAnterior(false);
			} else {
				this.setMostrarButtonObservacionAnterior(true);
				break;
			}
		}

	}

	/**
	 * Buscar Recaudos Entregados
	 * 
	 * @return el Listado de recaudos buscado de la lista
	 * @parameters cedula
	 * @throws No
	 *             dispara ninguna excepcion.
	 */
	@Command
	@NotifyChange({ "listaRecaudo" })
	public List<RecaudoEntregado> buscarRecaudosEntregados(String cedula) {
		listaRecaudo = serviciorecaudoentregado
				.buscarRecaudosEntregadosAnalizarValidezII(cedula);
		System.out.println("CEDULA" + cedula);
		System.out.println(listaRecaudosPorMotivo);
		return listaRecaudo;
	}

	/**
	 * Actualiza los RecaudosEntregados
	 * 
	 * @return No devuelve ningun valor.
	 * @parameters el objeto EstadoApelacion
	 * @throws No
	 *             dispara ninguna excepcion.
	 */
	@Command
	@NotifyChange({ "cedula", "nombres", "apellidos", "estudianteSancionado",
			"lapso", "observacionexperto", "observacion" })
	// el notifychange le avisa a que parametros en la pantalla se van a
	// cambiar, en este caso es cedula, nombre, apellidos,
	// estudianteSancionado,lapso,observacionexperto, observacion
	// al guardar
	public void actualizarRecaudosEntregados(
			@BindingParam("recaudosEntregados") List<Listitem> recaudos,
			@BindingParam("window") Window winAnalizarValidezII) {

		if (observacion == null) {
			mensajeAlUsuario.advertenciaAgregarObservacionGeneral();
		} else {
			ApelacionEstadoApelacion apelacionEstadoApelacion = new ApelacionEstadoApelacion();
			if (getSelected().equals("PROCEDENTE"))
				apelacionEstadoApelacion.setSugerencia("PROCEDENTE");
			else if ((getSelected().equals("NO PROCEDENTE")))
				apelacionEstadoApelacion.setSugerencia("NO PROCEDENTE");

			SolicitudApelacionPK solicitudApelacionPK = new SolicitudApelacionPK();
			solicitudApelacionPK.setCedulaEstudiante(cedula);
			solicitudApelacionPK.setCodigoLapso(lapso);
			solicitudApelacionPK.setIdInstanciaApelada(2);
			SolicitudApelacion solicitudApelacion = new SolicitudApelacion();
			solicitudApelacion = serviciosolicitudapelacion
					.buscarSolicitudPorID(solicitudApelacionPK);

			Recaudo recaudo = new Recaudo();
			for (Listitem miRecaudo : recaudos) {
				String nombreRecaudo = ((Listcell) miRecaudo.getChildren().get(
						1)).getLabel();
				String observacionExperto = ((Textbox) (miRecaudo.getChildren()
						.get(2)).getFirstChild()).getValue();
				if (observacionExperto.equals(""))
					observacionExperto = null;
				recaudo = serviciorecaudo.buscarRecaudoPorNombre(nombreRecaudo);
				RecaudoEntregadoPK recaudoEntregadoPK = new RecaudoEntregadoPK();
				recaudoEntregadoPK.setIdInstanciaApelada(2);
				recaudoEntregadoPK.setCedulaEstudiante(cedula);
				recaudoEntregadoPK.setIdTipoMotivo(recaudo.getTipoMotivo()
						.getIdTipoMotivo());
				recaudoEntregadoPK.setCodigoLapso(lapso);
				recaudoEntregadoPK.setIdRecaudo(recaudo.getIdRecaudo());
				RecaudoEntregado recaudoEntregadoAux = new RecaudoEntregado();
				recaudoEntregadoAux.setId(recaudoEntregadoPK);
				recaudoEntregadoAux.setObservacionExperto(observacionExperto);
				recaudoEntregadoAux.setEstatus(true);
				MotivoPK motivoPK = new MotivoPK();
				motivoPK.setCedulaEstudiante(cedula);
				motivoPK.setIdTipoMotivo(recaudo.getTipoMotivo()
						.getIdTipoMotivo());
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
			solicitudApelacionAux.setObservacion(observacion);
			solicitudApelacionAux.setVerificado(true);
			solicitudApelacionAux.setAnalizado(true);

			if (getSelected().equals("PROCEDENTE"))
				solicitudApelacionAux.setVeredicto("PROCEDENTE");
			else if ((getSelected().equals("NO PROCEDENTE")))
				solicitudApelacionAux.setVeredicto("NO PROCEDENTE");

			ApelacionEstadoApelacionPK apelacionEstadoApelacionPK = new ApelacionEstadoApelacionPK();
			apelacionEstadoApelacionPK.setCedulaEstudiante(cedula);
			apelacionEstadoApelacionPK.setCodigoLapso(lapso);
			apelacionEstadoApelacionPK.setIdEstadoApelacion(7);
			apelacionEstadoApelacionPK.setIdInstanciaApelada(2);
			apelacionEstadoApelacion.setId(apelacionEstadoApelacionPK);
			apelacionEstadoApelacion.setFechaEstado(new Date());
			apelacionEstadoApelacion.setObservacion(observacion);
			solicitudApelacionAux
					.addApelacionEstadosApelacion(apelacionEstadoApelacion);
			serviciosolicitudapelacion.guardar(solicitudApelacionAux);

			try {
				MensajesAlUsuario.informacionRegistroCorrectoStatic();
				winAnalizarValidezII.detach();
				actualizarListaSancionados();

			} catch (Exception e) {
				System.out.println(e.getMessage());

			}
		}
	}

	/**
	 * Metodo que limpia todos los campos
	 * 
	 * @parameters Observacion,selected, observacionexperto
	 * @throws No
	 *             dispara ninguna excepcion.
	 */
	@Command
	@NotifyChange({ "observacion", "selected", "observacionexperto" })
	public void limpiar() {
		observacion = "";
		selected = "";
		listaRecaudo = serviciorecaudoentregado
				.buscarRecaudosEntregadosAnalizarValidezII(cedula);

	}
	@GlobalCommand
    public void actualizarListaSancionados(){
    	BindUtils.postGlobalCommand(null, null, "buscarSancionados", null);
    }
	/**
	 * Metodo que ;Muestra el Historial de Observaciones
	 * 
	 * @parameters cedula, sancioando seleccionado
	 * @throws No
	 *             dispara ninguna excepcion.
	 */
	@Command
	public void mostrarHistorial() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("cedula", this.sancionadoSeleccionado.getEstudianteSancionado()
				.getEstudiante().getCedulaEstudiante());

		final Window window = (Window) Executions
				.createComponents(
						"/WEB-INF/sigarep/vistas/transacciones/HistorialObservacionAnalizarRecaudos.zul",
						null, map);
		window.setMaximizable(true);
		window.doModal();
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
	@NotifyChange({ "cedula", "nombres", "apellidos", "estudianteSancionado",
			"lapso", "observacionExperto", "observacion" })
		public void cerrarVentana(@BindingParam("ventana") final Window ventana){
			boolean condicion = false;
			if(observacion != null || selected != null
					|| observacionexperto != null)
				condicion = true;
			mensajeAlUsuario.confirmacionCerrarVentanaTransacciones(ventana,condicion);		
		}
		
}