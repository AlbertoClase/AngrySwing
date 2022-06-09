package alberto_clase;

import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Pajaro extends Base {
    

    JLabel pajarito;
    VentanaJuego ventana;
    
    public Pajaro(VentanaJuego ventana){
        super();
        imagen = "/pajaro.png";
        this.setIcon(new ImageIcon(getClass().getResource(imagen)));
        this.ventana = ventana;
        
    }

    public void colocar(){
        Rectangle tamañoVentana = ventana.getBounds();
        this.setLocation(50,(int)ventana.suelo.getBounds().getMinY()-this.getHeight());
    }
}
