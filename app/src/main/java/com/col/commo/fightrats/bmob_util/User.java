package com.col.commo.fightrats.bmob_util;

import cn.bmob.v3.BmobUser;

/**
 * Created by commo on 2017/6/5.
 */

public class User extends BmobUser {
    private Boolean status;
    private String token,easy,normal,diffcult;

    public String getEasy() {
        return easy;
    }

    public void setEasy(String easy) {
        this.easy = easy;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getDiffcult() {
        return diffcult;
    }

    public void setDiffcult(String diffcult) {
        this.diffcult = diffcult;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
