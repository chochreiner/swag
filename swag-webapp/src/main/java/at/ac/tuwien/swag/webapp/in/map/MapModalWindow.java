package at.ac.tuwien.swag.webapp.in.map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

import at.ac.tuwien.swag.model.domain.MapUser;
import at.ac.tuwien.swag.webapp.in.base.BasePanel;

public abstract class MapModalWindow extends ModalWindow {
	private static final long serialVersionUID = -7126506235299190200L;

	private long squareId;
	
	public MapModalWindow(String id) {
        super(id);
        squareId =new Long(111);
        // Set sizes of this ModalWindow. You can also do this from the HomePage
        // but its not a bad idea to set some good default values.
        setInitialWidth(800);
        setInitialHeight(600);

        setTitle("TEST MODAL");
        
        // Set the content panel, implementing the abstract methods
        setContent(new SelectContentPanel(this.getContentId()){
			private static final long serialVersionUID = -1675999722693245461L;

			void onCancel(AjaxRequestTarget target) {
				
				
                MapModalWindow.this.onCancel(target);
            }

            void onSelect(AjaxRequestTarget target, String selection) {
                
            	
            	MapModalWindow.this.onSelect(target, selection);
            }
        });
    }
	
	public void loadBasePanel() {
		 setTitle("Base on sqaure");
		setContent(new BasePanel(this.getContentId()){

			private static final long serialVersionUID = 8122892907898863542L;

			@Override
			public void onCancel(AjaxRequestTarget target) {
				// TODO Auto-generated method stub	
			}

			@Override
			public void onSubmitButton(AjaxRequestTarget target, long squareId) {
				setTitle("BLAAA");
			}
		});
	}
	
	public void loadEmptySquareModalPanel() {
		 setTitle("Empty sqaure - No one is here");
		setContent(new EmptySquareModalPanel(this.getContentId()){
		
			private static final long serialVersionUID = 8794745869537409170L;

			@Override
			void onSettle(AjaxRequestTarget target, long squareId) {
				MapModalWindow.this.onSettle(target, squareId);
				
			}
		});
	}
	
	public void loadForeignSquareModalPanel() {
		 setTitle("Empty sqaure - No one is here");
		setContent(new ForeignSquareModalPanel(this.getContentId()){
			
			private static final long serialVersionUID = 2266595948958730018L;

			@Override
			public void onSelect(AjaxRequestTarget target, String selection) {
				// TODO Auto-generated method stub	
			}
		});
	}

    public long getSquareId() {
		return squareId;
	}



	public void setSquareId(long squareId) {
		this.squareId = squareId;
	}



	abstract void onCancel(AjaxRequestTarget target);
	abstract void onSettle(AjaxRequestTarget target, long squareId);
    abstract void onSelect(AjaxRequestTarget target, String selection);
}
