package at.ac.tuwien.swag.webapp.in.messages;

import java.util.ArrayList;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class Notifications extends Panel {
    private static final long serialVersionUID = -4045913776508864182L;

    public Notifications(String id) {
        super(id);

        ArrayList<TODOREMOVE> notificationList = new ArrayList<TODOREMOVE>();

        for (int i = 0; i < 100; i++) {
            notificationList.add(new TODOREMOVE(i + "", ("moep" + i), "", ""));
        }

        DataView<TODOREMOVE> dataView =
            new DataView<TODOREMOVE>("notificationlist", new MessageSortableDataProvider(notificationList)) {
                private static final long serialVersionUID = -7500357470053232668L;

                @Override
                protected void populateItem(Item<TODOREMOVE> item) {
                    TODOREMOVE message = item.getModelObject();
                    PageParameters param = new PageParameters();
                    param.add("id", message.getId());
                    item.add(new Label("subject", message.getSubject()));
                    item.add(new BookmarkablePageLink<String>("view", MessageDetail.class, param));

                }
            };

        dataView.setItemsPerPage(15);
        add(dataView);

        add(new PagingNavigator("navigator", dataView));
    }

}
