package alberto_clase;

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
        double pajaroVX;
        double pajaroVY;
        tiempo += fps.getDelay();

        if (e.getSource() == fps) {

            if (!(pajaro.velocidadX > 1.0 || pajaro.velocidadX < -1.0)
                    && !(pajaro.velocidadY < -1.0 || pajaro.velocidadY > 1.0)
                    && pajaro.getBounds().getMaxY() > ventana.sueloHitbox - 2 && empezado == true) { // SE QUEDÓ QUIETO
                                                                                                     // EL PÁJARO
                pajaroQuieto = true;
            } else {
                moverPajaro();
            }
            colisiones();
        }
        empezado = true;
    }

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

            for (int z = 0; z < ventana.cerdos.size(); z++) {

                ColisionObjeto.colisionCaida(troncoActual, ventana.cerdos.get(z), controlRoce);
                ColisionObjeto.colisionLateral(troncoActual, ventana.cerdos.get(z), controlRoce, 1, 0.7);
                if(ventana.cerdos.get(z).velocidadX>2||ventana.cerdos.get(z).velocidadX<-2){
                    if(ventana.cerdos.get(z).muerto){
                        ventana.remove(ventana.cerdos.get(z));
                        ventana.cerdos.remove(z);
                    }
                }
            }


            if (troncosQuietos == troncos.size() && pajaroQuieto) {
                fps.stop();
            }

            if (troncoActual == troncoActual) {
                moverTronco(troncoActual); // MUEVE LOS TRONCOS
            } else {
                troncosQuietos++;
            } /*  */
        }
    }

    private void moverPajaro() {
        double rozamiento;
        pajaro.velocidadY = pajaro.velocidadY + 1;
        rotarPajaro();
        if (pajaro.getBounds().getMaxY() > ventana.sueloHitbox && rebote) {

            pajaro.velocidadY = -(int) (pajaro.velocidadY / 1.4);

            rozamiento = 1.2;

            pajaro.velocidadX = (int) (pajaro.velocidadX / rozamiento);

            if (pajaro.velocidadX < 1 && pajaro.velocidadX > -1) {
                pajaroQuieto = true;
            }
            rebote = false;
        } else {

            rebote = true; // EVITAR REBOTES POR FALLO DE INTERSECT
        }
        pajaro.setLocation((int) pajaro.getLocation().getX() + pajaro.velocidadX,
                (int) pajaro.getLocation().getY() + pajaro.velocidadY);
    }

    private void rotarPajaro() {
        BufferedImage iconoBuffeado = null;
        double unidad = 0;
        try {
            iconoBuffeado = ImageIO.read(getClass().getResource("/pajaro.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        gradosGirados += pajaro.velocidadX / 28.0;
        if (pajaro.velocidadX / 28.0 < 0.05) {
            gradosGirados += 0.05;
        }

        ImageIcon imagenRotada = new ImageIcon(Puntero.rotate(iconoBuffeado, gradosGirados));
        pajaro.setIcon(imagenRotada);
    }

    private void moverTronco(Troncos tronco) {
        boolean controlRoce = tiempo % (fps.getDelay() * 4) == 0; // EL MULTIPLICADOR INDICA EL NÚMERO DE VECES POR EL
                                                                  // QUE SE DIVIDE EL ROCE // YA QUE NO SE PUEDE
                                                                  // CONTROLAR A TRAVÉS DE DECIMALES CORRECTAMENTE
        if ((tronco.getBounds().getMaxY() + 1.5 > ventana.sueloHitbox
                && tronco.getBounds().getMaxY() - 1.5 < ventana.sueloHitbox)
                || ((tronco.velocidadY < 1 && tronco.velocidadY > -1)
                        && !(tronco.getBounds().getMaxX() > ventana.sueloHitbox - 10))) { // ESTÁ A RAS DEL SUELO,
                                                                                          // NO SALTA Y SOLO ROZA X
            tronco.velocidadY = 0;
            if (controlRoce) {
                tronco.velocidadX = (int) (tronco.velocidadX / 1.0005);
            }
        } else {
            if (tronco.getBounds().getMaxY() > ventana.sueloHitbox) { // EVITAR CAIDA DEBAJO DEL MAPA

                tronco.setLocation((int) tronco.getBounds().getX(),
                        (int) tronco.getLocation().getY() - tronco.velocidadY);
                tronco.velocidadY = tronco.velocidadY / 2;

            } else {
                tronco.velocidadY += 1; // ESTÁ EN CAIDA <- GRAVEDAD
            }
        }

        tronco.setLocation((int) tronco.getLocation().getX() + tronco.velocidadX,
                (int) tronco.getLocation().getY() + tronco.velocidadY); // APLICAR MOVIMIENTO
    }

}
