package sigarep.modelos.data.reportes;

/** Clase ReportType
 * @author Equipo Builder  
 * @version 1.0
 * @since 20/12/2013
 * @last 09/05/2014
 */
public class ReportType {
	// Atributos de la clase
	private String value;
	private String label;

	/**
	 * Constructor ReportType
	 * @param label, value
	 */
	public ReportType(String label, String value) {
		super();
		this.value = value;
		this.label = label;
	}

	//  M�todos Get
	public String getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}// Fin M�todos Get

}