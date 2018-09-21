package utilpacket.glideutil.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

import http.jrwd.com.okhttoputil.BuildConfig;
import utilpacket.glideutil.GlideApp;

/**
 * @author koma
 * @date 2017/12/25
 * @describe
 */

public class CropCircleTransformation extends BitmapTransformation {

    private BitmapPool mBitmapPool;

    private static final int VERSION = 1;

    private static final String ID = BuildConfig.APPLICATION_ID+"CropCircleTransformation." +
            VERSION;
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    public CropCircleTransformation(Context context) {
        this(GlideApp.get(context).getBitmapPool());
    }

    public CropCircleTransformation(BitmapPool pool) {
        this.mBitmapPool = pool;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {

        int size = Math.min(toTransform.getWidth(), toTransform.getHeight());

        int width = (toTransform.getWidth() - size) / 2;
        int height = (toTransform.getHeight() - size) / 2;

        Bitmap bitmap = mBitmapPool.get(size, size, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader =
                new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        if (width != 0 || height != 0) {
            // source isn't square, move viewport to center
            Matrix matrix = new Matrix();
            matrix.setTranslate(-width, -height);
            shader.setLocalMatrix(matrix);
        }
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        return BitmapResource.obtain(bitmap, mBitmapPool).get();
    }


    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

}
