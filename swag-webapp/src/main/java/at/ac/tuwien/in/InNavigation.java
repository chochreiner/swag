package at.ac.tuwien.in;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

import at.ac.tuwien.in.messages.Messages;

public class InNavigation extends Panel {
	private static final long serialVersionUID = 4142547334128417579L;

	public InNavigation(String id) {
        super(id);

        add( new BookmarkablePageLink<String>( "home",       MainPage.class   ) );
        add( new BookmarkablePageLink<String>( "map",        Map.class        ) );
        add( new BookmarkablePageLink<String>( "messages",   Messages.class   ) );
        add( new BookmarkablePageLink<String>( "statistics", Statistics.class ) );
    }
}
