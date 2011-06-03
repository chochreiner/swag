package at.ac.tuwien.out;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class OutPage extends WebPage {

    public OutPage(final PageParameters parameters) {
        add(new OutNavigation("mainNavigation"));
    }
}
