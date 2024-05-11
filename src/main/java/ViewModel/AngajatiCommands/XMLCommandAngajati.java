package ViewModel.AngajatiCommands;

import Model.*;
import ViewModel.ICommand;
import ViewModel.VM.VMAngajati;

import java.util.ArrayList;
import java.util.List;

public class XMLCommandAngajati implements ICommand {
    private VMAngajati vmAngajati;
    private MedicamenteRepository medicamenteRepository;
    private UtilizatorRepository utilizatorRepository;

    public XMLCommandAngajati(VMAngajati vmAngajati)
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
        String filePath = "medicamenteAngajati.xml";
        XMLWriter.writeMedicamenteListToXML(medicament, filePath);
    }
}
