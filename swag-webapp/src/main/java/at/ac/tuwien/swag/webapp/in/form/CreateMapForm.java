package at.ac.tuwien.swag.webapp.in.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.RangeValidator;

public class CreateMapForm extends Form<Void> {
    private static final long serialVersionUID = 6040480253914226510L;

    private RequiredTextField<String> name;
    private RequiredTextField<Integer> width;
    private RequiredTextField<Integer> height;
    private RequiredTextField<Integer> user;

    public CreateMapForm(String id) {

        super(id);

        name = new RequiredTextField<String>("name", new Model<String>());
        width = new RequiredTextField<Integer>("width", new Model<Integer>());
        height = new RequiredTextField<Integer>("height", new Model<Integer>());
        user = new RequiredTextField<Integer>("user", new Model<Integer>());

        width.setType(Integer.class);
        height.setType(Integer.class);
        user.setType(Integer.class);

        width.add(new RangeValidator<Integer>(1, 1000));
        height.add(new RangeValidator<Integer>(1, 1000));
        user.add(new RangeValidator<Integer>(1, 1000));

        add(name);
        add(width);
        add(height);
        add(user);
    }

    @Override
    protected void onSubmit() {
        info("TBD");
    }
}
