package texteditor;
import java.applet.Applet;
import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;




public class TxtEditor extends Frame {
    
    //UNDO class obj
    UndoableTextArea1 txt;
    // Varaiables to use as parameters
    String filename,st,fn="Untitled",dn;
    boolean check=true;
    //Frame Object = FM
    Frame fm = new Frame();
    // FileDialouge Object = FD
    FileDialog FD;
    // Fonts Lib Object=font
       Font F;
    // Font Style&size
   int Fstyle = Font.PLAIN;
    int Fsize = 14;   
    //system clipboard object 
    Clipboard clip= getToolkit().getSystemClipboard();
    
    // TextEditor Intializtion Method
    TxtEditor(){
        
        F=new Font("TimesRoman",Fstyle,Fsize);
        setLayout(new GridLayout(1,1));
        // previous class object
        txt=new UndoableTextArea1(80,25);
        txt.setFont(F);
        add(txt);
        MenuBar mb = new MenuBar();
        // Intializing Menu Item:Fonttype
        Menu Fonttype=new Menu("FontType");
        MenuItem one,two,three,four;
        // Font Types
        one=new MenuItem("TimesRoman");
        two=new MenuItem("Century");
        three=new MenuItem("Arial");
        four=new MenuItem("Arial Black");
        
        Fonttype.add(one);
        Fonttype.add(two);
        Fonttype.add(three);
        Fonttype.add(four);
        
        // Adding ActionListerner for MenuItems
        one.addActionListener(new Type());
        two.addActionListener(new Type());
        three.addActionListener(new Type());
         
        //Font-Menu
        Menu fontMenu= new Menu("Font");
        // Menuitems of font-menu
        MenuItem boldMenu=new MenuItem("Bold");
        MenuItem italicMenu=new MenuItem("Italic");
        MenuItem plainMenu=new MenuItem("Plain");
        // adding items in it
        fontMenu.add(boldMenu);
        fontMenu.add(italicMenu);
        fontMenu.add(plainMenu);
        //ActionListner for FONT-MENU
        boldMenu.addActionListener(new FM());
        italicMenu.addActionListener(new FM());
        plainMenu.addActionListener(new FM());
       
        // Font Size
        Menu size = new Menu("Size");
        //intializing MenuItems
       MenuItem s1,s2,s3,s4,s5,s6,s7,s8;
       s1=new MenuItem("10");
       s2=new MenuItem("12");
       s3=new MenuItem("14");
       // Adding MenuItems into Menu
       size.add(s1);
       size.add(s2);
       size.add(s3);
       //ActionListner for FONT-MENU
       s1.addActionListener(new Size());
       s2.addActionListener(new Size());
       s3.addActionListener(new Size());
       
       size.addActionListener(new FM());
       fontMenu.add(size);
       
       Menu file=new Menu("File");
       // Menu Affect File
       Menu mFile=new Menu("File");
       MenuItem n = new MenuItem ("New",new MenuShortcut(KeyEvent.VK_N));
       MenuItem o = new MenuItem ("Open",new MenuShortcut(KeyEvent.VK_O));
       MenuItem s = new MenuItem ("Save",new MenuShortcut(KeyEvent.VK_S));
       MenuItem e = new MenuItem ("Exit",new MenuShortcut(KeyEvent.VK_E));
       
       // Adding MenuItems into Menu
       mFile.add(n);
       mFile.add(o);
       mFile.add(s);
       mFile.add(e);
       //Assigning MenuItems their Functions
       n.addActionListener(new New());
       o.addActionListener(new Open());
       s.addActionListener(new Save());
       e.addActionListener(new Exit());
       
       // Adding into MenuBar
      
       addWindowListener(new Win());
       
       // Edit MenuList
       Menu edit= new Menu("Edit");
       MenuItem cut = new MenuItem ("Cut",new MenuShortcut(KeyEvent.VK_X));
       MenuItem copy = new MenuItem ("Copy",new MenuShortcut(KeyEvent.VK_C));
       MenuItem paste = new MenuItem ("Paste",new MenuShortcut(KeyEvent.VK_V));
       
       // Assigning MenuItems their Functions
       cut.addActionListener(new Cut());
       copy.addActionListener(new Copy());
       paste.addActionListener(new Paste());
       
       // Adding MenuItems into Menu
       edit.add(cut);
       edit.add(copy);
       edit.add(paste);
    
       //Redo Undo Menu
       Menu undo=new Menu("Undo&Redo");
       MenuItem un=new MenuItem("Undo"); 
       MenuItem re=new MenuItem("Redo");
       // ActionListner of Functions
       un.addActionListener(new WW());
       re.addActionListener(new WW());
       // Adding MeniItems into MenuBar
       undo.add(un);
       undo.add(re);
          //Adding Menus into MenuBar
       mb.add(mFile);
       mb.add(fontMenu);
       mb.add(Fonttype);
       mb.add(undo);
       mb.add(edit);
       // displaying Menubar: mb
       setMenuBar(mb);
       
       mylistener mylist= new mylistener(); 
       addWindowListener(mylist);
       
    }//TxtEditor default Constructor ends
    
    // FM Class
class FM extends Applet implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
    
        String b = e.getActionCommand();
        if(b=="Bold"){
            F=new Font("Courier",Font.BOLD,Fsize);
            Fstyle=F.getStyle();
            txt.setFont(F);
        }
        if(b=="Plain"){
            F=new Font("Courier",Font.PLAIN,Fsize);
            Fstyle=F.getStyle();
            txt.setFont(F);
        }
        if(b=="Italic"){
            F=new Font("Courier",Font.ITALIC,Fsize);
            Fstyle=F.getStyle();
            txt.setFont(F);
        }
        repaint();
     }
    }

//  Size Class Editor

public class Size  implements ActionListener {


    public void actionPerformed(ActionEvent e) {
       
        // Missing Something
        int style=F.getStyle();
        String w= e.getActionCommand();
        
    if(w=="10")
    {
        F=new Font("Courier",style,10);
        txt.setFont(F);
        Fsize=F.getSize();
        repaint();
    }
    if(w=="12")
    {
        F=new Font("Courier",style,12);
        txt.setFont(F);
        Fsize=F.getSize();
        repaint();
    }
    if(w=="14")
    {
        F=new Font("Courier",style,14);
        txt.setFont(F);
        Fsize=F.getSize();
        repaint();
    }
    if(w=="16")
    {
        F=new Font("Courier",style,16);
        txt.setFont(F);
        Fsize=F.getSize();
        repaint();
    }
    if(w=="18")
    {
        F=new Font("Courier",style,18);
        txt.setFont(F);
        Fsize=F.getSize();
        repaint();
    }
        
    }
    
    
}

// WW class Method
class WW implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String se= e.getActionCommand();
            if(se.equals("Undo"))
                txt.undo();
            if(se.equals("Redo"))
                txt.redo();
        }
    
}

// class mylistener method
class mylistener extends WindowAdapter{
    @Override
    public void windowClosing(WindowEvent WE){
        if(!check)
            System.exit(0);
    } 

}

// class New Method
class New implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        txt.setText(" ");
        setTitle(filename);
        fn="Untitled";
    }
    

}

// Class OPEN Method
class Open implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            FileDialog fd=new FileDialog(TxtEditor.this,"Select");
            fd.show();
            if((fn=fd.getFile())!=null){
                filename =fd.getDirectory()+fd.getFile();
                dn=fd.getDirectory();
                setTitle(filename);
                //readFile();      
            }
            txt.requestFocus();
        }
}

// class SAVE method
class Save implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
           
            // some parameters are missing
            FileDialog fd=new FileDialog(TxtEditor.this,"SaveFile");
            fd.setFile(fn);
            fd.show();
            
            if(fd.getFile()!=null){
                filename=fd.getDirectory();
                setTitle(filename);
               // writeFile();
                txt.requestFocus();
            }
        }
}
// Class Exit Method
class Exit implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
          System.exit(0);
          // check if file is saved
          // or not
        }
    
}

//  Method to readFile
    void readFile (){
        BufferedReader d;
        StringBuffer sb=new StringBuffer();
        
        try{
                d=new BufferedReader(new FileReader(filename));
                String line;
                while((line=d.readLine())!=null)
                    sb.append(line+"");
                    txt.setText(sb.toString());
                    d.close();
        }
        catch(FileNotFoundException FNFE){
            System.out.println("File Not Found");
        }
        catch(IOException IOE){
            System.out.println("INPUT/OUTPUT Exception");
        }
}
    
    //  Method to readFile
    void writeFile (){
        
        try{
             DataOutputStream d=new
         DataOutputStream(new FileOutputStream(filename));
                String line=txt.getText();
                BufferedReader br=new
         BufferedReader(new StringReader(line));
                while((line=br.readLine())!=null){
                    d.writeBytes(line+"");
                }
                d.close();
        }
        catch(FileNotFoundException FNFE){
            System.out.println("File Not Found");
        }
        catch(IOException IOE){
            System.out.println("INPUT/OUTPUT Exception");
        }
}
    
    //CLASS Cut Method
    class Cut implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String sel=txt.getSelectedText();
            StringSelection ss=new StringSelection(sel);
            clip.setContents(ss, ss);
            txt.replaceRange("",
                    txt.getSelectionStart(),txt.getSelectionEnd());
        }
    }
    
    // CLASS COPY METHOD
        class Copy implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
             String sel=txt.getSelectedText();
             StringSelection clipString=new StringSelection(sel);
             clip.setContents(clipString, clipString);
        }
    
    }
    // CLASS PASTE METHOD
       class Paste implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Transferable cliptran=
                    clip.getContents(TxtEditor.this);
            try{
                String sel=(String)
                    cliptran.getTransferData(DataFlavor.stringFlavor);
            }
            catch(Exception eAll){
                System.out.println("Not Startung Flavour");
            }
        }
    }
    //Class WINDOWS Method
       class Win extends WindowAdapter{
           @Override
           public void windowClosing(WindowEvent we){
             //  ConfirmDialog cd=new ConfirmDialog();
               if(!check){
                   System.exit(0);
               }
           }
       }

       // CLASS COPY METHOD
        class Type implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
             String lbl=e.getActionCommand();
             if(lbl=="TimesRoman"){
                 F=new Font("TimesRoman",Fstyle,Fsize);
                 txt.setFont(F);
             }
             if(lbl=="Century"){
                 F=new Font("Century",Fstyle,Fsize);
                 txt.setFont(F);
             }
             if(lbl=="Arial"){
                 F=new Font("Arial",Fstyle,Fsize);
                 txt.setFont(F);
             }
             if(lbl=="Arial Black"){
                 F=new Font("ArialBlack",Fstyle,Fsize);
                 txt.setFont(F);
             }
             repaint();
        }
        
    
    }
        
        //  Class BC Method
        class BC implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            st=e.getActionCommand();
            JFrame jf= new JFrame("JColorChooser");
            // Color chooser Implemntation
        }
            
    }
        
        //  Class ColorChooser Method
        class colorChooser implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // JButton(Class) UnIntialized Object
            Button ok;
            // JColorChooser(Class) UnIntialized Object
            JColorChooser jcc;
           // Method can't be declaring here
        }
            
    }
     
        //  Class B Method
        class B implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Color is :");
            
        }
            
    }
       
        public static void main(String[] args) {
        Frame fm= new TxtEditor();
        fm.setSize(800,600);
        fm.setVisible(true);
        fm.show();
    }
        
}// TxtEditor Class Ends
