package at.ac.tuwien.swag.webapp.in.messages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.model.dto.MessageDTO;
import at.ac.tuwien.swag.webapp.in.InPage;
import at.ac.tuwien.swag.webapp.service.MessageService;

import com.google.inject.Inject;

public class MessageDetail extends InPage {
    private static final long serialVersionUID = -4045913776508864182L;

    @Inject
    private MessageService messages;

    public MessageDetail(PageParameters parameters) {
        super(parameters);

        String id = parameters.get("id").toString();

        MessageDTO message = messages.getMessagebyId( Long.parseLong(id), "TODO");

        if (message == null) {
            add(new Label("subject", "wrong id"));
            add(new Label("text", "wrong id"));
        } else {
            if (!message.getRead()) {
                messages.updateReadStatus( message.getId() );
            }

            add(new Label("subject", message.getSubject()));
            add(new Label("text", message.getText()));
        }

    }

}
