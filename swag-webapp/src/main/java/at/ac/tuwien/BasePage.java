package at.ac.tuwien;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class BasePage extends WebPage {

    public BasePage(final PageParameters parameters) {
        add(new MainNavigation("mainNavigation"));
        // TODO Add your page's components here
    }
}
