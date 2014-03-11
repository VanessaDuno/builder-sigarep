package sigarep.viewmodels.transacciones;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import sigarep.herramientas.MensajesAlUsuario;
import sigarep.modelos.data.maestros.Reglamento;
import sigarep.modelos.servicio.maestros.ServicioReglamento;

/** View Model Ayuda UCLA-DCYT Sistemas de Informaci�n
 * Busca y muestra los manuales del Sistema y del Usuario
 * @author Equipo : Builder-Sigarep Lapso 2013-1
 * @version 2
 * @since 01/03/2014 
 */

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMAyuda {

	private AMedia contenidoManual;
	private Reglamento contenido;
	private Reglamento reglamento;
	private MensajesAlUsuario mensajeAlUsuario = new MensajesAlUsuario();
	@WireVariable
	private ServicioReglamento servicioreglamento;

//Set y Get 	
	public AMedia getContenidoManual() {
		return contenidoManual;
	}

	public void setContenidoManual(AMedia contenidoManual) {
		this.contenidoManual = contenidoManual;
	}

		
	public Reglamento getContenido() {
		return contenido;
	}

	public void setContenido(Reglamento contenido) {
		this.contenido = contenido;
	}

	/** Compara si la b�squeda del objeto del manual contiene o no informaci�n y de acuerdo a ello muestra dicho manual o mensaje de advertencia.
	 * @param  init
	 * @return c�digo de inicializaci�n
	 * @throws No dispara ninguna excepcion.
	   */
	
	@Init
	public void init(@ExecutionArgParam("rutaModal") String rutaModal) throws IOException{
        //initialization code
		if (rutaModal.equalsIgnoreCase("ManualUsuario")){
			buscarManualUsuario();
			if (contenido != null)
				mostrarManual();
		    else
				mensajeAlUsuario.advertenciaCargarDocumento();
		}
		else{
			buscarManualSistema();
			if (contenido != null)
				mostrarManual();
			else	
				mensajeAlUsuario.advertenciaCargarDocumento();
		}
	}
	
	/** M�todo de b�squeda del Manual del Sistema
	 * @param ninguno.
	 * @return contenido.
	 * @throws No dispara ninguna excepcion.
	   */

	private void buscarManualSistema() {
		List<Reglamento> busqueda = servicioreglamento.buscarManualSistema();
		if (busqueda.size() > 0) {
			reglamento = busqueda.get(0);
			
		} else {
			reglamento = null;
		}
		contenido = reglamento;
	}

	/** M�todo de b�squeda del Manual del Usuario.
	 * @param ninguno.
	 * @return contenido.
	 * @throws No dispara ninguna excepcion.
	   */

	private void buscarManualUsuario() {
		List<Reglamento> busqueda = servicioreglamento.buscarManualUsuario();
		if (busqueda.size() > 0) {
			reglamento = busqueda.get(0);
		} else {
			reglamento = null;
		}
		contenido = reglamento;
	}
	/** M�todo que muestra el manual.
	 * @param ninguno.
	 * @return contenidoManual.
	 * @throws No dispara ninguna excepcion.
	   */	
	
	@Command
    @NotifyChange("contenidoManual")
    public void mostrarManual() throws IOException { 	 	 
    	 contenidoManual = new AMedia(reglamento.getDocumento().getNombreDocumento(), "pdf", reglamento.getDocumento().getTipoDocumento(), reglamento.getDocumento().getContenidoDocumento());
   }
}