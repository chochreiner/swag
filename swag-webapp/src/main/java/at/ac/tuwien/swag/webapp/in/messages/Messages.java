package at.ac.tuwien.swag.webapp.in.messages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.webapp.in.InPage;

public class Messages extends InPage {
    private static final long serialVersionUID = -5939284250869774500L;

    public Messages(PageParameters parameters) {
        super(parameters);

        List<ITab> tabs = new ArrayList<ITab>();
        tabs.add(new AbstractTab(new Model<String>("new Message")) {
            private static final long serialVersionUID = -5211908135285482567L;

            @Override
            public Panel getPanel(String panelId) {
                return new NewMessage(panelId);
            }
        });

        tabs.add(new AbstractTab(new Model<String>("Inbox")) {
            private static final long serialVersionUID = 2022873278626286072L;

            @Override
            public Panel getPanel(String panelId) {
                return new Inbox(panelId);
            }
        });

        tabs.add(new AbstractTab(new Model<String>("Outbox")) {
            private static final long serialVersionUID = 1949786863242491270L;

            @Override
            public Panel getPanel(String panelId) {
                return new Outbox(panelId);
            }
        });

        tabs.add(new AbstractTab(new Model<String>("Notifications")) {
            private static final long serialVersionUID = 1949786864242491270L;

            @Override
            public Panel getPanel(String panelId) {
                return new Notifications(panelId);
            }
        });

        add(new AjaxTabbedPanel("messagesTabs", tabs));

    }
}
