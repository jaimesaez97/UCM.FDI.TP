package vista;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import control.Controlador;

@SuppressWarnings("serial")

public abstract class ModeloTabla<T> extends DefaultTableModel implements ObservadorSimuladorTrafico{
	
	protected String[] columnIds;
	protected List<T> lista;
	
	public ModeloTabla(String[] _columndIdEventos, Controlador ctrl) {
		this.lista = new ArrayList<>();
		this.columnIds = _columndIdEventos;
		this.setColumnIdentifiers(this.columnIds);
		ctrl.addObserver(this);
	}
	//Override
	public String getColumnName(int col) {
		return this.columnIds[col];
	}
	//Override
	public int getColumnCount() {
		return this.columnIds.length;
	}
	//Override
	public int getRowCount() {
		return this.lista == null ? 0 : this.lista.size();
	}
	
}
