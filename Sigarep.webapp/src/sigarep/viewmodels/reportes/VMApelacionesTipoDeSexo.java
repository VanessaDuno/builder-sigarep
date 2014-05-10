package sigarep.viewmodels.reportes;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.zkoss.bind.annotation.Command;

import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;

import sigarep.herramientas.MensajesAlUsuario;
import sigarep.modelos.data.maestros.LapsoAcademico;
import sigarep.modelos.data.maestros.ProgramaAcademico;
import sigarep.modelos.data.maestros.SancionMaestro;
import sigarep.modelos.data.maestros.TipoMotivo;
import sigarep.modelos.data.reportes.ApelacionesComparativos;

import sigarep.modelos.data.reportes.ReportConfig;
import sigarep.modelos.data.reportes.ReportType;
import sigarep.modelos.servicio.maestros.ServicioLapsoAcademico;
import sigarep.modelos.servicio.maestros.ServicioProgramaAcademico;
import sigarep.modelos.servicio.maestros.ServicioSancionMaestro;

import sigarep.modelos.servicio.reportes.ServicioReportesComparativos;

/**
 * VM Reporte Estad�stico de Apelaciones por Tipo de Sexo y Veredicto.
 * 
 * @author Equipo Builder
 * @version 2.5.2
 * @since 03/02/2014
 * @last 08/05/2014
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMApelacionesTipoDeSexo {
	// --------------------------Servicios------------------------------
	@WireVariable
	private ServicioProgramaAcademico servicioprogramaacademico;
	@WireVariable
	private ServicioSancionMaestro serviciosancionmaestro;
	@WireVariable
	private ServicioLapsoAcademico serviciolapsoacademico;
	@WireVariable
	private ServicioReportesComparativos servicioreportescomparativos;
	// --------------------------Variables de Control-------------------
	ReportType reportType = null;
	private ReportConfig reportConfig = null;
	String ruta = "/WEB-INF/sigarepReportes/estadisticos/RApelacionesTipoSexo-Veredicto.jasper";
	// --------------------------Variables lista------------------------
	private List<ProgramaAcademico> listaPrograma;
	private List<TipoMotivo> listaTipoMotivo;
	private List<SancionMaestro> listaTipoSancion;
	private List<LapsoAcademico> listaLapso;
	private List<ApelacionesComparativos> apelacionesComparativos = new LinkedList<ApelacionesComparativos>();
	// --------------------------Variables Objeto-----------------------
	private SancionMaestro objSancion;
	private LapsoAcademico objLapso;
	private ProgramaAcademico objPrograma;
	MensajesAlUsuario mensajeAlUsuario = new MensajesAlUsuario();

	// M�todos Set y Get
	public ListModelList<ReportType> getReportTypesModel() {
		return reportTypesModel;
	}

	public ReportConfig getReportConfig() {
		return reportConfig;
	}

	public ReportType getReportType() {
		return reportType;
	}

	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}

	public List<ProgramaAcademico> getListaPrograma() {
		return listaPrograma;
	}

	public void setListaPrograma(List<ProgramaAcademico> listaPrograma) {
		this.listaPrograma = listaPrograma;
	}

	public List<TipoMotivo> getListaTipoMotivo() {
		return listaTipoMotivo;
	}

	public void setListaTipoMotivo(List<TipoMotivo> listaTipoMotivo) {
		this.listaTipoMotivo = listaTipoMotivo;
	}

	public List<SancionMaestro> getListaTipoSancion() {
		return listaTipoSancion;
	}

	public void setListaTipoSancion(List<SancionMaestro> listaTipoSancion) {
		this.listaTipoSancion = listaTipoSancion;
	}

	public List<LapsoAcademico> getListaLapso() {
		return listaLapso;
	}

	public void setListaLapso(List<LapsoAcademico> listaLapso) {
		this.listaLapso = listaLapso;
	}

	public List<ApelacionesComparativos> getapelacionesComparativos() {
		return apelacionesComparativos;
	}

	public void setapelacionesPrograma(
			List<ApelacionesComparativos> apelacionesComparativos) {
		this.apelacionesComparativos = apelacionesComparativos;
	}

	public SancionMaestro getObjSancion() {
		return objSancion;
	}

	public void setObjSancion(SancionMaestro objSancion) {
		this.objSancion = objSancion;
	}

	public LapsoAcademico getObjLapso() {
		return objLapso;
	}

	public void setObjLapso(LapsoAcademico objLapso) {
		this.objLapso = objLapso;
	}

	public ProgramaAcademico getObjPrograma() {
		return objPrograma;
	}

	public void setObjPrograma(ProgramaAcademico objPrograma) {
		this.objPrograma = objPrograma;
	}

	// Fin M�todos Set y Get

	/**
	 * Inicializaci�n
	 * 
	 * @param init
	 * @return Carga de Variables y m�todos inicializados
	 * @throws No
	 *             dispara ninguna excepcion.
	 */
	@Init
	public void init() {
		buscarPrograma();
		buscarTipoSancion();
		buscarLapso();
	}

	/**
	 * ListModelList. Muestra los tipos de formatos que puede mostrarse el
	 * reporte.
	 * 
	 * @param Ninguno
	 * @return Tipos de formatos para el reporte.
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	private ListModelList<ReportType> reportTypesModel = new ListModelList<ReportType>(
			Arrays.asList(new ReportType("PDF", "pdf"), new ReportType(
					"Word (RTF)", "rtf"), new ReportType("Excel", "xls"),
					new ReportType("Excel (JXL)", "jxl"), new ReportType("CSV",
							"csv"), new ReportType("OpenOffice (ODT)", "odt")));

	/**
	 * Buscar programa acad�mico.
	 * 
	 * @param Ninguno
	 * @return Busca todos los registros.
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	@Command
	@NotifyChange({ "listaPrograma" })
	public void buscarPrograma() {
		listaPrograma = servicioprogramaacademico.listadoProgramas();
	}

	/**
	 * Buscar lapsos.
	 * 
	 * @param Ninguno
	 * @return Busca todos los registros.
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	@Command
	@NotifyChange({ "listaLapso" })
	public void buscarLapso() {
		listaLapso = serviciolapsoacademico.buscarTodosLosLapsos();
	}

	/**
	 * Buscar sanci�n.
	 * 
	 * @param Ninguno
	 * @return Busca todos los registros.
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	@Command
	@NotifyChange({ "listaTipoSancion" })
	public void buscarTipoSancion() {
		listaTipoSancion = serviciosancionmaestro.listaTipoSanciones();
		SancionMaestro sanc = new SancionMaestro(null, null, null, "Todos");
		listaTipoSancion.add(0, sanc);
	}

	/**
	 * Generar Reporte Estad�stico Comparativo de Apelaciones por Tipo de Sexo y
	 * Veredicto.
	 * 
	 * @param Ninguno
	 * @return Reporte Estad�stico Comparativo de Apelaciones por Tipo de Sexo y
	 *         Veredicto generado en PDF u otro tipo de archivo
	 * @throws Si
	 *             la lista est� vac�a no genera el reporte.
	 */
	@Command("GenerarReporteApelacionesMotivo")
	@NotifyChange({ "reportConfig" })
	public void GenerarReporte() {
		apelacionesComparativos.clear();
		ProgramaAcademico prog = objPrograma;
		LapsoAcademico lap = objLapso;
		if (objSancion == null || objLapso == null || objSancion == null
				|| objPrograma == null || reportType == null) {
			mensajeAlUsuario.advertenciaSeleccionarTodo();
		} else {
			if (objSancion.getNombreSancion() == "Todos") {
				apelacionesComparativos = servicioreportescomparativos
						.buscarPorSexoResultado_Programa(
								objLapso.getCodigoLapso(),
								objPrograma.getIdPrograma());
			} else {
				apelacionesComparativos = servicioreportescomparativos
						.buscarPorSexoResultado_ProgramaSancion(
								objLapso.getCodigoLapso(),
								objSancion.getIdSancion(),
								objPrograma.getIdPrograma());
			}
			if (apelacionesComparativos.size() > 0) {
				reportConfig = new ReportConfig(ruta);
				reportConfig
						.getParameters()
						.put("Titulo",
								"Reporte Comparativo de Apelaciones por Sexo y Veredicto");
				reportConfig.getParameters().put("Lapso", lap.getCodigoLapso());
				reportConfig.getParameters().put("Programa",
						prog.getNombrePrograma().toUpperCase());
				reportConfig.getParameters()
						.put("Lista",
								new JRBeanCollectionDataSource(
										apelacionesComparativos));
				reportConfig.setType(reportType);
				reportConfig.setDataSource(new JRBeanCollectionDataSource(
						apelacionesComparativos));
			} else {
				mensajeAlUsuario.informacionNoHayCoincidencias();
			}
		}
	}

	/**
	 * Limpiar. Inicializa los combos.
	 * 
	 * @param Ninguno
	 * @return Ninguno.
	 * @throws No
	 *             dispara ninguna exepci�n.
	 */
	@Command
	@NotifyChange({ "objLapso", "objSancion", "objPrograma", "reportType" })
	public void limpiar() {
		objLapso = null;
		objSancion = null;
		objPrograma = null;
		reportType = null;
	}

	/**
	 * Objeto Combo Programa.
	 * 
	 * @param Ninguno
	 * @return Objeto Programa Acad�mico
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	@Command
	@NotifyChange({ "listaPrograma" })
	public ProgramaAcademico objCmbPrograma() {
		return objPrograma;
	}

	/**
	 * Objeto Combo Sanci�n.
	 * 
	 * @param Ninguno
	 * @return Objeto Sanci�n
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	@Command
	@NotifyChange({ "listaTipoSancion" })
	public SancionMaestro objCmbSancion() {
		return objSancion;
	}

	/**
	 * Objeto lapso.
	 * 
	 * @param Ninguno
	 * @return Objeto Lapso
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	@Command
	@NotifyChange({ "listaLapso" })
	public LapsoAcademico objCmbLapso() {
		return objLapso;
	}

}
