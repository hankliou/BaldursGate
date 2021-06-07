package com.example.baldursgate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.io.*;
import java.net.Socket;

public class MainActivity extends Activity implements JoystickView.JoystickListener {

    // Android 物件 (封面)
    Context context;
    ImageButton config;                 // 連線按鈕
    ImageView cover;                    // 開始畫面
    RelativeLayout BaseRelativeLayout;  // 最底下的 RelativeLayout

    // Android 物件 (遊戲)
    RelativeLayout GameRelativeLayout;  // 裝遊戲畫面的
    ImageView bg;                       // 底圖
    RelativeLayout PlateLayout;         // 裝板塊的
    ImageView[][] Plate;                // 板塊
    ImageView player;                    // 玩家(之後換成imageView)

    // 遊戲角色屬性變數
    int myClientID;             // client號
    int characterID;            // 角色號
    int x, y;                   // 角色位移
    int visionX=0, visionY=0;   // 視野範圍
    int abVisionX, abVisionY;   // 視野座標
    int abX=1875, abY=1125;     // 角色絕對座標 (單位:DP) 600dp/plate (0,0)=>(-5740, -3500)
    int plateX, plateY;         // 角色所在的板塊

    // 遊戲變數
    int numberOfPlayer = 1;     // 玩家數量

    // 連線物件
    Socket clientSocket;    //客戶端的socket
    PrintWriter out;        //取得網路輸出串流
    BufferedReader in;      //取得網路輸入串流
    String msgFromServer;   //要接收的訊息
    String msgToServer;     //要發出的訊息
    static String SERVER_IP;
    static int SERVER_PORT;

    int TransDP(int n){
        return n*(int)this.getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        // 開始畫面
        BaseRelativeLayout = findViewById(R.id.BaseRelativeLayout);
        config = findViewById(R.id.config); // 設定按鈕
        cover = findViewById(R.id.cover);   // 封面
        GameRelativeLayout = findViewById(R.id.GameRelativeLayout);
        // 開始先讓GameRelativeLayout 消失
        GameRelativeLayout.setVisibility(View.GONE);


        // 遊戲畫面
        PlateLayout = findViewById(R.id.plateLayout);
        bg = findViewById(R.id.bg);
        Plate = new ImageView[25][15];
        player = findViewById(R.id.player1);

        // 設定DP->SP單位換算大小
        int dp = TransDP(150);
        ViewGroup.LayoutParams params;
        params = new ViewGroup.LayoutParams(dp,dp);

        // 初始化地圖板塊
        for(int i=0;i<25;i++){
            for(int j=0;j<15;j++){
                Plate[i][j] = new ImageView(this);
                Plate[i][j].setTranslationX(dp*i +500);
                Plate[i][j].setTranslationY(dp*j +500); // 預設圖片
                Plate[i][j].setLayoutParams(params);
                Plate[i][j].setImageResource(R.drawable.assassin);
                PlateLayout.addView(Plate[i][j]);
            }
        }

        // 玩家 初始位置 & 視角 在地圖中央
        player.setTranslationX(dp *1*12.5f + 500);
        player.setTranslationY(dp *1*7.5f + 500);
        abVisionX = (int)(dp*1*12.5f + 500);
        abVisionY = (int)(dp*1*7.5f + 500);
        // 移動視角
        player.setTranslationX((int)player.getX()-visionX - dp *1.2f*12.5f + 500);
        player.setTranslationY((int)player.getY()-visionY - dp *1.2f*9f + 500);
        bg.setTranslationX((int)bg.getX()-visionX - dp *1.2f*12.5f + 500);
        bg.setTranslationY((int)bg.getY()-visionY - dp *1.2f*9f + 500);
        PlateLayout.setTranslationX((int) PlateLayout.getX()-visionX - dp *1.2f*12.5f + 500);
        PlateLayout.setTranslationY((int) PlateLayout.getY()-visionY - dp *1.2f*9f + 500);
        abVisionX = (int)(abVisionX-visionX-(int)dp*1.2f*12.5f + 1350);
        abVisionY = (int) (abVisionY-visionY- (int)dp*1.2f*9f + 900);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id){

        switch (id){
            case R.id.joystick:
                // 取得搖桿的動作 因為回傳值是0.01~1 所以要乘10
                x = (int)(xPercent*10);
                y = (int)(yPercent*10);

                // 移動玩家
                player.setTranslationX((int)player.getX() + x);
                player.setTranslationY((int)player.getY() + y);

                // 做成封包
                packetMaker();

                // 顯示座標
                abX += TransDP(x);
                abY += TransDP(y);
                TextView coor = findViewById(R.id.coor);
                plateX = (abX + 5740) / 600;
                plateY = (abY + 3500) / 600;
                coor.setText("X: "+abX + " Y: "+abY+" PX: "+plateX + " PY: "+plateY);
                break;

            case R.id.joystick2:
                // 取得搖桿的動作 因為回傳值是0.01~1 所以要乘10
                visionX = (int) (xPercent*15);
                visionY = (int) (yPercent*15);

                // 移動視角
                player.setTranslationX((int)player.getX()-visionX);
                player.setTranslationY((int)player.getY()-visionY);
                bg.setTranslationX((int)bg.getX()-visionX);
                bg.setTranslationY((int)bg.getY()-visionY);
                PlateLayout.setTranslationX((int) PlateLayout.getX()-visionX);
                PlateLayout.setTranslationY((int) PlateLayout.getY()-visionY);
                break;
        }

    }

    // 封包切割機
    void packetMaker(){
        if(x!=0 && y!=0) {
            // 清空字串
            msgToServer = "";

            msgToServer += String.format("%04d", (int)player.getX());
            msgToServer += String.format ("%04d", (int)player.getY());

            // 送出自己座標的封包
            Sender sender = new Sender();
            new Thread(sender).start();
        }
    }

    // 設定按鈕的點擊事件
    public void Config(View view) {

        // 宣告inflater並找到XML
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.ip_and_port, null);

        // 從XML中找到按鈕
        final EditText ip = v.findViewById(R.id.IP);
        final EditText port = v.findViewById(R.id.PORT);

        // 宣告一個彈出視窗
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(v);

        // 設定一個「確定」按鈕與點擊事件
        alert.setPositiveButton("OK", (dialog, which) -> {

            // 取值給 ip 跟 port
            SERVER_IP = ip.getText().toString();
            SERVER_PORT = Integer.parseInt(port.getText().toString());

            // 開始執行緒並連線
            StartASocket startASocket = new StartASocket();
            new Thread(startASocket).start();

            // 清理開始畫面的小物件 & 轉到遊戲地圖
            config.setVisibility(View.GONE);    // 連線鈕消失
            cover.setVisibility(View.GONE);     // 封面消失
            // 讓遊戲框架顯示
            GameRelativeLayout.setVisibility(View.VISIBLE);

            // 跳轉到顯示玩家人數介面
            LayoutInflater inflater1 = LayoutInflater.from(context);
            View view1 = inflater1.inflate(R.layout.room_population, null);
            Button start = view1.findViewById(R.id.start);
            AlertDialog.Builder alert1 = new AlertDialog.Builder(context);
            alert1.setView(view1);
            AlertDialog dialog1 = alert1.create();
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.setCancelable(false);
            start.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    dialog1.dismiss();
                }
            });
            dialog1.show();
        });

        // 顯示
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void GoBack(View view) {
        visionX = (int)(player.getX()-abVisionX);
        visionY = (int)(player.getY()-abVisionY);
        player.setTranslationX((int)player.getX()-visionX);
        player.setTranslationY((int)player.getY()-visionY);
        bg.setTranslationX(bg.getX()-visionX);
        bg.setTranslationY(bg.getY()-visionY);
        PlateLayout.setTranslationX(PlateLayout.getX()-visionX);
        PlateLayout.setTranslationY(PlateLayout.getY()-visionY);
    }

    public void backpack(View view) {
        // 宣告inflater並找到XML
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.back_pack, null);
        // 從XML中找到物件
        final ImageButton use = v.findViewById(R.id.use);
        final ImageButton ubun = v.findViewById(R.id.ubun);
        // 宣告一個彈出視窗
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(v);
        // 顯示
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    // 回合結束按鈕
    public void roundOver(View view) {
    }

    // 擲骰子按鈕
    public void diceRoll(View view) {
    }

    // 送出訊息的執行緒
    class Sender implements Runnable {
        @Override
        public void run(){
            // 嘗試送出訊息
            try {
                out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
                out.println(msgToServer);
            } catch (IOException e) {} // 就算有例外我也沒辦法處裡
        }
    }

    // 接收訊息的執行緒
    class StartASocket implements Runnable{
        @Override
        public void run(){
            // 嘗試開一個接收器
            try{
                clientSocket = new Socket(SERVER_IP, SERVER_PORT);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e){} // 就算拋出例外我也沒辦法處裡
            // 嘗試從接收器擷取訊息
            try{
                while(true){
                    msgFromServer = in.readLine();

                    // 從封包切出座標
//                    x2 = Integer.valueOf(msgFromServer.substring(0,4));
//                    y2 = Integer.valueOf(msgFromServer.substring(4,8));

                    // 更新畫面
                    runOnUiThread(() -> {
//                            player2.setTranslationX(x2);
//                            player2.setTranslationY(y2);
                    });
                }
            }catch (IOException e){} // 就算拋出例外我也沒辦法處裡
        }
    }
}