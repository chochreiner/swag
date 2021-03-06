package at.ac.tuwien.swag.webapp.in.base;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.webapp.in.InPage;
import at.ac.tuwien.swag.webapp.in.form.StableForm;

public class Stable extends InPage {

    private static final long serialVersionUID = -2779784155739341375L;

    public Stable(PageParameters parameters) {
        super(parameters);

        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);

        add(new StableForm("stableForm", parameters.get("square").toLong()));

    }
}
