package com.whc.api.enums;


public enum BanDuration {
    //时长参数也以1、2、3、4、5代替1天，3天，7天，一个月，永久（鉴于MySQL最大只能到2038年，故暂设为10年）
    one(1),three(3),seven(7),aMonth(30),forever(3650);
    private final int duration;
    BanDuration(int i) {
        duration = i;
    }
    public int getDuration(){
        return duration;
    }
}
