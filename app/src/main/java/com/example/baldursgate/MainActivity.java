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
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends Activity implements JoystickView.JoystickListener {

    //物品的文字敘述 by周辰陽
    String[] AllItemName ={"蝙蝠長袍"
            ,"十字弓"
            ,"穢惡之書"
            ,"鎖子甲"
            ,"聖符"
            ,"火球項鍊"
            ,"再生戒指"
            ,"巨人力量藥劑"
            ,"賭徒的骰子"
            ,"十呎長棍"
            ,"毒藥瓶"
            ,"發光的石頭"
            ,"治療藥水"
            ,"速度藥水"
            ,"猛擊戒指"
            ,"閃電標槍"
            ,"項鍊墜"
    };
    String[] itemword = {"當面臨危險時，你可以使用這個披風變形成一隻蝙蝠，然後逃走。"
            ,"當你想要在一個安全距離外投入戰鬥時的好工具。"
            ,"只有在你無法獲利時，褻瀆才是一種罪過。"
            ,"沒什麼能像金屬皮膚一樣保護你自己。"
            ,"針的針腳是可以拆卸的，所以你可以有效率的更換神祗的徽記。"
            ,"有些問題的最佳解決辦法，就是利用大量的火焰及爆破力。"
            ,"以木頭製成，這戒指覆蓋著小樹葉，摸起來很溫暖。"
            ,"一片山丘巨人的指甲碎片，漂浮在瓶內的清澈液體中。"
            ,"儘管它們看起來邪惡，這些骰子讓你覺得你的運氣終於要扭轉了。"
            ,"當你想要在安全距離外踫觸某些物品時的完美工具。"
            ,"在柏德之門，毒藥是最直接的方法去告訴某人，你對他的存在已經厭煩了。"
            ,"多麼漂亮的石頭，應該有人會跟你交易它。"
            ,"當你搖動瓶身，瓶內的紅色液體發出陣陣微光。"
            ,"瓶內充滿著黃色及黑色的混合液體，在裡面旋轉著，就像小型的暴風。"
            ,"這個鐵戒指有一對公羊頭做為裝飾。"
            ,"擁有潛在能量的鋸齒狀標槍，發出陣陣嗡鳴聲。"
            ,"一條薄薄的金鍊上的愛情紀念品。"
    };
    String[] itemword2 = {"使用後當你下次受到物理傷害時，做一個速度3+檢定，若你成功，你免疫傷害，當成功使用後棄掉此物品。"
            ,"使用後下次攻擊以速度進行攻擊（敵人用速度防禦）。在你的速度攻擊中擲 1 個額外的骰(最多 8 顆)。當你使用此武器就不能使用其他武器。"
            ,"使用後下次擲骰多擲 2 顆骰子，之後就受到 1 級精神傷害。"
            ,"每當你受到物理傷害，就減少 1 級傷害。"
            ,"取得 1 級知識。當你攻擊給予傷害時，你可以造成額外 1 點精神傷害，若你失去聖符，就失去 1 級知識。"
            ,"取代正常攻擊，敵人進行速度檢定，擲出4以下受到4點傷害。"
            ,"在你的回合結束時，只要有屬性位於最低值，你就可以取得 1 級符合條件的一種屬性。"
            ,"使用後當你下次要做力量檢定時，你可以使用此道具來額外增加 2 個骰 (最多 8 個骰)，使用後棄掉此卡。"
            ,"一回合一次，使用後將重擲結果為 0 的所有骰子，並保留第二次擲骰的結果。每個重擲後結果仍然為 0 的骰子，就令你受到 1 級精神傷害。"
            ,"使用後下一個事件卡會導致你受傷時，將傷害變為0，並棄掉此物品"
            ,"使用後當你攻擊造成物理傷害時，令傷害增加[1 顆骰子+1]，並棄掉此卡。"
            ,"使用後隨機偷取一件玩家物品，並棄掉此卡。"
            ,"使用後 如果你的力量或速度低於初始值，將他們恢復到初始值，並棄掉此卡。"
            ,"使用後當你下次要做速度檢定時，額外增加 2 個骰 (最多 8 個骰)。並且可以額外多移動 2 步。使用後棄掉此卡。"
            ,"使用後，下次進行力量攻擊時，你額外擲 1 顆骰(最多 8 個骰)，使用此武器時不能使用其他武器。"
            ,"使用後，下次攻擊取代正常攻擊，目標會受到 2 個骰的物理傷害，使用此武器時不能使用其他武器，，並棄掉此卡。"
            ,"取得 1 級心智，每當你受到精神傷害時，減少 1 級傷害，若你失去項鍊墜，就失去 1 級心智。"
    };

    //事件的文字敘述 by周辰陽
    String[] eventname = {"爐邊恐懼"
            ,"莫安德的回聲"
            ,"惡魔支配"
            ,"顱骨鼠群"
            ,"爆破符文"
            ,"地獄契約"
            ,"孤獨的箱子"
            ,"吸血鬼迷霧"
            ,"腐蛆"
            ,"兇猛的大氣元素"
            ,"紅巫師"
            ,"地穴陷阱"
            ,"平靜時刻"
            ,"鴉閣城迷霧"
            ,"迷路之魂"
            ,"失去及尋獲"
            ,"一個飛天水母出現"
            ,"設陷者"
            ,"死亡三人眾的考驗"
            ,"潛行暗影"
            ,"饑餓部族"
            ,"綠色史萊姆"
            ,"為了眼睛"
            ,"未來景色"
            ,"蹣跚的形體"
            ,"散塔林投機者"
            ,"黃色黴菌"
            ,"鬼火"
            ,"謀殺者景象"
            ,"女巫的祝福"
            ,"乾燥的心臟"
            ,"死亡景象"
            ,"巴爾的呼喚"
            ,"被遺忘的聖物"
            ,"洪水"
    };
    String[] eventword = {"形式古怪的雕像充滿這個區域，這是一群收藏品，還是一個部落呢？"
            ,"無預警的，一群觸手從地面竄出並向你揮擊過來。"
            ,"你發現一個黑曜石雕像立在地面上，當你試著移動它時，觸動了一個惡魔陷阱並試著要支配著你。"
            ,"一群老鼠從他們的洞穴中竄出並圍繞著你，牠們體形巨大，跳動的大腦擠出頭骨，眼中發出紅光，你發現你的心靈跟一股巨大力量相連結，直達你的大腦及記憶深處。"
            ,"一塊羊皮紙陷在泥地裡，不知道上面寫了什麼。"
            ,"暗影中出現了一個英俊的人並提供協助，他溫和的語氣向你保證他真的是帶著一片好意前來的。"
            ,"這裡有個寶箱，樸素而帶有光澤，等待著某人來開啟它。"
            ,"迷霧向你移動過來，一隻老鼠跑了進去，隨後聽到一聲慘叫，當迷霧移開，只留下一副囓齒動物的外皮。"
            ,"某個東西掉到你背上並爬上你的頭，那應該是一種常見的蟲，但這腐蛆可以在幾秒內就鑽入你的腦袋裡！"
            ,"一個小型的龍捲風經過此區，它把你捲住並試著把你丟到小巷子裡。"
            ,"一個頭上覆蓋著刺青的光頭紅長袍女人，從迷霧中慢慢向你靠近，對你說：也許我有你想要的東西。"
            ,"有時候，你會在你跌進去時找到一個隱藏的地穴。"
            ,"你突然發現你孤單一人，現在搜索這個地方應該不會有損失吧？"
            ,"迷霧環繞，冷風吹拂，你看到一座糢糊的城堡座落在孤山的山頂上。"
            ,"你看見一個矮人幽靈蹣跚的向你靠近，說著：家…離家好遠啊…你能指引我回家的路嗎？"
            ,"黑杜高告訴你他從岡德神殿中取得的戰利品的藏匿處，毒針終止了他的事業，若你能找到寶藏的話，你就可以任意拿走。"
            ,"這是你看過最奇怪的事了－一個有眼睛的水母－漂浮在牆角，並且用帶有尖刺的觸手做出了一些明顯的手勢。"
            ,"這裡的牆看起來很奇怪，似乎佈滿了軟泥，當你靠近調查時，它又捲曲著將你緊緊纏住。"
            ,"三個幽魂出現在你面前：一個穿著盔甲的邪惡武士，一個穿著長袍的骷髏法師及一個拿著染血匕首冷笑著的人，你感覺他們在這裡準備考驗你。"
            ,"你的影子閃爍並舞動著，慢慢自行融合成一個可怕的人形並撲向你。"
            ,"老鼠佈滿街道，從下水道的格柵中湧出，像一波浪潮淹沒了路徑上的任何人及任何物品。"
            ,"一層厚厚的綠色史萊姆從這個區域的上方滴落。"
            ,"一些小東西在你視野的邊緣猛衝，你聽到一陣尖叫並往下看，一隻烏鴉飛衝上來，並啄向你的眼睛！"
            ,"一個染著血且糢糊的自身影像蹣跚的朝你前來，並說著：我希望我能早點擁有它。在他回到迷霧之前，他給了你一樣東西。"
            ,"一個醉漢穿過夜色朝你而來，當他靠近時，你看到了你的恐懼，那是一個身體發脹的蹣跚殭屍。"
            ,"一名暴徒從暗影中跳出，揮舞著一把匕首，嘶聲的說著：乖乖給我我想要的東西，這樣就沒有人會受傷。"
            ,"一叢致病的黃色黴菌生長在此，孢子噴出，使你不斷咳嗽。"
            ,"閃爍的光芒在前方的暗影中出現，在空中搖曳搖動著，你感覺無法抵抗，跟隨著光芒前進。"
            ,"你暈眩了一會兒，看到一段過去的景象：一名殺手折斷了受害者的脖子，並把他丟出一棟建築，那軀體墜落著，直到永遠。"
            ,"一個穿著破爛長袍的身影蹣跚地走向你，她乳白色的眼睛仔細觀察著，並指著你說道..."
            ,"你發現一個皺縮且乾燥的心臟被塞進一個黑色木盒裡，當這個心臟以相同的頻率和你的心臟一起跳動時，你開始覺得害怕。"
            ,"成群的蟲聚集成一堆，當你靠近時他們就逃開了，只留下一具被吃掉一半的屍體。"
            ,"你看到一個怪異的頭骨，腳下滿是血池，受折磨的靈魂哭喊的聲音迴盪在你耳中。"
            ,"隱藏在角落的牆上，那是正義之神提爾的聖符，也許這裡可以提供你一些幫助。"
            ,"那是什麼聲音？"
    };

    //匯入地圖圖片 by周辰陽
    private final int[] building = {
            R.drawable.building_fireworks,
            R.drawable.building_shrine,
            R.drawable.building_hall,
            R.drawable.building_blood_house,
            R.drawable.building_mansion,
            R.drawable.building_smithy,
            R.drawable.building_roost,
            R.drawable.building_storehouse,
            R.drawable.building_sorcerous_sundries,
            R.drawable.building_temple,
            R.drawable.building_trading_post,
            R.drawable.building_warehouse,
            R.drawable.building_weapon_shop};
    private final int[] catacomb = {
            R.drawable.catacomb_assassin_run,
            R.drawable.catacomb_lair,
            R.drawable.catacomb_dusty_cellar,
            R.drawable.catacomb_flooded_chamber,
            R.drawable.catacomb_forgotten_cache,
            R.drawable.catacomb_gladiator_pit,
            R.drawable.catacomb_kobold_hideout,
            R.drawable.catacomb_rat_nest,
            R.drawable.catacomb_arcane_retreat,
            R.drawable.catacomb_sacrificial_chamber,
            R.drawable.catacomb_shrine,
            R.drawable.catacomb_shrine_to_bhaal,
            R.drawable.catacomb_smuggler_drop,
            R.drawable.catacomb_vacated_shop,
            R.drawable.catacomb_weapon_shop_basement};
    private final int[] street = {
            R.drawable.street_ambush_alley,
            R.drawable.street_beloved_ranger_statue,
            R.drawable.street_cursed_statue,
            R.drawable.street_dead_end,
            R.drawable.street_executioner_block,
            R.drawable.street_fountain,
            R.drawable.street_gray_harbor,
            R.drawable.street_haunted_alley,
            R.drawable.street_market,
            R.drawable.street_murder_row,
            R.drawable.street_rose_portal,
            R.drawable.street_trash_pile,
            R.drawable.street_watch_post,
            R.drawable.street_waterfront};
    private final int[] allroom = {
            R.drawable.building_fireworks,
            R.drawable.building_shrine,
            R.drawable.building_hall,
            R.drawable.building_blood_house,
            R.drawable.building_mansion,
            R.drawable.building_smithy,
            R.drawable.building_roost,
            R.drawable.building_storehouse,
            R.drawable.building_sorcerous_sundries,
            R.drawable.building_temple,
            R.drawable.building_trading_post,
            R.drawable.building_warehouse,
            R.drawable.building_weapon_shop,
            R.drawable.catacomb_assassin_run,
            R.drawable.catacomb_lair,
            R.drawable.catacomb_dusty_cellar,
            R.drawable.catacomb_flooded_chamber,
            R.drawable.catacomb_forgotten_cache,
            R.drawable.catacomb_gladiator_pit,
            R.drawable.catacomb_kobold_hideout,
            R.drawable.catacomb_rat_nest,
            R.drawable.catacomb_arcane_retreat,
            R.drawable.catacomb_sacrificial_chamber,
            R.drawable.catacomb_shrine,
            R.drawable.catacomb_shrine_to_bhaal,
            R.drawable.catacomb_smuggler_drop,
            R.drawable.catacomb_vacated_shop,
            R.drawable.catacomb_weapon_shop_basement,
            R.drawable.street_ambush_alley,
            R.drawable.street_beloved_ranger_statue,
            R.drawable.street_cursed_statue,
            R.drawable.street_dead_end,
            R.drawable.street_executioner_block,
            R.drawable.street_fountain,
            R.drawable.street_gray_harbor,
            R.drawable.street_haunted_alley,
            R.drawable.street_market,
            R.drawable.street_murder_row,
            R.drawable.street_rose_portal,
            R.drawable.street_trash_pile,
            R.drawable.street_watch_post,
            R.drawable.street_waterfront};
    private final String[] allroomdoor = {"RNRY","YYYR","NNYR","RRRR","RRYN","YNRN","RNNN","RRRR","NNYR","YNYN","NNYN","RYRR","YYRR"
            ,"BNBN","BBNN","BBBN","BNBN","NBBB","BBBB","BNNN","BNNN","NBBB","BNBB","NBNB","NBBN","BNNN","BNBB","BNBN"
            ,"YNYN","YRYR","NRYN","RNNN","YYYR","YYYY","YNNN","YNYN","NYNR","RNNR","YYNY","NNRN","NRNR","YNRN"};
    //整張地圖目前的房間
    private final int[][] maproom = new int[25][15];      // 地圖上所有房間的板塊是哪個
    private final int[] buildingopen = new int [13];      // 開過哪些房間
    private final int[] catacombopen = new int[15];       // 開過哪些房間
    private final int[] streetopen = new int[14];         // 開過哪些房間
    private final String[][] door = new String[25][15];   // 這房間的4個門的狀態
    int getnum = -1;                                      // 哪一個板塊(隨機取數)
    int times = 0;                                        //旋轉次數
    int which = -1;

    //下回合可以移動的步數 by周辰陽
    int NextRoundMove;

    //事件 by 周辰陽
    int event[] = new int[35];
    boolean NextDiceZero = false;
    boolean DrawItem = false;

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
    Button RoundOver;

    TextView abVision;
    TextView coor,coor1;

    // 遊戲角色屬性變數
    int myClientID = 0;         // client號
    int x, y;                   // 角色位移
    int visionX=0, visionY=0;   // 視野範圍
    int plateX, plateY;         // 角色所在的板塊
    Character character = new Character();

    // 玩家們的 XY 座標   *****角色絕對座標 (單位:DP) 600dp/plate (0,0)=>(-7220, -4500)*****
    int[] playerX = { 100 ,100 ,100 ,100 ,100 ,100 };
    int[] playerY = {-100,-100,-100,-100,-100,-100 };

    // 遊戲變數
    int gameStart = 0;                  // 遊戲開始了嗎？
    int numberOfPlayer = 1;             // 玩家數量
    int[] characterIndex = new int[6];  // 角色代號
    boolean myRound = false;            // 是我的回合嗎？
    int roundOver = 2;                  // 回合結束了嗎 (0: 我的回合還沒結束, 1:我的回合結束了, 2:不是我的回合，郭?)
    boolean[] alive = { true, true, true, true, true, true }; // 是否還活著(由SERVER告知)
    boolean openplate = false;          // 有沒有開新板塊
    String newPlateThings;


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

    //背包的設定 by周辰陽
    int[] bag = {-1,-1,-1,-1,-1,-1};
    int[] item = new int [17];
    int takenitem = 0;

    //現在按的按鈕 by周辰陽
    private int clickitemnow = 0;

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
        RoundOver = findViewById(R.id.roundOver);

        abVision = findViewById(R.id.vision);
        coor = findViewById(R.id.coor);
        coor1 = findViewById(R.id.coor1);

        // 設定DP->SP單位換算大小
        int dp = TransDP(150);
        ViewGroup.LayoutParams params;
        params = new ViewGroup.LayoutParams(dp,dp);

        // 初始化地圖板塊
        for(int i=0;i<25;i++)
        {
            for(int j=0;j<15;j++)
            {
                Plate[i][j] = new ImageView(this);
                Plate[i][j].setTranslationX(dp *i +500);
                Plate[i][j].setTranslationY(dp *j +500); // 預設圖片
                Plate[i][j].setLayoutParams(params);
                Plate[i][j].setImageResource(R.drawable.catacomb_assassin_run);
                PlateLayout.addView(Plate[i][j]);
            }
        }
        Plate[12][7].setImageResource(R.drawable.inner);
        Plate[11][7].setImageResource(R.drawable.kitchen);
        Plate[13][7].setImageResource(R.drawable.elfsong);

        //初始化地圖 by周辰陽
        for(int a= 0;a<25;a++)
        {
            for(int b= 0;b<15;b++)
            {
                door[a][b] = "UUUU"; //四個口都是封住
                maproom[a][b] = -1;
            }
        }
        for(int a= 0;a<13;a++)
        {
            buildingopen[a] = 0;
            catacombopen[a] = 0;
            streetopen[a] = 0;
        }
        door[12][7] = "RGRG"; //R是紅色 G是開過了 B是藍色 Y是黃色 N是不能走的 U是還沒開過的
        door[11][7] = "RGRB";
        door[13][7] = "YYYG";
        maproom[11][7] = 42;
        maproom[12][7] = 43;
        maproom[13][7] = 44;


        // 初始化玩家角色
        role = new ImageView[6];
        for(int i = 0;i<6;i++)
            role[i] = new ImageView(this);

        // 初始化屬性與背板
        playerAttribute = new TextView[4][4];
        playerAttributeFrame = new ImageView[4];
        character = new Character();
        for(int i=0;i<4;i++)
        {
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

                    // 先算一次座標
                    plateX = (playerX[myClientID] + 7220) / 600;
                    plateY = (playerY[myClientID] + 4500) / 600;

                    // 移動玩家 (單純顯示)
                    // X
                    if(maproom[(playerX[myClientID] + TransDP(x) + 7220) / 600][plateY] > -1){
                        //if((playerX[myClientID] + TransDP(x) + 7220) / 600 != plateX && CanPass(plateX, plateY, ((playerX[myClientID] + TransDP(x) + 7220) / 600), plateY)) {
                        // 移動玩家 (單純顯示)
                        role[myClientID].setTranslationX(role[myClientID].getX() + x);
                        // 移動玩家 (更新絕對座標)
                        playerX[myClientID] += TransDP(x);
                        // 算板塊 XY
                        plateX = (playerX[myClientID] + 7220) / 600;
                        //}
//                        else if ((playerX[myClientID] + TransDP(x) + 7220) / 600 == plateX) {
//                            // 移動玩家 (單純顯示)
//                            role[myClientID].setTranslationX(role[myClientID].getX() + x);
//                            // 移動玩家 (更新絕對座標)
//                            playerX[myClientID] += TransDP(x);
//                            // 算板塊 XY
//                            plateX = (playerX[myClientID] + 7220) / 600;
//                        }
                    }

                    // Y
                    if(maproom[plateX][(playerY[myClientID] + TransDP(y) + 4500) / 600] > -1){
                        //if((playerY[myClientID] + TransDP(y) + 4500) / 600 != plateY && CanPass(plateX, plateY, plateX, (playerY[myClientID] + TransDP(y) + 4500) / 600)){
                        // 移動玩家 (單純顯示)
                        role[myClientID].setTranslationY(role[myClientID].getY() + y);
                        // 移動玩家 (更新絕對座標)
                        playerY[myClientID] += TransDP(y);
                        // 算板塊 XY
                        plateY = (playerY[myClientID] + 4500) / 600;
                        //}
//                        else if((playerY[myClientID] + TransDP(y) + 4500) / 600 == plateY){
//                            // 移動玩家 (單純顯示)
//                            role[myClientID].setTranslationY(role[myClientID].getY() + y);
//                            // 移動玩家 (更新絕對座標)
//                            playerY[myClientID] += TransDP(y);
//                            // 算板塊 XY
//                            plateY = (playerY[myClientID] + 4500) / 600;
//                        }
                    }

                    // 做成封包
                    packetMaker();

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

    public boolean CanPass(int nowx, int nowy, int gox, int goy) {
        if(gox > nowx) {
            if(door[nowx][nowy].charAt(1) == 'G')
                return true;
            else
                return false;
        }
        if(gox < nowx) {
            if(door[nowx][nowy].charAt(3) == 'G')
                return true;
            else
                return false;
        }
        if(nowy > goy) {
            if(door[nowx][nowy].charAt(0) == 'G')
                return true;
            else
                return false;
        }
        if(nowy < goy) {
            if(door[nowx][nowy].charAt(2) == 'G')
                return true;
            else
                return false;
        }
        return false;
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

        // 加上能力值
        msgToServer += character.chara[characterIndex[myClientID]].currentMight;
        msgToServer += character.chara[characterIndex[myClientID]].currentSpeed;
        msgToServer += character.chara[characterIndex[myClientID]].currentSanity;
        msgToServer += character.chara[characterIndex[myClientID]].currentKnowledge;

        if(openplate){
            openplate = false;
            msgToServer += newPlateThings;
        }
        else
            msgToServer += "000000000000000000000000000000";

        // 送出自己座標的封包
        Sender sender = new Sender();
        new Thread(sender).start();
    }
    // 設定按鈕的點擊事件
    @SuppressLint("SetTextI18n")
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
                // 設定遊戲開始畫面
                // 角色數量不超過玩家數
                for (int i = numberOfPlayer + 1; i < 6; i++)
                    role[i].setVisibility(View.GONE);
                // 屬性框數量不超過玩家數
                for (int i = numberOfPlayer - 1; i <= 3; i++) {
                    playerAttributeFrame[i].setVisibility(View.GONE);
                }
                // 初始化角色們的屬性值
                for (int i = 1, j = 0; i <= numberOfPlayer; i++, j++) { // i 迭代, j 代表屬性欄編號
                    if (i != myClientID) {
                        // playerAttribute 的範圍只到3
                        playerAttribute[j][0].setText(Integer.toString(
                                character.chara[characterIndex[i]].Might[character.chara[characterIndex[i]].iniMight]));
                        playerAttribute[j][1].setText(Integer.toString(
                                character.chara[characterIndex[i]].Speed[character.chara[characterIndex[i]].iniSpeed]));
                        playerAttribute[j][2].setText(Integer.toString(
                                character.chara[characterIndex[i]].Sanity[character.chara[characterIndex[i]].iniSanity]));
                        playerAttribute[j][3].setText(Integer.toString(
                                character.chara[characterIndex[i]].Knowledge[character.chara[characterIndex[i]].iniKnowledge]));
                    } else {
                        j--;
                        TextView myMight = findViewById(R.id.myMight);
                        TextView mySpeed = findViewById(R.id.mySpeed);
                        TextView mySanity = findViewById(R.id.mySanity);
                        TextView myKnowledge = findViewById(R.id.myKnowledge);
                        myMight.setText(Integer.toString(
                                character.chara[characterIndex[myClientID]].Might[character.chara[characterIndex[myClientID]].iniMight]));
                        mySpeed.setText(Integer.toString(
                                character.chara[characterIndex[myClientID]].Speed[character.chara[characterIndex[myClientID]].iniSpeed]));
                        mySanity.setText(Integer.toString(
                                character.chara[characterIndex[myClientID]].Sanity[character.chara[characterIndex[myClientID]].iniSanity]));
                        myKnowledge.setText(Integer.toString(
                                character.chara[characterIndex[myClientID]].Knowledge[character.chara[characterIndex[myClientID]].iniKnowledge]));
                    }
                }

                packetMaker();
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

        // 從XML中找到物件 by周辰陽
        final Button item1 = v.findViewById(R.id.item1);
        final Button item2 = v.findViewById(R.id.item2);
        final Button item3 = v.findViewById(R.id.item3);
        final Button item4 = v.findViewById(R.id.item4);
        final Button item5 = v.findViewById(R.id.item5);
        final Button item6 = v.findViewById(R.id.item6);
        final TextView itemname = v.findViewById(R.id.itemname);
        final TextView itemcontext = v.findViewById(R.id.itemcontext);
        final TextView itemcontext2 = v.findViewById(R.id.itemcontext2);
        final Button get = v.findViewById(R.id.get);

        // 宣告一個彈出視窗
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(v);
        // 顯示
        AlertDialog dialog = alert.create();
        dialog.show();

        //背包物品的按鈕 by周辰陽
        item1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clickitemnow = 1;
                if(bag[0] != -1)
                {
                    itemname.setText(AllItemName[bag[0]]);
                    itemcontext.setText(itemword[bag[0]]);
                    itemcontext2.setText(itemword2[bag[0]]);
                }
                else
                {
                    itemname.setText("沒有物品");
                    itemcontext.setText("沒有物品");
                    itemcontext2.setText("沒有物品");
                }
            }
        });
        item2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clickitemnow = 2;
                if(bag[1] != -1)
                {
                    itemname.setText(AllItemName[bag[1]]);
                    itemcontext.setText(itemword[bag[1]]);
                    itemcontext2.setText(itemword2[bag[1]]);
                }
                else
                {
                    itemname.setText("沒有物品");
                    itemcontext.setText("沒有物品");
                    itemcontext2.setText("沒有物品");
                }
            }
        });
        item3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clickitemnow = 3;
                if(bag[2] != -1)
                {
                    itemname.setText(AllItemName[bag[2]]);
                    itemcontext.setText(itemword[bag[2]]);
                    itemcontext2.setText(itemword2[bag[2]]);
                }
                else
                {
                    itemname.setText("沒有物品");
                    itemcontext.setText("沒有物品");
                    itemcontext2.setText("沒有物品");
                }
            }
        });
        item4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clickitemnow = 4;
                if(bag[3] != -1)
                {
                    itemname.setText(AllItemName[bag[3]]);
                    itemcontext.setText(itemword[bag[3]]);
                    itemcontext2.setText(itemword2[bag[3]]);
                }
                else
                {
                    itemname.setText("沒有物品");
                    itemcontext.setText("沒有物品");
                    itemcontext2.setText("沒有物品");
                }
            }
        });
        item5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clickitemnow = 5;
                if(bag[4] != -1)
                {
                    itemname.setText(AllItemName[bag[4]]);
                    itemcontext.setText(itemword[bag[4]]);
                    itemcontext2.setText(itemword2[bag[4]]);
                }
                else
                {
                    itemname.setText("沒有物品");
                    itemcontext.setText("沒有物品");
                    itemcontext2.setText("沒有物品");
                }
            }
        });
        item6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clickitemnow = 6;
                if(bag[5] != -1)
                {
                    itemname.setText(AllItemName[bag[5]]);
                    itemcontext.setText(itemword[bag[5]]);
                    itemcontext2.setText(itemword2[bag[5]]);
                }
                else
                {
                    itemname.setText("沒有物品");
                    itemcontext.setText("沒有物品");
                    itemcontext2.setText("沒有物品");
                }
            }
        });
        get.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                while (true)
                {
                    if(takenitem == 17) //物品最多17樣 多了就不能拿了
                        break;
                    int GetItem = (int)(Math.random()*17);
                    if(item[GetItem] == 0)
                    {
                        for(int a = 0 ; a<6;a++)
                        {
                            if(bag[a] == -1)
                            {
                                bag[a] = GetItem;
                                takenitem++;
                                item[GetItem] = 1;
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        });
        ubun.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                itemname.setText("沒有物品");
                itemcontext.setText("沒有物品");
                itemcontext2.setText("沒有物品");
                bag[clickitemnow-1] = -1;
            }
        });
    }
    // 回合結束按鈕
    public void roundOver(View view) {
        roundOver = 1;
        packetMaker();
        roundOver = 2;
        myRound = false;
    }
    // 擲骰子按鈕
    @SuppressLint("DefaultLocale")
    public void newPlate(View view) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.new_plate, null);
        // 宣告一個彈出視窗
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(v);
        // 顯示
        AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        // 找按鈕
        ImageButton up = v.findViewById(R.id.up);
        ImageButton down = v.findViewById(R.id.down);
        ImageButton left = v.findViewById(R.id.left);
        ImageButton right = v.findViewById(R.id.right);
        ImageButton red = v.findViewById(R.id.red);
        ImageButton blue = v.findViewById(R.id.blue);
        ImageButton yellow = v.findViewById(R.id.yellow);
        ImageButton rotate = v.findViewById(R.id.rotate);
        ImageView picked = v.findViewById(R.id.picked);
        ImageView origin = v.findViewById(R.id.origin);
        TextView textView = v.findViewById(R.id.howisnow);
        textView.setText(door[plateX][plateY]);

        // 這裡()裡面讓他的圖片跟玩家腳下的同一張
        origin.setImageResource(maproom[plateX][plateY]);
        picked.setVisibility(View.GONE);
        //origin.setVisibility(View.GONE);
        //要先把所有按鈕設定不能按 by周辰陽
        up.setEnabled(false);
        down.setEnabled(false);
        left.setEnabled(false);
        right.setEnabled(false);
        rotate.setEnabled(false);
        blue.setEnabled(false);
        red.setEnabled(false);
        yellow.setEnabled(false);

        //判定有沒有這顏色的門 by周辰陽
        for(int a= 0;a<4;a++)
        {
            if(door[plateX][plateY].charAt(a)=='B')
            {
                blue.setEnabled(true);
            }
            else if(door[plateX][plateY].charAt(a) == 'R') {
                red.setEnabled(true);
            }
            else if(door[plateX][plateY].charAt(a) == 'Y') {
                yellow.setEnabled(true);
            }
        }

        // 按鈕事件
        // 變數
        int[] degree = {0};
        rotate.setOnClickListener(vRotate ->
        {
            degree[0] += 90;
            picked.setRotation(degree[0]);

            //旋轉字母
            //char first = allroomdoor[getnum].charAt(0);
            String ans = "";
            ans = ans + allroomdoor[getnum].charAt(3);
            ans = ans + allroomdoor[getnum].charAt(0);
            ans = ans + allroomdoor[getnum].charAt(1);
            ans = ans + allroomdoor[getnum].charAt(2);
            allroomdoor[getnum] = ans;
            textView.setText(ans);
            times++;
            check(up,down,left,right,getnum);
        });
        blue.setOnClickListener(vBlue -> {
            red.setVisibility(View.GONE);
            blue.setVisibility(View.GONE);
            yellow.setVisibility(View.GONE);
            picked.setVisibility(View.VISIBLE);
            origin.setVisibility(View.VISIBLE);
            //把旋轉和上下左右出現 by周辰陽
            /*for(int a= 0;a<4;a++)
            {
                if (door[plateX][plateY].charAt(a) == 'B')
                {
                    switch(a)
                    {
                        case 0:
                            up.setEnabled(true);
                            break;
                        case 1:
                            right.setEnabled(true);
                            break;
                        case 2:
                            down.setEnabled(true);
                            break;
                        case 3:
                            left.setEnabled(true);
                            break;

                    }
                }
            }*/
            rotate.setEnabled(true);
            while(true)
            {
                getnum = (int)(Math.random()*15);
                if(catacombopen[getnum] == 0)
                {
                    catacombopen[getnum] = 1;
                    picked.setImageResource(catacomb[getnum]);
                    getnum = 13 + getnum;
                    break;
                }
            }
            which = 0;
            check(up,down,left,right,getnum);
        });
        red.setOnClickListener(vRed -> {
            red.setVisibility(View.GONE);
            blue.setVisibility(View.GONE);
            yellow.setVisibility(View.GONE);
            picked.setVisibility(View.VISIBLE);
            origin.setVisibility(View.VISIBLE);
            //把旋轉和上下左右出現 by周辰陽
            /*for(int a= 0;a<4;a++)
            {
                if (door[plateX][plateY].charAt(a) == 'R')
                {
                    switch(a)
                    {
                        case 0:
                            up.setEnabled(true);
                            break;
                        case 1:
                            right.setEnabled(true);
                            break;
                        case 2:
                            down.setEnabled(true);
                            break;
                        case 3:
                            left.setEnabled(true);
                            break;
                    }
                }
            }*/
            rotate.setEnabled(true);
            while(true)
            {
                getnum = (int)(Math.random()*13);
                if(buildingopen[getnum] == 0)
                {
                    buildingopen[getnum] = 1;
                    picked.setImageResource(building[getnum]);
                    break;
                }
            }
            which = 1;
            check(up,down,left,right,getnum);
        });
        yellow.setOnClickListener(vYellow -> {
            red.setVisibility(View.GONE);
            blue.setVisibility(View.GONE);
            yellow.setVisibility(View.GONE);
            picked.setVisibility(View.VISIBLE);
            origin.setVisibility(View.VISIBLE);
            //把旋轉和上下左右出現 by周辰陽
            /*for(int a= 0;a<4;a++)
            {
                if (door[plateX][plateY].charAt(a) == 'Y')
                {
                    switch(a)
                    {
                        case 0:
                            up.setEnabled(true);
                            break;
                        case 1:
                            right.setEnabled(true);
                            break;
                        case 2:
                            down.setEnabled(true);
                            break;
                        case 3:
                            left.setEnabled(true);
                            break;
                    }
                }
            }*/
            rotate.setEnabled(true);
            while(true)
            {
                getnum = (int)(Math.random()*14);
                if(streetopen[getnum] == 0)
                {
                    streetopen[getnum] = 1;
                    picked.setImageResource(street[getnum]);
                    getnum = 28 + getnum;
                    break;
                }
            }
            which = 2;
            check(up,down,left,right,getnum);
        });
        up.setOnClickListener(vUp -> {
            //放置抽到的板塊
            Plate[plateX][plateY - 1].setImageResource(allroom[getnum]);
            Plate[plateX][plateY - 1].setRotation(degree[0]); // 剛剛新增用來選轉的
            door[plateX][plateY - 1] = allroomdoor[getnum];
            maproom[plateX][plateY-1] = getnum;

            // 封包內容
            openplate = true;
            newPlateThings = "1";
            newPlateThings += String.format("%02d", plateX);
            newPlateThings += String.format("%02d", plateY-1);
            newPlateThings += String.format("%02d", getnum);
            newPlateThings += String.format("%03d", degree[0]);
            newPlateThings += door[plateX][plateY-1] + door[plateX][plateY-2] + door[plateX][plateY] + door[plateX-1][plateY-1] + door[plateX+1][plateY-1];
            packetMaker();

            //改變抽到的板塊變數
            StringBuilder MyString = new StringBuilder(door[plateX][plateY - 1]);
            door[plateX][plateY - 1] = String.valueOf(MyString.deleteCharAt(2));
            StringBuffer sb = new StringBuffer(door[plateX][plateY - 1]);
            door[plateX][plateY - 1]  = String.valueOf(sb.insert(2, 'G'));

            //檢查新板塊的四周可不可以走
            if(door[plateX-1][plateY-1].charAt(1) == 'N')
            {
                MyString = new StringBuilder(door[plateX][plateY-1]);
                door[plateX][plateY - 1] = String.valueOf(MyString.deleteCharAt(3));
                sb = new StringBuffer(door[plateX][plateY-1]);
                door[plateX][plateY - 1] = String.valueOf(sb.insert(3, 'N'));
            }
            if(door[plateX+1][plateY-1].charAt(3) == 'N')
            {
                MyString = new StringBuilder(door[plateX][plateY-1]);
                door[plateX][plateY - 1] = String.valueOf(MyString.deleteCharAt(1));
                sb = new StringBuffer(door[plateX][plateY-1]);
                door[plateX][plateY - 1] = String.valueOf(sb.insert(1, 'N'));
            }
            if(door[plateX][plateY-2].charAt(2) == 'N')
            {
                MyString = new StringBuilder(door[plateX][plateY-1]);
                door[plateX][plateY - 1] = String.valueOf(MyString.deleteCharAt(0));
                sb = new StringBuffer(door[plateX][plateY-1]);
                door[plateX][plateY - 1] = String.valueOf(sb.insert(0, 'N'));
            }

            //檢查新板塊有沒有被封住的地方
            if(door[plateX][plateY - 1].charAt(0) == 'N')
            {
                MyString = new StringBuilder(door[plateX][plateY-2]);
                door[plateX][plateY-2] = String.valueOf(MyString.deleteCharAt(2));
                sb = new StringBuffer(door[plateX][plateY-2]);
                door[plateX][plateY-2] = String.valueOf(sb.insert(2, 'N'));
            }
            else if(door[plateX][plateY-2].charAt(2) != 'U' && door[plateX][plateY-2].charAt(2) != 'N')
            {
                MyString = new StringBuilder(door[plateX][plateY-2]);
                door[plateX][plateY-2] = String.valueOf(MyString.deleteCharAt(2));
                sb = new StringBuffer(door[plateX][plateY-2]);
                door[plateX][plateY-2] = String.valueOf(sb.insert(2, 'G'));

                MyString = new StringBuilder(door[plateX][plateY-1]);
                door[plateX][plateY-1] = String.valueOf(MyString.deleteCharAt(0));
                sb = new StringBuffer(door[plateX][plateY-1]);
                door[plateX][plateY-1] = String.valueOf(sb.insert(0, 'G'));
            }
            if(door[plateX][plateY - 1].charAt(1) == 'N')
            {
                MyString = new StringBuilder(door[plateX+1][plateY-1]);
                door[plateX+1][plateY-1] = String.valueOf(MyString.deleteCharAt(3));
                sb = new StringBuffer(door[plateX+1][plateY-1]);
                door[plateX+1][plateY-1] = String.valueOf(sb.insert(3, 'N'));
            }
            else if(door[plateX+1][plateY-1].charAt(3) != 'U' && door[plateX+1][plateY-1].charAt(3) != 'N')
            {
                MyString = new StringBuilder(door[plateX+1][plateY-1]);
                door[plateX+1][plateY-1] = String.valueOf(MyString.deleteCharAt(3));
                sb = new StringBuffer(door[plateX+1][plateY-1]);
                door[plateX+1][plateY-1] = String.valueOf(sb.insert(3, 'G'));

                MyString = new StringBuilder(door[plateX][plateY-1]);
                door[plateX][plateY-1] = String.valueOf(MyString.deleteCharAt(1));
                sb = new StringBuffer(door[plateX][plateY-1]);
                door[plateX][plateY-1] = String.valueOf(sb.insert(1, 'G'));
            }
            if(door[plateX][plateY - 1].charAt(3) == 'N')
            {
                MyString = new StringBuilder(door[plateX-1][plateY-1]);
                door[plateX-1][plateY-1] = String.valueOf(MyString.deleteCharAt(1));
                sb = new StringBuffer(door[plateX-1][plateY-1]);
                door[plateX-1][plateY-1] = String.valueOf(sb.insert(1, 'N'));
            }
            else if(door[plateX-1][plateY-1].charAt(1) != 'U' && door[plateX-1][plateY-1].charAt(1) != 'N')
            {
                MyString = new StringBuilder(door[plateX-1][plateY-1]);
                door[plateX-1][plateY-1] = String.valueOf(MyString.deleteCharAt(1));
                sb = new StringBuffer(door[plateX-1][plateY-1]);
                door[plateX-1][plateY-1] = String.valueOf(sb.insert(1, 'G'));

                MyString = new StringBuilder(door[plateX][plateY-1]);
                door[plateX][plateY-1] = String.valueOf(MyString.deleteCharAt(3));
                sb = new StringBuffer(door[plateX][plateY-1]);
                door[plateX][plateY-1] = String.valueOf(sb.insert(3, 'G'));
            }


            //改變該門的變數
            MyString = new StringBuilder(door[plateX][plateY]);
            door[plateX][plateY] = String.valueOf(MyString.deleteCharAt(0));
            sb = new StringBuffer(door[plateX][plateY]);
            door[plateX][plateY]  = String.valueOf(sb.insert(0, 'G'));

            happend(getnum);
            times = 0;
            dialog.dismiss();
        });
        down.setOnClickListener(vDown -> {
            //放置抽到的板塊
            Plate[plateX][plateY+1].setImageResource(allroom[getnum]);
            Plate[plateX][plateY+1].setRotation(degree[0]);
            door[plateX][plateY + 1] = allroomdoor[getnum];
            maproom[plateX][plateY+1] = getnum;

            // 封包內容
            openplate = true;
            newPlateThings = "1";
            newPlateThings += String.format("%02d", plateX);
            newPlateThings += String.format("%02d", plateY+1);
            newPlateThings += String.format("%02d", getnum);
            newPlateThings += String.format("%03d", degree[0]);
            newPlateThings += door[plateX][plateY+1] + door[plateX][plateY] + door[plateX][plateY+2] + door[plateX-1][plateY+1] + door[plateX+1][plateY+1];
            packetMaker();

            //改變抽到的板塊變數
            StringBuilder MyString = new StringBuilder(door[plateX][plateY+1]);
            door[plateX][plateY+1] = String.valueOf(MyString.deleteCharAt(0));
            StringBuffer sb = new StringBuffer(door[plateX][plateY+1]);
            door[plateX][plateY+1]  = String.valueOf(sb.insert(0, 'G'));

            //檢查新板塊的四周可不可以走
            if(door[plateX-1][plateY+1].charAt(1) == 'N')
            {
                MyString = new StringBuilder(door[plateX][plateY+1]);
                door[plateX][plateY+1] = String.valueOf(MyString.deleteCharAt(3));
                sb = new StringBuffer(door[plateX][plateY+1]);
                door[plateX][plateY+1] = String.valueOf(sb.insert(3, 'N'));
            }
            if(door[plateX+1][plateY+1].charAt(3) == 'N')
            {
                MyString = new StringBuilder(door[plateX][plateY+1]);
                door[plateX][plateY+1] = String.valueOf(MyString.deleteCharAt(1));
                sb = new StringBuffer(door[plateX][plateY+1]);
                door[plateX][plateY+1] = String.valueOf(sb.insert(1, 'N'));
            }
            if(door[plateX][plateY+2].charAt(0) == 'N')
            {
                MyString = new StringBuilder(door[plateX][plateY+1]);
                door[plateX][plateY+1] = String.valueOf(MyString.deleteCharAt(2));
                sb = new StringBuffer(door[plateX][plateY+1]);
                door[plateX][plateY+1] = String.valueOf(sb.insert(2, 'N'));
            }

            //檢查新板塊有沒有被封住的地方
            if(door[plateX][plateY +1].charAt(1) == 'N')
            {
                MyString = new StringBuilder(door[plateX+1][plateY+1]);
                door[plateX+1][plateY+1] = String.valueOf(MyString.deleteCharAt(3));
                sb = new StringBuffer(door[plateX+1][plateY+1]);
                door[plateX+1][plateY+1] = String.valueOf(sb.insert(3, 'N'));
            }
            else if(door[plateX+1][plateY+1].charAt(3) != 'U' && door[plateX+1][plateY+1].charAt(3) != 'N')
            {
                MyString = new StringBuilder(door[plateX][plateY +1]);
                door[plateX][plateY +1] = String.valueOf(MyString.deleteCharAt(1));
                sb = new StringBuffer(door[plateX][plateY +1]);
                door[plateX][plateY +1] = String.valueOf(sb.insert(1, 'G'));

                MyString = new StringBuilder(door[plateX+1][plateY+1]);
                door[plateX+1][plateY+1] = String.valueOf(MyString.deleteCharAt(3));
                sb = new StringBuffer(door[plateX+1][plateY+1]);
                door[plateX+1][plateY+1] = String.valueOf(sb.insert(3, 'G'));
            }
            if(door[plateX][plateY+1].charAt(2) == 'N')
            {
                MyString = new StringBuilder(door[plateX][plateY+2]);
                door[plateX][plateY+2] = String.valueOf(MyString.deleteCharAt(0));
                sb = new StringBuffer(door[plateX][plateY+2]);
                door[plateX][plateY+2] = String.valueOf(sb.insert(0, 'N'));
            }
            else if(door[plateX][plateY+2].charAt(0) != 'U' && door[plateX][plateY+2].charAt(0) != 'N')
            {
                MyString = new StringBuilder(door[plateX][plateY +1]);
                door[plateX][plateY +1] = String.valueOf(MyString.deleteCharAt(2));
                sb = new StringBuffer(door[plateX][plateY +1]);
                door[plateX][plateY +1] = String.valueOf(sb.insert(2, 'G'));

                MyString = new StringBuilder(door[plateX][plateY+2]);
                door[plateX][plateY+2] = String.valueOf(MyString.deleteCharAt(0));
                sb = new StringBuffer(door[plateX][plateY+2]);
                door[plateX][plateY+2] = String.valueOf(sb.insert(0, 'G'));
            }
            if(door[plateX][plateY+1].charAt(3) == 'N')
            {
                MyString = new StringBuilder(door[plateX-1][plateY+1]);
                door[plateX-1][plateY+1] = String.valueOf(MyString.deleteCharAt(1));
                sb = new StringBuffer(door[plateX-1][plateY+1]);
                door[plateX-1][plateY+1] = String.valueOf(sb.insert(1, 'N'));
            }
            else if(door[plateX-1][plateY+1].charAt(1) != 'U' && door[plateX-1][plateY+1].charAt(1) != 'N')
            {
                MyString = new StringBuilder(door[plateX][plateY +1]);
                door[plateX][plateY +1] = String.valueOf(MyString.deleteCharAt(3));
                sb = new StringBuffer(door[plateX][plateY +1]);
                door[plateX][plateY +1] = String.valueOf(sb.insert(3, 'G'));

                MyString = new StringBuilder(door[plateX-1][plateY+1]);
                door[plateX-1][plateY+1] = String.valueOf(MyString.deleteCharAt(1));
                sb = new StringBuffer(door[plateX-1][plateY+1]);
                door[plateX-1][plateY+1] = String.valueOf(sb.insert(1, 'G'));
            }

            //改變該門的變數
            MyString = new StringBuilder(door[plateX][plateY]);
            door[plateX][plateY] = String.valueOf(MyString.deleteCharAt(2));
            sb = new StringBuffer(door[plateX][plateY]);
            door[plateX][plateY]  = String.valueOf(sb.insert(2, 'G'));

            happend(getnum);
            times = 0;
            dialog.dismiss();
        });
        left.setOnClickListener(vDown -> {
            //放置抽到的板塊
            Plate[plateX-1][plateY].setImageResource(allroom[getnum]);
            Plate[plateX-1][plateY].setRotation(degree[0]);
            door[plateX-1][plateY] = allroomdoor[getnum];
            maproom[plateX-1][plateY] = getnum;

            // 封包內容
            openplate = true;
            newPlateThings = "1";
            newPlateThings += String.format("%02d", plateX-1);
            newPlateThings += String.format("%02d", plateY);
            newPlateThings += String.format("%02d", getnum);
            newPlateThings += String.format("%03d", degree[0]);
            newPlateThings += door[plateX-1][plateY] + door[plateX-1][plateY-1] + door[plateX-1][plateY+1] + door[plateX-2][plateY] + door[plateX][plateY];
            packetMaker();

            //改變抽到的板塊變數
            StringBuilder MyString = new StringBuilder(door[plateX-1][plateY]);
            door[plateX-1][plateY] = String.valueOf(MyString.deleteCharAt(1));
            StringBuffer sb = new StringBuffer(door[plateX-1][plateY]);
            door[plateX-1][plateY]  = String.valueOf(sb.insert(1, 'G'));

            //檢查新板塊的四周可不可以走
            if(door[plateX-1][plateY-1].charAt(2) == 'N')
            {
                MyString = new StringBuilder(door[plateX-1][plateY]);
                door[plateX-1][plateY] = String.valueOf(MyString.deleteCharAt(0));
                sb = new StringBuffer(door[plateX-1][plateY]);
                door[plateX-1][plateY] = String.valueOf(sb.insert(0, 'N'));
            }
            if(door[plateX-1][plateY+1].charAt(0) == 'N')
            {
                MyString = new StringBuilder(door[plateX-1][plateY]);
                door[plateX-1][plateY] = String.valueOf(MyString.deleteCharAt(2));
                sb = new StringBuffer(door[plateX-1][plateY]);
                door[plateX-1][plateY] = String.valueOf(sb.insert(2, 'N'));
            }
            if(door[plateX-2][plateY].charAt(1) == 'N')
            {
                MyString = new StringBuilder(door[plateX-1][plateY]);
                door[plateX-1][plateY] = String.valueOf(MyString.deleteCharAt(3));
                sb = new StringBuffer(door[plateX-1][plateY]);
                door[plateX-1][plateY] = String.valueOf(sb.insert(3, 'N'));
            }

            //檢查新板塊有沒有被封住的地方
            if(door[plateX-1][plateY].charAt(0) == 'N')
            {
                MyString = new StringBuilder(door[plateX-1][plateY-1]);
                door[plateX-1][plateY-1] = String.valueOf(MyString.deleteCharAt(2));
                sb = new StringBuffer(door[plateX-1][plateY-1]);
                door[plateX-1][plateY-1] = String.valueOf(sb.insert(2, 'N'));
            }
            else if(door[plateX-1][plateY-1].charAt(2) != 'U' && door[plateX-1][plateY-1].charAt(2) != 'N')
            {
                MyString = new StringBuilder(door[plateX-1][plateY]);
                door[plateX-1][plateY] = String.valueOf(MyString.deleteCharAt(0));
                sb = new StringBuffer(door[plateX-1][plateY]);
                door[plateX-1][plateY] = String.valueOf(sb.insert(0, 'G'));

                MyString = new StringBuilder(door[plateX-1][plateY-1]);
                door[plateX-1][plateY-1] = String.valueOf(MyString.deleteCharAt(2));
                sb = new StringBuffer(door[plateX-1][plateY-1]);
                door[plateX-1][plateY-1] = String.valueOf(sb.insert(2, 'G'));
            }
            if(door[plateX-1][plateY].charAt(2) == 'N')
            {
                MyString = new StringBuilder(door[plateX-1][plateY+1]);
                door[plateX-1][plateY+1] = String.valueOf(MyString.deleteCharAt(0));
                sb = new StringBuffer(door[plateX-1][plateY+1]);
                door[plateX-1][plateY+1] = String.valueOf(sb.insert(0, 'N'));
            }
            else if(door[plateX-1][plateY+1].charAt(0) != 'U' && door[plateX-1][plateY+1].charAt(0) != 'N')
            {
                MyString = new StringBuilder(door[plateX-1][plateY]);
                door[plateX-1][plateY] = String.valueOf(MyString.deleteCharAt(2));
                sb = new StringBuffer(door[plateX-1][plateY]);
                door[plateX-1][plateY] = String.valueOf(sb.insert(2, 'G'));

                MyString = new StringBuilder(door[plateX-1][plateY+1]);
                door[plateX-1][plateY+1] = String.valueOf(MyString.deleteCharAt(0));
                sb = new StringBuffer(door[plateX-1][plateY+1]);
                door[plateX-1][plateY+1] = String.valueOf(sb.insert(0, 'G'));
            }
            if(door[plateX-1][plateY].charAt(3) == 'N')
            {
                MyString = new StringBuilder(door[plateX-2][plateY]);
                door[plateX-2][plateY] = String.valueOf(MyString.deleteCharAt(1));
                sb = new StringBuffer(door[plateX-2][plateY]);
                door[plateX-2][plateY] = String.valueOf(sb.insert(1, 'N'));
            }
            else if(door[plateX-2][plateY].charAt(1) != 'U' && door[plateX-2][plateY].charAt(1) != 'N')
            {
                MyString = new StringBuilder(door[plateX-1][plateY]);
                door[plateX-1][plateY] = String.valueOf(MyString.deleteCharAt(3));
                sb = new StringBuffer(door[plateX-1][plateY]);
                door[plateX-1][plateY] = String.valueOf(sb.insert(3, 'G'));

                MyString = new StringBuilder(door[plateX-2][plateY]);
                door[plateX-2][plateY] = String.valueOf(MyString.deleteCharAt(1));
                sb = new StringBuffer(door[plateX-2][plateY]);
                door[plateX-2][plateY] = String.valueOf(sb.insert(1, 'G'));
            }

            //改變該門的變數
            MyString = new StringBuilder(door[plateX][plateY]);
            door[plateX][plateY] = String.valueOf(MyString.deleteCharAt(3));
            sb = new StringBuffer(door[plateX][plateY]);
            door[plateX][plateY]  = String.valueOf(sb.insert(3, 'G'));

            happend(getnum);
            times = 0;
            dialog.dismiss();
        });
        right.setOnClickListener(vRight -> {
            //放置抽到的板塊
            Plate[plateX+1][plateY].setImageResource(allroom[getnum]);
            Plate[plateX+1][plateY].setRotation(degree[0]);
            door[plateX+1][plateY] = allroomdoor[getnum];
            maproom[plateX+1][plateY] = getnum;

            // 封包內容
            openplate = true;
            newPlateThings = "1";
            newPlateThings += String.format("%02d", plateX+1);
            newPlateThings += String.format("%02d", plateY);
            newPlateThings += String.format("%02d", getnum);
            newPlateThings += String.format("%03d", degree[0]);
            newPlateThings += door[plateX+1][plateY] + door[plateX+1][plateY-1] + door[plateX+1][plateY+1] + door[plateX][plateY] + door[plateX+2][plateY];
            packetMaker();

            //改變抽到的板塊變數
            StringBuilder MyString = new StringBuilder(door[plateX+1][plateY]);
            door[plateX+1][plateY] = String.valueOf(MyString.deleteCharAt(3));
            StringBuffer sb = new StringBuffer(door[plateX+1][plateY]);
            door[plateX+1][plateY]  = String.valueOf(sb.insert(3, 'G'));

            //檢查新板塊的四周可不可以走
            if(door[plateX+1][plateY-1].charAt(2) == 'N')
            {
                MyString = new StringBuilder(door[plateX+1][plateY]);
                door[plateX+1][plateY] = String.valueOf(MyString.deleteCharAt(0));
                sb = new StringBuffer(door[plateX+1][plateY]);
                door[plateX+1][plateY] = String.valueOf(sb.insert(0, 'N'));
            }
            if(door[plateX+2][plateY].charAt(3) == 'N')
            {
                MyString = new StringBuilder(door[plateX+1][plateY]);
                door[plateX+1][plateY] = String.valueOf(MyString.deleteCharAt(1));
                sb = new StringBuffer(door[plateX+1][plateY]);
                door[plateX+1][plateY] = String.valueOf(sb.insert(1, 'N'));
            }
            if(door[plateX+1][plateY+1].charAt(0) == 'N')
            {
                MyString = new StringBuilder(door[plateX+1][plateY]);
                door[plateX+1][plateY] = String.valueOf(MyString.deleteCharAt(2));
                sb = new StringBuffer(door[plateX+1][plateY]);
                door[plateX+1][plateY] = String.valueOf(sb.insert(2, 'N'));
            }

            //檢查新板塊有沒有被封住的地方
            if(door[plateX+1][plateY].charAt(0) == 'N')
            {
                MyString = new StringBuilder(door[plateX+1][plateY-1]);
                door[plateX+1][plateY-1] = String.valueOf(MyString.deleteCharAt(2));
                sb = new StringBuffer(door[plateX+1][plateY-1]);
                door[plateX+1][plateY-1] = String.valueOf(sb.insert(2, 'N'));
            }
            else if(door[plateX+1][plateY-1].charAt(2) != 'U' && door[plateX+1][plateY-1].charAt(2) != 'N')
            {
                MyString = new StringBuilder(door[plateX+1][plateY]);
                door[plateX+1][plateY] = String.valueOf(MyString.deleteCharAt(0));
                sb = new StringBuffer(door[plateX+1][plateY]);
                door[plateX+1][plateY] = String.valueOf(sb.insert(0, 'G'));

                MyString = new StringBuilder(door[plateX+1][plateY-1]);
                door[plateX+1][plateY-1] = String.valueOf(MyString.deleteCharAt(2));
                sb = new StringBuffer(door[plateX+1][plateY-1]);
                door[plateX+1][plateY-1] = String.valueOf(sb.insert(2, 'G'));
            }
            if(door[plateX+1][plateY].charAt(1) == 'N')
            {
                MyString = new StringBuilder(door[plateX+2][plateY]);
                door[plateX+2][plateY] = String.valueOf(MyString.deleteCharAt(3));
                sb = new StringBuffer(door[plateX+2][plateY]);
                door[plateX+2][plateY] = String.valueOf(sb.insert(3, 'N'));
            }
            else if(door[plateX+2][plateY].charAt(3) != 'U' &&  door[plateX+2][plateY].charAt(3) != 'N')
            {
                MyString = new StringBuilder(door[plateX+1][plateY]);
                door[plateX+1][plateY] = String.valueOf(MyString.deleteCharAt(1));
                sb = new StringBuffer(door[plateX+1][plateY]);
                door[plateX+1][plateY] = String.valueOf(sb.insert(1, 'G'));

                MyString = new StringBuilder(door[plateX+2][plateY]);
                door[plateX+2][plateY] = String.valueOf(MyString.deleteCharAt(3));
                sb = new StringBuffer(door[plateX+2][plateY]);
                door[plateX+2][plateY] = String.valueOf(sb.insert(3, 'G'));
            }
            if(door[plateX+1][plateY].charAt(2) == 'N')
            {
                MyString = new StringBuilder(door[plateX+1][plateY+1]);
                door[plateX+1][plateY+1] = String.valueOf(MyString.deleteCharAt(0));
                sb = new StringBuffer(door[plateX+1][plateY+1]);
                door[plateX+1][plateY+1] = String.valueOf(sb.insert(0, 'N'));
            }
            else if(door[plateX+1][plateY+1].charAt(0) != 'U' &&  door[plateX+1][plateY+1].charAt(0) != 'N')
            {
                MyString = new StringBuilder(door[plateX+1][plateY]);
                door[plateX+1][plateY] = String.valueOf(MyString.deleteCharAt(2));
                sb = new StringBuffer(door[plateX+1][plateY]);
                door[plateX+1][plateY] = String.valueOf(sb.insert(2, 'G'));

                MyString = new StringBuilder(door[plateX+1][plateY+1]);
                door[plateX+1][plateY+1] = String.valueOf(MyString.deleteCharAt(0));
                sb = new StringBuffer(door[plateX+1][plateY+1]);
                door[plateX+1][plateY+1] = String.valueOf(sb.insert(0, 'G'));
            }

            //改變該門的變數
            MyString = new StringBuilder(door[plateX][plateY]);
            door[plateX][plateY] = String.valueOf(MyString.deleteCharAt(1));
            sb = new StringBuffer(door[plateX][plateY]);
            door[plateX][plateY]  = String.valueOf(sb.insert(1, 'G'));

            happend(getnum);
            times = 0;
            dialog.dismiss();
        });
        dialog.show();
    }

    //判斷可不可以放
    public void check(ImageButton up,ImageButton down,ImageButton left,ImageButton right,int getnum) {
        switch (which)
        {
            case 0:
                for(int a= 0;a<4;a++)
                {
                    if (door[plateX][plateY].charAt(a) == 'B')
                    {
                        switch(a)
                        {
                            case 0:
                                up.setEnabled(true);
                                break;
                            case 1:
                                right.setEnabled(true);
                                break;
                            case 2:
                                down.setEnabled(true);
                                break;
                            case 3:
                                left.setEnabled(true);
                                break;

                        }
                    }
                }
                break;
            case 1:
                for(int a= 0;a<4;a++)
                {
                    if (door[plateX][plateY].charAt(a) == 'R')
                    {
                        switch(a)
                        {
                            case 0:
                                up.setEnabled(true);
                                break;
                            case 1:
                                right.setEnabled(true);
                                break;
                            case 2:
                                down.setEnabled(true);
                                break;
                            case 3:
                                left.setEnabled(true);
                                break;
                        }
                    }
                }
                break;
            case 2:
                for(int a= 0;a<4;a++)
                {
                    if (door[plateX][plateY].charAt(a) == 'Y')
                    {
                        switch(a)
                        {
                            case 0:
                                up.setEnabled(true);
                                break;
                            case 1:
                                right.setEnabled(true);
                                break;
                            case 2:
                                down.setEnabled(true);
                                break;
                            case 3:
                                left.setEnabled(true);
                                break;
                        }
                    }
                }
                break;
        }
        if(allroomdoor[getnum].charAt(0)=='N')
            down.setEnabled(false);
        if(allroomdoor[getnum].charAt(1)=='N')
            left.setEnabled(false);
        if(allroomdoor[getnum].charAt(2)=='N')
            up.setEnabled(false);
        if(allroomdoor[getnum].charAt(3)=='N')
            right.setEnabled(false);
    }

    //判斷該板塊有沒有事情發生 by周辰陽
    public void happend(int getnum) {
        switch(getnum)
        {
            //帶有事件的房間
            case 0: case 2: case 4: case 5:case 6:case 8:case 10:case 15: case 17: case 20:case 28:case 31:case 34:case 38:case 39:case 40: case 41:
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.event, null);
            // 宣告一個彈出視窗
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setView(v);
            // 顯示
            AlertDialog dialog = alert.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            // objects in XML
            ImageView card_type = v.findViewById(R.id.card_type);
            TextView Title = v.findViewById(R.id.title);
            TextView body = v.findViewById(R.id.body);
            TextView body2 = v.findViewById(R.id.body2);
            Button close = v.findViewById(R.id.close);

            //關掉的按鈕
            close.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    dialog.dismiss();
                }
            });

            // *記得所有有關畫面的變更要在dialog.show()之前完成
            //幾號事件並呼叫 by周辰陽
            int num = 0;
            while(true)
            {
                num = (int)(Math.random()*35);
                if(event[num]==0)
                {
                    event[num] = 1;
                    break;
                }
            }
            Title.setText(eventname[num]);
            body.setText(eventword[num]);
            Event(num,body2);
            reset();
            dialog.show();
            break;
            //帶有物品的房間
            case 7: case 9: case 11: case 12: case 25: case 26: case 27: case 36:
            while (true)
            {
                if(takenitem == 17) //物品最多17樣 多了就不能拿了
                    break;
                int GetItem = (int)(Math.random()*17);
                if(item[GetItem] == 0)
                {
                    for(int a = 0 ; a<6;a++)
                    {
                        if(bag[a] == -1)
                        {
                            bag[a] = GetItem;
                            takenitem++;
                            item[GetItem] = 1;
                            break;
                        }
                    }
                    break;
                }
            }
            break;
        }
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
                    System.out.println(msgFromServer);

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
                            if (msgFromServer.charAt(1) - 48 == myClientID) {
                                roundOver = 0;
                                myRound = true;
                                runOnUiThread(() -> {
                                    RoundOver.setVisibility(View.VISIBLE);
                                });
                            } else {
                                runOnUiThread(() -> {
                                    RoundOver.setVisibility(View.GONE);
                                });
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
                        case '2':
                            // 1 ~ 5
                            for(int i=1;i<numberOfPlayer+1;i++){
                                if(msgFromServer.charAt(i) == '0'){
                                    alive[i] = false;
                                }
                            }
                            // 6~9, 10~13, 14~17, 18~21, 22~25
                            for (int i = 1; i < numberOfPlayer + 1; i++) {
                                character.chara[characterIndex[i]].currentMight =
                                        msgFromServer.charAt(4 * i + 2) - '0';
                                character.chara[characterIndex[i]].currentSpeed =
                                        msgFromServer.charAt(4 * i + 3) - '0';
                                character.chara[characterIndex[i]].currentSanity =
                                        msgFromServer.charAt(4 * i + 4) - '0';
                                character.chara[characterIndex[i]].currentKnowledge =
                                        msgFromServer.charAt(4 * i + 5) - '0';
                            }
                            break;
                        case '3':
                            if(msgFromServer.charAt(1) == '1'){
                                int x = Integer.parseInt(msgFromServer.substring(2,4));
                                int y = Integer.parseInt(msgFromServer.substring(4,6));
                                int id = Integer.parseInt(msgFromServer.substring(6,8));
                                int deg = Integer.parseInt(msgFromServer.substring(8,11));
                                System.out.println(x + " " + y + " " + deg);
                                maproom[x][y] = id;
                                runOnUiThread(()->{
                                    Plate[x][y].setImageResource(allroom[id]);
                                    Plate[x][y].setRotation(deg);
                                });
                                door[x][y] = msgFromServer.substring(11,15);
                                door[x][y-1] = msgFromServer.substring(15,19);
                                door[x][y+1] = msgFromServer.substring(19,23);
                                door[x-1][y] = msgFromServer.substring(23,27);
                                door[x+1][y] = msgFromServer.substring(27,31);
                                if(id < 13) buildingopen[id] = 1;
                                else if (id < 28) catacombopen[id-13] = 1;
                                else streetopen[id-28] = 1;
                            }
                            break;
                    }
                }
            }catch (IOException e){} // 就算拋出例外我也沒辦法處裡
        }
    }

    //把畫面全螢幕 by周辰陽
    protected void onResume()
    {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    //發生的事件判定與文本
    public void Event(int num,TextView context2)
    {
        int GoDice = 0;
        int choose = 0;
        switch(num)
        {
            case 0:
                GoDice = dice(character.chara[characterIndex[myClientID]].Knowledge[character.chara[characterIndex[myClientID]].currentKnowledge]);
                if(GoDice>=4)
                {
                    context2.setText("你仔細一看，原來這是嘉年華季！取得 1 級知識。");
                    character.chara[characterIndex[myClientID]].currentKnowledge++;
                }
                else if(GoDice>=2)
                {
                    context2.setText("你仔細一看，這雕像看起來很平凡，沒有事情發生。");
                }
                else
                {
                    context2.setText("你仔細一看，你最後記得的事是有一個雕像開始移 動了，受到 1 級精神傷害");
                    if(character.chara[characterIndex[myClientID]].currentKnowledge>character.chara[characterIndex[myClientID]].currentSanity)
                        character.chara[characterIndex[myClientID]].currentKnowledge--;
                    else
                        character.chara[characterIndex[myClientID]].currentSanity--;
                }
                break;
            case 1:
                GoDice = dice(character.chara[characterIndex[myClientID]].Sanity[character.chara[characterIndex[myClientID]].currentSanity]);
                if(GoDice >= 5)
                {
                    context2.setText("只是星體的投射影像而已取得 1 級 心智及 1 級知識。");
                    character.chara[characterIndex[myClientID]].currentSanity++;
                    character.chara[characterIndex[myClientID]].currentKnowledge++;
                }
                else if(GoDice >= 2)
                {
                    context2.setText("這觸手並不是真的，但它震懾到你的心靈，失去 1 級心智");
                    character.chara[characterIndex[myClientID]].currentSanity--;
                }
                else
                {
                    context2.setText("觸手刺穿了你的心靈及身體，失去 1 級力量及 1 級心智。");
                    character.chara[characterIndex[myClientID]].currentMight--;
                    character.chara[characterIndex[myClientID]].currentSanity--;
                }
                break;
            case 2:
                GoDice = dice(character.chara[characterIndex[myClientID]].Sanity[character.chara[characterIndex[myClientID]].currentSanity]);
                if(GoDice >=6 )
                {
                    context2.setText("你戰勝了惡魔，取得 1 級最低屬性。");
                    for(int a= 1;a<9;a++)
                    {
                        if(a==character.chara[characterIndex[myClientID]].currentSpeed)
                        {
                            character.chara[characterIndex[myClientID]].currentSpeed++;
                            break;
                        }
                        if(a==character.chara[characterIndex[myClientID]].currentMight)
                        {
                            character.chara[characterIndex[myClientID]].currentMight++;
                            break;
                        }
                        if(a==character.chara[characterIndex[myClientID]].currentSanity)
                        {
                            character.chara[characterIndex[myClientID]].currentSanity++;
                            break;
                        }
                        if(a==character.chara[characterIndex[myClientID]].currentKnowledge)
                        {
                            character.chara[characterIndex[myClientID]].currentKnowledge++;
                            break;
                        }
                    }
                }
                else if(GoDice>=3)
                {
                    context2.setText("你們在戰鬥中打平，沒有事情發生。");
                }
                else
                {
                    context2.setText("他拿走了你一部份的心靈，失去 1 級心智。");
                    character.chara[characterIndex[myClientID]].currentSanity--;
                }
                break;
            case 3:
                GoDice = dice(
                        character.chara[characterIndex[myClientID]].Sanity[character.chara[characterIndex[myClientID]].currentSanity]);
                if(GoDice>=4)
                {
                    context2.setText("你戰勝了鼠群並掠奪了牠們的心靈，取得 1 級心智。");
                    character.chara[characterIndex[myClientID]].currentSanity++;
                }
                else if(GoDice>=2)
                {
                    context2.setText("牠們找到了牠們要找的東西，失去 1 級知識。");
                    character.chara[characterIndex[myClientID]].currentKnowledge--;
                }
                else
                {
                    context2.setText(" 你對這突襲毫無準備，受到 1 顆骰的精神傷害。");
                    if(character.chara[characterIndex[myClientID]].currentKnowledge>character.chara[characterIndex[myClientID]].currentSanity)
                        character.chara[characterIndex[myClientID]].currentKnowledge = character.chara[characterIndex[myClientID]].currentKnowledge - dice(1);
                    else
                        character.chara[characterIndex[myClientID]].currentSanity = character.chara[characterIndex[myClientID]].currentSanity - dice(1);
                }
                break;
            case 4:
                GoDice = dice(character.chara[characterIndex[myClientID]].Knowledge[character.chara[characterIndex[myClientID]].currentKnowledge]);
                if(GoDice >= 5)
                {
                    context2.setText("有關物品的還沒做好");
                }
                else if(GoDice >= 3)
                {
                    context2.setText("你注意到這符文即將爆炸了，你用物品將爆裂物覆蓋住。");
                }
                else
                {
                    context2.setText("你無視這些不重要的文字，結果它在半空中爆炸，受到 1 顆骰的物理傷害。");
                    if(character.chara[characterIndex[myClientID]].currentSpeed>character.chara[characterIndex[myClientID]].currentMight)
                        character.chara[characterIndex[myClientID]].currentSpeed = character.chara[characterIndex[myClientID]].currentSpeed - dice(1);
                    else
                        character.chara[characterIndex[myClientID]].currentMight = character.chara[characterIndex[myClientID]].currentMight - dice(1);
                }
                break;
            case 5:
                for(int a= 8;a>0;a--)
                {
                    if(a==character.chara[characterIndex[myClientID]].currentSpeed)
                    {
                        GoDice = dice(character.chara[characterIndex[myClientID]].Speed[character.chara[characterIndex[myClientID]].currentSpeed]);
                        choose = 1;
                        break;
                    }
                    if(a==character.chara[characterIndex[myClientID]].currentMight)
                    {
                        GoDice = dice(character.chara[characterIndex[myClientID]].Might[character.chara[characterIndex[myClientID]].currentMight]);
                        choose = 2;
                        break;
                    }
                    if(a==character.chara[characterIndex[myClientID]].currentSanity)
                    {
                        GoDice = dice(character.chara[characterIndex[myClientID]].Sanity[character.chara[characterIndex[myClientID]].currentSanity]);
                        choose = 3;
                        break;
                    }
                    if(a==character.chara[characterIndex[myClientID]].currentKnowledge)
                    {
                        GoDice = dice(character.chara[characterIndex[myClientID]].Knowledge[character.chara[characterIndex[myClientID]].currentKnowledge]);
                        choose = 4;
                        break;
                    }
                }
                if(GoDice >= 4)
                {
                    context2.setText("你抵抗了誘惑，取得 1 級你檢定的屬性。");
                    switch (choose)
                    {
                        case 1 :
                            character.chara[characterIndex[myClientID]].currentSpeed++;
                            break;
                        case 2 :
                            character.chara[characterIndex[myClientID]].currentMight++;
                            break;
                        case 3 :
                            character.chara[characterIndex[myClientID]].currentSanity++;
                            break;
                        case 4 :
                            character.chara[characterIndex[myClientID]].currentKnowledge++;
                            break;
                    }
                }
                else if(GoDice>= 1)
                {
                    context2.setText("你認為很不值得，失去 1 級你檢定的屬性。");
                    switch (choose)
                    {
                        case 1 :
                            character.chara[characterIndex[myClientID]].currentSpeed--;
                            break;
                        case 2 :
                            character.chara[characterIndex[myClientID]].currentMight--;
                            break;
                        case 3 :
                            character.chara[characterIndex[myClientID]].currentSanity--;
                            break;
                        case 4 :
                            character.chara[characterIndex[myClientID]].currentKnowledge--;
                            break;
                    }
                }
                else
                {
                    context2.setText("你們達成了協議，所選的屬性降到最低值");
                    switch (choose)
                    {
                        case 1 :
                            character.chara[characterIndex[myClientID]].currentSpeed = 1;
                            break;
                        case 2 :
                            character.chara[characterIndex[myClientID]].currentMight = 1;
                            break;
                        case 3 :
                            character.chara[characterIndex[myClientID]].currentSanity = 1;
                            break;
                        case 4 :
                            character.chara[characterIndex[myClientID]].currentKnowledge = 1;
                            break;
                    }
                }
                break;
            case 6:
                GoDice = dice(2);
                if(GoDice>=4)
                {
                    context2.setText("有關物品的還沒做好");
                }
                else if(GoDice>= 2)
                {
                    context2.setText("它已經被掠奪過了，沒找到任何東西。");
                }
                else
                {
                    context2.setText("這寶箱是一個寶箱怪！而且它試著 扯下你的手臂！受到 1 顆骰的物理傷害。");
                    if(character.chara[characterIndex[myClientID]].currentSpeed>character.chara[characterIndex[myClientID]].currentMight)
                        character.chara[characterIndex[myClientID]].currentSpeed = character.chara[characterIndex[myClientID]].currentSpeed - dice(1);
                    else
                        character.chara[characterIndex[myClientID]].currentMight = character.chara[characterIndex[myClientID]].currentMight - dice(1);
                }
                break;
            case 7:
                GoDice = dice(character.chara[characterIndex[myClientID]].Knowledge[character.chara[characterIndex[myClientID]].currentKnowledge]);
                if(GoDice>=4)
                {
                    context2.setText("這是吸血鬼迷霧，快跑！取得 1 級速度。");
                    character.chara[characterIndex[myClientID]].currentSpeed++;
                }
                else
                {
                    context2.setText("它應該只會殺掉老鼠吧，你一邊這麼 說一邊走了進去，受到 1 級物理傷害。");
                    if(character.chara[characterIndex[myClientID]].currentSpeed>character.chara[characterIndex[myClientID]].currentMight)
                        character.chara[characterIndex[myClientID]].currentSpeed--;
                    else
                        character.chara[characterIndex[myClientID]].currentMight--;
                }
                break;
            case 8:
                if(character.chara[characterIndex[myClientID]].currentSpeed>=character.chara[characterIndex[myClientID]].currentSanity)
                {
                    GoDice = dice(character.chara[characterIndex[myClientID]].Speed[character.chara[characterIndex[myClientID]].currentSpeed]);
                    choose = 1;
                }
                else
                {
                    GoDice = dice(character.chara[characterIndex[myClientID]].Sanity[character.chara[characterIndex[myClientID]].currentSanity]);
                    choose = 2;
                }
                if(GoDice>=5)
                {
                    context2.setText("你戰勝恐慌，取得 1 級檢定屬性。");
                    if(choose == 1)
                        character.chara[characterIndex[myClientID]].currentSpeed++;
                    else if(choose == 2)
                        character.chara[characterIndex[myClientID]].currentSanity++;
                }
                else if(GoDice>=3)
                {
                    context2.setText("你因恐慌而大聲尖叫，受到 1 顆骰的精神傷害。");
                    if(character.chara[characterIndex[myClientID]].currentKnowledge>character.chara[characterIndex[myClientID]].currentSanity)
                        character.chara[characterIndex[myClientID]].currentKnowledge = character.chara[characterIndex[myClientID]].currentKnowledge - dice(1);
                    else
                        character.chara[characterIndex[myClientID]].currentSanity = character.chara[characterIndex[myClientID]].currentSanity - dice(1);
                }
                else
                {
                    context2.setText("你會感到恐慌是非常合理的，當你拿著匕首想把腐蛆挖出來時，你受到 1 顆骰的物理傷害。");
                    if(character.chara[characterIndex[myClientID]].currentSpeed>character.chara[characterIndex[myClientID]].currentMight)
                        character.chara[characterIndex[myClientID]].currentSpeed = character.chara[characterIndex[myClientID]].currentSpeed - dice(1);
                    else
                        character.chara[characterIndex[myClientID]].currentMight = character.chara[characterIndex[myClientID]].currentMight - dice(1);
                }
                break;
            case 9:
                GoDice = dice(character.chara[characterIndex[myClientID]].Might[character.chara[characterIndex[myClientID]].currentMight]);
                if(GoDice>=4)
                {
                    context2.setText("你讓這陣風成為你的優勢，取得 1 級速度。");
                    character.chara[characterIndex[myClientID]].currentSpeed++;
                }
                else if(GoDice>=2)
                {
                    context2.setText("這個元素連續打擊著你，受到 1 級物理傷害。");
                    if(character.chara[characterIndex[myClientID]].currentMight>character.chara[characterIndex[myClientID]].currentSpeed)
                        character.chara[characterIndex[myClientID]].currentMight--;
                    else
                        character.chara[characterIndex[myClientID]].currentSpeed--;
                }
                else if(GoDice>= 0)
                {
                    context2.setText("你被吸進了元素裡，受到 1 顆骰的物理傷害，你下回合只能走 1 步。");
                    if(character.chara[characterIndex[myClientID]].currentMight>character.chara[characterIndex[myClientID]].currentSpeed)
                        character.chara[characterIndex[myClientID]].currentMight--;
                    else
                        character.chara[characterIndex[myClientID]].currentSpeed--;
                    NextRoundMove = 1;
                }
                break;
            case 10:
                context2.setText("下一次棄掉物品時可以抽一張物品卡");
                DrawItem = true;
                break;
            case 11:
                context2.setText("受到 1 級物理傷害。");
                if(character.chara[characterIndex[myClientID]].currentSpeed>character.chara[characterIndex[myClientID]].currentMight)
                    character.chara[characterIndex[myClientID]].currentSpeed--;
                else
                    character.chara[characterIndex[myClientID]].currentMight--;
                break;
            case 12:
                GoDice = dice(1);
                if(GoDice>=2)
                {
                    context2.setText("有關物品的還沒做好");
                }
                else if(GoDice>=1)
                {
                    context2.setText("沒有事情發生。");
                }
                else
                {
                    context2.setText("你打破了一個壺，壺的擁有者手上拿著武器，打算好好的回報你，失去 1 級力量。");
                    character.chara[characterIndex[myClientID]].currentMight--;
                }
                break;
            case 13:
                GoDice = dice(character.chara[characterIndex[myClientID]].Sanity[character.chara[characterIndex[myClientID]].currentSanity]);
                if(GoDice>=3)
                {
                    context2.setText("你已經看過這多元宇宙中最糟糕的 一面，眼睛都沒眨一下。取得 1 級心智。");
                    character.chara[characterIndex[myClientID]].currentSanity++;
                }
                else
                {
                    context2.setText("鴉閣城的景象讓你感到恐懼，失去 1 級心智。");
                    character.chara[characterIndex[myClientID]].currentSanity--;
                }
                break;
            case 14:
                GoDice = dice(character.chara[characterIndex[myClientID]].Knowledge[character.chara[characterIndex[myClientID]].currentKnowledge]);
                if(GoDice>=5)
                {
                    context2.setText("你辨認出矮人的部落，並告訴鬼魂要 如何回家。取得 1 級知識。");
                    character.chara[characterIndex[myClientID]].currentKnowledge++;
                }
                else if(GoDice>=1)
                {
                    context2.setText("你無法幫助這位悲傷的幽靈，失去 1 級心智。");
                    character.chara[characterIndex[myClientID]].currentSanity--;
                }
                else
                {
                    context2.setText("幽靈附身在你身上，並盲目的驚慌奔走，受到 1 顆骰的精神傷害。");
                    if(character.chara[characterIndex[myClientID]].currentKnowledge>character.chara[characterIndex[myClientID]].currentSanity)
                        character.chara[characterIndex[myClientID]].currentKnowledge = character.chara[characterIndex[myClientID]].currentKnowledge - dice(1);
                    else
                        character.chara[characterIndex[myClientID]].currentSanity = character.chara[characterIndex[myClientID]].currentSanity - dice(1);
                }
                break;
            case 15:
                GoDice = dice(character.chara[characterIndex[myClientID]].Knowledge[character.chara[characterIndex[myClientID]].currentKnowledge]);
                if(GoDice>=6)
                {
                    context2.setText("有關物品的還沒做好");
                }
                else
                {
                    context2.setText("你沒找到任何東西。");
                }
                break;
            case 16:
                GoDice = dice(character.chara[characterIndex[myClientID]].Knowledge[character.chara[characterIndex[myClientID]].currentKnowledge]);
                if(GoDice>=4)
                {
                    context2.setText("有關物品的還沒做好");
                }
                else if(GoDice>=2)
                {
                    context2.setText("你發現了一個空洞，裡面充滿了蜘蛛並聚集到你的手臂上，失去 1 級力量。");
                    character.chara[characterIndex[myClientID]].currentMight--;
                }
                else
                {
                    context2.setText("你發現了一個狗頭人的洞穴，受到 1 顆骰的物理傷害。");
                    if(character.chara[characterIndex[myClientID]].currentSpeed>character.chara[characterIndex[myClientID]].currentMight)
                        character.chara[characterIndex[myClientID]].currentSpeed = character.chara[characterIndex[myClientID]].currentSpeed - dice(1);
                    else
                        character.chara[characterIndex[myClientID]].currentMight = character.chara[characterIndex[myClientID]].currentMight - dice(1);
                }
                break;
            case 17:
                GoDice = dice(character.chara[characterIndex[myClientID]].Might[character.chara[characterIndex[myClientID]].currentMight]);
                if(GoDice>=4)
                {
                    context2.setText("你掙脫開，並奮力一擊殺了設陷者，抽１張物品牌。");
                }
                else if(GoDice>=1)
                {
                    context2.setText("你從設陷者的纏繞中掙脫並逃開，受到 1 顆骰的物理傷害。");
                    if(character.chara[characterIndex[myClientID]].currentSpeed>character.chara[characterIndex[myClientID]].currentMight)
                        character.chara[characterIndex[myClientID]].currentSpeed = character.chara[characterIndex[myClientID]].currentSpeed - dice(1);
                    else
                        character.chara[characterIndex[myClientID]].currentMight = character.chara[characterIndex[myClientID]].currentMight - dice(1);
                }
                else
                {
                    context2.setText("你被困住了！受到 1 顆骰的物理傷害，立刻結束回合");
                    if(character.chara[characterIndex[myClientID]].currentSpeed>character.chara[characterIndex[myClientID]].currentMight)
                        character.chara[characterIndex[myClientID]].currentSpeed = character.chara[characterIndex[myClientID]].currentSpeed - dice(1);
                    else
                        character.chara[characterIndex[myClientID]].currentMight = character.chara[characterIndex[myClientID]].currentMight - dice(1);
                }
                break;
            case 18:
                int howmuch = 0;
                GoDice = dice(character.chara[characterIndex[myClientID]].Might[character.chara[characterIndex[myClientID]].currentMight]);
                if(GoDice>=2)
                    howmuch++;
                else
                    context2.setText("失去１級力量屬性。");
                GoDice = dice(character.chara[characterIndex[myClientID]].Speed[character.chara[characterIndex[myClientID]].currentSpeed]);
                if(GoDice>=2)
                    howmuch++;
                else
                    context2.setText("失去１級速度屬性。");
                GoDice = dice(character.chara[characterIndex[myClientID]].Knowledge[character.chara[characterIndex[myClientID]].currentKnowledge]);
                if(GoDice>=2)
                    howmuch++;
                else
                    context2.setText("失去１級知識屬性。");
                if(howmuch==3)
                {
                    context2.setText("你通過了考驗，取得 1 級最低的屬性。");
                    for(int a = 0;a<9;a++)
                    {
                        if(character.chara[characterIndex[myClientID]].currentMight == a)
                        {
                            character.chara[characterIndex[myClientID]].currentMight++;
                            break;
                        }
                        if(character.chara[characterIndex[myClientID]].currentSpeed == a)
                        {
                            character.chara[characterIndex[myClientID]].currentSpeed++;
                            break;
                        }
                        if(character.chara[characterIndex[myClientID]].currentKnowledge == a)
                        {
                            character.chara[characterIndex[myClientID]].currentKnowledge++;
                            break;
                        }
                    }
                }
                break;
            case 19:
                GoDice = dice(character.chara[characterIndex[myClientID]].Speed[character.chara[characterIndex[myClientID]].currentSpeed]);
                if(GoDice>=4)
                {
                    context2.setText("你閃避了暗影並逃離了他的追捕。");
                }
                else if(GoDice>=2)
                {
                    context2.setText("暗影擦過了你的腿，失去 1 級心智。");
                    character.chara[characterIndex[myClientID]].currentSanity--;
                }
                else
                {
                    context2.setText("暗影抓到了你，並釋放一股令人麻痺的冷風穿過你的下體，失去１級速度。");
                    character.chara[characterIndex[myClientID]].currentSpeed--;
                }
                break;
            case 20:
                GoDice = dice(character.chara[characterIndex[myClientID]].Speed[character.chara[characterIndex[myClientID]].currentSpeed]);
                if(GoDice>=5)
                {
                    context2.setText("你閃避鼠群，取得 1 級速度屬性。");
                    character.chara[characterIndex[myClientID]].currentSpeed++;
                }
                else if(GoDice>=2)
                {
                    context2.setText(" 鼠群擊倒了你，棄掉 1 張物品牌。");
                }
                else
                {
                    context2.setText("牠們太多了！失去 1 級速度屬性。");
                    character.chara[characterIndex[myClientID]].currentSpeed--;
                }
                break;
            case 21:
                GoDice = dice(character.chara[characterIndex[myClientID]].Speed[character.chara[characterIndex[myClientID]].currentSpeed]);
                if(GoDice>=4)
                {
                    context2.setText("你閃避了史萊姆。");
                }
                else
                {
                    context2.setText("一團史萊姆落到你身上，受到 1 顆骰的物理傷害。");
                    if(character.chara[characterIndex[myClientID]].currentSpeed>character.chara[characterIndex[myClientID]].currentMight)
                        character.chara[characterIndex[myClientID]].currentSpeed = character.chara[characterIndex[myClientID]].currentSpeed - dice(1);
                    else
                        character.chara[characterIndex[myClientID]].currentMight = character.chara[characterIndex[myClientID]].currentMight - dice(1);
                }
                break;
            case 22:
                GoDice = dice(character.chara[characterIndex[myClientID]].Might[character.chara[characterIndex[myClientID]].currentMight]);
                if(GoDice>=5)
                {
                    context2.setText("一小片毛皮阻礙了烏鴉，他的身體滾入了黑暗之中，你安全了。");
                }
                else if(GoDice>=2)
                {
                    context2.setText("打鬥結束了，但付出了一些代價，失去 1 級力量。");
                    character.chara[characterIndex[myClientID]].currentMight--;
                }
                else
                {
                    context2.setText("看來你需要一個眼罩！失去 1 級速度。");
                    character.chara[characterIndex[myClientID]].currentSpeed--;
                }
                break;
            case 23:
                context2.setText("獲得一件物品");
                break;
            case 24:
                GoDice = dice(character.chara[characterIndex[myClientID]].Speed[character.chara[characterIndex[myClientID]].currentSpeed]);
                if(GoDice>=4)
                {
                    context2.setText("你閃避了殭屍，並在它身上的黏液跟 膿胞爆開前跑得老遠，在殘骸中你發現了一個有用的物品，抽 1 張物品牌。");
                }
                else if(GoDice>=2)
                {
                    context2.setText("你試著逃離，但還是沐浴在殭屍爆破 後的血雨當中，失去 1 級速度。");
                    character.chara[characterIndex[myClientID]].currentSpeed--;
                }
                else
                {
                    context2.setText("殭屍抓住了你，並且把你炸得亂七八 糟。失去 1 級力量。");
                    character.chara[characterIndex[myClientID]].currentMight--;
                }
                break;
            case 25:
                context2.setText("你交給暴徒一件物品，隨機棄掉 1 張物品牌。");
                break;
            case 26:
                GoDice = dice(character.chara[characterIndex[myClientID]].Might[character.chara[characterIndex[myClientID]].currentMight]);
                if(GoDice>=4)
                {
                    context2.setText("微小的孢子無法傷害你。");
                }
                else if(GoDice>=1)
                {
                    context2.setText("咳嗽還會持續一段時間，失去 1 級力量。");
                    character.chara[characterIndex[myClientID]].currentMight--;
                }
                else
                {
                    context2.setText("你的傷更嚴重了。受到 1 顆骰的物理傷害。");
                    if(character.chara[characterIndex[myClientID]].currentSpeed>character.chara[characterIndex[myClientID]].currentMight)
                        character.chara[characterIndex[myClientID]].currentSpeed = character.chara[characterIndex[myClientID]].currentSpeed - dice(1);
                    else
                        character.chara[characterIndex[myClientID]].currentMight = character.chara[characterIndex[myClientID]].currentMight - dice(1);
                }
                break;
            case 27:
                GoDice = dice(character.chara[characterIndex[myClientID]].Knowledge[character.chara[characterIndex[myClientID]].currentKnowledge]);
                if(GoDice>=4)
                {
                    context2.setText("你抵抗了跟隨光芒前進的衝動，取得 1 級知識。");
                    character.chara[characterIndex[myClientID]].currentKnowledge++;
                }
                else if(GoDice>=2)
                {
                    context2.setText("你跟著鬼火繞圈圈，沒有發生什麼事情");
                }
                else
                {
                    context2.setText("你跟著鬼火帶著走撞到了牆，受到 1 顆骰的物理傷害。");
                    if(character.chara[characterIndex[myClientID]].currentSpeed>character.chara[characterIndex[myClientID]].currentMight)
                        character.chara[characterIndex[myClientID]].currentSpeed = character.chara[characterIndex[myClientID]].currentSpeed - dice(1);
                    else
                        character.chara[characterIndex[myClientID]].currentMight = character.chara[characterIndex[myClientID]].currentMight - dice(1);
                }
                break;
            case 28:
                GoDice = dice(character.chara[characterIndex[myClientID]].Knowledge[character.chara[characterIndex[myClientID]].currentKnowledge]);
                if(GoDice>=4)
                {
                    context2.setText("你認出那個殺手是沙洛佛克，並記得他已經被逮捕受審了，你安心了許多，取得 1 級心智。");
                    character.chara[characterIndex[myClientID]].currentSanity++;
                }
                else
                {
                    context2.setText("這個景象令你渾身發抖，失去 1 級心智。");
                    character.chara[characterIndex[myClientID]].currentSanity--;
                }
                break;
            case 29:
                GoDice = dice(2);
                if(GoDice == 4)
                {
                    context2.setText("她低語著被禁止的知識，取得 1 級知識。");
                    character.chara[characterIndex[myClientID]].currentKnowledge++;
                }
                else if(GoDice == 3)
                {
                    context2.setText("他提供了一個保護法術。取得 1 級心智。");
                    character.chara[characterIndex[myClientID]].currentSanity++;
                }
                else if(GoDice == 2)
                {
                    context2.setText("她默默的自言自語，隨即離開。");
                }
                else if(GoDice == 1)
                {
                    context2.setText("她預言死亡及毀滅即將到來，失去 1 級心智。");
                    character.chara[characterIndex[myClientID]].currentSanity--;
                }
                else if(GoDice == 0)
                {
                    context2.setText("她詛咒你，當你下次要擲骰時，結果都視為 0。");
                    NextDiceZero = true;
                }
                break;
            case 30:
                GoDice = (int)(Math.random()*2);
                if(GoDice == 0)
                {
                    context2.setText("你摧毀它，你失去 1 級力量但取得 1 級心智。");
                    character.chara[characterIndex[myClientID]].currentMight--;
                    character.chara[characterIndex[myClientID]].currentSanity++;
                }
                else if(GoDice == 1)
                {
                    context2.setText("你保護它，你失去 1 級心智但取得 1 級力量。");
                    character.chara[characterIndex[myClientID]].currentSanity--;
                    character.chara[characterIndex[myClientID]].currentMight++;
                }
                break;
            case 31:
                GoDice = dice(character.chara[characterIndex[myClientID]].Sanity[character.chara[characterIndex[myClientID]].currentSanity]);
                if(GoDice >= 4)
                {
                    context2.setText("這只是一個可怕的幻象，取得 1 級心智。");
                    character.chara[characterIndex[myClientID]].currentSanity++;
                }
                else if(GoDice>=2)
                {
                    context2.setText("這景象讓你心神不寧，失去 1 級心智。");
                    character.chara[characterIndex[myClientID]].currentSanity--;
                }
                else
                {
                    context2.setText("當蟲子爬過你時，你大聲尖叫著，受到 1 顆骰的物理傷害。");
                    if(character.chara[characterIndex[myClientID]].currentSpeed>character.chara[characterIndex[myClientID]].currentMight)
                        character.chara[characterIndex[myClientID]].currentSpeed = character.chara[characterIndex[myClientID]].currentSpeed - dice(1);
                    else
                        character.chara[characterIndex[myClientID]].currentMight = character.chara[characterIndex[myClientID]].currentMight - dice(1);
                }
                break;
            case 32:
                GoDice = dice(character.chara[characterIndex[myClientID]].Sanity[character.chara[characterIndex[myClientID]].currentSanity]);
                if(GoDice >= 4)
                {
                    context2.setText("你發現這景象異常地吸引著你，取得 1 級力量。");
                    character.chara[characterIndex[myClientID]].currentMight++;
                }
                else if(GoDice>=2)
                {
                    context2.setText("你發現這景象嚇到，失去 1 級心智。");
                    character.chara[characterIndex[myClientID]].currentSanity--;
                }
                else
                {
                    context2.setText("你的心靈遭受攻擊，你受到3顆骰子與自己心智相差的心智傷害。");
                    GoDice = dice(3);
                    if(GoDice>character.chara[characterIndex[myClientID]].Sanity[character.chara[characterIndex[myClientID]].currentSanity])
                    {
                        GoDice = GoDice - character.chara[characterIndex[myClientID]].Sanity[character.chara[characterIndex[myClientID]].currentSanity];
                        character.chara[characterIndex[myClientID]].currentSanity = character.chara[characterIndex[myClientID]].currentSanity - GoDice;
                    }
                }
                break;
            case 33:
                GoDice = dice(character.chara[characterIndex[myClientID]].Knowledge[character.chara[characterIndex[myClientID]].currentKnowledge]);
                if(GoDice>=5)
                {
                    context2.setText("將低於起始值的最低屬性，重置為起始值");
                    for(int a= 1;a<9;a++)
                    {
                        if(a == character.chara[characterIndex[myClientID]].currentMight && character.chara[characterIndex[myClientID]].currentMight<character.chara[characterIndex[myClientID]].iniMight)
                        {
                            character.chara[characterIndex[myClientID]].currentMight = character.chara[characterIndex[myClientID]].iniMight;
                            break;
                        }
                        if(a == character.chara[characterIndex[myClientID]].currentSpeed && character.chara[characterIndex[myClientID]].currentSpeed<character.chara[characterIndex[myClientID]].iniSpeed)
                        {
                            character.chara[characterIndex[myClientID]].currentSpeed = character.chara[characterIndex[myClientID]].iniSpeed;
                            break;
                        }
                        if(a == character.chara[characterIndex[myClientID]].currentSanity && character.chara[characterIndex[myClientID]].currentSanity<character.chara[characterIndex[myClientID]].iniSanity)
                        {
                            character.chara[characterIndex[myClientID]].currentSanity = character.chara[characterIndex[myClientID]].iniSanity;
                            break;
                        }
                        if(a == character.chara[characterIndex[myClientID]].currentKnowledge && character.chara[characterIndex[myClientID]].currentKnowledge<character.chara[characterIndex[myClientID]].iniKnowledge)
                        {
                            character.chara[characterIndex[myClientID]].currentKnowledge = character.chara[characterIndex[myClientID]].iniKnowledge;
                            break;
                        }
                    }
                }
                else if(GoDice >= 4)
                {
                    context2.setText("取得 1 級低於初始值的屬性。");
                    for(int a= 1;a<9;a++)
                    {
                        if(a == character.chara[characterIndex[myClientID]].currentMight && character.chara[characterIndex[myClientID]].currentMight<character.chara[characterIndex[myClientID]].iniMight)
                        {
                            character.chara[characterIndex[myClientID]].currentMight++;
                            break;
                        }
                        if(a == character.chara[characterIndex[myClientID]].currentSpeed && character.chara[characterIndex[myClientID]].currentSpeed<character.chara[characterIndex[myClientID]].iniSpeed)
                        {
                            character.chara[characterIndex[myClientID]].currentSpeed++;
                            break;
                        }
                        if(a == character.chara[characterIndex[myClientID]].currentSanity && character.chara[characterIndex[myClientID]].currentSanity<character.chara[characterIndex[myClientID]].iniSanity)
                        {
                            character.chara[characterIndex[myClientID]].currentSanity++;
                            break;
                        }
                        if(a == character.chara[characterIndex[myClientID]].currentKnowledge && character.chara[characterIndex[myClientID]].currentKnowledge<character.chara[characterIndex[myClientID]].iniKnowledge)
                        {
                            character.chara[characterIndex[myClientID]].currentKnowledge++;
                            break;
                        }
                    }
                }
                else
                {
                    context2.setText("戰神提爾一定沒發現你，沒有事情發生。");
                }
                break;
            case 34:
                GoDice = dice(character.chara[characterIndex[myClientID]].Might[character.chara[characterIndex[myClientID]].currentMight]);
                if(GoDice>=4)
                {
                    context2.setText("你撐住了，獲得1級力量");
                    character.chara[characterIndex[myClientID]].currentMight++;
                }
                else if(GoDice>=3)
                {
                    context2.setText("你勉強撐住了");
                }
                else
                {
                    context2.setText("你被往下沖走了，失去 1 級力量。");
                    character.chara[characterIndex[myClientID]].currentMight--;
                }
                break;
        }
    }

    //判定骰子
    public int dice(int get)
    {
        int total = 0;
        for(int a =0;a<get;a++)
        {
            total = total + (int)(Math.random()*3);
        }
        if(NextDiceZero)
        {
            NextDiceZero = false;
            return 0;
        }
        return total;
    }

    //更新數值
    public void reset()
    {
        TextView myMight = findViewById(R.id.myMight);
        TextView mySpeed = findViewById(R.id.mySpeed);
        TextView mySanity = findViewById(R.id.mySanity);
        TextView myKnowledge = findViewById(R.id.myKnowledge);
        mySpeed.setText(character.chara[characterIndex[myClientID]].currentSpeed);
        myMight.setText(character.chara[characterIndex[myClientID]].currentMight);
        mySanity.setText(character.chara[characterIndex[myClientID]].currentSanity);
        myKnowledge.setText(character.chara[characterIndex[myClientID]].currentKnowledge);
    }
}