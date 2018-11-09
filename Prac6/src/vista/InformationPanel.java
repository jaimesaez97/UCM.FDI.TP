
		/**HACER TODO**/

package vista;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class InformationPanel extends JPanel{

	
	public InformationPanel() {
		this.initGUI();
		this.setVisible(true);
	}
	
	private void initGUI(){
		this.setBorder(BorderFactory.createTitledBorder("Generar Informes"));
		JLabel msg1 = new JLabel("Pulsa c para deseleccionar todos\nPulsa ctrl + a para seleccionar todos");
		msg1.setFont(new Font("Verdana", 	1, 14));
		this.add(msg1);
	}
}
