package view.pages;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import java.util.Hashtable;

/**
 * Created by xuxiangzhe on 2017/6/23.
 */
public class CreateFileController implements InitialContextFactory {
    @Override
    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {

        return null;
    }
    @FXML
    Button BFButton,OOKButton;
    @FXML
    TextField fileName;

    @FXML
    void createBFFile(){

    }

    @FXML
    void createOOKFile(){

    }

}
