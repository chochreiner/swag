package at.ac.tuwien.swag.webapp.in.map;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.webapp.SwagWebSession;
import at.ac.tuwien.swag.webapp.in.ChangeMap;
import at.ac.tuwien.swag.webapp.in.InPage;

public class MapPage extends InPage {
    private static final long serialVersionUID = -5939284250869774500L;

    public MapPage(PageParameters parameters) {
        super(parameters);

        add(new FeedbackPanel("feedback"));

        SwagWebSession session = (SwagWebSession) getSession();

        if (session.getMapname() != null) {
            add(new GameMapContainerPanel("gameMapContainer", session.getMapname()));

        } else {
            setResponsePage(ChangeMap.class);
            add(new Label("gameMapContainer", "YOU HAVE NO MAP SELECTED"));
        }
    }
}
