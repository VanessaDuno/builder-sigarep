package sigarep.modelos.data.maestros;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase ContactoSigarep
 * 
 * @author BUILDER
 * @version 1.0
 * @since 19/12/2013
 */
@Entity
@Access(AccessType.FIELD)
//anotaci�n indica que el JavaBean es una entidad persistente
@Table(name = "contacto_sigarep")
public class ContactoSigarep implements Serializable {
	private static final long serialVersionUID = 1L;

	// Atributos de la clase
	@Id
	// Clave principal de la clase
	@Column(name = "id_contacto")
	private Integer idContacto;

	@Column(name = "quienes_somos", length = 40000)
	private String quienesSomos;

	@Column(name = "correo_contacto", length = 150, nullable = false)
	private String correoContacto;
	
	@Column(name = "servidor_pop3", length = 50)
	private String servidorEntrantePop3;
	
	@Column(name = "puerto_pop3", length = 4)
	private String puertoEntradaPop3;
	
	@Column(name = "servidor_smtp", length = 50)
	private String servidorSalidaSmtp;
	
	@Column(name = "puerto_smtp", length = 4)
	private String puertoSalidaSmtp;
	
	@Column(name = "clave_correo", length = 30)
	private String claveCorreo;
	
	@Column(name = "twitter", length = 140)
	private String twitter;

	@Column(name = "facebook", length = 140)
	private String facebook;

	@Column(name = "telefono_contacto", length = 11)
	private String telefonoContacto;

	@Column(name = "direccion_contacto", length = 500)
	private String direccionContacto;
	
	@Column(nullable = false)
	private Boolean estatus;

	//Constructor por defecto
	public ContactoSigarep() {
	}

	/**
	 * Constructor ConstructorSigarep
	 * 
	 * @param id_contacto
	 *            , quienesSomos, correoContacto, twitter, facebook,
	 *            telefonoContacto, direccionContacto
	 * @return Constructor lleno
	 */
	public ContactoSigarep(Integer id_contacto, String quienesSomos,
			String correoContacto, String twitter, String facebook,
			String telefonoContacto, String direccionContacto) {
		super();
		this.idContacto = id_contacto;
		this.quienesSomos = quienesSomos;
		this.correoContacto = correoContacto;
		this.twitter = twitter;
		this.facebook = facebook;
		this.telefonoContacto = telefonoContacto;
		this.direccionContacto = direccionContacto;
	}

	//M�todos set y get
	public String getQuienesSomos() {
		return quienesSomos;
	}

	public void setQuienesSomos(String quienesSomos) {
		this.quienesSomos = quienesSomos;
	}

	public Integer getIdContacto() {
		return idContacto;
	}

	public void setIdContacto(Integer idContacto) {
		this.idContacto = idContacto;
	}

	public String getCorreoContacto() {
		return correoContacto;
	}

	public void setCorreoContacto(String correoContacto) {
		this.correoContacto = correoContacto;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTelefonoContacto() {
		return telefonoContacto;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}

	public String getDireccionContacto() {
		return direccionContacto;
	}

	public void setDireccionContacto(String direccionContacto) {
		this.direccionContacto = direccionContacto;
	}
	//Fin M�todos set y get

	public String getServidorEntrantePop3() {
		return servidorEntrantePop3;
	}

	public void setServidorEntrantePop3(String servidorEntrantePop3) {
		this.servidorEntrantePop3 = servidorEntrantePop3;
	}

	public String getPuertoEntradaPop3() {
		return puertoEntradaPop3;
	}

	public void setPuertoEntradaPop3(String puertoEntradaPop3) {
		this.puertoEntradaPop3 = puertoEntradaPop3;
	}

	public String getServidorSalidaSmtp() {
		return servidorSalidaSmtp;
	}

	public void setServidorSalidaSmtp(String servidorSalidaSmtp) {
		this.servidorSalidaSmtp = servidorSalidaSmtp;
	}

	public String getPuertoSalidaSmtp() {
		return puertoSalidaSmtp;
	}

	public void setPuertoSalidaSmtp(String puertoSalidaSmtp) {
		this.puertoSalidaSmtp = puertoSalidaSmtp;
	}

	public String getClaveCorreo() {
		return claveCorreo;
	}

	public void setClaveCorreo(String claveCorreo) {
		this.claveCorreo = claveCorreo;
	}

	public Boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
}