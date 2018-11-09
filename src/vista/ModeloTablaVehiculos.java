
		/**HACER LAS DE OST(ErrorSimulacion(), addEvento(), reinicia()**/

package vista;

import java.util.List;

import javax.swing.SwingUtilities;

import control.Controlador;
import control.Evento;
import control.MapaCarreteras;
import es.ucm.fdi.exception.ErrorDeSimulacion;
import model.vehiculos.Vehiculo;

@SuppressWarnings("serial")
public class ModeloTablaVehiculos extends ModeloTabla<Vehiculo>{
	 

	public ModeloTablaVehiculos(String[] _columndIdEventos, Controlador ctrl) {
		super(_columndIdEventos, ctrl);
		// TODO Auto-generated constructor stub
	}
	
	@Override	
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch(indiceCol) {
			case 0 : s = this.lista.get(indiceFil).getId(); break;
			case 1 : s = (this.lista.get(indiceFil).getRoad() == null) ? "arrived" : this.lista.get(indiceFil).getRoad().getId(); break;
			case 2 : s = this.lista.get(indiceFil).getCurLocationOnRoad(); break;
			case 3 : s = this.lista.get(indiceFil).getSpeed(); break;
			case 4 : s = this.lista.get(indiceFil).getKm(); break;
			case 5 : s = this.lista.get(indiceFil).getTiempoInfraccion(); break;
			case 6 : s = this.lista.get(indiceFil).getWayString(); break;
			default : assert (false);
		}
		return s;
	}

	@Override
	public void errorSimulador(int time, MapaCarreteras map,
			List<Evento> events, ErrorDeSimulacion e) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				lista = map.getCars();
				fireTableStructureChanged();	
			}
			
		});
		
	}

	@Override
	public void avanza(int time, MapaCarreteras map, List<Evento> events) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				lista = map.getCars();
				fireTableStructureChanged();	
			}
			
		});	
	}

	@Override
	public void addEvento(int time, MapaCarreteras map, List<Evento> events) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				lista = map.getCars();
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
				lista = map.getCars();
				fireTableStructureChanged();	
			}
			
		});	
	}

}
