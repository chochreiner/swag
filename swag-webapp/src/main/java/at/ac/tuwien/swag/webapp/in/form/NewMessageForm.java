package at.ac.tuwien.swag.webapp.in.form;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;

import at.ac.tuwien.swag.webapp.service.MessageService;

import com.google.inject.Inject;

public class NewMessageForm extends Form<Void> {
    private static final long serialVersionUID = 6040480253914226510L;

    private final RequiredTextField<String> reciever;
    private final RequiredTextField<String> subject;
    private final TextArea<String> text;

    @Inject
    private MessageService messages;

    public NewMessageForm(String id) {
        super(id);

        reciever = new RequiredTextField<String>("reciever", new Model<String>());
        subject = new RequiredTextField<String>("subject", new Model<String>());
        text = new TextArea<String>("text", new Model<String>());
        text.setRequired(true);

        add(reciever);
        add(subject);
        add(text);
    }

    @Override
    protected void onSubmit() {

        Set<String> to = new HashSet<String>();

        StringTokenizer st = new StringTokenizer(reciever.getModel().getObject(), ";");
        while (st.hasMoreTokens()) {
            to.add(st.nextToken().trim());
        }

        messages.sendMessage(subject.getModel().getObject(), text.getModel().getObject(), to, "TODO");

        info("The message was sent");
    }
}
