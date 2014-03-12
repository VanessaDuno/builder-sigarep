package sigarep.modelos.servicio.transacciones;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sigarep.modelos.data.transacciones.InstanciaMiembro;
import sigarep.modelos.data.transacciones.InstanciaMiembroPK;
import sigarep.modelos.repositorio.transacciones.IInstanciaMiembroDAO;

@Service("servicioInstanciaMiembro")
public class ServicioInstanciaMiembro {
	
	private @Autowired IInstanciaMiembroDAO iInstanciaMiembroDAO;
	
	public InstanciaMiembro guardar(InstanciaMiembro instanciaMiembro) {
		return iInstanciaMiembroDAO.save(instanciaMiembro);
	}
	
	public void eliminar(InstanciaMiembroPK id){
		InstanciaMiembro miInstanciaMiembro = iInstanciaMiembroDAO.findOne(id);
		miInstanciaMiembro.setEstatus(false);
		miInstanciaMiembro.setFechaSalida(new Date());
		iInstanciaMiembroDAO.save(miInstanciaMiembro);
	}
	


	public int contarTodos() {
		return iInstanciaMiembroDAO.findAll().size();
	}

	public InstanciaMiembro crear() {
		return new InstanciaMiembro();
	}
}
