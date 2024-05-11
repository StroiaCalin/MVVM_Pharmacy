package ViewModel.AngajatiCommands;

import Model.CSVUtil;
import Model.Medicament;
import Model.MedicamenteRepository;
import Model.UtilizatorRepository;
import ViewModel.ICommand;
import ViewModel.VM.VMAngajati;

import java.util.ArrayList;
import java.util.List;

public class CSVCommandAngajati implements ICommand {
    private VMAngajati vmAngajati;
    private MedicamenteRepository medicamenteRepository;
    private UtilizatorRepository utilizatorRepository;

    public CSVCommandAngajati(VMAngajati vmAngajati)
    {

        this.vmAngajati = vmAngajati;
        this.utilizatorRepository = new UtilizatorRepository();
        this.medicamenteRepository = new MedicamenteRepository();
    }
    @Override
    public void Execute() {
        String farmacia = utilizatorRepository.getFarmacieByName(vmAngajati.getNume().get().toString());
        List<Medicament> medicament = new ArrayList<>();
        medicament = MedicamenteRepository.getAllMedicamenteByPharmacy(farmacia);
        String filePath = "medicamenteAngajati.csv";
        CSVUtil.writeCSV(medicament, filePath);
    }
}
