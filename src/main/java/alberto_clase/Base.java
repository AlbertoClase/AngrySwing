package alberto_clase;

import javax.swing.Icon;
import javax.swing.JLabel;

public class Base extends JLabel{
    
    double gradosGirados=0;
    boolean quieto;
    String imagen;
    int velocidadX=0;
    int velocidadY=0;
    boolean rebote=false;
    public Base(){
        super();
    }

    public Base(Icon imagen){
        super(imagen);
    }

}
