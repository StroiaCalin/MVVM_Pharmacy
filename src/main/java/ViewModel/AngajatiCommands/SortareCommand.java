package ViewModel.AngajatiCommands;

import Model.Medicament;
import Model.MedicamenteRepository;
import Model.UtilizatorRepository;
import ViewModel.ICommand;
import ViewModel.VM.VMAngajati;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class SortareCommand implements ICommand {
    private UtilizatorRepository utilizatorRepository;
    private VMAngajati vmAngajati;
    private  MedicamenteRepository medicamenteRepository;

    public SortareCommand(VMAngajati vmAngajati)
    {
        this.utilizatorRepository = new UtilizatorRepository();
        this.vmAngajati = vmAngajati;
        this.medicamenteRepository = new MedicamenteRepository();
    }

    @Override
    public void Execute()
    {
        String farmacia = utilizatorRepository.getFarmacieByName(vmAngajati.getNume().get().toString());
        List<Medicament> medicament = new ArrayList<>();
        medicament = MedicamenteRepository.sortByF(vmAngajati.getCategoryBox().get().getSelectedItem().toString(),farmacia);

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
        vmAngajati.Tabelare(sb);
    }
}