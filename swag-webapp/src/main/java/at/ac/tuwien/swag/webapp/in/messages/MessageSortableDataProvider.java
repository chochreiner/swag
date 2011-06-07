package at.ac.tuwien.swag.webapp.in.messages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import at.ac.tuwien.swag.model.dto.MessageDTO;

public class MessageSortableDataProvider extends SortableDataProvider<MessageDTO> {
    private static final long serialVersionUID = -6863920289836559112L;

    public MessageSortableDataProvider(List<MessageDTO> data) {
        this.data = data;
    }

    private List<MessageDTO> data = new ArrayList<MessageDTO>();

    @Override
    public Iterator<? extends MessageDTO> iterator(int first, int count) {
        return data.listIterator(first);
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public IModel<MessageDTO> model(MessageDTO object) {
        return new Model<MessageDTO>(object);
    }
}
