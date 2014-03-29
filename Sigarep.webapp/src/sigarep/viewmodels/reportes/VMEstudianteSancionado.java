package sigarep.viewmodels.reportes;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import org.springframework.web.servlet.view.jasperreports.JasperReportsXlsView;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import sigarep.herramientas.MensajesAlUsuario;
import sigarep.modelos.data.maestros.Asignatura;
import sigarep.modelos.data.maestros.EstadoApelacion;
import sigarep.modelos.data.maestros.LapsoAcademico;
import sigarep.modelos.data.maestros.InstanciaApelada;
import sigarep.modelos.data.maestros.ProgramaAcademico;
import sigarep.modelos.data.maestros.SancionMaestro;
import sigarep.modelos.data.maestros.TipoMotivo;
import sigarep.modelos.data.reportes.EstudianteSancionado;
import sigarep.modelos.data.reportes.ReportConfig;
import sigarep.modelos.data.reportes.ReportType;
import sigarep.modelos.servicio.maestros.ServicioAsignatura;
import sigarep.modelos.servicio.maestros.ServicioEstadoApelacion;
import sigarep.modelos.servicio.maestros.ServicioLapsoAcademico;
import sigarep.modelos.servicio.maestros.ServicioInstanciaApelada;
import sigarep.modelos.servicio.maestros.ServicioProgramaAcademico;
import sigarep.modelos.servicio.maestros.ServicioSancionMaestro;
import sigarep.modelos.servicio.maestros.ServicioTipoMotivo;
import sigarep.modelos.servicio.reportes.ServicioReporteEstudianteSancionado;
/**
 * VM Reporte Estudiante Sancionado UCLA DCYT Sistemas de Informacion.
 * 
 * @author Equipo : Builder-Sigarep Lapso 2013-2
 * @version 2.0.3
 */@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMEstudianteSancionado {
	String ruta = "/WEB-INF/sigarepReportes/configurable/RpEstudiantesSancionadosConfigurable.jasper";
	//String ruta = "/WEB-INF/sigarepReportes/configurable/RpEstudiantesSancionadosConfigurable.jasper";
	// ***********************************DECLARACION DE LAS VARIABLES SERVICIOS*************************
	@WireVariable
	private ServicioTipoMotivo serviciotipomotivo;
	@WireVariable
	private ServicioProgramaAcademico servicioprogramaacademico;
	@WireVariable
	private ServicioLapsoAcademico serviciolapsoacademico;
	@WireVariable
	private ServicioSancionMaestro serviciosancionmaestro;
	@WireVariable
	private ServicioReporteEstudianteSancionado servicioreporteestudiantesancionado;
	@WireVariable
	private ServicioInstanciaApelada servicioInstanciaApelada;
	@WireVariable
	private ServicioEstadoApelacion servicioestadoapelacion;
	@WireVariable
	private ServicioAsignatura servicioAsignatura;
	// ***********************************DECLARACION DE LISTAS*************************
	private List<ProgramaAcademico> listaPrograma;
	private List<TipoMotivo> listaTipoMotivo;
	private List<LapsoAcademico> listaLapsoAcademico;
	private List<SancionMaestro> listaSancion;
	private List<InstanciaApelada> listaInstanciaApelada;
	private List<EstadoApelacion> listaEdoApelacion;
	private List<Asignatura> listaAsignaturas;
	// ***********************************DECLARACION DE LAS VARIABLES TIPO OBJETO*************************
	private LapsoAcademico objLapso;
	private SancionMaestro objSancion,objCmbSancion;
	private ProgramaAcademico objprograma;
	private TipoMotivo objtipoMotivo;
	private InstanciaApelada objinstanciaApelada;
	private String objsexo;
	private String objVeredicto;
	private EstadoApelacion objEdoApelacion;
	private Asignatura asignaturas, objAsignatura;
	private List<EstudianteSancionado> listaE = new LinkedList<EstudianteSancionado>();
	// *********************************Parametros para la Tira Sql***************************************
	private String parametroLapsoAcademico;
	private String parametroTipoSancion;
	private String parametroInstanciaApelada;
	private String parametroMotivo;
	private String parametroProgramaAcademico;
	private String parametroSexo;
	private String parametroVeredicto;
	private String parametroEdoApelacion;
	private String parametroAsignatura;
	private String validarAsignatura;
	// *****************************************REPORTE******************************************
	ReportType reportType = null;
	ReportConfig reportConfig = null;
	private ListModelList<String> cmbSexo;// Lista para llenar el combo de sexo
	private ListModelList<String> cmbVeredicto;// Lista para llenar el combo Veredicto
	private ListModelList<String> cmbEdoApelacion;// Lista para llenar el combo Edo Apelacion
	MensajesAlUsuario mensajeAlUsuario = new MensajesAlUsuario();
	//Conectar con el Combo de Sanci�n para Habilitarlo y deshabilitarlo
	@Wire("#cmbAsignaturas")
	Combobox cmbAsignatura;
	// **************METODOS SET Y GET NECESARIOS PARA GENERAR REPORTE*****************
	public SancionMaestro getObjSancion() {
		return objSancion;
	}

	public void setObjSancion(SancionMaestro objSancion) {
		this.objSancion = objSancion;
	}

	public ProgramaAcademico getObjprograma() {
		return objprograma;
	}

	public void setObjprograma(ProgramaAcademico objprograma) {
		this.objprograma = objprograma;
	}

	public LapsoAcademico getObjLapso() {
		return objLapso;
	}

	public void setObjLapso(LapsoAcademico objLapso) {
		this.objLapso = objLapso;
	}

	public TipoMotivo getObjtipoMotivo() {
		return objtipoMotivo;
	}

	public void setObjtipoMotivo(TipoMotivo objtipoMotivo) {
		this.objtipoMotivo = objtipoMotivo;
	}

	public InstanciaApelada getObjinstanciaApelada() {
		return objinstanciaApelada;
	}

	public void setObjinstanciaApelada(InstanciaApelada objinstanciaApelada) {
		this.objinstanciaApelada = objinstanciaApelada;
	}

	public Asignatura getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(Asignatura asignaturas) {
		this.asignaturas = asignaturas;
	}

	public SancionMaestro getObjCmbSancion() {
		return objCmbSancion;
	}

	public void setObjCmbSancion(SancionMaestro objCmbSancion) {
		this.objCmbSancion = objCmbSancion;
	}

	public List<InstanciaApelada> getListaInstanciaApelada() {
		return listaInstanciaApelada;
	}

	public void setListaInstanciaApelada(
			List<InstanciaApelada> listaInstanciaApelada) {
		this.listaInstanciaApelada = listaInstanciaApelada;
	}

	public List<TipoMotivo> getListaTipoMotivo() {
		return listaTipoMotivo;
	}

	public void setListaTipoMotivo(List<TipoMotivo> ListaTipoMotivo) {
		this.listaTipoMotivo = ListaTipoMotivo;
	}

	public List<ProgramaAcademico> getListaPrograma() {
		return listaPrograma;
	}

	public void setListaPrograma(List<ProgramaAcademico> listaPrograma) {
		this.listaPrograma = listaPrograma;
	}
    
	public String getValidarAsignatura() {
		return validarAsignatura;
	}

	public void setValidarAsignatura(String validarAsignatura) {
		this.validarAsignatura = validarAsignatura;
	}

	public List<LapsoAcademico> getListaLapsoAcademico() {
		return listaLapsoAcademico;
	}

	public void setListaLapsoAcademico(List<LapsoAcademico> listaLapsoAcademico) {
		this.listaLapsoAcademico = listaLapsoAcademico;
	}

	public List<SancionMaestro> getListaSancion() {
		return listaSancion;
	}

	public void setListaSancion(List<SancionMaestro> listaSancion) {
		this.listaSancion = listaSancion;
	}

	public List<EstadoApelacion> getListaEdoApelacion() {
		return listaEdoApelacion;
	}

	public void setListaEdoApelacion(List<EstadoApelacion> listaEdoApelacion) {
		this.listaEdoApelacion = listaEdoApelacion;
	}

	public ServicioAsignatura getServicioAsignatura() {
		return servicioAsignatura;
	}

	public List<Asignatura> getListaAsignaturas() {
		return listaAsignaturas;
	}

	public void setListaAsignaturas(List<Asignatura> listaAsignaturas) {
		this.listaAsignaturas = listaAsignaturas;
	}

	public void setServicioAsignatura(ServicioAsignatura servicioAsignatura) {
		this.servicioAsignatura = servicioAsignatura;
	}

	public List<EstudianteSancionado> getListaE() {
		return listaE;
	}

	public void setListaE(List<EstudianteSancionado> listaE) {
		this.listaE = listaE;
	}
	public ListModelList<String> getCmbSexo() {
		cmbSexo.add("F");
		cmbSexo.add("M");
		cmbSexo.add("Todos");
		return cmbSexo;
	}
	public void setCmbSexo(ListModelList<String> cmbSexo) {
		this.cmbSexo = cmbSexo;
	}

	public String getParametroLapsoAcademico() {
		return parametroLapsoAcademico;
	}

	public void setParametroLapsoAcademico(String parametroLapsoAcademico) {
		this.parametroLapsoAcademico = parametroLapsoAcademico;
	}

	public String getParametroTipoSancion() {
		return parametroTipoSancion;
	}

	public void setParametroTipoSancion(String parametroTipoSancion) {
		this.parametroTipoSancion = parametroTipoSancion;
	}

	public String getParametroMotivo() {
		return parametroMotivo;
	}

	public void setParametroMotivo(String parametroMotivo) {
		this.parametroMotivo = parametroMotivo;
	}

	public String getParametroInstanciaApelada() {
		return parametroInstanciaApelada;
	}

	public void setParametroInstanciaApelada(String parametroInstanciaApelada) {
		this.parametroInstanciaApelada = parametroInstanciaApelada;
	}

	public String getParametroProgramaAcademico() {
		return parametroProgramaAcademico;
	}

	public void setParametroProgramaAcademico(String parametroProgramaAcademico) {
		this.parametroProgramaAcademico = parametroProgramaAcademico;
	}

	public String getParametroSexo() {
		return parametroSexo;
	}
	public void setParametroSexo(String parametroSexo) {
		this.parametroSexo = parametroSexo;
	}
	public String getObjsexo() {
		return objsexo;
	}
	public void setObjsexo(String objsexo) {
		this.objsexo = objsexo;
	}
	public String getParametroVeredicto() {
		return parametroVeredicto;
	}

	public void setParametroVeredicto(String parametroVeredicto) {
		this.parametroVeredicto = parametroVeredicto;
	}

	public String getObjVeredicto() {
		return objVeredicto;
	}
	public void setObjVeredicto(String objVeredicto) {
		this.objVeredicto = objVeredicto;
	}

	public ListModelList<String> getCmbVeredicto() {
		cmbVeredicto.add("Procedente");
		cmbVeredicto.add("No Procedente");
		cmbVeredicto.add("Todos");
		return cmbVeredicto;
	}

	public EstadoApelacion getObjEdoApelacion() {
		return objEdoApelacion;
	}

	public void setObjEdoApelacion(EstadoApelacion objEdoApelacion) {
		this.objEdoApelacion = objEdoApelacion;
	}

	public ListModelList<String> getCmbEdoApelacion() {
		return cmbEdoApelacion;
	}

	public void setCmbEdoApelacion(ListModelList<String> cmbEdoApelacion) {
		this.cmbEdoApelacion = cmbEdoApelacion;
	}

	public void setCmbVeredicto(ListModelList<String> cmbVeredicto) {
		this.cmbVeredicto = cmbVeredicto;
	}

	public String getParametroEdoApelacion() {
		return parametroEdoApelacion;
	}

	public void setParametroEdoApelacion(String parametroEdoApelacion) {
		this.parametroEdoApelacion = parametroEdoApelacion;
	}

	public String getParametroAsignatura() {
		return parametroAsignatura;
	}

	public void setParametroAsignatura(String parametroAsignatura) {
		this.parametroAsignatura = parametroAsignatura;
	}

	public Asignatura getObjAsignatura() {
		return objAsignatura;
	}

	public void setObjAsignatura(Asignatura objAsignatura) {
		this.objAsignatura = objAsignatura;
	}

	// Reporte SET/GETS
	public ReportType getReportType() {
		return reportType;
	}

	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}

	public ReportConfig getReportConfig() {
		return reportConfig;
	}

	public void setReportConfig(ReportConfig reportConfig) {
		this.reportConfig = reportConfig;
	}

	public ListModelList<ReportType> getReportTypesModel() {
		return reportTypesModel;
	}

	// ===============================FIN DE LOS METODOS SET Y GET==============================
	
	/**
	 * AfterCompose
	 * 
	 * @param @ContextParam(ContextType.VIEW) Component view
	 * @ Mediante este Metodo se Conectan cualquier de los Componentes de la Vista
	 * {@link http://books.zkoss.org/wiki/ZK_Developer's_Reference/MVVM/Advanced/Wire_Components}      
	 */
	@AfterCompose//************METODO QUE SIRVE PARA CONECTAR CUALQUIER COMPONENTE EN LA VISTA*******
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
	        Selectors.wireComponents(view, this, false);
	        Selectors.wireComponents(cmbAsignatura, this, false);//***SE CONECTO EL COMBO DE SANCION*** 
	}
	/**
	 * Inicializaci�n
	 * 
	 * @param init
	 * @return Carga de Variables y metodos inicializados
	 * @throws No
	 *             dispara ninguna excepcion.
	 */
	@Init
	public void init() {
		// initialization code
		buscarTipoMotivo();
		buscarProgramaA();
		buscarActivoLapso();
		listadoSancion();
		listadoInstancia();
		buscarEdoApelacion();
		cmbSexo = new ListModelList<String>();
		cmbVeredicto = new ListModelList<String>();
	}

	/**
	 * buscar Asignaturas
	 * 
	 * @param IdPrograma
	 * @return lista de de asignaturas, programas
	 */
	@Command
	@NotifyChange({ "listaAsignaturas", "objprograma" })
	public void buscarAsignaturas() {
		if (objprograma.getNombrePrograma() == "Todos") {
			listaAsignaturas = servicioAsignatura.listaAsignaturas();
		} else {
			listaAsignaturas = servicioAsignatura
					.buscarAsignaturasPorPrograma(objprograma);
		}
	}
	// REPORTE
	/**
	 * Muestra los tipo de  Extension que puede generar el Reporte
	 * 
	 * @param
	 * @return El Tipo de Formato del Archivo(RTF,XLS,JXL,CSV,ODT)
	 */
	private ListModelList<ReportType> reportTypesModel = new ListModelList<ReportType>(
			Arrays.asList(new ReportType("Reporte en Excel", "xls"), 
						  new ReportType("Excel (JXL)","jxl")));
	/**
	 * buscar estado de Apelaci�n
	 * 
	 * @param
	 * @return lista de estado de Apelaci�n
	 */
	@Command
	@NotifyChange({ "listaEdoApelacion" })
	public void buscarEdoApelacion() {
		listaEdoApelacion = servicioestadoapelacion.listadoEstadoApelacionActivas();
		EstadoApelacion edo_ape = new EstadoApelacion(null, "Todos", "Todos",null);
		listaEdoApelacion.add(listaEdoApelacion.size(), edo_ape);
	}
	/**
	 * Objeto Combo Estado Apelaci�n.
	 * 
	 * @param Ninguno
	 * @return Objeto Estado Apelaci�n
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	@Command
	@NotifyChange({ "listaEdoApelacion" })
	public EstadoApelacion objCmbEdoApelacion() {
		return objEdoApelacion;
	}
	/**
	 * buscar Tipo Motivo
	 * 
	 * @param
	 * @return lista de tipo motivo
	 */
	@Command
	@NotifyChange({ "listaTipoMotivo" })
	public void buscarTipoMotivo() {
		listaTipoMotivo = serviciotipomotivo.listadoTipoMotivo();
		TipoMotivo mot = new TipoMotivo(null, "Todos", null, "Todos", false);
		listaTipoMotivo.add(listaTipoMotivo.size()/* 0 */, mot);
	}
	/**
	 * Objeto Combo Tipo Motivo.
	 * 
	 * @param Ninguno
	 * @return Objeto Tipo Motivo
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	@Command
	@NotifyChange({ "listaTipoMotivo" })
	public TipoMotivo objCmbtipoMotivo() {
		return objtipoMotivo;
	}
	/**
	 * buscar Programa Acad�mico
	 * 
	 * @param
	 * @return lista de programa Acad�mico
	 */
	@Command
	@NotifyChange({ "listaPrograma" }) //*******CARGAR LA LISTA DE PROGRAMAS ACADEMICOS*******
	public void buscarProgramaA() {
		listaPrograma = servicioprogramaacademico.listadoProgramas();
		ProgramaAcademico prog = new ProgramaAcademico(null, "Todos", null);
		listaPrograma.add(listaPrograma.size(), prog);
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
	public ProgramaAcademico objCmbprograma() {
		return objprograma;
	}
	/**
	 * buscar Lapso Acad�mico
	 * 
	 * @param
	 * @return lista de lapso Acad�mico
	 */
	@Command
	@NotifyChange({ "listaLapsoAcademico" }) //*******CARGAR LA LISTA DE LAPSOS ACADEMICOS*******
	public void buscarActivoLapso() {
		listaLapsoAcademico = serviciolapsoacademico.buscarTodosLosLapsos();
		LapsoAcademico lap = new LapsoAcademico("Todos", null, null, null);
		listaLapsoAcademico.add(listaLapsoAcademico.size(), lap);
	}
	/**
	 * Objeto Combo Lapso.
	 * 
	 * @param Ninguno
	 * @return Objeto Lapso Acad�mico
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	@Command
	@NotifyChange({ "listaLapsoAcademico" })
	public LapsoAcademico objCmbLapso() {
		return objLapso;
	}
	/**
	 * buscar Sanci�n
	 * 
	 * @param
	 * @return lista de sanci�n
	 */
	@Command
	@NotifyChange({ "listaSancion" })//*******CARGAR LA LISTA DE TIPO DE SANCIONES*******
	public void listadoSancion() {
		listaSancion = serviciosancionmaestro.listaTipoSanciones();
		SancionMaestro san = new SancionMaestro(null, "Todos", null, "Todos");
		listaSancion.add(listaSancion.size(), san);
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
	@NotifyChange({ "listaSancion" })
	public SancionMaestro objCmbSancion() {
		return objSancion;
	}
	/**
	 * buscar Instancia
	 * 
	 * @param
	 * @return lista de instacias apeladas
	 */
	@Command
	@NotifyChange({ "listaInstanciaApelada" })//******CARGAR LA LISTA DE INSTANCIA****
	public void listadoInstancia() {
		listaInstanciaApelada = servicioInstanciaApelada.listadoInstanciaApelada();
		InstanciaApelada ins = new InstanciaApelada(null, "Todos", null,"Todos", null);
		listaInstanciaApelada.add(listaInstanciaApelada.size(), ins);
	}
	/**
	 * Objeto Combo Instancia.
	 * 
	 * @param Ninguno
	 * @return Objeto Instancia Apelada
	 * @throws No
	 *             dispara ninguna excepci�n.
	 */
	@Command
	@NotifyChange({ "listaInstanciaApelada" })
	public InstanciaApelada objCmbinstanciaApelada() {
		return objinstanciaApelada;
	}
	/**
	 * buscar Estudiante Sancionado
	 * buscarEstudianteSancionado()
	 * @param programa, objSancion, objtipoMotivo,objinstanciaApelada, objLapso, objVeredicto,objEdoApelacion, asignaturas, objsexo,reportType
	 * @return ListaE lista estudiantes Sancionados
	 *    
	 */
	@Command
	@NotifyChange({ "listaE","parametroAsignatura" })
	public void buscarEstudianteSancionado() {//*******BUSCAR ESTUDIANTE SANCIONADO******
		parametroAsignatura="";
		if (objinstanciaApelada == null || objLapso == null || objprograma == null || objSancion == null || objsexo == null
				|| objtipoMotivo == null || objVeredicto == null || objEdoApelacion == null ||  reportType== null) {
			mensajeAlUsuario.advertenciaSeleccionarTodo();
		} 
		else {
			configurarLapsoAcademico();
			configurarParametroSancion();
			configurarParametroInstanciaApelada();
			configurarParametroMotivo();
			configurarParametroProgramaAcademico();
			configurarParametroSexo();
			configurarParametroVeredicto();
			configurarParametroEdoApelacion();
			if(objSancion.getNombreSancion().equals("RR")){
				parametroAsignatura="";
				configurarParametroAsignatura();
			}
			listaE = servicioreporteestudiantesancionado.buscarTodosSancionado(parametroLapsoAcademico, parametroTipoSancion,parametroInstanciaApelada, 
					 parametroMotivo,parametroProgramaAcademico, parametroSexo,parametroVeredicto, parametroEdoApelacion,parametroAsignatura);
		}
	}
	/**
	 * Limpiar Estudiante sancionado.
	 * 
	 * @param programa, objSancion, objtipoMotivo,objinstanciaApelada, objLapso, objVeredicto,objEdoApelacion, asignaturas, objsexo
	 * @return Deja los Campos en NULL en cada uno de los combos de la vista
	 * @throws No
	 *             dispara ninguna excepcion.
	 */
	@Command
	@NotifyChange({ "objprograma", "objSancion", "objtipoMotivo","objinstanciaApelada", "objLapso", "objVeredicto","objEdoApelacion", "objAsignatura", "objsexo","reportType"})
	public void limpiarCombos() {
		objprograma = null;
		objSancion = null;
		objtipoMotivo = null;
		objinstanciaApelada = null;
		objLapso = null;
		objVeredicto = null;
		objEdoApelacion = null;
		objAsignatura = null;
		objsexo = null;
		reportType= null;
	}
	/**
	 * Configurar Lapso Academico
	 *  configurarLapsoAcademico()
	 * @param parametroLapsoAcademico
	 * @return parametroLapsoAcademico
	 *  
	 */
	@NotifyChange({ "parametroLapsoAcademico" })//********CONFIGURAR  LAPSO ACADEMICO********
	@Command
	public String configurarLapsoAcademico(){
		if (objLapso.getCodigoLapso() == "Todos") {
			parametroLapsoAcademico = "sap.codigo_lapso";
		} 
		else {
			parametroLapsoAcademico = "'" + objLapso.getCodigoLapso() + "'";
		}
		return parametroLapsoAcademico;
	}
	/**
	 * Configurar Parametro Sancion
	 * configurarParametroSancion()
	 * @param parametroTipoSancion
	 * @return parametroTipoSancion
	 *  
	 */
	@NotifyChange({ "parametroTipoSancion"})// ********CONFIGURAR TIPO SANCION*******
	@Command
	public String configurarParametroSancion() {
		if (objSancion.getNombreSancion() == "Todos") {
			parametroTipoSancion = "esa.id_sancion";
		} else {
			parametroTipoSancion = "'" + objSancion.getIdSancion() + "'";
		}
		return parametroTipoSancion;
	}
	/**
	 * Configurar Parametro Instancia Apelada
	 * configurarParametroInstanciaApelada() 
	 * @param parametroInstanciaApelada
	 * @return parametroInstanciaApelada
	 *  
	 */
	@NotifyChange({ "parametroInstanciaApelada" })// ******CONFIGURAR PARAMETRO INSTANCIA APELADA
	@Command
	public String configurarParametroInstanciaApelada() {
		if (objinstanciaApelada.getDescripcion() == "Todos") {
			parametroInstanciaApelada = "sap.id_instancia_apelada";
		} 
		else {
			parametroInstanciaApelada = "'"+objinstanciaApelada.getIdInstanciaApelada()+"'";
		}
		return parametroInstanciaApelada;
	}
	/**
	 * Configurar Parametro Motivo
	 * configurarParametroMotivo() 
	 * @param parametroMotivo
	 * @return parametroMotivo
	 *  
	 */
	@NotifyChange({ "parametroMotivo" })// *********CONFIGURAR TIPO MOTIVO************  
	@Command
	public String configurarParametroMotivo() {
		if (objtipoMotivo.getDescripcion() == "Todos") {
			parametroMotivo = "mot.id_tipo_motivo";
		} 
		else {
			parametroMotivo = "'" + objtipoMotivo.getIdTipoMotivo() + "'";
		}
		return parametroMotivo;
	}
	/**
	 * Configurar Parametro de Programa Academico
	 * configurarParametroProgramaAcademico()
	 * @param parametroProgramaAcademico
	 * @return parametroProgramaAcademico
	 *  
	 */
	@NotifyChange({ "parametroProgramaAcademico" })// ******CONFIGURAR PROGRAMA ACADEMICO********
	@Command
	public String configurarParametroProgramaAcademico() {
		if (objprograma.getNombrePrograma() == "Todos") {
			parametroProgramaAcademico = "es.id_programa";
		}
		else {
			parametroProgramaAcademico = "'" + objprograma.getIdPrograma()+ "'";
		}
		return parametroProgramaAcademico;
	}
	/**
	 * Configurar Parametro Sexo
	 * configurarParametroSexo()
	 * @param parametroSexo
	 * @return parametroSexo
	 *  
	 */
	@NotifyChange({ "parametroSexo" })// ******CONFIGURAR SEXO********
	@Command
	public String configurarParametroSexo() {
		if (objsexo.equals("Todos")) {
			parametroSexo = "es.sexo";
		} 
		else {
			parametroSexo = "'" + objsexo + "'";
		}
		return parametroSexo;
	}
	/**
	 * Configurar Parametro Veredicto
	 * configurarParametroVeredicto()
	 * @param parametroVeredicto
	 * @return parametroVeredicto
	 *  
	 */
	@NotifyChange({ "parametroVeredicto" })
	@Command
	public String configurarParametroVeredicto() {// ******CONFIGURAR VEREDICTO********
		if (objVeredicto.equals("Todos")) {
			parametroVeredicto = "sap.veredicto";
		} 
		else {
			parametroVeredicto = "'" + objVeredicto.toUpperCase() + "'";
		}
		return parametroVeredicto;
	}
	/**
	 * Configurar Parametro de Estado Apelacion
	 * configurarParametroEdoApelacion()
	 * @param parametroEdoApelacion
	 * @return parametroEdoApelacion
	 *  
	 */
	@NotifyChange({ "parametroEdoApelacion" })// ******CONFIGURAR EDO APELACION********
	@Command
	public String configurarParametroEdoApelacion() {
		if (objEdoApelacion.getNombreEstado().equals("Todos")) {
			parametroEdoApelacion ="ape_edo_ape.id_estado_apelacion";
		} 
		else {
			parametroEdoApelacion = "'"+objEdoApelacion.getIdEstadoApelacion()+"'";
		}
		return parametroEdoApelacion;
	}
	/**
	 * configurarParametroAsignatura
	 *
	 * @param parametroAsignatura
	 * @return parametroAsignatura
	 *  
	 */
	@NotifyChange({ "parametroAsignatura" })// ******CONFIGURAR ASIGNATURA********
	@Command
	public String configurarParametroAsignatura() {
		if (objAsignatura.getNombreAsignatura() == "Todos") {
			parametroAsignatura = "asig.codigo_asignatura";
		} 
		else {
			parametroAsignatura = "'" + objAsignatura.getCodigoAsignatura()+ "'";
		}
		return parametroAsignatura;
	}
	/**
	 * configurarComboAsignatura
	 *
	 * @param cmbAsignatura,objAsignatura,parametroAsignatura
	 * @return cmbAsignatura.setDisabled(true) o cmbAsignatura.setDisabled(false)
	 * @throws No
	 *  
	 */
	@Command                       //*********CONFIGURAR COMBO ASIGNATURA POR SANCION*******
	@NotifyChange({"cmbAsignatura","objAsignatura","parametroAsignatura"})
	public void configurarComboAsignatura(){
		if(!objSancion.getNombreSancion().equals("RP")){
			cmbAsignatura.setDisabled(false);
			objAsignatura= new Asignatura(null, true,"Todos", 3,null);
		}
		else{
			cmbAsignatura.setDisabled(true);
			parametroAsignatura="val";
		}
	}
	/**
	 * Generar ReporteEstudiantes Sancionados Configurable
	 * @category Reporte Ireport configuracion
	 * @param reportConfig 
	 * @return reportConfig actualizado con los Datos de La Lista(listaE)
	 * @throws JRException 
	 * @throws No
	 *  
	 */
	@NotifyChange({ "reportConfig" })
	@Command("GenerarReporteEstudiantesSancionadosConfigurable")// ********CONFIGURAR REPORTE**********
	public void GenerarReporteEstudiantesSancionadosConfigurable() throws JRException {
		if (listaE.size() > 0) {
			reportConfig = new ReportConfig(ruta);
			reportConfig.getParameters().put("ListaSancionados",new JRBeanCollectionDataSource(listaE));
			reportConfig.getParameters().put("Report name","Reporte");
			reportConfig.getParameters().put("jasperReport name","Reporte");
			reportConfig.setType(reportType);
			reportConfig.setDataSource(new JRBeanCollectionDataSource(listaE));
			JasperPrint jasperPrint = JasperFillManager.fillReport(Sessions.getCurrent().getWebApp().getRealPath(ruta),null, new JRBeanCollectionDataSource(listaE));
		        //view the report using JasperViewer
		    JasperViewer.viewReport(jasperPrint,false);
		} 
		else {
			mensajeAlUsuario.informacionNoHayCoincidencias();
		}
	
	}
 //FIN DE LA CLASE
}