package control;



public abstract class Evento {
	protected int _time;
	
	public Evento(int tiempo){
		this._time = tiempo;
	}
	
	public abstract void ejecuta(MapaCarreteras map); // se ejecuta en cada HERENCIA
	
	// getters
	public int getTime(){
		return this._time;
	}
}
