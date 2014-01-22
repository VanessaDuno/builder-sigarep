package sigarep.viewmodels.transacciones;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import sigarep.herramientas.Archivo;
import sigarep.herramientas.Documento;
import sigarep.herramientas.MensajesAlUsuario;
import net.sf.jasperreports.engine.JRException;

import org.jfree.text.TextBox;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import org.zkoss.zul.Label;
import org.zkoss.zul.Window;
import sigarep.modelos.data.maestros.*;

import sigarep.herramientas.mensajes;
import sigarep.modelos.data.maestros.LapsoAcademico;
import sigarep.modelos.data.maestros.EstadoApelacion;

import sigarep.modelos.data.maestros.TipoMotivo;
import sigarep.modelos.data.transacciones.ApelacionEstadoApelacion;
import sigarep.modelos.data.transacciones.ApelacionEstadoApelacionPK;

import sigarep.modelos.data.transacciones.AsignaturaEstudianteSancionado;
import sigarep.modelos.data.transacciones.Motivo;
import sigarep.modelos.data.transacciones.MotivoPK;
import sigarep.modelos.data.transacciones.RecaudoEntregado;
import sigarep.modelos.data.transacciones.RecaudoEntregadoPK;
import sigarep.modelos.data.transacciones.SolicitudApelacion;
import sigarep.modelos.data.transacciones.SolicitudApelacionPK;
import sigarep.modelos.data.transacciones.Soporte;
import sigarep.modelos.servicio.maestros.ServicioLapsoAcademico;
import sigarep.modelos.servicio.maestros.ServicioEstadoApelacion;
import sigarep.modelos.servicio.transacciones.ListaApelacionEstadoApelacion;
import sigarep.modelos.servicio.transacciones.ListaRecaudosMotivoEstudiante;
import sigarep.modelos.servicio.transacciones.ServicioApelacion;
import sigarep.modelos.servicio.transacciones.ServicioApelacionEstadoApelacion;
import sigarep.modelos.servicio.transacciones.ServicioAsignaturaEstudianteSancionado;
import sigarep.modelos.servicio.transacciones.ServicioMotivo;
import sigarep.modelos.servicio.transacciones.ServicioMotivos;
import sigarep.modelos.servicio.transacciones.ServicioRecaudoEntregado;
import sigarep.modelos.servicio.transacciones.ServicioSolicitudApelacion;
import sigarep.modelos.servicio.transacciones.ServicioSoporte;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMRegistrarReconsideracion {
	@Wire("#modalDialog")
	private Window window;
	private String sancion;
	private String programa;
	private String telefono;
	private String email;
	private String apellido;
	private String nombre;
	private String lapso;
	private int instancia;
	private String motivo;
	private String recaudo;
	private String segundoNombre;
	private String segundoApellido;
	private String nombres;
	private String apellidos;
	private String asignatura;
	private Integer caso;


	private String nombreRecaudo;
	private String nombreTipoMotivo;
	private String nombreDocumento;
	private Byte[] contenidoDocumento;
	private String tipoDocumento;
	private Integer idTipoMotivo;
	private Integer idRecaudo;
	private List<TipoMotivo> listaTipoMotivo;
	private List<Recaudo> listaRecaudo;
	@WireVariable
	private ServicioApelacion serviciolista;
	private Documento doc = new Documento();
	private Media media;
	private AImage imagen;
	private String nombreDoc;
	private String cedula;
	private List<AsignaturaEstudianteSancionado> asignaturas;
	private String lapsosConsecutivos;
	private String asignaturaLapsosConsecutivos="";
	private String labelAsignaturaLapsosConsecutivos;
	
	private SolicitudApelacion solicitudapelacion;
	private LapsoAcademico lapsoAcademico = new LapsoAcademico();
	@WireVariable
	private ServicioLapsoAcademico serviciolapsoacademico;
	@WireVariable
	private ServicioSolicitudApelacion serviciosolicitudapelacion;
	@WireVariable
	private ServicioRecaudoEntregado serviciorecaudoentregado;
	@WireVariable
	private ServicioApelacionEstadoApelacion servicioapelacionestadoapelacion;
	@WireVariable
	private ServicioMotivos serviciomotivos;
	@WireVariable
	private ServicioSoporte serviciosoporte;
	@WireVariable
	private ServicioEstadoApelacion servicioestadoapelacion;
	@WireVariable
	private ServicioAsignaturaEstudianteSancionado servicioasignaturaestudiantesancionado;
	private Integer instanciaApelada;
	private Date fechaSolicitud;

	MensajesAlUsuario mensajesusuario = new MensajesAlUsuario(); // para llamar a los diferentes mensajes de
									// dialogo
	SolicitudApelacionPK solicitudApelacionPK = new SolicitudApelacionPK();
	SolicitudApelacion solicitudApelacion = new SolicitudApelacion();
	ApelacionEstadoApelacionPK apelacionEstadoApelacionPK = new ApelacionEstadoApelacionPK();
	ApelacionEstadoApelacion apelacionEstadoApelacion = new ApelacionEstadoApelacion();
	RecaudoEntregado recaudoEntregado = new RecaudoEntregado();
	RecaudoEntregadoPK recaudoEntregadoPK = new RecaudoEntregadoPK();
	Soporte soporte = new Soporte();
	Motivo motivos = new Motivo();
	MotivoPK motivoPK = new MotivoPK();
	EstadoApelacion estadoApelacion = new EstadoApelacion();
	Recaudo recaudos = new Recaudo();
	
	private List<RecaudoEntregado> listaRecaudos = new LinkedList<RecaudoEntregado>();
	private List<ListaApelacionEstadoApelacion> lista = new LinkedList<ListaApelacionEstadoApelacion>();

	
	public String getLapsosConsecutivos() {
		return lapsosConsecutivos;
	}

	public void setLapsosConsecutivos(String lapsosConsecutivos) {
		this.lapsosConsecutivos = lapsosConsecutivos;
	}

	public String getAsignaturaLapsosConsecutivos() {
		return asignaturaLapsosConsecutivos;
	}

	public void setAsignaturaLapsosConsecutivos(String asignaturaLapsosConsecutivos) {
		this.asignaturaLapsosConsecutivos = asignaturaLapsosConsecutivos;
	}

	public String getLabelAsignaturaLapsosConsecutivos() {
		return labelAsignaturaLapsosConsecutivos;
	}

	public void setLabelAsignaturaLapsosConsecutivos(
			String labelAsignaturaLapsosConsecutivos) {
		this.labelAsignaturaLapsosConsecutivos = labelAsignaturaLapsosConsecutivos;
	}

	public List<RecaudoEntregado> getListaRecaudos() {
		return listaRecaudos;
	}

	public void setListaRecaudos(List<RecaudoEntregado> listaRecaudos) {
		this.listaRecaudos = listaRecaudos;
	}

	public List<ListaApelacionEstadoApelacion> getLista() {
		return lista;
	}

	public void setLista(List<ListaApelacionEstadoApelacion> lista) {
		this.lista = lista;
	}

	public Integer getIdTipoMotivo() {
		return idTipoMotivo;
	}

	public void setIdTipoMotivo(Integer idTipoMotivo) {
		this.idTipoMotivo = idTipoMotivo;
	}

	public Integer getIdRecaudo() {
		return idRecaudo;
	}

	public void setIdRecaudo(Integer idRecaudo) {
		this.idRecaudo = idRecaudo;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public Byte[] getContenidoDocumento() {
		return contenidoDocumento;
	}

	public void setContenidoDocumento(Byte[] contenidoDocumento) {
		this.contenidoDocumento = contenidoDocumento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<TipoMotivo> getListaTipoMotivo() {
		return listaTipoMotivo;
	}

	public void setListaTipoMotivo(List<TipoMotivo> listaTipoMotivo) {
		this.listaTipoMotivo = listaTipoMotivo;
	}

	public List<Recaudo> getListaRecaudo() {
		return listaRecaudo;
	}

	public void setListaRecaudo(List<Recaudo> listaRecaudo) {
		this.listaRecaudo = listaRecaudo;
	}

	public String getNombreRecaudo() {
		return nombreRecaudo;
	}

	public void setNombreRecaudo(String nombreRecaudo) {
		this.nombreRecaudo = nombreRecaudo;
	}

	public String getNombreMotivo() {
		return nombreTipoMotivo;
	}

	public void setNombreMotivo(String nombreMotivo) {
		this.nombreTipoMotivo = nombreMotivo;
	}

	public Documento getDoc() {
		return doc;
	}

	public void setDoc(Documento doc) {
		this.doc = doc;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public AImage getImagen() {
		return imagen;
	}

	public void setImagen(AImage imagen) {
		this.imagen = imagen;
	}

	public String getNombreDoc() {
		return nombreDoc;
	}

	public void setNombreDoc(String nombreDoc) {
		this.nombreDoc = nombreDoc;
	}

	public Integer getCaso() {
		return caso;
	}

	public void setCaso(Integer caso) {
		this.caso = caso;
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}


	public String getRecaudo() {
		return recaudo;
	}

	public void setRecaudo(String recaudo) {
		this.recaudo = recaudo;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Integer getInstancia() {
		return instancia;
	}

	public void setInstancia(Integer instancia) {
		this.instancia = instancia;
	}

	public String getLapso() {
		return lapso;
	}

	public void setLapso(String lapso) {
		this.lapso = lapso;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public SolicitudApelacion getSolicitudapelacion() {
		return solicitudapelacion;
	}

	public void setSolicitudapelacion(SolicitudApelacion solicitudapelacion) {
		this.solicitudapelacion = solicitudapelacion;
	}

	public LapsoAcademico getLapsoAcademico() {
		return lapsoAcademico;
	}

	public void setLapsoAcademico(LapsoAcademico lapsoAcademico) {
		this.lapsoAcademico = lapsoAcademico;
	}

	public ServicioLapsoAcademico getServiciolapsoacademico() {
		return serviciolapsoacademico;
	}

	public void setServiciolapsoacademico(
			ServicioLapsoAcademico serviciolapsoacademico) {
		this.serviciolapsoacademico = serviciolapsoacademico;
	}

	public ServicioSolicitudApelacion getServiciosolicitudapelacion() {
		return serviciosolicitudapelacion;
	}

	public void setServiciosolicitudapelacion(
			ServicioSolicitudApelacion serviciosolicitudapelacion) {
		this.serviciosolicitudapelacion = serviciosolicitudapelacion;
	}

	public Integer getInstanciaApelada() {
		return instanciaApelada;
	}

	public void setInstanciaApelada(Integer instanciaApelada) {
		this.instanciaApelada = instanciaApelada;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getSancion() {
		return sancion;
	}

	public void setSancion(String sancion) {
		this.sancion = sancion;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void concatenacionNombres() {

		String nombre1 = nombre;
		String nombre2 = segundoNombre;
		nombres = nombre1 + " " + nombre2;
		System.out.println(nombres);
	}

	public void concatenacionApellidos() {

		String apellido1 = apellido;
		String apellido2 = segundoApellido;
		apellidos = apellido1 + " " + apellido2;

	}

	@Command
	@NotifyChange({ "listaRecaudos" })
	public void buscarRecaudosEntregados(String cedula) {
		listaRecaudos = serviciorecaudoentregado.buscarRecaudosEntregadosReconsideracion(cedula);
		System.out.println(listaRecaudos);

	}


	@Init
	public void init(

	@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("cedula") String v1,
			@ExecutionArgParam("nombre") String v2,
			@ExecutionArgParam("apellido") String v3,
			@ExecutionArgParam("email") String v4,
			@ExecutionArgParam("telefono") String v5,
			@ExecutionArgParam("programa") String v6,
			@ExecutionArgParam("sancion") String v7,
			@ExecutionArgParam("lapso") String v8,
			@ExecutionArgParam("instancia") Integer v9,

			@ExecutionArgParam("segundoNombre") String v11,
			@ExecutionArgParam("segundoApellido") String v12,
			@ExecutionArgParam("asignatura") String v13,
			@ExecutionArgParam("caso") Integer v14)

	// initialization code

	{
		Selectors.wireComponents(view, this, false);
		this.cedula = v1;
		this.nombre = v2;
		this.apellido = v3;
		this.email = v4;
		this.telefono = v5;
		this.programa = v6;
		this.sancion = v7;
		this.lapso = v8;
		this.instancia = v9;

		this.segundoNombre = v11;
		this.segundoApellido = v12;
		this.asignatura = v13;
		this.caso = v14;
	
		concatenacionNombres();
		concatenacionApellidos();
		
		
		
		if (sancion.equalsIgnoreCase("RR")){
			asignaturas = servicioasignaturaestudiantesancionado.buscarAsignaturaDeSancion(cedula, lapso);
			if (asignaturas != null)
				for (int i=0; i<asignaturas.size(); i++)
					asignaturaLapsosConsecutivos += asignaturas.get(i).getAsignatura().getNombreAsignatura() + ", ";
			labelAsignaturaLapsosConsecutivos = "Asignatura(s):";
		}
		else{
			labelAsignaturaLapsosConsecutivos = "Lapsos consecutivos:";
			asignaturaLapsosConsecutivos = lapsosConsecutivos;
		}
		media = null;
		doc = new Documento();
		buscarRecaudosEntregados(cedula);
	
	}
	

	@Command
	public void closeThis() {
		window.detach();
	}

	@NotifyChange({ "lista" })
	@Command
	public void registrarSolicitudApelacion() {

		Date fecha = new Date();
		Time hora = new Time(0);

		if (nombreDoc == null) {
			mensajesusuario.advertenciaLlenarCampos();

		} else {
			solicitudApelacionPK.setCedulaEstudiante(cedula);
			System.out.println("lapso " + lapso);
			solicitudApelacionPK.setCodigoLapso(lapso);
			solicitudApelacionPK.setIdInstanciaApelada(2);
			System.out.println("instancia " + instancia);
			solicitudApelacion.setId(solicitudApelacionPK);
			System.out.println("fecha " + fecha);
			solicitudApelacion.setFechaSolicitud(fecha);
			solicitudApelacion.setEstatus(true);
			solicitudApelacion.setNumeroCaso(caso);

			apelacionEstadoApelacionPK.setCedulaEstudiante(cedula);
			apelacionEstadoApelacionPK.setCodigoLapso(lapso);
			apelacionEstadoApelacionPK.setIdInstanciaApelada(2);
			apelacionEstadoApelacionPK.setIdEstadoApelacion(3);
			apelacionEstadoApelacion.setId(apelacionEstadoApelacionPK);
			apelacionEstadoApelacion.setFechaEstado(hora);
			// =
			idTipoMotivo  = listaRecaudos.get(0).getMotivo().getId().getIdTipoMotivo();
			motivoPK.setCedulaEstudiante(cedula);
			motivoPK.setCodigoLapso(lapso);
			motivoPK.setIdInstanciaApelada(2);
			motivoPK.setIdTipoMotivo(idTipoMotivo);
			if (motivoPK == null)
				System.out.println("nada1");
			else
				System.out.println("algo1");
			motivos.setId(motivoPK);
			motivos.setEstatus(true);

			recaudoEntregadoPK.setCedulaEstudiante(cedula);
			recaudoEntregadoPK.setCodigoLapso(lapso);
			recaudoEntregadoPK.setIdInstanciaApelada(2);
			recaudoEntregadoPK.setIdRecaudo(2);
			recaudoEntregadoPK.setIdTipoMotivo(idTipoMotivo);
			recaudoEntregado.setId(recaudoEntregadoPK);
			recaudoEntregado.setEstatus(true);
		
			
			soporte.setDocumento(doc);
			soporte.setEstatus(true);
			soporte.setFechaSubida(fecha);
			soporte.setRecaudoEntregado(recaudoEntregado);
//			recaudoEntregado.setSoporte(soporte);
	
		}
		try {

			serviciosolicitudapelacion.guardar(solicitudApelacion);
			servicioapelacionestadoapelacion.guardar(apelacionEstadoApelacion);
			serviciomotivos.guardar(motivos);
			serviciorecaudoentregado.guardar(recaudoEntregado);
			serviciosoporte.guardar(soporte);
			mensajesusuario.informacionRegistroCorrecto();
		} catch (Exception e) {
			System.out.println(e.getMessage());

			serviciolista.buscarApelaciones();
		}

	}

	@Command
	@NotifyChange("nombreDoc")
	public void cargarCartaReconsideracion(
			@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event) {
		media = event.getMedia();
		if (media != null) {
			if (media.getContentType().equals("image/jpeg")
					|| media.getContentType().equals("application/pdf")
					|| media.getContentType().equals("application/msword")
					|| media.getContentType()
							.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
					|| media.getContentType().equals(
							"application/vnd.oasis.opendocument.text")
					|| media.getContentType().equals(
							"application/x-vnd.oasis.opendocument.text")) {
				doc.setNombreDocumento(media.getName());
				doc.setTipoDocumento(media.getContentType());
				doc.setContenidoDocumento(media.getByteData());
				nombreDoc = doc.getNombreDocumento();
			} else {
				Messagebox.show(media.getName()
						+ " No es un tipo de archivo valido!", "Error",
						Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

//	@Command
//	public void descargarDocumento(
//			@ContextParam(ContextType.COMPONENT) Component componente) {
//		int idRecaudo = Integer.parseInt(componente.getAttribute("idRecaudo")
//				.toString());
//		for (int j = 0; j < listaRecaudos.size(); j++) {
//			if (listaRecaudos.get(j).getIdRecaudo() == idRecaudo)
//				Filedownload.save(listaRecaudos.get(j).getContenidoDocumento(),
//						listaRecaudos.get(j).getTipoDocumento(), listaRecaudos
//								.get(j).getNombreDocumento());
//		}
//
//	}

	@Command
	public void cargarDocumento(
			@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event,
			@ContextParam(ContextType.COMPONENT) Component componente) {
		media = event.getMedia();
		if (media != null) {
			if (media.getContentType().equals("image/jpeg")
					|| media.getContentType().equals("application/pdf")
					|| media.getContentType().equals("application/msword")
					|| media.getContentType()
							.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
					|| media.getContentType().equals(
							"application/vnd.oasis.opendocument.text")
					|| media.getContentType().equals(
							"application/x-vnd.oasis.opendocument.text")) {
				doc.setNombreDocumento(media.getName());
				doc.setTipoDocumento(media.getContentType());
				doc.setContenidoDocumento(media.getByteData());
				nombreDoc = doc.getNombreDocumento();

				String idRecaudo = componente.getAttribute("idRecaudo")
						.toString();

			} else {
				Messagebox.show(media.getName()
						+ " No es un tipo de archivo valido!", "Error",
						Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	@Command
	public void abrir() {
		Executions
				.createComponents(
						"/WEB-INF/sigarep/vistas/transacciones/VerificarApelaciones.zul",
						null, null);
	}

}
