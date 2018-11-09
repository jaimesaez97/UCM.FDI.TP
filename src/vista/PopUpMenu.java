
		/**HACER LAS DE OST**/

package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import control.ConstructorEventos;
import control.Evento;
import control.MapaCarreteras;
import control.ParserEventos;
import es.ucm.fdi.exception.ErrorDeSimulacion;

@SuppressWarnings("serial")
public class PopUpMenu extends JPopupMenu implements ObservadorSimuladorTrafico{

	public PopUpMenu(VentanaPrincipal window) {
		

		JMenu plantillas = new JMenu("Nueva plantilla");
		plantillas.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
			}		
		});
		this.add(plantillas);
		
		/***CREO QUE ESTO VA AQUÃ�***/
		for(ConstructorEventos ce : ParserEventos.getConstructoresEventos()){
			JMenuItem mi = new JMenuItem(ce.toString());
			mi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					window.inserta(ce.template() + System.lineSeparator());
				}
			});
			plantillas.add(mi);
		}
		
		JMenu cargar = new JMenu("Cargar");
		cargar.addMouseListener(new MouseListener() {
			
			public void mouseClicked(MouseEvent e) {
				window.cargaFichero();				
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		this.add(cargar);
		
		JMenu salvar = new JMenu("Salvar");
		salvar.addMouseListener(new MouseListener() {
			
			public void mouseClicked(MouseEvent e) {
				window.guardaEventos();				
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		this.add(salvar);
		
		JMenu limpiar = new JMenu("Limpiar");
		limpiar.addMouseListener(new MouseListener() {
			

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					window.limpiarEventos();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}			
		});
		this.add(limpiar);	
	}
	@Override
	public void errorSimulador(int time, MapaCarreteras map, List<Evento> events, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void avanza(int time, MapaCarreteras map, List<Evento> events) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEvento(int time, MapaCarreteras map, List<Evento> events) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reinicia(int time, MapaCarreteras map, List<Evento> events) {
		// TODO Auto-generated method stub
		
	}

}
