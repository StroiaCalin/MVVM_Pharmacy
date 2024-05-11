package ViewModel.ManagerCommands;

import Model.Medicament;
import Model.MedicamenteRepository;
import Model.UtilizatorRepository;
import ViewModel.ICommand;
import ViewModel.VM.VMManager;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class ReadCommandManager implements ICommand {
    private VMManager vmManager;
    private MedicamenteRepository medicamenteRepository;
    private UtilizatorRepository utilizatorRepository;


    public ReadCommandManager(VMManager vmManager)
    {
        this.vmManager = vmManager;
        this.utilizatorRepository = new UtilizatorRepository();
        this.medicamenteRepository = new MedicamenteRepository();
    }

    @Override
    public void Execute()
    {  List<Medicament> medicament = new ArrayList<>();
        medicament = medicamenteRepository.searchMedicamenteByName(vmManager.getValoriTextField().get().toString());
        DefaultTableModel tableModel = medicamenteRepository.createTable(medicament);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            sb.append(tableModel.getColumnName(i)).append("\t");
        }
        sb.append("\n");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                sb.append(tableModel.getValueAt(i, j)).append("\t");
            }
            sb.append("\n");
        }
        vmManager.Tabelare(sb);
    }
}
