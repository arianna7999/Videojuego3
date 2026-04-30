package juego;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import elementos.pantallas.MenuOpciones;
import elementos.pantallas.MenuPrincipal;
import utils.AudioPlayer;

public class VtaJuego extends JFrame {

    private JPanel contenedor;
    private CardLayout cardLayout;
    private AudioPlayer audioPlayer = new AudioPlayer();

    public VtaJuego(PanelJuego n) {
        // Configuraciones de la Ventana (Modo Ventana)
        this.setTitle("Mi Juego");                   // Título de la ventana
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(false);                 // false = Mostrar bordes y botones
        this.setResizable(true);                    // true = Permitir cambiar el tamaño
        
        // Definir tamaño de la ventana
        this.setSize(1280, 720);                    // Tamaño inicial
        this.setLocationRelativeTo(null);           // Centrar en pantalla

        // Configuración del Layout
        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        // Inicialización de Pantallas
        MenuPrincipal menu = new MenuPrincipal(this);
        MenuOpciones opciones = new MenuOpciones(this);

        contenedor.add(menu, "MENU");
        contenedor.add(n, "JUEGO");
        contenedor.add(opciones, "OPCIONES");

        // Agregar contenedor principal al JFrame
        this.add(contenedor);

        // Listener de foco para pausar el juego
        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                // Opcional: Reanudar audio o juego
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                if (n.getGame() != null) {
                    n.getGame().windowFocusLost();
                }
            }
        });

        // Mostrar la ventana
        this.setVisible(true);
    }

    public void mostrarMenu() {
        cardLayout.show(contenedor, "MENU");
        contenedor.getComponent(0).requestFocusInWindow();
    }

    public void mostrarJuego() {
        cardLayout.show(contenedor, "JUEGO");
        contenedor.getComponent(1).requestFocusInWindow();
    }

    public void mostrarOpciones() {
        cardLayout.show(contenedor, "OPCIONES");
        contenedor.revalidate();
        contenedor.repaint();
        contenedor.getComponent(2).requestFocusInWindow();
    }

    public AudioPlayer getAudioPlayer() {
        return this.audioPlayer;
    }
}