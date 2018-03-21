package date.bitman.utils_lib.mvp;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author 张禹
 *
 */

public interface BaseModel {

    void getData(Datas datas);

    interface Datas<T>{
        void onMSuccess(T t);
        void onMError();
    }
}