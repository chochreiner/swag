package at.ac.tuwien.in;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

import at.ac.tuwien.in.admin.CreateMap;

public class InSideNavigation extends Panel {
    private static final long serialVersionUID = 6811737388664263612L;

	public InSideNavigation(String id) {
        super(id);
        add(new BookmarkablePageLink<String>("createMap", CreateMap.class));
    }

}
