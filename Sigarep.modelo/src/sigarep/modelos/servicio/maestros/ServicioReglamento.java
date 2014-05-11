package sigarep.modelos.servicio.maestros;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sigarep.modelos.data.maestros.Reglamento;
import sigarep.modelos.repositorio.maestros.IReglamentoDAO;

/**
 * Clase  ServicioReglamento (Servicio para la
 * Clase Reglamento)
 * 
 * @author Equipo Builder
 * @version 1.0
 * @since 18/12/2013
 * @last 10/05/2014
 */
@Service("servicioreglamento")
public class ServicioReglamento {
	private @Autowired IReglamentoDAO rg;

	/**guardar reglamento
	 * @param Reglamento r
	 * @return No devuelve ning�n valor
	 */
	public void guardarReglamento(Reglamento r){
		if (r.getIdDocumento() != null)
			rg.save(r);
		else{
			r.setIdDocumento(rg.buscarUltimoID()+1);
			rg.save(r);
		}
	}
	
	/**eliminar reglamento 
	 * @param idDocumento
	 * @return No devuelve ning�n valor
	 */
	public void eliminar(Integer idDocumento) {
		Reglamento reglamentoBorrarLogico = rg.findOne(idDocumento);
		reglamentoBorrarLogico.setEstatus(false);
		rg.save(reglamentoBorrarLogico);
	}
	
	/**buscar reglamento por ID
	 * @param Integer idDocumento
	 * @return No devuelve ning�n valor
	 */
	public Reglamento buscarReglamento(Integer idDocumento){
		return rg.findOne(idDocumento);
	}
	
	/**listado de reglamento
	 * @param IDAO, el cual trae todos los registros en true,
	 * los que no han sido eliminado logicamente
	 * @return listaReglamentoLogico
	 */
	public List<Reglamento> listaReglamento() {
		return rg.findByEstatusTrue();
	}
	
	/**
	 *filtrar reglamento por Titulo, Categoria
	 * 
	 * @param String tituloF, String categoriaF
	 * @return busca un documento por t�tulo o categor�a en el filtro
	 * @throws No  dispara ninguna excepci�n.
	 */
	public List<Reglamento> filtrarReglamento(String tituloF, String categoriaF) {
		List<Reglamento> resultado = new ArrayList<Reglamento>();	
		if (tituloF == null ||categoriaF == null ) {
			resultado = listaReglamento();
		} else {
			for (Reglamento r : listaReglamento()) {
				if (r.getTitulo().toLowerCase().contains(tituloF.toLowerCase())&&
					r.getCategoria().toLowerCase().contains(categoriaF.toLowerCase()))
				{
					resultado.add(r);
				}
			}
		}
		return resultado;
	}
	
	/**
	 * Buscar Reglamento
	 * 
	 * @param null
	 * @return busca un documento por categor�a reglamento
	 * @throws No  dispara ninguna excepci�n.
	 */
	public List<Reglamento> buscarReglamentoPortal(){
		return rg.findByCategoriaAndEstatusTrue("reglamento");
	}
	
	/**
	 * Buscar Recaudo
	 * 
	 * @param null
	 * @return busca un documento por categor�a recaudo
	 * @throws No  dispara ninguna excepci�n.
	 */
	public List<Reglamento> buscarRecaudosPortal(){
		return rg.findByCategoriaAndEstatusTrue("recaudo");
	}
	
	/**
	 * Buscar Formato
	 * 
	 * @param null
	 * @return busca un documento por categor�a formato
	 * @throws No  dispara ninguna excepci�n.
	 */
	public List<Reglamento> buscarFormatoPortal(){
		return rg.findByCategoriaAndEstatusTrue("formato");
	}
	
	/**
	 * Buscar Gu�a
	 * 
	 * @param null
	 * @return busca un documento por categor�a gu�a
	 * @throws No  dispara ninguna excepci�n.
	 */
	public List<Reglamento> buscarGuia(){
		return rg.findByCategoriaAndEstatusTrue("guia");
	}
	
	/**
	 * Buscar Calendario
	 * 
	 * @param null
	 * @return busca un documento por categor�a calendario
	 * @throws No  dispara ninguna excepci�n.
	 */
	public List<Reglamento> buscarCalendario(){
		return rg.findByCategoriaAndEstatusTrue("calendario");
	}
	
	/**
	 * Buscar Acta
	 * 
	 * @param null
	 * @return busca un documento por categor�a acta
	 * @throws No  dispara ninguna excepci�n.
	 */
	public List<Reglamento> buscarActa(){
		return rg.findByCategoriaAndEstatusTrue("Acta");
	}
	
	/**
	 * Buscar Manual de Usuario
	 * 
	 * @param null
	 * @return busca un documento por categor�a manual usuario
	 * @throws No  dispara ninguna excepci�n.
	 */
	public List<Reglamento> buscarManualUsuario(){
		return rg.findByCategoriaAndEstatusTrue("ManualUsuario");
	}
	
	/**
	 * Buscar Manual Sistema
	 * 
	 * @param null
	 * @return busca un documento por categor�a manual sistema
	 * @throws No  dispara ninguna excepci�n.
	 */
	public List<Reglamento> buscarManualSistema(){
		return rg.findByCategoriaAndEstatusTrue("ManualSistema");
	}
}