package elementos.pantallas;

import juego.VtaJuego;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class MenuOpciones extends JPanel {

    private BufferedImage imgFondoCueva, imgPanel, imgBarraVacia, imgScrollbar, imgCheck, imgCruz;
    private Rectangle rectSlider, rectToggle;
    private VtaJuego ventana;
    
    private float volumen = 0.5f;
    private boolean sonidoActivo = true;

    public MenuOpciones(VtaJuego ventana) {
        this.ventana = ventana;
        this.setDoubleBuffered(true);
        cargarAssets();
        
        // Timer para refrescar la pantalla a 60 FPS
        new Timer(16, e -> repaint()).start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Si presionas el Checkbox, cambia el sonido Y regresa al menú
                if (rectToggle != null && rectToggle.contains(e.getPoint())) {
                    sonidoActivo = !sonidoActivo;
                    // Pequeña pausa opcional si quieres que se vea el cambio antes de salir
                    ventana.mostrarMenu(); 
                }
                actualizarSlider(e.getPoint());
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                actualizarSlider(e.getPoint());
            }
        });
    }

    private void cargarAssets() {
        try {
            // Asegúrate de que estos nombres coincidan exactamente con tus archivos en /res/
            imgFondoCueva = ImageIO.read(getClass().getResourceAsStream("/res/FondoOpciones.png"));
            imgPanel = ImageIO.read(getClass().getResourceAsStream("/res/wood_panel.png"));
            imgBarraVacia = ImageIO.read(getClass().getResourceAsStream("/res/wood_slider_empty.png"));
            imgScrollbar = ImageIO.read(getClass().getResourceAsStream("/res/wood_scrollbar.png"));
            imgCheck = ImageIO.read(getClass().getResourceAsStream("/res/wood_checkbox_checked.png"));
            imgCruz = ImageIO.read(getClass().getResourceAsStream("/res/wood_checkbox_unchecked.png"));
        } catch (Exception e) {
            System.err.println("Error cargando assets: " + e.getMessage());
        }
    }

    private void actualizarSlider(Point p) {
    if (rectSlider != null && rectSlider.contains(p)) {
        volumen = (float) (p.x - rectSlider.x) / rectSlider.width;
        volumen = Math.max(0, Math.min(1, volumen));

        // ESTO TE DIRÁ EL CULPABLE EN LA CONSOLA:
        if (ventana == null) {
            System.out.println("ERROR: La ventana es nula");
        } else if (ventana.getAudioPlayer() == null) {
            System.out.println("ERROR: El AudioPlayer en la ventana es nulo");
        } else {
            ventana.getAudioPlayer().setVolumen(volumen);
            System.out.println("Volumen ajustado a: " + volumen);
        }
    }
}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Renderizado Pixel Art nítido
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        // 1. Dibujar Fondo (image_2a5b05.jpg)
        if (imgFondoCueva != null) g2d.drawImage(imgFondoCueva, 0, 0, getWidth(), getHeight(), null);

        // 2. Dibujar Panel de Madera
        int pW = 500, pH = 400;
        int pX = (getWidth() - pW) / 2;
        int pY = (getHeight() - pH) / 2;
        if (imgPanel != null) g2d.drawImage(imgPanel, pX, pY, pW, pH, null);

        // 3. Título
        g2d.setFont(new Font("Monospaced", Font.BOLD, 40));
        g2d.setColor(new Color(255, 230, 180));
        g2d.drawString("OPCIONES", pX + 145, pY + 80);

        // 4. Slider de Volumen
        int sW = 350, sH = 40;
        int sX = (getWidth() - sW) / 2;
        int sY = pY + 150;
        rectSlider = new Rectangle(sX, sY, sW, sH);
        if (imgBarraVacia != null) g2d.drawImage(imgBarraVacia, sX, sY, sW, sH, null);

        // Cursor del Slider
        int selW = 30, selH = 50;
        int selX = sX + (int)(volumen * (sW - selW));
        if (imgScrollbar != null) g2d.drawImage(imgScrollbar, selX, sY - 5, selW, selH, null);

        // 5. Checkbox (Ahora reemplaza la funcionalidad del botón aplicar)
        int cSize = 80;
        int cX = (getWidth() - cSize) / 2;
        int cY = pY + 250;
        rectToggle = new Rectangle(cX, cY, cSize, cSize);
        
        BufferedImage imagenEstado = sonidoActivo ? imgCheck : imgCruz;
        if (imagenEstado != null) {
            g2d.drawImage(imagenEstado, cX, cY, cSize, cSize, null);
        }
    }
}