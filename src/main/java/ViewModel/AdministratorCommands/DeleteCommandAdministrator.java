package ViewModel.AdministratorCommands;

import Model.UtilizatorRepository;
import ViewModel.ICommand;
import ViewModel.VM.VMAdministrator;

public class DeleteCommandAdministrator implements ICommand {
    private VMAdministrator vmAdministrator;
    private UtilizatorRepository utilizatorRepository;

    public DeleteCommandAdministrator(VMAdministrator vmAdministrator)
    {
        this.vmAdministrator = vmAdministrator;
        this.utilizatorRepository = new UtilizatorRepository();
    }
    @Override
    public void Execute() {
        String name = vmAdministrator.getNumeTextField().get().toString();
        utilizatorRepository.delete(name);
    }
}

