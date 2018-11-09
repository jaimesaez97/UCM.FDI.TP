
		/**HACER LAS DE OST(errorSimulador)**/

package vista;

import java.util.List;

import javax.swing.SwingUtilities;

import control.Controlador;
import control.Evento;
import control.MapaCarreteras;
import es.ucm.fdi.exception.ErrorDeSimulacion;

@SuppressWarnings("serial")
public class ModeloTablaEventos extends ModeloTabla<Evento>{

	public ModeloTablaEventos(String[] _columndIdEventos, Controlador ctrl) {
		super(_columndIdEventos, ctrl);
	}
	@Override	
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch(indiceCol) {
			case 0 : s = indiceFil; break;
			case 1 : s = this.lista.get(indiceFil).getTime(); break;
			case 2 : s = this.lista.get(indiceFil).toString(); break;
			default : assert (false);
		}
		return s;
	}
	@Override
	public void avanza(int time, MapaCarreteras map, List<Evento> events) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				lista.clear();
				for(int i = 0; i < events.size(); ++i)
					if(events.get(i).getTime() > time)
						lista.add(events.get(i));
				
				fireTableStructureChanged();
			}
			
		});	
	}

	@Override
	public void addEvento(int time, MapaCarreteras map, List<Evento> events) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				if(!events.isEmpty()) {
					for(int i = 0; i < events.size(); i++) {
						if(!lista.contains(events.get(i)))
							lista.add(events.get(i));
					}			
				}
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
				lista.addAll(events);	
				fireTableStructureChanged();	
			}
			
		});
	}
	
	// ¿NECESARIO? ::
	@Override
	public void errorSimulador(int time, MapaCarreteras map, List<Evento> events, ErrorDeSimulacion e) {
		
	}


}
