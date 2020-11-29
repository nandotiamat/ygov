package src.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.Font;

public class MainMenu {
    private Font font;
    private boolean cardVisible = false;
    private float alpha = 0;
    private float alpha2 = 0;
    private BufferedImage logo;
    private BufferedImage sfondo;
    private List<Image> cards = new ArrayList<Image>();
    private int[][] positions = new int[25][2];
    private int[] vel = new int[25];

    public MainMenu() {
        Random r = new Random();
        font = new Font("Arial", 1, 30);
        try {
            logo = ImageIO.read(new File("src/img/logo.png"));
            sfondo = ImageIO.read(new File("src/img/uni.jpg"));
            for(int i=0; i<25; i++) {
                int tempInt = r.nextInt(200) + 100;
                cards.add(ImageIO.read(new File("src/img/" + (i+1) + ".png")).getScaledInstance(tempInt, (int) ( (float) tempInt * 1.4558f ) , Image.SCALE_DEFAULT));
                positions[i][0] = r.nextInt(Game.WIDTH);
                positions[i][1] = r.nextInt(Game.HEIGHT);
                vel[i] = tempInt/50 + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        g.drawImage(sfondo, 0, 0, null);
        Graphics2D g2d = (Graphics2D) g;
        if (alpha < 1.0f) {
            //change alpha value
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        }
            g.drawImage(logo, 0, 0, null);
        if (alpha < 1.0f) {
            //change back to normal
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }
        else if (cardVisible) {
            if (alpha2 < 1.0f) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha2));
            }
                for (int i=0; i<25; i++) {
                    g.drawImage(cards.get(i), positions[i][0], positions[i][1], null);
                }
                g.setColor(Color.white);
                g.fillRect(Game.WIDTH/2 - 50, Game.HEIGHT/2 - 30, 100, 60);
                g.setColor(Color.black);
                g.setFont(font);
                g.drawString("PLAY", Game.WIDTH/2 - 40, Game.HEIGHT/2 + 10);
            if (alpha2 < 1.0f) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            }
            g.drawImage(logo, 0, 0, null);
        }
    }
    
    public void tick() {
        if (alpha < 1f) {
            alpha += 0.005f;
        } else {
            cardVisible = true;
        }
        if (cardVisible) {
            if (alpha2 < 1f) {
                alpha2 += 0.01f;
            }
            for (int i=0; i<25; i++) {
                positions[i][1] += vel[i];
                if (positions[i][1] < -300 || positions[i][1] > 1000) {
                    vel[i] *= -1;
                }
            }
        }
    }
}
