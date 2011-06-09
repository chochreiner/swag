package at.ac.tuwien.swag.webapp.service.impl;

import java.util.Date;
import java.util.List;

import at.ac.tuwien.swag.model.dao.LogMessageDAO;
import at.ac.tuwien.swag.model.domain.LogMessage;
import at.ac.tuwien.swag.model.dto.LogMessageDTO;
import at.ac.tuwien.swag.webapp.service.LogService;

public class LogServiceImpl implements LogService {

    private LogMessageDAO logger;

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
    public List<LogMessageDTO> getUserLog() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LogMessageDTO> getAdminLog() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LogMessageDTO> getGameLog() {
        // TODO Auto-generated method stub
        return null;
    }

}
