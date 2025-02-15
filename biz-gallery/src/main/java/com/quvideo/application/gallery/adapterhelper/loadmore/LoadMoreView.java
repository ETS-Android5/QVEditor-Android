package com.quvideo.application.gallery.adapterhelper.loadmore;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import com.quvideo.application.gallery.adapterhelper.BaseViewHolder;

/**
 */

public abstract class LoadMoreView {

  public static final int STATUS_DEFAULT = 1;
  public static final int STATUS_LOADING = 2;
  public static final int STATUS_FAIL = 3;
  public static final int STATUS_END = 4;

  private int mLoadMoreStatus = STATUS_DEFAULT;
  private boolean mLoadMoreEndGone = false;

  public void setLoadMoreStatus(int loadMoreStatus) {
    this.mLoadMoreStatus = loadMoreStatus;
  }

  public int getLoadMoreStatus() {
    return mLoadMoreStatus;
  }

  public void convert(BaseViewHolder holder) {
    switch (mLoadMoreStatus) {
      case STATUS_LOADING:
        visibleLoading(holder, true);
        visibleLoadFail(holder, false);
        visibleLoadEnd(holder, false);
        break;
      case STATUS_FAIL:
        visibleLoading(holder, false);
        visibleLoadFail(holder, true);
        visibleLoadEnd(holder, false);
        break;
      case STATUS_END:
        visibleLoading(holder, false);
        visibleLoadFail(holder, false);
        visibleLoadEnd(holder, true);
        break;
      case STATUS_DEFAULT:
        visibleLoading(holder, false);
        visibleLoadFail(holder, false);
        visibleLoadEnd(holder, false);
        break;
      default:
        break;
    }
  }

  private void visibleLoading(BaseViewHolder holder, boolean visible) {
    holder.setGone(getLoadingViewId(), visible);
  }

  private void visibleLoadFail(BaseViewHolder holder, boolean visible) {
    holder.setGone(getLoadFailViewId(), visible);
  }

  private void visibleLoadEnd(BaseViewHolder holder, boolean visible) {
    final int loadEndViewId = getLoadEndViewId();
    if (loadEndViewId != 0) {
      holder.setGone(loadEndViewId, visible);
    }
  }

  public final void setLoadMoreEndGone(boolean loadMoreEndGone) {
    this.mLoadMoreEndGone = loadMoreEndGone;
  }

  public final boolean isLoadEndMoreGone() {
    if (getLoadEndViewId() == 0) {
      return true;
    }
    return mLoadMoreEndGone;
  }

  /**
   * No more data is hidden
   *
   * @return true for no more data hidden load more
   */
  @Deprecated public boolean isLoadEndGone() {
    return mLoadMoreEndGone;
  }

  /**
   * load more layout
   */
  public abstract @LayoutRes int getLayoutId();

  /**
   * loading view
   */
  protected abstract @IdRes int getLoadingViewId();

  /**
   * load fail view
   */
  protected abstract @IdRes int getLoadFailViewId();

  /**
   * load end view, you can return 0
   */
  protected abstract @IdRes int getLoadEndViewId();
}
