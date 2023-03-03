package livre;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NavigableSet;
import java.util.TreeSet;

import attaque.Feu;
import attaque.Glace;
import attaque.Tranchant;
import bataille.Bataille;
import protagoniste.Domaine;
import protagoniste.Heros;
import protagoniste.Homme;
import protagoniste.Monstre;

public class AideEcrivain {
	private Bataille bataille;
	private NavigableSet<Monstre<?>> monstresDeFeu = new TreeSet <>();
	private NavigableSet<Monstre<?>> monstresDeGlace = new TreeSet <>();
	private NavigableSet<Monstre<?>> monstresTranchants = new TreeSet <>();
	
	public NavigableSet<Monstre<?>> getMonstresDeFeu() {
		initMonstresDeFeu();
		return monstresDeFeu;
	}

	public NavigableSet<Monstre<?>> getMonstresDeGlace() {
		initMonstresDeGlace();
		return monstresDeGlace;
	}

	public NavigableSet<Monstre<?>> getMonstresTranchants() {
		initMonstresTranchants();
		return monstresTranchants;
	}

	private NavigableSet<Monstre<?>> monstreDomaineSet =  new TreeSet<>(new Comparator<Monstre<?>>() {
		public int compare (Monstre<?> monstre1, Monstre<?> monstre2) {
			int test = monstre1.getDomaine().compareTo(monstre2.getDomaine());
			if (test == 0) {
				return monstre1.getNom().compareTo(monstre2.getNom()); 
			}
			return test;
		}
	} );
	
	private NavigableSet<Monstre<?>> monstreZoneSet =  new TreeSet<>(new Comparator<Monstre<?>>() {
		public int compare (Monstre<?> monstre1, Monstre<?> monstre2) {
			int test = monstre1.getZoneDeCombat().compareTo(monstre2.getZoneDeCombat());
			if (test == 0) {
				int test2 = monstre2.getForceDeVie() - monstre1.getForceDeVie();
				if (test2 == 0) {
					return monstre1.getNom().compareTo(monstre2.getNom()); 
				}
				return test2;
			}
			return test;
		}
	} );

	public AideEcrivain(Bataille bataille) {
		this.bataille = bataille;
	}
	
	public Bataille getBataille() {
		return bataille;
	}
	
	public String visualiserForcesHumaines() {
		//a refaire avec for a la place des whille !!!
		LinkedList<Homme> listeTriee = new LinkedList<>();
		ListIterator<Homme> iterateur = listeTriee.listIterator();
		
		for (Homme homme : bataille.getCampHumains()) {
			if (!(homme instanceof Heros)) {
				while (iterateur.hasNext()) {
					iterateur.next();
				}
				iterateur.add(homme);
			}
			else {
				/* qd test avec iterateur.prev ou iterateur.next ; l'iterateur est qd meme deplacer donc 
				1_ on parcours la liste jusqu'a tomber sur un heros, l'iterateur se situe avant le premier heros
				2_ on fait next jusqu'a retombé sur un homme puis si precedent existe, precedent (au cas ou on est a l'indice 0 -> pas de retour possible
				3_ on ajoute le heros   */
				while (iterateur.hasPrevious() && (!(iterateur.previous() instanceof Heros)));
				while (iterateur.hasNext() && (iterateur.next() instanceof Heros));
				if (iterateur.hasPrevious()) { 
					iterateur.previous(); 
					}
				iterateur.add(homme);
				}
		}
		return listeTriee.toString();
	}
	
	public String ordreNaturelMonstre() {
		ListIterator<Monstre<?>> iterateur = bataille.getCampMonstres().iterator();
		NavigableSet<Monstre<?>> ordreNatMonstres = new TreeSet<>();
		while (iterateur.hasNext()) {
			ordreNatMonstres.add(iterateur.next());
		}
		String concatNomsMonstres = "";
		for (Iterator<Monstre<?>> iterator = ordreNatMonstres.iterator(); iterator.hasNext();) {
			Monstre<?> monstre = (Monstre<?>) iterator.next();
			concatNomsMonstres += monstre.getNom();
			if (iterator.hasNext()) {
					concatNomsMonstres += ", ";
			}
		}
		return concatNomsMonstres;
		
	}

	
	public void updateMonstresDomaine() {
		for (Monstre<?> monstre : bataille.getCampMonstres()) {
			monstreDomaineSet.add(monstre);
		}
	}
	
	public String ordreMonstreDomaine() {
		String strMonstreDom = "";
		String nomDomaine = "" ;
		updateMonstresDomaine();
		for (Iterator<Monstre<?>> iterator = monstreDomaineSet.iterator(); iterator.hasNext();) {
			Monstre<?> monstre = (Monstre<?>) iterator.next();
			if (monstre.getDomaine().name() != nomDomaine) {
				nomDomaine = monstre.getDomaine().name();
				strMonstreDom += ("\n" + nomDomaine + " :\n");
			}
			strMonstreDom += monstre.getNom();
			if (iterator.hasNext()) {
				strMonstreDom += ", ";
			}
		}
		return strMonstreDom;
	}
			
	public void updateMonstresZone() {
		for (Monstre<?> monstre : bataille.getCampMonstres()) {
			monstreZoneSet.add(monstre);
		}
	}	
		
	public String ordreMonstreZone() {
		String strMonstreZone = "";
		String nomZone = "" ;
		updateMonstresZone();
		for (Iterator<Monstre<?>> iterator = monstreZoneSet.iterator(); iterator.hasNext();) {
			Monstre<?> monstre = (Monstre<?>) iterator.next();
			if (monstre.getZoneDeCombat().name() != nomZone) {
				nomZone = monstre.getZoneDeCombat().name();
				strMonstreZone += ("\n" + nomZone + " :\n");
			}
			strMonstreZone += monstre.getNom() + " : " + monstre.getForceDeVie();
			if (iterator.hasNext()) {
				strMonstreZone += ", ";
			}
		}

		return strMonstreZone;
	}
	/*
	public Monstre<?> firstMonstreDomaine(Domaine domaine) {
		updateMonstresDomaine();
		for (Monstre<?> monstre : monstreDomaineSet) {
			if (monstre.getDomaine() == domaine) {
				return monstre;
			}
		}
		return null;
	}
	
	public void initMonstresDeFeu2() {
		updateMonstresDomaine();
		for (Iterator<Monstre<?>> iterator = monstreDomaineSet.iterator(); iterator.hasNext();) {
			Monstre<?> monstre = (Monstre<?>) iterator.next();
			if (monstre.getDomaine() == Domaine.FEU) {
				monstresDeFeu.add(monstre);
			}	
		}
	}
	
	public void initMonstresDeFeu() {
		updateMonstresDomaine();
		monstresDeFeu = monstreDomaineSet.headSet(firstMonstreDomaine(Domaine.GLACE), false);
	}
	*/
	
	public void initMonstresDeFeu() {
		updateMonstresDomaine();
		Monstre<Feu> monstreDegenere = new Monstre<Feu>("zzz", 0, null, Domaine.FEU);
		monstresDeFeu = monstreDomaineSet.headSet(monstreDegenere, false); 
	}
	
	public void initMonstresDeGlace() {
		updateMonstresDomaine();
		Monstre<Glace> monstreDegenere1 = new Monstre<Glace>("zzz", 0, null, Domaine.GLACE);
		Monstre<Glace> monstreDegenere2 = new Monstre<Glace>("aaa", 0, null, Domaine.GLACE);
		monstresDeGlace = monstreDomaineSet.subSet(monstreDegenere2, true, monstreDegenere1, true);
		
	}
	
	public void initMonstresTranchants() {
		updateMonstresDomaine();
		Monstre<Tranchant> monstreDegenere1 = new Monstre<Tranchant>("zzz", 0, null, Domaine.TRANCHANT);
		Monstre<Tranchant> monstreDegenere2 = new Monstre<Tranchant>("aaa", 0, null, Domaine.TRANCHANT);
		monstresTranchants = monstreDomaineSet.subSet(monstreDegenere2, true, monstreDegenere1, true);
	}
}
