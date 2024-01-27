package top.fhcy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum YesNoEnum {


    YES(true, "Y", "是"),
    NO(false, "N", "否");

    private Boolean code;

    private String en;

    private String cn;

    public static String getEnByCode(Boolean code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (YesNoEnum item : YesNoEnum.values()) {
            if (item.getCode() == code) {
                return item.getEn();
            }
        }
        return null;
    }

    public static String getCnByCode(Boolean code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (YesNoEnum item : YesNoEnum.values()) {
            if (item.getCode() == code) {
                return item.getEn();
            }
        }
        return null;
    }
}
