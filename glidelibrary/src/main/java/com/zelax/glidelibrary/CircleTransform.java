package com.zelax.glidelibrary;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.BaseRequestOptions;

/**
 * created by Zelax on 2021/2/5.
 */
@GlideExtension
public class CircleTransform {
    private CircleTransform() {
    }

    @GlideOption
    public static BaseRequestOptions<?> circleImg(BaseRequestOptions<?> options) {
        return options.transform(new CircleCrop());
    }
}
