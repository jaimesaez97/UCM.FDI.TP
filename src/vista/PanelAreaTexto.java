package vista;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public abstract class PanelAreaTexto extends JPanel{

	protected JTextArea areatexto;
	
	public PanelAreaTexto(String tit, boolean editable) {
		this.setLayout(new GridLayout(1, 1));
		this.areatexto = new JTextArea(40, 30);
		this.areatexto.setEditable(editable);
		this.add(new JScrollPane(areatexto));
		this.setBorde(tit);
		//this.setVisible(true);
	}

	public void setBorde(String tit) {
		this.setBorder(BorderFactory.createTitledBorder(tit));		
	}
	
	public String getTexto() {
		return this.areatexto.getText();
	}
	
	public void setTexto(String tex) {
		this.areatexto.setText(tex);
	}
	
	public void limpiar() {
		this.areatexto.setText("");
	}
	
	public void inserta(String value) {
		this.areatexto.insert(value, this.areatexto.getCaretPosition());
	}
}
