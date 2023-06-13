package solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backend.Empleado;
import backend.Pair;
import backend.RRHH;
import backend.RRHH.ROLES;
import observer.Observador;

public class SolverBacktracking extends Solver {

	private List<Empleado> _actual;
	private List<Empleado> _mejor;
	private Integer _lideres;
	private Integer _arquitectos;
	private Integer _programadores;
	private Integer _testers;
	private List<Empleado> _empleados;
	private List<Pair<Empleado, Empleado>> _incompatibilidades;
	private Integer _iteracion=0;
	private List<Observador> _observadores;
	private long _inicio;
	
	

	public SolverBacktracking(Integer lideres, Integer arquitectos, Integer programadores, Integer testers,
			List<Empleado> empleados, List<Pair<Empleado, Empleado>> incompatibilidades) {
		
		verificacionPedidoValida(lideres,arquitectos,programadores,testers);
		verificacionEmpleados(empleados);
		verificacionIncompatibilidades(incompatibilidades);

		_lideres = lideres;
		_arquitectos = arquitectos;
		_programadores = programadores;
		_testers = testers;
		_empleados = new ArrayList<>(empleados);
		_mejor = new ArrayList<Empleado>();
		_actual = new ArrayList<Empleado>();
		_incompatibilidades = new ArrayList<Pair<Empleado, Empleado>>(incompatibilidades);
		_observadores=new ArrayList<Observador>();

	}

	public void registrarObservador(Observador observador) {
		_observadores.add(observador);
	}

	private void verificacionIncompatibilidades(List<Pair<Empleado, Empleado>> incompatibilidades) {
		if(incompatibilidades==null) {
			throw new IllegalArgumentException("Entrada de incompatibilidades es null");
		}
		
	}

	private void verificacionEmpleados(List<Empleado> empleados) {
		if(empleados==null) {
			throw new IllegalArgumentException("Entrada de empleados es null");
		}
		
	}

	private void verificacionPedidoValida(Integer lideres, Integer arquitectos, Integer programadores,
			Integer testers) {
		if(lideres<0||arquitectos<0||programadores<0||testers<0) {
			throw new IllegalArgumentException("Entrada de pedido invalida:Ingrese valores mayores a 0");
		}
		
	}

	public List<Empleado> conjuntoOptimo() {
		_iteracion=0;
		_inicio=System.currentTimeMillis();;
		conjuntoOptimo(0);
		_iteracion = (int) Math.pow(2, _empleados.size());
		notificarObservadores();
				
		return clonar(_mejor);
	}

	private void conjuntoOptimo(int desde) {
		

		// Notifica a los observadores
		notificarObservadores();
		
		// Backtracking
		if (!solucionCompatible(_actual)) {
			return;
		}
		// Backtracking
		if (cantidadEmpleadosSuperaCantidadPedida(_actual)) {
			return;
		}

		if (cantidadEmpleadosCumpleCantidadPedida(_actual)) {
			if (conjuntoSuperaMejor(_actual)) {
				_mejor = clonar(_actual);
				return;
			}
		}

		if (desde == _empleados.size()) {
			return;
		}
		

		_iteracion++;
		// Caso recursivo con el desde
		_actual.add(_empleados.get(desde));
		conjuntoOptimo(desde + 1);

		
		// Caso recursivo sin el desde
		_actual.remove(_empleados.get(desde));
		conjuntoOptimo(desde + 1);

	}

	private void notificarObservadores() {
		for(Observador observador:_observadores) {
			observador.notificar();
		}
		
	}

	private boolean cantidadEmpleadosSuperaCantidadPedida(List<Empleado> solucion) {
		Map<ROLES, Integer> cantidadEmpleadosSolucion = cantidadDeEmpleados(solucion);

		return cantidadEmpleadosSolucion.get(RRHH.ROLES.LIDERPROYECTO) > _lideres
				|| cantidadEmpleadosSolucion.get(RRHH.ROLES.ARQUITECTO) > _arquitectos
				|| cantidadEmpleadosSolucion.get(RRHH.ROLES.PROGRAMADOR) > _programadores
				|| cantidadEmpleadosSolucion.get(RRHH.ROLES.TESTER) > _testers;
	}

	private Map<ROLES, Integer> cantidadDeEmpleados(List<Empleado> solucion) {
		Map<ROLES, Integer> salida = new HashMap<ROLES, Integer>();

		for (ROLES rol : RRHH.ROLES.values()) {
			salida.put(rol, 0);

		}

		for (Empleado empleado : solucion) {
			salida.put(empleado.getRol(), salida.get(empleado.getRol()) + 1);
		}
		return salida;
	}

	private boolean solucionCompatible(List<Empleado> solucion) {

		for (Empleado empleado1 : solucion) {
			for (Empleado empleado2 : solucion) {

				if (!empleado1.equals(empleado2) && !empleadosCompatibles(empleado1, empleado2)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean empleadosCompatibles(Empleado empleado1, Empleado empleado2) {
		for (Pair<Empleado, Empleado> coordenada : _incompatibilidades) {
			if (coordenada.esta(empleado1) && coordenada.esta(empleado2)) {
				return false;
			}
		}
		return true;
	}

	private boolean conjuntoSuperaMejor(List<Empleado> solucionPosible) {
		Integer puntosMejor = calcularPuntos(_mejor);
		Integer puntosSolucion = calcularPuntos(solucionPosible);
		return puntosSolucion > puntosMejor;
	}

	private Integer calcularPuntos(List<Empleado> solucion) {
		Integer puntos = 0;
		for (Empleado empleado : solucion) {
			puntos += empleado.getDesempenio();

		}
		return puntos;
	}

	private List<Empleado> clonar(List<Empleado> solucion) {

		return new ArrayList<Empleado>(solucion);
	}

	private boolean cantidadEmpleadosCumpleCantidadPedida(List<Empleado> solucion) {

		Map<ROLES, Integer> cantidadEmpleadosSolucion = cantidadDeEmpleados(solucion);

		return cantidadEmpleadosSolucion.get(RRHH.ROLES.LIDERPROYECTO) == _lideres
				&& cantidadEmpleadosSolucion.get(RRHH.ROLES.ARQUITECTO) == _arquitectos
				&& cantidadEmpleadosSolucion.get(RRHH.ROLES.PROGRAMADOR) == _programadores
				&& cantidadEmpleadosSolucion.get(RRHH.ROLES.TESTER) == _testers;
	}

	public Double progresoResolucionProblema() {
		
		return (double) (  (_iteracion)/Math.pow(2, _empleados.size()) *100   );
	}
	
	public int tiempoTotalEjecucionConjuntoOptimo() {
		long fin = System.currentTimeMillis();
		
		return (int) ((fin-_inicio)/1000);
	}

	public Integer getIteraciones() {
		Integer salida= _iteracion.intValue();
		return salida;
	}

}
