package sigarep.modelos.data.reportes;

import java.util.HashMap;
import java.util.Map;
import org.zkoss.zk.ui.Sessions;

import sigarep.modelos.data.maestros.ProgramaAcademico;
import  net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
public class ReportConfig {
	private String folder = Sessions.getCurrent().getWebApp().getRealPath("/WEB-INF/");
	private String source = Sessions.getCurrent().getWebApp().getRealPath("/WEB-INF/sigarepReportes/RpAsignaturasSancionados.jasper");
	private Map<String, Object> parametros;
	private JRBeanCollectionDataSource dataSource;
	private ReportType type;
	//private ProgramaAcademico programaAcademico;

	public ReportConfig() {
		parametros = new HashMap<String, Object>();
	 	parametros.put("ICON_LEFT_HEADER", "../../imagenes/imagenes-reportes/LOGO-UCLA.jpg"); // PASANDO POR PARAMETROS LAS IMAGENES DE DISE�O DEL REPORTE
		parametros.put("ICON_RIGHT_HEADER", "../../imagenes/imagenes-reportes/logo-decanato.jpg");
		parametros.put("ICON_FOOTER", "../../imagenes/imagenes-reportes/pie-reporte.jpg");
		parametros.put("ImagenSuperior", "../../imagenes/imagenes-reportes/cabecera-reporte.jpg");
		//parametros.put("nombrePrograma", programaAcademico.getIdPrograma());
		System.out.println(parametros);
	}
	
	//***************METODOS SET Y GET NECESARIOS PARA SER LLAMADOS EN EL ZUL DEL REPORTE****************
	
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
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}
	
	
}
