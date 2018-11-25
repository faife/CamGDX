package faife.learn.camgdx;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TestCamera implements SurfaceHolder.Callback, SurfaceTexture.OnFrameAvailableListener{
	private static final String TAG = TestCamera.class.getName();

	private AndroidLauncher activity;
	private SurfaceView preview=null;
	private SurfaceHolder previewHolder=null;
	private Camera camera=null;
	private boolean inPreview=false;
	private boolean cameraConfigured=false;
	private static final int CAMERA_REQUEST_CODE = 1;

	public TestCamera(AndroidLauncher activity) {
		this.activity = activity;

		preview = new SurfaceView(activity);
		previewHolder=preview.getHolder();
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

	private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
		Camera.Size result=null;
		for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
			if (size.width<=width && size.height<=height) {
				if (result==null) {
					result=size;
				}
				else {
					int resultArea=result.width*result.height;
					int newArea=size.width*size.height;

					if (newArea>resultArea) {
						result=size;
					}
				}
			}
		}
		return(result);
	}

	private void initPreview(int width, int height) {
		if (camera!=null && previewHolder.getSurface()!=null) {
			try {
				camera.setPreviewDisplay(previewHolder);
			}
			catch (Throwable t) {
				Log.e("PreviewDemo-surfaceCallback",
						"Exception in setPreviewDisplay()", t);
			}
			if (!cameraConfigured) {
				Camera.Parameters parameters=camera.getParameters();
				Camera.Size size=getBestPreviewSize(width, height,
						parameters);
				if (size!=null) {
					parameters.setPreviewSize(size.width, size.height);
					camera.setParameters(parameters);
					cameraConfigured=true;
				}
			}
		}
	}

	private void startPreview() {
		if (cameraConfigured && camera!=null) {
			camera.startPreview();
			inPreview=true;
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

	@Override
	public void onFrameAvailable(SurfaceTexture surfaceTexture) {
	}

	public SurfaceView getView() {
		return preview;
	}
}
