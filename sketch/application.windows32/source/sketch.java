import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sketch extends PApplet {

int num=100; //円の数
ArrayList<Circles> c_set = new ArrayList<Circles> ();
float[] point_x=new float[num]; //軌跡描く用に過去のx座標の保存
float[] point_y=new float[num]; //軌跡描く用に過去のy座標の保存
float[] g_x=new float[1]; 
float[] g_y=new float[1];
int fill_tmp=255;
int loop=1; //ループを止めるための変数
int time=0;
int savecount=1; //画像の保存の名前を連番にするための変数
int fill_freq=1; //フェードの透明度の具合
public void setup () {
     
    colorMode (RGB, 256);
    background (255);
    frameRate (180);
    for (int i = 0; i < num; i++) { //circleクラスを作成
    c_set.add (new Circles ());
    }
}

public void draw () {
  g_x[0]=mouseX;
  g_y[0]=mouseY;
  //fade();
  if(time%fill_freq==0){
    fillcircle();
  }
  int a=0;
  for (Circles c: c_set) {
    c.update ();
    c.drawc ();
    c.drawline();
    point_x[a]=c.x;
    point_y[a]=c.y;
    a++;
  }
  time++;
}

/*void mousePressed(){
  g_x=append(g_x,mouseX);
  g_y=append(g_y,mouseY);
 
}
*/
public void keyPressed(){ 
  if(key=='a'){ //質量をマイナスにして斥力を発生
    for (Circles c: c_set) {
        c.m*=-1;
        c.ax=0;
        c.ax=0;
        c.vx=0;
        c.vy=0;
      }
  }
  if(key=='s'){ //fillcircle関数の透明度を切り替えて軌跡を描画
    if(fill_tmp==255){
      fill_tmp=10;
      fill_freq=5;
    }else{
      fill_tmp=255;
      fill_freq=1;
    }
  }
  if(key=='d'){ //dを押す度にループが止まる/動く
  loop*=-1;
      if(loop==-1){
      noLoop();
      }else{
      loop();
  }
  }
  
  if(key=='f'){ //画像の保存
      save("sample" + savecount + ".png");
    savecount++;
  }
  if(key=='q'){
    for (Circles c: c_set) {
   c.x=c.px[0]=c.px[1]=c.px[2]=random(width); //初期座標をランダムに設定
   c.y=c.py[0]=c.py[1]=c.py[2]=random(height);
   c.vx=random(-0.5f,0.5f);//速さをランダムに設定
   c.vy=random(-0.5f,0.5f);
   c.ax=0; //加速度を0に設定
   c.ay=0;
    }
  }
}

public void fade () { //フェード
    noStroke ();
    fill (255, 0);
    rectMode (CORNER);
    rect (0, 0, width, height);
}

public void fillcircle () { //軌跡を描くための関数

    noStroke ();
    fill (255, fill_tmp);
    rectMode (CORNER);
    rect (0, 0, width, height);
}

public void draw_line(){ //各点を線でつなぐ

   for(int i=0;i<num-1;i++){
      stroke(255);
      fill (255);
      line(point_x[i],point_y[i],point_x[i+1],point_y[i+1]);
    }
    line(point_x[num-1],point_y[num-1],point_x[0],point_y[0]);
}
//Circlesクラス
class Circles {
  float m; //質量
  float x,y,vx,vy,ax,ay,rad; //座標、速度、加速度、半径
  float[] px=new float[3]; //軌跡を描くために過去の3点を保存
  float[] py=new float[3];
  int[] colors=new int[3]; //色

  Circles(){ //この関数で諸々の変数を初期化
   m=random(0.1f,1); //質量をランダムに設定
   x=px[0]=px[1]=px[2]=random(width); //初期座標をランダムに設定
   y=py[0]=py[1]=py[2]=random(height);
   vx=random(-0.5f,0.5f);//速さをランダムに設定
   vy=random(-0.5f,0.5f);
   ax=0; //加速度を0に設定
   ay=0;
   rad=m;//半径を設定
   colors[2]=255; //色の設定
   colors[0]=PApplet.parseInt(m*10);
   colors[1]=PApplet.parseInt(random(100,255));
  }
  
  public void update(){//速度、加速度の計算
    ax=-1*vx*0.5f;
    ay=-1*vy*0.5f;
    for(float px:g_x){
      ax+=(px-x)*0.5f;
    }
    for(float py:g_y){
      ay+=(py-y)*0.5f;
    }
    vx+=ax/m*0.01f;
    vy+=ay/m*0.01f;
    //constrain(vx,-1,1);
    //constrain(vy,-1,1);
    px[2]=px[1]; //過去の座標を配列pxに保存
    py[2]=py[1];
    px[1]=px[0];
    py[1]=py[0];
    px[0]=x;
    py[0]=y;
    x+=vx;
    y+=vy;
    constrain(x,0,width);
    constrain(y,0,height);
  }
  
  public void drawc () { //円の描画
    noStroke();
    fill(colors[0],colors[1],colors[2],255); 
    ellipseMode (CENTER);
    /*for(int i=0;i<g_x.length-1;i++){
      ellipse(g_x[i],g_y[i],10,10);
    }*/
    //ellipse (x, y, 4 * rad, 4 * rad);
    }
  public void drawline(){ //軌跡の描画
    stroke(colors[0],colors[1],colors[2],255);
    strokeWeight(6*rad);
    curve(px[2],py[2],px[1],py[1],px[0],py[0],x,y);
  }
}
  public void settings() {  size (1920, 1080); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "sketch" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
