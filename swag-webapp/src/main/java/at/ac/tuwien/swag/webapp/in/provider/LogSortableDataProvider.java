package at.ac.tuwien.swag.webapp.in.provider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import at.ac.tuwien.swag.model.dto.LogMessageDTO;

public class LogSortableDataProvider extends SortableDataProvider<LogMessageDTO> {
    private static final long serialVersionUID = -6863920289836559112L;

    public LogSortableDataProvider(List<LogMessageDTO> data) {
        this.data = data;
    }

    private List<LogMessageDTO> data = new ArrayList<LogMessageDTO>();

    @Override
    public Iterator<? extends LogMessageDTO> iterator(int first, int count) {
        return data.listIterator(first);
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public IModel<LogMessageDTO> model(LogMessageDTO object) {
        return new Model<LogMessageDTO>(object);
    }
}
