/* -*- mode: Java; c-basic-offset: 2; indent-tabs-mode: nil; coding: utf-8-unix -*-
 *
 * Copyright © 2017-2018 microBean.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.microbean.kubernetes.controller;

import java.util.Collection;

import io.fabric8.kubernetes.api.model.HasMetadata;

/**
 * A minimalistic interface indicating that its implementations cache
 * {@link Event}s representing Kubernetes resources.
 *
 * <h2>Thread Safety</h2>
 *
 * <p><strong>Implementations of this interface must be safe for
 * concurrent usage by multiple {@link Thread}s.</strong></p>
 *
 * @param <T> a type of Kubernetes resource
 *
 * @author <a href="https://about.me/lairdnelson"
 * target="_parent">Laird Nelson</a>
 *
 * @see Event
 */
public interface EventCache<T extends HasMetadata> {

  /**
   * Adds a new {@link Event} constructed out of the parameters
   * supplied to this method to this {@link EventCache} implementation
   * and returns the {@link Event} that was added.
   *
   * <p>Implementations of this method may return {@code null} to
   * indicate that for whatever reason no {@link Event} was actually
   * added.</p>
   *
   * @param source the {@linkplain Event#getSource() source} of the
   * {@link Event} that will be created and added; must not be {@code
   * null}
   *
   * @param eventType the {@linkplain Event#getType() type} of {@link
   * Event} that will be created and added; must not be {@code null}
   *
   * @param resource the {@linkplain Event#getResource() resource} of
   * the {@link Event} that will be created and added must not be
   * {@code null}
   *
   * @return the {@link Event} that was created and added, or {@code
   * null} if no {@link Event} was actually added as a result of this
   * method's invocation
   *
   * @exception NullPointerException if any of the parameters is
   * {@code null}
   *
   * @see Event
   */
  public Event<T> add(final Object source, final Event.Type eventType, final T resource);

  /**
   * A "full replace" operation that atomically replaces all internal
   * state with new state derived from the supplied {@link Collection}
   * of resources.
   *
   * @param incomingResources the resources comprising the new state;
   * must not be {@code null}
   *
   * @param resourceVersion the notional version of the supplied
   * {@link Collection}; may be {@code null}; often ignored by
   * implementations
   *
   * @exception NullPointerException if {@code incomingResources} is
   * {@code null}
   */
  public void replace(final Collection<? extends T> incomingResources, final Object resourceVersion);

  /**
   * Synchronizes this {@link EventCache} implementation's state with
   * its downstream consumers, if any.
   *
   * <p>Not all {@link EventCache} implementations need support
   * synchronization.  An implementation of this method that does
   * nothing is explicitly permitted.</p>
   */
  public void synchronize();
  
}
