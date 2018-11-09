package vista;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class PanelObjSim<T> extends JPanel {

	private ListModel<T> listModel;
	private JList<T> objList;
	
	public PanelObjSim(String titulo) {
		this.setBorder(BorderFactory.createTitledBorder(titulo));		
		this.listModel = new ListModel<T>();
		this.objList = new JList<T>(this.listModel);
		addCleanSelectionListener(objList);
		this.add(new JScrollPane(objList, 
				 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		this.setVisible(true);		
	}

	private void addCleanSelectionListener(JList<T> list) {
		list.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == DialogoInformes.TECLA_LIMPIAR) {
					list.clearSelection();
				}
				if(e.getKeyChar() == DialogoInformes.TECLA_SELECCIONAR){
					list.setSelectionInterval(0, list.getAnchorSelectionIndex());
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	public int getListSize() {
		return this.listModel.getSize();
	}
	
	public List<T> getSelectedItems() {
		List<T> l = new ArrayList<>();
		for(int i : this.objList.getSelectedIndices()) {
			l.add(listModel.getElementAt(i));
		}
		return l;
	}
	
	public void setList(List<T> list) { this.listModel.setList(list); }
}
