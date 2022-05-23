package alberto_clase;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class VentanaJuego extends JFrame {
    
    Pajaro pajaro;
    Puntero puntero;
    JLabel suelo;
    Double sueloHitbox ;
    ArrayList<Troncos> troncos;
    JLabel fondo;
    ArrayList<Cerdo> cerdos;

    public VentanaJuego(){
        super("AngryBirds");
        this.setLayout(null);
        fondo =new JLabel(new ImageIcon(getClass().getResource("/fondo.png")));
        fondo.setSize(1400,700);
        fondo.setLocation(0,0);
        this.pack();
       
        suelo = new JLabel(new ImageIcon(getClass().getResource("/hierba.png")));
        suelo.setLocation(-5,530);
        suelo.setSize(1400,137);
        sueloHitbox = suelo.getBounds().getMinY()+2;
        
        pajaro = new Pajaro(this);
        pajaro.setSize(72,70);
        pajaro.colocar();
        this.add(pajaro); 
        
        puntero = new Puntero(pajaro);
        puntero.setSize(130,62);
        puntero.colocar();
        this.add(puntero);
        this.addKeyListener(puntero.movimiento);


        this.add(suelo);
        troncos = new ArrayList<>();

        

        
       /*  for (int i = 0; i < 5; i++) {
            for (int j = 0; j < i-2; j++) {
                troncos.add(new Troncos(new Point(800+i*60,(int)(sueloHitbox-125*2))));
            }
            troncos.add(new Troncos(new Point(800+i*50,(int)(sueloHitbox-125))));
        } */
        troncos.add(new Troncos(new Point(800,(int)(sueloHitbox-250))));
        troncos.add(new Troncos(new Point(800,(int)(sueloHitbox-125))));



        for (int i = 0; i < troncos.size(); i++) {
            this.add(troncos.get(i));
        } 
        
        cerdos = new ArrayList<>();
        cerdos.add(new Cerdo(new Point(840,(int)(sueloHitbox+0)),1));
        this.add(cerdos.get(0));





    }
}
