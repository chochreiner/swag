package at.ac.tuwien.swag.webapp.in.form;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.RangeValidator;

import com.google.inject.Inject;

import at.ac.tuwien.swag.model.dao.MapDAO;
import at.ac.tuwien.swag.model.dao.SquareDAO;
import at.ac.tuwien.swag.model.domain.Map;
import at.ac.tuwien.swag.model.domain.Square;

public class CreateMapForm extends Form<Void> {
    
	private static final long serialVersionUID = 6040480253914226510L;

	@Inject
	private MapDAO mapDao;
	
	@Inject
	private SquareDAO squareDao;
	
	
    private RequiredTextField<String> name;
    private RequiredTextField<Integer> width;
    private RequiredTextField<Integer> height;
    private RequiredTextField<Integer> user;

    public CreateMapForm(String id) {

        super(id);

        name = new RequiredTextField<String>("name", new Model<String>());
        width = new RequiredTextField<Integer>("width", new Model<Integer>());
        height = new RequiredTextField<Integer>("height", new Model<Integer>());
        user = new RequiredTextField<Integer>("user", new Model<Integer>());

        width.setType(Integer.class);
        height.setType(Integer.class);
        user.setType(Integer.class);

        width.add(new RangeValidator<Integer>(1, 1000));
        height.add(new RangeValidator<Integer>(1, 1000));
        user.add(new RangeValidator<Integer>(1, 1000));

        add(name);
        add(width);
        add(height);
        add(user);
    }

    @Override
    protected void onSubmit() {
    	try{
    		mapDao.findByName(name.getValue());
    		info("The Map name "+name.getValue()+"is already in use");
    		
    	}catch(NoResultException e) {
    		int startX = 1;
        	int endX = Integer.valueOf(width.getValue());
        	int startY = 1;
        	int endY = Integer.valueOf(height.getValue());
        	
            Map map = new Map();
           
            map.setName(name.getValue());
            map.setXSize(endX);
            map.setYSize(endY);
            map.setMaxNumUsers(Integer.valueOf(user.getValue()));

            List<Square> squares = new ArrayList<Square>();
           
            for (int y = startY; y <= endY; y++) {
            	for (int x = startX; x <= endX; x++) {
    	            Square square = new Square();
    	            square.setCoordX(x);
    	            square.setCoordY(y);
    	            square.setMap(map);
    	            square.setIsHomeBase(false);
    	            squares.add(square);
            	}
            }
        	
        	 mapDao.beginTransaction();
         		mapDao.insert(map);
         		for (Square sq : squares) {
         			squareDao.insert(sq);
         		}
         	mapDao.commitTransaction();
        	 	
            info("The new Map "+name.getValue()+" was created");
    	}
    }
}
