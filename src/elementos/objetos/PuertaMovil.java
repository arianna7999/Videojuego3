package elementos.objetos;

import juego.Juego;

/**
 * Clase que gestiona las puertas y rejas del juego.
 * En los Mundos 1 y 2, la puerta se desplaza hacia arriba.
 * En el Mundo 3 (Nivel 2), la reja es estática y solo cambia su animación.
 */
public class PuertaMovil extends ObjetoJuego {

    private float yObjetivo;
    private float velocidad = .5f * Juego.SCALE;
    private boolean abriendo = false;
    private boolean abierta = false;
    private int animTick, animInd, animSpeed = 15;
    private int nivelActual; // Variable para distinguir el comportamiento por mundo

    public PuertaMovil(int x, int y, int tipo, int pixelesASubir, int nivelActual) {
        super(x, y, tipo);
        this.nivelActual = nivelActual;
        
        // Calculamos el punto final en el eje Y (solo se usa si la puerta se alza)
        this.yObjetivo = y - (pixelesASubir * Juego.SCALE);
        
        // Hitbox estándar: 1 tile de ancho (32) y 3 tiles de alto (96)
        initHitbox(32, 96);
    }

    public void update() {
        if (abriendo) {
            
            // LÓGICA DE MOVIMIENTO FÍSICO
            // Solo restamos Y si NO estamos en el Mundo 3 (nivel index 2)
            if (this.nivelActual != 2) {
                hitbox.y -= velocidad;
            }

            // LÓGICA DE ANIMACIÓN (Afecta a los frames de REJAS.png)
            animTick++;
            if (animTick >= animSpeed) {
                animTick = 0;
                animInd++;
                
                // El límite de animación según tus assets es el frame 4 (total 5)
                if (animInd >= 4) {
                    animInd = 4;
                    // En el mundo 3, aquí termina el proceso
                    if (this.nivelActual == 2) {
                        abriendo = false;
                        abierta = true;
                        // Al terminar la animación, eliminamos la colisión
                        hitbox.height = 0;
                    }
                }
            }

            // LÓGICA DE PARADA PARA PUERTAS QUE SE ALZAN (Mundos 1 y 2)
            if (this.nivelActual != 2) {
                if (hitbox.y <= yObjetivo) {
                    hitbox.y = yObjetivo; // Asegurar posición exacta
                    abriendo = false;
                    abierta = true;
                    // Eliminamos la colisión física para permitir el paso
                    hitbox.height = 0;
                }
            }
        }
    }

    // Getters y Setters de estado
    public int getAnimInd() {
        return animInd;
    }

    public void abrir() {
        if (!abierta)
            abriendo = true;
    }

    public boolean estaAbierta() {
        return abierta;
    }

    public boolean estaAbriendo() {
        return abriendo;
    }
}