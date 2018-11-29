package faife.learn.camgdx;

import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;

public class CamView extends GLSurfaceView {
    private static final String TAG = CamView.class.getName();

    private AndroidLauncher activity;
    private CamRenderer renderer;

    public CamView(AndroidLauncher activity) {
        super(activity);
        this.activity = activity;

        setPreserveEGLContextOnPause(true);
        setEGLContextClientVersion(2);
        renderer = new CamRenderer();
        setRenderer(renderer);
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
