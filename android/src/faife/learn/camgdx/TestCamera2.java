package faife.learn.camgdx;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TestCamera2 extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = TestCamera2.class.getName();

	private AndroidLauncher activity;
	private SurfaceHolder previewHolder;
	private Camera camera;
	private boolean inPreview;
	private boolean cameraConfigured;
	private static final int CAMERA_REQUEST_CODE = 1;

	public TestCamera2(AndroidLauncher activity) {
		super(activity);
		this.activity = activity;

		previewHolder = getHolder();
		previewHolder.addCallback(this);
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void onResume() {
		while(activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
		}
		try {
			releaseCameraAndPreview();
			camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
		} catch (Exception e) {
			Log.e(TAG, "could not open camera");
			e.printStackTrace();
		}
		startPreview();
	}

	public void onPause() {
		if (inPreview) {
			camera.stopPreview();
		}
		camera.release();
		camera=null;
		inPreview=false;
	}

	private void initPreview(int width, int height) {
		if (camera != null && previewHolder.getSurface() != null) {
			try {
				camera.setPreviewDisplay(previewHolder);
			}
			catch (Throwable t) {
				Log.e("PreviewDemo-surfaceCallback",
						"Exception in setPreviewDisplay()", t);
			}
			if (!cameraConfigured) {
				Camera.Parameters parameters=camera.getParameters();
					parameters.setPreviewSize(640, 480);
					camera.setParameters(parameters);
					cameraConfigured=true;
			}
		}
	}

	private void startPreview() {
		if (cameraConfigured && camera != null) {
			camera.startPreview();
			inPreview = true;
		}
	}

	private void releaseCameraAndPreview() {
		previewHolder.getSurface().release();
		if (camera != null) {
			camera.release();
			camera = null;
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {

	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
		initPreview(w, h);
		startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

	}

}
