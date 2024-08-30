package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Gui1 extends BorderPane {
    private TableView<Sepatu> table;
    private TextField merkField;
    private TextField modelField;
    private TextField warnaField;
    private TextField hargaField;
    private TextField idField;
    private TextField delField;
    Database db = new Database();
    int num=1;
    public Gui1() {
        // Initialize components
        table = new TableView<>();
        merkField = new TextField();
        modelField = new TextField();
        warnaField = new TextField();
        hargaField = new TextField();
        idField = new TextField();
        delField = new TextField();
        // Set up the form
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.add(new Label("Merk:"), 0, 0);
        form.add(merkField, 1, 0);
        form.add(new Label("Model:"), 0, 1);
        form.add(modelField, 1, 1);
        form.add(new Label("Warna:"), 0, 2);
        form.add(warnaField, 1, 2);
        form.add(new Label("Harga:"), 0, 3);
        form.add(hargaField, 1, 3);
        form.add(new Label("ID(Update Only):"), 0, 4);
        form.add(idField, 1, 4);
        form.add(new Label("ID(Delete Only):"), 0, 5);
        form.add(delField, 1, 5);
        
        // Set up buttons
        Button insertButton = new Button("Insert");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button viewButton = new Button("View");
        Button refresh = new Button("Refresh");
        HBox buttons = new HBox(10, insertButton, updateButton, deleteButton, viewButton,refresh);
        
        insertButton.setOnAction(event -> insertItem());
        viewButton.setOnAction(event -> viewItems());
        deleteButton.setOnAction(event -> deleteItem());
        updateButton.setOnAction(event -> updateItem());
        refresh.setOnAction(event ->refresh());
        
        // Set up the table
        TableColumn<Sepatu, String> kodeColumn = new TableColumn<>("Kode");
        TableColumn<Sepatu, String> modelColumn = new TableColumn<>("Model");
        TableColumn<Sepatu, String> merkColumn = new TableColumn<>("Merk");
        TableColumn<Sepatu, String> warnaColumn = new TableColumn<>("Warna");
        TableColumn<Sepatu, String> hargaColumn = new TableColumn<>("Harga");
        
        kodeColumn.setCellValueFactory(new PropertyValueFactory<>("kode"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        merkColumn.setCellValueFactory(new PropertyValueFactory<>("merk"));
        warnaColumn.setCellValueFactory(new PropertyValueFactory<>("warna"));
        hargaColumn.setCellValueFactory(new PropertyValueFactory<>("harga"));
        
        table.getColumns().addAll(kodeColumn, modelColumn, merkColumn, warnaColumn, hargaColumn);
        //table.getItems().add(new Sepatu("CU069", "Female", "MEMEL", "Java", 12000));
        // Add components to BorderPane
        view();
        this.setTop(form);
        this.setCenter(table);
        this.setBottom(buttons);
    }
    
    private void insertItem() {
        
        String model = modelField.getText();
        String warna = warnaField.getText();
        String harga = hargaField.getText();
        int convert = Integer.parseInt(harga);
        String merk = merkField.getText();
        String formatted = String.format("%03d", num); 
        char first = merk.charAt(0);
        String kode = first+formatted;
        
        
        db.insert(new Sepatu(kode, model, warna, merk, convert));
        num++;
    }
    
    private void viewItems() {
        // Implementation for viewing items
    	view();
        System.out.println("View Items");
    }
    
    private void deleteItem() {
        // Implementation for deleting an item
    	String del = delField.getText();
        
          
        //table.getItems().remove(0);
        db.delete(del); // Assuming you have a method in Database class to delete by Kode
          
            
        
    }
    
    private void updateItem() {
        // Implementation for updating an item
        System.out.println("Update Items");
        String carikode = idField.getText(); // Replace with your logic to get the inputted Kode
        // Iterate through the items in the table to find the one with the matching Kode
        String hasil = carikode.substring(1);
        String model = modelField.getText();
        String warna = warnaField.getText();
        String harga = hargaField.getText();
        int convert = Integer.parseInt(harga);
        String merk = merkField.getText();
        char first = merk.charAt(0);
        String kode = first + hasil;
        Sepatu updatedSepatu = new Sepatu(kode, model, merk, warna,convert);
        db.update(updatedSepatu);
        
        
    }
    
    public void view() {
		ResultSet rs = db.select(); // bikin result set sama dengan result set dari method select class Database
		try {
			while(rs.next()) { // selama masih ada barisan berikutnya di tabel
				// get nilai berdasarkan nama & tipe data kolomnya, simpan dalam array tipe Object
				Object[]barisan = {rs.getString("Kode"), rs.getString("Model"), 
						rs.getString("merk"),rs.getString("warna"), rs.getInt("harga")};
				
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
