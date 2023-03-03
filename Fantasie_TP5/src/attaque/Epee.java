package attaque;

public class Epee extends Arme {
	private String nomEpee ;
	
	public Epee(String nomEpee) {
		super(80, "�p�e") ;
		this.nomEpee = nomEpee ;
	}

	public String getNomEpee() {
		return nomEpee;
	}
}
