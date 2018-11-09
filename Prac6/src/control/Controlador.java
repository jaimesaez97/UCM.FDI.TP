package control;

import java.io.*;

import es.ucm.fdi.ini.*;
import es.ucm.fdi.simulador.SimuladorTrafico;
import vista.ObservadorSimuladorTrafico;
import es.ucm.fdi.exception.CantFindOnMap;
import es.ucm.fdi.exception.ErrorDeSimulacion;

public class Controlador{
	/*
	 * Recives the road sim, the time limit & the I/O files
	 * Loads the event, init the sim and throws the sim exec
	 * */
	
	private SimuladorTrafico _sim;
	private OutputStream _out;
	@SuppressWarnings("unused")
	private InputStream _in;
	private int _pasosSim;
	
	
	public Controlador (SimuladorTrafico sim, int pasos, InputStream in, OutputStream out) {
		this._sim = sim;
		this._out = out;
		this._in = in;
		this._pasosSim = pasos;
	}
	
	
	public void ejecuta (int pasos){
		try {
			this._sim.ejecuta(pasos, this._out);
			//Thread.currentThread().run();
		} catch (IOException | CantFindOnMap e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void reinicia() {
		this._sim.reinicia();
	}
	
	public void termina() throws Throwable {
		this.finalize();
	}
	
	public void addObserver(ObservadorSimuladorTrafico o) {
		this._sim.addObservador(o);
	}
	
	public void removeObserver(ObservadorSimuladorTrafico o) {
		this._sim.removeObservador(o);
	}
	
	public void cargaEventos(InputStream in) throws ErrorDeSimulacion {
		Ini ini;
		
		try{
			// lee el fichero y carga IniSections
			ini = new Ini(in);
		}
		catch(IOException e){
			throw new ErrorDeSimulacion("Error en la lectura de eventos: " + e);
		}
		// recorremos todos los eventos para generar el correspondiente
		for(IniSection sec : ini.getSections()){
			// parseamos la seccion para ver el correspondiente
			Evento e = ParserEventos.parseaEvento(sec);
			if (e != null) {
				this._sim.insertaEvento(e);
			}
			else
				throw new ErrorDeSimulacion("Evento desconocido: " + sec.getTag());
		}
		
		
	}
		// getter para steps (se piden en BarraMenu)
	public int getSteps(){
		return this._pasosSim;
	}
	public void setSteps(int a){
		this._pasosSim = a;
	}
	
}
