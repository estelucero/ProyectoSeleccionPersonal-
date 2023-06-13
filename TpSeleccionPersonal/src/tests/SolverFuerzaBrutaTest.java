package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import backend.Empleado;
import backend.Pair;
import backend.RRHH;
import solver.SolverBacktracking;

public class SolverFuerzaBrutaTest {
	private SolverBacktracking solver;
	List<Pair<Empleado, Empleado>> incompatibilidades;
	List<Empleado> empleados;

	@Before
	public void setup() {
		incompatibilidades = new ArrayList<Pair<Empleado, Empleado>>();
		empleados=new ArrayList<>(listaEmpleados());
	}

	@Test(expected = IllegalArgumentException.class)
	public void solverConstructorListaEmpleadosNullTest() {
		agregarIncompatibilidad(listaEmpleados().get(0), listaEmpleados().get(1));
		solver = new SolverBacktracking(1, 1, 1, 1, null, incompatibilidades);

	}

	@Test(expected = IllegalArgumentException.class)
	public void solverConstructorListaIncompatibilidadesNullTest() {
		solver = new SolverBacktracking(1, 1, 1, 1, empleados, null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void solverConstructorEnterosPedidosMenoresCeroTest() {
		solver = new SolverBacktracking(-1, 1, 1, 1, empleados, incompatibilidades);

	}

	@Test
	public void solucionVacioPedidoVacioTest() {
		solver = new SolverBacktracking(0, 0, 0, 0, empleados, incompatibilidades);
		assertEquals(new ArrayList<Empleado>(), solver.conjuntoOptimo());
	}

	@Test
	public void solucionVacioListaVacioTest() {
		solver = new SolverBacktracking(1, 0, 0, 0, new ArrayList<Empleado>(),
				new ArrayList<Pair<Empleado, Empleado>>());
		assertEquals(new ArrayList<Empleado>(), solver.conjuntoOptimo());
	}

	@Test
	public void solucionVacioPedidoIncompatibleTest() {
		this.agregarIncompatibilidad(listaEmpleados().get(0), listaEmpleados().get(4));
		solver = new SolverBacktracking(1, 1, 0, 0, empleados, incompatibilidades);
		assertEquals(new ArrayList<Empleado>(), solver.conjuntoOptimo());
	}
	
	@Test
	public void solucionVacioPedidoIncompatibleTest2() {
		this.agregarIncompatibilidad(listaEmpleados().get(3), listaEmpleados().get(1));
		solver = new SolverBacktracking(1, 1, 2, 1, empleados, incompatibilidades);
		
		assertEquals(new ArrayList<Empleado>(), solver.conjuntoOptimo());
	
	}

	@Test
	public void solucionVacioPedidoExcedeTest() {

		solver = new SolverBacktracking(10, 0, 0, 0, empleados, incompatibilidades);

		assertEquals(new ArrayList<Empleado>(), solver.conjuntoOptimo());
	}
	
	@Test
	public void solucionOptimaSinIncompatibilidades() {
		
		solver = new SolverBacktracking(1, 1, 1, 1, empleados, incompatibilidades);
		
		
		
		List<Empleado> igual=new ArrayList<Empleado>();
		igual.add(listaEmpleados().get(0));
		igual.add(listaEmpleados().get(2));
		igual.add(listaEmpleados().get(3));
		igual.add(listaEmpleados().get(4));
		assertEquals(igual, solver.conjuntoOptimo());
	
	}
	@Test
	public void solucionOptimaConIncompatibilidades() {
		this.agregarIncompatibilidad(listaEmpleados().get(2), listaEmpleados().get(4));
		solver = new SolverBacktracking(1, 1, 1, 1, empleados, incompatibilidades);
		
		solver.conjuntoOptimo();
		
		
		List<Empleado> igual=new ArrayList<Empleado>();
		igual.add(listaEmpleados().get(0));
		igual.add(listaEmpleados().get(1));
		igual.add(listaEmpleados().get(3));
		igual.add(listaEmpleados().get(4));
		assertEquals(igual, solver.conjuntoOptimo());
	
	}


	private List<Empleado> listaEmpleados() {
		List<Empleado> creacion = new ArrayList<Empleado>();
		Empleado e1 = new Empleado(1, "Pepe", RRHH.ROLES.LIDERPROYECTO, "a", 3);
		Empleado e2 = new Empleado(2, "Juan", RRHH.ROLES.PROGRAMADOR, "a", 1);
		Empleado e3 = new Empleado(3, "Maria", RRHH.ROLES.PROGRAMADOR, "a", 4);
		Empleado e4 = new Empleado(4, "Luna", RRHH.ROLES.TESTER, "a", 3);
		Empleado e5 = new Empleado(5, "Polska", RRHH.ROLES.ARQUITECTO, "a", 2);
		creacion.add(e1);
		creacion.add(e2);
		creacion.add(e3);
		creacion.add(e4);
		creacion.add(e5);
		return creacion;
	}

	private void agregarIncompatibilidad(Empleado e1, Empleado e2) {

		incompatibilidades.add(new Pair<Empleado, Empleado>(e1, e2));

	}

}
