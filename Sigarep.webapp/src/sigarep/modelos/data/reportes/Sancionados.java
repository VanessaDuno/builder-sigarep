package sigarep.modelos.data.reportes;

/** Sancionados
 * @author BUILDER 
 * @version 1.0
 * @since 20/12/13
 */
public class Sancionados {
	// Atributos de la clase
	String cedula;
	String nombre;
	String apellido;
	String veredicto;
	int procedentes;
	int noprocedentes;

	/**
	 * Constructor Sancionados
	 * @param cedula, nombre, apellido, veredicto, procedentes, noprocedentes
	 * @return Constructor lleno
	 */
	public Sancionados(String cedula, String nombre, String apellido,
			String veredicto, int procedentes, int noprocedentes) {
		super();
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		this.veredicto = veredicto;
		this.procedentes = procedentes;
		this.noprocedentes = noprocedentes;
	}

	// M�todos GET y SET
	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getVeredicto() {
		return veredicto;
	}

	public void setVeredicto(String veredicto) {
		this.veredicto = veredicto;
	}

	public int getProcedentes() {
		return procedentes;
	}

	public void setProcedentes(int procedentes) {
		this.procedentes = procedentes;
	}

	public int getNoProcedentes() {
		return noprocedentes;
	}

	public void setNoProcedentes(int noprocedentes) {
		this.noprocedentes = noprocedentes;
	}// Fin M�todos Set y Get

}//Fin Clase Sancionados
