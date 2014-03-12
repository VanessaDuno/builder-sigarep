package sigarep.modelos.data.maestros;

import java.io.Serializable;
import javax.persistence.*;

import sigarep.modelos.data.transacciones.Cronograma;

import java.util.List;

/**
 * Clase Actividad (Actividades del Cronograma)
 * 
 * @author BUILDER
 * @version 1.0
 * @since 15/12/2013
 */
@Entity
@Access(AccessType.FIELD)
// anotaci�n indica que el JavaBean es una entidad persistente
@Table(name = "actividad")
public class Actividad implements Serializable {
	private static final long serialVersionUID = 1L;

	// Atributos de la clase
	@Id
	// Clave principal de la clase
	@Column(name = "id_actividad", unique = true, nullable = false)
	private Integer idActividad;

	@Column(length = 255)
	private String descripcion;

	@Column(nullable = false)
	private Boolean estatus;

	@Column(nullable = false, length = 60)
	private String nombre;

	// Relaci�n bidireccional de muchos a uno, asociada a la clase Cronograma
	@OneToMany(mappedBy = "actividad")
	private List<Cronograma> cronogramas;

	// Relaci�n bidireccional de muchos a uno, asociada a la clase Instancia
	// Apelada
	@ManyToOne
	@JoinColumn(name = "id_instancia_apelada", nullable = true)
	private InstanciaApelada instanciaApelada;

	// Constructor por defecto
	public Actividad() {
	}

	/**
	 * Constructor Actividad
	 * 
	 * @param id_actividad
	 *            , nombre, descripcion, instanciaApelada, estatus
	 * @return Constructor lleno
	 */
	public Actividad(Integer id_actividad, String nombre, String descripcion,
			InstanciaApelada instanciaApelada, Boolean estatus) {
		super();
		this.idActividad = id_actividad;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.instanciaApelada = instanciaApelada;
		this.estatus = estatus;
	}

	// M�todos Set y Get
	public Integer getIdActividad() {
		return this.idActividad;
	}

	public void setIdActividad(Integer idActividad) {
		this.idActividad = idActividad;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEstatus() {
		return this.estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Cronograma> getCronogramas() {
		return this.cronogramas;
	}

	public void setCronogramas(List<Cronograma> cronogramas) {
		this.cronogramas = cronogramas;
	}

	
	public InstanciaApelada getInstanciaApelada() {
		return instanciaApelada;
	}

	public void setInstanciaApelada(InstanciaApelada instanciaApelada) {
		this.instanciaApelada = instanciaApelada;
	}
	
	
	/**
	 * Relaci�n de la clase Actividad con la clase Cronograma, Agregar
	 * Cronograma
	 * 
	 * @see Cronograma
	 * @param cronograma
	 * @return cronograma
	 */
	public Cronograma addCronograma(Cronograma cronograma) {
		getCronogramas().add(cronograma);
		cronograma.setActividad(this);

		return cronograma;
	}

	/**
	 * Relaci�n de la clase Actividad con la clase Cronograma, Quitar Cronograma
	 * 
	 * @see Cronograma
	 * @param cronograma
	 * @return cronograma
	 */
	public Cronograma removeCronograma(Cronograma cronograma) {
		getCronogramas().remove(cronograma);
		cronograma.setActividad(null);
		return cronograma;
	}// Fin M�todos Set y Get

}//Fin Clase Actividad