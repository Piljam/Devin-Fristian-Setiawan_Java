package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataStruk {
	
	Connection con; // buat connect ke database
	Statement s; // query langsung di 1 baris
	PreparedStatement ps; // query yg disiapkan dulu sebelum dijalankan
	ResultSet rs; // menampung hasil data yg didapatkan dari query

	
	public DataStruk() {
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
	
	
	public void insert(Struk struk) { // masukkan data ke database
		try {
			ps = con.prepareStatement("insert into struk (StrukID,KodeSepatu, Model, Merk, Warna, Harga,kuantitas,uang_pembayaran) values (?, ?, ?, ?, ?, ?, ? ,?)"); // tulis querynya dulu, '?' buat menandakan nilainya
			// set '?' nya sesuai dengan urutan dan tipe data kolom nya
			ps.setString(1, struk.getStrukID());
			ps.setString(2, struk.getKode());
			ps.setString(3, struk.getModel());
			ps.setString(4, struk.getMerk());
			ps.setString(5, struk.getWarna());
			ps.setInt(6, struk.getHarga());
			ps.setInt(7, struk.getKuantitas());
			ps.setInt(8, struk.getUang());
			ps.execute(); // jalanin
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public ResultSet select() { // lihat dgn terima data dari database
		try {
			rs = s.executeQuery("select * from struk"); // langsung tulis & jalanin query, simpan ke result set
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
