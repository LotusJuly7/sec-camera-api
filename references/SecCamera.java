package com.sec.android.seccamera;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.IAudioService;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
public class SecCamera {
    public static final String ACTION_NEW_PICTURE = "android.hardware.action.NEW_PICTURE";
    public static final String ACTION_NEW_VIDEO = "android.hardware.action.NEW_VIDEO";
    private static final int ACTION_SHOT_CANCELSERIES = 1134;
    private static final int ACTION_SHOT_CAPTURED = 61509;
    private static final int ACTION_SHOT_CREATING_RESULT_COMPLETED = 61507;
    private static final int ACTION_SHOT_CREATING_RESULT_PROGRESS = 61506;
    private static final int ACTION_SHOT_CREATING_RESULT_STARTED = 61505;
    private static final int ACTION_SHOT_FINALIZE = 1136;
    private static final int ACTION_SHOT_INITIALIZE = 1131;
    private static final int ACTION_SHOT_PROGRESS_ACQUISITION = 61508;
    private static final int ACTION_SHOT_RECT = 61510;
    private static final int ACTION_SHOT_SETRESOLUTION = 1132;
    private static final int ACTION_SHOT_STARTSERIES = 1133;
    private static final int ACTION_SHOT_STOPSERIES = 1135;
    private static final int ADDME_SHOT_CANCEL_CAPTURE = 1145;
    private static final int ADDME_SHOT_CAPUTRED_FIRSTPERSON = 61522;
    private static final int ADDME_SHOT_ERR = 61521;
    private static final int ADDME_SHOT_FINALIZE = 1146;
    private static final int ADDME_SHOT_HANDLE_SNAPSHOT = 1144;
    private static final int ADDME_SHOT_INIT = 1141;
    private static final int ADDME_SHOT_PROGRESS_STITCHING = 61523;
    private static final int ADDME_SHOT_START_CAPTURE = 1142;
    private static final int ADDME_SHOT_SWITCH_POSITION = 1143;
    private static final int AUTO_LOW_LIGHT_DETECTION_CHANGED = 62001;
    private static final int AUTO_LOW_LIGHT_SET = 1351;
    private static final int BABY_SHOT_DETECTION_REINIT = 1168;
    private static final int BABY_SHOT_FACE_DETECTED = 61590;
    private static final int BABY_SHOT_FACE_DETECTION_START = 1166;
    private static final int BABY_SHOT_FACE_DETECTION_STOP = 1167;
    private static final int BABY_SHOT_FACE_DETECTION_SUCCESS = 61589;
    private static final int BEAUTY_FACE_RETOUCH = 1181;
    private static final int BEAUTY_LIVE_EFFECT = 1182;
    private static final int BEAUTY_SHOT_MANUAL_MODE = 1183;
    private static final int BEAUTY_SHOT_PROGRESS_RENDERING = 61777;
    private static final int BURST_SHOT_CAPTURE = 1162;
    private static final int BURST_SHOT_CAPTURING_PROGRESSED = 61585;
    private static final int BURST_SHOT_CAPTURING_STOPPED = 61586;
    private static final int BURST_SHOT_FILE_STRING = 61588;
    private static final int BURST_SHOT_SAVING_COMPLETED = 61587;
    private static final int BURST_SHOT_START_CAPTURE = 1163;
    private static final int BURST_SHOT_STOP_AND_ENCODING = 1164;
    private static final int BURST_SHOT_STORING = 1161;
    private static final int BURST_SHOT_TERMINATE = 1165;
    private static final int CAMERA_CMD_GET_WB_CUSTOM_VALUE = 1231;
    private static final int CAMERA_CMD_RESET_WB_CUSTOM_VALUE = 1391;
    private static final int CAMERA_CMD_SMART_AUTO_S1_PUSH = 1251;
    private static final int CAMERA_CMD_SMART_AUTO_S1_RELEASE = 1252;
    public static final int CAMERA_ERROR_PREVIEWFRAME_TIMEOUT = 1001;
    public static final int CAMERA_ERROR_PRIORITY_DIED = 200;
    public static final int CAMERA_ERROR_SERVER_DIED = 100;
    public static final int CAMERA_ERROR_UNKNOWN = 1;
    private static final int CAMERA_FACE_DETECTION_HW = 0;
    private static final int CAMERA_FACE_DETECTION_SAMSUNG_SW = 4;
    private static final int CAMERA_FACE_DETECTION_SW = 1;
    private static final int CAMERA_FACE_DETECTION_SW_ONE_EYE = 2;
    private static final int CAMERA_FACE_DETECTION_SW_TWO_EYE = 3;
    private static final int CAMERA_MSG_ALL_MSGS = -1;
    private static final int CAMERA_MSG_AUTO_PARAMETERS_NOTIFY = 8192;
    private static final int CAMERA_MSG_COMPRESSED_IMAGE = 256;
    private static final int CAMERA_MSG_ERROR = 1;
    private static final int CAMERA_MSG_EXT_NOTIFY = 32768;
    private static final int CAMERA_MSG_FOCUS = 4;
    private static final int CAMERA_MSG_FOCUS_MOVE = 2048;
    private static final int CAMERA_MSG_MANUAL_FOCUS_NOTIFY = 262144;
    private static final int CAMERA_MSG_POSTVIEW_FRAME = 64;
    private static final int CAMERA_MSG_PREVIEW_FRAME = 16;
    private static final int CAMERA_MSG_PREVIEW_METADATA = 1024;
    private static final int CAMERA_MSG_RAW_IMAGE = 128;
    private static final int CAMERA_MSG_RAW_IMAGE_NOTIFY = 512;
    private static final int CAMERA_MSG_SHOT_END = 16384;
    private static final int CAMERA_MSG_SHUTTER = 2;
    private static final int CAMERA_MSG_TO_FACTORY_NOTIFY = 65536;
    private static final int CAMERA_MSG_VIDEO_FRAME = 32;
    private static final int CAMERA_MSG_ZOOM = 8;
    private static final int CAMERA_MSG_ZOOM_STEP_NOTIFY = 4096;
    private static final int CARTOON_SHOT_PROGRESS_RENDERING = 61553;
    private static final int CARTOON_SHOT_SELECT_MODE = 1151;
    private static final int CHECK_MARKER_OF_SAMSUNG_DEFINED_CALLBACK_MSGS = 61440;
    private static final int CONTINUOUS_SHOT_CAPTURING_PROGRESSED = 61489;
    private static final int CONTINUOUS_SHOT_CAPTURING_STOPPED = 61490;
    private static final int CONTINUOUS_SHOT_SAVING_COMPLETED = 61491;
    private static final int CONTINUOUS_SHOT_SOUND = 1124;
    private static final int CONTINUOUS_SHOT_START_CAPTURE = 1121;
    private static final int CONTINUOUS_SHOT_STOP_AND_ENCODING = 1122;
    private static final int CONTINUOUS_SHOT_TERMINATE = 1123;
    private static final int DEVICE_ORIENTATION = 1521;
    private static final int DRAMA_SHOT_CANCEL = 1333;
    private static final int DRAMA_SHOT_CAPTURING_PROGRESS = 61985;
    private static final int DRAMA_SHOT_ERROR = 61987;
    private static final int DRAMA_SHOT_INPUT_YUV_STRING = 61988;
    private static final int DRAMA_SHOT_MODE = 1334;
    private static final int DRAMA_SHOT_PROGRESS_POSTPROCESSING = 61986;
    private static final int DRAMA_SHOT_RESULT_YUV_STRING = 61989;
    private static final int DRAMA_SHOT_START = 1331;
    private static final int DRAMA_SHOT_STOP = 1332;
    private static final int DRAMA_SHOT_STORAGE = 1335;
    private static final int DUAL_CAMERA_CAPTURE_STATUS_CHANGED = 62033;
    private static final int DUAL_MODE_SHOT_ASYNC_CAPTURE = 1374;
    public static final int EFFECT_REAR_BOTTOM_FRONT_TOP = 0;
    public static final int EFFECT_REAR_TOP_FRONT_BOTTOM = 1;
    private static final int FACE_DETECTION_HINT = 1191;
    private static final int FIRMWARE_MSG_NOTIFY = 131072;
    private static final int GOLF_SHOT_CAPTURED = 61845;
    private static final int GOLF_SHOT_CREATING_RESULT_COMPLETED = 61843;
    private static final int GOLF_SHOT_CREATING_RESULT_PROGRESS = 61842;
    private static final int GOLF_SHOT_CREATING_RESULT_STARTED = 61841;
    private static final int GOLF_SHOT_ERROR = 61846;
    private static final int GOLF_SHOT_SAVE = 1313;
    private static final int GOLF_SHOT_SAVE_RESULT_PROGRESS = 61844;
    private static final int GOLF_SHOT_START = 1311;
    private static final int GOLF_SHOT_STOP = 1312;
    private static final int HAL_AE_AWB_LOCK_UNLOCK = 1501;
    private static final int HAL_AF_LAMP_CONTROL = 1555;
    private static final int HAL_CANCEL_AF_FOCUS_AREA = 1553;
    private static final int HAL_CAPTURE_END = 1554;
    private static final int HAL_DELETE_BURST_TAKE = 1573;
    private static final int HAL_DISABLE_POSTVIEW_TO_OVERLAY = 1509;
    private static final int HAL_DONE_CHK_DATALINE = 61442;
    private static final int HAL_FACE_DETECT_LOCK_UNLOCK = 1502;
    private static final int HAL_FLASH_POPUP = 1558;
    private static final int HAL_FLUSH_ION_MEMORY = 1561;
    private static final int HAL_MSG_OBJ_TRACKING = 61441;
    private static final int HAL_OBJECT_POSITION = 1503;
    private static final int HAL_OBJECT_TRACKING_STARTSTOP = 1504;
    private static final int HAL_QUICK_VIEW_CANCEL = 1586;
    private static final int HAL_SEND_FACE_ORIENTATION = 1530;
    private static final int HAL_SET_3D_PREVIEW_DISPLAY = 1592;
    private static final int HAL_SET_DEFAULT_IMEI = 1507;
    private static final int HAL_SET_FILTER_EFFECT = 1601;
    private static final int HAL_SET_FLASH_CHARGING_STATUS = 1556;
    private static final int HAL_SET_FOCUS_ICON_SIZE = 1593;
    private static final int HAL_SET_FRONT_SENSOR_MIRROR = 1510;
    private static final int HAL_SET_IFUNCTION_PUSH = 1611;
    private static final int HAL_SET_IFUNCTION_RELEASE = 1585;
    private static final int HAL_SET_INTERVAL_SHOT_MANUAL_FOCUS = 1610;
    private static final int HAL_SET_MANUAL_FOCUS_POSITION = 1584;
    private static final int HAL_SET_PROGRAM_SHIFT = 1589;
    private static final int HAL_SET_SAMSUNG_CAMERA = 1508;
    private static final int HAL_SET_ZOOM_STEP = 1557;
    private static final int HAL_SHOT_CAPTURE_START = 1581;
    private static final int HAL_SHOT_CAPTURE_STOP = 1582;
    private static final int HAL_SMART_CAPTURE_START = 1587;
    private static final int HAL_SMART_CAPTURE_STOP = 1588;
    private static final int HAL_SOUND_AND_SHOT_MIC_CONTROL = 1602;
    private static final int HAL_START_3D_PREVIEW = 1591;
    private static final int HAL_START_BURST_TAKE = 1571;
    private static final int HAL_START_CONTINUOUS_AF = 1551;
    private static final int HAL_START_EFFECT_RECORDING = 1621;
    private static final int HAL_START_FACEZOOM = 1531;
    private static final int HAL_START_SENSER_CLEANING = 1583;
    private static final int HAL_STOP_BURST_TAKE = 1572;
    private static final int HAL_STOP_CHK_DATALINE = 1506;
    private static final int HAL_STOP_CONTINUOUS_AF = 1552;
    private static final int HAL_STOP_EFFECT_RECORDING = 1622;
    private static final int HAL_STOP_FACEZOOM = 1532;
    private static final int HAL_TOUCH_AF_STARTSTOP = 1505;
    private static final int HDR_PICTURE_MODE_CHANGE = 1273;
    private static final int HDR_SHOT_ALL_PROGRESS_COMPLETED = 61572;
    private static final int HDR_SHOT_MODE_CHANGE = 1271;
    private static final int HDR_SHOT_RESULT_COMPLETED = 61571;
    private static final int HDR_SHOT_RESULT_PROGRESS = 61570;
    private static final int HDR_SHOT_RESULT_STARTED = 61569;
    private static final int HDR_SHOT_YUV_MODE_CHANGE = 1272;
    private static final int HDR_SHOT_YUV_STRING = 61573;
    private static final int HISTOGRAM_DATA = 62049;
    private static final int HISTOGRAM_SET_INCREMENT = 1363;
    private static final int HISTOGRAM_SET_SKIP_RATE = 1364;
    private static final int HISTOGRAM_START = 1361;
    private static final int HISTOGRAM_STOP = 1362;
    private static final int LOW_LIGHT_SHOT_SET = 1264;
    private static final int MAGICFRAME_SHOT_PROGRESS_RENDERING = 61745;
    private static final int MAGICFRAME_SHOT_SET_TEMPLATE = 1211;
    private static final int MULTIPLE_MAINJPEG_COUNT = 1241;
    private static final int MULTI_FRAME_SHOT_CAPTURE = 1262;
    private static final int MULTI_FRAME_SHOT_CAPTURING_PROGRESSED = 61729;
    private static final int MULTI_FRAME_SHOT_CAPTURING_STOPPED = 61730;
    private static final int MULTI_FRAME_SHOT_PROGRESS_POSTPROCESSING = 61731;
    private static final int MULTI_FRAME_SHOT_START = 1261;
    private static final int MULTI_FRAME_SHOT_TERMINATE = 1263;
    private static final int NOTIFY_QUALITY_CHANGED = 1401;
    private static final int Notify_FIRST_PREVIEW_FRAME_EVENT = 61809;
    private static final int PANORAMA_3D_SHOT_CANCEL = 1174;
    private static final int PANORAMA_3D_SHOT_CAPTURED = 61717;
    private static final int PANORAMA_3D_SHOT_CAPTURED_MAX_FRAMES = 61721;
    private static final int PANORAMA_3D_SHOT_CAPTURED_NEW = 61715;
    private static final int PANORAMA_3D_SHOT_DIR = 61718;
    private static final int PANORAMA_3D_SHOT_ERR = 61713;
    private static final int PANORAMA_3D_SHOT_FINALIZE = 1173;
    private static final int PANORAMA_3D_SHOT_LIVE_PREVIEW_DATA = 61720;
    private static final int PANORAMA_3D_SHOT_LOW_RESOLUTION_DATA = 61719;
    private static final int PANORAMA_3D_SHOT_MPO_DATA = 61728;
    private static final int PANORAMA_3D_SHOT_PROGRESS_STITCHING = 61716;
    private static final int PANORAMA_3D_SHOT_RECT_CENTER_POINT = 61714;
    private static final int PANORAMA_3D_SHOT_SHUTTER_START = 1175;
    private static final int PANORAMA_3D_SHOT_SHUTTER_STOP = 1176;
    private static final int PANORAMA_3D_SHOT_START = 1171;
    private static final int PANORAMA_3D_SHOT_STOP = 1172;
    public static final int PANORAMA_DIRECTION_DOWN = 8;
    public static final int PANORAMA_DIRECTION_DOWN_LEFT = 10;
    public static final int PANORAMA_DIRECTION_DOWN_RIGHT = 9;
    public static final int PANORAMA_DIRECTION_LEFT = 2;
    public static final int PANORAMA_DIRECTION_RIGHT = 1;
    public static final int PANORAMA_DIRECTION_UP = 4;
    public static final int PANORAMA_DIRECTION_UP_LEFT = 6;
    public static final int PANORAMA_DIRECTION_UP_RIGHT = 5;
    private static final int PANORAMA_SHOT_CANCEL = 1114;
    private static final int PANORAMA_SHOT_CAPTURED = 61477;
    private static final int PANORAMA_SHOT_CAPTURED_MAX_FRAMES = 61481;
    private static final int PANORAMA_SHOT_CAPTURED_NEW = 61475;
    private static final int PANORAMA_SHOT_DIR = 61478;
    private static final int PANORAMA_SHOT_ERR = 61473;
    private static final int PANORAMA_SHOT_FINALIZE = 1113;
    private static final int PANORAMA_SHOT_LIVE_PREVIEW_DATA = 61480;
    private static final int PANORAMA_SHOT_LOW_RESOLUTION_DATA = 61479;
    private static final int PANORAMA_SHOT_MOVE_SLOWLY = 61482;
    private static final int PANORAMA_SHOT_PROGRESS_STITCHING = 61476;
    private static final int PANORAMA_SHOT_RECT_CENTER_POINT = 61474;
    private static final int PANORAMA_SHOT_START = 1111;
    private static final int PANORAMA_SHOT_STOP = 1112;
    private static final int PET_DET_RECT = 61826;
    private static final int PET_DET_SUCCESS = 61825;
    private static final int PHOTOGRAPHER_SHOT_DETECTION_CHANGED = 61969;
    private static final int PHOTOGRAPHER_SHOT_DETECTION_RIGHT_BOTTOM = 1324;
    private static final int PHOTOGRAPHER_SHOT_DETECTION_START = 1321;
    private static final int PHOTOGRAPHER_SHOT_DETECTION_STOP = 1322;
    private static final int PHOTOGRAPHER_SHOT_SET_DETECTION_LEFT_TOP = 1323;
    private static final int PIP_SHOT_INITIALIZE = 1224;
    private static final int PIP_SHOT_PROGRESS_RENDERING = 61761;
    private static final int PIP_SHOT_SET_BACKGROUND = 1221;
    private static final int PIP_SHOT_SET_FRAME_POSITION = 1222;
    private static final int PIP_SHOT_SET_FRAME_SIZE = 1223;
    private static final int PIP_SHOT_SET_RESOLUTION = 1225;
    private static final int PREVIEW_CALLBACK_SIZE = 1381;
    private static final int RECORDING_TAKE_PICTURE = 1201;
    private static final int SAMSUNG_SHOT_COMPRESSED_IMAGE = 61953;
    private static final int SEC_IMAGE_EFFECT_SHOT_CREATING_RESULT_COMPLETED = 62019;
    private static final int SEC_IMAGE_EFFECT_SHOT_CREATING_RESULT_PROGRESS = 62018;
    private static final int SEC_IMAGE_EFFECT_SHOT_CREATING_RESULT_STARTED = 62017;
    private static final int SET_DISPLAY_ORIENTATION_MIRROR = 1511;
    private static final int SET_DUAL_MODE_SYNC = 1373;
    private static final int SET_EFFECT_COORDINATE = 1293;
    private static final int SET_EFFECT_EXTERNAL_MODE = 1297;
    private static final int SET_EFFECT_FILTER = 1291;
    private static final int SET_EFFECT_LAYER_ORDER = 1294;
    private static final int SET_EFFECT_MODE = 1543;
    private static final int SET_EFFECT_OPTION = 1292;
    private static final int SET_EFFECT_ORIENTATION = 1296;
    private static final int SET_EFFECT_SAVE_AS_FLIPPED = 1298;
    private static final int SET_EFFECT_SURFACE_SIZE = 1299;
    private static final int SET_EFFECT_VISIBLE = 1295;
    private static final int SET_EFFECT_VISIBLE_FOR_RECORDING = 1352;
    private static final int SET_ENABLE_SHUTTER_SOUND = 1541;
    private static final int SET_RECORDING_TAKE_PICTURE_CALLBACK = 1202;
    private static final int SET_SHUTTER_SOUND_VOLUME_LEVEL = 1542;
    private static final int SHOT_3D_PANORAMA = 1020;
    private static final int SHOT_3D_SINGLE = 1019;
    private static final int SHOT_ACTION = 1010;
    private static final int SHOT_ADDME = 1009;
    private static final int SHOT_AUTO = 1030;
    private static final int SHOT_BEAUTY = 1007;
    private static final int SHOT_BEST = 1024;
    private static final int SHOT_BESTGROUP = 1025;
    private static final int SHOT_BUDDY_PHOTOSHARING = 1018;
    private static final int SHOT_BURST = 1017;
    private static final int SHOT_CAPTURE_START = 1371;
    private static final int SHOT_CAPTURE_STOP = 1372;
    private static final int SHOT_CARTOON = 1013;
    private static final int SHOT_CONTINUOUS = 1001;
    private static final int SHOT_DRAMA = 1031;
    private static final int SHOT_FACESHOT = 1016;
    private static final int SHOT_FRAME = 1005;
    private static final int SHOT_GOLF = 1028;
    private static final int SHOT_HDR = 1014;
    private static final int SHOT_MAGICFRAME = 1021;
    private static final int SHOT_MOSAIC = 1004;
    private static final int SHOT_MULTI_FRAME = 1023;
    private static final int SHOT_PANORAMA = 1002;
    private static final int SHOT_PARTY = 1012;
    private static final int SHOT_PETDET = 1027;
    private static final int SHOT_PHOTOGRAPHER = 1029;
    private static final int SHOT_PIP = 1022;
    private static final int SHOT_SELF = 1006;
    private static final int SHOT_SHARESHOT = 1015;
    private static final int SHOT_SINGLE = 1000;
    private static final int SHOT_SMILE = 1003;
    private static final int SHOT_STOPMOTION = 1011;
    private static final int SHOT_THEME = 1032;
    private static final int SHOT_VINTAGE = 1008;
    private static final int SHUTTER_START = 1115;
    private static final int SHUTTER_STOP = 1116;
    private static final int SIM3DPHOTO_SHOT_MPO_DATA = 61698;
    private static final int SIM3DPHOTO_SHOT_PROGRESS_RENDERING = 61697;
    private static final int SINGLE_SHOT_BRACKET_NEXT_SHOT_READY = 62067;
    private static final int SINGLE_SHOT_ERROR = 62066;
    private static final int SINGLE_SHOT_LAST_PREVIEW_FRAME = 61793;
    private static final int SINGLE_SHOT_RAW_IMAGE_STRING = 62065;
    private static final int SMILE_SHOT_DETECTION_REINIT = 1103;
    private static final int SMILE_SHOT_DETECTION_START = 1101;
    private static final int SMILE_SHOT_DETECTION_STOP = 1102;
    private static final int SMILE_SHOT_DETECTION_SUCCESS = 61537;
    private static final int SMILE_SHOT_FACE_RECT = 61538;
    private static final int SMILE_SHOT_SMILE_RECT = 61539;
    private static final int START_COPY_LAST_PREVIEW_DATA = 1281;
    private static final int START_PETDET = 1301;
    private static final int STOP_COPY_LAST_PREVIEW_DATA = 1282;
    private static final int STOP_PETDET = 1302;
    private static final String TAG = "SecCamera-JNI-Java";
    private static final int THEME_SHOT_MASK_SET = 1341;
    private AutoFocusCallback mAutoFocusCallback;
    private AutoFocusMoveCallback mAutoFocusMoveCallback;
    private AutoParametersCallback mAutoParametersCallback;
    private ErrorCallback mErrorCallback;
    private EventHandler mEventHandler;
    private ExtensionCallback mExtensionCallback;
    private FaceDetectionListener mFaceListener;
    private FirmwareNotifyCallback mFirmwareNotifyCallback;
    private PictureCallback mJpegCallback;
    private ManualFocusNotifyCallback mManualFocusNotifyCallback;
    private MsgToFactoryCallback mMsgToFactoryCallback;
    private int mNativeContext;
    private boolean mOneShot;
    private PictureCallback mPostviewCallback;
    private PreviewCallback mPreviewCallback;
    private PictureCallback mRawImageCallback;
    private ShutterCallback mShutterCallback;
    private boolean mWithBuffer;
    private OnZoomChangeListener mZoomListener;
    private ZoomStepCallback mZoomStepListener;
    private boolean mFaceDetectionRunning = false;
    private Object mAutoFocusCallbackLock = new Object();
    private OnPanoramaEventListener mOnPanoramaEventListener = null;
    private On3DPanoramaEventListener mOn3DPanoramaEventListener = null;
    private OnContinuousShotEventListener mOnContinuousShotEventListener = null;
    private OnBurstShotEventListener mOnBurstShotEventListener = null;
    private OnAddMeEventListener mOnAddMeEventListener = null;
    private OnActionShotEventListener mOnActionShotEventListener = null;
    private OnCartoonShotEventListener mOnCartoonShotEventListener = null;
    private OnObjectTrackingMsgListener mOnObjectTrackingMsgListener = null;
    private OnChkDataLineListener mOnChkDataLineListener = null;
    private OnSmileShotDetectionSuccessListener mOnSmileShotDetectionSuccessListener = null;
    private OnHDRShotEventListener mOnHDRShotEventListener = null;
    private OnSIM3DPhotoShotEventListener mOnSIM3DPhotoShotEventListener = null;
    private OnMagicFrameShotEventListener mOnMagicFrameShotEventListener = null;
    private OnPIPShotEventListener mOnPIPShotEventListener = null;
    private OnMultiFrameShotEventListener mOnMultiFrameShotEventListener = null;
    private OnNotifyFirstPreviewFrameEventListener mOnNotifyFirstPreviewFrameEventListener = null;
    private OnPetDetectionListener mOnPetDetectionListener = null;
    private OnGolfShotEventListener mOnGolfShotEventListener = null;
    private OnDramaShotEventListener mOnDramaShotEventListener = null;
    private OnBeautyShotEventListener mOnBeautyShotEventListener = null;
    private OnSecImageEffectListner mOnSecImageEffectListner = null;
    private OnPhotoGrapherDetectionListener mOnPhotoGrapherDetectionListener = null;
    private OnAutoLowLightDetectionListener mOnAutoLowLightDetectionListener = null;
    private OnDualEventListener mOnDualEventListener = null;
    private OnHistogramEventListener mOnHistogramEventListener = null;
    private OnSingleShotEventListener mOnSingleShotEventListener = null;

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface AutoFocusCallback {
        void onAutoFocus(int i, int i2, SecCamera secCamera);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface AutoFocusMoveCallback {
        void onAutoFocusMoving(boolean z, SecCamera secCamera);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface AutoParametersCallback {
        void onAutoParameters(int i, int i2, SecCamera secCamera);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public static class CameraInfo {
        public static final int CAMERA_FACING_BACK = 0;
        public static final int CAMERA_FACING_FRONT = 1;
        public boolean canDisableShutterSound;
        public int facing;
        public int orientation;
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface ErrorCallback {
        void onError(int i, SecCamera secCamera);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface ExtensionCallback {
        void onExtension(int i, int i2);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public static class Face {
        public Rect rect;
        public int score;
        public int id = SecCamera.CAMERA_MSG_ALL_MSGS;
        public Point leftEye = null;
        public Point rightEye = null;
        public Point mouth = null;
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface FaceDetectionListener {
        void onFaceDetection(Face[] faceArr, SecCamera secCamera);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface FirmwareNotifyCallback {
        void onFirmwareNotify(int i, SecCamera secCamera);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface ManualFocusNotifyCallback {
        void onManualFocus(int i, int i2);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface MsgToFactoryCallback {
        void onMsgToFactory(int i, int i2, SecCamera secCamera);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface On3DPanoramaEventListener {
        void on3DPanoramaCaptured();

        void on3DPanoramaCapturedMaxFrames();

        void on3DPanoramaCapturedNew();

        void on3DPanoramaDirectionChanged(int i);

        void on3DPanoramaError(int i);

        void on3DPanoramaLivePreviewData(byte[] bArr);

        void on3DPanoramaLowResolutionData(byte[] bArr);

        void on3DPanoramaMpoData(byte[] bArr);

        void on3DPanoramaProgressStitching(int i);

        void on3DPanoramaRectChanged(int i, int i2);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnActionShotEventListener {
        void onActionShotAcquisitionProgress(int i);

        void onActionShotCaptured();

        void onActionShotCreatingResultCompleted(boolean z);

        void onActionShotCreatingResultProgress(int i);

        void onActionShotCreatingResultStarted();

        void onActionShotRectChanged(byte[] bArr);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnAddMeEventListener {
        void onAddMeCapturedFirstPerson();

        void onAddMeError(int i);

        void onAddMeProgressStitching(int i);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnAutoLowLightDetectionListener {
        void onAutoLowLightDetectionChanged(int i);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnBeautyShotEventListener {
        void onBeautyShotSavingProgress(int i);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnBurstShotEventListener {
        void onBabyShotFaceShotDetected(int i);

        void onBabyShotFaceShotDetectionSuccess();

        void onBurstShotCapturingProgressed(int i, int i2);

        void onBurstShotCapturingStopped(int i);

        void onBurstShotSavingCompleted(int i);

        void onBurstShotStringProgressed(byte[] bArr);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnCartoonShotEventListener {
        void onCartoonShotProgressRendering(int i);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnChkDataLineListener {
        void onChkDataLineDone();
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnContinuousShotEventListener {
        void onContinuousShotCapturingProgressed(int i, int i2);

        void onContinuousShotCapturingStopped(int i);

        void onContinuousShotSavingCompleted();
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnDramaShotEventListener {
        void onDramaShotCapturingProgress(int i, int i2);

        void onDramaShotError(int i);

        void onDramaShotInputString(byte[] bArr);

        void onDramaShotResultString(byte[] bArr);

        void onDramaShotSavingProgress(int i);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnDualEventListener {
        void onDualCaptureAvailable(int i);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnGolfShotEventListener {
        void onGolfShotCaptuered();

        void onGolfShotCreatingCompleted(byte[] bArr);

        void onGolfShotCreatingProgress(int i);

        void onGolfShotCreatingStarted();

        void onGolfShotError(int i);

        void onGolfShotSavingProgress(int i);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnHDRShotEventListener {
        void onHDRShotAllProgressCompleted(boolean z);

        void onHDRShotResultCompleted(boolean z);

        void onHDRShotResultProgress(int i);

        void onHDRShotResultStarted();

        void onHDRShotYUVFileString(String str);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnHistogramEventListener {
        void onHistogramUpdated(byte[] bArr);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnMagicFrameShotEventListener {
        void onMagicFrameShotProgressRendering(int i);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnMultiFrameShotEventListener {
        void onMultiFrameShotCapturingProgressed(int i, int i2);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnNotifyFirstPreviewFrameEventListener {
        void OnNotifyFirstPreviewFrame();
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnObjectTrackingMsgListener {
        void onObjectTrackingStatus(int i);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnPIPShotEventListener {
        void onPIPShotProgressRendering(int i);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnPanoramaEventListener {
        void onPanoramaCaptured();

        void onPanoramaCapturedMaxFrames();

        void onPanoramaCapturedNew();

        void onPanoramaDirectionChanged(int i);

        void onPanoramaError(int i);

        void onPanoramaLivePreviewData(byte[] bArr);

        void onPanoramaLowResolutionData(byte[] bArr);

        void onPanoramaMoveSlowly();

        void onPanoramaProgressStitching(int i);

        void onPanoramaRectChanged(int i, int i2);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnPetDetectionListener {
        void onPetDetectionSuccess();

        void onPetFaceRectChanged(Rect[] rectArr);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnPhotoGrapherDetectionListener {
        void onPhotoGrapherDetectionChanged(int i);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnSIM3DPhotoShotEventListener {
        void onSIM3DPhotoShotMpoData(byte[] bArr);

        void onSIM3DPhotoShotProgressRendering(int i);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnSecImageEffectListner {
        void onEffectShotCreatingCompleted(boolean z);

        void onEffectShotCreatingProgress(int i);

        void onEffectShotCreatingStarted();
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnSingleShotEventListener {
        void onSingleShotBracketNextShotReady();

        void onSingleShotError(int i);

        void onSingleShotRawImageString(byte[] bArr);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnSmileShotDetectionSuccessListener {
        void onSmileShotDetectionSuccess();

        void onSmileShotFaceRectChanged(byte[] bArr);

        void onSmileShotSmileRectChanged(byte[] bArr);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface OnZoomChangeListener {
        void onZoomChange(int i, boolean z, SecCamera secCamera);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface PictureCallback {
        void onPictureTaken(byte[] bArr, SecCamera secCamera);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface PreviewCallback {
        void onPreviewFrame(byte[] bArr, SecCamera secCamera);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface ShutterCallback {
        void onShutter();
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public interface ZoomStepCallback {
        void onZoomStepListener(int i, int i2, SecCamera secCamera);
    }

    private final native void _addCallbackBuffer(byte[] bArr, int i);

    private final native boolean _enableShutterSound(boolean z);

    private static native void _getCameraInfo(int i, CameraInfo cameraInfo);

    private final native void _startFaceDetection(int i);

    private final native void _stopFaceDetection();

    private final native void _stopPreview();

    private native void enableFocusMoveCallback(int i);

    public static native int getNumberOfCameras();

    private final native void native_autoFocus();

    private final native void native_cancelAutoFocus();

    private final native String native_getParameters();

    /* JADX INFO: Access modifiers changed from: private */
    public final native void native_release();

    private final native void native_setGenericParam(String str);

    private final native void native_setParameters(String str);

    private final native void native_setup(Object obj, int i, int i2);

    private final native void native_takePicture(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void setHasPreviewCallback(boolean z, boolean z2, boolean z3);

    private final native void setPreviewDisplay(Surface surface) throws IOException;

    public final native void lock();

    public final native void native_sendcommand(int i, int i2, int i3);

    public final native boolean previewEnabled();

    public final native void reconnect() throws IOException;

    public final native void setDisplayOrientation(int i);

    public final native void setPreviewTexture(SurfaceTexture surfaceTexture) throws IOException;

    public final native void startPreview();

    public final native void startSmoothZoom(int i);

    public final native void stopSmoothZoom();

    public final native void unlock();

    public static void getCameraInfo(int cameraId, CameraInfo cameraInfo) {
        _getCameraInfo(cameraId, cameraInfo);
        IBinder b = ServiceManager.getService("audio");
        IAudioService audioService = IAudioService.Stub.asInterface(b);
        if (audioService != null) {
            try {
                if (audioService.isCameraSoundForced()) {
                    cameraInfo.canDisableShutterSound = false;
                }
            } catch (RemoteException e) {
                Log.e(TAG, "Audio service is unavailable for queries");
            }
        }
    }

    public void setOnPanoramaEventListener(OnPanoramaEventListener l) {
        this.mOnPanoramaEventListener = l;
    }

    public void setOn3DPanoramaEventListener(On3DPanoramaEventListener l) {
        this.mOn3DPanoramaEventListener = l;
    }

    public void setOnContinuousShotEventListener(OnContinuousShotEventListener l) {
        this.mOnContinuousShotEventListener = l;
    }

    public void setOnBurstShotEventListener(OnBurstShotEventListener l) {
        this.mOnBurstShotEventListener = l;
    }

    public void setOnAddMeEventListener(OnAddMeEventListener l) {
        this.mOnAddMeEventListener = l;
    }

    public void setOnActionShotEventListener(OnActionShotEventListener l) {
        this.mOnActionShotEventListener = l;
    }

    public void setOnCartoonShotEventListener(OnCartoonShotEventListener l) {
        this.mOnCartoonShotEventListener = l;
    }

    public void setObjectTrackingMsgListener(OnObjectTrackingMsgListener l) {
        this.mOnObjectTrackingMsgListener = l;
    }

    public void setChkDataLineListener(OnChkDataLineListener l) {
        this.mOnChkDataLineListener = l;
    }

    public void setOnSmileShotDetectionSuccessListener(OnSmileShotDetectionSuccessListener l) {
        this.mOnSmileShotDetectionSuccessListener = l;
    }

    public void setOnHDRShotEventListener(OnHDRShotEventListener l) {
        this.mOnHDRShotEventListener = l;
    }

    public void setOnSIM3DPhotoShotEventListener(OnSIM3DPhotoShotEventListener l) {
        this.mOnSIM3DPhotoShotEventListener = l;
    }

    public void setOnMagicFrameShotEventListener(OnMagicFrameShotEventListener l) {
        this.mOnMagicFrameShotEventListener = l;
    }

    public void setOnPIPShotEventListener(OnPIPShotEventListener l) {
        this.mOnPIPShotEventListener = l;
    }

    public void setOnMultiFrameShotEventListener(OnMultiFrameShotEventListener l) {
        Log.i(TAG, "setOnDramaShotEventListener");
        this.mOnMultiFrameShotEventListener = l;
    }

    public void setOnNotifyFirstPreviewFrameEventListener(OnNotifyFirstPreviewFrameEventListener l) {
        this.mOnNotifyFirstPreviewFrameEventListener = l;
    }

    public void setOnPetDetectionListener(OnPetDetectionListener l) {
        this.mOnPetDetectionListener = l;
    }

    public void setOnGolfShotEventListener(OnGolfShotEventListener l) {
        this.mOnGolfShotEventListener = l;
    }

    public void setOnDramaShotEventListener(OnDramaShotEventListener l) {
        Log.i(TAG, "setOnDramaShotEventListener");
        this.mOnDramaShotEventListener = l;
    }

    public void setOnBeautyShotEventListener(OnBeautyShotEventListener l) {
        this.mOnBeautyShotEventListener = l;
    }

    public void setOnSecImageEffectListner(OnSecImageEffectListner l) {
        this.mOnSecImageEffectListner = l;
    }

    public void setOnPhotoGrapherDetectionListener(OnPhotoGrapherDetectionListener l) {
        this.mOnPhotoGrapherDetectionListener = l;
    }

    public void setOnAutoLowLightDetectionListener(OnAutoLowLightDetectionListener l) {
        this.mOnAutoLowLightDetectionListener = l;
    }

    public void setOnDualEventListener(OnDualEventListener l) {
        this.mOnDualEventListener = l;
    }

    public void setOnHistogramEventListener(OnHistogramEventListener l) {
        this.mOnHistogramEventListener = l;
    }

    public void setOnSingleShotEventListener(OnSingleShotEventListener l) {
        Log.i(TAG, "setOnSingleShotEventListener");
        this.mOnSingleShotEventListener = l;
    }

    public static SecCamera open(int cameraId) {
        Log.e(TAG, "SecCamera.open()");
        Camera.checkCameraEnabled();
        return new SecCamera(cameraId, 100, null, true);
    }

    public static SecCamera open(int cameraId, int priority) {
        Log.e(TAG, "SecCamera.open()");
        Camera.checkCameraEnabled();
        return new SecCamera(cameraId, priority, null, true);
    }

    public static SecCamera open(int cameraId, int priority, Looper looper) {
        Log.e(TAG, "SecCamera.open()");
        Camera.checkCameraEnabled();
        return new SecCamera(cameraId, priority, looper, true);
    }

    public static SecCamera open(int cameraId, int priority, Looper looper, boolean halsetting) {
        Log.e(TAG, "SecCamera.open()");
        Camera.checkCameraEnabled();
        return new SecCamera(cameraId, priority, looper, halsetting);
    }

    public static SecCamera open() {
        Camera.checkCameraEnabled();
        int numberOfCameras = getNumberOfCameras();
        CameraInfo cameraInfo = new CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == 0) {
                return new SecCamera(i, 100, null, true);
            }
        }
        return null;
    }

    SecCamera(int cameraId, int priority, Looper _looper, boolean halsettting) {
        Log.e(TAG, "SecCamera()");
        this.mShutterCallback = null;
        this.mRawImageCallback = null;
        this.mJpegCallback = null;
        this.mPreviewCallback = null;
        this.mPostviewCallback = null;
        this.mZoomListener = null;
        this.mAutoParametersCallback = null;
        this.mExtensionCallback = null;
        this.mMsgToFactoryCallback = null;
        this.mFirmwareNotifyCallback = null;
        this.mManualFocusNotifyCallback = null;
        if (_looper != null) {
            this.mEventHandler = new EventHandler(this, _looper);
        } else {
            Looper looper = Looper.myLooper();
            if (looper != null) {
                this.mEventHandler = new EventHandler(this, looper);
            } else {
                Looper looper2 = Looper.getMainLooper();
                if (looper2 != null) {
                    this.mEventHandler = new EventHandler(this, looper2);
                } else {
                    this.mEventHandler = null;
                }
            }
        }
        native_setup(new WeakReference(this), cameraId, priority);
        if (halsettting) {
            native_sendcommand(HAL_SET_SAMSUNG_CAMERA, 0, 0);
        }
    }

    SecCamera() {
    }

    protected void finalize() {
        native_release();
    }

    public final void release() {
        native_release();
        if (this.mEventHandler != null) {
            this.mEventHandler.removeCallbacksAndMessages(null);
        }
        this.mFaceDetectionRunning = false;
    }

    public final void setPreviewDisplay(SurfaceHolder holder) throws IOException {
        if (holder != null) {
            setPreviewDisplay(holder.getSurface());
        } else {
            setPreviewDisplay((Surface) null);
        }
    }

    public final void stopPreview() {
        _stopPreview();
        this.mFaceDetectionRunning = false;
        Log.i(TAG, "stopPreview");
    }

    public final void setPreviewCallback(PreviewCallback cb) {
        this.mPreviewCallback = cb;
        this.mOneShot = false;
        this.mWithBuffer = false;
        setHasPreviewCallback(cb != null, false, false);
    }

    public final void setOneShotPreviewCallback(PreviewCallback cb) {
        this.mPreviewCallback = cb;
        this.mOneShot = true;
        this.mWithBuffer = false;
        setHasPreviewCallback(cb != null, false, false);
    }

    public final void setPreviewCallbackWithBuffer(PreviewCallback cb) {
        this.mPreviewCallback = cb;
        this.mOneShot = false;
        this.mWithBuffer = true;
        setHasPreviewCallback(cb != null, true, false);
    }

    public final void setPreviewCallbackWithBufferNoDisable(PreviewCallback cb) {
        this.mPreviewCallback = cb;
        this.mOneShot = false;
        this.mWithBuffer = true;
        setHasPreviewCallback(cb != null, true, true);
    }

    public final void addCallbackBuffer(byte[] callbackBuffer) {
        _addCallbackBuffer(callbackBuffer, CAMERA_MSG_PREVIEW_FRAME);
    }

    public final void addRawImageCallbackBuffer(byte[] callbackBuffer) {
        addCallbackBuffer(callbackBuffer, CAMERA_MSG_RAW_IMAGE);
    }

    private final void addCallbackBuffer(byte[] callbackBuffer, int msgType) {
        if (msgType != CAMERA_MSG_PREVIEW_FRAME && msgType != CAMERA_MSG_RAW_IMAGE) {
            throw new IllegalArgumentException("Unsupported message type: " + msgType);
        }
        _addCallbackBuffer(callbackBuffer, msgType);
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    private class EventHandler extends Handler {
        private SecCamera mCamera;

        public EventHandler(SecCamera c, Looper looper) {
            super(looper);
            this.mCamera = c;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            AutoFocusCallback cb;
            Log.d(SecCamera.TAG, "handleMessage: " + msg.what);
            switch (msg.what) {
                case 1:
                    Log.e(SecCamera.TAG, "Error " + msg.arg1);
                    if (SecCamera.this.mErrorCallback != null) {
                        SecCamera.this.mErrorCallback.onError(msg.arg1, this.mCamera);
                    }
                    if (msg.arg1 == 200) {
                        SecCamera.this.native_release();
                        SecCamera.this.mFaceDetectionRunning = false;
                        return;
                    }
                    return;
                case 2:
                    if (SecCamera.this.mShutterCallback != null) {
                        SecCamera.this.mShutterCallback.onShutter();
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_DIRECTION_UP /* 4 */:
                    synchronized (SecCamera.this.mAutoFocusCallbackLock) {
                        cb = SecCamera.this.mAutoFocusCallback;
                    }
                    if (cb != null) {
                        cb.onAutoFocus(msg.arg1, msg.arg2, this.mCamera);
                        return;
                    }
                    return;
                case 8:
                    if (SecCamera.this.mZoomListener != null) {
                        SecCamera.this.mZoomListener.onZoomChange(msg.arg1, msg.arg2 != 0, this.mCamera);
                        return;
                    }
                    return;
                case SecCamera.CAMERA_MSG_PREVIEW_FRAME /* 16 */:
                    try {
                        PreviewCallback pCb = SecCamera.this.mPreviewCallback;
                        if (pCb != null) {
                            if (SecCamera.this.mOneShot) {
                                SecCamera.this.mPreviewCallback = null;
                            } else if (!SecCamera.this.mWithBuffer) {
                                SecCamera.this.setHasPreviewCallback(true, false, false);
                            }
                            pCb.onPreviewFrame((byte[]) msg.obj, this.mCamera);
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        Log.e(SecCamera.TAG, "CAMERA_MSG_PREVIEW_FRAME", e);
                        break;
                    }
                case SecCamera.CAMERA_MSG_POSTVIEW_FRAME /* 64 */:
                    break;
                case SecCamera.CAMERA_MSG_RAW_IMAGE /* 128 */:
                    if (SecCamera.this.mRawImageCallback != null) {
                        SecCamera.this.mRawImageCallback.onPictureTaken((byte[]) msg.obj, this.mCamera);
                        return;
                    }
                    return;
                case SecCamera.CAMERA_MSG_COMPRESSED_IMAGE /* 256 */:
                    if (SecCamera.this.mJpegCallback != null) {
                        SecCamera.this.mJpegCallback.onPictureTaken((byte[]) msg.obj, this.mCamera);
                        return;
                    }
                    return;
                case 1024:
                    if (SecCamera.this.mFaceListener != null && SecCamera.this.mFaceDetectionRunning) {
                        SecCamera.this.mFaceListener.onFaceDetection((Face[]) msg.obj, this.mCamera);
                        return;
                    }
                    return;
                case SecCamera.CAMERA_MSG_FOCUS_MOVE /* 2048 */:
                    if (SecCamera.this.mAutoFocusMoveCallback != null) {
                        SecCamera.this.mAutoFocusMoveCallback.onAutoFocusMoving(msg.arg1 != 0, this.mCamera);
                        return;
                    }
                    return;
                case SecCamera.CAMERA_MSG_ZOOM_STEP_NOTIFY /* 4096 */:
                    if (SecCamera.this.mZoomStepListener != null) {
                        Log.secE(SecCamera.TAG, "CAMERA_MSG_ZOOM_STEP_NOTIFY arg1 = " + msg.arg1 + ", arg2 = " + msg.arg2);
                        SecCamera.this.mZoomStepListener.onZoomStepListener(msg.arg1, msg.arg2, this.mCamera);
                        return;
                    }
                    return;
                case SecCamera.CAMERA_MSG_AUTO_PARAMETERS_NOTIFY /* 8192 */:
                    if (SecCamera.this.mAutoParametersCallback != null) {
                        Log.e(SecCamera.TAG, "CAMERA_MSG_AUTO_PARAMETERS_NOTIFY arg1 = " + msg.arg1 + ", arg2 = " + msg.arg2);
                        SecCamera.this.mAutoParametersCallback.onAutoParameters(msg.arg1, msg.arg2, this.mCamera);
                        return;
                    }
                    return;
                case SecCamera.CAMERA_MSG_EXT_NOTIFY /* 32768 */:
                    if (SecCamera.this.mExtensionCallback != null) {
                        Log.e(SecCamera.TAG, "CAMERA_MSG_EXT_NOTIFY arg1 = " + msg.arg1 + ", arg2 = " + msg.arg2);
                        SecCamera.this.mExtensionCallback.onExtension(msg.arg1, msg.arg2);
                        return;
                    }
                    return;
                case SecCamera.HAL_MSG_OBJ_TRACKING /* 61441 */:
                    if (SecCamera.this.mOnObjectTrackingMsgListener != null) {
                        Log.d(SecCamera.TAG, "HAL_MSG_OBJ_TRACKING :" + msg.arg1);
                        SecCamera.this.mOnObjectTrackingMsgListener.onObjectTrackingStatus(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.HAL_DONE_CHK_DATALINE /* 61442 */:
                    if (SecCamera.this.mOnChkDataLineListener != null) {
                        Log.d(SecCamera.TAG, "HAL_DONE_CHK_DATALINE");
                        SecCamera.this.mOnChkDataLineListener.onChkDataLineDone();
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_SHOT_ERR /* 61473 */:
                    if (SecCamera.this.mOnPanoramaEventListener != null) {
                        SecCamera.this.mOnPanoramaEventListener.onPanoramaError(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_SHOT_RECT_CENTER_POINT /* 61474 */:
                    if (SecCamera.this.mOnPanoramaEventListener != null) {
                        SecCamera.this.mOnPanoramaEventListener.onPanoramaRectChanged(msg.arg1, msg.arg2);
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_SHOT_CAPTURED_NEW /* 61475 */:
                    if (SecCamera.this.mOnPanoramaEventListener != null) {
                        SecCamera.this.mOnPanoramaEventListener.onPanoramaCapturedNew();
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_SHOT_PROGRESS_STITCHING /* 61476 */:
                    if (SecCamera.this.mOnPanoramaEventListener != null) {
                        SecCamera.this.mOnPanoramaEventListener.onPanoramaProgressStitching(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_SHOT_CAPTURED /* 61477 */:
                    if (SecCamera.this.mOnPanoramaEventListener != null) {
                        SecCamera.this.mOnPanoramaEventListener.onPanoramaCaptured();
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_SHOT_DIR /* 61478 */:
                    if (SecCamera.this.mOnPanoramaEventListener != null) {
                        SecCamera.this.mOnPanoramaEventListener.onPanoramaDirectionChanged(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_SHOT_LOW_RESOLUTION_DATA /* 61479 */:
                    if (SecCamera.this.mOnPanoramaEventListener != null) {
                        SecCamera.this.mOnPanoramaEventListener.onPanoramaLowResolutionData((byte[]) msg.obj);
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_SHOT_LIVE_PREVIEW_DATA /* 61480 */:
                    if (SecCamera.this.mOnPanoramaEventListener != null) {
                        SecCamera.this.mOnPanoramaEventListener.onPanoramaLivePreviewData((byte[]) msg.obj);
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_SHOT_CAPTURED_MAX_FRAMES /* 61481 */:
                    if (SecCamera.this.mOnPanoramaEventListener != null) {
                        SecCamera.this.mOnPanoramaEventListener.onPanoramaCapturedMaxFrames();
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_SHOT_MOVE_SLOWLY /* 61482 */:
                    if (SecCamera.this.mOnPanoramaEventListener != null) {
                        SecCamera.this.mOnPanoramaEventListener.onPanoramaMoveSlowly();
                        return;
                    }
                    return;
                case SecCamera.CONTINUOUS_SHOT_CAPTURING_PROGRESSED /* 61489 */:
                    if (SecCamera.this.mOnContinuousShotEventListener != null) {
                        SecCamera.this.mOnContinuousShotEventListener.onContinuousShotCapturingProgressed(msg.arg1, msg.arg2);
                        return;
                    }
                    return;
                case SecCamera.CONTINUOUS_SHOT_CAPTURING_STOPPED /* 61490 */:
                    if (SecCamera.this.mOnContinuousShotEventListener != null) {
                        SecCamera.this.mOnContinuousShotEventListener.onContinuousShotCapturingStopped(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.CONTINUOUS_SHOT_SAVING_COMPLETED /* 61491 */:
                    if (SecCamera.this.mOnContinuousShotEventListener != null) {
                        SecCamera.this.mOnContinuousShotEventListener.onContinuousShotSavingCompleted();
                        return;
                    }
                    return;
                case SecCamera.ACTION_SHOT_CREATING_RESULT_STARTED /* 61505 */:
                    if (SecCamera.this.mOnActionShotEventListener != null) {
                        SecCamera.this.mOnActionShotEventListener.onActionShotCreatingResultStarted();
                        return;
                    }
                    return;
                case SecCamera.ACTION_SHOT_CREATING_RESULT_PROGRESS /* 61506 */:
                    if (SecCamera.this.mOnActionShotEventListener != null) {
                        SecCamera.this.mOnActionShotEventListener.onActionShotCreatingResultProgress(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.ACTION_SHOT_CREATING_RESULT_COMPLETED /* 61507 */:
                    if (SecCamera.this.mOnActionShotEventListener != null) {
                        SecCamera.this.mOnActionShotEventListener.onActionShotCreatingResultCompleted(msg.arg1 == 1);
                        return;
                    }
                    return;
                case SecCamera.ACTION_SHOT_PROGRESS_ACQUISITION /* 61508 */:
                    Log.e(SecCamera.TAG, "onActionShotAcquisitionProgress " + msg.arg1);
                    if (SecCamera.this.mOnActionShotEventListener != null) {
                        SecCamera.this.mOnActionShotEventListener.onActionShotAcquisitionProgress(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.ACTION_SHOT_CAPTURED /* 61509 */:
                    if (SecCamera.this.mOnActionShotEventListener != null) {
                        SecCamera.this.mOnActionShotEventListener.onActionShotCaptured();
                        return;
                    }
                    return;
                case SecCamera.ACTION_SHOT_RECT /* 61510 */:
                    if (SecCamera.this.mOnActionShotEventListener != null) {
                        SecCamera.this.mOnActionShotEventListener.onActionShotRectChanged((byte[]) msg.obj);
                        return;
                    }
                    return;
                case SecCamera.SMILE_SHOT_DETECTION_SUCCESS /* 61537 */:
                    if (SecCamera.this.mOnSmileShotDetectionSuccessListener != null) {
                        SecCamera.this.mOnSmileShotDetectionSuccessListener.onSmileShotDetectionSuccess();
                        return;
                    }
                    return;
                case SecCamera.SMILE_SHOT_FACE_RECT /* 61538 */:
                    if (SecCamera.this.mOnSmileShotDetectionSuccessListener != null) {
                        SecCamera.this.mOnSmileShotDetectionSuccessListener.onSmileShotFaceRectChanged((byte[]) msg.obj);
                        return;
                    }
                    return;
                case SecCamera.SMILE_SHOT_SMILE_RECT /* 61539 */:
                    if (SecCamera.this.mOnSmileShotDetectionSuccessListener != null) {
                        SecCamera.this.mOnSmileShotDetectionSuccessListener.onSmileShotSmileRectChanged((byte[]) msg.obj);
                        return;
                    }
                    return;
                case SecCamera.CARTOON_SHOT_PROGRESS_RENDERING /* 61553 */:
                    if (SecCamera.this.mOnCartoonShotEventListener != null) {
                        Log.d(SecCamera.TAG, "CARTOON_SHOT_PROGRESS_RENDERING :" + msg.arg1);
                        SecCamera.this.mOnCartoonShotEventListener.onCartoonShotProgressRendering(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.HDR_SHOT_RESULT_STARTED /* 61569 */:
                    if (SecCamera.this.mOnHDRShotEventListener != null) {
                        SecCamera.this.mOnHDRShotEventListener.onHDRShotResultStarted();
                        return;
                    }
                    return;
                case SecCamera.HDR_SHOT_RESULT_PROGRESS /* 61570 */:
                    if (SecCamera.this.mOnHDRShotEventListener != null) {
                        SecCamera.this.mOnHDRShotEventListener.onHDRShotResultProgress(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.HDR_SHOT_RESULT_COMPLETED /* 61571 */:
                    if (SecCamera.this.mOnHDRShotEventListener != null) {
                        SecCamera.this.mOnHDRShotEventListener.onHDRShotResultCompleted(msg.arg1 == 1);
                        return;
                    }
                    return;
                case SecCamera.HDR_SHOT_ALL_PROGRESS_COMPLETED /* 61572 */:
                    if (SecCamera.this.mOnHDRShotEventListener != null) {
                        SecCamera.this.mOnHDRShotEventListener.onHDRShotAllProgressCompleted(msg.arg1 == 1);
                        return;
                    }
                    return;
                case SecCamera.HDR_SHOT_YUV_STRING /* 61573 */:
                    if (SecCamera.this.mOnHDRShotEventListener != null) {
                        String filename = new String((byte[]) msg.obj);
                        SecCamera.this.mOnHDRShotEventListener.onHDRShotYUVFileString(filename);
                        return;
                    }
                    return;
                case SecCamera.BURST_SHOT_CAPTURING_PROGRESSED /* 61585 */:
                    if (SecCamera.this.mOnBurstShotEventListener != null) {
                        SecCamera.this.mOnBurstShotEventListener.onBurstShotCapturingProgressed(msg.arg1, msg.arg2);
                        return;
                    }
                    return;
                case SecCamera.BURST_SHOT_CAPTURING_STOPPED /* 61586 */:
                    if (SecCamera.this.mOnBurstShotEventListener != null) {
                        SecCamera.this.mOnBurstShotEventListener.onBurstShotCapturingStopped(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.BURST_SHOT_SAVING_COMPLETED /* 61587 */:
                    if (SecCamera.this.mOnBurstShotEventListener != null) {
                        SecCamera.this.mOnBurstShotEventListener.onBurstShotSavingCompleted(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.BURST_SHOT_FILE_STRING /* 61588 */:
                    if (SecCamera.this.mOnBurstShotEventListener != null) {
                        SecCamera.this.mOnBurstShotEventListener.onBurstShotStringProgressed((byte[]) msg.obj);
                        return;
                    }
                    return;
                case SecCamera.BABY_SHOT_FACE_DETECTION_SUCCESS /* 61589 */:
                    if (SecCamera.this.mOnBurstShotEventListener != null) {
                        SecCamera.this.mOnBurstShotEventListener.onBabyShotFaceShotDetectionSuccess();
                        return;
                    }
                    return;
                case SecCamera.BABY_SHOT_FACE_DETECTED /* 61590 */:
                    if (SecCamera.this.mOnBurstShotEventListener != null) {
                        SecCamera.this.mOnBurstShotEventListener.onBabyShotFaceShotDetected(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.SIM3DPHOTO_SHOT_PROGRESS_RENDERING /* 61697 */:
                    if (SecCamera.this.mOnSIM3DPhotoShotEventListener != null) {
                        Log.d(SecCamera.TAG, "SIM3DPHOTO_SHOT_PROGRESS_RENDERING :" + msg.arg1);
                        SecCamera.this.mOnSIM3DPhotoShotEventListener.onSIM3DPhotoShotProgressRendering(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.SIM3DPHOTO_SHOT_MPO_DATA /* 61698 */:
                    if (SecCamera.this.mOnSIM3DPhotoShotEventListener != null) {
                        SecCamera.this.mOnSIM3DPhotoShotEventListener.onSIM3DPhotoShotMpoData((byte[]) msg.obj);
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_3D_SHOT_ERR /* 61713 */:
                    if (SecCamera.this.mOn3DPanoramaEventListener != null) {
                        SecCamera.this.mOn3DPanoramaEventListener.on3DPanoramaError(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_3D_SHOT_RECT_CENTER_POINT /* 61714 */:
                    if (SecCamera.this.mOn3DPanoramaEventListener != null) {
                        SecCamera.this.mOn3DPanoramaEventListener.on3DPanoramaRectChanged(msg.arg1, msg.arg2);
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_3D_SHOT_CAPTURED_NEW /* 61715 */:
                    if (SecCamera.this.mOn3DPanoramaEventListener != null) {
                        SecCamera.this.mOn3DPanoramaEventListener.on3DPanoramaCapturedNew();
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_3D_SHOT_PROGRESS_STITCHING /* 61716 */:
                    if (SecCamera.this.mOn3DPanoramaEventListener != null) {
                        SecCamera.this.mOn3DPanoramaEventListener.on3DPanoramaProgressStitching(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_3D_SHOT_CAPTURED /* 61717 */:
                    if (SecCamera.this.mOn3DPanoramaEventListener != null) {
                        SecCamera.this.mOn3DPanoramaEventListener.on3DPanoramaCaptured();
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_3D_SHOT_DIR /* 61718 */:
                    if (SecCamera.this.mOn3DPanoramaEventListener != null) {
                        SecCamera.this.mOn3DPanoramaEventListener.on3DPanoramaDirectionChanged(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_3D_SHOT_LOW_RESOLUTION_DATA /* 61719 */:
                    if (SecCamera.this.mOn3DPanoramaEventListener != null) {
                        SecCamera.this.mOn3DPanoramaEventListener.on3DPanoramaLowResolutionData((byte[]) msg.obj);
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_3D_SHOT_LIVE_PREVIEW_DATA /* 61720 */:
                    if (SecCamera.this.mOn3DPanoramaEventListener != null) {
                        SecCamera.this.mOn3DPanoramaEventListener.on3DPanoramaLivePreviewData((byte[]) msg.obj);
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_3D_SHOT_CAPTURED_MAX_FRAMES /* 61721 */:
                    if (SecCamera.this.mOn3DPanoramaEventListener != null) {
                        SecCamera.this.mOn3DPanoramaEventListener.on3DPanoramaCapturedMaxFrames();
                        return;
                    }
                    return;
                case SecCamera.PANORAMA_3D_SHOT_MPO_DATA /* 61728 */:
                    if (SecCamera.this.mOn3DPanoramaEventListener != null) {
                        SecCamera.this.mOn3DPanoramaEventListener.on3DPanoramaMpoData((byte[]) msg.obj);
                        return;
                    }
                    return;
                case SecCamera.MULTI_FRAME_SHOT_PROGRESS_POSTPROCESSING /* 61731 */:
                    if (SecCamera.this.mOnMultiFrameShotEventListener != null) {
                        Log.d(SecCamera.TAG, "seccamera MULTI_FRAME_SHOT_PROGRESS_POSTPROCESSING ");
                        SecCamera.this.mOnMultiFrameShotEventListener.onMultiFrameShotCapturingProgressed(msg.arg1, msg.arg2);
                        return;
                    }
                    return;
                case SecCamera.MAGICFRAME_SHOT_PROGRESS_RENDERING /* 61745 */:
                    if (SecCamera.this.mOnMagicFrameShotEventListener != null) {
                        Log.d(SecCamera.TAG, "MAGICFRAME_SHOT_PROGRESS_RENDERING : " + msg.arg1);
                        SecCamera.this.mOnMagicFrameShotEventListener.onMagicFrameShotProgressRendering(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.PIP_SHOT_PROGRESS_RENDERING /* 61761 */:
                    if (SecCamera.this.mOnPIPShotEventListener != null) {
                        Log.d(SecCamera.TAG, "PIP_SHOT_PROGRESS_RENDERING : " + msg.arg1);
                        SecCamera.this.mOnPIPShotEventListener.onPIPShotProgressRendering(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.BEAUTY_SHOT_PROGRESS_RENDERING /* 61777 */:
                    if (SecCamera.this.mOnBeautyShotEventListener != null) {
                        SecCamera.this.mOnBeautyShotEventListener.onBeautyShotSavingProgress(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.Notify_FIRST_PREVIEW_FRAME_EVENT /* 61809 */:
                    if (SecCamera.this.mOnNotifyFirstPreviewFrameEventListener != null) {
                        Log.d(SecCamera.TAG, "Notify to get the first preview frame ");
                        SecCamera.this.mOnNotifyFirstPreviewFrameEventListener.OnNotifyFirstPreviewFrame();
                        return;
                    }
                    return;
                case SecCamera.PET_DET_SUCCESS /* 61825 */:
                    if (SecCamera.this.mOnPetDetectionListener != null) {
                        Log.d(SecCamera.TAG, "Pet Detection success");
                        SecCamera.this.mOnPetDetectionListener.onPetDetectionSuccess();
                        return;
                    }
                    return;
                case SecCamera.PET_DET_RECT /* 61826 */:
                    if (SecCamera.this.mOnPetDetectionListener != null) {
                        Log.d(SecCamera.TAG, "Pet Detection Rect");
                        SecCamera.this.mOnPetDetectionListener.onPetFaceRectChanged((Rect[]) msg.obj);
                        return;
                    }
                    return;
                case SecCamera.GOLF_SHOT_CREATING_RESULT_STARTED /* 61841 */:
                    if (SecCamera.this.mOnGolfShotEventListener != null) {
                        SecCamera.this.mOnGolfShotEventListener.onGolfShotCreatingStarted();
                        return;
                    }
                    return;
                case SecCamera.GOLF_SHOT_CREATING_RESULT_PROGRESS /* 61842 */:
                    if (SecCamera.this.mOnGolfShotEventListener != null) {
                        SecCamera.this.mOnGolfShotEventListener.onGolfShotCreatingProgress(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.GOLF_SHOT_CREATING_RESULT_COMPLETED /* 61843 */:
                    if (SecCamera.this.mOnGolfShotEventListener != null) {
                        SecCamera.this.mOnGolfShotEventListener.onGolfShotCreatingCompleted((byte[]) msg.obj);
                        return;
                    }
                    return;
                case SecCamera.GOLF_SHOT_SAVE_RESULT_PROGRESS /* 61844 */:
                    if (SecCamera.this.mOnGolfShotEventListener != null) {
                        SecCamera.this.mOnGolfShotEventListener.onGolfShotSavingProgress(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.GOLF_SHOT_CAPTURED /* 61845 */:
                    if (SecCamera.this.mOnGolfShotEventListener != null) {
                        SecCamera.this.mOnGolfShotEventListener.onGolfShotCaptuered();
                        return;
                    }
                    return;
                case SecCamera.GOLF_SHOT_ERROR /* 61846 */:
                    if (SecCamera.this.mOnGolfShotEventListener != null) {
                        SecCamera.this.mOnGolfShotEventListener.onGolfShotError(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.SAMSUNG_SHOT_COMPRESSED_IMAGE /* 61953 */:
                    Log.w(SecCamera.TAG, "SAMSUNG_SHOT_COMPRESSED_IMAGE");
                    if (SecCamera.this.mJpegCallback != null) {
                        SecCamera.this.mJpegCallback.onPictureTaken((byte[]) msg.obj, this.mCamera);
                        return;
                    }
                    return;
                case SecCamera.PHOTOGRAPHER_SHOT_DETECTION_CHANGED /* 61969 */:
                    if (SecCamera.this.mOnPhotoGrapherDetectionListener != null) {
                        Log.d(SecCamera.TAG, "PhotoGrapher Detection changed");
                        SecCamera.this.mOnPhotoGrapherDetectionListener.onPhotoGrapherDetectionChanged(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.DRAMA_SHOT_CAPTURING_PROGRESS /* 61985 */:
                    if (SecCamera.this.mOnDramaShotEventListener != null) {
                        Log.i(SecCamera.TAG, "DRAMA_SHOT_CAPTURING_PROGRESS");
                        SecCamera.this.mOnDramaShotEventListener.onDramaShotCapturingProgress(msg.arg1, msg.arg2);
                        return;
                    }
                    Log.e(SecCamera.TAG, "DRAMA_SHOT_CAPTURING_PROGRESS, mOnDramaShotEventListener is null !!!");
                    return;
                case SecCamera.DRAMA_SHOT_PROGRESS_POSTPROCESSING /* 61986 */:
                    if (SecCamera.this.mOnDramaShotEventListener != null) {
                        Log.i(SecCamera.TAG, "DRAMA_SHOT_PROGRESS_POSTPROCESSING");
                        SecCamera.this.mOnDramaShotEventListener.onDramaShotSavingProgress(msg.arg1);
                        return;
                    }
                    Log.e(SecCamera.TAG, "DRAMA_SHOT_PROGRESS_POSTPROCESSING, mOnDramaShotEventListener is null !!!");
                    return;
                case SecCamera.DRAMA_SHOT_ERROR /* 61987 */:
                    if (SecCamera.this.mOnDramaShotEventListener != null) {
                        Log.i(SecCamera.TAG, "DRAMA_SHOT_ERROR");
                        SecCamera.this.mOnDramaShotEventListener.onDramaShotError(msg.arg1);
                        return;
                    }
                    Log.e(SecCamera.TAG, "DRAMA_SHOT_ERROR, mOnDramaShotEventListener is null !!!");
                    return;
                case SecCamera.DRAMA_SHOT_INPUT_YUV_STRING /* 61988 */:
                    if (SecCamera.this.mOnDramaShotEventListener != null) {
                        Log.i(SecCamera.TAG, "DRAMA_SHOT_INPUT_YUV_STRING");
                        SecCamera.this.mOnDramaShotEventListener.onDramaShotInputString((byte[]) msg.obj);
                        return;
                    }
                    Log.e(SecCamera.TAG, "DRAMA_SHOT_INPUT_YUV_STRING, mOnDramaShotEventListener is null !!!");
                    return;
                case SecCamera.DRAMA_SHOT_RESULT_YUV_STRING /* 61989 */:
                    if (SecCamera.this.mOnDramaShotEventListener != null) {
                        Log.i(SecCamera.TAG, "DRAMA_SHOT_RESULT_YUV_STRING");
                        SecCamera.this.mOnDramaShotEventListener.onDramaShotResultString((byte[]) msg.obj);
                        return;
                    }
                    Log.e(SecCamera.TAG, "DRAMA_SHOT_RESULT_YUV_STRING, mOnDramaShotEventListener is null !!!");
                    return;
                case SecCamera.AUTO_LOW_LIGHT_DETECTION_CHANGED /* 62001 */:
                    if (SecCamera.this.mOnAutoLowLightDetectionListener != null) {
                        SecCamera.this.mOnAutoLowLightDetectionListener.onAutoLowLightDetectionChanged(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.SEC_IMAGE_EFFECT_SHOT_CREATING_RESULT_STARTED /* 62017 */:
                    if (SecCamera.this.mOnSecImageEffectListner != null) {
                        SecCamera.this.mOnSecImageEffectListner.onEffectShotCreatingStarted();
                        return;
                    }
                    return;
                case SecCamera.SEC_IMAGE_EFFECT_SHOT_CREATING_RESULT_PROGRESS /* 62018 */:
                    if (SecCamera.this.mOnSecImageEffectListner != null) {
                        SecCamera.this.mOnSecImageEffectListner.onEffectShotCreatingProgress(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.SEC_IMAGE_EFFECT_SHOT_CREATING_RESULT_COMPLETED /* 62019 */:
                    if (SecCamera.this.mOnSecImageEffectListner != null) {
                        SecCamera.this.mOnSecImageEffectListner.onEffectShotCreatingCompleted(msg.arg1 == 1);
                        return;
                    }
                    return;
                case SecCamera.DUAL_CAMERA_CAPTURE_STATUS_CHANGED /* 62033 */:
                    if (SecCamera.this.mOnDualEventListener != null) {
                        SecCamera.this.mOnDualEventListener.onDualCaptureAvailable(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.HISTOGRAM_DATA /* 62049 */:
                    if (SecCamera.this.mOnHistogramEventListener != null) {
                        SecCamera.this.mOnHistogramEventListener.onHistogramUpdated((byte[]) msg.obj);
                        return;
                    }
                    return;
                case SecCamera.SINGLE_SHOT_RAW_IMAGE_STRING /* 62065 */:
                    if (SecCamera.this.mOnSingleShotEventListener != null) {
                        Log.i(SecCamera.TAG, "SINGLE_SHOT_RAW_IMAGE_STRING");
                        SecCamera.this.mOnSingleShotEventListener.onSingleShotRawImageString((byte[]) msg.obj);
                        return;
                    }
                    return;
                case SecCamera.SINGLE_SHOT_ERROR /* 62066 */:
                    if (SecCamera.this.mOnSingleShotEventListener != null) {
                        Log.i(SecCamera.TAG, "SINGLE_SHOT_ERROR");
                        SecCamera.this.mOnSingleShotEventListener.onSingleShotError(msg.arg1);
                        return;
                    }
                    return;
                case SecCamera.SINGLE_SHOT_BRACKET_NEXT_SHOT_READY /* 62067 */:
                    if (SecCamera.this.mOnSingleShotEventListener != null) {
                        Log.i(SecCamera.TAG, "SINGLE_SHOT_BRACKET_NEXT_SHOT_READY");
                        SecCamera.this.mOnSingleShotEventListener.onSingleShotBracketNextShotReady();
                        return;
                    }
                    return;
                case SecCamera.CAMERA_MSG_TO_FACTORY_NOTIFY /* 65536 */:
                    if (SecCamera.this.mMsgToFactoryCallback != null) {
                        Log.e(SecCamera.TAG, "CAMERA_MSG_TO_FACTORY_NOTIFY arg1 = " + msg.arg1 + ", arg2 = " + msg.arg2);
                        SecCamera.this.mMsgToFactoryCallback.onMsgToFactory(msg.arg1, msg.arg2, this.mCamera);
                        return;
                    }
                    return;
                case SecCamera.FIRMWARE_MSG_NOTIFY /* 131072 */:
                    if (SecCamera.this.mFirmwareNotifyCallback != null) {
                        Log.e(SecCamera.TAG, "FIRMWARE_MSG_NOTIFY arg1 = " + msg.arg1);
                        SecCamera.this.mFirmwareNotifyCallback.onFirmwareNotify(msg.arg1, this.mCamera);
                        return;
                    }
                    return;
                case SecCamera.CAMERA_MSG_MANUAL_FOCUS_NOTIFY /* 262144 */:
                    if (SecCamera.this.mManualFocusNotifyCallback != null) {
                        SecCamera.this.mManualFocusNotifyCallback.onManualFocus(msg.arg1, msg.arg2);
                        return;
                    }
                    return;
                default:
                    Log.e(SecCamera.TAG, "Unknown message type " + msg.what);
                    return;
            }
            if (SecCamera.this.mPostviewCallback != null) {
                SecCamera.this.mPostviewCallback.onPictureTaken((byte[]) msg.obj, this.mCamera);
            }
        }
    }

    private static void postEventFromNative(Object camera_ref, int what, int arg1, int arg2, Object obj) {
        SecCamera c = (SecCamera) ((WeakReference) camera_ref).get();
        if (c != null) {
            if (c.mEventHandler != null) {
                Message m = c.mEventHandler.obtainMessage(what, arg1, arg2, obj);
                if (m.what == CAMERA_MSG_EXT_NOTIFY && (m.arg1 == 8 || m.arg1 == 9)) {
                    if (c.mExtensionCallback != null) {
                        c.mExtensionCallback.onExtension(m.arg1, m.arg2);
                        return;
                    }
                    Log.e(TAG, "mExtensionCallback is null");
                    c.mEventHandler.sendMessage(m);
                    return;
                }
                c.mEventHandler.sendMessage(m);
                return;
            }
            Log.e(TAG, "mEventHandler is null");
        }
    }

    public final void autoFocus(AutoFocusCallback cb) {
        synchronized (this.mAutoFocusCallbackLock) {
            this.mAutoFocusCallback = cb;
        }
        native_autoFocus();
    }

    public final void cancelAutoFocus() {
        native_cancelAutoFocus();
        this.mEventHandler.removeMessages(4);
    }

    public void setAutoFocusMoveCallback(AutoFocusMoveCallback cb) {
        this.mAutoFocusMoveCallback = cb;
        enableFocusMoveCallback(this.mAutoFocusMoveCallback != null ? 1 : 0);
    }

    public final void setAutoFocusCb(AutoFocusCallback cb) {
        synchronized (this.mAutoFocusCallbackLock) {
            this.mAutoFocusCallback = cb;
        }
    }

    public final void takePicture(ShutterCallback shutter, PictureCallback raw, PictureCallback jpeg) {
        takePicture(shutter, raw, null, jpeg);
    }

    public final void setZoomStepCb(ZoomStepCallback cb) {
        this.mZoomStepListener = cb;
    }

    public final void setAutoParametersCb(AutoParametersCallback cb) {
        this.mAutoParametersCallback = cb;
    }

    public final void setExtensionCb(ExtensionCallback cb) {
        this.mExtensionCallback = cb;
    }

    public final void setMsgToFactoryCb(MsgToFactoryCallback cb) {
        this.mMsgToFactoryCallback = cb;
    }

    public final void setFirmwareNotifyCb(FirmwareNotifyCallback cb) {
        this.mFirmwareNotifyCallback = cb;
    }

    public final void setManualFocusCb(ManualFocusNotifyCallback cb) {
        this.mManualFocusNotifyCallback = cb;
    }

    public final void takePicture(ShutterCallback shutter, PictureCallback raw, PictureCallback postview, PictureCallback jpeg) {
        this.mShutterCallback = shutter;
        this.mRawImageCallback = raw;
        this.mPostviewCallback = postview;
        this.mJpegCallback = jpeg;
        int msgType = 0;
        if (this.mShutterCallback != null) {
            msgType = 0 | 2;
        }
        if (this.mRawImageCallback != null) {
            msgType |= CAMERA_MSG_RAW_IMAGE;
        }
        if (this.mPostviewCallback != null) {
            msgType |= CAMERA_MSG_POSTVIEW_FRAME;
        }
        if (this.mJpegCallback != null) {
            msgType |= CAMERA_MSG_COMPRESSED_IMAGE;
        }
        native_takePicture(msgType);
        this.mFaceDetectionRunning = false;
    }

    public final void recordingTakePicture() {
        native_sendcommand(RECORDING_TAKE_PICTURE, 0, 0);
    }

    public final void setRecordingTakePictureCallback(ShutterCallback shutter, PictureCallback raw, PictureCallback postview, PictureCallback jpeg) {
        this.mShutterCallback = shutter;
        this.mRawImageCallback = raw;
        this.mPostviewCallback = postview;
        this.mJpegCallback = jpeg;
        native_sendcommand(SET_RECORDING_TAKE_PICTURE_CALLBACK, 0, 0);
    }

    public void SetMultipleMainJpegCnt(int cnt) {
        native_sendcommand(MULTIPLE_MAINJPEG_COUNT, cnt, 0);
    }

    public final boolean enableShutterSound(boolean enabled) {
        if (!enabled) {
            IBinder b = ServiceManager.getService("audio");
            IAudioService audioService = IAudioService.Stub.asInterface(b);
            if (audioService != null) {
                try {
                    if (audioService.isCameraSoundForced()) {
                        return false;
                    }
                } catch (RemoteException e) {
                    Log.e(TAG, "Audio service is unavailable for queries");
                }
            }
        }
        return _enableShutterSound(enabled);
    }

    public final void setZoomChangeListener(OnZoomChangeListener listener) {
        this.mZoomListener = listener;
    }

    public final void setFaceDetectionListener(FaceDetectionListener listener) {
        this.mFaceListener = listener;
    }

    public final void startFaceDetection() {
        if (this.mFaceDetectionRunning) {
            throw new RuntimeException("Face detection is already running");
        }
        _startFaceDetection(0);
        this.mFaceDetectionRunning = true;
    }

    public final void startFaceDetectionSW() {
        if (this.mFaceDetectionRunning) {
            throw new RuntimeException("Face detection is already running");
        }
        _startFaceDetection(1);
        this.mFaceDetectionRunning = true;
    }

    public final void startSamsungFaceDetectionSW() {
        if (this.mFaceDetectionRunning) {
            throw new RuntimeException("Face detection is already running");
        }
        _startFaceDetection(4);
        this.mFaceDetectionRunning = true;
    }

    public final void startFaceDetectionSW_ForFaceService(boolean needOneEye) {
        if (this.mFaceDetectionRunning) {
            throw new RuntimeException("Face detection is already running");
        }
        if (needOneEye) {
            _startFaceDetection(2);
        } else {
            _startFaceDetection(CAMERA_FACE_DETECTION_SW_TWO_EYE);
        }
        this.mFaceDetectionRunning = true;
    }

    public final void sendFaceDetectionHint(int last) {
        native_sendcommand(FACE_DETECTION_HINT, last, 0);
    }

    public final void stopFaceDetection() {
        _stopFaceDetection();
        this.mFaceDetectionRunning = false;
        if (this.mEventHandler != null) {
            this.mEventHandler.removeMessages(1024);
        }
    }

    public final void setErrorCallback(ErrorCallback cb) {
        this.mErrorCallback = cb;
    }

    public void setParameters(Parameters params) {
        native_setParameters(params.flatten());
    }

    public Parameters getParameters() {
        Parameters p = new Parameters();
        String s = native_getParameters();
        p.unflatten(s);
        return p;
    }

    public void setShootingMode(int shootingMode) {
        native_sendcommand(shootingMode + SHOT_SINGLE, 0, 0);
    }

    public void setShootingMode(int shootingMode, int arg1, int arg2) {
        native_sendcommand(shootingMode + SHOT_SINGLE, arg1, arg2);
    }

    public void setShutterSoundEnable(boolean mode) {
        if (mode) {
            native_sendcommand(SET_ENABLE_SHUTTER_SOUND, 1, 0);
        } else {
            native_sendcommand(SET_ENABLE_SHUTTER_SOUND, 0, 0);
        }
    }

    public void setShutterSoundVolumeLevel(int Level) {
        native_sendcommand(SET_SHUTTER_SOUND_VOLUME_LEVEL, Level, 0);
    }

    public void setShootingModeCallbacks(ShutterCallback shutter, PictureCallback raw, PictureCallback jpeg) {
        this.mShutterCallback = shutter;
        this.mRawImageCallback = raw;
        this.mPostviewCallback = null;
        this.mJpegCallback = jpeg;
    }

    public void startSmileDetection(boolean start) {
        if (start) {
            native_sendcommand(SMILE_SHOT_DETECTION_START, 0, 0);
        } else {
            native_sendcommand(SMILE_SHOT_DETECTION_STOP, 0, 0);
        }
    }

    public void smileDetectionReinit() {
        native_sendcommand(SMILE_SHOT_DETECTION_REINIT, 0, 0);
    }

    public void startPhotoGrapherDetection(boolean start) {
        if (start) {
            native_sendcommand(PHOTOGRAPHER_SHOT_DETECTION_START, 0, 0);
        } else {
            native_sendcommand(PHOTOGRAPHER_SHOT_DETECTION_STOP, 0, 0);
        }
    }

    public void setPhotoGrapherDetectionArea(int left, int top, int right, int bottom) {
        native_sendcommand(PHOTOGRAPHER_SHOT_SET_DETECTION_LEFT_TOP, left, top);
        native_sendcommand(PHOTOGRAPHER_SHOT_DETECTION_RIGHT_BOTTOM, right, bottom);
    }

    public void startPetDetection() {
        native_sendcommand(START_PETDET, 0, 0);
    }

    public void stopPetDetection() {
        native_sendcommand(STOP_PETDET, 0, 0);
    }

    public void startPanorama(boolean start) {
        if (start) {
            native_sendcommand(PANORAMA_SHOT_START, 0, 0);
        } else {
            native_sendcommand(PANORAMA_SHOT_STOP, 0, 0);
        }
    }

    public void startShutterSound(boolean start) {
        if (start) {
            native_sendcommand(SHUTTER_START, 0, 0);
        } else {
            native_sendcommand(SHUTTER_STOP, 0, 0);
        }
    }

    public void cancelPanorama() {
        native_sendcommand(PANORAMA_SHOT_CANCEL, 0, 0);
    }

    public void start3DPanorama(boolean start) {
        if (start) {
            native_sendcommand(PANORAMA_3D_SHOT_START, 0, 0);
        } else {
            native_sendcommand(PANORAMA_3D_SHOT_STOP, 0, 0);
        }
    }

    public void start3DShutterSound(boolean start) {
        if (start) {
            native_sendcommand(PANORAMA_3D_SHOT_SHUTTER_START, 0, 0);
        } else {
            native_sendcommand(PANORAMA_3D_SHOT_SHUTTER_STOP, 0, 0);
        }
    }

    public void cancel3DPanorama() {
        native_sendcommand(PANORAMA_3D_SHOT_CANCEL, 0, 0);
    }

    public void setMagicFrameTemplate(int templateId) {
        if (templateId >= 0) {
            native_sendcommand(MAGICFRAME_SHOT_SET_TEMPLATE, templateId, 0);
        } else {
            native_sendcommand(MAGICFRAME_SHOT_SET_TEMPLATE, 0, 0);
        }
    }

    public void setPIPBackground(boolean mode) {
        if (mode) {
            native_sendcommand(PIP_SHOT_SET_BACKGROUND, 1, 0);
        } else {
            native_sendcommand(PIP_SHOT_SET_BACKGROUND, 0, 0);
        }
    }

    public void setPIPFramePosition(int left, int top) {
        native_sendcommand(PIP_SHOT_SET_FRAME_POSITION, left, top);
    }

    public void setPIPFrameSize(int width, int height) {
        native_sendcommand(PIP_SHOT_SET_FRAME_SIZE, width, height);
    }

    public void initializePIPShot() {
        native_sendcommand(PIP_SHOT_INITIALIZE, 0, 0);
    }

    public void startContinuousShot(boolean start) {
        if (start) {
            native_sendcommand(CONTINUOUS_SHOT_START_CAPTURE, 0, 0);
        } else {
            native_sendcommand(CONTINUOUS_SHOT_STOP_AND_ENCODING, 0, 0);
        }
    }

    public void terminateContinuousShot() {
        native_sendcommand(CONTINUOUS_SHOT_TERMINATE, 0, 0);
    }

    public void setContinuousShotSound(int sound) {
        if (sound > 0) {
            native_sendcommand(CONTINUOUS_SHOT_SOUND, sound, 0);
        } else {
            native_sendcommand(CONTINUOUS_SHOT_SOUND, 0, 0);
        }
    }

    public void startBurstShot(boolean start, int maxImgCnt) {
        if (start) {
            native_sendcommand(BURST_SHOT_START_CAPTURE, maxImgCnt, 0);
        } else {
            native_sendcommand(BURST_SHOT_STOP_AND_ENCODING, 0, 0);
        }
    }

    public void terminateBurstShot() {
        native_sendcommand(BURST_SHOT_TERMINATE, 0, 0);
    }

    public void setBurstShotStoring() {
        native_sendcommand(BURST_SHOT_STORING, 0, 0);
    }

    public void startBabyShotFaceDetection(boolean start) {
        if (start) {
            native_sendcommand(BABY_SHOT_FACE_DETECTION_START, 0, 0);
        } else {
            native_sendcommand(BABY_SHOT_FACE_DETECTION_STOP, 0, 0);
        }
    }

    public void babyDetectionReinit() {
        native_sendcommand(BABY_SHOT_DETECTION_REINIT, 0, 0);
    }

    public void captureBurstShot() {
        native_sendcommand(BURST_SHOT_CAPTURE, 0, 0);
    }

    public void captureBurstShot(int duration) {
        native_sendcommand(BURST_SHOT_CAPTURE, duration, 0);
    }

    public void initializeActionShot() {
        native_sendcommand(ACTION_SHOT_INITIALIZE, 0, 0);
    }

    public void setResolutionActionShot(int width, int height) {
        native_sendcommand(ACTION_SHOT_SETRESOLUTION, width, height);
    }

    public void startSeriesActionShot() {
        native_sendcommand(ACTION_SHOT_STARTSERIES, 0, 0);
    }

    public void stopSeriesActionShot() {
        native_sendcommand(ACTION_SHOT_STOPSERIES, 0, 0);
    }

    public void cancelSeriesActionShot() {
        native_sendcommand(ACTION_SHOT_CANCELSERIES, 0, 0);
    }

    public void finishActionShot() {
        native_sendcommand(ACTION_SHOT_FINALIZE, 0, 0);
    }

    public void initializeAddMeShot() {
        native_sendcommand(ADDME_SHOT_INIT, 0, 0);
    }

    public void startCaptureAddMeShot() {
        native_sendcommand(ADDME_SHOT_START_CAPTURE, 0, 0);
    }

    public void switchPositionAddMeShot(int setLeft) {
        native_sendcommand(ADDME_SHOT_SWITCH_POSITION, setLeft, 0);
    }

    public void doSnapAddMeShot() {
        native_sendcommand(ADDME_SHOT_HANDLE_SNAPSHOT, 0, 0);
    }

    public void cancelCaptureAddMeShot() {
        native_sendcommand(ADDME_SHOT_CANCEL_CAPTURE, 0, 0);
    }

    public void finishAddMeShot() {
        native_sendcommand(ADDME_SHOT_FINALIZE, 0, 0);
    }

    public void setLowLightShot(int set) {
        native_sendcommand(LOW_LIGHT_SHOT_SET, set, 0);
    }

    public void startMultiFrameShot() {
        native_sendcommand(MULTI_FRAME_SHOT_START, 0, 0);
    }

    public void captureMultiFrameShot() {
        native_sendcommand(MULTI_FRAME_SHOT_CAPTURE, 0, 0);
    }

    public void terminateMultiFrameShot() {
        native_sendcommand(MULTI_FRAME_SHOT_TERMINATE, 0, 0);
    }

    public void setFaceRetouchLevel(int retouchLevel) {
        native_sendcommand(BEAUTY_FACE_RETOUCH, retouchLevel, 0);
    }

    public void setBeautyEffect(boolean mLive) {
        if (!mLive) {
            native_sendcommand(BEAUTY_LIVE_EFFECT, 0, 0);
        } else {
            native_sendcommand(BEAUTY_LIVE_EFFECT, 1, 0);
        }
    }

    public void setBeautyShotManualMode(boolean mManual) {
        if (!mManual) {
            native_sendcommand(BEAUTY_SHOT_MANUAL_MODE, 0, 0);
        } else {
            native_sendcommand(BEAUTY_SHOT_MANUAL_MODE, 1, 0);
        }
    }

    public void startShotCapture(int count) {
        native_sendcommand(SHOT_CAPTURE_START, count, 0);
    }

    public void stopShotCapture(int stopCause, boolean isSingle) {
        if (isSingle) {
            native_sendcommand(SHOT_CAPTURE_STOP, stopCause, 0);
        } else {
            native_sendcommand(HAL_SHOT_CAPTURE_STOP, stopCause, 0);
        }
    }

    public void startSmartCapture(boolean start) {
        if (start) {
            native_sendcommand(HAL_SMART_CAPTURE_START, 0, 0);
        } else {
            native_sendcommand(HAL_SMART_CAPTURE_STOP, 0, 0);
        }
    }

    public void start3DPreview(boolean start) {
        if (start) {
            native_sendcommand(HAL_START_3D_PREVIEW, 1, 0);
        } else {
            native_sendcommand(HAL_START_3D_PREVIEW, 0, 0);
        }
    }

    public void start3DPreivewDisplay(boolean start) {
        if (start) {
            native_sendcommand(HAL_SET_3D_PREVIEW_DISPLAY, 1, 0);
        } else {
            native_sendcommand(HAL_SET_3D_PREVIEW_DISPLAY, 0, 0);
        }
    }

    public void cancelQuickView() {
        native_sendcommand(HAL_QUICK_VIEW_CANCEL, 0, 0);
    }

    public void setAEAWBLockState(int ae_lockunlock, int awb_lockunlock) {
        native_sendcommand(HAL_AE_AWB_LOCK_UNLOCK, ae_lockunlock, awb_lockunlock);
    }

    public void setHDRModeLevel(int Level) {
        native_sendcommand(HDR_SHOT_MODE_CHANGE, Level, 0);
    }

    public void setPictureMode(int picMode) {
        native_sendcommand(HDR_PICTURE_MODE_CHANGE, picMode, 0);
    }

    public void lockFaceDetect() {
        native_sendcommand(HAL_FACE_DETECT_LOCK_UNLOCK, 1, 0);
    }

    public void unlockFaceDetect() {
        native_sendcommand(HAL_FACE_DETECT_LOCK_UNLOCK, 0, 0);
    }

    public void setObjectTrackingPosition(int x, int y) {
        native_sendcommand(HAL_OBJECT_POSITION, x, y);
    }

    public void startObjectTracking() {
        native_sendcommand(HAL_OBJECT_TRACKING_STARTSTOP, 1, 0);
    }

    public void stopObjectTracking() {
        native_sendcommand(HAL_OBJECT_TRACKING_STARTSTOP, 0, 0);
    }

    public void startTouchAutoFocus() {
        native_sendcommand(HAL_TOUCH_AF_STARTSTOP, 1, 0);
    }

    public void stopTouchAutoFocus() {
        native_sendcommand(HAL_TOUCH_AF_STARTSTOP, 0, 0);
    }

    public void startEffectRecording() {
        native_sendcommand(HAL_START_EFFECT_RECORDING, 0, 0);
    }

    public void stopEffectRecording() {
        native_sendcommand(HAL_STOP_EFFECT_RECORDING, 0, 0);
    }

    public void setProgramShift(int shiftValue) {
        native_sendcommand(HAL_SET_PROGRAM_SHIFT, shiftValue, 0);
    }

    public void setDefaultIMEI(int bIsDefaultIMEI) {
        native_sendcommand(HAL_SET_DEFAULT_IMEI, bIsDefaultIMEI, 0);
    }

    public void smartAutoS1_Push() {
        native_sendcommand(CAMERA_CMD_SMART_AUTO_S1_PUSH, 0, 0);
    }

    public void smartAutoS1_Release() {
        native_sendcommand(CAMERA_CMD_SMART_AUTO_S1_RELEASE, 0, 0);
    }

    public void startHistogram(boolean start) {
        if (start) {
            Log.v(TAG, "startHistogram");
            native_sendcommand(HISTOGRAM_START, 0, 0);
            return;
        }
        native_sendcommand(HISTOGRAM_STOP, 0, 0);
    }

    public void setHistogramIncrement(int pIncrement) {
        Log.v(TAG, "startHistogram");
        native_sendcommand(HISTOGRAM_SET_INCREMENT, pIncrement, 0);
    }

    public void setHistogramFrameSkipRate(int pFrameSkipRate) {
        Log.v(TAG, "startHistogram");
        native_sendcommand(HISTOGRAM_SET_SKIP_RATE, pFrameSkipRate, 0);
    }

    public void setFrontSensorMirror(boolean mirror, int orientation) {
        if (mirror) {
            native_sendcommand(HAL_SET_FRONT_SENSOR_MIRROR, 1, 0);
            native_sendcommand(SET_DISPLAY_ORIENTATION_MIRROR, orientation, 1);
            return;
        }
        native_sendcommand(HAL_SET_FRONT_SENSOR_MIRROR, 0, 0);
        native_sendcommand(SET_DISPLAY_ORIENTATION_MIRROR, orientation, 0);
    }

    public void startFaceZoom(int x, int y) {
        native_sendcommand(HAL_START_FACEZOOM, x, y);
    }

    public void stopFaceZoom() {
        native_sendcommand(HAL_STOP_FACEZOOM, 0, 0);
    }

    public void setFocusiconSize(int x) {
        native_sendcommand(HAL_SET_FOCUS_ICON_SIZE, x, 0);
    }

    public void informFaceOrientationToHAL(int faceorientation) {
        int IsLandscape;
        if (faceorientation == 90 || faceorientation == 270) {
            IsLandscape = 0;
        } else if (faceorientation == 0 || faceorientation == 180) {
            IsLandscape = 1;
        } else {
            Log.secE(TAG, "unknown face orientation");
            return;
        }
        native_sendcommand(HAL_SEND_FACE_ORIENTATION, IsLandscape, 0);
    }

    public void startContinuousAF() {
        native_sendcommand(HAL_START_CONTINUOUS_AF, 0, 0);
    }

    public void stopContinuousAF() {
        native_sendcommand(HAL_STOP_CONTINUOUS_AF, 0, 0);
    }

    public void setCancelAF_FocusArea(int focusValue) {
        native_sendcommand(HAL_CANCEL_AF_FOCUS_AREA, focusValue, 0);
    }

    public void setWBCustomValue() {
        native_sendcommand(CAMERA_CMD_GET_WB_CUSTOM_VALUE, 0, 0);
    }

    public void resetWBCustomValue() {
        native_sendcommand(CAMERA_CMD_RESET_WB_CUSTOM_VALUE, 0, 0);
    }

    public void setEffect(int pfilterId) {
        native_sendcommand(SET_EFFECT_FILTER, pfilterId, 0);
    }

    public void setEffect(String str) {
        native_setGenericParam(str);
    }

    public void setExternalEffect(boolean isExternal) {
        if (isExternal) {
            native_sendcommand(SET_EFFECT_EXTERNAL_MODE, 1, 0);
        } else {
            native_sendcommand(SET_EFFECT_EXTERNAL_MODE, 0, 0);
        }
    }

    public void setEffectSaveAsFlipped(int isSaveAsFlipped) {
        native_sendcommand(SET_EFFECT_SAVE_AS_FLIPPED, isSaveAsFlipped, 0);
    }

    public void setEffectSurfaceSize(int width, int height) {
        native_sendcommand(SET_EFFECT_SURFACE_SIZE, width, height);
    }

    public void setHDRYuvMode(boolean isYUV) {
        if (!isYUV) {
            native_sendcommand(HDR_SHOT_YUV_MODE_CHANGE, 0, 0);
        } else {
            native_sendcommand(HDR_SHOT_YUV_MODE_CHANGE, 1, 0);
        }
    }

    public void startGolfShot(int right_Left_handleFlag) {
        native_sendcommand(GOLF_SHOT_START, right_Left_handleFlag, 0);
    }

    public void stopGolfShot() {
        native_sendcommand(GOLF_SHOT_STOP, 0, 0);
    }

    public void saveGolfShot(int Storage) {
        native_sendcommand(GOLF_SHOT_SAVE, Storage, 0);
    }

    public void setThemeMask(int mask) {
        native_sendcommand(THEME_SHOT_MASK_SET, mask, 0);
    }

    public void setAutoLowLight(boolean setting) {
        if (!setting) {
            native_sendcommand(AUTO_LOW_LIGHT_SET, 0, 0);
        } else {
            native_sendcommand(AUTO_LOW_LIGHT_SET, 1, 0);
        }
    }

    public void startDramaShot() {
        native_sendcommand(DRAMA_SHOT_START, 0, 0);
    }

    public void stopDramaShot() {
        native_sendcommand(DRAMA_SHOT_STOP, 0, 0);
    }

    public void cancelDramaShot() {
        native_sendcommand(DRAMA_SHOT_CANCEL, 0, 0);
    }

    public void setDramaShotStorage(int Storage) {
        native_sendcommand(DRAMA_SHOT_STORAGE, Storage, 0);
    }

    public void setDramaShotMode(int mode) {
        native_sendcommand(DRAMA_SHOT_MODE, mode, 0);
    }

    public void setEffectCoordinate(int currnet, int destination) {
        native_sendcommand(SET_EFFECT_COORDINATE, currnet, destination);
    }

    public void setEffectLayerOrder(boolean isRearGoesBottom) {
        if (isRearGoesBottom) {
            native_sendcommand(SET_EFFECT_LAYER_ORDER, 0, 0);
        } else {
            native_sendcommand(SET_EFFECT_LAYER_ORDER, 1, 0);
        }
    }

    public void setDualShotMode(int isDualSyncMode) {
        native_sendcommand(SET_DUAL_MODE_SYNC, isDualSyncMode, 0);
    }

    public void startDualModeAsyncShot(boolean isCapture) {
        if (isCapture) {
            native_sendcommand(DUAL_MODE_SHOT_ASYNC_CAPTURE, 1, 0);
        } else {
            native_sendcommand(DUAL_MODE_SHOT_ASYNC_CAPTURE, CAMERA_FACE_DETECTION_SW_TWO_EYE, 0);
        }
    }

    public void setEffectVisible(boolean isVisible) {
        if (!isVisible) {
            native_sendcommand(SET_EFFECT_VISIBLE, 0, 0);
        } else {
            native_sendcommand(SET_EFFECT_VISIBLE, 1, 0);
        }
    }

    public void setEffectVisibleForRecording(boolean isVisible) {
        if (!isVisible) {
            native_sendcommand(SET_EFFECT_VISIBLE_FOR_RECORDING, 0, 0);
        } else {
            native_sendcommand(SET_EFFECT_VISIBLE_FOR_RECORDING, 1, 0);
        }
    }

    public void setEffectOrientation(int orientation) {
        native_sendcommand(SET_EFFECT_ORIENTATION, orientation, 0);
    }

    public void setEffectMode(int pMode) {
        Log.secI(TAG, "setEffectMode : " + pMode);
        native_sendcommand(SET_EFFECT_MODE, pMode, 0);
    }

    public void sendOrientaionInfotoHAL(int orientation) {
        native_sendcommand(DEVICE_ORIENTATION, orientation, 0);
    }

    public void setPreviewCallbackSize(int width, int height) {
        native_sendcommand(PREVIEW_CALLBACK_SIZE, width, height);
    }

    public void startFlashPopUp() {
        Log.secI(TAG, "setFlashPopUp : ");
        native_sendcommand(HAL_FLASH_POPUP, 0, 0);
    }

    public void setAFLampControl(int timeValue) {
        native_sendcommand(HAL_AF_LAMP_CONTROL, timeValue, 0);
    }

    public void setAFLampControl(boolean start) {
        if (start) {
            native_sendcommand(HAL_AF_LAMP_CONTROL, 1, 0);
        } else {
            native_sendcommand(HAL_AF_LAMP_CONTROL, 0, 0);
        }
    }

    public void setZoomStep(int step) {
        native_sendcommand(HAL_SET_ZOOM_STEP, step, 0);
    }

    public void setSoundAndShotMicControl(boolean start) {
        if (start) {
            native_sendcommand(HAL_SOUND_AND_SHOT_MIC_CONTROL, 1, 0);
        } else {
            native_sendcommand(HAL_SOUND_AND_SHOT_MIC_CONTROL, 0, 0);
        }
    }

    public void sendISPDCommand(int command, int arg1, int arg2) {
        native_sendcommand(command, arg1, arg2);
    }

    public void startSenserCleaning() {
        native_sendcommand(HAL_START_SENSER_CLEANING, 0, 0);
    }

    public void setManualFocusPosition(int position) {
        native_sendcommand(HAL_SET_MANUAL_FOCUS_POSITION, position, 0);
    }

    public void setIFunctionPush() {
        native_sendcommand(HAL_SET_IFUNCTION_PUSH, 0, 0);
    }

    public void setIFunctionRelease() {
        native_sendcommand(HAL_SET_IFUNCTION_RELEASE, 0, 0);
    }

    public void setIntervalShotManualFocus(int value) {
        native_sendcommand(HAL_SET_INTERVAL_SHOT_MANUAL_FOCUS, value, 0);
    }

    public void notifyQualityChanged(int value) {
        native_sendcommand(NOTIFY_QUALITY_CHANGED, value, 0);
    }

    static {
        System.loadLibrary("seccamera_jni");
    }

    public static Parameters getEmptyParameters() {
        SecCamera camera = new SecCamera();
        camera.getClass();
        return new Parameters();
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public class Size {
        public int height;
        public int width;

        public Size(int w, int h) {
            this.width = w;
            this.height = h;
        }

        public boolean equals(Object obj) {
            if (obj instanceof Size) {
                Size s = (Size) obj;
                return this.width == s.width && this.height == s.height;
            }
            return false;
        }

        public int hashCode() {
            return (this.width * 32713) + this.height;
        }
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public static class Area {
        public Rect rect;
        public int weight;

        public Area(Rect rect, int weight) {
            this.rect = rect;
            this.weight = weight;
        }

        public boolean equals(Object obj) {
            if (obj instanceof Area) {
                Area a = (Area) obj;
                if (this.rect == null) {
                    if (a.rect != null) {
                        return false;
                    }
                } else if (!this.rect.equals(a.rect)) {
                    return false;
                }
                return this.weight == a.weight;
            }
            return false;
        }
    }

    /* loaded from: D:\APK反向编译\三星C101相机\framework\seccamera.dex */
    public class Parameters {
        public static final String ANTIBANDING_50HZ = "50hz";
        public static final String ANTIBANDING_60HZ = "60hz";
        public static final String ANTIBANDING_AUTO = "auto";
        public static final String ANTIBANDING_OFF = "off";
        public static final String EFFECT_AQUA = "aqua";
        public static final String EFFECT_BLACKBOARD = "blackboard";
        public static final String EFFECT_MONO = "mono";
        public static final String EFFECT_NEGATIVE = "negative";
        public static final String EFFECT_NONE = "none";
        public static final String EFFECT_POSTERIZE = "posterize";
        public static final String EFFECT_SEPIA = "sepia";
        public static final String EFFECT_SOLARIZE = "solarize";
        public static final String EFFECT_WHITEBOARD = "whiteboard";
        private static final String FALSE = "false";
        public static final String FLASH_MODE_AUTO = "auto";
        public static final String FLASH_MODE_OFF = "off";
        public static final String FLASH_MODE_ON = "on";
        public static final String FLASH_MODE_RED_EYE = "red-eye";
        public static final String FLASH_MODE_TORCH = "torch";
        public static final int FOCUS_DISTANCE_FAR_INDEX = 2;
        public static final int FOCUS_DISTANCE_NEAR_INDEX = 0;
        public static final int FOCUS_DISTANCE_OPTIMAL_INDEX = 1;
        public static final String FOCUS_MODE_AUTO = "auto";
        public static final String FOCUS_MODE_CONTINUOUS_PICTURE = "continuous-picture";
        public static final String FOCUS_MODE_CONTINUOUS_VIDEO = "continuous-video";
        public static final String FOCUS_MODE_EDOF = "edof";
        public static final String FOCUS_MODE_FIXED = "fixed";
        public static final String FOCUS_MODE_INFINITY = "infinity";
        public static final String FOCUS_MODE_MACRO = "macro";
        private static final String KEY_ANTIBANDING = "antibanding";
        private static final String KEY_AUTO_EXPOSURE_LOCK = "auto-exposure-lock";
        private static final String KEY_AUTO_EXPOSURE_LOCK_SUPPORTED = "auto-exposure-lock-supported";
        private static final String KEY_AUTO_WHITEBALANCE_LOCK = "auto-whitebalance-lock";
        private static final String KEY_AUTO_WHITEBALANCE_LOCK_SUPPORTED = "auto-whitebalance-lock-supported";
        private static final String KEY_CITYID = "contextualtag-cityid";
        private static final String KEY_EFFECT = "effect";
        private static final String KEY_EXPOSURE_COMPENSATION = "exposure-compensation";
        private static final String KEY_EXPOSURE_COMPENSATION_STEP = "exposure-compensation-step";
        private static final String KEY_FLASH_MODE = "flash-mode";
        private static final String KEY_FOCAL_LENGTH = "focal-length";
        private static final String KEY_FOCUS_AREAS = "focus-areas";
        private static final String KEY_FOCUS_DISTANCES = "focus-distances";
        private static final String KEY_FOCUS_MODE = "focus-mode";
        private static final String KEY_GPS_ALTITUDE = "gps-altitude";
        private static final String KEY_GPS_LATITUDE = "gps-latitude";
        private static final String KEY_GPS_LONGITUDE = "gps-longitude";
        private static final String KEY_GPS_PROCESSING_METHOD = "gps-processing-method";
        private static final String KEY_GPS_TIMESTAMP = "gps-timestamp";
        private static final String KEY_HORIZONTAL_VIEW_ANGLE = "horizontal-view-angle";
        private static final String KEY_JPEG_QUALITY = "jpeg-quality";
        private static final String KEY_JPEG_THUMBNAIL_HEIGHT = "jpeg-thumbnail-height";
        private static final String KEY_JPEG_THUMBNAIL_QUALITY = "jpeg-thumbnail-quality";
        private static final String KEY_JPEG_THUMBNAIL_SIZE = "jpeg-thumbnail-size";
        private static final String KEY_JPEG_THUMBNAIL_WIDTH = "jpeg-thumbnail-width";
        private static final String KEY_MAX_EXPOSURE_COMPENSATION = "max-exposure-compensation";
        private static final String KEY_MAX_NUM_DETECTED_FACES_HW = "max-num-detected-faces-hw";
        private static final String KEY_MAX_NUM_DETECTED_FACES_SW = "max-num-detected-faces-sw";
        private static final String KEY_MAX_NUM_FOCUS_AREAS = "max-num-focus-areas";
        private static final String KEY_MAX_NUM_METERING_AREAS = "max-num-metering-areas";
        private static final String KEY_MAX_ZOOM = "max-zoom";
        private static final String KEY_METERING_AREAS = "metering-areas";
        private static final String KEY_MIN_EXPOSURE_COMPENSATION = "min-exposure-compensation";
        private static final String KEY_PICTURE_FORMAT = "picture-format";
        private static final String KEY_PICTURE_SIZE = "picture-size";
        private static final String KEY_PREFERRED_PREVIEW_SIZE_FOR_VIDEO = "preferred-preview-size-for-video";
        private static final String KEY_PREVIEW_FORMAT = "preview-format";
        private static final String KEY_PREVIEW_FPS_RANGE = "preview-fps-range";
        private static final String KEY_PREVIEW_FRAME_RATE = "preview-frame-rate";
        private static final String KEY_PREVIEW_SIZE = "preview-size";
        private static final String KEY_RECORDING_HINT = "recording-hint";
        private static final String KEY_ROTATION = "rotation";
        private static final String KEY_SCENE_MODE = "scene-mode";
        private static final String KEY_SMOOTH_ZOOM_SUPPORTED = "smooth-zoom-supported";
        private static final String KEY_VERTICAL_VIEW_ANGLE = "vertical-view-angle";
        private static final String KEY_VIDEO_SIZE = "video-size";
        private static final String KEY_VIDEO_SNAPSHOT_SUPPORTED = "video-snapshot-supported";
        private static final String KEY_VIDEO_STABILIZATION = "video-stabilization";
        private static final String KEY_VIDEO_STABILIZATION_SUPPORTED = "video-stabilization-supported";
        private static final String KEY_WEATHER = "contextualtag-weather";
        private static final String KEY_WHITE_BALANCE = "whitebalance";
        private static final String KEY_ZOOM = "zoom";
        private static final String KEY_ZOOM_RATIOS = "zoom-ratios";
        private static final String KEY_ZOOM_SUPPORTED = "zoom-supported";
        private static final String PIXEL_FORMAT_BAYER_RGGB = "bayer-rggb";
        private static final String PIXEL_FORMAT_JPEG = "jpeg";
        private static final String PIXEL_FORMAT_RGB565 = "rgb565";
        private static final String PIXEL_FORMAT_YUV420P = "yuv420p";
        private static final String PIXEL_FORMAT_YUV420SP = "yuv420sp";
        private static final String PIXEL_FORMAT_YUV422I = "yuv422i-yuyv";
        private static final String PIXEL_FORMAT_YUV422SP = "yuv422sp";
        public static final int PREVIEW_FPS_MAX_INDEX = 1;
        public static final int PREVIEW_FPS_MIN_INDEX = 0;
        public static final String SCENE_MODE_ACTION = "action";
        public static final String SCENE_MODE_AUTO = "auto";
        public static final String SCENE_MODE_BARCODE = "barcode";
        public static final String SCENE_MODE_BEACH = "beach";
        public static final String SCENE_MODE_CANDLELIGHT = "candlelight";
        public static final String SCENE_MODE_FIREWORKS = "fireworks";
        public static final String SCENE_MODE_HDR = "hdr";
        public static final String SCENE_MODE_LANDSCAPE = "landscape";
        public static final String SCENE_MODE_NIGHT = "night";
        public static final String SCENE_MODE_NIGHT_PORTRAIT = "night-portrait";
        public static final String SCENE_MODE_PARTY = "party";
        public static final String SCENE_MODE_PORTRAIT = "portrait";
        public static final String SCENE_MODE_SNOW = "snow";
        public static final String SCENE_MODE_SPORTS = "sports";
        public static final String SCENE_MODE_STEADYPHOTO = "steadyphoto";
        public static final String SCENE_MODE_SUNSET = "sunset";
        public static final String SCENE_MODE_THEATRE = "theatre";
        private static final String SUPPORTED_VALUES_SUFFIX = "-values";
        private static final String TRUE = "true";
        public static final String WHITE_BALANCE_AUTO = "auto";
        public static final String WHITE_BALANCE_CLOUDY_DAYLIGHT = "cloudy-daylight";
        public static final String WHITE_BALANCE_DAYLIGHT = "daylight";
        public static final String WHITE_BALANCE_FLUORESCENT = "fluorescent";
        public static final String WHITE_BALANCE_INCANDESCENT = "incandescent";
        public static final String WHITE_BALANCE_SHADE = "shade";
        public static final String WHITE_BALANCE_TWILIGHT = "twilight";
        public static final String WHITE_BALANCE_WARM_FLUORESCENT = "warm-fluorescent";
        private ConcurrentHashMap<String, String> mMap;

        private Parameters() {
            this.mMap = new ConcurrentHashMap<>();
        }

        public void dump() {
            Log.e(SecCamera.TAG, "dump: size=" + this.mMap.size());
            for (String k : this.mMap.keySet()) {
                Log.e(SecCamera.TAG, "dump: " + k + "=" + this.mMap.get(k));
            }
        }

        public String flatten() {
            StringBuilder flattened = new StringBuilder((int) SecCamera.CAMERA_MSG_RAW_IMAGE);
            for (String k : this.mMap.keySet()) {
                flattened.append(k);
                flattened.append("=");
                flattened.append(this.mMap.get(k));
                flattened.append(";");
            }
            flattened.deleteCharAt(flattened.length() + SecCamera.CAMERA_MSG_ALL_MSGS);
            return flattened.toString();
        }

        public void unflatten(String flattened) {
            this.mMap.clear();
            TextUtils.StringSplitter<String> splitter = new TextUtils.SimpleStringSplitter(';');
            splitter.setString(flattened);
            for (String kv : splitter) {
                int pos = kv.indexOf(61);
                if (pos != SecCamera.CAMERA_MSG_ALL_MSGS) {
                    String k = kv.substring(0, pos);
                    String v = kv.substring(pos + 1);
                    this.mMap.put(k, v);
                }
            }
        }

        public void remove(String key) {
            this.mMap.remove(key);
        }

        public void set(String key, String value) {
            if (key.indexOf(61) != SecCamera.CAMERA_MSG_ALL_MSGS || key.indexOf(59) != SecCamera.CAMERA_MSG_ALL_MSGS || key.indexOf(0) != SecCamera.CAMERA_MSG_ALL_MSGS) {
                Log.e(SecCamera.TAG, "Key \"" + key + "\" contains invalid character (= or ; or \\0)");
            } else if (value.indexOf(61) != SecCamera.CAMERA_MSG_ALL_MSGS || value.indexOf(59) != SecCamera.CAMERA_MSG_ALL_MSGS || value.indexOf(0) != SecCamera.CAMERA_MSG_ALL_MSGS) {
                Log.e(SecCamera.TAG, "Value \"" + value + "\" contains invalid character (= or ; or \\0)");
            } else {
                this.mMap.put(key, value);
            }
        }

        public void set(String key, int value) {
            this.mMap.put(key, Integer.toString(value));
        }

        private void set(String key, List<Area> areas) {
            if (areas == null) {
                set(key, "(0,0,0,0,0)");
                return;
            }
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < areas.size(); i++) {
                Area area = areas.get(i);
                Rect rect = area.rect;
                buffer.append('(');
                buffer.append(rect.left);
                buffer.append(',');
                buffer.append(rect.top);
                buffer.append(',');
                buffer.append(rect.right);
                buffer.append(',');
                buffer.append(rect.bottom);
                buffer.append(',');
                buffer.append(area.weight);
                buffer.append(')');
                if (i != areas.size() + SecCamera.CAMERA_MSG_ALL_MSGS) {
                    buffer.append(',');
                }
            }
            set(key, buffer.toString());
        }

        public String get(String key) {
            return this.mMap.get(key);
        }

        public int getInt(String key) {
            return Integer.parseInt(this.mMap.get(key));
        }

        public void setPreviewSize(int width, int height) {
            String v = Integer.toString(width) + "x" + Integer.toString(height);
            set(KEY_PREVIEW_SIZE, v);
        }

        public Size getPreviewSize() {
            String pair = get(KEY_PREVIEW_SIZE);
            return strToSize(pair);
        }

        public List<Size> getSupportedPreviewSizes() {
            String str = get("preview-size-values");
            return splitSize(str);
        }

        public List<Size> getSupportedVideoSizes() {
            String str = get("video-size-values");
            return splitSize(str);
        }

        public Size getPreferredPreviewSizeForVideo() {
            String pair = get(KEY_PREFERRED_PREVIEW_SIZE_FOR_VIDEO);
            return strToSize(pair);
        }

        public void setJpegThumbnailSize(int width, int height) {
            set(KEY_JPEG_THUMBNAIL_WIDTH, width);
            set(KEY_JPEG_THUMBNAIL_HEIGHT, height);
        }

        public Size getJpegThumbnailSize() {
            return new Size(getInt(KEY_JPEG_THUMBNAIL_WIDTH), getInt(KEY_JPEG_THUMBNAIL_HEIGHT));
        }

        public List<Size> getSupportedJpegThumbnailSizes() {
            String str = get("jpeg-thumbnail-size-values");
            return splitSize(str);
        }

        public void setJpegThumbnailQuality(int quality) {
            set(KEY_JPEG_THUMBNAIL_QUALITY, quality);
        }

        public int getJpegThumbnailQuality() {
            return getInt(KEY_JPEG_THUMBNAIL_QUALITY);
        }

        public void setJpegQuality(int quality) {
            set(KEY_JPEG_QUALITY, quality);
        }

        public int getJpegQuality() {
            return getInt(KEY_JPEG_QUALITY);
        }

        @Deprecated
        public void setPreviewFrameRate(int fps) {
            set(KEY_PREVIEW_FRAME_RATE, fps);
        }

        @Deprecated
        public int getPreviewFrameRate() {
            return getInt(KEY_PREVIEW_FRAME_RATE);
        }

        @Deprecated
        public List<Integer> getSupportedPreviewFrameRates() {
            String str = get("preview-frame-rate-values");
            return splitInt(str);
        }

        public void setPreviewFpsRange(int min, int max) {
            set(KEY_PREVIEW_FPS_RANGE, "" + min + "," + max);
        }

        public void getPreviewFpsRange(int[] range) {
            if (range == null || range.length != 2) {
                throw new IllegalArgumentException("range must be an array with two elements.");
            }
            splitInt(get(KEY_PREVIEW_FPS_RANGE), range);
        }

        public List<int[]> getSupportedPreviewFpsRange() {
            String str = get("preview-fps-range-values");
            return splitRange(str);
        }

        public void setPreviewFormat(int pixel_format) {
            String s = cameraFormatForPixelFormat(pixel_format);
            if (s == null) {
                throw new IllegalArgumentException("Invalid pixel_format=" + pixel_format);
            }
            set(KEY_PREVIEW_FORMAT, s);
        }

        public int getPreviewFormat() {
            return pixelFormatForCameraFormat(get(KEY_PREVIEW_FORMAT));
        }

        public List<Integer> getSupportedPreviewFormats() {
            String str = get("preview-format-values");
            ArrayList<Integer> formats = new ArrayList<>();
            Iterator i$ = split(str).iterator();
            while (i$.hasNext()) {
                String s = i$.next();
                int f = pixelFormatForCameraFormat(s);
                if (f != 0) {
                    formats.add(Integer.valueOf(f));
                }
            }
            return formats;
        }

        public void setPictureSize(int width, int height) {
            String v = Integer.toString(width) + "x" + Integer.toString(height);
            set(KEY_PICTURE_SIZE, v);
        }

        public Size getPictureSize() {
            String pair = get(KEY_PICTURE_SIZE);
            return strToSize(pair);
        }

        public List<Size> getSupportedPictureSizes() {
            String str = get("picture-size-values");
            return splitSize(str);
        }

        public void setPictureFormat(int pixel_format) {
            String s = cameraFormatForPixelFormat(pixel_format);
            if (s == null) {
                throw new IllegalArgumentException("Invalid pixel_format=" + pixel_format);
            }
            set(KEY_PICTURE_FORMAT, s);
        }

        public int getPictureFormat() {
            return pixelFormatForCameraFormat(get(KEY_PICTURE_FORMAT));
        }

        public List<Integer> getSupportedPictureFormats() {
            String str = get("picture-format-values");
            ArrayList<Integer> formats = new ArrayList<>();
            Iterator i$ = split(str).iterator();
            while (i$.hasNext()) {
                String s = i$.next();
                int f = pixelFormatForCameraFormat(s);
                if (f != 0) {
                    formats.add(Integer.valueOf(f));
                }
            }
            return formats;
        }

        private String cameraFormatForPixelFormat(int pixel_format) {
            switch (pixel_format) {
                case SecCamera.PANORAMA_DIRECTION_UP /* 4 */:
                    return PIXEL_FORMAT_RGB565;
                case SecCamera.CAMERA_MSG_PREVIEW_FRAME /* 16 */:
                    return PIXEL_FORMAT_YUV422SP;
                case 17:
                    return PIXEL_FORMAT_YUV420SP;
                case 20:
                    return PIXEL_FORMAT_YUV422I;
                case SecCamera.CAMERA_MSG_COMPRESSED_IMAGE /* 256 */:
                    return PIXEL_FORMAT_JPEG;
                case SecCamera.CAMERA_MSG_RAW_IMAGE_NOTIFY /* 512 */:
                    return PIXEL_FORMAT_BAYER_RGGB;
                case 842094169:
                    return PIXEL_FORMAT_YUV420P;
                default:
                    return null;
            }
        }

        private int pixelFormatForCameraFormat(String format) {
            if (format == null) {
                return 0;
            }
            if (format.equals(PIXEL_FORMAT_YUV422SP)) {
                return SecCamera.CAMERA_MSG_PREVIEW_FRAME;
            }
            if (format.equals(PIXEL_FORMAT_YUV420SP)) {
                return 17;
            }
            if (format.equals(PIXEL_FORMAT_YUV422I)) {
                return 20;
            }
            if (format.equals(PIXEL_FORMAT_YUV420P)) {
                return 842094169;
            }
            if (format.equals(PIXEL_FORMAT_RGB565)) {
                return 4;
            }
            if (format.equals(PIXEL_FORMAT_JPEG)) {
                return SecCamera.CAMERA_MSG_COMPRESSED_IMAGE;
            }
            return 0;
        }

        public void setRotation(int rotation) {
            if (rotation == 0 || rotation == 90 || rotation == 180 || rotation == 270) {
                set(KEY_ROTATION, Integer.toString(rotation));
                return;
            }
            throw new IllegalArgumentException("Invalid rotation=" + rotation);
        }

        public void setWeather(int weather) {
            set(KEY_WEATHER, weather);
        }

        public void setCityId(long cityId) {
            set(KEY_CITYID, Long.toString(cityId));
        }

        public void setGpsLatitude(double latitude) {
            set(KEY_GPS_LATITUDE, Double.toString(latitude));
        }

        public void setGpsLongitude(double longitude) {
            set(KEY_GPS_LONGITUDE, Double.toString(longitude));
        }

        public void setGpsAltitude(double altitude) {
            set(KEY_GPS_ALTITUDE, Double.toString(altitude));
        }

        public void setGpsTimestamp(long timestamp) {
            set(KEY_GPS_TIMESTAMP, Long.toString(timestamp));
        }

        public void setGpsProcessingMethod(String processing_method) {
            set(KEY_GPS_PROCESSING_METHOD, processing_method);
        }

        public void removeGpsData() {
            remove(KEY_GPS_LATITUDE);
            remove(KEY_GPS_LONGITUDE);
            remove(KEY_GPS_ALTITUDE);
            remove(KEY_GPS_TIMESTAMP);
            remove(KEY_GPS_PROCESSING_METHOD);
        }

        public String getWhiteBalance() {
            return get(KEY_WHITE_BALANCE);
        }

        public void setWhiteBalance(String value) {
            String oldValue = get(KEY_WHITE_BALANCE);
            if (!same(value, oldValue)) {
                set(KEY_WHITE_BALANCE, value);
                set(KEY_AUTO_WHITEBALANCE_LOCK, FALSE);
            }
        }

        public List<String> getSupportedWhiteBalance() {
            String str = get("whitebalance-values");
            return split(str);
        }

        public String getColorEffect() {
            return get(KEY_EFFECT);
        }

        public void setColorEffect(String value) {
            set(KEY_EFFECT, value);
        }

        public List<String> getSupportedColorEffects() {
            String str = get("effect-values");
            return split(str);
        }

        public String getAntibanding() {
            return get(KEY_ANTIBANDING);
        }

        public void setAntibanding(String antibanding) {
            set(KEY_ANTIBANDING, antibanding);
        }

        public List<String> getSupportedAntibanding() {
            String str = get("antibanding-values");
            return split(str);
        }

        public String getSceneMode() {
            return get(KEY_SCENE_MODE);
        }

        public void setSceneMode(String value) {
            set(KEY_SCENE_MODE, value);
        }

        public List<String> getSupportedSceneModes() {
            String str = get("scene-mode-values");
            return split(str);
        }

        public String getFlashMode() {
            return get(KEY_FLASH_MODE);
        }

        public void setFlashMode(String value) {
            set(KEY_FLASH_MODE, value);
        }

        public List<String> getSupportedFlashModes() {
            String str = get("flash-mode-values");
            return split(str);
        }

        public String getFocusMode() {
            return get(KEY_FOCUS_MODE);
        }

        public void setFocusMode(String value) {
            set(KEY_FOCUS_MODE, value);
        }

        public List<String> getSupportedFocusModes() {
            String str = get("focus-mode-values");
            return split(str);
        }

        public float getFocalLength() {
            return Float.parseFloat(get(KEY_FOCAL_LENGTH));
        }

        public float getHorizontalViewAngle() {
            return Float.parseFloat(get(KEY_HORIZONTAL_VIEW_ANGLE));
        }

        public float getVerticalViewAngle() {
            return Float.parseFloat(get(KEY_VERTICAL_VIEW_ANGLE));
        }

        public int getExposureCompensation() {
            return getInt(KEY_EXPOSURE_COMPENSATION, 0);
        }

        public void setExposureCompensation(int value) {
            set(KEY_EXPOSURE_COMPENSATION, value);
        }

        public int getMaxExposureCompensation() {
            return getInt(KEY_MAX_EXPOSURE_COMPENSATION, 0);
        }

        public int getMinExposureCompensation() {
            return getInt(KEY_MIN_EXPOSURE_COMPENSATION, 0);
        }

        public float getExposureCompensationStep() {
            return getFloat(KEY_EXPOSURE_COMPENSATION_STEP, 0.0f);
        }

        public void setAutoExposureLock(boolean toggle) {
            set(KEY_AUTO_EXPOSURE_LOCK, toggle ? TRUE : FALSE);
        }

        public boolean getAutoExposureLock() {
            String str = get(KEY_AUTO_EXPOSURE_LOCK);
            return TRUE.equals(str);
        }

        public boolean isAutoExposureLockSupported() {
            String str = get(KEY_AUTO_EXPOSURE_LOCK_SUPPORTED);
            return TRUE.equals(str);
        }

        public void setAutoWhiteBalanceLock(boolean toggle) {
            set(KEY_AUTO_WHITEBALANCE_LOCK, toggle ? TRUE : FALSE);
        }

        public boolean getAutoWhiteBalanceLock() {
            String str = get(KEY_AUTO_WHITEBALANCE_LOCK);
            return TRUE.equals(str);
        }

        public boolean isAutoWhiteBalanceLockSupported() {
            String str = get(KEY_AUTO_WHITEBALANCE_LOCK_SUPPORTED);
            return TRUE.equals(str);
        }

        public int getZoom() {
            return getInt(KEY_ZOOM, 0);
        }

        public void setZoom(int value) {
            set(KEY_ZOOM, value);
        }

        public boolean isZoomSupported() {
            String str = get(KEY_ZOOM_SUPPORTED);
            return TRUE.equals(str);
        }

        public int getMaxZoom() {
            return getInt(KEY_MAX_ZOOM, 0);
        }

        public List<Integer> getZoomRatios() {
            return splitInt(get(KEY_ZOOM_RATIOS));
        }

        public boolean isSmoothZoomSupported() {
            String str = get(KEY_SMOOTH_ZOOM_SUPPORTED);
            return TRUE.equals(str);
        }

        public void getFocusDistances(float[] output) {
            if (output == null || output.length != SecCamera.CAMERA_FACE_DETECTION_SW_TWO_EYE) {
                throw new IllegalArgumentException("output must be a float array with three elements.");
            }
            splitFloat(get(KEY_FOCUS_DISTANCES), output);
        }

        public int getMaxNumFocusAreas() {
            return getInt(KEY_MAX_NUM_FOCUS_AREAS, 0);
        }

        public List<Area> getFocusAreas() {
            return splitArea(get(KEY_FOCUS_AREAS));
        }

        public void setFocusAreas(List<Area> focusAreas) {
            set(KEY_FOCUS_AREAS, focusAreas);
        }

        public int getMaxNumMeteringAreas() {
            return getInt(KEY_MAX_NUM_METERING_AREAS, 0);
        }

        public List<Area> getMeteringAreas() {
            return splitArea(get(KEY_METERING_AREAS));
        }

        public void setMeteringAreas(List<Area> meteringAreas) {
            set(KEY_METERING_AREAS, meteringAreas);
        }

        public int getMaxNumDetectedFaces() {
            return getInt(KEY_MAX_NUM_DETECTED_FACES_HW, 0);
        }

        public void setRecordingHint(boolean hint) {
            set(KEY_RECORDING_HINT, hint ? TRUE : FALSE);
        }

        public boolean isVideoSnapshotSupported() {
            String str = get(KEY_VIDEO_SNAPSHOT_SUPPORTED);
            return TRUE.equals(str);
        }

        public void setVideoStabilization(boolean toggle) {
            set(KEY_VIDEO_STABILIZATION, toggle ? TRUE : FALSE);
        }

        public boolean getVideoStabilization() {
            String str = get(KEY_VIDEO_STABILIZATION);
            return TRUE.equals(str);
        }

        public boolean isVideoStabilizationSupported() {
            String str = get(KEY_VIDEO_STABILIZATION_SUPPORTED);
            return TRUE.equals(str);
        }

        private ArrayList<String> split(String str) {
            if (str == null) {
                return null;
            }
            TextUtils.StringSplitter<String> splitter = new TextUtils.SimpleStringSplitter(',');
            splitter.setString(str);
            ArrayList<String> substrings = new ArrayList<>();
            for (String s : splitter) {
                substrings.add(s);
            }
            return substrings;
        }

        private ArrayList<Integer> splitInt(String str) {
            if (str == null) {
                return null;
            }
            TextUtils.StringSplitter<String> splitter = new TextUtils.SimpleStringSplitter(',');
            splitter.setString(str);
            ArrayList<Integer> substrings = new ArrayList<>();
            for (String s : splitter) {
                substrings.add(Integer.valueOf(Integer.parseInt(s)));
            }
            if (substrings.size() == 0) {
                return null;
            }
            return substrings;
        }

        private void splitInt(String str, int[] output) {
            if (str != null) {
                TextUtils.StringSplitter<String> splitter = new TextUtils.SimpleStringSplitter(',');
                splitter.setString(str);
                int index = 0;
                for (String s : splitter) {
                    output[index] = Integer.parseInt(s);
                    index++;
                }
            }
        }

        private void splitFloat(String str, float[] output) {
            if (str != null) {
                TextUtils.StringSplitter<String> splitter = new TextUtils.SimpleStringSplitter(',');
                splitter.setString(str);
                int index = 0;
                for (String s : splitter) {
                    output[index] = Float.parseFloat(s);
                    index++;
                }
            }
        }

        private float getFloat(String key, float defaultValue) {
            try {
                float defaultValue2 = Float.parseFloat(this.mMap.get(key));
                return defaultValue2;
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }

        private int getInt(String key, int defaultValue) {
            try {
                int defaultValue2 = Integer.parseInt(this.mMap.get(key));
                return defaultValue2;
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }

        private ArrayList<Size> splitSize(String str) {
            if (str == null) {
                return null;
            }
            TextUtils.StringSplitter<String> splitter = new TextUtils.SimpleStringSplitter(',');
            splitter.setString(str);
            ArrayList<Size> sizeList = new ArrayList<>();
            for (String s : splitter) {
                Size size = strToSize(s);
                if (size != null) {
                    sizeList.add(size);
                }
            }
            if (sizeList.size() == 0) {
                return null;
            }
            return sizeList;
        }

        private Size strToSize(String str) {
            if (str == null) {
                return null;
            }
            int pos = str.indexOf(120);
            if (pos != SecCamera.CAMERA_MSG_ALL_MSGS) {
                String width = str.substring(0, pos);
                String height = str.substring(pos + 1);
                return new Size(Integer.parseInt(width), Integer.parseInt(height));
            }
            Log.e(SecCamera.TAG, "Invalid size parameter string=" + str);
            return null;
        }

        private ArrayList<int[]> splitRange(String str) {
            int endIndex;
            if (str == null || str.charAt(0) != '(' || str.charAt(str.length() + SecCamera.CAMERA_MSG_ALL_MSGS) != ')') {
                Log.e(SecCamera.TAG, "Invalid range list string=" + str);
                return null;
            }
            ArrayList<int[]> rangeList = new ArrayList<>();
            int fromIndex = 1;
            do {
                int[] range = new int[2];
                endIndex = str.indexOf("),(", fromIndex);
                if (endIndex == SecCamera.CAMERA_MSG_ALL_MSGS) {
                    endIndex = str.length() + SecCamera.CAMERA_MSG_ALL_MSGS;
                }
                splitInt(str.substring(fromIndex, endIndex), range);
                rangeList.add(range);
                fromIndex = endIndex + SecCamera.CAMERA_FACE_DETECTION_SW_TWO_EYE;
            } while (endIndex != str.length() + SecCamera.CAMERA_MSG_ALL_MSGS);
            if (rangeList.size() == 0) {
                return null;
            }
            return rangeList;
        }

        private ArrayList<Area> splitArea(String str) {
            int endIndex;
            if (str == null || str.charAt(0) != '(' || str.charAt(str.length() + SecCamera.CAMERA_MSG_ALL_MSGS) != ')') {
                Log.e(SecCamera.TAG, "Invalid area string=" + str);
                return null;
            }
            ArrayList<Area> result = new ArrayList<>();
            int fromIndex = 1;
            int[] array = new int[5];
            do {
                endIndex = str.indexOf("),(", fromIndex);
                if (endIndex == SecCamera.CAMERA_MSG_ALL_MSGS) {
                    endIndex = str.length() + SecCamera.CAMERA_MSG_ALL_MSGS;
                }
                splitInt(str.substring(fromIndex, endIndex), array);
                result.add(new Area(new Rect(array[0], array[1], array[2], array[SecCamera.CAMERA_FACE_DETECTION_SW_TWO_EYE]), array[4]));
                fromIndex = endIndex + SecCamera.CAMERA_FACE_DETECTION_SW_TWO_EYE;
            } while (endIndex != str.length() + SecCamera.CAMERA_MSG_ALL_MSGS);
            if (result.size() == 0) {
                return null;
            }
            if (result.size() == 1) {
                Area area = result.get(0);
                Rect rect = area.rect;
                if (rect.left == 0 && rect.top == 0 && rect.right == 0 && rect.bottom == 0 && area.weight == 0) {
                    return null;
                }
                return result;
            }
            return result;
        }

        private boolean same(String s1, String s2) {
            if (s1 == null && s2 == null) {
                return true;
            }
            return s1 != null && s1.equals(s2);
        }
    }
}