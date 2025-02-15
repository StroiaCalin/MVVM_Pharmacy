package ViewModel.VM;

import ViewModel.ICommand;
import ViewModel.LoginCommand;
import lombok.Setter;
import lombok.Getter;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;

@Getter
@Setter
public class VMLogin {
    private Property<String> username = PropertyFactory.createProperty("textBoxUserNameTextField", this, String.class);
    private Property<String> password = PropertyFactory.createProperty("textBoxPasswordTextField", this, String.class);
    public ICommand loginCommand;

    public VMLogin()
    {
        this.loginCommand = new LoginCommand(this);
    }
}
