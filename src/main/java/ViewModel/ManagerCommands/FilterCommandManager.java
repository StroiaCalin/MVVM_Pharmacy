package ViewModel.ManagerCommands;

import Model.Medicament;
import Model.MedicamenteRepository;
import ViewModel.ICommand;
import ViewModel.VM.VMManager;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class FilterCommandManager implements ICommand {
    private  MedicamenteRepository medicamenteRepository;
    private VMManager vmManager;

    public FilterCommandManager(VMManager vmManager) {
        this.medicamenteRepository = new MedicamenteRepository();
        this.vmManager = vmManager;
    }

    @Override
    public void Execute() {
        List<Medicament> medicament = new ArrayList<>();
        System.out.println(vmManager.getCategoryBox().get().getSelectedItem().toString());
        String farmacie = vmManager.getCategoryBox().get().getSelectedItem().toString();

        medicament = MedicamenteRepository.filterBy(vmManager.getCategoryBox().get().getSelectedItem().toString(),vmManager.getValoriTextField().get().toString());
        System.out.println(medicament);
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
