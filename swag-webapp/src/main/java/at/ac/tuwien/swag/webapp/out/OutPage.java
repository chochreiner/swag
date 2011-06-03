package at.ac.tuwien.swag.webapp.out;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class OutPage extends WebPage {
    private static final long serialVersionUID = 2888285193316163881L;

	public OutPage(final PageParameters parameters) {
        add(new OutTopNavigation("topNavigation"));
        add(new OutNavigation("mainNavigation"));
        add(new OutSideNavigation("sideNavigation"));
    }
}
