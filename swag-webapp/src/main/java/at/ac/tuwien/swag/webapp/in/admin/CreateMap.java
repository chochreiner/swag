package at.ac.tuwien.swag.webapp.in.admin;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.webapp.in.InPage;
import at.ac.tuwien.swag.webapp.in.form.CreateMapForm;

public class CreateMap extends InPage {
    private static final long serialVersionUID = -5939284250869774500L;

    public CreateMap(PageParameters parameters) {
        super(parameters);
        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);

        Form<?> newMessageForm = new CreateMapForm("createMapForm");
        add(newMessageForm);

    }

}
