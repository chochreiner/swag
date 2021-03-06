package at.ac.tuwien.swag.webapp.in;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

import at.ac.tuwien.swag.webapp.in.admin.CreateMap;
import at.ac.tuwien.swag.webapp.in.admin.ViewDBPage;
import at.ac.tuwien.swag.webapp.in.admin.monitoring.Monitoring;
import at.ac.tuwien.swag.webapp.out.DropDBPage;

public class AdminNavigation extends Panel {
    private static final long serialVersionUID = 6811737388664263612L;

    public AdminNavigation(String id) {
        super(id);

        add(new BookmarkablePageLink<String>("createMap", CreateMap.class));
        add(new BookmarkablePageLink<String>("monitoring", Monitoring.class));
        add(new BookmarkablePageLink<String>("viewDB", ViewDBPage.class));
        add(new BookmarkablePageLink<String>("testData", TestData.class));
//        add(new BookmarkablePageLink<String>("base", Base.class));
    }
}
