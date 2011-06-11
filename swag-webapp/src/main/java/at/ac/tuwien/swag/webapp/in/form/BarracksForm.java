package at.ac.tuwien.swag.webapp.in.form;

import java.util.Arrays;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.Model;

import at.ac.tuwien.swag.model.domain.SoldierType;

public class BarracksForm extends Form<Void> {
    private static final long serialVersionUID = 6040480253914226510L;

    private RequiredTextField<Integer> amount;
    private DropDownChoice<SoldierType> dropdown;

    public BarracksForm(String id) {
        super(id);

        amount = new RequiredTextField<Integer>("amount", new Model<Integer>());
        dropdown =
            new DropDownChoice<SoldierType>("soldierType", new Model<SoldierType>(), Arrays.asList(new SoldierType[]{
                SoldierType.PEASANT,
                SoldierType.FOOTSOLDIER }));

        add(amount);
        add(dropdown);
    }

    @Override
    protected void onSubmit() {

        info("new soldiers built");
    }
}
