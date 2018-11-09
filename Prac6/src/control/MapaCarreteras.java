package control;

import java.util.ArrayList;	// o Sorted array list?
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.carreteras.Carretera;
import model.cruces.CruceGenerico;
import model.vehiculos.Vehiculo;
import es.ucm.fdi.exception.CantFindOnMap;
import es.ucm.fdi.exception.CarAlreadyAdded;
import es.ucm.fdi.exception.JunctionAlreadyAdded;
import es.ucm.fdi.exception.RoadAlreadyAdded;

public class MapaCarreteras {
	/*
	 * It contains lists of all the simulated objets
	 * */
	
	private List<Carretera> _roads;
	private List<CruceGenerico<?>> _junction;
	private List<Vehiculo> _cars;
	
	// estructuras para agilizar busqueda
	private Map<String, Carretera> _roadMap;
	private Map<String, CruceGenerico<?>> _junctionMap;
	private Map<String, Vehiculo> _carMap;
	
	
	public MapaCarreteras () {
			// inicializa sus atributos a sus constructoras por defecto:
		this._roads = new ArrayList<Carretera>();
		this._junction = new ArrayList<CruceGenerico<?>>();
		this._cars = new ArrayList<Vehiculo>();
		
		this._roadMap = new HashMap<String, Carretera>();
		this._junctionMap = new HashMap<String, CruceGenerico<?>>();
		this._carMap = new HashMap<String, Vehiculo>();
	}
	public void addJunction (String idCruce, CruceGenerico<?> junction) throws JunctionAlreadyAdded {
		if(this._junctionMap.containsKey(idCruce)) // if (idCruce Existe en Mapa)
			throw new JunctionAlreadyAdded(idCruce);
		
		else {
			this._junction.add(junction);
			this._junctionMap.put(idCruce, junction);
		}
	}
	
	public void addVehiculo (String idVehiculo, Vehiculo car) throws CarAlreadyAdded{
		if(this._carMap.containsKey(idVehiculo)) 	// if (car Existe)
			throw new CarAlreadyAdded(idVehiculo);
		else {
			this._cars.add(car);
			this._carMap.put(idVehiculo, car);
			try{
			car.moverASiguienteCarretera();
			}catch(CantFindOnMap e){
				System.out.println(e.getMessage());

			}
		}
	}
		
	public void addRoad (String idCarretera, CruceGenerico<?> origen, Carretera road, CruceGenerico<?> destino) throws RoadAlreadyAdded {
		if(this._roadMap.containsKey(idCarretera)) 	// if (road Existe)
			throw new RoadAlreadyAdded(idCarretera);
		
		else {
			this._roads.add(road);
			this._roadMap.put(idCarretera, road);
			//    - Añade al cruce origen la carretera, como carretera saliente
			origen.addCarreteraSalienteAlCruce(destino, road);
		    //    - Añade al cruce destino la carretera, como carretera entrante
			destino.addCarreteraEntranteAlCruce(idCarretera, road);
		}
	}

	public String generateReport(int time) {
			// Habria que arrastrar aqui parametro booleano
		String report = "";
		// genera informe para cruces
		for(int i = 0; i < this._junction.size(); i++) {
			report += this._junction.get(i).generaInforme(time);
			report += "\n";
		}
		
		// genera informe para carreteras	
		for(int i = 0; i < this._roads.size(); ++i) {
			report += this._roads.get(i).generaInforme(time);
			report +="\n";
		}
		
		// genera informe para cars
		for(int i = 0; i < this._cars.size(); ++i) {
			report += this._cars.get(i).generaInforme(time);
			report += "\n";
		}
		
		return report;
	}
	
	public void actualiza() throws CantFindOnMap  {
		
		// llama al metodo avanza de cada carretera
				if(!this._roads.isEmpty()){ 
					for(int i = 0 ; i < this._roads.size(); i++){
						this._roads.get(i).avanza();	
					}
				}

		// llama al metodo avanza de cada cruce
		if(!this._junction.isEmpty()){ 
			for(int i = 0 ; i < this._junction.size(); i++){
				this._junction.get(i).avanza();
			}
		}
		// ordeno carreteras
		for(int i = 0; i < this._roads.size(); ++i) {
			this._roads.get(i).ordenaLista();
		}
	}
	
	public CruceGenerico<?> getCruce(String id) throws CantFindOnMap {
		if(!this._junctionMap.containsKey(id)) // if (id !Existe en Mapa)
			throw new CantFindOnMap(id);
		else
			return this._junctionMap.get(id);
	}
	
	public Vehiculo getCar(String id) throws CantFindOnMap {
		if(!this._carMap.containsKey(id)) // if (id !Existe en Mapa)
			throw new CantFindOnMap(id);
		else
			return this._carMap.get(id);
	}

	public Carretera getRoad(String id) throws CantFindOnMap {
		if(!this._roadMap.containsKey(id)) // if (id!Existe en Mapa)
			throw new CantFindOnMap(id);
		else
			return this._roadMap.get(id);
	}
	
	public List<Vehiculo> getCars(){
		return this._cars;
	}
	
	public List<Carretera> getCarreteras() {
		return this._roads;
	}
	
	public List<CruceGenerico<?>> getCruces() {
		return this._junction;
	}
	
		// NO SIRVE
	public <T> String informeParticular(List<T> l, int time) {
		String s = "";
		for(int i = 0; i < l.size(); i++) {
			if(this._cars.contains(l.get(i))) {
				s += this._carMap.get(l.get(i)).generaInforme(time) + "\n";
			}
			else if(this._roads.contains(l.get(i))) {
				s += this._roadMap.get(l.get(i)).generaInforme(time) + "\n";
			}
			else if(this._junction.contains(l.get(i))) {
				s += this._junctionMap.get(l.get(i)).generaInforme(time) + "\n";
			}
		}
		return s;
	}
}
