package at.ac.tuwien.out;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

public class OutTopNavigation extends Panel {
    private static final long serialVersionUID = -3395436270588237193L;

    public OutTopNavigation(String id) {
        super(id);
        add(new BookmarkablePageLink<String>("login", Login.class));
        add(new BookmarkablePageLink<String>("register", Register.class));
        add(new BookmarkablePageLink<String>("about", About.class));
    }

}
