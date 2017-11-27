package tech.yunjing.biconlife.libbaselib.view.scan.decoding;

import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

import tech.yunjing.biconlife.libbaselib.view.ViewfinderView;


public final class ViewfinderResultPointCallback implements ResultPointCallback {

    private final ViewfinderView viewfinderView;

    public ViewfinderResultPointCallback(ViewfinderView viewfinderView) {
        this.viewfinderView = viewfinderView;
    }

    @Override
    public void foundPossibleResultPoint(ResultPoint point) {
        viewfinderView.addPossibleResultPoint(point);
    }

}
