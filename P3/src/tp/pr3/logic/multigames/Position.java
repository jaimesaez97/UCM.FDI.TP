package tp.pr3.logic.multigames;



public class Position {
	
	// coordenadas privadas:
	private int x;
	private int y;

	public Position (){
		super();
		this.x = 0;
		this.y = 0;
	}
	
	public Position (int x, int y){
		super();
		this.x = x;
		this.y = y;
	}
	
	
	// funcion VECINA:
	public Position vecina () {
	
	return new Position(getX() - 1, getY());
			//break;
		
	}
	
	
	
	
	// getters:
	public int getX () {
		return this.x;
	}
	
	public int getY () {
		return this.y;
	}
	
	// setters:
	public void setX (int x) {
		this.x = x;
	}
	
	public void setY (int y) {
		this.y = y;
	}

	
}
