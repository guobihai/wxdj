package com.smt.wxdj.swxdj.dao;

public class TokenInfo {

    /**
     * access_token : eyJhbGciOiJSUzI1NiIsImtpZCI6Ijc3OEVEQTUxOUJBMzg1MkYyQzc1NkY1NzI0QTQ5M0U4IiwidHlwIjoiYXQrand0In0.eyJuYmYiOjE2MTg2MjkwMDksImV4cCI6MTY1MDE2NTAwOSwiaXNzIjoiaHR0cHM6Ly9hdXRoLndpdC11bmlvbi5jb20iLCJhdWQiOlsiQmFja2VuZEdhdGV3YXkiLCJCaWxsaW5nU2VydmljZSIsIkNGU1NlcnZpY2UiLCJDbnRyU2VydmljZSIsIkNvbnRhaW5lckNSU2VydmljZSIsIkVESVNlcnZpY2UiLCJIUlNlcnZpY2UiLCJJZGVudGl0eVNlcnZpY2UiLCJJbnRlcm5hbEdhdGV3YXkiLCJNZXNzYWdlU2VydmljZSIsIlBsYXRmb3JtU2VydmljZSIsIlJlcG9ydFNlcnZpY2UiLCJTeXN0ZW1TZXJ2aWNlIiwiU3lzdGVtU2V0dGluZ1NlcnZpY2UiLCJUZW5hbnRTZXJ2aWNlIiwiVHJ1Y2tTZXJ2aWNlIiwiVmVzc2VsU2VydmljZSJdLCJjbGllbnRfaWQiOiJiYXNpYy13ZWIiLCJzdWIiOiIzOWY5NjdjYi0wMzRkLTYzZjItYTEzMi1jNGFjZGE0YWU3ZmYiLCJhdXRoX3RpbWUiOjE2MTg2MjkwMDksImlkcCI6ImxvY2FsIiwidGVuYW50aWQiOiIzOWY5NjdjYi0wMDQ5LWQyY2QtNzA5OS0zYmFjZjQ5OTczZjkiLCJyb2xlIjoiYWRtaW4iLCJwaG9uZV9udW1iZXIiOiIxMzMzMzMzMzMzMyIsInBob25lX251bWJlcl92ZXJpZmllZCI6IkZhbHNlIiwiZW1haWwiOiJ1c2VyQGV4YW1wbGUuY29tIiwiZW1haWxfdmVyaWZpZWQiOiJGYWxzZSIsIm5hbWUiOiJhZG1pbiIsImlhdCI6MTYxODYyOTAwOSwic2NvcGUiOlsiYWRkcmVzcyIsIkJhY2tlbmRHYXRld2F5IiwiQmlsbGluZ1NlcnZpY2UiLCJDRlNTZXJ2aWNlIiwiQ250clNlcnZpY2UiLCJDb250YWluZXJDUlNlcnZpY2UiLCJFRElTZXJ2aWNlIiwiZW1haWwiLCJIUlNlcnZpY2UiLCJJZGVudGl0eVNlcnZpY2UiLCJJbnRlcm5hbEdhdGV3YXkiLCJNZXNzYWdlU2VydmljZSIsIm9wZW5pZCIsInBob25lIiwiUGxhdGZvcm1TZXJ2aWNlIiwiUmVwb3J0U2VydmljZSIsInJvbGUiLCJTeXN0ZW1TZXJ2aWNlIiwiU3lzdGVtU2V0dGluZ1NlcnZpY2UiLCJUZW5hbnRTZXJ2aWNlIiwiVHJ1Y2tTZXJ2aWNlIiwiVmVzc2VsU2VydmljZSIsIm9mZmxpbmVfYWNjZXNzIl0sImFtciI6WyJwd2QiXX0.Dc6krtlj0bMywHlNKYEJdtNagdA_Kt8rVE7KTuDxvil0AuhiBpDpg1sL3jY6Bc9zpeVkyojdE2zSvkzK43h3uDBkidDKX0NgzidI3Qe4vh4As0TuGXZqIXv9pW4zZMyslW-EUQoSUVX-qfLffsVe-_Ia1WthXqv5z8IxlMaXZbRvQfcqExAggXhbOcmooXBmNp4BeKt5SDd67KFJtqc2_hATwxKhWReX-EZEeuTHox3jJFIn0s0GpaHbBM_ND4AqbbzUZvDF20DvhE-62RpOMaLOUaaBAKtgNVnQL26c64h7XDNbyMNCxseVwXvs5Xr2NEVsAGw6YwwB8LHdEBo9Ew
     * expires_in : 31536000
     * token_type : Bearer
     * refresh_token : A1B01B4A83EC2452C2DABE1FC282BB9802FCB19ACA1083DBC3B99C039D6F8EA3
     * scope : address BackendGateway BillingService CFSService CntrService ContainerCRService EDIService email HRService IdentityService InternalGateway MessageService offline_access openid phone PlatformService ReportService role SystemService SystemSettingService TenantService TruckService VesselService
     */

    private String access_token;
    private int expires_in;
    private String token_type;
    private String refresh_token;
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
