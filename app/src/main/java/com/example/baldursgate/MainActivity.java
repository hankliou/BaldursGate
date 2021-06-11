package com.example.baldursgate;

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
    ImageView[] role;                   // 玩家角色 (取代player)
    TextView population;                // 顯示玩家數
    ImageView[] playerAttributeFrame;   // 角色屬性背板
    TextView[][] playerAttribute;       // 角色屬性值

    TextView abVision;
    TextView coor,coor1;

    // 遊戲角色屬性變數
    int myClientID = 0;         // client號
    int x, y;                   // 角色位移
    int visionX=0, visionY=0;   // 視野範圍
    int plateX, plateY;         // 角色所在的板塊
    Character character;

    // 玩家們的 XY 座標   *****角色絕對座標 (單位:DP) 600dp/plate (0,0)=>(-7220, -4500)*****
    int[] playerX = { 100 ,100 ,100 ,100 ,100 ,100 };
    int[] playerY = {-100,-100,-100,-100,-100,-100 };

    // 遊戲變數
    int gameStart = 0;                  // 遊戲開始了嗎？
    int numberOfPlayer = 1;             // 玩家數量
    int[] characterIndex = new int[6];  // 角色代號
    boolean myRound = false;            // 是我的回合嗎？
    int roundOver = 2;                  // 回合結束了嗎 (0: 我的回合還沒結束, 1:我的回合結束了, 2:不是我的回合，郭?)

    // 連線物件
    Socket clientSocket;        //客戶端的socket
    PrintWriter out;            //取得網路輸出串流
    BufferedReader in;          //取得網路輸入串流
    String msgFromServer = "";  //要接收的訊息
    String msgToServer;         //要發出的訊息
    static String SERVER_IP;
    static int SERVER_PORT;

    // DP 單位換算
    int TransDP(int n) { return n*(int)this.getResources().getDisplayMetrics().density;}
    int TransPX(int n) { return n/(int)this.getResources().getDisplayMetrics().density;}

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

        abVision = findViewById(R.id.vision);
        coor = findViewById(R.id.coor);
        coor1 = findViewById(R.id.coor1);

        // 設定DP->SP單位換算大小
        int dp = TransDP(150);
        ViewGroup.LayoutParams params;
        params = new ViewGroup.LayoutParams(dp,dp);

        // 初始化地圖板塊
        for(int i=0;i<25;i++){
            for(int j=0;j<15;j++){
                Plate[i][j] = new ImageView(this);
                Plate[i][j].setTranslationX(dp *i +500);
                Plate[i][j].setTranslationY(dp *j +500); // 預設圖片
                Plate[i][j].setLayoutParams(params);
                Plate[i][j].setImageResource(R.drawable.assassin);
                PlateLayout.addView(Plate[i][j]);
            }
        }

        // 初始化玩家角色
        role = new ImageView[6];
        for(int i = 0;i<6;i++)
            role[i] = new ImageView(this);

        // 初始化屬性與背板
        playerAttribute = new TextView[4][4];
        playerAttributeFrame = new ImageView[4];
        character = new Character();
        for(int i=0;i<4;i++){
            playerAttributeFrame[i] = new ImageView(this);
            playerAttribute[i] = new TextView[4];
            for(int j=0;j<4;j++)
                playerAttribute[i][j] = new TextView(this);
        }
        // 列舉並初始化一些東西(括號只是想它可以折疊)
        {
            role[1] = findViewById(R.id.role1);
            role[2] = findViewById(R.id.role2);
            role[3] = findViewById(R.id.role3);
            role[4] = findViewById(R.id.role4);
            role[5] = findViewById(R.id.role5);
            playerAttributeFrame[0] = findViewById(R.id.one);
            playerAttributeFrame[1] = findViewById(R.id.two);
            playerAttributeFrame[2] = findViewById(R.id.three);
            playerAttributeFrame[3] = findViewById(R.id.four);
            playerAttribute[0][0] = findViewById(R.id.oneMight);
            playerAttribute[0][1] = findViewById(R.id.oneSpeed);
            playerAttribute[0][2] = findViewById(R.id.oneSanity);
            playerAttribute[0][3] = findViewById(R.id.oneKnowledge);
            playerAttribute[1][0] = findViewById(R.id.twoMight);
            playerAttribute[1][1] = findViewById(R.id.twoSpeed);
            playerAttribute[1][2] = findViewById(R.id.twoSanity);
            playerAttribute[1][3] = findViewById(R.id.twoKnowledge);
            playerAttribute[2][0] = findViewById(R.id.threeMight);
            playerAttribute[2][1] = findViewById(R.id.threeSpeed);
            playerAttribute[2][2] = findViewById(R.id.threeSanity);
            playerAttribute[2][3] = findViewById(R.id.threeKnowledge);
            playerAttribute[3][0] = findViewById(R.id.fourMight);
            playerAttribute[3][1] = findViewById(R.id.fourSpeed);
            playerAttribute[3][2] = findViewById(R.id.fourSanity);
            playerAttribute[3][3] = findViewById(R.id.fourKnowledge);
        }

        // 玩家初始位置在地圖中央
        for(int i = 1;i<6;i++) {
            role[i].setTranslationX(playerX[i]);
            role[i].setTranslationY(playerY[i]);
        }

        // 視角初始位置在地圖中央 (待補)
        bg.setTranslationX((int)bg.getX()-visionX - dp *1.2f*12.5f + 500);
        bg.setTranslationY((int)bg.getY()-visionY - dp *1.2f*9f + 500);
        PlateLayout.setTranslationX((int) PlateLayout.getX()-visionX - dp *1.2f*12.5f + 500);
        PlateLayout.setTranslationY((int) PlateLayout.getY()-visionY - dp *1.2f*9f + 500);

    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id){

        switch (id){
            case R.id.joystick:
                if(myRound){
                    // 取得搖桿的動作 因為回傳值是0.01~1 所以要乘10
                    x = (int)(xPercent*10);
                    y = (int)(yPercent*10);

                    // 移動玩家 (單純顯示)
                    role[myClientID].setTranslationX(role[myClientID].getX() + x);
                    role[myClientID].setTranslationY(role[myClientID].getY() + y);

                    // 移動玩家 (更新絕對座標)
                    playerX[myClientID] += TransDP(x);
                    playerY[myClientID] += TransDP(y);

                    // 做成封包
                    packetMaker();

                    // 算板塊 XY
                    plateX = (playerX[myClientID] + 7220) / 600;
                    plateY = (playerY[myClientID] + 4500) / 600;

                    coor.setText("X: "+playerX[myClientID] + " Y: "+playerY[myClientID]+" PX: "+plateX + " PY: "+plateY);
                    coor1.setText("ID: "+myClientID);

                }
                break;

            case R.id.joystick2:
                // 取得搖桿的動作 因為回傳值是0.01~1 所以要乘10
                visionX = (int) (xPercent*15);
                visionY = (int) (yPercent*15);

                // 移動視角
                for(int i=1;i<6;i++){
                    role[i].setTranslationX((int)role[i].getX()-visionX);
                    role[i].setTranslationY((int)role[i].getY()-visionY);
                }
                bg.setTranslationX((int)bg.getX()-visionX);
                bg.setTranslationY((int)bg.getY()-visionY);
                PlateLayout.setTranslationX((int) PlateLayout.getX()-visionX);
                PlateLayout.setTranslationY((int) PlateLayout.getY()-visionY);
                break;
        }
    }

    // 封包切割機
    void packetMaker(){

        // 清空字串
        msgToServer = "";
        // 加上座標
        msgToServer += String.format("%05d", playerX[myClientID]);
        msgToServer += String.format("%05d", playerY[myClientID]);
        // 加上回合是否結束
        msgToServer += roundOver;

        // 加上遊戲開始的訊號
        if(gameStart == 1)
            msgToServer += "1";
        else
            msgToServer += "0";

        // 送出自己座標的封包
        Sender sender = new Sender();
        new Thread(sender).start();
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
            population = view1.findViewById(R.id.population);
            Button start = view1.findViewById(R.id.start);
            AlertDialog.Builder alert1 = new AlertDialog.Builder(context);
            alert1.setView(view1);
            AlertDialog dialog1 = alert1.create();
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.setCancelable(false);
            start.setOnClickListener(v1 -> {
                gameStart = 1;
                packetMaker();
                // 設定遊戲開始畫面
                // 角色數量不超過玩家數
                System.out.println("numberOfPlayer: " + numberOfPlayer);
                for(int i = numberOfPlayer + 1; i < 6; i++)
                    role[i].setVisibility(View.GONE);
                // 屬性框數量不超過玩家數
                for(int i = numberOfPlayer-1; i<=3;i++)
                    playerAttributeFrame[i].setVisibility(View.GONE);
                // 初始化角色們的屬性值
                for(int i=1, j=0; i<=numberOfPlayer; i++, j++){   // i 迭代, j 代表屬性欄編號
                    if(i != myClientID){
                        // playerAttribute 的範圍只到3
                        playerAttribute[j][0].setText(Integer.toString(character.chara[characterIndex[i]].Might[character.chara[characterIndex[i]].iniMight]));
                        playerAttribute[j][1].setText(Integer.toString(character.chara[characterIndex[i]].Speed[character.chara[characterIndex[i]].iniSpeed]));
                        playerAttribute[j][2].setText(Integer.toString(character.chara[characterIndex[i]].Sanity[character.chara[characterIndex[i]].iniSanity]));
                        playerAttribute[j][3].setText(Integer.toString(character.chara[characterIndex[i]].Knowledge[character.chara[characterIndex[i]].iniKnowledge]));

                    } else {
                        j--;
                        TextView myMight = findViewById(R.id.myMight);
                        TextView mySpeed = findViewById(R.id.mySpeed);
                        TextView mySanity = findViewById(R.id.mySanity);
                        TextView myKnowledge = findViewById(R.id.myKnowledge);
                        myMight.setText(Integer.toString(character.chara[characterIndex[myClientID]].Might[character.chara[characterIndex[myClientID]].iniMight]));
                        mySpeed.setText(Integer.toString(character.chara[characterIndex[myClientID]].Speed[character.chara[characterIndex[myClientID]].iniSpeed]));
                        mySanity.setText(Integer.toString(character.chara[characterIndex[myClientID]].Sanity[character.chara[characterIndex[myClientID]].iniSanity]));
                        myKnowledge.setText(Integer.toString(character.chara[characterIndex[myClientID]].Knowledge[character.chara[characterIndex[myClientID]].iniKnowledge]));
                    }
                }

                // 關閉彈出視窗
                dialog1.dismiss();
            });
            dialog1.show();
        });

        // 顯示
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void GoBack(View view) {
        visionX = (int)(role[myClientID].getX()-abVision.getX());
        visionY = (int)(role[myClientID].getY()-abVision.getY());
        bg.setTranslationX(bg.getX()-visionX);
        bg.setTranslationY(bg.getY()-visionY);
        PlateLayout.setTranslationX(PlateLayout.getX()-visionX);
        PlateLayout.setTranslationY(PlateLayout.getY()-visionY);
        for(int i=1;i<6;i++){
            role[i].setTranslationX((int) role[i].getX() - visionX);
            role[i].setTranslationY((int) role[i].getY() - visionY);
        }
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
        roundOver = 1;
        packetMaker();
        roundOver = 2;
        myRound = false;
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
                    // 收一個封包
                    msgFromServer = in.readLine();

                    switch (msgFromServer.charAt(0)){
                        // 0 開頭
                        case '0':
                            // 第 1 格代表人數
                            numberOfPlayer = msgFromServer.charAt(1) - 48;
                            // 第 2~6 格代表抽出的角色序號
                            for(int i=1;i<6;i++)
                                characterIndex[i] = msgFromServer.charAt(i+1) - 48;
                            // 第 3 格代表該client的ID
                            if(myClientID == 0)
                                myClientID = msgFromServer.charAt(7) - 48;
                            // 顯示玩家人數
                            runOnUiThread(() ->
                                    population.setText("玩家人數 : " + numberOfPlayer));
                            break;
                        case '1':
                            // 第 1 格代表誰的回合
                            if(msgFromServer.charAt(1)-48 == myClientID){
                                roundOver = 0;
                                myRound = true;
                            }
                            // 角色 1~5 的座標
                            int from=2, to=7;
                            for(int i=1;i<6;i++){
                                if(i != myClientID && !myRound) {
                                    playerX[i] = Integer.parseInt(msgFromServer.substring(from, to));
                                    playerY[i] = Integer.parseInt(msgFromServer.substring(from + 5, to + 5));
                                    role[i].setTranslationX(role[myClientID].getX() + TransPX(playerX[i] - playerX[myClientID]));
                                    role[i].setTranslationY(role[myClientID].getY() + TransPX(playerY[i] - playerY[myClientID]));
                                }
                                from += 10;
                                to += 10;
                            }
                            break;
                    }
                }
            }catch (IOException e){} // 就算拋出例外我也沒辦法處裡
        }
    }
}