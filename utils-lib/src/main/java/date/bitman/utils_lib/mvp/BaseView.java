package date.bitman.utils_lib.mvp;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author 张禹
 *
 */

public interface BaseView<T> {
    void onVSuccess(T t);
    void onVError();
}