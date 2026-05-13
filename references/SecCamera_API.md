# SecCamera 系统接口文档（基于 `SecCamera.java`）

> 来源：`references/SecCamera.java`（三星 framework 反编译代码）

## 1. 概述

`com.sec.android.seccamera.SecCamera` 是对 Android `Camera` 的三星扩展封装，提供：

- 相机打开/关闭、预览、拍照、对焦、参数控制。
- 多种厂商拍摄模式（全景、3D 全景、连拍、HDR、Drama、Golf、Beauty、Dual 等）。
- 事件回调机制（普通 camera callback + Samsung 自定义扩展消息）。
- HAL 命令透传（`native_sendcommand` + 各类包装方法）。

---

## 2. 类与命名空间

- 包名：`com.sec.android.seccamera`
- 主类：`SecCamera`
- 重要嵌套类型：`SecCamera.Parameters`、`SecCamera.Size`、`SecCamera.Area`、`SecCamera.EventHandler`

---

## 3. 生命周期与基础能力

### 3.1 相机实例创建

- `static SecCamera open()`
- `static SecCamera open(int cameraId)`
- `static SecCamera open(int cameraId, int priority)`
- `static SecCamera open(int cameraId, int priority, Looper looper)`
- `static SecCamera open(int cameraId, int priority, Looper looper, boolean halsetting)`
- `static native int getNumberOfCameras()`
- `static void getCameraInfo(int cameraId, CameraInfo cameraInfo)`

### 3.2 资源释放与连接

- `native_release()` / `release()`
- `lock()` / `unlock()`
- `reconnect()`

### 3.3 预览控制

- `setPreviewDisplay(SurfaceHolder holder)`
- `setPreviewTexture(SurfaceTexture surfaceTexture)`
- `startPreview()` / `stopPreview()`
- `previewEnabled()`
- `setDisplayOrientation(int i)`

---

## 4. 拍照与对焦

### 4.1 对焦

- `autoFocus(AutoFocusCallback cb)`
- `cancelAutoFocus()`
- `setAutoFocusMoveCallback(AutoFocusMoveCallback cb)`

### 4.2 拍照

- `takePicture(ShutterCallback shutter, PictureCallback raw, PictureCallback jpeg)`
- `takePicture(ShutterCallback shutter, PictureCallback raw, PictureCallback postview, PictureCallback jpeg)`
- `recordingTakePicture()`
- `setRecordingTakePictureCallback(...)`

### 4.3 缩放

- `startSmoothZoom(int i)` / `stopSmoothZoom()`
- `setZoomChangeListener(OnZoomChangeListener listener)`
- `setZoomStep(int step)`

---

## 5. 预览帧回调与缓冲

- `setPreviewCallback(PreviewCallback cb)`
- `setOneShotPreviewCallback(PreviewCallback cb)`
- `setPreviewCallbackWithBuffer(PreviewCallback cb)`
- `setPreviewCallbackWithBufferNoDisable(PreviewCallback cb)`
- `addCallbackBuffer(byte[] callbackBuffer)`
- `addRawImageCallbackBuffer(byte[] callbackBuffer)`
- `setHasPreviewCallback(boolean z, boolean z2, boolean z3)`（native）

---

## 6. 参数系统（`SecCamera.Parameters`）

`Parameters` 提供 key-value 形式参数管理，兼容 Android Camera 参数风格：

- 序列化：`flatten()` / `unflatten(String)`
- 通用读写：`set(String, String)`、`set(String, int)`、`get(String)`、`getInt(String)`、`remove(String)`
- 典型能力：
  - 预览尺寸：`setPreviewSize/getPreviewSize/getSupportedPreviewSizes`
  - 视频尺寸：`getSupportedVideoSizes`
  - 帧率：`setPreviewFrameRate/getSupportedPreviewFrameRates/setPreviewFpsRange`
  - JPEG：`setJpegQuality`、`setJpegThumbnailSize` 等

---

## 7. Samsung 扩展拍摄模式接口

以下为典型扩展能力（实际是否生效依赖底层 HAL/机型）：

- 全景：`startPanorama`、`cancelPanorama`
- 3D 全景：`start3DPanorama`、`cancel3DPanorama`
- 连拍：`startContinuousShot`、`terminateContinuousShot`
- Burst：`startBurstShot`、`captureBurstShot`、`terminateBurstShot`
- Action Shot：`initializeActionShot`、`startSeriesActionShot`、`finishActionShot`
- AddMe：`initializeAddMeShot`、`startCaptureAddMeShot`、`finishAddMeShot`
- MultiFrame：`startMultiFrameShot`、`captureMultiFrameShot`、`terminateMultiFrameShot`
- Beauty：`setFaceRetouchLevel`、`setBeautyEffect`、`setBeautyShotManualMode`
- HDR：`setHDRModeLevel`、`setHDRYuvMode`
- Golf：`startGolfShot`、`stopGolfShot`、`saveGolfShot`
- Drama：`startDramaShot`、`stopDramaShot`、`cancelDramaShot`
- Dual：`setDualShotMode`、`startDualModeAsyncShot`

---

## 8. 检测/跟踪能力

- 人脸检测：
  - `startFaceDetection()`（硬件）
  - `startFaceDetectionSW()` / `startSamsungFaceDetectionSW()`（软件）
  - `stopFaceDetection()`
- 微笑检测：`startSmileDetection(boolean)`
- 宠物检测：`startPetDetection()` / `stopPetDetection()`
- 摄影师检测：`startPhotoGrapherDetection(...)`、`setPhotoGrapherDetectionArea(...)`
- 目标跟踪：`setObjectTrackingPosition(...)`、`startObjectTracking()`、`stopObjectTracking()`

---

## 9. HAL 扩展与底层命令

核心原语：

- `native_sendcommand(int cmd, int arg1, int arg2)`

大量高级 API 均是该方法的语义封装（例如：`setAFLampControl`、`startEffectRecording`、`setManualFocusPosition`、`setAEAWBLockState` 等），命令字对应内部常量（如 `HAL_*`、`*_SHOT_*`）。

---

## 10. 事件回调体系

### 10.1 标准 Camera 回调

- `ShutterCallback`
- `PictureCallback`
- `PreviewCallback`
- `AutoFocusCallback`
- `AutoFocusMoveCallback`
- `OnZoomChangeListener`
- `FaceDetectionListener`
- `ErrorCallback`

### 10.2 Samsung 扩展回调注册方法（示例）

- `setOnPanoramaEventListener(...)`
- `setOn3DPanoramaEventListener(...)`
- `setOnContinuousShotEventListener(...)`
- `setOnBurstShotEventListener(...)`
- `setOnHDRShotEventListener(...)`
- `setOnMagicFrameShotEventListener(...)`
- `setOnPIPShotEventListener(...)`
- `setOnMultiFrameShotEventListener(...)`
- `setOnGolfShotEventListener(...)`
- `setOnDramaShotEventListener(...)`
- `setOnBeautyShotEventListener(...)`
- `setOnDualEventListener(...)`
- `setOnHistogramEventListener(...)`

### 10.3 消息分发

- 内部通过 `EventHandler extends Handler` 处理 `CAMERA_MSG_*` 与 `CHECK_MARKER_OF_SAMSUNG_DEFINED_CALLBACK_MSGS` 范围内扩展消息。

---

## 11. 关键常量（节选）

### 11.1 错误码

- `CAMERA_ERROR_UNKNOWN = 1`
- `CAMERA_ERROR_SERVER_DIED = 100`
- `CAMERA_ERROR_PRIORITY_DIED = 200`
- `CAMERA_ERROR_PREVIEWFRAME_TIMEOUT = 1001`

### 11.2 广播 Action

- `ACTION_NEW_PICTURE = "android.hardware.action.NEW_PICTURE"`
- `ACTION_NEW_VIDEO = "android.hardware.action.NEW_VIDEO"`

### 11.3 全景方向

- `PANORAMA_DIRECTION_RIGHT/LEFT/UP/DOWN`
- `PANORAMA_DIRECTION_UP_RIGHT/UP_LEFT/DOWN_RIGHT/DOWN_LEFT`

---

## 12. 使用建议与注意事项

1. **兼容性**：该接口为 Samsung 私有扩展，不保证在非目标机型/系统版本可用。
2. **权限与线程**：预览、拍照、回调通常要求相机线程/Looper 正确配置。
3. **接口稳定性**：此文件来源于反编译 framework，方法命名与行为可能随 ROM 版本变化。
4. **错误处理**：务必注册 `ErrorCallback`，并处理 `SERVER_DIED` 等错误。
5. **参数兜底**：设置扩展参数前先读取 `getParameters()` 并判定支持性。

---

## 13. 快速索引（建议用于二次开发）

- 生命周期：`open/release/reconnect/lock/unlock`
- 预览：`setPreviewDisplay/setPreviewTexture/startPreview/stopPreview`
- 拍照：`takePicture/recordingTakePicture`
- 参数：`setParameters/getParameters/getEmptyParameters`
- 检测：`startFaceDetection/startSmileDetection/startPetDetection`
- 模式：`startPanorama/startBurstShot/startDramaShot/startGolfShot`
- HAL：`native_sendcommand`

