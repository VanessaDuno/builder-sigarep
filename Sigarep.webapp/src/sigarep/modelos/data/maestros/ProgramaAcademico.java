package sigarep.modelos.data.maestros;

import java.io.Serializable;
import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase ProgramaAcad�mico
 * 
 * @author BUILDER
 * @version 1
 * @since 15/12/2013
 */
@Entity
@Access(AccessType.FIELD)
//anotaci�n indica que el JavaBean es una entidad persistente
@Table(name = "programa_academico")
public class ProgramaAcademico implements Serializable {
	private static final long serialVersionUID = 1L;

	// Atributos de la clase
	@Id
	// Clave principal de la clase
	@Column(name = "id_programa", unique = true, nullable = false)
	private Integer idPrograma;

	@Column(name = "estatus_programa", nullable = false)
	private Boolean estatusPrograma;

	@Column(name = "nombre_programa", nullable = false, length = 60)
	private String nombrePrograma;

	// Relaci�n bidireccional de muchos a uno, asociada a la clase Asignatura
	@OneToMany(mappedBy = "programaAcademico", cascade = { CascadeType.ALL })
	private List<Asignatura> asignaturas = new LinkedList<Asignatura>();

	// Relaci�n bidireccional de muchos a uno, asociada a la clase Estudiante
	@OneToMany(mappedBy = "programaAcademico")
	private List<Estudiante> estudiantes = new LinkedList<Estudiante>();

	// constructor por defecto
	public ProgramaAcademico() {
	}

	/**
	 * Constructor ProgramaAcademico
	 * 
	 * @param idPrograma
	 *            , nombrePrograma, estatus
	 * @return Constructor lleno
	 */
	public ProgramaAcademico(Integer idPrograma, String nombrePrograma,
			Boolean estatus) {
		super();
		this.idPrograma = idPrograma;
		this.nombrePrograma = nombrePrograma;
		this.estatusPrograma = estatus;
	}

	//M�todos set y get
	public Integer getIdPrograma() {
		return this.idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	public Boolean getEstatusPrograma() {
		return this.estatusPrograma;
	}

	public void setEstatusPrograma(Boolean estatusPrograma) {
		this.estatusPrograma = estatusPrograma;
	}

	public String getNombrePrograma() {
		return this.nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}

	public List<Asignatura> getAsignaturas() {
		return this.asignaturas;
	}

	public void setAsignaturas(List<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}

	public List<Estudiante> getEstudiantes() {
		return this.estudiantes;
	}

	public void setEstudiantes(List<Estudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}
	
	/**
	 * Relaci�n de la clase ProgramaAcademico con la clase Asignatura, Agregar
	 * Asignatura
	 * 
	 * @see Asignatura
	 * @param Asignatura
	 * @return Asignatura
	 */
	public Asignatura addAsignatura(Asignatura asignatura) {
		getAsignaturas().add(asignatura);
		asignatura.setProgramaAcademico(this);

		return asignatura;
	}

	/**
	 * Relaci�n de la clase ProgramaAcademico con la clase Asignatura, Agregar
	 * Asignatura
	 * 
	 * @see Asignatura
	 * @param Asignatura
	 * @return Asignatura
	 */
	public Asignatura removeAsignatura(Asignatura asignatura) {
		getAsignaturas().remove(asignatura);
		asignatura.setProgramaAcademico(null);

		return asignatura;
	}

	/**
	 * Relaci�n de la clase ProgramaAcademico con la clase Estudiante, Agregar
	 * Estudiante
	 * 
	 * @see Estudiante
	 * @param Estudiante
	 * @return Estudiante
	 */
	public Estudiante addEstudiante(Estudiante estudiante) {
		getEstudiantes().add(estudiante);
		estudiante.setProgramaAcademico(this);

		return estudiante;
	}
	/**
	 * Relaci�n de la clase ProgramaAcademico con la clase Estudiante, Quitar
	 * Estudiante
	 * 
	 * @see Estudiante
	 * @param Estudiante
	 * @return Estudiante
	 */
	public Estudiante removeEstudiante(Estudiante estudiante) {
		getEstudiantes().remove(estudiante);
		estudiante.setProgramaAcademico(null);

		return estudiante;
	}
	//Fin M�todos set y get
}//Fin Clase ProgramaAcademico