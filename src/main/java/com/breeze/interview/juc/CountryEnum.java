package com.breeze.interview.juc;


import lombok.Getter;

/**
 * 国家的枚举类
 */
public enum CountryEnum {

    ONE(1,"齐"),TWO(2,"楚"),THREE(3,"燕"),
    FOUR(4,"韩"),FIVE(5,"赵"),SIX(6,"魏");

    @Getter private Integer retCode;
    @Getter private String retMessage;

    CountryEnum(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public static CountryEnum foreach_CountryEnum(int index){
        CountryEnum[] array = CountryEnum.values();
        for (CountryEnum countryEnum : array) {
            if (index == countryEnum.getRetCode()){
                return countryEnum;
            }
        }
        return null;
    }
}
