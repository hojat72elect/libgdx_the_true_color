package com.nopalsoft.thetruecolor;

import android.os.Bundle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.nopalsoft.thetruecolor.handlers.HandlerGWT;

public class AndroidLauncher extends AndroidApplication implements HandlerGWT {
    public TrueColorGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        game = new TrueColorGame();
        initialize(game, config);
    }

    @Override
    public void getTextureFromFacebook(String url, final OnTextureLoaded onTextureLoaded) {
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl(url);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                final byte[] bytes = httpResponse.getResult();
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        Pixmap pixmap = new Pixmap(bytes, 0, bytes.length);
                        Texture texture = new Texture(new PixmapTextureData(pixmap, pixmap.getFormat(), false, false, true));
                        pixmap.dispose();
                        onTextureLoaded.onTextureLoaded(texture);
                    }
                });

            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.log("EmptyDownloadTest", "Failed", t);
            }

            @Override
            public void cancelled() {
                Gdx.app.log("EmptyDownloadTest", "Cancelled");
            }
        });
    }
}
