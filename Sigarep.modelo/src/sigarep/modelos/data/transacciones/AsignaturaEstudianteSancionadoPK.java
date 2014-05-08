package sigarep.modelos.data.transacciones;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Clase Asignatura estudiante sancionado, trae los objetos de esta clase compuesta por
 * varias claves principales y la relacion con distintas tablas
 * 
 * @author BUILDER
 * @version 1
 * @since 03/01/2014
 * @last 08/05/2014
 */
@Embeddable
@Access(AccessType.FIELD)
public class AsignaturaEstudianteSancionadoPK implements Serializable {
	//declaracion por defecto de id, requerida para las clases serializables.
	private static final long serialVersionUID = 1L;
	// Atributos de la clase
	@Column(name="codigo_lapso", unique=false, nullable=false, length=6)
	private String codigoLapso;

	@Column(name="cedula_estudiante", unique=false, nullable=false, length=8)
	private String cedulaEstudiante;

	@Column(name="codigo_asignatura", unique=false, nullable=false, length=8)
	private String codigoAsignatura;

	public AsignaturaEstudianteSancionadoPK() {
	}
	
	//metodos set y get
	public String getCodigoLapso() {
		return this.codigoLapso;
	}
	public void setCodigoLapso(String codigoLapso) {
		this.codigoLapso = codigoLapso;
	}
	public String getCedulaEstudiante() {
		return this.cedulaEstudiante;
	}
	public void setCedulaEstudiante(String cedulaEstudiante) {
		this.cedulaEstudiante = cedulaEstudiante;
	}
	public String getCodigoAsignatura() {
		return this.codigoAsignatura;
	}
	public void setCodigoAsignatura(String codigoAsignatura) {
		this.codigoAsignatura = codigoAsignatura;
	}
	//Fin metodos set y get
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AsignaturaEstudianteSancionadoPK)) {
			return false;
		}
		AsignaturaEstudianteSancionadoPK castOther = (AsignaturaEstudianteSancionadoPK)other;
		return 
			this.codigoLapso.equals(castOther.codigoLapso)
			&& this.cedulaEstudiante.equals(castOther.cedulaEstudiante)
			&& this.codigoAsignatura.equals(castOther.codigoAsignatura);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codigoLapso.hashCode();
		hash = hash * prime + this.cedulaEstudiante.hashCode();
		hash = hash * prime + this.codigoAsignatura.hashCode();
		
		return hash;
	}
}