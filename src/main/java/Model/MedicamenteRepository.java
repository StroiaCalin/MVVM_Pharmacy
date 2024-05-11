package Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;


public class MedicamenteRepository {
	 private static Repository repository;

	    public MedicamenteRepository()
	    {
	        this.repository = new Repository();
	    }
	    
	    public DefaultTableModel createTable(List<Medicament> afisare) {
	        if (afisare == null || afisare.isEmpty()) {
	            return new DefaultTableModel();
	        }
	        
	        Class<?> type = afisare.get(0).getClass();
	        
	        Field[] medicamenteFields = Medicament.class.getDeclaredFields();
	        medicamenteFields = deleteLastElement(medicamenteFields);
	        Field[] medicamenteInfoFields = MedicamentInfo.class.getDeclaredFields();
	        
	        List<String> columnNames = new ArrayList<>();
	        
	        for (Field field : medicamenteFields) {
	            columnNames.add(field.getName());
	        }
	        
	        for (Field field : medicamenteInfoFields) {
	            columnNames.add(field.getName());
	        }
	        
	        DefaultTableModel tableModel = new DefaultTableModel(columnNames.toArray(new String[0]), 0);
	        
	        for (Medicament obj : afisare) {
	            Object[] rowData = new Object[columnNames.size()];
	            int index = 0;
	            
	            for (Field field : medicamenteFields) {
	                field.setAccessible(true);
	                try {
	                    rowData[index++] = field.get(obj);
	                } catch (IllegalAccessException e) {
	                    e.printStackTrace();
	                }
	            }
	            
	            MedicamentInfo info = obj.getMedicamenteInfo();
	            for (Field field : medicamenteInfoFields) {
	                field.setAccessible(true);
	                try {
	                    rowData[index++] = field.get(info);
	                } catch (IllegalAccessException e) {
	                    e.printStackTrace();
	                }
	            }
	            
	            tableModel.addRow(rowData);
	        }
	        return tableModel;
	    }
	   
	    
	    public static Field[] deleteLastElement(Field[] array) {
	        if (array == null || array.length == 0) {
	            return new Field[0]; // Return an empty array
	        }
	        Field[] newArray = new Field[array.length - 1];
	        System.arraycopy(array, 0, newArray, 0, newArray.length);

	        return newArray;
	    }

	    public static List<Medicament> sortByF(String field, String farmacie) {
	        List<Medicament> medicamentList = new ArrayList<>();
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;

	        try {
	            // Establish connection
	            connection = repository.getConnection();

	            // Define SQL query
	            String query = "SELECT * FROM medicamente WHERE farmacie = ? ORDER BY " + field;

	            // Create prepared statement
	            statement = connection.prepareStatement(query);
	            statement.setString(1, farmacie); // Set farmacie parameter

	            // Execute query
	            resultSet = statement.executeQuery();

	            // Process result set
	            while (resultSet.next()) {
	                int idMedicamente = resultSet.getInt("idMedicamente");
	                String nume = resultSet.getString("nume");

	                // Fetch MedicamenteInfo fields from ResultSet
	                Integer disponibilitate = resultSet.getInt("disponibilitate");
	                Date valabilitate = resultSet.getDate("valabilitate");
	                int pret = resultSet.getInt("pret");
	                String producator = resultSet.getString("producator");
	                // Note: farmacie is already specified in the query

	                // Create MedicamenteInfo object
	                MedicamentInfo medicamentInfo = new MedicamentInfo(disponibilitate, valabilitate, pret, producator, farmacie);

	                // Create Medicamente object
	                Medicament medicament = new Medicament(idMedicamente, nume, medicamentInfo);

	                // Add Medicamente object to list
	                medicamentList.add(medicament);
	            }

	            // Sort the list by the specified field
	            Collections.sort(medicamentList, new Comparator<Medicament>() {
	                @Override
	                public int compare(Medicament medicament1, Medicament medicament2) {
	                    switch (field) {
	                        case "disponibilitate":
	                            return Integer.compare(medicament1.getMedicamenteInfo().getDisoponibilitate(), medicament2.getMedicamenteInfo().getDisoponibilitate());
	                        case "pret":
	                            return Integer.compare(medicament1.getMedicamenteInfo().getPret(), medicament2.getMedicamenteInfo().getPret());
	                        case "valabilitate":
	                            return medicament1.getMedicamenteInfo().getValabilitate().compareTo(medicament2.getMedicamenteInfo().getValabilitate());
	                        case "producator":
	                            return medicament1.getMedicamenteInfo().getProducator().compareTo(medicament2.getMedicamenteInfo().getProducator());
	                        default:
	                            return 0;
	                    }
	                }
	            });
	        } catch (SQLException ex) {
	            Repository.getLOGGER().log(Level.SEVERE, "Error while fetching Medicamente", ex);
	        } finally {
	            // Close resources
	            try {
	                if (resultSet != null) {
	                    resultSet.close();
	                }
	                if (statement != null) {
	                    statement.close();
	                }
	                if (connection != null) {
	                    connection.close();
	                }
	            } catch (SQLException ex) {
	                Repository.getLOGGER().log(Level.SEVERE, "Error while closing resources", ex);
	            }
	        }

	        return medicamentList;
	    }
	    
	    
		/////findALL
	    public static List<Medicament> sortBy(String field) {
	        List<Medicament> medicamentList = new ArrayList<>();
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;

	        try {
	            connection = repository.getConnection();

	            String query = "SELECT * FROM medicamente ORDER BY \"+ field + \" ;";

	            // Create prepared statement
	            statement = connection.prepareStatement(query);

	            // Execute query
	            resultSet = statement.executeQuery();

	            // Process result set
	            while (resultSet.next()) {
	                int idMedicamente = resultSet.getInt("idMedicamente");
	                String nume = resultSet.getString("nume");

	                // Fetch MedicamenteInfo fields from ResultSet
	                Integer disoponibilitate = resultSet.getInt("disponibilitate");
	                Date valabilitate = resultSet.getDate("valabilitate");
	                int pret = resultSet.getInt("pret");
	                String producator = resultSet.getString("producator");
	                String farmacie = resultSet.getString("farmacie");
	                // Create MedicamenteInfo object
	                MedicamentInfo medicamentInfo = new MedicamentInfo(disoponibilitate, valabilitate, pret, producator,farmacie);

	                // Create Medicamente object
	                Medicament medicament = new Medicament(idMedicamente, nume, medicamentInfo);

	                // Add Medicamente object to list
	                medicamentList.add(medicament);
	            }
	            
	            // Sort the list by the disponibilitate field
	            Collections.sort(medicamentList, new Comparator<Medicament>() {
	                @Override
	                public int compare(Medicament medicament1, Medicament medicament2) {
	                    // Compare based on the disponibilitate field from MedicamenteInfo
	                	if(field.equals("disponibilitate"))
	                    {return Integer.compare(medicament1.getMedicamenteInfo().getDisoponibilitate(), medicament2.getMedicamenteInfo().getDisoponibilitate());
	                    }
	                	if(field.equals("pret"))
	                    {return Integer.compare(medicament1.getMedicamenteInfo().getPret(), medicament2.getMedicamenteInfo().getPret());
	                    }
	                	if(field.equals("valabilitate"))
	                    {
	                		Date valabilitate1 = medicament1.getMedicamenteInfo().getValabilitate();
	                        Date valabilitate2 = medicament2.getMedicamenteInfo().getValabilitate();
	                        return valabilitate1.compareTo(valabilitate2);
	                    }
	                 	if(field.equals("producator"))
	                {	String producator1 = medicament1.getMedicamenteInfo().getProducator();
	                    String producator2 = medicament2.getMedicamenteInfo().getProducator();
	                    return producator1.compareTo(producator2);
	                }
	                    return 0;
	                	
	                	}
	                	
	            });
	            
	        } catch (SQLException ex) {
	            Repository.getLOGGER().log(Level.SEVERE, "Error while fetching Medicamente", ex);
	        } finally {
	            // Close resources
	            try {
	                if (resultSet != null) {
	                    resultSet.close();
	                }
	                if (statement != null) {
	                    statement.close();
	                }
	                if (connection != null) {
	                    connection.close();
	                }
	            } catch (SQLException ex) {
	                Repository.getLOGGER().log(Level.SEVERE, "Error while closing resources", ex);
	            }
	        }

	        return medicamentList;
	    }

	public static List<Medicament> getAllMedicamenteByPharmacy(String pharmacyName) {
		List<Medicament> medicamentList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish connection to the database
			connection = repository.getConnection();

			// Define SQL query to select all medications from the specified pharmacy
			String query = "SELECT * FROM medicamente WHERE farmacie = ?";

			// Create prepared statement
			statement = connection.prepareStatement(query);

			// Set the parameter value for pharmacy name
			statement.setString(1, pharmacyName);

			// Execute query
			resultSet = statement.executeQuery();

			// Process result set
			while (resultSet.next()) {
				int idMedicamente = resultSet.getInt("idMedicamente");
				String nume = resultSet.getString("nume");

				// Fetch MedicamenteInfo fields from ResultSet
				Integer disponibilitate = resultSet.getInt("disponibilitate");
				Date valabilitate = resultSet.getDate("valabilitate");
				int pret = resultSet.getInt("pret");
				String producator = resultSet.getString("producator");

				// Create MedicamenteInfo object
				MedicamentInfo medicamentInfo = new MedicamentInfo(disponibilitate, valabilitate, pret, producator, pharmacyName);

				// Create Medicamente object
				Medicament medicament = new Medicament(idMedicamente, nume, medicamentInfo);

				// Add Medicamente object to list
				medicamentList.add(medicament);
			}
		} catch (SQLException ex) {
			Repository.getLOGGER().log(Level.SEVERE, "Error while fetching medications by pharmacy", ex);
		} finally {
			// Close resources
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				Repository.getLOGGER().log(Level.SEVERE, "Error while closing resources", ex);
			}
		}

		return medicamentList;
	}

	public static List<Medicament> filterBy(String column, String value) {
		List<Medicament> medicamentList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish connection
			connection = repository.getConnection();

			// Define SQL query
			String query = "SELECT * FROM medicamente WHERE " + column + " = ?";

			// Create prepared statement
			statement = connection.prepareStatement(query);

			// Set the parameter value
			statement.setString(1, value);

			// Execute query
			resultSet = statement.executeQuery();

			// Process result set
			while (resultSet.next()) {
				int idMedicamente = resultSet.getInt("idMedicamente");
				String nume = resultSet.getString("nume");

				// Fetch MedicamenteInfo fields from ResultSet
				Integer disoponibilitate = resultSet.getInt("disponibilitate");
				Date valabilitate = resultSet.getDate("valabilitate");
				int pret = resultSet.getInt("pret");
				String producator = resultSet.getString("producator");
				String farmacie = resultSet.getString("farmacie");

				// Create MedicamenteInfo object
				MedicamentInfo medicamentInfo = new MedicamentInfo(disoponibilitate, valabilitate, pret, producator, farmacie);

				// Create Medicamente object
				Medicament medicament = new Medicament(idMedicamente, nume, medicamentInfo);

				// Add Medicamente object to list
				medicamentList.add(medicament);
			}
		} catch (SQLException ex) {
			Repository.getLOGGER().log(Level.SEVERE, "Error while fetching Medicamente", ex);
		} finally {
			// Close resources
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				Repository.getLOGGER().log(Level.SEVERE, "Error while closing resources", ex);
			}
		}

		return medicamentList;
	}

	public static List<Medicament> getAllMedicamente() {
		List<Medicament> medicamentList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish connection to the database
			connection = repository.getConnection();

			// Define SQL query to select all medications
			String query = "SELECT * FROM medicamente";

			// Create prepared statement
			statement = connection.prepareStatement(query);

			// Execute query
			resultSet = statement.executeQuery();

			// Process result set
			while (resultSet.next()) {
				int idMedicamente = resultSet.getInt("idMedicamente");
				String nume = resultSet.getString("nume");

				// Fetch MedicamenteInfo fields from ResultSet
				Integer disponibilitate = resultSet.getInt("disponibilitate");
				Date valabilitate = resultSet.getDate("valabilitate");
				int pret = resultSet.getInt("pret");
				String producator = resultSet.getString("producator");
				String farmacie = resultSet.getString("farmacie");

				// Create MedicamenteInfo object
				MedicamentInfo medicamentInfo = new MedicamentInfo(disponibilitate, valabilitate, pret, producator, farmacie);

				// Create Medicamente object
				Medicament medicament = new Medicament(idMedicamente, nume, medicamentInfo);

				// Add Medicamente object to list
				medicamentList.add(medicament);
			}
		} catch (SQLException ex) {
			Repository.getLOGGER().log(Level.SEVERE, "Error while fetching all medications", ex);
		} finally {
			// Close resources
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				Repository.getLOGGER().log(Level.SEVERE, "Error while closing resources", ex);
			}
		}

		return medicamentList;
	}
	public static List<Medicament> filterBy(String column, String value, String farmacie) {
		List<Medicament> medicamentList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish connection
			connection = repository.getConnection();

			// Define SQL query
			String query = "SELECT * FROM medicamente WHERE " + column + " = ? AND farmacie = ?";

			// Create prepared statement
			statement = connection.prepareStatement(query);

			// Set the parameter values
			statement.setString(1, value);
			statement.setString(2, farmacie);

			// Execute query
			resultSet = statement.executeQuery();

			// Process result set
			while (resultSet.next()) {
				int idMedicamente = resultSet.getInt("idMedicamente");
				String nume = resultSet.getString("nume");

				// Fetch MedicamenteInfo fields from ResultSet
				Integer disoponibilitate = resultSet.getInt("disponibilitate");
				Date valabilitate = resultSet.getDate("valabilitate");
				int pret = resultSet.getInt("pret");
				String producator = resultSet.getString("producator");
				// No need to fetch "farmacie" again since it's used in the WHERE clause

				// Create MedicamenteInfo object
				MedicamentInfo medicamentInfo = new MedicamentInfo(disoponibilitate, valabilitate, pret, producator, farmacie);

				// Create Medicamente object
				Medicament medicament = new Medicament(idMedicamente, nume, medicamentInfo);

				// Add Medicamente object to list
				medicamentList.add(medicament);
			}
		} catch (SQLException ex) {
			Repository.getLOGGER().log(Level.SEVERE, "Error while fetching Medicamente", ex);
		} finally {
			// Close resources
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				Repository.getLOGGER().log(Level.SEVERE, "Error while closing resources", ex);
			}
		}

		return medicamentList;
	}


	public void insertMedicament(Medicament medicament) {
	        Connection connection = null;
	        PreparedStatement statement = null;

	        try {
	            // Obțineți conexiunea la baza de date
	            connection = repository.getConnection();

	            // Definiți interogarea SQL pentru inserarea unui nou medicament
	            String query = "INSERT INTO medicamente (nume, disponibilitate, valabilitate, pret, producator, farmacie) VALUES (?, ?, ?, ?, ?, ?)";

	            // Creați instrucțiunea pregătită
	            statement = connection.prepareStatement(query);

	            // Setăm valorile parametrilor pentru noul medicament
	            statement.setString(1, medicament.getNume());
	            statement.setInt(2, medicament.getMedicamenteInfo().getDisoponibilitate());
	            statement.setDate(3, medicament.getMedicamenteInfo().getValabilitate());
	            statement.setInt(4, medicament.getMedicamenteInfo().getPret());
	            statement.setString(5, medicament.getMedicamenteInfo().getProducator());
	            statement.setString(6, medicament.getMedicamenteInfo().getFarmacie());

	            // Executăm instrucțiunea de inserare
	            statement.executeUpdate();

				JOptionPane.showMessageDialog(null, "Medicamentul a fost inserat cu succes ", "Success", JOptionPane.INFORMATION_MESSAGE);
	        } catch (SQLException ex) {
	            Repository.getLOGGER().log(Level.SEVERE, "Eroare la inserarea medicamentului", ex);
	        } finally {
	            // Închidem resursele
	            try {
	                if (statement != null) {
	                    statement.close();
	                }
	                if (connection != null) {
	                    connection.close();
	                }
	            } catch (SQLException ex) {
	                Repository.getLOGGER().log(Level.SEVERE, "Eroare la închiderea resurselor", ex);
	            }
	        }
	    }
	    
	    public void readMedicamentByNameAndFarmacie(String medicamentName, String farmacieName) {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;

	        try {
	            // Obțineți conexiunea la baza de date
	            connection = repository.getConnection();

	            // Definiți interogarea SQL pentru a selecta toate coloanele bazate pe numele medicamentului și numele farmaciei
	            String query = "SELECT * FROM medicamente WHERE nume = ? AND farmacie = ?";

	            // Creați instrucțiunea pregătită
	            statement = connection.prepareStatement(query);
	            
	            // Set the parameter values for medicament name and farmacie name
	            statement.setString(1, medicamentName);
	            statement.setString(2, farmacieName);

	            // Executăm interogarea
	            resultSet = statement.executeQuery();

	            // Procesăm rezultatul interogării
	            if (resultSet.next()) {
	                // Afișăm rezultatele într-un popup
	                String message = "ID: " + resultSet.getInt("idMedicamente") + "\n" +
	                                 "Nume: " + resultSet.getString("nume") + "\n" +
	                                 "Disponibilitate: " + resultSet.getInt("disponibilitate") + "\n" +
	                                 "Valabilitate: " + resultSet.getDate("valabilitate") + "\n" +
	                                 "Pret: " + resultSet.getInt("pret") + "\n" +
	                                 "Producator: " + resultSet.getString("producator") + "\n" +
	                                 "Farmacie: " + resultSet.getString("farmacie");
	                
	                JOptionPane.showMessageDialog(null, message, "Detalii medicament", JOptionPane.INFORMATION_MESSAGE);
	            } else {
	                JOptionPane.showMessageDialog(null, "Medicamentul nu a fost găsit în farmacia specificată.", "Eroare", JOptionPane.ERROR_MESSAGE);
	            }
	        } catch (SQLException ex) {
	            Repository.getLOGGER().log(Level.SEVERE, "Eroare la citirea medicamentului", ex);
	        } finally {
	            // Închidem resursele
	            try {
	                if (resultSet != null) {
	                    resultSet.close();
	                }
	                if (statement != null) {
	                    statement.close();
	                }
	                if (connection != null) {
	                    connection.close();
	                }
	            } catch (SQLException ex) {
	                Repository.getLOGGER().log(Level.SEVERE, "Eroare la închiderea resurselor", ex);
	            }
	        }
	    }
	public List<Medicament> searchMedicamenteByName(String medicamentName) {
		List<Medicament> medicamentList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish connection
			connection = repository.getConnection();

			// Define SQL query to select all columns based on the medicament name
			String query = "SELECT * FROM medicamente WHERE nume LIKE ?";

			// Create prepared statement
			statement = connection.prepareStatement(query);

			// Set the parameter value for medicament name
			statement.setString(1, "%" + medicamentName + "%");

			// Execute query
			resultSet = statement.executeQuery();

			// Process result set
			while (resultSet.next()) {
				int idMedicamente = resultSet.getInt("idMedicamente");
				String nume = resultSet.getString("nume");

				// Fetch MedicamenteInfo fields from ResultSet
				Integer disponibilitate = resultSet.getInt("disponibilitate");
				Date valabilitate = resultSet.getDate("valabilitate");
				int pret = resultSet.getInt("pret");
				String producator = resultSet.getString("producator");
				String farmacie = resultSet.getString("farmacie");

				// Create MedicamenteInfo object
				MedicamentInfo medicamentInfo = new MedicamentInfo(disponibilitate, valabilitate, pret, producator, farmacie);

				// Create Medicamente object
				Medicament medicament = new Medicament(idMedicamente, nume, medicamentInfo);

				// Add Medicamente object to list
				medicamentList.add(medicament);
			}
		} catch (SQLException ex) {
			Repository.getLOGGER().log(Level.SEVERE, "Error while searching medicamente by name", ex);
		} finally {
			// Close resources
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				Repository.getLOGGER().log(Level.SEVERE, "Error while closing resources", ex);
			}
		}

		return medicamentList;
	}
	    public void readMedicamentByName(String medicamentName) {
	        Connection connection = null;
	        PreparedStatement statement = null;
	        ResultSet resultSet = null;

	        try {
	            // Obtain connection to the database
	            connection = repository.getConnection();

	            // Define SQL query to select all columns based on the medicament name from all pharmacies
	            String query = "SELECT * FROM medicamente WHERE nume = ?";

	            // Create prepared statement
	            statement = connection.prepareStatement(query);

	            // Set the parameter value for medicament name
	            statement.setString(1, medicamentName);

	            // Execute the query
	            resultSet = statement.executeQuery();

	            // Process the query result
	            while (resultSet.next()) {
	                // Display the results in a popup for each row
	                String message = "ID: " + resultSet.getInt("idMedicamente") + "\n" +
	                                 "Nume: " + resultSet.getString("nume") + "\n" +
	                                 "Disponibilitate: " + resultSet.getInt("disponibilitate") + "\n" +
	                                 "Valabilitate: " + resultSet.getDate("valabilitate") + "\n" +
	                                 "Pret: " + resultSet.getInt("pret") + "\n" +
	                                 "Producator: " + resultSet.getString("producator") + "\n" +
	                                 "Farmacie: " + resultSet.getString("farmacie");

	                JOptionPane.showMessageDialog(null, message, "Detalii medicament", JOptionPane.INFORMATION_MESSAGE);
	            }
	            
	            // If no rows found, display appropriate message
	            if (!resultSet.first()) {
	                JOptionPane.showMessageDialog(null, "Medicamentul nu a fost găsit în nicio farmacie.", "Eroare", JOptionPane.ERROR_MESSAGE);
	            }
	        } catch (SQLException ex) {
	            Repository.getLOGGER().log(Level.SEVERE, "Eroare la citirea medicamentului", ex);
	        } finally {
	            // Close resources
	            try {
	                if (resultSet != null) {
	                    resultSet.close();
	                }
	                if (statement != null) {
	                    statement.close();
	                }
	                if (connection != null) {
	                    connection.close();
	                }
	            } catch (SQLException ex) {
	                Repository.getLOGGER().log(Level.SEVERE, "Eroare la închiderea resurselor", ex);
	            }
	        }
	    }

	    
	    
	    public void updateMedicamentAtPharmacy(Medicament medicament, String pharmacyName) {
	        Connection connection = null;
	        PreparedStatement statement = null;

	        try {
	            // Obtain connection to the database
	            connection = repository.getConnection();

	            // Define SQL query for updating an existing medicament at the specified pharmacy
	            String query = "UPDATE medicamente SET disponibilitate = ?, valabilitate = ?, pret = ?, producator = ? WHERE nume = ? AND farmacie = ?";

	            // Create prepared statement
	            statement = connection.prepareStatement(query);

	            // Set the parameter values for the updated medicament
	            statement.setInt(1, medicament.getMedicamenteInfo().getDisoponibilitate());
	            statement.setDate(2, medicament.getMedicamenteInfo().getValabilitate());
	            statement.setInt(3, medicament.getMedicamenteInfo().getPret());
	            statement.setString(4, medicament.getMedicamenteInfo().getProducator());
	            statement.setString(5, medicament.getNume());
	            statement.setString(6, pharmacyName);

	            // Execute the update statement
	            int rowsUpdated = statement.executeUpdate();

	            // Check if any rows were updated
	            if (rowsUpdated > 0) {
					JOptionPane.showMessageDialog(null, "Medicamentul a fost actualizat cu succes ", "Success", JOptionPane.INFORMATION_MESSAGE);
	            } else {
					JOptionPane.showMessageDialog(null, "Medicamentul nu a fost actualizat cu succes ", "Success", JOptionPane.INFORMATION_MESSAGE);
	            }
	        } catch (SQLException ex) {
	            Repository.getLOGGER().log(Level.SEVERE, "Eroare la actualizarea medicamentului în farmacie", ex);
	        } finally {
	            // Close resources
	            try {
	                if (statement != null) {
	                    statement.close();
	                }
	                if (connection != null) {
	                    connection.close();
	                }
	            } catch (SQLException ex) {
	                Repository.getLOGGER().log(Level.SEVERE, "Eroare la închiderea resurselor", ex);
	            }
	        }
	    }

	    public void deleteMedicamentAtPharmacy(String medicamentName, String pharmacyName) {
	        Connection connection = null;
	        PreparedStatement statement = null;

	        try {
	            // Obtain connection to the database
	            connection = repository.getConnection();

	            // Define SQL query for deleting the medicament at the specified pharmacy
	            String query = "DELETE FROM medicamente WHERE nume = ? AND farmacie = ?";

	            // Create prepared statement
	            statement = connection.prepareStatement(query);

	            // Set the parameter values for the medicament name and pharmacy name
	            statement.setString(1, medicamentName);
	            statement.setString(2, pharmacyName);

	            // Execute the delete statement
	            int rowsDeleted = statement.executeUpdate();

	            // Check if any rows were deleted
	            if (rowsDeleted > 0) {
					JOptionPane.showMessageDialog(null, "Medicamentul '" + medicamentName + "' a fost șters cu succes din farmacia '" + pharmacyName + "'.", "Success", JOptionPane.INFORMATION_MESSAGE);
	            } else {
					JOptionPane.showMessageDialog(null, "Nu s-au găsit înregistrări pentru medicamentul '" + medicamentName + "' în farmacia '" + pharmacyName + "'.", "Failure", JOptionPane.ERROR_MESSAGE);
	            }
	        } catch (SQLException ex) {
	            Repository.getLOGGER().log(Level.SEVERE, "Eroare la ștergerea medicamentului din farmacie", ex);
	        } finally {
	            // Close resources
	            try {
	                if (statement != null) {
	                    statement.close();
	                }
	                if (connection != null) {
	                    connection.close();
	                }
	            } catch (SQLException ex) {
	                Repository.getLOGGER().log(Level.SEVERE, "Eroare la închiderea resurselor", ex);
	            }
	        }
	    }
}
