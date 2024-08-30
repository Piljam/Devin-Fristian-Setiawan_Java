package application;


public class Sepatu {
	private String kode;
	private String model;
	private String merk;
	private String warna;
	private Integer harga;
	public Sepatu(String kode, String model, String merk, String warna, Integer harga) {
		super();
		this.kode = kode;
		this.model = model;
		this.merk = merk;
		this.warna = warna;
		this.harga = harga;
	}
	public String getKode() {
		return kode;
	}
	public void setKode(String kode) {
		this.kode = kode;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMerk() {
		return merk;
	}
	public void setMerk(String merk) {
		this.merk = merk;
	}
	public String getWarna() {
		return warna;
	}
	public void setWarna(String warna) {
		this.warna = warna;
	}
	public Integer getHarga() {
		return harga;
	}
	public void setHarga(Integer harga) {
		this.harga = harga;
	}
	
	
	
	

}
