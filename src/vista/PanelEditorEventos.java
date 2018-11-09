package vista;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelEditorEventos extends PanelAreaTexto{

	public PanelEditorEventos(String tit, String texto, boolean editable, VentanaPrincipal mainWindow) {
		super(tit, editable);
		//this.setVisible(true);
		this.setTexto(texto);
			// OPCIONAL
		PopUpMenu popUp = new PopUpMenu(mainWindow);
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
	    panel.setLayout(new BorderLayout());
	    frame.add(panel);
	    panel.setComponentPopupMenu(popUp);		
		this.areatexto.setInheritsPopupMenu(true);
		this.areatexto.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(e.isPopupTrigger() && areatexto.isEnabled()) {
					popUp.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3){
					popUp.setVisible(true);
					popUp.show(e.getComponent(), e.getX(), e.getY());
				}
				else if ( e.getButton() == MouseEvent.BUTTON1){
					popUp.setSelected(areatexto);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
