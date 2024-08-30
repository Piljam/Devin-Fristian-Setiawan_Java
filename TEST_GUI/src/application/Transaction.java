package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Transaction extends BorderPane {
    private TableView<Sepatu> table;
    private TextField merkField;
    private TextField modelField;
    private TextField warnaField;
    private TextField hargaField;
    private TextField idField;
    private TextField uangBayarField;
    private TextField kuantitasField;
    int num=1;
    DataStruk ds = new DataStruk();
    Database db = new Database();
    private Sepatu selectedSepatu; // Untuk menyimpan sepatu yang dipilih
    
    public Transaction() {
        // Initialize components
        table = new TableView<>();
        merkField = new TextField();
        modelField = new TextField();
        warnaField = new TextField();
        hargaField = new TextField();
        idField = new TextField();
        uangBayarField = new TextField();
        kuantitasField = new TextField();
        
        // Set up the form
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));
        form.add(new Label("Merk:"), 0, 0);
        form.add(merkField, 1, 0);
        form.add(new Label("Model:"), 0, 1);
        form.add(modelField, 1, 1);
        form.add(new Label("Warna:"), 0, 2);
        form.add(warnaField, 1, 2);
        form.add(new Label("Harga:"), 0, 3);
        form.add(hargaField, 1, 3);
        form.add(new Label("Kode Sepatu:"), 0, 4);
        form.add(idField, 1, 4);
        
        // Set up transaction form
        GridPane transactionForm = new GridPane();
        transactionForm.setHgap(10);
        transactionForm.setVgap(10);
        transactionForm.setPadding(new Insets(10));
        transactionForm.add(new Label("Uang Dibayarkan:"), 0, 0);
        transactionForm.add(uangBayarField, 1, 0);
        transactionForm.add(new Label("Kuantitas Sepatu:"), 0, 1);
        transactionForm.add(kuantitasField, 1, 1);
        Button transaksiButton = new Button("Proses Transaksi");
        transactionForm.add(transaksiButton, 1, 2);
        
        // Set up buttons
        Button viewButton = new Button("View");
        Button clear = new Button("Clear");
        HBox buttons = new HBox(10, viewButton,clear);
        
        transaksiButton.setOnAction(event -> prosesTransaksi());
        viewButton.setOnAction(event->refresh());
        clear.setOnAction(event -> refresh());
        // Set up the table
        TableColumn<Sepatu, String> kodeColumn = new TableColumn<>("Kode");
        TableColumn<Sepatu, String> modelColumn = new TableColumn<>("Model");
        TableColumn<Sepatu, String> merkColumn = new TableColumn<>("Merk");
        TableColumn<Sepatu, String> warnaColumn = new TableColumn<>("Warna");
        TableColumn<Sepatu, Integer> hargaColumn = new TableColumn<>("Harga");
        
        kodeColumn.setCellValueFactory(new PropertyValueFactory<>("kode"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        merkColumn.setCellValueFactory(new PropertyValueFactory<>("merk"));
        warnaColumn.setCellValueFactory(new PropertyValueFactory<>("warna"));
        hargaColumn.setCellValueFactory(new PropertyValueFactory<>("harga"));
        
        table.getColumns().addAll(kodeColumn, modelColumn, merkColumn, warnaColumn, hargaColumn);
        //table.getItems().add(new Sepatu("CU069", "Female", "MEMEL", "Java", 100000));
        view();
        // Add selection listener to the table
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedSepatu = newValue;
            if (selectedSepatu != null) {
                hargaField.setText(String.valueOf(selectedSepatu.getHarga()));
                merkField.setText(selectedSepatu.getMerk());
                modelField.setText(selectedSepatu.getModel());
                warnaField.setText(selectedSepatu.getWarna());
                idField.setText(selectedSepatu.getKode());
            }
        });
        
        // Add components to BorderPane
        VBox formContainer = new VBox(10, form, transactionForm);
        formContainer.setAlignment(Pos.CENTER);
        
        this.setTop(formContainer);
        this.setCenter(table);
        this.setBottom(buttons);
    }
    
    private void prosesTransaksi() {
        try {
            if (selectedSepatu == null) {
                showAlert(AlertType.WARNING, "Pilih sepatu terlebih dahulu.");
                return;
            }
            
            int uangDibayar = Integer.parseInt(uangBayarField.getText());
            int kuantitas = Integer.parseInt(kuantitasField.getText());
            int harga = selectedSepatu.getHarga(); // Since harga is already an int
            int random1 = (int)(Math.random() * 10);
            int random2 = (int)(Math.random() * 10);
            int random3 = (int)(Math.random() * 10);
            String strukId = "ST" + random1+random2+random3;
            String kode = selectedSepatu.getKode();
            String model = selectedSepatu.getModel();
            String merk = selectedSepatu.getMerk();
            String warna = selectedSepatu.getWarna();
            int total = harga * kuantitas;
            
            if (uangDibayar < total) {
                showAlert(AlertType.ERROR, "Uang yang dibayarkan tidak cukup. Total harga adalah " + total);
            } else {
                showAlert(AlertType.INFORMATION, "Transaksi berhasil. Total harga adalah " + total);
                ds.insert(new Struk(strukId,kode,model,merk,warna,harga,kuantitas,uangDibayar));
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Input tidak valid. Pastikan uang, kuantitas, dan harga adalah angka.");
        }
    }
    
    private void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Informasi Transaksi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void view() {
		ResultSet rs = db.select(); // bikin result set sama dengan result set dari method select class Database
		try {
			while(rs.next()) { // selama masih ada barisan berikutnya di tabel
				// get nilai berdasarkan nama & tipe data kolomnya, simpan dalam array tipe Object
				Object[]barisan = {rs.getString("Kode"), rs.getString("Model"), 
						rs.getString("merk"),rs.getString("warna"), rs.getInt("harga")};
				// print nilainya
				table.getItems().add(new Sepatu(rs.getString("Kode"), rs.getString("Model"), 
						rs.getString("merk"),rs.getString("warna"), rs.getInt("harga")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    private void refresh() {
   	 ObservableList<Sepatu> items = table.getItems();
        items.clear();
        view();
   }
}
