package ViewModel.AdministratorCommands;

import Model.Utilizator;
import Model.UtilizatorRepository;
import ViewModel.ICommand;
import ViewModel.VM.VMAdministrator;

public class CreateCommandAdministrator implements ICommand {
    private VMAdministrator vmAdministrator;
    private UtilizatorRepository utilizatorRepository;

    public CreateCommandAdministrator(VMAdministrator vmAdministrator)
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
        if (farmacia.isEmpty()) {
            farmacia = null; // Set farmacia to null
        }
        Utilizator utilizator = new Utilizator(name, password, role,farmacia);
        System.out.println(utilizator.toString());
        utilizatorRepository.create(utilizator);
    }
}

