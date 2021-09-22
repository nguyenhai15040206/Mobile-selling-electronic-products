package com.example.banhangonline.Model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

public class ConvertStringToBitmapFromAsscess {
    public static Bitmap convertStringToBitmap(Context context, String fileName)
    {
        AssetManager assetManager = context.getAssets();
        try
        {
            InputStream is = assetManager.open(fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

}
