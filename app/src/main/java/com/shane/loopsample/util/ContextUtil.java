package com.shane.loopsample.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ContextUtil {

    public static void openPage(Context fromCtx, Class<?> cls, Bundle extras) {
        Intent intent = new Intent(fromCtx, cls);
        intent.putExtras(extras);
        fromCtx.startActivity(intent);
    }

}
