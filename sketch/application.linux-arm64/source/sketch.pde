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
void setup () {
    size (1920, 1080); 
    colorMode (RGB, 256);
    background (255);
    frameRate (180);
    for (int i = 0; i < num; i++) { //circleクラスを作成
    c_set.add (new Circles ());
    }
}

void draw () {
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
void keyPressed(){ 
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
   c.vx=random(-0.5,0.5);//速さをランダムに設定
   c.vy=random(-0.5,0.5);
   c.ax=0; //加速度を0に設定
   c.ay=0;
    }
  }
}

void fade () { //フェード
    noStroke ();
    fill (255, 0);
    rectMode (CORNER);
    rect (0, 0, width, height);
}

void fillcircle () { //軌跡を描くための関数

    noStroke ();
    fill (255, fill_tmp);
    rectMode (CORNER);
    rect (0, 0, width, height);
}

void draw_line(){ //各点を線でつなぐ

   for(int i=0;i<num-1;i++){
      stroke(255);
      fill (255);
      line(point_x[i],point_y[i],point_x[i+1],point_y[i+1]);
    }
    line(point_x[num-1],point_y[num-1],point_x[0],point_y[0]);
}
