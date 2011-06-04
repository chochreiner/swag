package at.ac.tuwien.swag.webapp.in.messages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class MessageSortableDataProvider extends SortableDataProvider<TODOREMOVE> {
    private static final long serialVersionUID = -6863920289836559112L;

    public MessageSortableDataProvider(List<TODOREMOVE> data) {
        this.data = data;
    }

    private List<TODOREMOVE> data = new ArrayList<TODOREMOVE>();

    @Override
    public Iterator<? extends TODOREMOVE> iterator(int first, int count) {
        return data.listIterator(first);
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public IModel<TODOREMOVE> model(TODOREMOVE object) {
        return new Model<TODOREMOVE>(object);
    }
}
