package Model;

public class Medicament {
    private int idMedicamente;
    private String nume;
    private MedicamentInfo medicamentInfo;

    public Medicament() {
        // Default constructor
    }
    public Medicament(String nume, MedicamentInfo medicamentInfo) {
        this(-1, nume, medicamentInfo);
    }
    
    public Medicament(int idMedicamente, String nume, MedicamentInfo medicamentInfo)
    {
    	this.idMedicamente=idMedicamente;
        this.nume = nume;
        this.medicamentInfo = medicamentInfo;
    }
	public int getIdMedicamente() {
		return idMedicamente;
	}
	public void setIdMedicamente(int idMedicamente) {
		this.idMedicamente = idMedicamente;
	}
	public String getNume() {
		return nume;
	}
	public void setNume(String nume) {
		this.nume = nume;
	}
	public MedicamentInfo getMedicamenteInfo() {
		return medicamentInfo;
	}
	public void setMedicamenteInfo(MedicamentInfo medicamentInfo) {
		this.medicamentInfo = medicamentInfo;
	}
	@Override
	public String toString() {
		return "Medicamente [idMedicamente=" + idMedicamente + ", nume=" + nume + ", medicamenteInfo=" + medicamentInfo.toString()
				+ "]";
	}
	
}