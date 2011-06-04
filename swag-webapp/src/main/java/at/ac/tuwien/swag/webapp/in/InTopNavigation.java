package at.ac.tuwien.swag.webapp.in;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import com.google.inject.Inject;

import at.ac.tuwien.swag.webapp.out.StartPage;
import at.ac.tuwien.swag.webapp.service.LoginService;

public class InTopNavigation extends Panel {
    private static final long serialVersionUID = -4312570776660359149L;

    @Inject
    private LoginService login;
    
	public InTopNavigation(String id) {
        super(id);
        add(new BookmarkablePageLink<String>( "settings",  Settings.class  ) );
        add(new BookmarkablePageLink<String>( "changeMap", ChangeMap.class ) );
        add(new Link<String>( "logout" ) {
			private static final long serialVersionUID = -976207757385096779L;

			@Override
        	public void onClick() {
				logout();
			}
        });
    }
	
	private void logout() {
		login.logout();
		
		getSession().invalidate();
		getPage().setResponsePage( StartPage.class );
	}

}
