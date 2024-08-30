package application;

// Class representing Struk (Receipt) that extends Sepatu
public class Struk extends Sepatu {
    private String strukID;
    private int kuantitas;
    private int uang;

    // Constructor
    public Struk(String strukID, String kode, String model, String merk, String warna,
                 int harga, int kuantitas, int uang) {
        super(kode, model, merk, warna, harga);
        this.strukID = strukID;
        this.kuantitas = kuantitas;
        this.uang = uang;
    }

	public String getStrukID() {
		return strukID;
	}

	public void setStrukID(String strukID) {
		this.strukID = strukID;
	}

	public int getKuantitas() {
		return kuantitas;
	}

	public void setKuantitas(int kuantitas) {
		this.kuantitas = kuantitas;
	}

	public int getUang() {
		return uang;
	}

	public void setUang(int uang) {
		this.uang = uang;
	}

    
    
}

