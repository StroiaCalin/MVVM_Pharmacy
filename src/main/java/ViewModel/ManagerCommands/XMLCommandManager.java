package ViewModel.ManagerCommands;

import Model.*;
import ViewModel.ICommand;
import ViewModel.VM.VMManager;

import java.util.ArrayList;
import java.util.List;

public class XMLCommandManager implements ICommand {
    private VMManager vmManager;
    private MedicamenteRepository medicamenteRepository;

    public XMLCommandManager(VMManager vmManager)
    {

        this.vmManager = vmManager;
        this.medicamenteRepository = new MedicamenteRepository();
    }
    @Override
    public void Execute() {
        List<Medicament> medicament = new ArrayList<>();
        medicament = MedicamenteRepository.getAllMedicamente();
        String filePath = "medicamenteManager.xml";
        XMLWriter.writeMedicamenteListToXML(medicament, filePath);
    }
}
