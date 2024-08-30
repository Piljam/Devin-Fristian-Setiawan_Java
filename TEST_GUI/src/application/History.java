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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class History extends BorderPane {
    private TableView<Struk> table;
    private TextField merkField;
    private TextField modelField;
    private TextField warnaField;
    private TextField hargaField;
    private TextField idField;
    private TextField uangBayarField;
    private TextField kuantitasField;
    int num=1;
    DataStruk ds = new DataStruk();
    private Struk selectedSepatu;
    
    public History() {
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
        Button cetakStruk = new Button("Cetak Struk");
        HBox buttons = new HBox(10, viewButton,cetakStruk);
        
        viewButton.setOnAction(event->refresh());
        cetakStruk.setOnAction(event->cetakstruk());
       
        // Set up the table
        TableColumn<Struk, String> kodeColumn = new TableColumn<>("StrukID");
        TableColumn<Struk, String> kodeSepatuColumn = new TableColumn<>("KodeSepatu");
        TableColumn<Struk, String> modelColumn = new TableColumn<>("Model");
        TableColumn<Struk, String> merkColumn = new TableColumn<>("Merk");
        TableColumn<Struk, String> warnaColumn = new TableColumn<>("Warna");
        TableColumn<Struk, Integer> hargaColumn = new TableColumn<>("Harga");
        TableColumn<Struk, Integer> kuantitasColumn = new TableColumn<>("kuantitas");
        TableColumn<Struk, Integer> uangColumn = new TableColumn<>("uang_pembayaran");
        
        
        kodeColumn.setCellValueFactory(new PropertyValueFactory<>("StrukID"));
        kodeSepatuColumn.setCellValueFactory(new PropertyValueFactory<>("Kode"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        merkColumn.setCellValueFactory(new PropertyValueFactory<>("merk"));
        warnaColumn.setCellValueFactory(new PropertyValueFactory<>("warna"));
        hargaColumn.setCellValueFactory(new PropertyValueFactory<>("harga"));
        kuantitasColumn.setCellValueFactory(new PropertyValueFactory<>("kuantitas"));
        uangColumn.setCellValueFactory(new PropertyValueFactory<>("uang"));
        table.getColumns().addAll(kodeColumn,kodeSepatuColumn, modelColumn, merkColumn, warnaColumn, hargaColumn,kuantitasColumn,uangColumn);
        
        view();
        
        VBox formContainer = new VBox(10, form, transactionForm);
        formContainer.setAlignment(Pos.CENTER);
        
        //this.setTop(formContainer);
        this.setTop(table);
        this.setCenter(buttons);
    }
    
   
    private void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Informasi Transaksi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void view() {
		ResultSet rs = ds.select(); // bikin result set sama dengan result set dari method select class Database
		try {
			while(rs.next()) { // selama masih ada barisan berikutnya di tabel
				// get nilai berdasarkan nama & tipe data kolomnya, simpan dalam array tipe Object
				Object[]barisan = {rs.getString("StrukID"), rs.getString("KodeSepatu"),
						rs.getString("Model"),rs.getString("merk"),rs.getString("warna"), rs.getInt("Harga"),rs.getInt("kuantitas"),rs.getInt("uang_pembayaran")};
				// print nilainya
				
				table.getItems().add(new Struk(rs.getString("StrukID"), rs.getString("KodeSepatu"), 
						rs.getString("Model"),rs.getString("merk"),rs.getString("warna"), rs.getInt("Harga"),rs.getInt("kuantitas"),rs.getInt("uang_pembayaran")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    private void refresh() {
   	 ObservableList<Struk> items = table.getItems();
        items.clear();
        view();
   }
    
    private void cetakstruk() {
    	System.out.println("cetak struk");
    	Struk selectedStruk = table.getSelectionModel().getSelectedItem();
        if (selectedStruk == null) {
            showAlert(AlertType.WARNING, "Please select a row to print.");
            return;
        }
        
        String fileName = "struk_" + selectedStruk.getStrukID() + ".txt"; // Unique file name based on Struk ID
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Struk ID: " + selectedStruk.getStrukID());
            writer.newLine();
            writer.write("Kode Sepatu: " + selectedStruk.getKode());
            writer.newLine();
            writer.write("Model: " + selectedStruk.getModel());
            writer.newLine();
            writer.write("Merk: " + selectedStruk.getMerk());
            writer.newLine();
            writer.write("Warna: " + selectedStruk.getWarna());
            writer.newLine();
            writer.write("Harga: " + selectedStruk.getHarga());
            writer.newLine();
            writer.write("Kuantitas: " + selectedStruk.getKuantitas());
            writer.newLine();
            writer.write("Uang Pembayaran: " + selectedStruk.getUang());
            writer.newLine();
            writer.write("Total: " + (selectedStruk.getHarga() * selectedStruk.getKuantitas()));
            writer.newLine();
            writer.write("Thank you for your purchase!");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error while printing the receipt.");
        }
        
        showAlert(AlertType.INFORMATION, "Receipt has been printed to " + fileName);
    }
}
