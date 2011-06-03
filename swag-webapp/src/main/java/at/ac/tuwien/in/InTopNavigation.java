package at.ac.tuwien.in;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

public class InTopNavigation extends Panel {
    private static final long serialVersionUID = -4312570776660359149L;

	public InTopNavigation(String id) {
        super(id);
        add(new BookmarkablePageLink<String>("settings", Settings.class));
        add(new BookmarkablePageLink<String>("changeMap", ChangeMap.class));
    }

}
