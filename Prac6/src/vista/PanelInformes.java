package vista;

import java.util.List;

import javax.swing.SwingUtilities;

import control.Controlador;
import control.Evento;
import control.MapaCarreteras;
import es.ucm.fdi.exception.ErrorDeSimulacion;

@SuppressWarnings("serial")
public class PanelInformes extends PanelAreaTexto implements ObservadorSimuladorTrafico{

	public PanelInformes(String tit, boolean editable, Controlador ctrl) {
		super(tit, editable);
		ctrl.addObserver(this);	// se añade como observador
		this.setVisible(true);
	}

	@Override
	public void errorSimulador(int time, MapaCarreteras map, List<Evento> events, ErrorDeSimulacion e) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				areatexto.setText(map.generateReport(time));			
			}
			
		});
		
	}

	@Override
	public void avanza(int time, MapaCarreteras map, List<Evento> events) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				areatexto.setText(map.generateReport(time));			
			}
			
		});
	}

	@Override
	public void addEvento(int time, MapaCarreteras map, List<Evento> events) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				areatexto.setText(areatexto.getText() + map.generateReport(time));			
			}
			
		});
	}

	@Override
	public void reinicia(int time, MapaCarreteras map, List<Evento> events) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				limpiar();
				areatexto.setText(map.generateReport(time));			
			}
			
		});
	}

}
