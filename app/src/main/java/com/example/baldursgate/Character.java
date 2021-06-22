package com.example.baldursgate;

public class Character {

    public Chara[] chara = new Chara[6];

    Character (){
        // 紫色
        chara[0] = new Chara();
        chara[0].Name = "";
        chara[0].Might =     new int[]{0,1,2,2,4,4,5,5,7};
        chara[0].Speed =     new int[]{0,2,3,3,4,5,6,7,7};
        chara[0].Sanity =    new int[]{0,3,4,5,5,6,7,7,8};
        chara[0].Knowledge = new int[]{0,1,3,3,4,5,6,6,8};
        chara[0].iniMight = 3;
        chara[0].iniSpeed = 3;
        chara[0].iniSanity = 5;
        chara[0].iniKnowledge = 4;

        chara[0].currentMight = 3;
        chara[0].currentSpeed = 3;
        chara[0].currentSanity = 5;
        chara[0].currentKnowledge = 4;

        // 綠色
        chara[1] = new Chara();
        chara[1].Name = "";
        chara[1].Might =     new int[]{ 0,2,2,3,3,4,4,6,7 };
        chara[1].Speed =     new int[]{ 0,4,4,4,4,5,6,8,8 };
        chara[1].Sanity =    new int[]{ 0,3,4,5,5,6,6,7,8 };
        chara[1].Knowledge = new int[]{ 0,1,2,3,4,4,5,5,5 };
        chara[1].iniMight = 4;
        chara[1].iniSpeed = 4;
        chara[1].iniSanity = 3;
        chara[1].iniKnowledge = 3;

        chara[1].currentMight = 4;
        chara[1].currentSpeed = 4;
        chara[1].currentSanity = 3;
        chara[1].currentKnowledge = 3;

        // 藍色
        chara[2] = new Chara();
        chara[2].Name = "";
        chara[2].Might =     new int[]{0,3,4,4,4,4,5,6,8};
        chara[2].Speed =     new int[]{0,2,3,4,4,4,5,6,8};
        chara[2].Sanity =    new int[]{0,1,1,2,4,4,4,5,6};
        chara[2].Knowledge = new int[]{0,2,3,3,4,4,5,6,8};
        chara[2].iniMight = 3;
        chara[2].iniSpeed = 4;
        chara[2].iniSanity = 5;
        chara[2].iniKnowledge = 3;

        chara[2].currentMight = 3;
        chara[2].currentSpeed = 4;
        chara[2].currentSanity = 5;
        chara[2].currentKnowledge = 3;

        // 紅色
        chara[3] = new Chara();
        chara[3].Name = "";
        chara[3].Might =     new int[]{0,2,2,2,4,4,5,6,6};
        chara[3].Speed =     new int[]{0,3,4,4,4,4,6,7,8};
        chara[3].Sanity =    new int[]{0,4,4,4,5,6,7,8,8};
        chara[3].Knowledge = new int[]{0,4,5,5,5,5,6,6,7};
        chara[3].iniMight = 3;
        chara[3].iniSpeed = 4;
        chara[3].iniSanity = 3;
        chara[3].iniKnowledge = 4;

        chara[3].currentMight = 3;
        chara[3].currentSpeed = 4;
        chara[3].currentSanity = 3;
        chara[3].currentKnowledge = 4;

        // 黑色
        chara[4] = new Chara();
        chara[4].Name = "";
        chara[4].Might =     new int[]{0,2,3,3,4,5,5,5,6};
        chara[4].Speed =     new int[]{0,2,3,3,5,5,6,6,7};
        chara[4].Sanity =    new int[]{0,4,4,4,5,6,7,8,8};
        chara[4].Knowledge = new int[]{0,1,3,4,4,4,5,6,6};
        chara[4].iniMight = 4;
        chara[4].iniSpeed = 3;
        chara[4].iniSanity = 3;
        chara[4].iniKnowledge = 4;

        chara[4].currentMight = 4;
        chara[4].currentSpeed = 3;
        chara[4].currentSanity = 3;
        chara[4].currentKnowledge = 4;

        // 橘色
        chara[5] = new Chara();
        chara[5].Name = "";
        chara[5].Might =     new int[]{0,2,2,2,4,4,5,6,6};
        chara[5].Speed =     new int[]{0,3,4,4,4,4,6,7,8};
        chara[5].Sanity =    new int[]{0,4,4,4,5,6,7,8,8};
        chara[5].Knowledge = new int[]{0,4,5,5,5,5,6,6,7};
        chara[5].iniMight = 4;
        chara[5].iniSpeed = 3;
        chara[5].iniSanity = 4;
        chara[5].iniKnowledge = 3;

        chara[5].currentMight = 4;
        chara[5].currentSpeed = 3;
        chara[5].currentSanity = 4;
        chara[5].currentKnowledge = 3;
    }

    public static class Chara{
        String Name;        // 名字
        int[] Might;        // 4 個屬性
        int[] Speed;
        int[] Sanity;
        int[] Knowledge;
        int iniMight;       // 4 個屬性的初始格子位置
        int iniSpeed;
        int iniSanity;
        int iniKnowledge;
        int currentMight;
        int currentSpeed;
        int currentSanity;
        int currentKnowledge;
    }
}
