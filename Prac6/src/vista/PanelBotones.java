package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelBotones extends JPanel{

	private int _status;
		
	public PanelBotones(DialogoInformes dialogoInformes) {
		this.initGUI(dialogoInformes);
		this.setVisible(true);
		this._status = -1;
	}
	
	private void initGUI(DialogoInformes dialogoInformes){
		JButton cancel = new JButton("Cancel");
		JButton generate = new JButton("Generate");
		
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setStatus(0);
			}
		});
		
		generate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setStatus(1);
			}			
		});
		
		this.add(cancel);
		this.add(generate);
	}
	
	public int getStatus(){
		return this._status;
	}
	
	public void setStatus(int stat) {
		this._status = stat;
	}
}
