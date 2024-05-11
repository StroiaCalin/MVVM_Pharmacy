package ViewModel.AdministratorCommands;

import Model.Utilizator;
import Model.UtilizatorRepository;
import ViewModel.ICommand;
import ViewModel.VM.VMAdministrator;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class VizualizareCommandAdministrator implements ICommand {
    private VMAdministrator vmAdministrator;
    private UtilizatorRepository utilizatorRepository;

    public VizualizareCommandAdministrator(VMAdministrator vmAdministrator)
    {
        this.vmAdministrator = vmAdministrator;
        this.utilizatorRepository = new UtilizatorRepository();
    }
    @Override
    public void Execute() {
        List<Utilizator> utilizatorList = utilizatorRepository.findAll();
        DefaultTableModel tableModel = utilizatorRepository.createTable(utilizatorList);
        StringBuilder sb = new StringBuilder();
        sb.append("Tabel Utilizatori\n");
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
        vmAdministrator.Tabelare(sb);
    }
}

