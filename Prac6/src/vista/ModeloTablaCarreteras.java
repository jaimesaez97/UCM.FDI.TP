
		/**HACER LAS DE OST**/

package vista;

import java.util.List;

import javax.swing.SwingUtilities;

import control.Controlador;
import control.Evento;
import control.MapaCarreteras;
import es.ucm.fdi.exception.ErrorDeSimulacion;
import model.carreteras.Carretera;

@SuppressWarnings("serial")
public class ModeloTablaCarreteras extends ModeloTabla<Carretera> {

	public ModeloTablaCarreteras(String[] _columndIdEventos, Controlador ctrl) {
		super(_columndIdEventos, ctrl);
		// TODO Auto-generated constructor stub
	}

	@Override	
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch(indiceCol) {
			case 0 : s = this.lista.get(indiceFil).getId(); break;
			case 1 : s = this.lista.get(indiceFil).getCruceOrigen().getId(); break;
			case 2 : s = this.lista.get(indiceFil).getCruceDestino().getId(); break;
			case 3 : s = this.lista.get(indiceFil).getLength(); break;
			case 4 : s = this.lista.get(indiceFil).maxSpeed(); break;
			case 5 : s = this.lista.get(indiceFil).getStringCars(); break;	// ASÃ� VALE?Â¿?Â¿
			default : assert (false);
		}
		return s;
	}

	
	@Override
	public void errorSimulador(int time, MapaCarreteras map, List<Evento> events, ErrorDeSimulacion e) {
		
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
					
				lista = map.getCarreteras();
				fireTableStructureChanged();	
			}
			
		});
	}

	@Override
	public void avanza(int time, MapaCarreteras map, List<Evento> events) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				lista = map.getCarreteras();
				fireTableStructureChanged();		
				
			}
			
		});
	}

	@Override
	public void addEvento(int time, MapaCarreteras map, List<Evento> events) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				lista = map.getCarreteras();
				fireTableStructureChanged();		
				
			}
			
		});	
	}

	@Override
	public void reinicia(int time, MapaCarreteras map, List<Evento> events) {
		
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				lista.clear();
				lista = map.getCarreteras();
				fireTableStructureChanged();		
				
			}
			
		});	
	}

}
