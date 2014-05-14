package sigarep.viewmodels.transacciones;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Window;
import sigarep.herramientas.MensajesAlUsuario;
import sigarep.modelos.data.maestros.Reglamento;
import sigarep.modelos.data.transacciones.Cronograma;
import sigarep.modelos.servicio.maestros.ServicioContactoSigarep;
import sigarep.modelos.servicio.maestros.ServicioLapsoAcademico;
import sigarep.modelos.servicio.maestros.ServicioReglamento;
import sigarep.modelos.servicio.transacciones.ServicioCronograma;
import sigarep.modelos.servicio.transacciones.ServicioEstudianteSancionado;
import sigarep.modelos.servicio.transacciones.ServicioSolicitudApelacion;

/**
 * VM Portal Principal. Controla el portal principal del sistema.
 * Maneja los m�todos necesarios para la gesti�n de la
 * informaci�n que es presentada en el portal principal.
 * @author Equipo Builder
 * @version 1.2
 * @since 10/12/2013
 * @last 10/05/2014
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMPortalPrincipal {
	// --------------------------Servicios------------------------------
	@WireVariable
	private ServicioReglamento servicioreglamento;
	@WireVariable
	private ServicioEstudianteSancionado servicioestudiantesancionado;
	@WireVariable
	private ServicioCronograma serviciocronograma;
	@WireVariable
	private ServicioSolicitudApelacion serviciosolicitudapelacion;
	@WireVariable
	private ServicioLapsoAcademico serviciolapsoacademico;
	@WireVariable
	private ServicioContactoSigarep serviciocontactosigarep;
	// --------------------------Variables de Control-------------------
	private String cedula;
	private String nombreActividad;
	private String descripcionActividad;
	private Date fecha_inicio;
	private Date fecha_fin;
	private Time hora_inicio;
	private String observacionCronograma;
	private String lugarActividad;
	Window win = null;
	// --------------------------Variables Lista------------------------
	private List<Cronograma> listaCronograma = new LinkedList<Cronograma>();
	private List<Reglamento> listaReglamento = new LinkedList<Reglamento>();
	// --------------------------Variables Objeto-----------------------
	private Cronograma cronograma;
	private MensajesAlUsuario mensajeAlUsuario = new MensajesAlUsuario();

	// M�todos Set y Get
	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}

	public String getDescripcionActividad() {
		return descripcionActividad;
	}

	public void setDescripcionActividad(String descripcionActividad) {
		this.descripcionActividad = descripcionActividad;
	}

	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public Date getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public Time getHora_inicio() {
		return hora_inicio;
	}

	public void setHora_inicio(Time hora_inicio) {
		this.hora_inicio = hora_inicio;
	}

	public String getObservacionCronograma() {
		return observacionCronograma;
	}

	public void setObservacionCronograma(String observacionCronograma) {
		this.observacionCronograma = observacionCronograma;
	}

	public String getLugarActividad() {
		return lugarActividad;
	}

	public void setLugarActividad(String lugarActividad) {
		this.lugarActividad = lugarActividad;
	}

	public Cronograma getCronograma() {
		return cronograma;
	}

	public void setCronograma(Cronograma cronograma) {
		this.cronograma = cronograma;
	}

	public List<Cronograma> getListaCronograma() {
		return listaCronograma;
	}

	public void setListaCronograma(List<Cronograma> listaCronograma) {
		this.listaCronograma = listaCronograma;
	}

	// Fin M�todos Set y Get

	/**
	 * inicializaci�n
	 * 
	 * @param init
	 * @return C�digo de inicializaci�n
	 * @throws No
	 *             dispara ninguna excepcion.
	 */
	@Init
	public void init() {
		if (isMobile() == true) {
			// Si es mobile redirecciona a la pagina m.mobile.zul
			Executions.sendRedirect("m.mobileSigarep.zul");
		}
	}

	/**
	 * isMobile()
	 * 
	 * @param Ninguno
	 * @return true o false si es mobile
	 * @throws No
	 *             dispara ninguna excepcion.
	 */
	public boolean isMobile() {
		return Executions.getCurrent().getBrowser("mobile") != null;
	}

	/**
	 * Modal Estado Estudiante.
	 * 
	 * @param Ninguno
	 * @return Muestra la ventana con el historial del estudiante sancionado.
	 * @throws Debe
	 *             agregar una c�dula y esta debe estar en la lista de
	 *             estudiantes sancionados.
	 */
	@Command
	public void modalEstadoEstudiante() {
		if (cedula == "" || cedula == null) {
			mensajeAlUsuario.advertenciaIngresarCedula();
		} else {
			if (servicioestudiantesancionado
					.buscarEstudianteSancionadoLapsoActual(cedula) == null) {
				mensajeAlUsuario.advertenciaNoExisteEstudianteSancionado();
			} else {
				if (serviciosolicitudapelacion
						.buscarSolicitudEstudiante(cedula).isEmpty()) {
					mensajeAlUsuario
							.informacionEstudianteSinSolicitudApelacion();
				}
				final HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("cedula", this.cedula);
				Executions
						.createComponents(
								"WEB-INF/sigarep/vistas/portal/externo/modales/HistorialEstudiante.zul",
								null, map);
			}
		}
	}

	/**
	 * Modal Preguntas Frecuentes.
	 * 
	 * @param Ninguno
	 * @return Muestra la ventana con las preguntas frecuentes
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	@Command
	public void modalPreguntasFrecuentes() {
		if (win != null) {
			win.detach();
		}
		win = (Window) Executions
				.createComponents(
						"WEB-INF/sigarep/vistas/portal/externo/modales/Preguntas_Frecuentes.zul",
						null, null);
		win.setMaximizable(true);
		win.doModal();
	}

	/**
	 * Descargar Guia.
	 * 
	 * @param Ninguno
	 * @return Descarga la Gu�a paso a paso.
	 * @throws Debe
	 *             haber un documento cargado.
	 * 
	 */
	@Command
	public void descargarGuia() {
		listaReglamento = servicioreglamento.buscarGuia();
		if (listaReglamento.size() > 0) {
			Reglamento guia = servicioreglamento.buscarGuia().get(0);
			if (guia != null) {
				Filedownload.save(guia.getDocumento().getContenidoDocumento(),
						guia.getDocumento().getTipoDocumento(), guia
								.getDocumento().getNombreDocumento());
			} else {
				mensajeAlUsuario.advertenciaDocumentoNOdisponible();
			}
		} else {
			mensajeAlUsuario.advertenciaDocumentoNOdisponible();
		}
	}

	/**
	 * Modal Cont�ctanos.
	 * 
	 * @param Ninguno
	 * @return Muestra la ventana cont�ctanos.
	 * @throws No
	 *             dispara ninguna excepci�n.
	 * 
	 */
	@Command
	public void modalContactanos() {
		if (win != null) {
			win.detach();
		}
		win = (Window) Executions
				.createComponents(
						"WEB-INF/sigarep/vistas/portal/externo/modales/Contactanos.zul",
						null, null);
		win.setMaximizable(true);
		win.doModal();
	}

	/**
	 * Modal Quienes Somos.
	 * 
	 * @param Ninguno
	 * @return Muestra la ventana qui�nes somos.
	 * @throws No
	 *             dispara ninguna excepci�n.
	 * 
	 */
	@Command
	public void modalQuienesSomos() {
		if (win != null) {
			win.detach();
		}
		win = (Window) Executions
				.createComponents(
						"WEB-INF/sigarep/vistas/portal/externo/modales/QuienesSomos.zul",
						null, null);
		win.setMaximizable(true);
		win.doModal();
	}

}//fin VMPortalPrincipal
