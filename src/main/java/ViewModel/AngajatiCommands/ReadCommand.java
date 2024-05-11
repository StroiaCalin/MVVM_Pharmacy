package ViewModel.AngajatiCommands;

import Model.MedicamenteRepository;
import Model.UtilizatorRepository;
import ViewModel.ICommand;
import ViewModel.VM.VMAngajati;

public class ReadCommand implements ICommand {
    private VMAngajati vmAngajati;
    private MedicamenteRepository medicamenteRepository;
    private UtilizatorRepository utilizatorRepository;


    public ReadCommand(VMAngajati vmAngajati)
    {
        this.vmAngajati = vmAngajati;
        this.utilizatorRepository = new UtilizatorRepository();
        this.medicamenteRepository = new MedicamenteRepository();
    }

    @Override
    public void Execute()
    {
        String farmacia = utilizatorRepository.getFarmacieByName(vmAngajati.getNume().get().toString());;
        medicamenteRepository.readMedicamentByNameAndFarmacie( vmAngajati.getDenumireTextField1().get().toString(),farmacia);
    }
}
