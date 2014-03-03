package sigarep.modelos.data.maestros;

import java.io.Serializable;
import javax.persistence.*;

import sigarep.herramientas.Archivo;

import java.util.Date;

/**
 * Noticia Registra y modifica una noticia. Utilizada en el portal web. UCLA
 * DCYT Sistemas de Informacion.
 * 
 * @author Equipo : Builder-Sigarep Lapso 2013-2
 * @version 1.0
 * @since 22/01/14
 */

@Entity
@Access(AccessType.FIELD)
// anotaci�n indica que el JavaBean es una entidad persistente
@Table(name = "noticia")
public class Noticia implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor Noticia
	 * 
	 * @param idNoticia
	 *            , contenido, enlaceNoticia, estatus, fechaRegistro,
	 *            fotoNoticia, titulo, vencimiento
	 * @return Constructor lleno
	 */
	public Noticia(Integer idNoticia, String contenido, String enlaceNoticia,
			Boolean estatus, Date fechaRegistro, Archivo fotoNoticia,
			String titulo, Date vencimiento) {
		super();
		this.idNoticia = idNoticia;
		this.contenido = contenido;
		this.enlaceNoticia = enlaceNoticia;
		this.estatus = estatus;
		this.fechaRegistro = fechaRegistro;
		this.fotoNoticia = fotoNoticia;
		this.titulo = titulo;
		this.vencimiento = vencimiento;
	}

	// Atributos de la clase
	@Id
	// Clave principal de la clase
	@Column(name = "id_noticia", unique = true, nullable = false)
	private Integer idNoticia;

	@Column(nullable = false, length = 10000)
	private String contenido;

	@Column(name = "enlace_noticia", length = 1000)
	private String enlaceNoticia;

	@Column(nullable = false)
	private Boolean estatus;

	@Column(name = "fecha_registro")
	@Temporal(TemporalType.DATE)
	private Date fechaRegistro;

	private Archivo fotoNoticia = new Archivo();

	@Column(nullable = false, length = 60)
	private String titulo;

	@Column(name = "fecha_vencimiento")
	@Temporal(TemporalType.DATE)
	private Date vencimiento;

	public Noticia() {
	}

	// metodos set y get
	public Integer getIdNoticia() {
		return this.idNoticia;
	}

	public void setIdNoticia(Integer idNoticia) {
		this.idNoticia = idNoticia;
	}

	public String getContenido() {
		return this.contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getEnlaceNoticia() {
		return this.enlaceNoticia;
	}

	public void setEnlaceNoticia(String enlaceNoticia) {
		this.enlaceNoticia = enlaceNoticia;
	}

	public Boolean getEstatus() {
		return this.estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getVencimiento() {
		return this.vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	/**
	 * fotoNoticia Se crea un nuevo tipo de dato que en este caso va ser la foto
	 */
	@Embedded()
	public Archivo getFotoNoticia() {
		return fotoNoticia;
	}

	public void setFotoNoticia(Archivo fotoNoticia) {
		this.fotoNoticia = fotoNoticia;
	}
}
