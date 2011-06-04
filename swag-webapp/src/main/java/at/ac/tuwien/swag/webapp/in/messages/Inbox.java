package at.ac.tuwien.swag.webapp.in.messages;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class Inbox extends Panel {
    private static final long serialVersionUID = -4045913776508864182L;

    public Inbox(String id) {
        super(id);

        ArrayList<TODOREMOVE> inboxList = new ArrayList<TODOREMOVE>();

        for (int i = 0; i < 100; i++) {
            inboxList.add(new TODOREMOVE(i + "", ("moep" + i), "sender", ""));
        }

        DataView<TODOREMOVE> dataView =
            new DataView<TODOREMOVE>("inboxlist", new MessageSortableDataProvider(inboxList)) {
                private static final long serialVersionUID = -7500357470052232668L;

                @Override
                protected void populateItem(Item<TODOREMOVE> item) {
                    TODOREMOVE message = item.getModelObject();
                    PageParameters param = new PageParameters();
                    param.add("id", message.getId());
                    item.add(new Label("subject", message.getSubject()));
                    item.add(new Label("sender", message.getSender()));
                    item.add(new BookmarkablePageLink("view", MessageDetail.class, param));
                }
            };

        dataView.setItemsPerPage(15);
        add(dataView);

        add(new PagingNavigator("navigator", dataView));

    }
}

class TODOREMOVE implements Serializable {
    private static final long serialVersionUID = -8784611490896491959L;
    private String subject;
    private String sender;
    private String reciever;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public TODOREMOVE(String id, String subject, String sender, String reciever) {
        this.id = id;
        this.subject = subject;
        this.sender = sender;
        this.reciever = reciever;
    }

}
