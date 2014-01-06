package sigarep.modelos.data.maestros;

import java.io.Serializable;
import javax.persistence.*;

import sigarep.modelos.data.transacciones.RecaudoEntregado;

import java.util.List;


/**
 * The persistent class for the recaudo database table.
 * 
 */
@Entity
@Access(AccessType.FIELD)
@Table(name="recaudo")
public class Recaudo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_recaudo", unique=true, nullable=false)
	private Integer idRecaudo;

	@Column(length=255)
	private String descripcion;

	@Column(nullable=false)
	private Boolean estatus;

	@Column(name="nombre_recaudo", nullable=false, length=60)
	private String nombreRecaudo;

	@Column(length=255)
	private String observacion;

	//bi-directional many-to-one association to TipoMotivo
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_tipo_motivo", nullable=false)
	private TipoMotivo tipoMotivo;

	//bi-directional many-to-one association to RecaudoEntregado
	@OneToMany(mappedBy="recaudo")
	private List<RecaudoEntregado> recaudoEntregados;

	public Recaudo() {
	}

	public Integer getIdRecaudo() {
		return this.idRecaudo;
	}

	public void setIdRecaudo(Integer idRecaudo) {
		this.idRecaudo = idRecaudo;
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

	public String getNombreRecaudo() {
		return this.nombreRecaudo;
	}

	public void setNombreRecaudo(String nombreRecaudo) {
		this.nombreRecaudo = nombreRecaudo;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public TipoMotivo getTipoMotivo() {
		return this.tipoMotivo;
	}

	public void setTipoMotivo(TipoMotivo tipoMotivo) {
		this.tipoMotivo = tipoMotivo;
	}

	public List<RecaudoEntregado> getRecaudoEntregados() {
		return this.recaudoEntregados;
	}

	public void setRecaudoEntregados(List<RecaudoEntregado> recaudoEntregados) {
		this.recaudoEntregados = recaudoEntregados;
	}

	public RecaudoEntregado addRecaudoEntregado(RecaudoEntregado recaudoEntregado) {
		getRecaudoEntregados().add(recaudoEntregado);
		recaudoEntregado.setRecaudo(this);

		return recaudoEntregado;
	}

	public RecaudoEntregado removeRecaudoEntregado(RecaudoEntregado recaudoEntregado) {
		getRecaudoEntregados().remove(recaudoEntregado);
		recaudoEntregado.setRecaudo(null);

		return recaudoEntregado;
	}

}