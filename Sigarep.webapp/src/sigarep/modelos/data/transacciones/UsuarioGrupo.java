package sigarep.modelos.data.transacciones;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import sigarep.modelos.data.seguridad.Grupo;
import sigarep.modelos.data.seguridad.Usuario;


/**
 * The persistent class for the usuario_grupo database table.
 * 
 */
@Entity
@Access(AccessType.FIELD)
@Table(name="usuario_grupo")
@AssociationOverrides({
	@AssociationOverride(name = "id.usuario", 
		joinColumns = @JoinColumn(name = "nombreusuario")),
	@AssociationOverride(name = "id.grupo", 
		joinColumns = @JoinColumn(name = "idgrupo")) })
public class UsuarioGrupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsuarioGrupoPK id;

	@Column(nullable=false)
	private Boolean estatus;

	//bi-directional many-to-one association to Grupo
	@ManyToOne(fetch = FetchType.EAGER,cascade={CascadeType.ALL})
	@JoinColumn(name="idgrupo", nullable=false, insertable=false, updatable=false)
	private Grupo grupo;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch = FetchType.EAGER,cascade={CascadeType.ALL})
	@JoinColumn(name="nombreusuario", nullable=false, insertable=false, updatable=false)
	private Usuario usuario;

	public UsuarioGrupo() {
	}

	public UsuarioGrupoPK getId() {
		return this.id;
	}

	public void setId(UsuarioGrupoPK id) {
		this.id = id;
	}

	public Boolean getEstatus() {
		return this.estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}

	public Grupo getGrupo() {
		return this.grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
 
		UsuarioGrupo that = (UsuarioGrupo) o;
 
		if (getId() != null ? !getId().equals(that.getId())
				: that.getId() != null)
			return false;
 
		return true;
	}

	@Override
	public int hashCode() {
	    return id.hashCode();
	}
}