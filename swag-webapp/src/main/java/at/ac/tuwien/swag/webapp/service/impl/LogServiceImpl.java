package at.ac.tuwien.swag.webapp.service.impl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;

import at.ac.tuwien.swag.model.dao.LogMessageDAO;
import at.ac.tuwien.swag.model.domain.LogMessage;
import at.ac.tuwien.swag.model.dto.LogMessageDTO;
import at.ac.tuwien.swag.webapp.in.provider.LogSortableDataProvider;
import at.ac.tuwien.swag.webapp.service.LogService;

import com.google.inject.Inject;

public class LogServiceImpl implements LogService {

    @Inject
    private LogMessageDAO logger;

    public LogServiceImpl() {

    }

    @Override
    public void logGameAction(String subject, String text) {
        LogMessage message = new LogMessage(new Date(), "GAME", subject, text);

        logger.beginTransaction();
        logger.insert(message);
        logger.commitTransaction();
    }

    @Override
    public void logUserAction(String subject, String text) {
        LogMessage message = new LogMessage(new Date(), "USER", subject, text);

        logger.beginTransaction();
        logger.insert(message);
        logger.commitTransaction();
    }

    @Override
    public void logAdminAction(String subject, String text) {
        LogMessage message = new LogMessage(new Date(), "ADMIN", subject, text);

        logger.beginTransaction();
        logger.insert(message);
        logger.commitTransaction();
    }

    @Override
    public DataView<LogMessageDTO> getUserLog() {
        return createDataview(logger.findByType("USER"));
    }

    @Override
    public DataView<LogMessageDTO> getAdminLog() {
        return createDataview(logger.findByType("ADMIN"));
    }

    @Override
    public DataView<LogMessageDTO> getGameLog() {
        return createDataview(logger.findByType("GAME"));
    }

    private DataView<LogMessageDTO> createDataview(List<LogMessage> logs) {

        List<LogMessageDTO> events = new ArrayList<LogMessageDTO>();

        for (LogMessage log : logs) {
            events.add(new LogMessageDTO(log.getTimestamp(), log.getType(), log.getSubject(), log.getText()));
        }

        final Format formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

        return new DataView<LogMessageDTO>("events", new LogSortableDataProvider(events)) {
            private static final long serialVersionUID = -7500357470042232668L;

            @Override
            protected void populateItem(Item<LogMessageDTO> item) {
                LogMessageDTO entry = item.getModelObject();

                item.add(new Label("subject", entry.getSubject()));
                item.add(new Label("text", entry.getText()));
                item.add(new Label("date", formatter.format(entry.getTimestamp())));
            }
        };
    }
}
