package alberto_clase;

import java.awt.Point;

import javax.swing.ImageIcon;

public class Troncos extends Base {

    public Troncos(Point localizacion) {
        super();
        this.setIcon(new ImageIcon(getClass().getResource("/tronco.png")));
        this.setSize(29,125);
        this.setLocation(localizacion);
        //TODO Auto-generated constructor stub
    }
   
}
