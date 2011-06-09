package at.ac.tuwien.swag.webapp.service;

import java.util.List;

import at.ac.tuwien.swag.model.dto.LogMessageDTO;

public interface LogService {

    public void logGameAction(String subject, String text);

    public void logUserAction(String subject, String text);

    public void logAdminAction(String subject, String text);

    public List<LogMessageDTO> getUserLog();

    public List<LogMessageDTO> getAdminLog();

    public List<LogMessageDTO> getGameLog();

}
