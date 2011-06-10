package at.ac.tuwien.swag.webapp.in.admin.monitoring;

import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.DataView;

import at.ac.tuwien.swag.model.dto.LogMessageDTO;
import at.ac.tuwien.swag.webapp.service.LogService;

import com.google.inject.Inject;

public class SystemEvents extends Panel {
    private static final long serialVersionUID = -4045913776508864182L;

    @Inject
    private LogService logger;

    public SystemEvents(String id) {
        super(id);

        DataView<LogMessageDTO> dataView = logger.getAdminLog();

        dataView.setItemsPerPage(25);
        add(dataView);

        add(new PagingNavigator("navigator", dataView));

    }
}
