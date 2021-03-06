/**
 * Copyright (c) 2015, The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cyanogenmod.app;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * A class that represents a quick settings tile
 *
 * <p>The {@link cyanogenmod.app.CustomTile.Builder} has been added to make it
 * easier to construct CustomTiles.</p>
 */
public class CustomTile implements Parcelable {

    /**
     * An optional intent to execute when the custom tile entry is clicked.  If
     * this is an activity, it must include the
     * {@link android.content.Intent#FLAG_ACTIVITY_NEW_TASK} flag, which requires
     * that you take care of task management.
     *
     * This takes priority over the onClickUri.
     **/
    public PendingIntent onClick;

    /**
     * An optional settings intent to execute when the custom tile's detail is shown
     * If this is an activity, it must include the
     * {@link android.content.Intent#FLAG_ACTIVITY_NEW_TASK} flag, which requires
     * that you take care of task management
     */
    public Intent onSettingsClick;

    /**
     * An optional Uri to be parsed and broadcast on tile click, if an onClick pending intent
     * is specified, it will take priority over the uri to be broadcasted.
     **/
    public Uri onClickUri;

    /**
     * A label specific to the quick settings tile to be created
     */
    public String label;

    /**
     * A content description for the custom tile state
     */
    public String contentDescription;

    /**
     * An icon to represent the custom tile
     */
    public int icon;

    /**
     * An expanded style for when the CustomTile is clicked, can either be
     * a {@link GridExpandedStyle} or a {@link ListExpandedStyle}
     */
    public ExpandedStyle expandedStyle;

    /**
     * Unflatten the CustomTile from a parcel.
     */
    public CustomTile(Parcel parcel) {
        if (parcel.readInt() != 0) {
            this.onClick = PendingIntent.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.onSettingsClick = Intent.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.onClickUri = Uri.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.label = parcel.readString();
        }
        if (parcel.readInt() != 0) {
            this.contentDescription = parcel.readString();
        }
        if (parcel.readInt() != 0) {
            this.expandedStyle = ExpandedStyle.CREATOR.createFromParcel(parcel);
        }
        this.icon = parcel.readInt();
    }

    /**
     * Constructs a CustomTile object with default values.
     * You might want to consider using {@link cyanogenmod.app.CustomTile.Builder} instead.
     */
    public CustomTile()
    {
        // Empty constructor
    }

    @Override
    public CustomTile clone() {
        CustomTile that = new CustomTile();
        cloneInto(that);
        return that;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        if (onClickUri != null) {
            b.append("onClickUri=" + onClickUri.toString() + NEW_LINE);
        }
        if (onClick != null) {
            b.append("onClick=" + onClick.toString() + NEW_LINE);
        }
        if (onSettingsClick != null) {
            b.append("onSettingsClick=" + onSettingsClick.toString() + NEW_LINE);
        }
        if (!TextUtils.isEmpty(label)) {
            b.append("label=" + label + NEW_LINE);
        }
        if (!TextUtils.isEmpty(contentDescription)) {
            b.append("contentDescription=" + contentDescription + NEW_LINE);
        }
        if (expandedStyle != null) {
            b.append("expandedStyle=" + expandedStyle + NEW_LINE);
        }
        b.append("icon=" + icon + NEW_LINE);
        return b.toString();
    }

    /**
     * Copy all of this into that
     * @hide
     */
    public void cloneInto(CustomTile that) {
        that.onClick = this.onClick;
        that.onSettingsClick = this.onSettingsClick;
        that.onClickUri = this.onClickUri;
        that.label = this.label;
        that.contentDescription = this.contentDescription;
        that.expandedStyle = this.expandedStyle;
        that.icon = this.icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        if (onClick != null) {
            out.writeInt(1);
            onClick.writeToParcel(out, 0);
        } else {
            out.writeInt(0);
        }
        if (onSettingsClick != null) {
            out.writeInt(1);
            onSettingsClick.writeToParcel(out, 0);
        } else {
            out.writeInt(0);
        }
        if (onClickUri != null) {
            out.writeInt(1);
            onClickUri.writeToParcel(out, 0);
        } else {
            out.writeInt(0);
        }
        if (label != null) {
            out.writeInt(1);
            out.writeString(label);
        } else {
            out.writeInt(0);
        }
        if (contentDescription != null) {
            out.writeInt(1);
            out.writeString(contentDescription);
        } else {
            out.writeInt(0);
        }
        if (expandedStyle != null) {
            out.writeInt(1);
            expandedStyle.writeToParcel(out, 0);
        } else {
            out.writeInt(0);
        }
        out.writeInt(icon);
    }

    /**
     * An object that can apply an expanded view style to a {@link CustomTile.Builder}
     * object.
     */
    public static class ExpandedStyle implements Parcelable {
        /**
         * @hide
         */
        public static final int NO_STYLE = -1;

        /**
         * Identifier for a grid style expanded view
         */
        public static final int GRID_STYLE = 0;

        /**
         * Identifier for a list style expanded view
         */
        public static final int LIST_STYLE = 1;

        private ExpandedStyle() {
            styleId = NO_STYLE;
        }

        private ExpandedItem[] expandedItems;
        private int styleId;

        private ExpandedStyle(Parcel parcel) {
            if (parcel.readInt() != 0) {
                expandedItems = parcel.createTypedArray(ExpandedItem.CREATOR);
            }
            styleId = parcel.readInt();
        }

        /**
         * @hide
         */
        public void setBuilder(Builder builder) {
            if (builder != null) {
                builder.setExpandedStyle(this);
            }
        }

        /**
         * @hide
         */
        protected void internalSetExpandedItems(ArrayList<? extends ExpandedItem> items) {
            expandedItems = new ExpandedItem[items.size()];
            items.toArray(expandedItems);
        }

        /**
         * @hide
         */
        protected void internalStyleId(int id) {
            styleId = id;
        }

        /**
         * Retrieve the {@link ExpandedItem}s that have been set on this expanded style
         * @return array of {@link ExpandedItem}
         */
        public ExpandedItem[] getExpandedItems() {
            return expandedItems;
        }

        /**
         * Retrieve the style id associated with the {@link ExpandedStyle}
         * @return id for style
         */
        public int getStyle() {
            return styleId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            if (expandedItems != null) {
                parcel.writeInt(1);
                parcel.writeTypedArray(expandedItems, 0);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeInt(styleId);
        }

        @Override
        public String toString() {
            StringBuilder b = new StringBuilder();
            String NEW_LINE = System.getProperty("line.separator");
            if (expandedItems != null) {
                b.append("expandedItems= "+ NEW_LINE);
                for (ExpandedItem item : expandedItems) {
                    b.append("     item=" + item.toString() + NEW_LINE);
                }
            }
            b.append("styleId=" + styleId + NEW_LINE);
            return b.toString();
        }

        /**
         * Parcelable.Creator that instantiates ExpandedStyle objects
         */
        public static final Creator<ExpandedStyle> CREATOR =
                new Creator<ExpandedStyle>() {
                    public ExpandedStyle createFromParcel(Parcel in) {
                        return new ExpandedStyle(in);
                    }

                    @Override
                    public ExpandedStyle[] newArray(int size) {
                        return new ExpandedStyle[size];
                    }
                };
    }

    /**
     * An instance of {@link ExpandedStyle} that shows the {@link ExpandedGridItem}s in a
     * non-scrollable grid.
     */
    public static class GridExpandedStyle extends ExpandedStyle {
        /**
         * Constructs a GridExpandedStyle object with default values.
         */
        public GridExpandedStyle() {
            internalStyleId(GRID_STYLE);
        }

        /**
         * Sets an {@link ArrayList} of {@link ExpandedGridItem}'s to be utilized by
         * the PseudoGridView for presentation.
         * @param expandedGridItems an array list of {@link ExpandedGridItem}'s
         */
        public void setGridItems(ArrayList<ExpandedGridItem> expandedGridItems) {
            internalSetExpandedItems(expandedGridItems);
        }
    }

    /**
     * An instance of {@link ExpandedStyle} that shows the {@link ExpandedListItem}'s in a
     * scrollable ListView.
     */
    public static class ListExpandedStyle extends ExpandedStyle {
        /**
         * Constructs a ListExpandedStyle object with default values.
         */
        public ListExpandedStyle() {
            internalStyleId(LIST_STYLE);
        }

        /**
         * Sets an {@link ArrayList} of {@link ExpandedListItem}s to be utilized by
         * the ListView for presentation.
         * @param expandedListItems an array list of {@link ExpandedListItem}s
         */
        public void setListItems(ArrayList<ExpandedListItem> expandedListItems) {
            internalSetExpandedItems(expandedListItems);
        }
    }

    /**
     * A container object that is utilized by {@link ExpandedStyle} to show specific items in either
     * a PseudoGridView or a ListView via {@link GridExpandedStyle} and {@link ListExpandedStyle}
     */
    public static class ExpandedItem implements Parcelable {

        /**
         * A {@link PendingIntent} associated with the item.
         * Triggers a {@link PendingIntent#send()} when the item is clicked.
         */
        public PendingIntent onClickPendingIntent;

        /**
         * A drawable resource id associated with the {@link ExpandedItem}
         */
        public int itemDrawableResourceId;

        /**
         * The title of the item
         */
        public String itemTitle;

        /**
         * The summary associated with the item, may be null
         */
        public String itemSummary = null;

        private ExpandedItem() {
            // Don't want to have this baseclass be instantiable
        }

        /**
         * @hide
         */
        protected void internalSetItemDrawable(int resourceId) {
            itemDrawableResourceId = resourceId;
        }

        /**
         * @hide
         */
        protected void internalSetItemSummary(String resourceId) {
            itemSummary = resourceId;
        }

        /**
         * @hide
         */
        protected void internalSetItemTitle(String title) {
            itemTitle = title;
        }

        /**
         * @hide
         */
        protected void internalSetOnClickPendingIntent(PendingIntent pendingIntent) {
            onClickPendingIntent = pendingIntent;
        }

        /**
         * Unflatten the ExpandedItem from a parcel.
         */
        protected ExpandedItem(Parcel parcel) {
            if (parcel.readInt() != 0) {
                onClickPendingIntent = PendingIntent.CREATOR.createFromParcel(parcel);
            }
            if (parcel.readInt() != 0) {
                itemTitle = parcel.readString();
            }
            if (parcel.readInt() != 0) {
                itemSummary = parcel.readString();
            }
            itemDrawableResourceId = parcel.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            if (onClickPendingIntent != null) {
                out.writeInt(1);
                onClickPendingIntent.writeToParcel(out, 0);
            } else {
                out.writeInt(0);
            }
            if (!TextUtils.isEmpty(itemTitle)) {
                out.writeInt(1);
                out.writeString(itemTitle);
            } else {
                out.writeInt(0);
            }
            if (!TextUtils.isEmpty(itemSummary)) {
                out.writeInt(1);
                out.writeString(itemSummary);
            } else {
                out.writeInt(0);
            }
            out.writeInt(itemDrawableResourceId);
        }

        @Override
        public String toString() {
            StringBuilder b = new StringBuilder();
            String NEW_LINE = System.getProperty("line.separator");
            if (onClickPendingIntent != null) {
                b.append("onClickPendingIntent= " + onClickPendingIntent.toString() + NEW_LINE);
            }
            if (itemTitle != null) {
                b.append("itemTitle= " + itemTitle.toString() + NEW_LINE);
            }
            if (itemSummary != null) {
                b.append("itemSummary= " + itemSummary.toString() + NEW_LINE);
            }
            b.append("itemDrawableResourceId=" + itemDrawableResourceId + NEW_LINE);
            return b.toString();
        }

        public static final Creator<ExpandedItem> CREATOR =
                new Creator<ExpandedItem>() {
                    @Override
                    public ExpandedItem createFromParcel(Parcel in) {
                        return new ExpandedItem(in);
                    }

                    @Override
                    public ExpandedItem[] newArray(int size) {
                        return new ExpandedItem[size];
                    }
                };
    }

    /**
     * An instance of {@link ExpandedItem} to be utilized within a {@link GridExpandedStyle}
     */
    public static class ExpandedGridItem extends ExpandedItem {
        /**
         * Constructor for the ExpandedGridItem
         */
        public ExpandedGridItem() {
        }

        /**
         * Sets the title for the {@link ExpandedGridItem}
         * @param title a string title
         */
        public void setExpandedGridItemTitle(String title) {
            internalSetItemTitle(title);
        }

        /**
         * Sets the {@link PendingIntent} associated with the {@link ExpandedGridItem}
         * @param intent a pending intent to be triggered on click
         */
        public void setExpandedGridItemOnClickIntent(PendingIntent intent) {
            internalSetOnClickPendingIntent(intent);
        }

        /**
         * Sets the drawable resource id associated with the {@link ExpandedGridItem}
         * @param resourceId a resource id that maps to a drawable
         */
        public void setExpandedGridItemDrawable(int resourceId) {
            internalSetItemDrawable(resourceId);
        }
    }

    /**
     * An instance of {@link ExpandedItem} to be utilized within a {@link ListExpandedStyle}
     */
    public static class ExpandedListItem extends ExpandedItem {
        /**
         * Constructor fot the ExpandedListItem
         */
        public ExpandedListItem() {
        }

        /**
         * Sets the title for the {@link ExpandedListItem}
         * @param title a string title
         */
        public void setExpandedListItemTitle(String title) {
            internalSetItemTitle(title);
        }

        /**
         * Sets the title for the {@link ExpandedListItem}
         * @param summary a string summary
         */
        public void setExpandedListItemSummary(String summary) {
            internalSetItemSummary(summary);
        }

        /**
         * Sets the {@link PendingIntent} associated with the {@link ExpandedListItem}
         * @param intent a pending intent to be triggered on click
         */
        public void setExpandedListItemOnClickIntent(PendingIntent intent) {
            internalSetOnClickPendingIntent(intent);
        }

        /**
         * Sets the drawable resource id associated with the {@link ExpandedListItem}
         * @param resourceId a resource id that maps to a drawable
         */
        public void setExpandedListItemDrawable(int resourceId) {
            internalSetItemDrawable(resourceId);
        }
    }

    /**
     * Parcelable.Creator that instantiates CustomTile objects
     */
    public static final Creator<CustomTile> CREATOR =
            new Creator<CustomTile>() {
                public CustomTile createFromParcel(Parcel in) {
                    return new CustomTile(in);
                }

                @Override
                public CustomTile[] newArray(int size) {
                    return new CustomTile[size];
                }
            };

    /**
     * Builder class for {@link cyanogenmod.app.CustomTile} objects.
     *
     * Provides a convenient way to set the various fields of a {@link cyanogenmod.app.CustomTile}
     *
     * <p>Example:
     *
     * <pre class="prettyprint">
     * CustomTile customTile = new CustomTile.Builder(mContext)
     *         .setLabel("custom label")
     *         .setContentDescription("custom description")
     *         .setOnClickIntent(pendingIntent)
     *         .setOnSettingsClickIntent(intent)
     *         .setOnClickUri(Uri.parse("custom uri"))
     *         .setIcon(R.drawable.ic_launcher)
     *         .build();
     * </pre>
     */
    public static class Builder {
        private PendingIntent mOnClick;
        private Intent mOnSettingsClick;
        private Uri mOnClickUri;
        private String mLabel;
        private String mContentDescription;
        private int mIcon;
        private Context mContext;
        private ExpandedStyle mExpandedStyle;

        /**
         * Constructs a new Builder with the defaults:
         */
        public Builder(Context context) {
            mContext = context;
        }

        /**
         * Set the label for the custom tile
         * @param label a string to be used for the custom tile label
         * @return {@link cyanogenmod.app.CustomTile.Builder}
         */
        public Builder setLabel(String label) {
            mLabel = label;
            return this;
        }

        /**
         * Set the label for the custom tile
         * @param id a string resource id to be used for the custom tile label
         * @return {@link cyanogenmod.app.CustomTile.Builder}
         */
        public Builder setLabel(int id) {
            mLabel = mContext.getString(id);
            return this;
        }

        /**
         * Set the content description for the custom tile
         * @param contentDescription a string to explain content
         * @return {@link cyanogenmod.app.CustomTile.Builder}
         */
        public Builder setContentDescription(String contentDescription) {
            mContentDescription = contentDescription;
            return this;
        }

        /**
         * Set the content description for the custom tile
         * @param id a string resource id to explain content
         * @return {@link cyanogenmod.app.CustomTile.Builder}
         */
        public Builder setContentDescription(int id) {
            mContentDescription = mContext.getString(id);
            return this;
        }

        /**
         * Set a {@link android.app.PendingIntent} to be fired on custom tile click
         * @param intent
         * @return {@link cyanogenmod.app.CustomTile.Builder}
         */
        public Builder setOnClickIntent(PendingIntent intent) {
            mOnClick = intent;
            return this;
        }

        /**
         * Set a settings {@link android.content.Intent} to be fired on custom
         * tile detail pane click
         * @param intent
         * @return {@link cyanogenmod.app.CustomTile.Builder}
         */
        public Builder setOnSettingsClickIntent(Intent intent) {
            mOnSettingsClick = intent;
            return this;
        }

        /**
         * Set a {@link android.net.Uri} to be broadcasted in an intent on custom tile click
         * @param uri
         * @return {@link cyanogenmod.app.CustomTile.Builder}
         */
        public Builder setOnClickUri(Uri uri) {
            mOnClickUri = uri;
            return this;
        }

        /**
         * Set an icon for the custom tile to be presented to the user
         * @param drawableId
         * @return {@link cyanogenmod.app.CustomTile.Builder}
         */
        public Builder setIcon(int drawableId) {
            mIcon = drawableId;
            return this;
        }

        /**
         * Set an {@link ExpandedStyle} to to be displayed when a user clicks the custom tile
         * @param expandedStyle
         * @return {@link cyanogenmod.app.CustomTile.Builder}
         */
        public Builder setExpandedStyle(ExpandedStyle expandedStyle) {
            if (mExpandedStyle != expandedStyle) {
                mExpandedStyle = expandedStyle;
                if (mExpandedStyle != null) {
                    expandedStyle.setBuilder(this);
                }
            }
            return this;
        }

        /**
         * Create a {@link cyanogenmod.app.CustomTile} object
         * @return {@link cyanogenmod.app.CustomTile}
         */
        public CustomTile build() {
            CustomTile tile = new CustomTile();
            tile.onClick = mOnClick;
            tile.onSettingsClick = mOnSettingsClick;
            tile.onClickUri = mOnClickUri;
            tile.label = mLabel;
            tile.contentDescription = mContentDescription;
            tile.expandedStyle = mExpandedStyle;
            tile.icon = mIcon;
            return tile;
        }
    }
}
