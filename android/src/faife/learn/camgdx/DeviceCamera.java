package faife.learn.camgdx;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

public class DeviceCamera {
    private static final String TAG = DeviceCamera.class.getName();

    private Camera camera;
    private SurfaceHolder holder;

    public DeviceCamera(SurfaceHolder holder) {
        this.holder = holder;
    }

    public void start() {
        if(camera != null) {
            camera.startPreview();
        }
    }

    public void init(SurfaceHolder holder) {
        if(camera == null) {
            try {
                release();
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            } catch (Exception e) {
                Log.e(TAG, "could not open camera");
                e.printStackTrace();
            }
        }
        if (this.holder == null) {
            this.holder = holder;
        }
        if (camera != null && holder.getSurface() != null) {
            try {
                camera.setPreviewDisplay(holder);
            } catch (Throwable t) {
                Log.e("PreviewDemo-surfaceCallback", "Exception in setPreviewDisplay()", t);
            }
            Camera.Parameters parameters = camera.getParameters();
            parameters.setPreviewSize(640, 480);
            camera.setParameters(parameters);
        }
    }

    public void release() {
        holder.getSurface().release();
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

}
