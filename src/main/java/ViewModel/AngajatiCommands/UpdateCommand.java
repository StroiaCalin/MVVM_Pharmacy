package ViewModel.AngajatiCommands;

import Model.Medicament;
import Model.MedicamentInfo;
import Model.MedicamenteRepository;
import Model.UtilizatorRepository;
import ViewModel.ICommand;
import ViewModel.VM.VMAngajati;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UpdateCommand implements ICommand {
    private VMAngajati vmAngajati;
    private MedicamenteRepository medicamenteRepository;
    private UtilizatorRepository utilizatorRepository;

    public UpdateCommand(VMAngajati vmAngajati)
    {

        this.vmAngajati = vmAngajati;
        this.utilizatorRepository = new UtilizatorRepository();
        this.medicamenteRepository = new MedicamenteRepository();
    }

    @Override
    public void Execute()
    {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = dateFormat.parse(vmAngajati.getValabilitateTextField().get().toString());
            System.out.println(utilDate);
            Date sqlDate = new Date(utilDate.getTime());

            int disponibilitate = Integer.parseInt(vmAngajati.getDisponibilitateTextField().get().toString());
            int pret = Integer.parseInt(vmAngajati.getPretTextField().get().toString());
            String producator = vmAngajati.getProducatorTextField().get().toString();

            String farmacia = utilizatorRepository.getFarmacieByName(vmAngajati.getNume().get().toString());

            System.out.println(sqlDate);
            MedicamentInfo medicamentInfo = new MedicamentInfo(disponibilitate, sqlDate, pret, producator, farmacia);

            Medicament medicament = new Medicament(vmAngajati.getDenumireTextField1().get().toString(), medicamentInfo);

            medicamenteRepository.updateMedicamentAtPharmacy(medicament,farmacia);
        } catch (
                ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing integer: " + e.getMessage());
        }
    }
}
