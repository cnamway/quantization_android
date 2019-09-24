package me.spark.mvvm.binding.viewadapter.image;


import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/2
 * 描    述：
 * 修订历史：
 * ================================================
 */
public final class ViewAdapter {
    @BindingAdapter(value = {"url", "placeholderRes"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            GlideApp.with(imageView.getContext())
                    .load(url)
                    .placeholder(placeholderRes)
                    .into(imageView);
        }
    }
}

