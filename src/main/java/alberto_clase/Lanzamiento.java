package alberto_clase;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Lanzamiento implements ActionListener {

    double tiempo = 0;
    boolean empezado = false;
    double vertical;
    VentanaJuego ventana;
    int potencia;
    Pajaro pajaro;
    Timer fps;
    ArrayList<Troncos> troncos;
    double gradosGirados = 0;
    boolean pajaroQuieto = false;
    int troncosQuietos = 0;

    boolean rebote = true;
    int alturaSuelo = +10; // Bajo el rebote en 10 pixel

    public Lanzamiento(Puntero puntero) {
        potencia = puntero.getLocation().x;
        pajaro = puntero.pajaroAsociado;
        fps = new Timer(30, this);
        fps.start();
        pajaro.velocidadX = (168 - (potencia - 5)) / 2;
        pajaro.velocidadY = -pajaro.velocidadX - 5;
        ventana = pajaro.ventana;
        troncos = pajaro.ventana.troncos;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TOD O Auto-generated method stub

        tiempo += fps.getDelay();

        if (e.getSource() == fps) {

            if(!pajaroQuieto){
                moverPed(pajaro);
            }
            colisiones();
        }
        empezado = true;
    }

    /**
     * Gestor de colisiones 
     */
    private void colisiones() {
        boolean controlRoce = tiempo % (fps.getDelay() * 8) == 0;
        Troncos otroTronco;
        Troncos troncoActual;
        for (int i = 0; i < troncos.size(); i++) {

            troncoActual = troncos.get(i);

            if (pajaro.getBounds().intersects(troncoActual.getBounds())) {//COLISION PAJARO
                pajaro.velocidadX = (int) (pajaro.velocidadX + troncoActual.velocidadX) / 2;
                troncoActual.velocidadX = (int) (pajaro.velocidadX * 1.2);
            }
            for (int j = i+1; j < troncos.size(); j++) { // CHECKEA COLISION ENTRE TRONCOS

                otroTronco = troncos.get(j);

                ColisionObjeto.colisionCaida(troncos.get(i), otroTronco, controlRoce);
                ColisionObjeto.colisionLateral(troncos.get(i), otroTronco, controlRoce, 4,1);

            }

            for (int z = 0; z < ventana.cerdos.size(); z++) {  //Checkea la colisión del cerdo con los troncos
                ColisionObjeto.colisionCaida(troncoActual, ventana.cerdos.get(z), controlRoce);
                ColisionObjeto.colisionLateral(troncoActual, ventana.cerdos.get(z), controlRoce, 1, 0.7);

                if((ventana.cerdos.get(z).velocidadX>1||ventana.cerdos.get(z).velocidadX<-1)&&!ventana.cerdos.get(z).muerto){
                    moverPed(ventana.cerdos.get(z));

                }
            }


            if (troncosQuietos == troncos.size() && pajaroQuieto) {
                fps.stop();
            }

            moverTronco(troncoActual);
        }
    }

    /**
     * Gestión del movimiento y rebote del pájaro
     */
    private void moverPed(Base ped) { 
        double rozamiento=1.2;
        ped.velocidadY = ped.velocidadY + 1;
        
        if (ped.getBounds().getMaxY() > ventana.sueloHitbox && ped.rebote) { //SI EL ped SOBREPASA EL SUELO Y PUEDE REBOTAR , REBOTA

            ped.velocidadY = -(int) (ped.velocidadY / 1.4); 
            ped.velocidadX = (int) (ped.velocidadX / rozamiento);

            if (ped.velocidadX < 1 && ped.velocidadX > -1) {
                ped.quieto = true;
            }
            ped.rebote = false;
        } else {

            ped.rebote = true; // EVITAR REBOTES POR FALLO DE INTERSECT
        }
        if (!(ped.velocidadX > 1.0 || ped.velocidadX < -1.0)
                    && !(ped.velocidadY < -1.0 || ped.velocidadY > 1.0)
                    && ped.getBounds().getMaxY() > ventana.sueloHitbox - 2 && empezado == true) { // SE QUEDÓ QUIETO
                                                                                                     // EL PÁJARO
                ped.quieto = true;
            } 
        ped.setLocation((int) ped.getLocation().getX() + ped.velocidadX,
                (int) ped.getLocation().getY() + ped.velocidadY);
        rotarPed(ped); //ROTACIÓN DE LA IMAGEN
    }

    /**
     * Generador de animación de rotamiento de la imagen del ped
     */
    private void rotarPed(Base ped) {
        System.out.println();
        BufferedImage iconoBuffeado = null;
        double unidad = 0;
        try {
            iconoBuffeado = ImageIO.read(getClass().getResource(ped.imagen));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        ped.gradosGirados += ped.velocidadX / 28.0;
        if (ped.velocidadX / 28.0 < 0.05) {
            ped.gradosGirados += 0.05;
        }
        System.out.println(ped.gradosGirados);
        ped.setIcon(new ImageIcon(Puntero.rotate(iconoBuffeado, ped.gradosGirados)));
    }


    /**
     * Controla el movimiento de los troncos, su roce con el suelo o si está encima de un tronco que se quede parado
     * @param tronco Tronco al que comprobar
     */
    private void moverTronco(Troncos tronco) {
        boolean controlRoce = tiempo % (fps.getDelay() * 4) == 0; // EL MULTIPLICADOR INDICA EL NÚMERO DE VECES POR EL
                                                                  // QUE SE DIVIDE EL ROCE YA QUE NO SE PUEDE CONTROLAR A TRAVÉS DE DECIMALES CORRECTAMENTE
                                                                   

        if ((tronco.getBounds().getMaxY() + 1.5 > ventana.sueloHitbox  //CONTACTO CON EL SUELO
                && tronco.getBounds().getMaxY() - 1.5 < ventana.sueloHitbox)) { // ESTÁ A RAS DEL SUELO,
                                                                                          // NO SALTA Y SOLO ROZA X
            tronco.velocidadY = 0; //PARA QUE DEJE DE CAER
            if (controlRoce) {  //ROZAMIENTO CON EL SUELO DISMINUYA LA VELOCIDAD
                tronco.velocidadX = (int) (tronco.velocidadX / 1.0005);
            }
        } else {
            if (tronco.getBounds().getMaxY() > ventana.sueloHitbox) { // EVITAR CAIDA DEBAJO DEL MAPA Y REBOTE
                tronco.setLocation((int) tronco.getBounds().getX(), 
                        (int) tronco.getLocation().getY() - tronco.velocidadY); // LO COLOCA POR ENCIMA DEL SUELO, SINO VOLVERÁ A DETECTAR LA COLISION Y HARÁ BUCLE INFINITO
                tronco.velocidadY = tronco.velocidadY / 2; //INDICE DE REBOTE-> VELOCIDAD / 2
            } else {
                tronco.velocidadY += 1; // ESTÁ EN CAIDA <- GRAVEDAD
            }
        }

        tronco.setLocation((int) tronco.getLocation().getX() + tronco.velocidadX,
                (int) tronco.getLocation().getY() + tronco.velocidadY); // APLICAR MOVIMIENTO
    }

}
