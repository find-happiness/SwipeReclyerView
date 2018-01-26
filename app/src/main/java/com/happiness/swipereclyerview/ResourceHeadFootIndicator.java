/*
 * Copyright (c) 2015 PocketHub
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
package com.happiness.swipereclyerview;

import com.xwray.groupie.Section;

/**
 * Helper for showing more items are being loaded at the bottom of a list via a
 * custom footer view
 */
public class ResourceHeadFootIndicator {

  private Section section;

  private boolean showing;

  private final LoadingItem loadingItem;

  /**
   * Create indicator using given inflater.
   *
   * @param loadingResId string resource id to show when loading
   */
  public ResourceHeadFootIndicator(final int loadingResId) {
    loadingItem = new LoadingItem(loadingResId);
  }

  /**
   * Set the adapter that this indicator should be added as a footer to.
   *
   * @return this indicator
   */
  public ResourceHeadFootIndicator setSection(final Section section) {
    this.section = section;
    return this;
  }

  /**
   * Set visibility of entire indicator view.
   *
   * @return this indicator
   */
  public ResourceHeadFootIndicator setHead(final boolean visible) {
    if (showing != visible && section != null) {
      if (visible) {
        section.setHeader(loadingItem);
      } else {
        section.removeHeader();
      }
    }
    showing = visible;
    return this;
  }
}
