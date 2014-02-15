package sigarep.viewmodels.seguridad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Window;

import sigarep.herramientas.MensajesAlUsuario;

import sigarep.modelos.data.seguridad.Grupo;
import sigarep.modelos.data.seguridad.Nodo;
import sigarep.modelos.servicio.seguridad.ServicioGrupo;
import sigarep.modelos.servicio.seguridad.ServicioNodo;

/*
 * @ (#) Grupo.java 
 *
 * Copyright 2013 Builder. Todos los derechos reservados.
 * CONFIDENCIAL. El uso est� sujeto a los t�rminos de la licencia.
 */
/*
 ** Esta clase es el ViewModel del registro del maestro "Grupo"
 * UCLA DCYT Sistemas de Informacion.
 * @author Equipo: Builder-SIGAREP 
 * @Version 1.0,
 * @Since  04/02/13
 */

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class VMRegistrarGrupo {
	@Wire
	private Window demoWindowRegistrarGrupo;
	@Wire
	private Tree tree;
	@Wire
	private Tree tree2;
	private MensajesAlUsuario msjs = new MensajesAlUsuario();

	private @WireVariable ServicioNodo servicionodo;

	private VMAdvancedTreeModel contactTreeModel;
	private VMAdvancedTreeModel contactTreeModel2;
	private VMAdvancedTreeModel contactTreeModelAux;
	
	private static VMmenuTreeNode  root;
	private static VMmenuTreeNode  root2;
	
	@WireVariable
	private ServicioGrupo serviciogrupo;

	
	private Integer idGrupo; // clave primaria de la tabla Grupo
	private String nombre; 	// nombre del Grupo
    private String descripcion; // descripci�n del Grupo
    private String estado;
	private String nombreGrupofiltro = "";
	private String descripcionfiltro = "";
    private Grupo grupoAux = new Grupo();
    private Grupo grupoSeleccionado = new Grupo();
    private List<Grupo> listaGrupos = new LinkedList<Grupo>();
    private ListModelList<Nodo> modelonodos; // lista de los nodos u opciones del men� �rbol.
	MensajesAlUsuario mensajeAlUsuario = new MensajesAlUsuario();

	// Metodos GETS Y SETS
	
	public ServicioNodo getsnodo() {
		return servicionodo;
	}

	public void setsnodo(ServicioNodo snodo) {
		this.servicionodo = snodo;
	}
	
	public Tree getTree() {
		return tree;
	}


	public void setTree(Tree tree) {
		this.tree = tree;
	}


	public Tree getTree2() {
		return tree2;
	}


	public void setTree2(Tree tree2) {
		this.tree2 = tree2;
	}


	public VMAdvancedTreeModel getContactTreeModel() {
		return contactTreeModel;
	}


	public void setContactTreeModel(VMAdvancedTreeModel contactTreeModel) {
		this.contactTreeModel = contactTreeModel;
	}


	public VMAdvancedTreeModel getContactTreeModel2() {
		return contactTreeModel2;
	}


	public void setContactTreeModel2(VMAdvancedTreeModel contactTreeModel2) {
		this.contactTreeModel2 = contactTreeModel2;
	}

	public VMAdvancedTreeModel getContactTreeModelAux() {
		return contactTreeModelAux;
	}

	public void setContactTreeModelAux(VMAdvancedTreeModel contactTreeModelAux) {
		this.contactTreeModelAux = contactTreeModelAux;
	}

	public VMmenuTreeNode getRoot2() {
		return root2;
	}


	public void setRoot2(VMmenuTreeNode root2) {
		this.root2 = root2;
	}


	public void setRoot(VMmenuTreeNode root) {
		this.root = root;
	}


	public VMmenuTreeNode getRoot() {
		return root;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}


	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public ListModelList<Nodo> getModeloGrupo() {
		return modelonodos;
	}


	public void setModeloGrupo(ListModelList<Nodo> modeloNodo) {
		this.modelonodos = modeloNodo;
	}
	
	public List<Grupo> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List<Grupo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}
	
	public Grupo getGrupoSeleccionado() {
		return grupoSeleccionado;
	}

	public void setGrupoSeleccionado(Grupo grupoSeleccionado) {
		this.grupoSeleccionado = grupoSeleccionado;
	}
	
	public String getNombreGrupofiltro() {
		return nombreGrupofiltro;
	}

	public void setNombreGrupofiltro(String nombreGrupofiltro) {
		this.nombreGrupofiltro = nombreGrupofiltro;
	}

	public String getDescripcionfiltro() {
		return descripcionfiltro;
	}

	public void setDescripcionfiltro(String descripcionfiltro) {
		this.descripcionfiltro = descripcionfiltro;
	}
	
	// Fin de los metodos gets y sets
	// OTROS METODOS

	// Metodos que permite guardar el Grupo de usuario
	@Command
	@NotifyChange({ "idGrupo", "descripcion", "nombre","grupoAux","listaGrupos","contactTreeModel","contactTreeModel2"})
	public void guardarGrupo(){
		Grupo grupo1;
		Set<Nodo> nodos = new HashSet<Nodo>();
		if (nombre.length() == 0 || descripcion.length() == 0)
			mensajeAlUsuario.advertenciaLlenarCampos();
		// else if(serviciogrupo.buscarGrupoNombre(nombre)!=null)
		// msjs.advertenciaGrupoYaExistente(nombre);
		else if (root2.getChildren().size() > 0) {
			if (grupoAux == null) {
				grupo1 = new Grupo();
				grupo1.setIdGrupo(idGrupo);
			} else grupo1 = grupoAux;

			grupo1.setDescripcion(descripcion);
			grupo1.setNombre(nombre);
			grupo1.setEstatus(true);

			if (root2.getChildCount() > 0) {
				for (TreeNode<Nodo> a2 : root2.getChildren()) {
					cargarHijosGrupo(a2, nodos);
				}
			}
			grupo1.setNodos(nodos);
			serviciogrupo.guardarGrupo(grupo1);
			limpiar();
			mensajeAlUsuario.informacionRegistroCorrecto();
		} else
			mensajeAlUsuario.advertenciaMenudelGrupoVacio();
	}

	@AfterCompose
	public void Init(Component comp) throws Exception {
		cargarArbol();	
		contactTreeModel = new VMAdvancedTreeModel(root); //modelo del men� arbol de las funciones que no tiene el grupo (es variable)
		contactTreeModelAux = new VMAdvancedTreeModel(root); //modelo de men� arbol que contendr� todos los nodos (no es variable) para restaurar el contactTreeModel en el comando limpiar. 
		root2 = new VMmenuTreeNode(null,null); //modelo del men� arbol de las funciones del grupo (es variable).
		contactTreeModel2 = new VMAdvancedTreeModel(root2);
		buscarListadoGrupos();
	}
	
	// Metodo que carga los nodos del men� arbol
	public void cargarArbol(){
		root = new VMmenuTreeNode(null,null);
		VMmenuTreeNode aux=null;
		ArrayList<Nodo> nodosPorPadreOrdenados = new ArrayList<Nodo>(servicionodo.buscarNodosPorPadre(0));
		Collections.sort(nodosPorPadreOrdenados, new Nodo());
		for(Nodo a:nodosPorPadreOrdenados){
			aux=new VMmenuTreeNode(a,null);
		    this.cargarHijos(aux,a);
			root.add(aux);
		}
	}

	// Metodo que carga los nodos hijos a los nodos padres
	public void cargarHijos(VMmenuTreeNode m1, Nodo a1) {
		VMmenuTreeNode m2 = null;
		ArrayList<Nodo> buscarPadreOrdenadamente = new ArrayList<Nodo>(servicionodo.buscarNodosPorPadre(a1.getId()));
		Collections.sort(buscarPadreOrdenadamente, new Nodo());
		
			for(Nodo a2:buscarPadreOrdenadamente){
				m1.setOpen(true);
				if(a2.esFuncion()==false)
					m2= new VMmenuTreeNode(a2,null);	
				else
					m2= new VMmenuTreeNode(a2);
				m1.add(m2);
		    if(!servicionodo.buscarNodosPorPadre(a2.getId()).isEmpty())
		    	cargarHijos(m2,a2);
			}
	}
	
	// Metodo que carga los hijos del grupo de usuario
	public void cargarHijosGrupo(TreeNode<Nodo> root, Set<Nodo> nodos) {
		if (root.getChildCount() > 0) {
			for (TreeNode<Nodo> a2 : root.getChildren()) {
				if (a2.getData().esFuncion()==false) {
					cargarHijosGrupo(a2, nodos);
				} else {
					nodos.add(a2.getData());
				}
			}
		} else {
			nodos.equals(root.getData());
		}
	}
	
	@Command
	@NotifyChange({ "nombre", "descripcion", "contactTreeModel2","contactTreeModel","contactTreeModelAux","root","root2","listaGrupos"})
	public void limpiar(){
		nombre = null;
		descripcion = null;
		root = new VMmenuTreeNode(null,null);
		contactTreeModel = contactTreeModelAux;
		root2 = new VMmenuTreeNode(null,null);
		contactTreeModel2 = new VMAdvancedTreeModel(root2);
		buscarListadoGrupos();
	}
	
	
	@Command
	@NotifyChange({ "nombre", "descripcion","grupoAux","contactTreeModel","contactTreeModel2","grupoSeleccionado"})
	public void buscarGrupo(){
		try {
			Grupo g=serviciogrupo.buscarGrupoNombre(grupoSeleccionado.getNombre());
			ArrayList<Nodo> nodosOrdenados = new ArrayList<Nodo>(g.getNodos());		
			Collections.sort(nodosOrdenados, new Nodo());
			root2 = new VMmenuTreeNode(null,null);
			cargarMenu(nodosOrdenados,root2);
			contactTreeModel2 = new VMAdvancedTreeModel(root2);
			root = new VMmenuTreeNode(null,null);
			cargarMenu(servicionodo.funcionesGrupoNoPerteneceGrupo(g.getIdGrupo()), root);
			contactTreeModel = new VMAdvancedTreeModel(root);
			grupoAux = g;
			nombre = grupoAux.getNombre();
			descripcion = grupoAux.getDescripcion();				
		} catch (Exception e) {
			System.out.println("Grupo no registrado, puede proceder a registrarlo");
		}
	}
	
	public void cargarMenu(List<Nodo> nodos, VMmenuTreeNode root){
		VMmenuTreeNode aux=null;
		for(Nodo a:nodos){
			aux=new VMmenuTreeNode(a);
			VMmenuTreeNode ctreenodo= this.cargarPadre(aux);
			ctreenodo.setOpen(true);
			Integer j = root.getChildCount();
			if(!(j.compareTo(0)==0)){
				this.cargarNodos(ctreenodo,root);
			}else{
				root.add(ctreenodo);
			}	
		}	
	}
	
	public VMmenuTreeNode cargarPadre(VMmenuTreeNode nodo) {
		VMmenuTreeNode padre=null;
			if(nodo.getData().getPadre()!=0){
				Nodo npadre=servicionodo.buscarNodo(nodo.getData().getPadre());
				padre=new VMmenuTreeNode(npadre,null);
				nodo.setOpen(true);
				padre.add(nodo);
				nodo=cargarPadre(padre);		
			}
			return nodo;
	}
	
	private void cargarNodos(VMmenuTreeNode nodo,VMmenuTreeNode root) { 
		boolean encontro=false;
		 for(int j=0;j< root.getChildCount();j++){
			  if(root.getChildAt(j).getData().getId().compareTo(nodo.getData().getId())==0){
				  for(int i=0;i< nodo.getChildCount();i++){
				    if(nodo.getChildCount()==1)
				    	 cargarNodos((VMmenuTreeNode) nodo.getChildAt(0),(VMmenuTreeNode) root.getChildAt(j));  
				    else{
				    	 VMmenuTreeNode aux = new VMmenuTreeNode(nodo.getChildAt(i).getData(),null);
				    	 cargarNodos(aux,(VMmenuTreeNode) root.getChildAt(j));
				     }
				  }
				    encontro=true;
			    }
		 }
		 if(!encontro){
			 root.add(nodo);
			 root2.getIndex(nodo);
		 }
	}	
	
	@Command
	@NotifyChange({ "listaGrupos"})
	public void buscarListadoGrupos(){
		listaGrupos = serviciogrupo.listadoGrupo();
	}
	
	// M�todo que busca y filtra los usuarios
	@Command
	@NotifyChange({"listaGrupos"})
	public void filtros(){
		listaGrupos = serviciogrupo.buscarGrupoFiltro(nombreGrupofiltro, descripcionfiltro);
	}
	
}
