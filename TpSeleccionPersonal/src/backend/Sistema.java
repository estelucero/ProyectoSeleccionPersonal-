package backend;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import backend.RRHH.ROLES;
import datos.Datos;
import observer.Observador;
import observer.ObservadorProgreso;
import solver.Solver;

public class Sistema extends Thread {
	private Map<ROLES, Integer> _cantidadEmpleadosPedidos;
	private RRHH _rrhh;
	List<Empleado> empleadosEquipoIdeal;

	private Long tiempoTardado;
	private Long tiempoMills;
	private Integer iteracionesTotales;
	
	
	public enum TIPO {
		 PROGRESO
	}

	private EnumMap<TIPO, Observador> observadorPorTipo;

	public Sistema() throws Exception {
		_cantidadEmpleadosPedidos = new HashMap<ROLES, Integer>();
		_rrhh = new RRHH(listaEmpleadosRRHH(), Datos.listaIncompatibilidades()); // Incompatibilidades vacio!
		observadorPorTipo = new EnumMap<TIPO, Observador>(TIPO.class);

		empleadosEquipoIdeal = new LinkedList<Empleado>();
	}

	public synchronized List<Empleado> conjuntoOptimo() {
		empleadosEquipoIdeal = _rrhh.conjuntoOptimoBacktracking();
		return empleadosEquipoIdeal;
	}

	public Observador registrarObservador(TIPO tipo) {
		if (TIPO.PROGRESO == tipo) {
			observadorPorTipo.put(TIPO.PROGRESO, new ObservadorProgreso(_rrhh.getSolver()));
			return observadorPorTipo.get(tipo);
		}
		
		return null;
	}

	public HashMap<ROLES, Integer> getCantidadEmpleadosPedidos() {
		HashMap<ROLES, Integer> salida = new HashMap<ROLES, Integer>(_cantidadEmpleadosPedidos);
		return salida;
	}

	public ArrayList<Empleado> getListaEmpleados(ROLES rol) {
		return _rrhh.getListaEmpleados(rol);
	}

	public ArrayList<Pair<Empleado, Empleado>> getIncompatibilidades() {
		return _rrhh.getIncompatibilidades();
	}

	private List<Empleado> listaEmpleadosRRHH() throws JsonIOException, JsonSyntaxException, FileNotFoundException {

		return Datos.listaDeEmpleados();
	}

	// Corre el algoritmo en un hilo aparte en sistema.
	@Override
	public synchronized void run() {
		long inicio = System.currentTimeMillis() / 1000;
		long milisInicio = System.currentTimeMillis();
		empleadosEquipoIdeal = conjuntoOptimo();
		long milisFin = System.currentTimeMillis();
		long fin = System.currentTimeMillis() / 1000;
		tiempoTardado = fin - inicio;
		tiempoMills = milisFin - milisInicio;
		
		iteracionesTotales = _rrhh.getSolver().getIteraciones();
		
	}

	public void reiniciar() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		_cantidadEmpleadosPedidos = new HashMap<ROLES, Integer>();
		_rrhh = new RRHH(listaEmpleadosRRHH(), Datos.listaIncompatibilidades()); // Incompatibilidades vacio!
		observadorPorTipo = new EnumMap<TIPO, Observador>(TIPO.class);

		empleadosEquipoIdeal = new LinkedList<Empleado>();
		
	}

	public void setSolver(Solver solver) {
		_rrhh.setSolver(solver);
	}

	public List<Empleado> getEquipoIdeal() {
		return empleadosEquipoIdeal;
	}
	
	public Long getTiempoTardado() {
		return tiempoTardado;
	}
	
	public Long getTiempoMills() {
		return tiempoMills;
	}
	
	public Integer getIteraciones() {
		return iteracionesTotales;
	}

	public int getDesempenio() {
		Integer desempenioTotal = 0;
		for(Empleado empleado: empleadosEquipoIdeal) {
			desempenioTotal += empleado.getDesempenio();
		}
		return desempenioTotal;
	}
	
	public Integer cantIncompatibilidadesEquipoIdeal() {
		if(empleadosEquipoIdeal == null || empleadosEquipoIdeal.size() <= 0) {
			return 0;
		}
		
		Integer contador = 0;
		List<Pair<Empleado,Empleado>> incompatibilidades = _rrhh.getIncompatibilidades();
		 
		for(Pair<Empleado,Empleado> par: incompatibilidades) {
			if(empleadosEquipoIdeal.contains(par.get_x()) && empleadosEquipoIdeal.contains(par.get_y())) {
				contador++;
			}
		}
		
		return contador;
	}

}
