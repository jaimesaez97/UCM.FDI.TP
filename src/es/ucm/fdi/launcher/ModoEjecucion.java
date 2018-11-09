package es.ucm.fdi.launcher;

enum ModoEjecucion {
	BATCH("batch"), GUI("gui");
	
	private String _descModo;
	
	private ModoEjecucion(String modeDesc) {
		this._descModo = modeDesc;
	}
	
	@SuppressWarnings("unused")
	private String getModelDesc()
	{
		return this._descModo;
	}
}
