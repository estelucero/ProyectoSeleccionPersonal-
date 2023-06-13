package solver;

import java.util.List;

import backend.Empleado;
import observer.Observador;

public abstract class Solver{
	
	public abstract void registrarObservador(Observador observador);
	public abstract List<Empleado> conjuntoOptimo();
	public abstract Double progresoResolucionProblema();	
	public abstract Integer getIteraciones();
	public abstract int tiempoTotalEjecucionConjuntoOptimo();

}
