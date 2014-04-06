package sigarep.viewmodels.seguridad;

import java.util.ArrayList;
import java.util.Collections;

import org.zkoss.bind.annotation.AfterCompose;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import sigarep.modelos.data.seguridad.Grupo;
import sigarep.modelos.data.seguridad.Nodo;

import sigarep.modelos.servicio.seguridad.ServicioGrupo;
import sigarep.modelos.servicio.seguridad.ServicioNodo;
import sigarep.modelos.servicio.seguridad.ServicioUsuario;
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMMenuAplicacion{
		
	private static String ruta="timeout.zul";
	private VMModeloArbolAvanzado modeloMenuArbol;
	private static VMNodoMenuArbol  raizPortalInicial;
	private @WireVariable ServicioNodo servicionodo;
	private @WireVariable ServicioGrupo serviciogrupo;
	private @WireVariable ServicioUsuario serviciousuario;
	private AImage imagen;
	private String nombreUsuario;
	private VMRenderizarMenuArbolAplicacion renderizarPortalAplicacion=new VMRenderizarMenuArbolAplicacion();
	VMUtilidadesDeSeguridad seguridad = new VMUtilidadesDeSeguridad();
	//SETs y GETs
	
	public VMRenderizarMenuArbolAplicacion getRendererPortalAplicacion() {
		return renderizarPortalAplicacion;
	}


	public void setRendererPortalAplicacion(
			VMRenderizarMenuArbolAplicacion rendererPortalAplicacion) {
		this.renderizarPortalAplicacion = rendererPortalAplicacion;
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}


	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}


	public AImage getImagen() {
		return imagen;
	}


	public void setImagen(AImage imagen) {
		this.imagen = imagen;
	}

    public VMModeloArbolAvanzado getModeloMenuArbol() {
		return modeloMenuArbol;
	}


	public void setModeloMenuArbol(VMModeloArbolAvanzado modeloMenuArbol) {
		this.modeloMenuArbol = modeloMenuArbol;
	}
	public static VMNodoMenuArbol getRaizPortalInicial() {
		return raizPortalInicial;
	}


	public static void setRaizPortalInicial(VMNodoMenuArbol raizPortalInicial) {
		VMMenuAplicacion.raizPortalInicial = raizPortalInicial;
	}
	
	//Fin SETs y GETs
	
	@AfterCompose
	@Command
	@GlobalCommand
	@NotifyChange({"modeloMenuArbol"})
	public void Init(@ContextParam(ContextType.COMPONENT) Component windowindex,@ContextParam(ContextType.VIEW) Component view) {
		raizPortalInicial = new VMNodoMenuArbol(null,null);	
		VMNodoMenuArbol aux = null;
		ArrayList<Grupo> roles = new ArrayList<Grupo>(serviciousuario.rolesDelUsuario((seguridad.getUsuario().getUsername())));
		ArrayList<Nodo> nodosOrdenados = new ArrayList<Nodo>();
		for(Grupo rol : roles){
			nodosOrdenados.addAll(rol.getNodos());
		}	
		Collections.sort(nodosOrdenados, new Nodo());
		for (Nodo a : nodosOrdenados) {
			aux = new VMNodoMenuArbol(a);
			VMNodoMenuArbol ctreenodo = this.cargarPadre(aux);
			Integer j = raizPortalInicial.getChildCount();
			if (!(j.compareTo(0) == 0)) {
				this.cargarNodos(ctreenodo, raizPortalInicial);
			} else {
				raizPortalInicial.add(ctreenodo);
			}
		}		
		modeloMenuArbol = new VMModeloArbolAvanzado(raizPortalInicial);
	}


	public VMNodoMenuArbol cargarPadre(VMNodoMenuArbol nodo) {
		VMNodoMenuArbol padre=null;
			if(nodo.getData().getPadre()!=0){
				Nodo npadre=servicionodo.buscarNodo(nodo.getData().getPadre());
				padre=new VMNodoMenuArbol(npadre,null);
				padre.add(nodo);
				nodo=cargarPadre(padre);		
			}
			return nodo;
	}
	private void cargarNodos(VMNodoMenuArbol nodo,VMNodoMenuArbol raiz) { 
		boolean encontro=false;
		
		 for(int j=0;j< raiz.getChildCount();j++){
			  if(raiz.getChildAt(j).getData().getId().compareTo(nodo.getData().getId())==0){
				  for(int i=0;i< nodo.getChildCount();i++){
				    if(nodo.getChildCount()==1)
					cargarNodos((VMNodoMenuArbol) nodo.getChildAt(0),(VMNodoMenuArbol) raiz.getChildAt(j));  
				    else{
				    	 VMNodoMenuArbol aux = new VMNodoMenuArbol(nodo.getChildAt(i).getData(),null);
				         cargarNodos(aux,(VMNodoMenuArbol) raiz.getChildAt(j));
				     }
				  }
				    encontro=true;
			    }
		 }
		 if(!encontro)
			 raiz.add(nodo);
	}	
}
