package at.ac.tuwien.in;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class InPage extends WebPage {
    public InPage(PageParameters parameters) {
        add(new InNavigation("mainNavigation"));

    }
}
