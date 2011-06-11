package at.ac.tuwien.swag.webapp.in;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.webapp.SwagWebSession;

@AuthorizeInstantiation({ Roles.ADMIN, Roles.USER })
public abstract class InPage extends WebPage {
    private static final long serialVersionUID = 7723393016017265570L;

    public InPage(PageParameters parameters) {
        SwagWebSession session = (SwagWebSession) getSession();
        add(new InTopNavigation("topNavigation"));
        add(new InNavigation("mainNavigation"));
        add(new ResourcenSidePanel("resourcenSidePanel"));

        AdminNavigation adminNav = new AdminNavigation("adminNavigation");
        adminNav.setVisible(false);
        if (session.getRoles().hasRole(Roles.ADMIN)) {
            adminNav.setVisible(true);
        }
        add(adminNav);
    }
}
