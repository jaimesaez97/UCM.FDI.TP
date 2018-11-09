package es.ucm.fdi.simulador;
/*
 * 
 *  -- INITGUI() -- Hecha la confirmacion de salir del simulador (CLASE: VentanaPrincipal)
 *  --AddBarraEstado() -- En la clase Ventana Principal, REVISAR.
 *  --createPanelSuperior() -- Falta algo mas?
 *  //--> ME HE QUEDADO EN VENTANA PRINCIPAL createPanelInferior();
 *  
 *  
 * 1-- Para notificar el avance de un evento (notificaAvanza();) ¿Hay que hacer 
 * que la función ejecuta haga throw de posibles exceps?
 * 2-- Que mas tiene el setMensaje() de Barra de Estado?
 * 3-- CREAR EN EL EJE DE LAS X VAN APARECIENDO 1/2/3/4?
 *
 * */

import java.io.IOException;
import vista.*;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import control.Evento;
import control.MapaCarreteras;
import control.SortedArrayList;
import es.ucm.fdi.exception.CantFindOnMap;
import es.ucm.fdi.exception.ErrorDeSimulacion;


public class SimuladorTrafico implements Observador<ObservadorSimuladorTrafico>{

	private MapaCarreteras _map;	// guarda el estado de cada uno de los objetos de la simulaciÃ³n
	private List<Evento> _event;	// ejecuta en cada paso un evento 
	private int _timeCounter;		// nÂº pasos simulados
	
	private List<ObservadorSimuladorTrafico> _observadores;
	
	public SimuladorTrafico () {
		this._map = new MapaCarreteras();
		this._timeCounter = 0;
		Comparator<Evento> cmp = new Comparator<Evento>() { // AL SORTED ARRAY LIST LE PASAMOS EL COMPARADOR HECHO
		    public int compare(Evento e1, Evento e2) {
			    //METODO A UTILIZAR CON COMPARATOR
			    	if (e1.getTime() == e2.getTime()) return 0;
			    	else if (e1.getTime() < e2.getTime()) return -1;
			    	else return 1;
		    	}
		    
		    //ESTO ORDENA EN ORDEN ASCENDENTE 
	    };
	    this._event = new SortedArrayList<Evento>(cmp);
	    this._observadores = new ArrayList<>();
	 
	}

	
	public void run(OutputStream out) throws CantFindOnMap, IOException {
		
		for(int i = 0; i < this._event.size(); ++i) {
			if(this._event.get(i).getTime() == this._timeCounter) {
				this._event.get(i).ejecuta(this._map);	
			}
		}			
			// 2. actualizar mapa	(invocar avanza() de cada cruce y carretera)
		this._map.actualiza();
		
			// 3. this.contadorTiempo++;
		this._timeCounter++;
			
			// 4. escribir informe
		//out.write(this._map.generateReport(this._timeCounter).getBytes(), 0, this._map.generateReport(this._timeCounter).length());
		
			// 5. notificar avanza a observadores
		this.notificaAvanza();	 // este notifica esta fallando (creo)
	}
	
	public void ejecuta (int pasos, OutputStream out) throws IOException, CantFindOnMap {	 // hay que hacer que esta funciuon la ejecute una hebra
		int timeLimit = this._timeCounter + pasos - 1;
		
		
		while(this._timeCounter <= timeLimit) {
		//	String s = "# ******* step " + this._timeCounter + " *******\n\n";
		//	out.write(s.getBytes());
				// 1. ejecutar los eventos correspondientes a ese tiempo
			for(int i = 0; i < this._event.size(); ++i) {
				if(this._event.get(i).getTime() == this._timeCounter) {
					this._event.get(i).ejecuta(this._map);	
				}
			}			
				// 2. actualizar mapa	(invocar avanza() de cada cruce y carretera)
			this._map.actualiza();
			
				// 3. this.contadorTiempo++;
			this._timeCounter++;
				
				// 4. escribir informe
			out.write(this._map.generateReport(this._timeCounter).getBytes(), 0, this._map.generateReport(this._timeCounter).length());
			
				// 5. notificar avanza a observadores
			this.notificaAvanza();
			
		}
	}
	
	private void notificaAvanza() {
		for(ObservadorSimuladorTrafico o : this._observadores) 
			o.avanza(this._timeCounter, this._map, this._event);
	}

	public void insertaEvento (Evento e) throws ErrorDeSimulacion {
		if(e != null)
			if(e.getTime() < this._timeCounter) {	// Este if antes no estaba
				ErrorDeSimulacion err = new ErrorDeSimulacion("Tiempo de evento menor al timeCounter");
				this.notificaError(err);
				throw err;
			}	
			else {
				this._event.add(e);
				this.notificaNuevoEvento();	// se notifica a los observadores
			}
		else { // e == null
			 ErrorDeSimulacion err = new ErrorDeSimulacion("Evento nulo");
			 this.notificaError(err);
			 throw err;
		}
	}
	
	private void notificaNuevoEvento() {
		for(ObservadorSimuladorTrafico o : this._observadores) {
			o.addEvento(this._timeCounter, this._map, this._event);
		}
	}

	private void notificaError(ErrorDeSimulacion err) {
		for(ObservadorSimuladorTrafico o : this._observadores) 
			o.errorSimulador(this._timeCounter, this._map, this._event, err);
	}

	@Override
	public void addObservador(ObservadorSimuladorTrafico o) {
		if(o != null && !this._observadores.contains(o)) {
			this._observadores.add(o);
		}
	}

	@Override
	public void removeObservador(ObservadorSimuladorTrafico o) {
		if(o != null && this._observadores.contains(o))
			this._observadores.remove(o);
	}

	public void reinicia() {
		this._map = new MapaCarreteras();
		this._timeCounter = 0;
		Comparator<Evento> cmp = new Comparator<Evento>() { // AL SORTED ARRAY LIST LE PASAMOS EL COMPARADOR HECHO
		    public int compare(Evento e1, Evento e2) {
			    //METODO A UTILIZAR CON COMPARATOR
			    	if (e1.getTime() == e2.getTime()) return 0;
			    	else if (e1.getTime() < e2.getTime()) return -1;
			    	else return 1;
		    	}
		    
		    // ORDENA EN ORDEN ASCENDENTE 
	    };
	    this._event = new SortedArrayList<Evento>(cmp);
	    this.notificaReinicia();
	}

	private void notificaReinicia() {
		for(ObservadorSimuladorTrafico o : this._observadores) 
			o.reinicia(this._timeCounter, this._map, this._event);
	}
	

}
