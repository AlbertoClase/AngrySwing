package alberto_clase;

import java.awt.Rectangle;
import java.awt.geom.Line2D;

public class ColisionObjeto {

    /**
     * Gestionar colisión caida, evitar introduccion de objetos dentro de otros
     * 
     * @param colisionador Objeto que golpea hacia abajo
     * @param receptor     Objeto que recibe el impacto
     * @param controlRoce  Gestionar la cantidad de roce por fps
     */
    public static void colisionCaida(Base colisionador, Base receptor, boolean controlRoce) {
        Rectangle bordesReceptor = receptor.getBounds();
        if (colisionador.getBounds().intersectsLine(new Line2D.Double(bordesReceptor.getMinX(),
                bordesReceptor.getMinY() + 1, bordesReceptor.getMaxX(), bordesReceptor.getMinY() + 1))) { // APILAR
                                                                                                          // TRONCOS
            if (controlRoce) {
                colisionador.velocidadX = (int) (colisionador.velocidadX / 1.0005);
            }
            colisionador.velocidadY = -1;
        }
        if(receptor.getClass()==Cerdo.class){
            if(colisionador.velocidadY>2){
                ((Cerdo)(receptor)).muerto=true;
            }
        }

    }
    


    /**
     * Gestión colisión lateral de objetos
     * @param colisionador Objeto que ejece la fuerza lateral
     * @param receptor Receptor de la fuerza
     * @param controlRoce Gestionar la cantidad de roce por fps
     * @param peso Peso por el cual dividirá la fuerza de rebote
     * @param fuerza por la cual se multiplica la velocidad al impactar
     */
    public static void colisionLateral(Base colisionador, Base receptor, boolean controlRoce,int peso,double fuerza) {
        Rectangle bordesReceptor = receptor.getBounds();

        if (colisionador.getBounds()
                .intersectsLine(new Line2D.Double(bordesReceptor.getMinX(), bordesReceptor.getMaxY() - 2,
                        bordesReceptor.getMinX() + 2, bordesReceptor.getMinY() + 2)) // golpear troncos
                || colisionador.getBounds().intersectsLine(new Line2D.Double(bordesReceptor.getMaxX(),
                        bordesReceptor.getMaxY() - 2, bordesReceptor.getMaxX() - 2, bordesReceptor.getMinY() + 2))) {
            receptor.velocidadX = (int) (colisionador.velocidadX*fuerza);
            colisionador.velocidadX = (colisionador.velocidadX / peso);
        }
        if(receptor.getClass()==Cerdo.class){
            if(colisionador.velocidadX>2){
                ((Cerdo)(receptor)).muerto=true;
            }
        }
    }
}
