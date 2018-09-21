package utilpacket.glideutil;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;


/**
 * @author koma
 * @date 2017/12/20
 * @describe
 */

@GlideModule
public class MyAppGlideModule extends AppGlideModule {


    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int memoryCacheSizeBytes = 1024 * 1024 * 20;
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
//        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }


    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
