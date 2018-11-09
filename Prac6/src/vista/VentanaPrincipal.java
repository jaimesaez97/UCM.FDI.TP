/**DUDAS:
 * 
 * 1.  PanelObjSim() -> En el Dialogo de Informes no se leen los cruces, coches y carreteras a pesar 
 * 	   de que se añaden bien (he sacado el tamaño de la listModel por pantalla y se añaden bien.
 * 2.  this/initGUI() -> ¿Dónde pongo el algoritmo de las lineas 136-145 (insercion de los informes
 * 	   de los elementos seleccionaods) para que se ejecute? ¿En avanza?
 * 			 2 maneras de plantear DialogoInformes:
 * 				1 -> que este siempre y tu cuando quieras le des a generar (hay que ahcer una funcion que vaya comprobando si le das)
 * 				2 -> que se abra y hasta que no se cierre no continue el programa con 2 posibles salidas: 
 * 						-no se genera ninguno especifico
 * 						-se generan pero solo se pregunta ahi
 *		SOL => ¿HAY QUE AÑADIR PARAMETRO BOOLEANO A CADA FUNCION DEL OBSERVADOR QUE INDQUE SI HAY QUE SELECCIONAR EL REPORT NORMAL O EL PARTICULAR?
 * 3. this/initGUI() -> YES_NO_OPTION para la salida de ventana esta mal : si le das a NO tambien se sale
 * **/


	/**HACER LAS DE OST Y MUESTRADIALOGOERROR**/

package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

import model.carreteras.Carretera;
import model.cruces.CruceGenerico;
import model.vehiculos.Vehiculo;
import control.Controlador;
import control.Evento;
import control.MapaCarreteras;
import es.ucm.fdi.exception.ErrorDeSimulacion;

@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame implements ObservadorSimuladorTrafico{
	
	/******************/
	public Thread hilo;
	/******************/
	
	
	public static Border bordePorDefecto = BorderFactory.createLineBorder(Color.black, 2); 
	
		// PANEL SUPERIOR
	static private final String[] columnIdEventos = {"#", "Tiempo", "Tipo"};	
	private PanelAreaTexto panelEditorEventos;
	private PanelAreaTexto panelInformes;
	private PanelTabla<Evento> panelColaEventos;
	
		// MENU AND TOOL BAR
	private JFileChooser fc;
	private ToolBar toolbar;
	private BarraMenu menuBar;
	
		// GRAPHIC PANEL
	private ComponenteMapa componenteMapa;
	
		// STATUS BAR
	private PanelBarraEstado panelBarraEstado;
	
		// INFERIOR PANEL
	static private final String[] columndIdVehiculo = {"ID", "Carretera", "Localizacion", "Vel", "Km", "Tiempo. Ave.", "Itinerario"};
	static private final String[] columndIdCarretera = {"ID", "Origen", "Destino", "Longitud", "Vel.max.", "Vehiculos"};
	static private final String[] columndIdCruce = {"ID", "Verde", "Rojo"};
	
	private PanelTabla<Vehiculo> panelVehiculos;
	private PanelTabla<Carretera> panelCarreteras;
	private PanelTabla<CruceGenerico<?>> panelCruces;
	
		// REPORT DIALOG
	private DialogoInformes dialogoInformes; 
	
		// MODEL PART - VIEW CONTROLLER MODEL
	private File ficheroActual;
	private Controlador controlador;

	public VentanaPrincipal(String inFile, Controlador ctrl) throws FileNotFoundException{
		super("Simulador de Trafico");
		this.setVisible(true);
		this.setIconImage (new ImageIcon("resources/icons/play.png").getImage());
		this.controlador = ctrl;
		this.ficheroActual = inFile != null ? new File(inFile) : null;
		this.initGUI();
		ctrl.addObserver(this);	
	}
	
	public void initHebra(){
		if (this.hilo!=null){
			this.toolbar.deshabilitar(this); 
			this.hilo = new Thread(){
				public void run(){
					try{
						int i = toolbar.getSteps();
						while(i > 0 && Thread.currentThread().isAlive()){
							Thread.sleep(toolbar.getDelay() * 1000);
							controlador.ejecuta(1);
							i--;
						}
						toolbar.habilitar();	// Hay que llamarlo aqui para que se
						enableMenu();			// habilite al finalizar la hebra
					}catch(InterruptedException e){
						
					}
				}
			};
		}
		
	}
	
	private void initGUI() {
		this.setSize(new Dimension(1250, 1250));	// Le damos un valor a la DIMENSION
		// TODO Auto-generated method stub
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
			 public void windowActivated(WindowEvent e){
				
			 }
	         public void windowClosed(WindowEvent e){}
	         public void windowDeactivated(WindowEvent e){}
	         public void windowDeiconified(WindowEvent e){}
	         public void windowIconified(WindowEvent e){}
	         public void windowOpened(WindowEvent e){}
	         public void windowClosing(WindowEvent e){
	        	 	// al salir pide confirmación
	        	 if (JOptionPane.showConfirmDialog(rootPane, "�Desea realmente salir del sistema?",
	                     "Salir del sistema", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
	                 System.exit(0);	 // IF ARREGLADO
	                 
	                 e.getWindow().dispose();
	        	 }
	        	        		 
	               
	            }
		});
		
			// PANEL PPAL
		JPanel panelPrincipal = this.creaPanelPrincipal();
		panelPrincipal.setVisible(true);
		this.setContentPane(panelPrincipal);

			// BARRA DE ESTADO INFERIOR: contiene una JLabel para mostrar sin estatus
		this.addBarraEstado(panelPrincipal);
		
			// PANEL QUE CONTIENE RESTO DE COMPONENTES -> lo dividimos en 2
		JPanel panelCentral = this.creaPanelCentral();
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);

			// PANEL SUPERIOR 
		this.createPanelSuperior(panelCentral);
		
			// MENU
		this.menuBar = new BarraMenu(this, this.controlador);
		this.setJMenuBar(menuBar);

			// PANEL INFERIOR
		this.createPanelInferior(panelCentral);

			// BARRA DE HERRAMIENTAS
		this.addToolBar(panelPrincipal);	
		
			// FILE CHOOSER
		this.fc = new JFileChooser("C:/hlocal");
		
	}

	

	private void addToolBar(JPanel panelPrincipal) {
		this.toolbar = new ToolBar(this, this.controlador);
		panelPrincipal.add(this.toolbar, BorderLayout.PAGE_START);
		this.toolbar.setVisible(true);
	}

	private void createPanelInferior(JPanel panelCentral) {
		JPanel panelInferior = new JPanel(new GridLayout(1, 2));	  
		JPanel grafica = new JPanel();
			

		this.panelVehiculos = new PanelTabla<Vehiculo>("Vehiculos",
				new ModeloTablaVehiculos(VentanaPrincipal.columndIdVehiculo,
				this.controlador));
		this.panelCarreteras = new PanelTabla<Carretera>("Carreteras",
				new ModeloTablaCarreteras(VentanaPrincipal.columndIdCarretera,
				this.controlador));
		this.panelCruces = new PanelTabla<CruceGenerico<?>>("Cruces",
				new ModeloTablaCruce(VentanaPrincipal.columndIdCruce,
				this.controlador));
		
		
		this.componenteMapa = new ComponenteMapa(this.controlador);
				// añadir un ScrollPanel al inferior donde se coloca la componente
		
		grafica.setLayout(new GridLayout(3, 1));
		grafica.add(this.panelVehiculos);
		grafica.add(this.panelCarreteras);
		grafica.add(this.panelCruces);
		
		panelInferior.add(grafica, BorderLayout.WEST);
		panelInferior.add(new JScrollPane(componenteMapa), BorderLayout.EAST);
		panelCentral.add(panelInferior);
	}

	private JPanel creaPanelPrincipal() {
		JPanel panelPrincipal = new JPanel(new BorderLayout());	
		panelPrincipal.setVisible(true);
		return panelPrincipal;
	}

	private void createPanelSuperior(JPanel panelCentral) {
		JPanel panelSuperior = new JPanel();
			// para colocar el panel superior e inferior
		panelSuperior.setLayout(new GridLayout(1, 3));
		panelCentral.add(panelSuperior, BoxLayout.X_AXIS);	// "Creamos un panel en el eje de las X"
		this.panelEditorEventos = new PanelEditorEventos("Eventos","",true,this); // "" es texto, es OPCIONAL
		panelSuperior.add(this.panelEditorEventos, BorderLayout.WEST);//W izda
		this.panelColaEventos = new PanelTabla<Evento>("Cola Eventos: ",
			 new ModeloTablaEventos(VentanaPrincipal.columnIdEventos,
			 this.controlador)); // Es observadora
		panelSuperior.add(this.panelColaEventos, BorderLayout.CENTER);
		this.panelInformes = new PanelInformes("Informes: ",true,
			 this.controlador);
		panelSuperior.add(this.panelInformes, BorderLayout.EAST);
	}

	private void addBarraEstado(JPanel panelPrincipal) {
		this.panelBarraEstado = new PanelBarraEstado("Bienvenido al simulador !",this.controlador);
		panelPrincipal.add(this.panelBarraEstado, BorderLayout.PAGE_END);
		this.panelBarraEstado.setVisible(true);
	}

	private JPanel creaPanelCentral() {
		JPanel panelCentral = new JPanel();
			// para colocar el panel superior e inferioR
		panelCentral.setLayout(new GridLayout(2, 1));
		panelCentral.setVisible(true);
		return panelCentral;
	}

	@Override
	public void errorSimulador(int time, MapaCarreteras map, List<Evento> events, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
		this.componenteMapa.errorSimulador(time,  map,  events,  e);
		this.muestraDialogoError(e.getMessage());
	}

	@Override
	public void avanza(int time, MapaCarreteras map, List<Evento> events) {
		this.componenteMapa.avanza(time, map, events);
	}

	@Override
	public void addEvento(int time, MapaCarreteras map, List<Evento> events) {
		// TODO Auto-generated method stub
		this.componenteMapa.addEvento(time,  map,  events);
	}

	@Override
	public void reinicia(int time, MapaCarreteras map, List<Evento> events) {
		// TODO Auto-generated method stub
		this.componenteMapa.reinicia(time,  map,  events);
	}

	public void cargaFichero() {
		int returnVal = this.fc.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File file = this.fc.getSelectedFile();
			try {	
				String s = leeFichero(file);
				this.ficheroActual = file;
				this.panelEditorEventos.setTexto(s);	
				this.panelEditorEventos.setBorde(this.ficheroActual.getName());
				this.panelBarraEstado.setMensaje("Fichero " + file.getName() + " de eventos cargado en el editor");
			} catch(IOException e) {
				this.muestraDialogoError("Error durante la lectura del fichero: " + e.getMessage());
			}
		}
	}
	
	private String leeFichero(File file) throws IOException {
		String s = "";
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String linea = "";
		while((linea = br.readLine()) != null)
			s += linea + "\n";
		
		br.close();
		fr.close();
		return s;
	}


	private void muestraDialogoError(String string) {
		// TODO Auto-generated method stub	
		JPanel panel = new JPanel();
		panel.setSize(new Dimension(70, 210));
		panel.add(new JLabel(string));
		panel.setVisible(true);
	}

	public void generaInformes(String tex) {
		this.panelInformes.inserta(tex);
	}

	public void limpiaInformes() {
		this.panelInformes.limpiar();
	}

	public int getSteps() {
		//return this.controlador.getSteps();
		// No pueden venir de ahi: hay que pedirlos
		return this.controlador.getSteps();
	}

	public String getTextoEditorEventos() {
		return this.panelEditorEventos.getTexto();
	}

	public void setMensaje(String msj) {
			// pone mensaje en la barra de estado
		this.panelBarraEstado.setMensaje(msj);
	}

	public void inserta(String string) {
		// Inserta de POP UP MENU
		// inserta las plantillas al panelAreaTexto
		this.panelEditorEventos.inserta(string);
	}

	public void limpiarEventos() {
		// vacia area de texto correspondiente a eventos
		this.panelEditorEventos.limpiar();		
	}
	
	public ToolBar getToolBar(){
		return this.toolbar;
	}

	public void guardaEventos(){
		int returnVal = this.fc.showSaveDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File file = this.fc.getSelectedFile();
			String s = this.panelEditorEventos.getTexto();
			
			try {
				// escribir s en un fichero
				FileWriter fr = new FileWriter(file);
				BufferedWriter br = new BufferedWriter(fr);
				
				br.write(s);
				br.close();
				fr.close();
				this.panelBarraEstado.setMensaje("Eventos guardados en el archivo" + file.getName());
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void guardaInformes() {
		// Contenido panel informes en un fichero
		int returnVal = this.fc.showSaveDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File file = this.fc.getSelectedFile();
			String s = this.panelInformes.getTexto();
			try {
				// escribir s en un fichero
				FileWriter fr = new FileWriter(file);
				BufferedWriter br = new BufferedWriter(fr);
				
				br.write(s);
				this.panelBarraEstado.setMensaje("Informes guardados en el archivo " + file.getName());
				
				br.close();
				fr.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void openDialogoInformes() {
		this.dialogoInformes = new DialogoInformes(this, controlador);	
		//this.pack();
		this.panelVehiculos.repaint();
		if(this.dialogoInformes.open() == 0){
			System.out.println("LEE STATUS A 0");
			this.dialogoInformes.setVisible(false);
		}
		else if(this.dialogoInformes.open() == 1){
			System.out.println("LEE STATUS A 1");
			this.generaInformes(this.dialogoInformes.getVehiculosSeleccionados().toString() +
								this.dialogoInformes.getCarreterasSeleccionados().toString()+
								this.dialogoInformes.getCrucesSeleccionados().toString());
		}
	}

	public void disableMenu() {
		this.menuBar.deshabilitarMenus();
	}
	
	public void enableMenu(){
		this.menuBar.habilitarMenus();
	}

	
}
