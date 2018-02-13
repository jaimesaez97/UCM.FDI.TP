package tp.pr2.logic.multigames;



public class GameState {
	
	// atributos privados
	
	private int[][] _boardState; //estado
	private int _score;
	
	

	public GameState(int[][] boardState, int score, int highest) {
	
		this._boardState = boardState;
		this._score = score;	
		
	}
	
	
	public int get_score () {
		return this._score;
	}
	
	
	
	public int[][] get_boardState () {
		return this._boardState;
	}
}
