package at.ac.tuwien.in.messages;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;

import at.ac.tuwien.in.form.NewMessageForm;

public class NewMessage extends Panel {
    private static final long serialVersionUID = -4045913776508864182L;

    public NewMessage(String id) {
        super(id);
        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);

        Form<?> newMessageForm = new NewMessageForm("newMessageForm");
        add(newMessageForm);
    }

}
