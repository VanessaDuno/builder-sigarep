package sigarep.modelos.data.reportes;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.zk.ui.Sessions;
import  net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/** ReportConfig
 * Reporte Configurable ReportConfig
 * @author BUILDER 
 * @version 1.0
 * @since 20/12/13
 */
public class ReportConfig {
	// Atributos de la clase
	private String source;
	private Map<String, Object> parametros;
	private JRBeanCollectionDataSource dataSource;
	private ReportType type;

	/**
	 * Constructor ReportConfig
	 * @param ruta
	 * @return Constructor lleno
	 */
	public ReportConfig(String ruta) {
		parametros = new HashMap<String, Object>();
		parametros.put("ICON_LEFT_HEADER", "../../imagenes/imagenes-reportes/LOGO-UCLA.jpg"); // PASANDO POR PARAMETROS LAS IMAGENES DE DISE�O DEL REPORTE
		parametros.put("ICON_RIGHT_HEADER", "../../imagenes/imagenes-reportes/logo-decanato.jpg");
		parametros.put("ICON_FOOTER", "../../imagenes/imagenes-reportes/pie-reporte.jpg");
		parametros.put("ImagenSuperior", "../../imagenes/imagenes-reportes/cabecera-reporte.jpg");
		System.out.println(parametros);
		source= Sessions.getCurrent().getWebApp().getRealPath(ruta);
	}

	//  M�todos Set y Get
	public ReportType getType() {
		return type;
	}

	public void setType(ReportType selectedReportType) {
		this.type = selectedReportType;
	}

	public String getSource() {
		return source;
	}

	public Map<String, Object> getParameters() {
		return parametros;
	}

	public JRBeanCollectionDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(JRBeanCollectionDataSource dataSource) {
		this.dataSource = dataSource;
	}// Fin M�todos Set y Get

}//Fin Clase ReportConfig
