package cn.com.dhcc.credit.approval.constants;

public enum AuthType {

    EMPTY("",new String[0]),
    ENT_AUTHTYPES(Constants.ENT_MARK, Constants.E_APPROVAL_STR),
    PERSON_AUTHTYPES(Constants.PERSON_MARK, Constants.P_APPROVAL_STR);

    public String mark;
    public String[] authTypes;

    AuthType(String mark, String... authTypes) {
        this.mark = mark;
        this.authTypes = authTypes;
    }

    public static String[] typeOf(String mark) {
        for (AuthType authType : values()) {
            if (authType.mark.equalsIgnoreCase(mark)) {
                return authType.authTypes;
            }
        }
        return EMPTY.authTypes;
    }
}
