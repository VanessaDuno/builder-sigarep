package sigarep.modelos.data.maestros;

import java.io.Serializable;
import javax.persistence.*;
import sigarep.modelos.data.transacciones.Motivo;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase TipoMotivo
 * 
 * @author BUILDER
 * @version 1.0
 * @since 18/12/2013
 * @last 08/05/2014
 */
@Entity
@Access(AccessType.FIELD)
//anotaci�n indica que el JavaBean es una entidad persistente
@Table(name = "tipo_motivo")
public class TipoMotivo implements Serializable {
private static final long serialVersionUID = 1L;
    
   //Atributos de la clase
	@Id
	// Clave principal de la clase
	@Column(name = "id_tipo_motivo", unique = true, nullable = false)
	private Integer idTipoMotivo;
	@Column(name = "nombre_tipo_motivo", nullable = false, length = 60)
	private String nombreTipoMotivo;
	@Column(name = "descripcion", length = 255)
	private String descripcion;
	@Column(name = "estatus", nullable = false)
	private Boolean estatus;
	@Column(name = "protegido", nullable = true)
	private Boolean protegido;
	//Relaci�n bidireccional de muchos a uno, asociada a la clase Recaudo
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "tipoMotivo")
	private List<Recaudo> recaudos = new LinkedList<Recaudo>();
	// Relaci�n bidireccional de muchos a uno, asociada a la clase Motivo
	@OneToMany(mappedBy = "tipoMotivo")
	private List<Motivo> motivos = new LinkedList<Motivo>();

	//Constructor por defecto
	public TipoMotivo() {

	}

	/**
	 * Constructor TipoMotivo
	 * 
	 * @param idTipoMotivo , descripcion, estatus, nombreTipoMotivo, protegido
	 */
	public TipoMotivo(Integer idTipoMotivo, String descripcion,
			Boolean estatus, String nombreTipoMotivo, Boolean protegido) {
		super();
		this.idTipoMotivo = idTipoMotivo;
		this.descripcion = descripcion;
		this.estatus = estatus;
		this.nombreTipoMotivo = nombreTipoMotivo;
		this.protegido = protegido;
	}

	// M�todos set y get
	public Integer getIdTipoMotivo() {
		return this.idTipoMotivo;
	}

	public String getNombreTipoMotivo() {
		return this.nombreTipoMotivo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public Boolean getEstatus() {
		return this.estatus;
	}

	public void setIdTipoMotivo(Integer idTipoMotivo) {
		this.idTipoMotivo = idTipoMotivo;
	}

	public void setNombreTipoMotivo(String nombreTipoMotivo) {
		this.nombreTipoMotivo = nombreTipoMotivo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
	
	public Boolean getProtegido() {
		return protegido;
	}

	public void setProtegido(Boolean protegido) {
		this.protegido = protegido;
	}

	public List<Recaudo> getRecaudos() {
		return this.recaudos;
	}

	public void setRecaudos(List<Recaudo> recaudos) {
		this.recaudos = recaudos;
	}

	public List<Motivo> getMotivos() {
		return this.motivos;
	}

	public void setMotivos(List<Motivo> motivos) {
		this.motivos = motivos;
	}
	//Fin M�todos set y get
	/**
	 * Relaci�n de la clase TipoMotivo con la clase Recaudo, Agregar Recaudo
	 * 
	 * @see Recaudo
	 * @param Recaudo
	 * @return Recaudo
	 */
	public Recaudo addRecaudo(Recaudo recaudo) {
		getRecaudos().add(recaudo);
		recaudo.setTipoMotivo(this);
		return recaudo;
	}

	/**
	 * Relaci�n de la clase TipoMotivo con la clase Recaudo, Quitar Recaudo
	 * 
	 * @see Recaudo
	 * @param Recaudo
	 * @return Recaudo
	 */
	public Recaudo removeRecaudo(Recaudo recaudo) {
		getRecaudos().remove(recaudo);
		recaudo.setTipoMotivo(null);
		return recaudo;
	}

	/**
	 * Relaci�n de la clase TipoMotivo con la clase Motivo, Agregar Motivo
	 * 
	 * @see Motivo
	 * @param Motivo
	 * @return Motivo
	 */
	public Motivo addMotivo(Motivo motivo) {
		getMotivos().add(motivo);
		motivo.setTipoMotivo(this);
		return motivo;
	}

	/**
	 * Relaci�n de la clase TipoMotivo con la clase Motivo, Quitar Motivo
	 * 
	 * @see Motivo
	 * @param Motivo
	 * @return Motivo
	 */
	public Motivo removeMotivo(Motivo motivo) {
		getMotivos().remove(motivo);
		motivo.setTipoMotivo(null);
		return motivo;
	}
}//Fin Clase TipoMotivo