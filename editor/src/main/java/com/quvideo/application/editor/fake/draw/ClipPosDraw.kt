package com.quvideo.application.editor.fake.draw

import android.graphics.Canvas
import com.quvideo.application.editor.fake.FakePosInfo
import com.quvideo.application.editor.fake.IFakeDraw

/**
 * @author wuzhongyou
 * @date 2020/6/24.
 */
class ClipPosDraw : IFakeDraw() {

  override fun drawView(
    canvas: Canvas,
    fakePosInfo: FakePosInfo
  ) {
  }

  override fun supportMultiScale(): Boolean {
    return true
  }
}