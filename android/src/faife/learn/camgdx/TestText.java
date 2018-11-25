package faife.learn.camgdx;

import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class TestText extends TextView {

	public TestText(AndroidLauncher activity) {
		super(activity);
		setTextSize(150);
		setText("Fuck Yeah");
		setGravity(Gravity.CENTER);
	}
}
