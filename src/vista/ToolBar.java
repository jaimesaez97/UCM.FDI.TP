package vista;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import control.Controlador;
import control.Evento;
import control.MapaCarreteras;
import es.ucm.fdi.exception.ErrorDeSimulacion;

@SuppressWarnings("serial")
public class ToolBar extends JToolBar implements ObservadorSimuladorTrafico{
	private JSpinner steps;
	private JTextField timer;
	private JSpinner delay;
	private JButton botonRun;
	private JButton botonStop;
	private JButton botonReset;
	private JButton botonClear;
	private JButton botonCargar;
	private JButton botonGuardar;
	private JButton botonCheckIn;
	private JButton botonGeneraReports;
	private JButton botonLimpiaInformes;
	private JButton botonSaveReport;
	private JButton botonExit;
	
	public ToolBar(VentanaPrincipal window, Controlador ctrl) {
		super();
		ctrl.addObserver(this);
		this.steps = new JSpinner();
		this.timer = new JTextField();
		
		this.cargarEventos(window, ctrl);
		this.guardaEventos(window);
		this.clear(window);
		this.addSeparator();
		this.checkIn(window, ctrl);
		this.play(window, ctrl);
		this.stop(window, ctrl);
		this.reset(window, ctrl);
		this.delay(window, ctrl);
		this.spinner(ctrl);
		this.time();
		this.addSeparator();
		this.informes(window);
		this.reportClean(window);
		this.addSeparator();
		this.saveReport(window);
		this.addSeparator();
		this.exit(window);
		this.setVisible(true);
	}
	
	public int getSteps(){
		return (int) this.steps.getValue();
	}
	
	public long getDelay(){
		return (int) this.delay.getValue();
	}
	
	public void play(VentanaPrincipal window, Controlador ctrl) {
		this.botonRun = new JButton();
		botonRun.setToolTipText("Inicia el simulador");
		botonRun.setIcon(new ImageIcon("resources/icons/play.png"));
		botonRun.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.initHebra();
				window.hilo.start();
				/*P5::
				ctrl.ejecuta(ctrl.getSteps());
				window.setMensaje("Simulador iniciado!");*/
					// deshabilitar botones menos STOP
			}
		});
		this.add(botonRun);
	}	
	
	protected void deshabilitar(VentanaPrincipal window) {
		this.delay.setEnabled(false);
		this.steps.setEnabled(false);
		this.botonCargar.setEnabled(false);
		this.botonGuardar.setEnabled(false);
		this.botonRun.setEnabled(false);
		this.botonReset.setEnabled(false);
		this.botonClear.setEnabled(false);
		this.botonCheckIn.setEnabled(false);
		this.botonGeneraReports.setEnabled(false);
		this.botonLimpiaInformes.setEnabled(false);
		this.botonSaveReport.setEnabled(false);
		this.botonExit.setEnabled(false);
		window.disableMenu();
	}

	public void stop(VentanaPrincipal window, Controlador ctrl) {
		this.botonStop = new JButton();
		botonStop.setToolTipText("Inicia el simulador");
		botonStop.setIcon(new ImageIcon("resources/icons/stop.png"));
		botonStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(window.hilo.isAlive())	window.hilo.interrupt();
				habilitar(window);
			}
		});
		this.add(botonStop);
	}
	
	public void habilitar() {
		this.delay.setEnabled(true);
		this.steps.setEnabled(true);
		this.botonCargar.setEnabled(true);
		this.botonGuardar.setEnabled(true);
		this.botonRun.setEnabled(true);
		this.botonReset.setEnabled(true);
		this.botonClear.setEnabled(true);
		this.botonCheckIn.setEnabled(true);
		this.botonGeneraReports.setEnabled(true);
		this.botonLimpiaInformes.setEnabled(true);
		this.botonSaveReport.setEnabled(true);
		this.botonExit.setEnabled(true);
	}
	
	protected void habilitar(VentanaPrincipal window) {
		this.delay.setEnabled(true);
		this.steps.setEnabled(true);
		this.botonCargar.setEnabled(true);
		this.botonGuardar.setEnabled(true);
		this.botonRun.setEnabled(true);
		this.botonReset.setEnabled(true);
		this.botonClear.setEnabled(true);
		this.botonCheckIn.setEnabled(true);
		this.botonGeneraReports.setEnabled(true);
		this.botonLimpiaInformes.setEnabled(true);
		this.botonSaveReport.setEnabled(true);
		this.botonExit.setEnabled(true);
		window.enableMenu();
		
	}

	public void delay(VentanaPrincipal window, Controlador ctrl) {
		this.add(new JLabel(" Delay : "));
		this.delay = new JSpinner(new SpinnerNumberModel(5, 0, 1000, 1));
		this.delay.setToolTipText(" ms de hebra dormida ");
		this.delay.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				
			}		
		});
		this.delay.setMaximumSize(new Dimension(70, 70));
		this.delay.setMinimumSize(new Dimension(70, 70));
		this.delay.setValue(1);
		this.add(this.delay);
	}

	public void reset(VentanaPrincipal window, Controlador ctrl) {
		this.botonReset = new JButton();
		botonReset.setToolTipText("Inicia el simulador");
		botonReset.setIcon(new ImageIcon("resources/icons/reset.png"));
		botonReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.reinicia();
				window.setMensaje("Simulador reseteado!");
			}
		});
		this.add(botonReset);
	}
	
	public void clear(VentanaPrincipal window) {
		this.botonClear = new JButton();
		botonClear.setToolTipText("Limpia el informe");
		botonClear.setIcon(new ImageIcon("resources/icons/clear.png"));
		botonClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.limpiaInformes();
				window.setMensaje("Informes limpio!");
			}
		});
		this.add(botonClear);
	}

	public void cargarEventos(VentanaPrincipal window, Controlador ctrl) { //¿PRIVATE?
		this.botonCargar = new JButton();
		botonCargar.setToolTipText("Carga un fichero de eventos");
		botonCargar.setIcon(new ImageIcon("resources/icons/open.png"));
		//botonCargar.setIcon(new ImageIcon(Utils.loadImage("resources/icons/open.png")));
		botonCargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.reinicia();// Limpiar los paneles
				window.cargaFichero();
			}
		});
		this.add(botonCargar);
	}
	
	public void guardaEventos(VentanaPrincipal window) {	 //AÑADIDO POR JAIME 25/04
		this.botonGuardar = new JButton();
		botonGuardar.setToolTipText("Guarda un fichero de informes");
		botonGuardar.setIcon(new ImageIcon("resources/icons/save.png"));
		botonGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.guardaEventos();
			}
		});
		this.add(botonGuardar);
	}
	
	public void checkIn(VentanaPrincipal window, Controlador ctrl) {	//¿PRIVATE?
		this.botonCheckIn = new JButton();
		botonCheckIn.setToolTipText("Carga los eventos al simulador");
		botonCheckIn.setIcon(new ImageIcon("resources/icons/events.png"));
		//botonCheckIn.setIcon(new ImageIcon(Utils.loadImage("resources/icons/events.png")));
		botonCheckIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					//ctrl.reinicia();
					byte[] contenido = window.getTextoEditorEventos().getBytes();
					ctrl.cargaEventos(new ByteArrayInputStream(contenido));
				} catch(ErrorDeSimulacion err) { }
				window.setMensaje("Eventos cargados al simulador!");
			}		
		});
		this.add(botonCheckIn);
	}
	
	public void spinner(Controlador ctrl) {
		this.add(new JLabel(" Pasos : "));
		this.steps = new JSpinner(new SpinnerNumberModel(5, 1, 1000, 1));
		this.steps.setToolTipText("pasos a ejecutar: 1-1000");
		this.steps.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				ctrl.setSteps((int) steps.getValue());
			}		
		});
		this.steps.setMaximumSize(new Dimension(70, 70));
		this.steps.setMinimumSize(new Dimension(70, 70));
		this.steps.setValue(1);
		this.add(this.steps);
	}
	
	public void time() {
		this.add(new JLabel(" Tiempo: "));
		this.timer = new JTextField("0", 5);
		this.timer.setToolTipText("Tiempo actual");
		this.timer.setMaximumSize(new Dimension(70, 70));
		this.timer.setMinimumSize(new Dimension(70, 70));
		this.timer.setEditable(false);
		this.add(this.timer);
		this.timer.setVisible(true);
	}
		// OPCIONAl
	public void informes(VentanaPrincipal window) {
				// NO LO HEMOS HECHO PORQUE HACEMOS INFORMES PARTICULARES	
		this.botonGeneraReports = new JButton();
		botonGeneraReports.setToolTipText("Dialogo informes");
		botonGeneraReports.setIcon(new ImageIcon("resources/icons/report.png"));
		//botonGeneraReports.setIcon(new ImageIcon(Utils.loadImage("resources/icons/report.png")));
		botonGeneraReports.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				window.openDialogoInformes();
			}
		});
		this.add(botonGeneraReports);
	}
	
	private void reportClean(VentanaPrincipal window) {
		this.botonLimpiaInformes = new JButton();
		botonLimpiaInformes.setToolTipText("Limpia informes");
		botonLimpiaInformes.setIcon(new ImageIcon("resources/icons/delete_report.png"));
		//botonGeneraReports.setIcon(new ImageIcon(Utils.loadImage("resources/icons/report.png")));
		botonLimpiaInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					// Limpia Informes total, ¿verdad? 
				window.limpiaInformes();
			}
		});
		this.add(botonLimpiaInformes);
	
	}
	
	private void saveReport(VentanaPrincipal window) {
		this.botonSaveReport = new JButton();
		botonSaveReport.setToolTipText("Guarda informes");
		botonSaveReport.setIcon(new ImageIcon("resources/icons/save_report.png"));
		//botonGeneraReports.setIcon(new ImageIcon(Utils.loadImage("resources/icons/report.png")));
		botonSaveReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					// Limpia Informes total, ¿verdad? 
				window.guardaInformes();
			}
		});
		this.add(botonSaveReport);
	}

	private void exit(VentanaPrincipal window) {
		this.botonExit = new JButton();
		botonExit.setToolTipText("Salir");
		botonExit.setIcon(new ImageIcon("resources/icons/exit.png"));
		//botonGeneraReports.setIcon(new ImageIcon(Utils.loadImage("resources/icons/report.png")));
		botonExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					// Limpia Informes total, ¿verdad? 
				window.setVisible(false);
			}
		});
		this.add(botonExit);
	}
	
	@Override
	public void errorSimulador(int time, MapaCarreteras map, List<Evento> events, ErrorDeSimulacion e) {
			
	}

	@Override
	public void avanza(int time, MapaCarreteras map, List<Evento> events) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				timer.setText(""+time);					
			}
			
		});	
	}

	@Override
	public void addEvento(int time, MapaCarreteras map, List<Evento> events) {	// FUNCION CARGAR EVENTO
		
	}

	@Override
	public void reinicia(int time, MapaCarreteras map, List<Evento> events) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				steps.setValue(1);
				timer.setText("0");				
			}
			
		});	
		
	}

	

}
