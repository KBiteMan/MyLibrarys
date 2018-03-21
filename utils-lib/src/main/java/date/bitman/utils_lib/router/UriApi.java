package date.bitman.utils_lib.router;

import date.bitman.designdemo.router.lib.RouterParam;
import date.bitman.designdemo.router.lib.RouterUri;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author 张禹
 * @date 2018-03-21 13:24
 */

public interface UriApi {
    String BaseAddress = "fgc://fang:8091/";
    @RouterUri(routerUri = BaseAddress+"mainActivity")
    void jumpToMainActivity(@RouterParam("name") String name);
    @RouterUri(routerUri = BaseAddress+"main2Activity")
    void jumpTo2MainActivity(@RouterParam("name") String name);
}
