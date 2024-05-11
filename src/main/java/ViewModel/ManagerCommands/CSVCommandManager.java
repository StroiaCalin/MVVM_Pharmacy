package ViewModel.ManagerCommands;

import Model.*;
import ViewModel.ICommand;
import ViewModel.VM.VMManager;

import java.util.ArrayList;
import java.util.List;

public class CSVCommandManager implements ICommand {
    private VMManager vmManager;
    private MedicamenteRepository medicamenteRepository;

    public CSVCommandManager(VMManager vmManager)
    {

        this.vmManager = vmManager;
        this.medicamenteRepository = new MedicamenteRepository();
    }
    @Override
    public void Execute() {
        List<Medicament> medicament = new ArrayList<>();
        medicament = MedicamenteRepository.getAllMedicamente();
        String filePath = "medicamenteManager.csv";
        CSVUtil.writeCSV(medicament, filePath);
    }
}
