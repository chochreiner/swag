package at.ac.tuwien.swag.webapp.out;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

public class OutTopNavigation extends Panel {
    private static final long serialVersionUID = -3395436270588237193L;

    public OutTopNavigation(String id) {
        super(id);
        add(new BookmarkablePageLink<String>("login", LoginPage.class));
        add(new BookmarkablePageLink<String>("register", RegisterPage.class));
        add(new BookmarkablePageLink<String>("about", About.class));
    }

}
