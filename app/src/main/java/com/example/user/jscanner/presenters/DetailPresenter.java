package com.example.user.jscanner.presenters;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.user.jscanner.activities.DetailActivity;
import com.example.user.jscanner.model.Country;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.logging.Handler;

public class DetailPresenter implements IBasePresenter {

    private DetailActivity activity;
    private Handler handler;

    @Override
    public void onAttach(AppCompatActivity activity) {
        this.activity = (DetailActivity) activity;
    }

    @Override
    public void onDetach() {
        activity = null;
    }

    public void process(final String code) {
        activity.showPB();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final Country country = new Country("123", "Germany", "de");//Country.getRegion(code.substring(0,2));
                Bitmap bitmap = null;
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(code, BarcodeFormat.EAN_13,1024,512);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    //imageView.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                final Bitmap finalBitmap = bitmap;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.setFlagText(country.getCountry());
                        Resources resources = activity.getResources();
                        final int resourceId = resources.getIdentifier(country.getCountryCode()+".png", "drawable",
                                activity.getPackageName());
                        if (resourceId != 0) {
                            activity.setFlagImage(resources.getDrawable(resourceId, activity.getTheme()));
                        } else {
                            Glide.with(activity)
                                    .load("https://www.countryflags.io/" + country.getCountryCode() + "/flat/64.png")
                                    .into(activity.getFlagImageView());
                        }
                        activity.setBarcodeImage(finalBitmap);
                        activity.setBarcodeCode(code);
                        activity.hidePB();
                    }
                });
            }
        }).start();

    }
}