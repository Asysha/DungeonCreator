package protagoniste;

import java.util.Random;
import java.util.Arrays;
import java.util.Iterator;
import attaque.Pouvoir;
import bataille.Bataille;

public class Monstre<P extends Pouvoir> extends EtreVivant {
	private ZoneDeCombat zoneDeCombat ;
	private Domaine domaine ;
	private P[] attaques ;
	private GestionAttaque gestionAttaque ;
	
	
	@SafeVarargs
	public Monstre(String nom, int forceDeVie, ZoneDeCombat zoneDeCombat, Domaine domaine, P... attaques) {
		super(nom, forceDeVie) ;
		this.zoneDeCombat = zoneDeCombat ;
		this.domaine = domaine ;
		this.attaques = attaques;
		this.gestionAttaque = new GestionAttaque() ;
	}
		private class GestionAttaque implements Iterator<P> {
			private P[] attaquesPossibles = attaques ;
			private int nbAttaquesPossibles = attaques.length ;
			@Override
			public boolean hasNext() {
				for(int i = 0 ; i<nbAttaquesPossibles ; i++ ) {
					if (!attaquesPossibles[i].isOperationnel()) {
						attaquesPossibles[i] = attaquesPossibles[nbAttaquesPossibles-1] ;
						nbAttaquesPossibles--;
					}
				}
				return (nbAttaquesPossibles != 0) ;
			}
			@Override
			public P next() {
				return attaquesPossibles[new Random().nextInt(nbAttaquesPossibles)];
			}
	}
	
	public ZoneDeCombat getZoneDeCombat() {
		return zoneDeCombat;
	}

	public Domaine getDomaine() {
		return domaine;
	}


	public P[] getAttaques() {
		return attaques;
	}

	@Override
	public String toString() {
		return "Monstre [nom=" + nom + ", forceDeVie=" + forceDeVie + "zoneDeCombat=" + zoneDeCombat + ", domaine=" + domaine + ", attaques="
				+ Arrays.toString(attaques) + "]";
	}
	
	public void entreEnCombat() {
		for (P attaque : attaques) {
			attaque.regenererPouvoir() ;
		}
		this.gestionAttaque = new GestionAttaque();
	}
	
	public P attaque() {
		if (gestionAttaque.hasNext()) {
			return gestionAttaque.next();
		}
		return null;
	}
	
	@Override
	public void rejointBataille (Bataille bataille) {
		super.rejointBataille(bataille);
		bataille.ajouter(this);
	}
	
	public void mourir() {
		bataille.eliminer(this);
	}
	
}
