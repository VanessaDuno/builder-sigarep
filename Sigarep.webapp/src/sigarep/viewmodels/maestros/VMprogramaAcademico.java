package sigarep.viewmodels.maestros;

import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import sigarep.herramientas.MensajesAlUsuario;
import sigarep.modelos.data.maestros.ProgramaAcademico;
import sigarep.modelos.data.maestros.ProgramaAcademicoFiltros;
import sigarep.modelos.servicio.maestros.ServicioProgramaAcademico;

/*
 * @ (#) ProgramaAcademico.java 
 *
 * Copyright 2013 Builder. Todos los derechos reservados.
 * CONFIDENCIAL. El uso est� sujeto a los t�rminos de la licencia.
 * Esta clase es del registro del maestro "Programa Academico"
 * @ Author Javier Chacon
 */

@SuppressWarnings("serial")
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMprogramaAcademico {
	@WireVariable
	ServicioProgramaAcademico servicioprogramaacademico;
	
	private MensajesAlUsuario mensajeAlUsuario = new MensajesAlUsuario();
	private Integer idPrograma;
	private String nombrePrograma;
	private Boolean estatus;
	private List<ProgramaAcademico> listaPrograma;
	private ProgramaAcademico programaseleccionado;
	private ProgramaAcademicoFiltros filtros = new ProgramaAcademicoFiltros();

	// Inicio M�todos Sets y Gets
	public Integer getIdProgramaAcademico() {
		return idPrograma;
	}

	public void setIdProgramaAcademico(Integer idProgramaAcademico) {
		this.idPrograma = idProgramaAcademico;
	}

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String programa) {
		this.nombrePrograma = programa;
	}

	public Boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}

	public List<ProgramaAcademico> getListaPrograma() {
		return listaPrograma;
	}

	public void setListaPrograma(List<ProgramaAcademico> listaPrograma) {
		this.listaPrograma = listaPrograma;
	}

	public ProgramaAcademico getProgramaseleccionado() {
		return programaseleccionado;
	}

	public void setProgramaseleccionado(ProgramaAcademico programaseleccionado) {
		this.programaseleccionado = programaseleccionado;
	}

	public ProgramaAcademicoFiltros getFiltros() {
		return filtros;
	}

	public void setFiltro(ProgramaAcademicoFiltros filtros) {
		this.filtros = filtros;
	}

	// Fin M�todos Sets y Gets

	@Init
	public void init() {
		buscarProgramaA();
	}

	// M�todo que guarda un programa acad�mico
	@Command
	@NotifyChange({ "idPrograma", "nombrePrograma", "estatus", "listaPrograma" })
	public void guardarPrograma() {
		if (nombrePrograma == null || nombrePrograma.equals("")) {
			mensajeAlUsuario.advertenciaLlenarCampos();
		} else {
			ProgramaAcademico proa = new ProgramaAcademico(idPrograma,
					nombrePrograma, true);
			servicioprogramaacademico.guardarPrograma(proa);
			mensajeAlUsuario.informacionRegistroCorrecto();
			limpiar();
		}
	}

	// M�todo que limpia todos los campos de la pantalla
	@Command
	@NotifyChange({ "idPrograma", "nombrePrograma", "estatus", "listaPrograma" })
	public void limpiar() {
		nombrePrograma = "";
		buscarProgramaA();
	}

	// M�todo que trae todos los programas acad�micos en la lista de programas
	@Command
	@NotifyChange({ "listaPrograma" })
	public void buscarProgramaA() {
		listaPrograma = servicioprogramaacademico.listadoProgramas();
	}

	// M�todos que elimina un programa acad�mico dado su IdPrograma
	@Command
	@NotifyChange({ "listaPrograma", "nombrePrograma" })
	public void eliminarPrograma() {
		if (nombrePrograma == null || nombrePrograma.equals("")) {
			mensajeAlUsuario.advertenciaSeleccionarParaEliminar();
		} else {
			servicioprogramaacademico
					.eliminarPrograma(getProgramaseleccionado().getIdPrograma());
			mensajeAlUsuario.informacionEliminarCorrecto();
			limpiar();
		}
	}

	// M�todo que muestra los datos de una programa seleccionado
	@Command
	@NotifyChange({ "nombrePrograma" })
	public void mostrarSeleccionado() {
		nombrePrograma = getProgramaseleccionado().getNombrePrograma();
	}

	// M�todo que busca y filtra los programas academicos
	@Command
	@NotifyChange({ "listaPrograma" })
	public void filtros() {
		listaPrograma = servicioprogramaacademico.buscarPrograma(filtros);
	}
}
