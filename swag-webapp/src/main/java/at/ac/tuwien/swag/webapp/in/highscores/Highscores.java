package at.ac.tuwien.swag.webapp.in.highscores;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.webapp.in.InPage;

public class Highscores extends InPage {
    private static final long serialVersionUID = -5939284250869774500L;

    public Highscores(PageParameters parameters) {
        super(parameters);

        List<ITab> tabs = new ArrayList<ITab>();
        tabs.add(new AbstractTab(new Model<String>("User Ranking")) {
            private static final long serialVersionUID = -5211908135285482567L;

            @Override
            public Panel getPanel(String panelId) {
                return new UserRanking(panelId);
            }
        });

        tabs.add(new AbstractTab(new Model<String>("Army Ranking")) {
            private static final long serialVersionUID = 2022873278626286072L;

            @Override
            public Panel getPanel(String panelId) {
                return new ArmyRanking(panelId);
            }
        });

        add(new AjaxTabbedPanel("highscoresTabs", tabs));

    }
}
