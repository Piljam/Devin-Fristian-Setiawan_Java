package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	Connection con; // buat connect ke database
	Statement s; // query langsung di 1 baris
	PreparedStatement ps; // query yg disiapkan dulu sebelum dijalankan
	ResultSet rs; // menampung hasil data yg didapatkan dari query

	
	public Database() {
		connect(); // panggil method pas class Database dipanggil
	}
	
	
	public void connect() {
		// cek apakah drivernya ada
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// connect ke database kita
		String url = "jdbc:mysql://localhost:3306/test"; // disesuaikan lagi dengan database yang kalian buat
		String username = "root"; // kalau default
		String password = ""; // kalau default
		try {
			con = DriverManager.getConnection(url, username, password);
			s = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void insert(Sepatu sepatu) { // masukkan data ke database
		try {
			ps = con.prepareStatement("insert into sepatu (Kode, Model, Merk, Warna, Harga) values (?, ?, ?, ?, ?)"); // tulis querynya dulu, '?' buat menandakan nilainya
			// set '?' nya sesuai dengan urutan dan tipe data kolom nya
			ps.setString(1, sepatu.getKode());
			ps.setString(2, sepatu.getModel());
			ps.setString(3, sepatu.getMerk());
			ps.setString(4, sepatu.getWarna());
			ps.setInt(5, sepatu.getHarga());
			ps.execute(); // jalanin
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String kode) { // delete data dari database
		try {
			ps = con.prepareStatement("delete from sepatu where kode=?"); // tulis querynya dulu, '?' buat menandakan nilainya
			// set '?' nya sesuai dengan urutan dan tipe data kolom nya
			ps.setString(1, kode);
			ps.execute(); // jalanin
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(Sepatu sepatu) { // update data di database
		try {
			ps = con.prepareStatement("update sepatu set kode = ?, model = ?, merk = ? ,warna = ?, harga = ?"); // tulis querynya dulu, '?' buat menandakan nilainya
			// set '?' nya sesuai dengan urutan dan tipe data kolom nya
			ps.setString(1, sepatu.getKode());
			ps.setString(2, sepatu.getModel());
			ps.setString(3, sepatu.getMerk());
			ps.setString(4, sepatu.getWarna());
			ps.setInt(5, sepatu.getHarga());
			ps.execute(); // jalanin
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet select() { // lihat dgn terima data dari database
		try {
			rs = s.executeQuery("select * from sepatu"); // langsung tulis & jalanin query, simpan ke result set
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
