package at.ac.tuwien.swag.webapp.in;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

import at.ac.tuwien.swag.webapp.in.highscores.Highscores;
import at.ac.tuwien.swag.webapp.in.map.MapPage;
import at.ac.tuwien.swag.webapp.in.messages.Messages;

public class InNavigation extends Panel {
    private static final long serialVersionUID = 4142547334128417579L;

    public InNavigation(String id) {
        super(id);

        add(new BookmarkablePageLink<String>("home", MainPage.class));
        add(new BookmarkablePageLink<String>("map", MapPage.class));
        add(new BookmarkablePageLink<String>("messages", Messages.class));
        add(new BookmarkablePageLink<String>("highscores", Highscores.class));
    }
}
