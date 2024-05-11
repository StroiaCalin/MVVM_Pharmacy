package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVUtil {
    public static void writeCSV(List<Medicament> medicamentList, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write header
            writer.append("ID,Nume,Disponibilitate,Valabilitate,Pret,Producator,Farmacie\n");

            // Write data
            for (Medicament medicament : medicamentList) {
                writer.append(String.valueOf(medicament.getIdMedicamente())).append(",");
                writer.append(medicament.getNume()).append(",");
                writer.append(String.valueOf(medicament.getMedicamenteInfo().getDisoponibilitate())).append(",");
                writer.append(medicament.getMedicamenteInfo().getValabilitate().toString()).append(",");
                writer.append(String.valueOf(medicament.getMedicamenteInfo().getPret())).append(",");
                writer.append(medicament.getMedicamenteInfo().getProducator()).append(",");
                writer.append(medicament.getMedicamenteInfo().getFarmacie()).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
