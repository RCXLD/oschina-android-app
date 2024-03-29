package cn.gov.psxq;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import cn.gov.psxq.common.StringUtils;

public class AppData {
    public static final String              TAG         = "AppData";
    public static final Map<String, String> urlList;
    public static Map<String, Boolean>      isShortCut;
    public static Map<String, Boolean>      isLink;
    public static Map<String, Integer>      ico;
    public static Map<String, Boolean>      LBS;
    public static Set<String>               optionSet;
    public static Set<String>               displayDateSet;
    public static Set<String>               displayWithImgSet;
    public static Set<String>               twoLevelMenuSet;
    public static Set<String>               twoLevelContentSet;
    public static GsonBuilder               gsonBuilder = new GsonBuilder();
    public static final String              WEATHERAPI  = "http://api.map.baidu.com/telematics/v3/weather?location=%E6%B7%B1%E5%9C%B3&output=json&ak=BPGPMj8GameGHglMDsbgUILZ";
    public static List<String>              list;

    public static void set(String key, String value, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("data",
            Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String get(String key, Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("data",
            Activity.MODE_PRIVATE);
        return mySharedPreferences.getString(key, "");
    }

    public static Map<String, Object> getMenu(Resources resource) {
        InputStream in = resource.openRawResource(R.raw.menu);
        String gsonString = null;
        try {
            gsonString = StringUtils.getStrFromInputSteam(in);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        Map<String, Object> gsonMap = gsonBuilder.create().fromJson(gsonString,
            new TypeToken<Map<String, Object>>() {
            }.getType());
        try {
            in.close();
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }
        return gsonMap;
    }

    static {
        urlList = Maps.newHashMap();
        isShortCut = Maps.newHashMap();
        isLink = Maps.newHashMap();
        ico = Maps.newHashMap();
        LBS = Maps.newHashMap();
        list = Lists.newArrayList();
        twoLevelContentSet = Sets.newHashSet();
        optionSet = Sets.newHashSet("信息公开", "政务动态", "通知公告", "领导成员", "规范性文件", "财政资金", "发展规划",
            "政府工作报告");
        displayDateSet = Sets.newHashSet("政务动态", "通知公告");
        displayWithImgSet = Sets.newHashSet("坪山概况", "文化坪山", "产业坪山");
        twoLevelMenuSet = Sets.newHashSet("个人办事", "企业办事");

        urlList.put("了解坪山", "");
        urlList.put("坪山概况",
            "http://www.psxq.gov.cn/app/opendata/getData/68429?pageSize=10&pageNo=");
        urlList.put("文化坪山",
            "http://www.psxq.gov.cn/app/opendata/getData/68430?pageSize=10&pageNo=");
        urlList.put("产业坪山",
            "http://www.psxq.gov.cn/app/opendata/getData/68431?pageSize=10&pageNo=");
        urlList.put("信息公开", "");
        urlList.put("政务动态",
            "http://www.psxq.gov.cn/app/opendata/getData/68432?pageSize=10&pageNo=");
        urlList.put("通知公告",
            "http://www.psxq.gov.cn/app/opendata/getData/68433?pageSize=10&pageNo=");
        urlList.put("领导成员",
            "http://www.psxq.gov.cn/app/opendata/getData/68434?pageSize=10&pageNo=");
        urlList.put("规范性文件",
            "http://www.psxq.gov.cn/app/opendata/getData/68435?pageSize=10&pageNo=");
        urlList.put("财政资金",
            "http://www.psxq.gov.cn/app/opendata/getData/68436?pageSize=10&pageNo=");
        urlList.put("发展规划",
            "http://www.psxq.gov.cn/app/opendata/getData/68437?pageSize=10&pageNo=");
        urlList.put("政府工作报告",
            "http://www.psxq.gov.cn/app/opendata/getData/68438?pageSize=10&pageNo=");
        urlList.put("名单名录", "");
        urlList.put("直属部门",
            "http://www.psxq.gov.cn/app/opendata/getData/68439?pageSize=10&pageNo=");
        urlList.put("驻区单位",
            "http://www.psxq.gov.cn/app/opendata/getData/68440?pageSize=10&pageNo=");
        urlList.put("办事处", "http://www.psxq.gov.cn/app/opendata/getData/68441?pageSize=10&pageNo=");
        urlList.put("中学", "http://www.psxq.gov.cn/app/opendata/getData/68444?pageSize=10&pageNo=");
        urlList.put("小学", "http://www.psxq.gov.cn/app/opendata/getData/68443?pageSize=10&pageNo=");
        urlList.put("幼儿园", "http://www.psxq.gov.cn/app/opendata/getData/68442?pageSize=10&pageNo=");
        urlList.put("医院", "http://www.psxq.gov.cn/app/opendata/getData/68445?pageSize=10&pageNo=");
        urlList.put("药店", "http://www.psxq.gov.cn/app/opendata/getData/68446?pageSize=10&pageNo=");
        urlList.put("诊所", "http://www.psxq.gov.cn/app/opendata/getData/68447?pageSize=10&pageNo=");
        urlList.put("社康中心",
            "http://www.psxq.gov.cn/app/opendata/getData/68448?pageSize=10&pageNo=");
        urlList.put("免费WIFI热点", "http://www.psxq.gov.cn/app/wxwtssp/html/appwifimap.jsp?type=1");
        urlList.put("办事服务", "");
        urlList.put("企业办事", "http://www.psxq.gov.cn/app/bsdt/getCategoryJson.jsp?id=2&pageSize=100&pageNo=");
        urlList.put("个人办事", "http://www.psxq.gov.cn/app/bsdt/getCategoryJson.jsp?id=1&pageSize=100&pageNo=");
        urlList.put("在线服务", "http://www.psxq.gov.cn/wexin/menu/10012/index.shtml");
        urlList.put("信息自主申报", "http://www.psxq.gov.cn/wexin/zzsbxt/index.shtml");
        urlList.put("服务地图", "http://www.psxq.gov.cn/app/wxwtssp/html/appservermap.jsp");
        urlList.put("互动交流", "");
        urlList.put("网上民声", "http://www.psxq.gov.cn/smartchat/chat/app/index");
        urlList.put("微调查", "http://www.psxq.gov.cn/wexin/menu/10013/index.shtml");
        urlList.put("乐在坪山", "");
        urlList.put("吃在坪山",
            "http://www.psxq.gov.cn/app/opendata/getData/68455?pageSize=10&pageNo=");
        urlList.put("游在坪山",
            "http://www.psxq.gov.cn/app/opendata/getData/68456?pageSize=10&pageNo=");
        urlList.put("挂号预约", "http://sz.91160.com/search/index/pid-2/cid-5/aid-3366.html");
        urlList.put("婚姻登记预约", "http://210.76.66.108/hyyy/");
        urlList.put("社保查询", "http://www.psxq.gov.cn/weixin/yanyun/social.html");
        urlList.put("公积金查询", "http://www.psxq.gov.cn/weixin/yanyun/fund.html");
        urlList.put("地铁查询", "http://www.psxq.gov.cn/weixin/bookingInquiry/theSubway.html");

        isShortCut.put("了解坪山", true);
        isShortCut.put("坪山概况", false);
        isShortCut.put("文化坪山", false);
        isShortCut.put("产业坪山", false);
        isShortCut.put("信息公开", true);
        isShortCut.put("政务动态", true);
        isShortCut.put("通知公告", false);
        isShortCut.put("领导成员", false);
        isShortCut.put("规范性文件", false);
        isShortCut.put("财政资金", false);
        isShortCut.put("发展规划", false);
        isShortCut.put("政府工作报告", false);
        isShortCut.put("名单名录", true);
        isShortCut.put("直属部门", false);
        isShortCut.put("驻区单位", false);
        isShortCut.put("办事处", false);
        isShortCut.put("中学", false);
        isShortCut.put("小学", true);
        isShortCut.put("幼儿园", false);
        isShortCut.put("医院", false);
        isShortCut.put("药店", false);
        isShortCut.put("诊所", false);
        isShortCut.put("社康中心", false);
        isShortCut.put("免费WIFI热点", true);
        isShortCut.put("办事服务", true);
        isShortCut.put("个人办事", false);
        isShortCut.put("企业办事", false);
        isShortCut.put("在线服务", false);
        isShortCut.put("信息自主申报", false);
        isShortCut.put("服务地图", false);
        isShortCut.put("互动交流", true);
        isShortCut.put("网上民声", false);
        isShortCut.put("微调查", false);
        isShortCut.put("乐在坪山", true);
        isShortCut.put("吃在坪山", false);
        isShortCut.put("游在坪山", false);
        isShortCut.put("挂号预约", false);
        isShortCut.put("婚姻登记预约", false);
        isShortCut.put("社保查询", false);
        isShortCut.put("公积金查询", false);
        isShortCut.put("地铁查询", false);

        isLink.put("了解坪山", false);
        isLink.put("坪山概况", false);
        isLink.put("文化坪山", false);
        isLink.put("产业坪山", false);
        isLink.put("信息公开", false);
        isLink.put("政务动态", false);
        isLink.put("通知公告", false);
        isLink.put("领导成员", false);
        isLink.put("规范性文件", false);
        isLink.put("财政资金", false);
        isLink.put("发展规划", false);
        isLink.put("政府工作报告", false);
        isLink.put("名单名录", false);
        isLink.put("直属部门", false);
        isLink.put("驻区单位", false);
        isLink.put("办事处", false);
        isLink.put("中学", false);
        isLink.put("小学", false);
        isLink.put("幼儿园", false);
        isLink.put("医院", false);
        isLink.put("药店", false);
        isLink.put("诊所", false);
        isLink.put("社康中心", false);
        isLink.put("免费WIFI热点", true);
        isLink.put("办事服务", false);
        isLink.put("个人办事", false);
        isLink.put("企业办事", false);
        isLink.put("在线服务", true);
        isLink.put("信息自主申报", true);
        isLink.put("服务地图", true);
        isLink.put("互动交流", false);
        isLink.put("网上民声", true);
        isLink.put("微调查", true);
        isLink.put("乐在坪山", false);
        isLink.put("吃在坪山", false);
        isLink.put("游在坪山", false);
        isLink.put("挂号预约", true);
        isLink.put("婚姻登记预约", true);
        isLink.put("社保查询", true);
        isLink.put("公积金查询", true);
        isLink.put("地铁查询", true);

        LBS.put("了解坪山", false);
        LBS.put("坪山概况", false);
        LBS.put("文化坪山", false);
        LBS.put("产业坪山", false);
        LBS.put("信息公开", false);
        LBS.put("政务动态", false);
        LBS.put("通知公告", false);
        LBS.put("领导成员", false);
        LBS.put("规范性文件", false);
        LBS.put("财政资金", false);
        LBS.put("发展规划", false);
        LBS.put("政府工作报告", false);
        LBS.put("名单名录", false);
        LBS.put("直属部门", false);
        LBS.put("驻区单位", false);
        LBS.put("办事处", false);
        LBS.put("中学", false);
        LBS.put("小学", false);
        LBS.put("幼儿园", false);
        LBS.put("医院", false);
        LBS.put("药店", false);
        LBS.put("诊所", false);
        LBS.put("社康中心", false);
        LBS.put("免费WIFI热点", true);
        LBS.put("办事服务", false);
        LBS.put("个人办事", false);
        LBS.put("企业办事", false);
        LBS.put("在线服务", false);
        LBS.put("信息自主申报", false);
        LBS.put("服务地图", false);
        LBS.put("互动交流", false);
        LBS.put("网上民声", false);
        LBS.put("微调查", false);
        LBS.put("乐在坪山", false);
        LBS.put("吃在坪山", false);
        LBS.put("游在坪山", false);
        LBS.put("挂号预约", false);
        LBS.put("婚姻登记预约", false);
        LBS.put("社保查询", false);
        LBS.put("公积金查询", false);
        LBS.put("地铁查询", false);

        ico.put("了解坪山", R.drawable.grid1);
        ico.put("坪山概况", R.drawable.grid8);
        ico.put("文化坪山", R.drawable.grid9);
        ico.put("产业坪山", R.drawable.grid10);
        ico.put("信息公开", R.drawable.grid2);
        ico.put("政务动态", R.drawable.grid11);
        ico.put("通知公告", R.drawable.grid12);
        ico.put("领导成员", R.drawable.grid13);
        ico.put("规范性文件", R.drawable.grid14);
        ico.put("财政资金", R.drawable.grid15);
        ico.put("发展规划", R.drawable.grid16);
        ico.put("政府工作报告", R.drawable.grid17);
        ico.put("名单名录", R.drawable.grid3);
        ico.put("直属部门", R.drawable.grid18);
        ico.put("驻区单位", R.drawable.grid19);
        ico.put("办事处", R.drawable.grid20);
        ico.put("中学", R.drawable.grid41);
        ico.put("小学", R.drawable.grid40);
        ico.put("幼儿园", R.drawable.grid39);
        ico.put("医院", R.drawable.grid22);
        ico.put("药店", R.drawable.grid23);
        ico.put("诊所", R.drawable.grid24);
        ico.put("社康中心", R.drawable.grid25);
        ico.put("免费WIFI热点", R.drawable.grid26);
        ico.put("办事服务", R.drawable.grid4);
        ico.put("个人办事", R.drawable.grid27);
        ico.put("企业办事", R.drawable.grid27);
        ico.put("在线服务", R.drawable.grid28);
        ico.put("信息自主申报", R.drawable.grid34);
        ico.put("服务地图", R.drawable.grid5);
        ico.put("互动交流", R.drawable.grid6);
        ico.put("网上民声", R.drawable.grid35);
        ico.put("微调查", R.drawable.grid36);
        ico.put("乐在坪山", R.drawable.grid7);
        ico.put("吃在坪山", R.drawable.grid37);
        ico.put("游在坪山", R.drawable.grid38);
        ico.put("挂号预约", R.drawable.grid29);
        ico.put("婚姻登记预约", R.drawable.grid30);
        ico.put("社保查询", R.drawable.grid31);
        ico.put("公积金查询", R.drawable.grid32);
        ico.put("地铁查询", R.drawable.grid33);

        list.add("了解坪山");
        list.add("坪山概况");
        list.add("文化坪山");
        list.add("产业坪山");
        list.add("信息公开");
        list.add("政务动态");
        list.add("通知公告");
        list.add("领导成员");
        list.add("规范性文件");
        list.add("财政资金");
        list.add("发展规划");
        list.add("政府工作报告");
        list.add("名单名录");
        list.add("直属部门");
        list.add("驻区单位");
        list.add("办事处");
        list.add("中学");
        list.add("小学");
        list.add("幼儿园");
        list.add("医院");
        list.add("药店");
        list.add("诊所");
        list.add("社康中心");
        list.add("免费WIFI热点");
        list.add("办事服务");
        list.add("个人办事");
        list.add("企业办事");
        list.add("在线服务");
        list.add("信息自主申报");
        list.add("服务地图");
        list.add("互动交流");
        list.add("网上民声");
        list.add("微调查");
        list.add("乐在坪山");
        list.add("吃在坪山");
        list.add("游在坪山");
        list.add("挂号预约");
        list.add("婚姻登记预约");
        list.add("社保查询");
        list.add("公积金查询");
        list.add("地铁查询");
    }

}
