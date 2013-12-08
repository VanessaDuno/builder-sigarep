package sigarep.viewmodels.reportes;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Messagebox;

public class WindowController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@Listen("onClick=#tree")
	public void onClickMenu(String rutaArchivoZul) {
		try {
			// get an instance of the borderlayout defined in the zul-file
			Borderlayout bl = (Borderlayout) Path
					.getComponent("/mainBorderLayout");
			// get an instance of the searched CENTER layout area
			Center center = bl.getCenter();
			// clear the center child comps
			center.getChildren().clear();
			// call the zul-file and put it in the center layout area
			Executions.createComponents(rutaArchivoZul, center, null);
		} catch (Exception e) {
			Messagebox.show(e.toString());
		}
	}
}