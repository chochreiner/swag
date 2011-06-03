package at.ac.tuwien.out.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

public class RegisterForm extends Form {
    private static final long serialVersionUID = 6040480253914226510L;

    private TextField<String> name;

    public RegisterForm(String id) {
        super(id);

        name = new TextField<String>("name", new Model<String>());

        add(name);
    }

    @Override
    protected void onSubmit() {
        info("the form was submitted!");
    }
}
