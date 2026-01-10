package com.google.common.util.concurrent;

import com.google.ai.client.generativeai.type.GenerateContentResponse;

public interface FutureCallback<T> {
    void onSuccess(GenerateContentResponse result);

    void onFailure(Throwable t);
}
