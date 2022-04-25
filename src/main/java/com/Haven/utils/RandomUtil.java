package com.Haven.utils;

import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Random;

public class RandomUtil {

    private static final String[] noun = {
            "刺绣", "剪纸", "书法", "对联", "象形文字", "雄黄酒", "四合院", "战国", "风水", "昆曲", "长城", "集体舞", "黄土高原", "红白喜事", "中秋", "花鼓戏", "儒家",
            "文化", "中国结", "古装", "武打", "元宵", "越剧", "火锅", "江南", "诗经", "谜语", "史记", "红楼梦", "西游记", "除夕", "针灸", "唐三彩", "二人转", "偏旁",
            "孟子", "亭阁", "黄梅戏", "火药", "农历", "印玺", "腊八节", "京剧", "秦腔", "太极拳", "本草纲目", "天坛", "小吃摊", "红双喜", "文房四宝", "春卷", "莲藕",
            "罗盘", "故宫博物院", "相声", "五行", "北京烤鸭", "桃花扇", "木偶戏", "敦煌莫高窟", "电视小品", "甲骨文", "古筝", "二胡", "门当户对", "水浒", "除夕", "国子监",
            "兵马俑", "旗袍", "指南针", "泼水节", "馄饨", "花卷", "羊肉泡馍", "冰糖葫芦", "八宝饭", "粉丝", "豆腐脑", "小品", "孝顺", "武术", "宣纸", "衙门", "叩头",
            "中庸", "牌楼", "东坡肉", "中山陵", "秦淮河", "玄武湖", "夫子庙", "鸭血粉丝", "盐水鸭", "大煮干丝", "小笼包", "明孝陵", "云锦", "宁缺勿滥", "三思而行", "好整以暇"
    };

    private static final String[] conj = {
            "和", "跟", "与", "同", "及", "而", "况", "况且", "何况", "乃至", "则", "乃", "就", "而", "于是", "至于", "说到", "此外", "像", "如", "一般", "比方", "却",
            "但是", "然而", "而", "偏偏", "只是", "不过", "至于", "致", "不料", "岂知", "原来", "因为", "由于", "以便", "因此", "所以", "是故", "以致", "或", "抑", "非",
            "即", "不是", "就是", "若", "如果", "若是", "假如", "假使", "倘若", "要是", "譬如", "像", "好比", "如同", "似乎", "等于", "不如", "不及与其", "若", "则",
            "虽然", "可是", "虽然", "固然", "尽管", "纵然", "即使"
    };

    private static final String str = new String("qwertyuiopasdfghjklzxcvbnmQWERTYUI_OPASDFGHJKLZXCVBNM1234567890");

    public static String getRandomUUID(int length) {
        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            resultString.append(str.charAt(new Random().nextInt(str.length())));
        }
        return resultString.toString();
    }

    public static Integer getRandomId(int length) {
        return new Random().nextInt((int)Math.pow(10, length-1) * 9 - 1) + (int)Math.pow(10, length-1);
    }

    @Bean(name="nickname")
    public static String getRandomNick() {
        return noun[new Random().nextInt(noun.length)]
                       + conj[new Random().nextInt(conj.length)]
                       + noun[new Random().nextInt(noun.length)]
                       + '_' + new Random().nextInt(100);
    }

    @Bean(name = "cron")
    public static String getRandomCron() {
        int second = new Random().nextInt(59);
        int minute = new Random().nextInt(59);
        int hour = new Random().nextInt(23);
        String day = "?";
        String month = "*";
        int week = new Random().nextInt(2) + 2;

        if (week == 2) {
            if (hour < 13) hour = new Random().nextInt(8) + 15;
            if (hour == 13 && minute <= 30) minute = new Random().nextInt(29) + 30;
        }

        return second + " " +
                minute + " " +
                hour + " " +
                day + " " +
                month + " " +
                week;
    }
}