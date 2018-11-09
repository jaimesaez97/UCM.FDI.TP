package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JDialog;
import model.carreteras.Carretera;
import model.cruces.CruceGenerico;
import model.vehiculos.Vehiculo;
import control.Controlador;
import control.Evento;
import control.MapaCarreteras;
import es.ucm.fdi.exception.ErrorDeSimulacion;

@SuppressWarnings("serial")
public class DialogoInformes extends JDialog implements ObservadorSimuladorTrafico {

	protected static final char TECLA_LIMPIAR = KeyEvent.VK_C;	
	protected static final char TECLA_SELECCIONAR = KeyEvent.CTRL_MASK + KeyEvent.VK_A;
	private PanelBotones panelBotones;
	private PanelObjSim<Vehiculo> panelVehiculos;
	private PanelObjSim<Carretera> panelCarreteras;
	private PanelObjSim<CruceGenerico<?>> panelCruces;
	
	public DialogoInformes(VentanaPrincipal ventanaPrincipal, Controlador controlador) {
		initGUI(ventanaPrincipal);	// �Bien pasando parametro?
		//this.setSize(new Dimension(500, 500));
		// this.setVisible(true) NO LO PONGO VISIBLE -> HAY QUE PULSARLO
		this.setVisible(false);
		controlador.addObserver(this);
	}
	
	private void initGUI(VentanaPrincipal ventanaPrincipal) {
		InformationPanel panelInfo = new InformationPanel();	
		JDialog panelPrincipal = new JDialog();
		panelPrincipal.setSize(new Dimension(1000, 500));
		panelPrincipal.setLocation(450, 300);
		panelPrincipal.add(panelInfo, BorderLayout.PAGE_START);
	
		this.panelVehiculos = new PanelObjSim<Vehiculo>("Vehiculos");
		this.panelCarreteras = new PanelObjSim<Carretera>("Carreteras");
		this.panelCruces = new PanelObjSim<CruceGenerico<?>>("Cruces");
		this.panelBotones = new PanelBotones(this);
		this.panelVehiculos.setVisible(true);
		
		panelPrincipal.add(this.panelVehiculos, BorderLayout.WEST);
		panelPrincipal.add(this.panelCarreteras, BorderLayout.CENTER);
		panelPrincipal.add(this.panelCruces, BorderLayout.EAST);
		panelPrincipal.add(this.panelBotones, BorderLayout.PAGE_END);
		panelPrincipal.setVisible(true);
	}
	
	
	
	public void mostrar() {
		this.setVisible(true);
	}
	
	public List<Vehiculo> getVehiculosSeleccionados(){
		return this.panelVehiculos.getSelectedItems();
	}
	
	public void close(){
		this.dispose();
	}
	
	public List<Carretera> getCarreterasSeleccionados(){
		return this.panelCarreteras.getSelectedItems();
	}
	
	public List<CruceGenerico<?>> getCrucesSeleccionados(){
		return this.panelCruces.getSelectedItems();
	}

	@Override
	public void errorSimulador(int time, MapaCarreteras map, List<Evento> events, ErrorDeSimulacion e) {
		this.setMapa(map);
		
	}

	@Override
	public void avanza(int time, MapaCarreteras map, List<Evento> events) {
		this.setMapa(map);
		
	}

	private void setMapa(MapaCarreteras map) {
		this.panelVehiculos.setList(map.getCars());
		this.panelCarreteras.setList(map.getCarreteras());
		this.panelCruces.setList(map.getCruces());
		//Para comprobar funcionamiento DialogoInformes-> OK
		System.out.println("Lista coches : " + map.getCars().toString() + "--- Panel Vehiculos: " + this.panelVehiculos.getListSize());
		System.out.println("Lista rroads : " + map.getCarreteras().toString() + "--- Panel Carreteras: " + this.panelCarreteras.getListSize());
		System.out.println("Lista cruces : " + map.getCruces().toString() + "--- Panel Cruces: " + this.panelCruces.getListSize());
	}

	@Override
	public void addEvento(int time, MapaCarreteras map, List<Evento> events) {
		this.setMapa(map);
	}

	@Override
	public void reinicia(int time, MapaCarreteras map, List<Evento> events) {
		this.setMapa(map);
	}
	
	public int open() {
		return this.panelBotones.getStatus();
	}
}
