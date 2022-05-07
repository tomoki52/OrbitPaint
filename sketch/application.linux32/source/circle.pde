//Circlesクラス
class Circles {
  float m; //質量
  float x,y,vx,vy,ax,ay,rad; //座標、速度、加速度、半径
  float[] px=new float[3]; //軌跡を描くために過去の3点を保存
  float[] py=new float[3];
  int[] colors=new int[3]; //色

  Circles(){ //この関数で諸々の変数を初期化
   m=random(0.1,1); //質量をランダムに設定
   x=px[0]=px[1]=px[2]=random(width); //初期座標をランダムに設定
   y=py[0]=py[1]=py[2]=random(height);
   vx=random(-0.5,0.5);//速さをランダムに設定
   vy=random(-0.5,0.5);
   ax=0; //加速度を0に設定
   ay=0;
   rad=m;//半径を設定
   colors[2]=255; //色の設定
   colors[0]=int(m*10);
   colors[1]=int(random(100,255));
  }
  
  void update(){//速度、加速度の計算
    ax=-1*vx*0.5;
    ay=-1*vy*0.5;
    for(float px:g_x){
      ax+=(px-x)*0.5;
    }
    for(float py:g_y){
      ay+=(py-y)*0.5;
    }
    vx+=ax/m*0.01;
    vy+=ay/m*0.01;
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
  
  void drawc () { //円の描画
    noStroke();
    fill(colors[0],colors[1],colors[2],255); 
    ellipseMode (CENTER);
    /*for(int i=0;i<g_x.length-1;i++){
      ellipse(g_x[i],g_y[i],10,10);
    }*/
    //ellipse (x, y, 4 * rad, 4 * rad);
    }
  void drawline(){ //軌跡の描画
    stroke(colors[0],colors[1],colors[2],255);
    strokeWeight(6*rad);
    curve(px[2],py[2],px[1],py[1],px[0],py[0],x,y);
  }
}
