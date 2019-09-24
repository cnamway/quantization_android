package me.spark.mvvm.utils;


import me.spark.mvvm.base.BaseApplication;
import me.spark.mvvm.pojo.User;

/**
 * 用户工具类
 * Created by Administrator on 2019/1/14 0014.
 */

public class UserUtils {
    /**
     * 保存
     */
    public static void saveCurrentUser(User currentUser) {
        SPUtils.getInstance().setIsLogin(true);
        SPUtils.getInstance().setUserJson(BaseApplication.gson.toJson(currentUser));
    }

    /**
     * 删除
     */
    public static void deleteCurrentUserFile() {
        SPUtils.getInstance().setIsLogin(false);
        SPUtils.getInstance().setUserJson("");
    }

    /**
     * 查询
     *
     * @return
     */
    public static User getCurrentUserFromFile() {
        User user;
        if (!StringUtils.isEmpty(SPUtils.getInstance().getUserJson())) {
            user = BaseApplication.gson.fromJson(SPUtils.getInstance().getUserJson(), User.class);
        } else {
            user = new User();
        }
        return user;
    }

}
