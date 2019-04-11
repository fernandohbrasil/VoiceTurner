package br.com.fernandohbrasil.voicetuner.config;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

import br.com.fernandohbrasil.voicetuner.model.Nota;
import br.com.fernandohbrasil.voicetuner.model.Pessoa;

public class MyApp extends Application {

    private static Context mContext;
    private static Pessoa usuarioLogado;

    @Override
    public void onCreate() {
        getDirFromSDCard();
        mContext = getApplicationContext();
        super.onCreate();
    }

    public static String getDirFromSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdcard = Environment.getExternalStorageDirectory().getAbsoluteFile();
            File dir = new File(sdcard, "VoiceTurner");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return dir.getAbsolutePath();
        } else {
            return null;
        }
    }

    public static Context getContext() {
        return mContext;
    }

    public static Pessoa getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(Pessoa usuarioLogado) {
        MyApp.usuarioLogado = usuarioLogado;
    }

    public static void tocarNota(Nota oNota, Context oContext) {
        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(oContext, oNota.getMedia());
        mediaPlayer.start();
    }
}
