package sigarep.modelos.repositorio.maestros;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import sigarep.modelos.data.maestros.Persona;

public interface IPersonaDAO extends JpaRepository<Persona, String> {
	
	/**
	 * Busca las personas que poseen estatus true
	 * @return List<Persona> Lista de Personas con estatus true
	 */
	public List<Persona> findByEstatusTrue();
	
	@Query("select persona from Persona persona where persona.nombreUsuario.nombreUsuario = :nombreUsuario")
	public Persona buscarPersonaPorNombreUsuario(@Param("nombreUsuario") String nombreUsuario);
	
}
