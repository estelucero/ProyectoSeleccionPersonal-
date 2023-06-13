package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import backend.Empleado;
import backend.Pair;
import backend.RRHH;
import backend.RRHH.ROLES;

public class RRHHTest {
	private List<Pair<Empleado, Empleado>> _incompatibilidades;
	private Map<ROLES, List<Empleado>> _empleados;

	@Before
	public void set() {
		_incompatibilidades = new ArrayList<Pair<Empleado, Empleado>>();
		_empleados = new HashMap<ROLES, List<Empleado>>();
		List<Empleado> var = listaEmpleados();
		crearDicEmpleados(var);
	}

	private List<Empleado> listaEmpleados() {
		List<Empleado> creacion = new ArrayList<Empleado>();
		Empleado e1 = new Empleado(1, "Pepe", RRHH.ROLES.LIDERPROYECTO, "a", 3);
		Empleado e2 = new Empleado(2, "Juan", RRHH.ROLES.PROGRAMADOR, "a", 1);
		Empleado e3 = new Empleado(3, "Juan", RRHH.ROLES.PROGRAMADOR, "a", 4);
		Empleado e4 = new Empleado(4, "Juan", RRHH.ROLES.TESTER, "a", 3);
		Empleado e5 = new Empleado(5, "Juan", RRHH.ROLES.ARQUITECTO, "a", 2);
		creacion.add(e1);
		creacion.add(e2);
		creacion.add(e3);
		creacion.add(e4);
		creacion.add(e5);
		return creacion;
	}

	private void crearDicEmpleados(List<Empleado> lista) {

		for (ROLES rol : RRHH.ROLES.values()) {
			_empleados.put(rol, new ArrayList<Empleado>());
		}

		for (Empleado empleado : lista) {
			_empleados.get(empleado.getRol()).add(empleado);
		}

	}

	@Test
	public void getListaEmpleadosTest() {
		_incompatibilidades.add(new Pair<Empleado, Empleado>(_empleados.get(RRHH.ROLES.PROGRAMADOR).get(0),
				_empleados.get(RRHH.ROLES.PROGRAMADOR).get(1)));

		RRHH rrhh = new RRHH(listaEmpleados(), _incompatibilidades);

		diccionarioAssert(rrhh);
	}

	private void diccionarioAssert(RRHH rrhh) {
		assertEquals(_empleados.get(RRHH.ROLES.LIDERPROYECTO), rrhh.getListaEmpleados(RRHH.ROLES.LIDERPROYECTO));
		assertEquals(_empleados.get(RRHH.ROLES.PROGRAMADOR), rrhh.getListaEmpleados(RRHH.ROLES.PROGRAMADOR));
		assertEquals(_empleados.get(RRHH.ROLES.TESTER), rrhh.getListaEmpleados(RRHH.ROLES.TESTER));
		assertEquals(_empleados.get(RRHH.ROLES.ARQUITECTO), rrhh.getListaEmpleados(RRHH.ROLES.ARQUITECTO));
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorListaEmpleadosNullTest() {
		_incompatibilidades.add(new Pair<Empleado, Empleado>(_empleados.get(RRHH.ROLES.PROGRAMADOR).get(0),
				_empleados.get(RRHH.ROLES.PROGRAMADOR).get(1)));
		@SuppressWarnings("unused")
		RRHH rrhh = new RRHH(null, _incompatibilidades);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorListaIncompatibilidadesNullTest() {

		@SuppressWarnings("unused")
		RRHH rrhh = new RRHH(listaEmpleados(), null);
	}

	@Test
	public void constructorListaEmpleadosVacioTest() {

		// _incompatibilidades esta vacio
		RRHH rrhh = new RRHH(new ArrayList<>(), _incompatibilidades);
		listaDiccionarioVaciasAssert(rrhh);

	}

	private void listaDiccionarioVaciasAssert(RRHH rrhh) {
		assertTrue(rrhh.getListaEmpleados(RRHH.ROLES.LIDERPROYECTO).isEmpty());
		assertTrue(rrhh.getListaEmpleados(RRHH.ROLES.PROGRAMADOR).isEmpty());
		assertTrue(rrhh.getListaEmpleados(RRHH.ROLES.TESTER).isEmpty());
		assertTrue(rrhh.getListaEmpleados(RRHH.ROLES.ARQUITECTO).isEmpty());

	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorListaEmpleadosVacioIncompatibilidadesTieneTest() {
		_incompatibilidades.add(new Pair<Empleado, Empleado>(_empleados.get(RRHH.ROLES.PROGRAMADOR).get(0),
				_empleados.get(RRHH.ROLES.PROGRAMADOR).get(1)));

		@SuppressWarnings("unused")
		RRHH rrhh = new RRHH(new ArrayList<>(), _incompatibilidades);

	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorListaEmpleadosIncompatibilidadesTieneDiferentesEmpleadosTest() {
		_incompatibilidades.add(new Pair<Empleado, Empleado>(new Empleado(6, "Chavo", RRHH.ROLES.ARQUITECTO, "", 3),
				_empleados.get(RRHH.ROLES.PROGRAMADOR).get(1)));

		@SuppressWarnings("unused")
		RRHH rrhh = new RRHH(listaEmpleados(), _incompatibilidades);

	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorListaEmpleadosrepetidos() {

		List<Empleado> nuevaLista = new ArrayList<Empleado>(listaEmpleados());
		nuevaLista.add(_empleados.get(RRHH.ROLES.ARQUITECTO).get(0));

		@SuppressWarnings("unused")
		RRHH rrhh = new RRHH(nuevaLista, _incompatibilidades);

	}

}
