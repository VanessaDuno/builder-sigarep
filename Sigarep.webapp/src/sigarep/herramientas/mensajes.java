package sigarep.herramientas;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
/*
 * Debe declarar una variable tipo mensajes como est�ndar mensajeAlUsuario(mensajes mensajeAlUsuario = new mensajes();) en donde usar� los mensajes (su clase viewmodels), 
 * primero importe el paquete herramientas
 * Cuando vaya a usar alg�n mensaje coloca mensajeAlUsuario.NombreDelMetodo();
 * por ejemplo: mensajeAlUsuario.advertenciaLlenarCampos();
 * Lea los est�ndaresInterfacesV1.2 para que observe cu�les son los mensajes que puede necesitar.
 * si necesita alg�n otro mensaje agr�guelo a esta clase, en orden.
 * por ejemplo: si es de advertencia, debajo del �ltimo de advertencia.
 * 
 * */

public class mensajes {
	
	public void advertenciaMenudelGrupoVacio(){
		
		Messagebox.show("�Debe agregar al menos una funci�n al menu del grupo!", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);

	}
	
	public void advertenciaLlenarCampos(){
		
		Messagebox.show("�Debe llenar todos los campos!", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);

	}
	
	
	public void advertenciaSoloNumeros(){
		
		Messagebox.show("El campo s�lo admite n�meros", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);

	}
	
public void advertenciaSoloLetras(){
		
		Messagebox.show("El campo s�lo admite letras", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);

	}

public void advertenciaIngresarUsuario(){
	
	Messagebox.show("�Debe ingresar un usuario!", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);

}

public void advertenciaIngresarContrase�a(){
	
	Messagebox.show("�Debe ingresar una contrase�a!", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);

}

public void advertenciaSeleccionarOpcion(){
	
	Messagebox.show("Debe seleccionar alguna opci�n para continuar", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);

}

public void advertenciaNoCaracteres(){
	
	Messagebox.show("El campo no permite caracteres especiales.", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);
}

public void advertenciaCargarImagen(){

	Messagebox.show("�Debe Cargar una Imagen!", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);
}

public void advertenciaSeleccionarParaEliminar(){
	
	
	Messagebox.show("Debe seleccionar un registro para eliminarlo", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);
}


	
	public void informacionRegistroCorrecto(){
		
	    Messagebox.show("Se ha registrado correctamente", "Informaci�n", Messagebox.OK, Messagebox.INFORMATION);
	    
	}
	
public void informacionEliminarCorrecto(){
		
	    Messagebox.show("Se ha eliminado correctamente.", "Informaci�n", Messagebox.OK, Messagebox.INFORMATION);
	    
	}

public void ErrorImposibleGuardar(){
	
    Messagebox.show("Imposible guardar el registro", "Error",  Messagebox.OK, Messagebox.ERROR);
    
}

public void ErrorImposibleEliminar(){
	
    Messagebox.show("Imposible eliminar el registro.", "Error",  Messagebox.OK, Messagebox.ERROR);
    
}

public void ErrorNoExiste(){
	
    Messagebox.show("El elemento solicitado no existe.", "Error",  Messagebox.OK, Messagebox.ERROR);
    
}

public void confirmacionSalir(){
	
    Messagebox.show("�Realmente desea salir?", "Confirmaci�n", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
    
}

public void confirmacionCerrarSesion(){
	
    Messagebox.show("�Esta seguro de querer cerrar sesi�n?", "Confirmaci�n", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
    
}



/*public void guardarCambios(){
	
	Messagebox.show("�Desea guardar los cambios realizados?", "Confirmaci�n", Messagebox.OK | Messagebox.IGNORE | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
		����public void onEvent(Event evt) throws InterruptedException {
		��������if (evt.getName().equals("onOK")) {
		������������alert("Data Saved !");
		��������} else if (evt.getName().equals("onIgnore")) {
		������������Messagebox.show("Ignore Save", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
		��������} else {
		������������alert("Save Canceled !");
		��������}
		����}

}	

}*/
}
	