package at.ac.tuwien.out;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

public class OutNavigation extends Panel {

    public OutNavigation(String id) {
        super(id);
        add(new BookmarkablePageLink("login", Login.class));
    }

}
