package sigarep.modelos.data.reportes;

import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.SimpleCategoryModel;

public class ChartDataApelacionesPorPrograma {

	public static CategoryModel getModel(){
		SimpleCategoryModel model = new SimpleCategoryModel();
		model.setValue("Inform�tica", "2006", new Integer(100));
		model.setValue("Inform�tica", "2007", new Integer(143));
		model.setValue("Inform�tica", "2008", new Integer(223));
		model.setValue("Inform�tica", "2009", new Integer(378));
		
		model.setValue("An�lisis", "2006", new Integer(174));
		model.setValue("An�lisis", "2007", new Integer(244));
		model.setValue("An�lisis", "2008", new Integer(124));
		model.setValue("An�lisis", "2009", new Integer(174));


		model.setValue("Matem�tica", "2006", new Integer(137));
		model.setValue("Matem�tica", "2007", new Integer(297));
		model.setValue("Matem�tica", "2008", new Integer(307));
		model.setValue("Matem�tica", "2009", new Integer(317));
		
		model.setValue("Producci�n", "2006", new Integer(137));
		model.setValue("Producci�n", "2007", new Integer(297));
		model.setValue("Producci�n", "2008", new Integer(307));
		model.setValue("Producci�n", "2009", new Integer(317));
		
		
		return model;
	}
}
