package alberto_clase;

import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Pajaro extends Base {
    
    JLabel pajarito;
    VentanaJuego ventana;

    public Pajaro(VentanaJuego ventana){
        super(new ImageIcon("C:\\Users\\AlbertoG\\Desktop\\Clase\\Programacion\\java\\TercerTrimestre\\angrybirds_swing\\src\\main\\java\\alberto_clase\\pajaro.png"));
        this.ventana = ventana;
        
    }

    public void colocar(){
        Rectangle tamañoVentana = ventana.getBounds();
        this.setLocation(50,(int)ventana.suelo.getBounds().getMinY()-70);
    }
}
