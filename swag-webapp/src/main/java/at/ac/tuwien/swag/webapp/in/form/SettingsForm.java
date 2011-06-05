package at.ac.tuwien.swag.webapp.in.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;

public class SettingsForm extends Form<Void> {
    private static final long serialVersionUID = 6040480253914226510L;

    private RequiredTextField<String> firstname;
    private RequiredTextField<String> surname;
    private RequiredTextField<String> email;
    private TextArea<String> address;

    public SettingsForm(String id) {

        super(id);

        // TODO inject current values

        firstname = new RequiredTextField<String>("firstname", new Model<String>());
        surname = new RequiredTextField<String>("surname", new Model<String>());
        email = new RequiredTextField<String>("email", new Model<String>());
        address = new TextArea<String>("address", new Model<String>());
        address.setRequired(true);

        add(firstname);
        add(surname);
        add(email);
        add(address);
    }

    @Override
    protected void onSubmit() {
        info("TBD");
    }
}
