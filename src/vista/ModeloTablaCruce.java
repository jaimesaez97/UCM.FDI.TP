
		/**HACER LAS DE OST**/

package vista;

import java.util.List;

import javax.swing.SwingUtilities;

import control.Controlador;
import control.Evento;
import control.MapaCarreteras;
import es.ucm.fdi.exception.ErrorDeSimulacion;
import model.cruces.CruceGenerico;

@SuppressWarnings("serial")
public class ModeloTablaCruce extends ModeloTabla<CruceGenerico<?>> {

	public ModeloTablaCruce(String[] _columndIdEventos, Controlador ctrl) {
		super(_columndIdEventos, ctrl);
	}

	@Override	
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch(indiceCol) {
			case 0 : s = this.lista.get(indiceFil).getId(); break;
			case 1 : s = this.lista.get(indiceFil).getCarreterasVerde(); break;	
			case 2 : s = this.lista.get(indiceFil).getCarreterasRojo(); break;	
			default : assert (false);
		}
		return s;
	}
	
	@Override
	public void errorSimulador(int time, MapaCarreteras map, List<Evento> events, ErrorDeSimulacion e) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				lista = map.getCruces();
				fireTableStructureChanged();	
				
			}
			
		});
		
	}

	@Override
	public void avanza(int time, MapaCarreteras map, List<Evento> events) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				lista = map.getCruces();
				fireTableStructureChanged();	
				
			}
			
		});	
	}

	@Override
	public void addEvento(int time, MapaCarreteras map, List<Evento> events) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				lista = map.getCruces();
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
				lista = map.getCruces();
				fireTableStructureChanged();	
				
			}
			
		});
	}

}
