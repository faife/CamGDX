package faife.learn.camgdx;

import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import faife.learn.camgdx.CamGDX;

public class AndroidLauncher extends AndroidApplication {
	private static final String TAG = AndroidLauncher.class.getName();

	private TestText test;
	private TestCamera testCam;
	private CamView camView;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FrameLayout layout = new FrameLayout(this);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.r = 8;
		cfg.g = 8;
		cfg.b = 8;
		cfg.a = 8;
		View gdxView = initializeForView(new CamGDX(), cfg);
		if(graphics.getView() instanceof SurfaceView) {
			GLSurfaceView glView = (GLSurfaceView) graphics.getView();
			glView.setZOrderOnTop(true);
			glView.getHolder().setFormat(PixelFormat.RGBA_8888);

			test = new TestText(this);
			testCam = new TestCamera(this);
			camView = new CamView(this);

			//layout.addView(test);
			layout.addView(camView);
			layout.addView(gdxView);

			setContentView(layout);
		}
	}

	@Override
	public void onPause() {
		testCam.onPause();
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		testCam.onResume();
	}

}
