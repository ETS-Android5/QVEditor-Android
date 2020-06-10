package com.quvideo.application.slide;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.webp.decoder.WebpDrawable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.quvideo.application.DPUtils;
import com.quvideo.application.editor.R;
import com.quvideo.application.glidedecoder.EffectThumbParams;
import com.quvideo.application.template.SimpleTemplate;
import com.quvideo.mobile.component.template.XytManager;
import com.quvideo.mobile.component.template.model.XytInfo;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SildeTemplateAdapter
    extends RecyclerView.Adapter<SildeTemplateAdapter.TemplateHolder> {

  private List<SimpleTemplate> mTemplates = new ArrayList<>();

  private MutableLiveData<LinkedList<Integer>> mSelected = new MutableLiveData<>();

  private Activity mActivity;

  private DialogFragment mDialogFragment;

  private OnItemSelectListener mOnItemClickListener;

  public interface OnItemSelectListener {
    void onItemSelected(SimpleTemplate template);
  }

  public SildeTemplateAdapter(AppCompatActivity activity, DialogFragment dialogFragment) {
    this.mActivity = activity;
    this.mDialogFragment = dialogFragment;
    mSelected.setValue(new LinkedList<Integer>() {{
      offer(0);
      offer(0);
    }});
    mSelected.observe(activity, new Observer<List<Integer>>() {
      @Override
      public void onChanged(List<Integer> integers) {
        for (Integer pos : integers) {
          notifyItemChanged(pos);
        }
      }
    });
  }

  public void updateList(List<SimpleTemplate> templates) {
    this.mTemplates = templates;
    notifyDataSetChanged();
  }

  public void setOnItemClickListener(OnItemSelectListener listener) {
    mOnItemClickListener = listener;
  }

  @NonNull
  @Override
  public TemplateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new TemplateHolder(
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.view_home_sample_template_list_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull TemplateHolder holder, final int position) {
    final SimpleTemplate item = mTemplates.get(position);
    if (!TextUtils.isEmpty(item.getTitle())) {
      holder.mTextView.setText(item.getTitle());
    } else {
      XytInfo xytInfo = XytManager.getXytInfo(item.getTemplateId());
      holder.mTextView.setText(xytInfo.getTitle(mActivity.getResources().getConfiguration().locale));
    }
    boolean isSelected = position == mSelected.getValue().get(mSelected.getValue().size() - 1);
    if (item.getThumbnailResId() <= 0) {
      final String filterPath = XytManager.getXytInfo(item.getTemplateId()).filePath;
      int thumbWidth = DPUtils.dpToPixel(holder.mImageView.getContext(), 60);
      int thumbHeight = DPUtils.dpToPixel(holder.mImageView.getContext(), 60);
      EffectThumbParams effectThumbParams =
          new EffectThumbParams(filterPath, thumbWidth, thumbHeight);
      Glide.with(holder.mImageView).load(effectThumbParams).into(holder.mImageView);
    } else {
      Glide.with(mActivity)
          .load(item.getThumbnailResId())
          .listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable final GlideException e, final Object model,
                final Target<Drawable> target, final boolean isFirstResource) {
              return false;
            }

            @Override public boolean onResourceReady(final Drawable resource, final Object model,
                final Target<Drawable> target, final DataSource dataSource,
                final boolean isFirstResource) {
              if (resource instanceof WebpDrawable) {
                final WebpDrawable webpDrawable = ((WebpDrawable) resource);
                webpDrawable.start();
              }
              return false;
            }
          })
          .into(holder.mImageView);
    }
    holder.mImageView.setSelected(isSelected);
    holder.mImageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mOnItemClickListener != null) {
          mOnItemClickListener.onItemSelected(item);
          mSelected.postValue(new LinkedList<Integer>() {{
            offer(mSelected.getValue().get(mSelected.getValue().size() - 1));
            offer(position);
          }});
        } else {
          item.onClick(mActivity);
          mDialogFragment.dismissAllowingStateLoss();
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return mTemplates.size();
  }

  class TemplateHolder extends RecyclerView.ViewHolder {

    private AppCompatTextView mTextView;
    private AppCompatImageView mImageView;

    public TemplateHolder(@NonNull View itemView) {
      super(itemView);
      mTextView = itemView.findViewById(R.id.home_template_item_text);
      mImageView = itemView.findViewById(R.id.home_template_item_image);
    }
  }
}
