package tech.yunjing.biconlife.jniplugin.im.voip.frame;

import java.lang.ref.WeakReference;

import tech.yunjing.biconlife.jniplugin.im.voip.frame.meeting.CenterMeetingController;
import tech.yunjing.biconlife.jniplugin.im.voip.frame.singlecontrol.CenterSingleController;


/**
 * 保存、维护Service 和 Controller
 * Created by Chen.qi on 2017/8/15
 */

public class CenterHolder {
    private static CenterHolder INSTANCE;

    private WeakReference<CenterService> mRefService;

    private WeakReference<CenterSingleController> mRefSingleController;

    private WeakReference<CenterMeetingController> mRefMeetingController;


    public static CenterHolder getInstance() {
        if (INSTANCE == null) {
            synchronized (CenterHolder.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CenterHolder();
                }
            }
        }
        return INSTANCE;
    }


    public CenterSingleController getSingleController() {
        if (mRefSingleController != null) {
            return mRefSingleController.get();
        }
        return null;
    }

    public void putSingleController(CenterSingleController controller) {
        mRefSingleController = new WeakReference<>(controller);
    }

    public CenterService getService() {
        if (mRefService != null) {
            return mRefService.get();
        }
        return null;
    }

    public void putService(CenterService service) {
        mRefService = new WeakReference<>(service);
    }

    public void clearService() {
        if (mRefService != null) {
            mRefService.clear();
        }
    }

    public void clearController() {
        if (mRefSingleController != null) {
            mRefSingleController.clear();
        }
    }

    public CenterMeetingController getMeetingController() {
        if (mRefMeetingController != null) {
            return mRefMeetingController.get();
        }
        return null;
    }

    public void putMeetingController(CenterMeetingController controller) {
        mRefMeetingController = new WeakReference<>(controller);

    }


}

