package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import solver.Solver;
import solver.SolverBacktracking;


public class RRHH {

	private List<Pair<Empleado, Empleado>> _incompatibilidades;
	private Map<ROLES, List<Empleado>> _empleados;
	private Solver _solver;

	private List<Empleado> _totalEmpleados;


	public enum ROLES {
		LIDERPROYECTO, ARQUITECTO, PROGRAMADOR, TESTER
	}

	public RRHH(List<Empleado> empleados, List<Pair<Empleado, Empleado>> incompatibilidades) {
		if(empleados == null) {
			throw new IllegalArgumentException("No puede ingresar una lista de empleados nulos.");
		}
		_totalEmpleados= new ArrayList<Empleado>(empleados);
		_empleados = new HashMap<ROLES, List<Empleado>>();
		inciarDiccionarioEmpleados();
		agregarEmpleados(empleados);

		incompatibilidadesConjuntoCoherente(incompatibilidades);
		_incompatibilidades = new ArrayList<Pair<Empleado, Empleado>>(incompatibilidades);
		

	}

	private void incompatibilidadesConjuntoCoherente(List<Pair<Empleado, Empleado>> incompatibilidades) {
		if(incompatibilidades==null) {
			throw new IllegalArgumentException("Error la lista de incompatibilidades no puede ser null");
		}
		
		for(Pair<Empleado,Empleado> tupla:incompatibilidades) {
			if(!existeEmpleadoCargado((Empleado)tupla.get_x())  || !existeEmpleadoCargado((Empleado)tupla.get_x() )) {
				throw new IllegalArgumentException("Error la entrada de incompatibilidades tiene empleados que no existen en RRHH");
			}
			
		}
		
	}

	private void agregarEmpleados(List<Empleado> empleados) {
		if(empleados==null) {
			throw new IllegalArgumentException("Error la lista de empleados no puede ser null");
		}
		
		for (Empleado empleado : empleados) {
			if (existeEmpleadoCargado(empleado)) {
				throw new IllegalArgumentException("Error la entrada de empleados tiene 2 entradas iguales");
			}

			_empleados.get(empleado.getRol()).add(empleado);
		}
	}

	private boolean existeEmpleadoCargado(Empleado empleado) {
		return _empleados.get(empleado.getRol()).contains(empleado);
		

	}

	private void inciarDiccionarioEmpleados() {
		for (ROLES rol : RRHH.ROLES.values()) {
			_empleados.put(rol, new ArrayList<Empleado>());
		}
	}

	public ArrayList<Empleado> conjuntoOptimoBacktracking() {
		
		if(_solver == null) {
			throw new RuntimeException("Solver no definido.");
		}
		
		return (ArrayList<Empleado>) _solver.conjuntoOptimo();
	}

	public ArrayList<Pair<Empleado, Empleado>> getIncompatibilidades() {
		ArrayList<Pair<Empleado, Empleado>> salida = new ArrayList<Pair<Empleado, Empleado>>(_incompatibilidades);
		return salida;
	}

	public ArrayList<Empleado> getListaEmpleados(ROLES rol) {
		ArrayList<Empleado> salida = new ArrayList<Empleado>(_empleados.get(rol));
		return salida;
	}
	
	public void setearSolver(Integer lideres, Integer arquitectos, Integer programadores,
			Integer Testers) {
		_solver=new SolverBacktracking(lideres, arquitectos, programadores, Testers, _totalEmpleados, _incompatibilidades);
	}
	
	public Solver getSolver() {
		
		
		return _solver;
	}

	public void setSolver(Solver solver) {
		_solver = solver;
	}


}
