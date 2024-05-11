package ViewModel.ManagerCommands;

import Model.Medicament;
import Model.MedicamenteRepository;
import ViewModel.ICommand;
import ViewModel.VM.VMManager;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class SortareCommandManager implements ICommand {
    private VMManager vmManager;
    private  MedicamenteRepository medicamenteRepository;

    public SortareCommandManager(VMManager vmManager)
    {
        this.vmManager = vmManager;
        this.medicamenteRepository = new MedicamenteRepository();
    }

    @Override
    public void Execute()
    {
        List<Medicament> medicament = new ArrayList<>();
        medicament = MedicamenteRepository.sortBy(vmManager.getCategoryBox().get().getSelectedItem().toString());

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
