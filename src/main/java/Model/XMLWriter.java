package Model;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class XMLWriter {

    public static void writeMedicamenteListToXML(List<Medicament> medicamentList, String filePath) {
        try {
            // Create an XMLEncoder object
            XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filePath)));

            // Write the list of Medicamente objects to the XML file
            encoder.writeObject(medicamentList);

            // Close the encoder
            encoder.close();

            System.out.println("Medicamente list has been successfully written to: " + filePath);
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + e.getMessage());
        }
    }
}
