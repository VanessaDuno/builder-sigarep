package sigarep.modelos.servicio.transacciones;

//Clase de filtros para la lista de apelacion veredicto por c�dula, nombre, apellido y sancion.
public class FiltroListaApelacionVeredicto1 {
	
		private String cedula="",nombre="",apellido="",sancion="", programa="",motivo="";

		public String getCedula() {
			return cedula;
		}

		public String getPrograma() {
			return programa;
		}

		public void setPrograma(String programa) {
			this.programa = programa;
		}

		public String getMotivo() {
			return motivo;
		}

		public void setMotivo(String motivo) {
			this.motivo = motivo;
		}

		public void setCedula(String cedula) {
			this.cedula = cedula==null?"":cedula.trim();//revisar
		}
		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre==null?"":nombre.trim();
		}
	
		public String getApellido() {
			return apellido;
		}

		public void setApellido(String apellido) {
			this.apellido = apellido==null?"":apellido.trim();
		}

		public String getSancion() {
			return sancion;
		}

		public void setSancion(String sancion) {
			this.sancion = sancion==null?"":sancion.trim();
		}
}
