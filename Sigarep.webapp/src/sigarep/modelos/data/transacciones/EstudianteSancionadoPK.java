package sigarep.modelos.data.transacciones;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the estudiante_sancionado database table.
 * 
 */
@Embeddable
@Access(AccessType.FIELD)
public class EstudianteSancionadoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="codigo_lapso", unique=true, nullable=false, length=6)
	private String codigoLapso;

	@Column(name="cedula_estudiante", unique=true, nullable=false, length=8)
	private String cedulaEstudiante;

	public EstudianteSancionadoPK() {
	}
	
	public EstudianteSancionadoPK(String codigoLapso, String cedulaEstudiante) {
		super();
		this.codigoLapso = codigoLapso;
		this.cedulaEstudiante = cedulaEstudiante;
	}

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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EstudianteSancionadoPK)) {
			return false;
		}
		EstudianteSancionadoPK castOther = (EstudianteSancionadoPK)other;
		return 
			this.codigoLapso.equals(castOther.codigoLapso)
			&& this.cedulaEstudiante.equals(castOther.cedulaEstudiante);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codigoLapso.hashCode();
		hash = hash * prime + this.cedulaEstudiante.hashCode();
		
		return hash;
	}
}