package alberto_clase;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.management.monitor.Monitor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Puntero extends JLabel{

    MovimientoPuntero movimiento;
    ImageIcon image;
    Graphics2D punteritog;
    double grados=0;
    int x;
    int baseY;
    VentanaJuego ventana;
    Pajaro pajaroAsociado;
    Icon imagen;
    Lanzamiento lanzamientoDePajaro;

    public Puntero(Pajaro pajaro){
        super();
        image = new ImageIcon(getClass().getResource("puntero.png"));
        this.setIcon(image);
        pajaroAsociado = pajaro;
        movimiento = new MovimientoPuntero(this);
        this.addKeyListener(movimiento);
    }

    public void colocar(){
        Point lugarPajaro = pajaroAsociado.getLocation();
        this.setLocation(lugarPajaro.x+100,lugarPajaro.y+20);
    }

    

    protected void eliminarListener(){
        movimiento = null;
    }


    private void girarPuntero(boolean direccion){

        BufferedImage imagenCambiada=null;
        if(direccion){
            if(grados>-1.5708){ //SON 90 GRADOS
                grados-=0.0174533;
                try {
                    imagenCambiada = rotate(ImageIO.read(getClass().getResource("puntero.png")), grados);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                this.setIcon(new ImageIcon(imagenCambiada));
            }
        }else{
            if(grados<0){ //SON 0 GRADOS
                grados+=0.0174533;
                try {
                    imagenCambiada = rotate(ImageIO.read(getClass().getResource("puntero.png")), grados);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                this.setIcon(new ImageIcon(imagenCambiada));
            }
        }
    }

    

    

    public static BufferedImage rotate(BufferedImage image, double angle) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
        GraphicsConfiguration gc = getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(angle,w/2,h/2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }
    
    private static GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }


    class MovimientoPuntero extends KeyAdapter{    // KEYLISTENER

        Puntero puntero;
        public MovimientoPuntero(Puntero padre){
            this.puntero = padre;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            
            // TODO Auto-generated method stub
            if(e.getKeyCode() == KeyEvent.VK_UP&& puntero.getLocation().x>pajaroAsociado.getLocation().x+50){
                girarPuntero(true);
                puntero.setLocation(puntero.getLocation().x-1,puntero.getLocation().y-1);
                }
                
         
            
            if(e.getKeyCode() == KeyEvent.VK_DOWN&& puntero.getLocation().x<pajaroAsociado.getLocation().x+100){
                puntero.setLocation(puntero.getLocation().x+1,puntero.getLocation().y+1);
                System.out.println(puntero.getLocation().x);
                girarPuntero(false);
            }
    
            if(e.getKeyCode()==KeyEvent.VK_SPACE){
                lanzamientoDePajaro = new Lanzamiento(puntero);
                pajaroAsociado.ventana.removeKeyListener(this);
                puntero.setIcon(null);
            }
        }
    }

    
}
