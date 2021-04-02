package texteditor;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.util.Hashtable;
import javax.swing.event.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.StateEdit;
import javax.swing.undo.StateEditable;
import javax.swing.undo.UndoManager;


 class UndoableTextArea1 extends TextArea implements StateEditable {
    
    private final static String KEY_STATE = "UndoableTextAreaKey1";
    private boolean textChanged=false;
    private UndoManager undoManager;
    private StateEdit currentEdit;
    
    // constructor with No parameters
    public UndoableTextArea1(){
        super();
        initUndoable();
    }
    
     // constructor with Only String 
    public UndoableTextArea1(String value){
        super(value);
        initUndoable();
    }
    
     // constructor Parameters(rows,columns)
    public UndoableTextArea1(int rows, int columns){
        super(rows,columns);
        initUndoable();
    }
    
     // constructor Parameters(string,rows,columns)
    public UndoableTextArea1(String value,int rows,int columns){
        super(value,rows,columns);
        initUndoable();
    }
    
     // constructor Parameters(string,rows,columns,scrollbars)
    public UndoableTextArea1(String value,int rows,
                             int columns,int scrollBars){
        super(value,rows,columns,scrollBars);
        initUndoable();
    }

    //  Method to take snap of added data
    
    private void takesnapshot(){
        if(textChanged){
            currentEdit.end();
            undoManager.addEdit(currentEdit);
            textChanged=false;
            currentEdit= new StateEdit(this);
        }
    }
    
    // catching exception for undo function
    public boolean undo(){
        try{
            undoManager.undo();
            return true;
        }
        catch(CannotUndoException e){
            System.out.println("cannot Undo");
            return false;
        }
        
    }
    
     // catching exception for redo function
    public boolean redo(){
        try{
            undoManager.redo();
            return true;
        }
        catch(CannotRedoException e){
            System.out.println("cannot do");
            return false;
        }
        
    }
    
            
      
             //  Abstract methods of Interface StateEditable
    @Override
    public void storeState(Hashtable state) {
        state.put(KEY_STATE,getText());
    }

    @Override
    public void restoreState(Hashtable state) {
        Object data=state.get(KEY_STATE);
        if (data!=null){
            setText((String)data);
        }
    }
    
    //  Method to Undo the Text
    
    private void initUndoable(){
        
        undoManager=new UndoManager();
        currentEdit= new StateEdit(this);
        
        // to listen keys to make changes
        // when user ask
        addKeyListener (new KeyAdapter()
        {
        public void KeyPressed(KeyEvent kEvent)
        {
        if (kEvent.isActionKey())
        {
        takesnapshot();
        }
            }
                });
        
        // Adding Focus Listerner
        addFocusListener (new FocusAdapter()
        {
         public void focusLost(FocusEvent fEvent)
        {
        textChanged=true;
        takesnapshot();
        }
             });
        
        // Adding Text Listerner
        addTextListener (new TextListener(){
         public void textValueChanged(TextEvent tEvent)
        {
        textChanged=true;
        takesnapshot();
        }
             });
        
    }
}

