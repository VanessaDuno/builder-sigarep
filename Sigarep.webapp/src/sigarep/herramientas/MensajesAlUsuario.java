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

public class MensajesAlUsuario {

	public void advertenciaLlenarCampos() {

		Messagebox.show("�Debe llenar todos los campos!", "Advertencia",
				Messagebox.OK, Messagebox.EXCLAMATION);

	}

	public void advertenciaSoloNumeros() {

		Messagebox.show("El campo s�lo admite n�meros.", "Advertencia",
				Messagebox.OK, Messagebox.EXCLAMATION);

	}

	public void advertenciaSoloLetras() {

		Messagebox.show("El campo s�lo admite letras.", "Advertencia",
				Messagebox.OK, Messagebox.EXCLAMATION);

	}

	public void advertenciaIngresarUsuario() {

		Messagebox.show("�Debe ingresar un usuario!", "Advertencia",
				Messagebox.OK, Messagebox.EXCLAMATION);

	}

	public void advertenciaIngresarContrase�a() {

		Messagebox.show("�Debe ingresar una contrase�a!", "Advertencia",
				Messagebox.OK, Messagebox.EXCLAMATION);

	}

	public void advertenciaSeleccionarOpcion() {

		Messagebox.show("Debe seleccionar alguna opci�n para continuar.",
				"Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);

	}

	public void advertenciaNoCaracteres() {

		Messagebox.show("El campo no permite caracteres especiales.",
				"Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);
	}

	public void advertenciaCargarImagen() {

		Messagebox.show("�Debe Cargar una Imagen!", "Advertencia",
				Messagebox.OK, Messagebox.EXCLAMATION);
	}

	public void advertenciaSeleccionarParaEliminar() {

		Messagebox.show("Debe seleccionar un registro para eliminarlo.",
				"Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);
	}
	public void  advertenciaFormatoNoSoportado() {

		Messagebox.show("El formato no es soportado.",
				"Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);
	}
	public void informacionRegistroCorrecto() {

		Messagebox.show("Se ha registrado correctamente.", "Informaci�n",
				Messagebox.OK, Messagebox.INFORMATION);

	}

	public void informacionEliminarCorrecto() {

		Messagebox.show("Se ha eliminado correctamente.", "Informaci�n",
				Messagebox.OK, Messagebox.INFORMATION);

	}

	public void informacionFinalizarLapsoExitoso() {

		Messagebox.show("�Lapso Acad�mico finalizado exitosamente!",
				"Informaci�n", Messagebox.OK, Messagebox.INFORMATION);

	}

	public void ErrorImposibleGuardar() {

		Messagebox.show("Imposible guardar el registro.", "Error",
				Messagebox.OK, Messagebox.ERROR);

	}

	public void ErrorFinalizarLapsoVeredicto() {

		Messagebox.show("No puede finalizar el lapso actual. "
				+ "Existen apelaciones sin veredicto.", "Error", Messagebox.OK,
				Messagebox.ERROR);
	}

	public void ErrorFinalizarLapsoSesion() {

		Messagebox.show("No puede finalizar el lapso actual. "
				+ "Existen apelaciones sin n�mero de sesi�n.", "Error",
				Messagebox.OK, Messagebox.ERROR);

	}

	public void ErrorFinalizarLapsoCronograma() {

		Messagebox.show("No puede finalizar el lapso actual. "
				+ "Existen actividades sin ejecutarse en el cronograma.",
				"Error", Messagebox.OK, Messagebox.ERROR);

	}
	
	public void ErrorLapsoActivoNoExistente() {

		Messagebox.show("No existe un lapso acad�mico activo.",
				"Error", Messagebox.OK, Messagebox.ERROR);

	}

	public void informacionArchivoCargado() {

		Messagebox.show("Archivo cargado correctamente.", "Informaci�n",
				Messagebox.OK, Messagebox.INFORMATION);

	}

	public void informacionArchivoEliminado() {

		Messagebox.show("Archivo eliminado correctamente.", "Informaci�n",
				Messagebox.OK, Messagebox.INFORMATION);

	}

	public void ErrorImposibleEliminar() {

		Messagebox.show("Imposible eliminar el registro.", "Error",
				Messagebox.OK, Messagebox.ERROR);

	}

	public void ErrorRangoFechas() {

		Messagebox.show("Error de rango de fechas.", "Error", Messagebox.OK,
				Messagebox.ERROR);

	}

	public void ErrorNoExiste() {

		Messagebox.show("El elemento solicitado no existe.", "Error",
				Messagebox.OK, Messagebox.ERROR);

	}

	public void confirmacionSalir() {

		Messagebox.show("�Realmente desea salir?", "Confirmaci�n",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);

	}

	public void confirmacionCerrarSesion() {

		Messagebox.show("�Esta seguro de querer cerrar sesi�n?",
				"Confirmaci�n", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION);

	}

}
