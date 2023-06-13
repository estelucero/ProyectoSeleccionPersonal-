package solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import backend.Empleado;
import backend.Pair;
import backend.RRHH.ROLES;
import observer.Observador;

public class SolverGoloso extends Solver {

	private Integer _lideres;
	private Integer _arquitectos;
	private Integer _programadores;
	private Integer _testers;
	private List<Empleado> _empleados;
	private List<Pair<Empleado, Empleado>> _incompatibilidades;

	private List<Observador> _observadores;
	private List<Empleado> _solucion;

	private Integer _iteraciones;


	public SolverGoloso(Integer lideres, Integer arquitectos, Integer programadores, Integer testers,
			List<Empleado> empleados, List<Pair<Empleado, Empleado>> incompatibilidades) {
		
		verificacionPedidoValida(lideres,arquitectos,programadores,testers);
		verificacionEmpleados(empleados);
		verificacionIncompatibilidades(incompatibilidades);

		_lideres = lideres;
		_arquitectos = arquitectos;
		_programadores = programadores;
		_testers = testers;
		_empleados = new ArrayList<>(empleados);

		_incompatibilidades = new ArrayList<Pair<Empleado, Empleado>>(incompatibilidades);
		_observadores=new ArrayList<Observador>();
		_solucion=new ArrayList<Empleado>();

		_iteraciones=0;

		Collections.sort(_empleados,new Comparator<Empleado>() {

			@Override
			public int compare(Empleado empleado1, Empleado empleado2) {
				
				if(calcularRelacion(empleado1)<calcularRelacion(empleado2)) {
					return 1;
					
				}else if (calcularRelacion(empleado1)==calcularRelacion(empleado2)) {
					return 0;
				}else {
					return -1;
				}
			}
		});
		Collections.reverse(_empleados);
		

	}
	
	


	private Double calcularRelacion(Empleado empleado) {
		
		
		return  (1.0*totalIncompatibilidades(empleado)/empleado.getDesempenio());
	}


	private Integer totalIncompatibilidades(Empleado empleado) {
		int sumador=0;
		for(Pair<Empleado,Empleado> par:_incompatibilidades) {
			if(par.esta(empleado)) {
				sumador++;
			}
		}
		 return sumador;
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
	
	@Override
	public void registrarObservador(Observador observador) {
		_observadores.add(observador);
		
	}

	@Override
	public List<Empleado> conjuntoOptimo() {

		_iteraciones=0;
		
		_solucion.clear();
		notificarObservadores();

		
		_solucion=clonar(generarSolucion());
		
		

		_iteraciones=(_empleados.size()*4);
		notificarObservadores();

		return clonar(_solucion);
	}




	private List<Empleado> generarSolucion() {
		List<Empleado> salida=new ArrayList<Empleado>();

		
		salida.addAll(conjuntoEmpleadosDelRol(ROLES.LIDERPROYECTO,_lideres));
		
		salida.addAll(conjuntoEmpleadosDelRol(ROLES.PROGRAMADOR,_programadores));
		
		salida.addAll(conjuntoEmpleadosDelRol(ROLES.ARQUITECTO,_arquitectos));
		

		salida.addAll(conjuntoEmpleadosDelRol(ROLES.TESTER,_testers));
		
		return salida.size()==_lideres+_arquitectos+_programadores+_testers?salida:new ArrayList<>();
	}






	private List<Empleado> clonar(List<Empleado> solucion) {

		return new ArrayList<Empleado>(solucion);
	}




	private List<Empleado> conjuntoEmpleadosDelRol(ROLES rol, Integer cantidad) {
		int faltan=cantidad.intValue();
		List<Empleado> conjuntoEmpleados=new ArrayList<Empleado>();
		for(Empleado empleado:_empleados) {
			if(faltan==0) {
				break;
			}
			if(empleado.getRol().equals(rol)) {
				conjuntoEmpleados.add(empleado);
				faltan--;
			}

			sumaIteraciones();
			notificarObservadores();

		}
		
		return conjuntoEmpleados;
	}





	private void sumaIteraciones() {
		_iteraciones++;
		
	}


	private void notificarObservadores() {
		for(Observador observador:_observadores) {
			observador.notificar();
		}
		
	}

	@Override
	public Double progresoResolucionProblema() {
		
		return (1.0*_iteraciones/(_empleados.size()*4)*100);

	}




	@Override
	public Integer getIteraciones() {
		return _iteraciones;
	}




	@Override
	public int tiempoTotalEjecucionConjuntoOptimo() {
		return (int) (System.currentTimeMillis()/1000);
	}

}
