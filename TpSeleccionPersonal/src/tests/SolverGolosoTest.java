package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import backend.Empleado;
import backend.Pair;
import backend.RRHH;
import solver.SolverGoloso;

public class SolverGolosoTest {
	private SolverGoloso solver;
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
		solver = new SolverGoloso(1, 1, 1, 1, null, incompatibilidades);

	}

	@Test(expected = IllegalArgumentException.class)
	public void solverConstructorListaIncompatibilidadesNullTest() {
		solver = new SolverGoloso(1, 1, 1, 1, empleados, null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void solverConstructorEnterosPedidosMenoresCeroTest() {
		solver = new SolverGoloso(-1, 1, 1, 1, empleados, incompatibilidades);

	}

	@Test
	public void solucionVacioPedidoVacioTest() {
		solver = new SolverGoloso(0, 0, 0, 0, empleados, incompatibilidades);
		assertEquals(new ArrayList<Empleado>(), solver.conjuntoOptimo());
	}

	@Test
	public void solucionVacioListaVacioTest() {
		solver = new SolverGoloso(1, 0, 0, 0, new ArrayList<Empleado>(),
				new ArrayList<Pair<Empleado, Empleado>>());
		assertEquals(new ArrayList<Empleado>(), solver.conjuntoOptimo());
	}

	@Test
	public void solucionNoVacioPedidoIncompatibleNoExactoTest() {
		this.agregarIncompatibilidad(listaEmpleados().get(0), listaEmpleados().get(4));
		solver = new SolverGoloso(1, 1, 0, 0, empleados, incompatibilidades);
		List<Empleado> ideal=new ArrayList<Empleado>();
		ideal.add(listaEmpleados().get(0));
		ideal.add(listaEmpleados().get(4));
		
		assertEquals(ideal, solver.conjuntoOptimo());
	}
	@Test
	public void solucionNoVacioPedidoIncompatibleExactoTest() {
		this.agregarIncompatibilidad(listaEmpleados().get(0), listaEmpleados().get(4));
		empleados.add(new Empleado(6, "ContraPolska", RRHH.ROLES.ARQUITECTO, "a", 2));
		solver = new SolverGoloso(1, 1, 0, 0, empleados, incompatibilidades);
		List<Empleado> ideal=new ArrayList<Empleado>();
		ideal.add(listaEmpleados().get(0));
		ideal.add(empleados.get(5));
		
		
		assertEquals(ideal, solver.conjuntoOptimo());
	}
	
	@Test
	public void solucionPedidoTodosLosEmpleadosConIncompatibilidadesTest() {
		this.agregarIncompatibilidad(listaEmpleados().get(3), listaEmpleados().get(1));
		solver = new SolverGoloso(1, 1, 2, 1, empleados, incompatibilidades);
		List<Empleado> salida=solver.conjuntoOptimo();
		Collections.sort(salida,(p,q)->p.getId().compareTo(q.getId()));
		
		
		assertEquals(listaEmpleados(), salida);;
	
	}

	@Test
	public void solucionVacioPedidoExcedeTest() {

		solver = new SolverGoloso(10, 0, 0, 0, empleados, incompatibilidades);

		assertEquals(new ArrayList<Empleado>(), solver.conjuntoOptimo());
	}
	
	@Test
	public void solucionOptimaSinIncompatibilidades() {
		
		solver = new SolverGoloso(1, 1, 1, 1, empleados, incompatibilidades);
		
		
		
		List<Empleado> igual=new ArrayList<Empleado>();
		igual.add(listaEmpleados().get(0));
		igual.add(listaEmpleados().get(1));
		igual.add(listaEmpleados().get(3));
		igual.add(listaEmpleados().get(4));
		List<Empleado> salida=solver.conjuntoOptimo();
		Collections.sort(salida,(p,q)->p.getId().compareTo(q.getId()));
		assertEquals(igual, salida);
	
	}
	@Test
	public void solucionOptimaConIncompatibilidades() {
		this.agregarIncompatibilidad(listaEmpleados().get(1), listaEmpleados().get(4));
		solver = new SolverGoloso(1, 1, 1, 1, empleados, incompatibilidades);
		
		solver.conjuntoOptimo();
		
		
		List<Empleado> igual=new ArrayList<Empleado>();
		igual.add(listaEmpleados().get(0));
		igual.add(listaEmpleados().get(2));
		igual.add(listaEmpleados().get(3));
		igual.add(listaEmpleados().get(4));
		List<Empleado> salida=solver.conjuntoOptimo();
		Collections.sort(salida,(p,q)->p.getId().compareTo(q.getId()));
		assertEquals(igual, salida);
	
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
