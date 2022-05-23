package alberto_clase;

import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Pajaro extends Base {
    
    JLabel pajarito;
    VentanaJuego ventana;

    public Pajaro(VentanaJuego ventana){
        super(new ImageIcon("src\\main\\resources\\pajaro.png"));
        this.ventana = ventana;
        
    }

    public void colocar(){
        Rectangle tamañoVentana = ventana.getBounds();
        this.setLocation(50,(int)ventana.suelo.getBounds().getMinY()-70);
    }
}
