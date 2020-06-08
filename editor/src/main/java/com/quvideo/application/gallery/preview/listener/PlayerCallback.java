package com.quvideo.application.gallery.preview.listener;

/**
 * Create by zhengjunfei on 2019/9/18
 */
public interface PlayerCallback {
  void onStartListener();

  void onPauseListener();

  default void onProgresslistener(int time) {
  }

  default boolean isRotateVertical(){
    return false;
  }

  void onCompleteListener();

  void onErrListener(int what, int extra);

  default void onVideoSizeChanged(int vWidth, int vHeight) {
  }

  default void onTextureDestory() {
  }

  default void onRotateStart(){

  }

  default void onRotateEnd(){

  }
}
