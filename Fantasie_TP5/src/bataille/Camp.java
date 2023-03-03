package bataille;

import java.util.LinkedList;
import java.util.ListIterator;
import protagoniste.EtreVivant;

public class Camp <E extends EtreVivant> implements Iterable<E> { 
	//implements Iterable<E> a garder !!
	private LinkedList<E> liste = new LinkedList<>();

	public ListIterator<E> iterator() {
		return liste.listIterator();
	}
	
	public void ajouter(E etreVivant) {
		if (!liste.contains(etreVivant)) {
			liste.add(etreVivant);
		}
	}
	
	public void eliminer(E etreVivant) {
		liste.remove(etreVivant);
	}

	@Override
	public String toString() {
		return liste.toString();
	}

	public LinkedList<E> getListe() {
		return liste;
	}
}
