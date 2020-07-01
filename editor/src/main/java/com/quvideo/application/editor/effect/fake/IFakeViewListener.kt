package com.quvideo.application.editor.effect.fake

import android.graphics.PointF

interface IFakeViewListener {

  /** 移动中 */
  fun onEffectMoving()

  /** 开始移动 */
  fun onEffectMoveStart()

  /** 结束移动 */
  fun onEffectMoveEnd(moved: Boolean)

  /** 判断是否有Effect选中 */
  fun checkEffectTouchHit(pointF: PointF)
}