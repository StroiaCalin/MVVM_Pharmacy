package ViewModel.AdministratorCommands;

import Model.Utilizator;
import Model.UtilizatorRepository;
import ViewModel.ICommand;
import ViewModel.VM.VMAdministrator;

public class UpdateCommandAdministrator implements ICommand {
    private VMAdministrator vmAdministrator;
    private UtilizatorRepository utilizatorRepository;

    public UpdateCommandAdministrator(VMAdministrator vmAdministrator)
    {
        this.vmAdministrator = vmAdministrator;
        this.utilizatorRepository = new UtilizatorRepository();
    }
    @Override
    public void Execute() {
        String name = vmAdministrator.getNumeTextField().get().toString();
        String password = vmAdministrator.getPasswordTextField().get().toString();
        String role =vmAdministrator.getRoleTextField().get().toString();
        String farmacia = vmAdministrator.getFarmaciaTextField().get().toString();

        Utilizator utilizator = new Utilizator(name, password, role,farmacia);
        utilizatorRepository.update(utilizator);
    }
}

