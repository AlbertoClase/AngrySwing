package alberto_clase;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Troncos extends Base implements ActionListener{

    Timer animacion;
    public Troncos(Point localizacion) {
        super();
        this.setIcon(new ImageIcon(getClass().getResource("/tronco.png")));
        this.setSize(29,125);
        this.setLocation(localizacion);
        //TODO Auto-generated constructor stub
    }

    public void romper(){
        this.setIcon(new ImageIcon(getClass().getResource("/explosion_tronco.png")));
        
        animacion = new Timer(500, this);
        animacion.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        this.setIcon(null);
        animacion.stop();
    }
   
}
