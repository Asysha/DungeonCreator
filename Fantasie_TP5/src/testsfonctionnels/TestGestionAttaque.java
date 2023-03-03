package testsfonctionnels;

import attaque.BouleDeFeu;
import attaque.Eclair;
import attaque.Feu;
import attaque.Lave;
import protagoniste.Domaine;
import protagoniste.Monstre;
import protagoniste.ZoneDeCombat;

public class TestGestionAttaque {

	public static void main(String[] args) {
		Monstre<Feu> dragotenebre = new Monstre<>("Dragotenebre", 200, ZoneDeCombat.AERIEN, Domaine.FEU, new Feu[] {new BouleDeFeu(0), new Lave(0), new Eclair(0)});
		System.out.println(dragotenebre.getNom() + " avec " + dragotenebre.getForceDeVie() + " de force de vie apparait ! Ses caracteristiques sont " + dragotenebre.toString() + ".");
	
		dragotenebre.entreEnCombat();
		
		for (int i = 0 ; i < 5 ; i++) {
			System.out.println(dragotenebre.attaque());
		}
		
	}

}
