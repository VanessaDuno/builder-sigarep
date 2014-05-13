package sigarep.modelos.data.maestros;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Clase EstadoApelacion 
 * 
 * @author Equipo Builder
 * @version 1.0
 * @since 15/12/2013
 * @last 08/05/2014
 */
@Entity
@Access(AccessType.FIELD)
// Anotaci�n indica que el JavaBean es una entidad persistente
@Table(name = "estado_apelacion")
public class EstadoApelacion implements Serializable {
	private static final long serialVersionUID = 1L;

	// Atributos de la clase
	@Id
	// Clave principal de la clase
	@Column(name = "id_estado_apelacion", unique = true, nullable = false)
	private Integer idEstadoApelacion;

	@Column(name = "estatus", nullable = false)
	private Boolean estatus;

	@Column(name = "nombre_estado", length = 100)
	private String nombreEstado;

	@Column(name = "descripcion", length = 255)
	private String descripcion;
	
	@Column(name = "prioridad_ejecucion", nullable = true)
	private Integer prioridadEjecucion;

	// Relaci�n bidireccional de muchos a uno, asociada a la clase
	// InstanciaApelada
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_instancia_apelada", nullable = false)
	private InstanciaApelada instanciaApelada;

	// Constructor por defecto
	public EstadoApelacion() {
	}

	/**
	 * Constructor Estado apelacion
	 * 
	 * @param idEstadoApelacion , nombreEstado, descripcion, estatus
	 */
	public EstadoApelacion(Integer idEstadoApelacion, String nombreEstado,
			String descripcion, Boolean estatus) {
		super();
		this.idEstadoApelacion = idEstadoApelacion;
		this.nombreEstado = nombreEstado;
		this.descripcion = descripcion;
		this.estatus = estatus;
	}
	
	// M�todos Set y Get
	public Boolean getEstatus() {
		return this.estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdEstadoApelacion() {
		return idEstadoApelacion;
	}

	public void setIdEstadoApelacion(Integer idEstadoApelacion) {
		this.idEstadoApelacion = idEstadoApelacion;
	}

	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
	
	public Integer getPrioridadEjecucion() {
		return prioridadEjecucion;
	}

	public void setPrioridadEjecucion(Integer prioridadEjecucion) {
		this.prioridadEjecucion = prioridadEjecucion;
	}

	public InstanciaApelada getInstanciaApelada() {
		return instanciaApelada;
	}

	public void setInstanciaApelada(InstanciaApelada instanciaApelada) {
		this.instanciaApelada = instanciaApelada;
	}
	// Fin M�todos Set y Get
}