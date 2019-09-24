package com.spark.casclient.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/9
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CasConfig implements Parcelable {

    /**
     * code : 200
     * data : {"version":"0.1.0","home":"http://www.sscoin.cc","register":"http://cas.www.sscoin.cc/#/register","favicon":"https://biteast.oss-cn-hangzhou.aliyuncs.com/logo/ico.ico","logo":"https://biteast.oss-cn-hangzhou.aliyuncs.com/logo/logo.png","title":"SScoin","agentUrl":"api.agent.sscoin.cc","websocket":"ws://ws.sscoin.cc:28905/ws","navs":[{"name":"c2c","url":"http://www.sscoin.cc/c2c"},{"name":"spot","url":"http://www.sscoin.cc/spot"},{"name":"market","url":"http://www.sscoin.cc/#/market"},{"name":"cfd","url":"http://www.sscoin.cc/cfd"}],"loginNav":[{"asset":[{"name":"MyAsset","url":"http://www.sscoin.cc/ac/#/index?type=SPOT"},{"name":"Otc_Asset","url":"http://www.sscoin.cc/ac/#/index?type=OTC"},{"name":"Address","url":"http://www.sscoin.cc/ac/#/withdrawAddress"}]},{"order":[{"name":"Current_Order","url":"http://www.sscoin.cc/spot/#/entrust?type=0"},{"name":"History_Order","url":"http://www.sscoin.cc/spot/#/entrust?type=1"},{"name":"In_Order","url":"http://www.sscoin.cc/c2c/#/myorder"}]},{"member":[{"name":"Order_Detail","url":"http://www.sscoin.cc/ac/#/record"},{"name":"Account_Safe","url":"http://www.sscoin.cc/uc/"},{"name":"In_Account","url":"http://www.sscoin.cc/c2c/#/mypay"},{"name":"Invite_Friend","url":"http://www.sscoin.cc/uc/#/iInviteFriend"},{"name":"Api_Manage","url":"http://www.sscoin.cc/uc/#/apiManage"}]}],"cas":{"basePath":"http://cas.www.sscoin.cc","applications":[{"name":"Home","basePath":"http://api.sscoin.cc/cms-api","service":"http://api.sscoin.cc/cms-api/cas?client_name=CasClient"},{"name":"CTC","basePath":"http://api.sscoin.cc/otc","service":"http://api.sscoin.cc/otc/cas?client_name=CasClient"},{"name":"AC","basePath":"http://api.sscoin.cc/ac","service":"http://api.sscoin.cc/ac/cas?client_name=CasClient"},{"name":"UC","basePath":"http://api.sscoin.cc/uc","service":"http://api.sscoin.cc/uc/cas?client_name=CasClient"},{"name":"OTC","basePath":"http://api.sscoin.cc/otc-system","service":"http://api.sscoin.cc/otc-system/cas?client_name=CasClient"},{"name":"SPOT","basePath":"http://api.sscoin.cc/spot","service":"http://api.sscoin.cc/spot/cas?client_name=CasClient"},{"name":"CMS","basePath":"http://api.sscoin.cc/cms-api","service":"http://api.sscoin.cc/cms-api/cas?client_name=CasClient"},{"name":"KLINE","basePath":"http://api.sscoin.cc/kline","service":""},{"name":"QUOTE","basePath":"http://api.sscoin.cc/quote","service":""},{"name":"PRICE","basePath":"http://api.sscoin.cc/price-api","service":""},{"name":"CFD","basePath":"http://api.sscoin.cc/cfd","service":"http://api.sscoin.cc/cfd/cas?client_name=CasClient"}]},"ws":[{"id":"1","ip":"60.168.155.150","address":"合肥","status":1,"createTime":1553496813000,"domainName":"","sort":1,"protocol":"","path":"","identity":""}]}
     */

    private int code;
    private DataBean data;

    protected CasConfig(Parcel in) {
        code = in.readInt();
    }

    public static final Creator<CasConfig> CREATOR = new Creator<CasConfig>() {
        @Override
        public CasConfig createFromParcel(Parcel in) {
            return new CasConfig(in);
        }

        @Override
        public CasConfig[] newArray(int size) {
            return new CasConfig[size];
        }
    };

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(code);
    }

    public static class DataBean {
        /**
         * version : 0.1.0
         * home : http://www.sscoin.cc
         * register : http://cas.www.sscoin.cc/#/register
         * favicon : https://biteast.oss-cn-hangzhou.aliyuncs.com/logo/ico.ico
         * logo : https://biteast.oss-cn-hangzhou.aliyuncs.com/logo/logo.png
         * title : SScoin
         * agentUrl : api.agent.sscoin.cc
         * websocket : ws://ws.sscoin.cc:28905/ws
         * navs : [{"name":"c2c","url":"http://www.sscoin.cc/c2c"},{"name":"spot","url":"http://www.sscoin.cc/spot"},{"name":"market","url":"http://www.sscoin.cc/#/market"},{"name":"cfd","url":"http://www.sscoin.cc/cfd"}]
         * loginNav : [{"asset":[{"name":"MyAsset","url":"http://www.sscoin.cc/ac/#/index?type=SPOT"},{"name":"Otc_Asset","url":"http://www.sscoin.cc/ac/#/index?type=OTC"},{"name":"Address","url":"http://www.sscoin.cc/ac/#/withdrawAddress"}]},{"order":[{"name":"Current_Order","url":"http://www.sscoin.cc/spot/#/entrust?type=0"},{"name":"History_Order","url":"http://www.sscoin.cc/spot/#/entrust?type=1"},{"name":"In_Order","url":"http://www.sscoin.cc/c2c/#/myorder"}]},{"member":[{"name":"Order_Detail","url":"http://www.sscoin.cc/ac/#/record"},{"name":"Account_Safe","url":"http://www.sscoin.cc/uc/"},{"name":"In_Account","url":"http://www.sscoin.cc/c2c/#/mypay"},{"name":"Invite_Friend","url":"http://www.sscoin.cc/uc/#/iInviteFriend"},{"name":"Api_Manage","url":"http://www.sscoin.cc/uc/#/apiManage"}]}]
         * cas : {"basePath":"http://cas.www.sscoin.cc","applications":[{"name":"Home","basePath":"http://api.sscoin.cc/cms-api","service":"http://api.sscoin.cc/cms-api/cas?client_name=CasClient"},{"name":"CTC","basePath":"http://api.sscoin.cc/otc","service":"http://api.sscoin.cc/otc/cas?client_name=CasClient"},{"name":"AC","basePath":"http://api.sscoin.cc/ac","service":"http://api.sscoin.cc/ac/cas?client_name=CasClient"},{"name":"UC","basePath":"http://api.sscoin.cc/uc","service":"http://api.sscoin.cc/uc/cas?client_name=CasClient"},{"name":"OTC","basePath":"http://api.sscoin.cc/otc-system","service":"http://api.sscoin.cc/otc-system/cas?client_name=CasClient"},{"name":"SPOT","basePath":"http://api.sscoin.cc/spot","service":"http://api.sscoin.cc/spot/cas?client_name=CasClient"},{"name":"CMS","basePath":"http://api.sscoin.cc/cms-api","service":"http://api.sscoin.cc/cms-api/cas?client_name=CasClient"},{"name":"KLINE","basePath":"http://api.sscoin.cc/kline","service":""},{"name":"QUOTE","basePath":"http://api.sscoin.cc/quote","service":""},{"name":"PRICE","basePath":"http://api.sscoin.cc/price-api","service":""},{"name":"CFD","basePath":"http://api.sscoin.cc/cfd","service":"http://api.sscoin.cc/cfd/cas?client_name=CasClient"}]}
         * ws : [{"id":"1","ip":"60.168.155.150","address":"合肥","status":1,"createTime":1553496813000,"domainName":"","sort":1,"protocol":"","path":"","identity":""}]
         */

        private String version;
        private String home;
        private String register;
        private String favicon;
        private String logo;
        private String symbol;
        private String title;
        private String agentUrl;
        private String websocket;
        private String appregister;
        private CasBean cas;
        private List<NavsBean> navs;
        private List<LoginNavBean> loginNav;
        private List<WsBean> ws;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getRegister() {
            return register;
        }

        public void setRegister(String register) {
            this.register = register;
        }

        public String getFavicon() {
            return favicon;
        }

        public void setFavicon(String favicon) {
            this.favicon = favicon;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAgentUrl() {
            return agentUrl;
        }

        public void setAgentUrl(String agentUrl) {
            this.agentUrl = agentUrl;
        }

        public String getWebsocket() {
            return websocket;
        }

        public void setWebsocket(String websocket) {
            this.websocket = websocket;
        }

        public CasBean getCas() {
            return cas;
        }

        public void setCas(CasBean cas) {
            this.cas = cas;
        }

        public List<NavsBean> getNavs() {
            return navs;
        }

        public void setNavs(List<NavsBean> navs) {
            this.navs = navs;
        }

        public List<LoginNavBean> getLoginNav() {
            return loginNav;
        }

        public void setLoginNav(List<LoginNavBean> loginNav) {
            this.loginNav = loginNav;
        }

        public List<WsBean> getWs() {
            return ws;
        }

        public String getAppregister() {
            return appregister;
        }

        public void setAppregister(String appregister) {
            this.appregister = appregister;
        }

        public void setWs(List<WsBean> ws) {
            this.ws = ws;
        }

        public static class CasBean {
            /**
             * basePath : http://cas.www.sscoin.cc
             * applications : [{"name":"Home","basePath":"http://api.sscoin.cc/cms-api","service":"http://api.sscoin.cc/cms-api/cas?client_name=CasClient"},{"name":"CTC","basePath":"http://api.sscoin.cc/otc","service":"http://api.sscoin.cc/otc/cas?client_name=CasClient"},{"name":"AC","basePath":"http://api.sscoin.cc/ac","service":"http://api.sscoin.cc/ac/cas?client_name=CasClient"},{"name":"UC","basePath":"http://api.sscoin.cc/uc","service":"http://api.sscoin.cc/uc/cas?client_name=CasClient"},{"name":"OTC","basePath":"http://api.sscoin.cc/otc-system","service":"http://api.sscoin.cc/otc-system/cas?client_name=CasClient"},{"name":"SPOT","basePath":"http://api.sscoin.cc/spot","service":"http://api.sscoin.cc/spot/cas?client_name=CasClient"},{"name":"CMS","basePath":"http://api.sscoin.cc/cms-api","service":"http://api.sscoin.cc/cms-api/cas?client_name=CasClient"},{"name":"KLINE","basePath":"http://api.sscoin.cc/kline","service":""},{"name":"QUOTE","basePath":"http://api.sscoin.cc/quote","service":""},{"name":"PRICE","basePath":"http://api.sscoin.cc/price-api","service":""},{"name":"CFD","basePath":"http://api.sscoin.cc/cfd","service":"http://api.sscoin.cc/cfd/cas?client_name=CasClient"}]
             */

            private String basePath;
            private List<ApplicationsBean> applications;

            public String getBasePath() {
                return basePath;
            }

            public void setBasePath(String basePath) {
                this.basePath = basePath;
            }

            public List<ApplicationsBean> getApplications() {
                return applications;
            }

            public void setApplications(List<ApplicationsBean> applications) {
                this.applications = applications;
            }

            public static class ApplicationsBean {
                /**
                 * name : Home
                 * basePath : http://api.sscoin.cc/cms-api
                 * service : http://api.sscoin.cc/cms-api/cas?client_name=CasClient
                 */

                private String name;
                private String basePath;
                private String service;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getBasePath() {
                    return basePath;
                }

                public void setBasePath(String basePath) {
                    this.basePath = basePath;
                }

                public String getService() {
                    return service;
                }

                public void setService(String service) {
                    this.service = service;
                }
            }
        }

        public static class NavsBean {
            /**
             * name : c2c
             * url : http://www.sscoin.cc/c2c
             */

            private String name;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class LoginNavBean {
            private List<AssetBean> asset;
            private List<OrderBean> order;
            private List<MemberBean> member;

            public List<AssetBean> getAsset() {
                return asset;
            }

            public void setAsset(List<AssetBean> asset) {
                this.asset = asset;
            }

            public List<OrderBean> getOrder() {
                return order;
            }

            public void setOrder(List<OrderBean> order) {
                this.order = order;
            }

            public List<MemberBean> getMember() {
                return member;
            }

            public void setMember(List<MemberBean> member) {
                this.member = member;
            }

            public static class AssetBean {
                /**
                 * name : MyAsset
                 * url : http://www.sscoin.cc/ac/#/index?type=SPOT
                 */

                private String name;
                private String url;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class OrderBean {
                /**
                 * name : Current_Order
                 * url : http://www.sscoin.cc/spot/#/entrust?type=0
                 */

                private String name;
                private String url;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class MemberBean {
                /**
                 * name : Order_Detail
                 * url : http://www.sscoin.cc/ac/#/record
                 */

                private String name;
                private String url;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }

        public static class WsBean {
            /**
             * id : 1
             * ip : 60.168.155.150
             * address : 合肥
             * status : 1
             * createTime : 1553496813000
             * domainName :
             * sort : 1
             * protocol :
             * path :
             * identity :
             */

            private String id;
            private String ip;
            private String address;
            private int status;
            private long createTime;
            private String domainName;
            private int sort;
            private String protocol;
            private String path;
            private String identity;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getDomainName() {
                return domainName;
            }

            public void setDomainName(String domainName) {
                this.domainName = domainName;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getProtocol() {
                return protocol;
            }

            public void setProtocol(String protocol) {
                this.protocol = protocol;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getIdentity() {
                return identity;
            }

            public void setIdentity(String identity) {
                this.identity = identity;
            }
        }
    }
}

