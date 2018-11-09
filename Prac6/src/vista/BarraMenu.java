package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import control.Controlador;

@SuppressWarnings("serial")
public class BarraMenu extends JMenuBar {
		
	private JMenu menuFicheros;
	private JMenu menuSimulador;
	private JMenu menuInformes;
	private JMenuItem menuSimulador_run;
	private JMenuItem menuSimulador_reset;
	
	public BarraMenu(VentanaPrincipal mainView, Controlador ctrl) {
		super();
		//this.setVisible(true);
			//MANEJO FICHEROS
		this.menuFicheros = new JMenu("Ficheros");
		this.add(menuFicheros);
		this.creaMenuFicheros(menuFicheros, mainView, ctrl);
			//SIMULADOR
		this.menuSimulador = new JMenu("Simulador");
		this.add(menuSimulador);
		this.creaMenuSimulador(menuSimulador, ctrl, mainView);
			//INFORMES
		this.menuInformes = new JMenu("Informes");
		this.add(menuInformes);
		this.creaMenuInformes(menuInformes, mainView);
	}
	

	
	private void creaMenuFicheros(JMenu menu, VentanaPrincipal window, Controlador ctrl) {
		JMenuItem cargar = new JMenuItem("Carga Eventos:");
		cargar.setMnemonic(KeyEvent.VK_L);
		cargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		cargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.reinicia(); // Para limpiar paneles
				window.cargaFichero();
			}
		});
		menu.add(cargar);
		
		JMenuItem salvar = new JMenuItem("Salva Eventos:");
		salvar.setMnemonic(KeyEvent.VK_S);
		salvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		salvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.guardaEventos();
			}
		});
		menu.add(salvar);
		
		JMenuItem salvarInformes = new JMenuItem("Salva Informes:");
		salvarInformes.setMnemonic(KeyEvent.VK_R);
		salvarInformes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		salvarInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.guardaInformes();	// �Es esta?�
			}
		});
		menu.add(salvarInformes);
 
		JMenuItem salir = new JMenuItem("Salir");
		salir.setMnemonic(KeyEvent.VK_E);
		salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		salir.addActionListener(new ActionListener() {
		
	         public void actionPerformed(ActionEvent e){
	        	 if (JOptionPane.showConfirmDialog(window, "�Desea realmente salir del sistema?",
	                     "Salir del sistema", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
	                 System.exit(0);	
	        	 
	               window.dispose();
	        	
	            }


		});
		
		menu.add(salir);
	}
	
	private void enableMenuRun() {
		this.menuSimulador.setEnabled(true);		
	}
	
	private void creaMenuSimulador(JMenu menu, Controlador ctrl, VentanaPrincipal window) {
		this.menuSimulador_run = new JMenuItem("Run");
		menuSimulador_run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.initHebra();
				enableMenuRun();
				window.hilo.start();
			}

			
		});
		menu.add(menuSimulador_run);	
		JMenuItem stop = new JMenuItem("Stop");
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(window.hilo.isAlive())	window.hilo.interrupt();
				window.getToolBar().habilitar(window);
			}			
		});
		menu.add(stop);		
		menuSimulador_reset = new JMenuItem("Reset");
		menuSimulador_reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.reinicia();
			}
		});
		menu.add(menuSimulador_reset);
		/*
		JMenuItem redirect = new JMenuItem("Redirect Output");
		redirect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 ctrl.
			}
		});
		menu.add(redirect);
		*/
	}
	
	private void creaMenuInformes(JMenu menu, VentanaPrincipal window) {
		JMenuItem generaInformes = new JMenuItem("Generar");
		generaInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					// NO IMPLEMENTADA POR GENERAR INFORMES CONCRETOS
				//window.generaInformes();
				//window
				// LLAMADA A DIALOGO INFORMES?
				window.openDialogoInformes();
			}
		});
		menu.add(generaInformes);	
		
		JMenuItem limpiaAreaInformes = new JMenuItem("Clear");
		limpiaAreaInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					//OPCIONAL
				window.limpiaInformes();
			}
		});
		menu.add(limpiaAreaInformes);
	}
	
	public void deshabilitarMenus(){
		this.menuFicheros.setEnabled(false);
		this.menuInformes.setEnabled(false);
		this.menuSimulador_run.setEnabled(false);
		this.menuSimulador_reset.setEnabled(false);
	}
		
	public void habilitarMenus(){
		this.menuFicheros.setEnabled(true);
		this.menuInformes.setEnabled(true);
		this.menuSimulador.setEnabled(true);
		this.menuSimulador_run.setEnabled(true);
		this.menuSimulador_reset.setEnabled(true);
	}
	
}
