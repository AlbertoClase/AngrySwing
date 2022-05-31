package alberto_clase;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Cerdo extends Base implements ActionListener{

    Timer animacion;
    int nVidas;
    boolean muerto=false;

    public Cerdo(Point localizacion,int nVidas) {
        super();
        this.nVidas = nVidas;
        crearCerdo();
        //TODO Auto-generated constructor stub
        this.setLocation((int)localizacion.getX(),(int)localizacion.getY()-this.getHeight());
    }
    
    private void crearCerdo(){
        if(nVidas<1||nVidas>3){
            nVidas=1;
        }
        String cualImagen = "/cerdo"+nVidas+".png";
        System.out.println(cualImagen);
        this.setIcon(new ImageIcon(getClass().getResource(cualImagen)));
        if(nVidas==1){
            this.setSize(84, 81);
        } else{
            if(nVidas==2){
                this.setSize(95,82);
            }else{
                this.setSize(133,144);
            }
        }
    }

    public void quitarVida(){
        nVidas-=1;

        if(nVidas==0){
            muerto=true;
            morir();
        }
    }

    private void morir(){
        this.setIcon(new ImageIcon(getClass().getResource("/muerte1.png")));
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
