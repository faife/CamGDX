package faife.learn.camgdx;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TestCamera extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = TestCamera.class.getName();

	private DeviceCamera deviceCam;
	private AndroidLauncher activity;
	private SurfaceHolder previewHolder;
	private static final int CAMERA_REQUEST_CODE = 1;

	public TestCamera(AndroidLauncher activity) {
		super(activity);
		this.activity = activity;

		previewHolder = getHolder();
		previewHolder.addCallback(this);
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		deviceCam = new DeviceCamera(previewHolder);
	}

	public void onResume() {
		while(activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
		}
		deviceCam.init(previewHolder);
		deviceCam.start();
	}

	public void onPause() {
		deviceCam.release();
	}

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		deviceCam.init(previewHolder);
		deviceCam.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

	}

}
