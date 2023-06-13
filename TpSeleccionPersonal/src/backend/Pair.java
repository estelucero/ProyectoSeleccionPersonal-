package backend;

public class Pair<T,K> {
	
	private T _x;
	private K _y;

	public Pair(T x,K y) {
		
		
		if(x==null) {
			 throw new IllegalArgumentException("Primer valor no puede ser null");
		}
		if(y==null) {
			 throw new IllegalArgumentException("Segundo valor no puede ser null");
		}
		
		if(x.equals(y)) {
			
			throw new IllegalArgumentException("Error no se puede agregar los dos puntos iguales");
		}
		_x=x;
		_y=y;
		
		
	}
	public T get_x() {
		return _x;
	}
	public K get_y() {
		return _y;
	}
	public boolean esta(T punto) {
		
		return _x.equals(punto) || _y.equals(punto);
		
	}
	@Override
	public String toString() {
		return "("+this.get_x()+","+this.get_y()+")";
	}
	

}
