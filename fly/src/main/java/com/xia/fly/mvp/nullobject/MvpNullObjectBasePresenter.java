package com.xia.fly.mvp.nullobject;

import com.xia.fly.mvp.common.IMvpPresenter;
import com.xia.fly.mvp.common.IMvpView;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

/**
 * @author xia
 * @date 2018/7/19.
 */
public abstract class MvpNullObjectBasePresenter<V extends IMvpView> implements IMvpPresenter<V> {
    private WeakReference<V> mView;
    private final V mNullView;

    @SuppressWarnings("unchecked")
    protected MvpNullObjectBasePresenter() {
        try {
            // Scan the inheritance hierarchy until we reached MvpNullObjectBasePresenter
            Class<V> viewClass = null;
            Class<?> currentClass = getClass();

            while (viewClass == null) {
                Type genericSuperType = null;
                if (currentClass != null) {
                    genericSuperType = currentClass.getGenericSuperclass();
                }
                while (!(genericSuperType instanceof ParameterizedType)) {
                    if (currentClass != null) {
                        // Scan inheritance tree until we find ParameterizedType which is probably a MvpSubclass
                        currentClass = currentClass.getSuperclass();
                        if (currentClass != null) {
                            genericSuperType = currentClass.getGenericSuperclass();
                        }
                    }
                }

                final Type[] types = ((ParameterizedType) genericSuperType).getActualTypeArguments();
                for (Type type : types) {
                    final Class<?> genericType = (Class<?>) type;
                    if (genericType.isInterface() && isSubTypeOfMvpView(genericType)) {
                        viewClass = (Class<V>) genericType;
                        break;
                    }
                }
                // Continue with next class in inheritance hierarchy (see genericSuperType assignment at start of while loop)
                currentClass = currentClass.getSuperclass();
            }
            mNullView = NoOp.of(viewClass);
        } catch (Throwable t) {
            throw new IllegalArgumentException(
                    "The generic type <V extends MvpView> must be the first generic type argument of class "
                            + getClass().getSimpleName()
                            + " (per convention). Otherwise we can't determine which type of View this"
                            + " Presenter coordinates.", t);
        }
    }

    /**
     * Scans the interface inheritance hierarchy and checks if on the root is MvpView.class
     *
     * @param cls The leaf interface where to begin to scan
     * @return true if subtype of MvpView, otherwise false
     */
    private boolean isSubTypeOfMvpView(Class<?> cls) {
        if (cls.equals(IMvpView.class)) {
            return true;
        }
        final Class[] superInterfaces = cls.getInterfaces();
        for (Class superInterface : superInterfaces) {
            if (isSubTypeOfMvpView(superInterface)) {
                return true;
            }
        }
        return false;
    }

    @UiThread
    @Override
    public void attachView(@NonNull V view) {
        this.mView = new WeakReference<>(view);
    }

    @UiThread
    @Override
    public void detachView() {
        if (mView != null) {
            mView.clear();
            mView = null;
        }
    }

    @UiThread
    @NonNull
    protected V getV() {
        if (mView != null) {
            final V realView = mView.get();
            if (realView != null) {
                return realView;
            }
        }
        return mNullView;
    }
}
