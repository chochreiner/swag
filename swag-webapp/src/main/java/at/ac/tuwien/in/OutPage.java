package at.ac.tuwien.in;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class OutPage extends WebPage {
    public OutPage(PageParameters parameters) {
    	add(new OutTopNavigation("topNavigation"));
        add(new OutNavigation("mainNavigation"));
        add(new OutSideNavigation("sideNavigation"));

    }
}
