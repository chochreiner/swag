package at.ac.tuwien.swag.webapp.service;

import org.apache.wicket.markup.repeater.data.DataView;

import at.ac.tuwien.swag.model.dto.LogMessageDTO;

public interface LogService {

    public void logGameAction(String subject, String text);

    public void logUserAction(String subject, String text);

    public void logAdminAction(String subject, String text);

    public DataView<LogMessageDTO> getUserLog();

    public DataView<LogMessageDTO> getAdminLog();

    public DataView<LogMessageDTO> getGameLog();

}
