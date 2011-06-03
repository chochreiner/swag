package at.ac.tuwien.swag.webapp.in;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class InPage extends WebPage {
	private static final long serialVersionUID = 7723393016017265570L;

    public InPage(PageParameters parameters) {
        add(new InTopNavigation("topNavigation"));
        add(new InNavigation("mainNavigation"));
        add(new InSideNavigation("sideNavigation"));
    }
}
