package alberto_clase;

import java.awt.Point;

import javax.swing.JLabel;

public class Fisicas {

    public static void ejecutarFisicas(Object objeto,VentanaJuego ventana){
        Base casteado = (Base)objeto;
        Point localizacion = casteado.getLocation();
        casteado.setLocation((int)localizacion.getX()+casteado.velocidadX,(int)localizacion.getY()+casteado.velocidadY);

        casteado.velocidadY = casteado.velocidadY+1;
        
        if(casteado.getBounds().getMaxY()>ventana.sueloHitbox){

            if(objeto.getClass()==Pajaro.class){// REBOTES DEL PÁJARO
                casteado.velocidadY =-(int)(casteado.velocidadY/1.4);
                casteado.velocidadX = (int)(casteado.velocidadX/1.3);
            }else{ //DE MOMENTO TRONQUITOS
                casteado.velocidadY =-(int)(casteado.velocidadY/3);
                casteado.velocidadX = (int)(casteado.velocidadX/2);
            }

        }



    }

    
}
