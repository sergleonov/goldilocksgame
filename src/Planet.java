//a class to represent planets

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Planet{

    public static void main(String[] args){

        Planet testPlanet = new Planet(7);
        testPlanet.setPos(new Pair(500,500));

        double[][] noiseMap = new double[2*testPlanet.radius + 1][2*testPlanet.radius + 1];

        for(int i =0; i < noiseMap.length ; i++){
            for(int j=0; j< noiseMap.length; j++){
                noiseMap[i][j] = testPlanet.noise(i, j);
                
            }
            
        }

        for(int i = 0; i < noiseMap.length; i++){
            for(int j = 0; j < noiseMap.length; j++){
                testPlanet.circularNoise();
                System.out.print(String.format("%.1f", noiseMap[i][j]) + " ");
            }
            System.out.println();
        }


    }

    public int radius;
    protected Pair pos;
    protected Pair vel;
    protected Pair accel;
    protected double[][] noiseMap;
    protected int planetType;
    private static int countPlanets = 0;

    public Planet(int initRadius){
        pos = new Pair();
        vel=new Pair();
        accel=new Pair();
        radius = initRadius;
        planetType=(int)(Math.random()*6);

        noiseMap = new double[2*this.radius + 1][2*this.radius + 1];

        for(int i =0; i < noiseMap.length ; i++){
            for(int j=0; j< noiseMap.length; j++){
                noiseMap[i][j] = this.noise(i, j);
            }
            
        }

        this.circularNoise();
        countPlanets++;
        try {
            makePNGfile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getNumPlanets(){
        return countPlanets;
    }

    public double[][] getNoiseMap(){
        return noiseMap;
    }

    public int getRadius(){
        return radius;
    }

    public double getX(){
        return pos.x;
    }

    public double getY(){
       
        return pos.y;
    }
    public Pair getCenter(){
        double x=pos.x;
        double y=pos.y;
      if(pos.x>0) x+=noiseMap.length/2+40;
      else x-=noiseMap.length/2-40;

      if(pos.y>0) y+=noiseMap.length/2+40;
      else y-=noiseMap.length/2-40;

      return new Pair(x,y);
    }

    public void setPos(Pair a){
        pos=a;
    }
    public void setVel(Pair a){
        vel=a;
    }
    public void setAccel(Pair a){
        accel=a;
    }

    public double noise(int i, int j){

        SimplexNoise noise = new SimplexNoise();

        return noise.noise(i, j);
    }

    public boolean insideCircle(Pair center, Pair point){

        // This code has been inspired by https://www.redblobgames.com/grids/circle-drawing/

        double dx = center.x - point.x;
        double dy = center.y - point.y;

        double distance = Math.sqrt(dx*dx + dy*dy);
        return distance <= radius;
        
    }

    public void circularNoise(){

        Pair center = new Pair(noiseMap.length / 2, noiseMap.length /2);
        
        for(int y = 0; y < noiseMap.length; y++ ){
            for(int x = 0; x < noiseMap.length; x++){
                if(!insideCircle(center, new Pair(y, x))){
                    this.noiseMap[y][x] = 0;
                }
                if(insideCircle(center, new Pair(y,x)) && this.noiseMap[y][x]==0){
                    noiseMap[y][x]=Math.random()*.9+.001;
                }
            }
        }

    }

    private void makePNGfile() throws IOException{
        BufferedImage img = new BufferedImage(noiseMap.length*8, noiseMap.length*8, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        for(int i=0; i<noiseMap.length;i++){
            for(int j =0; j<noiseMap.length;j++){
                if(noiseMap[i][j]!=0){


                    g2d.setColor(planetColorScheme(noiseMap[i][j]));
                    g2d.fillRect((int)pos.x+i*8, (int)pos.y+j*8, 8, 8);

                    
                }
            }
        }
        File file = new File("./assets/planets/planet" +countPlanets +".png");
        ImageIO.write(img, "png", file);


    }

    public void draw(Graphics g){
        for(int i=0; i<noiseMap.length;i++){
            for(int j =0; j<noiseMap.length;j++){
                if(noiseMap[i][j]!=0){

         

                    g.setColor(planetColorScheme(noiseMap[i][j]));
                    g.fillRect((int)pos.x+i*8, (int)pos.y+j*8, 8, 8);

                }
            }
        }
    }

    public Color planetColorScheme(double alpha){
    
        if(this.planetType==0){
            if(alpha<.4 &&alpha>0) return new Color( 	242, 56, 230);
            if(alpha>.4 && alpha<.8) return new Color( 	121, 18, 166);
            if(alpha<0 && alpha>-.4) return new Color( 	52, 28, 140);
            if(alpha<-.4) return new Color( 	24, 53, 140);
            if(alpha>.8) return new Color( 	102, 242, 242);
        }

        if(planetType==1){
            if(alpha<.4 &&alpha>0) return new Color(242, 169, 80);
            if(alpha>.4 && alpha<.8) return new Color(242, 116, 5);
            if(alpha<0 && alpha>-.4) return new Color(242, 92, 5);
            if(alpha<-.4) return new Color(191, 38, 4);
            if(alpha>.8) return new Color(89, 16, 10);
        }

        if(planetType==2){
            if(alpha<.4 &&alpha>0) return new Color(3,65,89);
            if(alpha>.4 && alpha<.8) return new Color(2,89,81);
            if(alpha<0 && alpha>-.4) return new Color(2,115,94);
            if(alpha<-.4) return new Color(3,140,62);
            if(alpha>.8) return new Color(12,242,93);
        }

        if(planetType==3){
            if(alpha<.4 &&alpha>0) return new Color(89, 52, 59);
            if(alpha>.4 && alpha<.8) return new Color(215, 215, 217);
            if(alpha<0 && alpha>-.4) return new Color(159, 159, 166);
            if(alpha<-.4) return new Color(102, 106, 115);
            if(alpha>.8) return new Color(242, 242, 240);
        }

        if(planetType==4){
            //green
            if(alpha<.3 &&alpha>0) return new Color(0,0,220);
            if(alpha>.3 && alpha<.5) return new Color(0,200,0);
            if(alpha>.5 && alpha<.8) return new Color(0,180,0);
            //white
            if(alpha>.8) return new Color(216,235,242);
            //blue
            if(alpha<-.3&&alpha>-.5) return new Color( 0, 0, 95);
            if(alpha<0 && alpha>-.3) return new Color(0,0,162);
            if(alpha<-.5&& alpha>-.8) return new Color( 0, 0, 128);
            if(alpha<-.8) return new Color(0, 0, 200);
        }
        if(planetType==5){
            if(alpha<.4 &&alpha>0) return new Color(166, 155, 134);
            if(alpha>.4 && alpha<.8) return new Color(102, 96, 83);
            if(alpha<0 && alpha>-.4) return new Color( 	230, 215, 186);
            if(alpha<-.4) return new Color( 	242, 227, 196);
            if(alpha>.8) return new Color( 	204, 191, 165);
        }

            return null;
        
   

        
    }


}

class Asteroid extends Planet{

    private boolean destroyed;
    //variables to store number of resources dropped after destroying the asteroid
    private int stone;
    private int iron;

    public Asteroid(){
        super(15);
        destroyed= false;
        //i set it to 0 through 8 for now plus smt proportional to radius
        stone = (int)(Math.random()*8+this.radius/3);
        //making iron more rare than stone here
        iron=(int)(Math.random()*4);

    }

    public void draw(Graphics g){
        g.fillOval(iron, iron, stone, iron);
    }

}

class Goldilocks extends Planet{
    private int gold;
    private int water;
    private int fossilfuels;

    public Goldilocks(int r){
        super(r);

    }

}

class StarterPlanet extends Planet {

    private Image starterPlanet;
    private Image cloud1;
    private Image cloud2;
    private Image castle;
    private  int cloudX1 = -350;
    private  int cloudX2 = -100;
    private int velocityX = 1;
    private int velocityX2 = -1;

    public StarterPlanet(){
        super(10);
        starterPlanet = new ImageIcon("assets\\StarterPlanet\\planet.png").getImage();
        starterPlanet = starterPlanet.getScaledInstance(500, 500, Image.SCALE_DEFAULT);
        
        cloud1 = new ImageIcon("assets\\StarterPlanet\\cloud1.png").getImage();
        cloud1 = cloud1.getScaledInstance(300, 200, Image.SCALE_DEFAULT);

        cloud2 = new ImageIcon("assets\\StarterPlanet\\cloud2.png").getImage();
        cloud2 = cloud2.getScaledInstance(600, 500, Image.SCALE_DEFAULT);

        castle = new ImageIcon("assets\\StarterPlanet\\castle.png").getImage();
        castle = castle.getScaledInstance(200, 200, Image.SCALE_DEFAULT);

    }

    public void drawStarterPlanet(Graphics g){
        g.drawImage(castle, -105, -400, null);
        g.drawImage(starterPlanet,-250 ,-250, null);
        animateCloud(g); 
    }

    public void animateCloud(Graphics g){

        // Moving cloud 1 to the right until it collides with a certain point

        if(velocityX > 0){
            if (cloudX1 < -100) {
                cloudX1 += velocityX;
                g.drawImage(cloud2, cloudX1, -150, null);
            } else {
                velocityX = -velocityX;
                g.drawImage(cloud2, cloudX1 - 1, -150, null);
            }
        }else if(velocityX < 0){
            if (cloudX1 > -500) {
                cloudX1 += velocityX;
                g.drawImage(cloud2, cloudX1, -150, null);
            } else {
                velocityX = -velocityX;
                g.drawImage(cloud2, cloudX1 + 1, -150, null);
            }
        }
        
    }

    public void animateCloud2(Graphics g){
        if(velocityX2 > 0){
            if (cloudX2 < -50) {
                cloudX2 += velocityX2;
                g.drawImage(cloud1, cloudX2, 0, null);
            } else {
                velocityX2 = -velocityX2;
            }
        }else if(velocityX2 < 0){
            if (cloudX2 > -400) {
                cloudX2+= velocityX2;
                g.drawImage(cloud1, cloudX2, 0, null);
            } else {
                velocityX2 = -velocityX2;
            }
        }
    }



    
    
}


