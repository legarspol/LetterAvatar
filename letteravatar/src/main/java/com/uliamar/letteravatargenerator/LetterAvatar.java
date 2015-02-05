package com.uliamar.letteravatargenerator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Pol on 05/02/15.
 */
public class LetterAvatar {
    private final static String TAG = "AvatarGen";

    /**
     * Render an avatar unique to that name
     * @param context
     * @param name the seed for the color. Can be the name of the user or its email adress...
     * @return a Bitmap to that picture.
     */
    public static Bitmap createAvatar(Context context, String name) {
        return createAvatar(context, name, 300, 50);
    }


    /**
     * Render an avatar unique to that name
     * @param context
     * @param name the seed for the color. Can be the name of the user or its email adress...
     * @param WIDTH with and height of the ouput Bitmap
     * @param paddinginDP Padding applied to that picture in pixel independent.
     * @return a Bitmap to that picture. Null if an error occured.
     */
    public static Bitmap createAvatar(Context context, String name, final int WIDTH, int paddinginDP) {
        int         pSize   = 0;
        float       pMesuredTextWidth;
        int         pBoundsTextHeight;

        Bitmap      b = Bitmap.createBitmap(WIDTH, WIDTH, Bitmap.Config.ARGB_8888);
        Canvas      canvas = new Canvas(b);
        Rect        bounds = new Rect();
        String      letter = name.substring(0,1).toUpperCase();
        String      colorName = getColor(name);

        if (colorName == null) {
            return null;
        }

        int         bgColor = Color.parseColor(colorName);
        int         textColor = 0xffffffff;
        Resources   pResources = context.getResources();
        float       ONE_DP = 1 * pResources.getDisplayMetrics().density;
        int         pPadding = Math.round(paddinginDP * ONE_DP);
        Paint       paint = new Paint();
        Paint       bgPaint = new Paint();

        paint.setAntiAlias(true);
        bgPaint.setAntiAlias(true);

        do {
            paint.setTextSize(++pSize);
            paint.getTextBounds(letter, 0, letter.length(), bounds);

        } while ((bounds.height() < (canvas.getHeight() - pPadding))
                && (paint.measureText(letter) < (canvas.getWidth() - pPadding)));

        paint.setTextSize(pSize);
        pMesuredTextWidth = paint.measureText(letter);
        pBoundsTextHeight = bounds.height();

        float xOffset = (canvas.getWidth() - pMesuredTextWidth) / 2;
        float yOffset = pBoundsTextHeight + (canvas.getHeight() - pBoundsTextHeight) / 2;

        paint.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf"));
        paint.setColor(textColor);
        bgPaint.setColor(bgColor);
        bgPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, WIDTH, WIDTH, bgPaint);
        canvas.drawText(letter, xOffset, yOffset, paint);

        return b;
    }


    /**
     * Return a color specific to that name or email adress
     * @param name
     * @return a string with the color like "#11BB33" or null if an error happened
     */
    public static String getColor(String name) {
        String code = toMD5(name);
        return code == null  || code.length() < 32 ? null : "#" + code.substring(0, 6);
    }


    private static String toMD5(String text)  {
        String hashtext = null;
        try {
            byte[] bytesOfMessage = text.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            BigInteger bigInt = new BigInteger(1,thedigest);
            hashtext = bigInt.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32 ){
                hashtext = "0"+hashtext;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(TAG, "This environnement doesn't support the Utf-8 encoding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(TAG, "This environnement doesn't support md5 hash");
        }
        return hashtext;
    }

}
