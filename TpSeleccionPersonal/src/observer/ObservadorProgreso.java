package observer;

import solver.Solver;

public class ObservadorProgreso implements Observador{
	
	private Solver _solver;
	private Double progreso;
	
	public ObservadorProgreso(Solver solver) {
		solver.registrarObservador(this);
		_solver=solver;
		progreso = 0.0;
	}
	
	@Override
	public void notificar() {
		progreso = _solver.progresoResolucionProblema();
	}
	
	public Double salida() {
		return progreso < 100.0 ? progreso : progreso;
	}

}
