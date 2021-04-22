package com.smt.wxdj.swxdj.boxs.model;

import com.smt.wxdj.swxdj.MyApplication;
import com.smt.wxdj.swxdj.R;
import com.smt.wxdj.swxdj.bean.BoxDetalBean;
import com.smt.wxdj.swxdj.interfaces.IPublicResultInterface;
import com.smt.wxdj.swxdj.utils.PraseJsonUtils;
import com.smt.wxdj.swxdj.utils.URLTool;
import com.smtlibrary.https.OkHttpUtils;
import com.smtlibrary.utils.LogUtils;

import java.util.List;

import static com.smt.wxdj.swxdj.utils.PraseJsonUtils.praseCancleBoxResult;
import static com.smt.wxdj.swxdj.utils.PraseJsonUtils.praseListBayData;
import static com.smtlibrary.utils.LogUtils.sysout;

/**
 * Created by gbh on 16/6/27.
 */
public class BoxModelImpl implements BoxsModel {
    private final String SOCKET_TIMEOUT = MyApplication.getContext().getString(R.string.network_error);

    @Override
    public void loadBoxs(String data, final IPublicResultInterface onLoadBoxsListener) {
        //参数
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e("loadBoxs response====:", response);
                List<BoxDetalBean> list = PraseJsonUtils.praseBoxListData(response);

//                for(int i=0;i<list.size();i++){
//                    LogUtils.e("list BoxDetalBean:", list.get(i).toString());
//                }
                if (null != list)
                    onLoadBoxsListener.onSucess(list);
                else
                    onLoadBoxsListener.onFailure(SOCKET_TIMEOUT, null);
            }

            @Override
            public void onFailure(Exception e) {
                onLoadBoxsListener.onFailure(SOCKET_TIMEOUT, e);
            }
        });

    }

    @Override
    public void cancleUpBox(String data, final IPublicResultInterface onLoadBoxsListener) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                sysout("box response:", response);
                try {
                    onLoadBoxsListener.onSucess(praseCancleBoxResult(response));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e) {
                sysout("box Exception:", e.toString());
                onLoadBoxsListener.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    @Override
    public void LoadBoxCd(String data, final IPublicResultInterface onLoadBoxsListener) {

        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                sysout("场地====", response);
                onLoadBoxsListener.onSucess(PraseJsonUtils.praseCdListData(response));
            }

            @Override
            public void onFailure(Exception e) {
                sysout("场地==Exception==", e.toString());
                onLoadBoxsListener.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    /**
     * 判断场地是否被锁住
     *
     * @param data
     * @param onLoadBoxsListener
     */
    @Override
    public void CheckStackIsLock(String data, final IPublicResultInterface onLoadBoxsListener) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
//                sysout("检验场地合法性====", response);
                onLoadBoxsListener.onSucess(PraseJsonUtils.praseCdListData(response));
            }

            @Override
            public void onFailure(Exception e) {
//                sysout("检验场地合法性==Exception==", e.toString());
                onLoadBoxsListener.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    /**
     * 获取场地被锁原因
     *
     * @param data
     * @param onLoadBoxsListener
     */
    @Override
    public void GetLockReason(String data, final IPublicResultInterface onLoadBoxsListener) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                sysout("锁定场地原因====", response);

            }

            @Override
            public void onFailure(Exception e) {
                sysout("锁定场地原因==Exception==", e.toString());
                onLoadBoxsListener.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }


    @Override
    public void LoadBoxTw(String data, final IPublicResultInterface onLoadBoxsListener) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                sysout("田位====", response);
//                List<Bay> bayList = PraseJsonUtils.praseBayListData(response);
//                if (bayList.size() > 0)
//                    onLoadBoxsListener.onSucess(bayList);
//                else onLoadBoxsListener.onFailure("加载场地数据失败...", null);
            }

            @Override
            public void onFailure(Exception e) {
                sysout("场地==Exception==", e.toString());
                onLoadBoxsListener.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    /**
     * 获取田位信息
     *
     * @param data
     * @param onLoadBoxsListener
     */
    @Override
    public void getSelectedBay(String data, final IPublicResultInterface onLoadBoxsListener) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                sysout("田位====", response);
                List<String> bayList = praseListBayData(response);
                sysout("list bay", bayList);
                if (bayList != null && bayList.size() > 0)
                    onLoadBoxsListener.onSucess(bayList);
                else onLoadBoxsListener.onFailure(MyApplication.getContext().getString(R.string.failure_please_try_again), null);
            }

            @Override
            public void onFailure(Exception e) {
                sysout("场地==Exception==", e.toString());
                onLoadBoxsListener.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    /**
     * 获取贝位详细信息
     *
     * @param data
     * @param onLoadBoxsListener
     */
    @Override
    public void CheckMaxCellTier(String data, final IPublicResultInterface onLoadBoxsListener) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                sysout("田位详细信息====", response);
                onLoadBoxsListener.onSucess(PraseJsonUtils.praseBayData(response));
            }

            @Override
            public void onFailure(Exception e) {
                sysout("田位详细信息==Exception==", e.toString());
                onLoadBoxsListener.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    @Override
    public void loadListBoxForCell(String data, final IPublicResultInterface onLoadBoxsListener) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                List<BoxDetalBean> list = PraseJsonUtils.praseBoxListData(response);
                onLoadBoxsListener.onSucess(list);
            }

            @Override
            public void onFailure(Exception e) {
                onLoadBoxsListener.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    @Override
    public void loadCheckBoxForCell(String data, final IPublicResultInterface iPublicResultInterface) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                sysout("check response:", response);
                try {
                    String result = PraseJsonUtils.praseCheckBoxResult(response);
                    if (result.equals("0"))
                        iPublicResultInterface.onSucess(response);
                    else
                        iPublicResultInterface.onFailure(result, null);
                } catch (Exception e) {
                    e.printStackTrace();
                    iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
                }
            }

            @Override
            public void onFailure(Exception e) {
                sysout("move error:", e.toString());
                iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    @Override
    public void loadMoveBoxForCell(String data, final IPublicResultInterface iPublicResultInterface) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                sysout("move response:", response);
                try {
//                    boolean obj = PraseJsonUtils.praseMoveBoxResult(response);
//                    if (obj)
//                        iPublicResultInterface.onSucess(obj);
//                    else {
                    String str = (String) PraseJsonUtils.praseGetBoxResult(response);
                    if (str.equals("0"))
                        iPublicResultInterface.onSucess(true);
                    else
                        iPublicResultInterface.onFailure(str, null);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception e) {
                sysout("move error:", e.toString());
                iPublicResultInterface.onFailure(e.toString(), e);
            }
        });
    }

    @Override
    public void loadUpBoxTask(String data, final IPublicResultInterface iPublicResultInterface) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                sysout("up box response:", response);
                try {
                    String str = (String) PraseJsonUtils.praseGetBoxResult(response);
                    if (str.equals("0")) {
                        iPublicResultInterface.onSucess(true);
                    } else {

                        iPublicResultInterface.onFailure(str, null);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    iPublicResultInterface.onFailure(MyApplication.getContext().getString(R.string.failure_please_try_again), e);
                }
            }

            @Override
            public void onFailure(Exception e) {
                sysout("move error:", e.toString());
                iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    @Override
    public void loadCheckChangeBoxTask(String data, final IPublicResultInterface iPublicResultInterface) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
//                LogUtils.sysout("loadCheckChangeBoxTask box response:", response);
                LogUtils.e("loadCheckChangeBoxTask box response:", response);
                try {
                    iPublicResultInterface.onSucess(praseCancleBoxResult(response));
                } catch (Exception e) {
                    iPublicResultInterface.onSucess(false);
                }
            }

            @Override
            public void onFailure(Exception e) {
                sysout("check change box response faile:", e.toString());
                iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    @Override
    public void loadChangeBoxTask(String data, final IPublicResultInterface iPublicResultInterface) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                sysout("change box response:", response);
            }

            @Override
            public void onFailure(Exception e) {
                iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    /**
     * 抄场
     *
     * @param data
     * @param iPublicResultInterface
     */
    @Override
    public void putBoxToParkingSpace(String data, final IPublicResultInterface iPublicResultInterface) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                sysout("putBoxToParkingSpace", response);
                try {
                    boolean b = PraseJsonUtils.praseCancleBoxResult(response);
                    if (b)
                        iPublicResultInterface.onSucess(b);
                    else
                        iPublicResultInterface.onFailure("fail", null);
                } catch (Exception e) {
                    e.printStackTrace();
                    iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
                }
            }

            @Override
            public void onFailure(Exception e) {
                iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    /**
     * 模糊搜索箱子
     *
     * @param data
     * @param iPublicResultInterface
     */
    @Override
    public void searchBox(String data, final IPublicResultInterface iPublicResultInterface) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                sysout("searchBox", response);
                List<BoxDetalBean> list = PraseJsonUtils.praseBoxListData(response);
                if (null != list && list.size() > 0)
                    iPublicResultInterface.onSucess(list);
                else
                    iPublicResultInterface.onFailure(MyApplication.getContext().getString(R.string.search_not_the_box), null);
            }

            @Override
            public void onFailure(Exception e) {
                iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    /**
     * 获取系统参数
     *
     * @param data
     * @param iPublicResultInterface
     */
    @Override
    public void GetSysParam(String data, final IPublicResultInterface iPublicResultInterface) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                sysout("=======GetSysParam", response);
                try {
                    iPublicResultInterface.onSucess(PraseJsonUtils.praseCheckBoxResult(response).equals("YES") ? true : false);
                } catch (Exception e) {
                    iPublicResultInterface.onSucess(false);
                }
            }

            @Override
            public void onFailure(Exception e) {
                sysout("=======GetCntrCellForRFCWS", e.toString());
                iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    /**
     * 获取放箱推荐位
     *
     * @param data
     * @param iPublicResultInterface
     */
    @Override
    public void GetCntrCellForRFCWS(String data, final IPublicResultInterface iPublicResultInterface) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                sysout("=======GetCntrCellForRFCWS", response);
                List<String> rmList = PraseJsonUtils.praseListBayData(response);
                if (rmList != null)
                    iPublicResultInterface.onSucess(rmList);
                iPublicResultInterface.onFailure(MyApplication.getContext().getString(R.string.get_data_failure), null);
            }

            @Override
            public void onFailure(Exception e) {
                sysout("=======GetCntrCellForRFCWS", e.toString());
                iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    /**
     * 获取拖车Size 大小
     *
     * @param data
     * @param iPublicResultInterface
     */
    @Override
    public void CheckTruckSize(String data, final IPublicResultInterface iPublicResultInterface) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                sysout("CheckTruckSize", response);
                try {
                    iPublicResultInterface.onSucess(PraseJsonUtils.praseCheckBoxResult(response));
                } catch (Exception e) {
                    iPublicResultInterface.onSucess("_WSFail_");
                }
            }

            @Override
            public void onFailure(Exception e) {
                iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    /**
     * 验证
     *
     * @param data
     * @param iPublicResultInterface
     */
    @Override
    public void ValidOperation(String data, final IPublicResultInterface iPublicResultInterface) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                sysout("ValidOperation", response);
                try {
                    BoxDetalBean info = PraseJsonUtils.praseBoxInfoData(response);
                    iPublicResultInterface.onSucess(info == null ? false : info.getResult().equals("0") ? true : false);
                } catch (Exception e) {
                    iPublicResultInterface.onSucess(false);
                }
            }

            @Override
            public void onFailure(Exception e) {
                iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    /**
     * 修改场地登录
     *
     * @param data
     * @param iPublicResultInterface
     */
    @Override
    public void UpdateCraneRow(String data, final IPublicResultInterface iPublicResultInterface) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                sysout("UpdateCraneRow", response);
                try {
                    boolean b = PraseJsonUtils.praseCancleBoxResult(response);
                    if (b)
                        iPublicResultInterface.onSucess(b);
                    else
                        iPublicResultInterface.onFailure("fail", null);
                } catch (Exception e) {
                    e.printStackTrace();
                    iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
                }
            }

            @Override
            public void onFailure(Exception e) {
                iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

    /**
     * 根据类型获取对应的数据
     *
     * @param data
     * @param iPublicResultInterface
     */
    @Override
    public void GetBasicData(String data, final IPublicResultInterface iPublicResultInterface) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                sysout("===============GetBasicData result:", result);
                iPublicResultInterface.onSucess(result);
            }

            @Override
            public void onFailure(Exception e) {
                sysout("============GetBasicData error:", e);
                iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }


    @Override
    public void GetCntrInfoConver(String data, final IPublicResultInterface iPublicResultInterface) {
        OkHttpUtils.post(URLTool.getUrl(), data, new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtils.e("GetCntrInfoConver", "result:" + result);
                List<BoxDetalBean> list = PraseJsonUtils.praseBoxListData(result);
                iPublicResultInterface.onSucess(list);
            }

            @Override
            public void onFailure(Exception e) {
                sysout("============GetCntrInfoConver error:", e);
                iPublicResultInterface.onFailure(SOCKET_TIMEOUT, e);
            }
        });
    }

}
