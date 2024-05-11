package Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


public class UtilizatorRepository {
    private Repository repository;

    public UtilizatorRepository()
    {
        this.repository = new Repository();
    }





    public String getFarmacieByName(String name) {
        String farmacie = null;
        String query = "SELECT farmacie FROM utilizatori WHERE name = ?";
        try (Connection connection = Repository.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    farmacie = resultSet.getString("farmacie");
                }
            }
        } catch (SQLException e) {
            Repository.getLOGGER().log(Level.WARNING, "DAO:getFarmacieByName " + e.getMessage());
        }
        return farmacie;
    }
    ////
    public Utilizator getUserByUsername(String username) {
        Utilizator utilizator = null;
        String query = "SELECT * FROM utilizatori WHERE name = ?";
        try (Connection connection = Repository.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String password = resultSet.getString("password");
                    String role = resultSet.getString("role");
                    String farmacie = resultSet.getString("farmacie");
                    utilizator = new Utilizator(id, username, password, role,farmacie);
                }
            }
        } catch (SQLException e) {
            Repository.getLOGGER().log(Level.WARNING, "DAO:getUserByUsername " + e.getMessage());
        }
        return utilizator;
    }



    private List<Utilizator> createObjects(ResultSet resultSet) throws SQLException {
        List<Utilizator> objects = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            String role = resultSet.getString("role");
            String farmacie = resultSet.getString("farmacie");
            Utilizator utilizator = new Utilizator(id, name,password,role,farmacie);
            objects.add(utilizator);
        }
        return objects;
    }
    public List<Utilizator> findAll() {
        String query = "SELECT * FROM utilizatori";
        List<Utilizator> objects = new ArrayList<>();

        try (Connection connection = Repository.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            objects = createObjects(resultSet);
        } catch (SQLException e) {
            Repository.getLOGGER().log(Level.WARNING,"DAO:findAll " + e.getMessage());
        }

        return objects;
    }
    ///read
    public void read(String name) {
        Utilizator utilizator = null;
        String query = "SELECT * FROM utilizatori WHERE name = ?";
        try (Connection connection = Repository.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String password = resultSet.getString("password");
                    String role = resultSet.getString("role");
                    String farmacie = resultSet.getString("farmacie");
                    // Assuming other fields here...

                    // Create a Utilizatori object
                    utilizator = new Utilizator(id, name, password, role, farmacie);

                    // Construct message to display in popup
                    String message = "ID: " + id + "\n" +
                            "Name: " + name + "\n" +
                            "Password: " + password + "\n" +
                            "Role: " + role + "\n" +
                            "Farmacie: " + farmacie; // Add other fields as needed

                    // Display message in popup
                    JOptionPane.showMessageDialog(null, message, "Utilizatori Details", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No utilizatori found with the name: " + name, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            Repository.getLOGGER().log(Level.WARNING, "DAO:read " + e.getMessage());
        }
    }
    // Create method
    public void create(Utilizator utilizator) {
        String query = "INSERT INTO utilizatori (name, password, role,farmacie) VALUES (?, ?, ?,?)";
        try (Connection connection = Repository.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, utilizator.getUsername());
            statement.setString(2, utilizator.getPassword());
            statement.setString(3, utilizator.getRole());
            statement.setString(4, utilizator.getFarmacie());
            // Set other parameters if needed for creation

            statement.executeUpdate();
        } catch (SQLException e) {
            Repository.getLOGGER().log(Level.WARNING, "DAO:create " + e.getMessage());
        }
    }


    // Update method
    public void update(Utilizator utilizator) {
        String query = "UPDATE utilizatori SET password = ?, role = ?, farmacie = ? WHERE name = ?";
        try (Connection connection = Repository.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, utilizator.getPassword());
            statement.setString(2, utilizator.getRole());
            statement.setString(3, utilizator.getFarmacie()); // Corrected parameter index
            statement.setString(4, utilizator.getUsername()); // Corrected parameter index

            statement.executeUpdate();
        } catch (SQLException e) {
            Repository.getLOGGER().log(Level.WARNING, "DAO:update " + e.getMessage());
        }
    }


    // Delete method
    public void delete(String name) {
        String query = "DELETE FROM utilizatori WHERE name = ?";
        try (Connection connection = Repository.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);

            statement.executeUpdate();
        } catch (SQLException e) {
            Repository.getLOGGER().log(Level.WARNING, "DAO:delete " + e.getMessage());
        }
    }
    
    
    public DefaultTableModel createTable(List<Utilizator> afisare) {
        if (afisare == null || afisare.isEmpty()) {
            return new DefaultTableModel();
        }

        Class<?> type = afisare.get(0).getClass();

        Field[] utilizatoriFields = Utilizator.class.getDeclaredFields();

        List<String> columnNames = new ArrayList<>();

        for (Field field : utilizatoriFields) {
            columnNames.add(field.getName());
        }

        DefaultTableModel tableModel = new DefaultTableModel(columnNames.toArray(new String[0]), 0);

        for (Utilizator obj : afisare) {
            Object[] rowData = new Object[columnNames.size()];
            int index = 0;

            for (Field field : utilizatoriFields) {
                field.setAccessible(true);
                try {
                    rowData[index++] = field.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            tableModel.addRow(rowData);
        }
        return tableModel;
    }
    
}
