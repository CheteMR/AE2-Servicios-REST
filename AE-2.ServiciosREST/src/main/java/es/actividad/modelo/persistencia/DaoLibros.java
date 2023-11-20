package es.actividad.modelo.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import es.actividad.modelo.entidad.Libros;

@Component
public class DaoLibros {
	
	private List<Libros> libros = new ArrayList<>();
	public int contador;
	
	public DaoLibros() {
		System.out.println("Creando la lista de Libros");
		libros = new ArrayList<Libros>();
        libros.add(new Libros(contador++, "El Poderoso Thor 5. 1965","Panini" , 10.0));
        libros.add(new Libros(contador++, "El Inmortal Hulk 10. Del infierno y de la muerte", "Panini", 10.0));
        libros.add(new Libros(contador++, "Los cuatro fantásticos 8", "Panini", 8.25));
        libros.add(new Libros(contador++, "Daredevil 7", "Panini", 9.75));
        libros.add(new Libros(contador++, "El asombroso Spiderman 1", "Panini", 9.67));
	}
	
	
	
	public Libros get (int posicion) {
		try {
			return libros.get(posicion);
		}catch (IndexOutOfBoundsException iobe) {
			System.out.println("Libro fuera de rango");
			return null;
		}
	}
	
	public List<Libros> list() {
		return libros;
	}
	
	public void add(Libros l) {
		l.setId(contador++);
		libros.add(l);
	}
	
	public Libros delete(int posicion) {
		try {
			return libros.remove(posicion);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Borrado -> Libro fuera de rango");
			return null;
		}
	}

	
	public Libros update(Libros l) {
		try {
			Libros pAux = libros.get(l.getId());
			pAux.setTitulo(l.getTitulo());
			pAux.setEditorial(l.getEditorial());
			pAux.setNota(l.getNota());

			return pAux;
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("Modificar -> Libro fuera de rango");
			return null;
		}
	}
	
	public List<Libros> listByTitulo(String titulo){
		List<Libros> listaTituloAux = new ArrayList<Libros>();
		for(Libros l : libros) {
			if(l.getTitulo().equalsIgnoreCase(titulo)) {//contains()
				listaTituloAux.add(l);
			}
		}
		return listaTituloAux;
	}
}
