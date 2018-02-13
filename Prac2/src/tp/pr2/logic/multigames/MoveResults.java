package tp.pr2.logic.multigames;

public class MoveResults {
	// ACABADO
	
	
	// atributos privados:
	private boolean moved;
	private int points;
	
	// constructora:
	public MoveResults (boolean moved, int points) {
		super();
		this.moved = moved;
		this.points = points;	// puntuacion
	}
	
	//¿habría que hacer una constructore?
	
	// getters:
	public boolean isMoved() {
		return moved;
	}
	
	public int getPoints () {
		return this.points;
	}
	

}
