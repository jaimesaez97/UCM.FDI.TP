package vista;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import control.Controlador;
import control.Evento;
import control.MapaCarreteras;
import es.ucm.fdi.exception.ErrorDeSimulacion;

@SuppressWarnings("serial")
public class PanelBarraEstado extends JPanel implements ObservadorSimuladorTrafico{

	private JLabel infoEjecucion;

	public PanelBarraEstado(String mensaje, Controlador ctrl) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.infoEjecucion = new JLabel(mensaje);
		this.add(this.infoEjecucion);
		this.setBorder(BorderFactory.createBevelBorder(1));
		ctrl.addObserver(this);
		//this.setVisible(true);
	}
	
	public void setMensaje(String msj) { 
		this.infoEjecucion.setText(msj);
	}

	@Override
	public void avanza(int time, MapaCarreteras map, List<Evento> events) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				infoEjecucion.setText("Paso: " + time + " del Simulador");
			}
			
		});
		
	}

	@Override
	public void addEvento(int time, MapaCarreteras map, List<Evento> events) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				infoEjecucion.setText("Se aniade evento");
			}
			
		});	}

	@Override
	public void reinicia(int time, MapaCarreteras map, List<Evento> events) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				infoEjecucion.setText("Se reinicia");
			}
			
		});	}
	
	@Override
	public void errorSimulador(int time, MapaCarreteras map, List<Evento> events, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				infoEjecucion.setText("Error del simulador");
			}
			
		});
	}

}
